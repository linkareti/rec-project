package pt.utl.ist.elab.driver.statsound.audio.media.protocol.sinewavegenerator;

import javax.media.Control;
import javax.media.Format;
import javax.media.format.AudioFormat;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PullBufferStream;

public class InterLacedSineWaveStream implements PullBufferStream {

	protected ContentDescriptor cd = new ContentDescriptor(ContentDescriptor.RAW);
	protected int maxDataLength;
	private double freq_out = 44100.0;
	private int bits_per_channel = 16;
	private int num_channels = 2;
	protected AudioFormat audioFormat;
	protected boolean started;
	// protected Thread thread;
	protected BufferTransferHandler transferHandler;
	protected Control[] controls = new Control[0];

	public InterLacedSineWaveStream() {
		// audio data
		audioFormat = new AudioFormat(AudioFormat.LINEAR, freq_out, bits_per_channel, num_channels,
				AudioFormat.LITTLE_ENDIAN, AudioFormat.SIGNED, bits_per_channel * num_channels, freq_out,
				Format.byteArray);

		// audioFormat=new AudioFormat( AudioFormat.LINEAR, freq_out,
		// bits_per_channel, num_channels);/*, SIGNED, LITTLE_ENDIAN);*/

		maxDataLength = (int) freq_out * num_channels * bits_per_channel / 8 / 4;// basicamente
		// estou
		// a
		// escolher
		// um
		// dataLength
		// de
		// 250ms
		// maxDataLength=256;

		/** CHANGED dividing by 4 */
		max_amplitude = (double) (Math.pow(2., (double) (bits_per_channel - 1)) - 1) / 4;

		// thread = new Thread(this);
		// thread.setPriority(Thread.MAX_PRIORITY);
	}

	/***************************************************************************
	 * SourceStream
	 ***************************************************************************/

	public ContentDescriptor getContentDescriptor() {
		System.out.println("Getting cd");
		return cd;
	}

	public long getContentLength() {
		System.out.println("Getting content length");
		return LENGTH_UNKNOWN;
	}

	public boolean endOfStream() {
		System.out.println("Getting EOS");
		return false;
	}

	/***************************************************************************
	 * PushBufferStream
	 ***************************************************************************/

	private double wave_left_freq = 220.0f;
	private double wave_right_freq = 440.0f;

	private double wave_left_shift = 0;
	private double wave_right_shift = 0;

	private double max_amplitude = 0;

	public void setWaveLeftFreq(double wave_left_freq) {
		synchronized (Synch) {
			this.wave_left_freq = wave_left_freq;
			// this.wave_left_shift=0;
			freqPlayed = false;
		}
	}

	public double getWaveLeftFreq() {
		return this.wave_left_freq;
	}

	public void setWaveRightFreq(double wave_right_freq) {
		synchronized (Synch) {
			this.wave_right_freq = wave_right_freq;
			// this.wave_right_shift=0;
			freqPlayed = false;
		}
	}

	public double getWaveRightFreq() {
		return this.wave_right_freq;
	}

	public Format getFormat() {
		return audioFormat;
	}

	public void setTransferHandler(BufferTransferHandler transferHandler) {
		System.out.println("Setting transfer handler");
		synchronized (this) {
			this.transferHandler = transferHandler;
			notifyAll();
		}
	}

	void start(boolean started) {

		synchronized (this) {
			this.started = started;
			/*
			 * if (started && !thread.isAlive()) { thread = new Thread(this);
			 * thread.setPriority(Thread.MAX_PRIORITY); thread.start(); }
			 */
			notifyAll();
		}
	}

	/***************************************************************************
	 * Runnable
	 ***************************************************************************/

	/*
	 * public void run() { while (started) {
	 * 
	 * while (transferHandler == null && started) { synchronized (this) { try {
	 * wait(1000); } catch (InterruptedException ie) { } } }// while
	 * 
	 * if (started && transferHandler != null) {
	 * transferHandler.transferData(this); try { double
	 * waitSecsForNewTransfer=(double
	 * )maxDataLength/(double)(num_channels*(bits_per_channel/8));
	 * waitSecsForNewTransfer=waitSecsForNewTransfer/((double)freq_out*0.5);
	 * Thread.currentThread().sleep((long)(waitSecsForNewTransfer*1000.));//J�
	 * alterei } catch (InterruptedException ise) { } } } // while (started) }
	 * // run
	 */
	// Controls

	public Object[] getControls() {
		System.out.println("Getting controls");
		return controls;
	}

	public Object getControl(String controlType) {
		System.out.println("Getting controls");
		try {
			Class cls = Class.forName(controlType);
			Object cs[] = getControls();
			for (int i = 0; i < cs.length; i++) {
				if (cls.isInstance(cs[i]))
					return cs[i];
			}
			return null;

		} catch (Exception e) { // no such controlType or such control
			return null;
		}
	}

	int seqNo = 0;
	double d_left_value = 0;
	double d_right_value = 0;

	Object Synch = new Object();

	public void read(javax.media.Buffer buffer) throws java.io.IOException {
		freqPlayed = false;
		Object outdata = buffer.getData();
		if (outdata == null || !(outdata.getClass() == Format.byteArray) || ((byte[]) outdata).length != maxDataLength) {// realoca
			// o
			// array
			// se
			// o
			// length
			// for
			// diferente
			// do
			// maxDataLength
			// o problema no meu PC mostrou-se porque o length era pequeno...
			outdata = new byte[maxDataLength];
			buffer.setData(outdata);
		}

		buffer.setFormat(audioFormat);

		byte[] data_out = (byte[]) outdata;

		double delta_t = 1. / (double) freq_out;

		int buffer_len = data_out.length / (num_channels * (bits_per_channel / 8));
		double wave_left_freq_temp = 0.;
		double wave_right_freq_temp = 0.;
		double time = 0.;
		for (int i = 0; i < buffer_len; i++) {
			time = (double) i * delta_t;
			synchronized (Synch) {
				wave_left_freq_temp = wave_left_freq;
				wave_right_freq_temp = wave_left_freq;
			}
			d_left_value = Math.sin(2 * Math.PI * wave_left_freq_temp * delta_t + wave_left_shift);
			d_right_value = Math.sin(2 * Math.PI * wave_right_freq_temp * delta_t + wave_right_shift);

			d_left_value *= max_amplitude;
			d_right_value *= max_amplitude;

			wave_left_shift += 2 * Math.PI * wave_left_freq_temp * delta_t;
			wave_right_shift += 2 * Math.PI * wave_right_freq_temp * delta_t;

			if (num_channels == 1) {
				if (bits_per_channel == 16) {
					data_out[(num_channels * bits_per_channel / 8) * i] = (byte) ((short) d_left_value & 0xFF);
					data_out[(num_channels * bits_per_channel / 8) * i + 1] = (byte) ((short) d_left_value >> 8);
				} else if (bits_per_channel == 8) {
					data_out[(num_channels * bits_per_channel / 8) * i] = (byte) d_left_value;
				}
			} else if (num_channels == 2) {
				if (bits_per_channel == 16) {
					data_out[(num_channels * bits_per_channel / 8) * i] = (byte) ((short) d_left_value & 0xFF);
					data_out[(num_channels * bits_per_channel / 8) * i + 1] = (byte) ((short) d_left_value >> 8);
					data_out[(num_channels * bits_per_channel / 8) * i + 2] = (byte) ((short) d_right_value & 0xFF);
					data_out[(num_channels * bits_per_channel / 8) * i + 3] = (byte) ((short) d_right_value >> 8);
				} else if (bits_per_channel == 8) {
					data_out[(num_channels * bits_per_channel / 8) * i] = (byte) d_left_value;
					data_out[(num_channels * bits_per_channel / 8) * i + 1] = (byte) d_right_value;
				}
			}
		}

		wave_left_shift = Math.IEEEremainder(wave_left_shift, 2 * Math.PI);
		wave_right_shift = Math.IEEEremainder(wave_right_shift, 2 * Math.PI);

		buffer.setSequenceNumber(seqNo);
		buffer.setLength(data_out.length);
		buffer.setOffset(0);
		buffer.setFlags(buffer.FLAG_RELATIVE_TIME);
		buffer.setTimeStamp(0);
		buffer.setHeader(null);
		seqNo++;
		freqPlayed = true;
	}

	boolean freqPlayed = false;

	public boolean isFreqPlayed() {
		return freqPlayed;
	}

	public boolean willReadBlock() {
		System.out.println("Will read block?");
		return false;
	}

}
