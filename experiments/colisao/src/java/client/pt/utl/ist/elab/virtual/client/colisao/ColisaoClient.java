/*
 * MMClient.java
 *
 * Created on 16 de Outubro de 2004, 11:29
 */

package pt.utl.ist.elab.virtual.client.colisao;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author  Ean
 */
public class ColisaoClient
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("COLISAO_V1.0");
	ui.show();
    }        
}
