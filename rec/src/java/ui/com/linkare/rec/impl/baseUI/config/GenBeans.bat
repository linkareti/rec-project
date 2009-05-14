set CLASSPATH=%CLASSPATH%;G:\NetBeansIDE_3.5.1\modules\schema2beans.jar
java org.netbeans.modules.schema2beans.GenBeans -f  ReCBaseUIConfig.dtd -propertyevents -ts -st -throw -validate -javabeans -attrprop -commoninterface -defaultsAccessable -p com.linkare.rec.impl.baseUI.config -r ..\..\..\..\..\..\ %1 %2 %3 %4 %5 %6 %7 %8 %9

