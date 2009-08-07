package pt.utl.ist.elab.driver.virtual.utils;

/*
 * PowerFFT.java
 *
 * Created on 7 de Junho de 2004, 13:53
 */

/**
 *
 * Autores
 *      Antonio Jose Rodrigues Figueiredo
 *      Joao Nuno Barbosa Rodrigues
 */
public class FFT {
    
    /** Creates a new instance of PowerFFT */
    public FFT() {
    }
    

    /*
     *
     *  Algoritmo Cooley-Tukey Radix-2 DIF Complex FFT, Simples
     *
     *  Tamanho do array de dados: N = Math.pow(2, M)
     *
     *  func -> Dados
     *  M -> log[2](N), N -> numero de dados
     *  itype : 
     ~          -1 FFT Inversa
     *          != -1 FFT Directa
     */
    public static Complex[] calculateFFT(Complex[] func, int M, int itype){

        int N = (int) Math.pow(2, M);
        double xi[] = new double[N], xr[] = new double[N], wi[] = new double[N], wr[] = new double[N];

        int i, j, k, l, n1, n2, ie, ia;
        double c, s, xt, yt, a, p;
        
        for (i = 0; i < func.length; i++){
            xr[i] = func[i].getRe();
            xi[i] = func[i].getIm();
        }
        
        p = 2*Math.PI/N;
        
        for (k = 1; k <= N; k++){
            a = (k-1)*p;
            wr[k-1] = Math.cos(a);
            wi[k-1] = Math.sin(a);
        }
        
        
        if (N == 1)
            return null;
        
        if (itype == -1)
            for (i = 1; i <= N; i++)
                xi[i-1] = -xi[i-1];
        
        n2 = N;
        
        for (k = 1; k <= M; k++){
            n1 = n2;
            n2 = n2/2;
            ie = N/n1;
            ia = 1;
            
            for (j = 1; j <= n2; j++){
                c = wr[ia-1];
                s = wi[ia-1];
                ia = ia + ie;
                
                for (i = j; i <= N; i += n1){
                    l = i + n2;
                    xt = xr[i-1] - xr[l-1];
                    xr[i-1] = xr[i-1] + xr[l-1];
                    
                    yt = xi[i-1] - xi[l-1];
                    xi[i-1] = xi[i-1] + xi[l-1];
                    
                    xr[l-1] = c*xt+s*yt;
                    xi[l-1] = c*yt-s*xt;
                }
            }
        }
        
        j = 1;
        n1 = N-1;
        
        for (i = 1; i <= n1; i++){
            if (!(i >= j)){
                xt = xr[j-1];
                xr[j-1] = xr[i-1];
                xr[i-1] = xt;
                yt = xi[j-1];
                xi[j-1] = xi[i-1];
                xi[i-1] = yt;
            }
            k = N/2;
            
            while (!(k >= j)){
                j = j-k;
                k = k/2;
            }
            j = j+k;
        }
        
        if (itype == -1){
            for (i = 1; i <= N; i++){
                xi[i-1] = -xi[i-1]/N;
                xr[i-1] = xr[i-1]/N;
            }
        }
        
        
        Complex[] toReturn = new Complex[N];
        
        for (i = 0; i < func.length; i++)
            toReturn[i] = new Complex(xr[i], xi[i]);
        
        return toReturn;
    }
    
    
}