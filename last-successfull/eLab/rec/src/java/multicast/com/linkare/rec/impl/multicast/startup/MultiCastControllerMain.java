/*
 * MultiCastControllerMain.java
 *
 * Created on 19 de Agosto de 2002, 15:57
 */

package com.linkare.rec.impl.multicast.startup;

import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerPOATie;
import com.linkare.rec.impl.multicast.ReCMultiCastController;
import com.linkare.rec.impl.utils.ORBBean;

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
	public static void main(String[] args) {
		ReCMultiCastController mcc = null;
		try {
			ORBBean.getORBBean(args);

			mcc = new ReCMultiCastController();
			MultiCastControllerPOATie mccpoatie = (MultiCastControllerPOATie) ORBBean.getORBBean()
					.registerRootPOAServant(MultiCastController.class, mcc, ReCMultiCastController.MULTICAST_INIT_REF.getBytes());
			String corbaURL = ORBBean.getORBBean().bindObjectToCorbalocService(ReCMultiCastController.MULTICAST_BIND_NAME,
					ORBBean.getORBBean().getRootPOA().servant_to_reference(mccpoatie));

			System.out.println("MultiCastController listening for requests at URL " + corbaURL);
			
			Thread.currentThread().join();

		} catch (Exception e) {
			System.out.println("Abnormal exit...");
			ORBBean.getORBBean(args).killORB();
			mcc.shutdown();
			mcc = null;
			e.printStackTrace();
		}

	}

}
