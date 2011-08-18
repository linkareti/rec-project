/*
 * Sound.java
 *
 * Created on September 22, 2003, 11:13 AM
 */

package pt.utl.ist.elab.driver.statsound.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import pt.utl.ist.elab.driver.statsound.audio.media.protocol.sinewavegenerator.InterLacedSineWaveStream;

/**
 * 
 * @author andre
 */

public class SoundProducer implements javax.media.ControllerListener, Runnable {
	private double freq = 50.0;
	private double lastFreq = 50.0;

	private boolean playing = true;
	private final javax.media.Player p = null;
	private final InterLacedSineWaveStream baseStream = null;

	/** Creates a new instance of Sound */
	public SoundProducer() {
		System.out.println("Starting Sound Producer");
		new Thread(this).start();
	}

	public void startPlayingAudioFile(final java.io.File file) throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		final AudioInputStream AIStream = AudioSystem.getAudioInputStream(file);
		final DataLine.Info Info = new DataLine.Info(SourceDataLine.class, AIStream.getFormat());
		SourceDataLine Line = (SourceDataLine) AudioSystem.getLine(Info);
		Line.open(AIStream.getFormat());
		Line.start();
		final byte[] Buffer = new byte[16384];
		int Count;
		while ((Count = AIStream.read(Buffer)) > 0) {
			Line.write(Buffer, 0, Count);
		}
		AIStream.close();
		Line.drain();
		Line.stop();
		Line.close();
		Line = null;

	}

	public void startPlayingAudioFile(final int waveForm) throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		if (waveForm == 0) {
			// startPlayingAudioFile(new
			// java.io.File("/home/elab/java/pink.wav"));
			// startPlayingAudioFile(new
			// java.io.File("/usr/local/ReC6.0/driver/eLab/StatSound/pink.wav"));
			startPlayingAudioFile(new java.io.File("/home/elab/whitenoise.wav"));
		} else if (waveForm == 1) {
			// startPlayingAudioFile(new
			// java.io.File("/usr/local/ReC6.0/driver/eLab/StatSound/pulse.wav"));
			startPlayingAudioFile(new java.io.File("/home/elab/pulse.wav"));
		}
	}

	private SoundThread soundBoard = null;

	public void startPlayingSinWave(final double freq) {
		System.out.println("Playing sin wave with freq=" + freq);

		playing = true;
		this.freq = freq;
		try {

			soundBoard = new SoundThread();
			soundBoard.configure((float) freq, (float) freq, 15, 0);
			soundBoard.run();

			// javax.media.PackageManager.getProtocolPrefixList().add(
			// "pt.utl.ist.elab.driver.serial.stamp.statsound.audio");
			// javax.media.MediaLocator loc = new javax.media.MediaLocator(
			// "sinewavegenerator:");
			// DataSource source = (DataSource) javax.media.Manager
			// .createDataSource(loc);
			//
			// baseStream = (InterLacedSineWaveStream) source.getStreams()[0];
			// baseStream.setWaveLeftFreq(freq);
			// baseStream.setWaveRightFreq(freq);
			//
			// p = null;
			// p = javax.media.Manager.createPlayer(source);
			//
			// p.addControllerListener(this);
			//
			// p.realize();
			// if (!waitForState(p.Realized)) {
			// System.out.println("Failed to realize the processor.");
			// return;
			// }
			//
			// p.prefetch();
			// if (!waitForState(p.Prefetched)) {
			// System.out.println("Failed to prefetch the processor.");
			// return;
			// }
			//
			// p.start();
			// if (!waitForState(p.Started)) {
			// System.out.println("Failed to start the processor.");
			// return;
			// }
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public void stopPlaying() {
		if (!playing) {
			return;
		}
		System.out.println("Stoping");
		soundBoard.stopWave();
		soundBoard = null;
		playing = false;
		// if (p != null) {
		// p.stop();
		// p.deallocate();
		// }
	}

	public static void main(final String args[]) {
		new SoundProducer();
	}

	public void setFrequency(final double freq) {
		lastFreq = this.freq;
		this.freq = freq;
		if (baseStream != null) {
			baseStream.setWaveLeftFreq(freq);
			baseStream.setWaveRightFreq(freq);
		}
	}

	public boolean isFreqPlayed() {
		if (baseStream != null) {
			return baseStream.isFreqPlayed();
		}
		return false;
	}

	public boolean isPlaying() {
		return playing;
	}

	private final Object waitSync = new Object();
	private boolean stateTransitionOK = true;

	@Override
	public void controllerUpdate(final javax.media.ControllerEvent evt) {
		if (evt instanceof javax.media.ConfigureCompleteEvent || evt instanceof javax.media.RealizeCompleteEvent
				|| evt instanceof javax.media.PrefetchCompleteEvent || evt instanceof javax.media.StartEvent) {
			synchronized (waitSync) {
				stateTransitionOK = true;
				waitSync.notifyAll();
			}
		} else if (evt instanceof javax.media.ResourceUnavailableEvent) {
			synchronized (waitSync) {
				stateTransitionOK = false;
				waitSync.notifyAll();
			}
		} else if (evt instanceof javax.media.EndOfMediaEvent) {
			System.out.println("Reached end of media...");
			playing = false;
			stopPlaying();
		}
	}

	private boolean waitForState(final int state) {
		synchronized (waitSync) {
			try {
				while (p.getState() != state && stateTransitionOK) {
					waitSync.wait();
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		return stateTransitionOK;
	}

	@Override
	public void run() {
		boolean fileFound = false;

		while (true) {
			synchronized (this) {
				fileFound = false;
				try {
					final double freq = Double.parseDouble(getData(new java.io.File("/tmp/startFreq")));
					startPlayingSinWave(freq);
					fileFound = true;
				} catch (final NumberFormatException nfe) {
				}

				if (!fileFound) {
					try {
						final int wave = Integer.parseInt(getData(new java.io.File("/tmp/startFile")));
						startPlayingAudioFile(wave);
						fileFound = true;
					} catch (final NumberFormatException nfe) {
					} catch (final UnsupportedAudioFileException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (final IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (final LineUnavailableException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (!fileFound) {
					try {
						final double freq = Double.parseDouble(getData(new java.io.File("/tmp/freq")));
						setFrequency(freq);
						fileFound = true;
					} catch (final NumberFormatException nfe) {
					}
				}

				if (!fileFound) {
					try {
						Integer.parseInt(getData(new java.io.File("/tmp/stop")));
						stopPlaying();
						fileFound = true;
					} catch (final NumberFormatException nfe) {
					}
				}

				try {
					wait(100);
				} catch (final InterruptedException ie) {
				}
			}
		}
	}

	private String getData(final java.io.File file) {
		java.io.FileReader fr;
		int c;
		final StringBuffer sb = new StringBuffer();
		;
		try {
			fr = new java.io.FileReader(file);
			while ((c = fr.read()) != -1) {
				sb.append((char) c);
			}
			fr.close();
			file.delete();
		} catch (final java.io.FileNotFoundException fnfe) {
		} catch (final java.io.IOException ioe) {
		}
		return sb.toString().trim();
	}
}
