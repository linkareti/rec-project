package pt.utl.ist.elab.driver.aleatorio.Utils;

/******************************************************
 * File: DeviceInfo.java.java
 * created 24.07.2001 21:44:12 by David Fischer, fischer@d-fischer.com
 */

import java.awt.Dimension;

import javax.media.Format;
import javax.media.control.FormatControl;
import javax.media.format.AudioFormat;
import javax.media.format.VideoFormat;
import javax.media.protocol.CaptureDevice;
import javax.media.protocol.DataSource;

public class DeviceInfo {

	public static Format formatMatches(final Format format, final Format supported[]) {
		if (supported == null) {
			return null;
		}
		for (final Format element : supported) {
			if (element.matches(format)) {
				return element;
			}
		}
		return null;
	}

	public static boolean setFormat(final DataSource dataSource, final Format format) {
		boolean formatApplied = false;

		FormatControl formatControls[] = null;
		formatControls = ((CaptureDevice) dataSource).getFormatControls();
		for (final FormatControl formatControl : formatControls) {
			if (formatControl == null) {
				continue;
			}

			final Format supportedFormats[] = formatControl.getSupportedFormats();
			if (supportedFormats == null) {
				continue;
			}

			if (DeviceInfo.formatMatches(format, supportedFormats) != null) {
				formatControl.setFormat(format);
				formatApplied = true;
			}
		}

		return formatApplied;
	}

	public static boolean isVideo(final Format format) {
		return (format instanceof VideoFormat);
	}

	public static boolean isAudio(final Format format) {
		return (format instanceof AudioFormat);
	}

	public static String formatToString(final Format format) {
		if (DeviceInfo.isVideo(format)) {
			return DeviceInfo.videoFormatToString((VideoFormat) format);
		}

		if (DeviceInfo.isAudio(format)) {
			return DeviceInfo.audioFormatToString((AudioFormat) format);
		}

		return ("--- unknown media device format ---");
	}

	public static String videoFormatToString(final VideoFormat videoFormat) {
		final StringBuffer result = new StringBuffer();

		// add width x height (size)
		final Dimension d = videoFormat.getSize();
		result.append("size=" + (int) d.getWidth() + "x" + (int) d.getHeight() + ", ");

		/*
		 * // try to add color depth if (videoFormat instanceof
		 * IndexedColorFormat) { IndexedColorFormat f = (IndexedColorFormat)
		 * videoFormat; result.append("color depth=" + f.getMapSize() + ", "); }
		 */

		// add encoding
		result.append("encoding=" + videoFormat.getEncoding() + ", ");

		// add max data length
		result.append("maxdatalength=" + videoFormat.getMaxDataLength() + "");

		return result.toString();
	}

	public static String audioFormatToString(final AudioFormat audioFormat) {
		final StringBuffer result = new StringBuffer();

		// short workaround
		result.append(audioFormat.toString().toLowerCase());

		return result.toString();
	}
}
