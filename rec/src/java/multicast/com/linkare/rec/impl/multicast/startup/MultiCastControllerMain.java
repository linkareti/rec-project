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
import java.net.InetAddress;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerPOATie;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.config.ReCSystemPropertyLocation;
import com.linkare.rec.impl.multicast.ReCMultiCastController;
import com.linkare.rec.impl.threading.ProcessingManager;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.SystemExitSecurityManager;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class MultiCastControllerMain {

	/** Creates a new instance of MultiCastControllerMain */
	public MultiCastControllerMain() {

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		System.setSecurityManager(new SystemExitSecurityManager());
		
		ReCMultiCastController mcc = null;
		try {
			
			Set<ReCSystemProperty> listRequiredNotDefined = ReCSystemProperty
					.listRequiredNotDefined(ReCSystemPropertyLocation.MULTICAST);
			if (listRequiredNotDefined.size() > 0) {
				System.out.println("The following system properties are required and where not defined:");
				for (ReCSystemProperty reCSystemProperty : listRequiredNotDefined) {
					System.out.println(reCSystemProperty.toString());
				}
				System.exit(-1);
			}
			
			
			ORBBean.getORBBean();

			mcc = new ReCMultiCastController();
			final MultiCastControllerPOATie mccpoatie = (MultiCastControllerPOATie) ORBBean.getORBBean()
					.registerRootPOAServant(MultiCastController.class, mcc,
							ReCSystemProperty.MULTICAST_INITREF.getValue().getBytes());
			final String corbaURL = ORBBean.getORBBean().bindObjectToCorbalocService(
					ReCSystemProperty.MULTICAST_BINDNAME.getValue(),
					ORBBean.getORBBean().getRootPOA().servant_to_reference(mccpoatie));

			System.out.println("MultiCastController listening for requests at URL " + corbaURL);
			
			String rmiRemoteHostName=System.getProperty("java.rmi.server.hostname");
			if(rmiRemoteHostName==null) {
				rmiRemoteHostName=InetAddress.getLocalHost().getHostAddress();
			}
			String jmxRemotePort=System.getProperty("com.sun.management.jmxremote.port");
			if(jmxRemotePort!=null && jmxRemotePort.trim().length()!=0) {
				final String jmxServiceUrl="service:jmx:rmi:///jndi/rmi://"+rmiRemoteHostName+":"+jmxRemotePort+"/jmxrmi";
				System.out.println("MultiCastController available for JMX at URL " + jmxServiceUrl);
			}
			
			
			
			
			

			if (!GraphicsEnvironment.isHeadless() && Boolean.parseBoolean(ReCSystemProperty.MULTICAST_SHOW_GUI.getValue())) {
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
				frameForKill.setTitle(ReCSystemProperty.MULTICAST_LAB_ID.getValue());
				frameForKill.pack();
			}

			Thread.currentThread().join();

		} catch (final Exception e) {
			System.out.println("Abnormal exit...");
			ORBBean.getORBBean().killORB();
			mcc.shutdown();
			ProcessingManager.getInstance().shutdown();
			e.printStackTrace();
		}

	}

}
