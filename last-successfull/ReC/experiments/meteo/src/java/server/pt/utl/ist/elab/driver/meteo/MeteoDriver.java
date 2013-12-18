/*
 * MeteoDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.meteo;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

import pt.utl.ist.cfn.math.MathUtils;
import pt.utl.ist.cfn.serial.SerialComm;
import pt.utl.ist.cfn.sql.SQLConnector;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.BaseDriver;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author André
 */

public class MeteoDriver extends BaseDriver {

	private static final Logger LOGGER = Logger.getLogger(MeteoDriver.class.getName());

	/* Hardware and driver related variables */
	// private static final String APPLICATION_IDENTIFIER =
	// "E-Lab (Meteo Driver)";
	private static final String DRIVER_UNIQUE_ID = "ELAB_METEO_V01";
	// private static final String HW_VERSION = "0.1";

	protected MeteoDataProducer dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	protected String flowChartString = null;

	private final SerialComm sc = null;
	private Connection conn = null;

	/** Creates a new instance of RobotDriver */
	public MeteoDriver() {
	}

	public SerialComm getSerialComm() {
		return sc;
	}

	public Connection getSQLConnection() {
		return conn;
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		try {
			configure(config, info);
		} catch (final Exception e) {
			e.printStackTrace();
			throw new WrongConfigurationException(20);
		}
	}

	private boolean[] selected = null;
	private String startDate = "";
	private String endDate = "";

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		if (dataSource != null) {
			dataSource = null;
		}

		dataSource = new MeteoDataProducer(this);

		this.config = config;
		this.info = info;

		selected = MathUtils.integerToBinaryBoolean(
				Integer.parseInt(config.getSelectedHardwareParameter("Sensor").getParameterValue().trim()), 8);

		final String startHour = config.getSelectedHardwareParameter("StartHour").getParameterValue().trim();
		final String startDay = config.getSelectedHardwareParameter("StartDay").getParameterValue().trim();
		final String startMonth = config.getSelectedHardwareParameter("StartMonth").getParameterValue().trim();
		final String startYear = config.getSelectedHardwareParameter("StartYear").getParameterValue().trim();
		final String endHour = config.getSelectedHardwareParameter("EndHour").getParameterValue().trim();
		final String endDay = config.getSelectedHardwareParameter("EndDay").getParameterValue().trim();
		final String endMonth = config.getSelectedHardwareParameter("EndMonth").getParameterValue().trim();
		final String endYear = config.getSelectedHardwareParameter("EndYear").getParameterValue().trim();

		startDate = startYear + "-" + startMonth + "-" + startDay + " " + startHour + ":0:0";
		endDate = endYear + "-" + endMonth + "-" + endDay + " " + endHour + ":59:59";

		final String res = config.getSelectedHardwareParameter("Resolution").getParameterValue();
		int resolution = 0;
		if (res.trim().equalsIgnoreCase("Daily")) {
			resolution = MeteoDataProducer.DAILY;
		} else if (res.trim().equalsIgnoreCase("Hourly")) {
			resolution = MeteoDataProducer.HOURLY;
		} else if (res.trim().equalsIgnoreCase("Monthly")) {
			resolution = MeteoDataProducer.MONTHLY;
		} else {
			resolution = MeteoDataProducer.YEARLY;
		}

		final int nSamples = dataSource.checkNSamples(startDate, endDate, resolution);

		config.setTotalSamples(nSamples);
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(nSamples);
		}
		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	@Override
	public void extraValidateConfig(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		/** not going to use */
	}

	@Override
	public String getDriverUniqueID() {
		return MeteoDriver.DRIVER_UNIQUE_ID;
	}

	private MeteoDataSource source = null;

	@Override
	public void init(final HardwareInfo info) {
		final SQLConnector sqlc = new SQLConnector(SQLConnector.MYSQL);
		conn = sqlc.connect("192.168.0.111/meteo", "root", "");

		source = new MeteoDataSource(conn);

		System.out.println("INITED!");
		fireIDriverStateListenerDriverInited();
	}

	public Float[] getRainArray() {
		return source.getRainArray();
	}

	@Override
	public void reset(final HardwareInfo info) throws IncorrectStateException {
		/** Reset in not supported in webrobot */
	}

	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.shutdown();
		}
		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		if (dataSource != null) {
			fireIDriverStateListenerDriverStarting();
			// dataSource.setRunning(true);
			dataSource.startProduction(selected);
			fireIDriverStateListenerDriverStarted();
			return dataSource;
		} else {
			return null;
		}
	}

	@Override
	public IDataSource startOutput(final HardwareInfo info, final IDataSource source) throws IncorrectStateException {
		/** Don't know what is startOutput... */
		return null;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopProduction();
		fireIDriverStateListenerDriverStoped();
	}

}
