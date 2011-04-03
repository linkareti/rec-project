/*
 * DefaulExptUsersListTableModel.java
 *
 * Created on 08 May 2003, 21:54
 */

package com.linkare.rec.impl.newface.component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener;
import com.linkare.rec.impl.client.experiment.ExpUsersListEvent;
import com.linkare.rec.impl.client.experiment.ExpUsersListSource;
import com.linkare.rec.impl.events.ChatMessageEvent;

/**
 * 
 * @author José Pedro Pereira
 */
public class DefaulExpUsersListTableModel extends javax.swing.table.DefaultTableModel implements ExpUsersListChangeListener {
	//    private static String UI_CLIENT_LOGGER="ReC.baseUI";
	//    
	//    static
	//    {
	//        Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
	//        if(l==null)
	//        {
	//            LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
	//        }
	//    }
	private static final Logger log = Logger.getLogger(DefaulExpUsersListTableModel.class.getName());

	public static long CONTROL_NOW = 0;

	public static long CONTROL_AVAILABLE = -1;

	public static long CONTROL_UNKNOWN = -2;

	/** Holds value of property expUsersListSource. */
	private ExpUsersListSource expUsersListSource;

	private String noUsersList;
	private String lblUserName;
	private String lbltime_to_control_min;
	private String lbltime_to_control_max;
	private String lblInControl;
	private String lblControlNow;

	/** Creates a new instance of DefaulExptUsersListTableModel */
	public DefaulExpUsersListTableModel() {
		super();
		init();

		setDataVector(new Object[0][1], new Object[] { noUsersList });
	}

	public void init() {
		org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(
				com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(DefaulExpUsersListTableModel.class);

		noUsersList = resourceMap.getString("noUsersList.text");
		lblUserName = resourceMap.getString("lblUserName.text");
		//        lblInControl = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.inControl", "In control since");
		//        lblControlNow = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.controlNow", "now!");
		lbltime_to_control_min = resourceMap.getString("lbltime_to_control_min.text");
		lbltime_to_control_max = resourceMap.getString("lbltime_to_control_max.text");

	}
	
	private static String calcPeriod(long startDateMillis, long endDateMillis) {
		org.joda.time.DateTime start = new org.joda.time.DateTime(startDateMillis);
		org.joda.time.DateTime end = new org.joda.time.DateTime(endDateMillis);
		Period period = new Period(start, end, PeriodType.yearMonthDayTime());

		if (period.getYears() > 0) {
			return period.getYears() + "Y:" + period.getMonths() + "M";
		} else if (period.getMonths() > 0) {
			return period.getMonths() + "M:" + period.getDays() + "d";
		} else if (period.getDays() > 0) {
			return period.getDays() + "d:" + period.getHours() + "h";
		} else if (period.getHours() > 0) {
			return period.getHours() + "h:" + period.getMinutes() + "m";
		} else if (period.getMinutes() > 0) {
			return period.getMinutes() + "m:" + period.getSeconds() + "s";
		} else {
			return period.getSeconds() + "s";
		}
	}

	public void usersListChanged(ExpUsersListEvent evt) {
		final UserInfo[] expUsers = evt.getUserInfo();
		
		SwingUtilities.invokeLater(new Runnable() {
			/**
			 * {@inheritDoc}
			 */
			@Override
			public void run() {
				
				if (expUsers == null) {
					setDataVector(new Object[0][1], new Object[] { noUsersList });
					return;
				}

				Arrays.sort(expUsers, new Comparator<UserInfo>() {
					public int compare(UserInfo u1, UserInfo u2) {
						if (u1 == null && u2 == null)
							return 0;

						if (u1 == null)
							return -1;
						if (u2 == null)
							return +1;

						if (u1.getNextLockTime() == null && u2.getNextLockTime() == null)
							return 0;
						if (u1.getNextLockTime() == null)
							return -1;
						if (u2.getNextLockTime() == null)
							return +1;
						if (u1.getNextLockTime()[0] == null)
							return -1;
						if (u2.getNextLockTime()[0] == null)
							return +1;

						if (u1.getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS)
								|| u2.getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS))
							return 0;

						if (u1.getNextLockTime()[0].getMilliSeconds() - u2.getNextLockTime()[0].getMilliSeconds() == 0)
							return 0;

						return (u1.getNextLockTime()[0].getMilliSeconds() - u2.getNextLockTime()[0].getMilliSeconds()) > 0 ? +1 : -1;
					}

					@Override
					public boolean equals(Object other) {
						if (other == null || !(other.getClass() == this.getClass()))
							return false;

						return true;
					}
				});

				Vector<String[]> expUsersList = new Vector<String[]>(expUsers.length);
				DateTime initTime = null;
				for (int i = 0; i < expUsers.length; i++) {
					if (expUsers[i].getUserName() != null && !expUsers[i].getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS)) {
						if (initTime == null) {
							initTime = expUsers[i].getNextLockTime()[UserInfo.MIN_TIME_LOCK];
						}
						
						DateTime expMin = expUsers[i].getNextLockTime()[UserInfo.MIN_TIME_LOCK];
						DateTime expMax = expUsers[i].getNextLockTime()[UserInfo.MAX_TIME_LOCK];
						
						String controlInMin = calcPeriod(initTime.getMilliSeconds(), expMin.getMilliSeconds());
						String controlInMax = calcPeriod(initTime.getMilliSeconds(), expMax.getMilliSeconds());
						String userName = expUsers[i].getUserName();
						
						expUsersList.add(new String[] { userName, controlInMin, controlInMax });
					}
				}

				expUsersList.trimToSize();
				Object[] values = expUsersList.toArray();

				Object[][] valuesMatrix = new Object[values.length][];
				System.arraycopy(values, 0, valuesMatrix, 0, values.length);

				setDataVector(valuesMatrix, new Object[] { lblUserName, lbltime_to_control_min, lbltime_to_control_max });
				fireTableDataChanged();
				
			}
		});
	}

	/**
	 * Returns true if the cell at <code>rowIndex</code> and <code>columnIndex</code> is editable. Otherwise,
	 * <code>setValueAt</code> on the cell will not change the value of that cell.
	 * 
	 * @param rowIndex
	 *            the row whose value to be queried
	 * @param columnIndex
	 *            the column whose value to be queried
	 * @return true if the cell is editable
	 * @see #setValueAt
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	/**
	 * Getter for property expUsersListSource.
	 * 
	 * @return Value of property expUsersListSource.
	 */
	public ExpUsersListSource getExpUsersListSource() {
		return this.expUsersListSource;
	}

	/**
	 * Setter for property expUsersListSource.
	 * 
	 * @param expUsersListSource
	 *            New value of property expUsersListSource.
	 */
	public void setExpUsersListSource(ExpUsersListSource expUsersListSource) {
		this.expUsersListSource = expUsersListSource;
		if (expUsersListSource != null)
			expUsersListSource.addExpUsersListChangeListener(this);

		chechRefresh();
	}

	private void startAutoRefresh() {
		if (expUsersListSource != null)
			expUsersListSource.startAutoRefresh(delay_refresh);
	}

	private void stopAutoRefresh() {
		if (expUsersListSource != null)
			expUsersListSource.stopAutoRefresh();
	}

	private long delay_refresh = -1;

	public void setAutoRefresh(long delay_refresh) {
		this.delay_refresh = delay_refresh;
		chechRefresh();
	}

	private void chechRefresh() {
		if (delay_refresh > 0)
			startAutoRefresh();
		if (delay_refresh <= 0)
			stopAutoRefresh();
	}

}
