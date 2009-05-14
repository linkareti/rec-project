/*
 * openFile.java
 *
 * Created on 14 de Dezembro de 2002, 16:08
 */

package pt.utl.ist.elab.driver.webrobot.utils;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class OpenFile {
    
    private java.io.File file;
    private pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock[][] matrix; 
    private String[][] matrixWiring;
    private Object[][] iValues;
    private pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock block;
    private pt.utl.ist.elab.driver.webrobot.RobotStateMachine robotStateMachine;
    private final static int MAX_ROWS=1000;
    private final static int MAX_COLUMNS=1000;
    private int counter;
    private int counter2=0;
    private int iValuesSize=0;       
    private boolean endedRowCol;
    private boolean isFileValid=true;
       
    /** Creates a new instance of openFile */
    public OpenFile(pt.utl.ist.elab.driver.webrobot.RobotStateMachine robotStateMachine, java.io.File file) 
    {
        this.robotStateMachine=robotStateMachine;
        this.file=file;        
        matrix=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock[MAX_ROWS][MAX_COLUMNS];
        matrixWiring=new String[MAX_ROWS][MAX_COLUMNS];
        try
        {
            openFile(file);
        }
        catch(java.io.IOException ioe)
        {
            System.out.println("Could not open the file...\n"+ioe);
        }
    }
    
   /**This opens the file
     *and then sends every line of information to treatData()
     */
    private void openFile(java.io.File openFile) throws java.io.IOException
    {
        String data;
        endedRowCol=false;//for treatData()!
        counter=0;//for treatData()!
        int check;
        int lineSeparator=(int)System.getProperty("line.separator").charAt(0);
        StringBuffer buffer=new StringBuffer(100); ;
        java.io.FileInputStream fileInputStream=new java.io.FileInputStream(openFile);            
        
        try
        {
            buffer=new StringBuffer(200);            
            while((check=fileInputStream.read())!=-1)
            {
               /**If the file isn't valid (like doesn't follow the Prograf rules
                *to save, abort
                */
               if(!isFileValid)
               {
                   fileInputStream.close();
                   return;
               }
               /**Adds the arriving chars to a stringbuffer and send it to 
                *treatData()
                */
               buffer.append((char)check);    
               if(check==lineSeparator)
               {
                   data=buffer.toString().trim();
                   treatData(data);
                   buffer=new StringBuffer(200);            
               }               
            }
            /**to send the last line!
             */
            data=buffer.toString().trim();
            treatData(data); 
        }
        catch(java.io.IOException ioe)
        {
            System.out.println(ioe);
        }
        fileInputStream.close();
        /**Update the connections (if the components aren't side by side)
         */
        updateConnections();
    }
    
    /**Treats the data: creates new components with the data
     *from the file! The method is very obvious, but long...
     */
    private void treatData(String data)
    {
        int coluna=0;
        int nivel=0;
        int tipo=0;
        String d1=" ";
        String d2=" ";
        String d3=" ";
        int valor=0;
        int valor2=0;
        int flag=0;
        String baixo="b";
        String temp;
        String esquerda="b";                
        int numLeft=0;
        String temp2;
   /*     com.JPrograf.Models.ModelBlock model;
        com.JPrograf.Comps.Block comp;*/
        
        if(data.startsWith("--->"))
        {
            return;
        }
        if(data.startsWith("k"))
        {
            endedRowCol=true;
            return;
        }
        if(!endedRowCol)
        {
            java.util.StringTokenizer st=new java.util.StringTokenizer(data,",");
            while(st.hasMoreTokens())
            {   
                try
                {
                    coluna=Integer.parseInt(st.nextToken().trim().substring("coluna=".length()));
                    nivel=Integer.parseInt(st.nextToken().trim().substring("nivel=".length()));
                    tipo=Integer.parseInt(st.nextToken().trim().substring("tipo=".length()));
                    d1=st.nextToken().trim().substring("d1=".length());
                    if(d1.length()==0)
                    {
                        d1=" ";
                    }
                    d2=st.nextToken().trim().substring("d2=".length());
                    if(d2.length()==0)
                    {
                        d2=" ";
                    }
                    d3=st.nextToken().trim().substring("d3=".length());
                    if(d3.length()==0)
                    {
                        d3=" ";
                    }
                    valor=Integer.parseInt(st.nextToken().trim().substring("valor=".length()));
                    valor2=Integer.parseInt(st.nextToken().trim().substring("valor2=".length()));
                    temp=st.nextToken().trim();
                    java.util.StringTokenizer st2=new java.util.StringTokenizer(temp);
                    while(st2.hasMoreTokens())
                    {
                        flag=Integer.parseInt(st2.nextToken().trim().substring("flag=".length()));
                        baixo=st2.nextToken().trim().substring("baixo=".length());
                    }
                    temp2=st.nextToken().trim();
                    java.util.StringTokenizer st3=new java.util.StringTokenizer(temp2);
                    if(st3.countTokens()==1)
                    {
                        esquerda=st3.nextToken().trim().substring("esquerda=".length());
                    }
                    else
                    {
                        esquerda=st3.nextToken().trim().substring("esquerda=".length());
                        numLeft=Integer.parseInt(st3.nextToken().trim());
                    }                    
                    if(esquerda.charAt(0)=='a')
                    {
                        matrixWiring[nivel][coluna]="<-";
                    }
                    block=new pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock();
                    block.setTipo(tipo);
                    block.setBaixo(baixo.charAt(0));
                    block.setColuna(coluna);
                    block.setD1(d1);
                    block.setD2(d2);
                    block.setD3(d3);
                    block.setEsquerda(esquerda);
                    block.setFlag(flag);
                    block.setNivel(nivel);
                    block.setValor(valor);
                    block.setValor2(valor2);                    
                    matrix[nivel][coluna]=block;                    
                    return;
                }
                catch(java.lang.NumberFormatException nfe)
                {
                    isFileValid=false;
                    return;                
                }
            }
        }
        
        counter++;

        if(counter==1)
        {
            return;
        }
        else if(counter==2)
        {
            return;
        }
        else if(counter==3)
        {
            return;
        }        
        else if(counter==4)
        {
            try
            {
                java.util.StringTokenizer st4=new java.util.StringTokenizer(data);
                robotStateMachine.setB0InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB1InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB2InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB3InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB4InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB5InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB6InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setB7InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setC0InOut(Integer.parseInt(st4.nextToken()));
                robotStateMachine.setC3InOut(Integer.parseInt(st4.nextToken()));
                return;   
            }                       
            catch(java.lang.NumberFormatException nfe)
            {
                isFileValid=false;
                return;                
            }
        }
        else if(counter==5)
        {
            try
            {
                java.util.StringTokenizer st5=new java.util.StringTokenizer(data);
                robotStateMachine.setA1State(Integer.parseInt(st5.nextToken()));
                robotStateMachine.setA2State(Integer.parseInt(st5.nextToken()));
                robotStateMachine.setA3State(Integer.parseInt(st5.nextToken()));
                robotStateMachine.setA4State(Integer.parseInt(st5.nextToken()));
                String dummyVar=st5.nextToken();                
                return;
            }        
            catch(java.lang.NumberFormatException nfe)
            {
                isFileValid=false;
                return;                
            }            
        }
        else if(counter==6)
        {
            try
            {
                java.util.StringTokenizer st6=new java.util.StringTokenizer(data);
                robotStateMachine.setV1State(Integer.parseInt(st6.nextToken()));
                robotStateMachine.setV2State(Integer.parseInt(st6.nextToken()));
                robotStateMachine.setV3State(Integer.parseInt(st6.nextToken()));
                robotStateMachine.setV4State(Integer.parseInt(st6.nextToken()));
                robotStateMachine.setV5State(Integer.parseInt(st6.nextToken()));                                    
            }        
            catch(java.lang.NumberFormatException nfe)
            {
                isFileValid=false;
                return;                
            }            
            return;
        }
        if(counter==7)
        {
            iValuesSize=Integer.parseInt(data.substring(1));
            iValues=new Object[iValuesSize][3];
            counter2=0;
            return;
        }
        if(counter2<iValuesSize)
        {
            java.util.StringTokenizer st=new java.util.StringTokenizer(data,"\t");
            iValues[counter2][0]=st.nextToken();
            iValues[counter2][1]=new Integer(st.nextToken().trim());
            iValues[counter2][2]=new Integer(st.nextToken().trim());
            counter2++;
            counter=7;
            return;
        }
        if(counter==8)
        {
            java.util.StringTokenizer st2=new java.util.StringTokenizer(data);
            robotStateMachine.setI0Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI1Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI2Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI3Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI4Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI5Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI6Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            robotStateMachine.setI7Sensitivity(Integer.parseInt(st2.nextToken().trim()));
            return;
        }   
        if(counter==9)
        {
            java.util.StringTokenizer st3=new java.util.StringTokenizer(data);
            robotStateMachine.setI0OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI1OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI2OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI3OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI4OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI5OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI6OnOff(Integer.parseInt(st3.nextToken().trim()));
            robotStateMachine.setI7OnOff(Integer.parseInt(st3.nextToken().trim()));
        }
    }     
        
    private void updateConnections()
    {
        for(int iRow=2;iRow<MAX_ROWS-1;iRow++)
        {
            for(int iCol=MAX_COLUMNS-1;iCol>1;iCol--)
            {
                if(matrixWiring[iRow][iCol]!=null&&matrixWiring[iRow][iCol].startsWith("<-")&&
                    matrix[iRow][iCol-1]==null)
                {
                    matrixWiring[iRow][iCol-1]="<-";    
                }
            }
        }
    }
    
    public pt.utl.ist.elab.driver.webrobot.utils.JPrografBlock[][] getMatrix()
    {
        return matrix;        
    }
    
    public String[][] getMatrixWiring()
    {
        return matrixWiring;
    }
    
    public Object[][] getIValues()
    {
        return iValues;
    }
}
