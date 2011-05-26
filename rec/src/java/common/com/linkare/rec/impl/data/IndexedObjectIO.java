/*
 * IndexedObjectIO.java
 *
 * Created on 7 de Janeiro de 2004, 14:23
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class IndexedObjectIO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7448912281979433566L;
	private transient File file;
	private boolean file_dirty = false;
	private boolean isTemporary = false;
	private transient RandomAccessFile raf;
	private transient ObjectOutputStream seqWriteStream;
	private HashMap<Object, long[]> objectsLocations = null;
	private long lastPos = 0;

	/** Creates a new instance of IndexedObjectIO */
	public IndexedObjectIO(final File f) throws IOException {
		setFile(f);
	}

	/** Creates a new instance of IndexedObjectIO */
	public IndexedObjectIO(final String path) throws IOException {
		setFile(new File(path));
	}

	/** Creates a new instance of IndexedObjectIO */
	public IndexedObjectIO() throws IOException {
		setFile(null);
	}

	/**
	 * Getter for property file.
	 * 
	 * @return Value of property file.
	 * 
	 */
	public File getFile() throws IOException {

		return file;

	}

	/**
	 * Setter for property file.
	 * 
	 * @param file New value of property file.133.5
	 * 
	 */
	public void setFile(File f) throws IOException {

		lastPos = 0;

		if (file != null && f != null && f.equals(file)) {
			return;
		} else {
			objectsLocations = new HashMap<Object, long[]>();

			if (f == null) {
				f = File.createTempFile("TempIndexedObjectIO_", ".mser");
				isTemporary = true;
				if (file != null) {
					file.delete();
				}
			} else {
				isTemporary = false;
				if (!f.exists()) {
					f.createNewFile();
				}
				if (file != null) {
					closeIO();

					// System.out.println("Renaming file from:" +
					// file.getAbsolutePath() + "to:" + f.getAbsolutePath());

					final FileInputStream fis = new FileInputStream(file);
					final FileOutputStream fos = new FileOutputStream(f);
					final byte[] dataChunk = new byte[1024];
					int bytesCount = 0;
					while ((bytesCount = fis.read(dataChunk)) != -1) {
						fos.write(dataChunk, 0, bytesCount);
					}
					fis.close();
					fos.flush();
					fos.close();

					// System.out.println("Renamed and moved contents of file from:"
					// + file.getAbsolutePath() + "to:" + f.getAbsolutePath());
				}
			}

			file = f;
			file_dirty = true;
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
			e.printStackTrace();
		}
		// System.out.println("No exception here at closeIO");
	}

	private void openIO() throws IOException {
		if (file_dirty) {
			closeIO();

			// nc
			raf = new RandomAccessFile(getFile(), "rw");
			// *nc
			// raf=new RandomAccessFile(getFile(),"r");

			// seqWriteStream=new ObjectOutputStream(new
			// FileOutputStream(getFile()));
			file_dirty = false;
		}
	}

	public Object[] readObjects(final Object[] keys) throws IndexedObjectReadException {
		Object[] retVal = null;
		if (keys == null) {
			throw new IndexedObjectReadException(new IOException("Unable to read Objects - keys are null"), null);
		}

		retVal = new Object[keys.length];

		for (int i = 0; i < retVal.length; i++) {
			retVal[i] = readObject(keys[i]);
		}

		return retVal;

	}

	public Object readObject(final Object key) throws IndexedObjectReadException {

		final long[] location = objectsLocations.get(key);

		if (location == null) {
			throw new IndexedObjectReadException(new IOException("Unable to read Object - key doesn't exist here!"),
					key);
		}

		final byte[] data = new byte[(int) (location[1] - location[0] + 1)];
		Object retVal = null;
		synchronized (raf) {
			try {
				/*
				 * raf.seek(location[0]); raf.readFully(data);
				 * ByteArrayInputStream bais=new ByteArrayInputStream(data);
				 * ObjectInputStream ois=new ObjectInputStream(bais);
				 * retVal=ois.readObject(); ois.close(); bais.close();
				 */

				raf.seek(location[0]);
				raf.readFully(data);
				final ByteArrayInputStream bais = new ByteArrayInputStream(data);

				// nc
				final BufferedInputStream bis = new BufferedInputStream(bais);
				// *nc

				// ObjectInputStream ois=new ObjectInputStream(bais);
				final ObjectInputStream ois = new ObjectInputStream(bis);
				retVal = ois.readObject();
				ois.close();

				// nc
				bis.close();
				bais.close();
				// *nc
			} catch (final IOException e) {
				e.printStackTrace();
				throw new IndexedObjectReadException(e, key);
			} catch (final ClassNotFoundException e) {
				throw new IndexedObjectReadException(new IOException(e.getMessage()), key);
			}
		}

		return retVal;
	}

	public Object removeObject(final Object key) throws IndexedObjectReadException {
		final Object retVal = readObject(key);
		objectsLocations.remove(key);
		return retVal;
	}

	public Object[] removeObjects(final Object[] keys) throws IndexedObjectReadException {
		final Object[] retVal = readObjects(keys);
		for (final Object key : keys) {
			objectsLocations.remove(key);
		}

		return retVal;
	}

	public void writeObject(final Object key, final Object value) throws IOException {
		synchronized (raf) {
			try {
				// THE FILE LENGTH WASN'T WORKING IN WINDOWS...
				// long startPos=getFile().length();

				long startPos = 0;

				if (lastPos > 0) {
					startPos = lastPos + 1;
				}

				// nc
				final ByteArrayOutputStream baos = new ByteArrayOutputStream();
				seqWriteStream = new ObjectOutputStream(baos);
				// *nc

				seqWriteStream.writeObject(value);
				seqWriteStream.flush();

				// nc
				raf.seek(startPos);
				raf.write(baos.toByteArray());
				// *nc

				baos.close();
				seqWriteStream.close();

				// long endPos=getFile().length()-1;
				final long endPos = startPos + baos.toByteArray().length - 1;

				lastPos = endPos;

				objectsLocations.put(key, new long[] { startPos, endPos });
			} catch (final java.io.IOException ioe) {
				/*
				 * System.out.println("********************");
				 * System.out.println("Exception at indexed Object IO");
				 * System.out.println("********************");
				 */
				ioe.printStackTrace();
			}

		}
	}

	public void writeObjects(final Map objects) throws IOException {
		final Iterator iter = objects.entrySet().iterator();
		while (iter.hasNext()) {
			final Map.Entry entry = (Map.Entry) iter.next();
			writeObject(entry.getKey(), entry.getValue());
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

	// private static final long serialVersionUID=1938846425062959110L;
	private void writeObject(final ObjectOutputStream oos) throws IOException {
		oos.defaultWriteObject();
		oos.writeUTF(getFile().getAbsolutePath());
	}

	private void readObject(final ObjectInputStream ois) throws IOException, ClassNotFoundException, NotActiveException {
		ois.defaultReadObject();
		final String path = ois.readUTF();
		setFile(new File(path));
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			// System.out.println("Class not need any more  - IndexedObjectIO...");
			closeIO();
			/*
			 * System.out.println("file is temporary? "+isTemporary);
			 * System.out.println("*******************************");
			 * System.out.
			 * println("Where is the file? "+getFile().getAbsolutePath());
			 */
			if (isTemporary && getFile() != null) {
				// System.out.println("Deleting File...!");
				getFile().delete();
			}
		} catch (final Exception e) {
			/*
			 * System.out.println("*******************************");
			 * System.out.println("*******************************");
			 * System.out.println("GOT AN EXCEPTION WHILE FINALIZING");
			 * e.printStackTrace();
			 * System.out.println("*******************************");
			 * System.out.println("*******************************");
			 */
		}
	}

}
