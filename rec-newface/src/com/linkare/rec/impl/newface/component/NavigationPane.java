/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NavigationPane.java
 *
 * Created on 20/Abr/2009, 10:52:44
 */

package com.linkare.rec.impl.newface.component;

/**
 * 
 * @author Henrique Fernandes
 */
public class NavigationPane extends javax.swing.JPanel {

    /** Creates new form NavigationPane */
    public NavigationPane() {
	initComponents();
    }

    public ApparatusSelectBox getApparatusSelectBox() {
	return apparatusSelectBox;
    }

    public ExperimentHistoryBox getExperimentHistoryBox() {
	return experimentHistoryBox;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	apparatusSelectBox = new com.linkare.rec.impl.newface.component.ApparatusSelectBox();
	experimentHistoryBox = new com.linkare.rec.impl.newface.component.ExperimentHistoryBox();
	separator = new javax.swing.JSeparator();

	setMaximumSize(new java.awt.Dimension(212, 500));
	setMinimumSize(new java.awt.Dimension(212, 398));
	setName("Form"); // NOI18N

	apparatusSelectBox.setName("apparatusSelectBox"); // NOI18N

	experimentHistoryBox.setName("experimentHistoryBox"); // NOI18N

	separator.setName("separator"); // NOI18N

	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
	this.setLayout(layout);
	layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
		separator, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addComponent(
		experimentHistoryBox, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE).addComponent(
		apparatusSelectBox, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE));
	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
		layout.createSequentialGroup().addComponent(apparatusSelectBox, javax.swing.GroupLayout.PREFERRED_SIZE,
			115, javax.swing.GroupLayout.PREFERRED_SIZE).addPreferredGap(
			javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(separator,
			javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
			.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(
				experimentHistoryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 341,
				javax.swing.GroupLayout.PREFERRED_SIZE)));
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.linkare.rec.impl.newface.component.ApparatusSelectBox apparatusSelectBox;
    private com.linkare.rec.impl.newface.component.ExperimentHistoryBox experimentHistoryBox;
    private javax.swing.JSeparator separator;
    // End of variables declaration//GEN-END:variables

}
