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
        System.out.println("Current class classloader:" + FullClientNewInterface.class.getClassLoader());
        System.out.println("TCCL classloader:" + Thread.currentThread().getContextClassLoader());
        System.out.println("JDesktop application classloader " + Application.class.getClassLoader());
        System.out.println("SwingUtilities classloader " + SwingUtilities.class.getClassLoader());
        System.out.println("Sytem classloader " + ClassLoader.getSystemClassLoader());

        try {
            System.out.println("Trying to find FlatLookAndFeel from current class classloader");
            Class.forName("com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel", false, FullClientNewInterface.class.getClassLoader());
            System.out.println("Loaded FlatLookAndFeel correctly from current class classloader");
        } catch (Exception e) {
            System.out.println("Couldn't load FlatLookAndFeel correctly from current class classloader");
            e.printStackTrace(System.out);

        }
        try {
            System.out.println("Trying to find FlatLookAndFeel from TCCL classloader");
            Class.forName("com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel", false, Thread.currentThread().getContextClassLoader());
            System.out.println("Loaded FlatLookAndFeel correctly from TCCL classloader");

        } catch (Exception e2) {
            System.out.println("Couldn't load FlatLookAndFeel correctly from TCCL class classloader");
            e2.printStackTrace(System.out);

        }
        try {
            System.out.println("Trying to find FlatLookAndFeel from JDesktop classloader");
            Class.forName("com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel", false, Application.class.getClassLoader());
            System.out.println("Loaded FlatLookAndFeel correctly from JDesktop classloader");

        } catch (Exception e3) {
            System.out.println("Couldn't load FlatLookAndFeel correctly from JDesktop class classloader");
            e3.printStackTrace(System.out);
        }

        try {
            System.out.println("Trying to find FlatLookAndFeel from Swingutilities classloader");
            Class.forName("com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel", false, SwingUtilities.class.getClassLoader());
            System.out.println("Loaded FlatLookAndFeel correctly from SwingUtilities classloader");

        } catch (Exception e3) {
            System.out.println("Couldn't load FlatLookAndFeel correctly from SwingUtilities classloader");
            e3.printStackTrace(System.out);
        }

        try {
            System.out.println("Trying to find FlatLookAndFeel from System classloader");
            Class.forName("com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel", false, ClassLoader.getSystemClassLoader());
            System.out.println("Loaded FlatLookAndFeel correctly from System classloader");

        } catch (Exception e3) {
            System.out.println("Couldn't load FlatLookAndFeel correctly from System classloader");
            e3.printStackTrace(System.out);
        }

        ReCApplication.main(args);
    }

}
