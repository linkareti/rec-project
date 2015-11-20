/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.web.repository;

import java.util.List;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class DataProducerDTO extends AbstractBaseDTO {

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	private HardwareAcquisitionConfigDTO acqHeader;

	private String dataProducerName;

	private String oid;

	private String user;

	private DataProducerStateEnum dataCollectorState;

	private List<SamplesPacketDTO> samplesPacketMatrix;

	private byte[] samplesPacketMatrixSerialized;

	public DataProducerDTO() {
	}

	public HardwareAcquisitionConfigDTO getAcqHeader() {
		return acqHeader;
	}

	public void setAcqHeader(HardwareAcquisitionConfigDTO acqHeader) {
		this.acqHeader = acqHeader;
	}

	public String getDataProducerName() {
		return dataProducerName;
	}

	public void setDataProducerName(String dataProducerName) {
		this.dataProducerName = dataProducerName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public List<SamplesPacketDTO> getSamplesPacketMatrix() {
		return samplesPacketMatrix;
	}

	public void setSamplesPacketMatrix(
			List<SamplesPacketDTO> samplesPacketMatrix) {
		this.samplesPacketMatrix = samplesPacketMatrix;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public byte[] getSamplesPacketMatrixSerialized() {
		return samplesPacketMatrixSerialized;
	}

	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public void setSamplesPacketMatrixSerialized(
			byte[] samplesPacketMatrixSerialized) {
		this.samplesPacketMatrixSerialized = samplesPacketMatrixSerialized;
	}

	public DataProducerStateEnum getDataCollectorState() {
		return dataCollectorState;
	}

	public void setDataCollectorState(DataProducerStateEnum dataCollectorState) {
		this.dataCollectorState = dataCollectorState;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DataProducerDTO [acqHeader=");
		builder.append(acqHeader);
		builder.append(", dataProducerName=");
		builder.append(dataProducerName);
		builder.append(", oid=");
		builder.append(oid);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

}