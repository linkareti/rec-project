/*
 * ImagePanelDisplay.java
 *
 * Created on 17 de Junho de 2003, 16:12
 */

package pt.utl.ist.elab.client.Aleatorio.displays;

/**
 *
 * @author Pedro Carvalho - LEFT - IST
 */
public class ImagePanelDisplay extends javax.swing.JPanel implements com.linkare.rec.impl.client.experiment.ExpDataDisplay, com.linkare.rec.impl.client.experiment.ExpDataModelListener{
    
    /** Creates new form ImagePanelDisplay */
    public ImagePanelDisplay() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        infoPanel = new javax.swing.JPanel();
        dostsFoundLabel = new javax.swing.JLabel();
        diceFoundLabel = new javax.swing.JLabel();
        dotsFoundText = new javax.swing.JTextField();
        diceFoundText = new javax.swing.JTextField();
        imageScrollPanel = new javax.swing.JScrollPane();
        ButtonsScrollPanel = new javax.swing.JScrollPane();
        controlsPanel = new javax.swing.JPanel();
        originalButton = new javax.swing.JButton();
        BWButton = new javax.swing.JButton();
        edgesButton = new javax.swing.JButton();
        houghButton = new javax.swing.JButton();
        countButton = new javax.swing.JButton();
        arrowLabel1 = new javax.swing.JLabel();
        arrowLabel11 = new javax.swing.JLabel();
        arrowLabel12 = new javax.swing.JLabel();
        arrowLabel13 = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        refineCountButton = new javax.swing.JButton();
        arrowLabel14 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        setMinimumSize(new java.awt.Dimension(700, 500));
        infoPanel.setLayout(new java.awt.GridLayout(2, 0));

        infoPanel.setName("infoPanel");
        dostsFoundLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dostsFoundLabel.setText("Dots Found");
        dostsFoundLabel.setToolTipText("Number of dots found in the image.");
        dostsFoundLabel.setName("dostsFoundLabel");
        infoPanel.add(dostsFoundLabel);

        diceFoundLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        diceFoundLabel.setText("Dice Found");
        diceFoundLabel.setToolTipText("The number of dice found in the image by clustering (might not be correct if there are at least 2 dice close toghether).");
        diceFoundLabel.setName("diceFoundLabel");
        infoPanel.add(diceFoundLabel);

        dotsFoundText.setEditable(false);
        dotsFoundText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        dotsFoundText.setToolTipText("Number of dots found in the image.");
        dotsFoundText.setName("dotsFoundText");
        infoPanel.add(dotsFoundText);

        diceFoundText.setBackground(new java.awt.Color(204, 204, 204));
        diceFoundText.setEditable(false);
        diceFoundText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        diceFoundText.setToolTipText("The number of dice found in the image by clustering (might not be correct if there are at least 2 dice close toghether).");
        diceFoundText.setName("diceFoundText");
        infoPanel.add(diceFoundText);

        add(infoPanel, java.awt.BorderLayout.NORTH);

        imageScrollPanel.setDoubleBuffered(true);
        imageScrollPanel.setMinimumSize(new java.awt.Dimension(100, 100));
        imageScrollPanel.setPreferredSize(new java.awt.Dimension(100, 100));
        add(imageScrollPanel, java.awt.BorderLayout.CENTER);

        ButtonsScrollPanel.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        ButtonsScrollPanel.setMinimumSize(new java.awt.Dimension(7, 43));
        ButtonsScrollPanel.setPreferredSize(new java.awt.Dimension(624, 43));
        controlsPanel.setLayout(new java.awt.GridBagLayout());

        controlsPanel.setName("controlsPanel");
        originalButton.setMnemonic('o');
        originalButton.setText("Original");
        originalButton.setToolTipText("Display the original image.");
        originalButton.setName("originalButton");
        originalButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                originalButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        controlsPanel.add(originalButton, gridBagConstraints);

        BWButton.setMnemonic('b');
        BWButton.setText("B&W");
        BWButton.setToolTipText("Display the Black&Whyte image.");
        BWButton.setName("BWButton");
        BWButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                BWButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(BWButton, gridBagConstraints);

        edgesButton.setMnemonic('e');
        edgesButton.setText("Edges");
        edgesButton.setToolTipText("Displays the image of the detected edges.");
        edgesButton.setName("edgesButton");
        edgesButton.setEnabled(false);
        edgesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                edgesButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(edgesButton, gridBagConstraints);

        houghButton.setMnemonic('h');
        houghButton.setText("Hough");
        houghButton.setToolTipText("Displays the Hough Transform image.");
        houghButton.setName("houghButton");
        houghButton.setEnabled(false);
        houghButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                houghButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(houghButton, gridBagConstraints);

        countButton.setMnemonic('c');
        countButton.setText("Count");
        countButton.setToolTipText("Displays the original image (with crosses where dots were found and circles where dice where found) and updates the counters.");
        countButton.setName("counterButton");
        countButton.setEnabled(false);
        countButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                countButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 8;
        controlsPanel.add(countButton, gridBagConstraints);

        arrowLabel1.setText("=>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(arrowLabel1, gridBagConstraints);

        arrowLabel11.setText("=>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(arrowLabel11, gridBagConstraints);

        arrowLabel12.setText("=>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(arrowLabel12, gridBagConstraints);

        arrowLabel13.setText("=>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(arrowLabel13, gridBagConstraints);

        nextButton.setText("Next Sample");
        nextButton.setName("nextButton");
        nextButton.setEnabled(false);
        nextButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nextButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(nextButton, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setPreferredSize(new java.awt.Dimension(20, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(jSeparator1, gridBagConstraints);

        refineCountButton.setMnemonic('r');
        refineCountButton.setText("Refine");
        refineCountButton.setEnabled(false);
        refineCountButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                refineCountButtonActionPerformedHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(refineCountButton, gridBagConstraints);

        arrowLabel14.setText("=>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        controlsPanel.add(arrowLabel14, gridBagConstraints);

        ButtonsScrollPanel.setViewportView(controlsPanel);

        add(ButtonsScrollPanel, java.awt.BorderLayout.SOUTH);

    }//GEN-END:initComponents

    private void refineCountButtonActionPerformedHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refineCountButtonActionPerformedHandler
        // Add your handling code here:
        if (analysisPanel[currentAnalysisPanelIndex].getImage("refineCount") == null)
            analysisPanel[currentAnalysisPanelIndex].refineCount(analysisPanel[currentAnalysisPanelIndex].IMAGE_HOUGH);
        else analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("refineCount"));
        dotsFoundText.setText(String.valueOf(analysisPanel[currentAnalysisPanelIndex].getCenterCounter()));
        diceFoundText.setText(String.valueOf(analysisPanel[currentAnalysisPanelIndex].getClusterCounter()));
        analysing = false;
        
        //if (analysisPanel.length>currentAnalysisPanelIndex+1)
        if (currentAnalysisPanelCount > currentAnalysisPanelIndex+1)
            nextButton.setEnabled(true);
        
        imageDisplaySingleton.setStoredInt(analysisPanel[currentAnalysisPanelIndex].getCenterCounter());    //stores the centerCounter
                                                                                    //in the Singleton to be accessed for the statistics
        //viewingPanel.repaint();
        analysisPanel[currentAnalysisPanelIndex].repaint();
        imageScrollPanel.repaint();
    }//GEN-LAST:event_refineCountButtonActionPerformedHandler

    private void originalButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originalButtonHandler
        // Add your handling code here:
        if(analysisPanel!=null && analysisPanel[currentAnalysisPanelIndex]!=null)
        {
            analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("original"));
            //viewingPanel.repaint();
            analysisPanel[currentAnalysisPanelIndex].repaint();
            imageScrollPanel.repaint();
        }
    }//GEN-LAST:event_originalButtonHandler

    private void BWButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BWButtonHandler
        // Add your handling code here:
        if (analysisPanel != null)
        {
            if (analysisPanel[currentAnalysisPanelIndex].getImage("bw") == null)
                analysisPanel[currentAnalysisPanelIndex].conversionBW();
            else analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("bw"));
            edgesButton.setEnabled(true);
            //viewingPanel.repaint();
            analysisPanel[currentAnalysisPanelIndex].repaint();
            imageScrollPanel.repaint();
        }//if
    }//GEN-LAST:event_BWButtonHandler

    private void edgesButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edgesButtonHandler
        // Add your handling code here:
        if (analysisPanel[currentAnalysisPanelIndex].getImage("edges") == null)
            analysisPanel[currentAnalysisPanelIndex].edgeDetector();
        else analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("edges"));
        houghButton.setEnabled(true);
        analysisPanel[currentAnalysisPanelIndex].repaint();
        imageScrollPanel.repaint();
    }//GEN-LAST:event_edgesButtonHandler

    private void houghButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_houghButtonHandler
        // Add your handling code here:
        if (analysisPanel[currentAnalysisPanelIndex].getImage("hough") == null)
            analysisPanel[currentAnalysisPanelIndex].houghTransform();
        else analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("hough"));
        countButton.setEnabled(true);
        analysisPanel[currentAnalysisPanelIndex].repaint();
        imageScrollPanel.repaint();
    }//GEN-LAST:event_houghButtonHandler

    private void countButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countButtonHandler
        // Add your handling code here:
        if (analysisPanel[currentAnalysisPanelIndex].getImage("houghCount") == null)
            analysisPanel[currentAnalysisPanelIndex].houghCount();
        else analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("houghCount"));
        dotsFoundText.setText(String.valueOf(analysisPanel[currentAnalysisPanelIndex].getHoughCenterCounter()));
        //diceFoundText.setText(String.valueOf(analysisPanel[0].getClusterCounter()));
        
        analysing = true;
        refineCountButton.setEnabled(true);
        analysisPanel[currentAnalysisPanelIndex].repaint();
        imageScrollPanel.repaint();
    }//GEN-LAST:event_countButtonHandler

    private void nextButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonHandler
        // Add your handling code here:
        
        //analysisPanel = removeArrayEntry(analysisPanel,0);
        //if (analysisPanel.length==1)
        //    nextButton.setEnabled(false);
        
        //for (int index = 1; index < analysisPanel.length; index++)
        //    analysisPanel[index-1] = (AnalysisPanel)analysisPanel[index].clone();
            
        
        //analysisPanel[analysisPanel.length] = null;
        //currentAnalysisPanelCount--;
        currentAnalysisPanelIndex++;
        imageScrollPanel.setViewportView(analysisPanel[currentAnalysisPanelIndex]);
        //analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("original"));
        analysisPanel[currentAnalysisPanelIndex] = analyseImage(analysisPanel[currentAnalysisPanelIndex]);
        analysisPanel[currentAnalysisPanelIndex].setImage(analysisPanel[currentAnalysisPanelIndex].getImage("original"));
        
        analysisPanel[currentAnalysisPanelIndex-1].resetImages();   //just so we don't use up too much mem!
        imageDisplaySingleton.setImage(analysisPanel[currentAnalysisPanelIndex].getImage("original"));
        
        dotsFoundText.setText("");
        diceFoundText.setText("");
        analysing = true;
        edgesButton.setEnabled(false);
        houghButton.setEnabled(false);
        countButton.setEnabled(false);
        refineCountButton.setEnabled(false);
        analysisPanel[currentAnalysisPanelIndex].repaint();
        imageScrollPanel.repaint();
        if (analysisPanel.length <= currentAnalysisPanelIndex+1)
            nextButton.setEnabled(false);
    }//GEN-LAST:event_nextButtonHandler
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BWButton;
    private javax.swing.JScrollPane ButtonsScrollPanel;
    private javax.swing.JLabel arrowLabel1;
    private javax.swing.JLabel arrowLabel11;
    private javax.swing.JLabel arrowLabel12;
    private javax.swing.JLabel arrowLabel13;
    private javax.swing.JLabel arrowLabel14;
    private javax.swing.JPanel controlsPanel;
    private javax.swing.JButton countButton;
    private javax.swing.JLabel diceFoundLabel;
    private javax.swing.JTextField diceFoundText;
    private javax.swing.JLabel dostsFoundLabel;
    private javax.swing.JTextField dotsFoundText;
    private javax.swing.JButton edgesButton;
    private javax.swing.JButton houghButton;
    private javax.swing.JScrollPane imageScrollPanel;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton originalButton;
    private javax.swing.JButton refineCountButton;
    // End of variables declaration//GEN-END:variables
    
    /**My vars*/
    private com.linkare.rec.impl.client.experiment.ExpDataModel model;
    private String name="Image Analysis";
    
    private javax.swing.Icon icon=new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/client/Aleatorio/Resource/AleatorioIcon.gif"));
    
    private int imageWidth = 640;
    private int imageHeight = 480;
    private AnalysisPanel[] analysisPanel = null;
    private boolean analysing = false;
    private int currentAnalysisPanelCount = 0, currentAnalysisPanelIndex=0;
    private pt.utl.ist.elab.client.Aleatorio.utils.StorageSingleton imageDisplaySingleton = pt.utl.ist.elab.client.Aleatorio.utils.StorageSingleton.getSingleton(); 
    private int[] configurationValues;
    private byte[] configurationByteArray;
    private byte[] imageByteArray =null;
    private boolean configured = false;
    
    /**
     *ExpDataDisplay Implementation
     */
    public void setExpDataModel(com.linkare.rec.impl.client.experiment.ExpDataModel model)
    {
        if(this.model!=null)
            model.removeExpDataModelListener(this);
	
	this.model=model;
	
	if(this.model!=null)
            this.model.addExpDataModelListener(this);
        
    }//setExpDataModel(ExpDataModel model)
    
    public javax.swing.JComponent getDisplay()
    {
        return this;
    }//getDisplay()

    public String getName()
    {
        return name;
    }//getName()
    
    public javax.swing.Icon getIcon()
    {
        return icon;
    }//getIcon()
    
    public javax.swing.JMenuBar getMenuBar()
    {
        return null;
    }//getMenuBar()
    
    public javax.swing.JToolBar getToolBar() 
    {
        return null;
    }//getToolBar()

    
    
    /**
     *ExpDataModelListener implementation
     */
    
    public void newSamples(com.linkare.rec.impl.client.experiment.NewExpDataEvent evt)
    {
        System.out.println(">>> Received New Samples!!");
        System.out.println("DataChannels:");
        for(int i=0;i<model.getChannelCount();i++)
        {
            if (model.getValueAt(evt.getSamplesStartIndex(), i) != null)
                System.out.println("channel["+i+"].name="+model.getChannelName(i));
        }
        
        for(int i=evt.getSamplesStartIndex();i<=evt.getSamplesEndIndex();i++)
        {
            //System.out.println(">>> checking sample "+i+"!!");
            //byte[] imageByteArray =null;
            //imageByteArray =null;
            
            //System.out.println("checking for image = "+(model.getValueAt(i, model.getChannelIndex("Image")) != null));
            if (model.getValueAt(i, model.getChannelIndex("Image")) != null)
            {
                if (analysisPanel == null)
                    analysisPanel = new AnalysisPanel[1];
                else
                {
                    analysisPanel = addArrayPanel(analysisPanel);
                    imageScrollPanel.setViewportView(analysisPanel[currentAnalysisPanelIndex]);
                }//else

                imageByteArray = model.getValueAt(i, model.getChannelIndex("Image")).getValue().getByteArrayValue().getData();
                java.awt.Image tempImage = byteArray2Image(imageByteArray);

                //System.out.println("analysisPanel.length:"+analysisPanel.length + "; currentAnalysisPanelCount:"+currentAnalysisPanelCount);
                analysisPanel[currentAnalysisPanelCount] = new AnalysisPanel(tempImage);
                
                if (configured)
                {
                    analysisPanel[currentAnalysisPanelCount].setParams(configurationValues[0], 
                                                                configurationValues[1],
                                                                configurationValues[2],
                                                                configurationValues[3],
                                                                configurationValues[4], 
                                                                configurationValues[5], 
                                                                configurationValues[6],
                                                                configurationValues[7]);
                    System.out.println("currentAnalysisPanelCount:"+currentAnalysisPanelCount+"\nanalysisPanel:"+analysisPanel+"\nconfigured:"+configured);
                }//if
                //if (currentAnalysisPanelCount == 0) viewingPanel.add(analysisPanel[0]);
                analysisPanel[currentAnalysisPanelCount].setPreferredSize(new java.awt.Dimension(analysisPanel[currentAnalysisPanelCount].imageSize()[0],analysisPanel[currentAnalysisPanelCount].imageSize()[1]));
                analysisPanel[currentAnalysisPanelCount].revalidate();
                if (currentAnalysisPanelCount == 0)
                {
                    imageScrollPanel.setViewportView(analysisPanel[0]);
                    analysisPanel[0].repaint();
                    imageScrollPanel.repaint();
                    imageDisplaySingleton.setImage(analysisPanel[0].getImage("original"));
                }//if
                else
                    nextButton.setEnabled(true);
                currentAnalysisPanelCount++;
                //System.out.println("Image:"+analysisPanel[currentAnalysisPanelCount-1].getImage("original"));
            }//if__image
            
            //configurationByteArray = null;
            //System.out.println("checking for config: "+(model.getValueAt(i, model.getChannelIndex("Configuration")) != null));
            if (model.getValueAt(i, model.getChannelIndex("Configuration")) != null)
            {
                System.out.println("Configuring!");
                configurationByteArray = model.getValueAt(i, model.getChannelIndex("Configuration")).getValue().getByteArrayValue().getData();
                configurationValues =  byteArray2IntArray(configurationByteArray);
                if (analysisPanel[currentAnalysisPanelCount-1] != null)
                {
                    analysisPanel[currentAnalysisPanelCount-1].setParams(configurationValues[0], 
                                                                configurationValues[1],
                                                                configurationValues[2],
                                                                configurationValues[3],
                                                                configurationValues[4], 
                                                                configurationValues[5], 
                                                                configurationValues[6],
                                                                configurationValues[7]);
                }//if
                configured=true;
            }//if__confiuration

            if (analysing && currentAnalysisPanelCount >1)
            {
                //currentAnalysisPanelCount++;
                nextButton.setEnabled(true);
            }
            
            /*
            if (currentAnalysisPanelCount == 1 && analysisPanel != null && configured)
            {
                //viewingPanel.add(analysisPanel[0]);
                //imageScrollPanel.add(analysisPanel[0]);
                imageDisplaySingleton.setImage(analysisPanel[0].getImage(analysisPanel[0].IMAGE_ORIGINAL));
                //System.out.println(">>>Analysing Image!");
                //analysisPanel[0] = analyseImage(analysisPanel[0]);
                //System.out.println(">>>Image Analysed!");
                analysing = true;
            }
             */
            System.out.println(">>> Processed new Sample!");
        }
    }//newSamples(NewExpDataEvent evt)
    
    public void dataModelStoped()
    {
    }//dataModelStoped()
    
    public void dataModelRunning()
    {
    }//dataModelRunning()

    public void headerAvailable(com.linkare.rec.data.config.HardwareAcquisitionConfig header)
    {
    }//headerAvailable(HardwareAcquisitionConfig header)
    
    
    /**
     *Utilities
     */

    private java.awt.Image byteArray2Image(byte[] byteArray)
    {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        try {baos.write(byteArray);}
        catch(java.io.IOException e){}
        
        java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(baos.toByteArray());
        
        javax.imageio.stream.MemoryCacheImageInputStream mciis = new javax.imageio.stream.MemoryCacheImageInputStream(bais);
        
        java.awt.image.BufferedImage bImage = null;
        
        //converting the jpg byteArray into an image
        try {bImage = javax.imageio.ImageIO.read(mciis);}
        catch(java.io.IOException e){}
        
        return (java.awt.Image) bImage;
    }//byteArray2Image(byte[] byteArray)
    
    private java.awt.Image byteArray2Image_old(byte[] byteArray)
    {
        int[] pixels = new int[byteArray.length / 3];
        for(int index = 0; index < byteArray.length; index+=3)
        {
            pixels[index/3] = (int)(byteArray[index] & 0xff) << 16;
            pixels[index/3]+= (int)(byteArray[index+1] & 0xff) << 8;
            pixels[index/3]+= (int)(byteArray[index+2] & 0xff);
        }
        
        java.awt.image.MemoryImageSource mis = new java.awt.image.MemoryImageSource(imageWidth,imageHeight,pixels,0,imageWidth);
        java.awt.Image image = createImage(mis);
        
        java.awt.MediaTracker tracker = new java.awt.MediaTracker(this);
        tracker.addImage(image,0);
        try
        {
            tracker.waitForAll();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return image;
    }//byteArray2Image(byte[] byteArray) ******** OLD ********

    private int[] byteArray2IntArray(byte[] byteArray)
    {
        int[] temp = new int[byteArray.length / 4];
        
        for (int index = 0; index < temp.length; index++)
        {
            temp[index] = (int)(byteArray[4*index] & 0xff) << 24;
            temp[index] += (int)(byteArray[4*index+1] & 0xff) << 16;
            temp[index] += (int)(byteArray[4*index+2] & 0xff) << 8;
            temp[index] += (int)(byteArray[4*index+3] & 0xff);
        }
        return temp;
    }//byteArray2IntArray(byte[] byteArray)
    
    private AnalysisPanel analyseImage (AnalysisPanel analysisPan)
    {
        analysisPan.conversionBW();
        analysisPan.edgeDetector();
        analysisPan.houghTransform();
        analysisPan.houghCount();
        //analysisPan.convolutionTransform();
        //analysisPan.fullCount();
        analysisPan.refineCount(analysisPanel[currentAnalysisPanelIndex].IMAGE_HOUGH);;
        return analysisPan;
    }//analyseImage (AnalysisPanel analysisPan)
    
    private AnalysisPanel[] addArrayPanel(AnalysisPanel[] analysisPan)
    {
        AnalysisPanel[] out = new AnalysisPanel[analysisPan.length+1];
        for (int i= 0; i < analysisPan.length; i++)
        {
            out[i] = (AnalysisPanel)analysisPan[i].clone();
        }//for_i
        out[out.length-1] = null;
        return out;
    }//addArrayPanel
    
    private AnalysisPanel[] removeArrayEntry(AnalysisPanel[] analysisPan, int index)
    {
        AnalysisPanel[] returnPanel = new AnalysisPanel[analysisPan.length-1];
        for (int i=0; i< analysisPan.length; i++)
        {
            if (i<index)
            {
                returnPanel[i] = analysisPan[i];
            }//if
            else if (i>index)   
            {
                returnPanel[i-1] = analysisPan[i];
            }//else_if
        }//for_i
        return returnPanel;
    }
    
    public void dataModelWaiting()
    {
    }
    
    public void dataModelStarted()
    {
    }
    
    public void dataModelStartedNoData()
    {
    }
    
    public void dataModelEnded()
    {
    }
    
    public void dataModelError()
    {
    }
    
//removeArrayEntry
}
