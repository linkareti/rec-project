/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.utl.ist.elab.client.rollpaper;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.customizer.ICustomizer;
import com.linkare.rec.impl.client.customizer.ICustomizerListener;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

/**
 *
 * @author Gedsimon Pereira
 */
public class TestCustomizer  extends JPanel implements ICustomizer{

    @Override
    public void addICustomizerListener(ICustomizerListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void removeICustomizerListener(ICustomizerListener l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JComponent getCustomizerComponent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public JMenuBar getMenuBar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHardwareInfo(HardwareInfo info) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig config) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public HardwareAcquisitionConfig getAcquisitionConfig() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImageIcon getCustomizerIcon() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getCustomizerTitle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
