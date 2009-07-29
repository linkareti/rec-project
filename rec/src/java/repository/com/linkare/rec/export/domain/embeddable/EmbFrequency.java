/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.export.domain.embeddable;

import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.export.domain.enums.EnumFrequencyDefType;
import com.linkare.rec.export.domain.enums.EnumMultiplier;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author artur
 */
@Embeddable
public class EmbFrequency implements Serializable {

    private Double frequency = null;
    private EnumMultiplier appliedMultiplier = null;
    private EnumFrequencyDefType frequencyDefType = null;

    //default constructor to serializable purposes
    public EmbFrequency() {
    }

    public EmbFrequency(Frequency frequency) {
        if (frequency != null) {
            if (frequency.getMultiplier() != null) {
                this.setAppliedMultiplier(EnumMultiplier.values()[frequency.getMultiplier().getValue()]);
            }
            this.setFrequency(frequency.getFrequency());
            if (frequency.getFrequencyDefType() != null) {
                this.setFrequencyDefType(EnumFrequencyDefType.values()[frequency.getFrequencyDefType().getValue()]);
            }
        }

    }

    public Double getFrequency() {
        return frequency;
    }

    public void setFrequency(Double frequency) {
        this.frequency = frequency;
    }

    public EnumMultiplier getAppliedMultiplier() {
        return appliedMultiplier;
    }

    public void setAppliedMultiplier(EnumMultiplier appliedMultiplier) {
        this.appliedMultiplier = appliedMultiplier;
    }

    public EnumFrequencyDefType getFrequencyDefType() {
        return frequencyDefType;
    }

    public void setFrequencyDefType(EnumFrequencyDefType frequencyDefType) {
        this.frequencyDefType = frequencyDefType;
    }
}
