/*
 * Block.java
 *
 * Created on 13 de Fevereiro de 2003, 17:19
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 *
 * @author  Andre
 */
public class Block extends javax.swing.JButton 
{
    private int WIDTH;
    private int HEIGHT;
    private int LINEWIDTH;
    private java.awt.Dimension dimension;
    private int TIPO;
    private pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock model;
    private pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfCompAndInt confCompAndInt;
    private java.awt.Color backgroundColor;
    private java.awt.Color anotherAndColor;
    private String text;
    private String fullNameDescription;
    public java.awt.Color darkBlue=java.awt.Color.blue.darker();
    public java.awt.Color highlightColor = new java.awt.Color(darkBlue.getRed(),darkBlue.getGreen(),darkBlue.getBlue(),50);           

    
    /** Holds value of property paintBottom. */
    private boolean paintBottom=false;
    
    /** Holds value of property paintLeft. */
    private boolean paintLeft=false;
            
    /** Holds value of property anotherAnd. */
    private boolean anotherAnd=false;
            
    /** Holds value of property cancel. */
    private boolean cancel=false;
    
    /** Creates a new instance of CompInt */
    public Block()
    {
        super();
    }   
    
    public void paintComponent(java.awt.Graphics g)
    {
    }
     
    public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel()
    {
        return model;
    }
    
    public void setDataModel(pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock model)
    {
        this.model=model;
    }
    
    public void edit(javax.swing.JFrame parent) 
    {
    }
    
    public int getTipo()
    {
        return TIPO;
    }
    
    public String getFullNameDescription()
    {
        return this.fullNameDescription;
    }
            
    /** Getter for property paintBottom.
     * @return Value of property paintBottom.
     */
    public boolean isPaintBottom() {
        return this.paintBottom;
    }
    
    /** Setter for property paintBottom.
     * @param paintBottom New value of property paintBottom.
     */
    public void setPaintBottom(boolean paintBottom) {
        this.paintBottom = paintBottom;
    }
    
    /** Getter for property paintLeft.
     * @return Value of property paintLeft.
     */
    public boolean isPaintLeft() {
        return this.paintLeft;
    }
    
    /** Setter for property paintLeft.
     * @param paintLeft New value of property paintLeft.
     */
    public void setPaintLeft(boolean paintLeft) {
        this.paintLeft = paintLeft;
    }
    
    /** Getter for property anotherAnd.
     * @return Value of property anotherAnd.
     */
    public boolean isAnotherAnd() {
        return this.anotherAnd;
    }
    
    /** Setter for property anotherAnd.
     * @param anotherAnd New value of property anotherAnd.
     */
    public void setAnotherAnd(boolean anotherAnd) {
        this.anotherAnd = anotherAnd;
    }    
    
    /** Getter for property cancel.
     * @return Value of property cancel.
     */
    public boolean isCancel() {
        return this.cancel;
    }
    
    /** Setter for property cancel.
     * @param cancel New value of property cancel.
     */
    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }        
}
