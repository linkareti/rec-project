/*
 * testMathUtil.java
 *
 * Created on 5 de Dezembro de 2003, 19:17
 */

package test;

import com.linkare.rec.impl.utils.MathUtil;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class testMathUtil
{
    
    /** Creates a new instance of testMathUtil */
    public testMathUtil()
    {
    }
    
    
    public static void main(String[] args)
    {
	MathUtil.isValueInScale(0.001, 0.250, 0.001, 0.011);
    }
}
