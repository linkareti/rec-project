package com.linkare.rec.impl.newface.component.media;

import com.linkare.rec.impl.newface.component.AbstractContentPane;
import java.awt.Canvas;

/**
 * Painel de vídeo da aplicação.
 * @author bcatarino
 */
public class VideoBox extends AbstractContentPane {

    /** Creates new form VideoPanel */
    public VideoBox() {
        initComponents();
    }

    /**
     * Devolve o Canvas associado ao painel de vídeo e onde será mostrado
     * o output do vídeo.
     * @return
     */
    public Canvas getVideoOutput() {
        return videoCanvas;
    }

    /**
     * Altera o tamanho da videobox.
     * @param width
     * @param height
     */
    public void changeSize(int width, int height) {
        this.setSize(width, height);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mediaPanel = new javax.swing.JPanel();
        videoCanvas = new java.awt.Canvas();

        videoCanvas.setVisible(true);

        javax.swing.GroupLayout mediaPanelLayout = new javax.swing.GroupLayout(mediaPanel);
        mediaPanel.setLayout(mediaPanelLayout);
        mediaPanelLayout.setHorizontalGroup(
            mediaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 320, Short.MAX_VALUE)
            .addGroup(mediaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(videoCanvas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE))
        );
        mediaPanelLayout.setVerticalGroup(
            mediaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 240, Short.MAX_VALUE)
            .addGroup(mediaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(videoCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mediaPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mediaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel mediaPanel;
    private java.awt.Canvas videoCanvas;
    // End of variables declaration//GEN-END:variables

}
