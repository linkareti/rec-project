package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("experimentHome")
public class ExperimentHome extends EntityHome<Experiment> {

	@In(create = true)
	LaboratoryHome laboratoryHome;

	public void setExperimentId(Long id) {
		setId(id);
	}

	public Long getExperimentId() {
		return (Long) getId();
	}

	@Override
	protected Experiment createInstance() {
		Experiment experiment = new Experiment();
		return experiment;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Laboratory laboratory = laboratoryHome.getDefinedInstance();
		if (laboratory != null) {
			getInstance().setLaboratory(laboratory);
		}
	}

	public boolean isWired() {
		return true;
	}

	public Experiment getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Reservation> getReservations() {
		return getInstance() == null ? null : new ArrayList<Reservation>(
				getInstance().getReservations());
	}

}
