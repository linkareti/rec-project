package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("experimentList")
public class ExperimentList extends EntityQuery<Experiment> {

	private static final String EJBQL = "select experiment from Experiment experiment";

	private static final String[] RESTRICTIONS = {
			"lower(experiment.description) like lower(concat(#{experimentList.experiment.description},'%'))",
			"lower(experiment.name) like lower(concat(#{experimentList.experiment.name},'%'))",
			"lower(experiment.state.helpMessage) like lower(concat(#{experimentList.experiment.state.helpMessage},'%'))",
			"lower(experiment.state.label) like lower(concat(#{experimentList.experiment.state.label},'%'))",
			"lower(experiment.state.url) like lower(concat(#{experimentList.experiment.state.url},'%'))",};

	private Experiment experiment = new Experiment();

	public ExperimentList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Experiment getExperiment() {
		return experiment;
	}
}
