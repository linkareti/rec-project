/*
 * SubBlock.java
 *
 * Created on 20 de Dezembro de 2002, 16:43
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs;

/**
 *
 * @author  Andr�
 */
public class SubBlock extends javax.swing.JDialog {
    
    /** Creates new form SubBlock */
    public SubBlock(java.awt.Frame parent, boolean modal,int tipo, boolean connectComp) 
    {
        super(parent, modal);
        initComponents();
        switch(tipo)
        {
            case 0:break;
            case 1:jButtonA.setIcon(image1);break;
            case 2:jButtonA.setIcon(image2);break;
            case 3:jButtonA.setIcon(image3);break;
            case 4:jButtonA.setIcon(image4);break;
            case 5:jButtonA.setIcon(image5);break;
            case 6:jButtonA.setIcon(image6);break;
            case 8:jButtonA.setIcon(image8);break;
            case 9:jButtonA.setIcon(image9);break;
            case 10:jButtonA.setIcon(image10);break;
            case 11:jButtonA.setIcon(image11);break;
            case 12:jButtonA.setIcon(image12);break;
            case 13:jButtonA.setIcon(image13);break;
            case 14:jButtonA.setIcon(image14);break;
        }
        if(connectComp)
        {
            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);
            jButton8.setEnabled(false);
            jButton9.setEnabled(false);
            jButton10.setEnabled(false);
            jButton11.setEnabled(false);
            jButton14.setEnabled(false);
        }
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelOkCancel = new javax.swing.JPanel();
        jButtonOk = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jPanelContainer = new javax.swing.JPanel();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanelChange = new javax.swing.JPanel();
        jButtonA = new javax.swing.JButton();
        jButtonB = new javax.swing.JButton();

        setTitle("Substituir o bloco");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jPanelOkCancel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        jPanelOkCancel.setForeground(new java.awt.Color(51, 0, 102));
        jButtonOk.setForeground(new java.awt.Color(51, 0, 102));
        jButtonOk.setText("Ok");
        jButtonOk.setPreferredSize(new java.awt.Dimension(73, 26));
        jButtonOk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jButtonOkKeyReleased(evt);
            }
        });

        jButtonOk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonOkMousePressed(evt);
            }
        });

        jPanelOkCancel.add(jButtonOk);

        jButtonCancel.setForeground(new java.awt.Color(51, 0, 102));
        jButtonCancel.setText("Cancel");
        jButtonCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonCancelMousePressed(evt);
            }
        });

        jPanelOkCancel.add(jButtonCancel);

        getContentPane().add(jPanelOkCancel, java.awt.BorderLayout.SOUTH);

        jPanelContainer.setLayout(new java.awt.GridBagLayout());

        jButton10.setIcon(image10);
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton10MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanelContainer.add(jButton10, gridBagConstraints);

        jButton11.setIcon(image11);
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton11MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanelContainer.add(jButton11, gridBagConstraints);

        jButton9.setIcon(image9);
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton9MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanelContainer.add(jButton9, gridBagConstraints);

        jButton8.setIcon(image8);
        jButton8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton8MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelContainer.add(jButton8, gridBagConstraints);

        jButton14.setIcon(image14);
        jButton14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton14MousePressed(evt);
            }
        });

        jPanelContainer.add(jButton14, new java.awt.GridBagConstraints());

        jButton3.setIcon(image3);
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });

        jPanelContainer.add(jButton3, new java.awt.GridBagConstraints());

        jButton4.setIcon(image4);
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
        });

        jPanelContainer.add(jButton4, new java.awt.GridBagConstraints());

        jButton6.setIcon(image6);
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
        });

        jPanelContainer.add(jButton6, new java.awt.GridBagConstraints());

        jButton5.setIcon(image5);
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton5MousePressed(evt);
            }
        });

        jPanelContainer.add(jButton5, new java.awt.GridBagConstraints());

        jButton13.setIcon(image13);
        jButton13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton13MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        jPanelContainer.add(jButton13, gridBagConstraints);

        jButton12.setIcon(image12);
        jButton12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton12MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        jPanelContainer.add(jButton12, gridBagConstraints);

        jButton2.setIcon(image2);
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        jPanelContainer.add(jButton2, gridBagConstraints);

        jButton1.setIcon(image1);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        jPanelContainer.add(jButton1, gridBagConstraints);

        jPanelChange.setBorder(new javax.swing.border.TitledBorder(null, "Converte Bloco A no Bloco B", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(51, 0, 102)));
        jButtonA.setBorder(new javax.swing.border.TitledBorder(null, "Bloco A", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(51, 0, 102)));
        jButtonA.setMaximumSize(new java.awt.Dimension(90, 50));
        jButtonA.setPreferredSize(new java.awt.Dimension(90, 70));
        jPanelChange.add(jButtonA);

        jButtonB.setBorder(new javax.swing.border.TitledBorder(null, "Bloco B", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 11), new java.awt.Color(51, 0, 102)));
        jButtonB.setMaximumSize(new java.awt.Dimension(90, 70));
        jButtonB.setPreferredSize(new java.awt.Dimension(90, 70));
        jPanelChange.add(jButtonB);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 4;
        jPanelContainer.add(jPanelChange, gridBagConstraints);

        getContentPane().add(jPanelContainer, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    private void jButtonCancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonCancelMousePressed
        setSubTipo(-1);
        closeDialog(null);
    }//GEN-LAST:event_jButtonCancelMousePressed

    private void jButtonOkKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jButtonOkKeyReleased
        if(evt.getKeyCode()==10)
        {
            jButtonOkMousePressed(null);
        }
        else if(evt.getKeyCode()==27)
        {
            jButtonCancelMousePressed(null);
        }
    }//GEN-LAST:event_jButtonOkKeyReleased

    private void jButtonOkMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButtonOkMousePressed
        closeDialog(null);
    }//GEN-LAST:event_jButtonOkMousePressed

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        setSubTipo(1);
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        setSubTipo(2);
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton12MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton12MousePressed
        setSubTipo(12);
    }//GEN-LAST:event_jButton12MousePressed

    private void jButton13MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton13MousePressed
        setSubTipo(13);
    }//GEN-LAST:event_jButton13MousePressed

    private void jButton5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MousePressed
        if(jButton5.isEnabled())        
        {
            setSubTipo(5);
        }
    }//GEN-LAST:event_jButton5MousePressed

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
        if(jButton6.isEnabled())
        {
            setSubTipo(6);
        }
    }//GEN-LAST:event_jButton6MousePressed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        if(jButton4.isEnabled())        
        {
            setSubTipo(4);
        }
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        if(jButton3.isEnabled())
        {
            setSubTipo(3);
        }
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton14MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton14MousePressed
        if(jButton14.isEnabled())
        {
            setSubTipo(14);
        }
    }//GEN-LAST:event_jButton14MousePressed

    private void jButton8MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton8MousePressed
        if(jButton8.isEnabled())
        {
            setSubTipo(8);
        }
    }//GEN-LAST:event_jButton8MousePressed

    private void jButton9MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MousePressed
        if(jButton9.isEnabled())
        {
            setSubTipo(9);
        }
    }//GEN-LAST:event_jButton9MousePressed

    private void jButton11MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MousePressed
        if(jButton11.isEnabled())
        {
            setSubTipo(11);        
        }
    }//GEN-LAST:event_jButton11MousePressed

    private void jButton10MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MousePressed
        if(jButton10.isEnabled())
        {
            setSubTipo(10);
        }
    }//GEN-LAST:event_jButton10MousePressed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        if(evt!=null)
        {
            setSubTipo(-1);
        }        
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeDialog
    
    /**
     * @param args the command line arguments
     *DEBUG
    public static void main(String args[]) {
        new SubBlock(new javax.swing.JFrame(), true, 5).show();
    }*/
    
    /** Getter for property subTipo.
     * @return Value of property subTipo.
     */
    public int getSubTipo() {
        return this.subTipo;
    }    
    
    /** Setter for property subTipo.
     * @param subTipo New value of property subTipo.
     */
    public void setSubTipo(int subTipo) {
        this.subTipo = subTipo;
        if(subTipo==-1)
        {
            return;
        }
        switch(subTipo)
        {
            case 0:break;
            case 1:jButtonB.setIcon(image1);break;
            case 2:jButtonB.setIcon(image2);break;
            case 3:jButtonB.setIcon(image3);break;
            case 4:jButtonB.setIcon(image4);break;
            case 5:jButtonB.setIcon(image5);break;
            case 6:jButtonB.setIcon(image6);break;
            case 8:jButtonB.setIcon(image8);break;
            case 9:jButtonB.setIcon(image9);break;
            case 10:jButtonB.setIcon(image10);break;
            case 11:jButtonB.setIcon(image11);break;
            case 12:jButtonB.setIcon(image12);break;
            case 13:jButtonB.setIcon(image13);break;
            case 14:jButtonB.setIcon(image14);break;
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanelOkCancel;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButtonA;
    private javax.swing.JPanel jPanelChange;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButtonOk;
    private javax.swing.JButton jButton13;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonB;
    // End of variables declaration//GEN-END:variables
    private javax.swing.ImageIcon image10 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/atribui.gif"));
    private javax.swing.ImageIcon image13 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/compAndBin.gif"));
    private javax.swing.ImageIcon image12 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/compAndInt.gif"));
    private javax.swing.ImageIcon image2 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/compBin.gif"));    
    private javax.swing.ImageIcon image1 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/compInt.gif"));    
    private javax.swing.ImageIcon image5 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/direita.gif"));    
    private javax.swing.ImageIcon image6 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/esquerda.gif"));
    private javax.swing.ImageIcon image3 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/frente.gif"));
    private javax.swing.ImageIcon image14 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/ivpwm.gif"));
    private javax.swing.ImageIcon image11 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/incdec+.gif"));
    private javax.swing.ImageIcon image4 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/marchatras.gif"));
    private javax.swing.ImageIcon image8 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/pwm.gif"));    
    private javax.swing.ImageIcon image9 = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/setreset.gif"));    
    
    /** Holds value of property subTipo. */
    private int subTipo=0;
    
}
