package com.linkare.rec.impl.newface.component.media;

import java.awt.Canvas;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.videolan.jvlc.Audio;
import org.videolan.jvlc.JVLC;
import org.videolan.jvlc.MediaDescriptor;
import org.videolan.jvlc.MediaList;
import org.videolan.jvlc.MediaListPlayer;
import org.videolan.jvlc.MediaPlayer;
import org.videolan.jvlc.VLM;
import org.videolan.jvlc.Video;
import org.videolan.jvlc.event.MediaPlayerListener;

import com.linkare.rec.impl.newface.component.media.events.MediaApplicationEventListener;
import com.linkare.rec.impl.newface.component.media.events.MediaNotConnectedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaStoppedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import com.linkare.rec.impl.newface.component.media.transcoding.TranscodingConfig;
import com.sun.jna.Platform;

/**
 * Wrapper em volta do JVLC e que contém as funcionalidades de controlo e
 * manipulação de vídeo e audio.
 * 
 * @author bcatarino
 */
public class VideoViewerController {

	private static final Logger LOGGER = Logger.getLogger(VideoViewerController.class.getName());

	/**
	 * The key that specifies the preference name for the path where the local
	 * VLC is installed.
	 */
	public static final String VLC_PATH_KEY = "vlcpath";

	private static VideoViewerController controller;

	/**
	 * Identificador dado à stream que se pretende gravar para ficheiro.
	 */
	private static final String BROADCAST_NAME = "MyBroadcast";

	/**
	 * Tempo de espera entre o pause e o play quando se move a posição do vídeo
	 * quando este está em pausa.
	 */
	private static final int PAUSE_TIME = 50;

	/**
	 * Time do Player que corresponde ao momento 0 da experiência e que tem um
	 * tempo relativo.
	 */
	private long relativeFrame0 = -1;

	/**
	 * Tempo absoluto que deverá corresponder à frame0 e que estará sincronizado
	 * com um tempo relativo do player.
	 */
	private long absoluteFrame0 = -1;

	/**
	 * Estado actual do media player.
	 */
	MediaPlayerState state;

	/**
	 * Principal componente de acesso às funções nativas do VLC.
	 */
	private final JVLC jvlc;

	/**
	 * Componente responsável pela parte de transcoding para os vários formatos
	 * suportados pelo VLC.
	 */
	private VLM vlm;

	/**
	 * Representa o player do VLC.
	 */
	private MediaPlayer player;

	/**
	 * Playlist.
	 */
	private MediaList mediaList;

	/**
	 * Player que mapeia a playlist.
	 */
	private MediaListPlayer mediaListPlayer;

	/**
	 * Controlador de vídeo do VLC.
	 */
	private Video video;

	/**
	 * Controlador de audio do VLC.
	 */
	private Audio audio;

	/**
	 * Listener com eventos associados ao media player.
	 */
	private MediaPlayerListener mediaListener;

	/**
	 * Event listeners do controller.
	 */
	EventListenerList eventListeners = new EventListenerList();

	/**
	 * URL to media for play
	 */
	private String mrl;

	private VideoViewerController() {

		jvlc = new JVLC();
		initPlayer();
	}

	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	private VideoViewerController(final String[] params) {

		jvlc = new JVLC(params);
		initPlayer();
	}

	/**
	 * Obtém uma instância para o controlador de vídeo, com opções por default.
	 * 
	 * @return instância do controlador do JVLC Player.
	 */
	public static VideoViewerController getInstance() {

		if (VideoViewerController.controller == null) {
			VideoViewerController.controller = new VideoViewerController();
		}
		return VideoViewerController.controller;
	}

	/**
	 * Obtém uma instância para o controlador de vídeo, permitindo definir
	 * parâmetros adicionais, à semelhança da instanciação do VLC nativo através
	 * da consola. Ter em conta que nem todos os parâmetros foram testados. É
	 * desconhecido se o JVLC oferece suporte para todos os parâmetros
	 * suportados nativamente.
	 * 
	 * @param params Parâmetros adicionais de criação do VLC (como especificado
	 *            na documentação do VLC). Cada posição do array corresponde a
	 *            um parâmetro adicional.
	 * @return instância do controlador do JVLC Player.
	 * @see <a
	 *      href="http://wiki.videolan.org/Documentation:Play_HowTo/Advanced_Use_of_VLC">
	 *      Lista de parâmetros suportados pelo VLC.</a>.
	 */
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public static VideoViewerController getInstance(final String[] params) {

		if (VideoViewerController.controller == null) {
			VideoViewerController.controller = new VideoViewerController(params);
		}
		return VideoViewerController.controller;
	}

	/**
	 * Cria a parent directory onde o ficheiro dado pelo destFile será criado.
	 * 
	 * @param destFile Caminho absoluto para um ficheiro.
	 */
	private void createParentDirs(final String destFile) {

		final File file = new File(destFile);
		final File parentDir = file.getParentFile();
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
	}

	/**
	 * Inicializa os vários objectos que fazem parte do VLC.
	 */
	private void initPlayer() {

		video = new Video(jvlc);
		audio = new Audio(jvlc);
		// this.player = new MediaPlayer(jvlc);

		mediaListener = getDefaultMediaListener();
		// this.player.addListener(mediaListener);

		// O construtor com parâmetros do JVLC não instancia a media list.
		// Nesses casos, é necessário garantir que a MediaList é instanciada.
		// this.mediaList = jvlc.getMediaList() == null
		// ? new MediaList(jvlc) : jvlc.getMediaList();
		// this.jvlc.setMediaList(mediaList);
		// this.mediaListPlayer = new MediaListPlayer(jvlc);
		// this.mediaListPlayer.setMediaList(mediaList);
		// this.mediaListPlayer.setMediaInstance(player);

		state = MediaPlayerState.EMPTY;
	}

	/**
	 * Define o Canvas onde será feito o rendering do vídeo. Se o vídeo já
	 * estiver associado a um canvas, faz o reparent. Faz o resize do vídeo para
	 * o tamanho do novo canvas.
	 * 
	 * @param jvlcCanvas
	 */
	public void setVideoOutput(final Canvas jvlcCanvas) {

		if (!player.hasVideoOutput()) {
			// Video output method is platform dependant. Perhaps the windows
			// lib version is old.
			if (Platform.isWindows()) {
				jvlc.setVideoOutput(jvlcCanvas);
			} else {
				jvlc.setVideoOutput(jvlcCanvas, player.getInstance());
			}
		} else {
			video.reparent(player, jvlcCanvas);
		}
		player.resize(jvlcCanvas.getWidth(), jvlcCanvas.getHeight());
	}

	/**
	 * Indica se já existe um canvas associado ao output do vídeo.
	 * 
	 * @return true, se sim. false, caso contrário.
	 */
	public boolean hasVideoOutput() {
		return player.hasVideoOutput();
	}

	/**
	 * Define o url do media a reproduzir. Inicializa os restantes objectos do
	 * JVLC que são específicos de cada execução de um media.
	 * 
	 * @param mrl URL a reproduzir. Pode ser um URL remoto ou local.
	 */
	public void setMediaToPlay(final String mrl) {
		this.mrl = mrl;
		final MediaDescriptor mediaDescriptor = new MediaDescriptor(jvlc, mrl);
		player = new MediaPlayer(mediaDescriptor);
		player.addListener(mediaListener);
		state = MediaPlayerState.STOPPED;
		resetFrame0();
	}

	/**
	 * Getter for the media url to play.
	 * 
	 * @return
	 */
	public String getMediaURL() {
		return mrl;
	}

	/**
	 * Define o url do media a reproduzir. Inicializa os restantes objectos do
	 * JVLC que são específicos de cada execução de um media. Define também que
	 * a stream recebida será gravada para o ficheiro em paralelo.
	 * 
	 * @param mrl URL a reproduzir. Pode ser um URL remoto ou local. Contudo, se
	 *            for um URL para um media local, a gravação para ficheiro não é
	 *            feita de forma sincronizada com a reprodução.
	 * @param config Configurações necessárias ao transcoding do vídeo para
	 *            ficheiro local.
	 * @param destFile Nome do ficheiro no qual se pretende gravar o vídeo.
	 */
	public void setMediaToPlay(final String mrl, final TranscodingConfig config, final String destFile) {

		setMediaToPlay(mrl);
		streamToFile(config, destFile);
	}

	/**
	 * Liberta os recursos.
	 */
	public void releaseMedia() {

	    if (player != null) {
		player.getMediaDescriptor().release();
		player.release();
	    }
	    
	    state = MediaPlayerState.EMPTY;
	}

	/**
	 * Devolve o nome do media que está actualmente à frente na lista de
	 * reprodução.
	 * 
	 * @return
	 */
	public String getCurrentMediaURL() {
		final MediaDescriptor descriptor = player.getMediaDescriptor();
		if (descriptor != null) {
			return descriptor.getMrl();
		}
		return "";
	}

	/**
	 * Devolve um Media Listener padrão que define o comportamento do player do
	 * VLC quando cada um dos eventos internos do JVLC é disparado.
	 * 
	 * @return
	 */
	private MediaPlayerListener getDefaultMediaListener() {

		return new MediaPlayerListener() {

			@Override
			public void playing(final MediaPlayer arg0) {
				state = MediaPlayerState.PLAYING;
			}

			@Override
			public void paused(final MediaPlayer arg0) {
				state = MediaPlayerState.PAUSED;
			}

			@Override
			public void stopped(final MediaPlayer arg0) {
				state = MediaPlayerState.STOPPED;
				fireMediaStoppedEvent(new MediaStoppedEvent(this));
			}

			@Override
			public void endReached(final MediaPlayer arg0) {

				VideoViewerController.LOGGER.info("MediaPlayer end reached.");

				// Se quando chega ao end está stopped é porque não recebeu mais
				// streams.
				if (state == MediaPlayerState.STOPPED) {
					fireMediaStoppedEvent(new MediaStoppedEvent(this));
				} else {
					fireMediaNotConnectedEvent(new MediaNotConnectedEvent(this));
				}

				state = MediaPlayerState.STOPPED;
			}

			@Override
			public void timeChanged(final MediaPlayer arg0, final long arg1) {
				fireMediaTimeChangedEvent(new MediaTimeChangedEvent(this));
			}

			@Override
			public void positionChanged(final MediaPlayer arg0) {
				// Do nothing
			}

			@Override
			public void errorOccurred(final MediaPlayer arg0) {
				// Bruno Do nothing?
			}
		};
	}

	/**
	 * Liga o som se este estiver desligado. Se o som estiver ligado, desliga.
	 */
	public void toggleMute() {
		audio.toggleMute();
	}

	/**
	 * Altera o volume do som.
	 * 
	 * @param units Valor para o qual se pretende alterar o som, entre 0 e 200.
	 */
	public void setVolume(final int units) {
		audio.setVolume(units);
	}

	/**
	 * Devolve o valor actual do volume de som.
	 * 
	 * @return
	 */
	public int getVolume() {
		return audio.getVolume();
	}

	/**
	 * Faz set à velocidade de reprodução do vídeo, permitindo fazer reprodução
	 * normal ou fast-forward.
	 * 
	 * @param rate Taxa à qual se pretende que o vídeo seja reproduzido. O valor
	 *            1 representa reprodução à velocidade normal. Valores
	 *            superiores a 1 representam fast-forward. Valores inferiores a
	 *            1 são ignorados.
	 */
	public void setPlayRate(final float rate) {
		player.setRate(rate);
	}

	/**
	 * Se o player estiver parado, mas com um media carregado, reproduz de novo.
	 * Caso contrário, faz togglePause. <br>
	 * Se tiver opção de streaming para ficheiro activada, faz streaming também
	 * para o destino escolhido. Deve ser usado apenas para vídeo locais.
	 */
	public void play() {

		if (player != null) {
			setPlayRate(1f);
			if (state == MediaPlayerState.STOPPED) {
				player.play();
				// mediaListPlayer.playItem(0, true);
				if (vlm != null) {
					vlm.playMedia(VideoViewerController.BROADCAST_NAME);
				}
			} else {
				pause();
				if (vlm != null) {
					vlm.pauseMedia(VideoViewerController.BROADCAST_NAME);
				}
			}
		} else {
			VideoViewerController.LOGGER.warning("There is no player!");
		}
	}

	
	/**
	 * Termina a execução do player. Se estiver a fazer transcoding, termina
	 * também e liberta os recursos do vlm.
	 */
	public void stop() {

		if (state != MediaPlayerState.PLAYING && state != MediaPlayerState.PAUSED) {
			return;
		}

		// Coloca a stopped para quando permitir determinar no evento de end
		// reached se chegou ao fim por acção do utilizador ou porque não
		// existem mais streams.
		state = MediaPlayerState.STOPPED;

		try {
			// mediaListPlayer.stop();
			player.stop();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		if (vlm != null) {
			vlm.stopMedia(VideoViewerController.BROADCAST_NAME);
		}

	}

	/**
	 * Coloca o player em pausa, no caso de estar a reproduzir um ficheiro
	 * local. Se estiver a reproduzir através de streaming, faz stop ao media.
	 */
	public void pause() {
		// TODO ver se existe alguma forma de fazer mesmo pause quando está a
		// reproduzir streaming (pause através do mediaPlayer ou do JVLC?) Em
		// alternativa, fazer setVideoOutput para um canvas escondido
		// mediaListPlayer.pause();
		player.pause();

		if (vlm != null) {
			vlm.pauseMedia(VideoViewerController.BROADCAST_NAME);
		}
	}

	/**
	 * Indica se o player está preparado para reproduzir media.
	 * 
	 * @return
	 */
	public boolean willPlay() {
		return player != null && player.willPlay();
	}

	/**
	 * Permite capturar uma frame e gravar numa imagem no formato png ou jpeg.
	 * Se a parent directory do ficheiro a ser criado não existir, será criada.
	 * Se width e height iguais a 0, é usado o tamanho da janela do vídeo.
	 * 
	 * @param fileName Nome do ficheiro que se pretende gravar. Pode ser um path
	 *            absoluto ou relativo.
	 * @param width Comprimento da imagem guardada, em pixeis.
	 * @param height Altura da imagem guardada, em pixeis.
	 */
	public void captureScreen(final String fileName, final int width, final int height) {
		createParentDirs(fileName);
		video.getSnapshot(player, fileName, width, height);
	}

	/**
	 * Liga/Desliga fullscreen.
	 */
	public void toggleFullScreen() {
		video.toggleFullscreen(player);
	}

	/**
	 * Liberta os recursos do VLC. Deve ser chamado sempre no final de uma
	 * aplicação que utilize esta funcionalidade.
	 */
	public void releaseVLC() {
		// TODO ver onde faz sentido chamar isto
		jvlc.release();
	}

	/**
	 * Grava a stream actual para ficheiro. Se a parent directory do ficheiro a
	 * ser criado não existir, será criada.
	 * 
	 * @param config Propriedades para configuração do módulo de transcoding do
	 *            vídeo para ficheiro. Todos os campos são obrigatórios.
	 * @param destFile URL para o filesystem local onde se pretende gravar o
	 *            ficheiro de vídeo.
	 */
	public void streamToFile(final TranscodingConfig config, final String destFile) {

		vlm = jvlc.getVLM();

		if (!validateTranscodingConfig(config)) {
			// TODO qual a excepção a enviar e a mensagem a devolver. Ver como
			// está feito a nível de internacionalização no resto do eLab
			throw new IllegalArgumentException();
		}

		// TODO more config needed/possible?
		final String sout = "#transcode{vcodec=" + config.getVideoCodec() + ",vb=" + config.getVideoBitrate()
				+ ",scale= " + config.getVideoScale() + ",acodec=" + config.getAudioCodec() + ",ab="
				+ config.getAudioBitrate() + ",channels=" + config.getSoundChannels() + ",audio-sync}"
				+ ":duplicate{dst=std{access=file,mux=" + config.getMuxer() + ",dst=" + destFile + "}}";

		createParentDirs(destFile);

		vlm.addBroadcast(VideoViewerController.BROADCAST_NAME, mediaList.getMediaDescriptorAtIndex(0).getMrl(), sout,
				new String[0], true, false);

	}

	/**
	 * Pára de fazer streaming, libertando os recursos do VLM.
	 */
	public void stopStreaming() {

		vlm.deleteMedia(VideoViewerController.BROADCAST_NAME);
		vlm.release();
		vlm = null;
	}

	/**
	 * Verifica se as propriedades de transcoding obrigatórias estão
	 * preenchidas.
	 * 
	 * @param config Conjunto de propriedades de configuração do vídeo.
	 * @return true se todas as propriedades necessárias ao transcoding da
	 *         stream recebida estiverem correctamente definidas. false caso
	 *         contrário.
	 */
	private boolean validateTranscodingConfig(final TranscodingConfig config) {

		if (config.getVideoCodec() == null || config.getVideoBitrate() <= 0 || config.getVideoScale() <= 0
				|| config.getMuxer() == null || config.getAudioCodec() == null || config.getAudioBitrate() <= 0
				|| config.getSoundChannels() <= 0) {
			return false;
		}
		return true;
	}

	/**
	 * Permite mover para uma posição do vídeo específica, relativa, de forma
	 * síncrona com a experiência, com base na frame0 previamente definida. Só
	 * funciona se estiver a reproduzir ficheiros locais. Lança um evento de
	 * MediaTimeChanged indicativo da alteração do current time do vídeo.
	 * 
	 * @param time Momento de tempo para o qual se pretende "saltar".
	 */
	public void moveTo(final long time) {

		// Apenas move o vídeo se a frame0 tiver sido inicializada.
		if (absoluteFrame0 == -1) {
			return;
		}

		final long diff = time - absoluteFrame0;
		final long nextTime = relativeFrame0 + diff;
		player.setTime(nextTime);

		fireMediaTimeChangedEvent(new MediaTimeChangedEvent(this));
		refreshPausedVideo();
	}

	/**
	 * Move o vídeo um determinado número de frames. Só funciona se estiver a
	 * reproduzir um ficheiro local. Lança um evento de MediaTimeChanged
	 * indicativo da alteração do current time do vídeo.
	 * 
	 * @param nFrames Número de frames que se pretende mover. Pode ser um valor
	 *            positivo (avançar) ou negativo (recuar).
	 */
	public void move(final int nFrames) {

		final long nextTime = getFrameToMove(nFrames);
		player.setTime(nextTime);

		fireMediaTimeChangedEvent(new MediaTimeChangedEvent(this));
		refreshPausedVideo();
	}

	/**
	 * Permite actualizar o ecrã do vídeo com a Frame actual, dada pelo
	 * videoplayer, no caso de o player estar em pausa.
	 */
	private void refreshPausedVideo() {

		// TODO ver se o VLC nativo tem o mesmo comportamento ou se existe
		// alguma configuração do VLC que actualiza automaticamente o vídeo
		// quando está em pausa.
		if (state == MediaPlayerState.PAUSED) {
			pause();
			// FIXME correcção que não seja um workaround.
			try {
				Thread.sleep(VideoViewerController.PAUSE_TIME);
			} catch (final InterruptedException e) {
				LOGGER.log(Level.WARNING,e.getMessage(),e);
			}
			pause();
		}
	}

	/**
	 * Calcula qual a nova frame para o qual se vai mover, baseado no nº de
	 * frames que se pretende mover o vídeo.
	 * 
	 * @param nFrames Número de frames que se pretende mover, face ao tempo
	 *            actual do player. Pode ser um valor positivo ou negativo.
	 * @return Novo tempo calculado que corresponde à frame actual mais o nº de
	 *         frames que se pretende mover.
	 */
	private long getFrameToMove(final int nFrames) {

		final float fps = player.getFPS();
		long currentTime = player.getTime();

		final double timeToMove = nFrames * 1000 / fps;

		// Recua N milisegundos para compensar o tempo do sleep em pausa
		// FIXME tem de ser um valor superior ao sleep time....
		if (nFrames < 0) {
			currentTime -= VideoViewerController.PAUSE_TIME;
		}

		final long nextTime = currentTime + (long) timeToMove;
		return nextTime;
	}

	/**
	 * Devolve o tempo que já passou desde que o vídeo começou a reprodução,
	 * relativamente ao início. Se o vídeo estiver parado, devolve 0.
	 * 
	 * @return
	 */
	public long getCurrentMediaTime() {
		if (state == MediaPlayerState.STOPPED || state == MediaPlayerState.EMPTY) {
			return 0L;
		}
		return player.getTime();
	}

	/**
	 * Devolve o tamanho total do media actualmente em reprodução. Se estiver a
	 * reproduzir em streaming, devolve sempre 0.
	 * 
	 * @return
	 */
	public long getTotalMediaTime() {

		if (player != null) {
			return player.getLength();
		}
		return 0;
	}

	/**
	 * Calcula a nova posição do vídeo baseado no valor actual que um dado
	 * marcador de tempo tem (por exemplo, um slider), face ao seu valor máximo.
	 * Só deve ser chamado se o media a reproduzir for um ficheiro local.
	 * 
	 * @param pos Valor actual do marcador de tempo.
	 * @param max Valor máximo do marcador de tempo.
	 */
	public void adjustVideoPosition(final int pos, final int max) {

		// TODO falta sincronizar correctamente para que o player não sobreponha
		// frames às definidas por acção do utilizador.
		VideoViewerController.LOGGER.fine("TimeChanged event tratado por " + Thread.currentThread());
		final long newPosition = getNewVideoPosition(pos, max);
		player.setTime(newPosition);
		refreshPausedVideo();
	}

	/**
	 * Calcula o novo tempo do vídeo com base na posição actual e posição máxima
	 * de um dado marcador de tempo.
	 * 
	 * @param pos Posição actual.
	 * @param max Tamanho máximo.
	 * @return Nova posição relativa do media a ser reproduzido.
	 */
	private long getNewVideoPosition(final int pos, final int max) {

		final long movieSize = player.getLength();
		return (movieSize * pos / max);
	}

	/**
	 * Devolve o estado em que o player se encontra actualmente.
	 * 
	 * @return
	 */
	public MediaPlayerState getCurrentState() {
		return state;
	}

	/**
	 * Define qual o tempo relativo ao player que corresponde ao início do
	 * sincronismo com a experiência, ou seja, a frame 0.<br>
	 * Define também o Timestamp absoluto que lhe corresponde.
	 * 
	 * @param frame0 Tempo absoluto que corresponde ao início da experiência.
	 */
	public void setFrame0(final long frame0) {

		relativeFrame0 = player.getTime();
		absoluteFrame0 = frame0;
	}

	/**
	 * Coloca a frame0 com o valor inicial.
	 */
	private void resetFrame0() {
		relativeFrame0 = -1;
		absoluteFrame0 = -1;
	}

	/* *******************************************************************
	 * Código para event handling *
	 ******************************************************************* */

	
	public void addMediaApplicationEventListener(final MediaApplicationEventListener listener) {
		eventListeners.add(MediaApplicationEventListener.class, listener);
	}

	public void removeMediaApplicationEventListener(final MediaApplicationEventListener listener) {
		eventListeners.remove(MediaApplicationEventListener.class, listener);
	}

	private void fireMediaTimeChangedEvent(final MediaTimeChangedEvent evt) {

		final MediaApplicationEventListener[] listeners = eventListeners
				.getListeners(MediaApplicationEventListener.class);
		for (final MediaApplicationEventListener listener : listeners) {
			listener.timeChanged(evt);
		}
	}

	private void fireMediaStoppedEvent(final MediaStoppedEvent evt) {
		final MediaApplicationEventListener[] listeners = eventListeners
				.getListeners(MediaApplicationEventListener.class);
		for (final MediaApplicationEventListener listener : listeners) {
			listener.stopped(evt);
		}
	}

	private void fireMediaNotConnectedEvent(final MediaNotConnectedEvent evt) {
		final MediaApplicationEventListener[] listeners = eventListeners
				.getListeners(MediaApplicationEventListener.class);
		for (final MediaApplicationEventListener listener : listeners) {
			listener.notConnected(evt);
		}
	}
}
