/*
 * SoundRecorder.java
 *
 * Created on November 19, 2003, 11:38 AM
 */

package pt.utl.ist.elab.driver.statsound.audio;

import javax.media.CaptureDeviceInfo;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.control.FormatControl;

/**
 * 
 * @author elab
 */
public class SoundRecorder implements DataSoundListener {
	public static final int LEFT_CHANNEL = 0;
	public static final int RIGHT_CHANNEL = 1;

	public static MediaLocator mediaLocator = null;
	public static javax.media.protocol.DataSource ds = null;
	public static FormatControl[] formatControls;

	private boolean acquiring = true;
	private byte[] acqBytes = new byte[0];

	private DataSourceReader dsr = null;

	/** Creates a new instance of SoundRecorder */
	public SoundRecorder() {
	}

	private boolean fileExp = false;

	public void startAcquiring(final boolean fileExp) {
		this.fileExp = fileExp;
		dsr = null;
		dsr = new DataSourceReader();
		dsr.addDataSoundListener(this);
		acqBytes = null;
		acqBytes = new byte[0];
		acquiring = true;
		totalRMSSquareLeft = 0;
		totalRMSSquareRight = 0;
		nPoints = 0;
		nBuffers = 0;
		final CaptureDeviceInfo di = null;
		final StateHelper sh = null;
		try {
			if (SoundRecorder.mediaLocator == null) {
				// mediaLocator = new MediaLocator("javasound://48000");
				// mediaLocator = new MediaLocator("javasound://44100");
				SoundRecorder.mediaLocator = new MediaLocator("javasound://11025");
				SoundRecorder.ds = Manager.createDataSource(SoundRecorder.mediaLocator);
				SoundRecorder.formatControls = ((javax.media.protocol.CaptureDevice) SoundRecorder.ds)
						.getFormatControls();
				// System.out.println("Format=" +
				// formatControls[0].getFormat());
			}
		} catch (final Exception e) {
			System.out.println("Error creating data source and locator");
			e.printStackTrace();
			return;
		}

		final boolean test = dsr.open(SoundRecorder.ds);
		if (!test) {
			System.out.println("There was a problem with datasourcereader!!");
		} else {
			System.out.println("Acquiring...");
		}
	}

	public void stopAcquiring() {
		if (!acquiring) {
			acquiring = false;
			dsr.stopProcessor();
			System.out.println("Stoping the sound acquisition!");
		}

	}

	public double getRMS(final int channel) {
		synchronized (this) {
			if (nPoints == 0) {
				nPoints = 1;
			}
			dataAddedToRMS = false;
			if (channel == SoundRecorder.LEFT_CHANNEL) {
				final double lReturn = 20 * getLog(10, Math.sqrt(totalRMSSquareLeft / nPoints) / 32768);
				// System.out.println("Return Left=" + lReturn);
				notifyAll();
				return lReturn;
			} else {
				final double rReturn = 20 * getLog(10, Math.sqrt(totalRMSSquareRight / nPoints) / 32768);
				// System.out.println("Return right=" + rReturn);
				notifyAll();
				return rReturn;
			}
		}
	}

	public byte[] getAcqBytes() {
		final byte[] toReturn = acqBytes;
		acqBytes = new byte[0];
		return toReturn;
	}

	public void resetRMS() {
		totalRMSSquareLeft = 0;
		totalRMSSquareRight = 0;
		nPoints = 0;
	}

	public boolean isAcquiring() {
		return acquiring;
	}

	public boolean isDataAddedToRMS() {
		return dataAddedToRMS;
	}

	/*
	 * Finds the log in any base using the formula log a=ln b/ln a b
	 */

	private double getLog(final double base, final double value) {
		if (value == 0) {
			return 0;
		}
		return Math.log(value) / Math.log(base);
	}

	private long totalRMSSquareLeft = 0;
	private long totalRMSSquareRight = 0;
	private long nPoints = 0;
	private boolean dataAddedToRMS = false;

	long maxValueLeft = 0;
	long maxValueRight = 0;

	/** ONLY LITTLE ENDIAN IS ACCEPTED!! */
	public void addDataToRMS(final byte[] audioAcqBytes) {
		synchronized (this) {

			final byte[] audioBytes = audioAcqBytes;
			final int[] audioData = null;
			short[] leftData = null;
			short[] rightData = null;

			// System.out.println("Add data to rms length: " +
			// audioBytes.length);

			leftData = new short[audioBytes.length / 4];
			rightData = new short[audioBytes.length / 4];
			for (int i = 0; i + 4 <= audioBytes.length; i += 4) {
				final short LSBLeft = audioBytes[i];
				final short MSBLeft = audioBytes[i + 1];
				final short LSBRight = audioBytes[i + 2];
				final short MSBRight = audioBytes[i + 3];

				leftData[i / 4] = (short) (MSBLeft << 8 | (255 & LSBLeft));
				rightData[i / 4] = (short) (MSBRight << 8 | (255 & LSBRight));
			}

			long squareLeft = 0;
			long squareRight = 0;
			final int leftMean = getMean(leftData);
			final int rightMean = getMean(rightData);

			for (int i = 0; i < leftData.length; i++) {
				squareLeft += (leftData[i] - leftMean) * (leftData[i] - leftMean);
				squareRight += (rightData[i] - rightMean) * (rightData[i] - rightMean);
			}

			// totalRMSSquareLeft += squareLeft;
			// totalRMSSquareRight += squareRight;
			// nPoints += leftData.length;

			totalRMSSquareLeft = squareLeft;
			totalRMSSquareRight = squareRight;
			nPoints = leftData.length;

			fireRMSAvailabe();
			notifyAll();
		}
	}

	private int nBuffers = 0;
	private javax.media.Buffer buffer;

	@Override
	public void bufferAvailable(final NewDataBufferEvent evt) {
		final javax.media.Buffer buffer = evt.getBuffer();
		this.buffer = buffer;
		final byte[] audioBytes = (byte[]) buffer.getData();

		if (!fileExp) {
			addDataToRMS(audioBytes);
		} else if (nBuffers < 10) {
			final byte[] tempArray = acqBytes;
			acqBytes = new byte[acqBytes.length + audioBytes.length];
			System.arraycopy(tempArray, 0, acqBytes, 0, tempArray.length);
			System.arraycopy(audioBytes, 0, acqBytes, tempArray.length, audioBytes.length);
			nBuffers++;
		} else if (nBuffers == 10) {
			synchronized (this) {
				fireRMSAvailabe();
				notifyAll();
			}
		}
	}

	private int getMean(final short[] array) {
		if (array.length == 0) {
			return 0;
		}

		int sum = 0;
		for (final short element : array) {
			sum += element;
		}
		return sum / array.length;
	}

	@Override
	public void rmsAvailable() {
	}

	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Registers DataSoundListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addDataSoundListener(final DataSoundListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(DataSoundListener.class, listener);
	}

	/**
	 * Removes DataSoundListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */

	public synchronized void removeDataSoundListener(final DataSoundListener listener) {
		listenerList.remove(DataSoundListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */

	private void fireRMSAvailabe() {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == DataSoundListener.class) {
				((DataSoundListener) listeners[i + 1]).rmsAvailable();
			}
		}
	}

	/*
	 * public static void main(String args[]) { new
	 * SoundRecorder().startAcquiring(); }
	 */
}
