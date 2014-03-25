/*
 * SamplesPacketMatrixIO.java
 *
 * Created on 5 de Janeiro de 2004, 17:59
 */

package com.linkare.rec.impl.data;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotActiveException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SequencedObjectIO implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(SequencedObjectIO.class.getName());

	/**
	 * 
	 */
	private static final int BUFFER_SIZE = 1024;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1382237311327166592L;
	private transient File file;
	private boolean dirtyFile = false;
	private boolean isTemporary = false;
	private transient RandomAccessFile raf;
	private transient ObjectOutputStream seqWriteStream;
	private List<long[]> objectsLocations = null;
	private int currentIndex = 0;

	public SequencedObjectIO() throws IOException {
		setFile(null);
	}

	public SequencedObjectIO(final File f) throws IOException {
		setFile(f);
	}

	public SequencedObjectIO(final String path) throws IOException {
		setFile(new File(path));
	}

	/**
	 * Getter for property file.
	 * 
	 * @return Value of property file.
	 * @throws IOException
	 * 
	 */
	public File getFile() throws IOException {
		return file;
	}

	/**
	 * Setter for property file.
	 * 
	 * @param outFile
	 * @throws IOException
	 * 
	 */
	public final void setFile(final File outFile) throws IOException {
		File tempFile = outFile;
		if (file != null && tempFile != null && tempFile.equals(file)) {
			return;
		} else {

			if (tempFile == null) {
				tempFile = File.createTempFile("TempIndexedObjectIO_", ".mser");
				objectsLocations = new ArrayList<long[]>();
				isTemporary = true;
				if (file != null) {
					file.delete();
				}
			} else {
				isTemporary = false;
				if (!tempFile.exists()) {
					tempFile.createNewFile();
				}
				if (file != null) {
					closeIO();

					final FileInputStream fis = new FileInputStream(file);
					final FileOutputStream fos = new FileOutputStream(tempFile);
					final byte[] dataChunk = new byte[BUFFER_SIZE];
					int bytesCount = 0;
					while ((bytesCount = fis.read(dataChunk)) != -1) {
						fos.write(dataChunk, 0, bytesCount);
					}
					fis.close();
					fos.flush();
					fos.close();

				} else {
					objectsLocations = new ArrayList<long[]>();
				}

			}

			file = tempFile;

			dirtyFile = true;

			openIO();
		}
	}

	private void closeIO() throws IOException {
		try {
			if (seqWriteStream != null) {
				seqWriteStream.close();
				seqWriteStream = null;
			}
			if (raf != null) {
				raf.close();
				raf = null;
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void openIO() throws IOException {
		if (dirtyFile) {
			closeIO();
			raf = new RandomAccessFile(getFile(), "rw");
			dirtyFile = false;
		}
	}

	public Object[] readObjects(final int startIndex, final int endIndex) throws SequencedObjectReadException {
		Object[] retVal = null;
		if (startIndex < 0 || endIndex >= objectsLocations.size() || endIndex < startIndex) {
			throw new SequencedObjectReadException(new IOException("Unable to read Objects"), Math.min(startIndex,
					endIndex));
		}

		retVal = new Object[endIndex - startIndex + 1];

		for (int i = startIndex; i <= endIndex; i++) {
			retVal[i - startIndex] = readObject(i);
		}

		return retVal;

	}

	public Object readObject(final int index) throws SequencedObjectReadException {

		final long[] location = objectsLocations.get(index);

		if (location == null) {
			throw new SequencedObjectReadException(new IOException("Unable to read Object!"), index);
		}

		final byte[] data = new byte[(int) (location[1] - location[0] + 1)];
		Object retVal = null;
		synchronized (raf) {
			try {
				raf.seek(location[0]);
				raf.readFully(data);
				final ByteArrayInputStream bais = new ByteArrayInputStream(data);

				final BufferedInputStream bis = new BufferedInputStream(bais);

				final ObjectInputStream ois = new ObjectInputStream(bis);
				retVal = ois.readObject();
				ois.close();

				bis.close();
				bais.close();
			} catch (final IOException e) {
				throw new SequencedObjectReadException(e, index);
			} catch (final ClassNotFoundException e) {
				throw new SequencedObjectReadException(new IOException(e.getMessage()), index);
			}
		}

		return retVal;
	}

	public void writeObject(final Object value) throws IOException {
		try {
			synchronized (raf)

			{
				long startPos = 0;

				if (currentIndex > 0) {
					final long[] location = objectsLocations.get(currentIndex - 1);
					startPos = location[1] + 1;
				}

				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				seqWriteStream = new ObjectOutputStream(baos);

				seqWriteStream.writeObject(value);
				seqWriteStream.flush();

				raf.seek(startPos);
				raf.write(baos.toByteArray());

				baos.close();
				seqWriteStream.close();

				final long endPos = startPos + baos.toByteArray().length - 1;

				objectsLocations.add(new long[] { startPos, endPos });
				currentIndex++;
			}
		} catch (final IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public void writeObjects(final Object[] objects) throws IOException {
		try {
			for (final Object object : objects) {
				writeObject(object);
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw new IOException(e.getMessage());
		}

	}

	public int size() {
		return objectsLocations == null ? 0 : objectsLocations.size();
	}

	public boolean moveToFile(final File newFile) throws IOException {
		final boolean retVal = getFile().renameTo(newFile);
		if (!retVal) {
			return retVal;
		}

		isTemporary = false;
		closeIO();
		openIO();
		return retVal;
	}

	private void writeObject(final ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeObject(getFile());
	}

	private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException, NotActiveException {
		ois.defaultReadObject();

		final File old_data_file = (File) ois.readObject();

		if (old_data_file != null && old_data_file.exists()) {
			closeIO();
			file = old_data_file;
			raf = new RandomAccessFile(file, "rw");
		}

	}

	@Override
	protected void finalize() throws Throwable {
		try {
			closeIO();
			if (isTemporary && getFile() != null) {
				getFile().delete();
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
		super.finalize();
	}
}
