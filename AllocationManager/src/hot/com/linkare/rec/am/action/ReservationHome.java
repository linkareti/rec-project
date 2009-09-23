package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("reservationHome")
public class ReservationHome extends EntityHome<Reservation> {

	@In(create = true)
	ExperimentHome experimentHome;

	public void setReservationId(Long id) {
		setId(id);
	}

	public Long getReservationId() {
		return (Long) getId();
	}

	@Override
	protected Reservation createInstance() {
		Reservation reservation = new Reservation();
		return reservation;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
		Experiment experiment = experimentHome.getDefinedInstance();
		if (experiment != null) {
			getInstance().setExperiment(experiment);
		}
	}

	public boolean isWired() {
		if (getInstance().getExperiment() == null)
			return false;
		return true;
	}

	public Reservation getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

}
