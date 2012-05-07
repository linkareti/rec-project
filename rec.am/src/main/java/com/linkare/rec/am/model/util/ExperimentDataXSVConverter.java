package com.linkare.rec.am.model.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.linkare.rec.am.repository.ByteArrayValueDTO;
import com.linkare.rec.am.repository.ChannelAcquisitionConfigDTO;
import com.linkare.rec.am.repository.DataProducerDTO;
import com.linkare.rec.am.repository.MultiplierEnum;
import com.linkare.rec.am.repository.ParameterConfigDTO;
import com.linkare.rec.am.repository.PhysicsValDTO;
import com.linkare.rec.am.repository.PhysicsValueDTO;
import com.linkare.rec.am.repository.PhysicsValueTypeEnum;
import com.linkare.rec.am.repository.RowPhysicsValueDTO;
import com.linkare.rec.am.repository.SamplesPacketDTO;

/**
 * Class used to convert information from a listing to a file with columns separated by any character (thus the X in XSV). This can be used to generate CSVs,
 * TSVs or any other type, depending of the given separator.
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
public class ExperimentDataXSVConverter {

    private static final String CR_LF = System.getProperty("line.separator");

    private final char SEPARATOR;
    private final String BINARY_FILENAME_PREFIX;

    /**
     * Binary files that are part of the XSV data and that cannot be represented as a table. Instead, they are represented only as a string, and the client of
     * this class should be responsible to call the method {@link #getReferencedBinaries()} and do whatever it needs with this information (generate the files
     * from their byte[], ignore them or something it requires). The key maps to the {@link String} value replaced in the corresponding cell of generated XSV
     * file.
     */
    private Map<String, ByteArrayValueDTO> referencedBinaries;

    public ExperimentDataXSVConverter(char separator, String namePrefix) {
	this.SEPARATOR = separator;
	this.BINARY_FILENAME_PREFIX = namePrefix;
    }

    public Map<String, ByteArrayValueDTO> getReferencedBinaries() {
	return referencedBinaries;
    }

    /**
     * Converts the output data of an experiment into a byte array in the format of a XSV file, depending on the given {@link #SEPARATOR}.
     * 
     * @param experienceData
     *            The method {@link DataProducerDTO#getSamplesPacketMatrix()} must return the data deserialized. The information about the channels should also
     *            be available.
     * @return
     * @throws IOException
     */
    public byte[] toByteArray(DataProducerDTO experienceData) throws IOException {

	ByteArrayOutputStream xsvFileOutputStream = new ByteArrayOutputStream();
	xsvFileOutputStream.write(getXsvHeader(experienceData).getBytes());

	for (SamplesPacketDTO dataSample : experienceData.getSamplesPacketMatrix()) {
	    for (RowPhysicsValueDTO row : dataSample.getData()) {
		String processedRow = processSampleRow(row, experienceData.getAcqHeader().getChannelsConfig());
		xsvFileOutputStream.write(processedRow.getBytes());
	    }
	}
	return xsvFileOutputStream.toByteArray();
    }

    /**
     * Separates a {@link RowPhysicsValueDTO} into a single line {@link String}, split by the current {@link #SEPARATOR} and ending with a line break.
     * 
     * @param row
     *            The data about the current row.
     * @param channelsConfig
     *            The list of channel configurations. This is necessary to make type conversions when the column unit is different of the cell unit.
     * @return Single line string ready to add to a XSV file.
     */
    private String processSampleRow(RowPhysicsValueDTO row, List<ChannelAcquisitionConfigDTO> channelsConfig) {
	StringBuilder sampleRow = new StringBuilder();
	//TODO get a fixed number of columns, to make sure ommited values are considered.
	for (int i = 0; i < row.getColumnValues().size(); i++) {
	    PhysicsValueDTO physicsValue = row.getColumnValues().get(i);
	    if (physicsValue != null) {
		MultiplierEnum columnMultiplier = channelsConfig.get(i).getScale().getMultiplier();
		PhysicsValueDTO translatedValue = getConvertedValue(physicsValue, columnMultiplier);
		if (translatedValue != null) {
		    sampleRow.append(translatedValue.getValue().getValue()).append(SEPARATOR).append(translatedValue.getError().getValue());
		}
	    }
	    sampleRow.append(SEPARATOR);
	}
	sampleRow.replace(sampleRow.length() - 1, sampleRow.length(), CR_LF);
	return sampleRow.toString();
    }

    /**
     * Creates the header of a XSV file. This means creating each column name, separated by {@link #SEPARATOR} and with a line break at the end.
     * 
     * @param experienceData
     * @return
     */
    private String getXsvHeader(DataProducerDTO experienceData) {
	StringBuilder sampleRow = new StringBuilder();
	for (ParameterConfigDTO parameter : experienceData.getAcqHeader().getHardwareParameters()) {
	    sampleRow.append(parameter.getParameterName()).append(SEPARATOR).append("e-").append(parameter.getParameterName()).append(SEPARATOR);
	}
	sampleRow.replace(sampleRow.length() - 1, sampleRow.length(), CR_LF);
	return sampleRow.toString();
    }

    /**
     * If the given {@link PhysicsValueDTO} has a different {@link MultiplierEnum} than the one given, creates a new {@link PhysicsValueDTO} converted to the
     * right type.
     * 
     * @param physicsValue
     *            The value and error to be converted.
     * @param columnMultiplier
     *            The multiplier for this column, to assure that all cells in this column have the same unit.
     * @return
     */
    private PhysicsValueDTO getConvertedValue(final PhysicsValueDTO physicsValue, MultiplierEnum columnMultiplier) {

	if (physicsValue.getValue().getValueType() == PhysicsValueTypeEnum.BYTEARRAY_VAL) {
	    ByteArrayValueDTO byteArrayValue = (ByteArrayValueDTO) physicsValue.getValue().getValue();
	    //TODO the format should be xpto_001 and not xpto_1.
	    referencedBinaries.put(BINARY_FILENAME_PREFIX + referencedBinaries.size(), byteArrayValue);
	    return null;
	}

	if (physicsValue.getValue().getValueType() == PhysicsValueTypeEnum.BOOLEAN_VAL || physicsValue.getAppliedMultiplier() == columnMultiplier) {
	    return physicsValue;
	}

	PhysicsValueDTO converted = new PhysicsValueDTO();
	converted.setAppliedMultiplier(columnMultiplier);

	PhysicsValDTO newValue = convertNumericValue(physicsValue.getValue(), physicsValue.getAppliedMultiplier(), columnMultiplier);
	converted.setValue(newValue);

	//XXX is the error in a different unit?
	PhysicsValDTO newError = convertNumericValue(physicsValue.getError(), physicsValue.getAppliedMultiplier(), columnMultiplier);
	converted.setError(newError);

	return converted;
    }

    /**
     * Converts a given {@link PhysicsValDTO} represented in a given base {@link MultiplierEnum} to another {@link MultiplierEnum}. As an example, if the given
     * value is in {@link MultiplierEnum#MILI} and is necessary to convert to {@link MultiplierEnum#MICRO}, the decimal separator is moved left three digits.
     * 
     * @param baseValue
     *            The value to convert.
     * @param baseMultiplier
     *            The current multiplier of the baseValue.
     * @param targetMultiplier
     *            The multiplier to which the value should be converted.
     * @return The new numeric value, always as double, since it's the only type that guarantees that any exponential number can be represented.
     * @throws IllegalArgumentException
     *             If the valuetype is {@link PhysicsValueTypeEnum#BYTEARRAY_VAL} or {@link PhysicsValueTypeEnum#BOOLEAN_VAL}.
     */
    private PhysicsValDTO convertNumericValue(final PhysicsValDTO baseValue, MultiplierEnum baseMultiplier, MultiplierEnum targetMultiplier) {

	if (baseValue.getValueType() == PhysicsValueTypeEnum.BOOLEAN_VAL || baseValue.getValueType() == PhysicsValueTypeEnum.BYTEARRAY_VAL) {
	    throw new IllegalArgumentException("The baseValue is not of a numeric type");
	}

	int multiplierDifference = targetMultiplier.getCode() - baseMultiplier.getCode();
	double newValue = ((Number) baseValue.getValue()).doubleValue() * Math.pow(10, multiplierDifference * 3);

	PhysicsValDTO valueDTO = new PhysicsValDTO();
	valueDTO.setValueType(baseValue.getValueType());
	valueDTO.setValue(newValue);

	return valueDTO;
    }
    //TODO create an actual file in the filesystem.
}