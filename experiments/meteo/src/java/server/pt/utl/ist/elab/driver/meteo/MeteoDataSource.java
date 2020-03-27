/*
 * MeteoDataSource.java
 *
 * Created on 01 February 2004, 10:12
 */

package pt.utl.ist.elab.driver.meteo;

/**
 *
 * @author  Andr�
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import pt.utl.ist.cfn.math.MathUtils;
import pt.utl.ist.cfn.serial.NewSerialDataEvent;
import pt.utl.ist.cfn.serial.SerialComm;
import pt.utl.ist.cfn.serial.SerialDataListener;

public class MeteoDataSource implements SerialDataListener {

	private SerialComm sc = null;
	private Connection conn = null;
	private Float[] rainArray = null;

	private float temp = 0;
	private float rain = 0;
	private float windDir = 0;
	private float windVel = 0;
	private float humidity = 0;
	private float cond = 0;
	private float pression = 0;
	private float lum = 0;
	private byte state = 0;

	private static float MAX_TEMP = 0;
	private static float MIN_TEMP = 0;
	private static float MAX_RAIN = 0;
	private static float MIN_RAIN = 0;
	private static float MAX_WIND_DIR = 0;
	private static float MIN_WIND_DIR = 0;
	private static float MAX_WIND_VEL = 0;
	private static float MIN_WIND_VEL = 0;
	private static float MAX_HUMIDITY = 0;
	private static float MIN_HUMIDITY = 0;
	private static float MAX_COND = 0;
	private static float MIN_COND = 0;
	private static float MAX_PRESSION = 0;
	private static float MIN_PRESSION = 0;
	private static float MAX_LUM = 0;
	private static float MIN_LUM = 0;

	/** Properties stuff... */
	private java.io.InputStream is = null;
	private java.io.File propFile = null;
	private String resourceLocation = null;
	private java.util.Properties props = null;

	private java.util.Timer timer = null;

	/** Creates a new instance of MeteoDataSource 
	 * @param conn */
	public MeteoDataSource(final Connection conn) {
		sc = new SerialComm();
		sc.addSerialDataListener(this);
		// sc.setDTR(false);
		sc.setRTS(false);

		this.conn = conn;

		rainArray = new Float[120];
		java.util.Arrays.fill(rainArray, Float.valueOf(0));

		readProps();

		timer = new java.util.Timer();
		timer.schedule(new HourTask(), 3600000, 3600000);
	}

	private class HourTask extends java.util.TimerTask {
		@Override
		public void run() {
			try {
				final java.util.GregorianCalendar c = new java.util.GregorianCalendar();
				final String datetime = "'" + c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
						+ c.get(Calendar.DAY_OF_MONTH) + " " + c.get(Calendar.HOUR_OF_DAY) + ":"
						+ c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND) + "'";

				PreparedStatement ps = conn.prepareStatement("select * from current_meteo;");
				final ResultSet rs = ps.executeQuery();

				ps = conn.prepareStatement("insert into meteo values(" + datetime + "," + getMeanValues(rs) + ");");
				ps.execute();
				ps = conn.prepareStatement("delete from current_meteo");
				ps.execute();

				rs.close();
			} catch (final SQLException sqle) {
			}
		}
	}

	private void readProps() {
		System.out.println("Reading Meteo props!");
		resourceLocation = "MeteoProps.properties";
		propFile = new java.io.File(resourceLocation);
		try {
			is = new java.io.FileInputStream(propFile);
			props = new java.util.Properties();
			props.load(is);
			is.close();
		} catch (final java.io.FileNotFoundException fnfe) {
			System.out.println("Couldn't found the file...\n" + fnfe);
			System.out.println("Trying defaults");
			return;
		} catch (final java.io.IOException ioe) {
			System.out.println("Exception...\n" + ioe);
		}
		MeteoDataSource.MAX_TEMP = Float.parseFloat(props.getProperty("MAXTEMP").trim());
		MeteoDataSource.MAX_RAIN = Float.parseFloat(props.getProperty("MAXRAIN").trim());
		MeteoDataSource.MAX_WIND_DIR = Float.parseFloat(props.getProperty("MAXWINDDIR").trim());
		MeteoDataSource.MAX_WIND_VEL = Float.parseFloat(props.getProperty("MAXWINDVEL").trim());
		MeteoDataSource.MAX_HUMIDITY = Float.parseFloat(props.getProperty("MAXHUMIDITY").trim());
		MeteoDataSource.MAX_COND = Float.parseFloat(props.getProperty("MAXCOND").trim());
		MeteoDataSource.MAX_PRESSION = Float.parseFloat(props.getProperty("MAXPRESSION").trim());
		MeteoDataSource.MAX_LUM = Float.parseFloat(props.getProperty("MAXLUM").trim());
		MeteoDataSource.MIN_TEMP = Float.parseFloat(props.getProperty("MINTEMP").trim());
		MeteoDataSource.MIN_RAIN = Float.parseFloat(props.getProperty("MINRAIN").trim());
		MeteoDataSource.MIN_WIND_DIR = Float.parseFloat(props.getProperty("MINWINDDIR").trim());
		MeteoDataSource.MIN_WIND_VEL = Float.parseFloat(props.getProperty("MINWINDVEL").trim());
		MeteoDataSource.MIN_HUMIDITY = Float.parseFloat(props.getProperty("MINHUMIDITY").trim());
		MeteoDataSource.MIN_COND = Float.parseFloat(props.getProperty("MINCOND").trim());
		MeteoDataSource.MIN_PRESSION = Float.parseFloat(props.getProperty("MINPRESSION").trim());
		MeteoDataSource.MIN_LUM = Float.parseFloat(props.getProperty("MINLUM").trim());

		System.out.println("Meteo props ok!");
	}

	public synchronized void serialDataAvailable(final NewSerialDataEvent evt) {
		if (!evt.getData().trim().startsWith("dados")) {
			return;
		}
		final long start = System.currentTimeMillis();

		final java.util.StringTokenizer st = new java.util.StringTokenizer(evt.getData(), "=");
		st.nextToken();
		updateDB(convertReceived(st.nextToken()));

		final boolean weather = checkWeather();

		byte openOk = 0;
		byte toggle = 0;

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {

			if (state == 1 && !weather) {
				System.out.println("Closing cupula\nBad weather");
				sc.write("aqui");
				sc.write("sim");
				state = 0;
			} else {

				ps = conn.prepareStatement("SELECT * FROM cupula;");
				rs = ps.executeQuery();

				if (rs.last()) {
					toggle = rs.getByte(3);
				}
				if (toggle == 1 && state == 0 && !weather) {
					System.out.println("Cannot open cupula");
					sc.write("aqui");
					toggle = 0;
				} else if (toggle == 1 && state == 0 && weather) {
					System.out.println("Opening cupula");
					sc.write("aqui");
					sc.write("sim");
					toggle = 0;
					state = 1;
				} else if (toggle == 1 && state == 1) {
					System.out.println("Closing cupula");
					sc.write("aqui");
					sc.write("sim");
					toggle = 0;
					state = 0;
				} else {
					sc.write("aqui");
				}
			}

			if (weather) {
				openOk = 1;
			}

			ps = conn.prepareStatement("DELETE FROM cupula;");
			ps.execute();

			ps = conn.prepareStatement("INSERT INTO cupula VALUES(" + state + "," + openOk + "," + toggle + ");");
			ps.execute();

			rs.close();
			ps.close();
		} catch (final SQLException sqle) {
			sqle.printStackTrace();
		}

		System.out.println("Total time = " + (System.currentTimeMillis() - start));
	}

	private boolean checkWeather() {
		final boolean weather = true;

		if (!MathUtils.isInScale(Float.valueOf(temp), Float.valueOf(MeteoDataSource.MIN_TEMP), Float.valueOf(
				MeteoDataSource.MAX_TEMP))) {
			System.out.println("Temp out of scale!");
			return false;
		}
		if (!MathUtils.isInScale(Float.valueOf(rain), Float.valueOf(MeteoDataSource.MIN_RAIN), Float.valueOf(
				MeteoDataSource.MAX_RAIN))) {
			System.out.println("Rain out of scale!");
			return false;
		}
		if (!MathUtils.isInScale(Float.valueOf(windDir), Float.valueOf(MeteoDataSource.MIN_WIND_DIR), Float.valueOf(
				MeteoDataSource.MAX_WIND_DIR))) {
			System.out.println("windDir out of scale!");
			return false;
		}
		if (!MathUtils.isInScale(Float.valueOf(windVel), Float.valueOf(MeteoDataSource.MIN_WIND_VEL), Float.valueOf(
				MeteoDataSource.MAX_WIND_VEL))) {
			System.out.println("windVel out of scale!");
			return false;
		}
		if (!MathUtils.isInScale(Float.valueOf(cond), Float.valueOf(MeteoDataSource.MIN_COND), Float.valueOf(
				MeteoDataSource.MAX_COND))) {
			System.out.println("cond out of scale!");
			return false;
		}
		if (!MathUtils.isInScale(Float.valueOf(pression), Float.valueOf(MeteoDataSource.MIN_PRESSION), Float.valueOf(
				MeteoDataSource.MAX_PRESSION))) {
			System.out.println("pression out of scale!");
			return false;
		}
		if (!MathUtils.isInScale(Float.valueOf((lum / getMaxLumValue()) * 100f), Float.valueOf(MeteoDataSource.MIN_LUM),
				Float.valueOf(MeteoDataSource.MAX_LUM))) {
			System.out.println("lum out of scale!");
			return false;
		}
		System.out.println("Weather is fine!!");
		return weather;
	}

	private void updateDB(final String csv) {
		System.out.println("updating db with:\n" + csv);
		if (csv == null) {
			return;
		}
		try {
			final ResultSet rs = null;
			final PreparedStatement ps = conn.prepareStatement("insert into current_meteo values(" + csv + ");");
			ps.execute();
			ps.close();
		} catch (final SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	private String convertReceived(final String csv) {
		final java.util.StringTokenizer st = new java.util.StringTokenizer(csv, ",");

		if (st.countTokens() < 8) {
			System.out.println("error tokenizing the received parameters from serial port!");
			return null;
		}

		rain = getRainValue(Float.parseFloat(st.nextToken()));
		temp = MeteoDataSource.getTemperatureValue(Float.parseFloat(st.nextToken()));
		humidity = getHumidityValue(Float.parseFloat(st.nextToken()));
		windDir = getWindDirectionValue(Float.parseFloat(st.nextToken()));
		windVel = getWindVelocityValue(Float.parseFloat(st.nextToken()));
		cond = getCondValue(Float.parseFloat(st.nextToken()));
		pression = getPressionValue(Float.parseFloat(st.nextToken()));
		lum = Float.parseFloat(st.nextToken());
		updateLum(lum);

		state = Byte.parseByte(st.nextToken());

		MathUtils.shiftArray(rainArray, MathUtils.SHIFT_LEFT);
		rainArray[rainArray.length - 1] = Float.valueOf(rain);

		return rain + "," + temp + "," + humidity + "," + windDir + "," + windVel + "," + cond + "," + pression + ","
				+ lum;
	}

	public Float[] getRainArray() {
		return rainArray;
	}

	private String getMeanValues(final ResultSet rs) {
		float temp = 0;
		float rain = 0;
		float windDir = 0;
		float windVel = 0;
		float humidity = 0;
		float cond = 0;
		float pression = 0;
		float lum = 0;

		int n = 0;

		try {
			while (rs.next()) {
				rain += rs.getFloat(1);
				temp += rs.getFloat(2);
				humidity += rs.getFloat(3);
				windDir += rs.getFloat(4);
				windVel += rs.getFloat(5);
				cond += rs.getFloat(6);
				pression += rs.getFloat(7);
				lum += rs.getFloat(8);
				n++;
			}
			rs.close();
		} catch (final SQLException sqle) {
			sqle.printStackTrace();
		}
		if (n == 0) {
			System.out.println("ONLY ONE CURRENT SAMPLE!!!");
			n = 1;
		}

		// rain /= n;
		temp /= n;
		humidity /= n;
		windDir /= n;
		windVel /= n;
		cond /= n;
		pression /= n;
		lum /= n;

		final String mean = rain + "," + temp + "," + humidity + "," + windDir + "," + windVel + "," + cond + ","
				+ pression + "," + lum;
		return mean;
	}

	private static float getTemperatureValue(final float temp) {
		return (float) (3970 / (Math.log(temp * Math.pow(10, -6)) + 17.24337) - 273.15);
	}

	private float getRainValue(final float rain) {
		return rain * 0.127f;
	}

	private float getWindDirectionValue(final float windDir) {
		return (360f - windDir);
	}

	private float getWindVelocityValue(final float windVel) {
		if (windVel != 0) {
			return 3.426f * windVel / 30f + 3.08f;
		}
		return 0;
	}

	private float getHumidityValue(float humidity) {
		humidity = humidity / 676f * 100f;
		if (humidity > 100) {
			humidity = 100;
		}
		return humidity;
	}

	private float getPressionValue(final float pression) {
		return 100f + pression * 1150f / 1023f;
	}

	private void updateLum(final float lum) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("select * from lightmax order by light desc;");
			rs = ps.executeQuery();

			if (rs.last()) {
				final float lastValue = rs.getFloat(1);
				if (lum > lastValue) {
					ps = conn.prepareStatement("update lightmax set light = " + lum + " where light = " + lastValue
							+ " LIMIT 1;");
					ps.execute();
				}
			}
		} catch (final SQLException sqle) {
			sqle.printStackTrace();
		}
	}

	private float getMaxLumValue() {
		float lum = 0;
		float sum = 0;
		int counter = 0;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("select * from lightmax order by light desc;");
			rs = ps.executeQuery();

			while (rs.next()) {
				sum += rs.getFloat(1);
				counter++;
			}

			if (counter != 0) {
				lum = sum / counter;
			}

			ps.close();
			rs.close();
		} catch (final SQLException sqle) {
			sqle.printStackTrace();
		}

		return lum;
	}

	private float getCondValue(final float cond) {
		return cond;
	}
}
