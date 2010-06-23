/*
 * wavelenghtChooser.java
 *
 * Created on 28 de Outubro de 2004, 0:00
 */

package pt.utl.ist.elab.client.vyounginterf;

/**
 *
 * @author  Ean
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public class wavelenghtChooser extends javax.swing.JPanel {

	// debugging main method
	/*
	 * public static void main(String[] args){ JFrame f = new JFrame("Teste");
	 * f.getContentPane().setLayout(new java.awt.BorderLayout());
	 * f.setBounds(400,400, 600,600);//425, 100); wavelenghtChooser wave = new
	 * wavelenghtChooser(); f.getContentPane().add(wave);
	 * f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); f.show(); }// end
	 * debugging main
	 */

	/** Creates new form wavelenghtChooser */
	public wavelenghtChooser() {

		initComponents();

		// adds a new Spectrum to the panel
		SpectrumCanvas spectrum = new SpectrumCanvas();
		jPanelSpectrum.add(spectrum);

	} // end constructor

	// this method returns the (value of the jSlider) selected wavelength
	public int getValue() {
		return jSliderWl.getValue();
	} // end getValue

	// this method returns the maxium wavelenght value ths slider can select
	public int getMaximum() {
		return jSliderWl.getMaximum();
	}

	// this method returns the minimum wavelenght value ths slider can select
	public int getMinimum() {
		return jSliderWl.getMinimum();
	}

	// this method sets the (value of the jSlider) wavelength value
	public void setValue(int value) {
		jSliderWl.setValue(value);
	} // end setValue

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	private void initComponents() {// GEN-BEGIN:initComponents
		java.awt.GridBagConstraints gridBagConstraints;

		rootPanel = new javax.swing.JPanel();
		jPanelSpectrum = new javax.swing.JPanel();
		jSliderWl = new javax.swing.JSlider();

		setLayout(new java.awt.GridBagLayout());

		setPreferredSize(new java.awt.Dimension(420, 100));
		rootPanel.setLayout(new java.awt.GridBagLayout());

		rootPanel.setMaximumSize(new java.awt.Dimension(420, 60));
		rootPanel.setMinimumSize(new java.awt.Dimension(420, 60));
		rootPanel.setPreferredSize(new java.awt.Dimension(420, 60));
		jPanelSpectrum.setLayout(new java.awt.BorderLayout());

		jPanelSpectrum.setBackground(new java.awt.Color(0, 0, 0));
		jPanelSpectrum.setAlignmentX(0.0F);
		jPanelSpectrum.setAlignmentY(0.0F);
		jPanelSpectrum.setMaximumSize(new java.awt.Dimension(418, 25));
		jPanelSpectrum.setMinimumSize(new java.awt.Dimension(418, 25));
		jPanelSpectrum.setPreferredSize(new java.awt.Dimension(418, 25));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		rootPanel.add(jPanelSpectrum, gridBagConstraints);

		jSliderWl.setMajorTickSpacing(50);
		jSliderWl.setMaximum(780);
		jSliderWl.setMinimum(380);
		jSliderWl.setMinorTickSpacing(25);
		jSliderWl.setPaintTicks(true);
		jSliderWl.setValue(580);
		jSliderWl.setAlignmentX(0.0F);
		jSliderWl.setAlignmentY(0.0F);
		jSliderWl.setMaximumSize(new java.awt.Dimension(414, 25));
		jSliderWl.setMinimumSize(new java.awt.Dimension(414, 25));
		jSliderWl.setPreferredSize(new java.awt.Dimension(414, 25));
		jSliderWl.addChangeListener(new javax.swing.event.ChangeListener() {
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				jSliderWlStateChanged(evt);
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		rootPanel.add(jSliderWl, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		add(rootPanel, gridBagConstraints);

	}// GEN-END:initComponents

	private void jSliderWlStateChanged(javax.swing.event.ChangeEvent evt) {// GEN-FIRST:event_jSliderWlStateChanged
		// System.out.println("O valor e': "+jSliderWl.getValue());
		fireStateChanged();
	}// GEN-LAST:event_jSliderWlStateChanged

	private class ModelListener implements ChangeListener, Serializable {
		public void stateChanged(ChangeEvent e) {
			fireStateChanged();
		}
	}

	/**
	 * Subclasses that want to handle model ChangeEvents differently can
	 * override this method to return their own ChangeListener implementation.
	 * The default ChangeListener just forwards ChangeEvents to the
	 * ChangeListeners added directly to the slider.
	 * 
	 * @see #fireStateChanged
	 */
	protected ChangeListener createChangeListener() {
		return new ModelListener();
	}

	/**
	 * Adds a ChangeListener to the slider.
	 * 
	 * @param l the ChangeListener to add
	 * @see #fireStateChanged
	 * @see #removeChangeListener
	 */
	public void addChangeListener(ChangeListener l) {
		listenerList.add(ChangeListener.class, l);
	}

	/**
	 * Removes a ChangeListener from the slider.
	 * 
	 * @param l the ChangeListener to remove
	 * @see #fireStateChanged
	 * @see #addChangeListener
	 * 
	 */
	public void removeChangeListener(ChangeListener l) {
		listenerList.remove(ChangeListener.class, l);
	}

	/**
	 * Returns an array of all the <code>ChangeListener</code>s added to this
	 * JSlider with addChangeListener().
	 * 
	 * @return all of the <code>ChangeListener</code>s added or an empty array
	 *         if no listeners have been added
	 * @since 1.4
	 */
	public ChangeListener[] getChangeListeners() {
		return (ChangeListener[]) listenerList.getListeners(ChangeListener.class);
	}

	/**
	 * Send a ChangeEvent, whose source is this Slider, to each listener. This
	 * method method is called each time a ChangeEvent is received from the
	 * model.
	 * 
	 * @see #addChangeListener
	 * @see EventListenerList
	 */
	protected void fireStateChanged() {
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChangeListener.class) {
				if (changeEvent == null) {
					changeEvent = new ChangeEvent(this);
				}
				((ChangeListener) listeners[i + 1]).stateChanged(changeEvent);
			}
		}
	}

	/**
	 * The changeListener (no suffix) is the listener we add to the Sliders
	 * model. By default this listener just forwards events to ChangeListeners
	 * (if any) added directly to the slider.
	 * 
	 * @see #addChangeListener
	 * @see #createChangeListener
	 */
	protected ChangeListener changeListener = createChangeListener();

	/**
	 * Only one <code>ChangeEvent</code> is needed per slider instance since the
	 * event's only (read-only) state is the source property. The source of
	 * events generated here is always "this". The event is lazily created the
	 * first time that an event notification is fired.
	 * 
	 * @see #fireStateChanged
	 */
	protected transient ChangeEvent changeEvent = null;

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JPanel jPanelSpectrum;
	private javax.swing.JSlider jSliderWl;
	private javax.swing.JPanel rootPanel;
	// End of variables declaration//GEN-END:variables
}// end waveLenghtChooser

// this internal class is responsible for the painting of the visible spectrum
// from 380nm to 780nm
class SpectrumCanvas extends java.awt.Canvas {

	static final int N = 400; // number of lines to be painted, equal to the
	// width of the spectrum
	static final int border = 9; // additional border
	// range we plot wavelenths over
	static final int w1 = 380;
	static final int w2 = 780;

	public void paint(Graphics g) {
		// for text
		Font little = new Font("Dialog", Font.PLAIN, 10);
		FontMetrics littlefm = getFontMetrics(little);

		Font big = new Font("Dialog", Font.BOLD, 14);
		FontMetrics bigfm = getFontMetrics(big);

		// draw the Light spectrum
		for (int i = 0; i < N; i++) {
			float wl = (w2 - w1) * i / (float) N + w1;
			Color shade = Wavelength.wvColor(wl, 1.0f);
			g.setColor(shade);
			g.drawLine(i + border, 0, i + border, 50);
		} // end for

		g.setFont(little);
		g.setColor(Color.gray);
		// draw nm ticks and numbering every 50 nm
		final int span = w2 - w1;
		for (int tick = w1; tick <= w2; tick += 50) {
			int tickPix = ((tick - w1) * N) / span + border;
			g.drawLine(tickPix, 0, tickPix, 5);
			String tickString = String.valueOf(tick);
			g.drawString(tickString, tickPix - littlefm.stringWidth(tickString) / 2, 20);
		} // end for
	} // end paint
}