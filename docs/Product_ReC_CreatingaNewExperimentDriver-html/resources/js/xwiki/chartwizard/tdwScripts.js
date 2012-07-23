function tdwWizard(){var Q=this;
var AB="none";
var q="none";
var D,C,i,j;
var a;
var M;
var p=false;
var n;
var Y;
var d;
var G;
var U;
var h;
var e;
var o;
var m;
var r;
var T;
var O;
var I=["Doc","Range","Extra"];
var w;
var g={Doc:true,Range:false,Extra:false};
var l=false;
var V=true;
var N=false;
this.initialize=function(AD,AC,AE){n=document.getElementById("tdwTables");
Y=document.getElementById("tdwWaiting");
d=document.getElementById("tdwRequestError");
G=document.getElementById("tdwNoTables");
U=document.getElementById("tdwSelectRange");
e=document.getElementById("tdwBackButton");
h=document.getElementById("tdwNextButton");
o=document.getElementById("tdwFinishButton");
m=AD;
r=AC;
T=AE;
if(document.documentElement.addEventListener){document.documentElement.addEventListener("mouseup",AA,true)
}else{document.documentElement.attachEvent("onmouseup",P);
document.documentElement.attachEvent("onselectstart",c);
document.documentElement.attachEvent("onselect",Z)
}w=I[0];
document.getElementById("tdw"+w+"Wizard").className="tdwActivePage";
document.getElementById("tdw"+w+"WizardButton").className="tdwNavigationImage";
if(document.getElementById("tdwPageInput").selectedIndex==-1){disableNext()
}};
getPageIndex=function(AC){for(var AD=0;
AD<I.length;
AD++){if(I[AD]==AC){return AD
}}};
getNextPageIndex=function(AC){if(AC>=I.length-1){return -1
}return AC+1
};
getPrevPageIndex=function(AC){return AC-1
};
enableBack=function(){e.className="tdwButton";
l=true
};
disableBack=function(){e.className="tdwButtonDisabled";
l=false
};
enableNext=function(){h.className="tdwButton";
V=true
};
disableNext=function(){h.className="tdwButtonDisabled";
V=false
};
enableFinish=function(){o.className="tdwButton";
N=true
};
disableFinish=function(){o.className="tdwButtonDisabled";
N=false
};
enablePage=function(AD){g[AD]=true;
var AC=document.getElementById("tdw"+AD+"WizardButton");
AC.className="tdwNavigationImage";
if(AC.src.indexOf("Hover.png")>=0){AC.src=r+"chwTaskCompletedHover.png"
}else{AC.src=r+"chwTaskCompleted.png"
}};
disablePage=function(AD){g[AD]=false;
var AC=document.getElementById("tdw"+AD+"WizardButton");
AC.className="tdwNavigationImageDisabled";
AC.src=r+"chwTaskWaiting.png"
};
this.showWizardPage=function(AF){if(w==AF){return 
}if(!g[AF]){return 
}var AE=getPageIndex(w);
if(AE==0){document.getElementById("tdwBackButton").className="tdwButton";
l=true
}var AC=getNextPageIndex(AE);
if(AC==-1){document.getElementById("tdwNextButton").className="tdwButton";
V=true
}document.getElementById("tdw"+w+"Wizard").className="tdwInactivePage";
var AD=document.getElementById("tdw"+w+"WizardButton");
if(AD.src.indexOf("Hover.png")>=0){AD.src=r+"chwTaskCompletedHover.png"
}else{AD.src=r+"chwTaskCompleted.png"
}w=AF;
if(w=="Range"&&!p){disableNext()
}var AE=getPageIndex(w);
if(AE==0){document.getElementById("tdwBackButton").className="tdwButtonDisabled";
l=false;
enableNext()
}var AC=getNextPageIndex(AE);
if(AC==-1){document.getElementById("tdwNextButton").className="tdwButtonDisabled";
V=false
}document.getElementById("tdw"+w+"Wizard").className="tdwActivePage";
AD=document.getElementById("tdw"+w+"WizardButton");
if(AD.src.indexOf("Hover.png")>=0){AD.src=r+"chwTaskCompletingHover.png"
}else{AD.src=r+"chwTaskCompleting.png"
}};
this.showNextPage=function(){if(!V){return false
}var AD=getPageIndex(w);
if(AD==0){this.getTablesFromPage(document.getElementById("tdwPageInput").value)
}var AC=getNextPageIndex(AD);
AC=I[AC];
enablePage(AC);
this.showWizardPage(AC);
return false
};
this.showPrevPage=function(){if(!l){return false
}enableNext();
var AD=getPageIndex(w);
var AC=getPrevPageIndex(AD);
AC=I[AC];
this.showWizardPage(AC);
return false
};
this.change=function(){disablePage("Range");
disablePage("Extra");
disableFinish();
enableNext()
};
this.flipVisible=function(AC,AE){var AD=document.getElementById("tdw"+AC+"Div");
if(AE){AD.className="tdwVisible"
}else{AD.className="tdwHidden"
}};
var J=function(){switch(q){case"table":return"*";
case"columns":return String.fromCharCode(64+i)+"-"+String.fromCharCode(64+j);
case"rows":return D+"-"+C;
case"cells":return String.fromCharCode(64+i)+D+"-"+String.fromCharCode(64+j)+C
}return"*"
};
this.finish=function(){if(!N){return false
}if(!window.opener){return false
}if(document.getElementById("tdwSaveInput").checked){if(document.getElementById("tdwSaveNameInput").value==""){if(!confirm(document.getElementById("tdwSaveNoNameMsg").firstChild.nodeValue)){return false
}}if(z()){window.opener.wizard.setValidDatasource("type:object;doc:"+M+";class:TableDataSource;object_number:"+O)
}else{return false
}}else{window.opener.wizard.setValidDatasource("type:table;doc:"+M+";table_number:"+a+";range:"+J()+";has_header_row:"+document.getElementById("tdwRowHeaderInput").checked+";has_header_column:"+document.getElementById("tdwColumnHeaderInput").checked+";ignore_alpha:"+document.getElementById("tdwIgnoreAlphaInput").checked+";decimal_symbol:"+(document.getElementById("tdwDecimalSymbolInput").checked?"comma":"period"))
}window.close();
return false
};
this.enterButton=function(AC){if(!g[AC]){return false
}var AD=document.getElementById("tdw"+AC+"WizardButton");
var AE=AD.src;
if(AE.indexOf("Hover.png")>=0){return 
}AE=AE.substring(0,AE.indexOf(".png"))+"Hover.png";
AD.src=AE
};
this.leaveButton=function(AC){if(!g[AC]){return 
}var AD=document.getElementById("tdw"+AC+"WizardButton");
var AE=AD.src;
if(AE.indexOf("Hover.png")<0){return 
}AE=AE.substring(0,AE.indexOf("Hover.png"))+".png";
AD.src=AE
};
var u=function(AF){var AD=f(AF);
var AE=AF.substring(AF.indexOf("R")+1,AF.indexOf("C"))-0;
var AC=AF.substring(AF.indexOf("C")+1)-0;
return[AD,AE,AC]
};
var f=function(AC){return AC.substring(AC.indexOf("T")+1,AC.indexOf("R"))-0
};
var x=function(AC){return AC.substring(AC.indexOf("R")+1,AC.indexOf("C"))-0
};
var W=function(AC){return AC.substring(AC.indexOf("C")+1)-0
};
var v=function(AC){if(AC<0){return -1
}else{if(AC>0){return 1
}else{return AC
}}};
var R=function(){switch(q){case"none":break;
case"table":document.getElementById("T"+a).className="tdwUnselectedTable";
break;
case"rows":for(var AD=D;
AD<=C;
AD++){document.getElementById("T"+a+"R"+AD).className="tdwUnselectedRow"
}break;
case"columns":for(var AD=i;
AD<=j;
AD++){document.getElementById("T"+a+"C"+AD).className="tdwUnselectedColumn";
document.getElementById("T"+a+"R0C"+AD).className="tdwUnselectedCell"
}break;
case"cells":for(var AD=D;
AD<=C;
AD++){for(var AC=i;
AC<=j;
AC++){document.getElementById("T"+a+"R"+AD+"C"+AC).className="tdwUnselectedCell"
}}break
}};
var S=function(AF){R();
p=false;
AB="none";
var AD=f(AF);
var AE=x(AF);
var AC=W(AF);
if(AE==0){if(AC==0){document.getElementById("T"+AD).className="tdwSelectedTable";
q="table";
enableNext();
enableFinish();
p=true
}else{document.getElementById("T"+AD+"C"+AC).className="tdwSelectedColumn";
document.getElementById("T"+AD+"R0C"+AC).className="tdwSelectedCell";
AB="columns";
q="columns"
}}else{if(AC==0){document.getElementById("T"+AD+"R"+AE).className="tdwSelectedRow";
AB="rows";
q="rows"
}else{document.getElementById("T"+AD+"R"+AE+"C"+AC).className="tdwSelectedCell";
AB="cells";
q="cells"
}}a=AD;
D=AE;
C=AE;
i=AC;
j=AC;
if(!p){disableNext();
disableFinish();
disablePage("Extra")
}};
var y=function(AC){if(AC.button!=0){return true
}if(AB!="none"){AA(AC)
}S(AC.target.id);
AC.preventDefault();
AC.stopPropagation();
return false
};
var H=function(){var AC=window.event;
if(AC.button!=1){return true
}if(AB!="none"){P()
}S(AC.srcElement.id);
AC.returnValue=false;
AC.cancelBubble=true;
return false
};
var L=function(AD){if(AB=="none"){return 
}var AJ=f(AD);
var AC=x(AD);
var AL=W(AD);
if(AJ!=a){return 
}switch(AB){case"rows":if(AC==0){AC=1
}if(C==AC){break
}if(D==C||v(C-D)==v(AC-C)){var AF=v(AC-C);
for(var AI=C+AF;
(AC-AI)*AF>=0;
AI+=AF){document.getElementById("T"+AJ+"R"+AI).className="tdwSelectedRow"
}}else{var AF=v(C-D);
var AE=((D+AC)+AF*v(D-AC)*(D-AC))/2+AF;
for(var AI=AE;
(C-AI)*AF>=0;
AI+=AF){document.getElementById("T"+AJ+"R"+AI).className="tdwUnSelectedRow"
}for(var AI=D-AF;
(AC-AI)*AF<=0;
AI-=AF){document.getElementById("T"+AJ+"R"+AI).className="tdwSelectedRow"
}}break;
case"columns":if(AL==0){AL=1
}if(j==AL){break
}if(i==j||v(j-i)==v(AL-j)){var AF=v(AL-j);
for(var AI=j+AF;
(AL-AI)*AF>=0;
AI+=AF){document.getElementById("T"+AJ+"C"+AI).className="tdwSelectedColumn";
document.getElementById("T"+AJ+"R0C"+AI).className="tdwSelectedCell"
}}else{var AF=v(j-i);
var AE=((i+AL)+AF*v(i-AL)*(i-AL))/2+AF;
for(var AI=AE;
(j-AI)*AF>=0;
AI+=AF){document.getElementById("T"+AJ+"C"+AI).className="tdwUnSelectedColumn";
document.getElementById("T"+AJ+"R0C"+AI).className="tdwUnselectedCell"
}for(var AI=i-AF;
(AL-AI)*AF<=0;
AI-=AF){document.getElementById("T"+AJ+"C"+AI).className="tdwSelectedColumn";
document.getElementById("T"+AJ+"R0C"+AI).className="tdwSelectedCell"
}}break;
case"cells":if(AC==0){AC=1
}if(C==AC){}else{if(D==C||v(C-D)==v(AC-C)){var AF=v(AC-C);
var AK=v(j-i);
if(AK==0){AK=1
}for(var AI=C+AF;
(AC-AI)*AF>=0;
AI+=AF){for(var AH=i;
(j-AH)*AK>=0;
AH+=AK){document.getElementById("T"+AJ+"R"+AI+"C"+AH).className="tdwSelectedCell"
}}}else{var AF=v(C-D);
var AK=v(j-i);
if(AK==0){AK=1
}var AE=((D+AC)+AF*v(D-AC)*(D-AC))/2+AF;
for(var AI=AE;
(C-AI)*AF>=0;
AI+=AF){for(var AH=i;
(j-AH)*AK>=0;
AH+=AK){document.getElementById("T"+AJ+"R"+AI+"C"+AH).className="tdwUnselectedCell"
}}for(var AI=D-AF;
(AC-AI)*AF<=0;
AI-=AF){for(var AH=i;
(j-AH)*AK>=0;
AH+=AK){document.getElementById("T"+AJ+"R"+AI+"C"+AH).className="tdwSelectedCell"
}}}}if(AL==0){AL=1
}if(j==AL){break
}else{if(i==j||v(j-i)==v(AL-j)){var AF=v(AL-j);
var AG=v(AC-D);
if(AG==0){AG=1
}for(var AI=j+AF;
(AL-AI)*AF>=0;
AI+=AF){for(var AH=D;
(AC-AH)*AG>=0;
AH+=AG){document.getElementById("T"+AJ+"R"+AH+"C"+AI).className="tdwSelectedCell"
}}}else{var AF=v(j-i);
var AG=v(AC-D);
if(AG==0){AG=1
}var AE=((i+AL)+AF*v(i-AL)*(i-AL))/2+AF;
for(var AI=AE;
(j-AI)*AF>=0;
AI+=AF){for(var AH=D;
(AC-AH)*AG>=0;
AH+=AG){document.getElementById("T"+AJ+"R"+AH+"C"+AI).className="tdwUnselectedCell"
}}for(var AI=i-AF;
(AL-AI)*AF<=0;
AI-=AF){for(var AH=D;
(AC-AH)*AG>=0;
AH+=AG){document.getElementById("T"+AJ+"R"+AH+"C"+AI).className="tdwSelectedCell"
}}}}break
}C=AC;
j=AL
};
var b=function(AC){L(AC.target.id);
AC.preventDefault();
AC.stopPropagation();
return false
};
var B=function(){var AC=window.event;
L(AC.srcElement.id);
AC.returnValue=false;
AC.cancelBubble=true;
return false
};
var A=function(AC){AC.preventDefault();
AC.stopPropagation();
return false
};
var X=function(){var AC=window.event;
AC.returnValue=false;
AC.cancelBubble=true;
return false
};
var s=function(AC){AB="none";
if(D>C){var AD=D;
D=C;
C=AD
}if(i>j){var AD=i;
i=j;
j=AD
}p=true;
enableNext();
enableFinish()
};
var AA=function(AC){if(AC.button!=0||AB=="none"){return true
}s();
AC.preventDefault();
AC.stopPropagation();
return false
};
var P=function(){var AC=window.event;
if(AC.button!=1||AB=="none"){return true
}s();
AC.returnValue=false;
AC.cancelBubble=true;
return false
};
var c=function(){var AC=window.event;
AC.returnValue=false;
AC.cancelBubble=true;
return false
};
var Z=function(){var AC=window.event;
AC.returnValue=false;
AC.cancelBubble=true;
return false
};
var F=function(){while(n.hasChildNodes()){n.removeChild(n.firstChild)
}};
var K=function(AH){for(var AF=0;
AF<AH.childNodes.length;
AF++){var AG=AH.childNodes.item(AF);
if(AG.nodeType!=1){continue
}for(k=AG.childNodes.length-1;
k>=0;
k--){if(AG.childNodes.item(k).nodeType==1){break
}}var AE=AG.childNodes.item(k);
for(var AJ=0;
AJ<AE.childNodes.length;
AJ++){var AI=AE.childNodes.item(AJ);
if(AI.nodeType!=1){continue
}for(var AD=0;
AD<AI.childNodes.length;
AD++){var AC=AI.childNodes.item(AD);
if(AC.nodeType!=1){continue
}AC.className="tdwUnselectedTableCell";
if(AC.addEventListener){AC.addEventListener("mousedown",y,true);
AC.addEventListener("mousemove",A,true);
AC.addEventListener("mouseover",b,true);
AC.addEventListener("mouseup",AA,true)
}else{AC.attachEvent("onmousedown",H);
AC.attachEvent("onmousemove",X);
AC.attachEvent("onmouseover",B);
AC.attachEvent("onmouseup",P);
AC.attachEvent("onselectstart",c);
AC.attachEvent("onselect",Z)
}}}}};
var t=function(AC){while(AC.hasChildNodes()){n.appendChild(AC.firstChild)
}};
var E;
var z=function(){var AC="classname=XWiki.TableDataSource";
AC+="&XWiki.TableDataSource_datasource_name="+encodeURI(document.getElementById("tdwSaveNameInput").value);
AC+="&XWiki.TableDataSource_table_number="+a;
AC+="&XWiki.TableDataSource_range="+J();
AC+="&XWiki.TableDataSource_has_header_row="+(document.getElementById("tdwRowHeaderInput").checked?"1":"0");
AC+="&XWiki.TableDataSource_has_header_column="+(document.getElementById("tdwColumnHeaderInput").checked?"1":"0");
AC+="&XWiki.TableDataSource_ignore_alpha="+(document.getElementById("tdwIgnoreAlphaInput").checked?"1":"0");
AC+="&XWiki.TableDataSource_decimal_symbol="+(document.getElementById("tdwDecimalSymbolInput").checked?"comma":"period");
if(window.XMLHttpRequest){E=new XMLHttpRequest()
}else{E=new ActiveXObject("Microsoft.XMLHTTP")
}E.open("POST",T+M.replace(".","/"),false);
E.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
try{E.send(AC)
}catch(AD){alert("Object could not be saved due to a connection problem.\n\n"+AD);
return false
}return true
};
this.getTablesFromPage=function(AC){d.className="tdwHidden";
G.className="tdwHidden";
U.className="tdwHidden";
n.className="tdwHidden";
Y.className="tdwMessage";
p=false;
M=AC.replace("/",".");
q="none";
AB="none";
disablePage("Extra");
disableNext();
disableFinish();
if(window.XMLHttpRequest){E=new XMLHttpRequest()
}else{E=new ActiveXObject("Microsoft.XMLHTTP")
}AC=AC.replace(".","/");
E.open("GET",m+AC,true);
E.onreadystatechange=this.handleRequest;
E.send(null)
};
this.handleRequest=function(){if(E.readyState==4){Y.className="tdwHidden";
try{if(E.status!==200){d.className="tdwErrorMessage";
return 
}}catch(AD){d.className="tdwErrorMessage";
return 
}if(E.responseXML){try{if(E.responseXML.getElementsByTagName("div").item(0).getAttribute("id")!="tdwEnvelope"){document.getElementById("tdwNoTablePageName").firstChild.nodeValue=M;
G.className="tdwErrorMessage";
return 
}else{var AC=E.responseXML.getElementsByTagName("div").item(0);
O=E.responseXML.getElementsByTagName("div").item(1).firstChild.nodeValue;
if(AC.getElementsByTagName("table").length==0){document.getElementById("tdwNoTablePageName").firstChild.nodeValue=M;
G.className="tdwErrorMessage";
return 
}F();
try{t(AC);
K(n)
}catch(AD){n.innerHTML=E.responseText.substring(E.responseText.indexOf("<table"),E.responseText.indexOf("</div>"));
K(n)
}U.className="tdwMessage";
n.className="tdwTables";
return 
}}catch(AD){document.getElementById("tdwNoTablePageName").firstChild.nodeValue=M;
G.className="tdwErrorMessage"
}}else{d.className="tdwErrorMessage";
return 
}}}
}window.wizard=new tdwWizard();