/*
 * ImageAnalyser.java
 *
 * Created on 23 de Junho de 2003, 14:17
 */

package pt.utl.ist.elab.driver.Aleatorio.Utils;

/**
 *
 * @author  PC
 */
public class ImageAnalyser_old extends javax.swing.JPanel implements java.lang.Cloneable {
    
    /** Creates a new instance of ImageAnalyser */
    public ImageAnalyser_old() {
    }
    
    private java.awt.Image image = null;    //stores the image to display
    private java.awt.Image originalImage = null;    //stores the original image
    private int[] originalPixels;
    private java.awt.Image BWImage = null;  //stores the B&W image
    private int[] BWPixels;
    private java.awt.Image edgesImage = null;   //stores the image with the edges detected
    private int[] edgesPixels;
    private java.awt.Image houghImage = null;   //stores the image of the hough transform
    private int[] houghPixels;
    private java.awt.Image houghCountImage = null;  //stores the image after counting from the hough transform image
    private int[] houghCountPixels;
    private java.awt.Image convolutionImage = null; //stores the image of the convolution transform
    
    private java.awt.Image convolutionCountImage = null;    //stores the image after counting from the convolution transform image
    private java.awt.Image fullCountImage = null;   //stores the image after compiling both the counting algorithms
    private int[] fullCountPixels;
    private int[][] refineCenters;
    
    private String currentImageType = null; //keeps track of which image is in the variable image
    public int imageWidth;
    public int imageHeight;
    private int area;
    
    private int maxSizeOfArray = 1024;  //just to keep a good slack
    private int houghCircles[][] = new int[maxSizeOfArray][2];
    private int convolutionCircles[][] = new int[maxSizeOfArray][2];
    private int fullCircles[][] = new int[maxSizeOfArray][2];
    private int houghCenterCounter;  //stores the count of the number of spots detected
    private int convolutionCenterCounter;  //stores the count of the number of spots detected
    private int center_counter;  //stores the full ammount of centers found
    private int cluster_counter; //stores the count of the number of dice detected
    private double[] info;
    private int centers[][];
    private int clusterCenters[][];
    private java.util.Vector vClusters;
    
    private int BWThreshold;
    private int radius;
    private int houghThreshold1;
    private int houghThreshold2;
    private int houghThreshold3;
    private int convolutionThreshold;
    private int maxClusterSize;
    private int maxDiceCount;
    
    public ImageAnalyser_old(java.awt.Image image)
    {
        this.originalImage = image;
        setImage(image,"original");
        imageWidth = originalImage.getWidth(this);
        imageHeight = originalImage.getHeight(this);
        area = imageWidth * imageHeight;
    }
    
    /**
     *sets the parameters that will be used by the algorithms
     */
    public void setParams(int BWThreshold, int radius, int houghThreshold1, int houghThreshold2, int houghThreshold3, int convolutionThreshold, int maxClusterSize, int maxDiceCount)
    {
        this.BWThreshold = BWThreshold;
        this.radius = radius;
        this.houghThreshold1 = houghThreshold1;
        this.houghThreshold2 = houghThreshold2;
        this.houghThreshold3 = houghThreshold3;
        this.convolutionThreshold = convolutionThreshold;
        this.maxClusterSize = maxClusterSize;
        this.maxDiceCount = maxDiceCount;
        
        //System.out.println("Configuring Image Analyser with values:\nBWThreshold:"+BWThreshold+"\nRadius:"+radius+"\nhoughThresh1:"+houghThreshold1+"\nhoughThresh2:"+houghThreshold2+"\nhoughThresh3:"+houghThreshold3+"\nConvThreshold:"+convolutionThreshold+"\nmaxClusterSize:"+maxClusterSize+"\nMaxDiceCount:"+maxDiceCount);
        
        /*javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
        imagemDialog.setTitle("Imagem Capturada");
        ImagePanel imagemCapturadaPanel = new ImagePanel(originalImage);
        imagemDialog.getContentPane().add(imagemCapturadaPanel);
        int[] dimIm = imagemCapturadaPanel.imageSize();
        imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
        //imagemDialog.pack();
        imagemDialog.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        imagemDialog.show();
        imagemDialog.repaint();*/
        
    }//setParams(int BWThreshold, int radius, int houghThreshold1, int hoguhThreshold2, int convolutionThreshold)
    
    public void resetImages()
    {
        BWImage = null;
        BWPixels = null;
        edgesImage = null;
        edgesPixels = null;
        houghImage = null;
        houghPixels = null;
        houghCountImage = null;
        houghCountPixels = null;
        convolutionImage = null;
        convolutionCountImage = null;
        fullCountImage = null;
        fullCountPixels = null;
    }//resetImages
    
    public java.awt.Image conversionBW()
    {
        int imageWidth = originalImage.getWidth(this);
        int imageHeight = originalImage.getHeight(this);
        int area = imageWidth * imageHeight;
        
        
        int inPixels[] = new int[area];
        int outPixels[] = new int[area];
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(originalImage, 0, 0, imageWidth, imageHeight, inPixels, 0, imageWidth);
        try {pg.grabPixels();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        for (int index = 0; index < inPixels.length; index++)
        {
            int r = (inPixels[index] & 0x00ff0000) >> 16;   //retrieving RGB components
            int g = (inPixels[index] & 0x0000ff00) >> 8;
            int b = (inPixels[index] & 0x000000ff);
            if ((r + g + b)/3 > BWThreshold)      //comparing the grayscale value to the threshold
                outPixels[index] = 0xffffffff;  //and building the intArray with the B&W values
            else
                outPixels[index] = 0xff000000;
        }
        
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,outPixels,0,imageWidth);
        BWImage = createImage(mis);
        //this might not be necessary (it's just to wait untill the image is in the variable BWImage)
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(BWImage,0);
        try {tracker.waitForAll();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        setImage(BWImage,"BW");
        
        /*javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
        imagemDialog.setTitle("BW Image");
        ImagePanel imagemCapturadaPanel = new ImagePanel(BWImage);
        imagemDialog.getContentPane().add(imagemCapturadaPanel);
        int[] dimIm = imagemCapturadaPanel.imageSize();
        imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
        //imagemDialog.pack();
        imagemDialog.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        imagemDialog.show();
        imagemDialog.repaint();
         */
        
        BWPixels = outPixels;
        return BWImage;
    }//conversionBW(java.awt.Image image, int threshold)
    
    public java.awt.Image edgeDetector()
    {
        int imageWidth=originalImage.getWidth(this);
        int imageHeight=originalImage.getHeight(this);
        
        java.awt.image.BufferedImage inputImage = new java.awt.image.BufferedImage(imageWidth, imageHeight, java.awt.image.BufferedImage.TYPE_INT_RGB);
        java.awt.image.BufferedImage outputImage = new java.awt.image.BufferedImage(imageWidth, imageHeight, java.awt.image.BufferedImage.TYPE_INT_RGB);
        
        java.awt.Graphics2D inputContext = inputImage.createGraphics();
        
        inputContext.drawImage(BWImage, 0, 0, imageWidth, imageHeight, this);
        
        float[] elements = {  0.0f, -3.0f, 0.0f,    //build the filter
                            -3.0f, 12.0f, -3.0f,
                            0.0f, -3.0f, 0.0f };
        java.awt.image.Kernel kernel = new java.awt.image.Kernel(3, 3, elements);
        java.awt.image.ConvolveOp cop = new java.awt.image.ConvolveOp(kernel, java.awt.image.ConvolveOp.EDGE_NO_OP, null );
        
        cop.filter(inputImage, outputImage);    //apply the filter
        edgesImage = (java.awt.Image)outputImage;
        setImage(edgesImage,"edges");
        return edgesImage;
    }//edgeDetector(java.awt.Image image)
    
    public java.awt.Image houghTransform()
    {
        int imageWidth = originalImage.getWidth(this);
        int imageHeight = originalImage.getHeight(this);
        int area = imageWidth * imageHeight;
        int inPixels[] = new int[area];
        int outPixels[] = new int[area];
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(edgesImage, 0, 0, imageWidth, imageHeight, inPixels, 0, imageWidth);
        try
        {
            pg.grabPixels();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        for (int index = 0; index < inPixels.length; index++)
            outPixels[index] = 0xff000000;
        
        for (int index = 0; index < inPixels.length; index++)
        {
            //Se estamos num pixel que e contorno, entao desenha um circulo
            //de raio 'raio' em torno desse pixel, na imagem de output
            if (inPixels[index] == 0xffffffff)
            {   
                int x = index % imageWidth;
                int y = index / imageWidth;
                
                for (int rIndex = 0; rIndex <= radius; rIndex++)
                {
                    try {outPixels[(y - rIndex) * imageWidth  + x - (radius - rIndex)] += 0xff0a0a0a;}
                    catch (ArrayIndexOutOfBoundsException e){}                
                    try {outPixels[(y + rIndex) * imageWidth + x - (radius - rIndex)] += 0xff0a0a0a;}
                    catch (ArrayIndexOutOfBoundsException e){}
                    try {outPixels[(y - rIndex) * imageWidth + x + (radius - rIndex)] += 0xff0a0a0a;}
                    catch (ArrayIndexOutOfBoundsException e){}
                    try {outPixels[(y + rIndex) * imageWidth + x + (radius - rIndex)] += 0xff0a0a0a;}
                    catch (ArrayIndexOutOfBoundsException e){}
                }                                  
            }            
        }
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,outPixels,0,imageWidth);
        houghImage = createImage(mis);
        
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(houghImage,0);
        try {tracker.waitForAll();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        setImage(houghImage,"hough");
        
        /*javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
        imagemDialog.setTitle("Hough Image");
        ImagePanel imagemCapturadaPanel = new ImagePanel(houghImage);
        imagemDialog.getContentPane().add(imagemCapturadaPanel);
        int[] dimIm = imagemCapturadaPanel.imageSize();
        imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
        //imagemDialog.pack();
        imagemDialog.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        imagemDialog.show();
        imagemDialog.repaint();
        */
        houghPixels = outPixels;
        return houghImage;

    }//houghTransform(java.awt.Image image, int radius)
    
    public java.awt.Image houghCount()
    {
        houghCenterCounter = 0;        //reinicializa os contadores
        
        //int circles[][] = new int[maxSizeOfArray][2];
        info = new double[maxSizeOfArray];
        
        if (originalPixels == null)
        {
            originalPixels = new int[area];
            java.awt.image.PixelGrabber pg1 = new java.awt.image.PixelGrabber(originalImage, 0, 0, imageWidth, imageHeight, originalPixels, 0, imageWidth);        
            try {pg1.grabPixels();}
            catch(InterruptedException e)
            {
                e.printStackTrace();
                System.exit(1);
            }//catch
        }//if
        
        int[] outPixels = new int[area];        
        outPixels = originalPixels;
        
        if (houghPixels == null)
        {
            houghPixels = new int[area];
            java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(houghImage, 0, 0, imageWidth, imageHeight, houghPixels, 0, imageWidth);
            try {pg.grabPixels();}
            catch(InterruptedException e)
            {
                e.printStackTrace();
                System.exit(1);
            }//catch
        }//if
        
        int valor, x, y;
        double media;
        boolean presente;
        
        for(int index = 0; index < originalPixels.length; index++)
        {
            valor = houghPixels[index] & 0x000000ff;    //get the first 8 bits (the other are equal)
            media = 0.;
            presente = false;
            if (valor >= houghThreshold1) {
                try {
                    //media = (double)((houghPixels[index] &0x000000ff) + (houghPixels[index+imageWidth] &0x000000ff) + (houghPixels[index+1] &0x000000ff) + (houghPixels[index+imageWidth+1] &0x000000ff))/4.;
                    media = (double)((houghPixels[index] &0x000000ff) + (houghPixels[index-imageWidth-1] &0x000000ff) + (houghPixels[index-imageWidth+1] &0x000000ff) + (houghPixels[index+imageWidth-1] &0x000000ff) + (houghPixels[index+imageWidth+1] &0x000000ff))/5.;
                    media = max(media, (double)((houghPixels[index] &0x000000ff) + (houghPixels[index-imageWidth] &0x000000ff) + (houghPixels[index-1] &0x000000ff) + (houghPixels[index+1] &0x000000ff) + (houghPixels[index+imageWidth] &0x000000ff))/5.);
                    media = max(media, (double)((houghPixels[index] &0x000000ff) + (houghPixels[index-imageWidth+1] &0x000000ff) + (houghPixels[index+imageWidth] &0x000000ff) + (houghPixels[index+1] &0x000000ff) + (houghPixels[index+imageWidth] &0x000000ff))/4.);
                }catch (ArrayIndexOutOfBoundsException e){media = (double)houghPixels[index];}
                
                if (media >= houghThreshold2) {
                    x = index % imageWidth;
                    y = index / imageWidth;
                    presente = false;   //check if this center has already been found and if "media" is greater than that which has been found
                    for(int indexa = 0; indexa <= center_counter; indexa++) {
                        if (Math.abs(houghCircles[indexa][0] - x) < Math.ceil((double)radius/2.) && Math.abs(houghCircles[indexa][1] - y) < Math.ceil((double)radius/2.)) {
                            if (info[indexa] <= media && (BWPixels[index] & 0x000000ff) != 255) {
                                //System.out.println("Overwriting " + circulos[indexa][0] + ";" + circulos[indexa][1] + " m:" + info[indexa]  + " with " + x + ";" + y + " m:" + media);
                                houghCircles[indexa][0] = x;
                                houghCircles[indexa][1] = y;
                                info[indexa] = media;
                            }
                            presente = true;
                            break;
                        }//if
                        else if(Math.abs(houghCircles[indexa][0] - x) <= radius-1 && Math.abs(houghCircles[indexa][1] - y) <= radius-1)
                        {
                            presente = true;
                            break;
                        }//else_if
                    }
                    int whiteCounter = 0;
                    if ((BWPixels[index-imageWidth-1] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index-imageWidth] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index-imageWidth+1] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index-1] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index+1] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index+imageWidth-1] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index+imageWidth] & 0x000000ff) == 255) whiteCounter++;
                    if ((BWPixels[index+imageWidth+1] & 0x000000ff) == 255) whiteCounter++;
                    
                    boolean whiteEdgeCheck = whiteCounter>2?false:true;
                    
                    if (!presente && (whiteEdgeCheck || media > houghThreshold3))
                    {
                        //System.out.println("Adding " + x + ";" + y + " Media:" + media);
                        houghCircles[center_counter][0] = x;
                        houghCircles[center_counter][1] = y;
                        info[center_counter] = media;
                        center_counter++;
                    }//if
                }//if
            }//if
        }//for_index
        
        int indice;
        for(int index = 0; index < center_counter; index++) {
            indice = houghCircles[index][1] * imageWidth + houghCircles[index][0];
            outPixels[indice] = 0xffff00ff;
            try {
                outPixels[indice - imageWidth - 1] = 0xffff00ff;
            }catch (ArrayIndexOutOfBoundsException e){}
            try {
                outPixels[indice - imageWidth + 1] = 0xffff00ff;
            }catch (ArrayIndexOutOfBoundsException e){}
            try {
                outPixels[indice + imageWidth - 1] = 0xffff00ff;
            }catch (ArrayIndexOutOfBoundsException e){}
            try {
                outPixels[indice + imageWidth + 1] = 0xffff00ff;
            }catch (ArrayIndexOutOfBoundsException e){}
        }
        //Builds the final image
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,outPixels,0,imageWidth);
        java.awt.Image outImage = createImage(mis);
        
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(outImage,0);
        try {
            tracker.waitForAll();
        }catch(InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        houghCountImage = outImage;
        houghCenterCounter = center_counter;
        //houghCountImage = buildImage(houghCircles, houghCenterCounter);
        setImage(houghCountImage,"houghCount");
        /*
        javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
        imagemDialog.setTitle("Imagem houghCount");
        ImagePanel imagemCapturadaPanel = new ImagePanel(houghCountImage);
        imagemDialog.getContentPane().add(imagemCapturadaPanel);
        int[] dimIm = imagemCapturadaPanel.imageSize();
        imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
        //imagemDialog.pack();
        imagemDialog.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        imagemDialog.show();
        imagemDialog.repaint();
        */
        countClusters(houghCircles);
        //System.out.println("hough cluster_counter:"+cluster_counter);
        //System.out.println("hough center_counter:"+center_counter);
        //System.out.println("Info.length:"+info.length);
        
        return houghCountImage;

    }//houghCount()
    
    public java.awt.Image convolutionTransform()
    {
        int inPixels[] = new int[area];
        int outPixels[] = new int[area];
        
        //setting up mask according to the radius specified
        int maskWidth = radius*2, maskHeight = radius*2, maskArea = maskWidth * maskHeight;
        
        float[] mask = new float[maskArea];
        int x, y;
        double theta;
        for(int angleIndex = 0; angleIndex < 90; angleIndex+=2)
        {
            theta = (double)angleIndex * java.lang.Math.PI / 180;
            x = (int)(radius * (double)(java.lang.Math.cos(theta) + 1));
            y = (int)(radius * (double)(-java.lang.Math.sin(theta) + 1));
            mask[x + y * maskWidth] = 1.0f;            
        }
        
        int indexShift = 0;
        for (int maskIndex = 0; maskIndex < maskWidth; maskIndex++)
            if (mask[maskIndex] != 0)
            {
                indexShift = maskIndex;
                break;
            }
        
        float maskSum = 0;
        for (int maskIndex = 0; maskIndex < maskArea; maskIndex++) maskSum += mask[maskIndex];
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(edgesImage, 0, 0, imageWidth, imageHeight, inPixels, 0, imageWidth);
        try {pg.grabPixels();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        //removing the border of the whole image
        for(int widthIndex = 0; widthIndex < imageWidth; widthIndex++)
        {
            inPixels[widthIndex] = 0x00000000;
            inPixels[widthIndex + (imageHeight - 1)* imageWidth] = 0x00000000;
        }
        for(int heightIndex = 0; heightIndex < imageHeight; heightIndex++)
        {
            inPixels[heightIndex * imageWidth] = 0x00000000;
            inPixels[(heightIndex + 1) * imageWidth -1] = 0x00000000;
        }
        
        float convResult;
        int pixelValue = 0;
        //Performing the convolution, at last!
        for (int imageIndex = 0; imageIndex < area; imageIndex++)
        {
            if (imageIndex < maskHeight/2 * imageWidth + maskWidth/2)
                outPixels[imageIndex] = 0xff000000;
                        
            convResult = 0;
            
            try {pixelValue = inPixels[imageIndex + indexShift] & 0x00ffffff;}
            catch(ArrayIndexOutOfBoundsException e){}
            
            if(pixelValue > 0)
                for (int maskIndex = 0; maskIndex < maskArea; maskIndex++)                
                    try {convResult += mask[maskIndex] * (inPixels[imageIndex + (maskIndex / maskWidth * imageWidth) + maskIndex % maskWidth] & 0x000000ff);}
                    catch(ArrayIndexOutOfBoundsException e){}
            
            convResult = convResult / maskSum;            
            if(imageHeight - imageIndex / imageWidth > maskHeight)
                try {outPixels[imageIndex + (maskHeight >> 1) * imageWidth + (maskWidth >> 1)] = 0xff000000 + /*((int)convResult << 16) + ((int)convResult << 8) + */(int)convResult;}
                catch(ArrayIndexOutOfBoundsException e){}
        }
        
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,outPixels,0,imageWidth);
        convolutionImage = createImage(mis);
        
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(convolutionImage,0);
        try {tracker.waitForAll();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        setImage(convolutionImage,"convolution");
        return convolutionImage;

    }//convolutionTransform()
    
    public java.awt.Image convolutionCount()
    {
        int convPixels[] = new int[area];
        
        int circles[][] = new int[maxSizeOfArray][2];
        info = new double[maxSizeOfArray];
        
        convolutionCenterCounter = 0;
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(convolutionImage, 0, 0, imageWidth, imageHeight, convPixels, 0, imageWidth);
        try {pg.grabPixels();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        boolean presente = false;
        
        for (int imageIndex = 0; imageIndex < area; imageIndex++)
        {
            int valor = convPixels[imageIndex] & 0x000000ff;
            if(valor > convolutionThreshold)
            {
                int x = imageIndex % imageWidth;
                int y = imageIndex / imageWidth;
                presente = false;
                for(int indexa = 0; indexa < convolutionCenterCounter; indexa++)
                    if (Math.abs(circles[indexa][0] - x) < radius && Math.abs(circles[indexa][1] - y) < radius)
                    {
                        if (info[indexa] < valor)
                        {
                            circles[indexa][0] = x;
                            circles[indexa][1] = y;
                        }
                        presente = true;
                    }
                if (!presente)
                {
                    circles[convolutionCenterCounter][0] = x;
                    circles[convolutionCenterCounter][1] = y;
                    info[convolutionCenterCounter] = valor;                    
                    convolutionCenterCounter++;                        
                }                
            }
        }
        convolutionCountImage = buildImage(circles, convolutionCenterCounter);
        setImage(convolutionCountImage,"convolutionCount");
        convolutionCircles = circles;
        countClusters(convolutionCircles);
        return convolutionCountImage;
    }//convolutionCount(java.awt.Image image, int threshold, int radius)
    
    
    /**
     *Performs all the counting routines and merges the results
     *Assumes that the image has already been transformed
     */
    public java.awt.Image fullCount()
    {
        houghCount();
        convolutionCount();
        int circlesCounter = 0;
        int circles[][] = new int[houghCircles.length + convolutionCircles.length][2];
        
        double minDistance = 0;
        double distance;
        int closestIndex = -1;
        int foundConvIndex[] = new int[convolutionCircles.length];
        int foundConvIndexCounter = 0;
        boolean found = false;
        
        for (int houghIndex = 0; houghIndex < houghCircles.length; houghIndex++)
        {
            found = false;
            minDistance = 0;
            int houghX = houghCircles[houghIndex][0];
            int houghY = houghCircles[houghIndex][1];
            
            for (int convIndex = 0; convIndex < convolutionCircles.length; convIndex++)
            {
                int convX = convolutionCircles[convIndex][0];
                int convY = convolutionCircles[convIndex][1];
                if( (distance = java.lang.Math.sqrt((double)(houghX-convX)*(houghX-convX) + (double)(houghY-convY)*(houghY-convY))) < radius)
                    if (distance < minDistance)
                    {
                        minDistance = distance;
                        closestIndex = convIndex;
                        found = true;
                    }
            }
            
            if (found)  //if found, take the average of the two
            {
                circles[circlesCounter][0] = (houghX - convolutionCircles[closestIndex][0])/2;
                circles[circlesCounter][1] = (houghY - convolutionCircles[closestIndex][1])/2;
                foundConvIndex[foundConvIndexCounter] = closestIndex;
                foundConvIndexCounter++;
                circlesCounter++;
            }else   //else store hough
            {
                circles[circlesCounter][0] = houghX;
                circles[circlesCounter][1] = houghY;
                circlesCounter++;
            }
        }
        
        //Add the circles found with the convolution algorithm
        for(int index = 0; index < foundConvIndex.length; index++)
        {
            found = false;
            for(int convIndex = 0; convIndex < foundConvIndex.length; convIndex++)
                if (foundConvIndex[convIndex] == index)
                    found = true;
            
            if (!found)
            {
                circles[circlesCounter][0] = convolutionCircles[index][0];
                circles[circlesCounter][1] = convolutionCircles[index][1];
                circlesCounter++;
            }
        }
        fullCircles = circles;
    
        center_counter = circlesCounter + 1;
        countClusters(fullCircles);
        return buildImage(circles, circlesCounter);
    }//fullCount()
    
    
    public java.awt.Image refineCount()
    {
        refineCenters = houghCircles;
        center_counter = houghCenterCounter;
        
        int x, y;
        
        //System.out.println("Info.length:"+info.length);
        //checking if we have any non spots and remove them
        //System.out.println("First Check for non spots!");
        
        for (int i = 0; i < center_counter; i++)
        {
            int pixelIndex = refineCenters[i][0] + refineCenters[i][1] * imageWidth;
            boolean isItSpot = ((Boolean)isSpot(pixelIndex, radius, true, false).get(0)).booleanValue();
            if (!isItSpot)
            {
                //System.out.println("Removing spot " + i + " -> ("+houghCircles[i][0]+";"+houghCircles[i][1]+")");
                refineCenters = removeArrayEntry(refineCenters, i, 0);
                info = removeArrayEntry(info, i);
                center_counter--;
                i--;
            }//if
            else    //check if the spot is in white and, if so, moves it to the nearest black pixel
            {
                if ((BWPixels[pixelIndex] & 0x000000ff)==255)
                {
                    int[] changedCircle = getNearestBlackPixel(pixelIndex, radius);
                    //System.out.println("Changing spot in white from ("+houghCircles[i][0]+","+houghCircles[i][1]+") to ("+changedCircle[0]+","+changedCircle[1]+")");
                    refineCenters[i][0] = changedCircle[0];
                    refineCenters[i][1] = changedCircle[1];
                }//if
            }//else
        }//for_i
        
        
        //System.out.println("Clustering...");
        //Counting the number of Dice by clustering
        int currentX, currentY;
        int maxDice = 1024;
        vClusters = new java.util.Vector(maxDice);     //is a vector of vectors
        java.util.Vector[] vDie = new java.util.Vector[maxDice];      //is an array of vectors of ints representing the spot numbers. the last entry of each Vector says if the Die is ok or not
        int vDieSize;
        boolean belongsToDie = true;
        boolean belongsToClusters = true;
        
        //create clusters
        for(int centerIndex = 0; centerIndex < center_counter; centerIndex++) {
            currentX = refineCenters[centerIndex][0];//houghCircles[centerIndex][0];
            currentY = refineCenters[centerIndex][1];//houghCircles[centerIndex][1];
            
            if (centerIndex == 0) {
                vDie[0] = new java.util.Vector(center_counter);
                vDie[0].addElement(new Integer(0));
                vClusters.addElement(vDie[0]);
            }else {
                for(int clusterIndex = 0; clusterIndex < vClusters.size() ; clusterIndex++) {
                    vDieSize = vDie[clusterIndex].size();
                    //int meanX = 0, meanY = 0;
                    for(int dieIndex = 0; dieIndex < vDieSize; dieIndex++) {
                        x = refineCenters[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0];//houghCircles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0];
                        y = refineCenters[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1];//houghCircles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1];
                        //meanX+=x; meanY+=y;
                        //System.out.println("pinta: " + centerIndex + " dist: " + java.lang.Math.sqrt((double)(currentX-x)*(currentX-x)+(currentY-y)*(currentY-y)));
                        //System.out.println("Cluster: " + clusterIndex);
                        if(java.lang.Math.round(java.lang.Math.sqrt((double)(currentX-x)*(currentX-x)+(currentY-y)*(currentY-y))) >= maxClusterSize) {
                            belongsToDie = false;
                            //System.out.println("esta pinta nao pertence a este dado.");
                            break;
                        }//if
                    }//for
                    //meanX = meanX / vDieSize; meanY = meanY / vDieSize;
                    if(belongsToDie)
                    {
                        //System.out.println("esta pinta pertence ao dado " + clusterIndex);
                        belongsToClusters = true;
                        vDie[clusterIndex].addElement(new Integer(centerIndex));
                        break;
                    }//if
                    
                    if (clusterIndex == vClusters.size()-1) {
                        belongsToClusters = false;
                    }
                    belongsToDie = true;
                }//for
                if(!belongsToClusters) {
                    vDie[vClusters.size()] = new java.util.Vector(center_counter);
                    vDie[vClusters.size()].addElement(new Integer(centerIndex));
                    vClusters.addElement(vDie[vClusters.size()]);
                    belongsToDie = true;
                    belongsToClusters = true;
                }
            }
        }
        
        //for (int i=0; i<center_counter; i++)
        //    System.out.println(i+ "=> "+ houghCircles[i][0]+";"+houghCircles[i][1]);
        
        for (int i=0; i<vClusters.size(); i++)
        {
            //System.out.println("Cluster " + i);
            vDie[i].add(Boolean.FALSE);
            //for(int j = 0; j < vDie[i].size()-1; j++)
            //    System.out.println(((Integer)vDie[i].get(j)).intValue() + "=> " + houghCircles[((Integer)vDie[i].get(j)).intValue()][0]+";"+houghCircles[((Integer)vDie[i].get(j)).intValue()][1]);
        }//for_i
        
        //System.out.println("No. de Clusters: " + vClusters.size());
        cluster_counter = vClusters.size();
        
        int dieSize = 6, maxWhileCounter = 6;
        boolean falseExists = true;
        int whileCounter = 0, previousCrash = -1, previousIndex = -1, repeatCounter=0;
        //parsing clusters to find parasite spots
        while (falseExists && whileCounter < maxWhileCounter)
        {
            repeatCounter=0;
            for (int index=0, forCounter=0; index < cluster_counter && forCounter < 3*cluster_counter && repeatCounter<12; index++, forCounter++)
            {
                if (index != previousCrash)
                {
                    if(index == previousIndex)
                        repeatCounter++;
                    //get the centers of all clusters
                    clusterCenters = getClusterCenters(vDie);
                    
                    vDieSize = vDie[index].size()-1;
                    
                    java.util.Vector vDieContinue;
                    double maxmaxDist = (double)maxClusterSize/2. * 1.196;//*1.45;//*1.35; // * 1.195;
                    
                    //check if this cluster is a part of a die which is in another cluster
                    for (int index1 = index+1; index1 < cluster_counter; index1++) {
                        double dist = java.lang.Math.sqrt((double)(clusterCenters[index][0]-clusterCenters[index1][0])*(clusterCenters[index][0]-clusterCenters[index1][0]) + (double)(clusterCenters[index][1]-clusterCenters[index1][1])*(clusterCenters[index][1]-clusterCenters[index1][1]));
                        if (dist <= maxmaxDist && ((vDie[index].get(vDie[index].size()-1)).equals(Boolean.FALSE) && (vDie[index1].get(vDie[index1].size()-1)).equals(Boolean.FALSE)) ) {
                            vDie = mergeClusters(vDie, index, index1);
                            cluster_counter--;
                            vClusters.remove(index1);
                            clusterCenters = getClusterCenters(vDie);
                        }//if
                    }//for_index1
                    
                    centers = refineCenters;
                    int cluster_counter_save = cluster_counter;
                    vDieSize = vDie[index].size()-1;
                    
                    //System.out.println("Checking Die " + index + " : " + vDie[index]);
                    
                    // Check dies with 1 spot in them
                    if (vDieSize == 1) {
                        if (vDie[index].get(1).equals(Boolean.FALSE))
                        {
                            int[] pixelIndex = new int[1];
                            pixelIndex[0] = centers[((Integer)vDie[index].get(0)).intValue()][0] + centers[((Integer)vDie[index].get(0)).intValue()][1] * imageWidth;
                            java.util.Vector checkDie1Vector;
                            if (isOne(pixelIndex,maxClusterSize))
                            {
                                checkDie1Vector = checkDie1(vDie, index, centers, info, radius, maxClusterSize, true);
                            }//if
                            else
                            checkDie1Vector = checkDie1(vDie, index, centers, info, radius, maxClusterSize, false);
                            if (checkDie1Vector.get(1).equals(Boolean.TRUE))
                                checkDie1Vector = checkDie1(vDie, index, centers, info, radius, maxClusterSize, true);
                            vDie = (java.util.Vector[])checkDie1Vector.get(0);
                        }//if
                    }//if_vDieSize_1
                    
                    // Check dies with more than 1 spot in them
                    else if (vDieSize >= 2)
                    {
                        vDieContinue = checkDie(vDie, index, centers, info, maxClusterSize, radius, -1);
                        vDie = (java.util.Vector[])vDieContinue.get(0);
                        if (!((Boolean)vDieContinue.get(1)).booleanValue())
                            index--;
                        if (cluster_counter != vClusters.size()) {
                            for(int indexa=0; indexa < cluster_counter; indexa++) {
                                vClusters.setElementAt(vDie[indexa],indexa);
                            }//for_indexa
                            vClusters.remove(cluster_counter);
                        }//if
                    }//if_vDieSize>=3
                    
                    if (cluster_counter_save > cluster_counter)
                        index-= (cluster_counter_save - cluster_counter);
                    if (index < 0) index =0;
                }//if
                previousIndex = index;
                 //check if this cluster is a part of a die which is in another cluster
                 for (int index1 = index+1; index1 < cluster_counter; index1++) {
                     double dist = java.lang.Math.sqrt((double)(clusterCenters[index][0]-clusterCenters[index1][0])*(clusterCenters[index][0]-clusterCenters[index1][0]) + (double)(clusterCenters[index][1]-clusterCenters[index1][1])*(clusterCenters[index][1]-clusterCenters[index1][1]));
                     if (dist <= maxClusterSize/4 || (vDie[index].size() == 2 && vDie[index1].size() == 2 && dist <= maxClusterSize/2.59)) {
                         vDie = mergeClusters(vDie, index, index1);
                         cluster_counter--;
                         //System.out.println("Setting die "+index+" as FALSE.");
                         vDie[index].set(vDie[index].size()-1, Boolean.FALSE);
                         vClusters.remove(index1);
                         clusterCenters = getClusterCenters(vDie);
                     }//if
                 }//for_index1
                 
                if (forCounter == 3*cluster_counter)
                {
                    //System.out.println("forCounter reached max!");
                    previousCrash = index;
                }//if
            }//for_index
            falseExists = false;
            //System.out.println("Checking for falses!");
            for (int index = 0; index < cluster_counter && !falseExists; index++)
            {
                //System.out.println(vDie[index]);
                if (((Boolean)vDie[index].get(vDie[index].size()-1)).equals(Boolean.FALSE))
                {
                    falseExists = true;
                }//if
            }//for_index
            whileCounter++;
            //System.out.println("whileCounter: " + whileCounter);
        }//while
        
        //search and remove any die that is false; if while counter reaches its max
        if (whileCounter >= maxWhileCounter)
        {
            //System.out.println(maxDiceCount + "?" + cluster_counter);
            for (int index = 0; index < cluster_counter; index++)
            {
                if (cluster_counter > maxDiceCount && ((Boolean)vDie[index].get(vDie[index].size()-1)).equals(Boolean.FALSE))
                {
                    //System.out.println("Removing cluster "+index+" because it's FALSE! "+vDie[index]);
                    center_counter-=vDie[index].size()-1;
                    for (int indexa = index+1; indexa < cluster_counter; indexa++)
                    {
                        vDie[indexa-1].clear();
                        vDie[indexa-1] = (java.util.Vector)vDie[indexa].clone();
                    }//for_indexa
                    vDie[cluster_counter-1] = null;
                    cluster_counter--;
                    index--;
                }//if
            }//for_index
        }//if
        
        
        //create the values for the final image
        int indice;
        int cor;
        int xMean, yMean;
        double theta;
        int[] outPixels=null;
        //if (originalPixels == null)//for some stupid reason, this may not have originalPixels, but houghCountPixels...?!?!??????
        //{
            originalPixels = new int[area];
            java.awt.image.PixelGrabber pg1 = new java.awt.image.PixelGrabber(originalImage, 0, 0, imageWidth, imageHeight, originalPixels, 0, imageWidth);        
            try {pg1.grabPixels();}
            catch(InterruptedException e)
            {
                e.printStackTrace();
                System.exit(1);
            }//catch
        //}//if
        outPixels = originalPixels;
            
        for(int clusterIndex = 0; clusterIndex < cluster_counter; clusterIndex++) {
            xMean = 0;
            yMean = 0;
            switch(clusterIndex) {
                case 0:
                    cor = 0xffff0000;   //red
                    //System.out.println("dado No 0 fica 'red'");
                    break;
                case 1:
                    cor = 0xff00ff00;   //green
                    //System.out.println("dado No 1 fica 'green'");
                    break;
                case 2:
                    cor = 0xff0000ff;   //blue
                    //System.out.println("dado No 2 fica 'blue'");
                    break;
                case 3:
                    cor = 0xffff00ff;   //pink
                    //System.out.println("dado No 3 fica 'pink'");
                    break;
                case 4:
                    cor = 0xffffffff;   //White
                    //System.out.println("dado No 4 fica 'white'");
                    break;
                case 5:
                    cor = 0xffffff00;   //yellow
                    //System.out.println("dado No 5 fica 'yellow'");
                    break;
                case 6:
                    cor = 0xff00ffff;   //cyan
                    //System.out.println("dado No 6 fica 'cyan'");
                    break;
                case 7:
                    cor = 0xffc89632;   //brown
                    //System.out.println("dado No 7 fica 'brown'");
                    break;
                case 8:
                    cor = 0xff96c832;   //dry green
                    //System.out.println("dado No 8 fica 'dry green'");
                    break;
                case 9:
                    cor = 0xff3296c8;   //sea blue
                    //System.out.println("dado No 9 fica 'sea blue'");
                    break;
                case 10:
                    cor = 0xff32c896;   //light blue
                    //System.out.println("dado No 10 fica 'light blue'");
                    break;
                case 11:
                    cor = 0xff9632c8;   //purple
                    //System.out.println("dado No 11 fica 'purple'");
                    break;
                case 12:
                    cor = 0xffc83296;   //dark pink
                    //System.out.println("dado No 12 fica 'dark pink'");
                    break;
                case 13:
                    cor = 0xffc83232;   //dry blood
                    //System.out.println("dado No 13 fica 'dry blood'");
                default:
                    cor = 0xffc0c0c0;   //gray
                    //System.out.println("Outros dados ficam 'gray'");
            }//switch
            
            for(int dieIndex = 0; dieIndex < vDie[clusterIndex].size()-1; dieIndex++) {
                //System.out.println("Cluster: " + clusterIndex + " pinta: " + ((Integer)vDie[clusterIndex].get(dieIndex)).intValue());
                
                // to draw the circles for each cluster
                xMean += centers[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0];//houghCircles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0];
                yMean += centers[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1];//houghCircles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1];
                if (dieIndex == vDie[clusterIndex].size()-2) {  //-1 ==> -2
                    xMean = xMean / (vDie[clusterIndex].size()-1);
                    yMean = yMean / (vDie[clusterIndex].size()-1);
                    indice = yMean * imageWidth + xMean;
                    for (int circleIndex = 0; circleIndex < 90; circleIndex++) {
                        theta = (double)circleIndex * java.lang.Math.PI / 180; //angle in radians
                        x = (int)((double)(maxClusterSize+5)/2 * java.lang.Math.cos(theta));
                        y = (int)((double)(maxClusterSize+5)/2 * java.lang.Math.sin(theta));
                        try {
                            outPixels[(yMean + y) * imageWidth + (xMean + x)] = 0xffff00ff;
                        }catch (ArrayIndexOutOfBoundsException e){}
                        try {
                            outPixels[(yMean + y) * imageWidth + (xMean - x)] = 0xffff00ff;
                        }catch (ArrayIndexOutOfBoundsException e){}
                        try {
                            outPixels[(yMean - y) * imageWidth + (xMean + x)] = 0xffff00ff;
                        }catch (ArrayIndexOutOfBoundsException e){}
                        try {
                            outPixels[(yMean - y) * imageWidth + (xMean - x)] = 0xffff00ff;
                        }catch (ArrayIndexOutOfBoundsException e){}
                    }
                    
                }
                
                indice = centers[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1] * imageWidth + centers[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0]; 
                        //houghCircles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1] * imageWidth + houghCircles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0];
                
                //Desenha as cruzes
                outPixels[indice] = cor;
                try {
                    outPixels[indice - imageWidth - 1] = cor;
                }catch (ArrayIndexOutOfBoundsException e){}
                try {
                    outPixels[indice - imageWidth + 1] = cor;
                }catch (ArrayIndexOutOfBoundsException e){}
                try {
                    outPixels[indice + imageWidth - 1] = cor;
                }catch (ArrayIndexOutOfBoundsException e){}
                try {
                    outPixels[indice + imageWidth + 1] = cor;
                }catch (ArrayIndexOutOfBoundsException e){}
            }
        }//for
        
        //Builds the final image
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,outPixels,0,imageWidth);
        fullCountImage = createImage(mis);
        
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(fullCountImage,0);
        try {
            tracker.waitForAll();
        }catch(InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        fullCountPixels = outPixels;
        refineCenters = centers;
        /*
        javax.swing.JFrame imagemDialog = new javax.swing.JFrame();
        imagemDialog.setTitle("Imagem fullCount");
        ImageStorePanel imagemCapturadaPanel = new ImageStorePanel(houghCountImage);
        imagemDialog.getContentPane().add(imagemCapturadaPanel);
        int[] dimIm = imagemCapturadaPanel.imageSize();
        imagemDialog.setSize(dimIm[0]+8, dimIm[1]+27);
        //imagemDialog.pack();
        imagemDialog.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        imagemDialog.show();
        imagemDialog.repaint();
        */
        fullCircles = new int[center_counter][2];
        int minimBefore = -1;
        for (int i =0; i<center_counter; i++)
        {
            int minim=1000;
            //find the minimumin vDie
            for (int ii=0; ii<cluster_counter; ii++)
            {
                for(int ij=0; ij < vDie[ii].size()-1; ij++)
                {
                    int temp = ((Integer)vDie[ii].get(ij)).intValue();
                    if (temp < minim && temp > minimBefore)
                        minim = temp;
                }//for_ij
            }//for_ii
            fullCircles[i][0] = centers[minim][0];//houghCircles[minim][0];
            fullCircles[i][1] = centers[minim][1];//houghCircles[minim][1];
            minimBefore = minim;
        }//for_i
        
        image = fullCountImage;
        return fullCountImage;
    }//refineCount
    
    
    private java.awt.Image buildImage(int[][] centers, int centersCounter)
    {
        int pixels[] = new int[area];
        
        java.awt.image.PixelGrabber pg = new java.awt.image.PixelGrabber(originalImage, 0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);        
        try {pg.grabPixels();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        
        int indice;
        for(int index = 0; index < centersCounter; index++)
        {
            indice = centers[index][1] * imageWidth + centers[index][0];
            pixels[indice] = 0xffff00ff;
            try {pixels[indice - imageWidth - 1] = 0xffff00ff;}
            catch (ArrayIndexOutOfBoundsException e){}
            try {pixels[indice - imageWidth + 1] = 0xffff00ff;}
            catch (ArrayIndexOutOfBoundsException e){}
            try {pixels[indice + imageWidth - 1] = 0xffff00ff;}
            catch (ArrayIndexOutOfBoundsException e){}
            try {pixels[indice + imageWidth + 1] = 0xffff00ff;}
            catch (ArrayIndexOutOfBoundsException e){}
        }
        //Builds the final image
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,pixels,0,imageWidth);
        java.awt.Image image = createImage(mis);
        
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(image,0);
        try {tracker.waitForAll();}
        catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return image;
    }//buildImage(int[][] centers, int centersCounter)
    
    private void countClusters(int[][] circles)
    {
        //Counting the number of Dice by clustering
        int currentX, currentY;        
        int x, y;
        int maxDice = 256;
        
        java.util.Vector vClusters = new java.util.Vector(maxDice);     //is a vector of vectors
        java.util.Vector[] vDie = new java.util.Vector[maxDice];      //is an array of vectors of ints               
        int vDieSize;
        boolean belongsToDie = true;
        boolean belongsToClusters = true;
                
        //create clusters
        for(int centerIndex = 0; centerIndex < center_counter; centerIndex++)
        {
            currentX = circles[centerIndex][0];
            currentY = circles[centerIndex][1];
            
            if (centerIndex == 0)
            {
                vDie[0] = new java.util.Vector(center_counter);
                vDie[0].addElement(new Integer(0));
                vClusters.addElement(vDie[0]);
            }else
            {
                for(int clusterIndex = 0; clusterIndex < vClusters.size() ; clusterIndex++)
                {
                    vDieSize = vDie[clusterIndex].size();
                    
                    for(int dieIndex = 0; dieIndex < vDieSize; dieIndex++)
                    {
                        x = circles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][0];
                        y = circles[((Integer)vDie[clusterIndex].get(dieIndex)).intValue()][1];
                        //System.out.println("pinta: " + centerIndex + " dist: " + java.lang.Math.sqrt((double)(currentX-x)*(currentX-x)+(currentY-y)*(currentY-y)));
                        //System.out.println("Cluster: " + clusterIndex);
                        if(java.lang.Math.sqrt((double)(currentX-x)*(currentX-x)+(currentY-y)*(currentY-y)) > maxClusterSize)
                        {
                            belongsToDie = false;
                            //System.out.println("esta pinta nao pertence a este dado.");
                            break;
                        }
                    }
                    if(belongsToDie == true)
                    {
                        //System.out.println("esta pinta pertence ao dado " + clusterIndex);
                        belongsToClusters = true;
                        vDie[clusterIndex].addElement(new Integer(centerIndex));
                        break;
                    }else
                        if (clusterIndex == vClusters.size()-1)
                            belongsToClusters = false;

                    belongsToDie = true;                    
                }
                if(belongsToClusters == false)
                {
                    vDie[vClusters.size()] = new java.util.Vector(center_counter);
                    vDie[vClusters.size()].addElement(new Integer(centerIndex));
                    vClusters.addElement(vDie[vClusters.size()]);
                    belongsToDie = true;
                    belongsToClusters = true;
                }
            }
        }
        //System.out.println("No. de Clusters: " + vClusters.size());
        
        cluster_counter = vClusters.size();
    }//countClusters()
    
    public int getHoughCenterCounter()
    {
        return houghCenterCounter;
    }//getHoughCenterCounter()
    
    public int getCenterCounter()
    {
        return center_counter;
    }//getCenterCounter()
    
    public int getClusterCounter()
    {
        return cluster_counter;
    }//getClusterCounter()
    
    public int[][] getCenters()
    {
        return fullCircles;
    }//getCenters()
    
    public int[] imageSize()
    {
        int[] size = {0,0};        
        
        if(image != null)
        {
            size[0] = image.getWidth(this);   
            size[1] = image.getHeight(this);
        }else
        {
            size[0] = -1;
            size[1] = -1;
        }
        return(size);
    }//imageSize()
    
    public void setImage(java.awt.Image image)
    {
        this.image = image;
        this.currentImageType = "unknown";
    }
    
    private void setImage(java.awt.Image image, String currentImageType)
    {
        this.currentImageType = currentImageType;
        this.image = image;
    }//setImage(java.awt.Image image, String currentImageType)
    
    public java.awt.Image getImage(String imageType)
    {
        if (imageType == "unknown")
            return null;
        else if (imageType == "Original" || imageType == "Original")
                return originalImage;
        else if (imageType == "BW" || imageType == "bw")
                return BWImage;
        else if (imageType == "Edges" || imageType == "edges")
                return edgesImage;
        else if (imageType == "Hough" || imageType == "hough")
                return houghImage;
        else if (imageType == "Convolution" || imageType == "convolution" || imageType == "Conv" || imageType == "conv")
                return convolutionImage;
        else if (imageType == "count" || imageType == "Count")
                return fullCountImage;
        else return originalImage;
    
    }//getImage(String imageType)
    
    /**
     *Implementation of Cloneable
     */
    public Object clone()
    {
        Object clone = null;
        try
        {
            // get our superclass to do the cloning for us
            clone = super.clone();
        }
        catch ( CloneNotSupportedException e )
        {
            // ignore this, because we know we're cloneable
        }
        
        return clone;
    }//clone()

    
    /**
     *Math Functions
     */
    private int max(int a, int b)
    {
        if (a>b)
            return a;
        return b;
    }//max_int
    
    private double max(double a, double b)
    {
        if (a>b)
            return a;
        return b;
    }//max_double
    
    private int min(int a, int b)
    {
        if (a<b)
            return a;
        return b;
    }//min
    
    private int abs(int a)
    {
        if (a < 0)
            return (-a);
        return a;
    }//abs
    
    private double abs(double a)
    {
        if (a < 0)
            return -a;
        return a;
    }//abs
    
    /**
     *   number!  (factorial)
     */
    private int permutations(int number)
    {
        if (number < 0)
            return 0;
        else if (number == 0)
            return 1;
        
        int returnNumber = 1;
        for (int i=2; i<=number; i++)
        {
            returnNumber = returnNumber*i;
        }//for_i
        
        return returnNumber;
    }//permutations
    
    /**
     *n_C_k = n! / ( k! * (n-k)! ) 
     */
    private int combinations(int n, int k)
    {
        if (n < k)
            return 0;
        return (permutations(n) / (permutations(k) * permutations(n-k)));
    }//combinations
    
    /**
     *Functions for Array Operations
     */
    
    private int[] removeArrayEntry(int[] array, int index) {
        int[] newArray = new int[array.length-1];
        for (int i=0; i<array.length;i++) {
            if (i<index)
                newArray[i] = array[i];
            else if (i>index)
                newArray[i-1] = array[i];
        }//for
        
        return newArray;
    }//removeArrayEntry_int[]
    
    private double[] removeArrayEntry(double[] array, int index) {
        double[] newArray = new double[array.length-1];
        for (int i=0; i<array.length;i++) {
            if (i<index)
                newArray[i] = array[i];
            else if (i>index)
                newArray[i-1] = array[i];
        }//for
        
        return newArray;
    }//removeArrayEntry_double[]
    
    private int[][] removeArrayEntry(int[][] array, int index, int level) {
        int[][] newArray;
        if (level == 0)
            newArray = new int[array.length-1][array[0].length];
        else
            newArray = new int[array.length][array[0].length-1];
        
        if(level == 0) {
            for (int i=0; i<array.length;i++) {
                if (i<index)
                    for(int j=0; j < array[0].length; j++)
                        newArray[i][j] = array[i][j];
                else if (i>index)
                    for(int j=0; j < array[0].length; j++)
                        newArray[i-1][j] = array[i][j];
            }//for
        }//if
        else if(level == 1) {
            for (int i=0; i<array.length; i++) {
                for(int j=0; j<array[0].length; j++)
                    if (j<index)
                        newArray[i][j] = array[i][j];
                    else if (j>index)
                        newArray[i][j-1] = array[i][j];
            }//for
        }//else if
        return newArray;
    }//removeArrayEntry_int[][]
    
    private double[][] removeArrayEntry(double[][] array, int index, int level) {
        double[][] newArray;
        if (level == 0)
            newArray = new double[array.length-1][array[0].length];
        else
            newArray = new double[array.length][array[0].length-1];
        
        if(level == 0) {
            for (int i=0; i<array.length;i++) {
                if (i<index)
                    for(int j=0; j < array[0].length; j++)
                        newArray[i][j] = array[i][j];
                else if (i>index)
                    for(int j=0; j < array[0].length; j++)
                        newArray[i-1][j] = array[i][j];
            }//for
        }//if
        else if(level == 1) {
            for (int i=0; i<array.length; i++) {
                for(int j=0; j<array[0].length; j++)
                    if (j<index)
                        newArray[i][j] = array[i][j];
                    else if (j>index)
                        newArray[i][j-1] = array[i][j];
            }//for
        }//else if
        return newArray;
    }//removeArrayEntry_double[][]
    
    private int[] addArrayEntry(int[] array, int newEntry)
    {
        int[] newArray = new int[array.length+1];
        for (int i=0; i<array.length; i++)
        {
            newArray[i] = array[i];
        }//for_i
        newArray[array.length] = newEntry;
        
        return newArray;
    }//addArrayEntry_int[]
    
    private double[] addArrayEntry(double[] array, double newEntry)
    {
        double[] newArray = new double[array.length+1];
        for (int i=0; i<array.length; i++)
        {
            newArray[i] = array[i];
        }//for_i
        newArray[array.length] = newEntry;
        
        return newArray;
    }//addArrayEntry_double[]
    
    
    /**
     *Cluster Operations
     */
    private int[][] getClusterCenters(java.util.Vector[] vDie) {
        int[][] clusterCentersThis = new int[cluster_counter][2];
        //System.out.println("getClusterCenters -- cluster_counter:"+cluster_counter);
        for (int index1 = 0; index1 < cluster_counter; index1++) {
            for ( int index2 = 0; index2 < vDie[index1].size()-1; index2++)
            {
                int centerIndex=0;
                //System.out.println(index1 + ", " + index2);
                try{centerIndex = Integer.parseInt(vDie[index1].get(index2).toString());}
                catch(NumberFormatException e){}
                //System.out.println(centerIndex + ";  " + centers[centerIndex][0] + "; " + centers[centerIndex][1]);
                
                clusterCentersThis[index1][0]+=refineCenters[centerIndex][0];
                clusterCentersThis[index1][1]+=refineCenters[centerIndex][1];
            }//for_index2
            clusterCentersThis[index1][0] = clusterCentersThis[index1][0]/(vDie[index1].size()-1);
            clusterCentersThis[index1][1] = clusterCentersThis[index1][1]/(vDie[index1].size()-1);
            //System.out.println("cluster " + index1 + ": " + clusterCentersThis[index1][0] + "; " + clusterCentersThis[index1][1]);
        }//for_index1
        return clusterCentersThis;
    }//getClusterCenters
    
    private java.util.Vector[] mergeClusters(java.util.Vector[] vDie, int index1, int index2) {
        //System.out.println("Merging clusters " + index1 + " and " + index2 + ".");
        for(int index=0; index<vDie[index2].size(); index++)    //copying elements from cluster 2 to cluster 1
        {
            if (index==0)
                vDie[index1].set(vDie[index1].size()-1, vDie[index2].get(index));
            else
                vDie[index1].addElement(vDie[index2].get(index));
        }//for_index
        //deleting cluster 2
        for (int index = index2+1; index < vDie.length; index++) {
            vDie[index-1] = vDie[index];
        }//for_index
        vDie[vDie.length-1] = null;
        return vDie;
    }//mergeClusters
    
    private java.util.Vector[] changeSpot(java.util.Vector[] vDie, int sourceCluster, int sourceIndex, int destinyCluster)
    {
        //System.out.println("Changing spot " + vDie[sourceCluster].get(sourceIndex) + " from cluster " + sourceCluster + " to cluster " + destinyCluster);
        vDie[destinyCluster].add(vDie[destinyCluster].size()-1,vDie[sourceCluster].get(sourceIndex));
        vDie[sourceCluster].remove(sourceIndex);
        //System.out.println("After removing spot => die:" + vDieIndex + " : " + vDie[vDieIndex]);
        return vDie;
    }//changeSpot
    
    private java.util.Vector[] removeSpot(java.util.Vector[] vDie, int vDieIndex, int index) {
        //System.out.println("removing spot " + vDie[vDieIndex].get(index));
        vDie[vDieIndex].remove(index);
        //System.out.println("After removing spot => die:" + vDieIndex + " : "+ vDie[vDieIndex]);
        center_counter--;
        return vDie;
    }//removeSpot
    
    /**
     *spot Checking
     */
    //checks the pixels of the B&W image
    private java.util.Vector isSpot(int pixelIndex, int raio, boolean isProbableSix, boolean isProbableOne) {
        int[] imSize = imageSize();
        int imageWidth = imSize[0], imageHeight = imSize[1], numberOfFalses = 0;
        boolean badTopRight = true, badTop = true, badTopLeft = true, badRight = true, badBottomRight = true, badBottom = true, badBottomLeft = true, badLeft = true;
        java.util.Vector toReturn = new java.util.Vector(4);
        toReturn.add(0, Boolean.FALSE);
        toReturn.add(1, new Integer(0));    //zeroCounter
        toReturn.add(2, new Integer(0));    //oneCounter
        toReturn.add(3, new Integer(0));    //maxCounter
        
        //System.out.println(pixelIndex%imageWidth+"; "+pixelIndex/imageWidth);
        
        //this is based on checking the pixels in a star like way.
        //we start at the center and check the pixels at top, topLeft, left, bottomLeft, bottom, bottomRight, right and topRight
        //when we reach a white, we stop and collect the number of pixels we had to move from the center until we reached the white.
        //the search must stop at the edges of the image.
        //only one of the rays can have a very large value (because of wormlike sixes and stuff like that)
        //all the other rays must have values close to each other.
        
        int topPixCount=0, topRightPixCount=0, rightPixCount=0, bottomRightPixCount=0;
        int bottomPixCount=0, bottomLeftPixCount=0, leftPixCount=0, topLeftPixCount=0;
        
        int xCenter = pixelIndex%imageWidth, yCenter = pixelIndex/imageWidth;
        int maxIndex = java.lang.Math.min(java.lang.Math.min(xCenter, imageWidth-xCenter), java.lang.Math.min(yCenter, imageHeight-yCenter));
        
        for (int index = 1; index < maxIndex; index++)
        {
            if (topPixCount == 0 && (BWPixels[pixelIndex - index * imageWidth] & 0x000000ff) == 255)
                topPixCount = index;
            if (topRightPixCount == 0 && (BWPixels[pixelIndex - index * imageWidth + index] & 0x000000ff) == 255)
                topRightPixCount = index;
            if (rightPixCount == 0 && (BWPixels[pixelIndex + index] & 0x000000ff) == 255)
                rightPixCount = index;
            if (bottomRightPixCount == 0 && (BWPixels[pixelIndex + index * imageWidth + index] & 0x000000ff) == 255)
                bottomRightPixCount = index;
            if (bottomPixCount == 0 && (BWPixels[pixelIndex + index * imageWidth] & 0x000000ff) == 255)
                bottomPixCount = index;
            if (bottomLeftPixCount == 0 && (BWPixels[pixelIndex + index * imageWidth - index] & 0x000000ff) == 255)
                bottomLeftPixCount = index;
            if (leftPixCount == 0 && (BWPixels[pixelIndex - index] & 0x000000ff) == 255)
                leftPixCount = index;
            if (topLeftPixCount == 0 && (BWPixels[pixelIndex - index * imageWidth - index] & 0x000000ff) == 255)
                topLeftPixCount = index;
        }//for_index
        
        int sumCount = topPixCount + topRightPixCount + rightPixCount + bottomRightPixCount + bottomPixCount + bottomLeftPixCount + leftPixCount + topLeftPixCount;
        double temp1 = Math.sqrt((double)(topRightPixCount)*(topRightPixCount) + (double)(topPixCount-topRightPixCount)*(topPixCount-topRightPixCount));
        double temp2 = Math.sqrt((double)(rightPixCount)*(rightPixCount) + (double)(topPixCount)*(topPixCount));
        double temp3 = Math.sqrt((double)(bottomRightPixCount)*(bottomRightPixCount) + (double)(topPixCount+bottomRightPixCount)*(topPixCount+bottomRightPixCount));
        double temp4 = (double)(topPixCount + bottomPixCount);
        double temp5 = Math.sqrt((double)(bottomLeftPixCount)*(bottomLeftPixCount) + (double)(topPixCount+bottomLeftPixCount)*(topPixCount+bottomLeftPixCount));
        double temp6 = Math.sqrt((double)(leftPixCount)*(leftPixCount) + (double)(topPixCount)*(topPixCount));
        double temp7 = Math.sqrt((double)(topLeftPixCount)*(topLeftPixCount) + (double)(topPixCount-topLeftPixCount)*(topPixCount-topLeftPixCount));
        
        double maxDiffD = max(max(max(temp1,temp2), max(temp3,temp4)), max(max(temp5, temp6),temp7));
        
        temp1 = Math.sqrt((double)(topRightPixCount-rightPixCount)*(topRightPixCount-rightPixCount) + (double)(topRightPixCount)*(topRightPixCount));
        temp2 = Math.sqrt((double)(topRightPixCount - bottomRightPixCount)*(topRightPixCount - bottomRightPixCount) + (double)(topRightPixCount + bottomRightPixCount)*(topRightPixCount + bottomRightPixCount));
        temp3 = Math.sqrt((double)(topRightPixCount)*(topRightPixCount) + (double)(topRightPixCount+bottomPixCount)*(topRightPixCount+bottomPixCount));
        temp4 = (double)(topRightPixCount+bottomLeftPixCount)*Math.sqrt(2);
        temp5 = Math.sqrt((double)(topRightPixCount+leftPixCount)*(topRightPixCount+leftPixCount) + (double)(topRightPixCount)*(topRightPixCount));
        temp6 = Math.sqrt((double)(topRightPixCount+topLeftPixCount)*(topRightPixCount+topLeftPixCount) + (double)(topRightPixCount-topLeftPixCount)*(topRightPixCount-topLeftPixCount));
        
        maxDiffD = max(max(max(temp1,temp2), max(temp3,temp4)), max(max(temp5, temp6), maxDiffD));
        
        temp1 = Math.sqrt((double)(rightPixCount-bottomRightPixCount)*(rightPixCount-bottomRightPixCount) + (double)(bottomRightPixCount)*(bottomRightPixCount));
        temp2 = Math.sqrt((double)(rightPixCount)*(rightPixCount) + (double)(bottomPixCount)*(bottomPixCount));
        temp3 = Math.sqrt((double)(rightPixCount+bottomLeftPixCount)*(rightPixCount+bottomLeftPixCount) + (double)(bottomLeftPixCount)*(bottomLeftPixCount));
        temp4 = (double)(rightPixCount+leftPixCount);
        temp5 = Math.sqrt((double)(rightPixCount+topLeftPixCount)*(rightPixCount+topLeftPixCount) + (double)(topLeftPixCount)*(topLeftPixCount));
        
        maxDiffD = max(max(max(temp1,temp2), max(temp3,temp4)), max(temp5, maxDiffD));
        
        temp1 = Math.sqrt((double)(bottomRightPixCount)*(bottomRightPixCount) + (double)(bottomRightPixCount-bottomPixCount)*(bottomRightPixCount-bottomPixCount));
        temp2 = Math.sqrt((double)(bottomRightPixCount+bottomLeftPixCount)*(bottomRightPixCount+bottomLeftPixCount) + (double)(bottomRightPixCount-bottomLeftPixCount)*(bottomRightPixCount-bottomLeftPixCount));
        temp3 = Math.sqrt((double)(bottomRightPixCount+leftPixCount)*(bottomRightPixCount+leftPixCount) + (double)(bottomRightPixCount)*(bottomRightPixCount));
        temp4 = (double)(bottomRightPixCount+topLeftPixCount)*Math.sqrt(2);
        
        maxDiffD = max(max(max(temp1,temp2), max(temp3,temp4)), maxDiffD);
        
        temp1 = Math.sqrt((double)(bottomLeftPixCount)*(bottomLeftPixCount) + (double)(bottomPixCount-bottomLeftPixCount)*(bottomPixCount-bottomLeftPixCount));
        temp2 = Math.sqrt((double)(leftPixCount)*(leftPixCount) + (double)(bottomPixCount)*(bottomPixCount));
        temp3 = Math.sqrt((double)(topLeftPixCount)*(topLeftPixCount) + (double)(bottomPixCount+topLeftPixCount)*(bottomPixCount+topLeftPixCount));
        
        maxDiffD = max(max(temp1,temp2), max(temp3,maxDiffD));
        
        temp1 = Math.sqrt((double)(bottomLeftPixCount-leftPixCount)*(bottomLeftPixCount-leftPixCount) + (double)(bottomLeftPixCount)*(bottomLeftPixCount));
        temp2 = Math.sqrt((double)(bottomLeftPixCount-topLeftPixCount)*(bottomLeftPixCount-topLeftPixCount) + (double)(bottomLeftPixCount+topLeftPixCount)*(bottomLeftPixCount+topLeftPixCount));
        
        maxDiffD = max(max(temp1,temp2), maxDiffD);
        
        temp1 = Math.sqrt((double)(leftPixCount-topLeftPixCount)*(leftPixCount-topLeftPixCount) + (double)(topLeftPixCount)*(topLeftPixCount));
        
        maxDiffD = max(temp1, maxDiffD);
        
        int maxDiff = (int)Math.round(maxDiffD);
        
        //System.out.println("top:" + topPixCount + ", topRight:" + topRightPixCount + ", right:" + rightPixCount + ", bottomRight:" + bottomRightPixCount + ", bottom:" + bottomPixCount + ", bottomLeft:" + bottomLeftPixCount + ", left:" + leftPixCount + ", topLeft:" + topLeftPixCount + ", maxDiff:" + maxDiff + ", sum:" + sumCount);
        
        //check for zeros
        int zeroCounter=0;
        if (topPixCount == 0)
            zeroCounter++;
        if (topRightPixCount == 0)
            zeroCounter++;
        if (rightPixCount == 0)
            zeroCounter++;
        if (bottomRightPixCount == 0)
            zeroCounter++;
        if (bottomPixCount == 0)
            zeroCounter++;
        if (bottomLeftPixCount == 0)
            zeroCounter++;
        if (leftPixCount == 0)
            zeroCounter++;
        if (topLeftPixCount == 0)
            zeroCounter++;
        
        if (zeroCounter > 2)
        {
            //System.out.println("zeroCounter("+zeroCounter+") >=2");
            toReturn.set(1, new Integer(zeroCounter));
            return toReturn;
        }
        
        
        //we'return going to use zeroCounter to count the number of times that we have a count higher than our max
        //zeroCounter = 0;  we don't reset the zerCounter so we can find spots that have a zero and something else
        //4.6 * 5 = 23 ; I need this to be smaller than 24 and larger than 22
        int minCount = 2, maxCount;
        int maxCounter = 0;

        if (isProbableSix)
            maxCount = (int) (5.2*raio);
        else maxCount = (int)(2.2*raio);//raio+2;
        
        if (topPixCount > maxCount)
            maxCounter++;
        if (topRightPixCount > maxCount)
            maxCounter++;
        if (rightPixCount > maxCount)
            maxCounter++;
        if (bottomRightPixCount > maxCount)
            maxCounter++;
        if (bottomPixCount > maxCount)
            maxCounter++;
        if (bottomLeftPixCount > maxCount)
            maxCounter++;
        if (leftPixCount > maxCount)
            maxCounter++;
        if (topLeftPixCount > maxCount)
            maxCounter++;
        
        int oneCounter=0;
        if (topPixCount < minCount && topPixCount > 0)
            oneCounter++;
        if (topRightPixCount < minCount && topRightPixCount > 0)
            oneCounter++;
        if (rightPixCount < minCount && rightPixCount > 0)
            oneCounter++;
        if (bottomRightPixCount < minCount && bottomRightPixCount > 0)
            oneCounter++;
        if (bottomPixCount < minCount && bottomPixCount > 0)
            oneCounter++;
        if (bottomLeftPixCount < minCount && bottomLeftPixCount > 0)
            oneCounter++;
        if (leftPixCount < minCount && leftPixCount > 0)
            oneCounter++;
        if (topLeftPixCount < minCount && topLeftPixCount > 0)
            oneCounter++;
        
        toReturn.set(1, new Integer(zeroCounter));    //zeroCounter
        toReturn.set(2, new Integer(oneCounter));    //oneCounter
        toReturn.set(3, new Integer(maxCounter));    //maxCounter
        //System.out.println("zeroCounter:" + zeroCounter + ", oneCounter:" + oneCounter + ", maxCounter:" + maxCounter + ", maxCount:" + maxCount);
        if ((maxCounter+zeroCounter) > 2)
        {
            //System.out.println("(maxCounter("+maxCounter+")+zeroCounter("+zeroCounter+")) > 2");
            return toReturn;
        }//if
        else if (zeroCounter >= 1 && (oneCounter > 0 || maxDiff > maxCount))
        {
            //System.out.println("zeroCounter("+zeroCounter+") >= 1 && (oneCounter("+oneCounter+") > 0 || maxDiff("+maxDiff+") > maxCount("+maxCount+"))");
            return toReturn;
        }//else_if
        if (isProbableOne && (zeroCounter > 1 || maxCounter > 1))//(zeroCounter > 0 || maxCounter > 0))
        {
            //System.out.println("isProbableOne("+isProbableOne+") && (zeroCounter("+zeroCounter+") > 1 || maxCounter("+maxCounter+") > 1)");
            return toReturn;
        }
        if ((zeroCounter+maxCounter) == 0 && !isProbableSix && maxDiff > maxCount)
        {
            //System.out.println("(zeroCounter("+zeroCounter+")+maxCounter("+maxCounter+")) == 0 && !isProbableSix("+isProbableSix+") && maxDiff("+maxDiff+") > maxCount("+maxCount+")");
            return toReturn;
        }//if
        if (maxCounter >= 2 && !isProbableSix)
        {
            //System.out.println("maxCounter("+maxCounter+")>=2 && !isProbableSix("+isProbableSix+")");
            return toReturn;
        }//if
        
        if(maxCounter > 1 && maxDiff > maxCount)
        {
            //System.out.println("maxCounter("+maxCounter+") > 1 && maxDiff("+maxDiff+") > maxCount("+maxCount+")");
            return toReturn;
        }//if
        //int maxmaxDiff = 4, minSumCount = 22;
        //if (oneCounter > 2 && maxDiff > maxmaxDiff && sumCount < minSumCount)
        //    return false;
        
        int minSumCount = 21;//24;//18;
        if (oneCounter > 6)//5
        {
            //System.out.println("oneCounter("+oneCounter+") > 6");
            return toReturn;
        }
        //else if (oneCounter > 3 && sumCount < minSumCount)
        //{
        //    System.out.println("oneCounter("+oneCounter+") > 3 && sumCount("+sumCount+") < minSumCount("+minSumCount+")");
        //    return toReturn;
        //}
        else if (oneCounter > 1 && maxCounter > 0)
        {
            //System.out.println("oneCounter("+oneCounter+") > 1 && maxCounter("+maxCounter+") > 0");
            return toReturn;
        }
        
        if ((zeroCounter > 1 || maxCounter > 0) && sumCount < minSumCount)
        {
            //System.out.println("(zeroCounter("+zeroCounter+")>0 || maxCounter("+maxCounter+")>0) && sumCount("+sumCount + ")<minSumCount(" + minSumCount+")");
            return toReturn;
        }//if
        
        toReturn.set(0, Boolean.TRUE);
        return toReturn;
    }//isSpot
    
    
    /**
     *Spot Count Checking
     */
    
    private boolean checkSpotCount(double[] distance, int spotCount, int[] pixelIndex, int maxClusterSize)
    {
        boolean spotCountIsCorrect = false;
        switch(spotCount) {
            case 1:
                spotCountIsCorrect = isOne(pixelIndex, maxClusterSize);
                break;
            case 2:
                spotCountIsCorrect = isTwo(distance, pixelIndex, false);
                break;
            case 3:
                spotCountIsCorrect = isThree(distance, pixelIndex);
                break;
            case 4:
                spotCountIsCorrect = isFour(distance, pixelIndex);
                break;
            case 5:
                spotCountIsCorrect = isFive(distance);
                break;
            case 6:
                spotCountIsCorrect = isSix(distance, pixelIndex);
                break;
            default:
                spotCountIsCorrect = false;
                break;
        }//switch
        return spotCountIsCorrect;
    }//checkSpotCount
    
    private boolean isOne(int[] pixelIndex, int maxClusterSize) {
        
        int x1, y1, centerIndex1=0;
        int imageWidth = imageSize()[0];
        //double maxmaxDist = (double)maxClusterSize/2. * 1.195;
        
        x1 = pixelIndex[0] % imageWidth;
        y1 = pixelIndex[0] / imageWidth;
        //check pixels in a star like manner to determine if this is really a 'one'
        boolean[] isPreviousWhite = new boolean[8], done = new boolean[8];
        int[] side = new int[8]; // start at the top and move clockWise (side[0] == top ; side[2] == right,  etc...)
        
        for (int i=0; i<8; i++)
        {
            isPreviousWhite[i]=false; side[i]=0; done[i] = false;
        }//for_i
        int x,y;
        for (int i = 2; i < maxClusterSize; i++)
        {
            x = x1; y = y1-i;   //top
            int pixel=-1;
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[0] && side[0] != 0 && !done[0])
                {
                    side[0] = i;
                    done[0]=true;
                }//if
                isPreviousWhite[0] = false;
            }//if
            else
            {
                isPreviousWhite[0] = true;
                if (side[0] == 0)
                    side[0] = i;
            }//else
            
            x = x1+i; y = y1-i;   //top-right
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[1] && side[1] != 0 && !done[1])
                {
                    side[1] = i;
                    done[1] = true;
                }//if
                isPreviousWhite[1] = false;
            }//if
            else
            {
                isPreviousWhite[1] = true;
                if (side[1] == 0)
                    side[1] = i;
            }//else
            
            x = x1+i; y = y1;   //right
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[2] && side[2] != 0 && !done[2])
                {
                    side[2] = i;
                    done[2] = true;
                }//if
                isPreviousWhite[2] = false;
            }//if
            else
            {
                isPreviousWhite[2] = true;
                if (side[2] == 0)
                    side[2] = i;
            }//else
            
            x = x1+i; y = y1+i;   //bottom-right
            //System.out.println("("+x+","+y+") is black?" + ((BWPixels[x+y*imageWidth]&0x000000ff) == 0));
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[3] && side[3] != 0  && !done[3])
                {
                    side[3] = i;
                    done[3] = true;
                }//if
                isPreviousWhite[3] = false;
            }//if
            else
            {
                isPreviousWhite[3] = true;
                if (side[3] == 0)
                    side[3] = i;
            }//else
            
            x = x1; y = y1+i;   //bottom
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[4] && side[4] != 0 && !done[4])
                {
                    side[4] = i;
                    done[4] = true;
                }//if
                isPreviousWhite[4] = false;
            }//if
            else
            {
                isPreviousWhite[4] = true;
                if (side[4] == 0)
                    side[4] = i;
            }//else
            
            x = x1-i; y = y1+i;   //bottom-left
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[5] && side[5] != 0 && !done[5])
                {
                    side[5] = i;
                    done[5] = true;
                }//if
                isPreviousWhite[5] = false;
            }//if
            else
            {
                isPreviousWhite[5] = true;
                if (side[5] == 0)
                    side[5] = i;
            }//else
            
            x = x1-i; y = y1;   //left
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[6] && side[6] != 0 && !done[6])
                {
                    side[6] = i;
                    done[6] = true;
                }//if
                isPreviousWhite[6] = false;
            }//if
            else
            {
                isPreviousWhite[6] = true;
                if (side[6] == 0)
                    side[6] = i;
            }//else
            
            x = x1-i; y = y1-i;   //top-left
            try{pixel=(BWPixels[x+y*imageWidth]&0x000000ff);}
            catch(Exception e){}
            if (pixel == 0)
            {
                if (isPreviousWhite[7] && side[7] != 0 && !done[7])
                {
                    side[7] = i;
                    done[7] = true;
                }//if
                isPreviousWhite[7] = false;
            }//if
            else
            {
                isPreviousWhite[7] = true;
                if (side[7] == 0)
                    side[7] = i;
            }//else
            
        }//for_i
        
        int minStraight = (int)((double)maxClusterSize/3.3);
        int maxStraight = (int)((double)maxClusterSize/1.5);
        int lessThanMinCounter = 0, moreThanMaxCounter=0, consecutiveLessThanMinCounter = 0;
        boolean good = true, wasBeforeLessThanMin=false;
        for (int i=0; i < 8; i++)
        {
            //System.out.println("side " + i + " => " + side[i] +" ;minStraight:"+ minStraight + " ;maxStraight:"+maxStraight);
            if (side[i] < minStraight)
            {
                lessThanMinCounter++;
                if (wasBeforeLessThanMin)
                    consecutiveLessThanMinCounter++;
                wasBeforeLessThanMin = true;
            }
            else if(side[i] > maxStraight)
            {
                moreThanMaxCounter++;
                wasBeforeLessThanMin = false;
            }//else_if
            else
            {
                wasBeforeLessThanMin = false;
            }//else   
        }//for_i
        if ((lessThanMinCounter > 2 && consecutiveLessThanMinCounter > 0) || (lessThanMinCounter > 3 && consecutiveLessThanMinCounter == 0) || moreThanMaxCounter > 0)
            good = false;
        
        return good;
    }//isOne
    
    private boolean isTwo(double[] distance, int[] pixelIndex, boolean isRelaxed) {
        double minDist = 10.770329614269007, maxDist = 15.811388300841896;//15.033296378372908;
        //System.out.println(distance[0] + "; " + distance[1]);
        int[] whiteCounters = countWhitePatches(pixelIndex[0], pixelIndex[1]);
        int whiteCounterNumMin=0, whiteCounterNumMax=0, minWhitePixelCounter=0, maxWhitePixelCounter=0;
        if (isRelaxed)
        {
            whiteCounterNumMin = 0;
            whiteCounterNumMax = 1000;
            minWhitePixelCounter = 0;
            maxWhitePixelCounter = 1000;
        }//if
        else
        {
            whiteCounterNumMin = 1;
            whiteCounterNumMax = 1;
            minWhitePixelCounter = 15;//16;
            maxWhitePixelCounter = 33;
        }
        //System.out.println("whiteCounter:"+whiteCounters[0] + ", maxWhiteCounter:"+whiteCounterNumMax +"; whitePixelCounter:"+whiteCounters[1]+", max:"+maxWhitePixelCounter+", min:"+minWhitePixelCounter);
        if (distance[0] >= minDist && distance[0] <= maxDist && distance[1] >= minDist && distance[1] <= maxDist && whiteCounters[0] >= whiteCounterNumMin && whiteCounters[0] <= whiteCounterNumMax && whiteCounters[1] >=minWhitePixelCounter && whiteCounters[1]<=maxWhitePixelCounter)
            return true;
        return false;
    }//isTwo
    
    private boolean isThree(double[] distance, int[] pixelIndex) {
        double minDistCenter=0.0, maxDistCenter=1.4142135623730951, minDist = 10.770329614269007, maxDist = 14.866068747318506;
        double distance1 = distance[0];
        double distance2 = distance[1];
        double distance3 = distance[2];
        int startPixelIndex=0, endPixelIndex=0, minWhiteCounter=2, maxWhiteCounter=2, minWhitePixelCounter=4, maxWhitePixelCounter=13;
        
        for (int i=0; i<3; i++)
        {
            if(startPixelIndex!=0 && distance[i]>=minDist)
                endPixelIndex=pixelIndex[i];
            if(startPixelIndex==0 && distance[i]>=minDist)
                startPixelIndex = pixelIndex[i];
        }//for_i
        //System.out.println("start: ("+startPixelIndex%imageSize()[0]+","+startPixelIndex/imageSize()[0]+") ::: end: ("+endPixelIndex%imageSize()[0]+","+endPixelIndex/imageSize()[0]+")");
        int[] whiteCounters = countWhitePatches(startPixelIndex, endPixelIndex);
        //System.out.println("whiteCounter:"+whiteCounters[0]+ ",pixelCounter:"+whiteCounters[1]);
        //System.out.println(distance1 + "; " + distance2 + "; " + distance3);
        int centerSpot=0;
        //First check if there is a spot in the middle. If not, then we have a bad detection. let's try to fix it!
        if (distance1 <= maxDistCenter && distance1 >= minDistCenter)
            centerSpot=1;
        else if(centerSpot==0 && distance2 <= maxDistCenter && distance2 >= minDistCenter)
            centerSpot=2;
        else if(centerSpot==0 && distance3 <= maxDistCenter && distance3 >= minDistCenter)
            centerSpot=3;
        else return false;
        if (centerSpot!=0)//there is a spot in the middle! cehck if the orthers are at the correct distance!
            switch(centerSpot) {
                case 1:
                    if(distance2 >= minDist && distance2 <= maxDist && distance3 >= minDist && distance3 <= maxDist && whiteCounters[0]>=minWhiteCounter && whiteCounters[0]<=maxWhiteCounter && whiteCounters[1]>=minWhitePixelCounter && whiteCounters[1]<=maxWhitePixelCounter)
                        return true;
                    break;
                case 2:
                    if(distance1 >= minDist && distance1 <= maxDist && distance3 >= minDist && distance3 <= maxDist && whiteCounters[0]>=minWhiteCounter && whiteCounters[0]<=maxWhiteCounter && whiteCounters[1]>=minWhitePixelCounter && whiteCounters[1]<=maxWhitePixelCounter)
                        return true;
                    break;
                case 3:
                    if(distance1 >= minDist && distance1 <= maxDist && distance2 >= minDist && distance2 <= maxDist && whiteCounters[0]>=minWhiteCounter && whiteCounters[0]<=maxWhiteCounter && whiteCounters[1]>=minWhitePixelCounter && whiteCounters[1]<=maxWhitePixelCounter)
                        return true;
                    break;
            }//switch
            return false;
    }//isThree
    
    private boolean isFour(double[] distance, int[] pixelIndex) {
        double minDist = 9.848857801796104, maxDist = 15.297058540778355;
        double minPointDist = 12, maxPointDist = 22;
        int[] x = new int[4], y = new int[4];
        double[][] pointDist = new double[4][4];
        for (int i=0; i < 4; i++)
        {
            x[i] = pixelIndex[i] % imageSize()[0];
            y[i] = pixelIndex[i] / imageSize()[0];
        }//for_i
        //System.out.println(distance[0] + "; " + distance[1] + "; " + distance[2] + "; " + distance[3]);
        //First check if there is bad detection! (The four spots must be at aprox. the same dist from the center)
        if ( (distance[0]<=maxDist&&distance[0]>=minDist) && (distance[1]<=maxDist&&distance[1]>=minDist) && (distance[2]<=maxDist&&distance[2]>=minDist) && (distance[3]<=maxDist&&distance[3]>=minDist))
        {
            for (int i=0; i < 4; i++)
            {
                for (int j=i+1; j < 4; j++)
                {
                    pointDist[i][j] = java.lang.Math.sqrt((double)(x[i]-x[j])*(x[i]-x[j])+(double)(y[i]-y[j])*(y[i]-y[j]));
                    pointDist[j][i] = pointDist[i][j];
                    //System.out.println("dist from point ("+x[i]+","+y[i]+") to ("+x[j]+","+y[j]+"): "+pointDist[i][j]);
                }//for_j
            }//for_i
            int[] smallDistCount = new int[4];
            int[] largeDistCount = new int[4];
            for (int i=0; i < 4; i++)
            {
                for(int j=0; j < 4; j++)
                {
                    //System.out.println("dist from ("+x[i]+","+y[i]+") to ("+x[j]+","+y[j]+"): "+pointDist[i][j]);
                    if (pointDist[i][j] > minPointDist && pointDist[i][j] < maxPointDist)
                        smallDistCount[i]++; 
                    else if (pointDist[i][j] >= maxPointDist)
                        largeDistCount[i]++;
                }//for_j
            }//for_i
            
            for (int i=0; i<4; i++)
            {
                //System.out.println("smallDistCount["+i+"]: "+smallDistCount[i] + "; largeDistCount["+i+"]: "+largeDistCount[i]);
                if (!(smallDistCount[i] == 2 && largeDistCount[i] == 1))
                    return false;
            }//for_i
            return true;
        }//if
            
        return false;
    }//isFour
    
    private boolean isFive(double[] distance) {
        double minDistCenter = 0.0, maxDistCenter=2.23606797749979,minDist = 9.433981132056603, maxDist = 15.231546211727817;
        double distance1 = distance[0];
        double distance2 = distance[1];
        double distance3 = distance[2];
        double distance4 = distance[3];
        double distance5 = distance[4];
        int centerSpot=0;
        
        //System.out.println(distance1 + "; " + distance2 + "; " + distance3 + "; " + distance4 + "; " + distance5);
        //First check if there is a spot in the middle. If not, then we have a bad detection. let's try to fix it!
        if (distance1 <= maxDistCenter)
            centerSpot=1;
        else if(centerSpot==0 && distance2 <= maxDistCenter)
            centerSpot=2;
        else if(centerSpot==0 && distance3 <= maxDistCenter)
            centerSpot=3;
        else if(centerSpot==0 && distance4 <= maxDistCenter)
            centerSpot=4;
        else if(centerSpot==0 && distance5 <= maxDistCenter)
            centerSpot=5;
        else return false;
        if (centerSpot!=0)//there is a spot in the middle! cehck if the orthers are at the correct distance!
            switch(centerSpot) {
                case 1:
                    if(distance2 >= minDist && distance2 <= maxDist && distance3 >= minDist && distance3 <= maxDist && distance4 >= minDist && distance4 <= maxDist && distance5 >= minDist && distance5 <= maxDist)
                        return true;
                    break;
                case 2:
                    if(distance1 >= minDist && distance1 <= maxDist && distance3 >= minDist && distance3 <= maxDist && distance4 >= minDist && distance4 <= maxDist && distance5 >= minDist && distance5 <= maxDist)
                        return true;
                    break;
                case 3:
                    if(distance1 >= minDist && distance1 <= maxDist && distance2 >= minDist && distance2 <= maxDist && distance4 >= minDist && distance4 <= maxDist && distance5 >= minDist && distance5 <= maxDist)
                        return true;
                    break;
                case 4:
                    if(distance1 >= minDist && distance1 <= maxDist && distance2 >= minDist && distance2 <= maxDist && distance3 >= minDist && distance3 <= maxDist && distance5 >= minDist && distance5 <= maxDist)
                        return true;
                    break;
                case 5:
                    if(distance1 >= minDist && distance1 <= maxDist && distance2 >= minDist && distance2 <= maxDist && distance3 >= minDist && distance3 <= maxDist && distance4 >= minDist && distance4 <= maxDist)
                        return true;
                    break;
            }//switch
            return false;
    }//isFive
    
    private boolean isSix(double[] distance, int[] pixelIndex) {
        int dotsNear = 0, dotsFar = 0, dotsOut = 0;
        double minDistNear = 6.708203932499369, maxDistNear = 12.041594578792296, minDist = 9.848857801796104, maxDist = 15.524174696260024;
        double distance1 = distance[0];
        double distance2 = distance[1];
        double distance3 = distance[2];
        double distance4 = distance[3];
        double distance5 = distance[4];
        double distance6 = distance[5];
        
        //System.out.println(distance1 + "; " + distance2 + "; " + distance3 + "; " + distance4 + "; " + distance5 + "; " + distance6);
        
        if (distance1 >= minDistNear && distance1 <= maxDistNear)
            dotsNear++; 
        if (distance1 >= minDist && distance1 <= maxDist)
            dotsFar++;
        if (distance1 <minDistNear || (distance1 > maxDistNear && distance1 < minDist) || distance1 > maxDist)
            dotsOut++;
        if (distance2 >= minDistNear && distance2 <= maxDistNear)
            dotsNear++;
        if (distance2 >= minDist && distance2 <= maxDist)
            dotsFar++;
        if (distance2 <minDistNear || (distance2 > maxDistNear && distance2 < minDist) || distance2 > maxDist)
            dotsOut++;
        if (distance3 >= minDistNear && distance3 <= maxDistNear)
            dotsNear++;
        if (distance3 >= minDist && distance3 <= maxDist)
            dotsFar++;
        if (distance3 <minDistNear || (distance3 > maxDistNear && distance3 < minDist) || distance3 > maxDist)
            dotsOut++;
        if (distance4 >= minDistNear && distance4 <= maxDistNear)
            dotsNear++;
        if (distance4 >= minDist && distance4 <= maxDist)
            dotsFar++;
        if (distance4 <minDistNear || (distance4 > maxDistNear && distance4 < minDist) || distance4 > maxDist)
            dotsOut++;
        if (distance5 >= minDistNear && distance5 <= maxDistNear)
            dotsNear++;
        if (distance5 >= minDist && distance5 <= maxDist)
            dotsFar++;
        if (distance5 <minDistNear || (distance5 > maxDistNear && distance5 < minDist) || distance5 > maxDist)
            dotsOut++;
        if (distance6 >= minDistNear && distance6 <= maxDistNear)
            dotsNear++;
        if (distance6 >= minDist && distance6 <= maxDist)
            dotsFar++;
        if (distance6 <minDistNear || (distance6 > maxDistNear && distance6 < minDist) || distance6 > maxDist)
            dotsOut++;
        
        
        //refined to find real six's
        
        int[] nearerSpots = new int[2];
        int minWhitePixelCounter = 7;
        double minDist1 = 1000, minDist2 = 1000;
        for (int i = 0; i<6; i++)
        {
            if (distance[i] <= minDist1)
            {
                minDist1 = distance[i];
                nearerSpots[0] = i;
            }//if
        }//for_i
        for (int i = 0; i<6; i++)
        {
            if (distance[i] <= minDist2 && distance[i]>=minDist1 && i!=nearerSpots[0] 
                && countWhitePatches(pixelIndex[nearerSpots[0]],pixelIndex[i])[1] >= minWhitePixelCounter)
            {
                minDist2 = distance[i];
                nearerSpots[1] = i;
            }//if
        }//for_i
        
        int[][] points = new int[6][2];
        int imageWidth = imageSize()[0];
        for (int i = 0; i<6; i++)
        {
            points[i][0] = pixelIndex[i] % imageWidth;
            points[i][1] = pixelIndex[i] / imageWidth;
        }//for_i
        
        //System.out.println("Nearer spots are: ("+points[nearerSpots[0]][0]+","+points[nearerSpots[0]][1]+") and ("+points[nearerSpots[1]][0]+","+points[nearerSpots[1]][1]+")");
        //new we have what are supposed to be the two middle spots
        //for each one of them, there should be two spots near it and not near the other
        //for the first:
        int[] nearerSpots1 = new int[2];
        minDist1 =1000; minDist2 =1000;
        for (int i = 0; i<6 ; i++)
        {
            if (i != nearerSpots[0] && i != nearerSpots[1])
            {
                double tempDist = java.lang.Math.sqrt((double)(points[i][0]-points[nearerSpots[0]][0])*(points[i][0]-points[nearerSpots[0]][0])+(double)(points[i][1]-points[nearerSpots[0]][1])*(points[i][1]-points[nearerSpots[0]][1]));
                if (tempDist < minDist1)
                {
                    minDist1 = tempDist;
                    nearerSpots1[0] = i;
                }//if
            }//if
        }//for_i
        for (int i = 0; i<6 ; i++)
        {
            if (i != nearerSpots[0] && i != nearerSpots[1] && i!=nearerSpots1[0])
            {
                double tempDist = java.lang.Math.sqrt((double)(points[i][0]-points[nearerSpots[0]][0])*(points[i][0]-points[nearerSpots[0]][0])+(double)(points[i][1]-points[nearerSpots[0]][1])*(points[i][1]-points[nearerSpots[0]][1]));
                if (tempDist < minDist2 && tempDist >= minDist1)
                {
                    minDist2 = tempDist;
                    nearerSpots1[1] = i;
                }//if
            }//if
        }//for_i
        //System.out.println("Spots nearest to the fisrt nearerSpot: ("+points[nearerSpots1[0]][0]+","+points[nearerSpots1[0]][1]+") & ("+points[nearerSpots1[1]][0]+","+points[nearerSpots1[1]][1]+")");
        //for the second:
        int[] nearerSpots2 = new int[2];
        minDist1 =1000; minDist2 =1000;
        for (int i = 0; i<6 ; i++)
        {
            if (i != nearerSpots[0] && i != nearerSpots[1])
            {
                double tempDist = java.lang.Math.sqrt((double)(points[i][0]-points[nearerSpots[1]][0])*(points[i][0]-points[nearerSpots[1]][0])+(double)(points[i][1]-points[nearerSpots[1]][1])*(points[i][1]-points[nearerSpots[1]][1]));
                if (tempDist < minDist1)
                {
                    minDist1 = tempDist;
                    nearerSpots2[0] = i;
                }//if
            }//if
        }//for_i
        for (int i = 0; i<6 ; i++)
        {
            if (i != nearerSpots[0] && i != nearerSpots[1] && i!=nearerSpots2[0])
            {
                double tempDist = java.lang.Math.sqrt((double)(points[i][0]-points[nearerSpots[1]][0])*(points[i][0]-points[nearerSpots[1]][0])+(double)(points[i][1]-points[nearerSpots[1]][1])*(points[i][1]-points[nearerSpots[1]][1]));
                if (tempDist < minDist2 && tempDist >= minDist1)
                {
                    minDist2 = tempDist;
                    nearerSpots2[1] = i;
                }//if
            }//if
        }//for_i
        //System.out.println("Spots nearest to the second nearerSpot: ("+points[nearerSpots2[0]][0]+","+points[nearerSpots2[0]][1]+") & ("+points[nearerSpots2[1]][0]+","+points[nearerSpots2[1]][1]+")");
        //Check if there is any overlap between the two sets of spots nearer to the two central spots
        for (int i=0; i<2; i++)
        {
            for (int j = 0; j<2;j++)
            {
                if(nearerSpots1[i] == nearerSpots2[j])
                {
                    //System.out.println(nearerSpots1[i] +"=="+nearerSpots2[j]);
                    return false;
                }//if
            }//for_j
        }//for_i
        
        if (dotsOut == 0 && dotsFar >= 4 && dotsFar <=6)
            if (dotsNear >= 2)
            {
                /*System.out.println("Possible Six! Checking Slopes!");
                for (int i = 0; i < 6; i++)
                {
                    if (numberOfSimilarSlopes[i] >= 4)
                        return false;
                }//for_i
                 */
                return true;
            }
        return false;
        
    }//isSix
    
    /**
     *Misc
     */
    private int[] getNearestBlackPixel(int pixelIndex,int raio)
    {
        int imageWidth = imageSize()[0], x = pixelIndex%imageWidth, y = pixelIndex/imageWidth;
        int[] coords = new int[2];
        int horMultiplier, verMultiplier;
        for (int i=0; i < raio; i++)    //we're expecting it to be near
        {
            for (int j=0; j < 8; j++)
            {
                if (j==7||j==0||j==1) horMultiplier = -1;
                else if (j==6||j==2) horMultiplier = 0;
                else horMultiplier = +1;
                if (j==1||j==2||j==3) verMultiplier=+1;
                else if (j==0||j==4) verMultiplier=0;
                else verMultiplier = -1;
                
                coords[0] = x + horMultiplier*i; coords[1] = y + verMultiplier*i;
                if ((BWPixels[coords[0]+coords[1]*imageWidth]&0x000000ff)==0)
                    return coords;
            }//for_j
        }//for_i
        return coords;
    }//getNearestBlackPixel
    
    private int[] buildArrayXY(int[] indexSource, String XY)
    {
        int[] returnArray = new int[indexSource.length];
        if ( XY.equalsIgnoreCase("x"))
        {
            for (int i = 0; i < indexSource.length; i++)
            {
                returnArray[i] = centers[indexSource[i]][0];
                //System.out.println(returnArray[i]);
            }//for_i
        }//if
        else if (XY.equalsIgnoreCase("y"))
        {
            for (int i = 0; i < indexSource.length; i++)
            {
                returnArray[i] = centers[indexSource[i]][1];
                //System.out.println(returnArray[i]);
            }//for_i
        }//else_if
        return returnArray;
    }//buildArrayXY
    
    /**
     *returns a int[]
     *int[0] ==> whitePatchCounter
     *int[1] ==> whitePixelCounter
     */
    private int[] countWhitePatches(int startPixelIndex, int endPixelIndex)
    {
        int imageWidth = imageSize()[0], whitePatchCounter=0, whitePixelCounter=0;
        int startX = startPixelIndex % imageWidth, endX = endPixelIndex % imageWidth;
        int startY = startPixelIndex / imageWidth, endY = endPixelIndex / imageWidth;
        int[] firstPoint= new int[2], lastPoint = new int[2];
        if (abs(endX-startX) >= abs(endY-startY))  //chooses the path with more pixels for greatest accuracy
        {
            if (startX > endX)
            {
                startX = endX;
                endX = startPixelIndex % imageWidth; 
                startY = endY;
                endY = startPixelIndex / imageWidth;
            }//else
            boolean isWhite = false, isPreviousWhite = false;
            double slope = (double)(endY - startY)/(double)(endX - startX);
            int pixelIndex, y;
            
            //System.out.println("startPoint:" + startX + ", " + startY + ", endPoint:" + endX + ", " + endY);
            //System.out.println("slope: " + slope);
            
            for (int x=startX; x <= endX; x++)
            {
                y = startY + (int)Math.round(slope*(x - startX));
                //System.out.println("currentPoint: " + x + ", " + y);
                pixelIndex = y * imageWidth + x;
                if ((BWPixels[pixelIndex] & 0x000000ff)==255)
                {
                    isWhite = true;
                    whitePixelCounter++;
                }//if
                else
                {
                    isWhite=false;
                    if (isPreviousWhite)
                    {
                        lastPoint[0] = x; lastPoint[1] = y;
                    }//if
                }//else
                if (!isPreviousWhite & isWhite)
                {
                    whitePatchCounter++;
                    firstPoint[0] = x; firstPoint[1] = y;
                }//if
                isPreviousWhite = isWhite;
            }//for_x
        }//if
        else
        {
            if (startY > endY)
            {
                startY = endY;
                endY = startPixelIndex / imageWidth;
                startX = endPixelIndex % imageWidth;
                endX = startPixelIndex % imageWidth; 
            }//else
            boolean isWhite = false, isPreviousWhite = false;
            double slope = (double)(endY - startY)/(double)(endX - startX);
            int pixelIndex, x;
            //System.out.println("startPoint:" + startX + ", " + startY + ", endPoint:" + endX + ", " + endY);
            //System.out.println("slope: " + slope);
            for (int y=startY; y <= endY; y++)
            {
                x = (int)(((double)y - (double)startY)/slope)+startX;
                //y = startY + (int)Math.round(slope*(x - startX));
                //System.out.println("currentPoint: " + x + ", " + y);
                pixelIndex = y * imageWidth + x;
                if ((BWPixels[pixelIndex] & 0x000000ff)==255)
                {
                    isWhite = true;
                    whitePixelCounter++;
                }//if
                else
                {
                    isWhite=false;
                    if (isPreviousWhite)
                    {
                        lastPoint[0] = x; lastPoint[1] = y;
                    }//if
                }//else
                if (!isPreviousWhite & isWhite)
                {
                    whitePatchCounter++;
                    firstPoint[0] = x; firstPoint[1] = y;
                }//if
                isPreviousWhite = isWhite;
            }//for_y
        }//else
        //System.out.println("whitePixelCounter:"+ whitePixelCounter + "; whitePatchCounter: "+ whitePatchCounter);
        int[] toReturn = new int[2];
        toReturn[0] = whitePatchCounter;// toReturn[1] = whitePixelCounter;
        toReturn[1] = max(whitePixelCounter,(int)java.lang.Math.round(java.lang.Math.sqrt((double)(firstPoint[0]-lastPoint[0])*(firstPoint[0]-lastPoint[0])+(double)(firstPoint[1]-lastPoint[1])*(firstPoint[1]-lastPoint[1]))));
        return toReturn;
    }//countWhitePatches
    
    
    /**
     *Tests and Attempts
     */
    /**
     *Creates a die without the spots proposed to be removed and checks if the result is an ok die.
     *If so, returns true, else, returns false
     */
    private boolean tryRemove(java.util.Vector[] vDie, int vDieIndex, int spotsExpected, int[] spotsToRemove, int[] pixelIndex, int raio, int maxClusterSize)
    {
        int[] vDieTest = new int[spotsExpected];
        int[] xTest = new int[spotsExpected], yTest = new int[spotsExpected], pixelIndexTest = new int[spotsExpected];
        double[] distanceTest;
        int testPos = 0;
        int spotCount = vDie[vDieIndex].size()-1;
        for (int j=0; j<spotCount; j++)
        {
            boolean temp=true;
            for (int i=0; i < spotsToRemove.length; i++)
                if (j == spotsToRemove[i])
                    temp =false;
            
            if(temp)
            {
                vDieTest[testPos] = Integer.parseInt(vDie[vDieIndex].get(j).toString());
                xTest[testPos] = centers[vDieTest[testPos]][0]; yTest[testPos] = centers[vDieTest[testPos]][1];
                pixelIndexTest[testPos] = pixelIndex[j];
                //System.out.println(vDieTest[testPos] + "; " + xTest[testPos] + ", " + yTest[testPos]);
                testPos++;
            }//if
        }//for_j
        
        int x_mean = 0, y_mean = 0;
        for (int j = 0; j<xTest.length;j++)
        {
            x_mean += xTest[j]; y_mean += yTest[j];
        }//for_j
        x_mean = x_mean / xTest.length; y_mean = y_mean / yTest.length;
        distanceTest = new double[spotsExpected];
        for(int indexc=0; indexc < distanceTest.length; indexc++)
        {
            distanceTest[indexc] = java.lang.Math.sqrt((double)(x_mean-xTest[indexc])*(x_mean-xTest[indexc])+(double)(y_mean-yTest[indexc])*(y_mean-yTest[indexc]));
            //System.out.println(distanceTest[indexc]);
        }
        if (checkSpotCount(distanceTest, spotsExpected, pixelIndexTest, maxClusterSize))
        {
            return true;
        }//if
        else
        {
            return false;
        }//else
    }//tryRemove
    
    
    /**
     * Spans a number of spots and removes them from the die if the result is ok.
     */
    private java.util.Vector testRemove(java.util.Vector[] vDie, int vDieIndex, int testDieSize, int[] pixelIndex, int raio, int maxClusterSize, int[] x, int[] y, int externalCounter)
    {
        int spotCount = vDie[vDieIndex].size()-1;
        int dieDiff = spotCount - testDieSize;
        int numberOfTests = combinations (spotCount, testDieSize);
        //System.out.println("Number Of Tests: " + numberOfTests + " = " + spotCount + "_C_" + testDieSize);
        //for (int testIndex=0; testIndex< numberOfTests; testIndex++)
        //{
        
        if (spotCount-testDieSize > 4)
        {
            //System.out.println("Die with an excess of more than 4 spots! Ignoring it Completely!!");
            java.util.Vector vDieXYpI = new java.util.Vector(4);
            vDieXYpI.add(0,vDie);
            vDieXYpI.add(1,x);
            vDieXYpI.add(2,y);
            vDieXYpI.add(3,pixelIndex);
            
            return vDieXYpI;
        }//if
            
        
            int[] vDieTest = new int[testDieSize];
            int[] xTest = new int[testDieSize], yTest = new int[testDieSize];
            double[] distanceTest;
            int[] spotsToRemove = new int[dieDiff];
            
            for (int indexa = 0; indexa < spotCount; indexa++)
            {
                spotsToRemove[0] = indexa;
                if (dieDiff == 1)
                {
                    if (tryRemove(vDie, vDieIndex, testDieSize, spotsToRemove, pixelIndex, raio, maxClusterSize))
                    {
                        java.util.Vector vDieXYpI = new java.util.Vector(4);
                        java.util.Vector isSpotVector = isSpot(pixelIndex[spotsToRemove[0]], raio, true, false);
                        int counterSum = ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                        
                        if (testDieSize == 6 && externalCounter > 1 && counterSum < 1)
                        {
                            vDie = removeSpot(vDie, vDieIndex, indexa);
                            x = removeArrayEntry(x, indexa);
                            y = removeArrayEntry(y, indexa);
                            pixelIndex = removeArrayEntry(pixelIndex, indexa);
                        }//if
                        else
                        {
                            //System.out.println("Breaking apart cluster " + vDieIndex);
                            //System.out.println("Spot Being changed: "+vDie[vDieIndex].get(indexa));
                            vDie[cluster_counter] = new java.util.Vector(center_counter);
                            vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexa));
                            vDie[cluster_counter].add(Boolean.FALSE);
                            vDie[vDieIndex].remove(indexa);
                            x = removeArrayEntry(x, indexa);
                            y = removeArrayEntry(y, indexa);
                            pixelIndex = removeArrayEntry(pixelIndex, indexa);
                            vClusters.addElement(vDie[cluster_counter]);
                            cluster_counter++;
                            clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                            java.util.Vector checkSpotsResult = checkSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
                            //System.out.println("vDie in TestRemove:"+((java.util.Vector[])checkSpotsResult.get(0))[vDieIndex]);
                            if (((Boolean)checkSpotsResult.get(4)).equals(Boolean.TRUE))
                            {
                                //System.out.println("Setting Die "+vDieIndex + " as TRUE!");
                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                            }//if
                            else
                            {
                                vDie = (java.util.Vector[])checkSpotsResult.get(0);
                                x = (int[])checkSpotsResult.get(1);
                                y = (int[])checkSpotsResult.get(2);
                                pixelIndex = (int[])checkSpotsResult.get(3);
                            }//else
                            clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                        }//else
                        
                        //System.out.println("Setting Die "+vDieIndex + " as TRUE!");
                        //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                        
                        vDieXYpI.add(0,vDie);
                        vDieXYpI.add(1,x);
                        vDieXYpI.add(2,y);
                        vDieXYpI.add(3,pixelIndex);
                        
                        return vDieXYpI;
                    }//if
                }//if
                else
                {
                    for (int indexb = indexa+1; indexb < spotCount; indexb++)
                    {
                        spotsToRemove[1] = indexb;
                        if (dieDiff == 2)
                        {
                            if (tryRemove(vDie, vDieIndex, testDieSize, spotsToRemove, pixelIndex, raio, maxClusterSize))
                            {
                                java.util.Vector vDieXYpI = new java.util.Vector(4);
                                java.util.Vector isSpotVector = isSpot(pixelIndex[spotsToRemove[0]], raio, true, false);
                                int counterSum = ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                isSpotVector = isSpot(pixelIndex[spotsToRemove[1]], raio, true, false);
                                counterSum += ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                if (testDieSize == 6 && externalCounter > 1)
                                {
                                    vDie = removeSpot(vDie, vDieIndex, indexb);
                                    x = removeArrayEntry(x, indexb);
                                    y = removeArrayEntry(y, indexb);
                                    pixelIndex = removeArrayEntry(pixelIndex, indexb);
                                    
                                    vDie = removeSpot(vDie, vDieIndex, indexa);
                                    x = removeArrayEntry(x, indexa);
                                    y = removeArrayEntry(y, indexa);
                                    pixelIndex = removeArrayEntry(pixelIndex, indexa);
                                }//if
                                else
                                {
                                    //System.out.println("Breaking apart cluster " + vDieIndex);
                                    //System.out.println("Spots Being changed: "+vDie[vDieIndex].get(indexa)+"; "+vDie[vDieIndex].get(indexb));
                                    vDie[cluster_counter] = new java.util.Vector(center_counter);
                                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexb));
                                    vDie[vDieIndex].remove(indexb);
                                    x = removeArrayEntry(x, indexb);
                                    y = removeArrayEntry(y, indexb);
                                    pixelIndex = removeArrayEntry(pixelIndex, indexb);
                                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexa));
                                    
                                    vDie[cluster_counter].add(Boolean.FALSE);
                                    
                                    vDie[vDieIndex].remove(indexa);
                                    x = removeArrayEntry(x, indexa);
                                    y = removeArrayEntry(y, indexa);
                                    pixelIndex = removeArrayEntry(pixelIndex, indexa);
                                    vClusters.addElement(vDie[cluster_counter]);
                                    cluster_counter++;
                                    clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                                    java.util.Vector checkSpotsResult = checkSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
                                    if (((Boolean)checkSpotsResult.get(4)).equals(Boolean.TRUE))
                                    {
                                        //System.out.println("Setting Die "+vDieIndex + " as TRUE!");
                                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                    }//if
                                    else
                                    {
                                        vDie = (java.util.Vector[])checkSpotsResult.get(0);
                                        x = (int[])checkSpotsResult.get(1);
                                        y = (int[])checkSpotsResult.get(2);
                                        pixelIndex = (int[])checkSpotsResult.get(3);
                                    }//else
                                    clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                                }//else
                                
                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                
                                vDieXYpI.add(0,vDie);
                                vDieXYpI.add(1,x);
                                vDieXYpI.add(2,y);
                                vDieXYpI.add(3,pixelIndex);
                                
                                return vDieXYpI;
                            }//if
                        }//if
                        else
                        {
                            for (int indexc = indexb+1; indexc < spotCount; indexc++)
                            {
                                spotsToRemove[2] = indexc;
                                if (dieDiff == 3)
                                {
                                    if (tryRemove(vDie, vDieIndex, testDieSize, spotsToRemove, pixelIndex, raio, maxClusterSize))
                                    {
                                        java.util.Vector vDieXYpI = new java.util.Vector(4);
                                        java.util.Vector isSpotVector = isSpot(pixelIndex[spotsToRemove[0]], raio, true, false);
                                        int counterSum = ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                        isSpotVector = isSpot(pixelIndex[spotsToRemove[1]], raio, true, false);
                                        counterSum += ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                        isSpotVector = isSpot(pixelIndex[spotsToRemove[2]], raio, true, false);
                                        counterSum += ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                        if (testDieSize == 6 && externalCounter > 1)
                                        {
                                            vDie = removeSpot(vDie, vDieIndex, indexc);
                                            x = removeArrayEntry(x, indexc);
                                            y = removeArrayEntry(y, indexc);
                                            pixelIndex = removeArrayEntry(pixelIndex, indexc);
                                            
                                            vDie = removeSpot(vDie, vDieIndex, indexb);
                                            x = removeArrayEntry(x, indexb);
                                            y = removeArrayEntry(y, indexb);
                                            pixelIndex = removeArrayEntry(pixelIndex, indexb);
                                            
                                            vDie = removeSpot(vDie, vDieIndex, indexa);
                                            x = removeArrayEntry(x, indexa);
                                            y = removeArrayEntry(y, indexa);
                                            pixelIndex = removeArrayEntry(pixelIndex, indexa);
                                        }//if
                                        else
                                        {
                                            //System.out.println("Breaking apart cluster " + vDieIndex);
                                            //System.out.println("Spots Being changed: "+vDie[vDieIndex].get(indexa)+"; "+vDie[vDieIndex].get(indexb)+"; "+vDie[vDieIndex].get(indexc));
                                            vDie[cluster_counter] = new java.util.Vector(center_counter);
                                            vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexc));
                                            vDie[vDieIndex].remove(indexc);
                                            x = removeArrayEntry(x, indexc);
                                            y = removeArrayEntry(y, indexc);
                                            pixelIndex = removeArrayEntry(pixelIndex, indexc);
                                            vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexb));
                                            vDie[vDieIndex].remove(indexb);
                                            x = removeArrayEntry(x, indexb);
                                            y = removeArrayEntry(y, indexb);
                                            pixelIndex = removeArrayEntry(pixelIndex, indexb);
                                            vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexa));
                                            
                                            vDie[cluster_counter].add(Boolean.FALSE);
                                            
                                            vDie[vDieIndex].remove(indexa);
                                            x = removeArrayEntry(x, indexa);
                                            y = removeArrayEntry(y, indexa);
                                            pixelIndex = removeArrayEntry(pixelIndex, indexa);
                                            vClusters.addElement(vDie[cluster_counter]);
                                            
                                            cluster_counter++;
                                            clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                                            
                                            java.util.Vector checkSpotsResult = checkSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
                                            
                                            if (((Boolean)checkSpotsResult.get(4)).equals(Boolean.TRUE))
                                            {
                                                //System.out.println("Setting Die "+vDieIndex + " as TRUE!");
                                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                            }//if
                                            else
                                            {
                                                vDie = (java.util.Vector[])checkSpotsResult.get(0);
                                                x = (int[])checkSpotsResult.get(1);
                                                y = (int[])checkSpotsResult.get(2);
                                                pixelIndex = (int[])checkSpotsResult.get(3);
                                            }//else
                                            //cluster_counter++;
                                            clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                                        }//else
                                        
                                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                        
                                        vDieXYpI.add(0,vDie);
                                        vDieXYpI.add(1,x);
                                        vDieXYpI.add(2,y);
                                        vDieXYpI.add(3,pixelIndex);
                                        
                                        return vDieXYpI;
                                    }//if
                                }//if
                                else
                                {
                                    for (int indexd = indexc+1; indexd < spotCount; indexd++)
                                    {   
                                        spotsToRemove[3] = indexd;
                                        if (dieDiff == 4)
                                        {
                                            if (tryRemove(vDie, vDieIndex, testDieSize, spotsToRemove, pixelIndex, raio, maxClusterSize))
                                            {
                                                java.util.Vector vDieXYpI = new java.util.Vector(4);
                                                java.util.Vector isSpotVector = isSpot(pixelIndex[spotsToRemove[0]], raio, true, false);
                                                int counterSum = ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                                isSpotVector = isSpot(pixelIndex[spotsToRemove[1]], raio, true, false);
                                                counterSum += ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                                isSpotVector = isSpot(pixelIndex[spotsToRemove[2]], raio, true, false);
                                                counterSum += ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                                isSpotVector = isSpot(pixelIndex[spotsToRemove[3]], raio, true, false);
                                                counterSum += ((Integer)isSpotVector.get(1)).intValue() + ((Integer)isSpotVector.get(2)).intValue() + ((Integer)isSpotVector.get(3)).intValue();
                                                if (testDieSize == 6 && externalCounter > 1)
                                                {
                                                    vDie = removeSpot(vDie, vDieIndex, indexd);
                                                    x = removeArrayEntry(x, indexd);
                                                    y = removeArrayEntry(y, indexd);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexd);
                                                    
                                                    vDie = removeSpot(vDie, vDieIndex, indexc);
                                                    x = removeArrayEntry(x, indexc);
                                                    y = removeArrayEntry(y, indexc);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexc);
                                                    
                                                    vDie = removeSpot(vDie, vDieIndex, indexb);
                                                    x = removeArrayEntry(x, indexb);
                                                    y = removeArrayEntry(y, indexb);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexb);
                                                    
                                                    vDie = removeSpot(vDie, vDieIndex, indexa);
                                                    x = removeArrayEntry(x, indexa);
                                                    y = removeArrayEntry(y, indexa);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexa);
                                                }//if
                                                else
                                                {
                                                    //System.out.println("Breaking apart cluster " + vDieIndex);
                                                    //System.out.println("Spots Being changed: "+vDie[vDieIndex].get(indexa)+"; "+vDie[vDieIndex].get(indexb)+"; "+vDie[vDieIndex].get(indexc)+"; "+vDie[vDieIndex].get(indexd));
                                                    vDie[cluster_counter] = new java.util.Vector(center_counter);
                                                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexd));
                                                    vDie[vDieIndex].remove(indexd);
                                                    x = removeArrayEntry(x, indexd);
                                                    y = removeArrayEntry(y, indexd);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexd);
                                                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexc));
                                                    vDie[vDieIndex].remove(indexc);
                                                    x = removeArrayEntry(x, indexc);
                                                    y = removeArrayEntry(y, indexc);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexc);
                                                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexb));
                                                    vDie[vDieIndex].remove(indexb);
                                                    x = removeArrayEntry(x, indexb);
                                                    y = removeArrayEntry(y, indexb);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexb);
                                                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(indexa));
                                                    
                                                    vDie[cluster_counter].add(Boolean.FALSE);
                                                    
                                                    vDie[vDieIndex].remove(indexa);
                                                    x = removeArrayEntry(x, indexa);
                                                    y = removeArrayEntry(y, indexa);
                                                    pixelIndex = removeArrayEntry(pixelIndex, indexa);
                                                    vClusters.addElement(vDie[cluster_counter]);
                                                    cluster_counter++;
                                                    clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                                                    java.util.Vector checkSpotsResult = checkSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
                                                    if (((Boolean)checkSpotsResult.get(4)).equals(Boolean.TRUE))
                                                    {
                                                        //System.out.println("Setting Die "+vDieIndex + " as TRUE!");
                                                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                                    }//if
                                                    else
                                                    {
                                                        vDie = (java.util.Vector[])checkSpotsResult.get(0);
                                                        x = (int[])checkSpotsResult.get(1);
                                                        y = (int[])checkSpotsResult.get(2);
                                                        pixelIndex = (int[])checkSpotsResult.get(3);
                                                    }//else
                                                    clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                                                }//else
                                                
                                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                                
                                                vDieXYpI.add(0,vDie);
                                                vDieXYpI.add(1,x);
                                                vDieXYpI.add(2,y);
                                                vDieXYpI.add(3,pixelIndex);
                                                
                                                return vDieXYpI;
                                            }//if
                                        }//if
                                        else
                                        {
                                            //System.out.println("Die with an excess of more than 4 spots! Ignoring it!!");
                                        }//else
                                    }//for_indexd
                                }//else
                            }//for_indexc
                        }//else
                    }//for_indexb
                }//else
            }//for_indexa
        //}//for_testIndex
        
        java.util.Vector vDieXYpI = new java.util.Vector(4);
        vDieXYpI.add(0,vDie);
        vDieXYpI.add(1,x);
        vDieXYpI.add(2,y);
        vDieXYpI.add(3,pixelIndex);
        
        return vDieXYpI;
    }//testRemove
    
    private java.util.Vector checkForSpotsThatReallyBelongToThis(java.util.Vector[] vDie, int vDieIndex, int[] x, int[] y, int[] pixelIndex, int maxClusterSize)    {
        int spotCount = vDie[vDieIndex].size()-1;
        java.util.Vector toReturn = new java.util.Vector(5);
        toReturn.add(0,vDie);
        toReturn.add(1, x);
        toReturn.add(2, y);
        toReturn.add(3, pixelIndex);
        toReturn.add(4, new Integer(vDieIndex));
        double maxTwoDist = maxClusterSize/2.1257;// 15.524174696260024;
            //if we have a two, it may be a part of a six (or a five, but this is unlikely to happen)
        double maxThreeDist = maxClusterSize/2.2195;//2.62;
            //if we have a three, it may be part of a five! so we search for any spot that can is near enough
        double maxFourDist = maxClusterSize/1.83;
        double maxDist;
        if (spotCount == 2) maxDist = maxTwoDist;
        else if (spotCount == 3) maxDist = maxThreeDist;
        else return toReturn;
        
        for (int i=0; i < cluster_counter; i++)
        {
            if (i != vDieIndex && vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE))
            {
                for (int j = 0; j < vDie[i].size()-1; j++)
                {
                    //if (spotCount == 4)
                    //    maxDist = maxFourDist;
                    int x_temp = centers[((Integer)vDie[i].get(j)).intValue()][0];
                    int y_temp = centers[((Integer)vDie[i].get(j)).intValue()][1];
                    double dist = java.lang.Math.sqrt((double)(x_temp-clusterCenters[vDieIndex][0])*(x_temp-clusterCenters[vDieIndex][0])+(double)(y_temp-clusterCenters[vDieIndex][1])*(y_temp-clusterCenters[vDieIndex][1]));
                    //System.out.println("Dist from center of "+spotCount+"("+clusterCenters[vDieIndex][0]+","+clusterCenters[vDieIndex][1]+") to spot "+vDie[i].get(j)+"("+x_temp+","+y_temp+")"+": "+dist);
                    if (dist < maxDist)
                    {
                        vDie = changeSpot(vDie, i, j, vDieIndex);
                        x = addArrayEntry(x, x_temp);
                        y = addArrayEntry(y, y_temp);
                        pixelIndex = addArrayEntry(pixelIndex, x_temp + y_temp*imageSize()[0]);
                        spotCount++;
                        j--;
                        if (vDie[i].size() == 1)
                        {
                            //System.out.println("Die "+i+" has been depleted!");
                            if (vDieIndex != cluster_counter-1)
                                for (int ii=i+1; ii<vDie.length;ii++)
                                {
                                    vDie[ii-1] = vDie[ii];
                                }//for_i
                            
                            vDie[cluster_counter-1] = null;
                            
                            cluster_counter--;
                            i--;
                            if (i==vDieIndex)
                                i++;
                            
                            //clusterCenters = getClusterCenters(vDie);
                            if (vDieIndex > i)
                                vDieIndex--;
                            
                            if (i >= cluster_counter)
                                break;
                        }//if
                    }//if
                }//for_j
            }//if
        }//for_i
        toReturn.set(0,vDie);
        toReturn.set(1, x);
        toReturn.set(2, y);
        toReturn.set(3, pixelIndex);
        toReturn.set(4, new Integer(vDieIndex));
        return toReturn;
    }//checkForSpotsThatReallyBelongToThis
    
    
    /**
     *
     *Die Checking This is the core of post processing
     *
     */
    /*This only works for dies with 3 or more spots detected. twos and ones are separate cases!*/
    private java.util.Vector checkDie(java.util.Vector[] vDie, int vDieIndex, int[][] centers, double[] info, int maxClusterSize, int raio, int insideCluster) {
        
        
        if (vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE))
        {
            java.util.Vector toReturn = new java.util.Vector(2);
            toReturn.add(0, vDie);
            toReturn.add(1, Boolean.TRUE);
            return toReturn;
        }//if
        
        int[] imSize = imageSize();
        int imageWidth = imSize[0];
        int x_mean=0, y_mean=0, spotCount = vDie[vDieIndex].size()-1;
        int[] x=new int[spotCount];
        int[] y=new int[spotCount];
        int[] centerIndex=new int[spotCount];
        
        double[] media=new double[spotCount];
        double[] distance=new double[spotCount];
        
        double maxmaxDist = (double)maxClusterSize/2. * 1.196;//*1.45;//
        
        clusterCenters = getClusterCenters(vDie);
        x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
        //System.out.println("mean: " + x_mean + ", " + y_mean);
        try {
            for(int index=0; index < spotCount; index++) {
                centerIndex[index] = Integer.parseInt(vDie[vDieIndex].get(index).toString());
                x[index] = centers[centerIndex[index]][0]; y[index] = centers[centerIndex[index]][1];
                //System.out.println(x[index] + "; " + y[index]);
                media[index] = info[centerIndex[index]];
                distance[index] = java.lang.Math.sqrt((double)(x_mean-x[index])*(x_mean-x[index])+(double)(y_mean-y[index])*(y_mean-y[index]));
            }
        }
        catch(NumberFormatException e){}
        
        
        //First check if this is a spotCount. If not, then we have a bad detection. let's try to fix it!
        //if (!checkSpotCount(distance, spotCount, new int[1], 0,0))  //the three last params in here are irrelevant because we never have a one to investigate here!
        distance = new double[spotCount];
        int[] pixelIndex = new int[spotCount];
        for(int indexa=0; indexa < distance.length; indexa++)
            distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
        
        pixelIndex = new int[spotCount];
        
        for (int index=0; index<spotCount; index++)
        {
            pixelIndex[index] = y[index] * imageWidth + x[index];
        }//for
        
        java.util.Vector toReturn = new java.util.Vector(2);
        
        int whileCounter = 0;
        boolean isSpotCountOK = checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize);
        boolean areSpotsOK = true;
        for (int i = 0; i < spotCount && areSpotsOK; i++)
        {
            areSpotsOK = ((Boolean)isSpot(pixelIndex[i], raio, vDie[vDieIndex].size()-1>=6?true:false, vDie[vDieIndex].size()-1==1?true:false).get(0)).booleanValue();
        }//for_i
        int maxWhileCounter = 6;
        //System.out.println("SpotCount: "+spotCount);
        while (spotCount > 0 && (!isSpotCountOK || !areSpotsOK) && whileCounter < maxWhileCounter)
        {
            //if(!isSpotCountOK)System.out.println("[0]spotCount check Failed!! spotCount: " + spotCount);
            //if(!areSpotsOK)System.out.println("[0a]at least one spot is not ok!!");
            if (spotCount == 1)
            {
                //System.out.println("Checking die with one spot after removing others from it!");
                pixelIndex = new int[1];
                pixelIndex[0] = centers[((Integer)vDie[vDieIndex].get(0)).intValue()][0] + centers[((Integer)vDie[vDieIndex].get(0)).intValue()][1] * imageWidth;
                java.util.Vector checkDie1Vector;
                if (isOne(pixelIndex,maxClusterSize))
                {
                    checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                }//if
                else
                    checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, false);
                if (checkDie1Vector.get(1).equals(Boolean.TRUE))
                    checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                
                /*vDie = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, false);
                if (!(vDie[vDieIndex]==null) && vDie[vDieIndex].size() == 2)
                    vDie = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, ((Boolean)vDie[vDieIndex].get(vDie[vDieIndex].size()-1)).booleanValue());
                */
                toReturn.add(0,(java.util.Vector[])checkDie1Vector.get(0));
                toReturn.add(1,(Boolean)checkDie1Vector.get(1));
                return toReturn;
                //return vDie;
            }//if
            else if (spotCount == 2)
            {
                double minDist = 10.770329614269007, maxDist = 15.811388300841896;//13.6;
                double[][] distMin_cluster = new double[2][2];
                
                clusterCenters = getClusterCenters(vDie);
                
                try {
                    centerIndex[0] = Integer.parseInt(vDie[vDieIndex].get(0).toString());
                    centerIndex[1] = Integer.parseInt(vDie[vDieIndex].get(1).toString());
                }//try
                catch(NumberFormatException e){}
                
                x[0] = centers[centerIndex[0]][0]; y[0] = centers[centerIndex[0]][1];
                x[1] = centers[centerIndex[1]][0]; y[1] = centers[centerIndex[1]][1];
                media[0] = info[centerIndex[0]]; media[1] = info[centerIndex[1]];
                
                //System.out.println(x[0] + ", " + y[0] + "\n" + x[1] + ", " + y[1]);
                
                x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                
                //check if every dot belongs to this die
                distance[0] = java.lang.Math.sqrt((double)(x_mean-x[0])*(x_mean-x[0])+(double)(y_mean-y[0])*(y_mean-y[0]));
                distance[1] = java.lang.Math.sqrt((double)(x_mean-x[1])*(x_mean-x[1])+(double)(y_mean-y[1])*(y_mean-y[1]));
                
                //System.out.println(distance[0] + ", " + distance[1]);
                
                distMin_cluster[0][0] = 1000; distMin_cluster[1][0]=1000;
                
                pixelIndex[0] = y[0] * imageWidth + x[0]; pixelIndex[1] = y[1] * imageWidth + x[1];
                boolean isProbableSix0 = false, isProbableSix1 = false;
                
                int maxDistForConsecutiveSpotsInSix = 10, minWhite = 2;
                //check if this spot can belong to some die that may be a six
                for (int i=0; i < cluster_counter; i++)
                {
                    for (int j=0; j < vDie[i].size()-1; j++)
                    {
                        int x_temp = centers[((Integer)vDie[i].get(j)).intValue()][0], y_temp = centers[((Integer)vDie[i].get(j)).intValue()][1];
                        int startPixelIndex = x_temp + y_temp*imageWidth;
                        if ( ((Integer)vDie[i].get(j)).intValue() != ((Integer)vDie[vDieIndex].get(0)).intValue() && vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE))
                        {
                            //System.out.println(((Integer)vDie[i].get(j)).intValue() +"!="+((Integer)vDie[vDieIndex].get(0)).intValue() );
                            //System.out.println("vDie["+i+"], spot("+j+")" );
                            int endPixelIndex = x[0] + y[0]*imageWidth;
                            int[] whitePatches = countWhitePatches(startPixelIndex, endPixelIndex);
                            double tempDist = java.lang.Math.sqrt((double)(x[0]-x_temp)*(x[0]-x_temp)+(double)(y[0]-y_temp)*(y[0]-y_temp));
                            //check distance between cluster centers so we can say  that this is not a six.
                            int maxDistForSingleCluster = (int)((double)maxClusterSize/1.22);
                            tempDist = java.lang.Math.sqrt((double)(centers[((Integer)vDie[vDieIndex].get(0)).intValue()][0] - centers[((Integer)vDie[i].get(j)).intValue()][0])*(centers[((Integer)vDie[vDieIndex].get(0)).intValue()][0] - centers[((Integer)vDie[i].get(j)).intValue()][0])+(double)(centers[((Integer)vDie[vDieIndex].get(0)).intValue()][1] - centers[((Integer)vDie[i].get(j)).intValue()][1])*(centers[((Integer)vDie[vDieIndex].get(0)).intValue()][1] - centers[((Integer)vDie[i].get(j)).intValue()][1]));
                            //System.out.println("tempDist:" + tempDist + ", max:" + maxDistForSingleCluster + ", spot:"+ vDie[i].get(j) + ", whitePatches:"+whitePatches[0]+", whiteCount:"+whitePatches[1]+"<"+minWhite);
                            if (tempDist < maxDistForConsecutiveSpotsInSix && (whitePatches[0] <= 1 && whitePatches[1] <= minWhite) && (tempDist < maxDistForSingleCluster && vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE)))
                            {
                                isProbableSix0 = true;
                                //System.out.println("Is Probable Six 0");
                            }//if
                        }//if
                        if ( ((Integer)vDie[i].get(j)).intValue() != ((Integer)vDie[vDieIndex].get(1)).intValue()  && vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE))
                        {
                            //System.out.println("vDie["+i+"], spot("+j+")" );
                            int endPixelIndex = x[1] + y[1]*imageWidth;
                            int[] whitePatches = countWhitePatches(startPixelIndex, endPixelIndex);
                            double tempDist = java.lang.Math.sqrt((double)(x[1]-x_temp)*(x[1]-x_temp)+(double)(y[1]-y_temp)*(y[1]-y_temp));
                            //check distance between cluster centers so we can say  that this is not a six.
                            int maxDistForSingleCluster = (int)((double)maxClusterSize/1.22);
                            tempDist = java.lang.Math.sqrt((double)(x[1] - x_temp)*(x[1]-x_temp)+(double)(y[1]-y_temp)*(y[1]-y_temp));
                            //System.out.println("tempDist:" + tempDist + ", max:" + maxDistForSingleCluster + ", cluster:" + i + "=="+vDie[i]);
                            //System.out.println("tempDist:" + tempDist + ", max:" + maxDistForSingleCluster + ", spot:"+ vDie[i].get(j) + ", whitePatches:"+whitePatches[0]+", whiteCount:"+whitePatches[1]+"<"+minWhite);
                            if (tempDist < maxDistForConsecutiveSpotsInSix && (whitePatches[0] <= 1 && whitePatches[1] <= minWhite) && (tempDist < maxDistForSingleCluster && vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE)))
                            {
                                isProbableSix1 = true;
                                //System.out.println("Is Probable Six 1");
                            }//if
                        }//if
                    }//for_j
                }//for_i
                /*for (int i=0; i < cluster_counter; i++)
                {
                    double tempDist0 = java.lang.Math.sqrt((double)(x[0]-clusterCenters[i][0])*(x[0]-clusterCenters[i][0])+(double)(y[0]-clusterCenters[i][1])*(y[0]-clusterCenters[i][1]));
                    double tempDist1 = java.lang.Math.sqrt((double)(x[1]-clusterCenters[i][0])*(x[1]-clusterCenters[i][0])+(double)(y[1]-clusterCenters[i][1])*(y[1]-clusterCenters[i][1]));
                    if (tempDist0 <= maxmaxDist && vDie[i].size() > 3)
                        isProbableSix0 = true;
                    if (tempDist1 <= maxmaxDist && vDie[i].size() > 3)
                        isProbableSix1 = true;
                }//for_i
                */
                
                if (!((Boolean)isSpot(pixelIndex[1], raio, isProbableSix1, false).get(0)).booleanValue())
                {
                    //System.out.println("is not Spot");
                    vDie = removeSpot(vDie, vDieIndex, 1);
                    x = removeArrayEntry(x, 1);
                    y = removeArrayEntry(y, 1);
                    pixelIndex = removeArrayEntry(pixelIndex, 1);
                    clusterCenters = getClusterCenters(vDie);
                }//if
                if (!((Boolean)isSpot(pixelIndex[0], raio, isProbableSix0, (vDie[vDieIndex].size()==1)?true:false).get(0)).booleanValue())
                {
                    //System.out.println("Is not Spot!");
                    vDie = removeSpot(vDie, vDieIndex, 0);
                    x = removeArrayEntry(x, 0);
                    y = removeArrayEntry(y, 0);
                    pixelIndex = removeArrayEntry(pixelIndex, 0);
                    if (vDie[vDieIndex].size() > 1)
                    {
                        clusterCenters = getClusterCenters(vDie);
                    }
                }//if
                
                spotCount = vDie[vDieIndex].size()-1;
                
                if (spotCount==2)   //check if the two spots belong to the same die or if it has to be split in two
                {
                    if(distance[0] > maxDist || distance[1] > maxDist)  //assume we have two "ones"
                    {
                        //System.out.println("or: "+ distance[0]+">" + maxDist + "\nor: "+distance[1]+">"+maxDist);
                        //System.out.println("Breaking apart cluster " + vDieIndex);
                        vDie[cluster_counter] = new java.util.Vector(center_counter);
                        vDie[cluster_counter].addElement(vDie[vDieIndex].get(1));
                        vDie[cluster_counter].addElement(Boolean.FALSE);
                        vDie[vDieIndex].remove(1);
                        //vDie[vDieIndex].setElementAt(Boolean.TRUE,vDie[vDieIndex].size()-1);
                        vClusters.addElement(vDie[cluster_counter]);
                        cluster_counter++;
                        clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                        //checking if the result leads to any one
                        pixelIndex = new int[1];
                        pixelIndex[0] = centers[((Integer)vDie[vDieIndex].get(0)).intValue()][0] + centers[((Integer)vDie[vDieIndex].get(0)).intValue()][1] * imageWidth;
                        java.util.Vector checkDie1Vector;
                        if (isOne(pixelIndex,maxClusterSize))
                        {
                            checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                        }//if
                        else
                            checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, false);
                        if (checkDie1Vector.get(1).equals(Boolean.TRUE))
                            checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                        toReturn.add(0,(java.util.Vector[])checkDie1Vector.get(0));
                        toReturn.add(1,(Boolean)checkDie1Vector.get(1));
                        return toReturn;
                        //return vDie;
                    }//if
                }//if
                //any other case, we have something which is not a two, but is a die, so it must be a three or more
                
                //System.out.println("SpotCount:"+ spotCount);
                if (spotCount > 0 && checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                {
                    //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    toReturn.add(0,vDie);
                    toReturn.add(1,Boolean.TRUE);
                    return toReturn;
                }//if
                if (spotCount == 2 && !checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                {
                    //System.out.println("Breaking apart cluster " + vDieIndex + ", => " + vDie[vDieIndex]);
                    
                        vDie[cluster_counter] = new java.util.Vector(center_counter);
                        vDie[cluster_counter].addElement(vDie[vDieIndex].get(1));
                        vDie[cluster_counter].addElement(Boolean.FALSE);
                        vDie[vDieIndex].remove(1);
                        //vDie[vDieIndex].setElementAt(Boolean.TRUE,vDie[vDieIndex].size()-1);
                        vClusters.addElement(vDie[cluster_counter]);
                        cluster_counter++;
                        clusterCenters = getClusterCenters(vDie);   //update clusterCenters
                        //System.out.println("result: " + vDie[vDieIndex]);
                        //toReturn.add(0,vDie);
                        //toReturn.add(1,Boolean.TRUE);
                        //return toReturn;
                }//if
                if(spotCount == 0)
                {
                    if (vDieIndex != cluster_counter-1)
                        for (int i=vDieIndex+1; i<vDie.length;i++) {
                            vDie[i-1] = vDie[i];
                        }//for_i
                    else
                        vDie[cluster_counter-1] = null;
                    
                    cluster_counter--;
                    
                    toReturn.add(0,vDie);
                    toReturn.add(1,Boolean.FALSE);
                    return toReturn;
                    
                }//else_if
                //return vDie;
            }//if
            
            
            
            
           //check if there are spots that are on the background
            //double[][] distMin_cluster;// = new double[spotCount][2];
            java.util.Vector vDieXYpIdM;
            
            vDieXYpIdM = checkSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);//, distMin_cluster);
            
            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
            x = (int[])vDieXYpIdM.get(1);
            y = (int[])vDieXYpIdM.get(2);
            pixelIndex = (int[])vDieXYpIdM.get(3);
            //distMin_cluster = (double[][])vDieXYpIdM.get(4);
            
            toReturn.add(0,vDie);
            toReturn.add(1,(Boolean)vDieXYpIdM.get(4));
            
            if (((Boolean)vDieXYpIdM.get(4)).equals(Boolean.TRUE))
            {
                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                return toReturn;
            }//if
            
            if (x.length == 0)// && vDieIndex == cluster_counter) 
                return toReturn;
            spotCount = vDie[vDieIndex].size()-1;
            if (spotCount != x.length)
            {
                int[] intArray = new int[spotCount];
                for (int i = 0; i < vDie[vDieIndex].size()-1; i++)
                {
                    intArray[i] = ((Integer)vDie[vDieIndex].get(i)).intValue();
                }//for_i
                x = buildArrayXY(intArray, "x");
                y = buildArrayXY(intArray, "y");
                pixelIndex = new int[spotCount];
                for (int i=0; i<spotCount; i++)
                {
                    //System.out.println(x[i] + ", " + y[i]);
                    pixelIndex[i] = y[i] * imageWidth + x[i];
                }//for_i
            }//if
            
            //sometimes, there are two spots detected in one real spot (both are on the same black patch)
            //let's search for very small distances between spots in the same die!
            
            vDieXYpIdM = checkDoubleSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
            
            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
            x = (int[])vDieXYpIdM.get(1);
            y = (int[])vDieXYpIdM.get(2);
            pixelIndex = (int[])vDieXYpIdM.get(3);
            toReturn.add(0,vDie);
            toReturn.add(1,(Boolean)vDieXYpIdM.get(4));
            
            if (((Boolean)vDieXYpIdM.get(4)).equals(Boolean.TRUE))
            {
                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                return toReturn;
            }//if
            
            if (x.length == 0)// && vDieIndex == cluster_counter) 
                return toReturn;
            spotCount = vDie[vDieIndex].size()-1;
            if (spotCount != x.length)
            {
                int[] intArray = new int[spotCount];
                for (int i = 0; i < vDie[vDieIndex].size()-1; i++)
                {
                    intArray[i] = ((Integer)vDie[vDieIndex].get(i)).intValue();
                }//for_i
                x = buildArrayXY(intArray, "x");
                y = buildArrayXY(intArray, "y");
                pixelIndex = new int[spotCount];
                for (int i=0; i<spotCount; i++)
                {
                    pixelIndex[i] = y[i] * imageWidth + x[i];
                }//for_i
            }//if
            
            
            //check if any spot belongs to another die and, if so, problem solved!
            
            vDieXYpIdM = checkIfSpotsBelongToAnother(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize, insideCluster, info);
            
            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
            x = (int[])vDieXYpIdM.get(1);
            y = (int[])vDieXYpIdM.get(2);
            pixelIndex = (int[])vDieXYpIdM.get(3);
            //distMin_cluster = (double[][])vDieXYpIdM.get(4);
            
            toReturn.add(0,vDie);
            toReturn.add(1,(Boolean)vDieXYpIdM.get(4));
            
            if (((Boolean)vDieXYpIdM.get(4)).equals(Boolean.TRUE))
            {
                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                return toReturn;
            }
                //return vDie;
            
            //System.out.println("vDieIndex: " + vDieIndex + ", vDie: " + vDie[vDieIndex] + ", cluster_counter:" + cluster_counter + "x.length" + x.length);
            if (x.length == 0)// && vDieIndex == cluster_counter) 
                return toReturn;
                //return vDie;
            
            spotCount = vDie[vDieIndex].size()-1;
            
            if (spotCount != x.length)
            {
                int[] intArray = new int[spotCount];
                for (int i = 0; i < vDie[vDieIndex].size()-1; i++)
                {
                    intArray[i] = ((Integer)vDie[vDieIndex].get(i)).intValue();
                }//for_i
                x = buildArrayXY(intArray, "x");
                y = buildArrayXY(intArray, "y");
                pixelIndex = new int[spotCount];
                for (int i=0; i<spotCount; i++)
                {
                    pixelIndex[i] = y[i] * imageWidth + x[i];
                }//for_i
            }//if
            
            
            //whatIf situation!
            //What if it has more than 6 spots in the same cluster
            if (!((Boolean)vDie[vDieIndex].get(vDie[vDieIndex].size()-1)).booleanValue())
            {
                if (spotCount > 6)
                {
                    //check if it can be a six
                    //System.out.println("Checking for SIX!");
                    vDieXYpIdM = testRemove(vDie, vDieIndex, 6, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                    vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                    x = (int[])vDieXYpIdM.get(1);
                    y = (int[])vDieXYpIdM.get(2);
                    pixelIndex = (int[])vDieXYpIdM.get(3);
                    spotCount = vDie[vDieIndex].size()-1;
                    clusterCenters = getClusterCenters(vDie);
                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                    distance = new double[spotCount];
                    for(int indexa=0; indexa < spotCount; indexa++)
                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                    if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 5)
                    {
                        //checking for 5
                        //System.out.println("Checking for FIVE!");
                        vDieXYpIdM = testRemove(vDie, vDieIndex, 5, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                        vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                        x = (int[])vDieXYpIdM.get(1);
                        y = (int[])vDieXYpIdM.get(2);
                        pixelIndex = (int[])vDieXYpIdM.get(3);
                        spotCount = vDie[vDieIndex].size()-1;
                        clusterCenters = getClusterCenters(vDie);
                        x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                        distance = new double[spotCount];
                        for(int indexa=0; indexa < spotCount; indexa++)
                            distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                        if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 4)
                        {
                            //checking for 4
                            //System.out.println("Checking for FOUR!");
                            vDieXYpIdM = testRemove(vDie, vDieIndex, 4, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                            x = (int[])vDieXYpIdM.get(1);
                            y = (int[])vDieXYpIdM.get(2);
                            pixelIndex = (int[])vDieXYpIdM.get(3);
                            spotCount = vDie[vDieIndex].size()-1;
                            clusterCenters = getClusterCenters(vDie);
                            x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                            distance = new double[spotCount];
                            for(int indexa=0; indexa < spotCount; indexa++)
                                distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                            if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 3)
                            {
                                //checking for 3
                                //System.out.println("Checking for THREE!");
                                vDieXYpIdM = testRemove(vDie, vDieIndex, 3, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                                vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                                x = (int[])vDieXYpIdM.get(1);
                                y = (int[])vDieXYpIdM.get(2);
                                pixelIndex = (int[])vDieXYpIdM.get(3);
                                spotCount = vDie[vDieIndex].size()-1;
                                clusterCenters = getClusterCenters(vDie);
                                x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                                distance = new double[spotCount];
                                for(int indexa=0; indexa < spotCount; indexa++)
                                    distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                                if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 2)
                                {
                                    //checking for 2
                                    //System.out.println("Checking for TWO!");
                                    vDieXYpIdM = testRemove(vDie, vDieIndex, 2, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                                    vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                                    x = (int[])vDieXYpIdM.get(1);
                                    y = (int[])vDieXYpIdM.get(2);
                                    pixelIndex = (int[])vDieXYpIdM.get(3);
                                    spotCount = vDie[vDieIndex].size()-1;
                                    clusterCenters = getClusterCenters(vDie);
                                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                                    distance = new double[spotCount];
                                    for (int indexa = 0; indexa < spotCount; indexa++)
                                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                                    if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                                    {
                                        //System.out.println("die " + vDieIndex + " is not a 2, a 3, a 4, a 5, nor a 6!!");
                                    }//if
                                    else
                                    {
                                        //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                    }//else
                                }//if
                                else
                                {
                                    //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                }//else
                            }//if
                            else
                            {
                                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                            }//else
                        }//if
                        else
                        {
                            //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                            vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                        }//else
                    }//if
                    else
                    {
                        //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    }//else
                }//if
                else if (spotCount > 5)
                {
                    //checking for 5
                    //System.out.println("Checking for FIVE!");
                    vDieXYpIdM = testRemove(vDie, vDieIndex, 5, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                    vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                    x = (int[])vDieXYpIdM.get(1);
                    y = (int[])vDieXYpIdM.get(2);
                    pixelIndex = (int[])vDieXYpIdM.get(3);
                    spotCount = vDie[vDieIndex].size()-1;
                    clusterCenters = getClusterCenters(vDie);
                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                    distance = new double[spotCount];
                    for(int indexa=0; indexa < spotCount; indexa++)
                    {
                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                    }//for_indexa
                    if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 4)
                    {
                        //checking for 4
                        //System.out.println("Checking for FOUR!");
                        vDieXYpIdM = testRemove(vDie, vDieIndex, 4, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                        vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                        x = (int[])vDieXYpIdM.get(1);
                        y = (int[])vDieXYpIdM.get(2);
                        pixelIndex = (int[])vDieXYpIdM.get(3);
                        spotCount = vDie[vDieIndex].size()-1;
                        clusterCenters = getClusterCenters(vDie);
                        x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                        distance = new double[spotCount];
                        for(int indexa=0; indexa < spotCount; indexa++)
                            distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                        if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 3)
                        {
                            //checking for 3
                            //System.out.println("Checking for THREE!");
                            vDieXYpIdM = testRemove(vDie, vDieIndex, 3, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                            x = (int[])vDieXYpIdM.get(1);
                            y = (int[])vDieXYpIdM.get(2);
                            pixelIndex = (int[])vDieXYpIdM.get(3);
                            spotCount = vDie[vDieIndex].size()-1;
                            clusterCenters = getClusterCenters(vDie);
                            x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                            distance = new double[spotCount];
                            for(int indexa=0; indexa < spotCount; indexa++)
                                distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                            if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 2)
                            {
                                //checking for 2
                                //System.out.println("Checking for TWO!");
                                vDieXYpIdM = testRemove(vDie, vDieIndex, 2, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                                vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                                x = (int[])vDieXYpIdM.get(1);
                                y = (int[])vDieXYpIdM.get(2);
                                pixelIndex = (int[])vDieXYpIdM.get(3);
                                spotCount = vDie[vDieIndex].size()-1;
                                clusterCenters = getClusterCenters(vDie);
                                x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                                distance = new double[spotCount];
                                for (int indexa = 0; indexa < spotCount; indexa++)
                                    distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                                if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                                {
                                    //System.out.println("die " + vDieIndex + " is not a 2, a 3, a 4, nor a 5!!");
                                }//if
                                else
                                {
                                    //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                }//else
                            }//if
                            else
                            {
                                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                            }//else
                        }//if
                        else
                        {
                            //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                            vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                        }//else
                    }//if
                    else
                    {
                        //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    }//else
                }//else_if
                else if (spotCount > 4)
                {
                    //checking for 4
                    //System.out.println("Checking for FOUR!");
                    vDieXYpIdM = testRemove(vDie, vDieIndex, 4, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                    vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                    x = (int[])vDieXYpIdM.get(1);
                    y = (int[])vDieXYpIdM.get(2);
                    pixelIndex = (int[])vDieXYpIdM.get(3);
                    spotCount = vDie[vDieIndex].size()-1;
                    clusterCenters = getClusterCenters(vDie);
                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                    distance = new double[spotCount];
                    for(int indexa=0; indexa < spotCount; indexa++)
                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                    if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 3)
                    {
                        //checking for 3
                        //System.out.println("Checking for THREE!");
                        vDieXYpIdM = testRemove(vDie, vDieIndex, 3, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                        vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                        x = (int[])vDieXYpIdM.get(1);
                        y = (int[])vDieXYpIdM.get(2);
                        pixelIndex = (int[])vDieXYpIdM.get(3);
                        spotCount = vDie[vDieIndex].size()-1;
                        clusterCenters = getClusterCenters(vDie);
                        x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                        distance = new double[spotCount];
                        for(int indexa=0; indexa < spotCount; indexa++)
                            distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                        if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 2)
                        {
                            //checking for 2
                            //System.out.println("Checking for TWO!");
                            vDieXYpIdM = testRemove(vDie, vDieIndex, 2, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                            x = (int[])vDieXYpIdM.get(1);
                            y = (int[])vDieXYpIdM.get(2);
                            pixelIndex = (int[])vDieXYpIdM.get(3);
                            spotCount = vDie[vDieIndex].size()-1;
                            clusterCenters = getClusterCenters(vDie);
                            x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                            distance = new double[spotCount];
                            for (int indexa = 0; indexa < spotCount; indexa++)
                                distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                            if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                            {
                                //System.out.println("die " + vDieIndex + " is not a 2, a 3, nor a 4!!");
                            }//if
                            else
                            {
                                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                            }//else
                        }//if
                        else
                        {
                            //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                            vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                        }//else
                    }//if
                    else
                    {
                        //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    }//else
                }//else_if
                else if (spotCount > 3)
                {
                    //checking for 3
                    //System.out.println("Checking for THREE!");
                    vDieXYpIdM = testRemove(vDie, vDieIndex, 3, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                    vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                    x = (int[])vDieXYpIdM.get(1);
                    y = (int[])vDieXYpIdM.get(2);
                    pixelIndex = (int[])vDieXYpIdM.get(3);
                    spotCount = vDie[vDieIndex].size()-1;
                    clusterCenters = getClusterCenters(vDie);
                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                    distance = new double[spotCount];
                    for(int indexa=0; indexa < spotCount; indexa++)
                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                    if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize) && spotCount > 2)
                    {
                        //checking for 2
                        //System.out.println("Checking for TWO!");
                        vDieXYpIdM = testRemove(vDie, vDieIndex, 2, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                        vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                        x = (int[])vDieXYpIdM.get(1);
                        y = (int[])vDieXYpIdM.get(2);
                        pixelIndex = (int[])vDieXYpIdM.get(3);
                        spotCount = vDie[vDieIndex].size()-1;
                        clusterCenters = getClusterCenters(vDie);
                        x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                        distance = new double[spotCount];
                        for (int indexa = 0; indexa < spotCount; indexa++)
                            distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                        if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                        {
                            //System.out.println("die " + vDieIndex + " is not a 2, nor a 3!!");
                        }//if
                        else
                        {
                            //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                            vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                        }//else
                    }//if
                    else
                    {
                        //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    }//else
                }//else_if
                else if (spotCount > 2)
                {
                    //checking for 2
                    //System.out.println("Checking for TWO!");
                    vDieXYpIdM = testRemove(vDie, vDieIndex, 2, pixelIndex, raio, maxClusterSize, x, y, whileCounter);
                    vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                    x = (int[])vDieXYpIdM.get(1);
                    y = (int[])vDieXYpIdM.get(2);
                    pixelIndex = (int[])vDieXYpIdM.get(3);
                    spotCount = vDie[vDieIndex].size()-1;
                    clusterCenters = getClusterCenters(vDie);
                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                    distance = new double[spotCount];
                    for (int indexa = 0; indexa < spotCount; indexa++)
                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                    if (!checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                    {
                        //System.out.println("die " + vDieIndex + " is not a 2!!");
                    }//if
                    else
                    {
                        //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    }//else
                }//else_if
            }//if
            
            spotCount = vDie[vDieIndex].size()-1;
            clusterCenters = getClusterCenters(vDie);
            if (spotCount >=2 && spotCount <=3 && vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE))
            {
                vDieXYpIdM = checkForSpotsThatReallyBelongToThis(vDie, vDieIndex, x, y, pixelIndex, maxClusterSize);
                vDie = (java.util.Vector[])vDieXYpIdM.get(0);
                x = (int[])vDieXYpIdM.get(1);
                y = (int[])vDieXYpIdM.get(2);
                pixelIndex = (int[])vDieXYpIdM.get(3);
                vDieIndex = ((Integer)vDieXYpIdM.get(4)).intValue();
                spotCount = vDie[vDieIndex].size()-1;
                clusterCenters = getClusterCenters(vDie);
            }//if
            
            
            x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
            distance = new double[spotCount];
            for (int indexa = 0; indexa < spotCount; indexa++)
                distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
            if (checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
            {
                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
            }//if
            else if(vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE))
            {
                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as FALSE!");
                vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.FALSE);
            }//else_if
            
            //check if there are any spots from another die that belong to this die!
            
            vDieXYpIdM = checkSpotsFromOthers(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
            
            vDie = (java.util.Vector[])vDieXYpIdM.get(0);
            x = (int[])vDieXYpIdM.get(1);
            y = (int[])vDieXYpIdM.get(2);
            pixelIndex = (int[])vDieXYpIdM.get(3);
            vDieIndex = ((Integer)vDieXYpIdM.get(4)).intValue();
            toReturn.add(0,vDie);
            toReturn.add(1,(Boolean)vDieXYpIdM.get(5));
            
            if (((Boolean)vDieXYpIdM.get(5)).equals(Boolean.TRUE))
            {
                //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                return toReturn;
            }//if
                //return vDie;
            
            if (x.length == 0 && vDieIndex == cluster_counter) 
                return toReturn;
            
            spotCount = vDie[vDieIndex].size()-1;
            
            if (spotCount != x.length)
            {
                int[] intArray = new int[spotCount];
                for (int i = 0; i < vDie[vDieIndex].size()-1; i++)
                {
                    intArray[i] = ((Integer)vDie[vDieIndex].get(i)).intValue();
                }//for_i
                x = buildArrayXY(intArray, "x");
                y = buildArrayXY(intArray, "y");
                pixelIndex = new int[spotCount];
                for (int i=0; i<spotCount; i++)
                {
                    pixelIndex[i] = y[i] * imageWidth + x[i];
                }//for_i
            }//if
            
            
            clusterCenters = getClusterCenters(vDie);   //important!!
            x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
            distance = new double[spotCount];
            for (int indexa = 0; indexa < spotCount; indexa++)
                distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
            
            isSpotCountOK = checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize);
            //System.out.println("At the end, spotCount: " + spotCount + " is "+ isSpotCountOK);
            
            areSpotsOK = true;
            whileCounter++;
        }//while    //if_!spotCountIsCorrect
        if (whileCounter == maxWhileCounter)
        {
            //System.out.println("WARNING: whileCounter reached "+maxWhileCounter+ "!!  This die may be incorrect!");
            //System.out.println("Breaking this die ["+vDieIndex+"] fully apart!");
            while (vDie[vDieIndex].size() > 2)
            {
                vDie[cluster_counter] = new java.util.Vector(center_counter);
                vDie[cluster_counter].addElement(vDie[vDieIndex].get(1));
                vDie[cluster_counter].addElement(Boolean.FALSE);
                //System.out.println("Spot " + vDie[cluster_counter].get(0) + " goes to die "+ cluster_counter +" and is set FALSE.");
                vDie[vDieIndex].remove(1);
                x = removeArrayEntry(x, 1);
                y = removeArrayEntry(y, 1);
                pixelIndex = removeArrayEntry(pixelIndex, 1);
                vClusters.addElement(vDie[cluster_counter]);
                cluster_counter++;
            }//while
            //System.out.println("die " + vDieIndex + " keeps spot " + vDie[vDieIndex].get(0));
            clusterCenters = getClusterCenters(vDie);   //update clusterCenters
            //checking if the result leads to any one
            pixelIndex = new int[1];
            pixelIndex[0] = centers[((Integer)vDie[vDieIndex].get(0)).intValue()][0] + centers[((Integer)vDie[vDieIndex].get(0)).intValue()][1] * imageWidth;
            java.util.Vector checkDie1Vector;
            if (isOne(pixelIndex,maxClusterSize))
            {
                checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
            }//if
            else
                checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, false);
            if (checkDie1Vector.get(1).equals(Boolean.TRUE))
                checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                
            
            ////vDie = checkDie1(vDie, cluster_counter-1, centers, info, raio, maxClusterSize, true);
            toReturn.add(0,(java.util.Vector[])checkDie1Vector.get(0));
            toReturn.add(1,(Boolean)checkDie1Vector.get(1));
            
            return toReturn;
            
        }//if
        
        if (isSpotCountOK)
        {
            //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
            vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
        }
        
        toReturn.add(0,vDie);
        toReturn.add(1,Boolean.TRUE);
        return toReturn;
        //return vDie;
    }//checkDie
    
   
    private java.util.Vector checkDie1(java.util.Vector[] vDie, int vDieIndex, int[][] centers, double[] info, int raio, int maxClusterSize, boolean isProbableOne)
    {
        //System.out.println("Checking a die with 1 spot!");
        
        int x1, y1, centerIndex1=0;
        double media1;
        int imageWidth = imageSize()[0];
        double maxmaxDist = (double)maxClusterSize/2. * 1.196; //=19.7175
        java.util.Vector toReturn = new java.util.Vector(2);
        toReturn.add(0, vDie);
        toReturn.add(1,Boolean.TRUE);//is TRUE when the checkDie1 returns the same die with the same spot. FALSE when this spot is moved or removed.
        
        try {centerIndex1 = Integer.parseInt(vDie[vDieIndex].get(0).toString());}
        catch(NumberFormatException e){}
        
        x1 = centers[centerIndex1][0]; y1 = centers[centerIndex1][1];
        media1 = info[centerIndex1];
        
        //System.out.println(x1 + ", " + y1);
        
        int pixelIndex1 = y1*imageWidth + x1;
        boolean isRelaxedSpot = true;
        //check if it's a spot. if not, remove the cluster!
        if(isSpot(pixelIndex1, raio, true, false).get(0).equals(Boolean.FALSE))
        {
            isRelaxedSpot = false;
        }//if
        java.util.Vector isSpotVector = isSpot(pixelIndex1, raio, false, true); 
        int maxZeroCount = 1, maxOneCount = 2, maxMaxCount = 1;
        if (!isRelaxedSpot && isSpotVector.get(0).equals(Boolean.FALSE) && ((Integer)isSpotVector.get(1)).intValue() > maxZeroCount || ((Integer)isSpotVector.get(2)).intValue() > maxOneCount || ((Integer)isSpotVector.get(3)).intValue() > maxMaxCount)
        {
            //System.out.println("spot " + vDie[vDieIndex].get(0) + " is not spot!");
            vDie = removeSpot(vDie, vDieIndex, 0);
            cluster_counter--;
            //now we have an empty die, so we shift the other dies
            for (int index1 = vDieIndex; index1 < cluster_counter; index1++) {
                //System.out.println(index1);
                vClusters.setElementAt(vClusters.get(index1+1),index1);
                vDie[index1] = vDie[index1+1];
            }//for
            
            clusterCenters = getClusterCenters(vDie);
            toReturn.set(0, vDie);
            toReturn.set(1,Boolean.FALSE);
            return toReturn;
        }//if_!isSpot
        else //if it is a spot, we check if it's a spot from some already existing cluster (if we're not in the special situation)
        {   //if we're in the special situation, we're expecting to find a one, so we check, in a starlike manner, the color of the pixels
            if(!isProbableOne)
            {
                double[] distMin1_clusterCenter = new double[3];
                for (int index1 = 0; index1 < cluster_counter; index1++) {
                    distMin1_clusterCenter = new double[3];
                    distMin1_clusterCenter[0]=1000;
                    //check if any spot belongs to another die and, if so, problem solved!
                    
                    if (index1 != vDieIndex)    //we don't want to compare this with it self, now do we??
                    {
                        double dist = java.lang.Math.sqrt((clusterCenters[index1][0]-x1)*(clusterCenters[index1][0]-x1) + (clusterCenters[index1][1]-y1)*(clusterCenters[index1][1]-y1));
                        if (dist < distMin1_clusterCenter[0]) {
                            distMin1_clusterCenter[0]=dist; distMin1_clusterCenter[1]=index1;distMin1_clusterCenter[2] = clusterCenters[index1][1]*imageWidth+clusterCenters[index1][0];
                        }//if_dist
                    }//if_index1!index
                    
                    
                    if (vDie[(int)distMin1_clusterCenter[1]].size() == 2)
                        maxmaxDist = (double)maxClusterSize/2. * 1.52;
                    else
                        maxmaxDist = (double)maxClusterSize/2. *1.2122;//* 1.195;
                    
                    double[] tempDist = new double[2];
                    tempDist[0] = distMin1_clusterCenter[0]/2; tempDist[1] = distMin1_clusterCenter[0]/2;
                    int[] tempPixelIndex = new int[2];
                    tempPixelIndex[0] = y1*imageWidth + x1; tempPixelIndex[1] = (int)distMin1_clusterCenter[2];
                    //System.out.println("minDist: " + distMin1_clusterCenter[0] + ", maxmaxDist: " + maxmaxDist + ", vDie: "
                    //    + vDie[(int)distMin1_clusterCenter[1]]);
                    
                    //System.out.println("is other die False? "+vDie[((int)distMin1_clusterCenter[1])].get(vDie[((int)distMin1_clusterCenter[1])].size()-1).equals(Boolean.FALSE)
                    //    +"; is other die >1? "+ (vDie[(int)distMin1_clusterCenter[1]] .size()-1 > 1));
                    
                    
                    if ((vDie[((int)distMin1_clusterCenter[1])].get(vDie[((int)distMin1_clusterCenter[1])].size()-1).equals(Boolean.FALSE) 
                        &&(distMin1_clusterCenter[0] <= maxmaxDist && (vDie[(int)distMin1_clusterCenter[1]] .size()-1 >= 1))) 
                        || (isTwo(tempDist, tempPixelIndex, false) && vDie[(int)distMin1_clusterCenter[1]] .size()-1 == 1)
                        /* || (countWhitePatches(tempPixelIndex[0], tempPixelIndex[1])[0] == 1  && distMin1_clusterCenter[0] <= (double)maxClusterSize/2.59)*/)
                    {
                        //System.out.println("Belongs To another Die!");
                        vDie = changeSpot(vDie, vDieIndex, 0, (int)distMin1_clusterCenter[1]);
                        //System.out.println("Setting die "+(int)distMin1_clusterCenter[1]+" as FALSE.");
                        vDie[(int)distMin1_clusterCenter[1]].set(vDie[(int)distMin1_clusterCenter[1]].size()-1,Boolean.FALSE);
                        //now we have an empty die, so we shift the other dies
                        for (int index2 = vDieIndex+1; index2 < cluster_counter; index2++) {
                            vClusters.setElementAt(vClusters.get(index2),index2-1);
                            vDie[index2-1] = vDie[index2];
                        }//for_index2
                        cluster_counter--;
                        //if (vDie[(int)distMin1_clusterCenter[1]].size()-1 > 6)
                        //    vDie[(int)distMin1_clusterCenter[1]].set(vDie[(int)distMin1_clusterCenter[1]].size()-1, Boolean.FALSE);
                        clusterCenters = getClusterCenters(vDie);
                        toReturn.set(0, vDie);
                        toReturn.set(1,Boolean.FALSE);
                        return toReturn;
                    }//if
                }//for_index1
                
                if (distMin1_clusterCenter[0] < maxClusterSize 
                    && vDie[((int)distMin1_clusterCenter[1])].get(vDie[((int)distMin1_clusterCenter[1])].size()-1).equals(Boolean.FALSE))
                {                                                //if the die doesn't belong to another die, but there is a die
                                                                 //center within range, try to get a spot from that other die.   
                    for (int i=0; i < vDie[((int)distMin1_clusterCenter[1])].size()-1; i++)
                    {
                        int x = centers[((Integer)vDie[((int)distMin1_clusterCenter[1])].get(i)).intValue()][0], y = centers[((Integer)vDie[((int)distMin1_clusterCenter[1])].get(i)).intValue()][1];
                        double testDist = java.lang.Math.sqrt((double)(x1-x)*(x1-x)+(double)(y1-y)*(y1-y));
                        maxmaxDist = (double)maxClusterSize*0.795; //1.59/2 = 0.795
                        //System.out.println("Testing spot " + vDie[((int)distMin1_clusterCenter[1])].get(i) + "("+x+","+y+")" +", is at dist: "+testDist + ", maxmaxDist:"+maxmaxDist);
                        double[] tempDist = new double[2]; tempDist[0] = testDist/2; tempDist[1]=tempDist[0];
                        int[] tempPixelIndex = new int[2]; tempPixelIndex[0]=x1+y1*imageWidth;tempPixelIndex[1]=x+y*imageWidth;
                        if (testDist < maxmaxDist && isTwo(tempDist,tempPixelIndex,false))
                        {
                            vDie = changeSpot(vDie, ((int)distMin1_clusterCenter[1]), i, vDieIndex);
                            if (vDie[((int)distMin1_clusterCenter[1])].size() == 1)
                            {
                                //System.out.println("vDie " + (int)(distMin1_clusterCenter[1]) + " has been depleted!");
                                if ((int)distMin1_clusterCenter[1] != cluster_counter-1)
                                    for (int k= (int)distMin1_clusterCenter[1]+1; k<vDie.length;k++)
                                        vDie[k-1] = vDie[k];
                                else
                                    vDie[cluster_counter-1] = null;
                                
                                cluster_counter--;
                                if (vDieIndex == cluster_counter)
                                    vDieIndex--;
                                
                                clusterCenters = getClusterCenters(vDie);
                                break;
                            }//if
                            toReturn.set(0,vDie);
                            toReturn.set(1,Boolean.FALSE);
                        }//if
                            
                    }//for_i
                    //return toReturn;
                }//if
                //check if it can make a two with any spot available
                //System.out.println("testing to get a spot and form a two!");
                for(int i=0; i<cluster_counter && vDie[vDieIndex].size()==2; i++)
                {
                    if (vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE) && i != vDieIndex)
                    {
                        for(int j=0; j<vDie[i].size()-1; j++)
                        {
                            int x0 = centers[((Integer)vDie[i].get(j)).intValue()][0];
                            int y0 = centers[((Integer)vDie[i].get(j)).intValue()][1];
                            int pixelIndex0 = x0 + y0 * imageWidth;
                            double testDist = java.lang.Math.sqrt((double)(x1-x0)*(x1-x0)+(double)(y1-y0)*(y1-y0));
                            //System.out.println("testDist:"+testDist);
                            double[] testDistance = new double[2];
                            int[] pixelIndex = new int[2];
                            testDistance[0] = testDist/2.; testDistance[1] = testDistance[0];
                            pixelIndex[0] = x1+y1*imageWidth; pixelIndex[1] = pixelIndex0;
                            if (isTwo(testDistance, pixelIndex, false))
                            {
                                vDie = changeSpot(vDie, i, j, vDieIndex);
                                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                                //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                                toReturn.set(0, vDie);
                                toReturn.set(1,Boolean.FALSE);
                                return toReturn;
                            }//
                        }//for_j
                    }//if
                }//for_i
                //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                //vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
            }//if
            else
            {
                int [] pixelIndex = new int[1];
                pixelIndex[0] = pixelIndex1;
                
                if (!isOne(pixelIndex, maxClusterSize))
                {
                    //System.out.println("Bad Spot detected! Spot " + vDie[vDieIndex].get(0));
                    vDie = removeSpot(vDie, vDieIndex,0);
                    
                    //System.out.println("[1a]This die has been depleted!");
                    if (vDieIndex != cluster_counter-1)
                        for (int i=vDieIndex+1; i<vDie.length;i++)
                        {
                            vDie[i-1] = vDie[i];
                        }//for_i
                    else
                        vDie[cluster_counter-1] = null;
                    
                    cluster_counter--;
                    clusterCenters = getClusterCenters(vDie);
                    toReturn.set(0, vDie);
                    toReturn.set(1,Boolean.FALSE);
                }//if
                else
                {
                    //System.out.println("Setting vDie "+vDieIndex+" as TRUE.");
                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1,Boolean.TRUE);
                    toReturn.set(0, vDie);
                    toReturn.set(1,Boolean.TRUE);
                }//else
            }//else
        }//else
        return toReturn;
    }//checkDie1
    
    private java.util.Vector checkDoubleSpots(java.util.Vector[] vDie, int vDieIndex, int[] x, int[] y, int[] pixelIndex, int raio, int maxClusterSize)
    {
        if (vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE)) //if this die is correct, do nothing!
        {
            java.util.Vector vDieXYpI = new java.util.Vector(5);
            vDieXYpI.add(0, vDie);
            vDieXYpI.add(1, x);
            vDieXYpI.add(2, y);
            vDieXYpI.add(3, pixelIndex);
            vDieXYpI.add(4, Boolean.TRUE);
        return vDieXYpI;
        }//if
        //System.out.println("Checking Double Spots!");
        int spotCount = vDie[vDieIndex].size()-1, x_mean, y_mean;
        double[] distance;
        java.util.Vector vDieXYpI = new java.util.Vector(5);
        for (int indexa=0; indexa<spotCount; indexa++)
        {
            int xa = x[indexa]; int ya = y[indexa];
            boolean removed = false;
            boolean aIsSpot = ((Boolean)isSpot(pixelIndex[indexa],raio, spotCount<6?false:true, spotCount==1?true:false).get(0)).booleanValue();
            for (int indexb=0; indexb<spotCount; indexb++)
            {
                //System.out.println(">>Before!   spotCount:" + spotCount + ", indexa:" + indexa + ", indexb:" + indexb);
                if (indexa != indexb && aIsSpot)
                {
                    int xb = x[indexb]; int yb = y[indexb];
                    double dist = java.lang.Math.sqrt((double)(xa-xb)*(xa-xb)+(double)(ya-yb)*(ya-yb));
                    //System.out.println("Spot " + xa+";"+ya + " to " + xb+";"+yb + " = " + dist);
                    if (dist <= raio+1) //raio
                    {
                        //System.out.println("Double Spot detected! Spot " + vDie[vDieIndex].get(indexb));
                        vDie = removeSpot(vDie, vDieIndex,indexb);
                        x = removeArrayEntry(x, indexb);
                        y = removeArrayEntry(y, indexb);
                        pixelIndex = removeArrayEntry(pixelIndex,indexb);
                        --spotCount;
                        if (spotCount==0)
                        {
                            //System.out.println("[2]This die has been depleted!");
                            if (vDieIndex != cluster_counter-1)
                                for (int i=vDieIndex+1; i<vDie.length;i++)
                                {
                                    vDie[i-1] = vDie[i];
                                }//for_i
                            else
                                vDie[cluster_counter-1] = null;
                            
                            cluster_counter--;
                            clusterCenters = getClusterCenters(vDie);
                            
                            vDieXYpI.add(0, vDie);
                            vDieXYpI.add(1, x);
                            vDieXYpI.add(2, y);
                            vDieXYpI.add(3, pixelIndex);
                            vDieXYpI.add(4, new Boolean(true));
                            return vDieXYpI;
                        }//if
                        clusterCenters = getClusterCenters(vDie);
                        
                        x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                        distance = new double[spotCount];
                        for(int indexc=0; indexc < distance.length; indexc++)
                            distance[indexc] = java.lang.Math.sqrt((double)(x_mean-x[indexc])*(x_mean-x[indexc])+(double)(y_mean-y[indexc])*(y_mean-y[indexc]));
                        if (checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize))
                        {
                            //System.out.println("Spot Count is correct after removal double spots!");
                            //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                            vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                            vDieXYpI.add(0, vDie);
                            vDieXYpI.add(1, x);
                            vDieXYpI.add(2, y);
                            vDieXYpI.add(3, pixelIndex);
                            vDieXYpI.add(4, new Boolean(true));
                            return vDieXYpI;
                        }//if
                        //else{ System.out.println("[2]spotCount check Failed!! spotCount: " + spotCount);}
                        --indexb;
                        removed=true;
                    }//if
                }//if
                if (spotCount == indexa && spotCount > 1) indexa--;
                //System.out.println(">>After!   spotCount:" + spotCount + ", indexa:" + indexa + ", indexb:" + indexb);
                
            }//for_indexb
            if (removed) --indexa;
        }//for_indexa
        
        
        vDieXYpI.add(0, vDie);
        vDieXYpI.add(1, x);
        vDieXYpI.add(2, y);
        vDieXYpI.add(3, pixelIndex);
        vDieXYpI.add(4, new Boolean(false));
        return vDieXYpI;
    }//checkDoubleSpots
    
    
    private java.util.Vector checkSpots(java.util.Vector[] vDie, int vDieIndex, int[] x, int[] y, int[] pixelIndex, int raio, int maxClusterSize)//, double[][] distMin_cluster)
    {
        if (vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE)) //if this die is correct, do nothing!
        {
            java.util.Vector vDieXYpI = new java.util.Vector(5);
            vDieXYpI.add(0, vDie);
            vDieXYpI.add(1, x);
            vDieXYpI.add(2, y);
            vDieXYpI.add(3, pixelIndex);
            vDieXYpI.add(4, Boolean.TRUE);
            return vDieXYpI;
        }//if
        int spotCount = vDie[vDieIndex].size()-1;
        //System.out.println("Checking Bad Spots! ("+spotCount+")");
        
        if (spotCount == 3)
        {
            int startPixelIndex=0, endPixelIndex=0;
            double minDist = maxClusterSize/3.064;// 10.770234986945169712793733681462;
            int x_mean = clusterCenters[vDieIndex][0], y_mean = clusterCenters[vDieIndex][1];
            double[] distance = new double[spotCount];
            for(int indexa=0; indexa < distance.length; indexa++)
                distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
            for (int i=0; i<3; i++)
            {
                if(startPixelIndex!=0 && distance[i]>=minDist)
                    endPixelIndex=pixelIndex[i];
                if(startPixelIndex==0 && distance[i]>=minDist)
                    startPixelIndex = pixelIndex[i];
            }//for_i
            //System.out.println("startPoint: (" + startPixelIndex%imageSize()[0]+","+startPixelIndex/imageSize()[0]+"); endPoint: ("+endPixelIndex%imageSize()[0]+","+endPixelIndex/imageSize()[0]+").");
            int[] whiteCounters = countWhitePatches(startPixelIndex, endPixelIndex);
            if (whiteCounters[0] < 2)
            {
                //get worst spot, to remove!
                int[] counter = new int[3];
                for (int i=0; i < spotCount; i++)
                {
                    java.util.Vector isSpotVector = isSpot(pixelIndex[i], raio, true, false);
                    counter[i] = ((Integer)isSpotVector.get(1)).intValue();
                    counter[i] += ((Integer)isSpotVector.get(2)).intValue();
                    counter[i] += ((Integer)isSpotVector.get(3)).intValue();
                }//for_i
                int worstSpot = -1;
                int counterTemp = 0;
                for (int i =0; i<spotCount; i++)
                {
                    if (counter[i] > counterTemp)
                    {
                        counterTemp = counter[i];
                        worstSpot = i;
                    }//if
                }//for_i
                if (worstSpot != -1)
                {
                    //now we know which is the worst, so we can send it away
                    //System.out.println("Breaking apart cluster " + vDieIndex);
                    vDie[cluster_counter] = new java.util.Vector(center_counter);
                    vDie[cluster_counter].addElement(vDie[vDieIndex].get(worstSpot));
                    vDie[cluster_counter].addElement(Boolean.FALSE);
                    vDie[vDieIndex].remove(worstSpot);
                    x = removeArrayEntry(x, worstSpot);
                    y = removeArrayEntry(y, worstSpot);
                    pixelIndex = removeArrayEntry(pixelIndex, worstSpot);
                    vClusters.addElement(vDie[cluster_counter]);
                    cluster_counter++;
                    clusterCenters = getClusterCenters(vDie);
                    spotCount--;
                    
                    x_mean = clusterCenters[vDieIndex][0]; y_mean = clusterCenters[vDieIndex][1];
                    distance = new double[spotCount];
                    for(int indexa=0; indexa < distance.length; indexa++)
                        distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                    if (checkSpotCount(distance,spotCount, pixelIndex,maxClusterSize))
                    {
                        //System.out.println("Setting die "+vDieIndex+" as TRUE!");
                        vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                        java.util.Vector vDieXYpI = new java.util.Vector(5);
                        vDieXYpI.add(0, vDie);
                        vDieXYpI.add(1, x);
                        vDieXYpI.add(2, y);
                        vDieXYpI.add(3, pixelIndex);
                        vDieXYpI.add(4, Boolean.TRUE);
                        return vDieXYpI;
                    }//if
                }//if
            }//if
        }//if
        
        java.util.Vector vDieXYpI = new java.util.Vector(5);
        double maxmaxDist = (double)maxClusterSize/2 ;//* 1.196;
        int imageWidth = imageSize()[0], minWhite = 2;
            
        for(int index=0; index<spotCount; index++)
        {
            //distMin_cluster[index][0] = 1000;
            
            //Check if it's really a spot and, remove if not; move, if yes.
            boolean isProbableSix = false;
            int maxDistForConsecutiveSpotsInSix = 10;
            //check if this spot can belong to some die that may be a six
            for (int i=0; i < cluster_counter && !isProbableSix; i++)
            {
                for (int j=0; j < vDie[i].size()-1 && !isProbableSix; j++)
                {
                    if (! (i==vDieIndex && j==index))
                    {
                        if (spotCount >= 6)
                        {
                            isProbableSix = true;
                        }//if
                        else
                        {
                            //System.out.println("vDie["+i+"], spot("+j+")" );
                            //System.out.println("spotCount:" + spotCount);
                            int x_temp = centers[((Integer)vDie[i].get(j)).intValue()][0], y_temp = centers[((Integer)vDie[i].get(j)).intValue()][1];
                            int startPixelIndex = x_temp + y_temp*imageWidth, endPixelIndex = x[index] + y[index]*imageWidth;
                            int[] whitePatches = countWhitePatches(startPixelIndex, endPixelIndex);
                            double tempDist = java.lang.Math.sqrt((double)(x[index]-x_temp)*(x[index]-x_temp)+(double)(y[index]-y_temp)*(y[index]-y_temp));
                            //System.out.println("tempDist: "+tempDist+";from spot 38 to die:"+i+", spot:"+vDie[i].get(j)+"; this die:"+vDieIndex+"=>"+vDie[vDieIndex]);
                            //System.out.println("tempDist: "+tempDist+"; maxDistForConsecutiveSpotsInSix:"+maxDistForConsecutiveSpotsInSix+", whitePatches:"+whitePatches[0]+", whitepixelCounter:"+whitePatches[1]);
                            if (tempDist < maxDistForConsecutiveSpotsInSix && (whitePatches[0] <= 1 && whitePatches[1] <= minWhite))
                            {
                                
                                if (spotCount <= 2)
                                {
                                    //check distance between cluster centers so we can say  that this is not a six.
                                    int maxDistForSingleCluster = (int)((double)maxClusterSize/1.22);
                                    tempDist = java.lang.Math.sqrt((double)(clusterCenters[vDieIndex][0] - clusterCenters[i][0])*(clusterCenters[vDieIndex][0] - clusterCenters[i][0])+(double)(clusterCenters[vDieIndex][1] - clusterCenters[i][1])*(clusterCenters[vDieIndex][1] - clusterCenters[i][1]));
                                    //System.out.println("tempDist:" + tempDist + ", max:" + maxDistForSingleCluster + ", cluster:" + i + "=="+vDie[i]);
                                    if (tempDist < maxDistForSingleCluster && vDie[i].get(vDie[i].size()-1).equals(Boolean.FALSE))
                                        isProbableSix = true;
                                }//if
                                else isProbableSix = true;
                            }//if
                        }//else
                        //System.out.println("temp point:"+x_temp+", "+y_temp  +"; " + isProbableSix);
                    }//if
                }//for_j
            }//for_i
            /*for (int i=0; i < cluster_counter && !isProbableSix; i++)
            {
                if (spotCount >=3)
                    isProbableSix = true;
                
                double tempDist = java.lang.Math.sqrt((double)(x[index]-clusterCenters[i][0])*(x[index]-clusterCenters[i][0])+(double)(y[index]-clusterCenters[i][1])*(y[index]-clusterCenters[i][1]));
                //System.out.println("TempDist:" + tempDist + ", maxmaxDist:"+maxmaxDist);
                if (tempDist <= maxmaxDist && vDie[i].size() > 3)
                    isProbableSix = true;
            }//for_i
             */
            //System.out.println("checking spot " + vDie[vDieIndex].get(index) + ", ("+x[index]+", "+y[index]+"),");
            java.util.Vector isSpotVector = isSpot(pixelIndex[index], raio, isProbableSix, spotCount==1?true:false);
            if(isSpotVector.get(0).equals(Boolean.FALSE))
            {
                //System.out.println("Is not Spot!");
                //System.out.println("zeroCounter:"+tempVector.get(1)+"; oneCounter:"+tempVector.get(2)+"; maxCounter:"+tempVector.get(3)+"; isProbableSix:"+isProbableSix);
                vDie = removeSpot(vDie, vDieIndex, index);
                x = removeArrayEntry(x, index);
                y = removeArrayEntry(y, index);
                pixelIndex = removeArrayEntry(pixelIndex,index);
                //distMin_cluster = removeArrayEntry(distMin_cluster, index, 0);
                spotCount--;
                if (spotCount==0) {
                    //System.out.println("[3]This die has been depleted!");
                    if (vDieIndex != cluster_counter-1)
                        for (int i=vDieIndex+1; i<vDie.length;i++) {
                            vDie[i-1] = vDie[i];
                        }//for_i
                    else
                        vDie[cluster_counter-1] = null;
                    
                    cluster_counter--;
                    clusterCenters = getClusterCenters(vDie);
                    vDieXYpI.add(0, vDie);
                    vDieXYpI.add(1, x);
                    vDieXYpI.add(2, y);
                    vDieXYpI.add(3, pixelIndex);
                    vDieXYpI.add(4, new Boolean(true));
                    return vDieXYpI;
                }//if
                clusterCenters = getClusterCenters(vDie);
                
                int x_mean = clusterCenters[vDieIndex][0], y_mean = clusterCenters[vDieIndex][1];
                double[] distance = new double[spotCount];
                for(int indexa=0; indexa < distance.length; indexa++)
                    distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                if (checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize)) {
                    //System.out.println("Spot Count is correct after removal non spots!");
                    //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    vDieXYpI.add(0, vDie);
                    vDieXYpI.add(1, x);
                    vDieXYpI.add(2, y);
                    vDieXYpI.add(3, pixelIndex);
                    //vDieXYpIdM.add(4, distMin_cluster);
                    vDieXYpI.add(4, Boolean.TRUE);
                    return vDieXYpI;
                }//if
                //else System.out.println("[3]spotCount check Failed!! spotCount: " + spotCount);
                index--;
            }//if
        }//for_index
        
        vDieXYpI.add(0, vDie);
        vDieXYpI.add(1, x);
        vDieXYpI.add(2, y);
        vDieXYpI.add(3, pixelIndex);
        vDieXYpI.add(4, new Boolean(false));
        
        return vDieXYpI;
    }//checkSpots
    
    
    private java.util.Vector checkIfSpotsBelongToAnother(java.util.Vector[] vDie, int vDieIndex, int[] x, int[] y, int[] pixelIndex, int raio, int maxClusterSize, int insideCluster, double[] info)
    {
        if (vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE)) //if this die is correct, do nothing!
        {
            java.util.Vector vDieXYpIdM = new java.util.Vector(5);
            double[][] distMin_cluster = new double[vDie[vDieIndex].size()-1][2];
            vDieXYpIdM.add(0, vDie);
            vDieXYpIdM.add(1, x);
            vDieXYpIdM.add(2, y);
            vDieXYpIdM.add(3, pixelIndex);
            vDieXYpIdM.add(4, Boolean.TRUE);
            return vDieXYpIdM;
        }//if
        //System.out.println("Checking if spots belong to another Die!");
        int spotCount = vDie[vDieIndex].size()-1;
        java.util.Vector vDieXYpIdM = new java.util.Vector(6);
        double maxmaxDist;// = (double)maxClusterSize/2. * 1.195;
        
        double[][] distMin_cluster = new double[spotCount][2];
        for (int i = 0; i < spotCount; i++)
        {
            distMin_cluster[i][0] = 1000;
        }//for_i
        double[][] minDistSpotClusterXY = new double[spotCount][5]; 
        
        for (int index = 0; index < cluster_counter; index++) {
            if (index != vDieIndex)    //we don't want to compare this with it self, now do we??
            {
                for (int index1=0; index1<spotCount; index1++) {
                    double dist = java.lang.Math.sqrt((clusterCenters[index][0]-x[index1])*(clusterCenters[index][0]-x[index1]) + (clusterCenters[index][1]-y[index1])*(clusterCenters[index][1]-y[index1]));
                    if (dist < distMin_cluster[index1][0]) {
                        distMin_cluster[index1][0]=dist; distMin_cluster[index1][1]=index;
                        minDistSpotClusterXY[index1][0] = dist;
                        minDistSpotClusterXY[index1][1] = index1;
                        minDistSpotClusterXY[index1][2] = index;
                        minDistSpotClusterXY[index1][3] = x[index1];
                        minDistSpotClusterXY[index1][4] = y[index1];
                    }//if
                }//for_index1
            }//if
        }//for_index
        
        int[] numberOfRemovedSpots = new int[spotCount];
        int numberOfRemovedSpotsInDie = 0;
        for(int index = 0; index < spotCount; index++)
        {
            //System.out.println("spot " + vDie[vDieIndex].get(index)+", distMin: "+distMin_cluster[index][0] + "; Cluster: " + distMin_cluster[index][1]);
            //System.out.println("temp:" + temp + distMin_cluster[index][0] + ";" + maxmaxDist);
            //System.out.println(distMin_cluster[index][1] + ";" +  insideCluster);
            if (vDie[((int)distMin_cluster[index][1])].size() == 2  || vDie[((int)distMin_cluster[index][1])].size() == 3) //if we have a one and we want to check if it can become a two, this is the way!
            {                                                           //if we have a two, it might be a part of a three or a five
                maxmaxDist = (double)maxClusterSize/2. * 1.59;
                //System.out.println("die " + ((int)distMin_cluster[index][1]) + " is a "+ (vDie[((int)distMin_cluster[index][1])].size()-1)+". maxmaxDist:" + maxmaxDist);
            }//if
            else
                maxmaxDist = (double)maxClusterSize/1.52;
            //System.out.println("distMin:" + distMin_cluster[index][0] + ", maxmaxDist = " + maxmaxDist + ", vDie:" + vDie[((int)distMin_cluster[index][1])]);
            //if(temp && distMin_cluster[index][0]<maxmaxDist)
            
            if(distMin_cluster[index][0]<maxmaxDist)
            {
                //if the other die is ok, test if it is still ok, after receiving the spot from this die.
                int testvDieIndex = (int)minDistSpotClusterXY[index][2];
                //System.out.println("Die " + testvDieIndex + ": "+  vDie[testvDieIndex]);
                if(vDie[testvDieIndex].get(vDie[testvDieIndex].size()-1).equals(Boolean.TRUE))
                {
                    if (!(vDie[testvDieIndex].size()-2 == 0))
                    {
                        //building stuff to test resulting die
                        int[] testX = new int[vDie[testvDieIndex].size()];
                        int[] testY = new int[vDie[testvDieIndex].size()];
                        int[] testPixelIndex = new int[testX.length];
                        
                        int testX_mean = 0, testY_mean = 0;
                        for (int i = 0; i < vDie[testvDieIndex].size()-1; i++)
                        {
                            testX[i] = centers[((Integer)vDie[testvDieIndex].get(i)).intValue()][0];
                            testY[i] = centers[((Integer)vDie[testvDieIndex].get(i)).intValue()][1];
                            testPixelIndex[i] = testY[i]*imageSize()[0]+testX[i];
                            testX_mean += testX[i];
                            testY_mean += testY[i];
                        }//for_i
                        
                        //System.out.println("spotIndex in Die:"+((int)minDistSpotClusterXY[index][1]-numberOfRemovedSpots));
                        //System.out.println(vDie[vDieIndex].get((int)minDistSpotClusterXY[index][1]-numberOfRemovedSpots));
                        //System.out.println("numberOfRemovedSpots["+index+"]:"+numberOfRemovedSpots[index] + "; die:"+vDie[vDieIndex] + "; pos:"+((int)minDistSpotClusterXY[index][1]-numberOfRemovedSpotsInDie)+"; numberOfRemovedSpotsInDie:"+numberOfRemovedSpotsInDie+"spot:"+minDistSpotClusterXY[index][1]);
                        int testIndex = (int)minDistSpotClusterXY[index][1]-numberOfRemovedSpots[index];
                        if (testIndex < 0)
                            testIndex=0;
                        else if (testIndex >= vDie[vDieIndex].size()-1)
                        {
                            numberOfRemovedSpots[index]+=testIndex-(vDie[vDieIndex].size()-2);
                            testIndex=vDie[vDieIndex].size()-2;
                        }//else_if
                        testX[testX.length-1] = centers[((Integer)vDie[vDieIndex].get(testIndex)).intValue()][0];
                        testY[testX.length-1] = centers[((Integer)vDie[vDieIndex].get(testIndex)).intValue()][1];
                        testPixelIndex[testX.length-1] = testY[testX.length-1]*imageSize()[0]+testX[testX.length-1];
                        testX_mean += testX[testX.length-1];
                        testY_mean += testY[testX.length-1];
                        
                        java.util.Vector[] fakevDie = new java.util.Vector[vDie.length];
                        
                        for (int i = 0; i < vDie.length; i++)
                        {
                            if (vDie[i] != null)
                                fakevDie[i] = (java.util.Vector)vDie[i].clone();
                        }//for_i
                        
                        fakevDie[testvDieIndex].add(fakevDie[testvDieIndex].size()-1,vDie[vDieIndex].get(testIndex));
                        testX_mean = testX_mean / testX.length; testY_mean = testY_mean / testY.length;
                        double[] distance = new double[testX.length];
                        for(int indexa=0; indexa < testX.length; indexa++)
                            distance[indexa] = java.lang.Math.sqrt((double)(testX_mean-testX[indexa])*(testX_mean-testX[indexa])+(double)(testY_mean-testY[indexa])*(testY_mean-testY[indexa]));
                        //System.out.println("spotCount:"+testX.length+", pixelIndex.length:"+pixelIndex.length);
                        if (!checkSpotCount(distance, testX.length, testPixelIndex, maxClusterSize))
                        {
                            vDieXYpIdM.add(0, vDie);
                            vDieXYpIdM.add(1, x);
                            vDieXYpIdM.add(2, y);
                            vDieXYpIdM.add(3, pixelIndex);
                            vDieXYpIdM.add(4, Boolean.FALSE);
                            return vDieXYpIdM;
                        }//if
                    }//if
                }//if
                
                int clusterIndex = (int)distMin_cluster[index][1];
                int imageWidth = imageSize()[0];
                if (vDie[clusterIndex].size() == 2)
                {
                    //System.out.println("Checking if this can belong to a two!");
                    //we're going to check if the result is a real two. if not, we'll keep them apart!
                    int[] pixelIndexTest = new int[2];
                    double[] distanceTest = new double[2];
                    
                    pixelIndexTest[0] = centers[((Integer)vDie[clusterIndex].get(0)).intValue()][1] * imageWidth + centers[((Integer)vDie[clusterIndex].get(0)).intValue()][0];
                    pixelIndexTest[1] = pixelIndex[index];
                    int x0 = centers[((Integer)vDie[clusterIndex].get(0)).intValue()][0], x1 = centers[((Integer)vDie[vDieIndex].get(index)).intValue()][0];
                    int y0 = centers[((Integer)vDie[clusterIndex].get(0)).intValue()][1], y1 = centers[((Integer)vDie[vDieIndex].get(index)).intValue()][1];
                    distanceTest[0] = Math.sqrt((double)(x0-x1)*(x0-x1) + (double)(y0-y1)*(y0-y1))/2;
                    distanceTest[1] = distanceTest[0];
                    //System.out.println("distance[0]:"+distanceTest[0]+", distance[1]:"+distanceTest[1]);
                    if (isTwo(distanceTest, pixelIndexTest,true))
                    {
                        //System.out.println("Is two!");
                        vDie = changeSpot(vDie, vDieIndex, index, clusterIndex);
                        x = removeArrayEntry(x, index);
                        y = removeArrayEntry(y, index);
                        distMin_cluster = removeArrayEntry(distMin_cluster, index, 0);
                        pixelIndex = removeArrayEntry(pixelIndex,index);
                        numberOfRemovedSpots = removeArrayEntry(numberOfRemovedSpots, index);
                        minDistSpotClusterXY = removeArrayEntry(minDistSpotClusterXY, index, 0);
                        
                        index = -1;
                        spotCount--;
                        numberOfRemovedSpotsInDie++;
                        for (int i=0; i< spotCount; i++)
                        {
                            if (minDistSpotClusterXY[i][1] > ((Integer)vDie[clusterIndex].get(vDie[clusterIndex].size()-2)).intValue())
                            {
                                numberOfRemovedSpots[i]++;
                            }//if
                        }//for_i
                        
                        if (spotCount==0)
                        {
                            //System.out.println("[4]This die ("+vDieIndex+") has been depleted!");
                            if (vDieIndex != cluster_counter-1)
                                for (int i=vDieIndex+1; i<vDie.length;i++) {
                                    vDie[i-1] = vDie[i];
                                }//for_i
                            else
                                vDie[cluster_counter-1] = null;
                            
                            cluster_counter--;
                            clusterCenters = getClusterCenters(vDie);
                        //}//if
                            vDieXYpIdM.add(0, vDie);
                            vDieXYpIdM.add(1, x);
                            vDieXYpIdM.add(2, y);
                            vDieXYpIdM.add(3, pixelIndex);
                            vDieXYpIdM.add(4, Boolean.FALSE);
                            
                            return vDieXYpIdM;
                        }//if
                        clusterCenters = getClusterCenters(vDie);
                        if (isTwo(distanceTest, pixelIndexTest, false))
                        {
                            //System.out.println("Setting vDie "+clusterIndex+":"+vDie[clusterIndex] +" as TRUE.");
                            vDie[clusterIndex].set(vDie[clusterIndex].size()-1, Boolean.TRUE);
                        }//if
                        else
                        {
                            //System.out.println("Setting vDie "+clusterIndex+":"+vDie[clusterIndex] +" as FALSE.");
                            vDie[clusterIndex].set(vDie[clusterIndex].size()-1, Boolean.FALSE);
                        }//else
                    }//if
                }//if
                else
                {
                    //System.out.println("Else!!");
                    vDie = changeSpot(vDie, vDieIndex, index, clusterIndex);
                    x = removeArrayEntry(x, index);
                    y = removeArrayEntry(y, index);
                    distMin_cluster = removeArrayEntry(distMin_cluster, index, 0);
                    pixelIndex = removeArrayEntry(pixelIndex,index);
                    minDistSpotClusterXY = removeArrayEntry(minDistSpotClusterXY,index,0);
                    numberOfRemovedSpots = removeArrayEntry(numberOfRemovedSpots, index);
                    
                    index = -1;
                    spotCount--;
                    numberOfRemovedSpotsInDie++;
                    for (int i=0; i< spotCount; i++)
                    {
                        //if (minDistSpotClusterXY[i][1] > ((Integer)vDie[clusterIndex].get(vDie[clusterIndex].size()-2)).intValue())
                        numberOfRemovedSpots[i]++;
                    }//for_i
                    
                    if (spotCount==0)
                    {
                        //System.out.println("[4]This die has been depleted!");
                        if (vDieIndex != cluster_counter-1)
                            for (int i=vDieIndex+1; i<vDie.length;i++) {
                                vDie[i-1] = vDie[i];
                            }//for_i
                        else
                            vDie[cluster_counter-1] = null;
                        
                        cluster_counter--;
                        clusterCenters = getClusterCenters(vDie);
                        
                        vDieXYpIdM.add(0, vDie);
                        vDieXYpIdM.add(1, x);
                        vDieXYpIdM.add(2, y);
                        vDieXYpIdM.add(3, pixelIndex);
                        vDieXYpIdM.add(4, Boolean.FALSE);
                        
                        return vDieXYpIdM;
                    }//if
                    clusterCenters = getClusterCenters(vDie);
                }//else
                
                clusterCenters = getClusterCenters(vDie);
                
                //System.out.println(vDie[vDieIndex]);
                int sizeBeforeCheck = vDie[vDieIndex].size();
                java.util.Vector dieBeforeCheck = (java.util.Vector)vDie[vDieIndex].clone();
                java.util.Vector checkSpotsResult = checkSpots(vDie, vDieIndex, x, y, pixelIndex, raio, maxClusterSize);
                //System.out.println("Size after checking spots: "+(((java.util.Vector[])checkSpotsResult.get(0))[vDieIndex].size()-1));
                vDie = (java.util.Vector[])checkSpotsResult.get(0);
                x = (int[])checkSpotsResult.get(1);
                y = (int[])checkSpotsResult.get(2);
                pixelIndex = (int[])checkSpotsResult.get(3);
                
                if (vDie[vDieIndex].size() < sizeBeforeCheck)
                {
                    numberOfRemovedSpotsInDie -= (sizeBeforeCheck-vDie[vDieIndex].size());
                    for (int i =0; i < sizeBeforeCheck-1;i++)
                    {
                        if (!(dieBeforeCheck.get(i).equals(vDie[vDieIndex].get(i))))
                        {
                            minDistSpotClusterXY = removeArrayEntry(minDistSpotClusterXY, i, 0);
                            numberOfRemovedSpots = removeArrayEntry(numberOfRemovedSpots, i);
                            for (int j=index>=0?index:0; j<vDie[vDieIndex].size()-1; j++)
                            {
                                //System.out.println("MinDist["+i+"]:"+minDistSpotClusterXY[i]);
                                //System.out.println("spot:"+((Integer)vDie[clusterIndex].get(vDie[clusterIndex].size()-2)).intValue());
                                //if (minDistSpotClusterXY[i][1] > ((Integer)vDie[clusterIndex].get(vDie[clusterIndex].size()-2)).intValue())
                                numberOfRemovedSpots[j]++;
                            }//for_j
                            break;
                        }//if
                        //there is something not yet right! work in progress!!!
                    }//for_i
                }//if
                
                
                if (((Boolean)checkSpotsResult.get(4)).equals(Boolean.TRUE))
                {
                    vDieXYpIdM.add(0, vDie);
                    vDieXYpIdM.add(1, x);
                    vDieXYpIdM.add(2, y);
                    vDieXYpIdM.add(3, pixelIndex);
                    vDieXYpIdM.add(4, Boolean.TRUE);
                    
                    return vDieXYpIdM;
                }//if
                spotCount = vDie[vDieIndex].size()-1;
                int x_mean = clusterCenters[vDieIndex][0], y_mean = clusterCenters[vDieIndex][1];
                double[]distance = new double[spotCount];
                for(int indexa=0; indexa < distance.length; indexa++)
                    distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                if (spotCount != 1 && checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize)) {
                    //System.out.println("Spot Count is correct after giving away spots!");
                    //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    
                    vDieXYpIdM.add(0, vDie);
                    vDieXYpIdM.add(1, x);
                    vDieXYpIdM.add(2, y);
                    vDieXYpIdM.add(3, pixelIndex);
                    vDieXYpIdM.add(4, new Boolean(true));
                    
                    return vDieXYpIdM;
                }//if
                else if(spotCount == 1) 
                {
                    pixelIndex = new int[1];
                    pixelIndex[0] = centers[((Integer)vDie[vDieIndex].get(0)).intValue()][0] + centers[((Integer)vDie[vDieIndex].get(0)).intValue()][1] * imageWidth;
                    java.util.Vector checkDie1Vector;
                    if (isOne(pixelIndex,maxClusterSize))
                    {
                        checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                    }//if
                    else
                        checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, false);
                    if (checkDie1Vector.get(1).equals(Boolean.TRUE))
                        checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, true);
                    //java.util.Vector checkDie1Vector = checkDie1(vDie, vDieIndex, centers, info, raio, maxClusterSize, false);
                    vDieXYpIdM.add(0, (java.util.Vector[])checkDie1Vector.get(0));
                    vDieXYpIdM.add(1, x);
                    vDieXYpIdM.add(2, y);
                    vDieXYpIdM.add(3, pixelIndex);
                    vDieXYpIdM.add(4, Boolean.TRUE);
                    
                    return vDieXYpIdM;
                }//else_if
                //else{System.out.println("[4]check Spot Count Failed!! spotCount:" + spotCount);}
            }//if
        }//for_index
        
        vDieXYpIdM.add(0, vDie);
        vDieXYpIdM.add(1, x);
        vDieXYpIdM.add(2, y);
        vDieXYpIdM.add(3, pixelIndex);
        vDieXYpIdM.add(4, Boolean.FALSE);
        
        return vDieXYpIdM;
        
    }//checkIfSpotBelongsToAnother
    
    private java.util.Vector checkSpotsFromOthers(java.util.Vector[] vDie, int vDieIndex, int[] x, int[] y, int[] pixelIndex, int raio, int maxClusterSize)
    {
        if (vDie[vDieIndex].get(vDie[vDieIndex].size()-1).equals(Boolean.TRUE)) //if this die is correct, do nothing!
        {
            java.util.Vector vDieXYpI = new java.util.Vector(6);
            vDieXYpI.add(0, vDie);
            vDieXYpI.add(1, x);
            vDieXYpI.add(2, y);
            vDieXYpI.add(3, pixelIndex);
            vDieXYpI.add(4, new Integer(vDieIndex));
            vDieXYpI.add(5, Boolean.TRUE);
            return vDieXYpI;
        }//if
        //System.out.println("Checking spots from other dies!");
        int spotCount = vDie[vDieIndex].size()-1;
        java.util.Vector vDieXYpI = new java.util.Vector(6);
        int imageWidth = imageSize()[0];
        double[] minDistSpotClusterXY;
        double maxmaxDist = maxClusterSize/1.4;
        int whileCounter = 0;
        
        
        while(spotCount < 6 && whileCounter < 6) //if(spotCount < 6)
        {
            whileCounter++;
            minDistSpotClusterXY = new double[5];
            minDistSpotClusterXY[0] = 1000;
            
            for (int i=0; i<cluster_counter; i++)
            {
                if (i != vDieIndex)
                {
                    int dieSize = vDie[i].size()-1;
                    //System.out.println(vDie[i]);
                    for (int j=0; j<dieSize; j++)
                    {
                        int xTest = centers[Integer.parseInt(vDie[i].get(j).toString())][0];
                        int yTest = centers[Integer.parseInt(vDie[i].get(j).toString())][1];
                        double distTest = java.lang.Math.sqrt((double)(xTest-clusterCenters[vDieIndex][0])*(xTest-clusterCenters[vDieIndex][0])+(double)(yTest-clusterCenters[vDieIndex][1])*(yTest-clusterCenters[vDieIndex][1]));
                        
                        //System.out.println("distTest: " + distTest + "; die " + i + ", spot " + vDie[i].get(j));
                        if (distTest < maxmaxDist && distTest < minDistSpotClusterXY[0])//maxmaxDist)
                        {
                            minDistSpotClusterXY[0] = distTest;
                            minDistSpotClusterXY[1] = j;
                            minDistSpotClusterXY[2] = i;
                            minDistSpotClusterXY[3] = xTest;
                            minDistSpotClusterXY[4] = yTest;
                        }//if
                    }//for_j
                }//if
            }//for_i
            //System.out.println("minDist: " + minDistSpotClusterXY[0]+ ", maxmaxDist:" + maxmaxDist);
            if (minDistSpotClusterXY[0] < maxmaxDist)
            {
                //first, check if the die from where the spot os comming is ok
                //if it is, check if the resulting die is ok, too!
                //if it's not, just go get it!
                int testvDieIndex = (int)minDistSpotClusterXY[2];
                if(vDie[testvDieIndex].get(vDie[testvDieIndex].size()-1).equals(Boolean.TRUE))
                {
                    if (vDie[testvDieIndex].size()-2 != 0)
                    {
                        //building stuff to test resulting die
                        int[] testX = new int[vDie[testvDieIndex].size()-2];
                        int[] testY = new int[vDie[testvDieIndex].size()-2];
                        int[] testPixelIndex = new int[vDie[testvDieIndex].size()-2];
                        int testX_mean = 0, testY_mean = 0;
                        for (int i = 0; i < vDie[testvDieIndex].size()-1; i++)
                        {
                            if (i < minDistSpotClusterXY[1])
                            {
                                testX[i] = centers[((Integer)vDie[testvDieIndex].get(i)).intValue()][0];
                                testY[i] = centers[((Integer)vDie[testvDieIndex].get(i)).intValue()][1];
                                testPixelIndex[i] = testX[i] + testY[i] * imageWidth;
                                testX_mean += testX[i];
                                testY_mean += testY[i];
                            }//if
                            else if(i != minDistSpotClusterXY[1])
                            {
                                testX[i-1] = centers[((Integer)vDie[testvDieIndex].get(i)).intValue()][0];
                                testY[i-1] = centers[((Integer)vDie[testvDieIndex].get(i)).intValue()][1];
                                testPixelIndex[i-1] = testX[i-1] + testY[i-1] * imageWidth;
                                testX_mean += testX[i-1];
                                testY_mean += testY[i-1];
                            }//else_if
                        }//for_i
                        
                        java.util.Vector[] fakevDie = new java.util.Vector[vDie.length];
                        
                        for (int i = 0; i < vDie.length; i++)
                        {
                            if (vDie[i] != null)
                                fakevDie[i] = (java.util.Vector)vDie[i].clone();
                        }//for_i
                        
                        fakevDie[testvDieIndex].remove((int)minDistSpotClusterXY[1]);
                        testX_mean = testX_mean / testX.length; testY_mean = testY_mean / testY.length;
                        double[] distance = new double[testX.length];
                        for(int indexa=0; indexa < testX.length; indexa++)
                            distance[indexa] = java.lang.Math.sqrt((double)(testX_mean-testX[indexa])*(testX_mean-testX[indexa])+(double)(testY_mean-testY[indexa])*(testY_mean-testY[indexa]));
                        
                        if (!checkSpotCount(distance, testX.length, testPixelIndex, maxClusterSize))
                        {
                            vDieXYpI.add(0, vDie);
                            vDieXYpI.add(1, x);
                            vDieXYpI.add(2, y);
                            vDieXYpI.add(3, pixelIndex);
                            vDieXYpI.add(4, new Integer(vDieIndex));
                            vDieXYpI.add(5, Boolean.FALSE);
                            return vDieXYpI;
                        }//if
                    }//if
                }//if
                
                vDie = changeSpot(vDie, (int)minDistSpotClusterXY[2], (int)minDistSpotClusterXY[1], vDieIndex);
                x = addArrayEntry(x, (int)minDistSpotClusterXY[3]);
                y = addArrayEntry(y, (int)minDistSpotClusterXY[4]);
                //System.out.println("spotCount:"+(vDie[vDieIndex].size()-1));
                pixelIndex = addArrayEntry(pixelIndex, (int)minDistSpotClusterXY[4] * imageWidth + (int)minDistSpotClusterXY[3]);
                //for (int i = 0 ; i < x.length; i++)
                //{
                //    System.out.println("vDie_spot: " + vDie[vDieIndex].get(i) + " => point: " + x[i] + ", " + y[i] + "; pixelIndex:" + pixelIndex[i]);
                //}//for_i
                
                spotCount++;
                
                if (vDie[(int)(minDistSpotClusterXY[2])].size()-1==0)
                {
                    //System.out.println("vDie " + (int)(minDistSpotClusterXY[2]) + " has been depleted!");
                    if ((int)minDistSpotClusterXY[2] != cluster_counter-1)
                        for (int k= (int)minDistSpotClusterXY[2]+1; k<vDie.length;k++)
                            vDie[k-1] = vDie[k];
                    else
                        vDie[cluster_counter-1] = null;
                    
                    cluster_counter--;
                    if (vDieIndex == cluster_counter+1)
                        vDieIndex--;
                    
                    clusterCenters = getClusterCenters(vDie);
                    
                    vDieXYpI.add(0, vDie);
                    vDieXYpI.add(1, x);
                    vDieXYpI.add(2, y);
                    vDieXYpI.add(3, pixelIndex);
                    vDieXYpI.add(4, new Integer(vDieIndex));
                    vDieXYpI.add(5, Boolean.FALSE);
                                                    
                    return vDieXYpI;
                    //break;
                }//if
                clusterCenters = getClusterCenters(vDie);
                spotCount = vDie[vDieIndex].size()-1;
                int x_mean = clusterCenters[vDieIndex][0], y_mean = clusterCenters[vDieIndex][1];
                double[] distance = new double[spotCount];
                for(int indexa=0; indexa < spotCount; indexa++)
                    distance[indexa] = java.lang.Math.sqrt((double)(x_mean-x[indexa])*(x_mean-x[indexa])+(double)(y_mean-y[indexa])*(y_mean-y[indexa]));
                if (checkSpotCount(distance, spotCount, pixelIndex, maxClusterSize)) {
                    //System.out.println("Spot Count is correct after getting spots!");
                    //System.out.println("Setting die " + vDieIndex + "=>" + vDie[vDieIndex] + " as TRUE!");
                    vDie[vDieIndex].set(vDie[vDieIndex].size()-1, Boolean.TRUE);
                    vDieXYpI.add(0, vDie);
                    vDieXYpI.add(1, x);
                    vDieXYpI.add(2, y);
                    vDieXYpI.add(3, pixelIndex);
                    vDieXYpI.add(4, new Integer(vDieIndex));
                    vDieXYpI.add(5, new Boolean(true));
                    
                    return vDieXYpI;
                }//if
                //else{System.out.println("[6]check Spot Count Failed!! spotCount:" + spotCount);}
                
            }//if
            else
            {
                break;
            }//else
        }//while //if
        
        vDieXYpI.add(0, vDie);
        vDieXYpI.add(1, x);
        vDieXYpI.add(2, y);
        vDieXYpI.add(3, pixelIndex);
        vDieXYpI.add(4, new Integer(vDieIndex));
        vDieXYpI.add(5, new Boolean(false));
        
        return vDieXYpI;
    }//checkSpotsFromOthers
    
}