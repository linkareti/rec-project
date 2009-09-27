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
import com.linkare.rec.impl.data.PhysicsValFactory;
import com.linkare.rec.impl.driver.BaseDataSource;

public class TelescopioDataProducer extends BaseDataSource {

	public static String DRIVER_LOCATION = System.getProperty("c.driver.location");
	public static String IMAGE_NAME = System.getProperty("c.image.name");
	public static String DARK_NAME = System.getProperty("c.dark.name");
	public static String FLAT_NAME = System.getProperty("c.flat.name");

	private int NUM_CHANNELS = 4;
	private String LS = System.getProperty("line.separator");

	private TelescopioDriver driver = null;

	/** Creates a new instance of TelescopioDataProducer */
	public TelescopioDataProducer(TelescopioDriver driver) {
		this.driver = driver;

		if (DRIVER_LOCATION == null)
			DRIVER_LOCATION = "/home/telescopio/Driver_telescopio/versao2";

		if (IMAGE_NAME == null)
			IMAGE_NAME = "image.fits";

		if (DARK_NAME == null)
			DARK_NAME = "dark.fits";

		if (FLAT_NAME == null)
			FLAT_NAME = "flat.fits";
	}

	private boolean running = false;

	class FileChecker extends Thread {
		public void run() {
			try {
				while (running) {
					java.io.File f = new java.io.File(DRIVER_LOCATION, "protocolo.dat");
					java.io.FileReader fr = new java.io.FileReader(f);
					java.io.BufferedReader br = new java.io.BufferedReader(fr);
					br.readLine();
					br.readLine();
					String result = br.readLine();

					System.out.println("Reading from file: " + f.getName());
					System.out.println("Result read was: " + result);

					if (result.trim().equals("1")) {
						sendImages();
						java.io.FileWriter fw = new java.io.FileWriter(f);
						fw.write("1" + LS);
						fw.write("0" + LS);
						fw.write("0");

						fw.close();

						running = false;

					}
					if (result.trim().equalsIgnoreCase("E")) {
						sendError();
						java.io.FileWriter fw = new java.io.FileWriter(f);
						fw.write("1" + LS);
						fw.write("0" + LS);
						fw.write("0");

						fw.close();

						running = false;

					}

					br.close();
					fr.close();

					sleep(2000);
				}
			} catch (InterruptedException ie) {
				ie.printStackTrace();
			} catch (java.io.IOException ioe) {
				ioe.printStackTrace();
			}

			driver.stop();

		}
	}

	private void sendImages() {
		try {
			java.io.File fimage = new java.io.File(DRIVER_LOCATION, IMAGE_NAME);
			java.io.File fflat = new java.io.File(DRIVER_LOCATION, DARK_NAME);
			java.io.File fdark = new java.io.File(DRIVER_LOCATION, FLAT_NAME);

			PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];

			PhysicsVal valimage = PhysicsValFactory.fromByteArray(getFileAsByteArray(fimage), "file/raw");
			PhysicsVal valdark = PhysicsValFactory.fromByteArray(getFileAsByteArray(fdark), "file/raw");
			PhysicsVal valflat = PhysicsValFactory.fromByteArray(getFileAsByteArray(fflat), "file/raw");

			values[0] = new PhysicsValue(valimage, null, com.linkare.rec.data.Multiplier.none);
			values[1] = new PhysicsValue(valdark, null, com.linkare.rec.data.Multiplier.none);
			values[2] = new PhysicsValue(valflat, null, com.linkare.rec.data.Multiplier.none);
			super.addDataRow(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendError() {
		try {
			PhysicsValue[] values = new PhysicsValue[NUM_CHANNELS];

			String error = "Error";

			PhysicsVal errorph = PhysicsValFactory.fromByteArray(error.getBytes(), "text/str");

			values[3] = new PhysicsValue(errorph, null, com.linkare.rec.data.Multiplier.none);
			super.addDataRow(values);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] getFileAsByteArray(java.io.File f) {
		try {
			java.io.FileInputStream fis = new java.io.FileInputStream(f);
			java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
			java.io.BufferedOutputStream fos = new java.io.BufferedOutputStream(baos);
			while (fis.available() > 0)
				fos.write(fis.read());

			fis.close();
			fos.close();

			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public void start(String comand, String texpo) {
		try {
			java.io.File dados = new java.io.File(DRIVER_LOCATION, "dados.txt");
			java.io.FileWriter fw = new java.io.FileWriter(dados);
			fw.write(comand + LS);
			fw.write(texpo);
			fw.close();

			// VER SQL!!!
			boolean isWeatherOK = true;

			if (isWeatherOK) {
				java.io.File protocol = new java.io.File(DRIVER_LOCATION, "protocolo.dat");
				fw = new java.io.FileWriter(protocol);
				fw.write("1" + LS); // weather
				fw.write("1"); // iniciar uma nova exp;
				fw.close();
			}

			running = true;
			new FileChecker().start();
		} catch (java.io.IOException ioe) {
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

	public static void main(String args[]) {
	}

	public void endProduction() {
		setDataSourceEnded();
	}

	public void stopNow() {
		stopProduction();
	}
}
