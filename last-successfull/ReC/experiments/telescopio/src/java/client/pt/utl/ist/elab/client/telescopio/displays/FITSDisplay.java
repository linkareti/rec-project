/*
 * FITSDisplay.java
 *
 * Created on February 2, 2005, 4:57 PM
 */

package pt.utl.ist.elab.client.telescopio.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.client.experiment.DataDisplayEnum;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

import eap.fitsbrowser.FITSFileDisplay;

public class FITSDisplay extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay,
		com.linkare.rec.impl.client.experiment.ExpDataModelListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3211953961172698100L;

	/** Creates new form FITSDisplay */
	public FITSDisplay() {
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents()//GEN-BEGIN:initComponents
	{

		setLayout(new java.awt.BorderLayout());

	}//GEN-END:initComponents

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
		return new javax.swing.ImageIcon(getClass().getResource(
				"/pt/utl/ist/elab/client/telescopio/resources/telescopio_iconified.png"));
	}

	@Override
	public javax.swing.JMenuBar getMenuBar() {
		return null;
	}

	@Override
	public javax.swing.JToolBar getToolBar() {
		return null;
	}

	@Override
	public void newSamples(final com.linkare.rec.impl.client.experiment.NewExpDataEvent evt) {
		if (model == null) {
			return;
		}
		for (int i = evt.getSamplesStartIndex(); i <= evt.getSamplesEndIndex(); i++) {
			if (model.getValueAt(i, model.getChannelIndex("Imagem_Telescopio")) != null) {
				final byte[] image = model.getValueAt(i, model.getChannelIndex("Imagem_Telescopio")).getValue()
						.getByteArrayValue().getData();
				final FITSFileDisplay ffd = new FITSFileDisplay();
				add(ffd, java.awt.BorderLayout.CENTER);
				try {
					final java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(image);
					ffd.load(bais);
				} catch (final java.io.IOException ioe) {
					ioe.printStackTrace();
				}
			} else if (model.getValueAt(i, model.getChannelIndex("Error_Channel")) != null) {
				final byte[] error = model.getValueAt(i, model.getChannelIndex("Error_Channel")).getValue()
						.getByteArrayValue().getData();

				if (error != null && error.length > 0) {
					javax.swing.JOptionPane.showMessageDialog(null,
							ReCResourceBundle.findStringOrDefault("telescopio$rec.exp.telescopio.lbl.error","telescopio$rec.exp.telescopio.lbl.error"),
							ReCResourceBundle.findStringOrDefault("telescopio$rec.exp.telescopio.title.error","telescopio$rec.exp.telescopio.title.error"),
							javax.swing.JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}

	private com.linkare.rec.impl.client.experiment.ExpDataModel model = null;

	@Override
	public void setExpDataModel(final com.linkare.rec.impl.client.experiment.ExpDataModel model) {
		if (this.model != null) {
			model.removeExpDataModelListener(this);
		}

		this.model = model;

		if (this.model != null) {
			this.model.addExpDataModelListener(this);
		}
	}

	@Override
	public String getName() {
		return ReCResourceBundle.findStringOrDefault("telescopio$rec.exp.display.telescopio.title.1", "FITS");
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	// End of variables declaration//GEN-END:variables
    @Override
    public DataDisplayEnum getDisplayType() {
        return DataDisplayEnum.FILE;
    }

}
