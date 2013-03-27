/*
 * SoundThread.java
 *
 * Created on 5 de Junho de 2003, 17:26
 */

package pt.utl.ist.elab.driver.aleatorio.Hardware;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import pt.utl.ist.elab.driver.aleatorio.Utils.Oscilador;

//import java.util.Hashtable;
/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class SoundThread implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(SoundThread.class.getName());
	
	private static final int EXTERNAL_BUFFER_SIZE = 44100;
	private final float waveSampleRate = 44100.0F; // sampleRate do audio
	// determina o formato do audio
	private final AudioFormat waveFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, waveSampleRate, 16, 2, 4,
			waveSampleRate, false);

	private float waveFrequency1; // frequency of the audio wave in Hz
	private float waveFrequency2; // frequency of the audio wave in Hz
	private float waveAmplitude = 1;
	private float waveDuration; // duration of the audio wave in seconds
	private long length;

	private AudioInputStream audiostream = null;
	private SourceDataLine linha = null;

	/** Creates a new instance of SoundThread */
	public SoundThread() {
	}

	/**
	 * Runs the thread. This is where the actual playing is done
	 */
	@Override
	public void run() {
		System.out.println(">>>Starting the sound line!");
		// starts the linha
		linha.start();

		int nBytesRead = 0;
		final byte[] abData = new byte[SoundThread.EXTERNAL_BUFFER_SIZE];
		System.out.println(">>>Reading the sound data!");
		while (nBytesRead != -1) {
			try {
				nBytesRead = audiostream.read(abData, 0, abData.length);
			} catch (final IOException e) {
				LOGGER.log(Level.SEVERE, "Exception: " + e.getMessage(), e);
				throw new RuntimeException(e);
			}
			if (nBytesRead >= 0) {
				linha.write(abData, 0, nBytesRead);
			}
		}
		System.out.println(">>>Draining the sound data!");
		linha.drain();
		// System.out.println(">>>Closing the sound line");
		// linha.close();

		// Thread.currentThread().interrupt();
	}

	public void newLine() {
		System.out.println(">>> Creating new oscilator!");
		try {
			audiostream = new Oscilador(waveFrequency1, waveFrequency2, waveAmplitude, length, waveFormat);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Exception: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}

		final DataLine.Info informacao = new DataLine.Info(SourceDataLine.class, waveFormat);

		try {
			linha = (SourceDataLine) AudioSystem.getLine(informacao);
			linha.open(waveFormat);
		} catch (final LineUnavailableException e) {
			LOGGER.log(Level.SEVERE, "Exception: " + e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Exception: " + e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}// newLine

	public void configure(final float waveFrequency1, final float waveFrequency2, final float waveDuration) {
		this.waveFrequency1 = waveFrequency1;
		this.waveFrequency2 = waveFrequency2;
		this.waveDuration = waveDuration;

		length = Math.round(waveSampleRate * this.waveDuration); // length
		// of
		// stream
		// in
		// frames

		/*
		 * try {audiostream = new
		 * Oscilador(this.waveFrequency1,this.waveFrequency2
		 * ,waveAmplitude,length,waveFormat);} catch(Exception e) {
		 * LOGGER.log(Level.SEVERE,"Exception: "+e.getMessage(),e); throw new
		 * RuntimeException(e); }
		 * 
		 * DataLine.Info informacao = new
		 * DataLine.Info(SourceDataLine.class,waveFormat);
		 * 
		 * try { linha = (SourceDataLine) AudioSystem.getLine(informacao);
		 * linha.open(waveFormat); }catch(LineUnavailableException e) {
		 * LOGGER.log(Level.SEVERE,"Exception: "+e.getMessage(),e); throw new
		 * RuntimeException(e); }catch(Exception e) {
		 * LOGGER.log(Level.SEVERE,"Exception: "+e.getMessage(),e); throw new
		 * RuntimeException(e); }
		 */
	}// configWave()

	/*
	 * public synchronized void playWave() {
	 * 
	 * }
	 */

	/**
	 * Emergency stop playing (don't know if it works)
	 */
	public synchronized void stopWave() {
		linha.drain();
		linha.close();
	}

	/**
	 * Setters and getters
	 * 
	 * @param waveFrequency1
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
