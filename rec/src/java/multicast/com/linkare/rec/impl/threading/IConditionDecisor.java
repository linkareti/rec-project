/*
 * IConditionDecisor.java
 *
 * Created on 11 May 2003, 12:17
 */

package com.linkare.rec.impl.threading;

/**
 *
 * @author  Jose Pedro Pereira
 */
public interface IConditionDecisor
{
    public static int CONDITION_NOT_MET=0;
    public static int CONDITION_MET_TRUE=1;
    public static int CONDITION_MET_FALSE=2;
    
    public int getConditionResult();
    public void onConditionMetTrue();
    public void onConditionMetFalse();
    public void onConditionTimeOut();
}
