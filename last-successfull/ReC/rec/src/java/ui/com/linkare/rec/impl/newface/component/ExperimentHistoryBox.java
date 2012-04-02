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

import java.util.logging.Logger;

/**
 * 
 * @author Henrique Fernandes
 */
public class ExperimentHistoryBox extends AbstractContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2266687057755910198L;
	private static final Logger log = Logger.getLogger(ExperimentHistoryBox.class.getName());

	/** Creates new form ExperimentHistoryBox */
	public ExperimentHistoryBox() {
		initComponents();
	}

	public AbstractContentPane getHistoryListPane() {
		return historyListPane;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		scrollHistoryPane = new javax.swing.JScrollPane();
		historyListPane = new com.linkare.rec.impl.newface.component.AbstractContentPane();

		setName("Form"); // NOI18N

		scrollHistoryPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollHistoryPane.setName("scrollHistoryPane"); // NOI18N

		historyListPane.setName("historyListPane"); // NOI18N
		historyListPane.setLayout(new javax.swing.BoxLayout(historyListPane, javax.swing.BoxLayout.Y_AXIS));
		scrollHistoryPane.setViewportView(historyListPane);

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(scrollHistoryPane, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addComponent(scrollHistoryPane, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
						.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	public void addExperimentHistory(final ExperimentHistoryUINode expHist) {
		historyListPane.add(new ExpHistoryEntryPane(expHist, historyListPane));
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.rec.impl.newface.component.AbstractContentPane historyListPane;
	private javax.swing.JScrollPane scrollHistoryPane;
	// End of variables declaration//GEN-END:variables

}
