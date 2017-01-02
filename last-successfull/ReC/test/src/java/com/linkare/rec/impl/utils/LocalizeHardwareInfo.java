/* 
 * LocalizeHardwareInfo.java created on 3 Mar 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.zip.ZipException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.linkare.rec.data.metadata.ChannelParameter;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.web.config.Apparatus;
import com.linkare.rec.web.config.LocalizationBundle;

/**
 * 
 * @author jpereira - Linkare TI
 */
public class LocalizeHardwareInfo {

	
	
	/**
	 * @param args
	 * @throws IOException
	 * @throws ZipException
	 * @throws JAXBException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */
	public static void main(String[] args) throws ZipException, IOException, JAXBException, ParserConfigurationException, SAXException {
		
		File baseDir = new File("experiments");
		if (baseDir.exists()) {
			File[] experimentsDirs = baseDir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					if (pathname.isDirectory()) {
						File childFaceConfig = new File(pathname, "etc/ReCFaceConfig.xml");
						return childFaceConfig.exists() && childFaceConfig.isFile() && childFaceConfig.canRead();
					}
					return false;
				}
			});

			JAXBContext ctx = JAXBContext.newInstance(Apparatus.class);
			Unmarshaller apparatusUnmarshaller = ctx.createUnmarshaller();
			Marshaller apparatusMarshaller = ctx.createMarshaller();
			apparatusMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			apparatusMarshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(false);

			for (File experimentDir : experimentsDirs) {
				File recFaceConfigFile = new File(experimentDir, "etc/ReCFaceConfig.xml");
				String experimentName = experimentDir.toString().replace("experiments/", "");
				try {
					Document recFaceConfigDocument = dbf.newDocumentBuilder().parse(recFaceConfigFile);
					Element documentElement = recFaceConfigDocument.getDocumentElement();
					recFaceConfigDocument.renameNode(documentElement, "http://rec.linkare.com/client/config",
							"apparatus");

					Apparatus apparatus = (Apparatus) apparatusUnmarshaller.unmarshal(recFaceConfigDocument);

					System.out.println("Read apparatus config: " + apparatus.getLocation());

					String bundleName = null;
					String bundleLocation = null;
					if (apparatus.getLocalizationBundle() != null) {
						List<LocalizationBundle> localizationBundles = apparatus.getLocalizationBundle();
						if (localizationBundles.size() > 1) {
							System.out.println("********* MORE THAN ONE BUNDLE PER EXPERIMENT *********");
						}
						for (LocalizationBundle bundle : localizationBundles) {
							bundleName = bundle.getName();
							bundleLocation = bundle.getLocation();
							System.out.println("Loading bundle: " + bundle.getName() + " from " + bundle.getLocation());
							ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
						}
					}

					File messagesFile = new File(experimentDir, "src/java/client"
							+ bundleLocation.substring("recresource://".length()) + ".properties");
					if (!messagesFile.exists()) {
						System.out.println("ERROR: messagesFile: " + messagesFile.getAbsolutePath()
								+ " does not exist...");
						messagesFile.createNewFile();
					}
					File messagesFileEn = new File(experimentDir, "src/java/client"
							+ bundleLocation.substring("recresource://".length()) + "_en.properties");
					if (!messagesFileEn.exists()) {
						System.out.println("ERROR: messagesFile English: " + messagesFileEn.getAbsolutePath()
								+ " does not exist...");
						copyFile(messagesFile, messagesFileEn);
					}
					File messagesFilePt = new File(experimentDir, "src/java/client"
							+ bundleLocation.substring("recresource://".length()) + "_pt.properties");
					if (!messagesFileEn.exists()) {
						System.out.println("ERROR: messagesFile Portuguese: " + messagesFilePt.getAbsolutePath()
								+ " does not exist...");
						copyFile(messagesFile, messagesFilePt);
					}

//					System.out.println("--------------- " + apparatus.getLocation() + " -------------");
//
//					System.out.println("Description: " + apparatus.getDescriptionStringBundleKey());
//					System.out.println("Experiment name: " + apparatus.getDisplayStringBundleKey());
//					System.out.println("Experiment icon: " + apparatus.getIconLocationBundleKey());
//					System.out.println("Experiment tooltip: " + apparatus.getToolTipBundleKey());
//					System.out.println("Experiment bg image: " + apparatus.getDesktopLocationBundleKey());
//					System.out.println("Experiment ID: " + apparatus.getLocation());
//					System.out.println("Experiment Customizer class: "
//							+ apparatus.getCustomizerClassLocationBundleKey());
//					System.out.println("Experiment header display class: "
//							+ apparatus.getHeaderDisplayClassLocationBundleKey());
//					System.out.println("Experiment DataModel class: " + apparatus.getDataModelClassLocationBundleKey());
//					System.out.println("Experiment DisplayFactory class: "
//							+ apparatus.getDisplayFactoryClassLocationBundleKey());
//
//					String bundleKey=apparatus.getDescriptionStringBundleKey();
//					if(!isDefined(bundleKey)) {
//						System.out.println("***** "+bundleKey +" is not defined... Ooops!");
//					}
//					bundleKey=apparatus.getDisplayStringBundleKey();
//					if(!isDefined(bundleKey)) {
//						System.out.println("***** "+bundleKey +" is not defined... Ooops!");
//					}
//					bundleKey=apparatus.getIconLocationBundleKey();
//					if(!isDefined(bundleKey)) {
//						System.out.println("***** "+bundleKey +" is not defined... Ooops!");
//					}
//					bundleKey=apparatus.getToolTipBundleKey();
//					if(!isDefined(bundleKey)) {
//						System.out.println("***** "+bundleKey +" is not defined... Ooops!");
//					}
//					bundleKey=apparatus.getCustomizerClassLocationBundleKey();
//					if(!isDefined(bundleKey)) {
//						System.out.println("***** "+bundleKey +" is not defined... Ooops!");
//					}
//
//					
//					boolean mustPersist=false;
//					
//					String resourceIfied = apparatus.getDescriptionStringBundleKey();
//					String propertyName = "description";
//					
//					
//					mustPersist = mustPersist || correctReCFaceConfigI18NProperty(apparatusMarshaller, recFaceConfigFile, apparatus, bundleName,
//							messagesFile, messagesFileEn, messagesFilePt, resourceIfied, propertyName);
//
//					resourceIfied = apparatus.getDisplayStringBundleKey();
//					propertyName = "title";
//					
//					mustPersist = mustPersist || correctReCFaceConfigI18NProperty(apparatusMarshaller, recFaceConfigFile, apparatus, bundleName,
//							messagesFile, messagesFileEn, messagesFilePt, resourceIfied, propertyName);
//
//					resourceIfied = apparatus.getIconLocationBundleKey();
//					propertyName = "icon";
//
//					mustPersist = mustPersist || correctReCFaceConfigI18NProperty(apparatusMarshaller, recFaceConfigFile, apparatus, bundleName,
//							messagesFile, messagesFileEn, messagesFilePt, resourceIfied, propertyName);
//					
//					resourceIfied = apparatus.getToolTipBundleKey();
//					propertyName = "tip";
//					
//					mustPersist = mustPersist || correctReCFaceConfigI18NProperty(apparatusMarshaller, recFaceConfigFile, apparatus, bundleName,
//							messagesFile, messagesFileEn, messagesFilePt, resourceIfied, propertyName);
//
//					resourceIfied = apparatus.getDesktopLocationBundleKey();
//					propertyName = "background";
//
//					mustPersist = mustPersist || correctReCFaceConfigI18NProperty(apparatusMarshaller, recFaceConfigFile, apparatus, bundleName,
//							messagesFile, messagesFileEn, messagesFilePt, resourceIfied, propertyName);
//					
//					
//					if(isEmpty(apparatus.getDisplayFactoryClassLocationBundleKey())) {
//						apparatus.setDisplayFactoryClassLocationBundleKey(null);
//						mustPersist=true;
//					}
//					if(isEmpty(apparatus.getDataModelClassLocationBundleKey())) {
//						apparatus.setDataModelClassLocationBundleKey(null);
//						mustPersist=true;
//					}
//					if(isEmpty(apparatus.getHeaderDisplayClassLocationBundleKey())) {
//						apparatus.setHeaderDisplayClassLocationBundleKey(null);
//						mustPersist=true;
//					}
//					
//					if(mustPersist) {
//						apparatusMarshaller.marshal(apparatus, recFaceConfigFile);
//					}
//					
//					System.out.println("/////////////// " + apparatus.getLocation() + " /////////////");
//
//					System.out.println("");
//					System.out.println("");

					File hardwareInfoFile=locateHardwareInfoFile(experimentDir);
					
					System.out.println("Reading hardware info file from: "+hardwareInfoFile.getAbsolutePath());
					
					HardwareInfo hardwareInfo = HardwareInfoXMLReader.readHardwareInfo(hardwareInfoFile.getAbsolutePath());

					String hardwareUniqueId=hardwareInfo.getHardwareUniqueID();
					if(!hardwareUniqueId.equals(apparatus.getLocation())) {
						System.out.println("**** Aparattus location '"+apparatus.getLocation()+"' in ReCFaceConfig.xml does not match the HardwareInfo hardware unique id '"+hardwareUniqueId+"' for experiment '"+experimentName+"'");
						System.out.println("Files are:");
						System.out.println("\t"+hardwareInfoFile.getAbsolutePath());
						System.out.println("\t"+recFaceConfigFile.getAbsolutePath());
					}
					
					String description=hardwareInfo.getDescriptionText();
					String familiarName=hardwareInfo.getFamiliarName();
					String hardwareName=hardwareInfo.getHardwareName();
					
					if(!isDefined(description)) {
						System.out.println("description for hardware "+description);
					}
					
					for (ChannelParameter parameter : hardwareInfo.getHardwareParameters()) {
						String parameterName=parameter.getParameterName();
						if(!isDefined(parameterName))
						{
							System.out.println(parameterName + " is not i18n");
							System.out.println(experimentName);
						}
					}	
					
					apparatusMarshaller.marshal(apparatus, recFaceConfigFile);
					
					
				} catch (Exception e) {
					System.out.println("Unable to read apparatus config: " + recFaceConfigFile.getAbsolutePath());
					e.printStackTrace();
					break;
				}
				
				
				
			}//for each experimentDir

		}

		

		
		
		
		

		
		

	}

	/**
	 * @param experimentDir
	 * @return
	 */
	private static File locateHardwareInfoFile(File dir) {
		File hardwareInfoFile=new File(dir,"HardwareInfo.xml");
		if(hardwareInfoFile.exists()) {
			return hardwareInfoFile;
		}
		else {
			File[] childDirs=dir.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File pathname) {
					return pathname.isDirectory();
				}
			});
			
			if(childDirs.length>0 && childDirs.length>0) {
				for (File subDir : childDirs) {
					File locateHardwareFile=locateHardwareInfoFile(subDir);
					if(locateHardwareFile!=null) {
						return locateHardwareFile;
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param apparatusMarshaller
	 * @param recFaceConfigFile
	 * @param apparatus
	 * @param bundleName
	 * @param messagesFile
	 * @param messagesFileEn
	 * @param messagesFilePt
	 * @param resourceIfied
	 * @param propertyName
	 * @throws FileNotFoundException
	 * @throws JAXBException
	 */
	private static boolean correctReCFaceConfigI18NProperty(Marshaller apparatusMarshaller, File recFaceConfigFile,
			Apparatus apparatus, String bundleName, File messagesFile, File messagesFileEn, File messagesFilePt,
			String resourceIfied, String propertyName) throws FileNotFoundException, JAXBException {
		if (isEmpty(resourceIfied)) {
			String bundleKey = constructBundleKey(propertyName, bundleName);
			if (!isDefined(bundleKey)) {
				appendToResourceBundles(bundleKey, null, messagesFile, messagesFileEn, messagesFilePt);
			}
			
			apparatus.setDescriptionStringBundleKey(bundleKey);
			return true;

		} else if (isBundleKey(resourceIfied)) {
			if (!isDefined(resourceIfied)) {
				appendToResourceBundles(resourceIfied, null, messagesFile, messagesFileEn, messagesFilePt);
			}
			
		} else {
			String bundleKey = constructBundleKey(propertyName, bundleName);
			appendToResourceBundles(bundleKey, resourceIfied, messagesFile, messagesFileEn, messagesFilePt);
			// Now replace the bundleKey on the recFaceConfig file
			apparatus.setDescriptionStringBundleKey(bundleKey);
			return true;
		}
		return false;
	}

	/**
	 * @param messagesFile
	 * @param messagesFileEn
	 * @throws IOException
	 */
	private static void copyFile(File messagesFile, File messagesFileEn) throws IOException {
		FileInputStream ios = new FileInputStream(messagesFile);
		FileOutputStream fos = new FileOutputStream(messagesFileEn);
		int readValue = -1;
		while ((readValue = ios.read()) != -1) {
			fos.write(readValue);
		}
		fos.flush();
		ios.close();
		fos.close();

	}

	private static final boolean isEmpty(String x) {
		return x == null || x.trim().length() == 0;
	}

	private static final boolean isBundleKey(String x) {
		return !isEmpty(x) && x.contains("$");
	}

	private static final String constructBundleKey(String property, String bundle) {
		return bundle + "$rec.exp." + property + "." + bundle;
	}

	private static final boolean isDefined(String bundleKey) {
		try {
			String retVal = ReCResourceBundle.findString(bundleKey);
			return !isEmpty(retVal);
		} catch (Exception e) {
			return false;
		}
	}

	private static final void appendToResourceBundles(String bundleKey, String value, File... messagesFiles)
			throws FileNotFoundException {
		for (File messageFile : messagesFiles) {
			PrintStream printStream = new PrintStream(new FileOutputStream(messageFile, true));
			printStream.println("#TODO-I18N - Define the property correctly!");
			printStream.println(bundleKey.substring(bundleKey.indexOf("$") + 1) + "="
					+ (isEmpty(value) ? "A definir" : value));
			printStream.flush();
			printStream.close();
		}
	}

}
