/* 
 * SerialPortConfig.java created on 2009/10/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import pt.utl.ist.elab.driver.serial.serialportgeneric.config.ErrorsNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.HardwareNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneErrorNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneParameterNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneTimeoutNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.ParametersNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.Rs232Node;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.TransferFunctionNode;
import pt.utl.ist.elab.driver.serial.serialportgeneric.rs232.SerialPortRs232Channel;
import pt.utl.ist.elab.driver.serial.serialportgeneric.rs232.SerialPortRs232Parameter;
import pt.utl.ist.elab.driver.serial.serialportgeneric.rs232.TransferFunction;

/**
 * 
 * @author fdias
 */
public class SerialPortConfig {

	private static SerialPortConfig instance;
	
	private String hardwareId = null;
	private Integer hardwareNumChannels = null;
	private Integer hardwareMinFrequency = null;
	private Integer hardwareMaxFrequency = null;
	
	private Integer rs232Baud = null;
	private Integer rs232StopBits = null;
	private Integer rs232ParityBits = null;
	private Integer rs232NumBts = null;
	private List<String> rs232PortsRetrict = new ArrayList<String>();
	
	private List<SerialPortRs232Parameter> parametersList = new ArrayList<SerialPortRs232Parameter>();
	private List<SerialPortRs232Channel> channelsList = new ArrayList<SerialPortRs232Channel>();
	
	private HardwareNode hardwareNode = null;

	public HardwareNode getHardwareNode() {
		return hardwareNode;
	}

	private SerialPortConfig() throws Exception {
		
		JAXBContext jaxbContext = JAXBContext.newInstance("generated");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<HardwareNode> jaxbElement = (JAXBElement<HardwareNode>) unmarshaller.unmarshal(new File("rs232config.xml"));
		
		HardwareNode hardwareNode = (HardwareNode) jaxbElement.getValue();
		
		hardwareId = hardwareNode.getId();
		hardwareNumChannels = Integer.getInteger(hardwareNode.getNumChannels());
		hardwareMinFrequency = Integer.getInteger(hardwareNode.getMinfrequency().toLowerCase().replace("hz", ""));
		hardwareMaxFrequency = Integer.getInteger(hardwareNode.getMaxfrequency().toLowerCase().replace("hz", ""));
		
		Rs232Node rs232Node = hardwareNode.getRs232();

		rs232Baud = rs232Node.getBaud().intValue();
		rs232StopBits = rs232Node.getStopbits().intValue();
		rs232ParityBits = rs232Node.getParitybits().intValue();
		rs232NumBts = rs232Node.getNumbits().intValue();
		rs232PortsRetrict = Arrays.asList(rs232Node.getPortsRestrict().split(","));
		
		ParametersNode parametersNode = rs232Node.getParameters();
		
		List<OneParameterNode> listOneParameterNode = parametersNode != null ? parametersNode.getParameter() : null;
		
		if (listOneParameterNode != null) {
			for (OneParameterNode oneParameterNode : listOneParameterNode) {
				SerialPortRs232Parameter sprp = new SerialPortRs232Parameter(oneParameterNode.getInput(), oneParameterNode.getOutput(), oneParameterNode.getMaxvalue(), oneParameterNode.getMinvalue(), oneParameterNode.getOrder().intValue());
				List<TransferFunctionNode> transferFunctionNode = oneParameterNode.getTransferFunction();
				parametersList.add(sprp);
			}
		}
		
		if (rs232Node.getTimeout() != null) {
			OneTimeoutNode defaultTimeout = rs232Node.getTimeout().getDefaultTimeout();
			OneTimeoutNode idTimeout = rs232Node.getTimeout().getId();
			OneTimeoutNode cfgTimeout = rs232Node.getTimeout().getCfg();
			OneTimeoutNode curTimout = rs232Node.getTimeout().getCur();
			OneTimeoutNode strTimeout = rs232Node.getTimeout().getStr();
			OneTimeoutNode datBinTmeout = rs232Node.getTimeout().getDatBin();
			OneTimeoutNode datNoData = rs232Node.getTimeout().getDatNoData();
			OneTimeoutNode binNoData = rs232Node.getTimeout().getBinNoData();
			OneTimeoutNode stpTimeout = rs232Node.getTimeout().getStp();
			OneTimeoutNode rstTimeout = rs232Node.getTimeout().getRst();
			OneTimeoutNode hardwareDiedTimeout = rs232Node.getTimeout().getHardwareDied();
		}
		
		ErrorsNode errorsNode = rs232Node.getErrors();
		
		List<OneErrorNode> listOneErrorNode = errorsNode != null ? errorsNode.getError() : null;
		
		if (listOneErrorNode != null) {
			for (OneErrorNode oneErrorNode : listOneErrorNode) {
				
			}
		}
		
	}

	public static SerialPortConfig getInstance() throws Exception {
		if (null == instance) {
			instance = new SerialPortConfig();
		}
		return instance;
	}
	
}
