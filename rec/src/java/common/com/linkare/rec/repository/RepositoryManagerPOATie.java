package com.linkare.rec.repository;

import org.omg.PortableServer.POA;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.UserInfo;

public class RepositoryManagerPOATie extends RepositoryManagerPOA {

	// Constructors

	public RepositoryManagerPOATie(final RepositoryManagerOperations delegate) {
		_impl = delegate;
	}

	public RepositoryManagerPOATie(final RepositoryManagerOperations delegate, final POA poa) {
		_impl = delegate;
		_poa = poa;
	}

	public RepositoryManagerOperations _delegate() {
		return _impl;
	}

	public void _delegate(final RepositoryManagerOperations delegate) {
		_impl = delegate;
	}

	@Override
	public POA _default_POA() {
		if (_poa != null) {
			return _poa;
		} else {
			return super._default_POA();
		}
	}

	@Override
	public DataProducer getDataProducer(final UserInfo user, final String id) {
		return _impl.getDataProducer(user, id);
	} // getDataProducer

	@Override
	public DataProducerConfig[] listDataProducers(final UserInfo user,
			final HardwareAcquisitionConfigSearch[] search_params) {
		return _impl.listDataProducers(user, search_params);
	} // listDataProducers

	private RepositoryManagerOperations _impl;
	private POA _poa;

} // class RepositoryManagerPOATie
