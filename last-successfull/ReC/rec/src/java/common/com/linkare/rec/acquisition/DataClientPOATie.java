package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/DataClientPOATie.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Terca-feira, 30 de Dezembro de 2003
 * 11H33m GMT
 */

public class DataClientPOATie extends DataClientPOA {

	// Constructors

	public DataClientPOATie(final com.linkare.rec.acquisition.DataClientOperations delegate) {
		_impl = delegate;
	}

	public DataClientPOATie(final com.linkare.rec.acquisition.DataClientOperations delegate,
			final org.omg.PortableServer.POA poa) {
		_impl = delegate;
		_poa = poa;
	}

	public com.linkare.rec.acquisition.DataClientOperations _delegate() {
		return _impl;
	}

	public void _delegate(final com.linkare.rec.acquisition.DataClientOperations delegate) {
		_impl = delegate;
	}

	@Override
	public org.omg.PortableServer.POA _default_POA() {
		if (_poa != null) {
			return _poa;
		} else {
			return super._default_POA();
		}
	}

	// Version 7.0 change: removed method getUser and getPassword and changed it
	// to getUserInfo... wich is much more generic
	@Override
	public com.linkare.rec.acquisition.UserInfo getUserInfo() {
		return _impl.getUserInfo();
	} // getUserInfo

	// wstring getPassword();
	@Override
	public void hardwareStateChange(final com.linkare.rec.acquisition.HardwareState newState) {
		_impl.hardwareStateChange(newState);
	} // hardwareStateChange

	@Override
	public void hardwareChange() {
		_impl.hardwareChange();
	} // hardwareChange

	@Override
	public void hardwareLockable(final long millisecs_to_lock_success) {
		_impl.hardwareLockable(millisecs_to_lock_success);
	} // hardwareLockable

	// version 5 added suport for messages
	@Override
	public void receiveMessage(final String clientFrom, final String clientTo, final String message) {
		_impl.receiveMessage(clientFrom, clientTo, message);
	} // receiveMessage

	private com.linkare.rec.acquisition.DataClientOperations _impl;
	private org.omg.PortableServer.POA _poa;

} // class DataClientPOATie
