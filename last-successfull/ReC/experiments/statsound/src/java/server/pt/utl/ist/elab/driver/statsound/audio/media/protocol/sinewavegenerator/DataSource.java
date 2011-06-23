package pt.utl.ist.elab.driver.statsound.audio.media.protocol.sinewavegenerator;

import java.io.IOException;

import javax.media.Duration;
import javax.media.Time;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PullBufferDataSource;

public class DataSource extends PullBufferDataSource {

	protected boolean started = false;
	protected String contentType = ContentDescriptor.RAW;
	protected boolean connected = false;
	protected Time duration = Duration.DURATION_UNKNOWN;
	protected InterLacedSineWaveStream[] streams = null;
	protected InterLacedSineWaveStream stream = new InterLacedSineWaveStream();
	// protected Object [] controls = new Object[]{new
	// FrequencyControl(stream)};
	protected Object[] controls = new Object[0];

	public DataSource() {
	}

	@Override
	public String getContentType() {
		if (!connected) {
			System.err.println("Error: DataSource not connected");
			return null;
		}
		return contentType;
	}

	@Override
	public void connect() throws IOException {
		if (connected) {
			return;
		}
		connected = true;
	}

	@Override
	public void disconnect() {
		try {
			if (started) {
				stop();
			}
		} catch (final IOException e) {
		}
		connected = false;
	}

	@Override
	public void start() throws IOException {
		// we need to throw error if connect() has not been called
		if (!connected) {
			throw new java.lang.Error("DataSource must be connected before it can be started");
		}
		if (started) {
			return;
		}
		started = true;
		stream.start(true);
	}

	@Override
	public void stop() throws IOException {
		if ((!connected) || (!started)) {
			return;
		}
		started = false;
		stream.start(false);
	}

	@Override
	public Object[] getControls() {
		return controls;
	}

	@Override
	public Object getControl(final String controlType) {
		try {
			final Class cls = Class.forName(controlType);
			final Object cs[] = getControls();
			for (final Object element : cs) {
				if (cls.isInstance(element)) {
					return element;
				}
			}
			return null;

		} catch (final Exception e) { // no such controlType or such control
			return null;
		}
	}

	@Override
	public Time getDuration() {
		return duration;
	}

	@Override
	public javax.media.protocol.PullBufferStream[] getStreams() {
		if (streams == null) {
			streams = new InterLacedSineWaveStream[1];
			streams[0] = stream;
		}

		return streams;
	}

}
