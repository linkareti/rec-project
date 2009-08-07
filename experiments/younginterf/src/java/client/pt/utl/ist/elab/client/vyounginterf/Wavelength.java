//package com.mindprod.wavelength; por uma questao de conveniencia nao usei o package original, espero que nao haja problemas
package pt.utl.ist.elab.client.vyounginterf;

import java.awt.Color;

/**
 * Create a Color object given the wavelength of the colour in nanometers.
 * Version 1.0
 *
 * instead of:
 * Color c = new Color(255, 0, 0);
 * use the freqency in nanometers, and gamma 0.0. .. 1.0.
 * Color c = Wavelength.wlColor( 400.0f, 0.80f );
 *
 * or using frequency in Terahertz and gamma 0.0. .. 1.0.
 * Color c = Wavelength.fColor( 500.0f, 1.0f );
 *
 * You might use it to draw a realistic rainbow, or to write
 * educational Applets about the light spectrum.
 *
 * Based on a Fortran program by Dan Bruton (astro@tamu.edu)
 * The original Fortran can be found at:
 * http://www.isc.tamu.edu/~astro/color.html
 * It uses linear interpolation on ten spectral bands.
 *
 * @author  copyright (c) 1998-2004 Roedy Green, Canadian Mind Products
 * may be copied and used freely for any purpose but military.
 *
 * Roedy Green
 * Canadian Mind Products
 * #327 - 964 Heywood Avenue
 * Victoria, BC Canada V8V 2Y5
 * tel: (250) 361-9093
 * mailto:roedyg@mindprod.com
 * http://mindprod.com
 *
 */
public class Wavelength {
    
    private static final String EmbeddedCopyright =
    "copyright (c) 1998-2004 Roedy Green, Canadian Mind Products, http://mindprod.com";
    
    /**
     * Creates a Color object given the wavelength instead of the
     * usual RGB or HSB values.
     *
     * @param wl wavelength of the light in nanometers.
     * Will show up black outside the range 380..780 nanometers.
     *
     * @param gamma 0.0 .. 1.0 intensity.
     */
    public static Color wvColor(float wl, float gamma ) {
        
        /**
         * red, green, blue component in range 0.0 .. 1.0.
         */
        float r = 0;
        float g = 0;
        float b = 0;
        
        /**
         * intensity 0.0 .. 1.0
         * based on drop off in vision at low/high wavelengths
         */
        float s = 1;
        
        /**
         * We use different linear interpolations on different bands.
         * These numbers mark the upper bound of each band.
         */
        final float [] bands = { 380, 420, 440, 490, 510, 580, 645, 700, 780, Float.MAX_VALUE};
        
        /**
         * Figure out which band we fall in.  A point on the edge
         * is considered part of the lower band.
         */
        int band = bands.length - 1;
        for ( int i=0; i<bands.length; i++ ) {
            if ( wl <= bands[i] ) {
                band = i;
                break;
            }
        }
        switch ( band ) {
            
            case 0:
                /* invisible below 380 */
                // The code is a little redundant for clarity.
                // A smart optimiser can remove any r=0, g=0, b=0.
                r = 0;
                g = 0;
                b = 0;
                s = 0;
                break;
                
            case 1:
                /* 380 .. 420, intensity drop off. */
                r = (440-wl)/(440-380);
                g = 0;
                b = 1;
                s = .3f + .7f*(wl-380)/(420-380);
                break;
                
            case 2:
                /* 420 .. 440 */
                r = (440-wl)/(440-380);
                g = 0;
                b = 1;
                break;
                
            case 3:
                /* 440 .. 490 */
                r = 0;
                g = (wl-440)/(490-440);
                b = 1;
                break;
                
            case 4:
                /* 490 .. 510 */
                r = 0;
                g = 1;
                b = (510-wl)/(510-490);
                break;
                
            case 5:
                /* 510 .. 580 */
                r = (wl-510)/(580-510);
                g = 1;
                b = 0;
                break;
                
            case 6:
                /* 580 .. 645 */
                r = 1;
                g = (645-wl)/(645-580);
                b = 0;
                break;
                
            case 7:
                /* 645 .. 700 */
                r = 1;
                g = 0;
                b = 0;
                break;
                
            case 8:
                /* 700 .. 780, intensity drop off */
                r = 1;
                g = 0;
                b = 0;
                s = .3f + .7f*(780-wl)/(780-700);
                break;
                
            case 9:
                /* invisible above 780 */
                r = 0;
                g = 0;
                b = 0;
                s = 0;
                break;
                
        } // end switch
        
        // apply intensity and gamma corrections.
        s *= gamma;
        r *= s;
        g *= s;
        b *= s;
        
        return new Color(r, g, b);
        
    } // end wvColor
    
    /**
     * Creates a Color object given the frequency instead of the
     * usual RGB or HSB values.
     *
     * @param freq frequency of the light in Terahertz (10.0e12 Hz)
     * Will show up black outside the range 384..789 TeraHertz.
     *
     * @param gamma 0.0 .. 1.0 intensity.
     */
    public static Color fColor(float freq, float gamma ) {
        // speed of light is 299,792,458 meters/sec
        // = 2.9972458e08 m/sec
        // = 2.9972458e17 nm/sec
        // = 2.9972458e05 nm/picosecond
        // = 299,724.58   nm/picosecond
        // 1 Terahertz = 1 cycle per picosecond
        return wvColor(299724.58f/freq, gamma);
    } // end fColor
    
} // end class WaveLength

