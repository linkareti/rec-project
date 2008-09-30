/*
 * Direita.java
 *
 * Created on 27 de Janeiro de 2003, 16:08
 */

package pt.utl.ist.elab.client.webrobot.customizer.Comps;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class Direita extends pt.utl.ist.elab.client.webrobot.customizer.Comps.Block 
{
    private int WIDTH=77;
    private int HEIGHT=45;
    private int LINEWIDTH=6;
    private java.awt.Dimension dimension = new java.awt.Dimension(WIDTH,HEIGHT);
    private int TIPO=5;
    private javax.swing.ImageIcon image = new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/webrobot/customizer/Comps/Icons/direita.gif"));        
    private pt.utl.ist.elab.client.webrobot.customizer.Models.ModelDireita model;
    private pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfDireita confDireita;
    private java.awt.Color backgroundColor = new java.awt.Color(204,204,204);
    private String text;
    private String fullNameDescription="Roda a direita";    
    
    /** Holds value of property paintBottom. */
    private boolean paintBottom=false;
    
    /** Holds value of property paintLeft. */
    private boolean paintLeft=false;
            
    /** Holds value of property cancel. */
    private boolean cancel=false;
    
    /** Creates a new instance of CompInt */
    public Direita(javax.swing.JFrame parent) 
    {
        super();
        setCancel(false);
        model = new pt.utl.ist.elab.client.webrobot.customizer.Models.ModelDireita();
        confDireita = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfDireita(parent, true, model);
        new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent,confDireita);
        confDireita.show();
        if(confDireita.isCancel())
        {
            setCancel(true);
            return;
        }
        setMinimumSize(dimension);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
        setBorderPainted(false);
        setBackground(new java.awt.Color(0,0,0,0));//background transparente
        setOpaque(false);
        text="";
    }   
    
    public Direita(pt.utl.ist.elab.client.webrobot.customizer.Models.ModelDireita model)
    {
        super();
        setCancel(false);
        this.model=new pt.utl.ist.elab.client.webrobot.customizer.Models.ModelDireita();
        this.model.setBaixo(model.getBaixo());
        this.model.setColuna(model.getColuna());
        this.model.setD1(model.getD1());
        this.model.setD2(model.getD2());
        this.model.setD3(model.getD3());
        this.model.setEsquerda(model.getEsquerda());
        this.model.setFlag(model.getFlag());
        this.model.setNivel(model.getNivel());
        this.model.setValor(model.getValor());
        this.model.setValor2(model.getValor2());
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
        g.setColor(java.awt.Color.black);
        text=""+model.getValor();
        g.drawString(text,(int)((image.getIconWidth()-text.length()*g.getFont().getSize()*1/2)/2),image.getIconWidth()/2);
        super.paintComponent(g);
    }
    
    public pt.utl.ist.elab.client.webrobot.customizer.Models.ModelBlock getDataModel()
    {
        return model;
    }
    
    public void setDataModel(pt.utl.ist.elab.client.webrobot.customizer.Models.ModelDireita model)
    {
        this.model=model;
    }
    
    public void edit(javax.swing.JFrame parent) 
    {
        confDireita = new pt.utl.ist.elab.client.webrobot.customizer.Comps.Configs.ConfDireita(parent, true, model);
        new pt.utl.ist.elab.client.webrobot.customizer.Utils.CenterFrame(parent,confDireita);
        confDireita.show();        
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
