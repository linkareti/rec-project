package pt.utl.ist.elab.driver.Aleatorio.Utils;

/*
 * LMFit.java
 *
 * Created on 30 de Maio de 2003, 11:20
 */

/**
 *
 * @author Pedro Carvalho - LEFT - IST
 */


public class LMFit {
    
    
    public static double CHI_SQ = 0.;
    /** Creates a new instance of LMFit */
    public LMFit() {
    }
    
    
    /**
     *Returns the value of ChiSquared
     */
    public static double chiSq(double[] x, double[] y, double mu, double sigma, double y0, double Amp)
    {
        double sum = 0.;        
        for (int index = 0; index < y.length; index++)
        {
            double temp = y[index] - gaussFunc(x[index], mu, sigma, y0, Amp);
            temp = temp*temp;
            sum += temp;
        }//index
        CHI_SQ = sum;
        return sum;
    }//chiSq
    
    /**
     *Returns the value of the Gauss function
     */
    public static double gaussFunc(double x, double mu, double sigma, double y0, double Amp)
    {     
        return (y0 + Amp*Math.exp(-((x-mu)*(x-mu))/(sigma*sigma*2.)));
        //return (y0 + Amp/(Math.sqrt(Math.PI*2.)*sigma)*Math.exp(-2.*((x-mu)*(x-mu))/(sigma*sigma)));
        //return (1./(Math.sqrt(2.*Math.PI)*sigma))*Math.exp(-((x-mu)*(x-mu))/(2*sigma*sigma));
    }
    
    /**
     *Returns the value of the gradient of the Gauss function
     *'choice' decides which of the gradients is returned
     *choice = 0 => df/d'mu'
     *choice = 1 => df/d'sigma'
     *choice = 2 => df/d'y0'
     *choice = 3 => df/d'Amp'
     */
    private static double gaussGrad(double x, double mu, double sigma, double y0, double Amp, int choice)
    {
        if (choice == 0)
        {
            return (Amp*Math.exp(-((x-mu)*(x-mu))/(sigma*sigma*2.))*(x-mu)/(sigma*sigma));
            //return(1./(Math.sqrt(2.*Math.PI)*sigma*sigma*sigma)*Math.exp(-2.*((x-mu)*(x-mu))/(sigma*sigma))*(x-mu));
        }else if(choice == 1)
        {
            return (Amp*Math.exp(-((x-mu)*(x-mu))/(sigma*sigma*2.))*((x-mu)*(x-mu)/(sigma*sigma*sigma)));
            //return (Amp/(Math.sqrt(2.*Math.PI)*sigma*sigma)*Math.exp(-2.*((x-mu)*(x-mu))/(sigma*sigma))*(4.*(x-mu)*(x-mu)/sigma-1.));
            //return(1./(Math.sqrt(2.*Math.PI)*sigma*sigma)*Math.exp(-((x-mu)*(x-mu))/(2*sigma*sigma))*((x-mu)*(x-mu)/sigma-1.));
        }   
        else if(choice == 2)
        {
            return (1);
        }else if(choice == 3)
        {
            return (Math.exp(-((x-mu)*(x-mu))/(sigma*sigma*2.)));
            //return (1./(Math.sqrt(2.*Math.PI)*sigma)*Math.exp(-2.*((x-mu)*(x-mu))/(sigma*sigma)));
        }
        else
        {
            System.out.println("Wrong gradient parameter. Returning zero!");
            return 0;
        }
    }//gaussGrad
    
    /**
     *Core for solving the Levenberg-Marquardt algorithm for the gaussian function
     *returns two parameters:
     *params[0] => mean value for the gaussian
     *params[1] => standard deviation of the gaussian
     *params[2] => vertical offset
     *params[3] => Amlitude
     *
     *good(?) initial values:
     *mu = 1, but, in the case of 14 dice, it is 3*14=42
     *sigma = 1
     *y0 = 0
     *Amp = 1
     *lambda = 0.001 (from numerical recipies in C)
     *termEpsilon = 0.01 (from numerical recipies in C)
     *maxIter = 10 (from Origin5.0)
     */
    public static double[] solve(double[] x, double[] y, double mu, double sigma, double y0, double Amp,
                                    double lambda, double termEpsilon, int maxIter)
    {
        int numPts = y.length;
        int numParams = 4;
        int iter = 0;
        int term = 0;
        
        double erro0 = chiSq(x, y, mu, sigma,y0, Amp);
        
        
        //hess = Hessiana; grad = gradiante
        double[][] hess = new double[numParams][numParams];
        double[] grad = new double[numParams];
        
        boolean done = false;
        
        while (!done)       
        {
            //aproximacao da Hessiana
            for(int parIndex1 = 0; parIndex1 < numParams; parIndex1++)
                for(int parIndex2 = 0; parIndex2 < numParams; parIndex2++)
                    for(int ptIndex = 0; ptIndex < numPts; ptIndex++)
                    {
                        if (ptIndex == 0) hess[parIndex1][parIndex2] = 0.;
                        hess[parIndex1][parIndex2] += gaussGrad(x[ptIndex],mu,sigma,y0,Amp,parIndex1) * gaussGrad(x[ptIndex],mu,sigma,y0,Amp,parIndex2); 
                    }//ptIndex
            
            //Atira a diagonal para a descida do gradiente
            for(int parIndex = 0; parIndex < numParams; parIndex++)
                hess[parIndex][parIndex] *= (1. + lambda);
            
            //Gradiente do chi quadrado
            for(int parIndex = 0; parIndex < numParams; parIndex++)
                for(int ptIndex = 0; ptIndex < numPts; ptIndex++)
                {
                    if(ptIndex == 0) grad[parIndex] = 0.;
                    grad[parIndex] += (y[ptIndex] - gaussFunc(x[ptIndex],mu,sigma,y0,Amp)) * gaussGrad(x[ptIndex],mu,sigma,y0,Amp,parIndex);
                }//ptIndex
            
        
            
            //Resolve hess * da = g, em ordem a da
            //da[0] = d'mu'; da[1] = d'sigma'; da[2] = d'y0'; da[3] = d'Amp'
            double[] da = solveLin(hess, grad, numParams);
            
            double newMu = mu + da[0];
            double newSigma = sigma + da[1];
            double newy0 = y0 + da[2];
            double newAmp = Amp + da[3];
            
            double erro1 = chiSq(x,y,newMu,newSigma,newy0,newAmp);
            
            //indicador de que terminou a convergencia
            if (Math.abs(erro1 - erro0) > termEpsilon) term = 0;
            else
            {
                term++;
                if(term == 4)
                {
                    System.out.println("A terminar apos " + iter + " iteracoes.");
                    done = true;
                }
            }
            //if (iter > maxIter) done = true;
            
            //se a nova posicao e pior que a anterior
            if (erro1 > erro0) lambda *= 10.;
            else    //caso seja melhor
            {
                lambda *= 0.1;
                erro0 = erro1;
                mu = newMu;
                sigma = newSigma;
                y0 = newy0;
                Amp = newAmp;
            }
            iter++;
        }//while
        
        double[] params = new double[numParams+1];
        params[0] = mu;
        params[1] = sigma;
        params[2] = y0;
        params[3] = Amp;
        params[4] = erro0;
        
        return params;
    }//solve
    
    /**
     *Solves the linear equation hess * res = g, for 'res'
     *Assuming that the hessian is a 2x2 matrix and g a 2x1
     */
    private static double[] solveLin2(double[][] hess, double[] grad)
    {
        double a = hess[0][0], b = hess[0][1];
        double c = hess[1][0], d = hess[1][1];
        
        double z = c/(a*d)*(b*c/(a*d)-1);
        double x = (1-b*z)/a;
        double w = a/(c*b)*(a*d/(c*b)-1);
        double y = (1-d*w)/c;
        
        double[] res = new double[grad.length];
        res[0] = x * grad[0] + y * grad[1];
        res[1] = z * grad[0] + w * grad[1];
        
        return res;
    }//solveLin2
    
    /**
     *Solves the linear equation hess * res = g, for 'res'
     *Assuming that the hessian is a 4x4 matrix and g a 4x1
     */    
    private static double[] solveLin4(double[][] hess, double[] grad)
    {
        double a = hess[0][0], b = hess[0][1], c = hess[0][2], d = hess[0][3];
        double e = hess[1][0], f = hess[1][1], g = hess[1][2], h = hess[1][3];
        double i = hess[2][0], j = hess[2][1], k = hess[2][2], l = hess[2][3];
        double m = hess[3][0], n = hess[3][1], o = hess[3][2], p = hess[3][3];
        
        double[][] hessInv = new double[4][4];
        
        double denominator = d*g*j*m - c*h*j*m - d*f*k*m + b*h*k*m + c*f*l*m - b*g*l*m - d*g*i*n + 
                            c*h*i*n + d*e*k*n - a*h*k*n - c*e*l*n + a*g*l*n + d*f*i*o - b*h*i*o -
                            d*e*j*o + a*h*j*o + b*e*l*o - a*f*l*o - c*f*i*p + b*g*i*p + c*e*j*p-
                            a*g*j*p - b*e*k*p + a*f*k*p;
        hessInv[0][0] = (g*l*n - h*k*n + h*j*o - f*l*o - g*j*p + f*k*p)/denominator;
        hessInv[0][1] = (d*k*n - c*l*n - d*j*o + b*l*o + c*j*p - b*k*p)/denominator;
        hessInv[0][2] = (c*h*n - d*g*n + d*f*o - b*h*o - c*f*p + b*g*p)/denominator;
        hessInv[0][3] = (d*g*j - c*h*j - d*f*k + b*h*k + c*f*l - b*g*l)/denominator;
        hessInv[1][0] = (h*k*m - g*l*m - h*i*o + e*l*o + g*i*p - e*k*p)/denominator;
        hessInv[1][1] = (c*l*m - d*k*m + d*i*o - a*l*o - c*i*p + a*k*p)/denominator;
        hessInv[1][2] = (d*g*m - c*h*m - d*e*o + a*h*o + c*e*p - a*g*p)/denominator;
        hessInv[1][3] = (c*h*i - d*g*i + d*e*k - a*h*k - c*e*l + a*g*l)/denominator;
        hessInv[2][0] = (f*l*m - h*j*m + h*i*n - e*l*n - f*i*p + e*j*p)/denominator;
        hessInv[2][1] = (d*j*m - b*l*m - d*i*n + a*l*n + b*i*p - a*j*p)/denominator;
        hessInv[2][2] = (b*h*m - d*f*m + d*e*n - a*h*n - b*e*p + a*f*p)/denominator;
        hessInv[2][3] = (d*f*i - b*h*i - d*e*j + a*h*j + b*e*l - a*f*l)/denominator;
        hessInv[3][0] = (g*j*m - f*k*m - g*i*n + e*k*n + f*i*o - e*j*o)/denominator;
        hessInv[3][1] = (b*k*m - c*j*m + c*i*n - a*k*n - b*i*o + a*j*o)/denominator;
        hessInv[3][2] = (c*f*m - b*g*m - c*e*n + a*g*n + b*e*o - a*f*o)/denominator;
        hessInv[3][3] = (b*g*i - c*f*i + c*e*j - a*g*j - b*e*k + a*f*k)/denominator;

        double[] res = new double[grad.length];
        
        for (int index = 0; index < grad.length; index++)
        {
            if(index == 0) res[index] = 0.;
            for(int index2 = 0; index2 < grad.length; index2++)
            {
                res[index] += hessInv[index][index2] * grad[index2];
            }
        }
        
        return res;
        
    }//solveLin4
    
    /**
     *Depending on the number of parameters, chooses which solveLin? to use
     */
    private static double[] solveLin(double[][] hess, double[] g, int numParams)
    {
        if(numParams == 4) return solveLin4(hess, g);
        else if(numParams == 2) return solveLin2(hess, g);
        else return null;
    }
}//LMFit
