/*
 * Block.java
 *
 * Created on 19 de Fevereiro de 2003, 10:59
 */

package pt.utl.ist.elab.client.webrobot.customizer.Interfaces;

/**
 *
 * @author André Neto - LEFT - IST
 */
public interface Block {
    int WIDTH=0;
    int HEIGHT=0;
    int LINEWIDTH=0;
    java.awt.Dimension dimension=null;
    int TIPO=0;
    javax.swing.ImageIcon image=null;
    pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock model=null;
    String text=null;
    java.awt.Color anotherAndColor=null;
    java.awt.Color backgroundColor=null;
    String fullNameDescription=null;    
    boolean paintBottom=false;
    boolean paintLeft=false;          
    boolean anotherAnd=false;
    boolean cancel=false;
        
    public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel();   
    public void edit(javax.swing.JFrame parent);
    public int getTipo();
    public String getFullNameDescription();
    public boolean isPaintBottom();
    public void setPaintBottom(boolean paintBottom);
    public boolean isPaintLeft();
    public void setPaintLeft(boolean paintLeft);
    public boolean isAnotherAnd();
    public void setAnotherAnd(boolean anotherAnd);
    public boolean isCancel();
    public void setCancel(boolean cancel);
}
