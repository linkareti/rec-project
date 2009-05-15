/*
 * IvPWM.java
 *
 * Created on 27 de Janeiro de 2003, 16:03
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class IVPWM extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block 
{
    private static int WIDTH=77;
    private static int HEIGHT=45;
    private static int LINEWIDTH=6;
    private static java.awt.Dimension dimension = new java.awt.Dimension(WIDTH,HEIGHT);
    private static int TIPO=14;
    private javax.swing.ImageIcon image=new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/ivpwm.gif"));    
    private static pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model;
    private static pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfIVPWM confIVPWM;
    private static java.awt.Color backgroundColor = new java.awt.Color(204,204,204);
    private String text;
    private static String fullNameDescription="Configuracao dos sensores IV";
    
    /** Holds value of property paintBottom. */
    private boolean paintBottom=false;
    
    /** Holds value of property paintLeft. */
    private boolean paintLeft=false;
            
    /** Holds value of property cancel. */
    private boolean cancel=false;
    
    /** Creates a new instance of CompInt */
    public IVPWM(javax.swing.JFrame parent, pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model) 
    {
        super();
        this.model=model;
        setCancel(false);
        confIVPWM = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfIVPWM(parent, true, model);
        new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent,confIVPWM);
        confIVPWM.show();
        if(confIVPWM.isCancel())
        {
            setCancel(true);
            return;
        }
        else
        {
            setCancel(false);
        }        
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setBorderPainted(false);
        setBackground(new java.awt.Color(0,0,0,0));//background transparente
        setOpaque(false);
        text="";
    }   
    
    public IVPWM(pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model)
    {
        super();
        this.model=model;
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setBorderPainted(false);
        setBackground(new java.awt.Color(0,0,0,0));//background transparente
        setOpaque(false);
        text="";
    }
    
    public void paintComponent(java.awt.Graphics g)
    {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
        g2.setStroke(new java.awt.BasicStroke(3f));
        g.setColor(backgroundColor);
        g.fillRect(0,0,this.getSize().width,this.getSize().height);
        g.drawImage(image.getImage(),0,0,image.getIconWidth(),image.getIconHeight(),null);
        g.setColor(java.awt.Color.black);
        if(paintBottom)
        {
            g2.drawLine(image.getIconWidth()/2,image.getIconHeight(),image.getIconWidth()/2,image.getIconHeight()+LINEWIDTH);
        }
        if(paintLeft)
        {
            g2.drawLine(image.getIconWidth(),image.getIconHeight()/2,image.getIconWidth()+LINEWIDTH,image.getIconHeight()/2);
        }
        super.paintComponent(g);
    }
    
    public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel()
    {
        return model;
    }
    
    public void setDataModel(pt.utl.ist.elab.client.webrobot.customizer.Models.ModelIVPWM model)
    {
        this.model=model;
    }
    
    public void edit(javax.swing.JFrame parent) 
    {
        confIVPWM = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfIVPWM(parent, true, model);
        new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent,confIVPWM);
        confIVPWM.show();        
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
        repaint();
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
        repaint();
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
