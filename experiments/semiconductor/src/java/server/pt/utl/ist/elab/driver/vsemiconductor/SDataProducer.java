package pt.utl.ist.elab.driver.vsemiconductor;

/** Motor de dados para o projecto final de semestre sobre jun��es P-N.
 *  Partindo dos dados fornecidos pelo painel de defini��es a simula��o
 *  vai ser corrida aqui, produzindo uma s�rie de resultados para serem
 *  apresentados ao utilizador no final da simula��o.
 */

import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.impl.data.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import java.util.logging.*;

/** Classe que cont�m a simula��o propriamente dita. Este motor de dados
 *  vai gerar uma colec��o de resultados de acordo com o input do recebido
 *  do utilizador atrav�s do painel de defini��es ap�s se executar a
 *  simula��o, apresentando estes resultados numa s�rie de formatos v�rios.
 */
public class SDataProducer extends BaseDataSource {
    
    /** Explicacao das Variaveis
     *
     *	a   -> lattice constant (cm^-4  => m^-4)
     *	dc (eS/eO) -> dielectric constant (adimensional)
     *  eF  -> Fermi energy level (eV)
     *	eG  -> energy bandgap	(eV  => V)
     *	el  -> electrical field (V/cm  => V/m)
     *	elM -> maximum field (talvez nao venha a ser usada...)
     *	e0  -> permittivity in vacuum	(F/cm  => F/m)
     *	eS  -> semiconductor permittivity	(F/cm  => F/m)
     *	h   -> Planck constant (J-s)
     *	k   -> Boltzmann constant	(J/K)
     *	kT (k*t) => kT nao e' utilizado como variavel de instancia) -> thermal energy
     *	m0  -> electron rest mass	(Kg)
     *	mn  -> electron effective mass	(Kg)
     *	mp  -> hole effective mass	(Kg)
     *	ni2 -> intrinsic density^2	(cm^-3  => m^-3)
     *	nA  -> acceptor impurity density	(cm^-3  => m^-3)
     *	nC  -> effective density of states in conduction band	(cm^-3  => m^-3)
     *	nD  -> donor impurity density	(cm^-3  => m^-3)
     *	nV  -> effective density of states in valence band	(cm^-3  => m^-3)
     *	q   -> magnitude of electronic charge	(C)
     *	t   -> absolute temperature	(K)
     *	vBI -> built-in potencial	(V)
     *	v   -> voltage	(V)
     *	w   -> thickness	(cm or microm  => m)
     *	x   -> position	(same as w)
     *	xp  -> first extreme position (same as w)
     *	xn  -> second extreme position	(same as w)
     */
    
    // Variaveis necessarias para as formulas
    private double a, eF, eG, el, elM, eS, mn, mp, ni, nA, nC, nD, nV, t, vBI, v, w, xp, xn, e, gradiente;
    
    final static double e0 = 8.85418 * Math.pow(10, -14), m0 = 0.91095 * Math.pow(10, -30), h =  6.62617 * Math.pow(10, -34), k =  1.38066* Math.pow(10, -23), q = 1.60218 * Math.pow(10, -19);
    
    // Vari�veis para conter os par�metros iniciais da simula��o
    private int numeroAmostras, tempoEntreAmostras;
    private double dadoresInicial, dadoresFinal, dadoresVariacao;
    private double aceitadoresInicial, aceitadoresFinal, aceitadoresVariacao;
    private double temperaturaInicial, temperaturaFinal, temperaturaVariacao;
    private double potencialInicial, potencialFinal, potencialVariacao;
    private boolean dadoresVaria = true, aceitadoresVaria = true, temperaturaVaria = true, potencialVaria = true;
    
    // Vari�vel de controlo da execu��o da anima��o
    private boolean running = false;
    
    // Variaveis de controlo para o material e a lingua
    private boolean silicio = true, portugues = true, linear = false;
    
    private SDriver sdriver = null;
    
    /** Construtor da classe. */
    public SDataProducer(SDriver sdriver) {
        this.sdriver = sdriver;
        numeroAmostras = 1;
        tempoEntreAmostras = 1;
        t = 300;
    }
    
    public void setGradienteImpurezas(double gradienteImpurezas) {
        gradiente = Math.pow(10, gradienteImpurezas);
    }
    
    public void setLingua(String lingua) {
        portugues = lingua.equals("portugues");
    }
    
    public void setJuncao(String tipo) {
        linear = tipo.equals("linear");
    }
    
    public void setMaterial(String material) {
        silicio = material.equals("silicio");
        
        // Calculo de mn e mp
        // a 300K, nV = 1.04 x 10^19 e nC = 2.8 x 10^19 (para o silicio) e nV = 7.0 x 10^18 e nC = 4.7 x 10^17 (para o GaAs)
        if(silicio) {
            nV = 1.04 * Math.pow(10, 19);
            nC = 2.8 * Math.pow(10, 19);
            eS = 11.9 * e0;
        } else if(!silicio) {
            nV = 7.0 * Math.pow(10, 18);
            nC = 4.7 * Math.pow(10, 17);
            eS = 13.1 * e0;
        }
    }
    
    /** M�todo para definir o n�mero de amostras a tomar. Basicamente, ser�
     *  o n�mero de itera��es para o qual se vai executar a simula��o.
     *  Servir� para determinar o ritmo de varia��o dos par�metros a considerar
     *  (caso variem ao longo da experi�ncia), assim como o tempo total
     *  (tempo "virtual") da simula��o.
     */
    public void setNumAmostras(int numAmostras) {
        numeroAmostras = numAmostras;
    }
    
    /** M�todo para definir o tempo entre amostras. Este intervalo de tempo
     *  servir� para avan�ar a simula��o no tempo de acordo com o definido
     *  pelo utilizador, servindo para determinar n�o s� o tempo total "virtual"
     *  da simula��o (com o n�mero de amostras) como para determinar a velocidade
     *  da anima��o e para calcular certas grandezas dependentes do tempo.
     */
    public void setTempoAmostras(int tempoAmostras) {
        tempoEntreAmostras = tempoAmostras;
    }
    
    /** M�todo para configurar as vari�veis referentes � concentra��o de
     *  dadores. O primeiro argumento determina se h� alguma varia��o ao
     *  longo da simula��o desse par�metro e os seguintes o seu valor inicial
     *  e final (que s� ser� considerado se o primeiro par�metro for verdadeiro).
     */
    public void configureDadores(boolean varia, double valIni, double valFin) {
        dadoresInicial = Math.pow(10, valIni);
        
        if(varia) {
            dadoresFinal = Math.pow(10, valFin);
            dadoresVariacao = (dadoresFinal - dadoresInicial) / (double)numeroAmostras;
            dadoresVaria = true;
        } else {
            dadoresVaria = false;
        }
    }
    
    /** M�todo para configurar as vari�veis referentes � concentra��o de
     *  aceitadores. O primeiro argumento determina se h� alguma varia��o ao
     *  longo da simula��o desse par�metro e os seguintes o seu valor inicial
     *  e final (que s� ser� considerado se o primeiro par�metro for verdadeiro).
     */
    public void configureAceitadores(boolean varia, double valIni, double valFin) {
        aceitadoresInicial = Math.pow(10, valIni);
        
        if(varia) {
            aceitadoresFinal = Math.pow(10, valFin);
            aceitadoresVariacao = (aceitadoresFinal - aceitadoresInicial) / (double)numeroAmostras;
            aceitadoresVaria = true;
        } else {
            aceitadoresVaria = false;
        }
    }
    
    /** M�todo para configurar as vari�veis referentes � temperatura.
     *  O primeiro argumento determina se h� alguma varia��o ao
     *  longo da simula��o desse par�metro e os seguintes o seu valor inicial
     *  e final (que s� ser� considerado se o primeiro par�metro for verdadeiro).
     */
    public void configureTemperatura(boolean varia, double valIni, double valFin) {
        temperaturaInicial = valIni;
        
        if(varia) {
            temperaturaFinal = valFin;
            temperaturaVariacao = (valFin - valIni) / (double)numeroAmostras;
            temperaturaVaria = true;
        } else {
            temperaturaVaria = false;
        }
    }
    
    /** M�todo para configurar as vari�veis referentes a um potencial externo
     *  aplicado. O primeiro argumento determina se h� alguma varia��o ao
     *  longo da simula��o desse par�metro e os seguintes o seu valor inicial
     *  e final (que s� ser� considerado se o primeiro par�metro for verdadeiro).
     */
    public void configurePotencial(boolean varia, double valIni, double valFin) {
        potencialInicial = valIni;
        
        if(varia) {
            potencialFinal = valFin;
            potencialVariacao = (valFin - valIni) / (double)numeroAmostras;
            potencialVaria = true;
        } else {
            potencialVaria = false;
        }
    }
    
    class Produtor extends Thread {
        
        /** M�todo para iniciar a simula��o.
         */
        public void run() {
            int i;
            int cSample = 0;
            nD = dadoresInicial;
            nA = aceitadoresInicial;
            t = temperaturaInicial;
            v = potencialInicial;
            
            mp = ((h * h) * Math.pow(nV, (2.0/3.0))) / (2 * Math.pow(2, (2.0/3.0)) * k * Math.PI * t);
            mn = ((h * h) * Math.pow(nC, (2.0/3.0))) / (2 * Math.pow(2, (2.0/3.0)) * k * Math.PI * t);
            
            try {
                sleep(1000);
            }
            catch(InterruptedException ie) {
            }
            
            while(running && cSample < numeroAmostras) {
                /** Calculando apenas o W! **/
                
                if(silicio) {
                    eG = 1.17 - ((4.73 * Math.pow(10, -4) * t*t) / (t + 363));
                } else if(!silicio) {
                    eG = 1.52 - ((5.4 * Math.pow(10, -4) * t*t) / (t + 204));
                }
                
                nV = 2 * Math.pow( (2*Math.PI*mp*k*t)/(h*h), 1.5);
                nC = 2 * Math.pow( (2*Math.PI*mn*k*t)/(h*h), 1.5);
                
                /* Calculo do nivel de Fermi - k e' dividido por q de forma a que a sua unidade seja eV */
                eF = eG - (k/q)*t*Math.log(nC / nD);
                
                /*System.out.println("*************************");
                System.out.println("Ef = " + eF);
                System.out.println("EG = " + eG);
                System.out.println("k/q = " + (k/q));
                System.out.println("nC/nD = " + (nC/nD));
                System.out.println("log(nC/nD) = " + Math.log(nC/nD));*/
                
                ni = Math.sqrt(nC*nV*Math.exp((-eG*q) / (k*t)));
                                
                if(linear) {
                    vBI = (2.0/3.0) * ((k*t)/q) * Math.log((gradiente*gradiente * eS * (k*t)/q)/(8 * q * ni*ni*ni));
                    
                    if(v >= vBI) w = 0; else w = Math.pow(((12 * eS * (vBI - v))/(q * gradiente)), 1.0/3.0);
                    
                    e = (q * gradiente * w*w)/(8 * eS);
                } else {
                    vBI = ((k*t)/q)*Math.log((nA*nD)/(ni*ni));
                    
                    if(v >= vBI) w = 0; else w = Math.sqrt(((2*eS)/q) * ((nA + nD)/(nA*nD)) * (vBI - v));
                    
                    xn = w/((nD/nA) + 1);
                    
                    xp = (nD/nA) * xn;
                    
                    e = (q * nA * xp)/(eS);
                }
                
                if(dadoresVaria) nD += dadoresVariacao;
                if(aceitadoresVaria) nA += aceitadoresVariacao;
                if(temperaturaVaria) t += temperaturaVariacao;
                if(potencialVaria) v += potencialVariacao;
                
                PhysicsValue[] value = new PhysicsValue[9];
                value[0] = new PhysicsValue(PhysicsValFactory.fromDouble(w),
                getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getDefaultErrorValue(),
                getAcquisitionHeader().getChannelsConfig(0).getSelectedScale().getMultiplier()
                );
                value[1] = new PhysicsValue(PhysicsValFactory.fromDouble(eG),
                getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getDefaultErrorValue(),
                getAcquisitionHeader().getChannelsConfig(1).getSelectedScale().getMultiplier()
                );
                value[2] = new PhysicsValue(PhysicsValFactory.fromDouble(vBI),
                getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getDefaultErrorValue(),
                getAcquisitionHeader().getChannelsConfig(2).getSelectedScale().getMultiplier()
                );
                value[3] = new PhysicsValue(PhysicsValFactory.fromDouble(e),
                getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getDefaultErrorValue(),
                getAcquisitionHeader().getChannelsConfig(3).getSelectedScale().getMultiplier()
                );
                if(temperaturaVaria) {
                    value[4] = new PhysicsValue(PhysicsValFactory.fromDouble(t),
                    getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(4).getSelectedScale().getMultiplier()
                    );
                }
                value[5] = new PhysicsValue(PhysicsValFactory.fromDouble(eF),
                                                getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getDefaultErrorValue(),
                                                getAcquisitionHeader().getChannelsConfig(5).getSelectedScale().getMultiplier()
                                            );
                if(dadoresVaria) {
                    value[6] = new PhysicsValue(PhysicsValFactory.fromDouble(nD),
                    getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(6).getSelectedScale().getMultiplier()
                    );
                }
                if(aceitadoresVaria) {
                    value[7] = new PhysicsValue(PhysicsValFactory.fromDouble(nA),
                    getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(7).getSelectedScale().getMultiplier()
                    );
                }
                if(potencialVaria) {
                    value[8] = new PhysicsValue(PhysicsValFactory.fromDouble(v),
                    getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getDefaultErrorValue(),
                    getAcquisitionHeader().getChannelsConfig(8).getSelectedScale().getMultiplier()
                    );
                }
                cSample++;
                
                
                addDataRow(value);
                
                try {
                    sleep(tempoEntreAmostras);
                }
                catch(InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            sdriver.setStoping();
            sdriver.setStoped();            
            endProduction();
        }        
    }
    
    public void startProduction() {
        running = true;
        new Produtor().start();
    }
    
    public void shutdown() {
        running = false;
    }
    
    public void endProduction() {        
	setDataSourceEnded();
    }
    
    public void stopNow()
    {
	running = false;
	//setRunning(running);
	setDataSourceStoped();
    }    
}