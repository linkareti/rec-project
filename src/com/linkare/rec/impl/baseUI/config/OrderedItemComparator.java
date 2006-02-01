/*
 * OrderedItemComparator.java
 *
 * Created on 27 de Janeiro de 2004, 2:07
 */

package com.linkare.rec.impl.baseUI.config;

/**
 *
 * @author  jp
 */
public class OrderedItemComparator implements java.util.Comparator
{
    
    public int compare(Object o1, Object o2)
    {
	if(o1==o2) return 0;
	
	if(!(o1 instanceof OrderedItem) || !(o2 instanceof OrderedItem) || o1==null || o2==null) return 0;
	
	return ((OrderedItem)o1).getOrder()-((OrderedItem)o2).getOrder();
    }
    
    
}
