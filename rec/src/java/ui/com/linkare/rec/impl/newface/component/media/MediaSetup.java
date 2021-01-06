package com.linkare.rec.impl.newface.component.media;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.fullscreen.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.fullscreen.exclusivemode.ExclusiveModeFullScreenStrategy;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

/**
 * Classe que faz todo o setup inicial do módulo de vídeo, extraindo as libs
 * nativas necessárias para o filesystem, de forma independente do Sistema
 * Operativo e carregando-as de acordo com as dependências.
 * 
 * @author bcatarino
 */
public class MediaSetup {

	private static final Logger LOGGER = Logger.getLogger(MediaSetup.class.getName());
	private static MediaPlayerFactory mediaPlayerFactory;
	private static EmbeddedMediaPlayer player;
	private static boolean hasVideoOutput;
    private static ImageView videoImageView;
    private static JPanel mediaPanel;
    

	public static final void initializeVideoSubsystem() {
		
		try {
			// For VLC - Try to avoid linux crashes
			if (RuntimeUtil.isNix()) {
				NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),"/usr/lib/x86_64-linux-gnu");
				LOGGER.log(Level.SEVERE, "I am here");
				Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);
				LOGGER.log(Level.SEVERE, "I was also here");
				//LibXUtil.initialise();
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to initialize LibX", e);
		} catch (UnsatisfiedLinkError ignoreMaybeNotLinuxArghhhh) {
			LOGGER.log(Level.SEVERE, "Unable to initialize LibX", ignoreMaybeNotLinuxArghhhh);
		}
		
		try {
			boolean found = new NativeDiscovery().discover();
	        System.out.println("Yes i found it : " + found);
	        System.out.println("The version is : " + LibVlc.libvlc_get_version());
			// For VLC - Try to discover VLC installations
			new NativeDiscovery().discover();
		} catch (Throwable ignoreDidMyBest) {
			LOGGER.log(Level.SEVERE, "Unable to setup VLC discovery", ignoreDidMyBest);
			LOGGER.log(Level.SEVERE, "Version is : ", LibVlc.libvlc_get_version());
		}
	}

	private static void initFX(JFXPanel fxPanel) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: green;");

        videoImageView.fitWidthProperty().bind(root.widthProperty());
        videoImageView.fitHeightProperty().bind(root.heightProperty());

        root.widthProperty().addListener((observableValue, oldValue, newValue) -> {
            // If you need to know about resizes
        });

        root.heightProperty().addListener((observableValue, oldValue, newValue) -> {
            // If you need to know about resizes
        });

        root.setCenter(videoImageView);
        
        Scene scene = new Scene(root, 180, 110, Color.RED);
        fxPanel.setScene(scene);
        //fxPanel.show();
        fxPanel.setVisible(true);
	}
	
	public static void initializeMediaFactory(JFrame window) {
		LOGGER.finest("Initializing Media Factory!");
		try {
			final JFXPanel fxPanel = new JFXPanel();
			
			// mediaPlayerFactory = new MediaPlayerFactory(getDefaultEmbeddedMediaParameters());
			// FullScreenStrategy fullScreenStrategy = new ExclusiveModeFullScreenStrategy(window);
			// player.fullScreen().strategy(fullScreenStrategy);
			// player.controls().setRate(1.f);

	        mediaPlayerFactory = new MediaPlayerFactory(getDefaultEmbeddedMediaParameters());
	        player = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
	        player.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
	            @Override
	            public void playing(MediaPlayer mediaPlayer) {
	            }

	            @Override
	            public void paused(MediaPlayer mediaPlayer) {
	            }

	            @Override
	            public void stopped(MediaPlayer mediaPlayer) {
	            }

	            @Override
	            public void timeChanged(MediaPlayer mediaPlayer, long newTime) {
	            }
	        });
	        
	        videoImageView = new ImageView();
	        videoImageView.setPreserveRatio(true);

	        player.videoSurface().set(videoSurfaceForImageView(videoImageView));
			//window.getContentPane().add(fxPanel);
	        //window.setSize(100,100);
			//window.setVisible(true);
			
			mediaPanel.add(fxPanel);
			mediaPanel.setVisible(true);
			
		    Platform.runLater(new Runnable() {
		        @Override public void run() {
		          initFX(fxPanel);        
		        }
		      });
		    

			
			//JPanel contentPane = new JPanel();
			//contentPane.setLayout(new BorderLayout());

			//EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
			//contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

			//JPanel controlsPane = new JPanel();
			//contentPane.add(controlsPane, BorderLayout.SOUTH);

			//window.setContentPane(contentPane);
			//window.setVisible(true);
			//player = mediaPlayerComponent.mediaPlayer();
			//mediaPlayerComponent.mediaPlayer().media().play("rtsp://elab-streamer-server:8554/vtiro");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Could not initialize Video SubSystem", e);
			return;
		} catch (UnsatisfiedLinkError e) {
			LOGGER.log(Level.SEVERE, "Returning UnsatisfiedLinkError", e);
			return;
		}
	}
	


	/**
	 * @return
	 */
	private static List<String> getSharedArgs() {
		// See vlc -H
		final List<String> args = new ArrayList<String>();
		args.add("--rtsp-tcp");
		// args.add("--no-plugins-cache");
		args.add("--no-video-title-show");
		args.add("--no-snapshot-preview");
//		args.add("--quiet");
//		args.add("--quiet-synchro");
//		args.add("--no-xlib");
		args.add("--aout=none");
		args.add("--no-audio");
		args.add("-vvv");
		return args;
	}

	/**
	 * Devolve um conjunto de parâmetros de configuração do VLC que permite
	 * fazer uma inicialização por default.
	 * 
	 * @return A lista de argumentos para o VLC em modo "embbeded"
	 */
	public static String[] getDefaultEmbeddedMediaParameters() {

		final List<String> args = getSharedArgs();
		args.add("--intf");
		args.add("dummy");
		return args.toArray(new String[0]);
	}

	/**
	 * Devolve um conjunto de parâmetros de configuração do VLC que permite
	 * fazer uma inicialização por default.
	 * 
	 * @return A lista de argumentos para o VLC
	 */
	public static String[] getDefaultExternalMediaParameters() {

		final List<String> args = getSharedArgs();
		return args.toArray(new String[0]);
	}

	public static boolean hasVideoOutput() {
		return hasVideoOutput;
	}
	
	
	public static boolean isVideoSubSystemAvailable() {
		return player != null;
	}

	/**
	 * @return the mediaPlayerFactory
	 */
	public static MediaPlayerFactory getMediaPlayerFactory() {
		return mediaPlayerFactory;
	}

	/**
	 * @return the player
	 */
	public static EmbeddedMediaPlayer getPlayer() {
		return player;
	}

	public static void setMediaPanel(JPanel mediaPanelInput) {
		LOGGER.finest("Setting video output canvas!");
		mediaPanel = mediaPanelInput;
		//ComponentVideoSurface canvasVideoSurface = mediaPlayerFactory.videoSurfaces().newVideoSurface(videoCanvas);
		//player.videoSurface().set(canvasVideoSurface);
		hasVideoOutput = true;
	}
	
	
	/**
	 * Liberta os recursos.
	 */
	public static void release() {

		/*
		if (player != null) {
			if(player.isPlaying()) {
				player.stop();
			}
		*/
		if (player != null) {
			if(player.status().isPlaying()) {
				player.controls().stop();
			}
			player.release();
		}
		
		if(mediaPlayerFactory!=null) {
			mediaPlayerFactory.release();
		}
	}

}