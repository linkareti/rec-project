function init(){tinyMCEPopup.resizeToInnerSize();
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("bordercolor_pickcontainer").innerHTML=getColorPickerHTML("bordercolor_pick","bordercolor");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var G=tinyMCE.selectedInstance;
var I=tinyMCE.getParentElement(G.getFocusElement(),"td,th");
var H=document.forms[0];
var O=tinyMCE.parseStyle(tinyMCE.getAttrib(I,"style"));
var E=I.nodeName.toLowerCase();
var J=tinyMCE.getAttrib(I,"align");
var L=tinyMCE.getAttrib(I,"valign");
var B=trimSize(getStyle(I,"width","width"));
var M=trimSize(getStyle(I,"height","height"));
var F=convertRGBToHex(getStyle(I,"bordercolor","borderLeftColor"));
var Q=convertRGBToHex(getStyle(I,"bgcolor","backgroundColor"));
var K=tinyMCE.getVisualAidClass(tinyMCE.getAttrib(I,"class"),false);
var P=getStyle(I,"background","backgroundImage").replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
var A=tinyMCE.getAttrib(I,"id");
var C=tinyMCE.getAttrib(I,"lang");
var D=tinyMCE.getAttrib(I,"dir");
var N=tinyMCE.getAttrib(I,"scope");
addClassesToList("class","table_cell_styles");
H.bordercolor.value=F;
H.bgcolor.value=Q;
H.backgroundimage.value=P;
H.width.value=B;
H.height.value=M;
H.id.value=A;
H.lang.value=C;
H.style.value=tinyMCE.serializeStyle(O);
selectByValue(H,"align",J);
selectByValue(H,"valign",L);
selectByValue(H,"class",K);
selectByValue(H,"celltype",E);
selectByValue(H,"dir",D);
selectByValue(H,"scope",N);
if(isVisible("backgroundimagebrowser")){document.getElementById("backgroundimage").style.width="180px"
}updateColor("bordercolor_pick","bordercolor");
updateColor("bgcolor_pick","bgcolor")
}function updateAction(){tinyMCEPopup.restoreSelection();
var C=tinyMCE.selectedInstance;
var E=tinyMCE.getParentElement(C.getFocusElement(),"td,th");
var H=tinyMCE.getParentElement(C.getFocusElement(),"tr");
var F=tinyMCE.getParentElement(C.getFocusElement(),"table");
var D=document.forms[0];
C.execCommand("mceBeginUndoLevel");
switch(getSelectValue(D,"action")){case"cell":var B=getSelectValue(D,"celltype");
var I=getSelectValue(D,"scope");
if(tinyMCE.getParam("accessibility_warnings")){if(B=="th"&&I==""){var K=confirm(tinyMCE.getLang("lang_table_missing_scope","",true))
}else{var K=true
}if(!K){return 
}}updateCell(E);
break;
case"row":var G=H.firstChild;
if(G.nodeName!="TD"&&G.nodeName!="TH"){G=nextCell(G)
}do{G=updateCell(G,true)
}while((G=nextCell(G))!=null);
break;
case"all":var J=F.getElementsByTagName("tr");
for(var A=0;
A<J.length;
A++){var G=J[A].firstChild;
if(G.nodeName!="TD"&&G.nodeName!="TH"){G=nextCell(G)
}do{G=updateCell(G,true)
}while((G=nextCell(G))!=null)
}break
}tinyMCE.handleVisualAid(C.getBody(),true,C.visualAid,C);
tinyMCE.triggerNodeChange();
C.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function nextCell(A){while((A=A.nextSibling)!=null){if(A.nodeName=="TD"||A.nodeName=="TH"){return A
}}return null
}function updateCell(B,G){var E=tinyMCE.selectedInstance;
var F=document.forms[0];
var A=B.nodeName.toLowerCase();
var D=getSelectValue(F,"celltype");
var K=E.getDoc();
if(!G){B.setAttribute("id",F.id.value)
}B.setAttribute("align",F.align.value);
B.setAttribute("vAlign",F.valign.value);
B.setAttribute("lang",F.lang.value);
B.setAttribute("dir",getSelectValue(F,"dir"));
B.setAttribute("style",tinyMCE.serializeStyle(tinyMCE.parseStyle(F.style.value)));
B.setAttribute("scope",F.scope.value);
tinyMCE.setAttrib(B,"class",getSelectValue(F,"class"));
tinyMCE.setAttrib(B,"width","");
tinyMCE.setAttrib(B,"height","");
tinyMCE.setAttrib(B,"bgColor","");
tinyMCE.setAttrib(B,"borderColor","");
tinyMCE.setAttrib(B,"background","");
B.style.width=getCSSSize(F.width.value);
B.style.height=getCSSSize(F.height.value);
if(F.bordercolor.value!=""){B.style.borderColor=F.bordercolor.value;
B.style.borderStyle=B.style.borderStyle==""?"solid":B.style.borderStyle;
B.style.borderWidth=B.style.borderWidth==""?"1px":B.style.borderWidth
}else{B.style.borderColor=""
}B.style.backgroundColor=F.bgcolor.value;
if(F.backgroundimage.value!=""){B.style.backgroundImage="url('"+F.backgroundimage.value+"')"
}else{B.style.backgroundImage=""
}if(A!=D){var C=K.createElement(D);
for(var I=0;
I<B.childNodes.length;
I++){C.appendChild(B.childNodes[I].cloneNode(1))
}for(var J=0;
J<B.attributes.length;
J++){var H=B.attributes[J];
C.setAttribute(H.name,H.value)
}B.parentNode.replaceChild(C,B);
B=C
}return B
}function changedBackgroundImage(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
B["background-image"]="url('"+A.backgroundimage.value+"')";
A.style.value=tinyMCE.serializeStyle(B)
}function changedSize(){var B=document.forms[0];
var C=tinyMCE.parseStyle(B.style.value);
var D=B.width.value;
if(D!=""){C.width=getCSSSize(D)
}else{C.width=""
}var A=B.height.value;
if(A!=""){C.height=getCSSSize(A)
}else{C.height=""
}B.style.value=tinyMCE.serializeStyle(C)
}function changedColor(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
B["background-color"]=A.bgcolor.value;
B["border-color"]=A.bordercolor.value;
A.style.value=tinyMCE.serializeStyle(B)
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