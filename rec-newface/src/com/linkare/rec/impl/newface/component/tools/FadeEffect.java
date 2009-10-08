/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component.tools;

/**
 * 
 * @author iies-consultor922
 */
public interface FadeEffect {

    public enum State {
	OPAQUE, TRANSLUCENT, FADING_IN, FADING_OUT
    }

    void fadeIn();

    void fadeOut();

    State getFadeEffectState();

}
