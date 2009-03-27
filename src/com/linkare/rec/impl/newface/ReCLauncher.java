/* 
 * ReCStandaloneStart.java Created on Jan 31, 2009
 * 
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.linkare.rec.impl.exceptions.ExceptionCode;
import com.linkare.rec.impl.exceptions.ReCConfigurationException;
import com.linkare.rec.impl.newface.component.DefaultDialog;
import com.linkare.rec.impl.newface.config.ReCFaceConfig;
import com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel;
import com.linkare.rec.impl.newface.laf.flat.elabtheme.ElabTheme;
import com.linkare.rec.impl.protocols.ReCProtocols;

/**
 * Main entry for ReC Application.
 * 
 * @author Henrique Fernandes
 */
class ReCLauncher {

	private static final Logger log = Logger.getLogger(ReCLauncher.class.getName());

	protected static final int STATUS_SUCCESS = 0;

	protected static final int STATUS_INVALID_CONFIGURATION = 10;

	protected static final int STATUS_INVALID_LAF = 20;

	protected static final int STATUS_INVALID_REC_FACE_CONFIG = 30;

	/**
	 * Holder for the ReC System properties. Maps the property name and the
	 * required flag.
	 */
	public enum ReCSystemProperty {

		RECBASEUICONFIG("ReCBaseUIConfig", true), RECFACECONFIG("ReCFaceConfig", true), REC_MULTICASTCONTROLLER_BINDNAME(
				"ReC.MultiCastController.BindName", true), REC_MULTICASTCONTROLLER_INITREF(
				"ReC.MultiCastController.InitRef", true), OPENORB_CONFIG("openorb.config", true), OPENORB_PROFILE(
				"openorb.profile", true), ORG_OMG_CORBA_ORBCLASS("org.omg.CORBA.ORBClass", true), ORG_OMG_CORBA_ORBSINGLETONCLASS(
				"org.omg.CORBA.ORBSingletonClass", true);

		String name;
		boolean required;

		ReCSystemProperty(String name, boolean required) {
			this.name = name;
			this.required = required;
		}

		public String getName() {
			return name;
		}

		public boolean isRequired() {
			return required;
		}

	}

	/** Holds the ReC Configuration */
	protected ReCFaceConfig reCFaceConfig;

	/** Holds the LAF class name */
	protected String lafClassName;

	/** Creates a new <code>ReCLauncher</code> */
	public ReCLauncher() {
		lafClassName = FlatLookAndFeel.class.getName();
	}

	/**
	 * @return the lafClassName
	 */
	public String getLafClassName() {
		return lafClassName;
	}

	/**
	 * @param lafClassName the lafClassName to set
	 */
	public void setLafClassName(String lafClassName) {
		this.lafClassName = lafClassName;
	}

	/**
	 * Starts the ReC application.
	 */
	public void run() {
		log.fine("Starting Standalone ReC");

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					// Set Look and Feel

					// Init System Properties
					// (Web Start properties are set on jnlp descriptor)
					// TODO check anti-aliasing on windows
					System.setProperty("swing.aatext", "true");

					// mac properties
					if (System.getProperty("os.name").equals("Mac OS X")) {
						System.setProperty("apple.laf.useScreenMenuBar", "false");
						System.setProperty("apple.awt.textantialiasing", "on");
					}
					UIManager.setLookAndFeel(lafClassName);
					log.info("Look and Feel is correctly installed.");

					// TODO Launch Splash
					log.warning("TODO - Launch Splash Screen Here");

					// Check System Properties Availability
					checkSystemProperties();
					log.info("ReC System Properties are ok.");

					// Parse xml configuration
					String configLocation = System.getProperty(ReCSystemProperty.RECFACECONFIG.getName());
					if (log.isLoggable(Level.FINE)) {
						log.fine("Unmarshalling ReCFaceConfig from input stream location = " + configLocation);
					}
					InputStream is = ReCProtocols.getURL(configLocation).openConnection().getInputStream();
					reCFaceConfig = ReCFaceConfig.unmarshall(is);

					log.info("ReCFaceConfig is ok.");

					// Run User Interface
					log.info("Starting user interface...");

					runUserInterface();

				} catch (Exception e) {
					log.log(Level.SEVERE, "Some unexpected error occured.", e);
					DefaultDialog.showUnexpectedErrorPane(e);
					System.exit(STATUS_INVALID_REC_FACE_CONFIG);
				}

			}
		}); // End SwingUtilities.invokeLater
	}

	/**
	 * The user interface launch.
	 */
	protected void runUserInterface() {
		new ReCView(reCFaceConfig).setVisible(true);
	}

	/**
	 * Check for the ReC System Properties Availability.
	 * 
	 * @throws ReCConfigurationException If some required property is missing.
	 */
	public void checkSystemProperties() {
		List<String> missingRequiredProperties = new ArrayList<String>();

		for (ReCSystemProperty property : ReCSystemProperty.values()) {
			String propertyValue = System.getProperty(property.getName());
			log.fine(property.getName() + "=" + propertyValue);

			if (property.isRequired()) { // Required Property
				if (propertyValue == null || (propertyValue != null && propertyValue.isEmpty())) {
					missingRequiredProperties.add(property.getName());
				}
			} else { // Optional Property
				log.warning("Optinal ReC system property is missing: " + property);
			}
		}

		if (!missingRequiredProperties.isEmpty()) {
			log.severe("Required ReC system properties are missing: " + missingRequiredProperties);
			throw new ReCConfigurationException(ExceptionCode.MISSING_SYSTEM_PROPERTIES,
					"Please check the required system properties before run. " + missingRequiredProperties);
		}
	}

}
