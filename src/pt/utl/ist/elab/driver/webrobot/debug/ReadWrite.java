/*
 * ReadWrite.java
 *
 * Created on 7 de Abril de 2003, 18:54
 */

package pt.utl.ist.elab.driver.webrobot.debug;

/**
 *
 * @author  Andr�
 */
public class ReadWrite extends javax.swing.JFrame {
    
    private pt.utl.ist.elab.driver.webrobot.serial.SerialComm serialComm;
    
    /** Creates new form ReadWrite */
    public ReadWrite()
    {
        serialComm=new pt.utl.ist.elab.driver.webrobot.serial.SerialComm(this);
        initComponents();
    }
    
    public ReadWrite(boolean asDriverDebug)
    {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelPWMs = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanelRW = new javax.swing.JPanel();
        jButtonWrite = new javax.swing.JButton();
        jButtonRead = new javax.swing.JButton();
        jPanelSensors = new javax.swing.JPanel();
        jPanelIRS = new javax.swing.JPanel();
        jProgressBarIR0 = new javax.swing.JProgressBar();
        jTextFieldIR0 = new javax.swing.JTextField();
        jProgressBarIR1 = new javax.swing.JProgressBar();
        jTextFieldIR1 = new javax.swing.JTextField();
        jProgressBarIR2 = new javax.swing.JProgressBar();
        jTextFieldIR2 = new javax.swing.JTextField();
        jProgressBarIR3 = new javax.swing.JProgressBar();
        jTextFieldIR3 = new javax.swing.JTextField();
        jProgressBarIR4 = new javax.swing.JProgressBar();
        jTextFieldIR4 = new javax.swing.JTextField();
        jProgressBarIR5 = new javax.swing.JProgressBar();
        jTextFieldIR5 = new javax.swing.JTextField();
        jProgressBarIR6 = new javax.swing.JProgressBar();
        jTextFieldIR6 = new javax.swing.JTextField();
        jProgressBarIR7 = new javax.swing.JProgressBar();
        jTextFieldIR7 = new javax.swing.JTextField();
        jPanelPortB = new javax.swing.JPanel();
        jRadioButtonb0 = new javax.swing.JRadioButton();
        jRadioButtonb1 = new javax.swing.JRadioButton();
        jRadioButtonb2 = new javax.swing.JRadioButton();
        jRadioButtonb3 = new javax.swing.JRadioButton();
        jRadioButtonb4 = new javax.swing.JRadioButton();
        jRadioButtonb5 = new javax.swing.JRadioButton();
        jRadioButtonb6 = new javax.swing.JRadioButton();
        jRadioButtonb7 = new javax.swing.JRadioButton();
        jPanelPortA = new javax.swing.JPanel();
        jTextFieldA1 = new javax.swing.JTextField();
        jTextFieldA2 = new javax.swing.JTextField();
        jTextFieldA3 = new javax.swing.JTextField();
        jTextFieldA4 = new javax.swing.JTextField();

        setTitle("Debug state machine");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jTextField1.setColumns(3);
        jTextField1.setText("127");
        jPanelPWMs.add(jTextField1);

        jTextField2.setColumns(3);
        jTextField2.setText("127");
        jPanelPWMs.add(jTextField2);

        getContentPane().add(jPanelPWMs, java.awt.BorderLayout.NORTH);

        jButtonWrite.setText("Write");
        jButtonWrite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWriteActionPerformed(evt);
            }
        });

        jPanelRW.add(jButtonWrite);

        jButtonRead.setText("Read");
        jButtonRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReadActionPerformed(evt);
            }
        });

        jPanelRW.add(jButtonRead);

        getContentPane().add(jPanelRW, java.awt.BorderLayout.CENTER);

        jPanelSensors.setLayout(new java.awt.BorderLayout());

        jPanelIRS.setLayout(new java.awt.GridBagLayout());

        jPanelIRS.setBorder(new javax.swing.border.TitledBorder("Infra-Vermelhos"));
        jProgressBarIR0.setMaximum(255);
        jProgressBarIR0.setBorder(new javax.swing.border.TitledBorder("IR0"));
        jProgressBarIR0.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR0StateChanged(evt);
            }
        });

        jPanelIRS.add(jProgressBarIR0, new java.awt.GridBagConstraints());

        jTextFieldIR0.setColumns(3);
        jPanelIRS.add(jTextFieldIR0, new java.awt.GridBagConstraints());

        jProgressBarIR1.setMaximum(255);
        jProgressBarIR1.setBorder(new javax.swing.border.TitledBorder("IR1"));
        jProgressBarIR1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR1StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        jPanelIRS.add(jProgressBarIR1, gridBagConstraints);

        jTextFieldIR1.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanelIRS.add(jTextFieldIR1, gridBagConstraints);

        jProgressBarIR2.setMaximum(255);
        jProgressBarIR2.setBorder(new javax.swing.border.TitledBorder("IR2"));
        jProgressBarIR2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR2StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanelIRS.add(jProgressBarIR2, gridBagConstraints);

        jTextFieldIR2.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanelIRS.add(jTextFieldIR2, gridBagConstraints);

        jProgressBarIR3.setMaximum(255);
        jProgressBarIR3.setBorder(new javax.swing.border.TitledBorder("IR3"));
        jProgressBarIR3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR3StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        jPanelIRS.add(jProgressBarIR3, gridBagConstraints);

        jTextFieldIR3.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanelIRS.add(jTextFieldIR3, gridBagConstraints);

        jProgressBarIR4.setMaximum(255);
        jProgressBarIR4.setBorder(new javax.swing.border.TitledBorder("IR4"));
        jProgressBarIR4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR4StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        jPanelIRS.add(jProgressBarIR4, gridBagConstraints);

        jTextFieldIR4.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanelIRS.add(jTextFieldIR4, gridBagConstraints);

        jProgressBarIR5.setMaximum(255);
        jProgressBarIR5.setBorder(new javax.swing.border.TitledBorder("IR5"));
        jProgressBarIR5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR5StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        jPanelIRS.add(jProgressBarIR5, gridBagConstraints);

        jTextFieldIR5.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        jPanelIRS.add(jTextFieldIR5, gridBagConstraints);

        jProgressBarIR6.setMaximum(255);
        jProgressBarIR6.setBorder(new javax.swing.border.TitledBorder("IR6"));
        jProgressBarIR6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR6StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        jPanelIRS.add(jProgressBarIR6, gridBagConstraints);

        jTextFieldIR6.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        jPanelIRS.add(jTextFieldIR6, gridBagConstraints);

        jProgressBarIR7.setMaximum(255);
        jProgressBarIR7.setBorder(new javax.swing.border.TitledBorder("IR7"));
        jProgressBarIR7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBarIR7StateChanged(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        jPanelIRS.add(jProgressBarIR7, gridBagConstraints);

        jTextFieldIR7.setColumns(3);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        jPanelIRS.add(jTextFieldIR7, gridBagConstraints);

        jPanelSensors.add(jPanelIRS, java.awt.BorderLayout.CENTER);

        jPanelPortB.setLayout(new java.awt.GridLayout(8, 0));

        jPanelPortB.setBorder(new javax.swing.border.TitledBorder("PORT B"));
        jRadioButtonb0.setText("b0");
        jPanelPortB.add(jRadioButtonb0);

        jRadioButtonb1.setText("b1");
        jPanelPortB.add(jRadioButtonb1);

        jRadioButtonb2.setText("b2");
        jPanelPortB.add(jRadioButtonb2);

        jRadioButtonb3.setText("b3");
        jPanelPortB.add(jRadioButtonb3);

        jRadioButtonb4.setText("b4");
        jPanelPortB.add(jRadioButtonb4);

        jRadioButtonb5.setText("b5");
        jPanelPortB.add(jRadioButtonb5);

        jRadioButtonb6.setText("b6");
        jPanelPortB.add(jRadioButtonb6);

        jRadioButtonb7.setText("b7");
        jPanelPortB.add(jRadioButtonb7);

        jPanelSensors.add(jPanelPortB, java.awt.BorderLayout.WEST);

        jPanelPortA.setLayout(new java.awt.GridLayout(4, 0));

        jPanelPortA.setBorder(new javax.swing.border.TitledBorder("PORT A"));
        jTextFieldA1.setColumns(4);
        jTextFieldA1.setBorder(new javax.swing.border.TitledBorder("A1"));
        jPanelPortA.add(jTextFieldA1);

        jTextFieldA2.setColumns(4);
        jTextFieldA2.setBorder(new javax.swing.border.TitledBorder("A2"));
        jPanelPortA.add(jTextFieldA2);

        jTextFieldA3.setColumns(4);
        jTextFieldA3.setBorder(new javax.swing.border.TitledBorder("A3"));
        jPanelPortA.add(jTextFieldA3);

        jTextFieldA4.setColumns(4);
        jTextFieldA4.setBorder(new javax.swing.border.TitledBorder("A4"));
        jPanelPortA.add(jTextFieldA4);

        jPanelSensors.add(jPanelPortA, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanelSensors, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents

    private void jProgressBarIR7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR7StateChanged
        jTextFieldIR7.setText(""+jProgressBarIR7.getValue());
    }//GEN-LAST:event_jProgressBarIR7StateChanged

    private void jProgressBarIR6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR6StateChanged
        jTextFieldIR6.setText(""+jProgressBarIR6.getValue());
    }//GEN-LAST:event_jProgressBarIR6StateChanged

    private void jProgressBarIR5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR5StateChanged
        jTextFieldIR5.setText(""+jProgressBarIR5.getValue());
    }//GEN-LAST:event_jProgressBarIR5StateChanged

    private void jProgressBarIR4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR4StateChanged
        jTextFieldIR4.setText(""+jProgressBarIR4.getValue());
    }//GEN-LAST:event_jProgressBarIR4StateChanged

    private void jProgressBarIR3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR3StateChanged
        jTextFieldIR3.setText(""+jProgressBarIR3.getValue());
    }//GEN-LAST:event_jProgressBarIR3StateChanged

    private void jProgressBarIR2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR2StateChanged
        jTextFieldIR2.setText(""+jProgressBarIR2.getValue());
    }//GEN-LAST:event_jProgressBarIR2StateChanged

    private void jProgressBarIR1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR1StateChanged
        jTextFieldIR1.setText(""+jProgressBarIR1.getValue());
    }//GEN-LAST:event_jProgressBarIR1StateChanged

    private void jProgressBarIR0StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBarIR0StateChanged
        jTextFieldIR0.setText(""+jProgressBarIR0.getValue());
    }//GEN-LAST:event_jProgressBarIR0StateChanged

    private void jButtonReadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReadActionPerformed
        serialComm.write(83);
        serialComm.write(69);
        serialComm.write(78);
        serialComm.write(68);
        serialComm.setReadMode();
    }//GEN-LAST:event_jButtonReadActionPerformed

    private void jButtonWriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWriteActionPerformed
        serialComm.write(80);
        serialComm.write(87);
        serialComm.write(77);
        serialComm.write(new Integer(jTextField1.getText()).intValue());
        serialComm.write(new Integer(jTextField2.getText()).intValue());                
    }//GEN-LAST:event_jButtonWriteActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    public void setValues(int[] values)
    {
        for(int i=0;i<values.length;i++)
        {
            if(values[i]<0)
            {
                values[i]+=256;
            }
        }        
        jProgressBarIR0.setValue(values[0]);
        jProgressBarIR1.setValue(values[1]);
        jProgressBarIR2.setValue(values[2]);
        jProgressBarIR3.setValue(values[3]);
        jProgressBarIR4.setValue(values[4]);
        jProgressBarIR5.setValue(values[5]);
        jProgressBarIR6.setValue(values[6]);
        jProgressBarIR7.setValue(values[7]);
        if((values[8]&1)==1)
        {
            jRadioButtonb0.setSelected(true);
        }
        else
        {
            jRadioButtonb0.setSelected(false);
        }
        if((values[8]&2)==2)
        {
            jRadioButtonb1.setSelected(true);
        }
        else
        {
            jRadioButtonb1.setSelected(false);
        }
        if((values[8]&4)==4)
        {
            jRadioButtonb2.setSelected(true);
        }
        else
        {
            jRadioButtonb2.setSelected(false);
        }
        if((values[8]&8)==8)
        {
            jRadioButtonb3.setSelected(true);
        }
        else
        {
            jRadioButtonb3.setSelected(false);
        }
        if((values[8]&16)==16)
        {
            jRadioButtonb4.setSelected(true);
        }
        else
        {
            jRadioButtonb4.setSelected(false);
        }
        if((values[8]&32)==32)
        {
            jRadioButtonb5.setSelected(true);
        }
        else
        {
            jRadioButtonb5.setSelected(false);
        }
        if((values[8]&64)==64)
        {
            jRadioButtonb6.setSelected(true);
        }
        else
        {
            jRadioButtonb6.setSelected(false);
        }
        if((values[8]&128)==128)
        {
            jRadioButtonb7.setSelected(true);
        }
        else
        {
            jRadioButtonb7.setSelected(false);
        }  
        jTextFieldA1.setText(""+values[9]);
        jTextFieldA2.setText(""+values[10]);
        jTextFieldA3.setText(""+values[11]);
        jTextFieldA4.setText(""+values[12]);
    }

    public void setSerialText(String text)
    {
        //jTextArea1.setText(text+"\n");
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new ReadWrite().show();
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRead;
    private javax.swing.JButton jButtonWrite;
    private javax.swing.JPanel jPanelIRS;
    private javax.swing.JPanel jPanelPWMs;
    private javax.swing.JPanel jPanelPortA;
    private javax.swing.JPanel jPanelPortB;
    private javax.swing.JPanel jPanelRW;
    private javax.swing.JPanel jPanelSensors;
    private javax.swing.JProgressBar jProgressBarIR0;
    private javax.swing.JProgressBar jProgressBarIR1;
    private javax.swing.JProgressBar jProgressBarIR2;
    private javax.swing.JProgressBar jProgressBarIR3;
    private javax.swing.JProgressBar jProgressBarIR4;
    private javax.swing.JProgressBar jProgressBarIR5;
    private javax.swing.JProgressBar jProgressBarIR6;
    private javax.swing.JProgressBar jProgressBarIR7;
    private javax.swing.JRadioButton jRadioButtonb0;
    private javax.swing.JRadioButton jRadioButtonb1;
    private javax.swing.JRadioButton jRadioButtonb2;
    private javax.swing.JRadioButton jRadioButtonb3;
    private javax.swing.JRadioButton jRadioButtonb4;
    private javax.swing.JRadioButton jRadioButtonb5;
    private javax.swing.JRadioButton jRadioButtonb6;
    private javax.swing.JRadioButton jRadioButtonb7;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldA1;
    private javax.swing.JTextField jTextFieldA2;
    private javax.swing.JTextField jTextFieldA3;
    private javax.swing.JTextField jTextFieldA4;
    private javax.swing.JTextField jTextFieldIR0;
    private javax.swing.JTextField jTextFieldIR1;
    private javax.swing.JTextField jTextFieldIR2;
    private javax.swing.JTextField jTextFieldIR3;
    private javax.swing.JTextField jTextFieldIR4;
    private javax.swing.JTextField jTextFieldIR5;
    private javax.swing.JTextField jTextFieldIR6;
    private javax.swing.JTextField jTextFieldIR7;
    // End of variables declaration//GEN-END:variables
    
}
