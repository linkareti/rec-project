/*
 * DefaulExptUsersListTableModel.java
 *
 * Created on 08 May 2003, 21:54
 */

package com.linkare.rec.impl.client.experiment;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class DefaulExpUsersListTableModel extends javax.swing.table.DefaultTableModel implements ExpUsersListChangeListener
{
    private static String UI_CLIENT_LOGGER="ReC.baseUI";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
        }
    }
    public static long CONTROL_NOW=0;
    
    public static long CONTROL_AVAILABLE=-1;
    
    public static long CONTROL_UNKNOWN=-2;
    
    /** Holds value of property expUsersListSource. */
    private ExpUsersListSource expUsersListSource;
    
    private String noUsersList;
    private String lblUserName;
    private String lbltime_to_control_min;
    private String lbltime_to_control_max;
    private String lblInControl;
    private String lblControlNow;
    
    /** Creates a new instance of DefaulExptUsersListTableModel */
    public DefaulExpUsersListTableModel()
    {
        super();
        noUsersList = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.noUsersList", "No Users List Available");
        lblUserName = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.username", "Username:");
        lblInControl = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.inControl", "In control since");
        lblControlNow = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.controlNow", "now!");
        lbltime_to_control_min = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.timeToControlMin", "Control at (min)");
        lbltime_to_control_max = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.timeToControlMax", "Control at (max)");
        setDataVector(new Object[0][1], new Object[]
        {noUsersList});
    }
    
    public void usersListChanged(ExpUsersListEvent evt)
    {
        UserInfo[] expUsers = evt.getUserInfo();
        if(expUsers==null)
        {
            setDataVector(new Object[0][1], new Object[]
            {noUsersList});
            return;
        }
        
        
        Arrays.sort(expUsers,new Comparator<UserInfo>()
        {
            public int compare(UserInfo u1,UserInfo u2)
            {
                if(u1==null && u2==null) return 0;
                
                if(u1==null) return -1;
                if(u2==null) return +1;
                
                
                if(u1.getNextLockTime() == null && u2.getNextLockTime() == null)
                    return 0;
                if(u1.getNextLockTime() == null)
                    return -1;
                if(u2.getNextLockTime() == null)
                    return +1;
                if(u1.getNextLockTime()[0] == null)
                    return -1;
                if(u2.getNextLockTime()[0] == null)
                    return +1;
                
                if(u1.getUserName().equals(IChatServer.EVERYONE_USER_ALIAS) || u1.getUserName().equals(IChatServer.EVERYONE_USER_ALIAS))
                    return 0;
                
                if(u1.getNextLockTime()[0].getMilliSeconds()-u2.getNextLockTime()[0].getMilliSeconds()==0)
                    return 0;
                
                return (u1.getNextLockTime()[0].getMilliSeconds()-u2.getNextLockTime()[0].getMilliSeconds())>0?+1:-1;
            }
            
            public boolean equals(Object other)
            {
                if(other==null || !(other.getClass()==this.getClass()))
                    return false;
                
                return true;
            }
        });
        
        
        Vector<String[]> expUsersList=new Vector<String[]>(expUsers.length);
        
        for(int i=0;i<expUsers.length;i++)
        {
            if(expUsers[i].getUserName() != null && !expUsers[i].getUserName().equals(IChatServer.EVERYONE_USER_ALIAS))
            {
                String userName = expUsers[i].getUserName();
                String controlInMin = "";
                String controlInMax = "";
                if(expUsers[i].getLockedTime() != null && expUsers[i].getLockedTime().getMilliSeconds()!=0)
                {
                    controlInMin = lblInControl;
                    controlInMax = "" + expUsers[i].getLockedTime().toSimpleString();
                }
                else
                {
                    if(expUsers[i].getNextLockTime() != null && expUsers[i].getNextLockTime()[UserInfo.MIN_TIME_LOCK] != null)
                        controlInMin = expUsers[i].getNextLockTime()[UserInfo.MIN_TIME_LOCK].toSimpleStringTimeFirst();
                    if(expUsers[i].getNextLockTime() != null && expUsers[i].getNextLockTime()[UserInfo.MAX_TIME_LOCK] != null)
                        controlInMax = expUsers[i].getNextLockTime()[UserInfo.MAX_TIME_LOCK].toSimpleStringTimeFirst();
                    
                }
                
                if(i==1 && controlInMin == controlInMax)
                {
                    controlInMin = lblControlNow;
                    controlInMax = controlInMin;
                }
                
                /*System.out.println("Username = " + userName);
                System.out.println("controlInMin = " + controlInMin);
                System.out.println("controlInMax = " + controlInMax);*/
                
                expUsersList.add(new String[]
                {userName, controlInMin,controlInMax});
            }
        }
        
        expUsersList.trimToSize();
        Object[] values=expUsersList.toArray();
        
        Object[][] valuesMatrix=new Object[values.length][];
        System.arraycopy(values,0,valuesMatrix,0,values.length);
        
        setDataVector(valuesMatrix,new Object[]
        {lblUserName, lbltime_to_control_min,lbltime_to_control_max});
        fireTableDataChanged();
        
    }
    
    /** Returns true if the cell at <code>rowIndex</code> and
     * <code>columnIndex</code>
     * is editable.  Otherwise, <code>setValueAt</code> on the cell will not
     * change the value of that cell.
     *
     * @param	rowIndex	the row whose value to be queried
     * @param	columnIndex	the column whose value to be queried
     * @return	true if the cell is editable
     * @see #setValueAt
     */
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {return false;}
    
    /** Getter for property expUsersListSource.
     * @return Value of property expUsersListSource.
     */
    public ExpUsersListSource getExpUsersListSource()
    {
        return this.expUsersListSource;
    }
    
    /** Setter for property expUsersListSource.
     * @param expUsersListSource New value of property expUsersListSource.
     */
    public void setExpUsersListSource(ExpUsersListSource expUsersListSource)
    {
        this.expUsersListSource = expUsersListSource;
        if(expUsersListSource!=null)
            expUsersListSource.addExpUsersListChangeListener(this);
        
        chechRefresh();
    }
    
    private void startAutoRefresh()
    {
        if(expUsersListSource!=null)
            expUsersListSource.startAutoRefresh(delay_refresh);
    }
    
    private void stopAutoRefresh()
    {
        if(expUsersListSource!=null)
            expUsersListSource.stopAutoRefresh();
    }
    
    private long delay_refresh=-1;
    public void setAutoRefresh(long delay_refresh)
    {
        this.delay_refresh=delay_refresh;
        chechRefresh();
    }
    
    private void chechRefresh()
    {
        if(delay_refresh>0)
            startAutoRefresh();
        if(delay_refresh<=0)
            stopAutoRefresh();
    }
    
}
