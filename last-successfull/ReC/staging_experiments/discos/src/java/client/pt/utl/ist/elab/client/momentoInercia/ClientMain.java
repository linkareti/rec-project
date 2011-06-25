/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.momentoInercia;

import javax.swing.JFrame;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * @author jp
 */
public class ClientMain {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		JFrame.setDefaultLookAndFeelDecorated(true);
		ReCBaseUI ui = new ReCBaseUI();
		initUI(ui);
		ui.setApparatusAutoConnectID("EXP_ANGULAR_STAMP_V1.0");
		ui.setVisible(true);
	}

	public static void initUI(ReCBaseUI ui) {
		/*
		 * ui.addAcqDisplay("ELAB_PV_STAMP_V02","pt.utl.ist.elab.client.serial.stamp.pv.SeringeSensor"
		 * );ui.addAcqDisplay("ELAB_PV_STAMP_V02",
		 * "com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable");
		 * ui.addAcqDisplay("ELAB_PV_STAMP_V02",
		 * "com.linkare.rec.impl.baseUI.graph.DefaultExperimentTimeSeriesGraph"
		 * );ui.addAcqDisplay("ELAB_PV_STAMP_V02",
		 * "com.linkare.rec.impl.baseUI.display.DefaultExperimentHeaderInfo");
		 */
	}

}
