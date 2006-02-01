/*
 * HardwareInit.java
 *
 * Created on 5 de Junho de 2003, 13:41
 *
 *Initializes the hardware associated with the "Aleatorio" Experiment.
 *That is, we initialize the webcam and the sound board.
 */

package pt.utl.ist.elab.driver.Aleatorio.Hardware;

import pt.utl.ist.elab.driver.Aleatorio.Utils.FileOps;

/**
 *
 * @author  PC
 */
public class HardwareInit {
    
    private WebCamThread webcam = null;
    private SoundThread soundBoard = null;
    
    /** Creates a new instance of HardwareInit */
    public HardwareInit() {
        //Initializes the webcam
        this.webcam = new WebCamThread();
        this.soundBoard = new SoundThread();
    }
    
    public WebCamThread getWebCamThread()
    {
        return this.webcam;
    }
    
    public SoundThread getSoundThread()
    {
        return this.soundBoard;
    }
}
