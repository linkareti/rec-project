/*
 * testCasts.java
 *
 * Created on 4 de Dezembro de 2003, 1:27
 */

package test;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class testCastsAndArrays
{
    
    /** Creates a new instance of testCasts */
    public testCastsAndArrays()
    {
    }
    
    public static void main(java.lang.String[] args)
    {
	String [] values={"1","2","3","4"};
	Object[] valuesObj=(Object[])values;
	String[] revert=(String[])valuesObj;
	
	
	Object[] valuesObj2={"1","2","3"};
	String[] values2=new String[valuesObj2.length];
	System.arraycopy(valuesObj2, 0, values2, 0, values2.length);
	
	
	int[] arrInt=new int[]{1,2,3,4,5,6,7};
	Object objIntArr=(Object)arrInt;
	System.out.println(""+arrInt.getClass());
	
	
	Object[] valuesPlus=(Object[])addArrayEntrance(values, "Test");
	
	System.out.println("valuesPlus="+valuesPlus);
	
	String[] revertValues=(String[])valuesPlus;
	
	objIntArr=addArrayEntrance(arrInt, new Integer(10));
	
	int[] newIntArr=(int[])objIntArr;
	
	for(int i=0;i<arrInt.length;i++)
	{
	    System.out.println("arrInt["+i+"]="+arrInt[i]);
	}
	
	for(int i=0;i<newIntArr.length;i++)
	{
	    System.out.println("newIntArr["+i+"]="+newIntArr[i]);
	}
	
	int [] size0intArray=new int[0];
	Object objIntArr1=addArrayEntrance(size0intArray,  new Integer(1));
	
	int[] newIntArr1=(int[])objIntArr1;
	
	for(int i=0;i<size0intArray.length;i++)
	{
	    System.out.println("size0intArray["+i+"]="+size0intArray[i]);
	}
	
	for(int i=0;i<newIntArr1.length;i++)
	{
	    System.out.println("newIntArr1["+i+"]="+newIntArr1[i]);
	}
	
	System.out.println(size0intArray.getClass().getComponentType());
	
	
	
	
	
	
	System.out.println("All ok");
    }
    
    
    public static Object addArrayEntrance(Object values,Object newValue)
    {
	Object tempValues=null;
	
	tempValues=java.lang.reflect.Array.newInstance(values.getClass().getComponentType(),java.lang.reflect.Array.getLength(values)+1);
	System.arraycopy(values, 0,tempValues,0,java.lang.reflect.Array.getLength(values));
	java.lang.reflect.Array.set(tempValues,java.lang.reflect.Array.getLength(values),newValue);
	
	return tempValues;
	
    }
}
