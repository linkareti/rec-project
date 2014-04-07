package com.linkare.rec.impl.config;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import com.linkare.rec.impl.utils.Defaults;

/**
 * Holds the ReC System properties. Maps the property name and the required
 * flag.
 */
public enum ReCSystemProperty {

	/**
	 * The ReCFaceConfig.xml url location
	 */
	FACE_CONFIG("rec.face.config", null, ReCSystemPropertyLocation.CLIENT),
	/**
	 * The MultiCastController bind name
	 */
	MULTICAST_BINDNAME("rec.multicastcontroller.bindname", null, ReCSystemPropertyLocation.MULTICAST),
	/**
	 * The MultiCastController initial reference
	 */
	MULTICAST_INITREF("rec.multicastcontroller.initref", "MultiCastController", ReCSystemPropertyLocation.MULTICAST,
			ReCSystemPropertyLocation.HARDWARE),
	/**
	 * The maximum number of hardwares supported by this MultiCast
	 */
	MULTICAST_MAX_HARDWARES("rec.multicastcontroller.max.hardwares", "40", ReCSystemPropertyLocation.MULTICAST),
	/**
	 * The maximum number of hardwares supported by this MultiCast
	 */
	MULTICAST_MAX_CLIENTS_PER_HARDWARE("rec.multicastcontroller.maxclients.per.hardware", "40",
			ReCSystemPropertyLocation.MULTICAST),
	/**
	 * The maximum time to be idle while waiting for samples in seconds.
	 * Anything beyond this time will make the DataCollector to deactivate
	 */
	MULTICAST_GET_SAMPLES_IDLE_TIME("rec.multicastdataproducer.getsamples.idletime", "60",
			ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The time for which to grant a lock on the hardware for each client
	 */
	MULTICAST_HARDWARE_LOCK_PERIOD("rec.multicast.hardware.lock.period", "10", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The percent of free memory after which serialization of data packets to
	 * disk occurs
	 */
	PERCENT_FREEMEMORY_SERIALIZATION("rec.percent.freememory.threshold.serialization", "10", ReCSystemPropertyLocation
			.values()),

	/**
	 * The core thread pool size to use by Processing manager threads
	 */
	CORE_THREADPOOL_SIZE("rec.processingmanager.threadPool.coresize", "10", ReCSystemPropertyLocation.values()),

	/**
	 * The max thread pool size to use by Processing manager threads
	 */
	MAX_THREADPOOL_SIZE("rec.processingmanager.threadPool.maxsize", "20", ReCSystemPropertyLocation.values()),

	/**
	 * The max thread pool size to use by Processing manager threads
	 */
	MAX_THREADPOOL_IDLETIME("rec.processingmanager.thread.idletime", "10", ReCSystemPropertyLocation.values()),

	/**
	 * The name of the repository plugin class implementation to use
	 */
	MULTICAST_REPOSITORY_CLASSNAME("rec.multicast.repository",
			"com.linkare.rec.impl.multicast.repository.impl.FSRepository", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * Wether to show a gui for the Hardware
	 */
	MULTICAST_SHOW_GUI("rec.multicastcontroller.show.gui", "false", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * System property key defining the ID of the laboratory as defined in the
	 * project rec.web
	 */
	MULTICAST_LAB_ID("rec.multicast.labid", "localhost", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The interval at which to refresh allocations from the underlying rec.web
	 */
	MULTICAST_SECURITYMANAGER_INTERVAL_TIME_LAP_MINUTES("rec.multicast.securitymanager.interval.lap.time.minutes",
			"0", ReCSystemPropertyLocation.MULTICAST),
	/**
	 * The remaining time at which multicast considers allocations are near and
	 * warns users that an allocation is going to be made
	 */
	MULTICAST_SECURITYMANAGER_NEAR_TIME_LAP_MINUTES("rec.multicast.securitymanager.near.lap.time.minutes", "5",
			ReCSystemPropertyLocation.MULTICAST),
	/**
	 * The interval to refresh kicked off users from the rec.web
	 */
	MULTICAST_SECURITYMANAGER_REFRESH_TIME_LAP_MINUTES("rec.multicast.securitymanager.refresh.lap.time.minutes",
			"15", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The interval to refresh badwords from the rec.web
	 */
	MULTICAST_BADWORD_REFRESH_TIME_LAP_MINUTES("rec.multicast.badwordmanager.refresh.lap.time.minutes", "1440",
			ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The classname of the SecurityManager to use in the multicast controller
	 * implementation
	 */
	MULTICAST_SECURITYMANAGER_CLASSNAME("rec.multicast.securitymanager",
			"com.linkare.rec.impl.multicast.security.DefaultSecurityManager", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The InitialContextFactory to use to connect to rec.web
	 */
	REC_WEB_INITIAL_CONTEXT_FACTORY("javax.naming.factory.initial",
			"com.sun.enterprise.naming.impl.SerialInitContextFactory", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The InitialContextFactory to use to connect to rec.web
	 */
	REC_WEB_NAMING_CTX_PKGS("java.naming.factory.url.pkgs", "com.sun.enterprise.naming",
			ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The Naming Factory State to use to connect to rec.web
	 */
	REC_WEB_NAMING_FACTORY_STATE("java.naming.factory.state",
			"com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The Naming Factory State to use to connect to rec.web
	 */
	REC_WEB_NAMING_HOST("rec.multicast.allocation.manager.host", "localhost", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The Naming Factory State to use to connect to rec.web
	 */
	REC_WEB_NAMING_PORT("rec.multicast.allocation.manager.port", "3700", ReCSystemPropertyLocation.MULTICAST),

	/**
	 * The ReC Web SOAP Endpoint to use on the client side
	 */
	REC_WEB_ENDPOINT("rec.web.endpoint", null),
	/**
	 * JNA library path location... Defined by JNA
	 */
	JNA_LIBRAY_PATH("jna.library.path", null),

	/**
	 * The openorb configuration file
	 */
	OPENORB_CONFIG("openorb.config", null, ReCSystemPropertyLocation.values()),
	/**
	 * The openorb profile
	 */
	OPENORB_PROFILE("openorb.profile", null, ReCSystemPropertyLocation.values()),
	/**
	 * The ORB Class
	 */
	ORG_OMG_CORBA_ORBCLASS("org.omg.CORBA.ORBClass", null, ReCSystemPropertyLocation.values()),
	/**
	 * The ORB SingletonClass
	 */
	ORG_OMG_CORBA_ORBSINGLETONCLASS("org.omg.CORBA.ORBSingletonClass", null, ReCSystemPropertyLocation.values()),

	/**
	 * Wether video is enabled or not
	 */
	VIDEO_ENABLED("video.enabled", null, ReCSystemPropertyLocation.CLIENT),
	/**
	 * define the client in auto acquire mode
	 */
	CLIENT_AUTO_ACQUIRE_RESULTS("rec.apparatus.autoacquireresult", "false", ReCSystemPropertyLocation.CLIENT),
	/**
	 * define the apparatus to connect to when in autoconnect mode
	 */
	CLIENT_AUTO_CONNECT_APPARATUS_ID("rec.apparatus.autoconnect.id", null),

	/**
	 * The url of the capture device for use with jmf capturing of data
	 */
	JMF_CAPTURE_DEVICE_URL("capture.device.url", null),

	/**
	 * The location of the hardware info xml definition file
	 */
	HARDWARE_INFO_FILE("HardwareInfo", null),

	/**
	 * Wether to show a gui for the Hardware
	 */
	HARDWARE_SHOW_GUI("rec.driver.show.gui", "false", ReCSystemPropertyLocation.HARDWARE),
	/**
	 * The hardware driver class to instantiate
	 */
	HARDWARE_DRIVER_CLASS("experiment.driver.class", null, ReCSystemPropertyLocation.HARDWARE),
	/**
	 * The location of the generic driver Rs232 config file path
	 */
	DRIVER_RS232_CONFIG_FILE_PATH("rec.driver.rs232_config_file_path", "hardwareserver/etc/Rs232Config.xml"),

	/**
	 * The user dir is established by the JVM but for completeness we define it
	 * here also
	 */
	USER_DIR("user.dir", null, ReCSystemPropertyLocation.values()),

	/**
	 * The user name is established by the JVM but for completeness we define it
	 * here also
	 */
	USER_NAME("user.name", null, ReCSystemPropertyLocation.values()),

	/**
	 * The user dir is established by the JVM but for completeness we define it
	 * here also
	 */
	USER_HOME("user.home", null, ReCSystemPropertyLocation.values()),

	/**
	 * The line separator is established by the JVM but for completeness we
	 * define it here also
	 */
	LINE_SEPARATOR("line.separator", null, ReCSystemPropertyLocation.values()),

	/**
	 * The file separator is established by the JVM but for completeness we
	 * define it here also
	 */
	FILE_SEPARATOR("file.separator", null, ReCSystemPropertyLocation.values()),
	/**
	 * The operating system name is established by the JVM but for completeness
	 * we define it here also
	 */
	OS_NAME("os.name", null, ReCSystemPropertyLocation.values()),
	/**
	 * The operating system architecture is established by the JVM but for
	 * completeness we define it here also
	 */
	OS_ARCH("os.arch", null, ReCSystemPropertyLocation.values()),
	/**
	 * The operating system version is established by the JVM but for
	 * completeness we define it here also
	 */
	OS_VERSION("os.version", null, ReCSystemPropertyLocation.values())

	;

	String name;

	String value;

	String defaultValue;

	/**
	 * Where the system property should be defined
	 */
	private EnumSet<ReCSystemPropertyLocation> requiredAt = EnumSet.noneOf(ReCSystemPropertyLocation.class);

	ReCSystemProperty(final String name, String defaultValue, ReCSystemPropertyLocation... requiredAt) {
		this.name = name;
		this.defaultValue = defaultValue;
		if (requiredAt != null && requiredAt.length > 0) {
			this.requiredAt = EnumSet.copyOf(Arrays.asList(requiredAt));
		}
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		if (value != null) {
			return value;
		}
		if (this == LINE_SEPARATOR) {
			value = System.getProperty(this.name);
		} else {
			value = Defaults.defaultIfEmpty(System.getProperty(this.name), defaultValue);
		}
		return value;
	}

	public boolean isRequired(ReCSystemPropertyLocation location) {
		return requiredAt.contains(location);
	}

	/**
	 * Lists all required system properties at a specified location that have no
	 * value defined
	 * 
	 * @param location The location at which to search for required properties.
	 *            {@link #requiredAt}
	 * @return The properties that should have been defined at that location
	 */
	public static Set<ReCSystemProperty> listRequiredNotDefined(ReCSystemPropertyLocation location) {
		final Set<ReCSystemProperty> requiredPropertiesNotDefined = EnumSet.noneOf(ReCSystemProperty.class);

		for (ReCSystemProperty recSystemProperty : ReCSystemProperty.values()) {
			if (recSystemProperty.isRequired(location)) {
				if ((System.getProperty(recSystemProperty.name) == null || (System.getProperty(recSystemProperty.name)
						.trim().length() == 0 && recSystemProperty != LINE_SEPARATOR))
						&& recSystemProperty.defaultValue == null) {
					requiredPropertiesNotDefined.add(recSystemProperty);
				}
			}
		}

		return requiredPropertiesNotDefined;
	}

	/**
	 * The defined system property value or the default passed as argument
	 * 
	 * @param defaultIfEmpty The value to be returned in case the defined
	 *            property is null or empty
	 * @return The value defined or the default passed as argument
	 */
	public String getValue(final String defaultIfEmpty) {
		return Defaults.defaultIfEmpty(getValue(), defaultIfEmpty);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "'" + this.name + "'" + (this.value == null ? "<undefined>" : "='" + value + "'");
	}

}
