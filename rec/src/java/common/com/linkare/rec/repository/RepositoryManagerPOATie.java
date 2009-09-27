package com.linkare.rec.repository;

import org.omg.PortableServer.POA;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.UserInfo;

public class RepositoryManagerPOATie extends RepositoryManagerPOA {

	// Constructors

	public RepositoryManagerPOATie(RepositoryManagerOperations delegate) {
		this._impl = delegate;
	}

	public RepositoryManagerPOATie(RepositoryManagerOperations delegate, POA poa) {
		this._impl = delegate;
		this._poa = poa;
	}

	public RepositoryManagerOperations _delegate() {
		return this._impl;
	}

	public void _delegate(RepositoryManagerOperations delegate) {
		this._impl = delegate;
	}

	public POA _default_POA() {
		if (_poa != null) {
			return _poa;
		} else {
			return super._default_POA();
		}
	}

	public DataProducer getDataProducer(UserInfo user, String id) {
		return _impl.getDataProducer(user, id);
	} // getDataProducer

	public DataProducerConfig[] listDataProducers(UserInfo user, HardwareAcquisitionConfigSearch[] search_params) {
		return _impl.listDataProducers(user, search_params);
	} // listDataProducers

	private RepositoryManagerOperations _impl;
	private POA _poa;

} // class RepositoryManagerPOATie
