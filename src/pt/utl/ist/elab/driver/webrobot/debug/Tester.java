/*
 * Tester.java
 *
 * Created on 10 de Junho de 2003, 12:31
 */

package pt.utl.ist.elab.driver.webrobot.debug;

import com.linkare.rec.acquisition.*;
import com.linkare.rec.impl.exceptions.*;

/**
 *
 * @author  Andre
 * Use this class to test doubts
 */
public class Tester {
    
    /** Creates a new instance of Tester */
    public Tester() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws WrongConfigurationException 
    {
       int[][] stupidQuestion=new int[3][4];
       System.out.println(stupidQuestion.length);
       System.out.println(stupidQuestion[2].length);
    }
    
}
