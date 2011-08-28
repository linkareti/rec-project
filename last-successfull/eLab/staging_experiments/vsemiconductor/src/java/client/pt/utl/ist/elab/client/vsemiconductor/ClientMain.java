/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.vsemiconductor;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		ui.setApparatusAutoConnectID("SCONDUCTOR_V1.0");
		ui.setVisible(true);
	}

	/*
	 * public static void initUI(RecBaseUI ui) {
	 * /*ui.addAcqDisplay("ELAB_PV_STAMP_V02"
	 * ,"pt.utl.ist.elab.client.pv.SeringeSensor");
	 * ui.addAcqDisplay("ELAB_PV_STAMP_V02"
	 * ,"com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable");
	 * ui.addAcqDisplay("ELAB_PV_STAMP_V02",
	 * "com.linkare.rec.impl.baseUI.graph.DefaultExperimentTimeSeriesGraph");
	 * ui.addAcqDisplay("ELAB_PV_STAMP_V02",
	 * "com.linkare.rec.impl.baseUI.display.DefaultExperimentHeaderInfo");
	 */
	// }

}
