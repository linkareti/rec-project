/*****************************************************************************
 * JVLC.java: Main Java Class, represents a libvlc_instance_t object
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

import java.awt.Canvas;

import org.videolan.jvlc.internal.LibVlc;
import org.videolan.jvlc.internal.LibVlc.LibVlcInstance;
import org.videolan.jvlc.internal.LibVlc.LibVlcMediaInstance;
import org.videolan.jvlc.internal.LibVlc.libvlc_exception_t;

import com.sun.jna.Native;

public class JVLC {

	private final LibVlcInstance instance;

	private final LibVlc libvlc = LibVlc.SYNC_INSTANCE;

	private MediaList mediaList;

	private VLM vlm;

	private volatile boolean released;

	public JVLC() {
		final String[] args = new String[] {};
		instance = createInstance(args);
		mediaList = new MediaList(this);
	}

	public JVLC(final String[] args) {
		instance = createInstance(args);
	}

	public JVLC(final String args) {
		this(args.split(" "));
	}

	/*
	 * Core methods
	 */
	private LibVlcInstance createInstance(final String[] args) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return libvlc.libvlc_new(args.length, args, exception);
	}

	public MediaPlayer play(final String media) {
		final MediaDescriptor mediaDescriptor = new MediaDescriptor(this, media);
		final MediaPlayer mediaInstance = new MediaPlayer(mediaDescriptor);
		mediaInstance.play();
		// Bruno o media descriptor é libertado logo após o play?
		mediaDescriptor.release();
		return mediaInstance;
	}

	public void setVideoOutput(final Canvas canvas) {
		final long drawable = Native.getComponentID(canvas);
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_video_set_parent(instance, drawable, exception);
	}

	public void setVideoOutput(final Canvas canvas, final LibVlcMediaInstance mediaInstance) {
		final long drawable = Native.getComponentID(canvas);
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_media_player_set_drawable(mediaInstance, (int) drawable, exception);
	}

	public Logger getLogger() {
		return new Logger(this);
	}

	public void setMediaList(final MediaList list) {
		mediaList = list;
	}

	/**
	 * Returns the mediaList.
	 * 
	 * @return the mediaList
	 */
	public MediaList getMediaList() {
		return mediaList;
	}

	public VLM getVLM() {
		if (vlm != null) {
			vlm.release();
		}
		vlm = new VLM(this);
		return vlm;
	}

	public LoggerVerbosityLevel getLogVerbosity() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		final int level = libvlc.libvlc_get_log_verbosity(instance, exception);
		return LoggerVerbosityLevel.getSeverity(level);
	}

	public void setLogVerbosity(final LoggerVerbosityLevel level) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		libvlc.libvlc_set_log_verbosity(instance, level.ordinal(), exception);
	}

	/**
	 * Returns the _instance.
	 * 
	 * @return the _instance
	 */
	LibVlcInstance getInstance() {
		return instance;
	}

	/**
	 * Returns the libvlc.
	 * 
	 * @return the libvlc
	 */
	LibVlc getLibvlc() {
		return libvlc;
	}

	/**
	 * Releases this instance and the native resources.
	 */
	public void release() {
		if (released) {
			return;
		}
		released = true;
		if (vlm != null) {
			vlm.release();
			vlm = null;
		}
		libvlc.libvlc_release(instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		release();
		super.finalize();
	}

}
