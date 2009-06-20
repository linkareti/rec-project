/*
 * MMClient.java
 *
 * Created on 16 de Outubro de 2004, 11:29
 */

package pt.utl.ist.elab.virtual.client.poisson;

import com.linkare.rec.impl.baseUI.ReCBaseUI;
import javax.swing.JFrame;

/**
 *
 * @author  André
 */
public class PoissonClient
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        //Este código é sempre igual, só altera o nome, ou melhor, o ID da exp, que neste caso vai ser MASSA_MOLA_V1.0
	JFrame.setDefaultLookAndFeelDecorated(true);
	ReCBaseUI ui=new ReCBaseUI();
	ui.setApparatusAutoConnectID("POISSON_V1.0");
	ui.show();
    }        
}
