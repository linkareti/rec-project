/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ApparatusUserList.java
 *
 * Created on 10/Jun/2009, 10:38:57
 */

package com.linkare.rec.impl.newface.component;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.linkare.rec.impl.client.ApparatusClientBean;
import com.linkare.rec.impl.newface.laf.flat.FlatLookAndFeel;

/**
 * 
 * @author joao
 */
public class ApparatusUserList extends javax.swing.JPanel {

	/** Creates new form ApparatusUserList */
	public ApparatusUserList() {
		initComponents();
	}

	public DefaulExpUsersListTableModel getModel() {
		return (DefaulExpUsersListTableModel) flatTable1.getModel();
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

		jScrollPane1 = new javax.swing.JScrollPane();
		flatTable1 = new com.linkare.rec.impl.newface.component.FlatTable();

		setName("Form"); // NOI18N

		jScrollPane1.setName("jScrollPane1"); // NOI18N

		flatTable1.setModel(new DefaulExpUsersListTableModel());

		flatTable1.setName("flatTable1"); // NOI18N
		jScrollPane1.setViewportView(flatTable1);

		 javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		 this.setLayout(layout);
		 layout.setHorizontalGroup(
		 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		 .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
		 layout.createSequentialGroup()
		 .addContainerGap(15, Short.MAX_VALUE)
		 .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
		 375, javax.swing.GroupLayout.PREFERRED_SIZE)
		 .addContainerGap())
		 );
		 layout.setVerticalGroup(
		 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		 .addGroup(layout.createSequentialGroup()
		 .addContainerGap()
		 .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
		 275, javax.swing.GroupLayout.PREFERRED_SIZE)
		 .addContainerGap(14, Short.MAX_VALUE))
		 );
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.rec.impl.newface.component.FlatTable flatTable1;
	private javax.swing.JScrollPane jScrollPane1;

	// End of variables declaration//GEN-END:variables

//	public static void main(String[] args) {
//		
//		try {
//			UIManager.setLookAndFeel(FlatLookAndFeel.class.getCanonicalName());
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		final JFrame frame = new JFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.getContentPane().setLayout(new BorderLayout());
//		final ApparatusUserList apparatusList = new ApparatusUserList();
//		frame.getContentPane().add(apparatusList, BorderLayout.CENTER);
//		
//		JButton btn=new JButton("DoIt!");
//		frame.getContentPane().add(btn,BorderLayout.SOUTH);
//		
//		btn.addActionListener(new ActionListener(){
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("Hi");
//				DefaulExpUsersListTableModel model = apparatusList.getModel();
//				model.setExpUsersListSource(new ApparatusClientBean());
//				System.out.println("model is now "+model);
//				System.out.println("model.class is now "+model!=null?model.getClass():"ops, no model!");
//				System.out.println("Hi again!");
//			}});
//		
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				frame.pack();
//				frame.setVisible(true);
//			}
//		});
//	}

}
