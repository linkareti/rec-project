function init(){tinyMCEPopup.resizeToInnerSize();
document.getElementById("backgroundimagebrowsercontainer").innerHTML=getBrowserHTML("backgroundimagebrowser","backgroundimage","image","table");
document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
var E=tinyMCE.selectedInstance;
var J=tinyMCE.getParentElement(E.getFocusElement(),"tr");
var F=document.forms[0];
var L=tinyMCE.parseStyle(tinyMCE.getAttrib(J,"style"));
var C=J.parentNode.nodeName.toLowerCase();
var G=tinyMCE.getAttrib(J,"align");
var I=tinyMCE.getAttrib(J,"valign");
var K=trimSize(getStyle(J,"height","height"));
var H=tinyMCE.getVisualAidClass(tinyMCE.getAttrib(J,"class"),false);
var N=convertRGBToHex(getStyle(J,"bgcolor","backgroundColor"));
var M=getStyle(J,"background","backgroundImage").replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1");
var A=tinyMCE.getAttrib(J,"id");
var B=tinyMCE.getAttrib(J,"lang");
var D=tinyMCE.getAttrib(J,"dir");
addClassesToList("class","table_row_styles");
F.bgcolor.value=N;
F.backgroundimage.value=M;
F.height.value=K;
F.id.value=A;
F.lang.value=B;
F.style.value=tinyMCE.serializeStyle(L);
selectByValue(F,"align",G);
selectByValue(F,"valign",I);
selectByValue(F,"class",H);
selectByValue(F,"rowtype",C);
selectByValue(F,"dir",D);
if(isVisible("backgroundimagebrowser")){document.getElementById("backgroundimage").style.width="180px"
}updateColor("bgcolor_pick","bgcolor")
}function updateAction(){tinyMCEPopup.restoreSelection();
var F=tinyMCE.selectedInstance;
var G=tinyMCE.getParentElement(F.getFocusElement(),"tr");
var B=tinyMCE.getParentElement(F.getFocusElement(),"table");
var A=document.forms[0];
var E=getSelectValue(A,"action");
F.execCommand("mceBeginUndoLevel");
switch(E){case"row":updateRow(G);
break;
case"all":var D=B.getElementsByTagName("tr");
for(var C=0;
C<D.length;
C++){updateRow(D[C],true)
}break;
case"odd":case"even":var D=B.getElementsByTagName("tr");
for(var C=0;
C<D.length;
C++){if((C%2==0&&E=="odd")||(C%2!=0&&E=="even")){updateRow(D[C],true,true)
}}break
}tinyMCE.handleVisualAid(F.getBody(),true,F.visualAid,F);
tinyMCE.triggerNodeChange();
F.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function updateRow(G,H,I){var E=tinyMCE.selectedInstance;
var F=document.forms[0];
var A=G.parentNode.nodeName.toLowerCase();
var B=getSelectValue(F,"rowtype");
var K=E.getDoc();
if(!H){G.setAttribute("id",F.id.value)
}G.setAttribute("align",getSelectValue(F,"align"));
G.setAttribute("vAlign",getSelectValue(F,"valign"));
G.setAttribute("lang",F.lang.value);
G.setAttribute("dir",getSelectValue(F,"dir"));
G.setAttribute("style",tinyMCE.serializeStyle(tinyMCE.parseStyle(F.style.value)));
tinyMCE.setAttrib(G,"class",getSelectValue(F,"class"));
G.setAttribute("background","");
G.setAttribute("bgColor","");
G.setAttribute("height","");
G.style.height=getCSSSize(F.height.value);
G.style.backgroundColor=F.bgcolor.value;
if(F.backgroundimage.value!=""){G.style.backgroundImage="url('"+F.backgroundimage.value+"')"
}else{G.style.backgroundImage=""
}if(A!=B&&!I){var M=G.cloneNode(1);
var C=tinyMCE.getParentElement(G,"table");
var J=B;
var L=null;
for(var D=0;
D<C.childNodes.length;
D++){if(C.childNodes[D].nodeName.toLowerCase()==J){L=C.childNodes[D]
}}if(L==null){L=K.createElement(J);
if(J=="thead"){C.insertBefore(L,C.firstChild)
}else{C.appendChild(L)
}}L.appendChild(M);
G.parentNode.removeChild(G);
G=M
}}function changedBackgroundImage(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
B["background-image"]="url('"+A.backgroundimage.value+"')";
A.style.value=tinyMCE.serializeStyle(B)
}function changedStyle(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
if(B["background-image"]){A.backgroundimage.value=B["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}else{A.backgroundimage.value=""
}if(B.height){A.height.value=trimSize(B.height)
}if(B["background-color"]){A.bgcolor.value=B["background-color"];
updateColor("bgcolor_pick","bgcolor")
}}function changedSize(){var B=document.forms[0];
var C=tinyMCE.parseStyle(B.style.value);
var A=B.height.value;
if(A!=""){C.height=getCSSSize(A)
}else{C.height=""
}B.style.value=tinyMCE.serializeStyle(C)
}function changedColor(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
B["background-color"]=A.bgcolor.value;
A.style.value=tinyMCE.serializeStyle(B)
};