/*
 * Debugger.java
 *
 * Created on 27 de Abril de 2003, 20:19
 */

package pt.utl.ist.elab.driver.webrobot.debug;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class Debugger {

	/** Creates a new instance of Debugger */
	public Debugger() {
	}

	public static void print(final Object className, final String text) {
		System.out.println(className.getClass().getName() + " says: " + text);
	}

}