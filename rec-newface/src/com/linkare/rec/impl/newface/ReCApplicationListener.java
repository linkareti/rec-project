/*
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface;

import com.linkare.rec.impl.client.lab.LabConnectorEvent;

/**
 *
 * @author Henrique Fernandes
 */
public interface ReCApplicationListener {

    public void labStatusChanged(LabConnectorEvent evt);

}
