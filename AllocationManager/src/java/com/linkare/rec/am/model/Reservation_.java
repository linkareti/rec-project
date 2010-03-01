package com.linkare.rec.am.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Wed Feb 17 16:38:22 GMT 2010")
@StaticMetamodel(Reservation.class)
public class Reservation_ { 

	public static volatile SingularAttribute<Reservation, String> id;
	public static volatile SingularAttribute<Reservation, Date> startDate;
	public static volatile SingularAttribute<Reservation, String> title;
	public static volatile SingularAttribute<Reservation, Boolean> allDay;
	public static volatile SingularAttribute<Reservation, String> endTimeSlot;
	public static volatile SingularAttribute<Reservation, Experiment> experiment;
	public static volatile SingularAttribute<Reservation, String> styleClass;
	public static volatile SingularAttribute<Reservation, Date> endDate;
	public static volatile SingularAttribute<Reservation, UserGroup> group;
	public static volatile SingularAttribute<Reservation, UserPrincipal> user;
	public static volatile SingularAttribute<Reservation, String> startTimeSlot;

}