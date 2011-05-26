/*
 * ApparatusConnectorAdaptor.java
 *
 * Created on July 30, 2004, 10:40 AM
 */

package com.linkare.rec.impl.client.apparatus;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public abstract class ApparatusConnectorAdaptor implements
		com.linkare.rec.impl.client.apparatus.ApparatusConnectorListener {

	@Override
	public void apparatusConnected(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusConnecting(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusDisconnected(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusDisconnecting(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusIncorrectState(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusLockable(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusLocked(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusMaxUsers(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusNotAuthorized(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusNotOwner(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusNotRegistered(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateConfigError(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateConfigured(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateConfiguring(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateReseted(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateReseting(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateStarted(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateStarting(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateStoped(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateStoping(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusStateUnknow(final ApparatusConnectorEvent evt) {
	}

	@Override
	public void apparatusUnreachable(final ApparatusConnectorEvent evt) {
	}

}
