/*
 * HorLine.java
 *
 * Created on 22 de Dezembro de 2002, 0:35
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 *
 * @author  Andre
 */
public class HorLine extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block 
{
    private int WIDTH=77;
    private int HEIGHT=45;
    private java.awt.Dimension dimension = new java.awt.Dimension(WIDTH,HEIGHT);
    private int TIPO=99;
    private java.awt.Color backgroundColor = new java.awt.Color(204,204,204);
    private javax.swing.ImageIcon image;
    private String fullNameDescription="ligacao";    
        
    /** Creates a new instance of HorLine */
    public HorLine() 
    {        
        super();
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setBorderPainted(false);
        setBackground(new java.awt.Color(0,0,0,0));//background transparente
        setOpaque(false);
    }
    

    public void paintComponent(java.awt.Graphics g)
    {
        image = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/atribui.gif"));
        java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
        g2.setStroke(new java.awt.BasicStroke(3f));
        g.setColor(backgroundColor);
        g.fillRect(0,0,this.getSize().width,this.getSize().height);
        g.setColor(java.awt.Color.black);
        g2.drawLine(0,image.getIconHeight()/2,this.getSize().width,image.getIconHeight()/2);
        super.paintComponent(g);
    }       
    
    public String getFullNameDescription()
    {
        return this.fullNameDescription;
    }
    
    public void setPaintBottom(boolean paintLeft)
    {
    }
    
    public boolean isPaintBottom()
    {
        return false;
    }
    
    public boolean isPaintLeft()
    {
        return true;
    }  
}
