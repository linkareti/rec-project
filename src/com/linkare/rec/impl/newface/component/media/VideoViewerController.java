package com.linkare.rec.impl.newface.component.media;

import java.awt.Canvas;
import org.videolan.jvlc.Audio;
import org.videolan.jvlc.JVLC;
import org.videolan.jvlc.MediaDescriptor;
import org.videolan.jvlc.MediaList;
import org.videolan.jvlc.MediaListPlayer;
import org.videolan.jvlc.MediaPlayer;
import org.videolan.jvlc.VLM;
import org.videolan.jvlc.Video;
import org.videolan.jvlc.event.MediaPlayerListener;

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
    private boolean streamToFile = false;

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
     * Componente que define a passagem do tempo definido pelo media.
     */
    private TimeMarker marker;

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

        this.video = new Video(jvlc);
        this.audio = new Audio(jvlc);
        this.mediaList = new MediaList(jvlc);
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
        jvlc.setVideoOutput(jvlcCanvas);
    }

    /**
     * Define o url do media a reproduzir.
     * @param mrl URL a reproduzir.
     */
    public void setMediaToPlay(String mrl) {

        MediaPlayerListener mediaListener = getDefaultMediaListener();
        MediaDescriptor mediaDescriptor = new MediaDescriptor(jvlc, mrl);
        this.player = mediaDescriptor.getMediaPlayer();
        this.player.addListener(mediaListener);
        this.mediaListPlayer.setMediaInstance(player);
        this.mediaList.addMedia(mediaDescriptor);
        this.state = MediaPlayerState.STOPPED;
    }
    
    /**
     * Liberta os recursos.
     */
    public void releaseMedia() {
        if (mediaList.size() > 0)
            mediaList.getMediaDescriptorAtIndex(0).release();
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
                System.out.println("I'm playing");
            }

            @Override
            public void paused(MediaPlayer arg0) {
                VideoViewerController.this.state = MediaPlayerState.PAUSED;
                System.out.println("I'm paused");
            }

            @Override
            public void stopped(MediaPlayer arg0) {
                marker.setMarkerPosition(0);
                VideoViewerController.this.state = MediaPlayerState.STOPPED;
                System.out.println("I'm stopped");
            }

            @Override
            public void endReached(MediaPlayer arg0) {
                marker.setMarkerPosition(0);
                VideoViewerController.this.state = MediaPlayerState.STOPPED;
                System.out.println("I reached the end");
            }

            @Override
            public void timeChanged(MediaPlayer arg0, long arg1) {

                
                System.out.println("TIME CHANGED: "
                        + Thread.currentThread().getName()
                        + ", "
                        + Thread.currentThread().getId());

                adjustSlider(arg1);
            }

            @Override
            public void positionChanged(MediaPlayer arg0) {
                System.out.println("My position changed");
                
            }

            @Override
            public void errorOccurred(MediaPlayer arg0) {
                System.err.println("Fix me");
            }
        };
    }

    /**
     * Associa um TimeMarker ao controller do vídeo, para que o tempo seja
     * alterado neste componente à medida que o vídeo é reproduzido.
     * @param marker Componente do tipo TimeMarker que irá registar o tempo
     * de reprodução do vídeo.
     */
    public void registerMarker(final TimeMarker marker) {
        this.marker = marker;
    }

    /**
     * Liga o som se este estiver desligado. Se o som estiver ligado, entÃ£o
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
     * Devolve o valor actual do som.
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
     *  Caso contrário, faz togglePause.
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
     * LIberta os recursos do VLC. Deve ser chamado sempre no final de uma
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

    public void move(int nFrames) {

//        synchronized(marker) {
        float fps = player.getFPS();
        long currentTime = player.getTime();

        double timeToMove = nFrames * 1000000 / fps;
        long nextTime = currentTime + (long)timeToMove;

        player.setTime(nextTime);
//            getPlayer().setPosition(0.5f);
//            BigDecimal bd = new BigDecimal(timeToMove);
//            bd.doubleValue();
        adjustSlider(nextTime);
    }

    private void adjustSlider(long arg1) {

        System.out.println("A aceder ao Marker " + Thread.currentThread());
        int max = marker.getMaximumPosition();
        long length = player.getLength();
        if (length == 0)
            length = 1;

        int val = (int)(max * (arg1/1000) / length);

        marker.setMarkerPosition(val);
    }

    public /*synchronized */void adjustVideoPosition() {

//        synchronized(marker) {
            System.out.println("A aceder ao Marker no adjustVideo" + Thread.currentThread());
            int position = marker.getCurrentPosition();
            int slideSize = marker.getMaximumPosition();

            long movieSize = player.getLength();
            long pos = (long)(movieSize * position / slideSize);

            player.setTime(pos);
            System.out.println(player.getTime());
        }
//    }

    /**
     * Devolve o estado em que o player se encontra actualmente.
     * @return
     */
    public MediaPlayerState getCurrentState() {
        return state;
    }
}
