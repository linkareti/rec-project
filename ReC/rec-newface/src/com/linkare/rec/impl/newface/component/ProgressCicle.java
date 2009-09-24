/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * @author Henrique Fernandes
 */
public class ProgressCicle extends JLabel {

    private static final Logger log = Logger.getLogger(ProgressCicle.class.getName());

    public enum State {IDLE, RUNNING};

    private Icon idleIcon;

    private Icon[] busyIcons;

    private Timer busyIconTimer;

    private int busyIconIndex;

    private State currentState;

    public ProgressCicle() {
    }

    public ProgressCicle(Icon idleIcon, final Icon[] busyIcons, int busyAnimationRate) {
        super(idleIcon);
        setIcon(idleIcon);
        setBusyIcons(busyIcons);
        this.currentState = State.IDLE;
    }

    private void initBusyIconTimer(int busyAnimationRate, final Icon[] busyIcons) {
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                ProgressCicle.this.setIcon(busyIcons[busyIconIndex]);
            }
        });
    }

    public void setIdleIcon(Icon idleIcon) {
        this.idleIcon = idleIcon;
        setIcon(idleIcon);
    }

    public void setBusyIcons(Icon[] busyIcons) {
        this.busyIcons = busyIcons;
        initBusyIconTimer(30, busyIcons);
    }

    public boolean isIdle() {
        return currentState == State.IDLE;
    }

    public boolean isRunning() {
        return currentState == State.RUNNING;
    }

    public void setState(State newState) {
        if (currentState != newState) {
            if (State.RUNNING == newState) {
                setIcon(busyIcons[0]);
                busyIconIndex = 0;
                busyIconTimer.start();
                currentState = State.RUNNING;
            } else {
                busyIconTimer.stop();
                setIcon(idleIcon);
                currentState = State.IDLE;
            }
        }
    }

    public void start() {
        setState(State.RUNNING);
    }

    public void stop() {
        setState(State.IDLE);
    }
}
