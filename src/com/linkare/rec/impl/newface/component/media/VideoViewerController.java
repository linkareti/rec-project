package com.linkare.rec.impl.newface.component.media;

import java.awt.Canvas;
import java.util.EventListener;
import java.util.EventObject;
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
import org.videolan.jvlc.internal.LibVlc;

/**
 * 
 * @author bcatarino
 */
public class VideoViewerController {

    private static VideoViewerController controller;

    /**
     * Identificador dado à stream que se pretende gravar para ficheiro.
     */
    private static final String BROADCAST_NAME = "MyBroadcast";

    /**
     * Indica se está a gravar também para ficheiro ou não.
     */
    private boolean streamToFile = false;

    /**
     * Time do Player que corresponde ao momento 0 da experiência e que tem um
     * tempo relativo-
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
     * Obtém uma instância para o controlador de vídeo. 
     * @return instância do controlador do JVLC Player.
     */
    public static VideoViewerController getInstance() {

        if (controller == null)
            controller = new VideoViewerController();
        return controller;
    }

    /**
     * Obtém uma instância para o controlador de vídeo.
     * @param Parâmetros adicionais de criação do VLC (como especificado na
     * documentação do VLC).
     * @return instância do controlador do JVLC Player.
     */
    public static VideoViewerController getInstance(String[] params) {

        if (controller == null)
            controller = new VideoViewerController(params);
        return controller;
    }

    /**
     * Inicializa os componentes do VLC.
     * @param urlToMedia
     */
    private void initPlayer() {

        this.jvlc.setLogVerbosity(LoggerVerbosityLevel.WARNING);
        this.video = new Video(jvlc);
        this.audio = new Audio(jvlc);
        this.mediaList = jvlc.getMediaList();
        this.mediaListPlayer = new MediaListPlayer(jvlc);
        this.vlm = jvlc.getVLM();
        this.mediaListPlayer.setMediaList(mediaList);
        this.state = MediaPlayerState.EMPTY;
    }

    
    /**
     * Define o Canvas onde será feito o rendering do vídeo.
     * @param jvlcCanvas
     */
    public void setVideoOutput(final Canvas jvlcCanvas) {
        if (!player.hasVideoOutput())
            jvlc.setVideoOutput(jvlcCanvas);
        else {
            //FIXME reparent após estar a reproduzir n mostra vídeo.
            long currentTime = player.getTime();
            stop();
            video.reparent(player, jvlcCanvas);
            video.setSize(jvlcCanvas.getWidth(), jvlcCanvas.getHeight());
            releaseMedia();
            play();
            player.setTime(currentTime);
        }
    }

    /**
     * Define o url do media a reproduzir.
     * @param mrl URL a reproduzir.
     */
    public void setMediaToPlay(String mrl) {

        mediaListener = getDefaultMediaListener();
        MediaDescriptor mediaDescriptor = new MediaDescriptor(jvlc, mrl);
        this.player = mediaDescriptor.getMediaPlayer();
        this.player.addListener(mediaListener);
        this.mediaListPlayer.setMediaInstance(player);
        this.mediaList.insertMediaDescriptor(mediaDescriptor, 0);
        this.state = MediaPlayerState.STOPPED;
    }

    /**
     * Define o url do media a reproduzir. Define também que a stream será
     * gravada para o ficheiro em paralelo.
     * @param mrl URL a reproduzir.
     * @param config Configurações necessárias ao transcoding do vídeo para
     * ficheiro local.
     * @param destFile Nome do ficheiro no qual se pretende gravar o vídeo.
     */
    public void setMediaToPlay(String mrl, TranscodingConfig config,
            String destFile) {

        setMediaToPlay(mrl);
        streamToFile(config, destFile);
    }
    
    /**
     * Liberta os recursos.
     */
    public void releaseMedia() {
        if (mediaList.size() > 0) {
            MediaDescriptor md = mediaList.getMediaDescriptorAtIndex(0);
            md.release();
        }
        this.state = MediaPlayerState.EMPTY;
    }

    /**
     * Media Listener que define o comportamento do player do VLC quando cada
     * um dos eventos é disparado.
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
                VideoViewerController.this.state = MediaPlayerState.STOPPED;
                fireMediaTimeChangedEvent(new MediaTimeChangedEvent(arg0));
            }

            @Override
            public void endReached(MediaPlayer arg0) {
                VideoViewerController.this.state = MediaPlayerState.STOPPED;
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
     * Liga o som se este estiver desligado. Se o som estiver ligado, então
     * desliga-o.
     */
    public void toggleMute() {
        audio.toggleMute();
    }

    /**
     * Altera o volume do som. O máximo que o VLC permite é 200.
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
        player.setRate(rate);
    }

    /**
     * Se o player estiver parado, mas com um media carregado, reproduz de novo.
     *  Caso contrário, faz togglePause. Se tiver opção de streaming activada,
     * faz streaming também para o destino escolhido.
     */
    public void play() {
        
        setPlayRate(1f);
        if (this.state == MediaPlayerState.STOPPED) {
            mediaListPlayer.playItem(0, true);
            if(streamToFile)
                vlm.playMedia(BROADCAST_NAME);
        } else {
            mediaListPlayer.pause();
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
    }

    /**
     * Coloca o player em pausa.
     */
    public void pause() {
        
        mediaListPlayer.pause();

        if (streamToFile)
            vlm.pauseMedia(BROADCAST_NAME);
    }

    /**
     * Permite capturar uma frame e gravar numa imagem no formato jpeg.
     * @param fileName Nome do ficheiro que se pretende gravar. Pode ser um
     * path absoluto ou relativo.
     * @param width Comprimento da imagem guardada, em pixeis.
     * @param height Altura da imagem guardada, em pixeis.
     */
    public void captureScreen(String fileName, int width, int height) {
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
        jvlc.release();
    }

    /**
     * Grava a stream actual para ficheiro.
     * @param config Propriedades para configuração do módulo de transcode do
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

        vlm.addBroadcast(BROADCAST_NAME, mediaList.getMediaDescriptorAtIndex(0)
                .getMrl(), sout, new String[0], true, false);

        streamToFile = true;
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
     * síncrona com a experiência, com base na frame0 previamente definida.
     * @param time Momento do tempo para o qual se pretende "saltar".
     */
    public void moveTo(long time) {

        long diff = time - absoluteFrame0;
        long nextTime = relativeFrame0 + diff;
        player.setTime(nextTime);

        fireMediaTimeChangedEvent(new MediaTimeChangedEvent(player));
        refreshVideo();
    }

    /**
     * Move o vídeo um determinado número de frames.
     * @param nFrames
     */
    public void move(int nFrames) {

        long nextTime = getFrameToMove(nFrames);
        player.setTime(nextTime);

        fireMediaTimeChangedEvent(new MediaTimeChangedEvent(player));
        refreshVideo();
    }

    /**
     * Permite actualizar o ecrã do vídeo com a Frame actual, dada pelo
     * vídeoplayer, no caso de o player estar em pausa.
     */
    private void refreshVideo() {

        if (state == MediaPlayerState.PAUSED) {
            pause();
            //FIXME correcção que não seja um workaround.
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {}
            pause();
        }
    }

    /**
     * Efectua os cálculos da frame para a qual se vai mover
     * @param nFrames Número de frames que se pretende mover, face ao tempo
     * actual do player.
     * @return Novo tempo calculado que corresponde à frame actual mais o nº
     * de frames que se pretende mover.
     */
    private long getFrameToMove(int nFrames) {
        
        float fps = player.getFPS();
        long currentTime = player.getTime();

        double timeToMove = nFrames * 1000 / fps;
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
     * Devolve o tamanho total do media actualmente em reprodução.
     * @return
     */
    public long getTotalMediaTime() {
        return player.getLength();
    }

    public LibVlc.LibVlcMediaInstance getLibVLCMediaInstance() {

        Class c = player.getClass();
        try {
            java.lang.reflect.Field f = c.getDeclaredField("instance");
            f.setAccessible(true);
            LibVlc.LibVlcMediaInstance o = (LibVlc.LibVlcMediaInstance)f.get(player);
            System.out.println(o);
            return o;
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    public LibVlc getLibVLC() {

        Class c = player.getClass();
        try {
            java.lang.reflect.Field f = c.getDeclaredField("libvlc");
            f.setAccessible(true);
            LibVlc o = (LibVlc)f.get(player);
            System.out.println(o);
            return o;
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    /**
     * Calcula a nova posição do vídeo baseado no valor actual que um dado
     * marcador de tempo tem (por exemplo, um slider), face ao seu valor máximo.
     * @param pos Valor actual do marcador de tempo.
     * @param max Valor máximo do marcador de tempo.
     */
    public void adjustVideoPosition(int pos, int max) {
        //TODO falta sincronizar correctamente para que o player não sobreponha frames às definidas por acção do utilizador.
//        MediaPlayerCallback callback = new MediaPlayerCallback(player, mediaListener);
//        synchronized(video) {
//        synchronized(mediaList.getMediaDescriptorAtIndex(0)) {
//        synchronized(player) {
//            synchronized(mediaListener) {
//                   synchronized(callback) {
//        LibVlc.SYNC_INSTANCE.libvlc_media_player_set_time(arg0, arg1, arg2)
                System.out.println("A aceder ao Marker no adjustVideo" + Thread.currentThread());
                
                long newPosition = getNewVideoPosition(pos, max);

                player.setTime(newPosition);
                System.out.println(player.getTime());

                refreshVideo();
//            }
//        }
//        }
//        }
//        }
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

        this.absoluteFrame0 = frame0;
        this.relativeFrame0 = player.getTime();
    }

    /********************************************************************
     *                  Código para event handling                      *
     ********************************************************************/
    
    /**
     * Classe que representa o evento de timeChanged sempre que o tempo do
     * player muda.
     */
    public class MediaTimeChangedEvent extends EventObject {
        public MediaTimeChangedEvent(Object source) {
            super(source);
        }
    }

    public interface MediaTimeChangedEventListener extends EventListener {
        void timeChanged(MediaTimeChangedEvent evt);
    }

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
