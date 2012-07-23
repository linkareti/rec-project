var preloadImg=null;
var orgImageWidth,orgImageHeight;
function preinit(){tinyMCE.setWindowArg("mce_windowresize",false);
var A=tinyMCE.getParam("external_image_list_url");
if(A!=null){if(A.charAt(0)!="/"&&A.indexOf("://")==-1){A=tinyMCE.documentBasePath+"/"+A
}document.write('<script language="javascript" type="text/javascript" src="'+A+'"><\/script>')
}}function convertURL(url,node,on_save){return eval("tinyMCEPopup.windowOpener."+tinyMCE.settings.urlconverter_callback+"(url, node, on_save);")
}function getImageSrc(B){var C=-1;
if(!B){return""
}if((C=B.indexOf("this.src="))!=-1){var A=B.substring(C+10);
A=A.substring(0,A.indexOf("'"));
if(tinyMCE.getParam("convert_urls")){A=convertURL(A,null,true)
}return A
}return""
}function init(){tinyMCEPopup.resizeToInnerSize();
var H=document.forms[0];
var F=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var I=F.getFocusElement();
var E="insert";
var G="";
G=getImageListHTML("imagelistsrc","src","onSelectMainImage");
if(G==""){document.getElementById("imagelistsrcrow").style.display="none"
}else{document.getElementById("imagelistsrccontainer").innerHTML=G
}G=getImageListHTML("imagelistover","onmouseoversrc");
if(G==""){document.getElementById("imagelistoverrow").style.display="none"
}else{document.getElementById("imagelistovercontainer").innerHTML=G
}G=getImageListHTML("imagelistout","onmouseoutsrc");
if(G==""){document.getElementById("imagelistoutrow").style.display="none"
}else{document.getElementById("imagelistoutcontainer").innerHTML=G
}G=getBrowserHTML("srcbrowser","src","image","advimage");
document.getElementById("srcbrowsercontainer").innerHTML=G;
G=getBrowserHTML("oversrcbrowser","onmouseoversrc","image","advimage");
document.getElementById("onmouseoversrccontainer").innerHTML=G;
G=getBrowserHTML("outsrcbrowser","onmouseoutsrc","image","advimage");
document.getElementById("onmouseoutsrccontainer").innerHTML=G;
G=getBrowserHTML("longdescbrowser","longdesc","file","advimage");
document.getElementById("longdesccontainer").innerHTML=G;
if(isVisible("srcbrowser")){document.getElementById("src").style.width="260px"
}if(isVisible("oversrcbrowser")){document.getElementById("onmouseoversrc").style.width="260px"
}if(isVisible("outsrcbrowser")){document.getElementById("onmouseoutsrc").style.width="260px"
}if(isVisible("longdescbrowser")){document.getElementById("longdesc").style.width="180px"
}if(I!=null&&I.nodeName=="IMG"){E="update"
}H.insert.value=tinyMCE.getLang("lang_"+E,"Insert",true);
if(E=="update"){var A=tinyMCE.getAttrib(I,"src");
var J=getImageSrc(tinyMCE.cleanupEventStr(tinyMCE.getAttrib(I,"onmouseover")));
var C=getImageSrc(tinyMCE.cleanupEventStr(tinyMCE.getAttrib(I,"onmouseout")));
A=convertURL(A,I,true);
var D=tinyMCE.getAttrib(I,"mce_src");
if(D!=""){A=D;
if(tinyMCE.getParam("convert_urls")){A=convertURL(A,I,true)
}}if(J!=""&&tinyMCE.getParam("convert_urls")){J=convertURL(J,I,true)
}if(C!=""&&tinyMCE.getParam("convert_urls")){C=convertURL(C,I,true)
}var B=tinyMCE.parseStyle(tinyMCE.getAttrib(I,"style"));
orgImageWidth=trimSize(getStyle(I,"width"));
orgImageHeight=trimSize(getStyle(I,"height"));
H.src.value=A;
H.alt.value=tinyMCE.getAttrib(I,"alt");
H.title.value=tinyMCE.getAttrib(I,"title");
H.border.value=trimSize(getStyle(I,"border","borderWidth"));
H.vspace.value=tinyMCE.getAttrib(I,"vspace");
H.hspace.value=tinyMCE.getAttrib(I,"hspace");
H.width.value=orgImageWidth;
H.height.value=orgImageHeight;
H.onmouseoversrc.value=J;
H.onmouseoutsrc.value=C;
H.id.value=tinyMCE.getAttrib(I,"id");
H.dir.value=tinyMCE.getAttrib(I,"dir");
H.lang.value=tinyMCE.getAttrib(I,"lang");
H.longdesc.value=tinyMCE.getAttrib(I,"longdesc");
H.usemap.value=tinyMCE.getAttrib(I,"usemap");
H.style.value=tinyMCE.serializeStyle(B);
if(tinyMCE.isMSIE){selectByValue(H,"align",getStyle(I,"align","styleFloat"))
}else{selectByValue(H,"align",getStyle(I,"align","cssFloat"))
}addClassesToList("classlist","advimage_styles");
selectByValue(H,"classlist",tinyMCE.getAttrib(I,"class"));
selectByValue(H,"imagelistsrc",A);
selectByValue(H,"imagelistover",J);
selectByValue(H,"imagelistout",C);
updateStyle();
showPreviewImage(A,true);
changeAppearance();
window.focus()
}else{addClassesToList("classlist","advimage_styles")
}if(tinyMCE.getParam("advimage_constrain_proportions",true)){H.constrain.checked=true
}if(H.onmouseoversrc.value!=""||H.onmouseoutsrc.value!=""){setSwapImageDisabled(false)
}else{setSwapImageDisabled(true)
}}function setSwapImageDisabled(B){var A=document.forms[0];
A.onmousemovecheck.checked=!B;
setBrowserDisabled("overbrowser",B);
setBrowserDisabled("outbrowser",B);
if(A.imagelistover){A.imagelistover.disabled=B
}if(A.imagelistout){A.imagelistout.disabled=B
}A.onmouseoversrc.disabled=B;
A.onmouseoutsrc.disabled=B
}function setAttrib(elm,attrib,value){var formObj=document.forms[0];
var valueElm=formObj.elements[attrib];
if(typeof (value)=="undefined"||value==null){value="";
if(valueElm){value=valueElm.value
}}if(value!=""){elm.setAttribute(attrib,value);
if(attrib=="style"){attrib="style.cssText"
}if(attrib=="longdesc"){attrib="longDesc"
}if(attrib=="width"){attrib="style.width";
value=value+"px"
}if(attrib=="height"){attrib="style.height";
value=value+"px"
}if(attrib=="class"){attrib="className"
}eval("elm."+attrib+"=value;")
}else{elm.removeAttribute(attrib)
}}function makeAttrib(D,C){var B=document.forms[0];
var A=B.elements[D];
if(typeof (C)=="undefined"||C==null){C="";
if(A){C=A.value
}}if(C==""){return""
}C=C.replace(/&/g,"&amp;");
C=C.replace(/\"/g,"&quot;");
C=C.replace(/</g,"&lt;");
C=C.replace(/>/g,"&gt;");
return" "+D+'="'+C+'"'
}function insertAction(){var E=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var H=E.getFocusElement();
var A=document.forms[0];
var G=A.src.value;
var F=A.onmouseoversrc.value;
var B=A.onmouseoutsrc.value;
if(tinyMCE.getParam("accessibility_warnings")){if(A.alt.value==""){var D=confirm(tinyMCE.getLang("lang_advimage_missing_alt","",true));
if(D==true){A.alt.value=" "
}}else{var D=true
}if(!D){return 
}}if(F&&F!=""){F="this.src='"+convertURL(F,tinyMCE.imgElement)+"';"
}if(B&&B!=""){B="this.src='"+convertURL(B,tinyMCE.imgElement)+"';"
}if(H!=null&&H.nodeName=="IMG"){setAttrib(H,"src",convertURL(G,tinyMCE.imgElement));
setAttrib(H,"mce_src",G);
setAttrib(H,"alt");
setAttrib(H,"title");
setAttrib(H,"border");
setAttrib(H,"vspace");
setAttrib(H,"hspace");
setAttrib(H,"width");
setAttrib(H,"height");
setAttrib(H,"onmouseover",F);
setAttrib(H,"onmouseout",B);
setAttrib(H,"id");
setAttrib(H,"dir");
setAttrib(H,"lang");
setAttrib(H,"longdesc");
setAttrib(H,"usemap");
setAttrib(H,"style");
setAttrib(H,"class",getSelectValue(A,"classlist"));
setAttrib(H,"align",getSelectValue(A,"align"));
if(A.width.value!=orgImageWidth||A.height.value!=orgImageHeight){E.repaint()
}if(tinyMCE.isMSIE5){H.outerHTML=H.outerHTML
}}else{var C="<img";
C+=makeAttrib("src",convertURL(G,tinyMCE.imgElement));
C+=makeAttrib("mce_src",G);
C+=makeAttrib("alt");
C+=makeAttrib("title");
C+=makeAttrib("border");
C+=makeAttrib("vspace");
C+=makeAttrib("hspace");
C+=makeAttrib("width");
C+=makeAttrib("height");
C+=makeAttrib("onmouseover",F);
C+=makeAttrib("onmouseout",B);
C+=makeAttrib("id");
C+=makeAttrib("dir");
C+=makeAttrib("lang");
C+=makeAttrib("longdesc");
C+=makeAttrib("usemap");
C+=makeAttrib("style");
C+=makeAttrib("class",getSelectValue(A,"classlist"));
C+=makeAttrib("align",getSelectValue(A,"align"));
C+=" />";
tinyMCEPopup.execCommand("mceInsertContent",false,C)
}tinyMCE._setEventsEnabled(E.getBody(),false);
tinyMCEPopup.close()
}function cancelAction(){tinyMCEPopup.close()
}function changeAppearance(){var A=document.forms[0];
var B=document.getElementById("alignSampleImg");
if(B){B.align=A.align.value;
B.border=A.border.value;
B.hspace=A.hspace.value;
B.vspace=A.vspace.value
}}function changeMouseMove(){var A=document.forms[0];
setSwapImageDisabled(!A.onmousemovecheck.checked)
}function updateStyle(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
if(tinyMCE.getParam("inline_styles",false)){B.width=A.width.value==""?"":A.width.value+"px";
B.height=A.height.value==""?"":A.height.value+"px";
B["border-width"]=A.border.value==""?"":A.border.value+"px";
B["margin-top"]=A.vspace.value==""?"":A.vspace.value+"px";
B["margin-bottom"]=A.vspace.value==""?"":A.vspace.value+"px";
B["margin-left"]=A.hspace.value==""?"":A.hspace.value+"px";
B["margin-right"]=A.hspace.value==""?"":A.hspace.value+"px"
}else{B.width=B.height=B["border-width"]=null;
if(B["margin-top"]==B["margin-bottom"]){B["margin-top"]=B["margin-bottom"]=null
}if(B["margin-left"]==B["margin-right"]){B["margin-left"]=B["margin-right"]=null
}}A.style.value=tinyMCE.serializeStyle(B)
}function styleUpdated(){var A=document.forms[0];
var B=tinyMCE.parseStyle(A.style.value);
if(B.width){A.width.value=B.width.replace("px","")
}if(B.height){A.height.value=B.height.replace("px","")
}if(B["margin-top"]&&B["margin-top"]==B["margin-bottom"]){A.vspace.value=B["margin-top"].replace("px","")
}if(B["margin-left"]&&B["margin-left"]==B["margin-right"]){A.hspace.value=B["margin-left"].replace("px","")
}if(B["border-width"]){A.border.value=B["border-width"].replace("px","")
}}function changeHeight(){var A=document.forms[0];
if(!A.constrain.checked||!preloadImg){updateStyle();
return 
}if(A.width.value==""||A.height.value==""){return 
}var B=(A.width.value/preloadImg.width)*preloadImg.height;
A.height.value=B.toFixed(0);
updateStyle()
}function changeWidth(){var A=document.forms[0];
if(!A.constrain.checked||!preloadImg){updateStyle();
return 
}if(A.width.value==""||A.height.value==""){return 
}var B=(A.height.value/preloadImg.height)*preloadImg.width;
A.width.value=B.toFixed(0);
updateStyle()
}function onSelectMainImage(C,B,D){var A=document.forms[0];
A.alt.value=B;
A.title.value=B;
resetImageData();
showPreviewImage(A.elements[C].value,false)
}function showPreviewImage(B,D){var A=document.forms[0];
selectByValue(document.forms[0],"imagelistsrc",B);
var C=document.getElementById("prev");
var B=B==""?B:tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,B);
if(!D&&tinyMCE.getParam("advimage_update_dimensions_onchange",true)){resetImageData()
}if(B==""){C.innerHTML=""
}else{C.innerHTML='<img src="'+B+'" border="0" />'
}getImageData(B)
}function getImageData(A){preloadImg=new Image();
tinyMCE.addEvent(preloadImg,"load",updateImageData);
tinyMCE.addEvent(preloadImg,"error",resetImageData);
preloadImg.src=A
}function updateImageData(){var A=document.forms[0];
if(A.width.value==""){A.width.value=preloadImg.width
}if(A.height.value==""){A.height.value=preloadImg.height
}updateStyle()
}function resetImageData(){var A=document.forms[0];
A.width.value=A.height.value=""
}function getSelectValue(A,B){var C=A.elements[B];
if(C==null||C.options==null){return""
}return C.options[C.selectedIndex].value
}function getImageListHTML(D,C,E){if(typeof (tinyMCEImageList)=="undefined"||tinyMCEImageList.length==0){return""
}var B="";
B+='<select id="'+D+'" name="'+D+'"';
B+=' class="mceImageList" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+C+".value=";
B+="this.options[this.selectedIndex].value;";
if(typeof (E)!="undefined"){B+=E+"('"+C+"',this.options[this.selectedIndex].text,this.options[this.selectedIndex].value);"
}B+='"><option value="">---</option>';
for(var A=0;
A<tinyMCEImageList.length;
A++){B+='<option value="'+tinyMCEImageList[A][1]+'">'+tinyMCEImageList[A][0]+"</option>"
}B+="</select>";
return B
}preinit();