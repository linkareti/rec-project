package pt.utl.ist.elab.client.virtual.guipack;

/*
 * Class.java
 *
 * Created on 21 de Dezembro de 2004, 17:03
 */

/**
 *
 * @author  nomead
 */
public class GUtils {
    
    /** Creates a new instance of Class */
    public GUtils() {
    }
    
    public static double validateInput(javax.swing.JTextField textField, javax.swing.JSlider slider, double multiplier) throws NumberFormatException {
        int val = (int) Math.round(Double.parseDouble(textField.getText()));
        
        if (val > slider.getMaximum())
            val = slider.getMaximum();
        else if (val < slider.getMinimum())
            val = slider.getMinimum();
        
        textField.setText(Integer.toString(val));
        slider.setValue(val);
        return val*multiplier;
    }
    
    public static float validateInput(javax.swing.JTextField textField, float min,float max) throws NumberFormatException{
        float val;
        
        try{
            val = Float.parseFloat(textField.getText());} catch(NumberFormatException e){
                textField.setText("5.0");
                val = Float.parseFloat(textField.getText());}
        
        
        if (val > max)
            val = max;
        else if (val < min)
            val = min;
        
        textField.setText(String.valueOf(val));
        return val;
    }
    
    public static String trimDecimalN(double val, int n){
        String str = Double.toString(val);
        String str1;
        String str2 = "";
        try {
            str1 = str.substring(0, str.indexOf(".")+n+1);
        } catch (StringIndexOutOfBoundsException e) {
            str1 = str;
        }
        try {
            str2 = str.substring(str.indexOf("E"), str.length());
        } catch (StringIndexOutOfBoundsException e){}
        
        return str1.concat(str2);
    }
    
    public static double getNumSld(String str, int i){
        return Double.parseDouble(str.substring(i, str.length()-i));
    }
    
}
