package com.linkare.rec.am.action;

import com.linkare.rec.am.model.*;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.framework.EntityQuery;
import java.util.Arrays;

@Name("userList")
public class UserList extends EntityQuery<User> {

	private static final String EJBQL = "select user from User user";

	private static final String[] RESTRICTIONS = {
			"lower(user.password) like lower(concat(#{userList.user.password},'%'))",
			"lower(user.username) like lower(concat(#{userList.user.username},'%'))",};

	private User user = new User();

	public UserList() {
		setEjbql(EJBQL);
		setRestrictionExpressionStrings(Arrays.asList(RESTRICTIONS));
		setMaxResults(25);
	}

	public User getUser() {
		return user;
	}
}
