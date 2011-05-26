/*****************************************************************************
 * MediaInstance.java: VLC Java Bindings Media Instance
 *****************************************************************************
 * Copyright (C) 1998-2008 the VideoLAN team
 *
 * Authors: Filippo Carone <filippo@carone.org>
 *
 *
 * $Id $
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111, USA.
 *****************************************************************************/

package org.videolan.jvlc;

import java.awt.Canvas;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.videolan.jvlc.event.MediaPlayerCallback;
import org.videolan.jvlc.event.MediaPlayerListener;
import org.videolan.jvlc.internal.LibVlc;
import org.videolan.jvlc.internal.LibVlc.LibVlcEventManager;
import org.videolan.jvlc.internal.LibVlc.LibVlcMediaInstance;
import org.videolan.jvlc.internal.LibVlc.libvlc_exception_t;
import org.videolan.jvlc.internal.LibVlcEventType;

import com.sun.jna.Native;

public class MediaPlayer {

	private final LibVlcMediaInstance instance;

	private final LibVlc libvlc;

	private final LibVlcEventManager eventManager;

	private final List<MediaPlayerCallback> callbacks = new ArrayList<MediaPlayerCallback>();

	private MediaDescriptor mediaDescriptor;

	private boolean released;

	/**
	 * Construtor que permite criar um MediaPlayer sem indicar qual o media a
	 * tocar.
	 * 
	 * @param jvlc
	 */
	// Bruno verificar se permite usar sempre o mesmo player durante toda a
	// execução.
	public MediaPlayer(final JVLC jvlc) {

		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc = jvlc.getLibvlc();
		instance = libvlc.libvlc_media_player_new(jvlc.getInstance(), exception);
		eventManager = libvlc.libvlc_media_player_event_manager(instance, exception);
	}

	MediaPlayer(final JVLC jvlc, final LibVlcMediaInstance instance) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		this.instance = instance;
		libvlc = jvlc.getLibvlc();
		eventManager = libvlc.libvlc_media_player_event_manager(instance, exception);
	}

	public MediaPlayer(final MediaDescriptor mediaDescriptor) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc = mediaDescriptor.getLibvlc();
		instance = libvlc.libvlc_media_player_new_from_media(mediaDescriptor.getInstance(), exception);
		eventManager = libvlc.libvlc_media_player_event_manager(instance, exception);
		this.mediaDescriptor = mediaDescriptor;
	}

	public MediaDescriptor getMediaDescriptor() {
		return mediaDescriptor;
	}

	public void play() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_play(instance, exception);
	}

	public void stop() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_stop(instance, exception);
	}

	public void pause() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_pause(instance, exception);
	}

	public long getLength() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_media_player_get_length(instance, exception);
	}

	public long getTime() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_media_player_get_time(instance, exception);
	}

	public void setTime(final long time) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_set_time(instance, time, exception);
	}

	public float getPosition() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_media_player_get_position(instance, exception);
	}

	public void setPosition(final float position) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_set_position(instance, position, exception);
	}

	public boolean willPlay() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return (libvlc.libvlc_media_player_will_play(instance, exception) == 1);
	}

	/**
	 * Resizes the video canvas currently bundled to the player without needing
	 * to destroy the player and create a new one before the resizing.
	 * 
	 * @author bcatarino
	 * @param width
	 * @param height
	 */
	public void resize(final int width, final int height) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_video_resize(instance, width, height, exception);
	}

	public float getRate() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_media_player_get_rate(instance, exception);
	}

	public void setRate(final float rate) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_set_rate(instance, rate, exception);
	}

	public void setVideoOutput(final Canvas canvas) {
		final int drawable = (int) Native.getComponentID(canvas);
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_set_drawable(instance, drawable, exception);
	}

	public boolean hasVideoOutput() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return (libvlc.libvlc_media_player_has_vout(instance, exception) == 1);
	}

	public float getFPS() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_media_player_get_fps(instance, exception);
	}

	public void addListener(final MediaPlayerListener listener) {
		final MediaPlayerCallback callback = new MediaPlayerCallback(this, listener);
		final libvlc_exception_t exception = new libvlc_exception_t();
		// Bruno adicionar tb o buffering e tv o opening?
		for (final LibVlcEventType event : EnumSet.range(LibVlcEventType.libvlc_MediaPlayerOpening,
		// LibVlcEventType.libvlc_MediaPlayerPlaying,
		// Bruno se em vez de time changed, for a position changed??????
		// altera em pausa???
		// LibVlcEventType.libvlc_MediaPlayerTimeChanged))
				LibVlcEventType.libvlc_MediaPlayerPositionChanged)) {
			// Bruno de alguma forma não fazer event attach para determinado
			// tipo. Adicionar tb o pause ao range??????
			libvlc.libvlc_event_attach(eventManager, event.ordinal(), callback, null, exception);
		}
		callbacks.add(callback);
	}

	/**
	 * Liberta os recursos do MediaPlayer. Remove também os listeners para os
	 * eventos lançados pelo MediaPlayer.
	 */
	public void release() {
		if (released) {
			return;
		}
		try {
			detachCallbacks();
		} catch (final Throwable e) {
			// TODO tratar excepção correctamente
			e.printStackTrace();
		}
		try {
			libvlc.libvlc_media_player_release(instance);
		} catch (final Throwable e) {
			// TODO tratar excepção correctamente
			e.printStackTrace();
		}
		released = true;
	}

	/**
	 * Faz o remove dos listeners para os eventos do MediaPlayer.
	 */
	public void detachCallbacks() {

		final libvlc_exception_t exception = new libvlc_exception_t();
		for (final MediaPlayerCallback callback : callbacks) {
			for (final LibVlcEventType event : EnumSet.range(LibVlcEventType.libvlc_MediaPlayerOpening,
			// LibVlcEventType.libvlc_MediaPlayerPlaying,
					LibVlcEventType.libvlc_MediaPlayerPositionChanged)) {
				libvlc.libvlc_event_detach(eventManager, event.ordinal(), callback, null, exception);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finalize() throws Throwable {

		// Bruno deve continuar a chamar o release no finaliza? release();
		super.finalize();
	}

	/**
	 * Returns the instance.
	 * 
	 * @return the instance
	 */
	public LibVlcMediaInstance getInstance() {
		return instance;
	}
}
