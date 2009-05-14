/*
 * im_analizer.java
 *
 * Created on 24 de Agosto de 2004, 2:16
 */

//package javaelab;
package pt.utl.ist.elab.client.thomson.displays;

import java.awt.Color;
import java.awt.Image;

import com.linkare.rec.impl.utils.Defaults;

/**
 *
 * @author  ivo
 */
public class AnalizadorImagens
{
    
    protected java.awt.Image im_temp = null;
    protected java.awt.Image im_original = null;
    protected java.awt.Image[] im_alinhada = null;
    protected java.awt.Image[] im_feixe = null;           //so o feixe num funco branco ou preto (preto e branco :)
    protected java.awt.Image img640480 = null;          // imagem final com o raio tracado....
    protected final int IMAGE_WIDTH = 640;
    protected final int IMAGE_HEIGHT = 480;
    protected int new_width = 0;
    protected int new_height = 0;
    protected int area = 640 * 480;
    protected double[] circunferencia = new double[3];   // circunferencia[0]=centro X circunferencia[1] = centro Y e circunferencia[2] = raio
    protected double[] recta = new double[3];   // recta[0]=m recta[1] = b e recta[2] = R quadrado   y=mx+b
    protected double[] distancias_referencia = new double[2];   //Dx e Dy por cada pixel
    protected boolean isUp = false;        // feixe � orientado para cima? serve para construir o display final na imagem em 640 x 480
    protected boolean allDone = false;      // todos os calculos feitos
    protected int i = 0;
    protected int j = 0;
    protected boolean circ_rect = false;  // desenhar para o equilibrio de forcas(recta)(false) ou so para o campo magnetico(circunferencia)(true)
    
    //Variáveis de sistema
    /*private static final int DIST = 50;
    private static final int CROSS = 19;
    private static final int D1 = 19;
    private static final int D2 = 80;
    private static final int D3 = 90;
    private static final int PRETO_R1 = 120;
    private static final int PRETO_G1 = 112;
    private static final int PRETO_B1 = 13;
    private static final int PRETO_R2 = 40;
    private static final int PRETO_G2 = 44;
    private static final int PRETO_B2 = 0;
    private static final int AZUL_R = 162;
    private static final int AZUL_G = 178;
    private static final int AZUL_B = 229;
    private static final int AZUL_RAIO = 55;*/
    
    public static final String THOMSON_CROSS = "Thomson.Cross";
    public static int CROSS = Defaults.defaultIfEmpty(System.getProperty(THOMSON_CROSS), 17);
    public static final String THOMSON_D1 = "Thomson.D1";
    public static int D1 = Defaults.defaultIfEmpty(System.getProperty(THOMSON_D1), 18);
    public static final String THOMSON_D2 = "Thomson.D2";
    public static int D2 = Defaults.defaultIfEmpty(System.getProperty(THOMSON_D2), 115);
    public static final String THOMSON_PRETO_R1 = "Thomson.Preto.R1";
    public static int PRETO_R1 = Defaults.defaultIfEmpty(System.getProperty(THOMSON_PRETO_R1), 165);
    public static final String THOMSON_PRETO_G1 = "Thomson.Preto.G1";
    public static int PRETO_G1 = Defaults.defaultIfEmpty(System.getProperty(THOMSON_PRETO_G1), 162);
    public static final String THOMSON_PRETO_B1 = "Thomson.Preto.B1";
    public static int PRETO_B1 = Defaults.defaultIfEmpty(System.getProperty(THOMSON_PRETO_B1), 140);
    public static final String THOMSON_AZUL_R = "Thomson.Azul.R";
    public static int AZUL_R = Defaults.defaultIfEmpty(System.getProperty(THOMSON_AZUL_R), 198);
    public static final String THOMSON_AZUL_G = "Thomson.Azul.G";
    public static int AZUL_G = Defaults.defaultIfEmpty(System.getProperty(THOMSON_AZUL_G), 218);
    public static final String THOMSON_AZUL_B = "Thomson.Azul.B";
    public static int AZUL_B = Defaults.defaultIfEmpty(System.getProperty(THOMSON_AZUL_B), 255);
    public static final String THOMSON_AZUL_RAIO = "Thomson.Azul.Raio";
    public static int AZUL_RAIO = Defaults.defaultIfEmpty(System.getProperty(THOMSON_AZUL_RAIO), 25);
    public static final String THOMSON_P = "Thomson.P";
    public static int P = Defaults.defaultIfEmpty(System.getProperty(THOMSON_P), 1);
    public static final String THOMSON_RANSAC_ERROR = "Thomson.Ransac.Error";
    public static int RANSAC_ERROR = Defaults.defaultIfEmpty(System.getProperty(THOMSON_RANSAC_ERROR), 1);
    
    //Fim variaveis sistema
    
    java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
    
    
    /** Creates a new instance of im_analizer */
    public AnalizadorImagens()
    {
        /*System.out.println("DIST = " + DIST);
        System.out.println("THOMSON_DIST = "  + THOMSON_DIST);
        System.out.println("Thomson.Preto.R1 = " + THOMSON_PRETO_R1);
        System.out.println("Thomson.Preto.G1 = " + THOMSON_PRETO_G1);
        System.out.println("Thomson.Preto.B1 = " + THOMSON_PRETO_B1);
        System.out.println("Thomson.Preto.R2 = " + THOMSON_PRETO_R2);
        System.out.println("Thomson.Preto.G2 = " + THOMSON_PRETO_G2);
        System.out.println("Thomson.Preto.B2 = " + THOMSON_PRETO_B2);
        System.out.println("Thomson.Azul.G = " + THOMSON_AZUL_G);*/
    }
    
    public java.awt.Image byteArray2Image(byte[] byteArray)
    {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try
        {
            baos.write(byteArray);
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
        }
        
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        
        javax.imageio.stream.MemoryCacheImageInputStream mciis = new javax.imageio.stream.MemoryCacheImageInputStream(bais);
        
        java.awt.image.BufferedImage bImage = null;
        
        //converting the jpg byteArray into an image
        try
        {
            bImage = javax.imageio.ImageIO.read(mciis);
        }
        catch(java.io.IOException e)
        {
            e.printStackTrace();
        }
        
        return (java.awt.Image) bImage;
    }//byteArray2Image(byte[] byteArray)
    
    public AnalizadorImagens(java.awt.Image im_original)
    {
        this.im_original = im_original;
        area = IMAGE_WIDTH * IMAGE_HEIGHT;
    }
    
    public void clearAll()
    {
        im_temp = null;
        im_original = null;
        im_alinhada = null;
        im_feixe = null;
        img640480 = null;
        new_width = 0;
        new_height = 0;
        
        
        isUp = false;
        allDone = false;
    }
    
    
    public boolean processAll()
    {
        
        //   falta acrescentar o resto !!!!!!!!!!!!!
        
        allDone = true;
        return (true);
    }
    
    public boolean getStatus()
    {
        
        return(allDone);
    }    //ver se ja esta tudo feito
    
    public void setOriginalImage(java.awt.Image img)
    {
        im_original = img ;
        area = IMAGE_WIDTH * IMAGE_HEIGHT;
    }
    
    public Image setOriginalImageAsByteArray(byte[] image)
    {
        Image original = byteArray2Image(image);
        setOriginalImage(original);
        return original;
    }
    
    public int getNewWidth()
    {
        
        return (new_width);
    }
    
    public int getNewHeight()
    {
        
        return (new_height);
    }
    
    public double[] getCircunferenciaPontos(java.awt.Image img)
    {
        try
        {
            /*if (circunferencia[0] * circunferencia[1] * circunferencia[2] != 0 )
            {
                return (circunferencia);
            }*/
            
            int p=0;
            int n=0;
            int n_max = 0;
            int med1 = 0;
            int med2 = 0;
            int med3 = 0;
            int med4 = 0;
            int sup = 0;
            double Ai = 0;
            double Bi = 0;
            double Ri = 0;
            double A = 0;
            double B = 0;
            double R = 0;
            int var1 = 0;
            int var2 = 0;
            double var3 = 0;
            double RANSAC_ERR = RANSAC_ERROR/10000d;
            
            
            int[] img_BW = new int[new_width*new_height];
            int[] img_BW_temp = new int[new_width*new_height];
            
            java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(img,0,0,new_width,new_height,img_BW, 0 , new_width);
            
            try
            {pg.grabPixels();}
            catch(InterruptedException e)
            { e.printStackTrace();}
            
/*
            p=300;
 
            while (p>150)
            {
 
 
 
                for (j = 5; j < (new_height - 6); j++)
                {    //erode para tira pontos deslinhados
                    for (i = 5; i < (new_width - 5); i++)
                    {
                        //                         if (img_BW[i+j*new_width] == 0xffffffff & img_BW[i+1+j*new_width] == 0xffffffff & img_BW[i-1+j*new_width] == 0xffffffff & img_BW[i+(j+1)*new_width] == 0xffffffff & img_BW[i+(j-1)*new_width] == 0xffffffff){
                        if (img_BW[i+j*new_width] == 0xffffffff & img_BW[i+1+j*new_width] == 0xffffffff & img_BW[i-1+j*new_width] == 0xffffffff)
                        {
                            img_BW_temp[i+j*new_width] = 0xffffffff;
                        }
                        else
                        {
                            img_BW_temp[i+j*new_width] = 0xff000000;
                        }
                    }
                }
                for (j = 5; j < (new_height - 6); j++)for (i = 5; i < (new_width - 5); i++)img_BW[i+j*new_width] =img_BW_temp[i+j*new_width];
 
 
                // numero de pontos total -> p
                p=0;
                for (i = 5; i < (new_width - 5); i++)
                    for (j = 5; j < (new_height - 6); j++)
                        if (img_BW[i+j*new_width] == 0xffffffff)
                            p++;
 
            }
 */
            int p_temp = 20;
            for (j = 0; j < (new_height - 1); j++)
            {    //erode para tira pontos deslinhados
                
                while (p_temp > P)
                {
                    
                    for (i = 1; i < (new_width - 2); i++)
                    {
                        if (img_BW[i+j*new_width] == 0xffffffff & img_BW[i+1+j*new_width] == 0xffffffff & img_BW[i-1+j*new_width] == 0xffffffff)
                        {
                            img_BW_temp[i+j*new_width] = 0xffffffff;
                        }
                        else
                        {
                            img_BW_temp[i+j*new_width] = 0xff000000;
                        }
                    }
                    //actualizar a imagem
                    for (i = 1; i < (new_width - 2); i++) img_BW[i+j*new_width] = img_BW_temp[i+j*new_width];
                    
                    p_temp = 0;
                    for (i = 1; i < (new_width - 2); i++)
                        if (img_BW[i+j*new_width] == 0xffffffff)
                            p_temp++;
                    
                }
                p_temp = 20;
            }
            
            // numero de pontos total -> p
            p=0;
            for (i = 1; i < (new_width - 2); i++)
                for (j = 0; j < (new_height - 1); j++)
                    if (img_BW[i+j*new_width] == 0xffffffff)
                        p++;
            
            
            System.out.println("p=" + p);
            //            im_alinhada[2] = toolkit.createImage(new java.awt.image.MemoryImageSource(new_width, new_height, img_BW, 0, new_width));
            
            
            
            int[] p_x = new int [p];
            int[] p_y = new int [p];
            
            
            
            
            for (j = 5; j < (new_height - 6); j++)
            {
                for (i = 5; i < (new_width - 5); i++)
                {
                    
                    
                    if (img_BW[i+j*new_width] == 0xffffffff)
                    {
                        
                        p_x[n]=i;
                        p_y[n]=j;
                        n++;
                        //System.out.println(String.valueOf(n) + " " +String.valueOf(i) + " " +String.valueOf(j) );
                        
                    }
                }
            }
            System.out.println(n);
            sup = p;
            med1 = Math.round(p/5);
            med2 = Math.round(2*p/5);
            med3 = Math.round(3*p/5);
            med4 = Math.round(4*p/5);
            
            
            // RANSAC para achar o X0 Y0 R da circunferencia
            for (int alfa = 0 ; alfa< med1; alfa ++)
            {  // x1 y1
                for (int beta = med2 ; beta < med3 ; beta ++)
                { //x2 y2
                    for ( int gama = med4 ; gama < sup;gama ++)
                    { //x3 y3
                        
                        
                        var3 = 2*( ( p_y[alfa]-p_y[beta] )*( p_x[gama]-p_x[alfa] )-( p_y[alfa]-p_y[gama] )*( p_x[beta]-p_x[alfa] ));
                        
                        if (var3 != 0)
                        { //para evitar divisoes por 0
                            
                            var1 =  p_x[beta]*p_x[beta] + p_y[beta]*p_y[beta] - p_x[alfa]*p_x[alfa] - p_y[alfa]*p_y[alfa] ;
                            var2 = p_x[gama]*p_x[gama] + p_y[gama]*p_y[gama] - p_x[alfa]*p_x[alfa] - p_y[alfa]*p_y[alfa]  ;
                            
                            Bi = ( var2*(p_x[beta] - p_x[alfa]) -  var1*(p_x[gama] - p_x[alfa])  )/var3;
                            Ai = (var1 + 2*Bi*(p_y[alfa]-p_y[beta])) /(2*p_x[beta]-2*p_x[alfa]);
                            Ri = Math.sqrt((p_x[alfa]-Ai)*(p_x[alfa]-Ai)+(p_y[alfa]-Bi)*(p_y[alfa]-Bi));
                            
                            //System.out.println( String.valueOf(Ri ) + " = " + String.valueOf(((p_x[alfa]-Ai)*(p_x[alfa]-Ai)+(p_y[alfa]-Bi)*(p_y[alfa]-Bi))/(Ri*Ri) ) + " = " + String.valueOf((p_x[beta]-Ai)*(p_x[beta]-Ai)+(p_y[beta]-Bi)*(p_y[beta]-Bi) /(Ri*Ri)) + " = " + String.valueOf((p_x[gama]-Ai)*(p_x[gama]-Ai)+(p_y[gama]-Bi)*(p_y[gama]-Bi)/(Ri*Ri) ));
                            
                            
                            n=0; // ver o numero de pontos k se ajustam ao modelo
                            for (int pontos = 0; pontos < p; pontos++)
                            {
                                
                                
                                if(( (p_x[pontos]-Ai)*(p_x[pontos]-Ai)+(p_y[pontos]-Bi)*(p_y[pontos]-Bi) - (Ri*Ri)) < RANSAC_ERR*(Ri*Ri) & ( (p_x[pontos]-Ai)*(p_x[pontos]-Ai)+(p_y[pontos]-Bi)*(p_y[pontos]-Bi) - (Ri*Ri)) > - RANSAC_ERR*(Ri*Ri) )
                                {
                                    n++;
                                    //System.out.println( String.valueOf(n ) + " = " + String.valueOf(Math.abs((p_x[pontos]-Ai)*(p_x[pontos]-Ai)+(p_y[pontos]-Bi)*(p_y[pontos]-Bi)) - (Ri*Ri) ) );
                                    
                                }
                            }
                            if (n > n_max)
                            {
                                R = Ri;
                                A = Ai;
                                B = Bi;
                                n_max = n;
                            }
                        }
                        
                    }
                }
            }
            
            System.out.println(String.valueOf(p) + " " + String.valueOf(n_max) + "  " + String.valueOf(A) + "  " + String.valueOf(B) + "  " + String.valueOf(R));
            
            
            circunferencia[0] = A;
            circunferencia[1] = B;
            circunferencia[2] = R;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return (circunferencia);
    }
    
    public double[] getEquilibrioData(java.awt.Image img)
    {
        if (recta[0] * recta[1] * recta[2] != 0 )
        {
            return (recta);
        }
        
        int p=0;
        int sx=0;
        int sy = 0;
        int sxy = 0;
        int sx2 = 0;
        int sy2 = 0;
        double SSyy =0;
        double SEE = 0;
        double x2 =0;
        
        int[] img_BW = new int[new_width*new_height];
        int[] img_BW_temp = new int[new_width*new_height];
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(img,0,0,new_width,new_height,img_BW, 0 , new_width);
        
        try
        {pg.grabPixels();}
        catch(InterruptedException e)
        { e.printStackTrace();}
        
        
        
        int p_temp = 20;
        for (i = 0; i < (new_width - 1); i++)
        {    //erode para tira pontos deslinhados
            
            while (p_temp > 1)
            {
                
                for (j = 1; j < (new_height - 2); j++)
                {
                    if (img_BW[i+j*new_width] == 0xffffffff & img_BW[i+(j+1)*new_width] == 0xffffffff & img_BW[i+(j-1)*new_width] == 0xffffffff)
                    {
                        img_BW_temp[i+j*new_width] = 0xffffffff;
                    }
                    else
                    {
                        img_BW_temp[i+j*new_width] = 0xff000000;
                    }
                }
                //actualizar a imagem
                for (j = 1; j < (new_height - 2); j++) img_BW[i+j*new_width] = img_BW_temp[i+j*new_width];
                
                p_temp = 0;
                for (j = 1; j < (new_height - 2); j++)
                    if (img_BW[i+j*new_width] == 0xffffffff)
                        p_temp++;
                
            }
            p_temp = 20;
        }
        
        // numero de pontos total -> p
        p=0;
        for (i = 1; i < (new_width - 2); i++)
            for (j = 0; j < (new_height - 1); j++)
                if (img_BW[i+j*new_width] == 0xffffffff)
                    p++;
        
        
        System.out.println("p=" + p);
        //            im_alinhada[2] = toolkit.createImage(new java.awt.image.MemoryImageSource(new_width, new_height, img_BW, 0, new_width));
        
/*
 
            int[] p_x = new int [p];
            int[] p_y = new int [p];
 
 */
        
        
        
        
        
        
        
        
        
        
        
/*        for (j = 5; j < (new_height - 6); j++)
        {    //erode para tira pontos deslinhados
            for (i = 5; i < (new_width - 5); i++)
            {
                if (img_BW[i+j*new_width] == 0xffffffff & img_BW[i+1+j*new_width] == 0xffffffff & img_BW[i-1+j*new_width] == 0xffffffff & img_BW[i+(j+1)*new_width] == 0xffffffff & img_BW[i+(j-1)*new_width] == 0xffffffff)
                {
 
                    img_BW_temp[i+j*new_width] = 0xffffffff;
                }
                else
                {
                    img_BW_temp[i+j*new_width] = 0xff000000;
                }
            }
        }
        for (j = 5; j < (new_height - 6); j++)for (i = 5; i < (new_width - 5); i++)img_BW[i+j*new_width] =img_BW_temp[i+j*new_width];
 */
        
        
        // y = mx+b
        for (j = 1; j < (new_height - 2); j++)
        {
            for (i = 1; i < (new_width - 2); i++)
            {
                
                
                if (img_BW[i+j*new_width] == 0xffffffff)
                {
                    sx += i;
                    sy += j;
                    sx2 += i*i;
                    sxy += i*j;
                    sy2 += j*j;
                    
                    p++;
                }
            }
        }
        recta[0] = (p*sxy-sx*sy) / (p*sx2-sx*sx);     //m --> y=mx+b
        recta[1] = sy/p-recta[0]*sx/p;     //b --> y=mx+b
        recta[2] = sxy*sxy / (sx2*sy2);
        
/*        // encontrar o R quadrado
        for (j = 5; j < (new_height - 6); j++)
        {
            for (i = 5; i < (new_width - 5); i++)
            {
 
                if (img_BW[i+j*new_width] == 0xffffffff)
                {
 
                    SSyy += (j-sy/p)*(j-sy/p);
                    SEE += (j-recta[0]*i - recta[1])*(j-recta[0]*i - recta[1]);
                }
            }
        }
        recta[2] = 1-(SEE/SSyy);  //r quadrado
 */
        System.out.println("rect ->" + String.valueOf(recta[0]) + "  " + String.valueOf(recta[1]) + "  " + String.valueOf(recta[2]));
        
        return (recta);
    }
    
    public java.awt.Image getImagemOriginal()
    {
        return im_original;
    }
    
    public Image getImage(int imgN)
    {
        if(im_alinhada == null || imgN < 0 || imgN > im_alinhada.length)
            return null;
        else
            return im_alinhada[imgN];
    }
    
    public java.awt.Image[] getImagemAlinhada(java.awt.Image img)
    {
        try
        {
            if(img == null)
                img = im_original;
            
            //if (im_alinhada != null) return (im_alinhada);
            
            img640480 = null;

            System.out.println("D2 = " + D2);           
            
            im_alinhada = new java.awt.Image[5];   // alterar depois para o numero desejado!!!!!!!!!!!
            
            int[] image_pixels = new int[area];
            int[] temp_pixels = new int[area];
            int[] img_BW = new int[area];
            int[] img_orig = new int[area];
            int acomulado = 0;
            int acomulado_max = 0;
            double[] m_horiz = new double[7];    // y=mx+b
            double[] b_horiz = new double[7];
            double[] m_vert = new double[9];     // x=my+b
            double[] b_vert = new double[9];
            long p = 0;                     //contador
            int n = 0;                     //contador
            double sx = 0;          //acomular para depois fazer as contas
            double sy = 0;
            double sx2 = 0;
            double sy2 = 0;
            double sxy = 0;
            double sup = 0;                   //limites superiores e inferiores
            double inf = 0;
            int[] pontos_x = new int[45];
            int[] pontos_y = new int[45];
            //         int[] refine_x = new int[45];
            //         int[] refine_y = new int[45];
            
            
            double peso1 =0;
            double peso2 =0;
            double peso3 =0;
            double peso4 =0;
            double dist1 =0;
            double dist2 =0;
            double dist3 =0;
            double dist4 =0;
            double dist5 =0;
            
            //         int FINE1 = 2;
            //         int FINE2 =25;
            
            java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(img,0,0,IMAGE_WIDTH,IMAGE_HEIGHT,image_pixels, 0 , IMAGE_WIDTH);
            
            pg.grabPixels();
            
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) img_orig[i+j*IMAGE_WIDTH] = image_pixels[i+j*IMAGE_WIDTH];
            
            
            for (i = 1; i < (IMAGE_WIDTH - 2); i++)
            {                   // onde varia a funcao.  out:(imagem preto e branco)
                
                for (j = 1; j < (IMAGE_HEIGHT - 1); j++)
                {
                    
                    int r = (image_pixels[i + j*IMAGE_WIDTH] & 0x00ff0000) >> 16;
                    int g = (image_pixels[i + j*IMAGE_WIDTH] & 0x0000ff00) >> 8;
                    int b = (image_pixels[i + j*IMAGE_WIDTH] & 0x000000ff);
                    
                    
                    int re = (image_pixels[i-1 + j*IMAGE_WIDTH] & 0x00ff0000) >> 16;  //valor de vermelho na esquerda do pixel central
                    int ge = (image_pixels[i-1 + j*IMAGE_WIDTH] & 0x0000ff00) >> 8;
                    int be = (image_pixels[i-1 + j*IMAGE_WIDTH] & 0x000000ff);
                    
                    int rd = (image_pixels[i + 1 + j*IMAGE_WIDTH] & 0x00ff0000) >> 16;
                    int gd = (image_pixels[i + 1 + j*IMAGE_WIDTH] & 0x0000ff00) >> 8;        //direita
                    int bd = (image_pixels[i + 1 + j*IMAGE_WIDTH] & 0x000000ff);
                    
                    int rc = (image_pixels[i + (j-1)*IMAGE_WIDTH] & 0x00ff0000) >> 16;
                    int gc = (image_pixels[i + (j-1)*IMAGE_WIDTH] & 0x0000ff00) >> 8;       // cima
                    int bc = (image_pixels[i + (j-1)*IMAGE_WIDTH] & 0x000000ff);
                    
                    int rb = (image_pixels[i + (j+1)*IMAGE_WIDTH] & 0x00ff0000) >> 16;
                    int gb = (image_pixels[i + (j+1)*IMAGE_WIDTH] & 0x0000ff00) >> 8;       // baixo
                    int bb = (image_pixels[i + (j+1)*IMAGE_WIDTH] & 0x000000ff);
                    
                    // System.out.println(String.valueOf(r) + " " + String.valueOf(g) + " " + String.valueOf(b) + " " );
                    
/*                if (getDistancia(r,g,b,re,ge,be) < D1 & getDistancia(r,g,b,rd,gd,bd) < D1 & getDistancia(r,g,b,rc,gc,bc) < D1 & getDistancia(r,g,b,rb,gb,bb) < D1 )
                {
                    temp_pixels[i + j*IMAGE_WIDTH] = temp_pixels[i] = 0xff000000;
                }
 
 */
                    if( getDistancia(r,g,b,PRETO_R1, PRETO_G1, PRETO_B1) < D2 )
                    {
                        temp_pixels[i + j*IMAGE_WIDTH] =  0xffffffff;
                        
                        if (getDistancia(r,g,b,re,ge,be) < D1 & getDistancia(r,g,b,rd,gd,bd) < D1 & getDistancia(r,g,b,rc,gc,bc) < D1 & getDistancia(r,g,b,rb,gb,bb) < D1 )
                            
                        {
                            temp_pixels[i + j*IMAGE_WIDTH] = 0xff000000;
                        }
                        
                    }
                    /*
                    else if( getDistancia(r,g,b,PRETO_R2, PRETO_G2, PRETO_B2) < D2 )
                    {
                        temp_pixels[i + j*IMAGE_WIDTH] = 0xffffffff;
                        
                        if (getDistancia(r,g,b,re,ge,be) < D1 & getDistancia(r,g,b,rd,gd,bd) < D1 & getDistancia(r,g,b,rc,gc,bc) < D1 & getDistancia(r,g,b,rb,gb,bb) < D1 )
                            
                        {
                            temp_pixels[i + j*IMAGE_WIDTH] = 0xff000000;
                        }
                        
                    }
                    */
                    else
                    {
                        temp_pixels[i + j*IMAGE_WIDTH] = 0xff000000;
                    }
                    /*
                    if(i<140 || i>500)
                    {
                        
                        if( getDistancia(r,g,b,PRETO_R2, PRETO_G2, PRETO_B2) < D2 )
                        {
                            temp_pixels[i + j*IMAGE_WIDTH] = 0xffffffff;
                            if (getDistancia(r,g,b,re,ge,be) < D4 & getDistancia(r,g,b,rd,gd,bd) < D4 & getDistancia(r,g,b,rc,gc,bc) < D4 & getDistancia(r,g,b,rb,gb,bb) < D4 )
                            {
                                temp_pixels[i + j*IMAGE_WIDTH] = 0xff000000;
                            }
                            
                            
                        }
                        
                    }
                    */
                }
            }
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) image_pixels[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            //gravar imagem preto e branco
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) img_BW[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            im_alinhada[0] = toolkit.createImage(new java.awt.image.MemoryImageSource(IMAGE_WIDTH, IMAGE_HEIGHT, img_BW, 0, IMAGE_WIDTH));
            
            
            
            for (int k = 0; k < 2; k++)
            {  //dialate x 2
                
                for (i = 2; i < (IMAGE_WIDTH - 3); i++)
                {     //dialate
                    for (j = 2; j < (IMAGE_HEIGHT - 2); j++)
                    {
                        
                        if (image_pixels[i+j*IMAGE_WIDTH] == 0xffffffff)
                        {
                            for (int alfa =-2; alfa < 3; alfa ++)
                            {
                                temp_pixels[i + (j+alfa)*IMAGE_WIDTH]= 0xffffffff;
                                temp_pixels[i + alfa + j*IMAGE_WIDTH]= 0xffffffff;
                            }
                        }
                    }
                }
                
                for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) image_pixels[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            }
            
            
            
            for (i = CROSS; i < (IMAGE_WIDTH - CROSS - 1); i++)
            {     //erode cruz
                for (j = CROSS; j < (IMAGE_HEIGHT - CROSS); j++)
                {
                    
                    acomulado = 1;
                    for (int alfa = -CROSS; alfa < (CROSS + 1); alfa++)
                    {
                        acomulado  = acomulado * image_pixels[i+alfa+j*IMAGE_WIDTH] / 0xffffffff;
                        acomulado  = acomulado * image_pixels[i+(alfa+j)*IMAGE_WIDTH] / 0xffffffff;
                    }
                    
                    if (acomulado == 1)
                    {
                        temp_pixels[i+j*IMAGE_WIDTH] = 0xffffffff;
                    }
                    else temp_pixels[i+j*IMAGE_WIDTH] = 0xff000000;
                    
                }
            }
            //apagar o resto k nao � analizado
            for (i = 0; i < (CROSS + 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) temp_pixels[i+j*IMAGE_WIDTH] = 0xff000000;
            for (i = (IMAGE_WIDTH - CROSS -2); i < (IMAGE_WIDTH  -1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) temp_pixels[i+j*IMAGE_WIDTH] = 0xff000000;
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < (CROSS + 1); j++) temp_pixels[i+j*IMAGE_WIDTH] = 0xff000000;
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = IMAGE_HEIGHT - CROSS -1; j < IMAGE_HEIGHT; j++) temp_pixels[i+j*IMAGE_WIDTH] = 0xff000000;
            //copiar temp_pixels -> image_pixels
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) image_pixels[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            
            
            //Isto é MESMO TEMP
            //copiar temp_pixels -> image_pixels
            int[] temp_px = new int[area];
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) temp_px[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            //im_alinhada[2] = toolkit.createImage(new java.awt.image.MemoryImageSource(IMAGE_WIDTH, IMAGE_HEIGHT, temp_px, 0, IMAGE_WIDTH));
            
            
            
            //rectas horizontais
            
            for (j = 54; j < (IMAGE_HEIGHT - 31); j++)
            {
                for (i = 31; i < (IMAGE_WIDTH - 32); i++)
                {
                    
                    if (image_pixels[i+j*IMAGE_WIDTH] == 0xffffffff )
                    {
                        p=0;
                        sx=0;
                        sy = 0;
                        sxy = 0;
                        sx2 = 0;
                        sup = j;
                        
                        for(int alfa = 0; alfa < 30;alfa ++)
                        {
                            for (int temp = 30; temp< (IMAGE_WIDTH -31);temp++)
                            {
                                
                                if (image_pixels[temp+(j+alfa)*IMAGE_WIDTH] == 0xffffffff)
                                {
                                    inf = j + alfa;
                                    p++;
                                    sx += temp;
                                    sy += j+alfa;
                                    sx2 += temp*temp;
                                    sxy += temp*(j+alfa);
                                }
                            }
                        }
                        if (p>45 && n<5)
                        {
                            b_horiz[n] = (sy*sx2-sx*sxy)/(p*sx2-sx*sx);
                            m_horiz[n] = (p*sxy-sx*sy) / (p*sx2-sx*sx);
                            //System.out.println(String.valueOf(n) + "  " + String.valueOf(m_horiz[n]) + "  " + String.valueOf(b_horiz[n]));
                            n++;
                            j += 42; // para nao repetir :)
                        }
                    }
                }
            }
            
            

            
                m_horiz[0]=m_horiz[1];
                b_horiz[0]=b_horiz[1];
                m_horiz[1]=m_horiz[2];
                b_horiz[1]=b_horiz[2];
            
/*            if(b_horiz[4] == 0 & m_horiz[4] == 0 )
            { // se nao forem detectadas as 5 rectas
                
                m_horiz[4] = m_horiz[3];
                b_horiz[4] = b_horiz[3];
                m_horiz[3] = m_horiz[2];
                b_horiz[3] = m_horiz[2];
            }
*/                                    
            //correccao da recta do meio
            m_horiz[2] = (m_horiz[0] + m_horiz[1] + m_horiz[3] + m_horiz[4]) /4;
            b_horiz[2] = (b_horiz[0] + b_horiz[1] + b_horiz[3] + b_horiz[4]) /4;
            
            
            
            //rectas verticais
            n=0;
            for (i = 31; i < (IMAGE_WIDTH - 32); i++)
            {
                for (j = 31; j < (IMAGE_HEIGHT - 31); j++)
                {
                    
                    if (image_pixels[i+j*IMAGE_WIDTH] == 0xffffffff )
                    {
                        
                        p=0;
                        sx=0;
                        sy = 0;
                        sxy = 0;
                        sy2 = 0;
                        sup = i;
                        for (int temp = 0; temp< 30;temp++)
                        {
                            
                            for(int alfa = 0; alfa < IMAGE_HEIGHT;alfa ++)
                            {
                                
                                if (image_pixels[i + temp + alfa*IMAGE_WIDTH] == 0xffffffff)
                                {
                                    inf = i + temp;
                                    //System.out.println(String.valueOf(j));
                                    p++;
                                    sx += i+temp;
                                    sy += alfa;
                                    sy2 += alfa*alfa;
                                    sxy += (i+temp)*alfa;
                                }
                            }
                        }
                        //System.out.println(String.valueOf(inf) + "  " + String.valueOf(sup));
                        if (p>15 && n<9)
                        {
                            //System.out.println(String.valueOf(sy));
                            b_vert[n] = (sx*sy2-sy*sxy)/(p*sy2-sy*sy);
                            m_vert[n] = (p*sxy-sy*sx) / (p*sy2-sy*sy);
                            //System.out.println(String.valueOf(n) + "  " + String.valueOf(m_vert[n]) + "  " + String.valueOf(b_vert[n]));
                            n++;
                            i += 30; // para nao repetir :)
                        }
                    }
                }
            }
            
            
            //correccao da recta do meio
            m_vert[4] = (m_vert[0] + m_vert[1] + m_vert[2] + m_vert[3] + m_vert[5] + m_vert[6] + m_vert[7] + m_vert[8]) /8;
            b_vert[4] = (b_vert[0] + b_vert[1] + b_vert[2] + b_vert[3] + b_vert[5] + b_vert[6] + b_vert[7] + b_vert[8]) /8;
            
            
            //imagem original com as rectas comentar para ficar a preto e branco com as rectas
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) temp_pixels[i+j*IMAGE_WIDTH] = img_orig[i+j*IMAGE_WIDTH];
            
            
            
            //desenhar rectas   importante.... gravar uma imagem com isto
            for (i = 0; i < (IMAGE_WIDTH - 1); i++)
            {
                for (j = 0; j < IMAGE_HEIGHT; j++)
                {
                    
                    for(int alfa = 0; alfa<5;alfa++)
                    {  //horizontais    y=mx+b
                        
                        if (j == Math.round(m_horiz[alfa]*i + b_horiz[alfa]))
                        {
                            
                            temp_pixels[i+j*IMAGE_WIDTH] = 0xff0000ff;
                        }
                    }
                    for(int alfa = 0; alfa<9;alfa++)
                    {  //verticais    x=my+b
                        if (i == Math.round(m_vert[alfa]*j + b_vert[alfa]))
                        {
                            
                            temp_pixels[i+j*IMAGE_WIDTH] = 0xff00f8ff;
                        }
                    }
                }
            }
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) image_pixels[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            
            im_alinhada[1] = toolkit.createImage(new java.awt.image.MemoryImageSource(IMAGE_WIDTH, IMAGE_HEIGHT, image_pixels, 0, IMAGE_WIDTH));
            
            
            
            //pontos de interseccao entre as rectas
            for(int alfa = 0; alfa<5;alfa++)
            {  //horizontais    y=mx+b
                
                for(int beta = 0; beta<9;beta++)
                {  //pontos de interseccao
                    
                    pontos_x[alfa*9 + beta] = (int) Math.round( (m_vert[beta]*b_horiz[alfa]+b_vert[beta])/(1-m_horiz[alfa]*m_vert[beta]) );
                    pontos_y[alfa*9 + beta] = (int) Math.round( (m_horiz[alfa]*b_vert[beta]+b_horiz[alfa])/(1-m_horiz[alfa]*m_vert[beta]) );
                    //System.out.println("->" + String.valueOf(alfa*9 + beta) + "  " + String.valueOf(pontos_x[alfa*9 + beta]) + "  " + String.valueOf(pontos_y[alfa*9 + beta]));
                }
            }
            
            
            //desenhar cruzes   uma imagem com isto :)
            for (int ni = 0;ni <45;ni++)
            {
                
                for (int alfa = -5; alfa<6;alfa++)
                {
                    temp_pixels[pontos_x[ni] + alfa +(pontos_y[ni]+alfa) * IMAGE_WIDTH ] = 0xffff0000;
                    temp_pixels[pontos_x[ni] + alfa +(pontos_y[ni]-alfa) * IMAGE_WIDTH ] = 0xffff0000;
                    
                }
                
                //System.out.println(String.valueOf(ni) + "  " + String.valueOf(refine_x[ni]) + "  " + String.valueOf(refine_y[ni]));
            }
            for (i = 0; i < (IMAGE_WIDTH - 1); i++) for (j = 0; j < IMAGE_HEIGHT; j++) image_pixels[i+j*IMAGE_WIDTH] = temp_pixels[i+j*IMAGE_WIDTH];
            
            
            //RECONTRUCAO DA IMAGEM espacamento de 50 pixeis entre rectas
            new_width = 400 + Math.round(  (640 - Math.max(pontos_x[8],pontos_x[44]))*400/640 ); //nova largura de imagem depois de ajustada
            new_height = 250;  //nova altura da imagem
            int[] new_img = new int[new_width * new_height];
            int[] new_tmp2 = new int[new_width * new_height];
            int cx = Math.round((pontos_x[8] - pontos_x[0] + pontos_x[44] - pontos_x[36])/16);  //distancias x e y entre pontos na imagem original
            int cy = Math.round((pontos_y[44] - pontos_y[8] + pontos_y[36] - pontos_y[0])/8);
            int[] rgb = new int[3];
            int g = 0;
            int b = 0;
            
            //System.out.println(String.valueOf(cx) + " " + String.valueOf(cy));
            for (int alfa=0;alfa<8;alfa++)
            {   //meio da grelha
                for(int beta=0;beta<4;beta++)
                {
                    for(i = 0; i<50; i++)
                    {
                        for(j = 0; j<50; j++)
                        {
                                dist1 = getDistancia(0,0,i,j)+0.0001;
                                dist2 = getDistancia(50,0,i,j)+0.0001;
                                dist3 = getDistancia(0,50,i,j)+0.0001;
                                dist4 = getDistancia(50,50,i,j)+0.0001;
                                dist5 = 1/dist1 + 1/dist2 + 1/dist3 + 1/dist4;
                                
                                peso1 = (1/dist1)/dist5;
                                peso2 = (1/dist2)/dist5;
                                peso3 = (1/dist3)/dist5;
                                peso4 = 1-peso1-peso2-peso3;  //para somatorio se igual a 1  //(1/dist4)/dist5;
                                
                                
                                rgb[0] = (int)Math.round(peso1*((img_orig[pontos_x[alfa+beta*9]+Math.round(i*cx/50) + (pontos_y[alfa+beta*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16)
                                + peso2*((img_orig[pontos_x[alfa+1+beta*9]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1+beta*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16)
                                + peso3*((img_orig[pontos_x[alfa+(beta+1)*9]+Math.round(i*cx/50) + (pontos_y[alfa+(beta+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x00ff0000)>> 16)
                                + peso4*((img_orig[pontos_x[alfa+1+(beta+1)*9]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1+(beta+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x00ff0000)>> 16));
                                
                                rgb[1] =(int)Math.round(peso1*((img_orig[pontos_x[alfa+beta*9]+Math.round(i*cx/50) + (pontos_y[alfa+beta*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8)
                                + peso2*((img_orig[pontos_x[alfa+1+beta*9]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1+beta*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8)
                                + peso3*((img_orig[pontos_x[alfa+(beta+1)*9]+Math.round(i*cx/50) + (pontos_y[alfa+(beta+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8)
                                + peso4*((img_orig[pontos_x[alfa+1+(beta+1)*9]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1+(beta+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8));
                                
                                rgb[2] = (int)Math.round(peso1*(img_orig[pontos_x[alfa+beta*9]+Math.round(i*cx/50) + (pontos_y[alfa+beta*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x000000ff)
                                + peso2*(img_orig[pontos_x[alfa+1+beta*9]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1+beta*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x000000ff)
                                + peso3*(img_orig[pontos_x[alfa+(beta+1)*9]+Math.round(i*cx/50) + (pontos_y[alfa+(beta+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x000000ff)
                                + peso4*(img_orig[pontos_x[alfa+1+(beta+1)*9]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1+(beta+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x000000ff));
                                
                                
                                new_img[i+50*alfa+(25+j+50*beta)*new_width] = 0xff000000 + rgb[0]*256*256 + rgb[1]*256 + rgb[2];                            
                        }
                    }
                }
            }
            
            
            for(int alfa=0; alfa<8;alfa++)
            { //parte acima e abaixo da grelha
                for (i=0;i<50;i++)
                {
                    for (j=0;j<25;j++)
                    {
                        
                        dist1 = getDistancia(0,0,i,j)+0.0001;
                        dist2 = getDistancia(50,0,i,j)+0.0001;
                        dist3 = 1/dist1 + 1/dist2;
                        
                        peso1 = (1/dist1)/dist3;
                        peso2 = 1-peso1;
                        
                        rgb[0] = (int)Math.round(peso1*((img_orig[pontos_x[alfa]+Math.round(i*cx/50) + (pontos_y[alfa]+Math.round(-j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16)
                        + peso2*((img_orig[pontos_x[alfa+1]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1]+Math.round(-j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16));
                        
                        rgb[1] =(int)Math.round(peso1*((img_orig[pontos_x[alfa]+Math.round(i*cx/50) + (pontos_y[alfa]+Math.round(-j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8)
                        + peso2*((img_orig[pontos_x[alfa+1]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1]+Math.round(-j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8));
                        
                        rgb[2] = (int)Math.round(peso1*(img_orig[pontos_x[alfa]+Math.round(i*cx/50) + (pontos_y[alfa]+Math.round(-j*cy/50))*IMAGE_WIDTH] & 0x000000ff)
                        + peso2*(img_orig[pontos_x[alfa+1]+Math.round((i-50)*cx/50) + (pontos_y[alfa+1]+Math.round(-j*cy/50))*IMAGE_WIDTH] & 0x000000ff));
                        
                        new_img[i+50*alfa+(25-j)*new_width] = 0xff000000 + rgb[0]*256*256 + rgb[1]*256 + rgb[2];
                        
                        
                        rgb[0] = (int)Math.round(peso1*((img_orig[pontos_x[alfa+36]+Math.round(i*cx/50) + (pontos_y[alfa+36]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16)
                        + peso2*((img_orig[pontos_x[alfa+37]+Math.round((i-50)*cx/50) + (pontos_y[alfa+37]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16));
                        
                        rgb[1] =(int)Math.round(peso1*((img_orig[pontos_x[alfa+36]+Math.round(i*cx/50) + (pontos_y[alfa+36]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8)
                        + peso2*((img_orig[pontos_x[alfa+37]+Math.round((i-50)*cx/50) + (pontos_y[alfa+37]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8));
                        
                        rgb[2] = (int)Math.round(peso1*(img_orig[pontos_x[alfa+36]+Math.round(i*cx/50) + (pontos_y[alfa+36]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x000000ff)
                        + peso2*(img_orig[pontos_x[alfa+37]+Math.round((i-50)*cx/50) + (pontos_y[alfa+37]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x000000ff));
                        
                        new_img[i+50*alfa+(225+j)*new_width] = 0xff000000 + rgb[0]*256*256 + rgb[1]*256 + rgb[2];
                    }
                }
            }
            
            for ( int alfa=0;alfa <4;alfa++)
            { //parte � direita da grelha
                for (i=400;i<(new_width);i++ )
                {
                    for (j=0;j<50;j++)
                    {
                        
                        dist1 = getDistancia(400,0,i,j)+0.0001;
                        dist2 = getDistancia(400,50,i,j)+0.0001;
                        dist3 = 1/dist1 + 1/dist2;
                        
                        peso1 = (1/dist1)/dist3;
                        peso2 = 1-peso1;
                        
                        rgb[0] = (int)Math.round(peso1*((img_orig[pontos_x[8+alfa*9]+Math.round((i-400)*cx/50) + (pontos_y[8+alfa*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16)
                        + peso2*((img_orig[pontos_x[8+(alfa+1)*9]+Math.round((i-400)*cx/50) + (pontos_y[8+(alfa+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x00ff0000) >> 16));
                        
                        rgb[1] =(int)Math.round(peso1*((img_orig[pontos_x[8+alfa*9]+Math.round((i-400)*cx/50) + (pontos_y[8+alfa*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8)
                        + peso2*((img_orig[pontos_x[8+(alfa+1)*9]+Math.round((i-400)*cx/50) + (pontos_y[8+(alfa+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x0000ff00) >> 8));
                        
                        rgb[2] = (int)Math.round(peso1*(img_orig[pontos_x[8+alfa*9]+Math.round((i-400)*cx/50) + (pontos_y[8+alfa*9]+Math.round(j*cy/50))*IMAGE_WIDTH] & 0x000000ff)
                        + peso2*(img_orig[pontos_x[8+(alfa+1)*9]+Math.round((i-400)*cx/50) + (pontos_y[8+(alfa+1)*9]+Math.round((j-50)*cy/50))*IMAGE_WIDTH] & 0x000000ff));
                        
                        new_img[i+(j+25+alfa*50)*new_width] = 0xff000000 + rgb[0]*256*256 + rgb[1]*256 + rgb[2];
                        
                        
                    }
                }
            }
            for (i=400;i<new_width;i++)
            { //dois cantos k restam ( canto superior direito e canto inferior direito
                for (j=0;j<25;j++)
                {
                    
                    new_img[i+(25-j)*new_width] = img_orig[pontos_x[8]+Math.round((i-400)*cx/50) + (pontos_y[8]+Math.round(-j*cy/50))*IMAGE_WIDTH];
                    new_img[i+(j+225)*new_width] = img_orig[pontos_x[44]+Math.round((i-400)*cx/50) + (pontos_y[44]+Math.round(j*cy/50))*IMAGE_WIDTH];
                }
            }
            for (i = 0; i < (new_width - 1); i++) for (j = 0; j < new_height; j++) new_tmp2[i+j*new_width] = new_img[i+j*new_width];
            
            im_alinhada[2] = toolkit.createImage(new java.awt.image.MemoryImageSource(new_width, new_height, new_tmp2, 0, new_width));
            
            
            int[] new_tmp = new int[new_width*new_height];
            for (i = 1; i < (new_width - 2); i++)
            {                   // onde varia a funcao.  out:(imagem preto e branco)
                
                for (j = 1; j < (new_height - 1); j++)
                {
                    
                    int r1 = (new_img[i + j*new_width] & 0x00ff0000) >> 16;
                    int g1 = (new_img[i + j*new_width] & 0x0000ff00) >> 8;
                    int b1 = (new_img[i + j*new_width] & 0x000000ff);
                    
                    if (getDistancia(0,0,b1,0,0,AZUL_B) < AZUL_RAIO)
                    {
                        
                        // secalhar � melhor calcular logo tudo aqui dentro ou entao fazer dialate e depois erode para "tapar" buracos do feixe
                        new_img[i+j*new_width] = 0xffffffff;
                    }
                    else new_img[i+j*new_width] = 0xff000000;
                    
                }
            }
            //for (i = 0; i < (new_width - 1); i++) for (j = 0; j < new_height; j++) image_pixels[i+j*new_width] = temp_pixels[i+j*new_width];
            
/*
        for (int k = 0; k < 3; k++){  //dialate x 2
 
            for (i = 4; i < (new_width - 5); i++){     //dialate
                for (j = 4; j < (new_height - 4); j++){
 
                    if (new_img[i+j*new_width] == 0xffffffff){
                        for (int alfa =-2; alfa < 3; alfa ++){
                            new_tmp[i + (j+alfa)*new_width]= 0xffffffff;
                            new_tmp[i + alfa + j*new_width]= 0xffffffff;
                        }
                    }
                }
            }
 
            for (i = 0; i < (new_width - 1); i++) for (j = 0; j < new_height; j++) new_img[i+j*new_width] = new_tmp[i+j*new_width];
        }
 
 
        for (int k = 0; k < 8; k++){  //erode x 3
 
            for (i = 4; i < (new_width - 5); i++){     //dialate
                for (j = 4; j < (new_height - 4); j++){
 
                    if (new_img[i+j*new_width] == 0xffffffff & new_img[i+1+j*new_width] == 0xffffffff & new_img[i-1+j*new_width] == 0xffffffff & new_img[i+(j+1)*new_width] == 0xffffffff & new_img[i+(j-1)*new_width] == 0xffffffff){
                        new_tmp[i+j*new_width] = 0xffffffff;
                    }
                    else new_tmp[i+j*new_width] = 0xff000000;
                }
            }
 
            for (i = 0; i < (new_width - 1); i++) for (j = 0; j < new_height; j++) new_img[i+j*new_width] = new_tmp[i+j*new_width];
        }
 */
            
            
            
            
            im_alinhada[3] = toolkit.createImage(new java.awt.image.MemoryImageSource(new_width, new_height, new_img, 0, new_width));
            
            
            return (im_alinhada);
        }
        catch(Exception e)
        {
            //e.printStackTrace();
            im_alinhada = null;
        }
        return null;
    }
    
    
    public double[] getDistanciasReferencia()
    { //distancias reais por pixel
        
        distancias_referencia[0] = 1.0 / 50;    //a imagem tem uma definicao de 400 x 250 com 50 pixeis a representarem 1 cm
        distancias_referencia[1] = 1.0 / 50;
        
        return (distancias_referencia);
    }
    
    
    public boolean getIsUp()
    {    //parametro de entrada: imagem depois do erode
        return (isUp);
    }
    
    public void setIsUp(boolean isUp)
    {
        this.isUp = isUp;
    }
    
    
    public java.awt.Image getFinalimage(java.awt.Image img)
    {
        if(img == null)
            return null;
        
        if (img640480 != null)
        {
            return (img640480);
        }
        
        im_temp = img;
        int[] temp_pixels = new int[new_width*new_height];
        int[] image_pixels = new int[area];
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(img,0,0,new_width,new_height,temp_pixels, 0 , new_width);
        
        try
        {pg.grabPixels();}
        catch(InterruptedException e)
        { e.printStackTrace();}
        
        java.awt.image.BufferedImage bImage = null;
        
        int newheight = 230;
        
        //por a imagem em cima ou em baixo para que se veja a circunferencia
        if(getIsUp())
        {
            for (i= 0; i < IMAGE_WIDTH;i++)
            {
                for (j=0; j < IMAGE_HEIGHT;j++)
                {
                    image_pixels[i + j*IMAGE_WIDTH] = 0xff000000;
                    
                    if ( i< new_width & j>(newheight-1))
                    {
                        image_pixels [i + j*IMAGE_WIDTH] = temp_pixels[i+(j-newheight)*new_width];
                    }
                    
                }
            }
            img640480 = toolkit.createImage(new java.awt.image.MemoryImageSource(IMAGE_WIDTH, IMAGE_HEIGHT, image_pixels, 0, IMAGE_WIDTH));
            int imageWidth = img640480.getWidth(null);
            int imageHeight = img640480.getHeight(null);
            bImage = new java.awt.image.BufferedImage(imageWidth,imageHeight,java.awt.image.BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g2d = bImage.createGraphics();
            g2d.drawImage(img640480, 0, 0, null);
            g2d.setColor(Color.RED);
            g2d.drawOval((int)Math.round(circunferencia[0]-circunferencia[2]), (int)Math.round(circunferencia[1]-circunferencia[2]+newheight), (int)Math.round(2*circunferencia[2]), (int)Math.round(2*circunferencia[2]));
            g2d.fillOval((int)Math.round(circunferencia[0]), (int)Math.round(circunferencia[1]+newheight), 3, 3);
            g2d.setColor(Color.CYAN);
            int x0 = 50;
            int y0 = newheight - 40;
            g2d.drawLine(x0, y0, x0 + 50, y0);
            g2d.drawLine(x0, y0 - 5, x0, y0 + 5);
            g2d.drawLine(x0 + 50, y0 - 5, x0 + 50, y0 + 5);
            g2d.drawString("1cm = 50px", x0 + 5, y0 + 20);
            g2d.dispose();
        }
        else
        {
            for (i= 0; i < IMAGE_WIDTH;i++)
            {
                for (j=0; j < IMAGE_HEIGHT;j++)
                {
                    image_pixels[i + j*IMAGE_WIDTH] = 0xff000000;
                    if ( i< new_width & j<250)
                    {
                        image_pixels [i + j*IMAGE_WIDTH] = temp_pixels[i+j*new_width];
                    }
                    
                }
            }
            img640480 = toolkit.createImage(new java.awt.image.MemoryImageSource(IMAGE_WIDTH, IMAGE_HEIGHT, image_pixels, 0, IMAGE_WIDTH));
            int imageWidth = img640480.getWidth(null);
            int imageHeight = img640480.getHeight(null);
            bImage = new java.awt.image.BufferedImage(imageWidth,imageHeight,java.awt.image.BufferedImage.TYPE_INT_RGB);
            java.awt.Graphics2D g2d = bImage.createGraphics();
            g2d.drawImage(img640480, 0, 0, null);
            g2d.setColor(Color.RED);
            g2d.drawOval((int)Math.round(circunferencia[0]-circunferencia[2]), (int)Math.round(circunferencia[1]-circunferencia[2]), (int)Math.round(2*circunferencia[2]), (int)Math.round(2*circunferencia[2]));
            g2d.fillOval((int)Math.round(circunferencia[0]), (int)Math.round(circunferencia[1]), 3, 3);
            
            g2d.setColor(Color.CYAN);
            int x0 = 50;
            int y0 = 300;
            g2d.drawLine(x0, y0, x0 + 50, y0);
            g2d.drawLine(x0, y0 - 5, x0, y0 + 5);
            g2d.drawLine(x0 + 50, y0 - 5, x0 + 50, y0 + 5);
            g2d.drawString("1cm = 50px", x0 + 5, y0 + 20);
            g2d.dispose();
        }
        
        img640480 = bImage;
        
        return (bImage);
    }
    
    public java.awt.Image getOriginalImage()
    {
        return (im_original);
    }
    
    
    public java.awt.Image convertTo640480(java.awt.Image img)
    {
        
        im_temp = img.getScaledInstance(640, 480, img.SCALE_SMOOTH);
        
        return (im_temp);
    }
    
    public java.awt.Image convertTo320240(java.awt.Image img)
    {
        
        im_temp = img.getScaledInstance(320, 240, img.SCALE_SMOOTH);
        
        return (im_temp);
        
    }
    
    public java.awt.Image convertToXY(java.awt.Image img, int x, int y)
    {
        
        if(x*y<25) return (null);
        
        im_temp = img.getScaledInstance(x, y, img.SCALE_SMOOTH);
        
        return (im_temp);
    }
    
    private double getDistancia(int x1, int y1, int z1, int x2, int y2, int z2)
    {
        
        return (java.lang.Math.sqrt((double)((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2)+(z1-z2)*(z1-z2))));
    }
    
    private double getDistancia(int x1, int y1, int x2, int y2)
    {
        
        return (java.lang.Math.sqrt((double)((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))));
    }
    
    private double getDistancia(double x1, double y1, double x2, double y2)
    {
        
        return (java.lang.Math.sqrt((double)((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2))));
    }
}
