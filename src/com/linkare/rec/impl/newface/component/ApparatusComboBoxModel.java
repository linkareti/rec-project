/*
 * ApparatusComboBoxModel.java created on Apr 29, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.config.Apparatus;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Henrique Fernandes
 */
public class ApparatusComboBoxModel extends DefaultComboBoxModel {

    public ApparatusComboBoxModel(List<Apparatus> apparatus) {
        super((Apparatus[]) apparatus.toArray(new Apparatus[apparatus.size()]));
    }

}
