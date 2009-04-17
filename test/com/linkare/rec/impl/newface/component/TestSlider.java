/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.component.media.TimeMarker;
import javax.swing.JSlider;

/**
 * Classe de teste que mostra como implementar o TimeMarker. Neste caso, é um
 * simples JSlider que não acrescenta nada à parent class a não ser os métodos
 * definidos em TimeMarker.
 * @author bcatarino
 */
public class TestSlider extends JSlider implements TimeMarker {

    @Override
    public synchronized int getCurrentPosition() {
        return this.getValue();
    }

    @Override
    public int getMaximumPosition() {
        return this.getMaximum();
    }

    @Override
    public synchronized void setMarkerPosition(int newPosition) {
        this.setValue(newPosition);
    }
}
