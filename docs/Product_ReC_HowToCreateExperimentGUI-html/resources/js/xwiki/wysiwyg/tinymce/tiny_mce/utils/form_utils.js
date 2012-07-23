function getColorPickerHTML(C,B){var A="";
A+='<a id="'+C+'_link" href="javascript:void(0);" onkeydown="pickColor(event,\''+B+"');\" onmousedown=\"pickColor(event,'"+B+"');return false;\">";
A+='<img id="'+C+'" src="../../themes/'+tinyMCE.getParam("theme")+'/images/color.gif"';
A+=" onmouseover=\"this.className='mceButtonOver'\"";
A+=" onmouseout=\"this.className='mceButtonNormal'\"";
A+=" onmousedown=\"this.className='mceButtonDown'\"";
A+=' width="20" height="16" border="0" title="'+tinyMCE.getLang("lang_browse")+'"';
A+=' class="mceButtonNormal" alt="'+tinyMCE.getLang("lang_browse")+'" /></a>';
return A
}function pickColor(B,A){if((B.keyCode==32||B.keyCode==13)||B.type=="mousedown"){tinyMCEPopup.pickColor(B,A)
}}function updateColor(B,A){document.getElementById(B).style.backgroundColor=document.forms[0].elements[A].value
}function setBrowserDisabled(D,B){var A=document.getElementById(D);
var C=document.getElementById(D+"_link");
if(C){if(B){C.setAttribute("realhref",C.getAttribute("href"));
C.removeAttribute("href");
tinyMCE.switchClass(A,"mceButtonDisabled",true)
}else{C.setAttribute("href",C.getAttribute("realhref"));
tinyMCE.switchClass(A,"mceButtonNormal",false)
}}}function getBrowserHTML(G,E,D,F){var C=F+"_"+D+"_browser_callback";
var A=tinyMCE.getParam(C,tinyMCE.getParam("file_browser_callback"));
if(A==null){return""
}var B="";
B+='<a id="'+G+'_link" href="javascript:openBrower(\''+G+"','"+E+"', '"+D+"','"+C+'\');" onmousedown="return false;">';
B+='<img id="'+G+'" src="../../themes/'+tinyMCE.getParam("theme")+'/images/browse.gif"';
B+=" onmouseover=\"this.className='mceButtonOver';\"";
B+=" onmouseout=\"this.className='mceButtonNormal';\"";
B+=" onmousedown=\"this.className='mceButtonDown';\"";
B+=' width="20" height="18" border="0" title="'+tinyMCE.getLang("lang_browse")+'"';
B+=' class="mceButtonNormal" alt="'+tinyMCE.getLang("lang_browse")+'" /></a>';
return B
}function openBrower(B,E,D,C){var A=document.getElementById(B);
if(A.className!="mceButtonDisabled"){tinyMCEPopup.openBrowser(E,D,C)
}}function selectByValue(A,C,G,F,H){if(!A||!A.elements[C]){return 
}var B=A.elements[C];
var I=false;
for(var D=0;
D<B.options.length;
D++){var E=B.options[D];
if(E.value==G||(H&&E.value.toLowerCase()==G.toLowerCase())){E.selected=true;
I=true
}else{E.selected=false
}}if(!I&&F&&G!=""){var E=new Option(G,G);
E.selected=true;
B.options[B.options.length]=E;
B.selectedIndex=B.options.length-1
}return I
}function getSelectValue(A,B){var C=A.elements[B];
if(C==null||C.options==null){return""
}return C.options[C.selectedIndex].value
}function addSelectValue(A,D,B,E){var C=A.elements[D];
var F=new Option(B,E);
C.options[C.options.length]=F
}function addClassesToList(C,B){var E=document.getElementById(C);
var H=tinyMCE.getParam("theme_advanced_styles",false);
H=tinyMCE.getParam(B,H);
if(H){var I=H.split(";");
for(var D=0;
D<I.length;
D++){if(I!=""){var G,F;
G=I[D].split("=")[0];
F=I[D].split("=")[1];
E.options[E.length]=new Option(G,F)
}}}else{var A=tinyMCE.getCSSClasses(tinyMCE.getWindowArg("editor_id"));
for(var D=0;
D<A.length;
D++){E.options[E.length]=new Option(A[D],A[D])
}}}function isVisible(A){var B=document.getElementById(A);
return B&&B.style.display!="none"
}function convertRGBToHex(B){var C=new RegExp("rgb\\s*\\(\\s*([0-9]+).*,\\s*([0-9]+).*,\\s*([0-9]+).*\\)","gi");
var A=B.replace(C,"$1,$2,$3").split(",");
if(A.length==3){r=parseInt(A[0]).toString(16);
g=parseInt(A[1]).toString(16);
b=parseInt(A[2]).toString(16);
r=r.length==1?"0"+r:r;
g=g.length==1?"0"+g:g;
b=b.length==1?"0"+b:b;
return"#"+r+g+b
}return B
}function convertHexToRGB(A){if(A.indexOf("#")!=-1){A=A.replace(new RegExp("[^0-9A-F]","gi"),"");
r=parseInt(A.substring(0,2),16);
g=parseInt(A.substring(2,4),16);
b=parseInt(A.substring(4,6),16);
return"rgb("+r+","+g+","+b+")"
}return A
}function trimSize(A){return A.replace(new RegExp("[^0-9%]","gi"),"")
}function getCSSSize(A){A=trimSize(A);
if(A==""){return""
}return A.indexOf("%")!=-1?A:A+"px"
}function getStyle(elm,attrib,style){var val=tinyMCE.getAttrib(elm,attrib);
if(val!=""){return""+val
}if(typeof (style)=="undefined"){style=attrib
}val=eval("elm.style."+style);
return val==null?"":""+val
};