/* 
 * ExpDataSaveCSV.java created on 11 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment;

import java.io.StringWriter;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVStrategy;

/**
 * Class to save the experiment table data in a CVS file.
 * 
 * @author npadriano
 */
public class ExpDataSaveCSV {
	
	/** CSV extention file */
	public static final String CSV_EXTENTION_FILE = "csv";

	/** Default strategy */
	public static final CSVStrategy DEFAULT_STRATEGY = CSVStrategy.DEFAULT_STRATEGY;

	/** Excel strategy (file formated to be opened with Excel) */
	public static final CSVStrategy EXCEL_STRATEGY = CSVStrategy.EXCEL_STRATEGY;

	/** Tabed strategy */
	public static final CSVStrategy TDF_STRATEGY = CSVStrategy.TDF_STRATEGY;
	
	
	/**
	 * Format data in CSV using excel strategy.
	 * 
	 * @param expDataModel model with the data
	 * @return csv file content
	 */
	public static String print(ExpDataModel expDataModel) {
		return print(expDataModel, EXCEL_STRATEGY);
	}
	
	/**
	 * Format data in CSV using the specified strategy.
	 * 
	 * @param expDataModel model with the data
	 * @param strategy strategy to use in the format
	 * @return csv file content
	 */
	public static String print(ExpDataModel expDataModel, CSVStrategy strategy) {
	    StringWriter sw = new StringWriter();
	    CSVPrinter printer = new CSVPrinter(sw);
	    printer.setStrategy(strategy);
		
	    // print the header with the columns name
		for (int headerCol = 0; headerCol < expDataModel.getChannelCount(); headerCol++) {
			printer.print(expDataModel.getChannelName(headerCol));
		}
		printer.println();
		
		// print the lines
		for (int row = 0; row < expDataModel.getTotalSamples(); row++) {
			for (int col = 0; col < expDataModel.getChannelCount(); col++) {
				printer.print(expDataModel.getValueAt(row, col).getValue().toString());
			}
			printer.println();
		}
		
		return sw.toString();
	}

}
