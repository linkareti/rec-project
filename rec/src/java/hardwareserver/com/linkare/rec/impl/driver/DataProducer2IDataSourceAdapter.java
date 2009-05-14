/*
 * DataProducer2IDataSourceAdapter.java
 *
 * Created on 12 de Maio de 2003, 15:49
 */

package com.linkare.rec.impl.driver;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class DataProducer2IDataSourceAdapter extends BaseDataSource
{
	private DataProducerWrapper delegateDataProducer=null;
	/** Creates a new instance of DataProducer2IDataSourceAdapter */
	public DataProducer2IDataSourceAdapter(DataProducerWrapper delegateDataProducer)
	{
		this.delegateDataProducer=delegateDataProducer;
	}
	
	public String getName()
	{
		return delegateDataProducer.getDataProducerName();
	}
	
	int packetSize=1;
	public int getPacketSize()
	{
		return packetSize;
	}
	
	public PhysicsValue[][] getSamples(int sampleIndexStart,int sampleIndexEnd)
	{
		return null;
	}
	
	public void stopNow()
	{
	}
	
}
