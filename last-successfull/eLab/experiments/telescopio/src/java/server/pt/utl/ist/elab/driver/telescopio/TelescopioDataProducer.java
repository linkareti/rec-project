/*
 * TelescopioDataProducer.java
 *
 * Created on February 2, 2005, 5:30 PM
 */

package pt.utl.ist.elab.driver.telescopio;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.driver.BaseDataSource;

public class TelescopioDataProducer extends BaseDataSource {

	public static String DRIVER_LOCATION = System.getProperty("c.driver.location");
	public static String IMAGE_NAME = System.getProperty("c.image.name");
	public static String DARK_NAME = System.getProperty("c.dark.name");
	public static String FLAT_NAME = System.getProperty("c.flat.name");

	private final int NUM_CHANNELS = 4;
	private final String LS = ReCSystemProperty.LINE_SEPARATOR.getValue();

	private TelescopioDriver driver = null;

	/** Creates a new instance of TelescopioDataProducer 
	 * @param driver */
	public TelescopioDataProducer(final TelescopioDriver driver) {
		this.driver = driver;

		if (TelescopioDataProducer.DRIVER_LOCATION == null) {
			TelescopioDataProducer.DRIVER_LOCATION = "/home/telescopio/Driver_telescopio/versao2";
		}

		if (TelescopioDataProducer.IMAGE_NAME == null) {
			TelescopioDataProducer.IMAGE_NAME = "image.fits";
		}

		if (TelescopioDataProducer.DARK_NAME == null) {
			TelescopioDataProducer.DARK_NAME = "dark.fits";
		}

		if (TelescopioDataProducer.FLAT_NAME == null) {
			TelescopioDataProducer.FLAT_NAME = "flat.fits";
		}
	}

	private boolean running = false;

	class FileChecker extends Thread {
		@Override
		public void run() {
			try {
				while (running) {
					final java.io.File f = new java.io.File(TelescopioDataProducer.DRIVER_LOCATION, "protocolo.dat");
					final java.io.FileReader fr = new java.io.FileReader(f);
					final java.io.BufferedReader br = new java.io.BufferedReader(fr);
					br.readLine();
					br.readLine();
					final String result = br.readLine();

					System.out.println("Reading from file: " + f.getName());
					System.out.println("Result read was: " + result);

					if (result.trim().equals("1")) {
						sendImages();
						final java.io.FileWriter fw = new java.io.FileWriter(f);
						fw.write("1" + LS);
						fw.write("0" + LS);
						fw.write("0");

						fw.close();

						running = false;

					}
					if (result.trim().equalsIgnoreCase("E")) {
						sendError();
						final java.io.FileWriter fw = new java.io.FileWriter(f);
						fw.write("1" + LS);
						fw.write("0" + LS);
						fw.write("0");

						fw.close();

						running = false;

					}

					br.close();
					fr.close();

					Thread.sleep(2000);
				}
			} catch (final InterruptedException ie) {
				ie.printStackTrace();
			} catch (final java.io.IOException ioe) {
				ioe.printStackTrace();
			}

			driver.stop();

		}
	}

	private void sendImages() {
		try {
			final java.io.File fimage = new java.io.File(TelescopioDataProducer.DRIVER_LOCATION,
					TelescopioDataProducer.IMAGE_NAME);
			final java.io.File fflat = new java.io.File(TelescopioDataProducer.DRIVER_LOCATION,
					TelescopioDataProducer.DARK_NAME);
			final java.io.File fdark = new java.io.File(TelescopioDataProducer.DRIVER_LOCATION,
					TelescopioDataProducer.FLAT_NAME);

			final PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];

			final PhysicsVal valimage = PhysicsValFactory.fromByteArray(getFileAsByteArray(fimage), "file/raw");
			final PhysicsVal valdark = PhysicsValFactory.fromByteArray(getFileAsByteArray(fdark), "file/raw");
			final PhysicsVal valflat = PhysicsValFactory.fromByteArray(getFileAsByteArray(fflat), "file/raw");

			values[0] = new PhysicsValue(valimage, null, com.linkare.rec.data.Multiplier.none);
			values[1] = new PhysicsValue(valdark, null, com.linkare.rec.data.Multiplier.none);
			values[2] = new PhysicsValue(valflat, null, com.linkare.rec.data.Multiplier.none);
			super.addDataRow(values);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void sendError() {
		try {
			final PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];

			final String error = "Error";

			final PhysicsVal errorph = PhysicsValFactory.fromByteArray(error.getBytes(), "text/str");

			values[3] = new PhysicsValue(errorph, null, com.linkare.rec.data.Multiplier.none);
			super.addDataRow(values);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] getFileAsByteArray(final java.io.File f) {
		try {
			final java.io.FileInputStream fis = new java.io.FileInputStream(f);
			final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			final java.io.BufferedOutputStream fos = new java.io.BufferedOutputStream(baos);
			while (fis.available() > 0) {
				fos.write(fis.read());
			}

			fis.close();
			fos.close();

			return baos.toByteArray();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void start(final String comand, final String texpo) {
		try {
			final java.io.File dados = new java.io.File(TelescopioDataProducer.DRIVER_LOCATION, "dados.txt");
			java.io.FileWriter fw = new java.io.FileWriter(dados);
			fw.write(comand + LS);
			fw.write(texpo);
			fw.close();

			// VER SQL!!!
			final boolean isWeatherOK = true;

			if (isWeatherOK) {
				final java.io.File protocol = new java.io.File(TelescopioDataProducer.DRIVER_LOCATION, "protocolo.dat");
				fw = new java.io.FileWriter(protocol);
				fw.write("1" + LS); // weather
				fw.write("1"); // iniciar uma nova exp;
				fw.close();
			}

			running = true;
			new FileChecker().start();
		} catch (final java.io.IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public void stopProduction() {
		running = false;
		setDataSourceStoped();
	}

	public void shutdown() {
		running = false;
	}

	public static void main(final String args[]) {
	}

	public void endProduction() {
		setDataSourceEnded();
	}

	@Override
	public void stopNow() {
		stopProduction();
	}
}
