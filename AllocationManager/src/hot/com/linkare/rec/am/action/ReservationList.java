package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("reservationList")
public class ReservationList extends EntityQuery<Reservation> {

	private static final String EJBQL = "select reservation from Reservation reservation";

	private static final String[] RESTRICTIONS = {};

	private Reservation reservation = new Reservation();

	public ReservationList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public Reservation getReservation() {
		return reservation;
	}
}
