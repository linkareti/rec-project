/* 
 * ReCFaceConfigMigration.java created on 2009/02/02
 * 
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.config.migration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.linkare.rec.impl.baseUI.config.LocalizationBundle;
import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.config.ReCFaceConfig;

/**
 * @author Henrique Fernandes
 */
public class ReCFaceConfigMigration {

	private static final String RESULT_XML_FILE = "etc/ReCFaceConfig.xml";

	private static MyPropertyUtils propertyUtils = new MyPropertyUtils();
	
	/**
	 * Run me to Generate the New Face Configuration xml.
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {

        // FIXME uncomment after project refactoring
        
		try {
			// Set properties for input format xml
			// System.setProperty(key, value);

			// Unmarshal input xml values
			ReCBaseUIConfig reCBaseUIConfig = ReCBaseUIConfig.sharedInstance();

			// Update default ReCBaseUIConfig values (if needed)
			// (...)

			// Get the Staging Area
			Map stage = propertyUtils.describe(reCBaseUIConfig);

			// Do the required mapping transformation (if needed)
			// (...)

			// Perform a java deep copy to the new ReCConfig structure
			// ReCFaceConfig newReCConfig = getTheNewReCConfig(reCBaseUIConfig);
			ReCFaceConfig newReCConfig = getTheNewReCConfig(stage);

			// Marshal the new xml file
			JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);
			final File f = new File(RESULT_XML_FILE);

			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(newReCConfig, new FileOutputStream(f));

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private static ReCFaceConfig getTheNewReCConfig(Object source) {
		ReCFaceConfig dest = new ReCFaceConfig();
		try { // to deep copy properties
			Set<String> discard = new HashSet<String>();
			discard.add("propertyChangeListeners");

			propertyUtils.deepCopyProperties(dest, source, discard);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dest;
	}

}
