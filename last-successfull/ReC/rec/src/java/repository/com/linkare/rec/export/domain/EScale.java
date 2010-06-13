package com.linkare.rec.export.domain;

import com.linkare.rec.data.metadata.Scale;
import com.linkare.rec.export.domain.enums.EnumMultiplier;
import com.linkare.rec.export.exceptions.InvalidMultiplierException;
import com.linkare.rec.export.exceptions.InvalidTypeValException;
import com.linkare.rec.utils.Utils;
import java.io.Serializable;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "SCALE")
public class EScale implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private EnumMultiplier appliedMultiplier;
    private EPhysicsVal minValue;
    private EPhysicsVal maxValue;
    private EPhysicsVal defaultError;
    private EPhysicsVal step;
    private String physicsUnitName;
    private String physicsUnitSymbol;
    private String scaleLabel;

    public EScale() {
    }

    public EScale(Scale scale) throws InvalidTypeValException, InvalidMultiplierException {
        if (scale != null) {
            try {
                if (scale.getMultiplier() != null) {
                    this.setAppliedMultiplier(EnumMultiplier.values()[scale.getMultiplier().getValue()]);
                }
            } catch (IndexOutOfBoundsException e) {
                throw new InvalidMultiplierException();
            }

            this.setMinValue(Utils.physicsValToEntity(scale.getMinimumValue()));
            this.setMaxValue(Utils.physicsValToEntity(scale.getMaximumValue()));
            this.setDefaultError(Utils.physicsValToEntity(scale.getDefaultErrorValue()));
            this.setStep(Utils.physicsValToEntity(scale.getStepValue()));
            this.setPhysicsUnitName(scale.getPhysicsUnitName());
            this.setPhysicsUnitSymbol(scale.getPhysicsUnitSymbol());
            this.setScaleLabel(scale.getScaleLabel());
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Enumerated(EnumType.STRING)
    public EnumMultiplier getAppliedMultiplier() {
        return appliedMultiplier;
    }

    public void setAppliedMultiplier(EnumMultiplier appliedMultiplier) {
        this.appliedMultiplier = appliedMultiplier;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    public EPhysicsVal getMinValue() {
        return minValue;
    }

    public void setMinValue(EPhysicsVal minValue) {
        this.minValue = minValue;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    public EPhysicsVal getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(EPhysicsVal maxValue) {
        this.maxValue = maxValue;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    public EPhysicsVal getDefaultError() {
        return defaultError;
    }

    public void setDefaultError(EPhysicsVal defaultError) {
        this.defaultError = defaultError;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    public EPhysicsVal getStep() {
        return step;
    }

    public void setStep(EPhysicsVal step) {
        this.step = step;
    }

    public String getPhysicsUnitName() {
        return physicsUnitName;
    }

    public void setPhysicsUnitName(String physicsUnitName) {
        this.physicsUnitName = physicsUnitName;
    }

    public String getPhysicsUnitSymbol() {
        return physicsUnitSymbol;
    }

    public void setPhysicsUnitSymbol(String physicsUnitSymbol) {
        this.physicsUnitSymbol = physicsUnitSymbol;
    }

    public String getScaleLabel() {
        return scaleLabel;
    }

    public void setScaleLabel(String scaleLabel) {
        this.scaleLabel = scaleLabel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EScale)) {
            return false;
        }
        EScale other = (EScale) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    }
}
