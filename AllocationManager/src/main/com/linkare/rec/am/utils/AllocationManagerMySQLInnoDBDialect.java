package com.linkare.rec.am.utils;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * @author jpereira
 * 
 */
public class AllocationManagerMySQLInnoDBDialect extends MySQL5InnoDBDialect {
	
   @Override
    public String getTableTypeString() {
	   return " ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";
    }
}
