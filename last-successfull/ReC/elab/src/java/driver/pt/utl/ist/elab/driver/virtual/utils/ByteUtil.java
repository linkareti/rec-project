/*
 * ByteUtil.java
 *
 * Created on 27 de Fevereiro de 2005, 2:55
 */
package pt.utl.ist.elab.driver.virtual.utils;

import java.nio.ByteBuffer;

/**
 * 
 * @author nomead
 */
public class ByteUtil {

	/** Creates a new instance of ByteUtil */
	public ByteUtil() {
	}

	public static byte[] floatToByteArray(final float value) {
		final ByteBuffer buf = ByteBuffer.allocate(4);

		buf.putFloat(value);
		return buf.array();
	}

	public static byte[] floatArrayToByteArray(final float[] values) {
		return ByteUtil.getObjectAsByteArray(values);
		/*
		 * byte[] toRet = new byte[4*values.length];
		 * 
		 * for(int i=0; i<values.length; i++) { byte[] converted =
		 * floatToByteArray(values[i]); for(int j=0; j<converted.length; j++) {
		 * toRet[4*i] = converted[0]; toRet[4*i + 1] = converted[1]; toRet[4*i +
		 * 2] = converted[2]; toRet[4*i + 3] = converted[3]; } } return toRet;
		 */

	}

	public static byte[] getObjectAsByteArray(final Object o) {
		System.out.println("Converting object as byte array...");
		byte[] objectAsByteArray = null;
		try {
			final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			final java.util.zip.GZIPOutputStream gz = new java.util.zip.GZIPOutputStream(baos);
			final java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(gz);

			oos.writeObject(o);
			gz.finish();
			objectAsByteArray = baos.toByteArray();

			oos.close();
			gz.close();
			baos.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return objectAsByteArray;
	}

	public static Object byteArrayToObject(final byte[] p) {
		System.out.println("Converting byte array to object...");
		Object obj = null;
		try {
			final java.io.ByteArrayInputStream braw = new java.io.ByteArrayInputStream(p);
			final java.util.zip.GZIPInputStream gzis = new java.util.zip.GZIPInputStream(braw);
			final java.io.ObjectInputStream ois = new java.io.ObjectInputStream(gzis);

			obj = ois.readObject();

			ois.close();
			gzis.close();
			braw.close();
		} catch (final java.io.IOException ie) {
			ie.printStackTrace();
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}

		return obj;
	}

	public static float byteArrayToFloat(final byte[] fvalue) {
		final ByteBuffer buf = ByteBuffer.allocate(fvalue.length);
		buf.put(fvalue);
		return buf.getFloat(0);
	}

	public static float[] byteArrayToFloatArray(final byte[] values) {
		if (values == null) {
			return null;
		}

		/*
		 * float[] toRet = new float[values.length / 4]; for(int i=0;
		 * i<toRet.length; i++) { byte[] toConvert = new byte[4]; toConvert[0] =
		 * values[4*i]; toConvert[1] = values[4*i + 1]; toConvert[2] =
		 * values[4*i + 2]; toConvert[3] = values[4*i + 3]; toRet[i] =
		 * byteArrayToFloat(toConvert); } return toRet;
		 */
		return (float[]) ByteUtil.byteArrayToObject(values);
	}

	public static void main(final String args[]) {
		final float[] test = (float[]) ByteUtil.byteArrayToObject(ByteUtil.getObjectAsByteArray(new float[1458000]));
		System.out.println(test.length);

	}
}
