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
import java.util.logging.Logger;

import org.omg.CORBA.LocalObject;
import org.omg.PortableServer.ForwardRequest;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantActivator;

import com.linkare.rec.acquisition.DataProducerPOATie;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import com.linkare.rec.impl.multicast.repository.RepositoryException;
import com.linkare.rec.impl.multicast.repository.RepositoryFactory;

public class DataProducerActivator extends LocalObject implements ServantActivator {

	/**
	 * 
	 */
	private static final String IN_POA_STR_LITERAL = " in POA ";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3719852533240590451L;
	private static final Logger LOGGER = Logger.getLogger(DataProducerActivator.class.getName());

	/** Creates a new instance of HardwareActivator */
	public DataProducerActivator() {
		DataProducerActivator.baseDir = ReCSystemProperty.USER_DIR.getValue()
				+ ReCSystemProperty.FILE_SEPARATOR.getValue() + "DataProducers";
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
			LOGGER.log(Level.FINE, "Etherealizing object " + (new String(oid)) + IN_POA_STR_LITERAL + poa.the_name());
			final Deactivatable objdataser = (Deactivatable) (((DataProducerPOATie) servant)._delegate());
			if (!objdataser.alreadySavedOnRepository()) {

				String filename = new String(oid);

				LOGGER.log(Level.FINE, "Deactivating object " + filename + IN_POA_STR_LITERAL + poa.the_name());

				RepositoryFactory.getRepository().persistExperimentResult(objdataser, filename);
				objdataser.setAlreadySavedOnRepository();
				// SerializationHelper.writeObject(filename, dir, objdataser);
				LOGGER.log(Level.FINE, "Serializing for the first time...!");
				LOGGER.log(Level.FINE, "Deactivated object " + filename + IN_POA_STR_LITERAL + poa.the_name());
			} else {
				LOGGER.log(Level.FINE, "Object already saved on repository no needed to serialize again!");
			}

		} catch (RepositoryException e) {
			LOGGER.log(Level.SEVERE, "Error deactivating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e);
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		}

	}

	@Override
	public org.omg.PortableServer.Servant incarnate(final byte[] oid, final POA poa) throws ForwardRequest {

		try {
			String filename = new String(oid);

			LOGGER.log(Level.FINE, "Activating object " + filename + IN_POA_STR_LITERAL + poa.the_name());

			// Object readed = SerializationHelper.readObject(filename, dir);
			// System.out.println("is readed an instance of ReCMultiCastDataProducer?? "
			// + (readed instanceof ReCMultiCastDataProducer));

			final ReCMultiCastDataProducer dataProducer = (ReCMultiCastDataProducer) RepositoryFactory.getRepository()
					.getExperimentResult(filename);
			dataProducer.setAlreadySavedOnRepository();

			new Deactivator(dataProducer);

			dataProducer.setOID(filename);

			// org.omg.PortableServer.Servant s=null;
			// s=poa.reference_to_servant(dataProducerImpl.getDataProducer());

			final DataProducerPOATie s = new DataProducerPOATie(dataProducer, poa);

			LOGGER.log(Level.FINE, "Activated object " + filename + IN_POA_STR_LITERAL + poa.the_name());
			return s;
		} catch (RepositoryException e) {
			LOGGER.log(Level.SEVERE, "Error activating dataproducer... - Throwing CORBA OBJ_ADAPTER Error", e);
			throw new org.omg.CORBA.OBJ_ADAPTER(e.getMessage());
		}
	}

}
