var action,orgTableWidth,orgTableHeight;
function insertTable(){var G=document.forms[0];
var D=tinyMCE.selectedInstance;
var J=2,I=2,N=0,P=-1,A=-1,O,M,L,B;
var K="";
var E=tinyMCE.tableElm;
var Q,R,C;
tinyMCEPopup.restoreSelection();
J=G.elements.cols.value;
I=G.elements.rows.value;
N=G.elements.border.value!=""?G.elements.border.value:0;
P=G.elements.cellpadding.value!=""?G.elements.cellpadding.value:"";
A=G.elements.cellspacing.value!=""?G.elements.cellspacing.value:"";
O=G.elements.align.options[G.elements.align.selectedIndex].value;
M=G.elements.width.value;
L=G.elements.height.value;
bordercolor=G.elements.bordercolor.value;
bgcolor=G.elements.bgcolor.value;
B="wiki-table";
id=G.elements.id.value;
summary=G.elements.summary.value;
style=G.elements.style.value;
dir=G.elements.dir.value;
lang=G.elements.lang.value;
background=G.elements.backgroundimage.value;
Q=tinyMCE.getParam("table_cell_limit",false);
R=tinyMCE.getParam("table_row_limit",false);
C=tinyMCE.getParam("table_col_limit",false);
if(C&&J>C){alert(tinyMCE.getLang("lang_table_col_limit","",true,{cols:C}));
return false
}else{if(R&&I>R){alert(tinyMCE.getLang("lang_table_row_limit","",true,{rows:R}));
return false
}else{if(Q&&J*I>Q){alert(tinyMCE.getLang("lang_table_cell_limit","",true,{cells:Q}));
return false
}}}if(action=="update"){D.execCommand("mceBeginUndoLevel");
tinyMCE.setAttrib(E,"cellPadding",P,true);
tinyMCE.setAttrib(E,"cellSpacing",A,true);
tinyMCE.setAttrib(E,"border",N,true);
tinyMCE.setAttrib(E,"align",O);
tinyMCE.setAttrib(E,"class",B);
tinyMCE.setAttrib(E,"style",style);
tinyMCE.setAttrib(E,"id",id);
tinyMCE.setAttrib(E,"summary",summary);
tinyMCE.setAttrib(E,"dir",dir);
tinyMCE.setAttrib(E,"lang",lang);
if(!tinyMCE.getParam("inline_styles")){tinyMCE.setAttrib(E,"width",M,true)
}tinyMCE.setAttrib(E,"borderColor","");
tinyMCE.setAttrib(E,"bgColor","");
tinyMCE.setAttrib(E,"background","");
tinyMCE.setAttrib(E,"height","");
if(background!=""){E.style.backgroundImage="url('"+background+"')"
}else{E.style.backgroundImage=""
}if(tinyMCE.getParam("inline_styles")){E.style.borderWidth=N+"px"
}if(tinyMCE.getParam("inline_styles")){if(M!=""){E.style.width=getCSSSize(M)
}}if(bordercolor!=""){E.style.borderColor=bordercolor;
E.style.borderStyle=E.style.borderStyle==""?"solid":E.style.borderStyle;
E.style.borderWidth=N==""?"1px":N
}else{E.style.borderColor=""
}E.style.backgroundColor=bgcolor;
E.style.height=getCSSSize(L);
tinyMCE.handleVisualAid(tinyMCE.tableElm,false,D.visualAid,D);
tinyMCE.tableElm.outerHTML=tinyMCE.tableElm.outerHTML;
tinyMCE.handleVisualAid(D.getBody(),true,D.visualAid,D);
tinyMCE.triggerNodeChange();
D.execCommand("mceEndUndoLevel");
if(G.width.value!=orgTableWidth||G.height.value!=orgTableHeight){D.repaint()
}tinyMCEPopup.close();
return true
}K+='<table class="wiki-table" cellpadding="0" cellspacing="0" align="center">';
for(var F=0;
F<I;
F++){if(F==0){K+='<tr class="table-head">';
for(var H=0;
H<J;
H++){K+="<td>&nbsp;</td>"
}K+="</tr>"
}else{if((F%2)==1){K+='<tr class="table-odd">'
}else{K+='<tr class="table-even">'
}for(var H=0;
H<J;
H++){K+="<td>&nbsp;</td>"
}K+="</tr>"
}}K+="</table>";
D.execCommand("mceBeginUndoLevel");
D.execCommand("mceInsertContent",false,K);
tinyMCE.handleVisualAid(D.getBody(),true,tinyMCE.settings.visual);
D.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function makeAttrib(D,C){var B=document.forms[0];
var A=B.elements[D];
if(typeof (C)=="undefined"||C==null){C="";
if(A){C=A.value
}}if(C==""){return""
}C=C.replace(/&/g,"&amp;");
C=C.replace(/\"/g,"&quot;");
C=C.replace(/</g,"&lt;");
C=C.replace(/>/g,"&gt;");
return" "+D+'="'+C+'"'
}function init(){tinyMCEPopup.resizeToInnerSize();
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("bordercolor_pickcontainer").innerHTML=getColorPickerHTML("bordercolor_pick","bordercolor");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var I=2,H=2,O=0,U="",B="";
var P="",N="",K="",R="",A="",C="wiki-table";
var L="",F="",S="",M="",V="",T="",A="",R="";
var D=tinyMCE.selectedInstance;
var G=document.forms[0];
var E=tinyMCE.getParentElement(D.getFocusElement(),"table");
tinyMCE.tableElm=E;
action=tinyMCE.getWindowArg("action");
if(action==null){action=tinyMCE.tableElm?"update":"insert"
}if(tinyMCE.tableElm&&action!="insert"){var J=tinyMCE.tableElm.rows;
var I=0;
for(var Q=0;
Q<J.length;
Q++){if(J[Q].cells.length>I){I=J[Q].cells.length
}}I=I;
H=J.length;
st=tinyMCE.parseStyle(tinyMCE.getAttrib(tinyMCE.tableElm,"style"));
O=trimSize(getStyle(E,"border","borderWidth"));
U=tinyMCE.getAttrib(tinyMCE.tableElm,"cellpadding","");
B=tinyMCE.getAttrib(tinyMCE.tableElm,"cellspacing","");
N=trimSize(getStyle(E,"width","width"));
K=trimSize(getStyle(E,"height","height"));
R=convertRGBToHex(getStyle(E,"bordercolor","borderLeftColor"));
A=convertRGBToHex(getStyle(E,"bgcolor","backgroundColor"));
P=tinyMCE.getAttrib(tinyMCE.tableElm,"align",P);
L=tinyMCE.getAttrib(tinyMCE.tableElm,"id");
F=tinyMCE.getAttrib(tinyMCE.tableElm,"summary");
S=tinyMCE.serializeStyle(st);
M=tinyMCE.getAttrib(tinyMCE.tableElm,"dir");
V=tinyMCE.getAttrib(tinyMCE.tableElm,"lang");
T=getStyle(E,"background","backgroundImage").replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
orgTableWidth=N;
orgTableHeight=K;
action="update"
}addClassesToList("class","table_styles");
selectByValue(G,"align",P);
selectByValue(G,"class",C);
G.cols.value=I;
G.rows.value=H;
G.border.value=O;
G.cellpadding.value=U;
G.cellspacing.value=B;
G.width.value=N;
G.height.value=K;
G.bordercolor.value=R;
G.bgcolor.value=A;
G.id.value=L;
G.summary.value=F;
G.style.value=S;
G.dir.value=M;
G.lang.value=V;
G.backgroundimage.value=T;
G.insert.value=tinyMCE.getLang("lang_"+action,"Insert",true);
updateColor("bordercolor_pick","bordercolor");
updateColor("bgcolor_pick","bgcolor");
if(isVisible("backgroundimagebrowser")){document.getElementById("backgroundimage").style.width="180px"
}if(action=="update"){G.cols.disabled=true;
G.rows.disabled=true
}}function changedSize(){var B=document.forms[0];
var C=tinyMCE.parseStyle(B.style.value);
var D=B.width.value;
if(D!=""){C.width=tinyMCE.getParam("inline_styles")?getCSSSize(D):""
}else{C.width=""
}var A=B.height.value;
if(A!=""){C.height=getCSSSize(A)
}else{C.height=""
}B.style.value=tinyMCE.serializeStyle(C)
}function changedBackgroundImage(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
B["background-image"]="url('"+A.backgroundimage.value+"')";
A.style.value=tinyMCE.serializeStyle(B)
}function changedBorder(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
if(A.border.value!=""&&A.bordercolor.value!=""){B["border-width"]=A.border.value+"px"
}A.style.value=tinyMCE.serializeStyle(B)
}function changedColor(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
B["background-color"]=A.bgcolor.value;
if(A.bordercolor.value!=""){B["border-color"]=A.bordercolor.value;
if(!B["border-width"]){B["border-width"]=A.border.value==""?"1px":A.border.value+"px"
}}A.style.value=tinyMCE.serializeStyle(B)
}function changedStyle(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
if(B["background-image"]){A.backgroundimage.value=B["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}else{A.backgroundimage.value=""
}if(B.width){A.width.value=trimSize(B.width)
}if(B.height){A.height.value=trimSize(B.height)
}if(B["background-color"]){A.bgcolor.value=B["background-color"];
updateColor("bgcolor_pick","bgcolor")
}if(B["border-color"]){A.bordercolor.value=B["border-color"];
updateColor("bordercolor_pick","bordercolor")
}};