/*
 * Laffy - Swing Look and Feel Sampler
 * Copyright (C) Sun Microsystems
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package org.jdesktop.laffy;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;

/**
 * PagePrinter - Helper class for printing a page
 * 
 * @author Created by Jasper Potts (Jan 9, 2008)
 * @version 1.0
 */
public class PagePrinter {
	/** Print a page using Java 1.2 API */
	public static void printPage(Page page) {
		new PrintUtilities(page).print();
	}

	/** Print a page using Java 1.1 API */
	public static void printPageOld(Page page) {
		PrintJob printJob = Toolkit.getDefaultToolkit().getPrintJob(Laffy.getInstance().getFrame(),
				I18nResourceHandler.getMessage("Laffy_1.1_Printing_Test"), null, null);
		if (printJob != null) {
			Graphics pg = printJob.getGraphics();
			if (pg != null) {
				page.printAll(pg);
				pg.dispose();
			}
			printJob.end();
		}
	}

	public static class PrintUtilities implements Printable {
		private Component componentToBePrinted;

		public PrintUtilities(Component componentToBePrinted) {
			this.componentToBePrinted = componentToBePrinted;
		}

		public void print() {
			PrinterJob printJob = PrinterJob.getPrinterJob();
			printJob.setPrintable(this);
			if (printJob.printDialog())
				try {
					printJob.print();
				} catch (PrinterException pe) {
					System.out.println(I18nResourceHandler.getMessage("Error_printing") + pe);
				}
		}

		public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
			if (pageIndex > 0) {
				return (NO_SUCH_PAGE);
			} else {
				Graphics2D g2d = (Graphics2D) g;
				g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				disableDoubleBuffering(componentToBePrinted);
				componentToBePrinted.paint(g2d);
				enableDoubleBuffering(componentToBePrinted);
				return (PAGE_EXISTS);
			}
		}

		public void disableDoubleBuffering(Component c) {
			RepaintManager currentManager = RepaintManager.currentManager(c);
			currentManager.setDoubleBufferingEnabled(false);
		}

		public void enableDoubleBuffering(Component c) {
			RepaintManager currentManager = RepaintManager.currentManager(c);
			currentManager.setDoubleBufferingEnabled(true);
		}
	}
}
