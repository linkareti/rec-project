/*
 * MainDisplay.java
 *
 * Created on 26 de Maio de 2003, 23:55
 */

package pt.utl.ist.elab.client.webrobot.displays;

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;

/**
 * 
 * @author André Neto - LEFT - IST
 */

public class MainDisplay extends javax.swing.JPanel implements ExpDataDisplay {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6264873711019619672L;

	/** Creates new form MainDisplay */
	public MainDisplay() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {//GEN-BEGIN:initComponents
		jToolBar = new javax.swing.JToolBar();
		jButtonSave = new javax.swing.JButton();
		jButtonTBPrint = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel2 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jPanelAnalogic1 = new pt.utl.ist.elab.client.webrobot.displays.auxUi.JPanelAnalogic();
		jPanelDigital1 = new pt.utl.ist.elab.client.webrobot.displays.auxUi.JPanelDigital();
		jPanelIV1 = new pt.utl.ist.elab.client.webrobot.displays.auxUi.JPanelIV();

		jToolBar.setBackground(new java.awt.Color(204, 204, 204));
		jButtonSave.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/displays/resources/Save16.gif")));
		jButtonSave.setToolTipText("Guardar dados...");
		jToolBar.add(jButtonSave);

		jButtonTBPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/webrobot/displays/resources/Print16.gif")));
		jButtonTBPrint.setToolTipText("Imprimir dados e/ou gr\u00e1fico...");
		jToolBar.add(jButtonTBPrint);

		setLayout(new java.awt.BorderLayout());

		setMinimumSize(new java.awt.Dimension(560, 270));
		setPreferredSize(new java.awt.Dimension(560, 270));
		jPanel2.setLayout(new java.awt.BorderLayout());

		jPanel2.setMinimumSize(new java.awt.Dimension(560, 270));
		jPanel2.setPreferredSize(new java.awt.Dimension(560, 270));
		jPanel1.setLayout(new java.awt.BorderLayout());

		jPanelAnalogic1.setBorder(new javax.swing.border.TitledBorder(null, "Entradas anal\u00f3gicas",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 0, 11),
				new java.awt.Color(0, 51, 153)));
		jPanel1.add(jPanelAnalogic1, java.awt.BorderLayout.NORTH);

		jPanelDigital1.setBorder(new javax.swing.border.TitledBorder(null, "Entradas digitais",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 0, 11),
				new java.awt.Color(0, 51, 153)));
		jPanelDigital1.setForeground(new java.awt.Color(0, 0, 0));
		jPanelDigital1.setMinimumSize(new java.awt.Dimension(181, 500));
		jPanelDigital1.setPreferredSize(new java.awt.Dimension(181, 500));
		jPanel1.add(jPanelDigital1, java.awt.BorderLayout.CENTER);

		jPanel2.add(jPanel1, java.awt.BorderLayout.EAST);

		jPanelIV1.setBorder(new javax.swing.border.TitledBorder(null, "Sensores infravermelhos",
				javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
				javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Comic Sans MS", 0, 11),
				new java.awt.Color(0, 51, 153)));
		jPanel2.add(jPanelIV1, java.awt.BorderLayout.CENTER);

		jScrollPane1.setViewportView(jPanel2);

		add(jScrollPane1, java.awt.BorderLayout.CENTER);

	}//GEN-END:initComponents

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public javax.swing.Icon getIcon() {
		return icon;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		jPanelIV1.setExpDataModel(model);
		jPanelAnalogic1.setExpDataModel(model);
		jPanelDigital1.setExpDataModel(model);
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton jButtonSave;
	private pt.utl.ist.elab.client.webrobot.displays.auxUi.JPanelAnalogic jPanelAnalogic1;
	private javax.swing.JButton jButtonTBPrint;
	private javax.swing.JToolBar jToolBar;
	private javax.swing.JPanel jPanel2;
	private pt.utl.ist.elab.client.webrobot.displays.auxUi.JPanelIV jPanelIV1;
	private javax.swing.JPanel jPanel1;
	private pt.utl.ist.elab.client.webrobot.displays.auxUi.JPanelDigital jPanelDigital1;
	// End of variables declaration//GEN-END:variables
	/** My vars */
	private ExpDataModel model;
	private final javax.swing.ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource(
			"/pt/utl/ist/elab/client/webrobot/displays/resources/Main16.gif"));
	private final String name = "Painel principal";
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.CHART;
    }
}
