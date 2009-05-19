package com.linkare.rec.impl.newface;

import com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent;

public class ReCApplicationAdapter implements ReCApplicationListener {
	
	public void applicationEvent(ReCAppEvent evt) {};

	public void labStateChanged(LabConnectorEvent evt) {};

	public void apparatusListChanged(ApparatusListChangeEvent evt) {};

	public void apparatusStateChanged(ApparatusEvent evtSelector, ApparatusConnectorEvent evt) {};

}

