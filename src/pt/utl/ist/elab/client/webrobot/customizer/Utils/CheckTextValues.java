/*
 * CheckTextValues.java
 *
 * Created on 28 de Janeiro de 2003, 20:47
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class CheckTextValues {
    
    javax.swing.JDialog parent;
    
    /** Creates a new instance of CheckTextValues */
    public CheckTextValues(javax.swing.JDialog parent)     
    {        
        this.parent=parent;
    }
    
    public boolean isOK(javax.swing.JTextField jTextField)
    {
        int value=-1;
        try
        {
            value = new Integer(jTextField.getText()).intValue();
        }
        catch(NumberFormatException nfe)
        {
            javax.swing.JOptionPane.showMessageDialog(parent, "Por favor, introduza um numero inteiro", "Erro!", javax.swing.JOptionPane.ERROR_MESSAGE);            
            jTextField.selectAll();
            jTextField.requestFocus();
            return false;
        }
        if(value<0||value>255)
        {
            javax.swing.JOptionPane.showMessageDialog(parent, "Por favor, introduza um numero inteiro no intervalo [0,255]", "Erro!", javax.swing.JOptionPane.ERROR_MESSAGE);            
            jTextField.selectAll();
            jTextField.requestFocus();
            return false;
        }
        else
        {
            return true;
        }
    }    
}
