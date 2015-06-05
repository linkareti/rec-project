package pt.utl.ist.elab.client;

import javax.swing.SwingUtilities;

import com.linkare.rec.impl.newface.ReCApplication;
import com.linkare.rec.utils.ClassUtils;

import org.jdesktop.application.Application;

/**
 *
 * This class is just a wrapper class for the true main class. The reason for
 * its existence is only to guarantee that the certificate shown to the user is
 * the eLab's one and not the ReC's framework Linkare default
 *
 * @author André Leitão - Linkare TI
 */
public class FullClientNewInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        ReCApplication.main(args);
    }

}
