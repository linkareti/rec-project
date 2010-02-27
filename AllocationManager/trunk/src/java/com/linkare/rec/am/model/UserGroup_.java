package com.linkare.rec.am.model;

import java.util.List;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated("EclipseLink - Thu Feb 25 12:36:07 GMT 2010")
@StaticMetamodel(UserGroup.class)
public class UserGroup_ {

	public static volatile SingularAttribute<UserGroup, String> name;
	public static volatile SetAttribute<UserGroup, UserPrincipal> members;
	public static volatile SingularAttribute<UserGroup, List> reservations;

}