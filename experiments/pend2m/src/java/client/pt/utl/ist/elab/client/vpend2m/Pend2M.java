/*
 * Pend2M.java
 *
 * Created on 28 de Fevereiro de 2005, 5:27
 */

package pt.utl.ist.elab.client.vpend2m;

import org.opensourcephysics.displayejs.*;

import pt.utl.ist.elab.client.virtual.guipack.*;

import java.awt.event.*;

/**
 *
 * @author  nomead
 */
public class Pend2M extends DrawingPanel3D implements ActionListener, MouseListener {
    
    protected PopupMenu viewPopMenu;
    protected InteractiveMenu par;

    protected InteractiveArrow bar;
    protected InteractiveTrace [] fio;
    protected InteractiveSphere [] bola;
    protected InteractiveSphere point;
    
    protected InteractiveArrow [] acel;
    protected InteractiveArrow [] vel;
    
    protected double fase = 0;
    
    public Pend2M(InteractiveMenu par){
        super(DISPLAY_NO_PERSPECTIVE);
        buildPend2M();
        this.par = par;
    }
    
    public Pend2M() {
        super(DISPLAY_NO_PERSPECTIVE);
        buildPend2M();
    }
    
    private void buildPend2M() {
        setPreferredMinMax(-10,10,-10,10,-10,10);
        
        addMouseListener(this);
        
        bar = new InteractiveArrow();
        bar.setArrowType(InteractiveArrow.SEGMENT);
        bar.setXYZ(0,-5,0);
        bar.setSizeXYZ(0,10,0);
        
        point = new InteractiveSphere();
        point.setXYZ(0,0,0);
        point.setSizeXYZ(.3,.3,.3);
        point.getStyle().setFillPattern(java.awt.Color.BLACK);
        point.getStyle().setEdgeColor(java.awt.Color.BLACK);
        
        
        
        fio = new InteractiveTrace[]{new InteractiveTrace(),new InteractiveTrace()};
        fio[0].addPoint(0,0,0);
        fio[0].addPoint(0,-4,-4);
        
        fio[1].addPoint(0,-4,-4);
        fio[1].addPoint(0,-9,-7);
        
        bola = new InteractiveSphere[]{new InteractiveSphere(), new InteractiveSphere()};
        bola[0].setXYZ(0,-4,-4);
        bola[0].setSizeXYZ(1,1,1);
        bola[0].getStyle().setFillPattern(java.awt.Color.BLUE);
        bola[0].getStyle().setEdgeColor(java.awt.Color.BLUE);
        
        bola[1].setXYZ(0,-9,-7);
        bola[1].setSizeXYZ(1,1,1);
        bola[1].getStyle().setFillPattern(java.awt.Color.ORANGE);
        bola[1].getStyle().setEdgeColor(java.awt.Color.ORANGE);

        vel = new InteractiveArrow[]{new InteractiveArrow(), new InteractiveArrow()};
        vel[0].setXYZ(0,bola[0].getY(),bola[0].getZ());
        setThetaVecVel(1.7);
        vel[0].getStyle().setFillPattern(java.awt.Color.GREEN);
        vel[0].getStyle().setEdgeColor(java.awt.Color.GREEN);
        vel[1].setXYZ(0,bola[1].getY(),bola[1].getZ());
        setPhiVecVel(-1.7);
        vel[1].getStyle().setFillPattern(java.awt.Color.GREEN);
        vel[1].getStyle().setEdgeColor(java.awt.Color.GREEN);
        
        Group group = new Group();
        point.setGroup(group);
        fio[0].setGroup(group);
        fio[1].setGroup(group);
        bola[1].setGroup(group);
        bola[0].setGroup(group);
        vel[0].setGroup(group);
        vel[1].setGroup(group);
        
        addDrawable(bar);
        addDrawable(fio[0]);
        addDrawable(fio[1]);
        addDrawable(bola[0]);
        addDrawable(bola[1]);
        addDrawable(point);
        addDrawable(vel[0]);
        addDrawable(vel[1]);
        
        acel = new InteractiveArrow[]{new InteractiveArrow(), new InteractiveArrow()};
        acel[0].setXYZ(0,bola[0].getY(),bola[0].getZ());
        acel[0].setSizeXYZ(0,0,0);
        acel[1].setXYZ(0,bola[1].getY(),bola[1].getZ());
        acel[1].setSizeXYZ(0,0,0);
        acel[0].getStyle().setFillPattern(java.awt.Color.RED);
        acel[0].getStyle().setEdgeColor(java.awt.Color.RED);
        acel[1].getStyle().setFillPattern(java.awt.Color.RED);
        acel[1].getStyle().setEdgeColor(java.awt.Color.RED);

        acel[0].setGroup(group);
        acel[1].setGroup(group);
        addDrawable(acel[0]);
        addDrawable(acel[1]);
        
        repaint();
        buildPopupMenu();
    }
 
    private void buildPopupMenu(){
        viewPopMenu = new PopupMenu(this);
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.1"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.1"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.2"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.2"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.3"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.3"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.4"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.4"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.5"), java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.5"));
    }
    
    public void setListener(InteractionListener list){
        point.addListener(list);
        bar.addListener(list);
        bola[0].addListener(list);
        bola[1].addListener(list);
        vel[0].addListener(list);
        vel[1].addListener(list);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void setFase(double fase){
        this.fase = fase;
        point.getGroup().setY(getAmp()*Math.sin(fase));
    }

    public double getFase(){
        return fase;
    }
    
    protected void keepDistances(){
        byte n1 = 1, n2 = 1;
        
        if (fio[1].getZMax() != bola[1].getZ())
            n2 = -1;
        if (fio[1].getYMax() != bola[1].getY()+bola[1].getGroup().getY())
            n1 = -1;
        
        bola[1].setXYZ(0,bola[0].getY()+n1*(fio[1].getYMax()-fio[1].getYMin()),bola[0].getZ()+n2*(fio[1].getZMax()-fio[1].getZMin()));
    }
    
    public double getAmp(){
        return Math.abs(bar.getSizeY()/2);
    }
    
    public void setAmp(double size){
        bar.setY(-size);
        bar.setSizeY(2*size);
        point.getGroup().setY(size*Math.sin(fase));
        repaint();
    }
    
    public void setL1(double len){
        double ang = getTheta();
        bola[0].setXYZ(0, Math.sin(ang)*len, -Math.cos(ang)*len);
        //force.setXYZ(0, bola.getY(), bola.getZ());
        vel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());
        fio[0].clear();
        fio[0].addPoint(0, 0, 0);
        fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());
        
        keepDistances();
        bola[1].setRotationPoint(new javax.vecmath.Vector3d(0,bola[0].getY(),bola[0].getZ()));
        fio[1].clear();
        fio[1].addPoint(0,bola[0].getY(),bola[0].getZ());
        fio[1].addPoint(0,bola[1].getY(),bola[1].getZ());
        vel[1].setXYZ(0,bola[1].getY(),bola[1].getZ());
        repaint();
    }
    
    public void setL2(double len){
        double ang = getPhi();
        bola[1].setXYZ(0, bola[0].getY()+Math.sin(ang)*len, bola[0].getZ()-Math.cos(ang)*len);
        //force.setXYZ(0, bola.getY(), bola.getZ());
        vel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
        fio[1].clear();
        fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
        fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());
        repaint();
    }
    
    public double getL1(){
        return Math.sqrt(Math.pow(fio[0].getYMax()-fio[0].getYMin(),2)+Math.pow(fio[0].getZMax()-fio[0].getZMin(),2));
    }
    
    public double getL2(){
        return Math.sqrt(Math.pow(fio[1].getYMax()-fio[1].getYMin(),2)+Math.pow(fio[1].getZMax()-fio[1].getZMin(),2));
    }
    
    public void setThetaVecVel(double vel){
        this.vel[0].setSizeY(vel*Math.cos(getTheta()));
        this.vel[0].setSizeZ(vel*Math.sin(getTheta()));
        repaint();
    }
    
    public void setPhiVecVel(double vel){
        this.vel[1].setSizeY(vel*Math.cos(getPhi()));
        this.vel[1].setSizeZ(vel*Math.sin(getPhi()));
        repaint();
    }
    
    public double getThetaVecVel(){
        if (-Math.cos(getTheta())*vel[0].getSizeY()-Math.sin(getTheta())*vel[0].getSizeZ() > 0)
            return -Math.sqrt(Math.pow(vel[0].getSizeZ(), 2)+Math.pow(vel[0].getSizeY(), 2));
        else
            return Math.sqrt(Math.pow(vel[0].getSizeZ(), 2)+Math.pow(vel[0].getSizeY(), 2));
    }
    
    public double getPhiVecVel(){
        if (-Math.cos(getPhi())*vel[1].getSizeY()-Math.sin(getPhi())*vel[1].getSizeZ() > 0)
            return -Math.sqrt(Math.pow(vel[1].getSizeZ(), 2)+Math.pow(vel[1].getSizeY(), 2));
        else
            return Math.sqrt(Math.pow(vel[1].getSizeZ(), 2)+Math.pow(vel[1].getSizeY(), 2));
    }
    
    public void move(double theta, double phi, double thetaDot, double phiDot, double point, double thetaDot2, double phiDot2){
        bola[0].setXYZ(0, Math.sin(theta)*getL1(), -Math.cos(theta)*getL1());
        fio[0].clear();
        fio[0].addPoint(0, 0, 0);
        fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());

        bola[1].setXYZ(0, bola[0].getY()+Math.sin(phi)*getL2(), bola[0].getZ()-Math.cos(phi)*getL2());
        fio[1].clear();
        fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
        fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());

        vel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());
        vel[0].setSizeY(thetaDot*Math.cos(getTheta()));
        vel[0].setSizeZ(thetaDot*Math.sin(getTheta()));
        acel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());
        acel[0].setSizeY(thetaDot2*Math.cos(getTheta()));
        acel[0].setSizeZ(thetaDot2*Math.sin(getTheta()));
        
        vel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
        vel[1].setSizeY(phiDot*Math.cos(getPhi()));
        vel[1].setSizeZ(phiDot*Math.sin(getPhi()));
        acel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
        acel[1].setSizeY(phiDot2*Math.cos(getPhi()));
        acel[1].setSizeZ(phiDot2*Math.sin(getPhi()));
        
        this.point.getGroup().setY(point);
        
        repaint();
    
    }
    
    public void move(double theta, double phi, double thetaDot, double phiDot, double point){
        bola[0].setXYZ(0, Math.sin(theta)*getL1(), -Math.cos(theta)*getL1());
        fio[0].clear();
        fio[0].addPoint(0, 0, 0);
        fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());

        bola[1].setXYZ(0, bola[0].getY()+Math.sin(phi)*getL2(), bola[0].getZ()-Math.cos(phi)*getL2());
        fio[1].clear();
        fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
        fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());

        vel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());
        vel[0].setSizeY(thetaDot*Math.cos(getTheta()));
        vel[0].setSizeZ(thetaDot*Math.sin(getTheta()));
        
        vel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
        vel[1].setSizeY(phiDot*Math.cos(getPhi()));
        vel[1].setSizeZ(phiDot*Math.sin(getPhi()));
        
        this.point.getGroup().setY(point);
        
        repaint();
    }
    
    public void move(double theta, double phi, double point){
        bola[0].setXYZ(0, Math.sin(theta)*getL1(), -Math.cos(theta)*getL1());
        fio[0].clear();
        fio[0].addPoint(0, 0, 0);
        fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());
        
        bola[1].setXYZ(0, bola[0].getY()+Math.sin(phi)*getL2(), bola[0].getZ()-Math.cos(phi)*getL2());
        fio[1].clear();
        fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
        fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());
        
        this.point.getGroup().setY(point);
        
        repaint();
    }
    
    public void setTheta(double ang){
        bola[0].setXYZ(0, Math.sin(ang)*getL1(), -Math.cos(ang)*getL1());
        fio[0].clear();
        fio[0].addPoint(0, 0, 0);
        fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());
        //force.setXYZ(0, bola.getY(), bola.getZ());
        vel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());

        keepDistances();
        bola[1].setRotationPoint(new javax.vecmath.Vector3d(0,bola[0].getY(),bola[0].getZ()));
        fio[1].clear();
        fio[1].addPoint(0,bola[0].getY(),bola[0].getZ());
        fio[1].addPoint(0,bola[1].getY(),bola[1].getZ());
        vel[1].setXYZ(0,bola[1].getY(),bola[1].getZ());
        setThetaVecVel(getThetaVecVel());
    }
    
    public void setPhi(double ang){
        bola[1].setXYZ(0, bola[0].getY()+Math.sin(ang)*getL2(), bola[0].getZ()-Math.cos(ang)*getL2());
        fio[1].clear();
        fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
        fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());
        //force.setXYZ(0, bola.getY(), bola.getZ());
        vel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
        setPhiVecVel(getPhiVecVel());
    }
    
    public double getTheta(){
        int n = (int) (bola[0].getY()/Math.abs(bola[0].getY()));
        return n*Math.acos(-bola[0].getZ()/getL1());
    }
    
    public double getPhi(){
        int n = (int) ((bola[1].getY()-bola[0].getY())/Math.abs(bola[1].getY()-bola[0].getY()));
        return n*Math.acos(-(bola[1].getZ()-bola[0].getZ())/getL2());
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.1"))){
            setAlphaAndBeta(0,0);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.2"))){
            setZoom(1);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.3"))){
            setZoom(.5);
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.4"))){
            this.setDisplayMode(this.DISPLAY_PLANAR_YZ);
            repaint();
            ((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.6"));
            ((javax.swing.JMenuItem) e.getSource()).setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.6"));
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.6"))){
            this.setDisplayMode(this.DISPLAY_NO_PERSPECTIVE);
            repaint();
            ((javax.swing.JMenuItem) e.getSource()).setText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.4"));
            ((javax.swing.JMenuItem) e.getSource()).setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.tip.4"));
        }
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/pend2m/resources/ReCExpPend2M").getString("rec.exp.customizer.viewMenu.title.5")))
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
    
    public void config(double theta, double phi, double thetaDot, double phiDot, double l1, double l2, double amp, double fase){
        bola[0].setXYZ(0, Math.sin(theta)*getL1(), -Math.cos(theta)*getL1());
        fio[0].clear();
        fio[0].addPoint(0, 0, 0);
        fio[0].addPoint(0, bola[0].getY(), bola[0].getZ());
        
        bola[1].setXYZ(0, bola[0].getY()+Math.sin(phi)*getL2(), bola[0].getZ()-Math.cos(phi)*getL2());
        fio[1].clear();
        fio[1].addPoint(0, bola[0].getY(), bola[0].getZ());
        fio[1].addPoint(0, bola[1].getY(), bola[1].getZ());
        
        bola[1].setRotationPoint(new javax.vecmath.Vector3d(0,bola[0].getY(),bola[0].getZ()));
        
        vel[0].setXYZ(0, bola[0].getY(), bola[0].getZ());
        vel[0].setSizeY(thetaDot*Math.cos(getTheta()));
        vel[0].setSizeZ(thetaDot*Math.sin(getTheta()));
        
        vel[1].setXYZ(0, bola[1].getY(), bola[1].getZ());
        vel[1].setSizeY(phiDot*Math.cos(getPhi()));
        vel[1].setSizeZ(phiDot*Math.sin(getPhi()));
        
        this.fase = fase;
        setAmp(amp);
    }
    
}
