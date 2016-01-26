/*
 * SerializationHelper.java
 *
 * Created on 28 de Outubro de 2002, 12:36
 */

package com.linkare.rec.impl.utils;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */

import java.io.File;

import com.linkare.rec.impl.config.ReCSystemProperty;

public class SerializationHelper {

	public static void writeObject(final String filename, final String dir, final Object o) throws java.io.IOException {
		final File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}

		final File file = new File(SerializationHelper.getFileName(filename, dir));
		if (file.exists()) {
			return;
		}

		/*
		 * XMLEncoder encoder=new XMLEncoder(new FileOutputStream(file));
		 * encoder.writeObject(o); encoder.flush(); encoder.close();
		 */

		final FileObjectOutputStream oos = new FileObjectOutputStream(file);
		oos.writeObject(o);
		oos.flush();
		oos.close();
	}

	public static Object readObject(final String filename, final String dir) throws java.io.IOException,
			ClassNotFoundException {
		final File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}

		/*
		 * XMLDecoder decoder=new XMLDecoder(new
		 * FileInputStream(getFileName(filename,dir))); Object
		 * o=decoder.readObject(); decoder.close();
		 */

		final FileObjectInputStream ois = new FileObjectInputStream(new File(SerializationHelper.getFileName(filename,
				dir)));
		final Object o = ois.readObject();
		ois.close();

		return o;
	}

	private static String getFileName(final String filename, final String dir) {
		return dir + ReCSystemProperty.FILE_SEPARATOR.getValue() + "DataBuffer_" + filename + ".ser";
	}

}
