package com.linkare.rec.impl.newface.component.media;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.binding.RuntimeUtil;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.fullscreen.FullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.fullscreen.exclusivemode.ExclusiveModeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.videosurface.ComponentVideoSurface;

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

	public static void initializeMediaFactory(JFrame window) {
		LOGGER.finest("Initializing Media Factory!");
		try {
			mediaPlayerFactory = new MediaPlayerFactory(getDefaultEmbeddedMediaParameters());
			FullScreenStrategy fullScreenStrategy = new ExclusiveModeFullScreenStrategy(window);
			player = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
			player.fullScreen().strategy(fullScreenStrategy);
			player.controls().setRate(1.f);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Could not initialize Video SubSystem", e);
			return;
		} catch (UnsatisfiedLinkError e) {
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

	public static void setVideoOutput(Canvas videoCanvas) {
		if (hasVideoOutput) {
			return;
		}
		LOGGER.finest("Setting video output canvas!");
		ComponentVideoSurface canvasVideoSurface = mediaPlayerFactory.videoSurfaces().newVideoSurface(videoCanvas);
		player.videoSurface().set(canvasVideoSurface);
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