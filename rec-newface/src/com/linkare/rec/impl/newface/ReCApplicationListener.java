/*
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface;

import com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.newface.ReCApplication.ApparatusEvent;

/**
 *
 * @author Henrique Fernandes
 */
public interface ReCApplicationListener {

    void labStateChanged(LabConnectorEvent evt);

	void apparatusListChanged(ApparatusListChangeEvent evt);

	void apparatusStateChanged(ApparatusEvent evtSelector, ApparatusConnectorEvent evt);

}
