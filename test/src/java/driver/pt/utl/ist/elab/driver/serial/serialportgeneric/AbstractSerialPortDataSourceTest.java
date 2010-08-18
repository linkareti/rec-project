/* 
 * AbstractSerialPortDataSourceTest.java created on 18 Aug 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric;


import java.io.File;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.HardwareNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment.GenericSerialPortDataSource;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.Scale;

/**
 * 
 * @author npadriano
 */
public class AbstractSerialPortDataSourceTest {
	
	private static final String RS232_CONFIG_FILE_PATH = "experiments/optica/etc/Rs232Config.xml";
	private static final int CHANNELS_CONFIG_LENGTH = 1;
	
	private AbstractSerialPortDataSource dataSource = new GenericSerialPortDataSource();
	private HardwareNode rs232configs;
	
	/**
	 * Creates the <code>AbstractSerialPortDataSourceTest</code>.
	 */
	public AbstractSerialPortDataSourceTest() {
		rs232configs = loadRs232Configs(RS232_CONFIG_FILE_PATH);
		dataSource.setRs232configs(rs232configs);
		dataSource.setAcquisitionHeader(getHardwareAcquisitionConfig());
	}
	
	@SuppressWarnings("unchecked")
	private static HardwareNode loadRs232Configs(String file) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance("pt.utl.ist.elab.driver.serial.serialportgeneric.config");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<HardwareNode> jaxbElement = (JAXBElement<HardwareNode>) unmarshaller.unmarshal(new File(file));
			return (HardwareNode) jaxbElement.getValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static HardwareAcquisitionConfig getHardwareAcquisitionConfig() {
		HardwareAcquisitionConfig aquisitionHeader = new HardwareAcquisitionConfig();
		ChannelAcquisitionConfig[] channelsConfig = new ChannelAcquisitionConfig[CHANNELS_CONFIG_LENGTH];
		for (int i = 0; i < channelsConfig.length; i++) {
			ChannelAcquisitionConfig cConfig = new ChannelAcquisitionConfig();
			Scale scale = new Scale();
			PhysicsVal defaultError = new PhysicsVal();
			scale.setDefaultErrorValue(defaultError);
			cConfig.setSelectedScale(scale);
			channelsConfig[i] = cConfig;
		}
		aquisitionHeader.setChannelsConfig(channelsConfig);
		return aquisitionHeader;
	}
	
	private static SerialPortCommand createDataCommand(String command) {
		SerialPortCommand cmd = new SerialPortCommand(SerialPortCommandList.DAT.toString());
		cmd.setData(true);
		cmd.setCommand(command);
		return cmd;
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 
	 */
	@Test
	public void testChannels() {
		System.out.println("testChannels");
		
		SerialPortCommand cmd = null;
		PhysicsValue[] value = null;
		
		// beware of CHANNELS_CONFIG_LENGTH
		cmd = createDataCommand("10	1");
		value = dataSource.processDataCommand(cmd);
		System.out.println("Value = " + Arrays.deepToString(value));
		
		Assert.assertNotNull(value);
		Assert.assertTrue(value.length == CHANNELS_CONFIG_LENGTH + 1);
		Assert.assertTrue(value[0].getValue().getIntValue() == 19);
		Assert.assertTrue(value[1].getValue().getIntValue() == 1);
		System.out.println("OK");
	}

}
