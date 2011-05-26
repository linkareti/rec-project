/*****************************************************************************
 * Video.java: JVLC Video Output
 *****************************************************************************
 *
 * Copyright (C) 1998-2008 the VideoLAN team
 * 
 * Author: Filippo Carone <filippo@carone.org>
 *         Philippe Morin <phmorin@free.fr>
 *
 * Created on 28-feb-2006
 *
 * $Id: JVLC.java 20141 2007-05-16 19:31:35Z littlejohn $
 *
 * This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 * 
 */
package org.videolan.jvlc;

import java.awt.Dimension;

import org.videolan.jvlc.internal.LibVlc;
import org.videolan.jvlc.internal.LibVlc.LibVlcInstance;
import org.videolan.jvlc.internal.LibVlc.libvlc_exception_t;

public class Video {

	private final LibVlcInstance libvlcInstance;

	private final LibVlc libvlc;

	public Video(final JVLC jvlc) {
		libvlcInstance = jvlc.getInstance();
		libvlc = jvlc.getLibvlc();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#destroyVideo()
	 */
	public void destroyVideo(final MediaPlayer media) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_video_destroy(media.getInstance(), exception);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#getFullscreen()
	 */
	public boolean getFullscreen(final MediaPlayer media) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_get_fullscreen(media.getInstance(), exception) == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#getSnapshot(java.lang.String)
	 */
	public void getSnapshot(final MediaPlayer media, final String filepath, final int width, final int height) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_video_take_snapshot(media.getInstance(), filepath, width, height, exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#getVideoHeight()
	 */
	public int getHeight(final MediaPlayer media) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_video_get_height(media.getInstance(), exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#getVideoWidth()
	 */
	public int getWidth(final MediaPlayer media) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		// Bruno mal implementado??????????? n seria getWidth
		return libvlc.libvlc_video_get_height(media.getInstance(), exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#reparentVideo(java.awt.Component)
	 */
	public void reparent(final MediaPlayer media, final java.awt.Canvas canvas) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		final long drawable = com.sun.jna.Native.getComponentID(canvas);
		libvlc.libvlc_video_reparent(media.getInstance(), drawable, exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#resizeVideo(int, int)
	 */
	public void setSize(final int width, final int height) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_video_set_size(libvlcInstance, width, height, exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#setFullscreen(boolean)
	 */
	public void setFullscreen(final MediaPlayer media, final boolean fullscreen) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_set_fullscreen(media.getInstance(), fullscreen ? 1 : 0, exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#toggleFullscreen()
	 */
	public void toggleFullscreen(final MediaPlayer media) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_toggle_fullscreen(media.getInstance(), exception);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#getSize()
	 */
	public Dimension getSize(final MediaPlayer media) {
		return new Dimension(getWidth(media), getHeight(media));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.videolan.jvlc.VideoIntf#setSize(java.awt.Dimension)
	 */
	public void setSize(final Dimension d) {
		setSize(d.width, d.height);
	}
}
