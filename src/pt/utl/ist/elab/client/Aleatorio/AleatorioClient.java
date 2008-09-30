/*
 * AleatorioClient.java
 *
 * Created on 12 de Junho de 2003, 13:49
 */

package pt.utl.ist.elab.client.Aleatorio;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
/**
 *
 * @author Pedro Carvalho - LEFT - IST
 */
public class AleatorioClient
{
    
    private static String NAME = "ELAB_ALEATORIO_V02";
    private static String PATH = "pt.utl.ist.elab.client.Aleatorio.displays.";
    /** Creates a new instance of AleatorioClient */
    public AleatorioClient()
    {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        ReCBaseUI ui=new ReCBaseUI();
        ui.setApparatusAutoConnectID(NAME);
        ui.show();
    }
}
