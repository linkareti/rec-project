/*
 * DefaulExptUsersListTableModel.java
 *
 * Created on 08 May 2003, 21:54
 */

package com.linkare.rec.impl.client.experiment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira
 */
public class DefaulExpUsersListTableModel extends javax.swing.table.DefaultTableModel implements
		ExpUsersListChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1242039289732037050L;

	private static final String UI_CLIENT_LOGGER = "ReC.baseUI";

	static {
		final Logger l = LogManager.getLogManager().getLogger(DefaulExpUsersListTableModel.UI_CLIENT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DefaulExpUsersListTableModel.UI_CLIENT_LOGGER));
		}
	}
	/** Holds value of property expUsersListSource. */
	private ExpUsersListSource expUsersListSource;

	private final String noUsersList;
	private final String lblUserName;
	private final String lblTimeToControlMin;
	private final String lblTimeToControlMax;
	private final String lblInControl;
	private final String lblControlNow;

	/** Creates a new instance of DefaulExptUsersListTableModel */
	public DefaulExpUsersListTableModel() {
		super();
		noUsersList = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.noUsersList",
				"No Users List Available");
		lblUserName = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.username", "Username:");
		lblInControl = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.inControl", "In control since");
		lblControlNow = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.controlNow", "now!");
		lblTimeToControlMin = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.timeToControlMin",
				"Control at (min)");
		lblTimeToControlMax = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.timeToControlMax",
				"Control at (max)");
		setDataVector(new Object[0][1], new Object[] { noUsersList });
	}

	@Override
	public void usersListChanged(final ExpUsersListEvent evt) {
		final UserInfo[] expUsers = evt.getUserInfo();
		if (expUsers == null) {
			setDataVector(new Object[0][1], new Object[] { noUsersList });
			return;
		}

		Arrays.sort(expUsers, new Comparator<UserInfo>() {
			@Override
			public int compare(final UserInfo u1, final UserInfo u2) {
				if (u1 == null && u2 == null) {
					return 0;
				}

				if (u1 == null) {
					return -1;
				}
				if (u2 == null) {
					return +1;
				}

				if (u1.getNextLockTime() == null && u2.getNextLockTime() == null) {
					return 0;
				}
				if (u1.getNextLockTime() == null) {
					return -1;
				}
				if (u2.getNextLockTime() == null) {
					return +1;
				}
				if (u1.getNextLockTime()[0] == null) {
					return -1;
				}
				if (u2.getNextLockTime()[0] == null) {
					return +1;
				}

				if (u1.getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS)
						|| u1.getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS)) {
					return 0;
				}

				if (u1.getNextLockTime()[0].getMilliSeconds() - u2.getNextLockTime()[0].getMilliSeconds() == 0) {
					return 0;
				}

				return (u1.getNextLockTime()[0].getMilliSeconds() - u2.getNextLockTime()[0].getMilliSeconds()) > 0 ? +1
						: -1;
			}

		});

		final List<String[]> expUsersList = new ArrayList<String[]>(expUsers.length);

		for (int i = 0; i < expUsers.length; i++) {
			if (expUsers[i].getUserName() != null
					&& !expUsers[i].getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS)) {
				final String userName = expUsers[i].getUserName();
				String controlInMin = "";
				String controlInMax = "";
				if (expUsers[i].getLockedTime() != null && expUsers[i].getLockedTime().getMilliSeconds() != 0) {
					controlInMin = lblInControl;
					controlInMax = "" + expUsers[i].getLockedTime().toSimpleString();
				} else {
					if (expUsers[i].getNextLockTime() != null
							&& expUsers[i].getNextLockTime()[UserInfo.MIN_TIME_LOCK] != null) {
						controlInMin = expUsers[i].getNextLockTime()[UserInfo.MIN_TIME_LOCK].toSimpleStringTimeFirst();
					}
					if (expUsers[i].getNextLockTime() != null
							&& expUsers[i].getNextLockTime()[UserInfo.MAX_TIME_LOCK] != null) {
						controlInMax = expUsers[i].getNextLockTime()[UserInfo.MAX_TIME_LOCK].toSimpleStringTimeFirst();
					}

				}

				if (i == 1 && controlInMin!=null && controlInMin.equals(controlInMax)) {
					controlInMin = lblControlNow;
					controlInMax = controlInMin;
				}

				/*
				 * System.out.println("Username = " + userName);
				 * System.out.println("controlInMin = " + controlInMin);
				 * System.out.println("controlInMax = " + controlInMax);
				 */

				expUsersList.add(new String[] { userName, controlInMin, controlInMax });
			}
		}

		final Object[] values = expUsersList.toArray();

		final Object[][] valuesMatrix = new Object[values.length][];
		System.arraycopy(values, 0, valuesMatrix, 0, values.length);

		setDataVector(valuesMatrix, new Object[] { lblUserName, lblTimeToControlMin, lblTimeToControlMax });
		fireTableDataChanged();

	}

	/**
	 * Returns true if the cell at <code>rowIndex</code> and
	 * <code>columnIndex</code> is editable. Otherwise, <code>setValueAt</code>
	 * on the cell will not change the value of that cell.
	 * 
	 * @param rowIndex the row whose value to be queried
	 * @param columnIndex the column whose value to be queried
	 * @return true if the cell is editable
	 * @see #setValueAt
	 */
	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return false;
	}

	/**
	 * Getter for property expUsersListSource.
	 * 
	 * @return Value of property expUsersListSource.
	 */
	public ExpUsersListSource getExpUsersListSource() {
		return expUsersListSource;
	}

	/**
	 * Setter for property expUsersListSource.
	 * 
	 * @param expUsersListSource New value of property expUsersListSource.
	 */
	public void setExpUsersListSource(final ExpUsersListSource expUsersListSource) {
		this.expUsersListSource = expUsersListSource;
		if (expUsersListSource != null) {
			expUsersListSource.addExpUsersListChangeListener(this);
		}

		chechRefresh();
	}

	private void startAutoRefresh() {
		if (expUsersListSource != null) {
			expUsersListSource.startAutoRefresh(delayRefresh);
		}
	}

	private void stopAutoRefresh() {
		if (expUsersListSource != null) {
			expUsersListSource.stopAutoRefresh();
		}
	}

	private long delayRefresh = -1;

	public void setAutoRefresh(final long delayRefresh) {
		this.delayRefresh = delayRefresh;
		chechRefresh();
	}

	private void chechRefresh() {
		if (delayRefresh > 0) {
			startAutoRefresh();
		}
		if (delayRefresh <= 0) {
			stopAutoRefresh();
		}
	}

}
