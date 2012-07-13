/*
 * YoungInterfImage.java
 *
 * Created on 20 de Mar�o de 2005, 5:31
 */

package pt.utl.ist.elab.client.vyounginterf.displays;

/**
 *
 * @author   Emanuel Antunes
 */

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.utl.ist.elab.common.virtual.utils.ByteUtil;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;

public class YoungInterfImage extends JPanel implements ExpDataDisplay, ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1040016606949070827L;
	public JLabel label = null;
	public ImageIcon icon = null;
	public JScrollPane scroll = null;
	private int channelIndexImageIcon=-1;

	/** Creates a new instance of YoungInterfImage */
	public YoungInterfImage() {

		setLayout(new BorderLayout());
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(1200, 400));
		scroll = new JScrollPane();
		this.add(scroll);
	}

	@Override
	public void dataModelEnded() {
	}

	@Override
	public void dataModelError() {
	}

	@Override
	public void dataModelStarted() {
	}

	@Override
	public void dataModelStartedNoData() {
	}

	@Override
	public void dataModelStoped() {
	}

	@Override
	public void dataModelWaiting() {
	}

	@Override
	public javax.swing.JComponent getDisplay() {
		return this;
	}

	@Override
	public javax.swing.Icon getIcon() {
		return null;
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	@Override
	public void newSamples(final com.linkare.rec.impl.client.experiment.NewExpDataEvent evt) {

		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			// sample, canal

			PhysicsValue channelIndexValue = model.getValueAt(i, channelIndexImageIcon);
			if (channelIndexValue != null) {
				icon = (ImageIcon) ByteUtil.byteArrayToObject(channelIndexValue
						.getValue().getByteArrayValue().getData());
				label = new JLabel(icon);
				scroll.setViewportView(label);
				repaint();
			}
		}
	}

	private ExpDataModel model = null;

	@Override
	public void setExpDataModel(final ExpDataModel model) {
		if (this.model != null) {
			this.model.removeExpDataModelListener(this);
		}
		this.model = model;
		if (this.model != null) {
			this.model.addExpDataModelListener(this);
			channelIndexImageIcon=this.model.getChannelIndex("imageIcon");
		}
	}
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.IMAGE;
    }

}
