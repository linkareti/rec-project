/*
 * AboutDialog.java
 *
 * Created on 11 de Junho de 2003, 12:01
 */

package com.linkare.rec.impl.baseUI;

import java.net.URL;

import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class AboutDialog extends javax.swing.JDialog
{
    private String ABOUT_REC = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.about", "About ReC");   
    
    /** Creates new form AboutDialog */
    public AboutDialog()
    {
	super();
        
        ReCBaseUIConfig recBaseUIConfig = ReCBaseUIConfig.sharedInstance();
        
	initComponents();
	
	URL urlPageAboutPersonalized=null;
	try
	{
	    urlPageAboutPersonalized = recBaseUIConfig.getAboutPageURL();
	}
	catch(Exception e)
	{
	}
	
	try
	{	    
            if(urlPageAboutPersonalized != null)
                tAbout.setPage(urlPageAboutPersonalized);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        tAbout = new javax.swing.JTextPane();

        setTitle(ABOUT_REC);
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter()
        {
            public void windowClosing(java.awt.event.WindowEvent evt)
            {
                closeDialog(evt);
            }
        });

        tAbout.setEditable(false);
        jScrollPane1.setViewportView(tAbout);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-410)/2, (screenSize.height-380)/2, 410, 380);
    }//GEN-END:initComponents
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt)
    {//GEN-FIRST:event_closeDialog
	setVisible(false);
	dispose();
    }//GEN-LAST:event_closeDialog
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane tAbout;
    // End of variables declaration//GEN-END:variables
    
}
