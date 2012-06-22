/*
 * MovProjDriver.java
 *
 * Created on 24 de Abril de 2003, 8:53
 */

package pt.utl.ist.elab.driver.vmovproj;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.virtual.VirtualBaseDataSource;
import pt.utl.ist.elab.driver.virtual.VirtualBaseDriver;

import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.driver.IDataSource;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.net.protocols.Protocols;
import com.linkare.rec.impl.utils.Defaults;

/**
 * 
 * @author nomead
 */

public class MovProjDriver extends VirtualBaseDriver {

	private static String MovProj_DRIVER_LOGGER = "MovProj.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(MovProjDriver.MovProj_DRIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MovProjDriver.MovProj_DRIVER_LOGGER));
		}
	}

	/* Hardware and driver related variables */
	private static final String APPLICATION_IDENTIFIER = "E-Lab (Movimento de Projecteis Driver)";
	private static final String DRIVER_UNIQUE_ID = "MOVIMENTO_DE_PROJECTEIS_V1.0";
	private static final String HW_VERSION = "0.1";

	protected VirtualBaseDataSource dataSource = null;
	protected HardwareAcquisitionConfig config = null;
	protected HardwareInfo info = null;

	/** Creates a new instance of CGDriver */
	public MovProjDriver() {
	}

	@Override
	public void config(final HardwareAcquisitionConfig config, final HardwareInfo info) throws IncorrectStateException,
			WrongConfigurationException {
		fireIDriverStateListenerDriverConfiguring();
		info.validateConfig(config);
		extraValidateConfig(config, info);
		try {
			configure(config, info);
		} catch (final Exception e) {
			e.printStackTrace();
			LoggerUtil.logThrowable("Error on config...", e, Logger.getLogger(MovProjDriver.MovProj_DRIVER_LOGGER));
			throw new WrongConfigurationException();
		}
	}

	@Override
	public void configure(final HardwareAcquisitionConfig config, final HardwareInfo info)
			throws WrongConfigurationException {
		this.config = config;
		this.info = info;

		final int tbs = (int) config.getSelectedFrequency().getFrequency();
		final int nSamples = config.getTotalSamples();

		final boolean odeType = config.getSelectedHardwareParameterValue("odeType").trim().equals("1") ? true : false;
		final boolean gType = config.getSelectedHardwareParameterValue("gType").trim().equals("1") ? true : false;
		final boolean dragType = config.getSelectedHardwareParameterValue("dragType").trim().equals("1") ? true : false;

		final float x = Float.parseFloat(config.getSelectedHardwareParameterValue("x"));
		final float y = Float.parseFloat(config.getSelectedHardwareParameterValue("y"));
		final float z = Float.parseFloat(config.getSelectedHardwareParameterValue("z"));

		final float velMod = Float.parseFloat(config.getSelectedHardwareParameterValue("velMod"));
		final float velTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("velTheta"));
		final float velPhi = Float.parseFloat(config.getSelectedHardwareParameterValue("velPhi"));

		final float spinMod = Float.parseFloat(config.getSelectedHardwareParameterValue("spinMod"));
		final float spinTheta = Float.parseFloat(config.getSelectedHardwareParameterValue("spinTheta"));
		final float spinPhi = Float.parseFloat(config.getSelectedHardwareParameterValue("spinPhi"));

		final float mass = Float.parseFloat(config.getSelectedHardwareParameterValue("mass"));
		final float radius = Float.parseFloat(config.getSelectedHardwareParameterValue("radius"));

		final float dragCoef1 = Float.parseFloat(config.getSelectedHardwareParameterValue("dragCoef1"));
		final float dragCoef2 = Float.parseFloat(config.getSelectedHardwareParameterValue("dragCoef2"));
		final float densL = Float.parseFloat(config.getSelectedHardwareParameterValue("densL"));

		final float s0 = Float.parseFloat(config.getSelectedHardwareParameterValue("s0"));
		final float g = Float.parseFloat(config.getSelectedHardwareParameterValue("g"));
		final int dt = Integer.parseInt(config.getSelectedHardwareParameterValue("dt"));

		boolean rMod = config.getSelectedHardwareParameterValue("posModulus").trim().equals("1") ? true : false;
		boolean vMod = config.getSelectedHardwareParameterValue("velModulus").trim().equals("1") ? true : false;

		final ParameterConfig[] selectedParams = config.getSelectedHardwareParameters();

		if (selectedParams != null) {
			final ParameterConfig flowParam = null;
			for (final ParameterConfig selectedParam : selectedParams) {
				System.out.println(selectedParam.getParameterName() + " = " + selectedParam.getParameterValue());
			}
		}

		System.out.println("1");
		if (!rMod || !vMod) {
			final String[] graph1 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph1"));
			for (int i = 0; i < 2; i++) {
				if (graph1[i].equalsIgnoreCase("| r |")) {
					rMod = true;
				} else if (graph1[i].equalsIgnoreCase("| v |")) {
					vMod = true;
				}
			}
			if (!rMod || !vMod) {
				final String[] graph2 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph2"));
				for (int i = 0; i < 2; i++) {
					if (graph2[i].equalsIgnoreCase("| r |")) {
						rMod = true;
					} else if (graph2[i].equalsIgnoreCase("| v |")) {
						vMod = true;
					}
				}
				if (!rMod || !vMod) {
					final String[] graph3 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph3"));
					for (int i = 0; i < 2; i++) {
						if (graph3[i].equalsIgnoreCase("| r |")) {
							rMod = true;
						} else if (graph3[i].equalsIgnoreCase("| v |")) {
							vMod = true;
						}
					}
					if (!rMod || !vMod) {
						final String[] graph4 = splitArroundPoint(config.getSelectedHardwareParameterValue("graph4"));
						for (int i = 0; i < 2; i++) {
							if (graph4[i].equalsIgnoreCase("| r |")) {
								rMod = true;
							} else if (graph4[i].equalsIgnoreCase("| v |")) {
								vMod = true;
							}
						}
					}
				}
			}
		}
		System.out.println("2");
		try {
			dataSource = new MovProjDataProducer(this, x, y, z, velMod, velTheta, velPhi, spinMod, spinTheta, spinPhi,
					radius, mass, dragCoef1, dragCoef2, densL, s0, g, dt, odeType, gType, dragType, rMod, vMod, tbs,
					nSamples);
		} catch (final Exception e) {
			e.printStackTrace();
		} catch (final Throwable t) {
			t.printStackTrace();
		}

		System.out.println("3");
		for (int i = 0; i < config.getChannelsConfig().length; i++) {
			config.getChannelsConfig(i).setTotalSamples(config.getTotalSamples());
		}

		dataSource.setAcquisitionHeader(config);

		fireIDriverStateListenerDriverConfigured();
	}

	private String[] splitArroundPoint(final String tosplit) {
		final java.util.Vector<String> v = new java.util.Vector();
		final java.util.StringTokenizer token = new java.util.StringTokenizer(tosplit, ".");
		while (token.hasMoreTokens()) {
			v.add(token.nextToken());
		}
		return (String[]) v.toArray(new String[0]);
	}

	@Override
	public String getDriverUniqueID() {
		return MovProjDriver.DRIVER_UNIQUE_ID;
	}

	@Override
	public void shutdown() {
		if (dataSource != null) {
			dataSource.stopNow();
		}
		super.shutDownNow();
	}

	@Override
	public IDataSource start(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStarting();
		dataSource.startProduction();
		fireIDriverStateListenerDriverStarted();
		return dataSource;
	}

	@Override
	public void stop(final HardwareInfo info) throws IncorrectStateException {
		fireIDriverStateListenerDriverStoping();
		dataSource.stopNow();
		fireIDriverStateListenerDriverStoped();
	}

	@Override
	public Object getHardwareInfo() {
		fireIDriverStateListenerDriverReseting();

		final String baseHardwareInfoFile = "recresource://" + getClass().getPackage().getName().replaceAll("\\.", "/")
				+ "/HardwareInfo.xml";
		String prop = Defaults.defaultIfEmpty(System.getProperty("HardwareInfo"), baseHardwareInfoFile);

		if (prop.indexOf("://") == -1) {
			prop = "file:///" + System.getProperty("user.dir") + "/" + prop;
		}

		java.net.URL url = null;
		try {
			url = Protocols.getURL(prop);
		} catch (final java.net.MalformedURLException e) {
			LoggerUtil.logThrowable("Unable to load resource: " + prop, e,
					Logger.getLogger(MovProjDriver.MovProj_DRIVER_LOGGER));
			try {
				url = new java.net.URL(baseHardwareInfoFile);
			} catch (final java.net.MalformedURLException e2) {
				LoggerUtil.logThrowable("Unable to load resource: " + baseHardwareInfoFile, e2,
						Logger.getLogger(MovProjDriver.MovProj_DRIVER_LOGGER));
			}
		}
		fireIDriverStateListenerDriverReseted();
		return url;
	}
}
