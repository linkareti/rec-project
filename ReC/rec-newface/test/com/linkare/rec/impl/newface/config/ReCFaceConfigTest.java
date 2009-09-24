package com.linkare.rec.impl.newface.config;

import static org.junit.Assert.*;

import java.io.FileInputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.junit.Test;


public class ReCFaceConfigTest {

	private static final String XML_FILE = "etc/ReCFaceConfig.xml";

	@Test
	public void testReCFaceConfigUnmarshall() throws Exception {
		JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);
		Unmarshaller un = jc.createUnmarshaller();
		ReCFaceConfig result = (ReCFaceConfig) un.unmarshal(new FileInputStream(XML_FILE));
		
		assertNotNull(result);
		
		ReCFaceConfig unmarshall = ReCFaceConfig.unmarshall(new FileInputStream(XML_FILE));
		assertNotNull(unmarshall);
		
	}

}
