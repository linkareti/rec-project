tinyMCE.importPluginLanguagePack("paste","en,tr,sv,cs,zh_cn,fr_ca,da,he,nb,de,hu,ru,ru_KOI8-R,ru_UTF-8,nn,fi,es,cy,is,pl,nl,fr,pt_br");
var TinyMCE_PastePlugin={getInfo:function(){return{longname:"Paste text/word",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_paste.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){if(tinyMCE.isMSIE&&tinyMCE.getParam("paste_auto_cleanup_on_paste",false)){tinyMCE.addEvent(A.getBody(),"paste",TinyMCE_PastePlugin._handlePasteEvent)
}},getControlHTML:function(A){switch(A){case"pastetext":return tinyMCE.getButtonHTML(A,"lang_paste_text_desc","{$pluginurl}/images/pastetext.gif","mcePasteText",true);
case"pasteword":return tinyMCE.getButtonHTML(A,"lang_paste_word_desc","{$pluginurl}/images/pasteword.gif","mcePasteWord",true);
case"selectall":return tinyMCE.getButtonHTML(A,"lang_selectall_desc","{$pluginurl}/images/selectall.gif","mceSelectAll",true)
}return""
},execCommand:function(G,C,F,H,E){switch(F){case"mcePasteText":if(H){if((tinyMCE.isMSIE&&!tinyMCE.isOpera)&&!tinyMCE.getParam("paste_use_dialog",false)){TinyMCE_PastePlugin._insertText(clipboardData.getData("Text"),true)
}else{var D=new Array();
D.file="../../plugins/paste/pastetext.htm";
D.width=450;
D.height=400;
var A="";
tinyMCE.openWindow(D,{editor_id:G,plain_text:A,resizable:"yes",scrollbars:"no",inline:"yes",mceDo:"insert"})
}}else{TinyMCE_PastePlugin._insertText(E.html,E.linebreaks)
}return true;
case"mcePasteWord":if(H){if((tinyMCE.isMSIE&&!tinyMCE.isOpera)&&!tinyMCE.getParam("paste_use_dialog",false)){var B=TinyMCE_PastePlugin._clipboardHTML();
if(B&&B.length>0){TinyMCE_PastePlugin._insertWordContent(B)
}}else{var D=new Array();
D.file="../../plugins/paste/pasteword.htm";
D.width=450;
D.height=400;
var A="";
tinyMCE.openWindow(D,{editor_id:G,plain_text:A,resizable:"yes",scrollbars:"no",inline:"yes",mceDo:"insert"})
}}else{TinyMCE_PastePlugin._insertWordContent(E)
}return true;
case"mceSelectAll":tinyMCE.execInstanceCommand(G,"selectall");
return true
}return false
},_handlePasteEvent:function(D){switch(D.type){case"paste":var A=TinyMCE_PastePlugin._clipboardHTML();
var B,C=tinyMCE.selectedInstance;
if(C&&(B=C.getRng())&&B.text.length>0){tinyMCE.execCommand("delete")
}if(A&&A.length>0){tinyMCE.execCommand("mcePasteWord",false,A)
}tinyMCE.cancelEvent(D);
return false
}return true
},_insertText:function(D,C){if(D&&D.length>0){if(C){if(tinyMCE.getParam("paste_create_paragraphs",true)){var G=tinyMCE.getParam("paste_replace_list","\u2122,<sup>TM</sup>,\u2026,...,\u201c|\u201d,\",\u2019,',\u2013|\u2014|\u2015|\u2212,-").split(",");
for(var A=0;
A<G.length;
A+=2){D=D.replace(new RegExp(G[A],"gi"),G[A+1])
}D=tinyMCE.regexpReplace(D,"\r\n\r\n","</p><p>","gi");
D=tinyMCE.regexpReplace(D,"\r\r","</p><p>","gi");
D=tinyMCE.regexpReplace(D,"\n\n","</p><p>","gi");
if((pos=D.indexOf("</p><p>"))!=-1){tinyMCE.execCommand("Delete");
var B=tinyMCE.selectedInstance.getFocusElement();
var H=new Array();
do{if(B.nodeType==1){if(B.nodeName=="TD"||B.nodeName=="BODY"){break
}H[H.length]=B
}}while(B=B.parentNode);
var E="",F="</p>";
E+=D.substring(0,pos);
for(var A=0;
A<H.length;
A++){E+="</"+H[A].nodeName+">";
F+="<"+H[(H.length-1)-A].nodeName+">"
}E+="<p>";
D=E+D.substring(pos+7)+F
}}if(tinyMCE.getParam("paste_create_linebreaks",true)){D=tinyMCE.regexpReplace(D,"\r\n","<br />","gi");
D=tinyMCE.regexpReplace(D,"\r","<br />","gi");
D=tinyMCE.regexpReplace(D,"\n","<br />","gi")
}}tinyMCE.execCommand("mceInsertRawHTML",false,D)
}},_insertWordContent:function(content){if(content&&content.length>0){var bull=String.fromCharCode(8226);
var middot=String.fromCharCode(183);
var cb;
if((cb=tinyMCE.getParam("paste_insert_word_content_callback",""))!=""){content=eval(cb+"('before', content)")
}var rl=tinyMCE.getParam("paste_replace_list","\u2122,<sup>TM</sup>,\u2026,...,\u201c|\u201d,\",\u2019,',\u2013|\u2014|\u2015|\u2212,-").split(",");
for(var i=0;
i<rl.length;
i+=2){content=content.replace(new RegExp(rl[i],"gi"),rl[i+1])
}if(tinyMCE.getParam("paste_convert_headers_to_strong",false)){content=content.replace(new RegExp("<p class=MsoHeading.*?>(.*?)</p>","gi"),"<p><b>$1</b></p>")
}content=content.replace(new RegExp('tab-stops: list [0-9]+.0pt">',"gi"),'">--list--');
content=content.replace(new RegExp(bull+"(.*?)<BR>","gi"),"<p>"+middot+"$1</p>");
content=content.replace(new RegExp('<SPAN style="mso-list: Ignore">',"gi"),"<span>"+bull);
content=content.replace(/<o:p><\/o:p>/gi,"");
content=content.replace(new RegExp('<br style="page-break-before: always;.*>',"gi"),"-- page break --");
content=content.replace(new RegExp("<(!--)([^>]*)(--)>","g"),"");
if(tinyMCE.getParam("paste_remove_spans",true)){content=content.replace(/<\/?span[^>]*>/gi,"")
}if(tinyMCE.getParam("paste_remove_styles",true)){content=content.replace(new RegExp('<(\\w[^>]*) style="([^"]*)"([^>]*)',"gi"),"<$1$3")
}content=content.replace(/<\/?font[^>]*>/gi,"");
switch(tinyMCE.getParam("paste_strip_class_attributes","all")){case"all":content=content.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi,"<$1$3");
break;
case"mso":content=content.replace(new RegExp('<(\\w[^>]*) class="?mso([^ |>]*)([^>]*)',"gi"),"<$1$3");
break
}content=content.replace(new RegExp('href="?'+TinyMCE_PastePlugin._reEscape(""+document.location)+"","gi"),'href="'+tinyMCE.settings.document_base_url);
content=content.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi,"<$1$3");
content=content.replace(/<\\?\?xml[^>]*>/gi,"");
content=content.replace(/<\/?\w+:[^>]*>/gi,"");
content=content.replace(/-- page break --\s*<p>&nbsp;<\/p>/gi,"");
content=content.replace(/-- page break --/gi,"");
if(!tinyMCE.settings.force_p_newlines){content=content.replace("","","gi");
content=content.replace("</p>","<br /><br />","gi")
}if(!tinyMCE.isMSIE&&!tinyMCE.settings.force_p_newlines){content=content.replace(/<\/?p[^>]*>/gi,"")
}content=content.replace(/<\/?div[^>]*>/gi,"");
if(tinyMCE.getParam("paste_convert_middot_lists",true)){var div=document.createElement("div");
div.innerHTML=content;
var className=tinyMCE.getParam("paste_unindented_list_class","unIndentedList");
while(TinyMCE_PastePlugin._convertMiddots(div,"--list--")){}while(TinyMCE_PastePlugin._convertMiddots(div,middot,className)){}while(TinyMCE_PastePlugin._convertMiddots(div,bull)){}content=div.innerHTML
}if(tinyMCE.getParam("paste_convert_headers_to_strong",false)){content=content.replace(/<h[1-6]>&nbsp;<\/h[1-6]>/gi,"<p>&nbsp;&nbsp;</p>");
content=content.replace(/<h[1-6]>/gi,"<p><b>");
content=content.replace(/<\/h[1-6]>/gi,"</b></p>");
content=content.replace(/<b>&nbsp;<\/b>/gi,"<b>&nbsp;&nbsp;</b>");
content=content.replace(/^(&nbsp;)*/gi,"")
}content=content.replace(/--list--/gi,"");
if((cb=tinyMCE.getParam("paste_insert_word_content_callback",""))!=""){content=eval(cb+"('after', content)")
}tinyMCE.execCommand("mceInsertContent",false,content);
window.setTimeout('tinyMCE.execCommand("mceCleanup");',1)
}},_reEscape:function(C){var A="?.\\*[](){}+^$:";
var D="";
for(var B=0;
B<C.length;
B++){var E=C.charAt(B);
if(A.indexOf(E)!=-1){D+="\\"+E
}else{D+=E
}}return D
},_convertMiddots:function(A,M,E){var I=String.fromCharCode(183);
var H=String.fromCharCode(8226);
var B=A.getElementsByTagName("p");
var D;
for(var F=0;
F<B.length;
F++){var C=B[F];
if(C.innerHTML.indexOf(M)==0){var G=document.createElement("ul");
if(E){G.className=E
}var L=document.createElement("li");
L.innerHTML=C.innerHTML.replace(new RegExp(""+I+"|"+H+"|--list--|&nbsp;","gi"),"");
G.appendChild(L);
var K=C.nextSibling;
while(K){if(K.nodeType==3&&new RegExp("^\\s$","m").test(K.nodeValue)){K=K.nextSibling;
continue
}if(M==I){if(K.nodeType==1&&new RegExp("^o(\\s+|&nbsp;)").test(K.innerHTML)){if(!D){D=G;
G=document.createElement("ul");
D.appendChild(G)
}K.innerHTML=K.innerHTML.replace(/^o/,"")
}else{if(D){G=D;
D=null
}if(K.nodeType!=1||K.innerHTML.indexOf(M)!=0){break
}}}else{if(K.nodeType!=1||K.innerHTML.indexOf(M)!=0){break
}}var J=K.nextSibling;
var L=document.createElement("li");
L.innerHTML=K.innerHTML.replace(new RegExp(""+I+"|"+H+"|--list--|&nbsp;","gi"),"");
K.parentNode.removeChild(K);
G.appendChild(L);
K=J
}C.parentNode.replaceChild(G,C);
return true
}}return false
},_clipboardHTML:function(){var div=document.getElementById("_TinyMCE_clipboardHTML");
if(!div){var div=document.createElement("DIV");
div.id="_TinyMCE_clipboardHTML";
with(div.style){visibility="hidden";
overflow="hidden";
position="absolute";
width=1;
height=1
}document.body.appendChild(div)
}div.innerHTML="";
var rng=document.body.createTextRange();
rng.moveToElementText(div);
rng.execCommand("Paste");
var html=div.innerHTML;
div.innerHTML="";
return html
}};
tinyMCE.addPlugin("paste",TinyMCE_PastePlugin);