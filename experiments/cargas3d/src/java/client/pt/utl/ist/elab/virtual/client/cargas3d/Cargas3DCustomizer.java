/*
 * Cargas3DCustomizer.java
 *
 * Criado a 18 de Mar�o de 2005, 11:29
 */

package pt.utl.ist.elab.virtual.client.cargas3d;
import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.InteractionEvent;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractionTargetElementPosition;
import org.opensourcephysics.displayejs.InteractiveCharge;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
/**
 *
 * @autor  n0dP2
 */
public class Cargas3DCustomizer extends javax.swing.JPanel implements com.linkare.rec.impl.client.customizer.ICustomizer{
    
    
    private PainelCargas painelCargas;
    
    public Cargas3DCustomizer() {
        Sistema.novaCarga(5f, 8f, 5f, -25f);
        Sistema.novaCarga(5f, 2f, 5f, 25f);
        painelCargas = new PainelCargas();
        initComponents();
    }
    
    //****************************REC********************************************/
    
    
    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    
    /** Registers ICustomizerListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addICustomizerListener(ICustomizerListener listener) {
        if (listenerList == null ) {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(ICustomizerListener.class, listener);
    }
    
    /** Removes ICustomizerListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeICustomizerListener(ICustomizerListener listener) {
        listenerList.remove(ICustomizerListener.class, listener);
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerCanceled() {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ICustomizerListener.class) {
                ((ICustomizerListener)listeners[i+1]).canceled();
            }
        }
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
     */
    private void fireICustomizerListenerDone() {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ICustomizerListener.class) {
                
                ((ICustomizerListener)listeners[i+1]).done();
            }
        }
    }
    
    
    private HardwareInfo hardwareInfo=null;
    private HardwareAcquisitionConfig acqConfig=null;
    
    public HardwareAcquisitionConfig getAcquisitionConfig() {
        return acqConfig;
    }
    
    //ESTE � PARA ALTERAR
    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig acqConfig) {
        //Aqui s�o fornecidos parametros do ultimo utilizador que fez a exp, e' bom manter!
        this.acqConfig=acqConfig;
        if(acqConfig!=null) {
            //            int xini = (int)(Float.parseFloat(acqConfig.getSelectedHardwareParameterValue("xini")));
            //            jSliderX0.setValue(xini);
            //            //etc...
        }
    }
    
    public void setHardwareInfo(HardwareInfo hardwareInfo) {
        this.hardwareInfo=hardwareInfo;
    }
    
    protected HardwareInfo getHardwareInfo() {
        return this.hardwareInfo;
    }
    
    public javax.swing.JComponent getCustomizerComponent() {
        return this;
    }
    
    public javax.swing.ImageIcon getCustomizerIcon() {
        return new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/virtual/client/cargas3d/resources/cargas3d_iconified.png"));
    }
    
    //ESTE � PARA ALTERAR
    public String getCustomizerTitle() {
        return ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.12","3D Charges Experiment Configuration Utility");
    }
    
    public javax.swing.JMenuBar getMenuBar() {
        return null;
    }
    
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        painelAdd = new javax.swing.JPanel();
        painelComum = new javax.swing.JPanel();
        labelX = new javax.swing.JLabel();
        labelY = new javax.swing.JLabel();
        labelZ = new javax.swing.JLabel();
        labelQ = new javax.swing.JLabel();
        textX = new javax.swing.JTextField();
        textY = new javax.swing.JTextField();
        textZ = new javax.swing.JTextField();
        textQ = new javax.swing.JTextField();
        labelXU = new javax.swing.JLabel();
        labelYU = new javax.swing.JLabel();
        labelZU = new javax.swing.JLabel();
        labelQU = new javax.swing.JLabel();
        labelInfoX = new javax.swing.JLabel();
        labelInfoY = new javax.swing.JLabel();
        labelInfoZ = new javax.swing.JLabel();
        labelInfoQ = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        painelEdit = new javax.swing.JPanel();
        painelComum1 = new javax.swing.JPanel();
        labelX1 = new javax.swing.JLabel();
        labelY1 = new javax.swing.JLabel();
        labelZ1 = new javax.swing.JLabel();
        labelQ1 = new javax.swing.JLabel();
        textX1 = new javax.swing.JTextField();
        textY1 = new javax.swing.JTextField();
        textZ1 = new javax.swing.JTextField();
        textQ1 = new javax.swing.JTextField();
        labelXU1 = new javax.swing.JLabel();
        labelYU1 = new javax.swing.JLabel();
        labelZU1 = new javax.swing.JLabel();
        labelQU1 = new javax.swing.JLabel();
        labelInfoX1 = new javax.swing.JLabel();
        labelInfoY1 = new javax.swing.JLabel();
        labelInfoZ1 = new javax.swing.JLabel();
        labelInfoQ1 = new javax.swing.JLabel();
        editOK = new javax.swing.JButton();
        editApagar = new javax.swing.JButton();
        painelTabs = new javax.swing.JTabbedPane();
        painelSistema = new javax.swing.JPanel();
        painelAddEdit = new javax.swing.JPanel();
        panelDefault = new javax.swing.JPanel();
        buttonOK = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();
        buttonDefault = new javax.swing.JButton();

        painelAdd.setBorder(new javax.swing.border.TitledBorder(null, ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.2","Add Charge"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14)));
        painelComum.setLayout(new java.awt.GridBagLayout());

        painelComum.setMaximumSize(new java.awt.Dimension(350, 200));
        painelComum.setMinimumSize(new java.awt.Dimension(350, 200));
        painelComum.setPreferredSize(new java.awt.Dimension(350, 200));
        labelX.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelX.setText("X");
        labelX.setMaximumSize(new java.awt.Dimension(35, 15));
        labelX.setMinimumSize(new java.awt.Dimension(35, 15));
        labelX.setPreferredSize(new java.awt.Dimension(35, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum.add(labelX, gridBagConstraints);

        labelY.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelY.setText("Y");
        labelY.setMaximumSize(new java.awt.Dimension(35, 15));
        labelY.setMinimumSize(new java.awt.Dimension(35, 15));
        labelY.setPreferredSize(new java.awt.Dimension(35, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum.add(labelY, gridBagConstraints);

        labelZ.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelZ.setText("Z");
        labelZ.setMaximumSize(new java.awt.Dimension(35, 15));
        labelZ.setMinimumSize(new java.awt.Dimension(35, 15));
        labelZ.setPreferredSize(new java.awt.Dimension(35, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum.add(labelZ, gridBagConstraints);

        labelQ.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.4","Charge"));
        labelQ.setMaximumSize(new java.awt.Dimension(40, 15));
        labelQ.setMinimumSize(new java.awt.Dimension(40, 15));
        labelQ.setPreferredSize(new java.awt.Dimension(40, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum.add(labelQ, gridBagConstraints);

        textX.setText("5.0");
        textX.setMinimumSize(new java.awt.Dimension(35, 20));
        textX.setPreferredSize(new java.awt.Dimension(40, 20));
        textX.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textXFocusLost(evt);
            }
        });

        painelComum.add(textX, new java.awt.GridBagConstraints());

        textY.setText("5.0");
        textY.setMinimumSize(new java.awt.Dimension(40, 20));
        textY.setPreferredSize(new java.awt.Dimension(40, 20));
        textY.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textYFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        painelComum.add(textY, gridBagConstraints);

        textZ.setText("5.0");
        textZ.setMinimumSize(new java.awt.Dimension(35, 20));
        textZ.setPreferredSize(new java.awt.Dimension(40, 20));
        textZ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textZFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        painelComum.add(textZ, gridBagConstraints);

        textQ.setText("5.0");
        textQ.setMinimumSize(new java.awt.Dimension(35, 20));
        textQ.setPreferredSize(new java.awt.Dimension(40, 20));
        textQ.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textQFocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        painelComum.add(textQ, gridBagConstraints);

        labelXU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelXU.setText("m");
        labelXU.setMaximumSize(new java.awt.Dimension(30, 15));
        labelXU.setMinimumSize(new java.awt.Dimension(30, 15));
        labelXU.setPreferredSize(new java.awt.Dimension(30, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum.add(labelXU, gridBagConstraints);

        labelYU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelYU.setText("m");
        labelYU.setMaximumSize(new java.awt.Dimension(30, 15));
        labelYU.setMinimumSize(new java.awt.Dimension(30, 15));
        labelYU.setPreferredSize(new java.awt.Dimension(30, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum.add(labelYU, gridBagConstraints);

        labelZU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelZU.setText("m");
        labelZU.setMaximumSize(new java.awt.Dimension(30, 15));
        labelZU.setMinimumSize(new java.awt.Dimension(30, 15));
        labelZU.setPreferredSize(new java.awt.Dimension(30, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum.add(labelZU, gridBagConstraints);

        labelQU.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelQU.setMaximumSize(new java.awt.Dimension(30, 15));
        labelQU.setMinimumSize(new java.awt.Dimension(30, 15));
        labelQU.setPreferredSize(new java.awt.Dimension(30, 15));
        labelQU.setText("\u03BCC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum.add(labelQU, gridBagConstraints);

        labelInfoX.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.6","position values"));
        labelInfoX.setMaximumSize(new java.awt.Dimension(100, 15));
        labelInfoX.setMinimumSize(new java.awt.Dimension(100, 15));
        labelInfoX.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum.add(labelInfoX, gridBagConstraints);

        labelInfoY.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.7","between"));
        labelInfoY.setMaximumSize(new java.awt.Dimension(100, 15));
        labelInfoY.setMinimumSize(new java.awt.Dimension(100, 15));
        labelInfoY.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum.add(labelInfoY, gridBagConstraints);

        labelInfoZ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfoZ.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.8","10 and -10"));
        labelInfoZ.setMaximumSize(new java.awt.Dimension(100, 15));
        labelInfoZ.setMinimumSize(new java.awt.Dimension(100, 15));
        labelInfoZ.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum.add(labelInfoZ, gridBagConstraints);

        labelInfoQ.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfoQ.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.9","between -25 and 25"));
        labelInfoQ.setMaximumSize(new java.awt.Dimension(120, 15));
        labelInfoQ.setMinimumSize(new java.awt.Dimension(120, 15));
        labelInfoQ.setPreferredSize(new java.awt.Dimension(120, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum.add(labelInfoQ, gridBagConstraints);

        painelAdd.add(painelComum);

        addButton.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.2","Add Charge"));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        painelAdd.add(addButton);

        painelEdit.setBorder(new javax.swing.border.TitledBorder(null, ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.3","Edit Charge"), javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14)));
        painelComum1.setLayout(new java.awt.GridBagLayout());

        painelComum1.setMaximumSize(new java.awt.Dimension(350, 200));
        painelComum1.setMinimumSize(new java.awt.Dimension(350, 200));
        painelComum1.setPreferredSize(new java.awt.Dimension(350, 200));
        labelX1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelX1.setText("X");
        labelX1.setMaximumSize(new java.awt.Dimension(35, 15));
        labelX1.setMinimumSize(new java.awt.Dimension(35, 15));
        labelX1.setPreferredSize(new java.awt.Dimension(35, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum1.add(labelX1, gridBagConstraints);

        labelY1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelY1.setText("Y");
        labelY1.setMaximumSize(new java.awt.Dimension(35, 15));
        labelY1.setMinimumSize(new java.awt.Dimension(35, 15));
        labelY1.setPreferredSize(new java.awt.Dimension(35, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum1.add(labelY1, gridBagConstraints);

        labelZ1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelZ1.setText("Z");
        labelZ1.setMaximumSize(new java.awt.Dimension(35, 15));
        labelZ1.setMinimumSize(new java.awt.Dimension(35, 15));
        labelZ1.setPreferredSize(new java.awt.Dimension(35, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum1.add(labelZ1, gridBagConstraints);

        labelQ1.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.4","Charge"));
        labelQ1.setMaximumSize(new java.awt.Dimension(40, 15));
        labelQ1.setMinimumSize(new java.awt.Dimension(40, 15));
        labelQ1.setPreferredSize(new java.awt.Dimension(40, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 3);
        painelComum1.add(labelQ1, gridBagConstraints);

        textX1.setText("5.0");
        textX1.setMinimumSize(new java.awt.Dimension(35, 20));
        textX1.setPreferredSize(new java.awt.Dimension(40, 20));
        textX1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textX1FocusLost(evt);
            }
        });

        painelComum1.add(textX1, new java.awt.GridBagConstraints());

        textY1.setText("5.0");
        textY1.setMinimumSize(new java.awt.Dimension(40, 20));
        textY1.setPreferredSize(new java.awt.Dimension(40, 20));
        textY1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textY1FocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        painelComum1.add(textY1, gridBagConstraints);

        textZ1.setText("5.0");
        textZ1.setMinimumSize(new java.awt.Dimension(35, 20));
        textZ1.setPreferredSize(new java.awt.Dimension(40, 20));
        textZ1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textZ1FocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        painelComum1.add(textZ1, gridBagConstraints);

        textQ1.setText("5.0");
        textQ1.setMinimumSize(new java.awt.Dimension(35, 20));
        textQ1.setPreferredSize(new java.awt.Dimension(40, 20));
        textQ1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                textQ1FocusLost(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        painelComum1.add(textQ1, gridBagConstraints);

        labelXU1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelXU1.setText("m");
        labelXU1.setMaximumSize(new java.awt.Dimension(30, 15));
        labelXU1.setMinimumSize(new java.awt.Dimension(30, 15));
        labelXU1.setPreferredSize(new java.awt.Dimension(30, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum1.add(labelXU1, gridBagConstraints);

        labelYU1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelYU1.setText("m");
        labelYU1.setMaximumSize(new java.awt.Dimension(30, 15));
        labelYU1.setMinimumSize(new java.awt.Dimension(30, 15));
        labelYU1.setPreferredSize(new java.awt.Dimension(30, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum1.add(labelYU1, gridBagConstraints);

        labelZU1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelZU1.setText("m");
        labelZU1.setMaximumSize(new java.awt.Dimension(30, 15));
        labelZU1.setMinimumSize(new java.awt.Dimension(30, 15));
        labelZU1.setPreferredSize(new java.awt.Dimension(30, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum1.add(labelZU1, gridBagConstraints);

        labelQU1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelQU1.setMaximumSize(new java.awt.Dimension(30, 15));
        labelQU1.setMinimumSize(new java.awt.Dimension(30, 15));
        labelQU1.setPreferredSize(new java.awt.Dimension(30, 15));
        labelQU1.setText("\u03BCC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        painelComum1.add(labelQU1, gridBagConstraints);

        labelInfoX1.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.6","position values"));
        labelInfoX1.setMaximumSize(new java.awt.Dimension(100, 15));
        labelInfoX1.setMinimumSize(new java.awt.Dimension(100, 15));
        labelInfoX1.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum1.add(labelInfoX1, gridBagConstraints);

        labelInfoY1.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.7","between"));
        labelInfoY1.setMaximumSize(new java.awt.Dimension(100, 15));
        labelInfoY1.setMinimumSize(new java.awt.Dimension(100, 15));
        labelInfoY1.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum1.add(labelInfoY1, gridBagConstraints);

        labelInfoZ1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfoZ1.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.8","10 and -10"));
        labelInfoZ1.setMaximumSize(new java.awt.Dimension(100, 15));
        labelInfoZ1.setMinimumSize(new java.awt.Dimension(100, 15));
        labelInfoZ1.setPreferredSize(new java.awt.Dimension(100, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum1.add(labelInfoZ1, gridBagConstraints);

        labelInfoQ1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        labelInfoQ1.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.9","between -25 and 25"));
        labelInfoQ1.setMaximumSize(new java.awt.Dimension(120, 15));
        labelInfoQ1.setMinimumSize(new java.awt.Dimension(120, 15));
        labelInfoQ1.setPreferredSize(new java.awt.Dimension(120, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        painelComum1.add(labelInfoQ1, gridBagConstraints);

        painelEdit.add(painelComum1);

        editOK.setText("OK");
        editOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editOKActionPerformed(evt);
            }
        });

        painelEdit.add(editOK);

        editApagar.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.5","remove charge"));
        editApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editApagarActionPerformed(evt);
            }
        });

        painelEdit.add(editApagar);

        setLayout(new java.awt.GridBagLayout());

        painelTabs.setMinimumSize(new java.awt.Dimension(400, 450));
        painelTabs.setPreferredSize(new java.awt.Dimension(400, 450));
        painelTabs.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                painelTabsStateChanged(evt);
            }
        });

        painelSistema.setLayout(new java.awt.BorderLayout());

        painelSistema.setMaximumSize(new java.awt.Dimension(400, 400));
        painelSistema.setMinimumSize(new java.awt.Dimension(400, 400));
        painelSistema.setPreferredSize(new java.awt.Dimension(400, 400));
        painelSistema.add(painelCargas);
        painelTabs.addTab(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.1","System"), painelSistema);

        painelAddEdit.setLayout(new java.awt.BorderLayout());

        painelAddEdit.add(painelAdd);
        painelTabs.addTab(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.2","Add Charge"), painelAddEdit);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(painelTabs, gridBagConstraints);

        panelDefault.setLayout(new java.awt.GridBagLayout());

        panelDefault.setMinimumSize(new java.awt.Dimension(400, 25));
        panelDefault.setPreferredSize(new java.awt.Dimension(400, 25));
        buttonOK.setText("OK");
        buttonOK.setMaximumSize(new java.awt.Dimension(130, 23));
        buttonOK.setMinimumSize(new java.awt.Dimension(130, 23));
        buttonOK.setPreferredSize(new java.awt.Dimension(130, 23));
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });

        panelDefault.add(buttonOK, new java.awt.GridBagConstraints());

        buttonCancel.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.cancel","Cancel"));
        buttonCancel.setMaximumSize(new java.awt.Dimension(130, 23));
        buttonCancel.setMinimumSize(new java.awt.Dimension(130, 23));
        buttonCancel.setPreferredSize(new java.awt.Dimension(130, 23));
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        panelDefault.add(buttonCancel, new java.awt.GridBagConstraints());

        buttonDefault.setText(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.dfc","Default Config"));
        buttonDefault.setMaximumSize(new java.awt.Dimension(140, 23));
        buttonDefault.setMinimumSize(new java.awt.Dimension(140, 23));
        buttonDefault.setPreferredSize(new java.awt.Dimension(140, 23));
        buttonDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonDefaultActionPerformed(evt);
            }
        });

        panelDefault.add(buttonDefault, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(panelDefault, gridBagConstraints);

    }//GEN-END:initComponents

    private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
        acqConfig.setTotalSamples(1);
        
//        acqConfig.setSelectedFrequency(
//                new Frequency((double)jSliderTBS.getValue(),
//                hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getMultiplier(),
//                hardwareInfo.getHardwareFrequencies(0).getMinimumFrequency().getFrequencyDefType()));
        acqConfig.getSelectedHardwareParameter("Sistema").setParameterValue(Sistema.sistemaToString());
        fireICustomizerListenerDone();
    }//GEN-LAST:event_buttonOKActionPerformed
    
    private void buttonDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonDefaultActionPerformed
        Sistema.sistema.clear();
        Sistema.novaCarga(5f, 8f, 5f, -25f);
        Sistema.novaCarga(5f, 2f, 5f, 25f);
        painelCargas.refresh();
        
    }//GEN-LAST:event_buttonDefaultActionPerformed
    
    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        fireICustomizerListenerCanceled();
    }//GEN-LAST:event_buttonCancelActionPerformed
    
    private void editApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editApagarActionPerformed
        Sistema.apagarCarga(Sistema.sistema.indexOf(painelCargas.interaction.carga));
        painelTabs.setSelectedIndex(0);
        painelCargas.refresh();
    }//GEN-LAST:event_editApagarActionPerformed
    
    private void editOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editOKActionPerformed
        
        Sistema.editarCarga(
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textX1,0f, 10f),
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textY1,0f, 10f),
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textZ1,0f, 10f),
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textQ1,-25f, 25f),
                Sistema.sistema.indexOf(painelCargas.interaction.carga));
        painelTabs.setSelectedIndex(0);
        painelCargas.refresh();
    }//GEN-LAST:event_editOKActionPerformed
    
    private void textQ1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textQ1FocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textQ1,-25f, 25f);
    }//GEN-LAST:event_textQ1FocusLost
    
    private void textZ1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textZ1FocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textZ1,0f, 10f);
    }//GEN-LAST:event_textZ1FocusLost
    
    private void textY1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textY1FocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textY1,0f, 10f);
    }//GEN-LAST:event_textY1FocusLost
    
    private void textX1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textX1FocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textX1,0f, 10f);
    }//GEN-LAST:event_textX1FocusLost
    
    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        Sistema.novaCarga(
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textX,0f, 10f),
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textY,0f, 10f),
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textZ,0f, 10f),
                pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textQ,-25f, 25f));
        painelTabs.setSelectedIndex(0);
        painelCargas.refresh();
    }//GEN-LAST:event_addButtonActionPerformed
    
    private void textQFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textQFocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textQ,-25f, 25f);
    }//GEN-LAST:event_textQFocusLost
    
    private void textZFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textZFocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textZ,0f, 10f);
    }//GEN-LAST:event_textZFocusLost
    
    private void textYFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textYFocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textY,0f, 10f);
    }//GEN-LAST:event_textYFocusLost
    
    private void textXFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_textXFocusLost
        pt.utl.ist.elab.client.virtual.guipack.GUtils.validateInput(textX,0f, 10f);
    }//GEN-LAST:event_textXFocusLost
    
    private void painelTabsStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_painelTabsStateChanged
        
        if(painelTabs.getSelectedIndex()==0 && painelTabs.getTabCount()==2){
            painelTabs.remove(1);
            painelTabs.add(painelAdd,1);
            painelTabs.setTitleAt(1, ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.2","Add Charge"));
        }
    }//GEN-LAST:event_painelTabsStateChanged
    class Interaction implements InteractionListener{
        private java.awt.Color cor;
        public InteractiveCharge carga;
        
        private InteractiveCharge getCarga(){
            return ((InteractiveCharge)((InteractionTargetElementPosition)(painelCargas.getInteractive())).getSource());
        }
        
        public void interactionPerformed(InteractionEvent _event) {
            
            if(_event.getID()==InteractionEvent.MOUSE_OVER){
                cor=(java.awt.Color)(getCarga().getStyle().getFillPattern());
                carga=getCarga();
                getCarga().getStyle().setFillPattern(java.awt.Color.YELLOW);
            }
            
            if(_event.getID()==InteractionEvent.MOUSE_LEFT){
                carga.getStyle().setFillPattern(cor);
            }
            
            if(_event.getID()==InteractionEvent.MOUSE_PRESSED_LEFT){
                painelTabs.remove(1);
                painelTabs.add(painelEdit,1);
                painelTabs.setSelectedIndex(1);
                painelTabs.setTitleAt(1, ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.3","Edit Charge"));
                setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                carga.getStyle().setFillPattern(cor);
                
                textX1.setText(Double.toString(pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondar(carga.getX(),2)));
                textY1.setText(Double.toString(pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondar(carga.getY(),2)));
                textZ1.setText(Double.toString(pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondar(carga.getZ(),2)));
                textQ1.setText(Float.toString(pt.utl.ist.elab.client.virtual.guipack.QMethods.arredondar(carga.getCharge(),2)));
            }
        }
    }
    
    class PainelCargas extends DrawingPanel3D{
        
        java.util.ArrayList sistema;
        Interaction interaction = new Interaction();
        
        public PainelCargas() {
            setMessage(ReCResourceBundle.findStringOrDefault("ReCExpCargas3D$rec.exp.customizer.title.11","Click on a charge to edit it."));
            setPreferredSize(new java.awt.Dimension(400,400));
            setPreferredMinMax(0,10,0,10,0,10);
            setBackground(java.awt.Color.white);
            setUseColorDepth(false);
            setDecorationType(DECORATION_AXES);
            setDisplayMode(DISPLAY_NO_PERSPECTIVE);
            setShowCoordinates(false);
            showAllTrackers(false);
            setAlpha(Math.PI/7);
            setBeta(Math.PI/8);
            setEnabledDrag(false);
            refresh();
            
        }
        public void refresh(){
            clear();
            sistema = Sistema.sistema;
            for (int i=0; i<sistema.size();i++){
                InteractiveCharge carga = (InteractiveCharge)sistema.get(i);
                carga.setEnabled(true);
                addDrawable(carga);
                carga.addListener(interaction);
            }
            repaint();
        }
    }
    
    public static void main(String args[]){
        javax.swing.JFrame frame = new javax.swing.JFrame();
        frame.getContentPane().add(new Cargas3DCustomizer());
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.pack();
        frame.show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonDefault;
    public javax.swing.JButton buttonOK;
    private javax.swing.JButton editApagar;
    private javax.swing.JButton editOK;
    private javax.swing.JLabel labelInfoQ;
    private javax.swing.JLabel labelInfoQ1;
    private javax.swing.JLabel labelInfoX;
    private javax.swing.JLabel labelInfoX1;
    private javax.swing.JLabel labelInfoY;
    private javax.swing.JLabel labelInfoY1;
    private javax.swing.JLabel labelInfoZ;
    private javax.swing.JLabel labelInfoZ1;
    private javax.swing.JLabel labelQ;
    private javax.swing.JLabel labelQ1;
    private javax.swing.JLabel labelQU;
    private javax.swing.JLabel labelQU1;
    private javax.swing.JLabel labelX;
    private javax.swing.JLabel labelX1;
    private javax.swing.JLabel labelXU;
    private javax.swing.JLabel labelXU1;
    private javax.swing.JLabel labelY;
    private javax.swing.JLabel labelY1;
    private javax.swing.JLabel labelYU;
    private javax.swing.JLabel labelYU1;
    private javax.swing.JLabel labelZ;
    private javax.swing.JLabel labelZ1;
    private javax.swing.JLabel labelZU;
    private javax.swing.JLabel labelZU1;
    private javax.swing.JPanel painelAdd;
    private javax.swing.JPanel painelAddEdit;
    private javax.swing.JPanel painelComum;
    private javax.swing.JPanel painelComum1;
    private javax.swing.JPanel painelEdit;
    private javax.swing.JPanel painelSistema;
    private javax.swing.JTabbedPane painelTabs;
    private javax.swing.JPanel panelDefault;
    private javax.swing.JTextField textQ;
    private javax.swing.JTextField textQ1;
    private javax.swing.JTextField textX;
    private javax.swing.JTextField textX1;
    private javax.swing.JTextField textY;
    private javax.swing.JTextField textY1;
    private javax.swing.JTextField textZ;
    private javax.swing.JTextField textZ1;
    // End of variables declaration//GEN-END:variables
    
}
