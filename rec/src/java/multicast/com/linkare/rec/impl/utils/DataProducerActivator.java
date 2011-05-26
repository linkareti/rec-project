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
import com.linkare.rec.impl.multicast.repository.RepositoryException;
import com.linkare.rec.impl.multicast.repository.RepositoryFactory;

public class DataProducerActivator extends LocalObject implements ServantActivator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3719852533240590451L;
	private static String RECMULTICAST_DATAPRODUCER_LOGGER = "ReCMultiCastDataProducer.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(DataProducerActivator.RECMULTICAST_DATAPRODUCER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(
					Logger.getLogger(DataProducerActivator.RECMULTICAST_DATAPRODUCER_LOGGER));
		}
	}

	/** Creates a new instance of HardwareActivator */
	public DataProducerActivator() {
		DataProducerActivator.baseDir = System.getProperty("user.dir") + System.getProperty("file.separator")
				+ "DataProducers";
		java.io.File f = new java.io.File(DataProducerActivator.baseDir);
		if (!f.exists()) {
			f.mkdirs();
		}
		f = null;

	}

	private static String baseDir = null;

	@Override
	public void etherealize(final byte[] oid, final POA poa, final Servant servant, final boolean param,
			final boolean param4) {

		try {
			Logger.getLogger(DataProducerActivator.RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
					"Etherealizing object " + (new String(oid)) + " in POA " + poa.the_name());
			final Deactivatable objdataser = (Deactivatable) (((DataProducerPOATie) servant)._delegate());
			if (!objdataser.alreadySavedOnRepository()) {

				String filename = new String(oid);

				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
						"Deactivating object " + filename + " in POA " + poa.the_name());

				RepositoryFactory.getRepository().persistExperimentResult(objdataser, filename);
				objdataser.setAlreadySavedOnRepository();
				// SerializationHelper.writeObject(filename, dir, objdataser);
				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER)
						.log(Level.FINE, "Serializing for the first time...!");
				Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
							"Deactivated object " + filename + " in POA " + poa.the_name());
			} else {
				Logger.getLogger(DataProducerActivator.RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
						"Object already saved on repository no needed to serialize again!");
			}

		} catch (RepositoryException e) {
			LoggerUtil.logThrowable("Error deactivating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e,
					Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		}

	}

	@Override
	public org.omg.PortableServer.Servant incarnate(final byte[] oid, final POA poa) throws ForwardRequest {

		try {
			String filename = new String(oid);

			Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
					"Activating object " + filename + " in POA " + poa.the_name());

			// Object readed = SerializationHelper.readObject(filename, dir);
			// System.out.println("is readed an instance of ReCMultiCastDataProducer?? "
			// + (readed instanceof ReCMultiCastDataProducer));

			final ReCMultiCastDataProducer dataProducer = (ReCMultiCastDataProducer) RepositoryFactory.getRepository()
					.getExperimentResult(filename);
			dataProducer.setAlreadySavedOnRepository();

			new Deactivator(dataProducer, Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));

			dataProducer.setOID(filename);

			// org.omg.PortableServer.Servant s=null;
			// s=poa.reference_to_servant(dataProducerImpl.getDataProducer());

			final DataProducerPOATie s = new DataProducerPOATie(dataProducer, poa);

			Logger.getLogger(DataProducerActivator.RECMULTICAST_DATAPRODUCER_LOGGER).log(Level.FINE,
					"Activated object " + filename + " in POA " + poa.the_name());
			return s;
		} catch (RepositoryException e) {
			LoggerUtil.logThrowable("Error activating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e,
					Logger.getLogger(RECMULTICAST_DATAPRODUCER_LOGGER));
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		}
	}

}
