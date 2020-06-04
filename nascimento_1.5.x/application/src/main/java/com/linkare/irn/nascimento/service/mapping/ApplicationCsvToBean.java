package com.linkare.irn.nascimento.service.mapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class ApplicationCsvToBean<T> extends CsvToBean<T> {

    @Override
    public List<T> parse(MappingStrategy<T> mapper, CSVReader csv) {
	try {
	    mapper.captureHeader(csv);
	    String[] line;
	    final List<T> list = new ArrayList<T>();
	    while (null != (line = csv.readNext())) {
		T obj = processLine(mapper, line);
		list.add(obj);
	    }
	    return list;
	} catch (IOException e) {
	    throw new RuntimeException("Error parsing CSV header!", e);
	} catch (Exception e) {
	    throw new RuntimeException("Error parsing CSV!", e);
	}
    }
}