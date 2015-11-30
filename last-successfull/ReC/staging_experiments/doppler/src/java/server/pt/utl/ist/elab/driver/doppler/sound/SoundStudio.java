/*
 * SoundStudio.java
 *
 * Created on November 3, 2005, 12:29 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package pt.utl.ist.elab.driver.doppler.sound;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 * 
 * @author andre
 */
public class SoundStudio {

	/** Creates a new instance of SoundStudio */
	public SoundStudio() {
	}

	private boolean playing = false;
	private boolean acquiring = false;
	/** Stores the acquired data */
	private ByteArrayOutputStream byteOutputStream = null;

	private Object playLocker = new Object();
	private Object acqLocker = new Object();

	class PlayerThread extends Thread {
		private SourceDataLine source = null;
		private byte[] soundBuffer = null;
		private AudioFormat audioFormat = null;

		public PlayerThread(SourceDataLine source, byte[] soundBuffer, AudioFormat audioFormat) {
			this.source = source;
			this.soundBuffer = soundBuffer;
			this.audioFormat = audioFormat;
		}

		public void run() {
			try {
				source.open(audioFormat);
				source.start();
				while (playing) {
					source.write(soundBuffer, 0, soundBuffer.length);
				}

				source.drain();
				source.stop();
				source.close();

				synchronized (playLocker) {
					playLocker.notifyAll();
				}
			} catch (Exception e) {
				e.printStackTrace();
				playing = false;
			}
		}
	}

	class AcquireThread extends Thread {
		private TargetDataLine line = null;
		private AudioFormat captureFormat = null;

		public AcquireThread(TargetDataLine line, AudioFormat captureFormat) {
			this.line = line;
			this.captureFormat = captureFormat;
		}

		public void run() {
			try {
				line.open(captureFormat);
				line.start();

				int bufferSize = (int) captureFormat.getSampleRate() * captureFormat.getFrameSize();
				byte buffer[] = new byte[bufferSize];

				int count = 0;
				byteOutputStream = new ByteArrayOutputStream();

				while (acquiring) {
					count = line.read(buffer, 0, buffer.length);
					if (count > 0) {
						byteOutputStream.write(buffer, 0, count);
					}
				}

				line.drain();
				line.stop();
				line.close();
				byteOutputStream.close();

				synchronized (acqLocker) {
					acqLocker.notifyAll();
				}
			} catch (Exception e) {
				e.printStackTrace();
				acquiring = false;
			}

		}
	}

	/**
	 * Starts the sound playing 8bits stereo...it will loop until the
	 * stopPlaying method is called
	 */
	public void playSound(byte[] soundBuffer, int sampleRate) {
		if (soundBuffer == null)
			return;

		try {
			stopPlaying();
			AudioFormat af = new AudioFormat((float) sampleRate, 8, 2, true, true);
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
			playing = true;
			new PlayerThread(source, soundBuffer, af).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Not implemented yet... */
	public void playSound(File f) {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Stops the sound playing... */
	public void stopPlaying() {
		if (playing) {
			synchronized (playLocker) {
				try {
					playing = false;
					playLocker.wait();
				} catch (Exception e) {
				}
			}
		}
	}

	/** Acquire audio...8bits mono */
	private void captureAudio(int sampleRate) {
		stopAcquiring();
		AudioFormat captureFormat = new AudioFormat(sampleRate, 8, 1, true, true);
		try {
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, captureFormat);
			TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);

			acquiring = true;
			new AcquireThread(line, captureFormat).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Stops the sound playing... */
	public void stopAcquiring() {
		if (acquiring) {
			synchronized (acqLocker) {
				try {
					acquiring = false;
					acqLocker.wait();
				} catch (Exception e) {
				}
			}
		}
	}

	public byte[] getAcquiredBytes() {
		if (byteOutputStream == null)
			return null;

		return byteOutputStream.toByteArray();
	}

	public short[] getAcquiredBytesAsShort() {
		byte[] acq = getAcquiredBytes();
		if (acq == null)
			return null;

		ByteBuffer bb = ByteBuffer.wrap(acq);

		short[] toRet = new short[acq.length / 2];
		for (int i = 0; i < toRet.length; i++) {
			toRet[i] = bb.getShort(i);
		}

		return toRet;
	}

	public void playAndAcquire(byte[] soundBuffer, int playSampleRate, int acqSampleRate) {
		playSound(soundBuffer, playSampleRate);
		captureAudio(acqSampleRate);
	}

	public static void main(String args[]) {
		SoundStudio ss = new SoundStudio();
		for (int i = 0; i < 1; i++) {
			ss.playSound(SoundWaves.getSineWave((byte) 127, 8000, 440f), 8000);
			ss.captureAudio(8000);
			try {
				Thread.currentThread().sleep(1500);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}

			ss.stopPlaying();
			ss.stopAcquiring();

			try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			}
		}
	}
}
