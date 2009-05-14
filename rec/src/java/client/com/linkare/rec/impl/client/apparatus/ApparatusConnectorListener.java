/*
 * LabConnectorListener.java
 *
 * Created on 13 de Maio de 2003, 18:08
 */

package com.linkare.rec.impl.client.apparatus;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface ApparatusConnectorListener extends java.util.EventListener
{
	public void apparatusConnecting(ApparatusConnectorEvent evt);
	public void apparatusConnected(ApparatusConnectorEvent evt);
	public void apparatusDisconnecting(ApparatusConnectorEvent evt);
	public void apparatusDisconnected(ApparatusConnectorEvent evt);
	public void apparatusUnreachable(ApparatusConnectorEvent evt);
	public void apparatusNotAuthorized(ApparatusConnectorEvent evt);
	public void apparatusMaxUsers(ApparatusConnectorEvent evt);
	public void apparatusNotRegistered(ApparatusConnectorEvent evt);
	public void apparatusLockable(ApparatusConnectorEvent evt);
	public void apparatusLocked(ApparatusConnectorEvent evt);
	public void apparatusNotOwner(ApparatusConnectorEvent evt);
	public void apparatusStateUnknow(ApparatusConnectorEvent evt);
	public void apparatusStateConfiguring(ApparatusConnectorEvent evt);
	public void apparatusStateConfigured(ApparatusConnectorEvent evt);
	public void apparatusStateConfigError(ApparatusConnectorEvent evt);
	public void apparatusStateStarting(ApparatusConnectorEvent evt);
	public void apparatusStateStarted(ApparatusConnectorEvent evt);
	public void apparatusStateStoping(ApparatusConnectorEvent evt);
	public void apparatusStateStoped(ApparatusConnectorEvent evt);
	public void apparatusStateReseting(ApparatusConnectorEvent evt);
	public void apparatusStateReseted(ApparatusConnectorEvent evt);
	public void apparatusIncorrectState(ApparatusConnectorEvent evt);
}
