var templates={"window.open":"window.open('${url}','${target}','${options}')"};
function preinit(){tinyMCE.setWindowArg("mce_windowresize",false);
var A=tinyMCE.getParam("external_link_list_url");
if(A!=null){if(A.charAt(0)!="/"&&A.indexOf("://")==-1){A=tinyMCE.documentBasePath+"/"+A
}document.write('<script language="javascript" type="text/javascript" src="'+A+'"><\/script>')
}}function changeClass(){var A=document.forms[0];
A.classes.value=getSelectValue(A,"classlist")
}function init(){tinyMCEPopup.resizeToInnerSize();
var A=document.forms[0];
var G=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var H=G.getFocusElement();
var F="insert";
var E;
document.getElementById("hrefbrowsercontainer").innerHTML=getBrowserHTML("hrefbrowser","href","file","advlink");
document.getElementById("popupurlbrowsercontainer").innerHTML=getBrowserHTML("popupurlbrowser","popupurl","file","advlink");
document.getElementById("linklisthrefcontainer").innerHTML=getLinkListHTML("linklisthref","href");
document.getElementById("anchorlistcontainer").innerHTML=getAnchorListHTML("anchorlist","href");
document.getElementById("targetlistcontainer").innerHTML=getTargetListHTML("targetlist","target");
E=getLinkListHTML("linklisthref","href");
if(E==""){document.getElementById("linklisthrefrow").style.display="none"
}else{document.getElementById("linklisthrefcontainer").innerHTML=E
}if(isVisible("hrefbrowser")){document.getElementById("href").style.width="260px"
}if(isVisible("popupurlbrowser")){document.getElementById("popupurl").style.width="180px"
}H=tinyMCE.getParentElement(H,"a");
if(H!=null&&H.nodeName=="A"){F="update"
}A.insert.value=tinyMCE.getLang("lang_"+F,"Insert",true);
setPopupControlsDisabled(true);
if(F=="update"){var B=tinyMCE.getAttrib(H,"href");
B=convertURL(B,H,true);
var D=tinyMCE.getAttrib(H,"mce_href");
if(D!=""){B=D;
if(tinyMCE.getParam("convert_urls")){B=convertURL(B,H,true)
}}var C=tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onclick"));
setFormValue("href",B);
setFormValue("title",tinyMCE.getAttrib(H,"title"));
setFormValue("id",tinyMCE.getAttrib(H,"id"));
setFormValue("style",tinyMCE.serializeStyle(tinyMCE.parseStyle(tinyMCE.getAttrib(H,"style"))));
setFormValue("rel",tinyMCE.getAttrib(H,"rel"));
setFormValue("rev",tinyMCE.getAttrib(H,"rev"));
setFormValue("charset",tinyMCE.getAttrib(H,"charset"));
setFormValue("hreflang",tinyMCE.getAttrib(H,"hreflang"));
setFormValue("dir",tinyMCE.getAttrib(H,"dir"));
setFormValue("lang",tinyMCE.getAttrib(H,"lang"));
setFormValue("tabindex",tinyMCE.getAttrib(H,"tabindex",typeof (H.tabindex)!="undefined"?H.tabindex:""));
setFormValue("accesskey",tinyMCE.getAttrib(H,"accesskey",typeof (H.accesskey)!="undefined"?H.accesskey:""));
setFormValue("type",tinyMCE.getAttrib(H,"type"));
setFormValue("onfocus",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onfocus")));
setFormValue("onblur",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onblur")));
setFormValue("onclick",C);
setFormValue("ondblclick",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"ondblclick")));
setFormValue("onmousedown",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onmousedown")));
setFormValue("onmouseup",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onmouseup")));
setFormValue("onmouseover",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onmouseover")));
setFormValue("onmousemove",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onmousemove")));
setFormValue("onmouseout",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onmouseout")));
setFormValue("onkeypress",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onkeypress")));
setFormValue("onkeydown",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onkeydown")));
setFormValue("onkeyup",tinyMCE.cleanupEventStr(tinyMCE.getAttrib(H,"onkeyup")));
setFormValue("target",tinyMCE.getAttrib(H,"target"));
setFormValue("classes",tinyMCE.getAttrib(H,"class"));
if(C!=null&&C.indexOf("window.open")!=-1){parseWindowOpen(C)
}else{parseFunction(C)
}selectByValue(A,"dir",tinyMCE.getAttrib(H,"dir"));
selectByValue(A,"rel",tinyMCE.getAttrib(H,"rel"));
selectByValue(A,"rev",tinyMCE.getAttrib(H,"rev"));
selectByValue(A,"linklisthref",B);
if(B.charAt(0)=="#"){selectByValue(A,"anchorlist",B)
}addClassesToList("classlist","advlink_styles");
selectByValue(A,"classlist",tinyMCE.getAttrib(H,"class"),true);
selectByValue(A,"targetlist",tinyMCE.getAttrib(H,"target"),true)
}else{addClassesToList("classlist","advlink_styles")
}window.focus()
}function setFormValue(A,B){document.forms[0].elements[A].value=B
}function convertURL(url,node,on_save){return eval("tinyMCEPopup.windowOpener."+tinyMCE.settings.urlconverter_callback+"(url, node, on_save);")
}function parseWindowOpen(C){var A=document.forms[0];
if(C.indexOf("return false;")!=-1){A.popupreturn.checked=true;
C=C.replace("return false;","")
}else{A.popupreturn.checked=false
}var E=parseLink(C);
if(E!=null){A.ispopup.checked=true;
setPopupControlsDisabled(false);
var D=parseOptions(E.options);
var B=E.url;
if(tinyMCE.getParam("convert_urls")){B=convertURL(B,null,true)
}A.popupname.value=E.target;
A.popupurl.value=B;
A.popupwidth.value=getOption(D,"width");
A.popupheight.value=getOption(D,"height");
A.popupleft.value=getOption(D,"left");
A.popuptop.value=getOption(D,"top");
if(A.popupleft.value.indexOf("screen")!=-1){A.popupleft.value="c"
}if(A.popuptop.value.indexOf("screen")!=-1){A.popuptop.value="c"
}A.popuplocation.checked=getOption(D,"location")=="yes";
A.popupscrollbars.checked=getOption(D,"scrollbars")=="yes";
A.popupmenubar.checked=getOption(D,"menubar")=="yes";
A.popupresizable.checked=getOption(D,"resizable")=="yes";
A.popuptoolbar.checked=getOption(D,"toolbar")=="yes";
A.popupstatus.checked=getOption(D,"status")=="yes";
A.popupdependent.checked=getOption(D,"dependent")=="yes";
buildOnClick()
}}function parseFunction(B){var A=document.forms[0];
var C=parseLink(B)
}function getOption(B,A){return typeof (B[A])=="undefined"?"":B[A]
}function setPopupControlsDisabled(B){var A=document.forms[0];
A.popupname.disabled=B;
A.popupurl.disabled=B;
A.popupwidth.disabled=B;
A.popupheight.disabled=B;
A.popupleft.disabled=B;
A.popuptop.disabled=B;
A.popuplocation.disabled=B;
A.popupscrollbars.disabled=B;
A.popupmenubar.disabled=B;
A.popupresizable.disabled=B;
A.popuptoolbar.disabled=B;
A.popupstatus.disabled=B;
A.popupreturn.disabled=B;
A.popupdependent.disabled=B;
setBrowserDisabled("popupurlbrowser",B)
}function parseLink(F){F=F.replace(new RegExp("&#39;","g"),"'");
var A=F.replace(new RegExp("\\s*([A-Za-z0-9.]*)\\s*\\(.*","gi"),"$1");
var H=templates[A];
if(H){var G=H.match(new RegExp("'?\\$\\{[A-Za-z0-9.]*\\}'?","gi"));
var E="\\s*[A-Za-z0-9.]*\\s*\\(";
var D="";
for(var C=0;
C<G.length;
C++){if(G[C].indexOf("'${")!=-1){E+="'(.*)'"
}else{E+="([0-9]*)"
}D+="$"+(C+1);
G[C]=G[C].replace(new RegExp("[^A-Za-z0-9]","gi"),"");
if(C!=G.length-1){E+="\\s*,\\s*";
D+="<delim>"
}else{E+=".*"
}}E+="\\);?";
var I=new Array();
I._function=A;
var B=F.replace(new RegExp(E,"gi"),D).split("<delim>");
for(var C=0;
C<G.length;
C++){I[G[C]]=B[C]
}return I
}return null
}function parseOptions(C){if(C==null||C==""){return new Array()
}C=C.toLowerCase();
C=C.replace(/;/g,",");
C=C.replace(/[^0-9a-z=,]/g,"");
var E=C.split(",");
var A=new Array();
for(var B=0;
B<E.length;
B++){var D=E[B].split("=");
if(D.length==2){A[D[0]]=D[1]
}}return A
}function buildOnClick(){var A=document.forms[0];
if(!A.ispopup.checked){A.onclick.value="";
return 
}var C="window.open('";
var B=A.popupurl.value;
if(tinyMCE.getParam("convert_urls")){B=convertURL(B,null,true)
}C+=B+"','";
C+=A.popupname.value+"','";
if(A.popuplocation.checked){C+="location=yes,"
}if(A.popupscrollbars.checked){C+="scrollbars=yes,"
}if(A.popupmenubar.checked){C+="menubar=yes,"
}if(A.popupresizable.checked){C+="resizable=yes,"
}if(A.popuptoolbar.checked){C+="toolbar=yes,"
}if(A.popupstatus.checked){C+="status=yes,"
}if(A.popupdependent.checked){C+="dependent=yes,"
}if(A.popupwidth.value!=""){C+="width="+A.popupwidth.value+","
}if(A.popupheight.value!=""){C+="height="+A.popupheight.value+","
}if(A.popupleft.value!=""){if(A.popupleft.value!="c"){C+="left="+A.popupleft.value+","
}else{C+="left='+(screen.availWidth/2-"+(A.popupwidth.value/2)+")+',"
}}if(A.popuptop.value!=""){if(A.popuptop.value!="c"){C+="top="+A.popuptop.value+","
}else{C+="top='+(screen.availHeight/2-"+(A.popupheight.value/2)+")+',"
}}if(C.charAt(C.length-1)==","){C=C.substring(0,C.length-1)
}C+="');";
if(A.popupreturn.checked){C+="return false;"
}A.onclick.value=C;
if(A.href.value==""){A.href.value=B
}}function setAttrib(elm,attrib,value){var formObj=document.forms[0];
var valueElm=formObj.elements[attrib.toLowerCase()];
if(typeof (value)=="undefined"||value==null){value="";
if(valueElm){value=valueElm.value
}}if(value!=""){elm.setAttribute(attrib.toLowerCase(),value);
if(attrib=="style"){attrib="style.cssText"
}if(attrib.substring(0,2)=="on"){value="return true;"+value
}if(attrib=="class"){attrib="className"
}eval("elm."+attrib+"=value;")
}else{elm.removeAttribute(attrib)
}}function getAnchorListHTML(F,E){var D=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var A=D.getBody().getElementsByTagName("a");
var C="";
C+='<select id="'+F+'" name="'+F+'" class="mceAnchorList" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+E+".value=";
C+='this.options[this.selectedIndex].value;">';
C+='<option value="">---</option>';
for(var B=0;
B<A.length;
B++){if((name=tinyMCE.getAttrib(A[B],"name"))!=""){C+='<option value="#'+name+'">'+name+"</option>"
}}C+="</select>";
return C
}function insertAction(){var F=tinyMCE.getInstanceById(tinyMCE.getWindowArg("editor_id"));
var G=F.getFocusElement();
G=tinyMCE.getParentElement(G,"a");
tinyMCEPopup.execCommand("mceBeginUndoLevel");
if(G==null){if(tinyMCE.isSafari){tinyMCEPopup.execCommand("mceInsertContent",false,'<a href="#mce_temp_url#">'+F.selection.getSelectedHTML()+"</a>")
}else{tinyMCEPopup.execCommand("createlink",false,"#mce_temp_url#")
}var B=tinyMCE.getElementsByAttributeValue(F.getBody(),"a","href","#mce_temp_url#");
for(var C=0;
C<B.length;
C++){var G=B[C];
if(tinyMCE.isGecko){var E=F.getDoc().createTextNode(" ");
if(G.nextSibling){G.parentNode.insertBefore(E,G.nextSibling)
}else{G.parentNode.appendChild(E)
}var A=F.getDoc().createRange();
A.setStartAfter(G);
A.setEndAfter(G);
var D=F.getSel();
D.removeAllRanges();
D.addRange(A)
}setAllAttribs(G)
}}else{setAllAttribs(G)
}tinyMCE._setEventsEnabled(F.getBody(),false);
tinyMCEPopup.execCommand("mceEndUndoLevel");
tinyMCEPopup.close()
}function setAllAttribs(D){var A=document.forms[0];
var B=A.href.value;
var C=getSelectValue(A,"targetlist");
if(B.charAt(0)=="#"&&tinyMCE.getParam("convert_urls")){B=tinyMCE.settings.document_base_url+B
}setAttrib(D,"href",convertURL(B,D));
setAttrib(D,"mce_href",B);
setAttrib(D,"title");
setAttrib(D,"target",C=="_self"?"":C);
setAttrib(D,"id");
setAttrib(D,"style");
setAttrib(D,"class",getSelectValue(A,"classlist"));
setAttrib(D,"rel");
setAttrib(D,"rev");
setAttrib(D,"charset");
setAttrib(D,"hreflang");
setAttrib(D,"dir");
setAttrib(D,"lang");
setAttrib(D,"tabindex");
setAttrib(D,"accesskey");
setAttrib(D,"type");
setAttrib(D,"onfocus");
setAttrib(D,"onblur");
setAttrib(D,"onclick");
setAttrib(D,"ondblclick");
setAttrib(D,"onmousedown");
setAttrib(D,"onmouseup");
setAttrib(D,"onmouseover");
setAttrib(D,"onmousemove");
setAttrib(D,"onmouseout");
setAttrib(D,"onkeypress");
setAttrib(D,"onkeydown");
setAttrib(D,"onkeyup");
if(tinyMCE.isMSIE5){D.outerHTML=D.outerHTML
}}function getSelectValue(A,B){var C=A.elements[B];
if(C==null||C.options==null){return""
}return C.options[C.selectedIndex].value
}function getLinkListHTML(D,C,E){if(typeof (tinyMCELinkList)=="undefined"||tinyMCELinkList.length==0){return""
}var B="";
B+='<select id="'+D+'" name="'+D+'"';
B+=' class="mceLinkList" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+C+".value=";
B+="this.options[this.selectedIndex].value;";
if(typeof (E)!="undefined"){B+=E+"('"+C+"',this.options[this.selectedIndex].text,this.options[this.selectedIndex].value);"
}B+='"><option value="">---</option>';
for(var A=0;
A<tinyMCELinkList.length;
A++){B+='<option value="'+tinyMCELinkList[A][1]+'">'+tinyMCELinkList[A][0]+"</option>"
}B+="</select>";
return B
}function getTargetListHTML(F,E){var A=tinyMCE.getParam("theme_advanced_link_targets","").split(";");
var D="";
D+='<select id="'+F+'" name="'+F+'" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.'+E+".value=";
D+='this.options[this.selectedIndex].value;">';
D+='<option value="_self">'+tinyMCE.getLang("lang_advlink_target_same")+"</option>";
D+='<option value="_blank">'+tinyMCE.getLang("lang_advlink_target_blank")+" (_blank)</option>";
D+='<option value="_parent">'+tinyMCE.getLang("lang_advlink_target_parent")+" (_parent)</option>";
D+='<option value="_top">'+tinyMCE.getLang("lang_advlink_target_top")+" (_top)</option>";
for(var C=0;
C<A.length;
C++){var B,G;
if(A[C]==""){continue
}B=A[C].split("=")[0];
G=A[C].split("=")[1];
D+='<option value="'+B+'">'+G+" ("+B+")</option>"
}D+="</select>";
return D
}preinit();