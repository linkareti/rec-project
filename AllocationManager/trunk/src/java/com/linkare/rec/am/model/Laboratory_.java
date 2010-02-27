package com.linkare.rec.am.model;

import com.linkare.rec.am.model.State;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Wed Feb 17 16:38:22 GMT 2010")
@StaticMetamodel(Laboratory.class)
public class Laboratory_ extends Resource_ {

	public static volatile SingularAttribute<Laboratory, List> experiments;
	public static volatile SingularAttribute<Laboratory, String> description;
	public static volatile SingularAttribute<Laboratory, String> name;
	public static volatile SingularAttribute<Laboratory, State> state;

}