/*
 * Sistema.java
 *
 * Created on 19 de Mar�o de 2005, 16:19
 */

package pt.utl.ist.elab.driver.vcargas3d;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.opensourcephysics.displayejs.InteractiveCharge;

/**
 * 
 * @author n0dP2
 */
public class Sistema {

	/** Creates a new instance of Sistema */
	public Sistema() {
	}

	// public static void main(String args[]){
	// stringToSistema("&1#7#2#12");
	// javax.swing.JFrame dummy = new javax.swing.JFrame();
	// pt.utl.ist.elab.virtual.client.cargas3d.displays.Painel painel = new
	// pt.utl.ist.elab.virtual.client.cargas3d.displays.Painel();
	// dummy.getContentPane().add(painel);
	// for(int i=0;i<sistema.size();i++){
	// painel.addDrawable((InteractiveCharge)sistema.get(i));
	// }
	// dummy.setDefaultCloseOperation(dummy.EXIT_ON_CLOSE);
	// dummy.pack();
	// dummy.show();
	// }

	public static List<InteractiveCharge> sistema = new ArrayList<InteractiveCharge>();

	public static List<InteractiveCharge> novaCarga(final float x, final float y, final float z, final float q) {
		Sistema.sistema.add(Sistema.set(x, y, z, q));
		return Sistema.sistema;
	}

	public static List<InteractiveCharge> editarCarga(final float x, final float y, final float z, final float q,
			final int i) {
		Sistema.sistema.set(i, Sistema.set(x, y, z, q));
		return Sistema.sistema;
	}

	public static List<InteractiveCharge> apagarCarga(final int i) {
		Sistema.sistema.remove(i);
		return Sistema.sistema;
	}

	private static InteractiveCharge set(final float x, final float y, final float z, final float q) {

		final InteractiveCharge carga = new InteractiveCharge();
		carga.setSizeXYZ(0.666, 0.666, 0.666);
		carga.setXYZ(x, y, z);
		carga.setCharge(q);
		Color cor = Color.white;
		if (q > 0) {
			cor = new Color(255, (int) ((25 - q) * 10.2), (int) ((25 - q) * 10.2));
		}
		if (q < 0) {
			cor = new Color((int) ((25 + q) * 10.2), (int) ((25 + q) * 10.2), 255);
		}
		carga.getStyle().setFillPattern(cor);

		return carga;
	}

	public static List<InteractiveCharge> stringToSistema(final String str) {
		Sistema.sistema.clear();
		final String[] str2 = str.split("&");
		for (final String element : str2) {
			final String[] str3 = element.split("#");
			try {
				final float X = Float.parseFloat(str3[0]);
				final float Y = Float.parseFloat(str3[1]);
				final float Z = Float.parseFloat(str3[2]);
				final float Q = Float.parseFloat(str3[3]);
				Sistema.novaCarga(X, Y, Z, Q);
			} catch (final NumberFormatException e) {
			}
		}
		return Sistema.sistema;
	}

	public static String sistemaToString() {
		final List<InteractiveCharge> sist = Sistema.sistema;
		String str = new String();
		String X, Y, Z, Q;
		for (int i = 0; i < sist.size(); i++) {
			final InteractiveCharge carg = sist.get(i);
			X = Float.toString((float) carg.getX());
			Y = Float.toString((float) carg.getY());
			Z = Float.toString((float) carg.getZ());
			Q = Float.toString(carg.getCharge());
			str = str + "&" + X + "#" + Y + "#" + Z + "#" + Q;
		}
		return str;
	}

}