/*
 * Esfera.java
 *
 * Created on 8 de Abril de 2005, 2:56
 */

package pt.utl.ist.elab.virtual.driver.colisao;

/**
 *
 * @author  Ean
 */
public class Esfera {
    
    public double x,y;           // posicao (x,y)
    public double vX,vY;         // velocidade (vX,vY)
    public double raio;          // raio da esfera
    public double m;             // massa de esfera
    
    /** Creates a new instance of Esfera */
    
    public Esfera( double x_, double y_ , double vX_, double vY_, double raio_, double massa_ ) {
        x=x_;
        y=y_;
        vX=vX_;
        vY=vY_;
        m = massa_;
        raio=raio_;
    }
    
    public static double dot(double[] v1,double[] v2){
    return v1[0]*v2[0]+v1[1]*v2[1];  }
    
    public static double[] normalize(double[] vect){
        return new double[] {vect[0]/magnitude(vect), vect[1]/magnitude(vect)};}
    
    public static double magnitude(double[] vect){
        return Math.sqrt(vect[0]*vect[0]+vect[1]*vect[1]);}
    
    /*
     metodo central no programa - ele preve o tempo de colisao ( se esta for possivel ) entre 2 moleculas
     a formula utilizada e assumindo que as moleculas sao esferas de raios iguais que viajam em movimento rectilineo uniforme
     tempoDeColisao:  ( caso se real )   t=-(produtoEscalar+Math.sqrt(delta))/velocidadeQuadrado
    sendo
        dX=x-outraMolecula.x;
        dY=y-outraMolecula.y;
        dZ=z-outraMolecula.z;
        dVX=vX-outraMolecula.vX;
        dVY=vY-outraMolecula.vY;
        dVZ=vZ-outraMolecula.vZ;
        produtoEscalar=dVX*dX+dVY*dY+dVY*dY;
        distanciaQuadrado=dX*dX+dY*dY+dZ*dZ;
        velocidadeQuadrado=dVX*dVX+dVY*dVY+dVZ*dVZ;
        delta=produtoEscalar*produtoEscalar-velocidadeQuadrado*(distanciaQuadrado-4*raio*raio);
     *
     *
     */
/*    public final double tempoColisao(Molecula outraMolecula) {
        double dX,dY,dZ,dVX,dVY,dVZ,produtoEscalar,delta,velocidadeQuadrado, distanciaQuadrado;
        dX=x-outraMolecula.x;
        dY=y-outraMolecula.y;
        dZ=z-outraMolecula.z;
        dVX=vX-outraMolecula.vX;
        dVY=vY-outraMolecula.vY;
        dVZ=vZ-outraMolecula.vZ;
 
 
        produtoEscalar=dVX*dX+dVY*dY+dVZ*dZ;
        distanciaQuadrado=dX*dX+dY*dY+dZ*dZ;
        velocidadeQuadrado=dVX*dVX+dVY*dVY+dVZ*dVZ;
        delta=produtoEscalar*produtoEscalar-velocidadeQuadrado*(distanciaQuadrado-4*raio*raio);
        if ( delta>=0 && produtoEscalar<0) {
            return -(produtoEscalar+Math.sqrt(delta))/velocidadeQuadrado;
        }
        else return 1e301 ;
    }
 */
}



