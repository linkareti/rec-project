/*
 * MeteoTable.java
 *
 * Created on May 12, 2004, 11:24 AM
 */

package pt.utl.ist.elab.client.meteo.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterGraphics;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;
import com.linkare.rec.impl.ui.table.ExcelAdapter;
import com.linkare.rec.impl.utils.ExtensionFilter;

/**
 * 
 * @author José Pedro Pereira - Linkare TI & André
 */
public class MeteoTable extends javax.swing.JPanel implements ExpDataDisplay, Printable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8961298577399250212L;
	private ExcelAdapter excelAdapter = null;

	/** Creates new form DefaultExperimentDataTable */
	public MeteoTable() {
		initComponents();
		setColArray(new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 });
		excelAdapter = new ExcelAdapter(dataTable);
		dataTable.setCellSelectionEnabled(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {
		defaultTableModelProxy = new MeteoTableModelProxy();
		toolBarTable = new javax.swing.JToolBar();
		printBtn = new javax.swing.JButton();
		saveBtn = new javax.swing.JButton();
		copyBtn = new javax.swing.JButton();
		selectAllBtn = new javax.swing.JButton();
		scrollPaneTable = new javax.swing.JScrollPane();
		dataTable = new javax.swing.JTable();

		defaultTableModelProxy.addTableModelListener(new javax.swing.event.TableModelListener() {
			@Override
			public void tableChanged(final javax.swing.event.TableModelEvent evt) {
				defaultTableModelProxyTableChanged(evt);
			}
		});

		toolBarTable.setRollover(true);
		printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/linkare/rec/impl/newface/resources/legacy/Print16.gif")));
		printBtn.setToolTipText("Print");
		printBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				printBtnActionPerformed(evt);
			}
		});

		toolBarTable.add(printBtn);

		saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/linkare/rec/impl/newface/resources/legacy/Save16.gif")));
		saveBtn.setToolTipText("Save csv file");
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				saveBtnActionPerformed(evt);
			}
		});
		toolBarTable.add(saveBtn);

		copyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/linkare/rec/impl/newface/resources/legacy/Copy16.gif")));
		copyBtn.setToolTipText("Copy to Clipboard in Excel Format");
		copyBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				copyBtnActionPerformed(evt);
			}
		});
		toolBarTable.add(copyBtn);
		selectAllBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/com/linkare/rec/impl/newface/resources/legacy/tableSelectAll16.gif")));
		selectAllBtn.setToolTipText("Select All");
		selectAllBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				selectAllBtnActionPerformed(evt);
			}
		});
		toolBarTable.add(selectAllBtn);

		setLayout(new java.awt.BorderLayout());
		dataTable.setModel(defaultTableModelProxy);
		dataTable.setAutoCreateColumnsFromModel(true);
		// dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPaneTable.setViewportView(dataTable);

		add(scrollPaneTable, java.awt.BorderLayout.CENTER);
	}

	private void selectAllBtnActionPerformed(final java.awt.event.ActionEvent evt) {
		dataTable.selectAll();
	}

	private void copyBtnActionPerformed(final java.awt.event.ActionEvent evt) {
		excelAdapter.copyToClipBoard();
	}

	private void saveBtnActionPerformed(final java.awt.event.ActionEvent evt) {
		final javax.swing.JFileChooser jFileChooserSave = new javax.swing.JFileChooser();

		final ExtensionFilter textExtension = new ExtensionFilter("csv", "ext");

		textExtension.setDescription("Comma separated values files");
		jFileChooserSave.setAcceptAllFileFilterUsed(false);
		jFileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
		jFileChooserSave.setFileFilter(textExtension);

		final int returnValue = jFileChooserSave.showSaveDialog(this);
		String extension = null;
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			extension = textExtension.getExtension();
		}
		String path = jFileChooserSave.getSelectedFile().getPath();
		if (path.endsWith(extension)) {
			path = path.substring(0, path.length() - 4);
		}
		final File saveFile = new File(path + "." + extension);
		saveTable(saveFile, false);
	}

	public void saveTable(final File saveFile, final boolean append) {
		try {
			final Writer fileWriter = new OutputStreamWriter(new FileOutputStream(saveFile, append));
			final String LS = System.getProperty("line.separator");
			final String COMMA = ",";
			final String QUOTE = "\"";
			// java.io.FileWriter fileWriter = new java.io.FileWriter(saveFile,
			// append);
			for (int headerCol = 0; headerCol < dataTable.getColumnCount(); headerCol++) {
				fileWriter.write(QUOTE + dataTable.getColumnName(headerCol) + QUOTE);
				fileWriter.write(COMMA);
			}
			fileWriter.write(LS);
			for (int row = 0; row < dataTable.getRowCount(); row++) {
				for (int col = 0; col < dataTable.getColumnCount(); col++) {
					new String();
					fileWriter.write(QUOTE + String.valueOf(dataTable.getValueAt(row, col)) + QUOTE);
					fileWriter.write(COMMA);
				}
				fileWriter.write(LS);
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (final java.io.IOException ioe) {
			System.err.println("Error while trying to save data to file: " + ioe);
		}
	}

	private void printBtnActionPerformed(final java.awt.event.ActionEvent evt) {

		final PrinterJob job = PrinterJob.getPrinterJob();
		final PageFormat pf = job.defaultPage();
		pf.setOrientation(PageFormat.PORTRAIT);
		final PageFormat pf2 = job.pageDialog(pf);
		if (pf2 != pf) {
			job.setPrintable(this, pf2);
			if (job.printDialog()) {
				try {
					job.print();
				} catch (final PrinterException e) {
					javax.swing.JOptionPane.showMessageDialog(this, e);
				}
			}
		}

	}

	private void defaultTableModelProxyTableChanged(final javax.swing.event.TableModelEvent evt) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final Rectangle old_rect = dataTable.getVisibleRect();
				final Rectangle new_rect = dataTable.getCellRect(dataTable.getRowCount() + 1, 0, true);
				new_rect.x = old_rect.x;
				new_rect.width = old_rect.width;
				dataTable.scrollRectToVisible(new_rect);
			}
		});

	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/newface/resources/legacy/table16.gif"));
	}

	@Override
	public String getName() {
		return "Data Table";
	}

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		defaultTableModelProxy.setExpDataModel(model);
		model.addExpDataModelListener(new ExpDataModelListener() {
			private boolean resizeDone = false;

			public void headerAvailable(final HardwareAcquisitionConfig config) {

			}

			@Override
			public void newSamples(final NewExpDataEvent evt) {
				if (!resizeDone) {
					resizeDone = true;
					final FontMetrics fm = dataTable.getFontMetrics(dataTable.getFont());
					final TableColumnModel model = dataTable.getColumnModel();
					for (int i = 0; i < model.getColumnCount(); i++) {
						final TableColumn col = model.getColumn(i);
						if (dataTable.getColumnName(i) != null) {
							final int width = fm.stringWidth(dataTable.getColumnName(i)) + model.getColumnMargin() * 2
									+ 10;
							col.setPreferredWidth(width);
						}
					}
				}
			}

			@Override
			public void dataModelEnded() {
			}

			@Override
			public void dataModelError() {
			}

			@Override
			public void dataModelStarted() {
				defaultTableModelProxy.fireTableStructureChanged();
				defaultTableModelProxy.fireTableDataChanged();
			}

			@Override
			public void dataModelStartedNoData() {
				defaultTableModelProxy.fireTableStructureChanged();
				defaultTableModelProxy.fireTableDataChanged();
			}

			@Override
			public void dataModelStoped() {// BIG SILENT NOOP
			}

			@Override
			public void dataModelWaiting() {
			}
		});

	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return toolBarTable;
	}

	/**
	 * Prints the page at the specified index into the specified
	 * {@link Graphics} context in the specified format. A
	 * <code>PrinterJob</code> calls the <code>Printable</code> interface to
	 * request that a page be rendered into the context specified by
	 * <code>graphics</code>. The format of the page to be drawn is specified by
	 * <code>pageFormat</code>. The zero based index of the requested page is
	 * specified by <code>pageIndex</code>. If the requested page does not exist
	 * then this method returns NO_SUCH_PAGE; otherwise PAGE_EXISTS is returned.
	 * The <code>Graphics</code> class or subclass implements the
	 * {@link PrinterGraphics} interface to provide additional information. If
	 * the <code>Printable</code> object aborts the print job then it throws a
	 * {@link PrinterException}.
	 * 
	 * @param graphics the context into which the page is drawn
	 * @param pageFormat the size and orientation of the page being drawn
	 * @param pageIndex the zero based index of the page to be drawn
	 * @return PAGE_EXISTS if the page is rendered successfully or NO_SUCH_PAGE
	 *         if <code>pageIndex</code> specifies a non-existent page.
	 * @exception java.awt.print.PrinterException thrown when the print job is
	 *                terminated.
	 */
	@Override
	public int print(final Graphics g, final PageFormat pageFormat, final int pageIndex) throws PrinterException {

		final java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
		final int fontHeight = g2.getFontMetrics().getHeight();
		final int fontDesent = g2.getFontMetrics().getDescent();

		// deixar espa�o para o numero da pagina
		final double pageHeight = pageFormat.getImageableHeight() - fontHeight;
		final double pageWidth = pageFormat.getImageableWidth();

		final double tableWidth = dataTable.getColumnModel().getTotalColumnWidth();
		double scale = 1;
		if (tableWidth >= pageWidth) {
			scale = pageWidth / tableWidth;
		}

		final double headerHeightOnPage = dataTable.getTableHeader().getHeight() * scale;
		final double tableWidthOnPage = tableWidth * scale;
		final double oneRowHeight = (dataTable.getRowHeight() + dataTable.getRowMargin()) * scale;
		final int numRowsOnAPage = (int) ((pageHeight - headerHeightOnPage) / oneRowHeight);
		final double pageHeightForTable = oneRowHeight * numRowsOnAPage;
		final int totalNumPages = (int) Math.ceil(((double) dataTable.getRowCount()) / numRowsOnAPage);

		if (pageIndex >= totalNumPages) {
			return (java.awt.print.Printable.NO_SUCH_PAGE);
		}

		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

		// bottom center
		g2.drawString("Page" + (pageIndex + 1), (int) pageWidth / 2 - 35, (int) (pageHeight + fontHeight - fontDesent));

		g2.translate(0f, headerHeightOnPage);
		g2.translate(0f, -pageIndex * pageHeightForTable);

		// If this piece of the table is smaller
		// than the size available,
		// clip to the appropriate bounds.
		if (pageIndex + 1 == totalNumPages) {
			final int lastRowPrinted = numRowsOnAPage * pageIndex;
			final int numRowsLeft = dataTable.getRowCount() - lastRowPrinted;
			g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage),
					(int) Math.ceil(oneRowHeight * numRowsLeft));
		}
		// else clip to the entire area available.
		else {
			g2.setClip(0, (int) (pageHeightForTable * pageIndex), (int) Math.ceil(tableWidthOnPage),
					(int) Math.ceil(pageHeightForTable));
		}

		g2.scale(scale, scale);
		dataTable.paint(g2);
		g2.scale(1 / scale, 1 / scale);
		g2.translate(0f, pageIndex * pageHeightForTable);
		g2.translate(0f, -headerHeightOnPage);
		g2.setClip(0, 0, (int) Math.ceil(tableWidthOnPage), (int) Math.ceil(headerHeightOnPage));
		g2.scale(scale, scale);
		// paint header at top
		dataTable.getTableHeader().paint(g2);
		return (java.awt.print.Printable.PAGE_EXISTS);

	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param col
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 * @return
	 */
	public int getColAtArray(final int col) {
		return defaultTableModelProxy.getColAtArray(col);
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public int[] getColArray() {
		return defaultTableModelProxy.getColArray();
	}

	/**
	 * Setter for property channelDisplayY.
	 * 
	 * @param colArray
	 * 
	 * @param channelDisplayY New value of property channelDisplayY.
	 */
	public void setColArray(final int[] colArray) {
		defaultTableModelProxy.setColArray(colArray);
	}

	// Variables declaration - do not modify
	private javax.swing.JButton printBtn;
	private MeteoTableModelProxy defaultTableModelProxy;
	private javax.swing.JTable dataTable;
	private javax.swing.JScrollPane scrollPaneTable;
	private javax.swing.JButton saveBtn;
	private javax.swing.JToolBar toolBarTable;
	private javax.swing.JButton copyBtn;
	private javax.swing.JButton selectAllBtn;
	// End of variables declaration
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.DATA_TABLE;
    }
}
