/*
 * InternalBrowser.java
 *
 * Created on August 17, 2004, 10:33 AM
 */

package com.linkare.rec.impl.baseUI;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class InternalBrowser extends javax.swing.JInternalFrame
{
    
    /** Creates new form InternalBrowser */
    public InternalBrowser()
    {
        initComponents();
        setTitle(com.linkare.rec.impl.i18n.ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.internalBrowser", "Internal Browser"));
    }
    
    public InternalBrowser(java.net.URL url)
    {
        this();
        try
	{	    
            if(url != null)
                jTextPane.setPage(url);
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
        jTextPane = new javax.swing.JTextPane();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(250, 200));
        jTextPane.setEditable(false);
        jScrollPane1.setViewportView(jTextPane);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane;
    // End of variables declaration//GEN-END:variables
    
}
