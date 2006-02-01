/*
 * IDriverStateListener.java
 *
 * Created on 9 de Maio de 2003, 18:12
 */

package com.linkare.rec.impl.driver;

/**
 *
 * @author  jp
 */
public interface IDriverStateListener extends java.util.EventListener
{
	public void driverInited();
	public void driverConfiguring();
	public void driverConfigured();
	public void driverStarting();
	public void driverStarted();
	public void driverStoping();
	public void driverStoped();
	public void driverReseting();
	public void driverReseted();
	public void driverShutdown();
}
