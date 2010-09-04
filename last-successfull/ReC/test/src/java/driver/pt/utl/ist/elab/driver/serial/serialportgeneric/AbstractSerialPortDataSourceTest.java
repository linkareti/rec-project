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
	private static final int CHANNELS_CONFIG_LENGTH = 7;
	
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
//			PhysicsVal defaultError = new PhysicsVal();
//			scale.setDefaultErrorValue(defaultError);
			scale.setDefaultErrorValue(null);
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
		String cmdStr = "10	3	5	20	2	15	123	1";
		cmd = createDataCommand(cmdStr);
		value = dataSource.processDataCommand(cmd);
		System.out.println("Cmd ["+cmdStr+"] Value " + Arrays.deepToString(value));
		
		Assert.assertNotNull(value);
		Assert.assertTrue(value.length == CHANNELS_CONFIG_LENGTH + 1);
		
		Assert.assertTrue(value[0].getValue().getDoubleValue() == 19);
		Assert.assertTrue(value[1].getValue().getDoubleValue() == 12);
		Assert.assertTrue(value[2].getValue().getDoubleValue() > 148 && value[2].getValue().getDoubleValue() < 149); // TODO confirm result value
		Assert.assertTrue(value[3].getValue().getDoubleValue() > 13 && value[3].getValue().getDoubleValue() < 14);
		Assert.assertTrue(value[4].getValue().getDoubleValue() > 75 && value[4].getValue().getDoubleValue() < 76);
		Assert.assertTrue(value[5].getValue().getDoubleValue() > 1.6 && value[5].getValue().getDoubleValue() < 1.7);
		Assert.assertTrue(value[6].getValue().getDoubleValue() == 123);
		
		Assert.assertTrue(value[CHANNELS_CONFIG_LENGTH].getValue().getLongValue() == 1);
		System.out.println("OK");
	}

}
