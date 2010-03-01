package com.linkare.rec.am.model;

import com.linkare.rec.am.model.Laboratory;
import com.linkare.rec.am.model.State;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Wed Feb 17 16:38:22 GMT 2010")
@StaticMetamodel(Experiment.class)
public class Experiment_ extends Resource_ {

	public static volatile SingularAttribute<Experiment, Long> id;
	public static volatile SingularAttribute<Experiment, String> description;
	public static volatile SingularAttribute<Experiment, String> name;
	public static volatile SingularAttribute<Experiment, State> state;
	public static volatile SingularAttribute<Experiment, Laboratory> laboratory;
	public static volatile SingularAttribute<Experiment, List> reservations;

}