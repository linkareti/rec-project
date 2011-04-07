package com.linkare.rec.am.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 
 * @author artur
 */
@Entity
@DiscriminatorValue(value = "HARDWARE")
public class HardwareParameterConfig extends ParameterConfig {

    private static final long serialVersionUID = 1L;

}
