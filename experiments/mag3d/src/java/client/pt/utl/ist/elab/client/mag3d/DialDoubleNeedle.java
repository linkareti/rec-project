/* 
 * DialDoubleNeedle.java created on 16 de Out de 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.client.mag3d;

/**
 * 
 * @author joao - Linkare TI
 * 
 * Modification of the DialDemo2 from JFreeChart
 */
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.ScrollPaneConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

/**
 * A sample application showing the use of a {@link DialPlot}.
 */
@SuppressWarnings("serial")
public class DialDoubleNeedle extends JPanel {

    /** The first dataset. */
    private DefaultValueDataset dataset1;
    /** The second dataset. */
    private DefaultValueDataset dataset2;
    private JFreeChart chart;

    /** 
     * Creates a new instance.
     *
     * @param title  the frame title.
     */
    public DialDoubleNeedle() {
        super();

        this.dataset1 = new DefaultValueDataset(0.0);
        this.dataset2 = new DefaultValueDataset(0.0);

        // get data for diagrams
        DialPlot plot = new DialPlot();
        plot.setView(0.0, 0.0, 1.0, 1.0);
        plot.setDataset(0, this.dataset1);
        plot.setDataset(1, this.dataset2);
        StandardDialFrame dialFrame = new StandardDialFrame();
        dialFrame.setBackgroundPaint(Color.lightGray);
        dialFrame.setForegroundPaint(Color.darkGray);
        plot.setDialFrame(dialFrame);

        GradientPaint gp = new GradientPaint(new Point(),
                new Color(255, 255, 255), new Point(),
                new Color(170, 170, 220));
        DialBackground db = new DialBackground(gp);
        db.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.VERTICAL));
        plot.setBackground(db);

        DialTextAnnotation annotation1 = new DialTextAnnotation("mA/º");
        annotation1.setFont(new Font("Dialog", Font.BOLD, 14));
        annotation1.setRadius(0.7);

        plot.addLayer(annotation1);

        DialValueIndicator dvi = new DialValueIndicator(0);//new DialValueIndicator(0, "c");
        dvi.setFont(new Font("Dialog", Font.PLAIN, 10));
        dvi.setOutlinePaint(Color.darkGray);
        dvi.setRadius(0.60);
        dvi.setAngle(-103.0);
        dvi.setNumberFormat(new java.text.DecimalFormat("0"));
        plot.addLayer(dvi);

        DialValueIndicator dvi2 = new DialValueIndicator(1);//new DialValueIndicator(1, "c");
        dvi2.setFont(new Font("Dialog", Font.PLAIN, 10));
        dvi2.setOutlinePaint(Color.red);
        dvi2.setRadius(0.60);
        dvi2.setAngle(-77.0);
        dvi2.setNumberFormat(new java.text.DecimalFormat("0"));
        plot.addLayer(dvi2);

        StandardDialScale scale = new StandardDialScale(0, 200, -130, -280, 20, 9);
        scale.setTickRadius(0.88);
        scale.setTickLabelOffset(0.15);
        scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
        plot.addScale(0, scale);

        StandardDialScale scale2 = new StandardDialScale(0, 90, -180, -90, 30, 5);
        scale2.setTickRadius(0.50);
        scale2.setTickLabelOffset(0.15);
        scale2.setTickLabelFont(new Font("Dialog", Font.PLAIN, 10));
        scale2.setMajorTickPaint(Color.red);
        plot.addScale(1, scale2);
        plot.mapDatasetToScale(1, 1);

        DialPointer needle2 = new DialPointer.Pin(1);
        needle2.setRadius(0.55);
        plot.addLayer(needle2);

        DialPointer needle = new DialPointer.Pointer(0);
        plot.addLayer(needle);

        DialCap cap = new DialCap();
        cap.setRadius(0.10);
        plot.setCap(cap);

        chart = new JFreeChart(plot);
        chart.setTitle("Title");
        ChartPanel cp1 = new ChartPanel(chart);
        cp1.setPreferredSize(new Dimension(300, 300));
        cp1.setMouseZoomable(true, false);
        add(cp1);

    }

    void setTitle(String title) {
        chart.setTitle(title);
        return;
    }

    /**
     * @return the dataset1
     */
    public DefaultValueDataset getDataset1() {
        return dataset1;
    }

    /**
     * @param dataset1 the dataset1 to set
     */
    public void setDataset1(DefaultValueDataset dataset1) {
        this.dataset1 = dataset1;
    }

    /**
     * @return the dataset2
     */
    public DefaultValueDataset getDataset2() {
        return dataset2;
    }

    /**
     * @param dataset2 the dataset2 to set
     */
    public void setDataset2(DefaultValueDataset dataset2) {
        this.dataset2 = dataset2;
    }

    /**
     * Starting point for the demo application.
     * 
     * @param args  ignored.
     */
    public static void main(final String args[]) {
        final javax.swing.JFrame test = new javax.swing.JFrame();
        test.addWindowListener(new java.awt.event.WindowAdapter() {

            @Override
            public void windowClosing(final java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;

        final JPanel jpanel1 = new JPanel();

        DialDoubleNeedle app1 = new DialDoubleNeedle();
        app1.setTitle("Current/Angle");


        jpanel1.add(app1, gridBagConstraints);


        final javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(jpanel1, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollPane.setViewportView(jpanel1);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);




        scrollPane.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        //scrollPane.getViewportBorderBounds();
                        final Rectangle old_rect = jpanel1.getVisibleRect();
                        final Rectangle new_rect = scrollPane.getViewportBorderBounds();
                        //new_rect.x = old_rect.x - scrollPane.getHorizontalScrollBar().getHeight();
                        //new_rect.width = old_rect.width - scrollPane.getVerticalScrollBar().getWidth();

                        new_rect.x = old_rect.x - 20;
                        new_rect.width = old_rect.width - 20;

                        jpanel1.scrollRectToVisible(new Rectangle(50, 50));
                        //scrollPane.getViewport().getComponent(0).;
                    }
                });
            }
        });



        test.getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);
        test.pack();
        test.setVisible(true);


        int i;
        Number v;
        Number ang;

        for (i = 0; i < 1000000000; i++) {
            if (i == (1000000000 - 1)) {
                i = 0;
                v = app1.dataset1.getValue();
                app1.dataset1.setValue(v.doubleValue() + 1.0);
                ang = app1.dataset2.getValue();
                app1.dataset2.setValue(ang.doubleValue() + 1.0);

                //scrollPane.
                scrollPane.getHorizontalScrollBar().repaint();
                scrollPane.getVerticalScrollBar().repaint();
            }
        }

    }
}