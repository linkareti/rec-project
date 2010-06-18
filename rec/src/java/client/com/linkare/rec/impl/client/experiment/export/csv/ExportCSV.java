/* 
 * ExpDataSaveCSV.java created on 11 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment.export.csv;

import java.io.StringWriter;

import javax.swing.table.DefaultTableModel;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVStrategy;

import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.MultSeriesTableModelProxy;

/**
 * Class to save the experiment table data in a CVS file.
 * 
 * @author npadriano
 */
public class ExportCSV {
	
	/** CSV extention file */
	public static final String CSV_EXTENTION_FILE = "csv";

	/** Default strategy */
	public static final CSVStrategy DEFAULT_STRATEGY = CSVStrategy.DEFAULT_STRATEGY;

	/** Excel strategy (file formated to be opened with Excel) */
	public static final CSVStrategy EXCEL_STRATEGY = CSVStrategy.EXCEL_STRATEGY;

	/** Tabed strategy */
	public static final CSVStrategy TDF_STRATEGY = CSVStrategy.TDF_STRATEGY;
	
	
	/**
	 * Static Class
	 */
	private ExportCSV() {
		throw new UnsupportedOperationException("Static class!");
	}


	/**
	 * Format data in CSV using excel strategy.
	 * 
	 * @param model model with the data
	 * @return csv file content
	 */
	public static String print(CSVModel model) {
		return print(model, EXCEL_STRATEGY);
	}
	
	/**
	 * Format data in CSV using excel strategy.
	 * 
	 * @param model model with the data
	 * @return csv file content
	 */
	public static String print(ExpDataModel model) {
		return print(new CSVExpDataModel(model));
	}
	
	/**
	 * Format data in CSV using excel strategy.
	 * 
	 * @param model model with the data
	 * @return csv file content
	 */
	public static String print(MultSeriesTableModelProxy model) {
		return print(new CSVMultSeriesTableModelProxy(model));
	}
	
	/**
	 * Format data in CSV using excel strategy.
	 * 
	 * @param model model with the data
	 * @return csv file content
	 */
	public static String print(DefaultTableModel model) {
		return print(new CSVDefaultTableModelProxy(model));
	}
	
	/**
	 * Format data in CSV using the specified strategy.
	 * 
	 * @param model model with the data
	 * @param strategy strategy to use in the format
	 * @return csv file content
	 */
	public static String print(CSVModel model, CSVStrategy strategy) {
	    StringWriter sw = new StringWriter();
	    CSVPrinter printer = new CSVPrinter(sw);
	    printer.setStrategy(strategy);
		
	    // print the header with the columns name
		for (int headerCol = 0; headerCol < model.getColumnCount(); headerCol++) {
			printer.print(model.getColumnHeader(headerCol));
		}
		printer.println();
		
		// print the lines
		for (int row = 0; row < model.getRowCount(); row++) {
			for (int col = 0; col < model.getColumnCount(); col++) {
				printer.print(model.getCell(row, col));
			}
			printer.println();
		}
		
		return sw.toString();
	}
	
}
