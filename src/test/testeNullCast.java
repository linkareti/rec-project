/*
 * testeIORW.java
 *
 * Created on 5 de Janeiro de 2004, 19:44
 */

package test;

import java.io.*;
/**
 *
 * @author  Administrator
 */
public class testeNullCast
{
    
    /** Creates a new instance of testeIORW */
    public testeNullCast()
    {
    }
    
    
    public static void main(String[] args)
    {
	Object o=null;
	long[] val=(long[])o;
    }
}
