/*
 * CartPole.java
 *
 * Created on 16 de Fevereiro de 2005, 0:04
 */

package pt.utl.ist.elab.client.vcartpole;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.jfree.chart.encoders.EncoderUtil;
import org.jfree.chart.encoders.ImageFormat;
import org.opensourcephysics.displayejs.DrawingPanel3D;
import org.opensourcephysics.displayejs.Group;
import org.opensourcephysics.displayejs.InteractionListener;
import org.opensourcephysics.displayejs.InteractiveArrow;
import org.opensourcephysics.displayejs.InteractiveBox;
import org.opensourcephysics.displayejs.InteractiveSphere;
import org.opensourcephysics.displayejs.InteractiveTrace;

import pt.utl.ist.elab.client.virtual.guipack.InteractiveMenu;
import pt.utl.ist.elab.client.virtual.guipack.PopupMenu;

/**
 *
 * @author  nomead
 */
public class CartPole extends DrawingPanel3D implements ActionListener, MouseListener, ComponentListener {
    
    protected String statusStr;
    private String actionStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.animation.actionStr");
    
    private PopupMenu viewPopMenu;
    protected InteractiveMenu par;
    
    protected InteractiveSphere pole;
    protected InteractiveBox cart;
    protected InteractiveTrace vara;
    
    private double mCart;
    private double mPole;
    
    private double sucAngle;
    private boolean savingPast;
    private InteractiveTrace rasto;
    
    protected InteractiveArrow actArrow;
    protected InteractiveArrow [] genVel; //0-X 1-Theta
    protected InteractiveArrow [] limits; //0-L 1-R
    
    private java.awt.image.BufferedImage pastImage;
    
    public void setListener(InteractionListener list){
        cart.addListener(list);
        pole.addListener(list);
        vara.addListener(list);
        actArrow.addListener(list);
        genVel[0].addListener(list);
        genVel[1].addListener(list);
        limits[0].addListener(list);
        limits[1].addListener(list);
    }
    
    /** Creates a new instance of BalancaTorcao */
    public CartPole() {
        super(DISPLAY_PLANAR_XY);
        buildCartPole();
    }
    
    public CartPole(InteractiveMenu par) {
        super(DISPLAY_PLANAR_XY);
        this.par = par;
        buildCartPole();
    }
    
    public void buildCartPole(){
        setBorder(new javax.swing.border.TitledBorder(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.title.1")));
        setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.tip.1"));
        setDecorationType(DECORATION_NONE);
        setSquareAspect(true);
        setPreferredMinMax(-15,15,-5,5);
        setCursorMode(CURSOR_CROSSHAIR);
        
        setEnabled(true);
        addMouseListener(this);
        addComponentListener(this);
        enableInspector(true);
        
        mCart = 1;
        mPole = .1;
        
        cart = new InteractiveBox();
        cart.setXY(0,0);
        cart.setSizeXYZ(4,2,0);
        cart.getStyle().setEdgeColor(java.awt.Color.BLACK);
        
        pole = new InteractiveSphere();
        pole.setXY(Math.sin(Math.toRadians(2))*10, Math.cos(Math.toRadians(2))*10);
        pole.setSizeXYZ(1,1,0);
        
        vara = new InteractiveTrace();
        vara.addPoint(0, 0);
        vara.addPoint(pole.getX(), pole.getY());
        vara.getStyle().setEdgeStroke(new java.awt.BasicStroke(6, java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));
        vara.setConnected(true);
        
        sucAngle = Math.toRadians(12);
        setThetaColor(Math.toRadians(2));
        
        actArrow = new InteractiveArrow(InteractiveArrow.ARROW);
        actArrow.setXY(0,0);
        actArrow.setSizeXY(0,0);
        actArrow.getStyle().setEdgeColor(java.awt.Color.RED);
        actArrow.getStyle().setFillPattern(java.awt.Color.RED);
        
        genVel = new InteractiveArrow[]{new InteractiveArrow(InteractiveArrow.ARROW), new InteractiveArrow(InteractiveArrow.ARROW)};
        
        genVel[0].setXY(0,0);
        genVel[0].setSizeXY(0,0);
        genVel[0].getStyle().setEdgeColor(java.awt.Color.ORANGE);
        genVel[0].getStyle().setFillPattern(java.awt.Color.ORANGE);
        
        genVel[1].setXY(pole.getX(),pole.getY());
        genVel[1].setSizeXY(0,0);
        genVel[1].getStyle().setEdgeColor(java.awt.Color.GREEN);
        genVel[1].getStyle().setFillPattern(java.awt.Color.GREEN);
        
        Group cartPole = new Group();
        pole.setGroup(cartPole);
        cart.setGroup(cartPole);
        vara.setGroup(cartPole);
        actArrow.setGroup(cartPole);
        genVel[0].setGroup(cartPole);
        genVel[1].setGroup(cartPole);
        
        limits = new InteractiveArrow[]{new InteractiveArrow(), new InteractiveArrow()};
        
        limits[0].setArrowType(InteractiveArrow.SEGMENT);
        limits[0].setXY(-30, getPreferredXMin());
        limits[0].setSizeXY(0, getPreferredXMax()-getPreferredXMin());
        
        limits[1].setArrowType(InteractiveArrow.SEGMENT);
        limits[1].setXY(30, getPreferredXMin());
        limits[1].setSizeXY(0, getPreferredXMax()-getPreferredXMin());
        
        rasto = new InteractiveTrace();//[]{new InteractiveTrace(), new InteractiveTrace()};
        //rasto[1].getStyle().setEdgeColor(new java.awt.Color(230,224,75,1));
        //this.setDoubleBuffered(true);
        addDrawable(cart);
        addDrawable(pole);
        addDrawable(vara);
        addDrawable(actArrow);
        addDrawable(genVel[0]);
        addDrawable(genVel[1]);
        addDrawable(limits[0]);
        addDrawable(limits[1]);
        
        savingPast = false;
        
        repaint();
        buildPopupMenu();
    }
    
    protected void zooming() {
        clearTrail();
        vara.getStyle().setEdgeStroke(new java.awt.BasicStroke(Math.abs((int) Math.round(this.getZoom())*6), java.awt.BasicStroke.CAP_ROUND, java.awt.BasicStroke.JOIN_ROUND));
    }
    
    protected void movingViewPoint() {
        clearTrail();
    }
    
    private void buildPopupMenu() {
        viewPopMenu = new PopupMenu(this);
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.title.9"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.tip.9"));
        viewPopMenu.addCheckBoxItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.title.10"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.tip.10"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.title.11"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.tip.11"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.save"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.save.tip"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.print"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.print.tip"));
    }
    
    protected void updateGUI(){
        par.update();
    }
    
    public void measure(){
        setPreferredMinMax(-Math.abs(limits[0].getX()),Math.abs(limits[0].getX()),-5,5);
        setPan(0,0);
        setZoom(1);
        zooming();
        repaint();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.save")))
            try{doSaveAs();}catch(IOException io){}
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.displays.print")))
            createChartPrintJob();
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.title.9"))){
            measure();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.title.10"))){
            savingPast = ((javax.swing.JCheckBoxMenuItem) e.getSource()).isSelected();
            
            if (!savingPast)
                removeDrawable(rasto);
            else
                addDrawable(rasto);
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("rec.exp.customizer.viewMenu.title.11"))) {
            clearTrail();
        }
    }
    
    public void mouseClicked(MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
            viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
            if (actionStr != null){
                actionStr = null;
                repaint();
            }
        }
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    
    public double getPoleLength(){
        return Math.sqrt(Math.pow(vara.getXMax()-vara.getXMin(),2)+Math.pow(vara.getYMax()-vara.getYMin(),2));
    }
    
    public void setPoleLength(double len){
        double ang = getTheta();
        pole.setXY(Math.sin(ang)*len, Math.cos(ang)*len);
        genVel[1].setXY(pole.getX(), pole.getY());
        vara.clear();
        vara.addPoint(cart.getX(), cart.getY());
        vara.addPoint(Math.sin(ang)*len, Math.cos(ang)*len);
        repaint();
    }
    
    public double getPoleMass(){
        return mPole;
    }
    
    public void setPoleMass(double m){
        this.mPole = m;
    }
    
    public double getCartMass(){
        return mCart;
    }
    
    public void setCartMass(double m){
        this.mCart = m;
    }
    
    public double getCartX(){
        return cart.getGroup().getX();
    }
    
    public void setCartX(double x){
        cart.getGroup().setX(x);
        checkLimits();
        repaint();
    }
    
    public double getTheta(){
        int n = (int) (pole.getX()/Math.abs(pole.getX()));
        return n*Math.acos(pole.getY()/getPoleLength());
    }
    
    public void setSuccessAngle(double ang){
        this.sucAngle = ang;
        setThetaColor(getTheta());
        repaint();
    }
    
    public double getSuccessAngle(){
        return this.sucAngle;
    }
    
    protected void setThetaColor(double ang){
        if ((ang = Math.abs(ang%(2*Math.PI))) <= sucAngle/2d-Math.toRadians(1) || (ang = 2*Math.PI-Math.abs(ang%(2*Math.PI))) <= sucAngle/2d-Math.toRadians(1)){
            vara.getStyle().setEdgeColor(new java.awt.Color((int) Math.round(255*ang/sucAngle/2d-Math.toRadians(1)),255,0,255));
            pole.getStyle().setEdgeColor(new java.awt.Color((int) Math.round(255*ang/sucAngle/2d-Math.toRadians(1)),255,0,255));
            pole.getStyle().setFillPattern(new java.awt.Color((int) Math.round(255*ang/sucAngle/2d-Math.toRadians(1)),255,0,255));
            
            /*vara.getStyle().setEdgeColor(new java.awt.Color(0f,.7f,0f,1f));
            pole.getStyle().setEdgeColor(new java.awt.Color(0f,.7f,0f,1f));
            pole.getStyle().setFillPattern(new java.awt.Color(0f,.7f,0f,1f));*/
        }
        else if ((ang = Math.abs(ang%(2*Math.PI))) <= sucAngle*1.5 || (ang = 2*Math.PI-Math.abs(ang%(2*Math.PI))) <= sucAngle*1.5){
            vara.getStyle().setEdgeColor(new java.awt.Color(255,(int) Math.round(255-((ang-(sucAngle/2d-Math.toRadians(1)))/(sucAngle*1.5-(sucAngle/2d-Math.toRadians(1))))*255),0,255));
            pole.getStyle().setEdgeColor(new java.awt.Color(255,(int) Math.round(255-((ang-(sucAngle/2d-Math.toRadians(1)))/(sucAngle*1.5-(sucAngle/2d-Math.toRadians(1))))*255),0,255));
            pole.getStyle().setFillPattern(new java.awt.Color(255,(int) Math.round(255-((ang-(sucAngle/2d-Math.toRadians(1)))/(sucAngle*1.5-(sucAngle/2d-Math.toRadians(1))))*255),0,255));
            
            /*vara.getStyle().setEdgeColor(new java.awt.Color(.9f,.9f,0f,1f));
            pole.getStyle().setEdgeColor(new java.awt.Color(.9f,.9f,0f,1f));
            pole.getStyle().setFillPattern(new java.awt.Color(.9f,.9f,0f,1f));*/
        }
        else if ((ang = Math.abs(ang%(2*Math.PI))) <= Math.toRadians(180) || (ang = 2*Math.PI-Math.abs(ang%(2*Math.PI))) <= Math.toRadians(180)){
            vara.getStyle().setEdgeColor(new java.awt.Color((int) Math.round(255-125*(ang-sucAngle*1.5)/(Math.toRadians(180)-sucAngle*1.5)),0,0,255));
            pole.getStyle().setEdgeColor(new java.awt.Color((int) Math.round(255-125*(ang-sucAngle*1.5)/(Math.toRadians(180)-sucAngle*1.5)),0,0,255));
            pole.getStyle().setFillPattern(new java.awt.Color((int) Math.round(255-125*(ang-sucAngle*1.5)/(Math.toRadians(180)-sucAngle*1.5)),0,0,255));
            
            /*vara.getStyle().setEdgeColor(new java.awt.Color(.7f,.0f,0f,1f));
            pole.getStyle().setEdgeColor(new java.awt.Color(.7f,.0f,0f,1f));
            pole.getStyle().setFillPattern(new java.awt.Color(.7f,.0f,0f,1f));*/
        }
        
        /*
        if (Math.abs(ang%(2*Math.PI)) <= Math.toRadians(12) || 2*Math.PI-Math.abs(ang%(2*Math.PI)) < Math.toRadians(12)){
            vara.getStyle().setEdgeColor(new java.awt.Color((int) Math.round(255*Math.abs(ang)%(2*Math.PI)/Math.toRadians(12)), (int) Math.round(255-(Math.abs(ang)%(2*Math.PI)/Math.toRadians(12))*255), 0));
            pole.getStyle().setEdgeColor(new java.awt.Color((int) Math.round(255*Math.abs(ang)%(2*Math.PI)/Math.toRadians(12)), (int) Math.round(255-(Math.abs(ang)%(2*Math.PI)/Math.toRadians(12))*255), 0));
            pole.getStyle().setFillPattern(new java.awt.Color((int) Math.round(255*Math.abs(ang)%(2*Math.PI)/Math.toRadians(12)), (int) Math.round(255-(Math.abs(ang)%(2*Math.PI)/Math.toRadians(12))*255), 0));
        }
        else
            vara.getStyle().setEdgeColor(new java.awt.Color(255,0,0));*/
        
    }
    
    public void setTheta(double ang){
        setThetaColor(ang);
        pole.setXY(Math.sin(ang)*getPoleLength(), Math.cos(ang)*getPoleLength());
        vara.clear();
        vara.addPoint(cart.getX(), cart.getY());
        vara.addPoint(pole.getX(), pole.getY());
        genVel[1].setXY(pole.getX(), pole.getY());
        setThetaVecVel(getThetaVecVel());
    }
    
    public double getAction(){
        return actArrow.getSizeX();
    }
    
    public void setAction(double act){
        actArrow.setSizeX(act);
        repaint();
    }
    
    public double getXVecVel(){
        return genVel[0].getSizeX();
    }
    
    public void setXVecVel(double vel){
        genVel[0].setSizeX(vel);
        repaint();
    }
    
    public double getThetaVecVel(){
        if (-Math.cos(getTheta())*genVel[1].getSizeX()+Math.sin(getTheta())*genVel[1].getSizeY() > 0)
            return -Math.sqrt(Math.pow(genVel[1].getSizeX(), 2)+Math.pow(genVel[1].getSizeY(), 2));
        else
            return Math.sqrt(Math.pow(genVel[1].getSizeX(), 2)+Math.pow(genVel[1].getSizeY(), 2));
    }
    
    public void setThetaVecVel(double vel){
        genVel[1].setSizeXY(vel*Math.cos(getTheta()), -vel*Math.sin(getTheta()));
        repaint();
    }
    
    public double getXLimit(){
        return Math.max(limits[0].getX(), limits[1].getX());
    }
    
    public void setXLimit(double xmax){
        limits[0].setX(-xmax);
        limits[1].setX(xmax);
        checkLimits();
        repaint();
    }
    
    public void move(double _x, double _theta, double _xdot, double _thetadot){
        if (savingPast){
            if (pastImage == null){
                pastImage = new java.awt.image.BufferedImage(getWidth(),getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
                
                java.awt.Graphics ggg = pastImage.getGraphics();
                ggg.setColor(getBackground());
                ggg.fillRect(0,0, getWidth(), getHeight());
                ggg.setColor(java.awt.Color.BLACK);
            }
            
            java.awt.Graphics gg = pastImage.getGraphics();
            
            cart.draw(this, gg);
            pole.draw(this, gg);
            vara.draw(this, gg);
            
            if (rasto.getSize() == 0)
                rasto.addPoint(pole.getX()+cart.getGroup().getX(), pole.getY());
        }
        
        setThetaColor(_theta);
        cart.getGroup().setX(_x);
        pole.setXY(Math.sin(_theta)*getPoleLength(), Math.cos(_theta)*getPoleLength());
        vara.clear();
        vara.addPoint(cart.getX(), cart.getY());
        vara.addPoint(pole.getX(), pole.getY());
        genVel[0].setSizeX(_xdot);
        genVel[1].setXY(pole.getX(), pole.getY());
        genVel[1].setSizeXY(_thetadot*Math.cos(getTheta()), -_thetadot*Math.sin(getTheta()));
        checkLimits();
        
        if (savingPast)
            rasto.addPoint(pole.getX()+cart.getGroup().getX(), pole.getY());
        
        repaint();
    }
    
    protected void paintEverything(java.awt.Graphics g) {
        if(dimensionSetter!=null){
            java.awt.Dimension interiorDimension=dimensionSetter.getInterior(this);
            if(interiorDimension!=null){
                squareAspect=false;
                leftGutter=rightGutter=Math.max(0,getWidth()- interiorDimension.width)/2;
                topGutter=bottomGutter=Math.max(0,getHeight()- interiorDimension.height)/2;
            }
        }
        java.util.ArrayList tempList= getDrawables();
        scale(tempList);
        setPixelScale();
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(java.awt.Color.black);
        if (savingPast && pastImage != null)
            g.drawImage(pastImage,0,0,java.awt.Color.WHITE,this);
        paintDrawableList(g, tempList);
        if (statusStr != null){
            g.setColor(java.awt.Color.WHITE);
            g.fillRect(7, 18, g.getFontMetrics().stringWidth(statusStr)+3, g.getFontMetrics().getHeight());
            
            g.setColor(java.awt.Color.BLACK);
            g.drawString(statusStr, 10, 29);
        }
        if (actionStr != null){
            g.setColor(new java.awt.Color(.6f, .12f, .3f));
            g.drawString(actionStr, 5, getHeight()-10);
        }
    }
    
    public void move(double _x, double _theta, double _xdot, double _thetadot, double _action){
        if (savingPast){
            if (pastImage == null){
                pastImage = new java.awt.image.BufferedImage(getWidth(),getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
                
                java.awt.Graphics ggg = pastImage.getGraphics();
                ggg.setColor(getBackground());
                ggg.fillRect(0,0, getWidth(), getHeight());
                ggg.setColor(java.awt.Color.BLACK);
            }
            
            java.awt.Graphics gg = pastImage.getGraphics();
            
            cart.draw(this, gg);
            pole.draw(this, gg);
            vara.draw(this, gg);
            
            if (rasto.getSize() == 0)
                rasto.addPoint(pole.getX()+cart.getGroup().getX(), pole.getY());
        }
        
        setThetaColor(_theta);
        cart.getGroup().setX(_x);
        pole.setXY(Math.sin(_theta)*getPoleLength(), Math.cos(_theta)*getPoleLength());
        vara.clear();
        vara.addPoint(cart.getX(), cart.getY());
        vara.addPoint(pole.getX(), pole.getY());
        genVel[0].setSizeX(_xdot);
        genVel[1].setXY(pole.getX(), pole.getY());
        genVel[1].setSizeXY(_thetadot*Math.cos(getTheta()), -_thetadot*Math.sin(getTheta()));
        actArrow.setSizeX(_action);
        checkLimits();
        if (savingPast)
            rasto.addPoint(pole.getX()+cart.getGroup().getX(), pole.getY());
        repaint();
    }
    
    public void setGenVelVisible(boolean mod){
        genVel[0].setVisible(mod);
        genVel[1].setVisible(mod);
        repaint();
    }
    
    protected void checkLimits(){
        if (cart.getGroup().getY() != 0)
            cart.getGroup().setY(0);
        if (cart.getGroup().getX() > Math.max(limits[0].getX(), limits[1].getX()))
            cart.getGroup().setX(Math.max(limits[0].getX(), limits[1].getX()));
        else if (cart.getGroup().getX() < Math.min(limits[0].getX(), limits[1].getX()))
            cart.getGroup().setX(Math.min(limits[0].getX(), limits[1].getX()));
        else if (getThetaVecVel() > 100)
            setThetaVecVel(100);
        else if (getThetaVecVel() < -100)
            setThetaVecVel(-100);
        else if (getXVecVel() > 100)
            setXVecVel(100);
        else if (getXVecVel() < -100)
            setXVecVel(-100);
        else if (getAction() > 100)
            setAction(100);
        else if (getAction() < -100)
            setAction(-100);
    }
    
    public void config(double _x, double _theta, double _xdot, double _thetadot, double _poleLength, double _mCart, double _mPole, double _action, double _sucAngle){
        setThetaColor(_theta);
        cart.getGroup().setX(_x);
        pole.setXY(Math.sin(_theta)*_poleLength, Math.cos(_theta)*_poleLength);
        vara.clear();
        vara.addPoint(cart.getX(), cart.getY());
        vara.addPoint(pole.getX(), pole.getY());
        genVel[0].setSizeX(_xdot);
        genVel[1].setXY(pole.getX(), pole.getY());
        genVel[1].setSizeXY(_thetadot*Math.cos(getTheta()), -_thetadot*Math.sin(getTheta()));
        actArrow.setSizeX(_action);
        setCartMass(_mCart);
        setPoleMass(_mPole);
        
        checkLimits();
        setSuccessAngle(_sucAngle);
    }
    
    public void configSI(double [] iniVars){
        setThetaColor(iniVars[2]);
        cart.getGroup().setX(iniVars[0]*10);
        pole.setXY(Math.sin(iniVars[2])*getPoleLength(), Math.cos(iniVars[2])*getPoleLength());
        vara.clear();
        vara.addPoint(cart.getX(), cart.getY());
        vara.addPoint(pole.getX(), pole.getY());
        genVel[0].setSizeX(iniVars[1]*10);
        genVel[1].setXY(pole.getX(), pole.getY());
        genVel[1].setSizeXY(iniVars[3]*Math.cos(getTheta()), -iniVars[3]*Math.sin(getTheta()));
        actArrow.setSizeX(iniVars[4]*10);
        checkLimits();
        repaint();
    }
    
    public void clearTrail(){
        if (pastImage != null){
            java.awt.Graphics ggg = pastImage.getGraphics();
            ggg.setColor(getBackground());
            ggg.fillRect(0,0, getWidth(), getHeight());
            ggg.setColor(java.awt.Color.BLACK);
        }
        rasto.clear();
        repaint();
    }
    
    public static void main(String args[]){
        javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            };
        });
        test.getContentPane().add(new CartPole());
        test.pack();
        test.show();
    }
    
    public void componentHidden(ComponentEvent e) {
    }
    
    public void componentMoved(ComponentEvent e) {
    }
    
    public void componentResized(ComponentEvent e) {
        if (pastImage != null){
            pastImage = new java.awt.image.BufferedImage(getWidth(),getHeight(),java.awt.image.BufferedImage.TYPE_INT_RGB);
            
            java.awt.Graphics ggg = pastImage.getGraphics();
            ggg.setColor(getBackground());
            ggg.fillRect(0,0, getWidth(), getHeight());
            ggg.setColor(java.awt.Color.BLACK);
        }
        rasto.clear();
        repaint();
    }
    
    public void componentShown(ComponentEvent e) {
    }
    
    public void doSaveAs() throws IOException {
        
        JFileChooser fileChooser = new JFileChooser();
        org.jfree.ui.ExtensionFileFilter filter = new org.jfree.ui.ExtensionFileFilter(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/cartpole/resources/ReCExpCartPole").getString("PNG_Image_Files"),".png");
        fileChooser.addChoosableFileFilter(filter);
        
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            
            if (!filename.endsWith(".png")) {
                filename = filename + ".png";
            }
            
            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename)));
            
            EncoderUtil.writeBufferedImage(render(), ImageFormat.PNG, out);
            out.close();
        }
        
    }
    
    public void createChartPrintJob() {
        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        PageFormat pf2 = job.pageDialog(pf);
        if (pf2 != pf) {
            job.setPrintable(this, pf2);
            if (job.printDialog()) {
                try {
                    job.print();
                }
                catch (PrinterException e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        }
    }
    
}
