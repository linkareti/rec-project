package pt.utl.ist.elab.driver.statsound.audio;

/*
 * SoundThread.java
 *
 * Created on 5 de Junho de 2003, 17:26
 */

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.SourceDataLine;

import pt.utl.ist.elab.driver.statsound.SoundWaveType;

/**
 * 
 * @author PC
 */
public class SoundThread implements Runnable {

	public static final int SIN_WAVE = 0;

	public static final int PINK_NOISE = 1;

	public static final int PULSE = 2;

	private static final int EXTERNAL_BUFFER_SIZE = 44100;

	private final float waveSampleRate = 11025.0F; // 44100.0F; // sampleRate do
													// audio

	private final float waveUltraSampleRate = 44100.0F; // sampleRate do audio
	// determina o formato do audio
	private final AudioFormat waveFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, waveSampleRate, 16, 1, 2,
			waveSampleRate, false);
	private final AudioFormat waveFormatUltra = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, waveUltraSampleRate,
			16, 1, 2, waveUltraSampleRate, false);

	private float waveFrequency1; // frequency of the audio wave in Hz
	private float waveFrequency2; // frequency of the audio wave in Hz
	private float waveAmplitude = 1;
	private float waveDuration; // duration of the audio wave in seconds
	private long length;

	private AudioInputStream audiostream = null;
	private SourceDataLine linha = null;

	private int soundType = -1;
	private int wait;

	// new stuff!
	private SoundWaveType soundWaveType;

	/** Creates a new instance of SoundThread */
	public SoundThread() {
	}

	public SoundThread(final int type) {
		soundType = type;
	}

	public SoundThread(final SoundWaveType soundWaveType) {
		this.soundWaveType = soundWaveType;
	}

	/**
	 * Runs the thread. This is where the actual playing is done
	 */
	@Override
	public void run() {
		System.out.println(">>>Starting the sound line!");
		// TODO: I'm here, at the moment. The generator code must be executed
		// here, I guess...
		int nBytesRead = 0;
		final byte[] abData = new byte[SoundThread.EXTERNAL_BUFFER_SIZE];
		System.out.println(">>>Reading the sound data!");
		while (nBytesRead != -1) {
			try {
				nBytesRead = audiostream.read(abData, 0, abData.length);
			} catch (final IOException e) {
				System.out.println("excep");
				e.printStackTrace();
			}
			if (nBytesRead >= 0) {
				final int nBytesWritten = linha.write(abData, 0, nBytesRead);
				// System.out.println(nBytesRead);
			}
		}
		System.out.println(">>>Draining the sound data!");
		linha.drain();
	}

	public void configure(final float waveFrequency1, final float waveFrequency2, final float waveDuration,
			final int wait) {
		this.waveFrequency1 = waveFrequency1;
		this.waveFrequency2 = waveFrequency2;
		this.waveDuration = waveDuration;
		this.wait = wait;

		if (waveFrequency2 == 0.0f) {
			length = Math.round(waveSampleRate * this.waveDuration); // length
		} else {
			length = Math.round(waveSampleRate * this.waveDuration);
		}
	}

	/**
	 * Emergency stop playing (don't know if it works)
	 */
	public synchronized void stopWave() {
		linha.drain();
		linha.close();
	}

	/**
	 * Setters and getters
	 */
	public void setFrequency1(final float waveFrequency1) {
		this.waveFrequency1 = waveFrequency1;
	}

	public float getFrequency1() {
		return waveFrequency1;
	}

	public void setFrequency2(final float waveFrequency2) {
		this.waveFrequency2 = waveFrequency2;
	}

	public float getFrequency2() {
		return waveFrequency2;
	}

	public void setAmplitude(final float waveAmplitude) {
		this.waveAmplitude = waveAmplitude;
	}

	public float getAmplitude() {
		return waveAmplitude;
	}

	public void setDuration(final float waveDuration) {
		this.waveDuration = waveDuration;
	}

	public float getDuration() {
		return waveDuration;
	}

	@Override
	public void finalize() throws Throwable {
		try {
			super.finalize();
		} catch (final Exception e) {
			throw e.fillInStackTrace();
		}
	}// finalize
}