/*
 * DefaultExperimentDataTable.java
 *
 * Created on 7 de Maio de 2003, 18:33
 */

package com.linkare.rec.impl.baseUI.table;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.border.Border;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import com.linkare.rec.impl.baseUI.utils.ExtensionFilter;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelContainer;
import com.linkare.rec.impl.client.experiment.export.csv.ExportCSV;
import com.linkare.rec.impl.client.experiment.export.printer.ExportPrinter;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.component.ResultsActionBar;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultExperimentDataTable extends javax.swing.JPanel implements ExpDataDisplay, Printable {

	/** Generated UID */
	private static final long serialVersionUID = 1441407957009157543L;

	private static String UI_CLIENT_LOGGER = "ReC.baseUI";

	private ExcelAdapter excelAdapter = null;

	private ExpDataModelContainer expDataModelContainer = null;

	private DefaultTableModel actualTableModel = null;

	private TableModelListener actualTableActionListener = null;

	private static Logger log = null;
	static {
		DefaultExperimentDataTable.log = LogManager.getLogManager().getLogger(
				DefaultExperimentDataTable.UI_CLIENT_LOGGER);
		if (DefaultExperimentDataTable.log == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DefaultExperimentDataTable.UI_CLIENT_LOGGER));
		}
	}

	private static final String CSV_DESCRIPTION_STR = ReCResourceBundle.findStringOrDefault(
			"ReCBaseUI$rec.bui.csv.discription", "Comma separeted files");

	/** Creates new form DefaultExperimentDataTable */
	public DefaultExperimentDataTable() {
		initComponents();

		actualTableModel = defaultTableModelProxy;
		expDataModelContainer = defaultTableModelProxy;

		excelAdapter = new ExcelAdapter(dataTable);
		dataTable.setCellSelectionEnabled(true);

		resizeDataTableColumns();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()// GEN-BEGIN:initComponents
	{
		// FIXME codigo inserido manualmente vindo do MultSeriesTable
		defaultTableModelProxy = new com.linkare.rec.impl.client.experiment.DefaultTableModelProxy();
		toolBarTable = new javax.swing.JToolBar();
		printBtn = new javax.swing.JButton();
		saveBtn = new javax.swing.JButton();
		copyBtn = new javax.swing.JButton();
		selectAllBtn = new javax.swing.JButton();
		btnPlayStop = new javax.swing.JButton();
		scrollPaneTable = new javax.swing.JScrollPane();
		dataTable = new javax.swing.JTable();

		actualTableActionListener = new javax.swing.event.TableModelListener() {
			@Override
			public void tableChanged(final javax.swing.event.TableModelEvent evt) {
				defaultTableModelProxyTableChanged(evt);
			}
		};
		defaultTableModelProxy.addTableModelListener(actualTableActionListener);

		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(ResultsActionBar.class);

		toolBarTable.setRollover(true);
		toolBarTable.setOpaque(false);

		final javax.swing.ActionMap actionMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getActionMap(DefaultExperimentDataTable.class, this);
		btnPlayStop.setAction(actionMap.get("stop")); // NOI18N

		btnPlayStop.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
		btnPlayStop.setIcon(resourceMap.getIcon("btnPlayStop.icon")); // NOI18N
		btnPlayStop.setText(resourceMap.getString("btnPlayStop.text")); // NOI18N
		btnPlayStop.setToolTipText(resourceMap.getString("btnPlayStop.toolTipText")); // NOI18N
		btnPlayStop.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		btnPlayStop.setBorderPainted(false);
		btnPlayStop.setName("btnPlayStop"); // NOI18N
		btnPlayStop.setPressedIcon(resourceMap.getIcon("btnPlayStop.pressedIcon")); // NOI18N

		setButtonBorder(btnPlayStop);
		// toolBarTable.addSeparator();
		toolBarTable.add(btnPlayStop);

		printBtn.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
		printBtn.setIcon(resourceMap.getIcon("btnPrint.icon")); // NOI18N
		printBtn.setToolTipText(resourceMap.getString("btnPrint.toolTipText")); // NOI18N
		printBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		printBtn.setBorderPainted(false);
		printBtn.setName("printBtn"); // NOI18N
		printBtn.setPressedIcon(resourceMap.getIcon("btnPrint.pressedIcon")); // NOI18N
		printBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				printBtnActionPerformed(evt);
			}
		});
		setButtonBorder(printBtn);
		// toolBarTable.addSeparator();
		toolBarTable.add(printBtn);

		saveBtn.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
		saveBtn.setIcon(resourceMap.getIcon("btnSave.icon")); // NOI18N
		saveBtn.setToolTipText(resourceMap.getString("btnSave.toolTipText")); // NOI18N
		saveBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		saveBtn.setBorderPainted(false);
		saveBtn.setName("saveBtn"); // NOI18N
		saveBtn.setPressedIcon(resourceMap.getIcon("btnSave.pressedIcon")); // NOI18N
		saveBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				saveBtnActionPerformed(evt);
			}
		});
		setButtonBorder(saveBtn);
		// toolBarTable.addSeparator();
		toolBarTable.add(saveBtn);

		copyBtn.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
		copyBtn.setIcon(resourceMap.getIcon("btnCopy.icon")); // NOI18N
		copyBtn.setToolTipText(resourceMap.getString("btnCopy.toolTipText")); // NOI18N
		copyBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		copyBtn.setBorderPainted(false);
		copyBtn.setName("btnCopy"); // NOI18N
		copyBtn.setPressedIcon(resourceMap.getIcon("btnCopy.pressedIcon")); // NOI18N
		copyBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				copyBtnActionPerformed(evt);
			}
		});
		setButtonBorder(copyBtn);
		// toolBarTable.addSeparator();
		toolBarTable.add(copyBtn);

		selectAllBtn.setBackground(resourceMap.getColor("btnPlayStop.background")); // NOI18N
		selectAllBtn.setIcon(resourceMap.getIcon("btnSelectAll.icon")); // NOI18N
		selectAllBtn.setToolTipText(resourceMap.getString("btnSelectAll.toolTipText")); // NOI18N
		selectAllBtn.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 4, 4));
		selectAllBtn.setBorderPainted(false);
		selectAllBtn.setName("btnSelectAll"); // NOI18N
		selectAllBtn.setPressedIcon(resourceMap.getIcon("btnSelectAll.pressedIcon")); // NOI18N
		selectAllBtn.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				selectAllBtnActionPerformed(evt);
			}
		});
		setButtonBorder(selectAllBtn);
		// toolBarTable.addSeparator();
		toolBarTable.add(selectAllBtn);

		setLayout(new java.awt.BorderLayout());
		dataTable.setModel(defaultTableModelProxy);
		dataTable.setAutoCreateColumnsFromModel(true);
		dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		// dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPaneTable.setViewportView(dataTable);

		add(scrollPaneTable, java.awt.BorderLayout.CENTER);

		// FIXME codigo em conformidade com o .form
		// defaultTableModelProxy = new
		// com.linkare.rec.impl.client.experiment.DefaultTableModelProxy();
		// toolBarTable = new javax.swing.JToolBar();
		// printBtn = new javax.swing.JButton();
		// saveBtn = new javax.swing.JButton();
		// copyBtn = new javax.swing.JButton();
		// selectAllBtn = new javax.swing.JButton();
		// scrollPaneTable = new javax.swing.JScrollPane();
		// dataTable = new javax.swing.JTable();
		//
		// defaultTableModelProxy.addTableModelListener(new
		// javax.swing.event.TableModelListener() {
		// public void tableChanged(javax.swing.event.TableModelEvent evt) {
		// defaultTableModelProxyTableChanged(evt);
		// }
		// });
		//
		// toolBarTable.setRollover(true);
		// printBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		// "/com/linkare/rec/impl/baseUI/resources/Print16.gif")));
		// printBtn.setToolTipText("Print");
		// printBtn.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// printBtnActionPerformed(evt);
		// }
		// });
		//
		// toolBarTable.add(printBtn);
		//
		// saveBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		// "/com/linkare/rec/impl/baseUI/resources/Save16.gif")));
		// saveBtn.setToolTipText("Save csv file");
		// saveBtn.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// saveBtnActionPerformed(evt);
		// }
		// });
		//
		// toolBarTable.add(saveBtn);
		//
		// copyBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource(
		// "/com/linkare/rec/impl/baseUI/resources/Copy16.gif")));
		// copyBtn.setToolTipText("Copy to Clipboard in Excel Format");
		// copyBtn.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// copyBtnActionPerformed(evt);
		// }
		// });
		//
		// toolBarTable.add(copyBtn);
		//
		// selectAllBtn.setIcon(new
		// javax.swing.ImageIcon(getClass().getResource(
		// "/com/linkare/rec/impl/baseUI/resources/tableSelectAll16.gif")));
		// selectAllBtn.setToolTipText("Select All");
		// selectAllBtn.addActionListener(new java.awt.event.ActionListener() {
		// public void actionPerformed(java.awt.event.ActionEvent evt) {
		// selectAllBtnActionPerformed(evt);
		// }
		// });
		//
		// toolBarTable.add(selectAllBtn);
		//
		// setLayout(new java.awt.BorderLayout());
		//
		// dataTable.setModel(defaultTableModelProxy);
		// dataTable.setAutoCreateColumnsFromModel(true);
		// dataTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
		// scrollPaneTable.setViewportView(dataTable);
		//
		// add(scrollPaneTable, java.awt.BorderLayout.CENTER);

	}// GEN-END:initComponents

	private void selectAllBtnActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_selectAllBtnActionPerformed
	{// GEN-HEADEREND:event_selectAllBtnActionPerformed
		dataTable.selectAll();
	}// GEN-LAST:event_selectAllBtnActionPerformed

	private void copyBtnActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_copyBtnActionPerformed
	{// GEN-HEADEREND:event_copyBtnActionPerformed
		excelAdapter.copyToClipBoard();
	}// GEN-LAST:event_copyBtnActionPerformed

	private void saveBtnActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_saveBtnActionPerformed
	{// GEN-HEADEREND:event_saveBtnActionPerformed
		saveTableToFile();
	}// GEN-LAST:event_saveBtnActionPerformed

	private void printBtnActionPerformed(final java.awt.event.ActionEvent evt)// GEN-FIRST:event_printBtnActionPerformed
	{// GEN-HEADEREND:event_printBtnActionPerformed
		try {
			ExportPrinter.print(this);
		} catch (final PrinterException e) {
			DefaultExperimentDataTable.log.warning("Error while trying to print: " + e);
			javax.swing.JOptionPane.showMessageDialog(this, e);
		}
	}// GEN-LAST:event_printBtnActionPerformed

	private void defaultTableModelProxyTableChanged(final javax.swing.event.TableModelEvent evt)// GEN-FIRST:event_defaultTableModelProxyTableChanged
	{// GEN-HEADEREND:event_defaultTableModelProxyTableChanged
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

	}// GEN-LAST:event_defaultTableModelProxyTableChanged

	private void setButtonBorder(final JButton button) {
		final int top = 0;
		final int left = 6;
		final int bottom = 0;
		final int right = 6;
		final Border border = BorderFactory.createEmptyBorder(top, left, bottom, right);
		button.setBorder(border);
	}

	protected void setActualTableModel(final DefaultTableModel model) {
		actualTableModel.removeTableModelListener(actualTableActionListener);
		actualTableModel = model;
		actualTableModel.addTableModelListener(actualTableActionListener);
		dataTable.setModel(actualTableModel);

		resizeDataTableColumns();
	}

	protected void setExpDataModelContainer(final ExpDataModelContainer expDataModelContainer) {
		this.expDataModelContainer = expDataModelContainer;
	}

	private void saveTableToFile() {
		final javax.swing.JFileChooser jFileChooserSave = new javax.swing.JFileChooser();
		final ExtensionFilter textExtension = new ExtensionFilter(ExportCSV.CSV_EXTENTION_FILE, "ext");

		textExtension.setDescription(DefaultExperimentDataTable.CSV_DESCRIPTION_STR);
		jFileChooserSave.setAcceptAllFileFilterUsed(false);
		jFileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
		jFileChooserSave.setFileFilter(textExtension);

		final int returnValue = jFileChooserSave.showSaveDialog(this);
		String extension = null;
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			extension = textExtension.getExtension();
		}
		final File selectedFile = jFileChooserSave.getSelectedFile();
		if (selectedFile != null) {
			String path = selectedFile.getPath();
			if (path != null && path.trim().length() > 0) {
				if (path.endsWith(extension)) {
					path = path.substring(0, path.length() - 4);
				}
				final File saveFile = new File(path + "." + extension);
				saveTable(saveFile, false);
			}
		}
	}

	private void saveTable(final File saveFile, final boolean append) {
		try {
			final Writer fileWriter = new OutputStreamWriter(new FileOutputStream(saveFile, append));
			fileWriter.write(ExportCSV.print(actualTableModel));
			fileWriter.flush();
			fileWriter.close();
		} catch (final java.io.IOException ioe) {
			DefaultExperimentDataTable.log.warning("Error while trying to save data to file: " + ioe);
		}
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public Icon getIcon() {
		return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/table16.gif"));
	}

	@Override
	public String getName() {
		return "Data Table";
	}

	private void resizeDataTableColumns() {
		final FontMetrics fm = dataTable.getFontMetrics(dataTable.getFont());
		final TableColumnModel model = dataTable.getColumnModel();
		for (int i = 0; i < model.getColumnCount(); i++) {
			final TableColumn col = model.getColumn(i);
			final int width = fm.stringWidth(dataTable.getColumnName(i)) + model.getColumnMargin() * 2 + 10;
			col.setPreferredWidth(width);
		}
	}

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		if (model == null) {
			return;
		}

		expDataModelContainer.setExpDataModel(model);
		// after set the model the columns size must be set
		resizeDataTableColumns();
	}

	public ExpDataModel getExpDataModel() {
		return expDataModelContainer == null ? null : expDataModelContainer.getExpDataModel();
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

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton printBtn;
	private com.linkare.rec.impl.client.experiment.DefaultTableModelProxy defaultTableModelProxy;
	private javax.swing.JTable dataTable;
	private javax.swing.JScrollPane scrollPaneTable;
	private javax.swing.JButton saveBtn;
	private javax.swing.JToolBar toolBarTable;
	private javax.swing.JButton copyBtn;
	private javax.swing.JButton selectAllBtn;
	// End of variables declaration//GEN-END:variables

	private javax.swing.JButton btnPlayStop;

}
