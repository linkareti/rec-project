package pt.utl.ist.elab.client.virtual.guipack;

/*
 * Class.java
 *
 * Created on 21 de Dezembro de 2004, 17:03
 */

/**
 * 
 * @author nomead
 */
public class GUtils {

	/** Creates a new instance of Class */
	public GUtils() {
	}

	public static double validateInput(final javax.swing.JTextField textField, final javax.swing.JSlider slider,
			final double multiplier) throws NumberFormatException {
		int val = (int) Math.round(Double.parseDouble(textField.getText()));

		if (val > slider.getMaximum()) {
			val = slider.getMaximum();
		} else if (val < slider.getMinimum()) {
			val = slider.getMinimum();
		}

		textField.setText(Integer.toString(val));
		slider.setValue(val);
		return val * multiplier;
	}

	public static float validateInput(final javax.swing.JTextField textField, final float min, final float max)
			throws NumberFormatException {
		float val;

		try {
			val = Float.parseFloat(textField.getText());
		} catch (final NumberFormatException e) {
			textField.setText("5.0");
			val = Float.parseFloat(textField.getText());
		}

		if (val > max) {
			val = max;
		} else if (val < min) {
			val = min;
		}

		textField.setText(String.valueOf(val));
		return val;
	}

	public static String trimDecimalN(final double val, final int n) {
		final String str = Double.toString(val);
		String str1;
		String str2 = "";
		try {
			str1 = str.substring(0, str.indexOf(".") + n + 1);
		} catch (final StringIndexOutOfBoundsException e) {
			str1 = str;
		}
		try {
			str2 = str.substring(str.indexOf("E"), str.length());
		} catch (final StringIndexOutOfBoundsException e) {
		}

		return str1.concat(str2);
	}

	public static double getNumSld(final String str, final int i) {
		return Double.parseDouble(str.substring(i, str.length() - i));
	}

}
