package com.linkare.rec.impl.newface.component.media;

import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEvent;
import com.linkare.rec.impl.newface.component.media.events.MediaTimeChangedEventListener;
import com.linkare.rec.impl.newface.component.media.transcoding.TranscodingConfig;
import java.awt.Canvas;
import java.io.File;
import javax.swing.event.EventListenerList;
import org.videolan.jvlc.Audio;
import org.videolan.jvlc.JVLC;
import org.videolan.jvlc.LoggerVerbosityLevel;
import org.videolan.jvlc.MediaDescriptor;
import org.videolan.jvlc.MediaList;
import org.videolan.jvlc.MediaListPlayer;
import org.videolan.jvlc.MediaPlayer;
import org.videolan.jvlc.VLM;
import org.videolan.jvlc.Video;
import org.videolan.jvlc.event.MediaPlayerListener;

/**
 * Wrapper em volta do JVLC e que contém as funcionalidades de controlo
 * e manipulação de vídeo e audio.
 * @author bcatarino
 */
public class VideoViewerController {

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
     * Indica se está a gravar também para ficheiro ou não.
     */
    private boolean streamToFile = false;

    /**
     * Time do Player que corresponde ao momento 0 da experiência e que tem um
     * tempo relativo.
     */
    private long relativeFrame0;

    /**
     * Tempo absoluto que deverá corresponder à frame0 e que estará 
     * sincronizado com um tempo relativo do player.
     */
    private long absoluteFrame0;

    /**
     * Estado actual do media player.
     */
    MediaPlayerState state;

    /**
     * Principal componente de acesso às funções nativas do VLC.
     */
    private JVLC jvlc;

    /**
     * Componente responsável pela parte de transcoding para os vários
     * formatos suportados pelo VLC.
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


    private VideoViewerController() {

        jvlc = new JVLC();
        initPlayer();
    }

    private VideoViewerController(String[] params) {

        jvlc = new JVLC(params);
        initPlayer();
    }

    /**
     * Obtém uma instância para o controlador de vídeo, com opções por default.
     * @return instância do controlador do JVLC Player.
     */
    public static VideoViewerController getInstance() {

        if (controller == null)
            controller = new VideoViewerController();
        return controller;
    }

    /**
     * Obtém uma instância para o controlador de vídeo, permitindo definir
     * parâmetros adicionais, à semelhança da instanciação do VLC nativo através
     * da consola. Ter em conta que nem todos os parâmetros foram testados.
     * É desconhecido se o JVLC oferece suporte para todos os parâmetros
     * suportados nativamente.
     * @param params Parâmetros adicionais de criação do VLC (como especificado
     * na documentação do VLC). Cada posição do array corresponde a um parâmetro
     * adicional.
     * @return instância do controlador do JVLC Player.
     * @see <a href="http://wiki.videolan.org/Documentation:Play_HowTo/Advanced_Use_of_VLC">
     * Lista de parâmetros suportados pelo VLC.</a>.
     */
    public static VideoViewerController getInstance(String[] params) {

        if (controller == null)
            controller = new VideoViewerController(params);
        return controller;
    }

    /**
     * Cria a parent directory onde o ficheiro dado pelo destFile será criado.
     * @param destFile Caminho absoluto para um ficheiro.
     */
    private void createParentDirs(String destFile) {

        File file = new File(destFile);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }
    }

    /**
     * Inicializa os vários objectos que fazem parte do VLC.
     */
    private void initPlayer() {

        this.jvlc.setLogVerbosity(LoggerVerbosityLevel.WARNING);
        this.video = new Video(jvlc);
        this.audio = new Audio(jvlc);
        // O construtor com parâmetros do JVLC não instancia a media list.
        // Nesses casos, é necessário garantir que a MediaList é instanciada.
//        this.mediaList = jvlc.getMediaList() == null
//                ? new MediaList(jvlc) : jvlc.getMediaList();
//        this.mediaListPlayer = new MediaListPlayer(jvlc);
        this.vlm = jvlc.getVLM();
//        this.mediaListPlayer.setMediaList(mediaList);
        this.state = MediaPlayerState.EMPTY;
    }

    
    /**
     * Define o Canvas onde será feito o rendering do vídeo. Se o vídeo já
     * estiver associado a um canvas, faz o reparent e o resize do vídeo para
     * o tamanho do novo canvas.
     * @param jvlcCanvas
     */
    public void setVideoOutput(final Canvas jvlcCanvas) {
        
        if (!player.hasVideoOutput()) {
            jvlc.setVideoOutput(jvlcCanvas);
        } else {
            video.reparent(player, jvlcCanvas);
            player.resize(jvlcCanvas.getWidth(), jvlcCanvas.getHeight());
        }
    }

    /**
     * Indica se já existe um canvas associado ao output do vídeo. true, se sim.
     * false, caso contrário.
     * @return
     */
    public boolean hasVideoOutput() {
        return player.hasVideoOutput();
    }

    /**
     * Define o url do media a reproduzir. Inicializa os restantes objectos
     * do JVLC que são específicos de cada execução de um media.
     * @param mrl URL a reproduzir. Pode ser um URL remoto ou local.
     */
    public void setMediaToPlay(String mrl) {

//        releaseMedia();
        // O construtor com parâmetros do JVLC não instancia a media list.
        // Nesses casos, é necessário garantir que a MediaList é instanciada.
        this.mediaList = jvlc.getMediaList() == null
                ? new MediaList(jvlc) : jvlc.getMediaList();
        this.mediaListPlayer = new MediaListPlayer(jvlc);
        this.mediaListPlayer.setMediaList(mediaList);
        mediaListener = getDefaultMediaListener();
        MediaDescriptor mediaDescriptor = new MediaDescriptor(jvlc, mrl);
        this.player = mediaDescriptor.getMediaPlayer();
        this.player.addListener(mediaListener);
        this.mediaListPlayer.setMediaInstance(player);
        this.mediaList.insertMediaDescriptor(mediaDescriptor, 0);
        this.state = MediaPlayerState.STOPPED;
    }

    /**
     * Define o url do media a reproduzir. Inicializa os restantes objectos
     * do JVLC que são específicos de cada execução de um media. Define também
     * que a stream recebida será gravada para o ficheiro em paralelo. 
     * @param mrl URL a reproduzir. Pode ser um URL remoto ou local. Contudo,
     * se for um URL para um media local, a gravação para ficheiro não é feita
     * de forma sincronizada com a reprodução.
     * @param config Configurações necessárias ao transcoding do vídeo para
     * ficheiro local.
     * @param destFile Nome do ficheiro no qual se pretende gravar o vídeo.
     */
    public void setMediaToPlay(String mrl, TranscodingConfig config,
            String destFile) {

        setMediaToPlay(mrl);
        //TODO Estudar a necessidade de sincronizar reprodução de vídeo local com gravação para ficheiro.
        streamToFile(config, destFile);
    }
    
    /**
     * Liberta os recursos.
     */
    public void releaseMedia() {
        //TODO ver se este release tem, de facto algum efeito. O JVLC já faz algures o release do mediaDescriptor (ver onde!)
//        if (mediaList.size() > 0) {
//            MediaDescriptor md = mediaList.getMediaDescriptorAtIndex(0);
//            md.release();
//        }

        // Os recursos são libertados no finalize dos objectos.
        this.mediaListener = null;
        this.player = null;
        this.mediaListPlayer = null;
        this.mediaList = null;
        this.state = MediaPlayerState.EMPTY;
    }

    /**
     * Devolve um Media Listener padrão que define o comportamento do player
     * do VLC quando cada um dos eventos internos do JVLC é disparado.
     * @return
     */
    private MediaPlayerListener getDefaultMediaListener() {

        return new MediaPlayerListener() {

            @Override
            public void playing(MediaPlayer arg0) {
                VideoViewerController.this.state = MediaPlayerState.PLAYING;
            }

            @Override
            public void paused(MediaPlayer arg0) {
                VideoViewerController.this.state = MediaPlayerState.PAUSED;
            }

            @Override
            public void stopped(MediaPlayer arg0) {
                //TODO por vezes o JVLC pára sem razão aparente. perceber diferenças e como dar a volta! Ver se o evento associado é um stopped ou endReached
                VideoViewerController.this.state = MediaPlayerState.STOPPED;
                fireMediaTimeChangedEvent(new MediaTimeChangedEvent(arg0));
            }

            @Override
            public void endReached(MediaPlayer arg0) {
                VideoViewerController.this.state = MediaPlayerState.STOPPED;
                VideoViewerController.this.releaseMedia();
                fireMediaTimeChangedEvent(new MediaTimeChangedEvent(arg0));
            }

            @Override
            public void timeChanged(MediaPlayer arg0, long arg1) {
                fireMediaTimeChangedEvent(new MediaTimeChangedEvent(arg0));
            }

            @Override
            public void positionChanged(MediaPlayer arg0) {
            }

            @Override
            public void errorOccurred(MediaPlayer arg0) {
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
     * @param units Valor para o qual se pretende alterar o som, entre 0 e 200.
     */
    public void setVolume(int units) {
        audio.setVolume(units);
    }

    /**
     * Devolve o valor actual do volume de som.
     * @return
     */
    public int getVolume() {
        return audio.getVolume();
    }

    /**
     * Faz set à velocidade de reprodução do vídeo, permitindo fazer reprodução
     * normal ou fast-forward.
     * @param rate Taxa à qual se pretende que o vídeo seja reproduzido. O
     * valor 1 representa reprodução à velocidade normal. Valores superiores a
     * 1 representam fast-forward.
     */
    public void setPlayRate(float rate) {
        //TODO curiosity, valores inferiores a 1 têm o mesmo efeito que 1 ou no effect?
        player.setRate(rate);
    }

    /**
     * Se o player estiver parado, mas com um media carregado, reproduz de novo.
     *  Caso contrário, faz togglePause. <br>
     * Se tiver opção de streaming para ficheiro activada, faz streaming também
     * para o destino escolhido.
     */
    public void play() {

        setPlayRate(1f);
        if (this.state == MediaPlayerState.STOPPED) {
            mediaListPlayer.playItem(0, true);
            if(streamToFile)
                vlm.playMedia(BROADCAST_NAME);
        } else {
            pause();
            if(streamToFile)
                vlm.pauseMedia(BROADCAST_NAME);
        }
    }

    /**
     * Termina a execução do player.
     */
    public void stop() {

        mediaListPlayer.stop();

        if (streamToFile)
            vlm.stopMedia(BROADCAST_NAME);

        //TODO deve libertar algum tipo de recursos???
    }

    /**
     * Coloca o player em pausa, no caso de estar a reproduzir um ficheiro
     * local. Se estiver a reproduzir através de streaming, faz stop ao media.
     */
    public void pause() {
        //TODO ver se existe alguma forma de fazer mesmo pause quando está a reproduzir streaming (pause através do mediaPlayer ou do JVLC?)
        mediaListPlayer.pause();
        
        //TODO em streaming, em vez de pausar, pode fazer reparent para um canvas escondido... ver com JP se pode ser!!!!

        if (streamToFile)
            vlm.pauseMedia(BROADCAST_NAME);
    }

    /**
     * Indica se o player está preparado para reproduzir media.
     * @return
     */
    public boolean willPlay() {
        return player != null && player.willPlay();
    }

    /**
     * Permite capturar uma frame e gravar numa imagem no formato png ou jpeg.
     * Se a parent directory do ficheiro a ser criado não existir, será criada.
     * Se width e height iguais a 0, é usado o tamanho da janela do vídeo.
     * @param fileName Nome do ficheiro que se pretende gravar. Pode ser um
     * path absoluto ou relativo.
     * @param width Comprimento da imagem guardada, em pixeis.
     * @param height Altura da imagem guardada, em pixeis.
     */
    public void captureScreen(String fileName, int width, int height) {
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
        //TODO ver efeito disto
        jvlc.release();
    }

    /**
     * Grava a stream actual para ficheiro. Se a parent directory do ficheiro a
     * ser criado não existir, será criada.
     * @param config Propriedades para configuração do módulo de transcoding do
     * vídeo para ficheiro. Todos os campos são obrigatórios.
     * @param destFile URL para o filesystem local onde se pretende gravar
     * o ficheiro de vídeo.
     */
    public void streamToFile(TranscodingConfig config, String destFile) {

        if (!validateTranscodingConfig(config)) {
            //TODO qual a excepção a enviar e a mensagem a devolver. Ver como está feito a nível de internacionalização.
            throw new IllegalArgumentException();
        }

        String sout = "#transcode{vcodec=" + config.getVideoCodec()
                + ",vb=" + config.getVideoBitrate()
                + ",scale= " + config.getVideoScale()
                + ",acodec=" + config.getAudioCodec()
                + ",ab=" + config.getAudioBitrate()
                + ",channels=" + config.getSoundChannels()
                + ",audio-sync}" +
                ":duplicate{dst=std{access=file,mux=" + config.getMuxer()
                                    + ",dst=" + destFile + "}}";

        createParentDirs(destFile);

        vlm.addBroadcast(BROADCAST_NAME, mediaList.getMediaDescriptorAtIndex(0)
                .getMrl(), sout, new String[0], true, false);

        streamToFile = true;
    }

    /**
     * Pára de fazer streaming, libertando os recursos do VLM.
     */
    public void stopStreaming() {

        vlm.deleteMedia(BROADCAST_NAME);
        //TODO ver se o release vem antes ou depois do delete e as implicacoes
        vlm.release();
        streamToFile = false;
    }

    /**
     * Verifica se as propriedades de transcoding obrigatórias estão preenchidas.
     * @param config Conjunto de propriedades de configuração do vídeo.
     * @return true se todas as propriedades necessárias ao transcoding da
     * stream recebida estiverem correctamente definidas. false caso contrário.
     */
    private boolean validateTranscodingConfig(TranscodingConfig config) {
        
        if (config.getVideoCodec() == null 
                || config.getVideoBitrate() <= 0
                || config.getVideoScale() <= 0 
                || config.getMuxer() == null
                || config.getAudioCodec() == null 
                || config.getAudioBitrate() <= 0
                || config.getSoundChannels() <= 0)
            return false;
        return true;
    }

    /**
     * Permite mover para uma posição do vídeo específica, relativa, de forma
     * síncrona com a experiência, com base na frame0 previamente definida. Só
     * funciona se estiver a reproduzir ficheiros locais. Lança um evento de
     * MediaTimeChanged indicativo da alteração do current time do vídeo.
     * @param time Momento de tempo para o qual se pretende "saltar".
     */
    public void moveTo(long time) {

        long diff = time - absoluteFrame0;
        long nextTime = relativeFrame0 + diff;
        player.setTime(nextTime);

        fireMediaTimeChangedEvent(new MediaTimeChangedEvent(player));
        refreshPausedVideo();
    }

    /**
     * Move o vídeo um determinado número de frames. Só funciona se estiver a
     * reproduzir um ficheiro local. Lança um evento de MediaTimeChanged
     * indicativo da alteração do current time do vídeo.
     * @param nFrames Número de frames que se pretende mover. Pode ser um valor
     * positivo (avançar) ou negativo (recuar).
     */
    public void move(int nFrames) {

        long nextTime = getFrameToMove(nFrames);
        player.setTime(nextTime);

        //TODO retirar daqui
        player.setTime(player.getLength() - 100);

        fireMediaTimeChangedEvent(new MediaTimeChangedEvent(player));
        refreshPausedVideo();
    }

    /**
     * Permite actualizar o ecrã do vídeo com a Frame actual, dada pelo
     * videoplayer, no caso de o player estar em pausa.
     */
    private void refreshPausedVideo() {

        //TODO ver se o VLC nativo tem o mesmo comportamento ou se existe alguma configuração do VLC que actualiza automaticamente o vídeo quando está em pausa.
        if (state == MediaPlayerState.PAUSED) {
            pause();
            //FIXME correcção que não seja um workaround.
            try {
                    Thread.sleep(PAUSE_TIME);
            } catch (InterruptedException e) {}
            pause();
        }
    }

    /**
     * Calcula qual a nova frame para o qual se vai mover, baseado no nº de
     * frames que se pretende mover o vídeo.
     * @param nFrames Número de frames que se pretende mover, face ao tempo
     * actual do player. Pode ser um valor positivo ou negativo.
     * @return Novo tempo calculado que corresponde à frame actual mais o nº
     * de frames que se pretende mover.
     */
    private long getFrameToMove(int nFrames) {
        
        float fps = player.getFPS();
        long currentTime = player.getTime();

        double timeToMove = nFrames * 1000 / fps;

        // Recua N milisegundos para compensar o tempo do sleep em pausa
        //FIXME tem de ser um valor superior ao sleep time....
        if (nFrames < 0)
            currentTime -= PAUSE_TIME;
        
        long nextTime = currentTime + (long)timeToMove;
        return nextTime;
    }

    /**
     * Devolve o tempo que já passou desde que o vídeo começou a reprodução,
     * relativamente ao início. Se o vídeo estiver parado, devolve 0.
     * @return
     */
    public long getCurrentMediaTime() {
        if (state == MediaPlayerState.STOPPED
                || state == MediaPlayerState.EMPTY)
            return 0L;
        return player.getTime();
    }

    /**
     * Devolve o tamanho total do media actualmente em reprodução. Se estiver
     * a reproduzir em streaming, devolve sempre 0.
     * @return
     */
    public long getTotalMediaTime() {

        if (player != null)
            return player.getLength();
        return 0;
    }

    /**
     * Calcula a nova posição do vídeo baseado no valor actual que um dado
     * marcador de tempo tem (por exemplo, um slider), face ao seu valor máximo.
     *  Só deve ser chamado se o media a reproduzir for um ficheiro local.
     * @param pos Valor actual do marcador de tempo.
     * @param max Valor máximo do marcador de tempo.
     */
    public void adjustVideoPosition(int pos, int max) {

        //TODO falta sincronizar correctamente para que o player não sobreponha frames às definidas por acção do utilizador.
        //TODO perceber porque é que ao chegar a uma determinada thread sem estar a reproduzir, faz stop ao vídeo.
        System.out.println("A aceder ao Marker no adjustVideo" + Thread.currentThread());
        long newPosition = getNewVideoPosition(pos, max);
        player.setTime(newPosition);
        refreshPausedVideo();
    }

    /**
     * Calcula o novo tempo do vídeo com base na posição actual e posição máxima
     *  de um dado marcador de tempo.
     * @param pos Posição actual.
     * @param max Tamanho máximo.
     * @return Nova posição relativa do media a ser reproduzido.
     */
    private long getNewVideoPosition(int pos, int max) {

        long movieSize = player.getLength();
        return (long)(movieSize * pos / max);
    }

    /**
     * Devolve o estado em que o player se encontra actualmente.
     * @return
     */
    public MediaPlayerState getCurrentState() {
        return state;
    }

    /**
     * Define qual o tempo relativo ao player que corresponde ao início do
     * sincronismo com a experiência, ou seja, a frame 0.<br>
     * Define também o Timestamp absoluto que lhe corresponde.
     * @param frame0 Tempo absoluto que corresponde ao início da experiência.
     */
    public void setFrame0(long frame0) {

        this.relativeFrame0 = player.getTime();
        this.absoluteFrame0 = frame0;
    }

    /********************************************************************
     *                  Código para event handling                      *
     ********************************************************************/

    public void addMediaTimeChangedEventListener(MediaTimeChangedEventListener listener) {
        eventListeners.add(MediaTimeChangedEventListener.class, listener);
    }

    public void removeMediaTimeChangedEventListener(MediaTimeChangedEventListener listener) {
        eventListeners.remove(MediaTimeChangedEventListener.class, listener);
    }

    private void fireMediaTimeChangedEvent(MediaTimeChangedEvent evt) {
        Object[] listeners = eventListeners.getListenerList();
        for (int i=0; i<listeners.length; i+=2) {
            if (listeners[i]==MediaTimeChangedEventListener.class) {
                ((MediaTimeChangedEventListener)listeners[i+1]).timeChanged(evt);
            }
        }
    }
}
