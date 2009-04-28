/*
 * OrderedItemComparator.java
 *
 * Created on 27 de Janeiro de 2004, 2:07
 */

package com.linkare.rec.impl.newface.config;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class OrderedItemComparator implements java.util.Comparator<OrderedItem>
{
    
    public int compare(OrderedItem o1, OrderedItem o2)
    {
	if(o1==o2) return 0;
	
	if(o1==null || o2==null) return 0;
	
	return (o1.getOrder()-o2.getOrder());
    }
    
    
}
