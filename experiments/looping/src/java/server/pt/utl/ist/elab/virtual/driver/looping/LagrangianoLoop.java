/*
 * LagrangianoLoop.java
 *
 * Created on 6 de Abril de 2005, 5:11
 */

package pt.utl.ist.elab.virtual.driver.looping;

import org.opensourcephysics.numerics.*;
/**
 *
 * @author  Emanuel Antunes
 */
public class LagrangianoLoop implements ODE{
    
    private double raio;
    private double g; // valor a tomar para a acceleracao gravitacional
    private double[] state;
    
    
    /**Contrutores da classe
     * Contrutor por omissao*/
    public LagrangianoLoop() {
        
        raio = 4.0;
        g = 9.8;
        state = new double[] {0, 0, 0};}
    
    /* Construtor com argumentos */
    public LagrangianoLoop(double anguloInic, double omegaInic, double raio, double g){
        this.raio=raio;
        this.g = g;
        state = new double[] {anguloInic, omegaInic, 0}; // respectivamente {theta, dtheta, tempo]
    }
    
    public void getRate(double[] state, double[] rate) {
        
        /* variacao do theta */
        rate[0] = state[1];
        
        /* variacao do dtheta*/
        rate[1] = -g*Math.sin(state[0])/raio;
        
        /* a derivada do tempo e' 1 */
        rate[2] = 1;
    }
    
    
    public double[] getState() {return state;}
    
}
