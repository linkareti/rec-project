/*
 * Sistema.java
 *
 * Created on 19 de Março de 2005, 16:19
 */

package pt.utl.ist.elab.virtual.client.cargas3d;
import org.opensourcephysics.displayejs.*;
import java.util.ArrayList;
import java.awt.Color;
/**
 *
 * @author n0dP2
 */
public class Sistema {
    
    /** Creates a new instance of Sistema */
    public Sistema() {
    }
    
//    public static void main(String args[]){
//        stringToSistema("&1#7#2#12");
//        javax.swing.JFrame dummy = new javax.swing.JFrame();
//        pt.utl.ist.elab.virtual.client.cargas3d.displays.Painel painel = new pt.utl.ist.elab.virtual.client.cargas3d.displays.Painel();
//        dummy.getContentPane().add(painel);
//        for(int i=0;i<sistema.size();i++){
//            painel.addDrawable((InteractiveCharge)sistema.get(i));
//        }
//        dummy.setDefaultCloseOperation(dummy.EXIT_ON_CLOSE);
//        dummy.pack();
//        dummy.show();
//    }
    
    public static ArrayList sistema = new ArrayList();
    
    
    public static ArrayList novaCarga(float x,float y,float z,float q){
        sistema.add(set(x,y,z,q));
        return sistema;
    }
    
    public static ArrayList editarCarga(float x,float y,float z,float q,int i){
        sistema.set(i,set(x,y,z,q));
        return sistema;
    }
    
    public static ArrayList apagarCarga(int i){
        sistema.remove(i);
        return sistema;
    }
    
    private static InteractiveCharge set(float x,float y,float z,float q){
        
        InteractiveCharge carga = new InteractiveCharge();
        carga.setSizeXYZ(0.666,0.666,0.666);
        carga.setXYZ(x,y,z);
        carga.setCharge(q);
        Color cor=Color.white;
        if(q>0) cor=new Color(255,(int)((25-q)*10.2),(int)((25-q)*10.2));
        if(q<0) cor=new Color((int)((25+q)*10.2),(int)((25+q)*10.2),255);
        carga.getStyle().setFillPattern(cor);
        
        return carga;
    }
    
    public static java.util.ArrayList stringToSistema(String str){
        sistema.clear();
        String[] str2=str.split("&");
        for(int i=0;i<str2.length;i++){
            String[] str3 = str2[i].split("#");
            for(int j=0;j<str3.length;j++){
                try{
                    float X = Float.parseFloat(str3[0]);
                    float Y = Float.parseFloat(str3[1]);
                    float Z = Float.parseFloat(str3[2]);
                    float Q = Float.parseFloat(str3[3]);
                    novaCarga(X,Y,Z,Q);} catch (NumberFormatException e){}
            }
        }
        return sistema;
    }
    
    public static String sistemaToString(){
        java.util.ArrayList sist=sistema;
        String str=new String();
        String X,Y,Z,Q;
        for(int i=0;i<sist.size();i++){
            InteractiveCharge carg = ((InteractiveCharge)sist.get(i));
            X=Float.toString((float)carg.getX());
            Y=Float.toString((float)carg.getY());
            Z=Float.toString((float)carg.getZ());
            Q=Float.toString(carg.getCharge());
            str=str+"&"+X+"#"+Y+"#"+Z+"#"+Q;
        }
        return str;
    }
    
}