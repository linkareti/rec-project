/*
 * StaticCharts.java
 *
 * Created on 26 de Mar�o de 2005, 0:35
 */

package pt.utl.ist.elab.client.vmvbrown.displays;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

import java.awt.event.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import org.jfree.ui.*;
import org.jfree.chart.encoders.*;
import java.awt.print.*;
/**
 *
 * @author  nomead
 */
public class StaticChart extends javax.swing.JPanel implements Printable, ActionListener, MouseListener {
    
    protected pt.utl.ist.elab.client.virtual.guipack.PopupMenu viewPopMenu;
    
    protected BufferedImage image;
    private int width, height;
    
    /** Creates a new instance of Chart1 */
    public StaticChart(int w, int h){
        super();
        width = w;
        height = h;
    }
    
    private void buildPopupMenu(){
        viewPopMenu = new pt.utl.ist.elab.client.virtual.guipack.PopupMenu(this);
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.save"),java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.save.tip"));
        viewPopMenu.addItem(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.print"),java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.print.tip"));
    }
    
    
    public void makeImage(byte [] b){
        addMouseListener(this);
        buildPopupMenu();
        image = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_INDEXED);
        image.getGraphics().drawImage(java.awt.Toolkit.getDefaultToolkit().createImage(new java.awt.image.MemoryImageSource(image.getWidth(), image.getHeight(), new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_INDEXED).getColorModel(), b, 0, image.getWidth())),0,0,null);
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null)
            g.drawImage(image,0,0,getWidth(),getHeight(),this);
        else {
            String statusStr = java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.statusStr.recImage");
            g.setFont(new java.awt.Font("SansSerif",java.awt.Font.BOLD,26));
            
            int x = (int) Math.round((double) getWidth()/2d)-(int) Math.round((double)g.getFontMetrics().stringWidth(statusStr)/2);
            int y = (int) Math.round((double) getHeight()/2d)-g.getFontMetrics().getHeight()+5;
            
            g.setColor(new java.awt.Color(.6f, .12f, .3f));
            g.drawString(statusStr, x, (int) Math.round((double) getHeight()/2d));
            
            g.setColor(new java.awt.Color(0,0,0,.4f));
            g.fillRect(0,y,getWidth(),g.getFontMetrics().getHeight());
            
            g.setColor(java.awt.Color.WHITE);
            g.drawLine(0, y+2, getWidth(),y+2);
            g.drawLine(0, y+g.getFontMetrics().getHeight()-2, getWidth(),y+g.getFontMetrics().getHeight()-2);
        }
    }
    
    public void doSaveAs() throws IOException {
        
        JFileChooser fileChooser = new JFileChooser();
        ExtensionFileFilter filter = new ExtensionFileFilter(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("PNG_Image_Files"),".png");
        fileChooser.addChoosableFileFilter(filter);
        
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            
            if (!filename.endsWith(".png")) {
                filename = filename + ".png";
            }
            
            OutputStream out = new BufferedOutputStream(new FileOutputStream(new File(filename)));
            
            EncoderUtil.writeBufferedImage(image, ImageFormat.PNG, out);
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
    
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if(pageIndex >= 1){
            return Printable.NO_SUCH_PAGE;
        }
        if(g == null) {
            return Printable.NO_SUCH_PAGE;
        }
        Graphics2D g2     = (Graphics2D) g;
        double     scalex = pageFormat.getImageableWidth() / (double) getWidth();
        double     scaley = pageFormat.getImageableHeight() / (double) getHeight();
        double     scale  = Math.min(scalex, scaley);
        g2.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
        g2.scale(scale, scale);
        g2.drawImage(image,0,0,null);
        return Printable.PAGE_EXISTS;
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.save")))
            try{doSaveAs();}catch(IOException io){}
        else if (e.getActionCommand().equalsIgnoreCase(java.util.ResourceBundle.getBundle("pt/utl/ist/elab/virtual/client/mvbrown/resources/ReCExpMvBrown").getString("rec.exp.displays.print")))
            createChartPrintJob();
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
