/*
 * TestAscii.java
 *
 * Created on 6 de Abril de 2003, 15:36
 */

package pt.utl.ist.elab.driver.webrobot.utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class TestAscii {

	/** Creates a new instance of TestAscii */
	public TestAscii() {
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 256; i++) {
			System.out.println(i + " " + (byte) (i));
		}
	}
}
