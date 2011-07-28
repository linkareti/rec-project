/*
 * PrintComponent.java
 *
 * Created on 13 de Dezembro de 2002, 8:39
 */

package pt.utl.ist.elab.client.webrobot.utils;

import java.awt.print.PageFormat;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class PrintComponent implements java.awt.print.Printable {
	private java.awt.Component comp;
	private javax.swing.JTable table;
	private boolean isTable = false;

	/** Creates a new instance of PrintComponent */
	public PrintComponent(final java.awt.Component comp) {
		this.comp = comp;
		print();
	}

	public PrintComponent(final javax.swing.JTable table) {
		this.table = table;
		isTable = true;
		print();
	}

	public void print() {
		final java.awt.print.PrinterJob printJob = java.awt.print.PrinterJob.getPrinterJob();
		/******************************************************************************
		 * BIG PROBLEM.... O utilizador tem de escolher a orientacao da pagina
		 * no primeiro painel que eu lhe dou ou nao funciona....JAVA BUG:
		 * 4311283
		 *******************************************************************************/
		final java.awt.print.PageFormat pageFormat = new java.awt.print.PageFormat();
		if (printJob.pageDialog(printJob.defaultPage()).getOrientation() == PageFormat.LANDSCAPE) {
			try {
				pageFormat.setOrientation(PageFormat.LANDSCAPE);
			} catch (final java.lang.IllegalStateException ise) {
				System.out.println("Print error...\n" + ise);
			}
		}
		printJob.setPrintable(this, pageFormat);
		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (final java.awt.print.PrinterException pe) {
				System.out.println("Could not print...\n" + pe);
			}
		}
	}

	@Override
	public int print(final java.awt.Graphics g, final java.awt.print.PageFormat pageFormat, final int pageIndex) {
		final java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		final int fontHeight = g2.getFontMetrics().getHeight();
		final int fontDesent = g2.getFontMetrics().getDescent();

		// deixar espaco para o numero da pagina
		final double pageHeight = pageFormat.getImageableHeight() - fontHeight;
		final double pageWidth = pageFormat.getImageableWidth();

		if (isTable) {
			final double tableWidth = table.getColumnModel().getTotalColumnWidth();
			double scale = 1;
			if (tableWidth >= pageWidth) {
				scale = pageWidth / tableWidth;
			}

			final double headerHeightOnPage = table.getTableHeader().getHeight() * scale;
			final double tableWidthOnPage = tableWidth * scale;
			final double oneRowHeight = (table.getRowHeight() + table.getRowMargin()) * scale;
			final int numRowsOnAPage = (int) ((pageHeight - headerHeightOnPage) / oneRowHeight);
			final double pageHeightForTable = oneRowHeight * numRowsOnAPage;
			final int totalNumPages = (int) Math.ceil(((double) table.getRowCount()) / numRowsOnAPage);

			if (pageIndex >= totalNumPages) {
				return (java.awt.print.Printable.NO_SUCH_PAGE);
			}

			g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

			// bottom center
			g2.drawString("Pagina: " + (pageIndex + 1), (int) pageWidth / 2 - 35,
					(int) (pageHeight + fontHeight - fontDesent));

			g2.translate(0f, headerHeightOnPage);
			g2.translate(0f, -pageIndex * pageHeightForTable);

			// If this piece of the table is smaller
			// than the size available,
			// clip to the appropriate bounds.
			if (pageIndex + 1 == totalNumPages) {
				final int lastRowPrinted = numRowsOnAPage * pageIndex;
				final int numRowsLeft = table.getRowCount() - lastRowPrinted;
				g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage),
						(int) Math.ceil(oneRowHeight * numRowsLeft));
			}
			// else clip to the entire area available.
			else {
				g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage),
						(int) Math.ceil(pageHeightForTable));
			}

			g2.scale(scale, scale);
			table.paint(g2);
			g2.scale(1 / scale, 1 / scale);
			g2.translate(0f, pageIndex * pageHeightForTable);
			g2.translate(0f, -headerHeightOnPage);
			g2.setClip(0, 0, (int) Math.ceil(tableWidthOnPage), (int) Math.ceil(headerHeightOnPage));
			g2.scale(scale, scale);
			// paint header at top
			table.getTableHeader().paint(g2);
			return (java.awt.print.Printable.PAGE_EXISTS);
		} else {
			if (pageIndex > 0) {
				return (java.awt.print.Printable.NO_SUCH_PAGE);
			} else {
				g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				comp.paint(g2);
				return (java.awt.print.Printable.PAGE_EXISTS);
			}
		}
	}
}
