/*
 * MySlider.java
 *
 * Created on 18 de Dezembro de 2003, 23:39
 */

package test;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class MySlider extends javax.swing.JSlider
{
    /** Creates a new instance of MySlider */
    public MySlider()
    {
	super();
	super.setSnapToTicks(false);
    }
    
    public void setSnapToTicks(boolean val){
	//overriden because of StackOverFlow Problems
    }
    
    
}
