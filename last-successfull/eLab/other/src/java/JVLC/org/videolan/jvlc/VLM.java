/*****************************************************************************
 * VLM.java: VLC Java Bindings
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

import org.videolan.jvlc.internal.LibVlc.libvlc_exception_t;

public class VLM {

	private final JVLC jvlc;

	private volatile boolean released;

	public VLM(final JVLC jvlc) {
		this.jvlc = jvlc;
	}

	public void addBroadcast(final String name, final String input, final String output, final String[] options,
			final boolean enabled, final boolean loop) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_add_broadcast(jvlc.getInstance(), name, input, output,
				options == null ? 0 : options.length, options, enabled ? 1 : 0, loop ? 1 : 0, exception);
	}

	public void addVod(final String name, final String input, final String[] options, final boolean enabled,
			final String muxer) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_add_vod(jvlc.getInstance(), name, input, options == null ? 0 : options.length,
				options, enabled ? 1 : 0, muxer, exception);
	}

	public void deleteMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_del_media(jvlc.getInstance(), name, exception);
	}

	public void enableMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_set_enabled(jvlc.getInstance(), name, 1, exception);
	}

	public void disableMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_set_enabled(jvlc.getInstance(), name, 0, exception);
	}

	public void setMediaOutput(final String name, final String output) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_set_output(jvlc.getInstance(), name, output, exception);
	}

	public void setMediaInput(final String name, final String input) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_set_input(jvlc.getInstance(), name, input, exception);
	}

	public void addMediaInput(final String name, final String input) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_add_input(jvlc.getInstance(), name, input, exception);
	}

	public void setMux(final String name, final String muxer) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_set_mux(jvlc.getInstance(), name, muxer, exception);
	}

	public void setMediaLoop(final String media, final boolean loop) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_set_loop(jvlc.getInstance(), media, loop ? 1 : 0, exception);
	}

	public void changeMedia(final String name, final String input, final String output, final String[] options,
			final boolean enabled, final boolean loop) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_change_media(jvlc.getInstance(), name, input, output,
				options == null ? 0 : options.length, options, enabled ? 1 : 0, loop ? 1 : 0, exception);
	}

	public void playMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_play_media(jvlc.getInstance(), name, exception);
	}

	public void stopMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_stop_media(jvlc.getInstance(), name, exception);
	}

	public void pauseMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_pause_media(jvlc.getInstance(), name, exception);
	}

	public void seekMedia(final String name, final float percentage) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_seek_media(jvlc.getInstance(), name, percentage, exception);
	}

	public void showMedia(final String name) {
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_show_media(jvlc.getInstance(), name, exception);
	}

	/**
	 * Releases native resources related to VLM.
	 */
	public void release() {
		if (released) {
			return;
		}
		released = true;
		final libvlc_exception_t exception = new libvlc_exception_t();
		jvlc.getLibvlc().libvlc_vlm_release(jvlc.getInstance(), exception);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finalize() throws Throwable {
		release();
		super.finalize();
	}

}
