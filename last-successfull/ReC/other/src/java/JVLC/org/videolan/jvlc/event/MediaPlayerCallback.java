/*****************************************************************************
 * MediaInstancePlayCallback.java: VLC Java Bindings
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

package org.videolan.jvlc.event;

import java.util.logging.Logger;

import org.videolan.jvlc.MediaPlayer;
import org.videolan.jvlc.internal.LibVlc;
import org.videolan.jvlc.internal.LibVlc.LibVlcCallback;
import org.videolan.jvlc.internal.LibVlc.libvlc_event_t;
import org.videolan.jvlc.internal.LibVlc.media_player_time_changed;
import org.videolan.jvlc.internal.LibVlcEventType;

import com.sun.jna.Pointer;

public class MediaPlayerCallback implements LibVlcCallback {
	private static final Logger LOGGER = Logger.getLogger(MediaPlayerCallback.class.getName());

	private final MediaPlayerListener listener;
	private final MediaPlayer mediaPlayer;

	public MediaPlayerCallback(final MediaPlayer mediaInstance, final MediaPlayerListener listener) {
		mediaPlayer = mediaInstance;
		this.listener = listener;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void callback(final libvlc_event_t libvlc_event, final Pointer userData) {
		LibVlcEventType eventType = null;
		for (LibVlcEventType eventTypeVar : LibVlcEventType.values()) {
			if (eventTypeVar.ordinal() == libvlc_event.type) {
				eventType = eventTypeVar;
				break;
			}
		}
		
		if (eventType != null) {
			switch (eventType) {

			case libvlc_MediaPlayerOpening:
			case libvlc_MediaPlayerBuffering:
				break;
			case libvlc_MediaPlayerPlaying:
				listener.playing(mediaPlayer);
				break;
			case libvlc_MediaPlayerPaused:
				listener.paused(mediaPlayer);
				break;
			case libvlc_MediaPlayerEndReached:
				listener.endReached(mediaPlayer);
				break;
			case libvlc_MediaPlayerPositionChanged:
				listener.positionChanged(mediaPlayer);
				break;
			case libvlc_MediaPlayerStopped:
				listener.stopped(mediaPlayer);
				break;
			case libvlc_MediaPlayerTimeChanged:
				// deveria suprimir listener.timeChanged quando foi feito um
				// ajuste
				// pelo user mas este ainda não foi feito no vídeo, de facto.
				libvlc_event.event_type_specific.setType(LibVlc.media_player_time_changed.class);
				final LibVlc.media_player_time_changed timeChanged = (media_player_time_changed) libvlc_event.event_type_specific
						.readField("media_player_time_changed");

				// Evita que em streaming sejam lançados e tratados eventos
				// desnecessários. Ver se é necessário
				// if (timeChanged != null && timeChanged.new_time > 0)
				listener.timeChanged(mediaPlayer, timeChanged.new_time);
				break;
			case libvlc_MediaPlayerEncounteredError:
				MediaPlayerCallback.LOGGER.warning("Media player encountered error.");
				listener.errorOccurred(mediaPlayer);
				break;
			default:
				MediaPlayerCallback.LOGGER.fine(String.format("Unsupported event type. Event type: %d - '%s'",
						eventType.ordinal(), eventType.name()));
				break;
			}
		} else {
			MediaPlayerCallback.LOGGER.fine(String.format("Unsupported event type. Event type: %d", libvlc_event.type));
		}

	}
}
