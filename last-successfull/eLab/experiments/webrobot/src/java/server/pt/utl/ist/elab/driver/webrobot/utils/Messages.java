/*
 * Messages.java
 *
 * Created on 14 de Marco de 2003, 17:18
 */

package pt.utl.ist.elab.driver.webrobot.utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */

public class Messages {

	private static boolean errorOccurred;

	/** Creates a new instance of Error */
	public Messages() {
		Messages.errorOccurred = false;
	}

	public static void displayErrorMessage(final String errorMessage) {
		System.out.println(errorMessage);
		javax.swing.JOptionPane.showMessageDialog(new javax.swing.JFrame(), errorMessage, "Erro",
				javax.swing.JOptionPane.ERROR_MESSAGE);
		Messages.setErrorOccurred(true);
	}

	public static boolean isErrorOccurred() {
		return Messages.errorOccurred;
	}

	public static void setErrorOccurred(final boolean errorState) {
		Messages.errorOccurred = errorState;
	}
}
