/*
 * Animation2D.java
 *
 * Created on 24 de Março de 2005, 3:09
 */

package pt.utl.ist.elab.virtual.client.mvbrown.displays;

import java.awt.event.*;
import org.opensourcephysics.display.*;
import javax.swing.*;
import pt.utl.ist.elab.virtual.guipack.*;

import org.jfree.chart.encoders.*;
import java.awt.print.*;
import java.io.*;
import java.awt.*;

import pt.utl.ist.elab.virtual.utils.ByteUtil;
/**
 *
 * @author  nomead
 */
public class Animation2D extends DrawingPanel implements Drawable, ActionListener, MouseListener, BrownMovement {
    
    private Dataset avgPos;
    
    private String actionStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.animation.actionStr");
    
    /** Creates a new instance of Animation2D */
    public Animation2D() {
        super();
        addMouseListener(this);
        setPreferredMinMax(-50,50,-50,50);
        setSquareAspect(true);
    }
    
    protected void buildPopupmenu(){
        popupmenu.setEnabled(true);
        ActionListener listener = this;
        JMenuItem item = new JMenuItem("Zoom In");
        item.addActionListener(listener);
        popupmenu.add(item);
        item = new JMenuItem("Zoom Out");
        item.addActionListener(listener);
        popupmenu.add(item);
        item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.save"));
        item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.save.tip"));
        item.addActionListener(listener);
        popupmenu.add(item);
        item = new JMenuItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.print"));
        item.setToolTipText(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.print.tip"));
        item.addActionListener(listener);
        popupmenu.add(item); 
    }
    
    public void config(int _numPart, byte _animaRadius, java.awt.Color _cor){
        state = new double[_numPart][2]; //(x,y)
        numPart = _numPart;
        cor = _cor;
        raio = _animaRadius;
        addDrawable(this);
        avgPos = new Dataset(java.awt.Color.RED,java.awt.Color.RED,true);
        addDrawable(avgPos);
        repaint();
    }
    private boolean isMedVisible = true;
    private boolean isVisible = true;
    private int numPart;
    private double [][] state;
    
    private java.awt.Color cor;
    private byte raio;
    
    public void draw(DrawingPanel panel, java.awt.Graphics g) {
        if (isVisible){
            int xpix = 0;
            int ypix = 0;
            g.setColor(cor);
            
            for(int i = 0; i < numPart; i++){
                xpix = panel.xToPix(state[i][0])-raio;
                ypix = panel.yToPix(state[i][1])-raio;
                g.fillOval(xpix,ypix,2*raio,2*raio);
            }
        }
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
        paintDrawableList(g, tempList);
        
        g.setColor(new java.awt.Color(.6f, .12f, .3f));
        g.drawString(actionStr, 5, getHeight()-10);
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase("Zoom In"))
            zoomIn();
        else if (e.getActionCommand().equalsIgnoreCase("Zoom Out"))
            zoomOut();
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.save")))
            try{doSaveAs();}catch(IOException io){}
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.print")))
            createChartPrintJob();
    }
    
    public void mouseClicked(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
    
    public void mousePressed(MouseEvent e) {
        if (javax.swing.SwingUtilities.isRightMouseButton(e)){
            actionStr = "";
            repaint();
        }
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void moves(byte[] mv) {
        float [] mvf = ByteUtil.byteArrayToFloatArray(mv);
        
        double [] med = new double[2];
        if (isVisible || isMedVisible){
            if (mvf.length == 2*numPart){ //dim 2
                for (int i = 0; i < state.length; i++){
                    if (isVisible){
                        state[i][0] = mvf[i];
                        state[i][1] = mvf[i+1];
                    }
                    if (isMedVisible){
                        med[0] += mvf[i];
                        med[1] += mvf[i+1];
                    }
                }
                if (isMedVisible)
                    avgPos.append(med[0]/numPart,med[1]/numPart);
            }
            else { //dim 1
                for (int i = 0; i < state.length; i++){
                    if (isVisible)
                        state[i][0] = mvf[i];
                    if (isMedVisible)
                        med[0] += mvf[i];
                }
                if (isMedVisible)
                    avgPos.append(med[0]/numPart,0);
            }
        }
        repaint();
    }
    
    public void doSaveAs() throws IOException {
        
        JFileChooser fileChooser = new JFileChooser();
        org.jfree.ui.ExtensionFileFilter filter = new org.jfree.ui.ExtensionFileFilter(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("PNG_Image_Files"),".png");
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
