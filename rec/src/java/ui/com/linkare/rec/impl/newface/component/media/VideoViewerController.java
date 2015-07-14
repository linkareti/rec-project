package com.linkare.rec.impl.newface.component.media;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

import com.linkare.rec.impl.newface.component.media.events.MediaApplicationEventListener;
import com.linkare.rec.impl.newface.component.media.events.MediaNotConnectedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaStoppedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import com.linkare.rec.impl.newface.component.media.transcoding.TranscodingConfig;

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
	private MediaPlayerState state;

	/**
	 * Listener com eventos associados ao media player.
	 */
	private MediaPlayerEventListener mediaListener;

	/**
	 * Event listeners do controller.
	 */
	EventListenerList eventListeners = new EventListenerList();

	/**
	 * URL to media for play
	 */
	private String mrl;

	private VideoViewerController() {
		state=MediaPlayerState.EMPTY;
		mediaListener=getDefaultMediaListener();
		if(MediaSetup.isVideoSubSystemAvailable()) {
			MediaSetup.getPlayer().addMediaPlayerEventListener(mediaListener);
		}
	}

	/**
	 * Obtém uma instância para o controlador de vídeo, com opções por default.
	 * 
	 * @param window
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
	 * Define o url do media a reproduzir. Inicializa os restantes objectos do
	 * VLCJ que são específicos de cada execução de um media.
	 * 
	 * @param mrl URL a reproduzir. Pode ser um URL remoto ou local.
	 */
	public void setMediaToPlay(final String mrl) {
		this.mrl = mrl;
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
	 * VLCJ que são específicos de cada execução de um media. Define também que
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
	 * Devolve o nome do media que está actualmente à frente na lista de
	 * reprodução.
	 * 
	 * @return
	 */
	public String getCurrentMediaURL() {
		return MediaSetup.getPlayer().mrl();
	}

	/**
	 * Devolve um Media Listener padrão que define o comportamento do player do
	 * VLC quando cada um dos eventos internos do VLCJ é disparado.
	 * 
	 * @return
	 */
	private MediaPlayerEventListener getDefaultMediaListener() {

		return new MediaPlayerEventAdapter() {

			@Override
			public void playing(final MediaPlayer mediaPlayer) {
				state = MediaPlayerState.PLAYING;
			}

			@Override
			public void paused(final MediaPlayer mediaPlayer) {
				state = MediaPlayerState.PAUSED;
			}

			@Override
			public void stopped(final MediaPlayer mediaPlayer) {
				state = MediaPlayerState.STOPPED;
				fireMediaStoppedEvent(new MediaStoppedEvent(this));
			}

			@Override
			public void timeChanged(final MediaPlayer mediaPlayer, final long arg1) {
				fireMediaTimeChangedEvent(new MediaTimeChangedEvent(this));
			}

			@Override
			public void finished(MediaPlayer mediaPlayer) {
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

		};
	}

	/**
	 * Liga o som se este estiver desligado. Se o som estiver ligado, desliga.
	 */
	public void toggleMute() {
		MediaSetup.getPlayer().mute(!MediaSetup.getPlayer().isMute());
	}

	/**
	 * Altera o volume do som.
	 * 
	 * @param units Valor para o qual se pretende alterar o som, entre 0 e 200.
	 */
	public void setVolume(final int units) {
		MediaSetup.getPlayer().setVolume(units);
	}

	/**
	 * Devolve o valor actual do volume de som.
	 * 
	 * @return
	 */
	public int getVolume() {
		return MediaSetup.getPlayer().getVolume();
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
		MediaSetup.getPlayer().setRate(rate);
	}

	/**
	 * Se o player estiver parado, mas com um media carregado, reproduz de novo.
	 * Caso contrário, faz togglePause. <br>
	 * Se tiver opção de streaming para ficheiro activada, faz streaming também
	 * para o destino escolhido. Deve ser usado apenas para vídeo locais.
	 */
	public void play() {

		Runnable playAction = new Runnable() {

			@Override
			public void run() {
				if (MediaSetup.getPlayer() != null) {
					if (state == MediaPlayerState.STOPPED) {
						MediaSetup.getPlayer().removeMediaPlayerEventListener(mediaListener);
						MediaSetup.getPlayer().enableOverlay(false);
						VideoViewerController.LOGGER.finest("Playing media on EDT: " + mrl);
						MediaSetup.getPlayer().playMedia(mrl);
						MediaSetup.getPlayer().enableOverlay(true);
						MediaSetup.getPlayer().addMediaPlayerEventListener(mediaListener);
					} else {
						VideoViewerController.LOGGER.finest("Pausing media on EDT: " + mrl);
						pause();
					}
				} else {
					VideoViewerController.LOGGER.warning("There is no player!");
				}
			}
		};

		// if (!SwingUtilities.isEventDispatchThread()) {
		// try {
		// SwingUtilities.invokeAndWait(playAction);
		// } catch (Exception ignored) {
		//
		// }
		// } else {
		playAction.run();
		// }

	}

	/**
	 * Termina a execução do player. Se estiver a fazer transcoding, termina
	 * também e liberta os recursos do vlm.
	 */
	public void stop() {

		Runnable stopAction = new Runnable() {

			@Override
			public void run() {
				if (state != MediaPlayerState.PLAYING && state != MediaPlayerState.PAUSED) {
					return;
				}

				state = MediaPlayerState.STOPPED;

				try {
					MediaSetup.getPlayer().stop();
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
				}
			}
		};

		// if (!SwingUtilities.isEventDispatchThread()) {
		// try {
		// SwingUtilities.invokeAndWait(stopAction);
		// } catch (Exception ignored) {
		//
		// }
		// } else {
		stopAction.run();
		// }

	}

	/**
	 * Coloca o player em pausa, no caso de estar a reproduzir um ficheiro
	 * local. Se estiver a reproduzir através de streaming, faz stop ao media.
	 */
	public void pause() {

		Runnable pauseAction = new Runnable() {

			@Override
			public void run() {
				MediaSetup.getPlayer().pause();
			}
		};

		// if (!SwingUtilities.isEventDispatchThread()) {
		// try {
		// SwingUtilities.invokeAndWait(pauseAction);
		// } catch (Exception ignored) {
		//
		// }
		// } else {
		pauseAction.run();
		// }

	}

	/**
	 * Indica se o player está preparado para reproduzir media.
	 * 
	 * @return
	 */
	public boolean willPlay() {
		return MediaSetup.getPlayer() != null && MediaSetup.getPlayer().isPlayable();
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
		MediaSetup.getPlayer().saveSnapshot(new File(fileName), width, height);
	}

	/**
	 * Liga/Desliga fullscreen.
	 */
	public void toggleFullScreen() {
		MediaSetup.getPlayer().toggleFullScreen();
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

		if (!validateTranscodingConfig(config)) {
			throw new IllegalArgumentException();
		}

		final String sout = "#transcode{vcodec=" + config.getVideoCodec() + ",vb=" + config.getVideoBitrate()
				+ ",scale= " + config.getVideoScale() + ",acodec=" + config.getAudioCodec() + ",ab="
				+ config.getAudioBitrate() + ",channels=" + config.getSoundChannels() + ",audio-sync}"
				+ ":duplicate{dst=std{access=file,mux=" + config.getMuxer() + ",dst=" + destFile + "},dst=display}";

		createParentDirs(destFile);

		MediaSetup.getPlayer().playMedia(MediaSetup.getPlayer().mrl(), sout);
	}

	/**
	 * Pára de fazer streaming, libertando os recursos do VLM.
	 */
	public void stopStreaming() {

		String mediaUrl = getMediaURL();
		MediaSetup.getPlayer().stop();
		MediaSetup.getPlayer().playMedia(mediaUrl);

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
		MediaSetup.getPlayer().setTime(nextTime);

		fireMediaTimeChangedEvent(new MediaTimeChangedEvent(this));
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
		MediaSetup.getPlayer().skip((long) (nFrames * 1000f / MediaSetup.getPlayer().getFps()));
		fireMediaTimeChangedEvent(new MediaTimeChangedEvent(this));
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
		return MediaSetup.getPlayer().getTime();
	}

	/**
	 * Devolve o tamanho total do media actualmente em reprodução. Se estiver a
	 * reproduzir em streaming, devolve sempre 0.
	 * 
	 * @return
	 */
	public long getTotalMediaTime() {

		if (MediaSetup.getPlayer() != null) {
			return MediaSetup.getPlayer().getLength();
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

		VideoViewerController.LOGGER.fine("TimeChanged event tratado por " + Thread.currentThread());
		final long newPosition = getNewVideoPosition(pos, max);
		MediaSetup.getPlayer().setTime(newPosition);
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

		final long movieSize = MediaSetup.getPlayer().getLength();
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

		relativeFrame0 = MediaSetup.getPlayer().getTime();
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
	 * ******************************************************************
	 */

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
