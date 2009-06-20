/*
 * MovProj.java
 *
 * Created on 28 de Fevereiro de 2005, 5:27
 */

package pt.utl.ist.elab.virtual.client.movproj;

import org.opensourcephysics.displayejs.*;
import pt.utl.ist.elab.virtual.guipack.*;
import java.awt.event.*;

/**
 *
 * @author  nomead
 */
public class MovProj extends DrawingPanel3D implements ActionListener, MouseListener {
    
    protected PopupMenu viewPopMenu;
    protected InteractiveMenu par;
    
    protected InteractiveSphere bola;
    
    protected InteractiveArrow acel;
    protected InteractiveArrow vel;
    protected InteractiveArrow spin;
    
    private InteractivePlane floor;
    
    public MovProj(InteractiveMenu par){
        super(DISPLAY_NO_PERSPECTIVE);
        buildMovProj();
        this.par = par;
    }
    
    public MovProj() {
        super(DISPLAY_NO_PERSPECTIVE);
        buildMovProj();
    }
    
    private void buildMovProj() {
        setAlphaAndBeta(-Math.PI/2,Math.PI/2);
        setPreferredMinMax(-20,20,0,25,-20,20);
        setDecorationType(DECORATION_NONE);
        
        addMouseListener(this);
        
        bola = new InteractiveSphere();
        bola.setXYZ(0,0,0);
        bola.setSizeXYZ(2,2,2);
        bola.getStyle().setFillPattern(java.awt.Color.WHITE);
        bola.getStyle().setEdgeColor(java.awt.Color.WHITE);
        
        vel = new InteractiveArrow();
        vel.setXYZ(bola.getX(),bola.getY(),bola.getZ());
        vel.setSizeXYZ(10,10,0);
        vel.getStyle().setFillPattern(java.awt.Color.ORANGE);
        vel.getStyle().setEdgeColor(java.awt.Color.ORANGE);
        
        spin = new InteractiveArrow();
        spin.setXYZ(bola.getX(),bola.getY(),bola.getZ());
        spin.setSizeXYZ(0,10,0);
        spin.getStyle().setFillPattern(java.awt.Color.BLUE);
        spin.getStyle().setEdgeColor(java.awt.Color.BLUE);
        
        acel = new InteractiveArrow();
        acel.setXYZ(bola.getX(),bola.getY(),bola.getZ());
        acel.setSizeXYZ(0,-9.8,0);
        acel.getStyle().setFillPattern(java.awt.Color.RED);
        acel.getStyle().setEdgeColor(java.awt.Color.RED);
        
        Group group = new Group();
        bola.setGroup(group);
        vel.setGroup(group);
        spin.setGroup(group);
        acel.setGroup(group);
        
        group.setY(1);
        
        floor = new InteractivePlane();
        floor.setVectorU(1,0,0);
        floor.setVectorV(0,0,1);
        floor.setSizeXYZ(50,50,0);
        floor.getStyle().setFillPattern(new java.awt.Color(.1f,.9f,.1f,.3f));
        
        addDrawable(bola);
        addDrawable(vel);
        addDrawable(spin);
        addDrawable(acel);
        addDrawable(floor);
        
        repaint();
        buildPopupMenu();
    }
    
    private void buildPopupMenu(){
        viewPopMenu = new PopupMenu(this);
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.1"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.1"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.2"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.2"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.3"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.3"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.4"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.4"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.15"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.15"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.16"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.16"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.6"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.6"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.5"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.tip.5"));
    }
    
    public void setListener(InteractionListener list){
        bola.addListener(list);
        vel.addListener(list);
        spin.addListener(list);
        acel.addListener(list);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void move(double x, double y, double z, double vx, double vy, double vz, double ax, double ay, double az){
        bola.getGroup().setXYZ(x,y+bola.getSizeX()/2,z);
        vel.setSizeXYZ(vx,vy,vz);
        acel.setSizeXYZ(ax,ay,az);
        
        floor.setSizeXYZ(bola.getGroup().getX()*2,bola.getGroup().getZ()*2,0);
    }
    
    public void setX(double x){
        bola.getGroup().setX(x);
        repaint();
    }
    
    public double getPosX(){
        return bola.getGroup().getX();
    }
    
    public void setY(double y){
        bola.getGroup().setY(y+bola.getSizeX()/2);
        repaint();
    }
    
    public double getPosY(){
        return bola.getGroup().getY()-bola.getSizeX()/2;
    }
    
    public void setZ(double z){
        bola.getGroup().setZ(z);
        repaint();
    }
    
    public double getPosZ(){
        return bola.getGroup().getZ();
    }
    
    
    
    public static double getLength(InteractiveElement elm){
        return Math.sqrt(Math.pow(elm.getSizeX(),2)+Math.pow(elm.getSizeY(),2)+Math.pow(elm.getSizeZ(),2));
    }
    
    public static void setLength(InteractiveElement elm, double len){
        double theta = getTheta(elm);
        double phi = getPhi(elm);
        elm.setSizeXYZ(len*Math.cos(theta)*Math.sin(phi), len*Math.sin(theta)*Math.sin(phi), len*Math.cos(phi));
    }
    
    public static void setTheta(InteractiveElement elm, double theta){
        double len = getLength(elm);
        double phi = getPhi(elm);
        elm.setSizeXY(len*Math.cos(theta)*Math.sin(phi), len*Math.sin(theta)*Math.sin(phi));
    }
    
    public static void setPhi(InteractiveElement elm, double phi){
        double len = getLength(elm);
        double theta = getTheta(elm);
        elm.setSizeXYZ(len*Math.cos(theta)*Math.sin(phi), len*Math.sin(theta)*Math.sin(phi), len*Math.cos(phi));
    }
    
    public static void setAngles(InteractiveElement elm, double theta, double phi){
        double len = getLength(elm);
        elm.setSizeXYZ(len*Math.cos(theta)*Math.sin(phi), len*Math.sin(theta)*Math.sin(phi), len*Math.cos(phi));
    }
    
    public static double getTheta(InteractiveElement elm){
        return Math.atan2(elm.getSizeY(),elm.getSizeX());
    }
    
    public static double getPhi(InteractiveElement elm){
        return Math.acos(elm.getSizeZ()/getLength(elm));
    }
    
    public void setRadius(double r){
        bola.setSizeXYZ(r*2,r*2,r*2);
        if (bola.getGroup().getY() < bola.getSizeX()/2)
            bola.getGroup().setY(bola.getSizeX()/2);
        else if (bola.getGroup().getY() > 50+bola.getSizeX()/2)
            bola.getGroup().setY(50+bola.getSizeX()/2);
        repaint();
    }
    
    public double getRadius(){
        return bola.getSizeX()/2;
    }
    
    public void setVel(double v){
        setLength(vel,v);
        repaint();
    }
    
    public double getVel(){
        return getLength(vel);
    }
    
    public void setThetaVel(double tVel){
        setTheta(vel,tVel);
        repaint();
    }
    
    public double getThetaVel(){
        return getTheta(vel);
    }
    
    public void setPhiVel(double pVel){
        setPhi(vel,pVel);
        repaint();
    }
    
    public void setAngsVel(double tVel, double pVel){
        setAngles(vel,tVel,pVel);
        repaint();
    }
    
    public double getPhiVel(){
        return getPhi(vel);
    }
    
    public void setSpin(double sp){
        setLength(spin,sp);
        repaint();
    }
    
    public double getSpin(){
        return getLength(spin);
    }
    
    public void setThetaSpin(double tSp){
        setTheta(spin,tSp);
        repaint();
    }
    
    public double getThetaSpin(){
        return getTheta(spin);
    }
    
    public void setPhiSpin(double pSp){
        setPhi(spin,pSp);
        repaint();
    }
    
    public double getPhiSpin(){
        return getPhi(spin);
    }
    
    public void setAngsSpin(double tSp, double pSp){
        setAngles(spin,tSp,pSp);
        repaint();
    }
    
    public void setAcel(double acl){
        setLength(acel,acl);
        repaint();
    }
    
    public double getAcel(){
        return getLength(acel);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.1"))){
            setAlphaAndBeta(-Math.PI/2,Math.PI/2);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.2"))){
            if (getZoom() != 1){
                setZoom(1);
                repaint();
            }
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.3"))){
            if (getZoom() != .5){
                setZoom(.5);
                repaint();
            }
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.4"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_XY);
            setPan(0,0);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.15"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_XZ);
            setPan(0,0);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.16"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_YZ);
            setPan(0,0);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.6"))){
            this.setDisplayMode(this.DISPLAY_NO_PERSPECTIVE);
            setPan(0,0);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/movproj/resources/ReCExpMovProj").getString("rec.exp.customizer.viewMenu.title.5")))
            snapshot();
    }
    
    public void mouseClicked(MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e))
            viewPopMenu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    protected void updateGUI(){
        par.update();
    }
    
    public void config(double x, double y, double z, double vel, double thetaVel, double phiVel, double spin, double thetaSpin, double phiSpin, double acel, double radius){
        bola.setSizeXYZ(radius*2,radius*2,radius*2);
        bola.getGroup().setXYZ(x,y+bola.getSizeX()/2,z);
        
        this.vel.setSizeXYZ(vel*Math.cos(thetaVel)*Math.sin(phiVel), vel*Math.sin(thetaVel)*Math.sin(phiVel), vel*Math.cos(phiVel));
        
        this.spin.setSizeXYZ(spin*Math.cos(thetaSpin)*Math.sin(phiSpin), spin*Math.sin(thetaSpin)*Math.sin(phiSpin), spin*Math.cos(phiSpin));
        
        setAcel(acel);
    }
    
}
