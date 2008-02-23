/*
 * WebRobotConfigurator.java
 *
 * Created on 4 de Maio de 2003, 19:23
 */

package pt.utl.ist.elab.driver.webrobot.configs;

/**
 *
 * @author  Andre
 */

import pt.utl.ist.elab.driver.webrobot.debug.*;

public class WebRobotConfigurator extends javax.swing.JFrame {

    /**Properties stuff...*/     
    java.io.InputStream is=null;
    java.io.File propFile=null;
    String resourceLocation=null;
    java.util.Properties props=null;
            
    /** Creates new form WebRobotConfigurator */
    public WebRobotConfigurator() {
        initComponents();
        readProps();
    }
    
    private void readProps()
    {
        resourceLocation="WebRobotProps.properties";
        //resourceLocation=getClass().getResource("/pt/utl/ist/elab/driver/webrobot/configs/WebRobotProps.properties")
        propFile=new java.io.File(resourceLocation);
        try
        {
            is=new java.io.FileInputStream(propFile);
            props=new java.util.Properties();
            props.load(is);            
            is.close();
        }
         catch(java.io.FileNotFoundException fnfe)
        {
            Debugger.print(this,"Couldn't found the file...\n"+fnfe);
            resourceLocation=getClass().getResource("/pt/utl/ist/elab/driver/webrobot/configs/WebRobotProps.properties").getFile();            
            try
            {
                propFile=new java.io.File(resourceLocation);
                is=new java.io.FileInputStream(propFile);
                props=new java.util.Properties();
                props.load(is);            
                is.close();
            }            
            catch(java.io.FileNotFoundException fnfe2)
            {          
                Debugger.print(this,"Couldn't found the file...\n"+fnfe2);
            }  
            catch(java.io.IOException ioe2)
            {
                Debugger.print(this,"Exception...\n"+ioe2);
            }                              
        }
        catch(java.io.IOException ioe)
        {
            Debugger.print(this,"Exception...\n"+ioe);
        }                                  
        jComboBoxPort.setSelectedItem(props.getProperty("comName"));
        jTextFieldBaud.setText(props.getProperty("baud"));
        jComboBoxDB.setSelectedIndex(Integer.parseInt(props.getProperty("databits"),10)-5);
        jComboBoxParity.setSelectedIndex(Integer.parseInt(props.getProperty("parity"),10));
        jComboBoxSB.setSelectedIndex(Integer.parseInt(props.getProperty("stopbits"),10)-1);            
        int fc=Integer.parseInt(props.getProperty("flowcontrol"),10);
        if(fc==4)
        {
            fc=3;
        }
        else if(fc==8)
        {
            fc=4;
        }
        jComboBoxFC.setSelectedIndex(fc);
        jTextFieldI0.setText(props.getProperty("i0parked"));
        jTextFieldI1.setText(props.getProperty("i1parked"));
        jTextFieldI2.setText(props.getProperty("i2parked"));
        jTextFieldI3.setText(props.getProperty("i3parked"));
        jTextFieldI4.setText(props.getProperty("i4parked"));
        jTextFieldI5.setText(props.getProperty("i5parked"));
        jTextFieldI6.setText(props.getProperty("i6parked"));
        jTextFieldI7.setText(props.getProperty("i7parked"));
        jTextFieldDE.setText(props.getProperty("expDuration"));
        jTextFieldVF.setText(props.getProperty("velAhead"));
        jTextFieldVA.setText(props.getProperty("velAback"));
        jTextFieldVB.setText(props.getProperty("batCheck"));
        jTextFieldBMin.setText(props.getProperty("batDownVal"));
        jTextFieldBMax.setText(props.getProperty("batFullVal"));
        jComboBoxLMTI0.setSelectedItem(props.getProperty("I0LMT"));
        jComboBoxLMTI1.setSelectedItem(props.getProperty("I1LMT"));
        jComboBoxLMTI2.setSelectedItem(props.getProperty("I2LMT"));
        jComboBoxLMTI3.setSelectedItem(props.getProperty("I3LMT"));
        jComboBoxLMTI4.setSelectedItem(props.getProperty("I4LMT"));
        jComboBoxLMTI5.setSelectedItem(props.getProperty("I5LMT"));
        jComboBoxLMTI6.setSelectedItem(props.getProperty("I6LMT"));
        jComboBoxLMTI7.setSelectedItem(props.getProperty("I7LMT"));
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        jPanelSP = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxPort = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldBaud = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBoxDB = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxParity = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        jComboBoxSB = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxFC = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanelStateMachine = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldVF = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldVA = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldI0 = new javax.swing.JTextField();
        jLabel91 = new javax.swing.JLabel();
        jTextFieldI1 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldI7 = new javax.swing.JTextField();
        jTextFieldI6 = new javax.swing.JTextField();
        jTextFieldI5 = new javax.swing.JTextField();
        jTextFieldI4 = new javax.swing.JTextField();
        jTextFieldI3 = new javax.swing.JTextField();
        jTextFieldI2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldVB = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldBMin = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldBMax = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldDE = new javax.swing.JTextField();
        jComboBoxLMTI0 = new javax.swing.JComboBox();
        jComboBoxLMTI1 = new javax.swing.JComboBox();
        jComboBoxLMTI2 = new javax.swing.JComboBox();
        jComboBoxLMTI3 = new javax.swing.JComboBox();
        jComboBoxLMTI4 = new javax.swing.JComboBox();
        jComboBoxLMTI5 = new javax.swing.JComboBox();
        jComboBoxLMTI6 = new javax.swing.JComboBox();
        jComboBoxLMTI7 = new javax.swing.JComboBox();

        setTitle("Web-Robot server config");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jPanelSP.setLayout(new java.awt.GridBagLayout());

        jPanelSP.setBorder(new javax.swing.border.TitledBorder("Porta s\u00e9rie"));
        jLabel1.setText("Port:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 50, 0, 0);
        jPanelSP.add(jLabel1, gridBagConstraints);

        jComboBoxPort.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "COM1", "COM2", "COM3", "COM4" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 0);
        jPanelSP.add(jComboBoxPort, gridBagConstraints);

        jLabel2.setText("Baud:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 45, 0, 0);
        jPanelSP.add(jLabel2, gridBagConstraints);

        jTextFieldBaud.setColumns(5);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanelSP.add(jTextFieldBaud, gridBagConstraints);

        jLabel3.setText("Databits:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(14, 27, 0, 0);
        jPanelSP.add(jLabel3, gridBagConstraints);

        jComboBoxDB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "6", "7", "8" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanelSP.add(jComboBoxDB, gridBagConstraints);

        jLabel4.setText("Parity:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(13, 40, 0, 0);
        jPanelSP.add(jLabel4, gridBagConstraints);

        jComboBoxParity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "ODD", "EVEN", "MARK", "SPACE" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanelSP.add(jComboBoxParity, gridBagConstraints);

        jLabel5.setText("StopBits:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(13, 25, 0, 0);
        jPanelSP.add(jLabel5, gridBagConstraints);

        jComboBoxSB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "1.5" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanelSP.add(jComboBoxSB, gridBagConstraints);

        jLabel6.setText("FlowControl:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(13, 7, 0, 0);
        jPanelSP.add(jLabel6, gridBagConstraints);

        jComboBoxFC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NONE", "RTS_CTS_IN", "RTS_CTS_OUT", "XON_XOFF_IN", "XON_XOFF_OUT" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 0);
        jPanelSP.add(jComboBoxFC, gridBagConstraints);

        getContentPane().add(jPanelSP, java.awt.BorderLayout.CENTER);

        jButton1.setText("Gravar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton1);

        jButton2.setText("Sair");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel1.add(jButton2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        jPanelStateMachine.setLayout(new java.awt.GridBagLayout());

        jPanelStateMachine.setBorder(new javax.swing.border.TitledBorder("Parametros gerais"));
        jLabel7.setText("Velocidade frente [128,255]:");
        jLabel7.setToolTipText("Quando o utilizador escolhe um dos blocos para fazer o robot andar x segundos, qual a velocidade a aplicar as rodas?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 16, 0, 0);
        jPanelStateMachine.add(jLabel7, gridBagConstraints);

        jTextFieldVF.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldVF, gridBagConstraints);

        jLabel8.setText("Velocidade \"marcha atr\u00e1s\":");
        jLabel8.setToolTipText("Quando o utilizador escolhe um dos blocos para fazer o robot andar x segundos, qual a velocidade a aplicar as rodas?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 16, 0, 0);
        jPanelStateMachine.add(jLabel8, gridBagConstraints);

        jTextFieldVA.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldVA, gridBagConstraints);

        jLabel9.setText("I0 parqueado:");
        jLabel9.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel9, gridBagConstraints);

        jTextFieldI0.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI0, gridBagConstraints);

        jLabel91.setText("I1 parqueado:");
        jLabel91.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel91, gridBagConstraints);

        jTextFieldI1.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI1, gridBagConstraints);

        jLabel10.setText("I2 parqueado:");
        jLabel10.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel10, gridBagConstraints);

        jLabel11.setText("I3 parqueado:");
        jLabel11.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel11, gridBagConstraints);

        jLabel12.setText("I4 parqueado:");
        jLabel12.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel12, gridBagConstraints);

        jLabel13.setText("I7 parqueado:");
        jLabel13.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel13, gridBagConstraints);

        jLabel14.setText("I6 parqueado:");
        jLabel14.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel14, gridBagConstraints);

        jLabel15.setText("I5 parqueado:");
        jLabel15.setToolTipText("O valor m\u00e1ximo que o sensor pode ter quando o robot est\u00e1 estacionado");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(12, 96, 0, 0);
        jPanelStateMachine.add(jLabel15, gridBagConstraints);

        jTextFieldI7.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI7, gridBagConstraints);

        jTextFieldI6.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI6, gridBagConstraints);

        jTextFieldI5.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI5, gridBagConstraints);

        jTextFieldI4.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI4, gridBagConstraints);

        jTextFieldI3.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI3, gridBagConstraints);

        jTextFieldI2.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldI2, gridBagConstraints);

        jLabel16.setText("Verificar bateria cada (s):");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 30, 0, 0);
        jPanelStateMachine.add(jLabel16, gridBagConstraints);

        jTextFieldVB.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldVB, gridBagConstraints);

        jLabel17.setText("Valor minimo bateria [0,255]:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jLabel17, gridBagConstraints);

        jTextFieldBMin.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldBMin, gridBagConstraints);

        jLabel18.setText("Valor m\u00e1ximo bateria [0,255]:");
        jLabel18.setToolTipText("Nao carregar mais a partir deste valor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 0);
        jPanelStateMachine.add(jLabel18, gridBagConstraints);

        jTextFieldBMax.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldBMax, gridBagConstraints);

        jLabel19.setText("Dura\u00e7\u00e3o da experi\u00eancia:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 34, 0, 0);
        jPanelStateMachine.add(jLabel19, gridBagConstraints);

        jTextFieldDE.setColumns(4);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 15;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 0);
        jPanelStateMachine.add(jTextFieldDE, gridBagConstraints);

        jComboBoxLMTI0.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI0, gridBagConstraints);

        jComboBoxLMTI1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI1, gridBagConstraints);

        jComboBoxLMTI2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI2, gridBagConstraints);

        jComboBoxLMTI3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI3, gridBagConstraints);

        jComboBoxLMTI4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI4, gridBagConstraints);

        jComboBoxLMTI5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI5, gridBagConstraints);

        jComboBoxLMTI6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI6, gridBagConstraints);

        jComboBoxLMTI7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "<", ">" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 14;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        jPanelStateMachine.add(jComboBoxLMTI7, gridBagConstraints);

        getContentPane().add(jPanelStateMachine, java.awt.BorderLayout.EAST);

        pack();
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        saveProps();   
        System.out.println("As alteracoes foram gravadas.\nPor favor reinicie o servidor...");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        exitForm(null);
    }//GEN-LAST:event_jButton2ActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.hide();
    }//GEN-LAST:event_exitForm
            
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new WebRobotConfigurator().show();
    }
    
    private void saveProps()
    {
        props.setProperty("baud",jTextFieldBaud.getText());
        props.setProperty("comName",(String)jComboBoxPort.getSelectedItem());
        props.setProperty("databits",""+(jComboBoxDB.getSelectedIndex()+5));
        props.setProperty("parity",""+jComboBoxParity.getSelectedIndex());
        props.setProperty("stopbits",""+(jComboBoxSB.getSelectedIndex()+1));
        int fc=jComboBoxFC.getSelectedIndex();
        if(fc==3)
        {
            fc=4;
        }
        else if(fc==4)
        {
            fc=8;
        }
        props.setProperty("flowcontrol",""+fc);
        props.setProperty("i0parked",jTextFieldI0.getText());
        props.setProperty("i1parked",jTextFieldI1.getText());
        props.setProperty("i2parked",jTextFieldI2.getText());
        props.setProperty("i3parked",jTextFieldI3.getText());
        props.setProperty("i4parked",jTextFieldI4.getText());
        props.setProperty("i5parked",jTextFieldI5.getText());
        props.setProperty("i6parked",jTextFieldI6.getText());
        props.setProperty("i7parked",jTextFieldI7.getText());
        props.setProperty("expDuration",jTextFieldDE.getText());
        props.setProperty("velAhead",jTextFieldVF.getText());
        props.setProperty("velAback",jTextFieldVA.getText());
        props.setProperty("batCheck",jTextFieldVB.getText());
        props.setProperty("batDownVal",jTextFieldBMin.getText());
        props.setProperty("batFullVal",jTextFieldBMax.getText());     
        props.setProperty("I0LMT",(String)jComboBoxLMTI0.getSelectedItem());        
        props.setProperty("I1LMT",(String)jComboBoxLMTI1.getSelectedItem());        
        props.setProperty("I2LMT",(String)jComboBoxLMTI2.getSelectedItem());        
        props.setProperty("I3LMT",(String)jComboBoxLMTI3.getSelectedItem());        
        props.setProperty("I4LMT",(String)jComboBoxLMTI4.getSelectedItem());        
        props.setProperty("I5LMT",(String)jComboBoxLMTI5.getSelectedItem());        
        props.setProperty("I6LMT",(String)jComboBoxLMTI6.getSelectedItem());        
        props.setProperty("I7LMT",(String)jComboBoxLMTI7.getSelectedItem());        
        java.io.FileOutputStream fos=null;
        try
        {
            fos=new java.io.FileOutputStream(propFile);            
            props.store(fos,"Elab:Web-Robot Server properties");
            fos.close();
        }
        catch(java.io.IOException ioe)
        {
            Debugger.print(this,"Nao consegui escrever as propriedades\n"+ioe);
        }        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JTextField jTextFieldI7;
    private javax.swing.JComboBox jComboBoxLMTI3;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JComboBox jComboBoxLMTI4;
    private javax.swing.JTextField jTextFieldI3;
    private javax.swing.JComboBox jComboBoxSB;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox jComboBoxLMTI7;
    private javax.swing.JPanel jPanelStateMachine;
    private javax.swing.JTextField jTextFieldVB;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JTextField jTextFieldI0;
    private javax.swing.JTextField jTextFieldBMax;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldI6;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JComboBox jComboBoxParity;
    private javax.swing.JTextField jTextFieldI4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JTextField jTextFieldVF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JComboBox jComboBoxLMTI6;
    private javax.swing.JTextField jTextFieldI1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton jButton1;
    private javax.swing.JTextField jTextFieldI2;
    private javax.swing.JComboBox jComboBoxLMTI2;
    private javax.swing.JTextField jTextFieldBMin;
    private javax.swing.JComboBox jComboBoxLMTI5;
    private javax.swing.JComboBox jComboBoxPort;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JTextField jTextFieldBaud;
    private javax.swing.JComboBox jComboBoxFC;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JTextField jTextFieldI5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanelSP;
    private javax.swing.JComboBox jComboBoxLMTI0;
    private javax.swing.JTextField jTextFieldDE;
    private javax.swing.JComboBox jComboBoxDB;
    private javax.swing.JComboBox jComboBoxLMTI1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JTextField jTextFieldVA;
    private javax.swing.JLabel jLabel10;
    // End of variables declaration//GEN-END:variables
    
}
