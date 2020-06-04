package com.linkare.irn.nascimento.service.mapping;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.HeaderColumnNameMappingStrategy;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ApplicationMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T> {

    private Map<String, String> columnMapping = new LinkedHashMap<String, String>();

    @Override
    protected String getColumnName(int col) {
	return col < header.length ? columnMapping.get(header[col]) : null;
    }

    public Map<String, String> getColumnMapping() {
	return columnMapping;
    }

    public void setColumnMapping(Map<String, String> columnMapping) {
	for (String key : columnMapping.keySet()) {
	    this.columnMapping.put(key, columnMapping.get(key));
	}
    }

    @Override
    public void captureHeader(CSVReader reader) throws IOException {
	header = reader.readNext();
	if (header.length != getColumnMapping().size()) {
	    throw new IOException("The number of columns in the header " + header.length + " whose values are " + Arrays.toString(header)
		    + " does not match the expectd number of " + getColumnMapping().size());
	} else {
	    int i = 0;
	    for (final Entry<String, String> entry : getColumnMapping().entrySet()) {
		final String expectedColumn = entry.getKey();
		final String actualColumn = header[i++];
		if (!expectedColumn.equalsIgnoreCase(actualColumn.trim())) {
		    throw new IOException("The actual header " + Arrays.toString(header) + " does not match the expected header "
			    + getColumnMapping().keySet().toString());
		}
	    }
	}
    }
}