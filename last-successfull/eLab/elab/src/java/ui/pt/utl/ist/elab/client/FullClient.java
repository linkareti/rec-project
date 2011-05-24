/*
 * ClientMain.java
 *
 * Created on 26 de Maio de 2003, 19:01
 */

package pt.utl.ist.elab.client;

import com.linkare.rec.impl.baseUI.ReCBaseUI;

/**
 * 
 * This class is just a wrapper class for the true main class. The reason for
 * its existence is only to guarantee that the certificate shown to the user is the eLab's one 
 * and not the ReC's framework Linkare default
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class FullClient {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		ReCBaseUI.main(args);
	}

}
