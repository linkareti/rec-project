package pt.utl.ist.elab.driver.Aleatorio.Utils;

/*
 * FileOps.java
 *
 * Created on 28 de Maio de 2003, 10:48
 */

/**
 *
 * @author Pedro Carvalho - LEFT - IST
 */

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileOps {
    
    public RandomAccessFile dadosFile = null;
    public String[]         dadosFileData = null;
    private String filename = null;
    
    /** Creates a new instance of FileOps */
    public FileOps(String filename, int[] newFileProperties) {
        
        this.filename = filename;
        openFile();
        
        try {
            if ((int)dadosFile.length() == 0)
            {
                System.out.println("New File: " + this.filename);
                newFileData(newFileProperties);
            }
        }
        catch(IOException e){};
        
        byte[] dadosFileTempData = null;
        try {dadosFileTempData = new byte[(int)dadosFile.length()];}
        catch(IOException e){};
        
        try {dadosFile.read(dadosFileTempData, 0, (int)dadosFile.length());}
        catch(IOException e){};
        dadosFileData = parseTempData(dadosFileTempData);
        
        closeFile();
    }
    
    //Converts byte[] to String[], each String ends when a ';' is found
    private String[] parseTempData(byte[] tempData)
    {
        String[] target1 = new String[tempData.length];  //larger than necessary, just to be sure!
        
        int stringIndex = 0;
        target1[stringIndex] = "";
        for(int index = 0; index < tempData.length; index++)
        {
            if ((char)tempData[index] != ';')
            {
                target1[stringIndex] += (char)tempData[index];
            }
            else
            {
                //System.out.println(target1[stringIndex].length());
                stringIndex++;
                if (index != tempData.length-1)
                    target1[stringIndex] = "";
            }
        }
        
        String[] target2 = new String[stringIndex]; //builds a String[] with the correct size
        for (int index = 0; index < stringIndex; index++)
        {
            target2[index] = target1[index];
        }
        //target2[stringIndex] +='\n';
        return target2;
    }
    
    public String[] getData()
    {
        return dadosFileData;
    }
    
    public double[] getDDataX()
    {
        double[] x = new double[dadosFileData.length];
        for (int index = 0; index < dadosFileData.length; index++)
            x[index]  = (double)parseString(dadosFileData[index])[0];
        return x;
    }//getDataX()
    public int[] getIDataX()
    {
        int[] x = new int[dadosFileData.length];
        for (int index = 0; index < dadosFileData.length; index++)
            x[index]  = parseString(dadosFileData[index])[0];
        return x;
    }//getDataX()
    
    public double[] getDDataY()
    {
        double[] y = new double[dadosFileData.length];
        for (int index = 0; index < dadosFileData.length; index++)
            y[index]  = (double)parseString(dadosFileData[index])[1];
        return y;
    }
    public int[] getIDataY()
    {
        int[] y = new int[dadosFileData.length];
        for (int index = 0; index < dadosFileData.length; index++)
            y[index]  = parseString(dadosFileData[index])[1];
        return y;
    }
    private static int[] parseString(String string)
    {
        int[] numbers = new int[2];
        numbers[0] = -1;
        numbers[1] = -1;
        int virgulaIndex = string.indexOf(',');
        
        if (string.length() > 0  && virgulaIndex > 0)
        {
            try {numbers[0] = Integer.parseInt(string.substring(0,virgulaIndex));}
            catch(NumberFormatException e){System.out.println(e.toString());}
            
            try {numbers[1] = Integer.parseInt(string.substring(virgulaIndex+1, string.length()));}
            catch(NumberFormatException e){System.out.println(e.toString());}
        }
        
        return numbers;
    }//parseString(String string)
    
    private void newFileData(int[] newFileProperties)
    {
        int numDice = newFileProperties[0];
        int minValueOfDie = newFileProperties[1];
        int maxValueOfDie = newFileProperties[2];
        int[] xx = new int[numDice * (maxValueOfDie - minValueOfDie)];
        int[] yy = new int[numDice * (maxValueOfDie - minValueOfDie)];
        String temp = "";
        for (int index = 0; index < xx.length; index++)
        {
            xx[index] = numDice * minValueOfDie + index;
            yy[index] = 0;
            temp += String.valueOf(xx[index]) + "," + String.valueOf(0) + ";";
        }
        
        try {dadosFile.writeBytes(temp);} //if this doesn't work use writeChars(String)
        catch(IOException e){}
        
        try {dadosFile.seek(0);}
        catch(IOException e){}
    }
    
    public void updateFile(int pos, int value)
    {
        openFile();
        int numDados = Integer.parseInt(dadosFileData[0].substring(0,dadosFileData[0].indexOf(',')));
        //Vai para o inicio do ficheiro
        try {dadosFile.seek(0);} catch(IOException e){}  
        
        int letra = 0, lineCounter = 0;
        while (lineCounter < pos-numDados)
        {
            try{letra = dadosFile.read();} catch(IOException e){}
            if ((char)letra == ';') lineCounter++;
        }
        //guarda a posicao onde se vai escrever
        int writePointer = -1;
        try{writePointer = (int)dadosFile.getFilePointer();} catch(IOException e){}
        //procura a posicao a partir da qual se vai guardar o resto do ficheiro
        letra = 0;
        while((char)letra != ';') try{letra = dadosFile.read();} catch(IOException e){}                
        //le o resto do ficheiro
        byte[] tempBytes = null;
        try {tempBytes = new byte[(int)(dadosFile.length() - dadosFile.getFilePointer())];}
        catch(IOException e){};                
        try {dadosFile.read(tempBytes);} catch(IOException e){}        
        //volta a posicao onde vai escrever
        try {dadosFile.seek(writePointer);} catch(IOException e){}
        //escreve a nova entrada
        try {dadosFile.writeBytes(String.valueOf(pos) + "," + String.valueOf(value) + ';');} catch(IOException e){}
        //escreve o que guardou
        try {dadosFile.write(tempBytes);} catch(IOException e){}
        closeFile();
    }
    
    
    //Open file
    private void openFile()
    {
        try 
        {
            //String path = "C:\\Documents and Settings\\PC\\My Documents\\Java\\Prog\\";
            dadosFile = new RandomAccessFile(filename,"rwd"); //("rwd"):Open for reading and writing, 
                                                                    //as with "rw", and also require that 
                                                                    //every update to the file's content be 
                                                                    //written synchronously to the underlying
                                                                    //storage device. 
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File Not Found!\nAborting!");
            System.exit(0);
        }
        
        FileDescriptor descricao = null;
        try {descricao = dadosFile.getFD();}
        catch(IOException e){}        
        while (!descricao.valid()){}
    }
    
    //Do not forget to close the file!!!!
    private void closeFile()
    {
        try {dadosFile.close();}
        catch(IOException e){System.err.println("Could not close file.");}
    }
}

