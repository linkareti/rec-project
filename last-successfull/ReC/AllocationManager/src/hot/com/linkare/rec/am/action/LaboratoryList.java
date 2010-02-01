package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("laboratoryList")
public class LaboratoryList extends EntityQuery<Laboratory> {

	private static final String EJBQL = "select laboratory from Laboratory laboratory";

	private static final String[] RESTRICTIONS = {
			"lower(laboratory.description) like lower(concat(#{laboratoryList.laboratory.description},'%'))",
			"lower(laboratory.name) like lower(concat(#{laboratoryList.laboratory.name},'%'))",
			"lower(laboratory.state.helpMessage) like lower(concat(#{laboratoryList.laboratory.state.helpMessage},'%'))",
			"lower(laboratory.state.label) like lower(concat(#{laboratoryList.laboratory.state.label},'%'))",
			"lower(laboratory.state.url) like lower(concat(#{laboratoryList.laboratory.state.url},'%'))",};

	private Laboratory laboratory = new Laboratory();

	public LaboratoryList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Laboratory getLaboratory() {
		return laboratory;
	}
}
