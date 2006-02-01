/*
 * IDataSourceListener.java
 *
 * Created on 12 de Maio de 2003, 11:54
 */

package com.linkare.rec.impl.driver;

import com.linkare.rec.impl.data.SamplesSourceEventListener;
/**
 *
 * @author  jp
 */
public interface IDataSourceListener extends SamplesSourceEventListener
{
	public void dataSourceWaiting();
	public void dataSourceStarted();
	public void dataSourceEnded();
	public void dataSourceStoped();
	public void dataSourceError();
}
