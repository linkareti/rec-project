/*
 * HardwareActivator.java
 *
 * Created on 28 de Outubro de 2002, 12:30
 */

package com.linkare.rec.impl.utils;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;

import com.linkare.rec.acquisition.DataProducerPOATie;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;

public class DataProducerActivator extends LocalObject implements ServantActivator {

	private static String RECMULTICAST_DATAPRODUCER_LOGGER = "ReCMultiCastDataProducer.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(RECMULTICAST_DATAPRODUCER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));
		}
	}

	/** Creates a new instance of HardwareActivator */
	public DataProducerActivator() {
		baseDir = System.getProperty("user.dir") + System.getProperty("file.separator") + "DataProducers";
		java.io.File f = new java.io.File(baseDir);
		if (!f.exists())
			f.mkdirs();
		f = null;

	}

	private static String baseDir = null;

	public void etherealize(byte[] oid, POA poa, Servant servant, boolean param, boolean param4) {

		try {
			Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
					"Etherealizing object " + (new String(oid)) + " in POA " + poa.the_name());
			Deactivatable objdataser = (Deactivatable) (((DataProducerPOATie) servant)._delegate());
			if (!objdataser.alreadySerialized()) {
				String filename = new String(oid);
				int barPos = filename.lastIndexOf(System.getProperty("file.separator"));
				String dir = baseDir + System.getProperty("file.separator") + filename.substring(0, barPos);
				filename = filename.substring(barPos + 1);
				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
						"Deactivating object " + filename + " in POA " + poa.the_name());
				SerializationHelper.writeObject(filename, dir, objdataser);
				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER)
						.log(Level.FINE, "Serializing for the first time...!");
				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
						"Deactivated object " + filename + " in POA " + poa.the_name());
			} else {
				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
						"Object already serialized no needed to serialize again!");
			}

		} catch (java.io.IOException e) {
			LoggerUtil.logThrowable("Error deactivating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e, Logger
					.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		}

	}

	public org.omg.PortableServer.Servant incarnate(byte[] oid, POA poa) throws ForwardRequest {

		try {
			String filename = new String(oid);
			int barPos = filename.lastIndexOf(System.getProperty("file.separator"));
			String dir = baseDir + System.getProperty("file.separator") + filename.substring(0, barPos);
			filename = filename.substring(barPos + 1);

			Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
					"Activating object " + filename + " in POA " + poa.the_name());

			Object readed = SerializationHelper.readObject(filename, dir);
			// System.out.println("is readed an instance of ReCMultiCastDataProducer?? "
			// + (readed instanceof ReCMultiCastDataProducer));

			ReCMultiCastDataProducer dataProducer = (ReCMultiCastDataProducer) readed;

			Deactivator deactivator = new Deactivator(dataProducer);

			dataProducer.setOID(new String(oid));

			// org.omg.PortableServer.Servant s=null;
			// s=poa.reference_to_servant(dataProducerImpl.getDataProducer());

			DataProducerPOATie s = new DataProducerPOATie(dataProducer, poa);

			Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
					"Activated object " + filename + " in POA " + poa.the_name());
			return s;
		} catch (java.io.IOException e) {
			LoggerUtil.logThrowable("Error activating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e, Logger
					.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		} catch (ClassNotFoundException e) {
			LoggerUtil.logThrowable("Error activating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e, Logger
					.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		}
	}

}
