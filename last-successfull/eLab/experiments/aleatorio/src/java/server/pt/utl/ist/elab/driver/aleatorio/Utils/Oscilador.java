package pt.utl.ist.elab.driver.aleatorio.Utils;

/*
 * Oscilador.java
 *
 * Created on 06 April 2003, 15:19
 */
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class Oscilador extends AudioInputStream {

	private final byte[] m_abData;
	private int m_nBufferPosition;
	private long m_lRemainingFrames;

	/** Creates a new instance of Oscilador */
	public Oscilador(final float frequencia1, final float frequencia2, final float amplitude,
			final long lengthInFrames, final AudioFormat audioFormat) {
		super(new ByteArrayInputStream(new byte[0]), new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
				audioFormat.getSampleRate(), 16, 2, 4, audioFormat.getFrameRate(), audioFormat.isBigEndian()),
				lengthInFrames);

		final float fAmplitude = (float) (amplitude * Math.pow(2, getFormat().getSampleSizeInBits() - 1));
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

		for (int nFrame = 0; nFrame < nFullLengthInFrames; nFrame++) {
			final double dCurrentPeriod = 1. / (frequencia1 + (double) nFrame / (double) nFullLengthInFrames
					* (frequencia2 - frequencia1));
			final int nCurrentPeriodLengthInFrames = (int) Math.round(getFormat().getFrameRate() * dCurrentPeriod);
			// float fPeriodPosition = (float) nFrame / ((float)
			// nPeriodLengthInFrames;
			// float fValue = (float) Math.cos(fPeriodPosition * 2.0 * Math.PI);

			final float fFullPosition = (float) nFrame / (float) nFullLengthInFrames * (lengthInSeconds);

			final float fValue = (float) Math.cos(fFullPosition * 2.0 * Math.PI / dCurrentPeriod);
			final int nValue = Math.round(fValue * fAmplitude);
			final int nBaseAddr = nFrame * getFormat().getFrameSize();
			// this is for 16 bit stereo, little endian
			m_abData[nBaseAddr + 0] = (byte) (nValue & 0xFF);
			m_abData[nBaseAddr + 1] = (byte) ((nValue >>> 8) & 0xFF);
			m_abData[nBaseAddr + 2] = (byte) (nValue & 0xFF);
			m_abData[nBaseAddr + 3] = (byte) ((nValue >>> 8) & 0xFF);
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
