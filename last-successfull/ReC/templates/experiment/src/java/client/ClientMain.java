
import com.linkare.rec.impl.newface.ReCApplication;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * 
 * This class is just a wrapper class for the true main class. The reason for
 * its existence is only to guarantee that the certificate shown to the user is
 * the eLab's one and not the ReC's framework Linkare default
 * 
 * @author Gedsimon Pereira - Linkare TI
 */
public class ClientMain {
        /**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		ReCApplication.main(args);
	}
}
