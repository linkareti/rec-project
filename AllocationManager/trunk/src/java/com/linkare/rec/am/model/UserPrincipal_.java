package com.linkare.rec.am.model;

import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Thu Feb 25 12:36:07 GMT 2010")
@StaticMetamodel(UserPrincipal.class)
public class UserPrincipal_ { 

	public static volatile SingularAttribute<UserPrincipal, String> name;
	public static volatile SetAttribute<UserPrincipal, UserGroup> groups;
	public static volatile SingularAttribute<UserPrincipal, List> reservations;

}