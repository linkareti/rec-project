/*
 * DrawableMolas.java
 *
 * Created on 17 de Fevereiro de 2005, 22:06
 */

package pt.utl.ist.elab.client.vm3;

import java.awt.Color;
import java.awt.Dimension;

import org.opensourcephysics.display.Dataset;
import org.opensourcephysics.display.InteractiveCircle;
import org.opensourcephysics.display.InteractivePanel;
import org.opensourcephysics.displayejs.InteractiveSpring;
import org.opensourcephysics.displayejs.InteractiveText;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author n0dP2
 */
public class DrawableMolas extends InteractivePanel {
	public InteractiveSpring mola1, mola2, mola3;
	private InteractiveText mola1T, mola2T, mola3T;
	public InteractiveCircle bola;
	public Dataset caixa;
	private int rato;
	public M3Customizer gui;

	public DrawableMolas(M3Customizer gui_) {
		gui = gui_;
		setShowCoordinates(false);

		setPreferredMinMax(-2.5, 12.5, -2.5, 12.5);
		setPreferredSize(new Dimension(250, 250));
		mola1T = new InteractiveText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.6",
				"Spring 1"));
		mola1T.getStyle().setEdgeColor(new Color(255, 228, 0));
		mola1T.setXY(5, 11);
		mola3T = new InteractiveText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.8",
				"Spring 3"));
		mola3T.getStyle().setEdgeColor(new Color(255, 228, 0));
		mola3T.setXY(10, -1);
		mola2T = new InteractiveText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.7",
				"Spring 2"));
		mola2T.getStyle().setEdgeColor(new Color(255, 228, 0));
		mola2T.setXY(0, -1);

		mola1 = new InteractiveSpring(0.8);
		mola2 = new InteractiveSpring(0.8);
		mola3 = new InteractiveSpring(0.8);
		mola1.setSizeX(0);
		mola1.setSizeY(-5);
		mola2.setSizeX(5);
		mola2.setSizeY(5);
		mola3.setSizeX(-5);
		mola3.setSizeY(5);
		mola1.setXY(5, 10);
		mola2.setXY(0, 0);
		mola3.setXY(10, 0);
		bola = new InteractiveCircle(5, 5);
		bola.color = Color.gray;
		bola.pixRadius = 7;
		bola.setXY(5, 5);

		caixa = new Dataset();
		caixa.setConnected(true);
		caixa.setMarkerShape(Dataset.NO_MARKER);
		caixa.append(0, 0);
		caixa.append(0, 10);
		caixa.append(10, 10);
		caixa.append(10, 0);
		caixa.append(0, 0);

		addDrawable(mola1);
		addDrawable(mola2);
		addDrawable(mola3);
		addDrawable(mola1T);
		addDrawable(mola2T);
		addDrawable(mola3T);
		addDrawable(caixa);
		addDrawable(bola);

	}

	public void handleMouseAction(InteractivePanel panel, java.awt.event.MouseEvent evt) {
		double mousey, mousex;
		if (panel.getMouseAction() == InteractivePanel.MOUSE_DRAGGED) {
			mousex = panel.getMouseX();
			mousey = panel.getMouseY();
			fazMexer(mousex, mousey);
		}
		rato = panel.getMouseAction();

	}

	public void fazMexer(double mousex, double mousey) {
		try {
			bola.setXY(mousex, mousey);
			mola1.setSizeX(mousex - 5);
			mola1.setSizeY(mousey - 10);
			mola2.setSizeX(mousex);
			mola2.setSizeY(mousey);
			mola3.setSizeX(mousex - 10);
			mola3.setSizeY(mousey);
			gui.textX.setText(Double.toString(QMethods.arredondar(mousex, 2)));
			gui.textY.setText(Double.toString(QMethods.arredondar(mousey, 2)));
			if (gui.textX.getText().equals("5.0") && gui.textY.getText().equals("5.0")) {
				gui.labelInf2.setForeground(java.awt.Color.red);
				gui.labelInf1.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.9",
						"Please disturb the system"));
				gui.labelInf2.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.10",
						"by dragging the mass."));
				gui.buttonOK.setEnabled(false);
			} else {
				gui.labelInf1.setText("");
				gui.labelInf2.setForeground(new java.awt.Color(0, 125, 0));
				gui.labelInf2.setText(ReCResourceBundle.findStringOrDefault("ReCExpM3$rec.exp.customizer.title.11",
						"Ready."));
				gui.buttonOK.setEnabled(true);
			}
			repaint();
		} catch (NullPointerException e) {
		}
	}

}
