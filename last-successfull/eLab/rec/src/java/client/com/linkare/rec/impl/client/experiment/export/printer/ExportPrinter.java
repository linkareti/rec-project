/* 
 * ExportPrinter.java created on 17 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.client.experiment.export.printer;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * 
 * @author npadriano
 */
public class ExportPrinter {

	/**
	 * Static Class
	 */
	private ExportPrinter() {
		throw new UnsupportedOperationException("Static class!");
	}

	/**
	 * @param painter
	 * @throws PrinterException
	 */
	public static void print(Printable painter) throws PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pf = job.defaultPage();
		pf.setOrientation(PageFormat.PORTRAIT);
		PageFormat pf2 = job.pageDialog(pf);
		if (pf2 != pf) {
			job.setPrintable(painter, pf2);
			if (job.printDialog()) {
				job.print();
			}
		}
	}

}
