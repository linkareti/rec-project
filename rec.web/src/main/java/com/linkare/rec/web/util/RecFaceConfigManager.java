package com.linkare.rec.web.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import com.linkare.rec.web.config.ReCFaceConfig;

public class RecFaceConfigManager {
	
	private static final RecFaceConfigManager MANAGERINSTANCE = new RecFaceConfigManager();
	
	private ReCFaceConfig faceConfig;
	
	private void marshallFile(String recFaceConfigFilePath) throws FileNotFoundException, JAXBException {
		
		System.out.println("recFaceConfigFilePath="+recFaceConfigFilePath);
		faceConfig = ReCFaceConfig
				.unmarshall(new FileInputStream(recFaceConfigFilePath));
	}
	
	
	public static RecFaceConfigManager getInstance(String recFaceConfigFilePath) {
		return MANAGERINSTANCE;
	}
	
	public void addNewLabToRecFaceConfigFile(String recFaceConfigFilePath, String newRecFaceConfig) {
		
	}
	
	
	
	

}
