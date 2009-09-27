/*
 * PrintComponent.java
 *
 * Created on 13 de Dezembro de 2002, 8:39
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class PrintComponent implements java.awt.print.Printable {
	private java.awt.Component comp;
	private javax.swing.JTable table;
	private boolean isTable = false;

	/** Creates a new instance of PrintComponent */
	public PrintComponent(java.awt.Component comp) {
		this.comp = comp;
		print();
	}

	public PrintComponent(javax.swing.JTable table) {
		this.table = table;
		isTable = true;
		print();
	}

	public void print() {
		java.awt.print.PrinterJob printJob = java.awt.print.PrinterJob.getPrinterJob();
		/******************************************************************************
		 *BIG PROBLEM.... O utilizador tem de escolher a orientacao da pagina
		 * no primeiro painel que eu lhe dou ou nao funciona....JAVA BUG:
		 * 4311283
		 *******************************************************************************/
		java.awt.print.PageFormat pageFormat = new java.awt.print.PageFormat();
		if (printJob.pageDialog(printJob.defaultPage()).getOrientation() == pageFormat.LANDSCAPE) {
			try {
				pageFormat.setOrientation(pageFormat.LANDSCAPE);
			} catch (java.lang.IllegalStateException ise) {
				System.out.println("Print error...\n" + ise);
			}
		}
		printJob.setPrintable(this, pageFormat);
		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (java.awt.print.PrinterException pe) {
				System.out.println("Could not print...\n" + pe);
			}
		}
	}

	public int print(java.awt.Graphics g, java.awt.print.PageFormat pageFormat, int pageIndex) {
		java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		int fontHeight = g2.getFontMetrics().getHeight();
		int fontDesent = g2.getFontMetrics().getDescent();

		// deixar espaco para o numero da pagina
		double pageHeight = pageFormat.getImageableHeight() - fontHeight;
		double pageWidth = pageFormat.getImageableWidth();

		if (isTable) {
			double tableWidth = (double) table.getColumnModel().getTotalColumnWidth();
			double scale = 1;
			if (tableWidth >= pageWidth) {
				scale = pageWidth / tableWidth;
			}

			double headerHeightOnPage = table.getTableHeader().getHeight() * scale;
			double tableWidthOnPage = tableWidth * scale;
			double oneRowHeight = (table.getRowHeight() + table.getRowMargin()) * scale;
			int numRowsOnAPage = (int) ((pageHeight - headerHeightOnPage) / oneRowHeight);
			double pageHeightForTable = oneRowHeight * numRowsOnAPage;
			int totalNumPages = (int) Math.ceil(((double) table.getRowCount()) / numRowsOnAPage);

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
				int lastRowPrinted = numRowsOnAPage * pageIndex;
				int numRowsLeft = table.getRowCount() - lastRowPrinted;
				g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage), (int) Math
						.ceil(oneRowHeight * numRowsLeft));
			}
			// else clip to the entire area available.
			else {
				g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage), (int) Math
						.ceil(pageHeightForTable));
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
