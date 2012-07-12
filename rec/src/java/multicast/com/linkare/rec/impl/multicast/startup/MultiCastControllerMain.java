/*
 * MultiCastControllerMain.java
 *
 * Created on 19 de Agosto de 2002, 15:57
 */

package com.linkare.rec.impl.multicast.startup;

import java.awt.Color;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerPOATie;
import com.linkare.rec.impl.multicast.ReCMultiCastController;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiCastControllerMain {

	private static final boolean SHOW_GUI = Boolean.parseBoolean(Defaults.defaultIfEmpty(
			System.getProperty("rec.multicastcontroller.showgui"), "false"));

	/** Creates a new instance of MultiCastControllerMain */
	public MultiCastControllerMain() {

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		ReCMultiCastController mcc = null;
		try {
			ORBBean.getORBBean();

			mcc = new ReCMultiCastController();
			final MultiCastControllerPOATie mccpoatie = (MultiCastControllerPOATie) ORBBean.getORBBean()
					.registerRootPOAServant(MultiCastController.class, mcc,
							ReCMultiCastController.MULTICAST_INIT_REF.getBytes());
			final String corbaURL = ORBBean.getORBBean().bindObjectToCorbalocService(
					ReCMultiCastController.MULTICAST_BIND_NAME,
					ORBBean.getORBBean().getRootPOA().servant_to_reference(mccpoatie));

			System.out.println("MultiCastController listening for requests at URL " + corbaURL);

			if (!GraphicsEnvironment.isHeadless() && MultiCastControllerMain.SHOW_GUI) {
				final JFrame frameForKill = new JFrame();
				final JButton btnExit = new JButton("End MulticastController!");
				btnExit.setBackground(Color.red);
				btnExit.setForeground(Color.white);
				btnExit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						System.exit(-1);

					}
				});
				frameForKill.getContentPane().add(btnExit);
				frameForKill.setVisible(true);
				frameForKill.pack();
			}

			Thread.currentThread().join();

		} catch (final Exception e) {
			System.out.println("Abnormal exit...");
			ORBBean.getORBBean().killORB();
			mcc.shutdown();
			mcc = null;
			e.printStackTrace();
		}

	}

}
