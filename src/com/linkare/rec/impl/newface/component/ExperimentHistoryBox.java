/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ExperimentHistoryBox.java
 *
 * Created on 20/Abr/2009, 11:22:31
 */

package com.linkare.rec.impl.newface.component;

/**
 *
 * @author iies-consultor922
 */
public class ExperimentHistoryBox extends javax.swing.JPanel {

    /** Creates new form ExperimentHistoryBox */
    public ExperimentHistoryBox() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        experimentHistoryList = new javax.swing.JList();

        setName("Form"); // NOI18N

        scrollPane.setBorder(null);
        scrollPane.setName("scrollPane"); // NOI18N

        experimentHistoryList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        experimentHistoryList.setName("experimentHistoryList"); // NOI18N
        scrollPane.setViewportView(experimentHistoryList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList experimentHistoryList;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

}
