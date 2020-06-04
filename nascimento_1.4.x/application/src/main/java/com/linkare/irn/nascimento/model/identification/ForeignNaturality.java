package com.linkare.irn.nascimento.model.identification;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@DiscriminatorValue("ForeignNaturality")
public class ForeignNaturality extends Naturality {

    private static final long serialVersionUID = 1L;

    @Column(name = "location", length = 150)
    @Size(max = 150)
    @NotNull
    private String location;

    /**
     * @return the location
     */
    public String getLocation() {
	return location;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    @Override
    public NaturalityType getNaturalityType() {
	return NaturalityType.FOREIGN;
    }
}