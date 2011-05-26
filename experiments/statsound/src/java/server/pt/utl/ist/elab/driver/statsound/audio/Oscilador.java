package pt.utl.ist.elab.driver.statsound.audio;

/*
 * Oscilador.java
 *
 * Created on 06 April 2003, 15:19
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * 
 * @author PC
 */
public class Oscilador extends AudioInputStream {

	private final byte[] m_abData;
	private int m_nBufferPosition;
	private long m_lRemainingFrames;

	private double[] multipliers;
	private double[] values;

	/** Creates a new instance of Oscilador */
	public Oscilador(final float frequencia1, final float frequencia2, final float amplitude,
			final long lengthInFrames, final AudioFormat audioFormat, final int type) {
		super(new ByteArrayInputStream(new byte[0]), new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				audioFormat.getSampleRate(), 16, 2, 4, audioFormat.getFrameRate(), audioFormat.isBigEndian()),
				lengthInFrames);

		System.out.println("new Oscilador : type=" + type + ", lengthInFrames=" + lengthInFrames + ", bufferLength="
				+ lengthInFrames * getFormat().getFrameSize());

		final float fAmplitude = 0.80f * (float) (amplitude * Math.pow(2, getFormat().getSampleSizeInBits() - 1));
		// int nPeriodLengthInFrames = Math.round(getFormat().getFrameRate() /
		// frequencia1);
		// int nBufferLength = nPeriodLengthInFrames *
		// getFormat().getFrameSize();
		final int nFullLengthInFrames = (int) lengthInFrames;

		final int nFirstPeriodLengthInFrames = Math.round(getFormat().getFrameRate() / frequencia1);
		final int nLastPeriodLengthInFrames = Math.round(getFormat().getFrameRate() / frequencia2);

		final int nBufferLength = nFullLengthInFrames * getFormat().getFrameSize();

		int freqSign = 0;
		if (frequencia1 < frequencia2) {
			freqSign = -1;
		} else {
			freqSign = 1;
		}

		// int nFreqDif = Math.round((frequencia1 - fequencia2) * freqSign);
		final int lengthInSeconds = (int) Math.round((double) lengthInFrames / audioFormat.getFrameRate());
		final int nPeriodCount = (int) Math.round(lengthInSeconds / ((1. / frequencia1 + 1. / frequencia2) / 2));

		System.out.println("seconds:" + lengthInSeconds + " periods: " + nPeriodCount);

		m_abData = new byte[nBufferLength];
		m_lRemainingFrames = lengthInFrames;

		// SIN
		if (type == 1) {

			double dCurrentPeriod = 0;

			for (int nFrame = 0; nFrame < nFullLengthInFrames; nFrame++) {

				// if (nFrame % Math.round(nFullLengthInFrames/100) == 0 ||
				// dCurrentPeriod == 0)
				dCurrentPeriod = 1. / (frequencia1 + (double) nFrame / (double) nFullLengthInFrames
						* (frequencia2 - frequencia1));

				final float fFullPosition = (float) nFrame / (float) nFullLengthInFrames * (lengthInSeconds);
				final float fValue = (float) Math.cos(fFullPosition * 2.0 * Math.PI / dCurrentPeriod);

				final int nValue = Math.round(fValue * fAmplitude);
				final int nBaseAddr = nFrame * getFormat().getFrameSize();
				m_abData[nBaseAddr + 0] = (byte) (nValue & 0xFF);
				m_abData[nBaseAddr + 1] = (byte) ((nValue >>> 8) & 0xFF);
				m_abData[nBaseAddr + 2] = (byte) (nValue & 0xFF);
				m_abData[nBaseAddr + 3] = (byte) ((nValue >>> 8) & 0xFF);
			}

			// PINK
		} else if (type == 2) {

			final int poles = 5;
			final double alpha = 1.0;
			multipliers = new double[poles];
			values = new double[poles];

			final Random rnd = new Random();

			double a = 1;
			for (int i = 0; i < poles; i++) {
				a = (i - alpha / 2) * a / (i + 1);
				multipliers[i] = a;
			}
			// fill with trash
			for (int i = 0; i < 5 * poles; i++) {
				double x = rnd.nextGaussian();
				for (int j = 0; j < poles; j++) {
					x -= multipliers[j] * values[j];
				}
				System.arraycopy(values, 0, values, 1, values.length - 1);
				values[0] = x;
			}

			for (int nFrame = 0; nFrame < nFullLengthInFrames; nFrame++) {

				if (nFrame % (Math.round(getFormat().getFrameRate() / frequencia1)) == 0) {

					double x = rnd.nextGaussian();

					for (int i = 0; i < poles; i++) {
						x -= multipliers[i] * values[i];
					}
					System.arraycopy(values, 0, values, 1, values.length - 1);
					values[0] = x;

					final float fValue = (float) x;

					final int nValue = Math.round(fValue * fAmplitude);
					final int nBaseAddr = nFrame * getFormat().getFrameSize();
					m_abData[nBaseAddr + 0] = (byte) (nValue & 0xFF);
					m_abData[nBaseAddr + 1] = (byte) ((nValue >>> 8) & 0xFF);
					m_abData[nBaseAddr + 2] = (byte) (nValue & 0xFF);
					m_abData[nBaseAddr + 3] = (byte) ((nValue >>> 8) & 0xFF);
				}
			}
			// PULSE
		} else if (type == 3) {
			System.out.println("#: " + audioFormat.getFrameRate() + " " + frequencia1 / 10);
			// int perFrames = Math.round(audioFormat.getFrameRate() / ((float)
			// -1900 + frequencia1 * 15.2f ));
			final int perFrames = Math.round(audioFormat.getFrameRate() / (frequencia1 / 10));
			final int perFrames10 = Math.round(perFrames / 25f);
			float fValue = 0f;
			for (int nFrame = 0; nFrame < nFullLengthInFrames; nFrame++) {
				if (nFrame % perFrames < perFrames10) {
					fValue = 1f;
				} else {
					fValue = 0f;
				}
				final int nValue = Math.round(fValue * fAmplitude);
				final int nBaseAddr = nFrame * getFormat().getFrameSize();
				m_abData[nBaseAddr + 0] = (byte) (nValue & 0xFF);
				m_abData[nBaseAddr + 1] = (byte) ((nValue >>> 8) & 0xFF);
				m_abData[nBaseAddr + 2] = (byte) (nValue & 0xFF);
				m_abData[nBaseAddr + 3] = (byte) ((nValue >>> 8) & 0xFF);
			}
		}
	}

	@Override
	public int read(final byte[] abData, int nOffset, final int nLength) throws IOException {
		if (nLength % getFormat().getFrameSize() != 0) {
			throw new IOException("Length tem de ser um multiplo inteiro de FrameSize");
		}
		final int nConstrainedLength = Math.min(available(), nLength);
		int nRemainingLength = nConstrainedLength;
		while (nRemainingLength > 0) {
			int nNumBytesToCopyNow = m_abData.length - m_nBufferPosition;
			nNumBytesToCopyNow = Math.min(nNumBytesToCopyNow, nRemainingLength);
			System.arraycopy(m_abData, m_nBufferPosition, abData, nOffset, nNumBytesToCopyNow);
			nRemainingLength -= nNumBytesToCopyNow;
			nOffset += nNumBytesToCopyNow;
			m_nBufferPosition = (m_nBufferPosition + nNumBytesToCopyNow) % m_abData.length;
		}
		final int nFramesRead = nConstrainedLength / getFormat().getFrameSize();
		if (m_lRemainingFrames != AudioSystem.NOT_SPECIFIED) {
			m_lRemainingFrames -= nFramesRead;
		}
		int nReturn = nConstrainedLength;
		if (m_lRemainingFrames == 0) {
			nReturn = -1;
		}
		return nReturn;
	}

	@Override
	public int available() {
		int nAvailable = 0;
		if (m_lRemainingFrames == AudioSystem.NOT_SPECIFIED) {
			nAvailable = Integer.MAX_VALUE;
		} else {
			final long lBytesAvailable = m_lRemainingFrames * getFormat().getFrameSize();
			nAvailable = (int) Math.min(lBytesAvailable, Integer.MAX_VALUE);
		}
		return nAvailable;
	}
}
