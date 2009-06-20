/*
 * Disco.java
 *
 * Created on 24 de Março de 2005, 1:41
 */

/**
 *
 * @author  The Godfather
 */
 
package pt.utl.ist.elab.virtual.driver.di;

/** Imports
 */
import org.opensourcephysics.numerics.ODE;

public class Disco implements ODE {
    private double ri, re, inc, aT;
    static public final double g = 9.8;
    private double[] state = new double[7];
    
    /** Creates a new instance of Disco */
    public Disco(double raioInt, double raioExt, double angulo) {
        ri = raioInt;
        re = raioExt;
        inc = angulo;
        aT = (2*g*re*re*Math.sin(inc))/((3*re*re) + (ri*ri));
        
        double beta = (Math.PI/2.0) - angulo;
        
        state[0] = re*Math.cos(beta);
        state[1] = 0;
        state[2] = re*Math.sin(beta);
        state[3] = 0;
        state[4] = 0;
        state[5] = 0;
        state[6] = 0;
    }
    
    public void getRate(double[] state, double[] rate) {
        rate[0] = state[1];
        rate[1] = aT*Math.cos(inc);
        rate[2] = state[3];
        rate[3] = -aT*Math.sin(inc);
        rate[4] = state[5];
        rate[5] = aT/re;
        rate[6] = 1;
    }
    
    public double getModuloVelocidade() {
    	return Math.sqrt(state[1]*state[1] + state[3]*state[3]);
    }
    
    public double getEspacoPercorrido() {
    	return this.calcDist(re*Math.sin(inc), re*Math.cos(inc), state[0], state[2]);
    }
    
    private double calcDist(double _x1, double _y1, double _x2, double _y2) {
        return Math.sqrt(Math.pow((_x2-_x1),2)+Math.pow((_y2-_y1),2));
    }
    
    public double getInclinacao() { return inc; }
    
    public double getRaioInt() { return ri; }
    
    public double getRaioExt() { return re; }
    
    public double[] getState() { return state; }
    
    //public void setInclinacao(double angulo) { inc = angulo; }
    
    //public void setRaioInt(double raioInt) { ri = raioInt; }
    
    //public void setRaioExt(double raioExt) { ri = raioExt; }
}