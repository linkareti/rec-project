/*
 * BalancaTorcao.java
 *
 * Created on 27 de Outubro de 2004, 5:13
 */

package pt.utl.ist.elab.virtual.client.cg;

import org.opensourcephysics.displayejs.*;
import java.awt.event.*;
import javax.swing.*;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 *
 * @author  nomead
 *
 *
 * ang -> rad
 * d -> mm
 * s0 -> mm
 * l -> cm
 * luzX -> cm
 * reguaSize -> cm
 */
public class BalancaTorcao extends DrawingPanel3D implements ActionListener, MouseListener {
    
    protected double d = 50;
    protected double luzX = 0;
    
    /*
     * 0 -> Perspective
     * 1 -> XZ
     * 2 -> XY
     * 3 -> YZ
     * 4 -> Free 3D
     */
    protected short view2D = 0;
    private PopupMenu viewPopMenu;
    
    protected InteractiveSphereMass [] mm;
    protected InteractiveSphereMass [] mM;
    protected InteractiveCylinderSimple barra;
    protected InteractivePlane regua;
    protected InteractiveTrace luz;
    
    public void setListener(InteractionListener list){
        mM[0].addListener(list);
        mM[1].addListener(list);
        mm[0].addListener(list);
        mm[1].addListener(list);
        regua.addListener(list);
    }
    
    /** Creates a new instance of BalancaTorcao */
    public BalancaTorcao() {
        super(DISPLAY_PERSPECTIVE);
        setBorder(new javax.swing.border.TitledBorder(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.title.13","Torsion Balance View")));
        setToolTipText(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.tip.13","Mouse Interactive View"));
        setDecorationType(DECORATION_NONE);
        setAlphaAndBeta(Math.PI/2, Math.PI/70+Math.PI);
        setPreferredMinMaxX(-40, 60);
        setCursorMode(CURSOR_CUBE);
        
        setEnabled(true);
        addMouseListener(this);
        enableInspector(true);
        
        mm = new InteractiveSphereMass[]{new InteractiveSphereMass(15e-3), new InteractiveSphereMass(15e-3)};
        mm[0].setXYZ(-d, 0, 0);
        mm[0].setSizeXYZ(13.2, 13.2, 13.2);
        mm[1].setXYZ(d, 0, 0);
        mm[1].setSizeXYZ(13.2, 13.2, 13.2);
        mm[0].getStyle().setEdgeColor(java.awt.Color.RED);
        mm[1].getStyle().setEdgeColor(java.awt.Color.RED);
        
        mM = new InteractiveSphereMass[]{new InteractiveSphereMass(1.5), new InteractiveSphereMass(1.5)};
        mM[0].setXYZ(-d, 46.5, 0);
        mM[0].setSizeXYZ(26.4, 26.4,26.4);
        mM[1].setXYZ(d, -46.5, 0);
        mM[1].setSizeXYZ(26.4, 26.4,26.4);
        mM[0].getStyle().setEdgeColor(java.awt.Color.RED);
        mM[1].getStyle().setEdgeColor(java.awt.Color.RED);
        
        barra = new InteractiveCylinderSimple();
        barra.setXYZ(-d, 0, 0);
        barra.setRadius(1.25);
        barra.setSizeXYZ(d*2, 0, 0);
        barra.getStyle().setEdgeColor(java.awt.Color.RED);
        barra.getStyle().setFillPattern(java.awt.Color.BLACK);
        
        regua = new InteractivePlane();
        regua.setVectorU(1,0,0);
        regua.setVectorV(0,0,1);
        regua.setSizeXYZ(100, 10, 0);
        regua.setXYZ(0,-500,0);
        regua.getStyle().setEdgeColor(java.awt.Color.WHITE);
        regua.getStyle().setFillPattern(java.awt.Color.WHITE);
        
        luz = new InteractiveTrace();
        luz.addPoint(0,-500,0);
        luz.addPoint(0,0,0);
        luz.setConnected(true);
        luz.getStyle().setEdgeColor(java.awt.Color.RED);
        luz.getStyle().setFillPattern(java.awt.Color.WHITE);
        
        addDrawable(regua);
        addDrawable(mm[0]);
        addDrawable(mm[1]);
        addDrawable(mM[0]);
        addDrawable(mM[1]);
        addDrawable(barra);
        addDrawable(luz);
        
        buildPopupMenu();
    }
    
    private void buildPopupMenu() {
        viewPopMenu = new PopupMenu(this);
        viewPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.title.1","Perspective"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.tip.1","Perspective View (3D)"));
        viewPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.title.2","Frontal View"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.tip.2","Frontal View (2D)"));
        viewPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.title.3","Top View"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.tip.3","Top View (2D)"));
        viewPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.title.4","Lateral View"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.tip.4","Lateral View (2D)"));
        viewPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.title.5","Free 3D"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.tip.5","Go to 3D mantaining the current view"));
        viewPopMenu.addItem(ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.title.6","Measure"), ReCResourceBundle.findStringOrDefault("rec.exp.customizer.balanca.popup.view.tip.6","Define the measure that shows all the objects"));
    }
    
    public void config(double angInit, double l, double s0, double s, double targetSize){
        setD(s);
        setS0(s0);
        setL(l);
        setReguaSize(targetSize);
        updateAngle(angInit);
    }
    
    public double getReguaSize(){
        return regua.getSizeX();
    }
    
    public void setReguaSize(double size){
        regua.setSizeX(size);
    }
    
    public void updateReguaSize(double size){
        regua.setSizeX(size);
        repaint();
    }
    
    public double getL(){
        return Math.abs(regua.getY());
    }
    
    public void setL(double l){
        regua.setY(-Math.abs(l));
    }
    
    public void updateL(double l){
        regua.setY(-Math.abs(l));
        updateLuz();
    }
    
    public void setD(double s){
        d = Math.abs(s);
    }
    
    public void updateD(double s){
        double ang = getAngle();
        d = Math.abs(s);
        mm[0].setXY(-Math.cos(ang)*d, -Math.sin(ang)*d);
        mm[1].setXY(Math.cos(ang)*d, Math.sin(ang)*d);
        mM[0].setX(-d);
        mM[1].setX(d);
        updateBarra();
        repaint();
    }
    
    public double getD(){
        return Math.sqrt(Math.pow(mm[0].getX(),2)+Math.pow(mm[0].getY(),2));
    }
    
    protected void updateGUI(){
        try {
            ((CGCustomizer) getParent()).update();
        }
        catch (ClassCastException e){}
    }
    
    public double getS0(){
        return Math.max(Math.abs(mM[0].getY()), Math.abs(mM[1].getY()));
    }
    
    public void setS0(double s0){
        mM[0].setY(Math.abs(s0));
        mM[1].setY(-Math.abs(s0));
        mM[0].setX(-d);
        mM[1].setX(d);
    }
    
    public void updateS0(double s0){
        mM[0].setY(Math.abs(s0));
        mM[1].setY(-Math.abs(s0));
        repaint();
    }
    
    public void cMode(){
        mM[0].setVisible(false);
        mM[1].setVisible(false);
        repaint();
    }
    
    public void gMode(){
        mM[0].setVisible(true);
        mM[1].setVisible(true);
        repaint();
    }
    
    public double getLuzX(){
        return luzX;
    }
    
    public double getAngle(){
        return Math.atan(mm[0].getY()/mm[0].getX());
    }
    
    public void updateAngle(double ang){
        mm[0].setXY(-Math.cos(ang)*d, -Math.sin(ang)*d);
        mm[1].setXY(Math.cos(ang)*d, Math.sin(ang)*d);
        updateBarra();
        luz.clear();
        luz.addPoint(luzX = Math.tan(getAngle())*(-regua.getY()),regua.getY(),0);
        luz.addPoint(0,0,0);
        repaint();
    }
    
    public void updatePosAngle(double ang){
        mm[0].setXY(-Math.cos(ang)*d, -Math.sin(ang)*d);
        mm[1].setXY(Math.cos(ang)*d, Math.sin(ang)*d);
        
        updateAngle();
    }
    
    public void updateBarra(){
        barra.setXYZ(mm[0].getX(), mm[0].getY(), mm[0].getZ());
        barra.setSizeXYZ(mm[1].getX()-mm[0].getX(), mm[1].getY()-mm[0].getY(), mm[1].getZ()-mm[0].getZ());
    }
    
    public void updateAngle(){
        updateBarra();
        updateLuz();
    }
    
    public void updateLuz(){
        luz.clear();
        luz.addPoint(luzX = Math.tan(getAngle())*(-regua.getY()),regua.getY(),0);
        luz.addPoint(0,0,0);
        repaint();
        updateGUI();
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Perspective")){
            setDisplayMode(DISPLAY_PERSPECTIVE);
            setAlphaAndBeta(Math.PI/2, Math.PI/70+Math.PI);
            setPreferredMinMax(-40, 60, -1,1);
            view2D = 0;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Frontal View")){
            setDisplayMode(DISPLAY_PLANAR_XZ);
            setPreferredMinMax(-60,60,-60,60);
            setAlphaAndBeta(Math.PI/2, 0);
            view2D = 1;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Top View")){
            setDisplayMode(DISPLAY_PLANAR_XY);
            setPreferredMinMax(-60,60,-60,60);
            view2D = 2;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Lateral View")){
            setDisplayMode(DISPLAY_PLANAR_YZ);
            setPreferredMinMaxY(-60,60);
            view2D = 3;
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Free 3D")){
            setDisplayMode(DISPLAY_3D);
            switch (view2D){
                case 1 : setAlphaAndBeta(-Math.PI/2, Math.PI);
                break;
                case 2 : setAlphaAndBeta(-Math.PI/2, Math.PI/2);
                break;
                case 3 : setAlphaAndBeta(0, Math.PI);
            }
            repaint();
        }
        else if (e.getActionCommand().equalsIgnoreCase("Measure")){
            measure();
            repaint();
        }
        
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
    
}
