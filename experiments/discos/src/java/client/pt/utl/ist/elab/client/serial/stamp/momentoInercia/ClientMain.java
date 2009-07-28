/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client.serial.stamp.momentoInercia;

import com.linkare.rec.impl.baseUI.*;
import javax.swing.JFrame;

/**
 *
 * @author  jp
 */
public class ClientMain
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
	JFrame.setDefaultLookAndFeelDecorated(true);
	RecBaseUI ui=new RecBaseUI();
	initUI(ui);
	ui.setApparatusAutoConnectId("EXP_ANGULAR_STAMP_V1.0");
	ui.show();
    }
    
     public static void initUI(RecBaseUI ui)
    {
	/*ui.addAcqDisplay("ELAB_PV_STAMP_V02","pt.utl.ist.elab.client.serial.stamp.pv.SeringeSensor");
	ui.addAcqDisplay("ELAB_PV_STAMP_V02","com.linkare.rec.impl.baseUI.table.DefaultExperimentDataTable");
	ui.addAcqDisplay("ELAB_PV_STAMP_V02","com.linkare.rec.impl.baseUI.graph.DefaultExperimentTimeSeriesGraph");
	ui.addAcqDisplay("ELAB_PV_STAMP_V02","com.linkare.rec.impl.baseUI.display.DefaultExperimentHeaderInfo");
	 */
    }
    
}
