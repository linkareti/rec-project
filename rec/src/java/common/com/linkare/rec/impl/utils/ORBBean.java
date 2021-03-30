/*
 * ORBRegisterBean.java
 *
 * Created on 20 de Maio de 2002, 12:53
 */

package com.linkare.rec.impl.utils;

import java.applet.Applet;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.TimeBase;

import org.omg.BiDirPolicy.BIDIRECTIONAL_POLICY_TYPE;
import org.omg.BiDirPolicy.BOTH;
import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Policy;
import org.omg.CORBA.PolicyManager;
import org.omg.CORBA.SetOverrideType;
import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.Messaging.RELATIVE_REQ_TIMEOUT_POLICY_TYPE;
import org.omg.Messaging.RELATIVE_RT_TIMEOUT_POLICY_TYPE;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.IdUniquenessPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;
import org.omg.PortableServer.RequestProcessingPolicyValue;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.ServantManager;
import org.omg.PortableServer.ServantRetentionPolicyValue;
import org.omg.PortableServer.ThreadPolicyValue;
import org.omg.TimeBase.TimeTHelper;

import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerHelper;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.wrappers.MultiCastControllerWrapper;
import com.linkare.rec.utils.ClassUtils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */
@SuppressWarnings("unchecked")
public class ORBBean {
	/**
	 * 
	 */
	private static final Logger LOGGER = Logger.getLogger(ORBBean.class.getName());

	public static ORBBean getORBBean() {
		if (ORBBean.this_object == null) {
			ORBBean.this_object = new ORBBean();
		}

		return ORBBean.this_object;
	}

	public static ORBBean getORBBean(final Applet applet) {
		if (ORBBean.this_object == null) {
			ORBBean.this_object = new ORBBean(applet);
		}

		return ORBBean.this_object;
	}

	/* Singleton Pattern */
	private static ORBBean this_object = null;

	/** Holds value of property applet. */
	private Applet applet;

	private org.omg.CORBA.ORB the_orb = null;

	private POA dataProducerPOA = null;

	private ORBBean() {
		initORB();
	}

	private ORBBean(final Applet applet) {
		this.applet = applet;
		initORB();
	}

	private final java.lang.Object orb_synch = new java.lang.Object();

	private synchronized void initORB() {
		synchronized (orb_synch) {
			if (the_orb != null) {
				return;
			}

			if (applet != null) {
				the_orb = ORB.init(applet, null);
			} else {
				final Properties props = System.getProperties();
				the_orb = ORB.init(new String[] {}, props);
			}

			final ORBRunner runner = new ORBRunner(the_orb);
			try {
				synchronized (runner) {
					runner.start();
					runner.wait(1000);
				}
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Couldn't start the ORB!", e);
			}
		}

	}

	private class ORBRunner extends Thread {
		private org.omg.CORBA.ORB the_orb = null;

		public ORBRunner(final org.omg.CORBA.ORB the_orb) {
			this.the_orb = the_orb;
			setDaemon(true);
			setPriority(Thread.MAX_PRIORITY);
		}

		@Override
		public void run() {
			synchronized (this) {
				notifyAll();
			}
			try {
				Runtime.getRuntime().addShutdownHook(new Thread() {
					/**
					 * {@inheritDoc}
					 */
					@Override
					public void run() {
						the_orb.shutdown(false);
					}
				});
				the_orb.run();
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Error running the ORB: " + e.getMessage(), e);
			}
		}
	}

	private org.omg.CORBA.ORB getORB() {
		synchronized (orb_synch) {
			return the_orb;
		}
	}

	private Policy bidirpol = null;

	private Policy getBidirPolicy() {
		synchronized (orb_synch) {

			if (bidirpol == null) {
				try {

					final Any any = getORB().create_any();

					any.insert_ushort(BOTH.value);

					bidirpol = getORB().create_policy(BIDIRECTIONAL_POLICY_TYPE.value, any);

					final PolicyManager opm = (PolicyManager) getORB().resolve_initial_references("ORBPolicyManager");
					opm.set_policy_overrides(new Policy[] { bidirpol }, SetOverrideType.ADD_OVERRIDE);

				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
				}
			}

			return bidirpol;
		}
	}

	private Policy rttpol = null;
	
	private Policy getRoundTripTimeOutPolicy() {
		if (rttpol == null) {
			try {
				final Any any = getORB().create_any();
				//TimeTHelper.insert(any, Integer.valueOf(System.getProperty("corba.rt.timeout", "30")) * 1000 * 10000);// 30 seconds*ms*(10.000 units of 100 ns)
				any.insert_longlong(Integer.valueOf(System.getProperty("corba.rt.timeout", "30")) * 1000 * 10000);
				rttpol = getORB().create_policy(RELATIVE_RT_TIMEOUT_POLICY_TYPE.value, any); //RoundTrip TimeOut
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		return rttpol;
	}

	public synchronized POA getDataProducerPOA(final ServantManager deactivator) throws Exception {
		synchronized (orb_synch) {
			try {
				if (dataProducerPOA == null) {

					final POA rootPOA = POAHelper.narrow(the_orb.resolve_initial_references("RootPOA"));

					rootPOA.the_POAManager().activate();

					Policy[] poa_policies = null;

					poa_policies = new Policy[] {
							getBidirPolicy(),
							getRoundTripTimeOutPolicy(),
							rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
							rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
							rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
							rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
							rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_SERVANT_MANAGER),
							rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
							rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) };

					dataProducerPOA = rootPOA.create_POA("DataProducerPOA", null, poa_policies);

					dataProducerPOA.set_servant_manager(deactivator);

					dataProducerPOA.the_POAManager().activate();
				}

				return dataProducerPOA;
			} catch (final Exception e) {
				throw e;
			}
		}
	}

	private POA myRootPOA = null;

	public POA getRootPOA() throws Exception {
		synchronized (orb_synch) {
			try {
				if (myRootPOA == null) {

					final POA rootPOA = POAHelper.narrow(the_orb.resolve_initial_references("RootPOA"));
					rootPOA.the_POAManager().activate();

					Policy[] poa_policies = null;

					poa_policies = new Policy[] {
							getBidirPolicy(),
							getRoundTripTimeOutPolicy(),
							rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
							rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
							rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
							rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
							rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
							rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
							rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL), };

					myRootPOA = rootPOA.create_POA("MyRootPOA", null, poa_policies);

					myRootPOA.the_POAManager().activate();

				}
				return myRootPOA;
			} catch (final Exception e) {
				throw e;
			}
		}
	}

	private POA myAutoIdRootPOA = null;

	public POA getAutoIdRootPOA() throws Exception {
		synchronized (orb_synch) {

			try {
				if (myAutoIdRootPOA == null) {

					final POA rootPOA = POAHelper.narrow(the_orb.resolve_initial_references("RootPOA"));
					rootPOA.the_POAManager().activate();

					Policy[] poa_policies = null;

					poa_policies = new Policy[] {
							getBidirPolicy(),
							getRoundTripTimeOutPolicy(),
							rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID),
							rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
							rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION),
							rootPOA.create_lifespan_policy(LifespanPolicyValue.TRANSIENT),
							rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
							rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
							rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL) };

					myAutoIdRootPOA = rootPOA.create_POA("MyAutoIdRootPOA", null, poa_policies);

					myAutoIdRootPOA.the_POAManager().activate();

				}
				return myAutoIdRootPOA;
			} catch (final Exception e) {
				throw e;
			}
		}
	}

	// public void deactivateServant(final Servant servant) {
	// synchronized (orb_synch) {
	// try {
	// getAutoIdRootPOA().deactivate_object(getAutoIdRootPOA().servant_to_id(servant));
	// } catch (final Exception e) {
	// LOGGER.log(Level.SEVERE, e.getMessage(), e);
	// }
	// }
	// }

	public void deactivateServant(final byte[] oid) {
		synchronized (orb_synch) {
			try {
				getAutoIdRootPOA().deactivate_object(oid);
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public Servant registerDataProducerPOAServant(final Class<?> remoteInterface,
			final java.lang.Object servantDelegate, final byte[] oid, final ServantManager deactivator)
			throws Exception {
		synchronized (orb_synch) {
			final String delegateInterfaceName = remoteInterface.getName() + "Operations";
			final Class<?> delegateInterface = ClassUtils.findClass(delegateInterfaceName, getClass()
					.getClassLoader());

			final String poaTieServantClassName = remoteInterface.getName() + "POATie";
			final Class<?> poaTieServantClass = ClassUtils.findClass(poaTieServantClassName, getClass()
					.getClassLoader());

			final Constructor<?> servantCtr = poaTieServantClass.getConstructor(new Class[] { delegateInterface,
					POA.class });

			final Servant servant = (Servant) servantCtr.newInstance(new java.lang.Object[] { servantDelegate,
					getDataProducerPOA(deactivator) });

			getDataProducerPOA(deactivator).activate_object_with_id(oid, servant);

			return servant;
		}
	}

	public Servant registerRootPOAServant(final Class<?> remoteInterface, final java.lang.Object servantDelegate,
			final byte[] oid) throws Exception {
		synchronized (orb_synch) {
			final String delegateInterfaceName = remoteInterface.getName() + "Operations";
			final Class<?> delegateInterface = ClassUtils.findClass(delegateInterfaceName, getClass()
					.getClassLoader());

			final String poaTieServantClassName = remoteInterface.getName() + "POATie";
			final Class<?> poaTieServantClass = ClassUtils.findClass(poaTieServantClassName, getClass()
					.getClassLoader());

			final Constructor<?> servantCtr = poaTieServantClass.getConstructor(new Class[] { delegateInterface,
					POA.class });

			final Servant servant = (Servant) servantCtr.newInstance(new java.lang.Object[] { servantDelegate,
					getRootPOA() });

			getRootPOA().activate_object_with_id(oid, servant);

			return servant;
		}
	}

	public Servant registerAutoIdRootPOAServant(final Class<?> remoteInterface, final java.lang.Object servantDelegate,
			final ObjectID oidOut) throws Exception {
		synchronized (orb_synch) {
			final String delegateInterfaceName = remoteInterface.getName() + "Operations";
			final Class<?> delegateInterface = ClassUtils.findClass(delegateInterfaceName, getClass()
					.getClassLoader());

			final String poaTieServantClassName = remoteInterface.getName() + "POATie";
			final Class<?> poaTieServantClass = ClassUtils.findClass(poaTieServantClassName, getClass()
					.getClassLoader());

			final Constructor<?> servantCtr = poaTieServantClass.getConstructor(new Class[] { delegateInterface,
					POA.class });

			final Servant servant = (Servant) servantCtr.newInstance(new java.lang.Object[] { servantDelegate,
					getAutoIdRootPOA() });

			final byte[] oid = getAutoIdRootPOA().activate_object(servant);
			if (oidOut != null) {
				oidOut.setOid(oid);
			}

			return servant;
		}
	}

	public NamingContextExt createDirectory(final java.lang.String FullName) throws Exception {
		synchronized (orb_synch) {
			try {

				NamingContextExt nc = getNameService();
				final NameComponent[] fullname = nc.to_name(FullName);
				NamingContextExt nc1 = null;

				for (final NameComponent element : fullname) {
					try {
						nc1 = NamingContextExtHelper.narrow(nc.bind_new_context(new NameComponent[] { element }));
						nc = nc1;
					} catch (final Exception e) {
						nc1 = NamingContextExtHelper.narrow(nc.resolve(new NameComponent[] { element }));
						if (nc1 == null) {
							throw new Exception("The name " + element.id + " is not a directory...");
						}

						nc = nc1;
					}
				}

				return nc;
			} catch (final Exception e) {
				throw e;
			}
		}
	}

	public void bindObject(final java.lang.String FullName, final org.omg.CORBA.Object obj) throws Exception {
		synchronized (orb_synch) {
			try {
				final NamingContextExt nc = getNameService();
				final NameComponent[] fullPath = nc.to_name(FullName);
				nc.rebind(fullPath, obj);
			} catch (final Exception e) {
				throw e;
			}
		}
	}

	private NamingContextExt ns = null;

	public NamingContextExt getNameService() throws Exception {
		synchronized (orb_synch) {
			if (ns != null) {
				try {
					if (ns._non_existent()) {
						ns = null;
					} else {
						return ns;
					}
				} catch (final Exception e) {
					ns = null;
				}
			}

			try {
				try {
					// in OpenORB I think I know how to reset NameService
					// initial references
					// ((ORB)getORB()).addInitialReference("NameService",null);
					// org.openorb.CORBA.ORB has been deprecated... in favour of
					// org.openorb.core.ORB

					((org.openorb.orb.core.ORB) getORB()).addInitialReference("NameService", null);

				} catch (final Exception ignored) {
				}
				ns = NamingContextExtHelper.narrow(getORB().resolve_initial_references("NameService"));
				return ns;
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				throw e;
			}
		}
	}

	public org.omg.CORBA.Object getRemoteObject(final String FullName) throws Exception {
		org.omg.CORBA.Object out = null;
		// org.omg.CORBA.Object out=getNameService().resolve_str(FullName);
		// org.omg.CORBA.Object out =
		// getRemoteObject(getNameService().to_name(FullName));
		try {
			out = getNameService().resolve_str(FullName);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}

		return out;
	}

	public org.omg.CORBA.Object getRemoteObject(final NameComponent[] fullname) throws Exception {
		synchronized (the_orb) {
			final org.omg.CORBA.Object out = getNameService().resolve(fullname);
			return out;
		}
	}

	public void removeNSRegistration(final NameComponent[] fullname) {
		synchronized (the_orb) {
			try {
				getNameService().unbind(fullname);
			} catch (final Exception ignored) {
			}
		}
	}

	public org.omg.CORBA.Object getRemoteCORBALocObject(final String corbalocURL) throws Exception {
		if (!corbalocURL.startsWith("corbaloc")) {
			return getRemoteObject(corbalocURL);
		}

		org.omg.CORBA.Object obj = null;
		try {
			obj = getORB().string_to_object(corbalocURL);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			throw e;
		}

		return obj;
	}

	public String bindObjectToCorbalocService(final String name, final org.omg.CORBA.Object obj) {
		final org.omg.CORBA.ORB orb = getORB();
		String result = null;
		try {
			final org.omg.CORBA.Object clsobj = orb.resolve_initial_references("CorbalocService");

			/*
			 * Thia was for openorb 1.3.1
			 * 
			 * 
			 * Class cls_clz =
			 * Thread.currentThread().getContextClassLoader().loadClass(
			 * "org.openorb.corbaloc.CorbalocService" ); Class clsstub_clz =
			 * Thread.currentThread().getContextClassLoader().loadClass(
			 * "org.openorb.corbaloc._CorbalocServiceStub" );
			 */

			// And now for Openorb 1.4.0
			final Class<?> cls_clz = Thread.currentThread().getContextClassLoader()
					.loadClass("org.openorb.orb.corbaloc.CorbalocService");
			final Class<?> clsstub_clz = Thread.currentThread().getContextClassLoader()
					.loadClass("org.openorb.orb.corbaloc._CorbalocServiceStub");

			/*
			 * Thia was for openorb 1.3.1
			 * 
			 * if ( clsobj != null && ( clsobj.getClass().isAssignableFrom(
			 * cls_clz ) || clsobj._is_a(
			 * "IDL:openorb.org/corbaloc/CorbalocService:1.1" ) ) )
			 */

			// And now for Openorb 1.4.0
			if (clsobj != null
					&& (clsobj.getClass().isAssignableFrom(cls_clz) || clsobj
							._is_a("IDL:orb.openorb.org/corbaloc/CorbalocService:1.1"))) {
				// create an instance of _CorbalocServiceStub (default
				// constructor)
				final java.lang.Object clsstub_obj = getStubInstance(clsstub_clz, clsobj);

				// get the put method
				final Method put = clsstub_obj.getClass().getMethod("put",
						new Class[] { String.class, org.omg.CORBA.Object.class });

				// call the _get_delegate() method on the stub
				final java.lang.Object deleg = ((ObjectImpl) clsobj)._get_delegate();

				try {
					// call the put operation on the CorbalocService
					put.invoke(clsstub_obj, new java.lang.Object[] { name, obj });

					// invocation was successful, get several methods and
					// classes

					/*
					 * This was for openorb 1.3.1
					 * 
					 * 
					 * Class orgOpenorbOrbNetAddressClz =
					 * Thread.currentThread().getContextClassLoader().loadClass(
					 * "org.openorb.net.Address" );
					 */

					final Class<?> orgOpenorbOrbNetAddressClz = Thread.currentThread().getContextClassLoader()
							.loadClass("org.openorb.orb.net.Address");

					/*
					 * This was for openorb 1.3.1
					 * 
					 * 
					 * Class orgOpenorbOrbCoreDelegateClz =
					 * Thread.currentThread().getContextClassLoader().loadClass(
					 * "org.openorb.CORBA.Delegate" );
					 */

					// And now for Openorb 1.4.0
					final Class<?> orgOpenorbOrbCoreDelegateClz = Thread.currentThread().getContextClassLoader()
							.loadClass("org.openorb.orb.core.Delegate");

					final Method getAddresses = orgOpenorbOrbCoreDelegateClz.getMethod("getAddresses",
							new java.lang.Class[] { org.omg.CORBA.Object.class });

					final Method getProtocol = orgOpenorbOrbNetAddressClz.getMethod("getProtocol",
							new java.lang.Class[] {});
					final Method getEndpointString = orgOpenorbOrbNetAddressClz.getMethod("getEndpointString",
							new java.lang.Class[] {});

					final java.lang.Object addrs = getAddresses.invoke(deleg, new java.lang.Object[] { obj });

					String endpoint = null;
					if (addrs != null) {
						final int len = Array.getLength(addrs);
						for (int i = 0; i < len; i++) {
							final java.lang.Object addr = Array.get(addrs, i);
							final String prot = (String) getProtocol.invoke(addr, new java.lang.Object[] {});
							if (prot.equals("iiop")) {
								endpoint = (String) getEndpointString.invoke(addr, new java.lang.Object[] {});
							}
						}
						if (endpoint == null) {
							endpoint = (String) getEndpointString
									.invoke(Array.get(addrs, 0), new java.lang.Object[] {});
						}
						result = "corbaloc:" + endpoint + "/" + name;
					}
				} catch (final Exception ex) {
					// do nothing, return false
				}
			}
		} catch (final Exception ex) {
			// do nothing, return false
		}
		return result;
	}

	private java.lang.Object getStubInstance(final Class<?> clz, final org.omg.CORBA.Object obj) {
		java.lang.Object result = null;
		try {
			try {
				// JDK 1.4
				// create an instance of _NamingContextStub (default
				// constructor)
				result = clz.newInstance();
				final Method setDelegate = clz.getMethod("_set_delegate", new java.lang.Class[] { Delegate.class });
				setDelegate.invoke(result, new java.lang.Object[] { ((ObjectImpl) obj)._get_delegate() });
			} catch (final InstantiationException ex) {
				// JDK 1.3
				// create an instance of _NamingContextStub (constructor takes
				// Delegate)
				final Constructor<?> ctor = clz.getConstructor(new Class[] { Delegate.class });
				result = ctor.newInstance(new java.lang.Object[] { ((ObjectImpl) obj)._get_delegate() });
			}
		} catch (final Exception ex) {
			LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return result;
	}

	public static byte[] StrToOid(final String strOid) {
		return strOid.getBytes();
	}

	public static String OidtoStr(final byte[] Oid) {
		return new String(Oid);
	}

	public void killORB() {
		synchronized (orb_synch) {
			try {
				the_orb.shutdown(true);
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			try {
				the_orb.destroy();
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
			the_orb = null;
		}
	}

	/**
	 * Resolves the multicast initial reference
	 * 
	 * @return A multicastcontroller wrapper
	 * @throws InvalidName if the multicast controller system property does not
	 *             refer to the correct object
	 */
	public MultiCastControllerWrapper resolveMultiCast() throws InvalidName {
		MultiCastController delegate;
		try {
			delegate = MultiCastControllerHelper.narrow(this.getORB().resolve_initial_references(
					ReCSystemProperty.MULTICAST_INITREF.getValue()));
			MultiCastControllerWrapper wrapper = new MultiCastControllerWrapper(delegate);
			return wrapper;

		} catch (InvalidName e) {
			LOGGER.log(Level.SEVERE, "Unable to resolve multicastcontroller " + e.getMessage(), e);
			throw e;
		}

	}

	/**
	 * @return
	 */
	public Any createAny() {
		return getORB().create_any();
	}

}
