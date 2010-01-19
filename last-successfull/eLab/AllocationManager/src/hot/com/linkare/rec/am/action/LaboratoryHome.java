package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import java.util.ArrayList;
import java.util.List;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityHome;

@Name("laboratoryHome")
public class LaboratoryHome extends EntityHome<Laboratory> {

	public void setLaboratoryId(Long id) {
		setId(id);
	}

	public Long getLaboratoryId() {
		return (Long) getId();
	}

	@Override
	protected Laboratory createInstance() {
		Laboratory laboratory = new Laboratory();
		return laboratory;
	}

	public void load() {
		if (isIdDefined()) {
			wire();
		}
	}

	public void wire() {
		getInstance();
	}

	public boolean isWired() {
		return true;
	}

	public Laboratory getDefinedInstance() {
		return isIdDefined() ? getInstance() : null;
	}

	public List<Experiment> getExperiments() {
		return getInstance() == null ? null : new ArrayList<Experiment>(
				getInstance().getExperiments());
	}

}
