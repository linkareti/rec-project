/*
 * MultiplierCustomizer.java
 *
 * Created on 29 de Junho de 2002, 19:19
 */

package com.linkare.rec.data;

/**
 *
 * @author  José Pedro Pereira - Linkare TI
 */
public class MultiplierCustomizer extends javax.swing.JPanel implements java.beans.Customizer
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 10662401807877360L;

	/** Creates new customizer MultiplierCustomizer */
	public MultiplierCustomizer()
	{
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the FormEditor.
	 */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        multiplierEditor = new com.linkare.rec.data.MultiplierEditor();
        jLabel1 = new javax.swing.JLabel();
        tagsList = new javax.swing.JComboBox(multiplierEditor.getTags());

        multiplierEditor.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                multiplierEditorPropertyChange(evt);
            }
        });

        setLayout(new java.awt.GridLayout(2, 1, 0, 3));

        setMaximumSize(new java.awt.Dimension(196, 53));
        jLabel1.setForeground((java.awt.Color) javax.swing.UIManager.getDefaults().get("desktop"));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Altere aqui o multiplicador");
        add(jLabel1);

        tagsList.setForeground((java.awt.Color) javax.swing.UIManager.getDefaults().get("ComboBox.selectionBackground"));
        tagsList.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                tagsListActionPerformed(evt);
            }
        });

        add(tagsList);

    }//GEN-END:initComponents

    private void multiplierEditorPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_multiplierEditorPropertyChange
    {//GEN-HEADEREND:event_multiplierEditorPropertyChange
		if(mybean!=null)
		{
			Byte oldValue=new Byte(mybean.getValue());
			mybean.setValue( ((Multiplier)multiplierEditor.getValue() ).getValue() );
			firePropertyChange("value",oldValue,new Byte(mybean.getValue()));
		}
    }//GEN-LAST:event_multiplierEditorPropertyChange

    private void tagsListActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_tagsListActionPerformed
    {//GEN-HEADEREND:event_tagsListActionPerformed
		multiplierEditor.setAsText((String)tagsList.getSelectedItem());
    }//GEN-LAST:event_tagsListActionPerformed

	private Multiplier mybean=null;

	public synchronized void setObject(Object obj)
	{
		
		if(java.beans.Beans.isInstanceOf(obj,Multiplier.class))
		{
			mybean=(Multiplier)java.beans.Beans.getInstanceOf(obj,Multiplier.class);
			multiplierEditor.setValue(mybean);
			tagsList.setSelectedItem(multiplierEditor.getAsText());
		}
	}


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox tagsList;
    private com.linkare.rec.data.MultiplierEditor multiplierEditor;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
