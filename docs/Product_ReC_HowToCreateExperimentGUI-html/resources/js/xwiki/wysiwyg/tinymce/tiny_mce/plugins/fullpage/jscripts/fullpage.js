var defaultDocTypes='XHTML 1.0 Transitional=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">,XHTML 1.0 Frameset=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">,XHTML 1.0 Strict=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">,XHTML 1.1=<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">">,HTML 4.01 Transitional=<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">,HTML 4.01 Strict=<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">,HTML 4.01 Frameset=<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">';
var defaultEncodings="Western european (iso-8859-1)=iso-8859-1,Central European (iso-8859-2)=iso-8859-2,Unicode (UTF-8)=utf-8,Chinese traditional (Big5)=big5,Cyrillic (iso-8859-5)=iso-8859-5,Japanese (iso-2022-jp)=iso-2022-jp,Greek (iso-8859-7)=iso-8859-7,Korean (iso-2022-kr)=iso-2022-kr,ASCII (us-ascii)=us-ascii";
var defaultMediaTypes="all=all,screen=screen,print=print,tty=tty,tv=tv,projection=projection,handheld=handheld,braille=braille,aural=aural";
var defaultFontNames="Arial=arial,helvetica,sans-serif;Courier New=courier new,courier,monospace;Georgia=georgia,times new roman,times,serif;Tahoma=tahoma,arial,helvetica,sans-serif;Times New Roman=times new roman,times,serif;Verdana=verdana,arial,helvetica,sans-serif;Impact=impact;WingDings=wingdings";
var defaultFontSizes="10px,11px,12px,13px,14px,15px,16px";
var addMenuLayer=new MCLayer("addmenu");
var lastElementType=null;
var topDoc;
function init(){var G=document.forms.fullpage;
var D,A,B,H,F,I;
var E=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
B=tinyMCE.getParam("fullpage_doctypes",defaultDocTypes).split(",");
for(D=0;
D<B.length;
D++){A=B[D].split("=");
if(A.length>1){addSelectValue(G,"doctypes",A[0],A[1])
}}I=tinyMCE.getParam("fullpage_fonts",defaultFontNames).split(";");
for(D=0;
D<I.length;
D++){A=I[D].split("=");
if(A.length>1){addSelectValue(G,"fontface",A[0],A[1])
}}I=tinyMCE.getParam("fullpage_fontsizes",defaultFontSizes).split(",");
for(D=0;
D<I.length;
D++){addSelectValue(G,"fontsize",I[D],I[D])
}F=tinyMCE.getParam("fullpage_media_types",defaultMediaTypes).split(",");
for(D=0;
D<F.length;
D++){A=F[D].split("=");
if(A.length>1){addSelectValue(G,"element_style_media",A[0],A[1]);
addSelectValue(G,"element_link_media",A[0],A[1])
}}H=tinyMCE.getParam("fullpage_encodings",defaultEncodings).split(",");
for(D=0;
D<H.length;
D++){A=H[D].split("=");
if(A.length>1){addSelectValue(G,"docencoding",A[0],A[1]);
addSelectValue(G,"element_script_charset",A[0],A[1]);
addSelectValue(G,"element_link_charset",A[0],A[1])
}}document.getElementById("bgcolor_pickcontainer").innerHTML=getColorPickerHTML("bgcolor_pick","bgcolor");
document.getElementById("link_color_pickcontainer").innerHTML=getColorPickerHTML("link_color_pick","link_color");
document.getElementById("visited_color_pickcontainer").innerHTML=getColorPickerHTML("visited_color_pick","visited_color");
document.getElementById("active_color_pickcontainer").innerHTML=getColorPickerHTML("active_color_pick","active_color");
document.getElementById("textcolor_pickcontainer").innerHTML=getColorPickerHTML("textcolor_pick","textcolor");
document.getElementById("stylesheet_browsercontainer").innerHTML=getBrowserHTML("stylesheetbrowser","stylesheet","file","fullpage");
document.getElementById("link_href_pickcontainer").innerHTML=getBrowserHTML("link_href_browser","element_link_href","file","fullpage");
document.getElementById("script_src_pickcontainer").innerHTML=getBrowserHTML("script_src_browser","element_script_src","file","fullpage");
document.getElementById("bgimage_pickcontainer").innerHTML=getBrowserHTML("bgimage_browser","bgimage","image","fullpage");
if(isVisible("stylesheetbrowser")){document.getElementById("stylesheet").style.width="220px"
}if(isVisible("link_href_browser")){document.getElementById("element_link_href").style.width="230px"
}if(isVisible("bgimage_browser")){document.getElementById("bgimage").style.width="210px"
}var C=document.createElement("iframe");
C.id="tempFrame";
C.style.display="none";
C.src=tinyMCE.baseURL+"/plugins/fullpage/blank.htm";
document.body.appendChild(C);
tinyMCEPopup.resizeToInnerSize()
}function setupIframe(O){var G=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var H=G.fullpageTopContent;
var I=document.forms[0];
var L,J,K;
var C,F,M,B,N,E,D;
H=H.replace(/<script>/gi,'<script type="text/javascript">');
H=H.replace(/\ssrc=/gi," mce_src=");
H=H.replace(/\shref=/gi," mce_href=");
H=H.replace(/\stype=/gi," mce_type=");
H=H.replace(/<script/gi,'<script type="text/unknown" ');
H+="</body></html>";
topDoc=O;
O.open();
O.write(H);
O.close();
L=getReItem(/<\?\s*?xml.*?version\s*?=\s*?"(.*?)".*?\?>/gi,H,1);
J=getReItem(/<\?\s*?xml.*?encoding\s*?=\s*?"(.*?)".*?\?>/gi,H,1);
K=getReItem(/<\!DOCTYPE.*?>/gi,H,0);
I.langcode.value=getReItem(/lang="(.*?)"/gi,H,1);
I.metatitle.value=tinyMCE.entityDecode(getReItem(/<title>(.*?)<\/title>/gi,H,1));
C=O.getElementsByTagName("meta");
for(F=0;
F<C.length;
F++){B=tinyMCE.getAttrib(C[F],"name");
N=tinyMCE.getAttrib(C[F],"content");
httpEquiv=tinyMCE.getAttrib(C[F],"httpEquiv");
switch(B.toLowerCase()){case"keywords":I.metakeywords.value=N;
break;
case"description":I.metadescription.value=N;
break;
case"author":I.metaauthor.value=N;
break;
case"copyright":I.metacopyright.value=N;
break;
case"robots":selectByValue(I,"metarobots",N,true,true);
break
}switch(httpEquiv.toLowerCase()){case"content-type":E=getReItem(/charset\s*=\s*(.*)\s*/gi,N,1);
if(E!=""){J=E
}break
}}selectByValue(I,"doctypes",K,true,true);
selectByValue(I,"docencoding",J,true,true);
selectByValue(I,"langdir",tinyMCE.getAttrib(O.body,"dir"),true,true);
if(L!=""){I.xml_pi.checked=true
}C=O.getElementsByTagName("link");
for(F=0;
F<C.length;
F++){D=C[F];
E=tinyMCE.getAttrib(D,"media");
if(tinyMCE.getAttrib(D,"mce_type")=="text/css"&&(E==""||E=="screen"||E=="all")&&tinyMCE.getAttrib(D,"rel")=="stylesheet"){I.stylesheet.value=tinyMCE.getAttrib(D,"mce_href");
break
}}C=O.getElementsByTagName("style");
for(F=0;
F<C.length;
F++){E=parseStyleElement(C[F]);
for(M=0;
M<E.length;
M++){if(E[M].rule.indexOf("a:visited")!=-1&&E[M].data.color){I.visited_color.value=E[M].data.color
}if(E[M].rule.indexOf("a:link")!=-1&&E[M].data.color){I.link_color.value=E[M].data.color
}if(E[M].rule.indexOf("a:active")!=-1&&E[M].data.color){I.active_color.value=E[M].data.color
}}}I.textcolor.value=convertRGBToHex(tinyMCE.getAttrib(O.body,"text"));
I.active_color.value=convertRGBToHex(tinyMCE.getAttrib(O.body,"alink"));
I.link_color.value=convertRGBToHex(tinyMCE.getAttrib(O.body,"link"));
I.visited_color.value=convertRGBToHex(tinyMCE.getAttrib(O.body,"vlink"));
I.bgcolor.value=convertRGBToHex(tinyMCE.getAttrib(O.body,"bgcolor"));
I.bgimage.value=convertRGBToHex(tinyMCE.getAttrib(O.body,"background"));
var A=tinyMCE.parseStyle(tinyMCE.getAttrib(O.body,"style"));
if(A["font-family"]){selectByValue(I,"fontface",A["font-family"],true,true)
}else{selectByValue(I,"fontface",tinyMCE.getParam("fullpage_default_fontface",""),true,true)
}if(A["font-size"]){selectByValue(I,"fontsize",A["font-size"],true,true)
}else{selectByValue(I,"fontsize",tinyMCE.getParam("fullpage_default_fontsize",""),true,true)
}if(A.color){I.textcolor.value=convertRGBToHex(A.color)
}if(A["background-image"]){I.bgimage.value=A["background-image"].replace(new RegExp("url\\('?([^']*)'?\\)","gi"),"$1")
}if(A["background-color"]){I.bgcolor.value=convertRGBToHex(A["background-color"])
}if(A.margin){E=A.margin.replace(/[^0-9 ]/g,"");
E=E.split(/ +/);
I.topmargin.value=E.length>0?E[0]:"";
I.rightmargin.value=E.length>1?E[1]:E[0];
I.bottommargin.value=E.length>2?E[2]:E[0];
I.leftmargin.value=E.length>3?E[3]:E[0]
}if(A["margin-left"]){I.leftmargin.value=A["margin-left"].replace(/[^0-9]/g,"")
}if(A["margin-right"]){I.rightmargin.value=A["margin-right"].replace(/[^0-9]/g,"")
}if(A["margin-top"]){I.topmargin.value=A["margin-top"].replace(/[^0-9]/g,"")
}if(A["margin-bottom"]){I.bottommargin.value=A["margin-bottom"].replace(/[^0-9]/g,"")
}I.style.value=tinyMCE.serializeStyle(A);
updateColor("textcolor_pick","textcolor");
updateColor("bgcolor_pick","bgcolor");
updateColor("visited_color_pick","visited_color");
updateColor("active_color_pick","active_color");
updateColor("link_color_pick","link_color")
}function updateAction(){var G=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var I=document.forms[0];
var A,E,H,K,L,J,F,B,D,C=true;
J=topDoc.getElementsByTagName("head")[0];
A=topDoc.getElementsByTagName("script");
for(E=0;
E<A.length;
E++){if(tinyMCE.getAttrib(A[E],"mce_type")==""){A[E].setAttribute("mce_type","text/javascript")
}}A=topDoc.getElementsByTagName("link");
for(E=0;
E<A.length;
E++){B=A[E];
D=tinyMCE.getAttrib(B,"media");
if(tinyMCE.getAttrib(B,"mce_type")=="text/css"&&(D==""||D=="screen"||D=="all")&&tinyMCE.getAttrib(B,"rel")=="stylesheet"){C=false;
if(I.stylesheet.value==""){B.parentNode.removeChild(B)
}else{B.setAttribute("mce_href",I.stylesheet.value)
}break
}}if(I.stylesheet.value!=""){B=topDoc.createElement("link");
B.setAttribute("mce_type","text/css");
B.setAttribute("mce_href",I.stylesheet.value);
B.setAttribute("rel","stylesheet");
J.appendChild(B)
}setMeta(J,"keywords",I.metakeywords.value);
setMeta(J,"description",I.metadescription.value);
setMeta(J,"author",I.metaauthor.value);
setMeta(J,"copyright",I.metacopyright.value);
setMeta(J,"robots",getSelectValue(I,"metarobots"));
setMeta(J,"Content-Type",getSelectValue(I,"docencoding"));
topDoc.body.dir=getSelectValue(I,"langdir");
topDoc.body.style.cssText=I.style.value;
topDoc.body.setAttribute("vLink",I.visited_color.value);
topDoc.body.setAttribute("link",I.link_color.value);
topDoc.body.setAttribute("text",I.textcolor.value);
topDoc.body.setAttribute("aLink",I.active_color.value);
topDoc.body.style.fontFamily=getSelectValue(I,"fontface");
topDoc.body.style.fontSize=getSelectValue(I,"fontsize");
topDoc.body.style.backgroundColor=I.bgcolor.value;
if(I.leftmargin.value!=""){topDoc.body.style.marginLeft=I.leftmargin.value+"px"
}if(I.rightmargin.value!=""){topDoc.body.style.marginRight=I.rightmargin.value+"px"
}if(I.bottommargin.value!=""){topDoc.body.style.marginBottom=I.bottommargin.value+"px"
}if(I.topmargin.value!=""){topDoc.body.style.marginTop=I.topmargin.value+"px"
}F=topDoc.getElementsByTagName("html")[0];
F.setAttribute("lang",I.langcode.value);
F.setAttribute("xml:lang",I.langcode.value);
if(I.bgimage.value!=""){topDoc.body.style.backgroundImage="url('"+I.bgimage.value+"')"
}else{topDoc.body.style.backgroundImage=""
}G.cleanup.addRuleStr("-title,meta[http-equiv|name|content],base[href|target],link[href|rel|type|title|media],style[type],script[type|language|src],html[lang|xml:lang|xmlns],body[style|dir|vlink|link|text|alink],head");
H=G.cleanup.serializeNodeAsHTML(topDoc.documentElement);
H=H.substring(0,H.lastIndexOf("</body>"));
if(H.indexOf("<title>")==-1){H=H.replace(/<head.*?>/,"$&\n<title>"+G.cleanup.xmlEncode(I.metatitle.value)+"</title>")
}else{H=H.replace(/<title>(.*?)<\/title>/,"<title>"+G.cleanup.xmlEncode(I.metatitle.value)+"</title>")
}if((K=getSelectValue(I,"doctypes"))!=""){H=K+"\n"+H
}if(I.xml_pi.checked){L='<?xml version="1.0"';
if((K=getSelectValue(I,"docencoding"))!=""){L+=' encoding="'+K+'"'
}L+="?>\n";
H=L+H
}G.fullpageTopContent=H;
tinyMCEPopup.execCommand("mceFullPageUpdate",false,"");
tinyMCEPopup.close()
}function setMeta(F,D,C){var B,E,A;
B=F.getElementsByTagName("meta");
for(E=0;
E<B.length;
E++){if(D=="Content-Type"&&tinyMCE.getAttrib(B[E],"http-equiv")==D){if(C==""){B[E].parentNode.removeChild(B[E])
}else{B[E].setAttribute("content","text/html; charset="+C)
}return 
}if(tinyMCE.getAttrib(B[E],"name")==D){if(C==""){B[E].parentNode.removeChild(B[E])
}else{B[E].setAttribute("content",C)
}return 
}}if(C==""){return 
}A=topDoc.createElement("meta");
if(D=="Content-Type"){A.httpEquiv=D
}else{A.setAttribute("name",D)
}A.setAttribute("content",C);
F.appendChild(A)
}function parseStyleElement(E){var A=E.innerHTML;
var D,B,C;
A=A.replace(/<!--/gi,"");
A=A.replace(/-->/gi,"");
A=A.replace(/[\n\r]/gi,"");
A=A.replace(/\s+/gi," ");
C=new Array();
D=A.split(/{|}/);
for(B=0;
B<D.length;
B+=2){if(D[B]!=""){C[C.length]={rule:tinyMCE.trim(D[B]),data:tinyMCE.parseStyle(D[B+1])}
}}return C
}function serializeStyleElement(D){var B,C,A;
C="<!--\n";
for(B=0;
B<D.length;
B++){C+=D[B].rule+" {\n";
A=tinyMCE.serializeStyle(D[B].data);
if(A!=""){A+=";"
}C+=A.replace(/;/g,";\n");
C+="}\n";
if(B!=D.length-1){C+="\n"
}}C+="\n-->";
return C
}function getReItem(C,B,A){var D=C.exec(B);
if(D&&D.length>A){return D[A]
}return""
}function changedStyleField(A){}function showAddMenu(){var A=document.getElementById("addbutton");
addMenuLayer.moveRelativeTo(A,"tr");
if(addMenuLayer.isMSIE){addMenuLayer.moveBy(2,0)
}addMenuLayer.show();
addMenuLayer.setAutoHide(true,hideAddMenu);
addMenuLayer.addCSSClass(A,"selected")
}function hideAddMenu(A,D,E,C){var B=document.getElementById("addbutton");
addMenuLayer.removeCSSClass(B,"selected")
}function addHeadElm(C){var A=document.getElementById("headlist");
var B=document.getElementById("addbutton");
var D=document.getElementById(C+"_element");
if(lastElementType){lastElementType.style.display="none"
}D.style.display="block";
lastElementType=D;
addMenuLayer.hide();
addMenuLayer.removeCSSClass(B,"selected");
document.getElementById(C+"_updateelement").value=tinyMCE.getLang("lang_insert","Insert",true);
A.size=10
}function updateHeadElm(D){var C=D.substring(0,D.indexOf("_"));
var A=document.getElementById("headlist");
var B=document.getElementById("addbutton");
var E=document.getElementById(C+"_element");
if(lastElementType){lastElementType.style.display="none"
}E.style.display="block";
lastElementType=E;
addMenuLayer.hide();
addMenuLayer.removeCSSClass(B,"selected");
document.getElementById(C+"_updateelement").value=tinyMCE.getLang("lang_update","Update",true);
A.size=10
}function cancelElementUpdate(){var A=document.getElementById("headlist");
if(lastElementType){lastElementType.style.display="none"
}A.size=26
};