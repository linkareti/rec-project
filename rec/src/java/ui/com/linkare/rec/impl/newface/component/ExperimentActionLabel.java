package com.linkare.rec.impl.newface.component;

import static com.linkare.rec.impl.newface.component.ExperimentActionLabel.State.GREEN;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * @author Henrique Fernandes - Linkare TI
 */
public class ExperimentActionLabel extends JLabel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3487382075362301306L;

	public enum State {

		// GREEN
		// #4b6830 = 75, 104, 48
		// #D0E7C5 = 208, 231, 197

		// YELLOW
		// #6D6336 = 109, 99, 54
		// #F7F2BA = 247, 242, 186

		// RED
		// #BA2C36 = 186, 44, 54
		// #FBD1C7 = 251, 209, 199

		GREEN(new Color(75, 104, 48), new Color(208, 231, 197), Color.white), YELLOW(new Color(109, 99, 54), new Color(
				247, 242, 186), Color.white), RED(new Color(186, 44, 54), new Color(251, 209, 199), Color.white);

		private Color foregroundColor;
		private Color backGroundColor;
		private Color borderColor;

		private State(final Color foregroundColor, final Color backGroundColor, final Color borderColor) {
			this.foregroundColor = foregroundColor;
			this.backGroundColor = backGroundColor;
			this.borderColor = borderColor;
		}

		public Color getBackGroundColor() {
			return backGroundColor;
		}

		public Color getBorderColor() {
			return borderColor;
		}

		public Color getForegroundColor() {
			return foregroundColor;
		}

	}

//	private State state;

	public ExperimentActionLabel() {
		this("Hello", GREEN);
	}

	public ExperimentActionLabel(final String text, final State state) {
		super(text);
		setOpaque(true);
		setState(state);
	}

	public void setState(final State state) {
//		this.state = state;
		updateStateUI(state);
	}

	private void updateStateUI(final State state) {
		setForeground(state.getForegroundColor());
		setBackground(state.getBackGroundColor());
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(state.getBorderColor()),
				BorderFactory.createEmptyBorder(3, 10, 3, 10)));
	}

}
