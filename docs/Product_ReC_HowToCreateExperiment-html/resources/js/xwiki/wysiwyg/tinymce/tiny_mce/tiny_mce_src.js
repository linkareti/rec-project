function TinyMCE_Engine(){this.majorVersion="2";
this.minorVersion="0.6.1";
this.releaseDate="2006-05-04";
this.instances=new Array();
this.switchClassCache=new Array();
this.windowArgs=new Array();
this.loadedFiles=new Array();
this.pendingFiles=new Array();
this.loadingIndex=0;
this.configs=new Array();
this.currentConfig=0;
this.eventHandlers=new Array();
var A=navigator.userAgent;
this.isMSIE=(navigator.appName=="Microsoft Internet Explorer");
this.isMSIE5=this.isMSIE&&(A.indexOf("MSIE 5")!=-1);
this.isMSIE5_0=this.isMSIE&&(A.indexOf("MSIE 5.0")!=-1);
this.isGecko=A.indexOf("Gecko")!=-1;
this.isSafari=A.indexOf("Safari")!=-1;
this.isOpera=A.indexOf("Opera")!=-1;
this.isMac=A.indexOf("Mac")!=-1;
this.isNS7=A.indexOf("Netscape/7")!=-1;
this.isNS71=A.indexOf("Netscape/7.1")!=-1;
this.dialogCounter=0;
this.plugins=new Array();
this.themes=new Array();
this.menus=new Array();
this.loadedPlugins=new Array();
this.buttonMap=new Array();
this.isLoaded=false;
if(this.isOpera){this.isMSIE=true;
this.isGecko=false;
this.isSafari=false
}this.idCounter=0
}TinyMCE_Engine.prototype={init:function(E){var G;
this.settings=E;
if(typeof (document.execCommand)=="undefined"){return 
}if(!tinyMCE.baseURL){var B=document.getElementsByTagName("script");
for(var H=0;
H<B.length;
H++){if(B[H].src&&(B[H].src.indexOf("tiny_mce.js")!=-1||B[H].src.indexOf("tiny_mce_dev.js")!=-1||B[H].src.indexOf("tiny_mce_src.js")!=-1||B[H].src.indexOf("tiny_mce_gzip")!=-1)){var A=B[H].src;
tinyMCE.srcMode=(A.indexOf("_src")!=-1||A.indexOf("_dev")!=-1)?"_src":"";
tinyMCE.gzipMode=A.indexOf("_gzip")!=-1;
A=A.substring(0,A.lastIndexOf("/"));
if(E.exec_mode=="src"||E.exec_mode=="normal"){tinyMCE.srcMode=E.exec_mode=="src"?"_src":""
}tinyMCE.baseURL=A;
break
}}}this.documentBasePath=document.location.href;
if(this.documentBasePath.indexOf("?")!=-1){this.documentBasePath=this.documentBasePath.substring(0,this.documentBasePath.indexOf("?"))
}this.documentURL=this.documentBasePath;
this.documentBasePath=this.documentBasePath.substring(0,this.documentBasePath.lastIndexOf("/"));
if(tinyMCE.baseURL.indexOf("://")==-1&&tinyMCE.baseURL.charAt(0)!="/"){tinyMCE.baseURL=this.documentBasePath+"/"+tinyMCE.baseURL
}this._def("mode","none");
this._def("theme","advanced");
this._def("plugins","",true);
this._def("language","en");
this._def("docs_language",this.settings.language);
this._def("elements","");
this._def("textarea_trigger","mce_editable");
this._def("editor_selector","");
this._def("editor_deselector","mceNoEditor");
this._def("valid_elements","+a[id|style|rel|rev|charset|hreflang|dir|lang|tabindex|accesskey|type|name|href|target|title|class|onfocus|onblur|onclick|ondblclick|onmousedown|onmouseup|onmouseover|onmousemove|onmouseout|onkeypress|onkeydown|onkeyup],-strong/-b[class|style],-em/-i[class|style],-strike[class|style],-u[class|style],#p[id|style|dir|class|align],-ol[class|style],-ul[class|style],-li[class|style],br,img[id|dir|lang|longdesc|usemap|style|class|src|onmouseover|onmouseout|border|alt=|title|hspace|vspace|width|height|align],-sub[style|class],-sup[style|class],-blockquote[dir|style],-table[border=0|cellspacing|cellpadding|width|height|class|align|summary|style|dir|id|lang|bgcolor|background|bordercolor],-tr[id|lang|dir|class|rowspan|width|height|align|valign|style|bgcolor|background|bordercolor],tbody[id|class],thead[id|class],tfoot[id|class],-td[id|lang|dir|class|colspan|rowspan|width|height|align|valign|style|bgcolor|background|bordercolor|scope],-th[id|lang|dir|class|colspan|rowspan|width|height|align|valign|style|scope],caption[id|lang|dir|class|style],-div[id|dir|class|align|style],-span[style|class|align],-pre[class|align|style],address[class|align|style],-h1[id|style|dir|class|align],-h2[id|style|dir|class|align],-h3[id|style|dir|class|align],-h4[id|style|dir|class|align],-h5[id|style|dir|class|align],-h6[id|style|dir|class|align],hr[class|style],-font[face|size|style|id|class|dir|color],dd[id|class|title|style|dir|lang],dl[id|class|title|style|dir|lang],dt[id|class|title|style|dir|lang]");
this._def("extended_valid_elements","");
this._def("invalid_elements","");
this._def("encoding","");
this._def("urlconverter_callback",tinyMCE.getParam("urlconvertor_callback","TinyMCE_Engine.prototype.convertURL"));
this._def("save_callback","");
this._def("debug",false);
this._def("force_br_newlines",false);
this._def("force_p_newlines",true);
this._def("add_form_submit_trigger",true);
this._def("relative_urls",true);
this._def("remove_script_host",true);
this._def("focus_alert",true);
this._def("document_base_url",this.documentURL);
this._def("visual",true);
this._def("visual_table_class","mceVisualAid");
this._def("setupcontent_callback","");
this._def("fix_content_duplication",true);
this._def("custom_undo_redo",true);
this._def("custom_undo_redo_levels",-1);
this._def("custom_undo_redo_keyboard_shortcuts",true);
this._def("custom_undo_redo_restore_selection",true);
this._def("verify_html",true);
this._def("apply_source_formatting",false);
this._def("directionality","ltr");
this._def("cleanup_on_startup",false);
this._def("inline_styles",false);
this._def("convert_newlines_to_brs",false);
this._def("auto_reset_designmode",true);
this._def("entities","38,amp,34,quot,162,cent,8364,euro,163,pound,165,yen,169,copy,174,reg,8482,trade,8240,permil,181,micro,183,middot,8226,bull,8230,hellip,8242,prime,8243,Prime,167,sect,182,para,223,szlig,8249,lsaquo,8250,rsaquo,171,laquo,187,raquo,8216,lsquo,8217,rsquo,8220,ldquo,8221,rdquo,8218,sbquo,8222,bdquo,60,lt,62,gt,8804,le,8805,ge,8211,ndash,8212,mdash,175,macr,8254,oline,164,curren,166,brvbar,168,uml,161,iexcl,191,iquest,710,circ,732,tilde,176,deg,8722,minus,177,plusmn,247,divide,8260,frasl,215,times,185,sup1,178,sup2,179,sup3,188,frac14,189,frac12,190,frac34,402,fnof,8747,int,8721,sum,8734,infin,8730,radic,8764,sim,8773,cong,8776,asymp,8800,ne,8801,equiv,8712,isin,8713,notin,8715,ni,8719,prod,8743,and,8744,or,172,not,8745,cap,8746,cup,8706,part,8704,forall,8707,exist,8709,empty,8711,nabla,8727,lowast,8733,prop,8736,ang,180,acute,184,cedil,170,ordf,186,ordm,8224,dagger,8225,Dagger,192,Agrave,194,Acirc,195,Atilde,196,Auml,197,Aring,198,AElig,199,Ccedil,200,Egrave,202,Ecirc,203,Euml,204,Igrave,206,Icirc,207,Iuml,208,ETH,209,Ntilde,210,Ograve,212,Ocirc,213,Otilde,214,Ouml,216,Oslash,338,OElig,217,Ugrave,219,Ucirc,220,Uuml,376,Yuml,222,THORN,224,agrave,226,acirc,227,atilde,228,auml,229,aring,230,aelig,231,ccedil,232,egrave,234,ecirc,235,euml,236,igrave,238,icirc,239,iuml,240,eth,241,ntilde,242,ograve,244,ocirc,245,otilde,246,ouml,248,oslash,339,oelig,249,ugrave,251,ucirc,252,uuml,254,thorn,255,yuml,914,Beta,915,Gamma,916,Delta,917,Epsilon,918,Zeta,919,Eta,920,Theta,921,Iota,922,Kappa,923,Lambda,924,Mu,925,Nu,926,Xi,927,Omicron,928,Pi,929,Rho,931,Sigma,932,Tau,933,Upsilon,934,Phi,935,Chi,936,Psi,937,Omega,945,alpha,946,beta,947,gamma,948,delta,949,epsilon,950,zeta,951,eta,952,theta,953,iota,954,kappa,955,lambda,956,mu,957,nu,958,xi,959,omicron,960,pi,961,rho,962,sigmaf,963,sigma,964,tau,965,upsilon,966,phi,967,chi,968,psi,969,omega,8501,alefsym,982,piv,8476,real,977,thetasym,978,upsih,8472,weierp,8465,image,8592,larr,8593,uarr,8594,rarr,8595,darr,8596,harr,8629,crarr,8656,lArr,8657,uArr,8658,rArr,8659,dArr,8660,hArr,8756,there4,8834,sub,8835,sup,8836,nsub,8838,sube,8839,supe,8853,oplus,8855,otimes,8869,perp,8901,sdot,8968,lceil,8969,rceil,8970,lfloor,8971,rfloor,9001,lang,9002,rang,9674,loz,9824,spades,9827,clubs,9829,hearts,9830,diams,8194,ensp,8195,emsp,8201,thinsp,8204,zwnj,8205,zwj,8206,lrm,8207,rlm,173,shy,233,eacute,237,iacute,243,oacute,250,uacute,193,Aacute,225,aacute,201,Eacute,205,Iacute,211,Oacute,218,Uacute,221,Yacute,253,yacute",true);
this._def("cleanup_callback","");
this._def("add_unload_trigger",true);
this._def("ask",false);
this._def("nowrap",false);
this._def("auto_resize",false);
this._def("auto_focus",false);
this._def("cleanup",true);
this._def("remove_linebreaks",true);
this._def("button_tile_map",false);
this._def("submit_patch",true);
this._def("browsers","msie, safari, gecko,opera",true);
this._def("dialog_type","window");
this._def("accessibility_warnings",true);
this._def("accessibility_focus",true);
this._def("merge_styles_invalid_parents","");
this._def("force_hex_style_colors",true);
this._def("trim_span_elements",true);
this._def("convert_fonts_to_spans",true);
this._def("doctype",'<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">');
this._def("font_size_classes","");
this._def("font_size_style_values","8px,10px,12px,14px,18px,24px,36px",true);
this._def("event_elements","a,img",true);
this._def("convert_urls",true);
this._def("table_inline_editing",false);
this._def("object_resizing",true);
this._def("custom_shortcuts",true);
this._def("convert_on_click",false);
this._def("content_css","");
this._def("fix_list_elements",false);
this._def("fix_table_elements",false);
this._def("strict_loading_mode",document.contentType=="application/xhtml+xml");
this._def("hidden_tab_class","");
this._def("display_tab_class","");
if(this.isMSIE&&!this.isOpera){this.settings.strict_loading_mode=false
}if(this.isMSIE&&this.settings.browsers.indexOf("msie")==-1){return 
}if(this.isGecko&&this.settings.browsers.indexOf("gecko")==-1){return 
}if(this.isSafari&&this.settings.browsers.indexOf("safari")==-1){return 
}if(this.isOpera&&this.settings.browsers.indexOf("opera")==-1){return 
}var K=tinyMCE.settings.document_base_url;
var I=document.location.href;
var C=I.indexOf("://");
if(C>0&&document.location.protocol!="file:"){C=I.indexOf("/",C+3);
I=I.substring(0,C);
if(K.indexOf("://")==-1){K=I+K
}tinyMCE.settings.document_base_url=K;
tinyMCE.settings.document_base_prefix=I
}if(K.indexOf("?")!=-1){K=K.substring(0,K.indexOf("?"))
}this.settings.base_href=K.substring(0,K.lastIndexOf("/"))+"/";
G=this.settings.theme;
this.blockRegExp=new RegExp("^(h[1-6]|p|div|address|pre|form|table|li|ol|ul|td|blockquote|center|dl|dt|dd|dir|fieldset|form|noscript|noframes|menu|isindex|samp)$","i");
this.posKeyCodes=new Array(13,45,36,35,33,34,37,38,39,40);
this.uniqueURL="javascript:TINYMCE_UNIQUEURL();";
this.uniqueTag='<div id="mceTMPElement" style="display: none">TMP</div>';
this.callbacks=new Array("onInit","getInfo","getEditorTemplate","setupContent","onChange","onPageLoad","handleNodeChange","initInstance","execCommand","getControlHTML","handleEvent","cleanup");
this.settings.theme_href=tinyMCE.baseURL+"/themes/"+G;
if(!tinyMCE.isMSIE){this.settings.force_br_newlines=false
}if(tinyMCE.getParam("popups_css",false)){var F=tinyMCE.getParam("popups_css","");
if(F.indexOf("://")==-1&&F.charAt(0)!="/"){this.settings.popups_css=this.documentBasePath+"/"+F
}else{this.settings.popups_css=F
}}else{this.settings.popups_css=tinyMCE.baseURL+"/themes/"+G+"/css/editor_popup.css"
}if(tinyMCE.getParam("editor_css",false)){var F=tinyMCE.getParam("editor_css","");
if(F.indexOf("://")==-1&&F.charAt(0)!="/"){this.settings.editor_css=this.documentBasePath+"/"+F
}else{this.settings.editor_css=F
}}else{this.settings.editor_css=tinyMCE.baseURL+"/themes/"+G+"/css/editor_ui.css"
}if(tinyMCE.settings.debug){var D="Debug: \n";
D+="baseURL: "+this.baseURL+"\n";
D+="documentBasePath: "+this.documentBasePath+"\n";
D+="content_css: "+this.settings.content_css+"\n";
D+="popups_css: "+this.settings.popups_css+"\n";
D+="editor_css: "+this.settings.editor_css+"\n";
alert(D)
}if(this.configs.length==0){if(this.isSafari&&this.getParam("safari_warning",false)){alert("Safari support is very limited and should be considered experimental.\nSo there is no need to even submit bugreports on this early version.\nYou can disable this message by setting: safari_warning option to false")
}if(typeof (TinyMCECompressed)=="undefined"){tinyMCE.addEvent(window,"DOMContentLoaded",TinyMCE_Engine.prototype.onLoad);
if(tinyMCE.isMSIE&&!tinyMCE.isOpera){if(document.body){tinyMCE.addEvent(document.body,"readystatechange",TinyMCE_Engine.prototype.onLoad)
}else{tinyMCE.addEvent(document,"readystatechange",TinyMCE_Engine.prototype.onLoad)
}}tinyMCE.addEvent(window,"load",TinyMCE_Engine.prototype.onLoad);
tinyMCE._addUnloadEvents()
}}this.loadScript(tinyMCE.baseURL+"/themes/"+this.settings.theme+"/editor_template"+tinyMCE.srcMode+".js");
this.loadScript(tinyMCE.baseURL+"/langs/"+this.settings.language+".js");
this.loadCSS(this.settings.editor_css);
var C=tinyMCE.getParam("plugins","",true,",");
if(C.length>0){for(var H=0;
H<C.length;
H++){if(C[H].charAt(0)!="-"){this.loadScript(tinyMCE.baseURL+"/plugins/"+C[H]+"/editor_plugin"+tinyMCE.srcMode+".js")
}}}E.cleanup_entities=new Array();
var J=tinyMCE.getParam("entities","",true,",");
for(var H=0;
H<J.length;
H+=2){E.cleanup_entities["c"+J[H]]=J[H+1]
}E.index=this.configs.length;
this.configs[this.configs.length]=E;
this.loadNextScript()
},_addUnloadEvents:function(){if(tinyMCE.isMSIE){if(tinyMCE.settings.add_unload_trigger){tinyMCE.addEvent(window,"unload",TinyMCE_Engine.prototype.unloadHandler);
tinyMCE.addEvent(window.document,"beforeunload",TinyMCE_Engine.prototype.unloadHandler)
}}else{if(tinyMCE.settings.add_unload_trigger){tinyMCE.addEvent(window,"unload",function(){tinyMCE.triggerSave(true,true)
})
}}},_def:function(D,B,C){var A=tinyMCE.getParam(D,B);
A=C?A.replace(/\s+/g,""):A;
this.settings[D]=A
},hasPlugin:function(A){return typeof (this.plugins[A])!="undefined"&&this.plugins[A]!=null
},addPlugin:function(C,A){var B=this.plugins[C];
A.baseURL=B?B.baseURL:tinyMCE.baseURL+"/plugins/"+C;
this.plugins[C]=A;
this.loadNextScript()
},setPluginBaseURL:function(C,A){var B=this.plugins[C];
if(B){B.baseURL=A
}else{this.plugins[C]={baseURL:A}
}},loadPlugin:function(B,A){A=A.indexOf(".js")!=-1?A.substring(0,A.lastIndexOf("/")):A;
A=A.charAt(A.length-1)=="/"?A.substring(0,A.length-1):A;
this.plugins[B]={baseURL:A};
this.loadScript(A+"/editor_plugin"+(tinyMCE.srcMode?"_src":"")+".js")
},hasTheme:function(A){return typeof (this.themes[A])!="undefined"&&this.themes[A]!=null
},addTheme:function(B,A){this.themes[B]=A;
this.loadNextScript()
},addMenu:function(B,A){this.menus[B]=A
},hasMenu:function(A){return typeof (this.plugins[A])!="undefined"&&this.plugins[A]!=null
},loadScript:function(A){var B;
for(B=0;
B<this.loadedFiles.length;
B++){if(this.loadedFiles[B]==A){return 
}}if(tinyMCE.settings.strict_loading_mode){this.pendingFiles[this.pendingFiles.length]=A
}else{document.write('<script language="javascript" type="text/javascript" src="'+A+'"><\/script>')
}this.loadedFiles[this.loadedFiles.length]=A
},loadNextScript:function(){var B=document,A;
if(!tinyMCE.settings.strict_loading_mode){return 
}if(this.loadingIndex<this.pendingFiles.length){A=B.createElementNS("http://www.w3.org/1999/xhtml","script");
A.setAttribute("language","javascript");
A.setAttribute("type","text/javascript");
A.setAttribute("src",this.pendingFiles[this.loadingIndex++]);
B.getElementsByTagName("head")[0].appendChild(A)
}else{this.loadingIndex=-1
}},loadCSS:function(C){var D=C.replace(/\s+/,"").split(",");
var F=0,I=0;
var H=false;
var G=0,E=0,B,A;
for(G=0,I=D.length;
G<I;
G++){ignore_css=false;
if(D[G]!=null&&D[G]!="null"&&D[G].length>0){for(E=0,F=this.loadedFiles.length;
E<F;
E++){if(this.loadedFiles[E]==D[G]){H=true;
break
}}if(!H){if(tinyMCE.settings.strict_loading_mode){B=document.getElementsByTagName("head");
A=document.createElement("link");
A.setAttribute("href",D[G]);
A.setAttribute("rel","stylesheet");
A.setAttribute("type","text/css");
B[0].appendChild(A)
}else{document.write('<link href="'+D[G]+'" rel="stylesheet" type="text/css" />')
}this.loadedFiles[this.loadedFiles.length]=D[G]
}}}},importCSS:function(G,E){var D=E.replace(/\s+/,"").split(",");
var F,H,B,A,C;
for(A=0,F=D.length;
A<F;
A++){C=D[A];
if(C!=null&&C!="null"&&C.length>0){if(C.indexOf("://")==-1&&C.charAt(0)!="/"){C=this.documentBasePath+"/"+C
}if(typeof (G.createStyleSheet)=="undefined"){H=G.createElement("link");
H.rel="stylesheet";
H.href=C;
if((B=G.getElementsByTagName("head"))!=null&&B.length>0){B[0].appendChild(H)
}}else{G.createStyleSheet(C)
}}}},confirmAdd:function(C,B){var D=tinyMCE.isMSIE?event.srcElement:C.target;
var A=D.name?D.name:D.id;
tinyMCE.settings=B;
if(tinyMCE.settings.convert_on_click||(!D.getAttribute("mce_noask")&&confirm(tinyMCELang.lang_edit_confirm))){tinyMCE.addMCEControl(D,A)
}D.setAttribute("mce_noask","true")
},updateContent:function(A){var B=document.getElementById(A);
for(var E in tinyMCE.instances){var C=tinyMCE.instances[E];
if(!tinyMCE.isInstance(C)){continue
}C.switchSettings();
if(C.formElement==B){var D=C.getDoc();
tinyMCE._setHTML(D,C.formElement.value);
if(!tinyMCE.isMSIE){D.body.innerHTML=tinyMCE._cleanupHTML(C,D,this.settings,D.body,C.visualAid)
}}}},addMCEControl:function(D,A,B){var E="mce_editor_"+tinyMCE.idCounter++;
var C=new TinyMCE_Control(tinyMCE.settings);
C.editorId=E;
this.instances[E]=C;
C._onAdd(D,A,B)
},removeMCEControl:function(G){var D=tinyMCE.getInstanceById(G);
if(D){D.switchSettings();
G=D.editorId;
var E=tinyMCE.getContent(G);
var A=new Array();
for(var H in tinyMCE.instances){var I=tinyMCE.instances[H];
if(!tinyMCE.isInstance(I)){continue
}if(H!=G){A[H]=I
}}tinyMCE.instances=A;
tinyMCE.selectedElement=null;
tinyMCE.selectedInstance=null;
var B=document.getElementById(G+"_parent");
var F=D.oldTargetElement;
var C=F.nodeName.toLowerCase();
if(C=="textarea"||C=="input"){B.parentNode.removeChild(B);
F.style.display="inline";
F.value=E
}else{F.innerHTML=E;
F.style.display="block";
B.parentNode.insertBefore(F,B);
B.parentNode.removeChild(B)
}}},triggerSave:function(B,A){var C,D;
if(typeof (B)=="undefined"){B=false
}if(typeof (A)=="undefined"){A=false
}for(D in tinyMCE.instances){C=tinyMCE.instances[D];
if(!tinyMCE.isInstance(C)){continue
}C.triggerSave(B,A)
}},resetForm:function(D){var B,C,E,A=document.forms[D];
for(E in tinyMCE.instances){C=tinyMCE.instances[E];
if(!tinyMCE.isInstance(C)){continue
}C.switchSettings();
for(B=0;
B<A.elements.length;
B++){if(C.formTargetElementId==A.elements[B].name){C.getBody().innerHTML=C.startContent
}}}},execInstanceCommand:function(E,D,F,C,A){var B=tinyMCE.getInstanceById(E);
if(B){if(typeof (A)=="undefined"){A=true
}if(A){B.contentWindow.focus()
}B.autoResetDesignMode();
this.selectedElement=B.getFocusElement();
this.selectedInstance=B;
tinyMCE.execCommand(D,F,C);
if(tinyMCE.isMSIE&&window.event!=null){tinyMCE.cancelEvent(window.event)
}}},execCommand:function(D,F,B){F=F?F:false;
B=B?B:null;
if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.switchSettings()
}switch(D){case"mceHelp":tinyMCE.openWindow({file:"about.htm",width:480,height:380},{tinymce_version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion,tinymce_releasedate:tinyMCE.releaseDate,inline:"yes"});
return ;
case"mceFocus":var A=tinyMCE.getInstanceById(B);
if(A){A.contentWindow.focus()
}return ;
case"mceAddControl":case"mceAddEditor":tinyMCE.addMCEControl(tinyMCE._getElementById(B),B);
return ;
case"mceAddFrameControl":tinyMCE.addMCEControl(tinyMCE._getElementById(B.element,B.document),B.element,B.document);
return ;
case"mceRemoveControl":case"mceRemoveEditor":tinyMCE.removeMCEControl(B);
return ;
case"mceResetDesignMode":if(!tinyMCE.isMSIE){for(var E in tinyMCE.instances){if(!tinyMCE.isInstance(tinyMCE.instances[E])){continue
}try{tinyMCE.instances[E].getDoc().designMode="on"
}catch(C){}}}return 
}if(this.selectedInstance){this.selectedInstance.execCommand(D,F,B)
}else{if(tinyMCE.settings.focus_alert){alert(tinyMCELang.lang_focus_alert)
}}},_createIFrame:function(F,E,D){var B,G=F.getAttribute("id");
var C,A;
if(typeof (E)=="undefined"){E=document
}if(typeof (D)=="undefined"){D=window
}B=E.createElement("iframe");
C=""+tinyMCE.settings.area_width;
A=""+tinyMCE.settings.area_height;
if(C.indexOf("%")==-1){C=parseInt(C);
C=C<0?300:C;
C=C+"px"
}if(A.indexOf("%")==-1){A=parseInt(A);
A=A<0?245:A;
A=A+"px"
}B.setAttribute("id",G);
B.setAttribute("class","mceEditorIframe");
B.setAttribute("border","0");
B.setAttribute("frameBorder","0");
B.setAttribute("marginWidth","0");
B.setAttribute("marginHeight","0");
B.setAttribute("leftMargin","0");
B.setAttribute("topMargin","0");
B.setAttribute("width",C);
B.setAttribute("height",A);
B.setAttribute("allowtransparency","true");
B.className="mceEditorIframe";
if(tinyMCE.settings.auto_resize){B.setAttribute("scrolling","no")
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera){B.setAttribute("src",this.settings.default_document)
}B.style.width=C;
B.style.height=A;
if(tinyMCE.settings.strict_loading_mode){B.style.marginBottom="-5px"
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera){F.outerHTML=B.outerHTML
}else{F.parentNode.replaceChild(B,F)
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera){return D.frames[G]
}else{return B
}},setupContent:function(editor_id){var inst=tinyMCE.instances[editor_id];
var doc=inst.getDoc();
var head=doc.getElementsByTagName("head").item(0);
var content=inst.startContent;
if(tinyMCE.settings.strict_loading_mode){content=content.replace(/&lt;/g,"<");
content=content.replace(/&gt;/g,">");
content=content.replace(/&quot;/g,'"');
content=content.replace(/&amp;/g,"&")
}inst.switchSettings();
if(!tinyMCE.isMSIE&&tinyMCE.getParam("setupcontent_reload",false)&&doc.title!="blank_page"){try{doc.location.href=tinyMCE.baseURL+"/blank.htm"
}catch(ex){}window.setTimeout("tinyMCE.setupContent('"+editor_id+"');",1000);
return 
}if(!head){window.setTimeout("tinyMCE.setupContent('"+editor_id+"');",10);
return 
}tinyMCE.importCSS(inst.getDoc(),tinyMCE.baseURL+"/themes/"+inst.settings.theme+"/css/editor_content.css");
tinyMCE.importCSS(inst.getDoc(),inst.settings.content_css);
tinyMCE.dispatchCallback(inst,"init_instance_callback","initInstance",inst);
if(tinyMCE.getParam("custom_undo_redo_keyboard_shortcuts")){inst.addShortcut("ctrl","z","lang_undo_desc","Undo");
inst.addShortcut("ctrl","y","lang_redo_desc","Redo")
}if(tinyMCE.isGecko){inst.addShortcut("ctrl","b","lang_bold_desc","Bold");
inst.addShortcut("ctrl","i","lang_italic_desc","Italic");
inst.addShortcut("ctrl","u","lang_underline_desc","Underline")
}if(tinyMCE.getParam("convert_fonts_to_spans")){inst.getDoc().body.setAttribute("id","mceSpanFonts")
}if(tinyMCE.settings.nowrap){doc.body.style.whiteSpace="nowrap"
}doc.body.dir=this.settings.directionality;
doc.editorId=editor_id;
if(!tinyMCE.isMSIE){doc.documentElement.editorId=editor_id
}inst.setBaseHREF(tinyMCE.settings.base_href);
if(tinyMCE.settings.convert_newlines_to_brs){content=tinyMCE.regexpReplace(content,"\r\n","<br />","gi");
content=tinyMCE.regexpReplace(content,"\r","<br />","gi");
content=tinyMCE.regexpReplace(content,"\n","<br />","gi")
}content=tinyMCE.storeAwayURLs(content);
content=tinyMCE._customCleanup(inst,"insert_to_editor",content);
if(tinyMCE.isMSIE){window.setInterval('try{tinyMCE.getCSSClasses(tinyMCE.instances["'+editor_id+'"].getDoc(), "'+editor_id+'");}catch(e){}',500);
if(tinyMCE.settings.force_br_newlines){doc.styleSheets[0].addRule("p","margin: 0;")
}var body=inst.getBody();
body.editorId=editor_id
}content=tinyMCE.cleanupHTMLCode(content);
if(!tinyMCE.isMSIE){var contentElement=inst.getDoc().createElement("body");
var doc=inst.getDoc();
contentElement.innerHTML=content;
if(tinyMCE.isGecko&&tinyMCE.settings.remove_lt_gt){content=content.replace(new RegExp("&lt;&gt;","g"),"")
}if(tinyMCE.settings.cleanup_on_startup){tinyMCE.setInnerHTML(inst.getBody(),tinyMCE._cleanupHTML(inst,doc,this.settings,contentElement))
}else{content=tinyMCE.regexpReplace(content,"<strong","<b","gi");
content=tinyMCE.regexpReplace(content,"<em(/?)>","<i$1>","gi");
content=tinyMCE.regexpReplace(content,"<em ","<i ","gi");
content=tinyMCE.regexpReplace(content,"</strong>","</b>","gi");
content=tinyMCE.regexpReplace(content,"</em>","</i>","gi");
tinyMCE.setInnerHTML(inst.getBody(),content)
}tinyMCE.convertAllRelativeURLs(inst.getBody())
}else{if(tinyMCE.settings.cleanup_on_startup){tinyMCE._setHTML(inst.getDoc(),content);
eval("try {tinyMCE.setInnerHTML(inst.getBody(), tinyMCE._cleanupHTML(inst, inst.contentDocument, this.settings, inst.getBody()));} catch(e) {}")
}else{tinyMCE._setHTML(inst.getDoc(),content)
}}var parentElm=inst.targetDoc.getElementById(inst.editorId+"_parent");
inst.formElement=tinyMCE.isGecko?parentElm.previousSibling:parentElm.nextSibling;
tinyMCE.handleVisualAid(inst.getBody(),true,tinyMCE.settings.visual,inst);
tinyMCE.dispatchCallback(inst,"setupcontent_callback","setupContent",editor_id,inst.getBody(),inst.getDoc());
if(!tinyMCE.isMSIE){tinyMCE.addEventHandlers(inst)
}if(tinyMCE.isMSIE){tinyMCE.addEvent(inst.getBody(),"blur",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(inst.getBody(),"beforedeactivate",TinyMCE_Engine.prototype._eventPatch);
if(!tinyMCE.isOpera){tinyMCE.addEvent(doc.body,"mousemove",TinyMCE_Engine.prototype.onMouseMove);
tinyMCE.addEvent(doc.body,"beforepaste",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(doc.body,"drop",TinyMCE_Engine.prototype._eventPatch)
}}tinyMCE.selectedInstance=inst;
tinyMCE.selectedElement=inst.contentWindow.document.body;
tinyMCE._customCleanup(inst,"insert_to_editor_dom",inst.getBody());
tinyMCE._customCleanup(inst,"setup_content_dom",inst.getBody());
tinyMCE._setEventsEnabled(inst.getBody(),false);
tinyMCE.cleanupAnchors(inst.getDoc());
if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertSpansToFonts(inst.getDoc())
}inst.startContent=tinyMCE.trim(inst.getBody().innerHTML);
inst.undoRedo.add({content:inst.startContent});
if(tinyMCE.isGecko){tinyMCE.selectNodes(inst.getBody(),function(n){if(n.nodeType==3||n.nodeType==8){n.nodeValue=n.nodeValue.replace(new RegExp('\\smce_src="[^"]*"',"gi"),"");
n.nodeValue=n.nodeValue.replace(new RegExp('\\smce_href="[^"]*"',"gi"),"")
}return false
})
}tinyMCE._removeInternal(inst.getBody());
tinyMCE.selectedInstance=inst;
tinyMCE.triggerNodeChange(false,true)
},storeAwayURLs:function(A){if(!A.match(/(mce_src|mce_href)/gi,A)){A=A.replace(new RegExp('src\\s*=\\s*"([^ >"]*)"',"gi"),'src="$1" mce_src="$1"');
A=A.replace(new RegExp('href\\s*=\\s*"([^ >"]*)"',"gi"),'href="$1" mce_href="$1"')
}return A
},_removeInternal:function(A){if(tinyMCE.isGecko){tinyMCE.selectNodes(A,function(B){if(B.nodeType==3||B.nodeType==8){B.nodeValue=B.nodeValue.replace(new RegExp('\\smce_src="[^"]*"',"gi"),"");
B.nodeValue=B.nodeValue.replace(new RegExp('\\smce_href="[^"]*"',"gi"),"")
}return false
})
}},removeTinyMCEFormElements:function(B){if(typeof (B)=="undefined"||B==null){return 
}if(B.nodeName!="FORM"){if(B.form){B=B.form
}else{B=tinyMCE.getParentElement(B,"form")
}}if(B==null){return 
}for(var C=0;
C<B.elements.length;
C++){var A=B.elements[C].name?B.elements[C].name:B.elements[C].id;
if(A.indexOf("mce_editor_")==0){B.elements[C].disabled=true
}}},handleEvent:function(H){var D=tinyMCE.selectedInstance;
if(typeof (tinyMCE)=="undefined"){return true
}if(tinyMCE.executeCallback(tinyMCE.selectedInstance,"handle_event_callback","handleEvent",H)){return false
}switch(H.type){case"beforedeactivate":case"blur":if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.execCommand("mceEndTyping")
}tinyMCE.hideMenus();
return ;
case"drop":case"beforepaste":if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.setBaseHREF(null)
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera){var O=tinyMCE.selectedInstance.iframeElement;
if(O.style.height.indexOf("%")!=-1){O._oldHeight=O.style.height;
O.style.height=O.clientHeight
}}window.setTimeout("tinyMCE.selectedInstance.setBaseHREF(tinyMCE.settings['base_href']);tinyMCE._resetIframeHeight();",1);
return ;
case"submit":tinyMCE.removeTinyMCEFormElements(tinyMCE.isMSIE?window.event.srcElement:H.target);
tinyMCE.triggerSave();
tinyMCE.isNotDirty=true;
return ;
case"reset":var E=tinyMCE.isMSIE?window.event.srcElement:H.target;
for(var C=0;
C<document.forms.length;
C++){if(document.forms[C]==E){window.setTimeout("tinyMCE.resetForm("+C+");",10)
}}return ;
case"keypress":if(D&&D.handleShortcut(H)){return false
}if(H.target.editorId){tinyMCE.selectedInstance=tinyMCE.instances[H.target.editorId]
}else{if(H.target.ownerDocument.editorId){tinyMCE.selectedInstance=tinyMCE.instances[H.target.ownerDocument.editorId]
}}if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.switchSettings()
}if(tinyMCE.isGecko&&tinyMCE.settings.force_p_newlines&&H.keyCode==13&&!H.shiftKey){if(TinyMCE_ForceParagraphs._insertPara(tinyMCE.selectedInstance,H)){tinyMCE.execCommand("mceAddUndoLevel");
tinyMCE.cancelEvent(H);
return false
}}if(tinyMCE.isGecko&&tinyMCE.settings.force_p_newlines&&(H.keyCode==8||H.keyCode==46)&&!H.shiftKey){if(TinyMCE_ForceParagraphs._handleBackSpace(tinyMCE.selectedInstance,H.type)){tinyMCE.execCommand("mceAddUndoLevel");
tinyMCE.cancelEvent(H);
return false
}}if(tinyMCE.isMSIE&&tinyMCE.settings.force_br_newlines&&H.keyCode==13){if(H.target.editorId){tinyMCE.selectedInstance=tinyMCE.instances[H.target.editorId]
}if(tinyMCE.selectedInstance){var B=tinyMCE.selectedInstance.getDoc().selection;
var A=B.createRange();
if(tinyMCE.getParentElement(A.parentElement(),"li")!=null){return false
}H.returnValue=false;
H.cancelBubble=true;
A.pasteHTML("<br />");
A.collapse(false);
A.select();
tinyMCE.execCommand("mceAddUndoLevel");
tinyMCE.triggerNodeChange(false);
return false
}}if(H.keyCode==8||H.keyCode==46){tinyMCE.selectedElement=H.target;
tinyMCE.linkElement=tinyMCE.getParentElement(H.target,"a");
tinyMCE.imgElement=tinyMCE.getParentElement(H.target,"img");
tinyMCE.triggerNodeChange(false)
}return false;
break;
case"keyup":case"keydown":tinyMCE.hideMenus();
tinyMCE.hasMouseMoved=false;
if(D&&D.handleShortcut(H)){return false
}if(H.target.editorId){tinyMCE.selectedInstance=tinyMCE.instances[H.target.editorId]
}else{return 
}if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.switchSettings()
}var D=tinyMCE.selectedInstance;
if(tinyMCE.isGecko&&tinyMCE.settings.force_p_newlines&&(H.keyCode==8||H.keyCode==46)&&!H.shiftKey){if(TinyMCE_ForceParagraphs._handleBackSpace(tinyMCE.selectedInstance,H.type)){tinyMCE.execCommand("mceAddUndoLevel");
H.preventDefault();
return false
}}tinyMCE.selectedElement=null;
tinyMCE.selectedNode=null;
var I=tinyMCE.selectedInstance.getFocusElement();
tinyMCE.linkElement=tinyMCE.getParentElement(I,"a");
tinyMCE.imgElement=tinyMCE.getParentElement(I,"img");
tinyMCE.selectedElement=I;
if(tinyMCE.isGecko&&H.type=="keyup"&&H.keyCode==9){tinyMCE.handleVisualAid(tinyMCE.selectedInstance.getBody(),true,tinyMCE.settings.visual,tinyMCE.selectedInstance)
}if(tinyMCE.isMSIE&&H.type=="keydown"&&H.keyCode==13){tinyMCE.enterKeyElement=tinyMCE.selectedInstance.getFocusElement()
}if(tinyMCE.isMSIE&&H.type=="keyup"&&H.keyCode==13){var I=tinyMCE.enterKeyElement;
if(I){var N=new RegExp("^HR|IMG|BR$","g");
var J=new RegExp("^H[1-6]$","g");
if(!I.hasChildNodes()&&!N.test(I.nodeName)){if(J.test(I.nodeName)){I.innerHTML="&nbsp;&nbsp;"
}else{I.innerHTML="&nbsp;"
}}}}var M=tinyMCE.posKeyCodes;
var G=false;
for(var C=0;
C<M.length;
C++){if(M[C]==H.keyCode){G=true;
break
}}if(tinyMCE.isMSIE&&tinyMCE.settings.custom_undo_redo){var M=new Array(8,46);
for(var C=0;
C<M.length;
C++){if(M[C]==H.keyCode){if(H.type=="keyup"){tinyMCE.triggerNodeChange(false)
}}}}if(H.keyCode==17){return true
}if(!G&&H.type=="keyup"){tinyMCE.execCommand("mceStartTyping")
}if(H.type=="keydown"&&(G||H.ctrlKey)&&D){D.undoBookmark=D.selection.getBookmark()
}if(H.type=="keyup"&&(G||H.ctrlKey)){tinyMCE.execCommand("mceEndTyping")
}if(G&&H.type=="keyup"){tinyMCE.triggerNodeChange(false)
}if(tinyMCE.isMSIE&&H.ctrlKey){window.setTimeout("tinyMCE.triggerNodeChange(false);",1)
}break;
case"mousedown":case"mouseup":case"click":case"focus":tinyMCE.hideMenus();
if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.switchSettings();
tinyMCE.selectedInstance.isFocused=true
}var L=tinyMCE.getParentElement(H.target,"body");
for(var K in tinyMCE.instances){if(!tinyMCE.isInstance(tinyMCE.instances[K])){continue
}var D=tinyMCE.instances[K];
D.autoResetDesignMode();
if(D.getBody()==L){tinyMCE.selectedInstance=D;
tinyMCE.selectedElement=H.target;
tinyMCE.linkElement=tinyMCE.getParentElement(tinyMCE.selectedElement,"a");
tinyMCE.imgElement=tinyMCE.getParentElement(tinyMCE.selectedElement,"img");
break
}}if(!tinyMCE.selectedInstance.undoRedo.undoLevels[0].bookmark){tinyMCE.selectedInstance.undoRedo.undoLevels[0].bookmark=tinyMCE.selectedInstance.selection.getBookmark()
}if(tinyMCE.isSafari){tinyMCE.selectedInstance.lastSafariSelection=tinyMCE.selectedInstance.selection.getBookmark();
tinyMCE.selectedInstance.lastSafariSelectedElement=tinyMCE.selectedElement;
var F=tinyMCE.getParentElement(tinyMCE.selectedElement,"a");
if(F&&H.type=="mousedown"){F.setAttribute("mce_real_href",F.getAttribute("href"));
F.setAttribute("href","javascript:void(0);")
}if(F&&H.type=="click"){window.setTimeout(function(){F.setAttribute("href",F.getAttribute("mce_real_href"));
F.removeAttribute("mce_real_href")
},10)
}}if(H.type!="focus"){tinyMCE.selectedNode=null
}tinyMCE.triggerNodeChange(false);
tinyMCE.execCommand("mceEndTyping");
if(H.type=="mouseup"){tinyMCE.execCommand("mceAddUndoLevel")
}if(!tinyMCE.selectedInstance&&H.target.editorId){tinyMCE.selectedInstance=tinyMCE.instances[H.target.editorId]
}return false;
break
}},getButtonHTML:function(A,B,F,D,H,C){var G="",E,I;
D="tinyMCE.execInstanceCommand('{$editor_id}','"+D+"'";
if(typeof (H)!="undefined"&&H!=null){D+=","+H
}if(typeof (C)!="undefined"&&C!=null){D+=",'"+C+"'"
}D+=");";
if(tinyMCE.getParam("button_tile_map")&&(!tinyMCE.isMSIE||tinyMCE.isOpera)&&(E=this.buttonMap[A])!=null&&(tinyMCE.getParam("language")=="en"||F.indexOf("$lang")==-1)){I=0-(E*20)==0?"0":0-(E*20);
G+='<a id="{$editor_id}_'+A+'" href="javascript:'+D+'" onclick="'+D+'return false;" onmousedown="return false;" class="mceTiledButton mceButtonNormal" target="_self">';
G+='<img src="{$themeurl}/images/spacer.gif" style="background-position: '+I+'px 0" title="{$'+B+'}" />';
G+="</a>"
}else{G+='<a id="{$editor_id}_'+A+'" href="javascript:'+D+'" onclick="'+D+'return false;" onmousedown="return false;" class="mceButtonNormal" target="_self">';
G+='<img src="'+F+'" title="{$'+B+'}" />';
G+="</a>"
}return G
},addButtonMap:function(A){var C,B=A.replace(/\s+/,"").split(",");
for(C=0;
C<B.length;
C++){this.buttonMap[B[C]]=C
}},submitPatch:function(){tinyMCE.removeTinyMCEFormElements(this);
tinyMCE.triggerSave();
this.mceOldSubmit();
tinyMCE.isNotDirty=true
},onLoad:function(){if(tinyMCE.settings.strict_loading_mode&&this.loadingIndex!=-1){window.setTimeout("tinyMCE.onLoad();",1);
return 
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera&&window.event.type=="readystatechange"&&document.readyState!="complete"){return true
}if(tinyMCE.isLoaded){return true
}tinyMCE.isLoaded=true;
tinyMCE.dispatchCallback(null,"onpageload","onPageLoad");
for(var N=0;
N<tinyMCE.configs.length;
N++){tinyMCE.settings=tinyMCE.configs[N];
var G=tinyMCE.getParam("editor_selector");
var C=tinyMCE.getParam("editor_deselector");
var O=new Array();
if(document.forms&&tinyMCE.settings.add_form_submit_trigger&&!tinyMCE.submitTriggers){for(var J=0;
J<document.forms.length;
J++){var B=document.forms[J];
tinyMCE.addEvent(B,"submit",TinyMCE_Engine.prototype.handleEvent);
tinyMCE.addEvent(B,"reset",TinyMCE_Engine.prototype.handleEvent);
tinyMCE.submitTriggers=true;
if(tinyMCE.settings.submit_patch){try{B.mceOldSubmit=B.submit;
B.submit=TinyMCE_Engine.prototype.submitPatch
}catch(L){}}}}var K=tinyMCE.settings.mode;
switch(K){case"exact":var A=tinyMCE.getParam("elements","",true,",");
for(var J=0;
J<A.length;
J++){var I=tinyMCE._getElementById(A[J]);
var D=I?I.getAttribute(tinyMCE.settings.textarea_trigger):"";
if(tinyMCE.getAttrib(I,"class").indexOf(C)!=-1){continue
}if(D=="false"){continue
}if((tinyMCE.settings.ask||tinyMCE.settings.convert_on_click)&&I){O[O.length]=I;
continue
}if(I){tinyMCE.addMCEControl(I,A[J])
}else{if(tinyMCE.settings.debug){alert("Error: Could not find element by id or name: "+A[J])
}}}break;
case"specific_textareas":case"textareas":var F=document.getElementsByTagName("textarea");
for(var J=0;
J<F.length;
J++){var M=F.item(J);
var D=M.getAttribute(tinyMCE.settings.textarea_trigger);
if(G!=""&&tinyMCE.getAttrib(M,"class").indexOf(G)==-1){continue
}if(G!=""){D=G!=""?"true":""
}if(tinyMCE.getAttrib(M,"class").indexOf(C)!=-1){continue
}if((K=="specific_textareas"&&D=="true")||(K=="textareas"&&D!="false")){O[O.length]=M
}}break
}for(var J=0;
J<O.length;
J++){var I=O[J];
var H=I.name?I.name:I.id;
if(tinyMCE.settings.ask||tinyMCE.settings.convert_on_click){if(tinyMCE.isGecko){var E=tinyMCE.settings;
tinyMCE.addEvent(I,"focus",function(P){window.setTimeout(function(){TinyMCE_Engine.prototype.confirmAdd(P,E)
},10)
});
if(I.nodeName!="TEXTAREA"&&I.nodeName!="INPUT"){tinyMCE.addEvent(I,"click",function(P){window.setTimeout(function(){TinyMCE_Engine.prototype.confirmAdd(P,E)
},10)
})
}}else{var E=tinyMCE.settings;
tinyMCE.addEvent(I,"focus",function(){TinyMCE_Engine.prototype.confirmAdd(null,E)
});
tinyMCE.addEvent(I,"click",function(){TinyMCE_Engine.prototype.confirmAdd(null,E)
})
}}else{tinyMCE.addMCEControl(I,H)
}}if(tinyMCE.settings.auto_focus){window.setTimeout(function(){var P=tinyMCE.getInstanceById(tinyMCE.settings.auto_focus);
P.selection.selectNode(P.getBody(),true,true);
P.contentWindow.focus()
},10)
}tinyMCE.dispatchCallback(null,"oninit","onInit")
}},isInstance:function(A){return A!=null&&typeof (A)=="object"&&A.isTinyMCE_Control
},getParam:function(C,A,G,B){var F=(typeof (this.settings[C])=="undefined")?A:this.settings[C];
if(F=="true"||F=="false"){return(F=="true")
}if(G){F=tinyMCE.regexpReplace(F,"[ \t\r\n]","")
}if(typeof (B)!="undefined"&&B!=null){F=F.split(B);
var E=new Array();
for(var D=0;
D<F.length;
D++){if(F[D]&&F[D]!=""){E[E.length]=F[D]
}}F=E
}return F
},getLang:function(D,A,C,E){var B=(typeof (tinyMCELang[D])=="undefined")?A:tinyMCELang[D],F;
if(C){B=tinyMCE.entityDecode(B)
}if(E){for(F in E){B=this.replaceVar(B,F,E[F])
}}return B
},entityDecode:function(A){var B=document.createElement("div");
B.innerHTML=A;
return B.innerHTML
},addToLang:function(C,A){for(var B in A){if(typeof (A[B])=="function"){continue
}tinyMCELang[(B.indexOf("lang_")==-1?"lang_":"")+(C!=""?(C+"_"):"")+B]=A[B]
}this.loadNextScript()
},triggerNodeChange:function(J,H){if(tinyMCE.selectedInstance){var C=tinyMCE.selectedInstance;
var B=C.editorId;
var F=(typeof (H)!="undefined"&&H)?tinyMCE.selectedElement:C.getFocusElement();
var D=-1;
var I=-1;
var E=false;
var A=C.selection.getSelectedText();
if(H&&tinyMCE.isGecko&&C.isHidden()){F=C.getBody()
}C.switchSettings();
if(tinyMCE.settings.auto_resize){var G=C.getDoc();
C.iframeElement.style.width=G.body.offsetWidth+"px";
C.iframeElement.style.height=G.body.offsetHeight+"px"
}if(tinyMCE.selectedElement){E=(tinyMCE.selectedElement.nodeName.toLowerCase()=="img")||(A&&A.length>0)
}if(tinyMCE.settings.custom_undo_redo){D=C.undoRedo.undoIndex;
I=C.undoRedo.undoLevels.length
}tinyMCE.dispatchCallback(C,"handle_node_change_callback","handleNodeChange",B,F,D,I,C.visualAid,E,H)
}if(this.selectedInstance&&(typeof (J)=="undefined"||J)){this.selectedInstance.contentWindow.focus()
}},_customCleanup:function(inst,type,content){var pl,po,i;
var customCleanup=tinyMCE.settings.cleanup_callback;
if(customCleanup!=""&&eval("typeof("+customCleanup+")")!="undefined"){content=eval(customCleanup+"(type, content, inst);")
}pl=inst.plugins;
for(i=0;
i<pl.length;
i++){po=tinyMCE.plugins[pl[i]];
if(po&&po.cleanup){content=po.cleanup(type,content,inst)
}}return content
},setContent:function(A){if(tinyMCE.selectedInstance){tinyMCE.selectedInstance.execCommand("mceSetContent",false,A);
tinyMCE.selectedInstance.repaint()
}},importThemeLanguagePack:function(A){if(typeof (A)=="undefined"){A=tinyMCE.settings.theme
}tinyMCE.loadScript(tinyMCE.baseURL+"/themes/"+A+"/langs/"+tinyMCE.settings.language+".js")
},importPluginLanguagePack:function(B,D){var E="en",A=tinyMCE.baseURL+"/plugins/"+B;
D=D.split(",");
for(var C=0;
C<D.length;
C++){if(tinyMCE.settings.language==D[C]){E=tinyMCE.settings.language
}}if(this.plugins[B]){A=this.plugins[B].baseURL
}tinyMCE.loadScript(A+"/langs/"+E+".js")
},applyTemplate:function(E,A){var C,D,B=E.match(new RegExp("\\{\\$[a-z0-9_]+\\}","gi"));
if(B&&B.length>0){for(C=B.length-1;
C>=0;
C--){D=B[C].substring(2,B[C].length-1);
if(D.indexOf("lang_")==0&&tinyMCELang[D]){E=tinyMCE.replaceVar(E,D,tinyMCELang[D])
}else{if(A&&A[D]){E=tinyMCE.replaceVar(E,D,A[D])
}else{if(tinyMCE.settings[D]){E=tinyMCE.replaceVar(E,D,tinyMCE.settings[D])
}}}}}E=tinyMCE.replaceVar(E,"themeurl",tinyMCE.themeURL);
return E
},replaceVar:function(B,C,A){return B.replace(new RegExp("{\\$"+C+"}","g"),A)
},openWindow:function(template,args){var html,width,height,x,y,resizable,scrollbars,url;
args.mce_template_file=template.file;
args.mce_width=template.width;
args.mce_height=template.height;
tinyMCE.windowArgs=args;
html=template.html;
if(!(width=parseInt(template.width))){width=320
}if(!(height=parseInt(template.height))){height=200
}if(tinyMCE.isMSIE){height+=40
}else{height+=20
}x=parseInt(screen.width/2)-(width/2);
y=parseInt(screen.height/2)-(height/2);
resizable=(args&&args.resizable)?args.resizable:"no";
scrollbars=(args&&args.scrollbars)?args.scrollbars:"no";
if(template.file.charAt(0)!="/"&&template.file.indexOf("://")==-1){url=tinyMCE.baseURL+"/themes/"+tinyMCE.getParam("theme")+"/"+template.file
}else{url=template.file
}for(var name in args){if(typeof (args[name])=="function"){continue
}url=tinyMCE.replaceVar(url,name,escape(args[name]))
}if(html){html=tinyMCE.replaceVar(html,"css",this.settings.popups_css);
html=tinyMCE.applyTemplate(html,args);
var win=window.open("","mcePopup"+new Date().getTime(),"top="+y+",left="+x+",scrollbars="+scrollbars+",dialog=yes,minimizable="+resizable+",modal=yes,width="+width+",height="+height+",resizable="+resizable);
if(win==null){alert(tinyMCELang.lang_popup_blocked);
return 
}win.document.write(html);
win.document.close();
win.resizeTo(width,height);
win.focus()
}else{if((tinyMCE.isMSIE&&!tinyMCE.isOpera)&&resizable!="yes"&&tinyMCE.settings.dialog_type=="modal"){height+=10;
var features="resizable:"+resizable+";scroll:"+scrollbars+";status:yes;center:yes;help:no;dialogWidth:"+width+"px;dialogHeight:"+height+"px;";
window.showModalDialog(url,window,features)
}else{var modal=(resizable=="yes")?"no":"yes";
if(tinyMCE.isGecko&&tinyMCE.isMac){modal="no"
}if(template.close_previous!="no"){try{tinyMCE.lastWindow.close()
}catch(ex){}}var win=window.open(url,"mcePopup"+new Date().getTime(),"top="+y+",left="+x+",scrollbars="+scrollbars+",dialog="+modal+",minimizable="+resizable+",modal="+modal+",width="+width+",height="+height+",resizable="+resizable);
if(win==null){alert(tinyMCELang.lang_popup_blocked);
return 
}if(template.close_previous!="no"){tinyMCE.lastWindow=win
}eval("try { win.resizeTo(width, height); } catch(e) { }");
if(tinyMCE.isGecko){if(win.document.defaultView.statusbar.visible){win.resizeBy(0,tinyMCE.isMac?10:24)
}}win.focus()
}}},closeWindow:function(A){A.close()
},getVisualAidClass:function(E,D){var G="";
if(typeof (D)=="undefined"){D=tinyMCE.settings.visual
}var F=new Array();
var A=E.split(" ");
for(var B=0;
B<A.length;
B++){if(A[B]==G){A[B]=""
}if(A[B]!=""){F[F.length]=A[B]
}}if(D){F[F.length]=G
}var C="";
for(var B=0;
B<F.length;
B++){if(B>0){C+=""
}C+=F[B]
}return C
},handleVisualAid:function(D,K,C,F,I){if(!D){return 
}if(!I){tinyMCE.dispatchCallback(F,"handle_visual_aid_callback","handleVisualAid",D,K,C,F)
}var A=null;
switch(D.nodeName){case"TABLE":var L=D.style.width;
var G=D.style.height;
var B=tinyMCE.getAttrib(D,"border");
B=B==""||B=="0"?true:false;
tinyMCE.setAttrib(D,"class",tinyMCE.getVisualAidClass(tinyMCE.getAttrib(D,"class"),C&&B));
D.style.width=L;
D.style.height=G;
if(D.className=="wiki-table"){for(var J=0;
J<D.rows.length;
J++){if(J==0){D.rows[J].className="table-head"
}else{if((J%2)==1){D.rows[J].className="table-odd"
}else{D.rows[J].className="table-even"
}}}}break;
case"A":var H=tinyMCE.getAttrib(D,"name");
if(H!=""&&C){D.title=H;
D.className="mceItemAnchor"
}else{if(H!=""&&!C){D.className=""
}}break
}if(K&&D.hasChildNodes()){for(var E=0;
E<D.childNodes.length;
E++){tinyMCE.handleVisualAid(D.childNodes[E],K,C,F,true)
}}},fixGeckoBaseHREFBug:function(D,G,F){var A,E,J,C,I,H,B;
if(tinyMCE.isGecko){if(D==1){F=F.replace(/\ssrc=/gi," mce_tsrc=");
F=F.replace(/\shref=/gi," mce_thref=");
return F
}else{B=new Array("a","img","select","area","iframe","base","input","script","embed","object","link");
for(J=0;
J<B.length;
J++){C=G.getElementsByTagName(B[J]);
for(E=0;
E<C.length;
E++){I=tinyMCE.getAttrib(C[E],"mce_tsrc");
H=tinyMCE.getAttrib(C[E],"mce_thref");
if(I!=""){try{C[E].src=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,I)
}catch(G){}C[E].removeAttribute("mce_tsrc")
}if(H!=""){try{C[E].href=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,H)
}catch(G){}C[E].removeAttribute("mce_thref")
}}}B=tinyMCE.selectNodes(G,function(K){if(K.nodeType==3||K.nodeType==8){K.nodeValue=K.nodeValue.replace(/\smce_tsrc=/gi," src=");
K.nodeValue=K.nodeValue.replace(/\smce_thref=/gi," href=")
}return false
})
}}return F
},_setHTML:function(G,E){E=tinyMCE.cleanupHTMLCode(E);
try{tinyMCE.setInnerHTML(G.body,E)
}catch(F){if(this.isMSIE){G.body.createTextRange().pasteHTML(E)
}}if(tinyMCE.isMSIE&&tinyMCE.settings.fix_content_duplication){var A=G.getElementsByTagName("P");
for(var C=0;
C<A.length;
C++){var D=A[C];
while((D=D.parentNode)!=null){if(D.nodeName=="P"){D.outerHTML=D.innerHTML
}}}var B=G.body.innerHTML;
tinyMCE.setInnerHTML(G.body,B)
}tinyMCE.cleanupAnchors(G);
if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertSpansToFonts(G)
}},getEditorId:function(A){var B=this.getInstanceById(A);
if(!B){return null
}return B.editorId
},getInstanceById:function(C){var B=this.instances[C];
if(!B){for(var D in tinyMCE.instances){var A=tinyMCE.instances[D];
if(!tinyMCE.isInstance(A)){continue
}if(A.formTargetElementId==C){B=A;
break
}}}return B
},queryInstanceCommandValue:function(C,B){var A=tinyMCE.getInstanceById(C);
if(A){return A.queryCommandValue(B)
}return false
},queryInstanceCommandState:function(C,B){var A=tinyMCE.getInstanceById(C);
if(A){return A.queryCommandState(B)
}return null
},setWindowArg:function(B,A){this.windowArgs[B]=A
},getWindowArg:function(B,A){return(typeof (this.windowArgs[B])=="undefined")?A:this.windowArgs[B]
},getCSSClasses:function(editor_id,doc){var output=new Array();
if(typeof (tinyMCE.cssClasses)!="undefined"){return tinyMCE.cssClasses
}if(typeof (editor_id)=="undefined"&&typeof (doc)=="undefined"){var instance;
for(var instanceName in tinyMCE.instances){instance=tinyMCE.instances[instanceName];
if(!tinyMCE.isInstance(instance)){continue
}break
}doc=instance.getDoc()
}if(typeof (doc)=="undefined"){var instance=tinyMCE.getInstanceById(editor_id);
doc=instance.getDoc()
}if(doc){var styles=doc.styleSheets;
if(styles&&styles.length>0){for(var x=0;
x<styles.length;
x++){var csses=null;
eval("try {var csses = tinyMCE.isMSIE ? doc.styleSheets("+x+").rules : styles["+x+"].cssRules;} catch(e) {}");
if(!csses){return new Array()
}for(var i=0;
i<csses.length;
i++){var selectorText=csses[i].selectorText;
if(selectorText){var rules=selectorText.split(",");
for(var c=0;
c<rules.length;
c++){var rule=rules[c];
while(rule.indexOf(" ")==0){rule=rule.substring(1)
}if(rule.indexOf(" ")!=-1||rule.indexOf(":")!=-1||rule.indexOf("mceItem")!=-1){continue
}if(rule.indexOf(tinyMCE.settings.visual_table_class)!=-1||rule.indexOf("mceEditable")!=-1||rule.indexOf("mceNonEditable")!=-1){continue
}if(rule.indexOf(".")!=-1){var cssClass=rule.substring(rule.indexOf(".")+1);
var addClass=true;
for(var p=0;
p<output.length&&addClass;
p++){if(output[p]==cssClass){addClass=false
}}if(addClass){output[output.length]=cssClass
}}}}}}}}if(output.length>0){tinyMCE.cssClasses=output
}return output
},regexpReplace:function(E,A,C,D){if(E==null){return E
}if(typeof (D)=="undefined"){D="g"
}var B=new RegExp(A,D);
return E.replace(B,C)
},trim:function(A){return A.replace(/^\s*|\s*$/g,"")
},cleanupEventStr:function(A){A=""+A;
A=A.replace("function anonymous()\n{\n","");
A=A.replace("\n}","");
A=A.replace(/^return true;/gi,"");
return A
},getControlHTML:function(F){var C,A,E,D,B;
A=tinyMCE.plugins;
for(E in A){D=A[E];
if(D.getControlHTML&&(B=D.getControlHTML(F))!=""){return tinyMCE.replaceVar(B,"pluginurl",D.baseURL)
}}D=tinyMCE.themes[tinyMCE.settings.theme];
if(D.getControlHTML&&(B=D.getControlHTML(F))!=""){return B
}return""
},evalFunc:function(f,idx,a){var s="(",i;
for(i=idx;
i<a.length;
i++){s+="a["+i+"]";
if(i<a.length-1){s+=","
}}s+=");";
return eval("f"+s)
},dispatchCallback:function(A,B,C){return this.callFunc(A,B,C,0,this.dispatchCallback.arguments)
},executeCallback:function(A,B,C){return this.callFunc(A,B,C,1,this.executeCallback.arguments)
},execCommandCallback:function(A,B,C){return this.callFunc(A,B,C,2,this.execCommandCallback.arguments)
},callFunc:function(ins,p,n,m,a){var l,i,on,o,s,v;
s=m==2;
l=tinyMCE.getParam(p,"");
if(l!=""&&(v=tinyMCE.evalFunc(typeof (l)=="function"?l:eval(l),3,a))==s&&m>0){return true
}if(ins!=null){for(i=0,l=ins.plugins;
i<l.length;
i++){o=tinyMCE.plugins[l[i]];
if(o[n]&&(v=tinyMCE.evalFunc(o[n],3,a))==s&&m>0){return true
}}}l=tinyMCE.themes;
for(on in l){o=l[on];
if(o[n]&&(v=tinyMCE.evalFunc(o[n],3,a))==s&&m>0){return true
}}return false
},xmlEncode:function(A){A=""+A;
A=A.replace(/&/g,"&amp;");
A=A.replace(new RegExp('"',"g"),"&quot;");
A=A.replace(/\'/g,"&#39;");
A=A.replace(/</g,"&lt;");
A=A.replace(/>/g,"&gt;");
return A
},extend:function(B,A){var C={};
C.parent=B;
for(n in B){C[n]=B[n]
}for(n in A){C[n]=A[n]
}return C
},hideMenus:function(){var A=tinyMCE.lastSelectedMenuBtn;
if(tinyMCE.lastMenu){tinyMCE.lastMenu.hide();
tinyMCE.lastMenu=null
}if(A){tinyMCE.switchClass(A,tinyMCE.lastMenuBtnClass);
tinyMCE.lastSelectedMenuBtn=null
}},explode:function(E,C){var A=C.split(E),D=new Array(),B;
for(B=0;
B<A.length;
B++){if(A[B]!=""){D[D.length]=A[B]
}}return D
}};
var TinyMCE=TinyMCE_Engine;
var tinyMCE=new TinyMCE_Engine();
var tinyMCELang={};
function TinyMCE_Control(C){var I,E,H,D,A,G,F,D,B,J=C;
this.undoRedoLevel=true;
this.isTinyMCE_Control=true;
this.settings=J;
this.settings.theme=tinyMCE.getParam("theme","default");
this.settings.width=tinyMCE.getParam("width",-1);
this.settings.height=tinyMCE.getParam("height",-1);
this.selection=new TinyMCE_Selection(this);
this.undoRedo=new TinyMCE_UndoRedo(this);
this.cleanup=new TinyMCE_Cleanup();
this.shortcuts=new Array();
this.hasMouseMoved=false;
this.cleanup.init({valid_elements:J.valid_elements,extended_valid_elements:J.extended_valid_elements,entities:J.entities,entity_encoding:J.entity_encoding,debug:J.cleanup_debug,url_converter:"TinyMCE_Cleanup.prototype._urlConverter",indent:J.apply_source_formatting,invalid_elements:J.invalid_elements,verify_html:J.verify_html,fix_content_duplication:J.fix_content_duplication});
I=this.settings.theme;
if(!tinyMCE.hasTheme(I)){F=tinyMCE.callbacks;
H={};
for(E=0;
E<F.length;
E++){if((D=window["TinyMCE_"+I+"_"+F[E]])){H[F[E]]=D
}}tinyMCE.addTheme(I,H)
}this.plugins=new Array();
A=tinyMCE.getParam("plugins","",true,",");
if(A.length>0){for(E=0;
E<A.length;
E++){B=A[E];
if(B.charAt(0)=="-"){B=B.substring(1)
}if(!tinyMCE.hasPlugin(B)){F=tinyMCE.callbacks;
H={};
for(G=0;
G<F.length;
G++){if((D=window["TinyMCE_"+B+"_"+F[G]])){H[F[G]]=D
}}tinyMCE.addPlugin(B,H)
}this.plugins[this.plugins.length]=B
}}}TinyMCE_Control.prototype={hasPlugin:function(B){var A;
for(A=0;
A<this.plugins.length;
A++){if(this.plugins[A]==B){return true
}}return false
},addPlugin:function(B,A){if(!this.hasPlugin(B)){tinyMCE.addPlugin(B,A);
this.plugins[this.plugins.length]=B
}},repaint:function(){if(tinyMCE.isMSIE&&!tinyMCE.isOpera){return 
}try{var C=this.selection;
var A=C.getBookmark(true);
this.getBody().style.display="none";
this.getDoc().execCommand("selectall",false,null);
this.getSel().collapseToStart();
this.getBody().style.display="block";
C.moveToBookmark(A)
}catch(B){}},switchSettings:function(){if(tinyMCE.configs.length>1&&tinyMCE.currentConfig!=this.settings.index){tinyMCE.settings=this.settings;
tinyMCE.currentConfig=this.settings.index
}},getBody:function(){return this.getDoc().body
},getDoc:function(){return this.contentWindow.document
},getWin:function(){return this.contentWindow
},addShortcut:function(D,E,G,C,J,K){var B=typeof (E)=="number",A=tinyMCE.isMSIE,I,H,F;
var L=this.shortcuts;
if(!tinyMCE.getParam("custom_shortcuts")){return false
}D=D.toLowerCase();
E=A&&!B?E.toUpperCase():E;
I=B?null:E.charCodeAt(0);
G=G&&G.indexOf("lang_")==0?tinyMCE.getLang(G):G;
H={alt:D.indexOf("alt")!=-1,ctrl:D.indexOf("ctrl")!=-1,shift:D.indexOf("shift")!=-1,charCode:I,keyCode:B?E:(A?I:null),desc:G,cmd:C,ui:J,val:K};
for(F=0;
F<L.length;
F++){if(H.alt==L[F].alt&&H.ctrl==L[F].ctrl&&H.shift==L[F].shift&&H.charCode==L[F].charCode&&H.keyCode==L[F].keyCode){return false
}}L[L.length]=H;
return true
},handleShortcut:function(C){var A,B=this.shortcuts,D;
for(A=0;
A<B.length;
A++){D=B[A];
if(D.alt==C.altKey&&D.ctrl==C.ctrlKey&&(D.keyCode==C.keyCode||D.charCode==C.charCode)){if(D.cmd&&(C.type=="keydown"||(C.type=="keypress"&&!tinyMCE.isOpera))){tinyMCE.execCommand(D.cmd,D.ui,D.val)
}tinyMCE.cancelEvent(C);
return true
}}return false
},autoResetDesignMode:function(){if(!tinyMCE.isMSIE&&this.isHidden()&&tinyMCE.getParam("auto_reset_designmode")){eval('try { this.getDoc().designMode = "On"; } catch(e) {}')
}},isHidden:function(){if(tinyMCE.isMSIE){return false
}var A=this.getSel();
return(!A||!A.rangeCount||A.rangeCount==0)
},isDirty:function(){return this.startContent!=tinyMCE.trim(this.getBody().innerHTML)&&!tinyMCE.isNotDirty
},_mergeElements:function(C,G,F,D){if(C=="removeformat"){G.className="";
G.style.cssText="";
F.className="";
F.style.cssText="";
return 
}var B=tinyMCE.parseStyle(tinyMCE.getAttrib(G,"style"));
var A=tinyMCE.parseStyle(tinyMCE.getAttrib(F,"style"));
var E=tinyMCE.getAttrib(G,"class");
E+=" "+tinyMCE.getAttrib(F,"class");
if(D){for(var H in B){if(typeof (B[H])=="function"){continue
}A[H]=B[H]
}}else{for(var H in A){if(typeof (A[H])=="function"){continue
}B[H]=A[H]
}}tinyMCE.setAttrib(G,"style",tinyMCE.serializeStyle(B));
tinyMCE.setAttrib(G,"class",tinyMCE.trim(E));
F.className="";
F.style.cssText="";
F.removeAttribute("class");
F.removeAttribute("style")
},_setUseCSS:function(A){var C=this.getDoc();
try{C.execCommand("useCSS",false,!A)
}catch(B){}try{C.execCommand("styleWithCSS",false,A)
}catch(B){}if(!tinyMCE.getParam("table_inline_editing")){try{C.execCommand("enableInlineTableEditing",false,"false")
}catch(B){}}if(!tinyMCE.getParam("object_resizing")){try{C.execCommand("enableObjectResizing",false,"false")
}catch(B){}}},execCommand:function(command,user_interface,value){var doc=this.getDoc();
var win=this.getWin();
var focusElm=this.getFocusElement();
if(!new RegExp("mceStartTyping|mceEndTyping|mceBeginUndoLevel|mceEndUndoLevel|mceAddUndoLevel","gi").test(command)){this.undoBookmark=null
}if(this.lastSafariSelection&&!new RegExp("mceStartTyping|mceEndTyping|mceBeginUndoLevel|mceEndUndoLevel|mceAddUndoLevel","gi").test(command)){this.selection.moveToBookmark(this.lastSafariSelection);
tinyMCE.selectedElement=this.lastSafariSelectedElement
}if(!tinyMCE.isMSIE&&!this.useCSS){this._setUseCSS(false);
this.useCSS=true
}this.contentDocument=doc;
if(tinyMCE.execCommandCallback(this,"execcommand_callback","execCommand",this.editorId,this.getBody(),command,user_interface,value)){return 
}if(focusElm&&focusElm.nodeName=="IMG"){var align=focusElm.getAttribute("align");
var img=command=="JustifyCenter"?focusElm.cloneNode(false):focusElm;
switch(command){case"JustifyLeft":if(align=="left"){img.removeAttribute("align")
}else{img.setAttribute("align","left")
}var div=focusElm.parentNode;
if(div&&div.nodeName=="DIV"&&div.childNodes.length==1&&div.parentNode){div.parentNode.replaceChild(img,div)
}this.selection.selectNode(img);
this.repaint();
tinyMCE.triggerNodeChange();
return ;
case"JustifyCenter":img.removeAttribute("align");
var div=tinyMCE.getParentElement(focusElm,"div");
if(div&&div.style.textAlign=="center"){if(div.nodeName=="DIV"&&div.childNodes.length==1&&div.parentNode){div.parentNode.replaceChild(img,div)
}}else{var div=this.getDoc().createElement("div");
div.style.textAlign="center";
div.appendChild(img);
focusElm.parentNode.replaceChild(div,focusElm)
}this.selection.selectNode(img);
this.repaint();
tinyMCE.triggerNodeChange();
return ;
case"JustifyRight":if(align=="right"){img.removeAttribute("align")
}else{img.setAttribute("align","right")
}var div=focusElm.parentNode;
if(div&&div.nodeName=="DIV"&&div.childNodes.length==1&&div.parentNode){div.parentNode.replaceChild(img,div)
}this.selection.selectNode(img);
this.repaint();
tinyMCE.triggerNodeChange();
return 
}}if(tinyMCE.settings.force_br_newlines){var alignValue="";
if(doc.selection.type!="Control"){switch(command){case"JustifyLeft":alignValue="left";
break;
case"JustifyCenter":alignValue="center";
break;
case"JustifyFull":alignValue="justify";
break;
case"JustifyRight":alignValue="right";
break
}if(alignValue!=""){var rng=doc.selection.createRange();
if((divElm=tinyMCE.getParentElement(rng.parentElement(),"div"))!=null){divElm.setAttribute("align",alignValue)
}else{if(rng.pasteHTML&&rng.htmlText.length>0){rng.pasteHTML('<div align="'+alignValue+'">'+rng.htmlText+"</div>")
}}tinyMCE.triggerNodeChange();
return 
}}}switch(command){case"mceRepaint":this.repaint();
return true;
case"InsertUnorderedList":case"InsertOrderedList":var tag=(command=="InsertUnorderedList")?"ul":"ol";
if(tinyMCE.isSafari){this.execCommand("mceInsertContent",false,"<"+tag+"><li>&nbsp;</li><"+tag+">")
}else{this.getDoc().execCommand(command,user_interface,value)
}tinyMCE.triggerNodeChange();
break;
case"Strikethrough":if(tinyMCE.isSafari){this.execCommand("mceInsertContent",false,"<strike>"+this.selection.getSelectedHTML()+"</strike>")
}else{this.getDoc().execCommand(command,user_interface,value)
}tinyMCE.triggerNodeChange();
break;
case"mceSelectNode":this.selection.selectNode(value);
tinyMCE.triggerNodeChange();
tinyMCE.selectedNode=value;
break;
case"FormatBlock":if(value==null||value==""){var elm=tinyMCE.getParentElement(this.getFocusElement(),"p,div,h1,h2,h3,h4,h5,h6,pre,address,blockquote,dt,dl,dd,samp");
if(elm){this.execCommand("mceRemoveNode",false,elm)
}}else{if(tinyMCE.isGecko&&new RegExp("<(div|blockquote|code|dt|dd|dl|samp)>","gi").test(value)){value=value.replace(/[^a-z]/gi,"")
}if(tinyMCE.isMSIE&&new RegExp("blockquote|code|samp","gi").test(value)){var b=this.selection.getBookmark();
this.getDoc().execCommand("FormatBlock",false,"<p>");
tinyMCE.renameElement(tinyMCE.getParentBlockElement(this.getFocusElement()),value);
this.selection.moveToBookmark(b)
}else{this.getDoc().execCommand("FormatBlock",false,value)
}}tinyMCE.triggerNodeChange();
break;
case"mceRemoveNode":if(!value){value=tinyMCE.getParentElement(this.getFocusElement())
}if(tinyMCE.isMSIE){value.outerHTML=value.innerHTML
}else{var rng=value.ownerDocument.createRange();
rng.setStartBefore(value);
rng.setEndAfter(value);
rng.deleteContents();
rng.insertNode(rng.createContextualFragment(value.innerHTML))
}tinyMCE.triggerNodeChange();
break;
case"mceSelectNodeDepth":var parentNode=this.getFocusElement();
for(var i=0;
parentNode;
i++){if(parentNode.nodeName.toLowerCase()=="body"){break
}if(parentNode.nodeName.toLowerCase()=="#text"){i--;
parentNode=parentNode.parentNode;
continue
}if(i==value){this.selection.selectNode(parentNode,false);
tinyMCE.triggerNodeChange();
tinyMCE.selectedNode=parentNode;
return 
}parentNode=parentNode.parentNode
}break;
case"SetStyleInfo":var rng=this.getRng();
var sel=this.getSel();
var scmd=value.command;
var sname=value.name;
var svalue=value.value==null?"":value.value;
var wrapper=value.wrapper?value.wrapper:"span";
var parentElm=null;
var invalidRe=new RegExp("^BODY|HTML$","g");
var invalidParentsRe=tinyMCE.settings.merge_styles_invalid_parents!=""?new RegExp(tinyMCE.settings.merge_styles_invalid_parents,"gi"):null;
if(tinyMCE.isMSIE){if(rng.item){parentElm=rng.item(0)
}else{var pelm=rng.parentElement();
var prng=doc.selection.createRange();
prng.moveToElementText(pelm);
if(rng.htmlText==prng.htmlText||rng.boundingWidth==0){if(invalidParentsRe==null||!invalidParentsRe.test(pelm.nodeName)){parentElm=pelm
}}}}else{var felm=this.getFocusElement();
if(sel.isCollapsed||(new RegExp("td|tr|tbody|table","gi").test(felm.nodeName)&&sel.anchorNode==felm.parentNode)){parentElm=felm
}}if(parentElm&&!invalidRe.test(parentElm.nodeName)){if(scmd=="setstyle"){tinyMCE.setStyleAttrib(parentElm,sname,svalue)
}if(scmd=="setattrib"){tinyMCE.setAttrib(parentElm,sname,svalue)
}if(scmd=="removeformat"){parentElm.style.cssText="";
tinyMCE.setAttrib(parentElm,"class","")
}var ch=tinyMCE.getNodeTree(parentElm,new Array(),1);
for(var z=0;
z<ch.length;
z++){if(ch[z]==parentElm){continue
}if(scmd=="setstyle"){tinyMCE.setStyleAttrib(ch[z],sname,"")
}if(scmd=="setattrib"){tinyMCE.setAttrib(ch[z],sname,"")
}if(scmd=="removeformat"){ch[z].style.cssText="";
tinyMCE.setAttrib(ch[z],"class","")
}}}else{this._setUseCSS(false);
doc.execCommand("FontName",false,"#mce_temp_font#");
var elementArray=tinyMCE.getElementsByAttributeValue(this.getBody(),"font","face","#mce_temp_font#");
for(var x=0;
x<elementArray.length;
x++){elm=elementArray[x];
if(elm){var spanElm=doc.createElement(wrapper);
if(scmd=="setstyle"){tinyMCE.setStyleAttrib(spanElm,sname,svalue)
}if(scmd=="setattrib"){tinyMCE.setAttrib(spanElm,sname,svalue)
}if(scmd=="removeformat"){spanElm.style.cssText="";
tinyMCE.setAttrib(spanElm,"class","")
}if(elm.hasChildNodes()){for(var i=0;
i<elm.childNodes.length;
i++){spanElm.appendChild(elm.childNodes[i].cloneNode(true))
}}spanElm.setAttribute("mce_new","true");
elm.parentNode.replaceChild(spanElm,elm);
var ch=tinyMCE.getNodeTree(spanElm,new Array(),1);
for(var z=0;
z<ch.length;
z++){if(ch[z]==spanElm){continue
}if(scmd=="setstyle"){tinyMCE.setStyleAttrib(ch[z],sname,"")
}if(scmd=="setattrib"){tinyMCE.setAttrib(ch[z],sname,"")
}if(scmd=="removeformat"){ch[z].style.cssText="";
tinyMCE.setAttrib(ch[z],"class","")
}}}}}var nodes=doc.getElementsByTagName(wrapper);
for(var i=nodes.length-1;
i>=0;
i--){var elm=nodes[i];
var isNew=tinyMCE.getAttrib(elm,"mce_new")=="true";
elm.removeAttribute("mce_new");
if(elm.childNodes&&elm.childNodes.length==1&&elm.childNodes[0].nodeType==1){this._mergeElements(scmd,elm,elm.childNodes[0],isNew);
continue
}if(elm.parentNode.childNodes.length==1&&!invalidRe.test(elm.nodeName)&&!invalidRe.test(elm.parentNode.nodeName)){if(invalidParentsRe==null||!invalidParentsRe.test(elm.parentNode.nodeName)){this._mergeElements(scmd,elm.parentNode,elm,false)
}}}var nodes=doc.getElementsByTagName(wrapper);
for(var i=nodes.length-1;
i>=0;
i--){var elm=nodes[i];
var isEmpty=true;
var tmp=doc.createElement("body");
tmp.appendChild(elm.cloneNode(false));
tmp.innerHTML=tmp.innerHTML.replace(new RegExp('style=""|class=""',"gi"),"");
if(new RegExp("<span>","gi").test(tmp.innerHTML)){for(var x=0;
x<elm.childNodes.length;
x++){if(elm.parentNode!=null){elm.parentNode.insertBefore(elm.childNodes[x].cloneNode(true),elm)
}}elm.parentNode.removeChild(elm)
}}if(scmd=="removeformat"){tinyMCE.handleVisualAid(this.getBody(),true,this.visualAid,this)
}tinyMCE.triggerNodeChange();
break;
case"FontName":if(value==null){var s=this.getSel();
if(tinyMCE.isGecko&&s.isCollapsed){var f=tinyMCE.getParentElement(this.getFocusElement(),"font");
if(f!=null){this.selection.selectNode(f,false)
}}this.getDoc().execCommand("RemoveFormat",false,null);
if(f!=null&&tinyMCE.isGecko){var r=this.getRng().cloneRange();
r.collapse(true);
s.removeAllRanges();
s.addRange(r)
}}else{this.getDoc().execCommand("FontName",false,value)
}if(tinyMCE.isGecko){window.setTimeout("tinyMCE.triggerNodeChange(false);",1)
}return ;
case"FontSize":this.getDoc().execCommand("FontSize",false,value);
if(tinyMCE.isGecko){window.setTimeout("tinyMCE.triggerNodeChange(false);",1)
}return ;
case"forecolor":this.getDoc().execCommand("forecolor",false,value);
break;
case"HiliteColor":if(tinyMCE.isGecko){this._setUseCSS(true);
this.getDoc().execCommand("hilitecolor",false,value);
this._setUseCSS(false)
}else{this.getDoc().execCommand("BackColor",false,value)
}break;
case"Cut":case"Copy":case"Paste":var cmdFailed=false;
eval("try {this.getDoc().execCommand(command, user_interface, value);} catch (e) {cmdFailed = true;}");
if(tinyMCE.isOpera&&cmdFailed){alert("Currently not supported by your browser, use keyboard shortcuts instead.")
}if(tinyMCE.isGecko&&cmdFailed){if(confirm(tinyMCE.entityDecode(tinyMCE.getLang("lang_clipboard_msg")))){window.open("http://www.mozilla.org/editor/midasdemo/securityprefs.html","mceExternal")
}return 
}else{tinyMCE.triggerNodeChange()
}break;
case"mceSetContent":if(!value){value=""
}value=tinyMCE.storeAwayURLs(value);
value=tinyMCE._customCleanup(this,"insert_to_editor",value);
tinyMCE._setHTML(doc,value);
tinyMCE.setInnerHTML(doc.body,tinyMCE._cleanupHTML(this,doc,tinyMCE.settings,doc.body));
tinyMCE.convertAllRelativeURLs(doc.body);
tinyMCE._removeInternal(this.getBody());
if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertSpansToFonts(doc)
}tinyMCE.handleVisualAid(doc.body,true,this.visualAid,this);
tinyMCE._setEventsEnabled(doc.body,false);
return true;
case"mceCleanup":var b=this.selection.getBookmark();
tinyMCE._setHTML(this.contentDocument,this.getBody().innerHTML);
tinyMCE.setInnerHTML(this.getBody(),tinyMCE._cleanupHTML(this,this.contentDocument,this.settings,this.getBody(),this.visualAid));
tinyMCE.convertAllRelativeURLs(doc.body);
if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertSpansToFonts(doc)
}tinyMCE.handleVisualAid(this.getBody(),true,this.visualAid,this);
tinyMCE._setEventsEnabled(this.getBody(),false);
this.repaint();
this.selection.moveToBookmark(b);
tinyMCE.triggerNodeChange();
break;
case"mceReplaceContent":if(!value){value=""
}this.getWin().focus();
var selectedText="";
if(tinyMCE.isMSIE){var rng=doc.selection.createRange();
selectedText=rng.text
}else{selectedText=this.getSel().toString()
}if(selectedText.length>0){value=tinyMCE.replaceVar(value,"selection",selectedText);
tinyMCE.execCommand("mceInsertContent",false,value)
}tinyMCE.triggerNodeChange();
break;
case"mceSetAttribute":if(typeof (value)=="object"){var targetElms=(typeof (value.targets)=="undefined")?"p,img,span,div,td,h1,h2,h3,h4,h5,h6,pre,address":value.targets;
var targetNode=tinyMCE.getParentElement(this.getFocusElement(),targetElms);
if(targetNode){targetNode.setAttribute(value.name,value.value);
tinyMCE.triggerNodeChange()
}}break;
case"mceSetCSSClass":this.execCommand("SetStyleInfo",false,{command:"setattrib",name:"class",value:value});
break;
case"mceInsertRawHTML":var key="tiny_mce_marker";
this.execCommand("mceBeginUndoLevel");
this.execCommand("mceInsertContent",false,key);
var scrollX=this.getDoc().body.scrollLeft+this.getDoc().documentElement.scrollLeft;
var scrollY=this.getDoc().body.scrollTop+this.getDoc().documentElement.scrollTop;
var html=this.getBody().innerHTML;
if((pos=html.indexOf(key))!=-1){tinyMCE.setInnerHTML(this.getBody(),html.substring(0,pos)+value+html.substring(pos+key.length))
}this.contentWindow.scrollTo(scrollX,scrollY);
this.execCommand("mceEndUndoLevel");
break;
case"mceInsertContent":if(!value){value=""
}var insertHTMLFailed=false;
this.getWin().focus();
if(tinyMCE.isGecko||tinyMCE.isOpera){try{if(value.indexOf("<")==-1&&!value.match(/(&#38;|&#160;|&#60;|&#62;)/g)){var r=this.getRng();
var n=this.getDoc().createTextNode(tinyMCE.entityDecode(value));
var s=this.getSel();
var r2=r.cloneRange();
s.removeAllRanges();
r.deleteContents();
r.insertNode(n);
r2.selectNode(n);
r2.collapse(false);
s.removeAllRanges();
s.addRange(r2)
}else{value=tinyMCE.fixGeckoBaseHREFBug(1,this.getDoc(),value);
this.getDoc().execCommand("inserthtml",false,value);
tinyMCE.fixGeckoBaseHREFBug(2,this.getDoc(),value)
}}catch(ex){insertHTMLFailed=true
}if(!insertHTMLFailed){tinyMCE.triggerNodeChange();
return 
}}if(tinyMCE.isOpera&&insertHTMLFailed){this.getDoc().execCommand("insertimage",false,tinyMCE.uniqueURL);
var ar=tinyMCE.getElementsByAttributeValue(this.getBody(),"img","src",tinyMCE.uniqueURL);
ar[0].outerHTML=value;
return 
}if(!tinyMCE.isMSIE){var isHTML=value.indexOf("<")!=-1;
var sel=this.getSel();
var rng=this.getRng();
if(isHTML){if(tinyMCE.isSafari){var tmpRng=this.getDoc().createRange();
tmpRng.setStart(this.getBody(),0);
tmpRng.setEnd(this.getBody(),0);
value=tmpRng.createContextualFragment(value)
}else{value=rng.createContextualFragment(value)
}}else{var el=document.createElement("div");
el.innerHTML=value;
value=el.firstChild.nodeValue;
value=doc.createTextNode(value)
}if(tinyMCE.isSafari&&!isHTML){this.execCommand("InsertText",false,value.nodeValue);
tinyMCE.triggerNodeChange();
return true
}else{if(tinyMCE.isSafari&&isHTML){rng.deleteContents();
rng.insertNode(value);
tinyMCE.triggerNodeChange();
return true
}}rng.deleteContents();
if(rng.startContainer.nodeType==3){var node=rng.startContainer.splitText(rng.startOffset);
node.parentNode.insertBefore(value,node)
}else{rng.insertNode(value)
}if(!isHTML){sel.selectAllChildren(doc.body);
sel.removeAllRanges();
var rng=doc.createRange();
rng.selectNode(value);
rng.collapse(false);
sel.addRange(rng)
}else{rng.collapse(false)
}tinyMCE.fixGeckoBaseHREFBug(2,this.getDoc(),value)
}else{var rng=doc.selection.createRange();
var c=value.indexOf("<!--")!=-1;
if(c){value=tinyMCE.uniqueTag+value
}if(rng.item){rng.item(0).outerHTML=value
}else{rng.pasteHTML(value)
}if(c){var e=this.getDoc().getElementById("mceTMPElement");
e.parentNode.removeChild(e)
}}tinyMCE.triggerNodeChange();
break;
case"mceStartTyping":if(tinyMCE.settings.custom_undo_redo&&this.undoRedo.typingUndoIndex==-1){this.undoRedo.typingUndoIndex=this.undoRedo.undoIndex;
this.execCommand("mceAddUndoLevel")
}break;
case"mceEndTyping":if(tinyMCE.settings.custom_undo_redo&&this.undoRedo.typingUndoIndex!=-1){this.execCommand("mceAddUndoLevel");
this.undoRedo.typingUndoIndex=-1
}break;
case"mceBeginUndoLevel":this.undoRedoLevel=false;
break;
case"mceEndUndoLevel":this.undoRedoLevel=true;
this.execCommand("mceAddUndoLevel");
break;
case"mceAddUndoLevel":if(tinyMCE.settings.custom_undo_redo&&this.undoRedoLevel){if(this.undoRedo.add()){tinyMCE.triggerNodeChange(false)
}}break;
case"Undo":if(tinyMCE.settings.custom_undo_redo){tinyMCE.execCommand("mceEndTyping");
this.undoRedo.undo();
tinyMCE.triggerNodeChange()
}else{this.getDoc().execCommand(command,user_interface,value)
}break;
case"Redo":if(tinyMCE.settings.custom_undo_redo){tinyMCE.execCommand("mceEndTyping");
this.undoRedo.redo();
tinyMCE.triggerNodeChange()
}else{this.getDoc().execCommand(command,user_interface,value)
}break;
case"mceToggleVisualAid":this.visualAid=!this.visualAid;
tinyMCE.handleVisualAid(this.getBody(),true,this.visualAid,this);
tinyMCE.triggerNodeChange();
break;
case"Indent":this.getDoc().execCommand(command,user_interface,value);
tinyMCE.triggerNodeChange();
if(tinyMCE.isMSIE){var n=tinyMCE.getParentElement(this.getFocusElement(),"blockquote");
do{if(n&&n.nodeName=="BLOCKQUOTE"){n.removeAttribute("dir");
n.removeAttribute("style")
}}while(n!=null&&(n=n.parentNode)!=null)
}break;
case"removeformat":var text=this.selection.getSelectedText();
if(tinyMCE.isOpera){this.getDoc().execCommand("RemoveFormat",false,null);
return 
}if(tinyMCE.isMSIE){try{var rng=doc.selection.createRange();
rng.execCommand("RemoveFormat",false,null)
}catch(e){}this.execCommand("SetStyleInfo",false,{command:"removeformat"})
}else{this.getDoc().execCommand(command,user_interface,value);
this.execCommand("SetStyleInfo",false,{command:"removeformat"})
}if(text.length==0){this.execCommand("mceSetCSSClass",false,"")
}tinyMCE.triggerNodeChange();
break;
default:this.getDoc().execCommand(command,user_interface,value);
if(tinyMCE.isGecko){window.setTimeout("tinyMCE.triggerNodeChange(false);",1)
}else{tinyMCE.triggerNodeChange()
}}if(command!="mceAddUndoLevel"&&command!="Undo"&&command!="Redo"&&command!="mceStartTyping"&&command!="mceEndTyping"){tinyMCE.execCommand("mceAddUndoLevel")
}},queryCommandValue:function(B){try{return this.getDoc().queryCommandValue(B)
}catch(A){return null
}},queryCommandState:function(A){return this.getDoc().queryCommandState(A)
},_onAdd:function(O,C,P){var G,B,K,D;
B=this.settings.theme;
K=tinyMCE.themes[B];
var Q=P?P:document;
this.targetDoc=Q;
tinyMCE.themeURL=tinyMCE.baseURL+"/themes/"+this.settings.theme;
this.settings.themeurl=tinyMCE.themeURL;
if(!O){alert("Error: Could not find the target element.");
return false
}if(K.getEditorTemplate){D=K.getEditorTemplate(this.settings,this.editorId)
}var N=D.delta_width?D.delta_width:0;
var M=D.delta_height?D.delta_height:0;
var F='<span id="'+this.editorId+'_parent" class="mceEditorContainer">'+D.html;
F=tinyMCE.replaceVar(F,"editor_id",this.editorId);
this.settings.default_document=tinyMCE.baseURL+"/blank.htm";
this.settings.old_width=this.settings.width;
this.settings.old_height=this.settings.height;
if(this.settings.width==-1){this.settings.width=O.offsetWidth
}if(this.settings.height==-1){this.settings.height=O.offsetHeight
}if(this.settings.width==0){this.settings.width=O.style.width
}if(this.settings.height==0){this.settings.height=O.style.height
}if(this.settings.width==0){this.settings.width=320
}if(this.settings.height==0){this.settings.height=240
}this.settings.area_width=parseInt(this.settings.width);
this.settings.area_height=parseInt(this.settings.height);
this.settings.area_width+=N;
this.settings.area_height+=M;
if((""+this.settings.width).indexOf("%")!=-1){this.settings.area_width="100%"
}if((""+this.settings.height).indexOf("%")!=-1){this.settings.area_height="100%"
}if((""+O.style.width).indexOf("%")!=-1){this.settings.width=O.style.width;
this.settings.area_width="100%"
}if((""+O.style.height).indexOf("%")!=-1){this.settings.height=O.style.height;
this.settings.area_height="100%"
}F=tinyMCE.applyTemplate(F);
this.settings.width=this.settings.old_width;
this.settings.height=this.settings.old_height;
this.visualAid=this.settings.visual;
this.formTargetElementId=C;
if(O.nodeName=="TEXTAREA"||O.nodeName=="INPUT"){this.startContent=O.value
}else{this.startContent=O.innerHTML
}if(O.nodeName!="TEXTAREA"&&O.nodeName!="INPUT"){this.oldTargetElement=O;
if(tinyMCE.settings.debug){G='<textarea wrap="off" id="'+C+'" name="'+C+'" cols="100" rows="15"></textarea>'
}else{G='<input type="hidden" id="'+C+'" name="'+C+'" />';
this.oldTargetElement.style.display="none"
}F+="</span>";
if(tinyMCE.isGecko){F=G+F
}else{F+=G
}if(tinyMCE.isGecko){var A=O.ownerDocument.createRange();
A.setStartBefore(O);
var I=A.createContextualFragment(F);
tinyMCE.insertAfter(I,O)
}else{O.insertAdjacentHTML("beforeBegin",F)
}}else{F+="</span>";
this.oldTargetElement=O;
if(!tinyMCE.settings.debug){this.oldTargetElement.style.display="none"
}if(tinyMCE.isGecko){var A=O.ownerDocument.createRange();
A.setStartBefore(O);
var I=A.createContextualFragment(F);
tinyMCE.insertAfter(I,O)
}else{O.insertAdjacentHTML("beforeBegin",F)
}}var L=false;
var E=Q.getElementById(this.editorId);
if(!tinyMCE.isMSIE){if(E&&(E.nodeName=="SPAN"||E.nodeName=="span")){E=tinyMCE._createIFrame(E,Q);
L=true
}this.targetElement=E;
this.iframeElement=E;
this.contentDocument=E.contentDocument;
this.contentWindow=E.contentWindow
}else{if(E&&E.nodeName=="SPAN"){E=tinyMCE._createIFrame(E,Q,Q.parentWindow)
}else{E=Q.frames[this.editorId]
}this.targetElement=E;
this.iframeElement=Q.getElementById(this.editorId);
if(tinyMCE.isOpera){this.contentDocument=this.iframeElement.contentDocument;
this.contentWindow=this.iframeElement.contentWindow;
L=true
}else{this.contentDocument=E.window.document;
this.contentWindow=E.window
}this.getDoc().designMode="on"
}var J=this.contentDocument;
if(L){var F=tinyMCE.getParam("doctype")+'<html><head xmlns="http://www.w3.org/1999/xhtml"><base href="'+tinyMCE.settings.base_href+'" /><title>blank_page</title><meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></head><body class="mceContentBody"></body></html>';
try{if(!this.isHidden()){this.getDoc().designMode="on"
}J.open();
J.write(F);
J.close()
}catch(H){this.getDoc().location.href=tinyMCE.baseURL+"/blank.htm"
}}if(tinyMCE.isMSIE){window.setTimeout('tinyMCE.addEventHandlers(tinyMCE.instances["'+this.editorId+'"]);',1)
}tinyMCE.setupContent(this.editorId,true);
return true
},setBaseHREF:function(C){var D,A,E,B;
E=this.getDoc();
B=E.getElementsByTagName("base");
A=B.length>0?B[0]:null;
if(!A){B=E.getElementsByTagName("head");
D=B.length>0?B[0]:null;
A=E.createElement("base");
A.setAttribute("href",C);
D.appendChild(A)
}else{if(C==""||C==null){A.parentNode.removeChild(A)
}else{A.setAttribute("href",C)
}}},getFocusElement:function(){return this.selection.getFocusElement()
},getSel:function(){return this.selection.getSel()
},getRng:function(){return this.selection.getRng()
},triggerSave:function(skip_cleanup,skip_callback){var e,nl=new Array(),i,s;
this.switchSettings();
s=tinyMCE.settings;
if(tinyMCE.isMSIE&&!tinyMCE.isOpera){e=this.iframeElement;
do{if(e.style&&e.style.display=="none"){e.style.display="block";
nl[nl.length]={elm:e,type:"style"}
}if(e.style&&s.hidden_tab_class.length>0&&e.className.indexOf(s.hidden_tab_class)!=-1){e.className=s.display_tab_class;
nl[nl.length]={elm:e,type:"class"}
}}while((e=e.parentNode)!=null)
}tinyMCE.settings.preformatted=false;
if(typeof (skip_cleanup)=="undefined"){skip_cleanup=false
}if(typeof (skip_callback)=="undefined"){skip_callback=false
}tinyMCE._setHTML(this.getDoc(),this.getBody().innerHTML);
if(this.settings.cleanup==false){tinyMCE.handleVisualAid(this.getBody(),true,false,this);
tinyMCE._setEventsEnabled(this.getBody(),true)
}tinyMCE._customCleanup(this,"submit_content_dom",this.contentWindow.document.body);
var htm=skip_cleanup?this.getBody().innerHTML:tinyMCE._cleanupHTML(this,this.getDoc(),this.settings,this.getBody(),tinyMCE.visualAid,true,true);
htm=tinyMCE._customCleanup(this,"submit_content",htm);
if(!skip_callback&&tinyMCE.settings.save_callback!=""){var content=eval(tinyMCE.settings.save_callback+"(this.formTargetElementId,htm,this.getBody());")
}if((typeof (content)!="undefined")&&content!=null){htm=content
}htm=tinyMCE.regexpReplace(htm,"&#40;","(","gi");
htm=tinyMCE.regexpReplace(htm,"&#41;",")","gi");
htm=tinyMCE.regexpReplace(htm,"&#59;",";","gi");
htm=tinyMCE.regexpReplace(htm,"&#34;","&quot;","gi");
htm=tinyMCE.regexpReplace(htm,"&#94;","^","gi");
if(this.formElement){this.formElement.value=htm
}if(tinyMCE.isSafari&&this.formElement){this.formElement.innerText=htm
}for(i=0;
i<nl.length;
i++){if(nl[i].type=="style"){nl[i].elm.style.display="none"
}else{nl[i].elm.className=s.hidden_tab_class
}}}};
TinyMCE_Engine.prototype.cleanupHTMLCode=function(A){A=A.replace(new RegExp("<p \\/>","gi"),"<p>&nbsp;</p>");
A=A.replace(new RegExp("<p>\\s*<\\/p>","gi"),"<p>&nbsp;</p>");
A=A.replace(new RegExp("<br>\\s*<\\/br>","gi"),"<br />");
A=A.replace(new RegExp("<(h[1-6]|p|div|address|pre|form|table|li|ol|ul|td|b|font|em|strong|i|strike|u|span|a|ul|ol|li|blockquote)([a-z]*)([^\\\\|>]*)\\/>","gi"),"<$1$2$3></$1$2>");
A=A.replace(new RegExp("\\s+></","gi"),"></");
A=A.replace(new RegExp("<(img|br|hr)([^>]*)><\\/(img|br|hr)>","gi"),"<$1$2 />");
if(tinyMCE.isMSIE){A=A.replace(new RegExp("<p><hr \\/><\\/p>","gi"),"<hr>")
}if(tinyMCE.getParam("convert_urls")){A=A.replace(new RegExp('(href="{0,1})(\\s*#)',"gi"),"$1"+tinyMCE.settings.document_base_url+"#")
}return A
};
TinyMCE_Engine.prototype.parseStyle=function(F){var A=new Array();
if(F==null){return A
}var B=F.split(";");
tinyMCE.clearArray(A);
for(var C=0;
C<B.length;
C++){if(B[C]==""){continue
}var E=new RegExp("^\\s*([^:]*):\\s*(.*)\\s*$");
var D=B[C].replace(E,"$1||$2").split("||");
if(D.length==2){A[D[0].toLowerCase()]=D[1]
}}return A
};
TinyMCE_Engine.prototype.compressStyle=function(B,G,F,D){var E=new Array();
E[0]=B[G+"-top"+F];
E[1]=B[G+"-left"+F];
E[2]=B[G+"-right"+F];
E[3]=B[G+"-bottom"+F];
for(var C=0;
C<E.length;
C++){if(E[C]==null){return 
}for(var A=0;
A<E.length;
A++){if(E[A]!=E[C]){return 
}}}B[D]=E[0];
B[G+"-top"+F]=null;
B[G+"-left"+F]=null;
B[G+"-right"+F]=null;
B[G+"-bottom"+F]=null
};
TinyMCE_Engine.prototype.serializeStyle=function(ar){var str="";
tinyMCE.compressStyle(ar,"border","","border");
tinyMCE.compressStyle(ar,"border","-width","border-width");
tinyMCE.compressStyle(ar,"border","-color","border-color");
tinyMCE.compressStyle(ar,"border","-style","border-style");
tinyMCE.compressStyle(ar,"padding","","padding");
tinyMCE.compressStyle(ar,"margin","","margin");
for(var key in ar){var val=ar[key];
if(typeof (val)=="function"){continue
}if(key.indexOf("mso-")==0){continue
}if(val!=null&&val!=""){val=""+val;
val=val.replace(new RegExp("url\\(\\'?([^\\']*)\\'?\\)","gi"),"url('$1')");
if(val.indexOf("url(")!=-1&&tinyMCE.getParam("convert_urls")){var m=new RegExp("url\\('(.*?)'\\)").exec(val);
if(m.length>1){val="url('"+eval(tinyMCE.getParam("urlconverter_callback")+"(m[1], null, true);")+"')"
}}if(tinyMCE.getParam("force_hex_style_colors")){val=tinyMCE.convertRGBToHex(val,true)
}if(val!="url('')"){str+=key.toLowerCase()+": "+val+"; "
}}}if(new RegExp("; $").test(str)){str=str.substring(0,str.length-2)
}return str
};
TinyMCE_Engine.prototype.convertRGBToHex=function(D,A){if(D.toLowerCase().indexOf("rgb")!=-1){var C=new RegExp("(.*?)rgb\\s*?\\(\\s*?([0-9]+).*?,\\s*?([0-9]+).*?,\\s*?([0-9]+).*?\\)(.*?)","gi");
var B=D.replace(C,"$1,$2,$3,$4,$5").split(",");
if(B.length==5){r=parseInt(B[1]).toString(16);
g=parseInt(B[2]).toString(16);
b=parseInt(B[3]).toString(16);
r=r.length==1?"0"+r:r;
g=g.length==1?"0"+g:g;
b=b.length==1?"0"+b:b;
D="#"+r+g+b;
if(A){D=B[0]+D+B[4]
}}}return D
};
TinyMCE_Engine.prototype.convertHexToRGB=function(A){if(A.indexOf("#")!=-1){A=A.replace(new RegExp("[^0-9A-F]","gi"),"");
return"rgb("+parseInt(A.substring(0,2),16)+","+parseInt(A.substring(2,4),16)+","+parseInt(A.substring(4,6),16)+")"
}return A
};
TinyMCE_Engine.prototype.convertSpansToFonts=function(G){var H=tinyMCE.getParam("font_size_style_values").replace(/\s+/,"").split(",");
var E=G.body.innerHTML;
E=E.replace(/<span/gi,"<font");
E=E.replace(/<\/span/gi,"</font");
G.body.innerHTML=E;
var I=G.getElementsByTagName("font");
for(var C=0;
C<I.length;
C++){var J=tinyMCE.trim(I[C].style.fontSize).toLowerCase();
var D=0;
for(var F=0;
F<H.length;
F++){if(H[F]==J){D=F+1;
break
}}if(D>0){tinyMCE.setAttrib(I[C],"size",D);
I[C].style.fontSize=""
}var A=I[C].style.fontFamily;
if(A!=null&&A!=""){tinyMCE.setAttrib(I[C],"face",A);
I[C].style.fontFamily=""
}var B=I[C].style.color;
if(B!=null&&B!=""){tinyMCE.setAttrib(I[C],"color",tinyMCE.convertRGBToHex(B));
I[C].style.color=""
}}};
TinyMCE_Engine.prototype.convertFontsToSpans=function(G){var H=tinyMCE.getParam("font_size_style_values").replace(/\s+/,"").split(",");
var F=G.body.innerHTML;
F=F.replace(/<font/gi,"<span");
F=F.replace(/<\/font/gi,"</span");
G.body.innerHTML=F;
var B=tinyMCE.getParam("font_size_classes");
if(B!=""){B=B.replace(/\s+/,"").split(",")
}else{B=null
}var I=G.getElementsByTagName("span");
for(var D=0;
D<I.length;
D++){var E,A,C;
E=tinyMCE.getAttrib(I[D],"size");
A=tinyMCE.getAttrib(I[D],"face");
C=tinyMCE.getAttrib(I[D],"color");
if(E!=""){E=parseInt(E);
if(E>0&&E<8){if(B!=null){tinyMCE.setAttrib(I[D],"class",B[E-1])
}else{I[D].style.fontSize=H[E-1]
}}I[D].removeAttribute("size")
}if(A!=""){I[D].style.fontFamily=A;
I[D].removeAttribute("face")
}if(C!=""){I[D].style.color=C;
I[D].removeAttribute("color")
}}this.splitOverlapSpans(G)
};
TinyMCE_Engine.prototype.splitOverlapSpans=function(I){var M=I.getElementsByTagName("span");
for(var F=M.length-1;
F>=0;
F--){var B=M[F];
var L=B.childNodes;
var J=B.parentNode;
var K=B.cloneNode(false);
var H=M[F].style;
for(var E=0;
E<L.length;
E++){if(L[E].nodeName.toLowerCase()!="span"){K.appendChild(L[E].cloneNode(true));
J.insertBefore(K,B)
}else{var D=L[E].style;
K=L[E].cloneNode(true);
if(H!=null){for(var C=0;
C<H.length;
C++){var G=new Boolean("true");
if(D!=null){for(var A=0;
A<D.length;
A++){if(this.trim(H[C])==this.trim(D[A])){G=false
}}}else{G=false
}if(G){switch(H[C]){case"color":K.style.color=M[F].style.color;
break;
case"background-color":K.style.backgroundColor=M[F].style.backgroundColor;
break;
case"font-family":K.style.fontFamily=M[F].style.fontFamily;
break;
case"font-size":K.style.fontSize=M[F].style.fontSize;
break
}}}}J.insertBefore(K,B);
K=B.cloneNode(false)
}J.insertBefore(K,B)
}J.removeChild(B)
}};
TinyMCE_Engine.prototype.cleanupAnchors=function(D){var B,E,A,C=D.getElementsByTagName("a");
for(B=C.length-1;
B>=0;
B--){if(tinyMCE.getAttrib(C[B],"name")!=""&&tinyMCE.getAttrib(C[B],"href")==""){E=C[B].childNodes;
for(A=E.length-1;
A>=0;
A--){tinyMCE.insertAfter(E[A],C[B])
}}}};
TinyMCE_Engine.prototype.getContent=function(B){var A;
if(typeof (B)!="undefined"){tinyMCE.selectedInstance=tinyMCE.getInstanceById(B)
}if(tinyMCE.selectedInstance){A=tinyMCE._cleanupHTML(this.selectedInstance,this.selectedInstance.getDoc(),tinyMCE.settings,this.selectedInstance.getBody(),false,true);
if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertSpansToFonts(this.selectedInstance.getDoc())
}return A
}return null
};
TinyMCE_Engine.prototype._fixListElements=function(F){var B,H,G=["ol","ul"],E,D,C,A=new RegExp("^(OL|UL)$"),I;
for(H=0;
H<G.length;
H++){B=F.getElementsByTagName(G[H]);
for(E=0;
E<B.length;
E++){D=B[E];
C=D.parentNode;
if(A.test(C.nodeName)){I=tinyMCE.prevNode(D,"LI");
if(!I){I=F.createElement("li");
I.innerHTML="&nbsp;";
I.appendChild(D);
C.insertBefore(I,C.firstChild)
}else{I.appendChild(D)
}}}}};
TinyMCE_Engine.prototype._fixTables=function(G){var B,D,H,F,E,A,C;
B=G.getElementsByTagName("table");
for(D=0;
D<B.length;
D++){H=B[D];
if((F=tinyMCE.getParentElement(H,"p,div,h1,h2,h3,h4,h5,h6"))!=null){E=F.cloneNode(false);
E.removeAttribute("id");
C=H;
while((H=H.nextSibling)){E.appendChild(H)
}tinyMCE.insertAfter(E,F);
tinyMCE.insertAfter(C,F)
}}};
TinyMCE_Engine.prototype._cleanupHTML=function(I,O,B,M,A,E,G){var J,L,K,H,F,D,C,N,P;
if(!tinyMCE.getParam("cleanup")){return M.innerHTML
}E=typeof (E)=="undefined"?false:E;
N=I.cleanup;
P=I.settings;
L=N.settings.debug;
if(L){K=new Date().getTime()
}if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertFontsToSpans(O)
}if(tinyMCE.getParam("fix_list_elements")){tinyMCE._fixListElements(O)
}if(tinyMCE.getParam("fix_table_elements")){tinyMCE._fixTables(O)
}tinyMCE._customCleanup(I,E?"get_from_editor_dom":"insert_to_editor_dom",O.body);
if(L){H=new Date().getTime()
}N.settings.on_save=E;
N.idCount=0;
N.serializationId++;
N.serializedNodes=new Array();
N.sourceIndex=-1;
if(P.cleanup_serializer=="xml"){J=N.serializeNodeAsXML(M)
}else{J=N.serializeNodeAsHTML(M)
}if(L){F=new Date().getTime()
}J=J.replace(/<\/?(body|head|html)[^>]*>/gi,"");
J=J.replace(new RegExp(' (rowspan="1"|colspan="1")',"g"),"");
J=J.replace(/<p><hr \/><\/p>/g,"<hr />");
J=J.replace(/<p>(&nbsp;|&#160;)<\/p><hr \/><p>(&nbsp;|&#160;)<\/p>/g,"<hr />");
J=J.replace(/<td>\s*<br \/>\s*<\/td>/g,"<td>&nbsp;</td>");
J=J.replace(/<p>\s*<br \/>\s*<\/p>/g,"<p>&nbsp;</p>");
J=J.replace(/<p>\s*(&nbsp;|&#160;)\s*<br \/>\s*(&nbsp;|&#160;)\s*<\/p>/g,"<p>&nbsp;</p>");
J=J.replace(/<p>\s*(&nbsp;|&#160;)\s*<br \/>\s*<\/p>/g,"<p>&nbsp;</p>");
J=J.replace(/<p>\s*<br \/>\s*&nbsp;\s*<\/p>/g,"<p>&nbsp;</p>");
J=J.replace(new RegExp("<a>(.*?)<\\/a>","g"),"$1");
J=J.replace(/<p([^>]*)>\s*<\/p>/g,"<p$1>&nbsp;</p>");
if(/^\s*(<br \/>|<p>&nbsp;<\/p>|<p>&#160;<\/p>|<p><\/p>)\s*$/.test(J)){J=""
}if(P.preformatted){J=J.replace(/^<pre>/,"");
J=J.replace(/<\/pre>$/,"");
J="<pre>"+J+"</pre>"
}if(tinyMCE.isGecko){J=J.replace(/<o:p _moz-userdefined="" \/>/g,"");
J=J.replace(/<td([^>]*)>\s*<br \/>\s*<\/td>/g,"<td$1>&nbsp;</td>")
}if(P.force_br_newlines){J=J.replace(/<p>(&nbsp;|&#160;)<\/p>/g,"<br />")
}J=tinyMCE._customCleanup(I,E?"get_from_editor":"insert_to_editor",J);
if(E){J=J.replace(new RegExp(" ?(mceItem[a-zA-Z0-9]*|"+P.visual_table_class+")","g"),"");
J=J.replace(new RegExp(' ?class=""',"g"),"")
}if(P.remove_linebreaks&&!N.settings.indent){J=J.replace(/\n|\r/g," ")
}if(L){D=new Date().getTime()
}if(E&&N.settings.indent){J=N.formatHTML(J)
}if(G&&(P.encoding=="xml"||P.encoding=="html")){J=N.xmlEncode(J)
}if(L){C=new Date().getTime()
}if(N.settings.debug){tinyMCE.debug("Cleanup in ms: Pre="+(H-K)+", Serialize: "+(F-H)+", Post: "+(D-F)+", Format: "+(C-D)+", Sum: "+(C-K)+".")
}return J
};
function TinyMCE_Cleanup(){this.isMSIE=(navigator.appName=="Microsoft Internet Explorer");
this.rules=tinyMCE.clearArray(new Array());
this.settings={indent_elements:"head,table,tbody,thead,tfoot,form,tr,ul,ol,blockquote,object",newline_before_elements:"h1,h2,h3,h4,h5,h6,pre,address,div,ul,ol,li,meta,option,area,title,link,base,script,td",newline_after_elements:"br,hr,p,pre,address,div,ul,ol,meta,option,area,link,base,script",newline_before_after_elements:"html,head,body,table,thead,tbody,tfoot,tr,form,ul,ol,blockquote,p,object,param,hr,div",indent_char:"\t",indent_levels:1,entity_encoding:"raw",valid_elements:"*[*]",entities:"",url_converter:"",invalid_elements:"",verify_html:false};
this.vElements=tinyMCE.clearArray(new Array());
this.vElementsRe="";
this.closeElementsRe=/^(IMG|BR|HR|LINK|META|BASE|INPUT|BUTTON|AREA)$/;
this.codeElementsRe=/^(SCRIPT|STYLE)$/;
this.serializationId=0;
this.mceAttribs={href:"mce_href",src:"mce_src",type:"mce_type"}
}TinyMCE_Cleanup.prototype={init:function(D){var G,A,C,E,F,B;
for(G in D){this.settings[G]=D[G]
}D=this.settings;
this.inRe=this._arrayToRe(D.indent_elements.split(","),"","^<(",")[^>]*");
this.ouRe=this._arrayToRe(D.indent_elements.split(","),"","^<\\/(",")[^>]*");
this.nlBeforeRe=this._arrayToRe(D.newline_before_elements.split(","),"gi","<(",")([^>]*)>");
this.nlAfterRe=this._arrayToRe(D.newline_after_elements.split(","),"gi","<(",")([^>]*)>");
this.nlBeforeAfterRe=this._arrayToRe(D.newline_before_after_elements.split(","),"gi","<(\\/?)(",")([^>]*)>");
if(D.invalid_elements!=""){this.iveRe=this._arrayToRe(D.invalid_elements.toUpperCase().split(","),"g","^(",")$")
}else{this.iveRe=null
}B="";
for(C=0;
C<D.indent_levels;
C++){B+=D.indent_char
}this.inStr=B;
if(!D.verify_html){D.valid_elements="*[*]";
D.extended_valid_elements=""
}this.fillStr=D.entity_encoding=="named"?"&nbsp;":"&nbsp;";
this.idCount=0
},addRuleStr:function(A){var B=this.parseRuleStr(A);
var C;
for(C in B){if(B[C]){this.rules[C]=B[C]
}}this.vElements=tinyMCE.clearArray(new Array());
for(C in this.rules){if(this.rules[C]){this.vElements[this.vElements.length]=this.rules[C].tag
}}this.vElementsRe=this._arrayToRe(this.vElements,"")
},parseRuleStr:function(N){var G,D,A,I,F,J,K,M,L,H,C,E=tinyMCE.clearArray(new Array()),B;
if(N==null||N.length==0){return E
}G=N.split(",");
for(J=0;
J<G.length;
J++){N=G[J];
if(N.length==0){continue
}D=this.split(/\[|\]/,N);
if(D==null||D.length<1){M=N.toUpperCase()
}else{M=D[0].toUpperCase()
}L=this.split("/",M);
for(H=0;
H<L.length;
H++){A={};
A.tag=L[H];
A.forceAttribs=null;
A.defaultAttribs=null;
A.validAttribValues=null;
K=A.tag.charAt(0);
A.forceOpen=K=="+";
A.removeEmpty=K=="-";
A.fill=K=="#";
A.tag=A.tag.replace(/\+|-|#/g,"");
A.oTagName=L[0].replace(/\+|-|#/g,"").toLowerCase();
A.isWild=new RegExp("\\*|\\?|\\+","g").test(A.tag);
A.validRe=new RegExp(this._wildcardToRe("^"+A.tag+"$"));
if(D.length>1){A.vAttribsRe="^(";
I=this.split(/\|/,D[1]);
for(F=0;
F<I.length;
F++){M=I[F];
C=new RegExp("(=|:|<)(.*?)$").exec(M);
M=M.replace(new RegExp("(=|:|<).*?$"),"");
if(C&&C.length>0){if(C[0].charAt(0)==":"){if(!A.forceAttribs){A.forceAttribs=tinyMCE.clearArray(new Array())
}A.forceAttribs[M.toLowerCase()]=C[0].substring(1)
}else{if(C[0].charAt(0)=="="){if(!A.defaultAttribs){A.defaultAttribs=tinyMCE.clearArray(new Array())
}B=C[0].substring(1);
A.defaultAttribs[M.toLowerCase()]=B==""?"mce_empty":B
}else{if(C[0].charAt(0)=="<"){if(!A.validAttribValues){A.validAttribValues=tinyMCE.clearArray(new Array())
}A.validAttribValues[M.toLowerCase()]=this._arrayToRe(this.split("?",C[0].substring(1)),"")
}}}}A.vAttribsRe+=""+M.toLowerCase()+(F!=I.length-1?"|":"");
I[F]=M.toLowerCase()
}A.vAttribsRe+=")$";
A.vAttribsRe=this._wildcardToRe(A.vAttribsRe);
A.vAttribsReIsWild=new RegExp("\\*|\\?|\\+","g").test(A.vAttribsRe);
A.vAttribsRe=new RegExp(A.vAttribsRe);
A.vAttribs=I.reverse()
}else{A.vAttribsRe="";
A.vAttribs=tinyMCE.clearArray(new Array());
A.vAttribsReIsWild=false
}E[A.tag]=A
}}return E
},serializeNodeAsXML:function(D){var B,A;
if(!this.xmlDoc){if(this.isMSIE){try{this.xmlDoc=new ActiveXObject("MSXML2.DOMDocument")
}catch(C){}if(!this.xmlDoc){try{this.xmlDoc=new ActiveXObject("Microsoft.XmlDom")
}catch(C){}}}else{this.xmlDoc=document.implementation.createDocument("","",null)
}if(!this.xmlDoc){alert("Error XML Parser could not be found.")
}}if(this.xmlDoc.firstChild){this.xmlDoc.removeChild(this.xmlDoc.firstChild)
}A=this.xmlDoc.createElement("html");
A=this.xmlDoc.appendChild(A);
this._convertToXML(D,A);
if(this.isMSIE){return this.xmlDoc.xml
}else{return new XMLSerializer().serializeToString(this.xmlDoc)
}},_convertToXML:function(B,F){var J,A,E,D,H,C,I,G=false;
if(this._isDuplicate(B)){return 
}J=this.xmlDoc;
switch(B.nodeType){case 1:G=B.hasChildNodes();
A=J.createElement(B.nodeName.toLowerCase());
C=B.attributes;
for(E=C.length-1;
E>-1;
E--){I=C[E];
if(I.specified&&I.nodeValue){A.setAttribute(I.nodeName.toLowerCase(),I.nodeValue)
}}if(!G&&!this.closeElementsRe.test(B.nodeName)){A.appendChild(J.createTextNode(""))
}F=F.appendChild(A);
break;
case 3:F.appendChild(J.createTextNode(B.nodeValue));
return ;
case 8:F.appendChild(J.createComment(B.nodeValue));
return 
}if(G){H=B.childNodes;
for(E=0,D=H.length;
E<D;
E++){this._convertToXML(H[E],F)
}}},serializeNodeAsHTML:function(D){var B,L,G="",F,E,A,K,J=false,I=false,C,H;
this._setupRules();
if(this._isDuplicate(D)){return""
}switch(D.nodeType){case 1:H=D.hasChildNodes();
if((tinyMCE.isMSIE&&!tinyMCE.isOpera)&&D.nodeName.indexOf("/")!=-1){break
}if(this.vElementsRe.test(D.nodeName)&&(!this.iveRe||!this.iveRe.test(D.nodeName))){J=true;
A=this.rules[D.nodeName];
if(!A){C=this.rules;
for(L in C){if(C[L]&&C[L].validRe.test(D.nodeName)){A=C[L];
break
}}}B=A.isWild?D.nodeName.toLowerCase():A.oTagName;
I=A.fill;
if(A.removeEmpty&&!H){return""
}G+="<"+B;
if(A.vAttribsReIsWild){C=D.attributes;
for(F=C.length-1;
F>-1;
F--){L=C[F];
if(L.specified&&A.vAttribsRe.test(L.nodeName)){G+=this._serializeAttribute(D,A,L.nodeName)
}}}else{for(F=A.vAttribs.length-1;
F>-1;
F--){G+=this._serializeAttribute(D,A,A.vAttribs[F])
}}if(!this.settings.on_save){C=this.mceAttribs;
for(L in C){if(C[L]){G+=this._serializeAttribute(D,A,C[L])
}}}if(this.closeElementsRe.test(D.nodeName)){return G+" />"
}G+=">";
if(this.isMSIE&&this.codeElementsRe.test(D.nodeName)){G+=D.innerHTML
}}break;
case 3:if(D.parentNode&&this.codeElementsRe.test(D.parentNode.nodeName)){return this.isMSIE?"":D.nodeValue
}return this.xmlEncode(D.nodeValue);
case 8:return"<!--"+this._trimComment(D.nodeValue)+"-->"
}if(H){K=D.childNodes;
for(F=0,E=K.length;
F<E;
F++){G+=this.serializeNodeAsHTML(K[F])
}}if(I&&!H){G+=this.fillStr
}if(J){G+="</"+B+">"
}return G
},_serializeAttribute:function(n,r,an){var av="",t,os=this.settings.on_save;
if(os&&(an.indexOf("mce_")==0||an.indexOf("_moz")==0)){return""
}if(os&&this.mceAttribs[an]){av=this._getAttrib(n,this.mceAttribs[an])
}if(av.length==0){av=this._getAttrib(n,an)
}if(av.length==0&&r.defaultAttribs&&(t=r.defaultAttribs[an])){av=t;
if(av=="mce_empty"){return" "+an+'=""'
}}if(r.forceAttribs&&(t=r.forceAttribs[an])){av=t
}if(os&&av.length!=0&&this.settings.url_converter.length!=0&&/^(src|href|longdesc)$/.test(an)){av=eval(this.settings.url_converter+"(this, n, av)")
}if(av.length!=0&&r.validAttribValues&&r.validAttribValues[an]&&!r.validAttribValues[an].test(av)){return""
}if(av.length!=0&&av=="{$uid}"){av="uid_"+(this.idCount++)
}if(av.length!=0){return" "+an+'="'+this.xmlEncode(av)+'"'
}return""
},formatHTML:function(E){var D=this.settings,F="",C=0,A=0,G="",B;
E=E.replace(/\r/g,"");
E="\n"+E;
E=E.replace(new RegExp("\\n\\s+","gi"),"\n");
E=E.replace(this.nlBeforeRe,"\n<$1$2>");
E=E.replace(this.nlAfterRe,"<$1$2>\n");
E=E.replace(this.nlBeforeAfterRe,"\n<$1$2$3>\n");
E+="\n";
while((C=E.indexOf("\n",C+1))!=-1){if((B=E.substring(A+1,C)).length!=0){if(this.ouRe.test(B)&&F.length>=D.indent_levels){F=F.substring(D.indent_levels)
}G+=F+B+"\n";
if(this.inRe.test(B)){F+=this.inStr
}}A=C
}return G
},xmlEncode:function(C){var B,A,D,E="",F;
this._setupEntities();
switch(this.settings.entity_encoding){case"raw":return tinyMCE.xmlEncode(C);
case"named":for(B=0,A=C.length;
B<A;
B++){F=C.charCodeAt(B);
D=this.entities[F];
if(D&&D!=""){E+="&"+D+";"
}else{E+=String.fromCharCode(F)
}}return E;
case"numeric":for(B=0,A=C.length;
B<A;
B++){F=C.charCodeAt(B);
if(F>127||F==60||F==62||F==38||F==39||F==34){E+="&#"+F+";"
}else{E+=String.fromCharCode(F)
}}return E
}return C
},split:function(D,C){var F=C.split(D);
var B,A,E=new Array();
for(B=0,A=F.length;
B<A;
B++){if(F[B]!=""){E[B]=F[B]
}}return E
},_trimComment:function(A){A=A.replace(new RegExp('\\smce_src="[^"]*"',"gi"),"");
A=A.replace(new RegExp('\\smce_href="[^"]*"',"gi"),"");
return A
},_getAttrib:function(B,D,C){if(typeof (C)=="undefined"){C=""
}if(!B||B.nodeType!=1){return C
}var A=B.getAttribute(D,0);
if(D=="class"&&!A){A=B.className
}if(this.isMSIE&&D=="http-equiv"){A=B.httpEquiv
}if(this.isMSIE&&B.nodeName=="FORM"&&D=="enctype"&&A=="application/x-www-form-urlencoded"){A=""
}if(this.isMSIE&&B.nodeName=="INPUT"&&D=="size"&&A=="20"){A=""
}if(this.isMSIE&&B.nodeName=="INPUT"&&D=="maxlength"&&A=="2147483647"){A=""
}if(D=="style"&&!tinyMCE.isOpera){A=B.style.cssText
}if(D=="style"){A=tinyMCE.serializeStyle(tinyMCE.parseStyle(A))
}if(this.settings.on_save&&D.indexOf("on")!=-1&&this.settings.on_save&&A&&A!=""){A=tinyMCE.cleanupEventStr(A)
}return(A&&A!="")?""+A:C
},_urlConverter:function(c,n,v){if(!c.settings.on_save){return tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,v)
}else{if(tinyMCE.getParam("convert_urls")){return eval(tinyMCE.settings.urlconverter_callback+"(v, n, true);")
}}return v
},_arrayToRe:function(A,F,E,B){var C,D;
F=typeof (F)=="undefined"?"gi":F;
E=typeof (E)=="undefined"?"^(":E;
B=typeof (B)=="undefined"?")$":B;
D=E;
for(C=0;
C<A.length;
C++){D+=this._wildcardToRe(A[C])+(C!=A.length-1?"|":"")
}D+=B;
return new RegExp(D,F)
},_wildcardToRe:function(A){A=A.replace(/\?/g,"(\\S?)");
A=A.replace(/\+/g,"(\\S+)");
A=A.replace(/\*/g,"(\\S*)");
return A
},_setupEntities:function(){var D,A,B,C=this.settings;
if(!this.entitiesDone){if(C.entity_encoding=="named"){D=tinyMCE.clearArray(new Array());
A=this.split(",",C.entities);
for(B=0;
B<A.length;
B+=2){D[A[B]]=A[B+1]
}this.entities=D
}this.entitiesDone=true
}},_setupRules:function(){var A=this.settings;
if(!this.rulesDone){this.addRuleStr(A.valid_elements);
this.addRuleStr(A.extended_valid_elements);
this.rulesDone=true
}},_isDuplicate:function(B){var A;
if(!this.settings.fix_content_duplication){return false
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera&&B.nodeType==1){if(B.mce_serialized==this.serializationId){return true
}B.setAttribute("mce_serialized",this.serializationId)
}else{for(A=0;
A<this.serializedNodes.length;
A++){if(this.serializedNodes[A]==B){return true
}}this.serializedNodes[this.serializedNodes.length]=B
}return false
}};
TinyMCE_Engine.prototype.getElementByAttributeValue=function(D,C,A,B){return(D=this.getElementsByAttributeValue(D,C,A,B)).length==0?null:D[0]
};
TinyMCE_Engine.prototype.getElementsByAttributeValue=function(G,E,B,C){var D,A=G.getElementsByTagName(E),F=new Array();
for(D=0;
D<A.length;
D++){if(tinyMCE.getAttrib(A[D],B).indexOf(C)!=-1){F[F.length]=A[D]
}}return F
};
TinyMCE_Engine.prototype.isBlockElement=function(A){return A!=null&&A.nodeType==1&&this.blockRegExp.test(A.nodeName)
};
TinyMCE_Engine.prototype.getParentBlockElement=function(A){while(A){if(this.isBlockElement(A)){return A
}A=A.parentNode
}return null
};
TinyMCE_Engine.prototype.insertAfter=function(B,A){if(A.nextSibling){A.parentNode.insertBefore(B,A.nextSibling)
}else{A.parentNode.appendChild(B)
}};
TinyMCE_Engine.prototype.setInnerHTML=function(D,C){var B,A,E;
if(tinyMCE.isMSIE&&!tinyMCE.isOpera){C=C.replace(/\s\/>/g,">");
C=C.replace(/<p([^>]*)>\u00A0?<\/p>/gi,'<p$1 mce_keep="true">&nbsp;</p>');
C=C.replace(/<p([^>]*)>\s*&nbsp;\s*<\/p>/gi,'<p$1 mce_keep="true">&nbsp;</p>');
C=C.replace(/<p([^>]*)>\s+<\/p>/gi,'<p$1 mce_keep="true">&nbsp;</p>');
D.innerHTML=tinyMCE.uniqueTag+C;
D.firstChild.removeNode(true);
A=D.getElementsByTagName("p");
for(B=A.length-1;
B>=0;
B--){E=A[B];
if(E.nodeName=="P"&&!E.hasChildNodes()&&!E.mce_keep){E.parentNode.removeChild(E)
}}}else{C=this.fixGeckoBaseHREFBug(1,D,C);
D.innerHTML=C;
this.fixGeckoBaseHREFBug(2,D,C)
}};
TinyMCE_Engine.prototype.getOuterHTML=function(A){if(tinyMCE.isMSIE){return A.outerHTML
}var B=A.ownerDocument.createElement("body");
B.appendChild(A);
return B.innerHTML
};
TinyMCE_Engine.prototype.setOuterHTML=function(B,A){if(tinyMCE.isMSIE){B.outerHTML=A;
return 
}var C=B.ownerDocument.createElement("body");
C.innerHTML=A;
B.parentNode.replaceChild(C.firstChild,B)
};
TinyMCE_Engine.prototype._getElementById=function(F,E){var D,B,A,C;
if(typeof (E)=="undefined"){E=document
}D=E.getElementById(F);
if(!D){C=E.forms;
for(B=0;
B<C.length;
B++){for(A=0;
A<C[B].elements.length;
A++){if(C[B].elements[A].name==F){D=C[B].elements[A];
break
}}}}return D
};
TinyMCE_Engine.prototype.getNodeTree=function(E,A,C,D){var B;
if(typeof (C)=="undefined"||E.nodeType==C&&(typeof (D)=="undefined"||E.nodeName==D)){A[A.length]=E
}if(E.hasChildNodes()){for(B=0;
B<E.childNodes.length;
B++){tinyMCE.getNodeTree(E.childNodes[B],A,C,D)
}}return A
};
TinyMCE_Engine.prototype.getParentElement=function(D,F,B,E){if(typeof (F)=="undefined"){if(D.nodeType==1){return D
}while((D=D.parentNode)!=null&&D.nodeType!=1){}return D
}if(D==null){return null
}var A=F.toUpperCase().split(",");
do{for(var C=0;
C<A.length;
C++){if(D.nodeName==A[C]||F=="*"){if(typeof (B)=="undefined"){return D
}else{if(D.getAttribute(B)){if(typeof (E)=="undefined"){if(D.getAttribute(B)!=""){return D
}}else{if(D.getAttribute(B)==E){return D
}}}}}}}while((D=D.parentNode)!=null);
return null
};
TinyMCE_Engine.prototype.getParentNode=function(B,A){while(B){if(A(B)){return B
}B=B.parentNode
}return null
};
TinyMCE_Engine.prototype.getAttrib=function(D,C,A){if(typeof (A)=="undefined"){A=""
}if(!D||D.nodeType!=1){return A
}var B=D.getAttribute(C);
if(C=="class"&&!B){B=D.className
}if(tinyMCE.isGecko&&C=="src"&&D.src!=null&&D.src!=""){B=D.src
}if(tinyMCE.isGecko&&C=="href"&&D.href!=null&&D.href!=""){B=D.href
}if(C=="http-equiv"&&tinyMCE.isMSIE){B=D.httpEquiv
}if(C=="style"&&!tinyMCE.isOpera){B=D.style.cssText
}return(B&&B!="")?B:A
};
TinyMCE_Engine.prototype.setAttrib=function(C,B,E,A){if(typeof (E)=="number"&&E!=null){E=""+E
}if(A){if(E==null){E=""
}var D=new RegExp("[^0-9%]","g");
E=E.replace(D,"")
}if(B=="style"){C.style.cssText=E
}if(B=="class"){C.className=E
}if(E!=null&&E!=""&&E!=-1){C.setAttribute(B,E)
}else{C.removeAttribute(B)
}};
TinyMCE_Engine.prototype.setStyleAttrib=function(elm,name,value){eval("elm.style."+name+"=value;");
if(tinyMCE.isMSIE&&value==null||value==""){var str=tinyMCE.serializeStyle(tinyMCE.parseStyle(elm.style.cssText));
elm.style.cssText=str;
elm.setAttribute("style",str)
}};
TinyMCE_Engine.prototype.switchClass=function(B,C){var A;
if(tinyMCE.switchClassCache[B]){A=tinyMCE.switchClassCache[B]
}else{A=tinyMCE.switchClassCache[B]=document.getElementById(B)
}if(A){if(tinyMCE.settings.button_tile_map&&A.className&&A.className.indexOf("mceTiledButton")==0){C="mceTiledButton "+C
}A.className=C
}};
TinyMCE_Engine.prototype.getAbsPosition=function(B){var A={absLeft:0,absTop:0};
while(B){A.absLeft+=B.offsetLeft;
A.absTop+=B.offsetTop;
B=B.offsetParent
}return A
};
TinyMCE_Engine.prototype.prevNode=function(C,D){var A=D.split(","),B;
while((C=C.previousSibling)!=null){for(B=0;
B<A.length;
B++){if(C.nodeName==A[B]){return C
}}}return null
};
TinyMCE_Engine.prototype.nextNode=function(C,D){var A=D.split(","),B;
while((C=C.nextSibling)!=null){for(B=0;
B<A.length;
B++){if(C.nodeName==A[B]){return C
}}}return null
};
TinyMCE_Engine.prototype.selectNodes=function(D,C,A){var B;
if(!A){A=new Array()
}if(C(D)){A[A.length]=D
}if(D.hasChildNodes()){for(B=0;
B<D.childNodes.length;
B++){tinyMCE.selectNodes(D.childNodes[B],C,A)
}}return A
};
TinyMCE_Engine.prototype.addCSSClass=function(B,D,A){var C=this.removeCSSClass(B,D);
return B.className=A?D+(C!=""?(" "+C):""):(C!=""?(C+" "):"")+D
};
TinyMCE_Engine.prototype.removeCSSClass=function(C,D){var A=this.explode(" ",C.className),B;
for(B=0;
B<A.length;
B++){if(A[B]==D){A[B]=""
}}return C.className=A.join(" ")
};
TinyMCE_Engine.prototype.renameElement=function(D,F,E){var C,B,A;
E=typeof (E)=="undefined"?tinyMCE.selectedInstance.getDoc():E;
if(D){C=E.createElement(F);
A=D.attributes;
for(B=A.length-1;
B>-1;
B--){if(A[B].specified&&A[B].nodeValue){C.setAttribute(A[B].nodeName.toLowerCase(),A[B].nodeValue)
}}A=D.childNodes;
for(B=0;
B<A.length;
B++){C.appendChild(A[B].cloneNode(true))
}D.parentNode.replaceChild(C,D)
}};
TinyMCE_Engine.prototype.parseURL=function(D){var F=new Array();
if(D){var E,C;
E=D.indexOf("://");
if(E!=-1){F.protocol=D.substring(0,E);
C=E+3
}for(var A=C;
A<D.length;
A++){var B=D.charAt(A);
if(B==":"){break
}if(B=="/"){break
}}E=A;
F.host=D.substring(C,E);
F.port="";
C=E;
if(D.charAt(E)==":"){E=D.indexOf("/",C);
F.port=D.substring(C+1,E)
}C=E;
E=D.indexOf("?",C);
if(E==-1){E=D.indexOf("#",C)
}if(E==-1){E=D.length
}F.path=D.substring(C,E);
C=E;
if(D.charAt(E)=="?"){E=D.indexOf("#");
E=(E==-1)?D.length:E;
F.query=D.substring(C+1,E)
}C=E;
if(D.charAt(E)=="#"){E=D.length;
F.anchor=D.substring(C+1,E)
}}return F
};
TinyMCE_Engine.prototype.serializeURL=function(A){var B="";
if(A.protocol){B+=A.protocol+"://"
}if(A.host){B+=A.host
}if(A.port){B+=":"+A.port
}if(A.path){B+=A.path
}if(A.query){B+="?"+A.query
}if(A.anchor){B+="#"+A.anchor
}return B
};
TinyMCE_Engine.prototype.convertAbsoluteURLToRelativeURL=function(K,C){var B=this.parseURL(K);
var J=this.parseURL(C);
var A;
var L;
var H=0;
var E="";
var G=false;
if(J.path==""){J.path="/"
}else{G=true
}K=B.path.substring(0,B.path.lastIndexOf("/"));
A=K.split("/");
L=J.path.split("/");
if(A.length>=L.length){for(var F=0;
F<A.length;
F++){if(F>=L.length||A[F]!=L[F]){H=F+1;
break
}}}if(A.length<L.length){for(var F=0;
F<L.length;
F++){if(F>=A.length||A[F]!=L[F]){H=F+1;
break
}}}if(H==1){return J.path
}for(var F=0;
F<(A.length-(H-1));
F++){E+="../"
}for(var F=H-1;
F<L.length;
F++){if(F!=(H-1)){E+="/"+L[F]
}else{E+=L[F]
}}J.protocol=null;
J.host=null;
J.port=null;
J.path=E==""&&G?"/":E;
var D=B.path;
var I;
if((I=D.lastIndexOf("/"))!=-1){D=D.substring(I+1)
}if(D==J.path&&J.anchor!=""){J.path=""
}if(J.path==""&&!J.anchor){J.path=D!=""?D:"/"
}return this.serializeURL(J)
};
TinyMCE_Engine.prototype.convertRelativeToAbsoluteURL=function(K,H){var C=this.parseURL(K);
var I=this.parseURL(H);
if(H==""||H.charAt(0)=="/"||H.indexOf("://")!=-1||H.indexOf("mailto:")!=-1||H.indexOf("javascript:")!=-1){return H
}baseURLParts=C.path.split("/");
relURLParts=I.path.split("/");
var F=new Array();
for(var E=baseURLParts.length-1;
E>=0;
E--){if(baseURLParts[E].length==0){continue
}F[F.length]=baseURLParts[E]
}baseURLParts=F.reverse();
var J=new Array();
var L=0;
for(var E=relURLParts.length-1;
E>=0;
E--){if(relURLParts[E].length==0||relURLParts[E]=="."){continue
}if(relURLParts[E]==".."){L++;
continue
}if(L>0){L--;
continue
}J[J.length]=relURLParts[E]
}relURLParts=J.reverse();
var G=baseURLParts.length-L;
var A=(G<=0?"":"/")+baseURLParts.slice(0,G).join("/")+"/"+relURLParts.join("/");
var B="",D="";
I.protocol=C.protocol;
I.host=C.host;
I.port=C.port;
if(I.path.charAt(I.path.length-1)=="/"){A+="/"
}I.path=A;
return this.serializeURL(I)
};
TinyMCE_Engine.prototype.convertURL=function(A,F,H){var K=document.location.protocol;
var L=document.location.hostname;
var E=document.location.port;
if(K=="file:"){return A
}A=tinyMCE.regexpReplace(A,"(http|https):///","/");
if(A.indexOf("mailto:")!=-1||A.indexOf("javascript:")!=-1||tinyMCE.regexpReplace(A,"[ \t\r\n+]|%20","").charAt(0)=="#"){return A
}if(!tinyMCE.isMSIE&&!H&&A.indexOf("://")==-1&&A.charAt(0)!="/"){return tinyMCE.settings.base_href+A
}if(H&&tinyMCE.getParam("relative_urls")){var G=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,A);
if(G.charAt(0)=="/"){G=tinyMCE.settings.document_base_prefix+G
}var D=tinyMCE.parseURL(G);
var J=tinyMCE.parseURL(tinyMCE.settings.document_base_url);
if(D.host==J.host&&(D.port==J.port)){return tinyMCE.convertAbsoluteURLToRelativeURL(tinyMCE.settings.document_base_url,G)
}}if(!tinyMCE.getParam("relative_urls")){var D=tinyMCE.parseURL(A);
var C=tinyMCE.parseURL(tinyMCE.settings.base_href);
A=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,A);
if(D.anchor&&D.path==C.path){return"#"+D.anchor
}}if(tinyMCE.getParam("remove_script_host")){var B="",I="";
if(E!=""){I=":"+E
}B=K+"//"+L+I+"/";
if(A.indexOf(B)==0){A=A.substring(B.length-1)
}}return A
};
TinyMCE_Engine.prototype.convertAllRelativeURLs=function(A){var E=A.getElementsByTagName("img");
for(var D=0;
D<E.length;
D++){var G=tinyMCE.getAttrib(E[D],"src");
var B=tinyMCE.getAttrib(E[D],"mce_src");
if(B!=""){G=B
}if(G!=""){G=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,G);
E[D].setAttribute("src",G)
}}var E=A.getElementsByTagName("a");
for(var D=0;
D<E.length;
D++){var C=tinyMCE.getAttrib(E[D],"href");
var F=tinyMCE.getAttrib(E[D],"mce_href");
if(F!=""){C=F
}if(C&&C!=""){C=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,C);
E[D].setAttribute("href",C)
}}};
TinyMCE_Engine.prototype.clearArray=function(A){for(var B in A){A[B]=null
}return A
};
TinyMCE_Engine.prototype._setEventsEnabled=function(D,A){var I=new Array("onfocus","onblur","onclick","ondblclick","onmousedown","onmouseup","onmouseover","onmousemove","onmouseout","onkeypress","onkeydown","onkeydown","onkeyup");
var H=tinyMCE.settings.event_elements.split(",");
for(var F=0;
F<H.length;
F++){var C=D.getElementsByTagName(H[F]);
for(var E=0;
E<C.length;
E++){var B="";
for(var G=0;
G<I.length;
G++){if((B=tinyMCE.getAttrib(C[E],I[G]))!=""){B=tinyMCE.cleanupEventStr(""+B);
if(!A){B="return true;"+B
}else{B=B.replace(/^return true;/gi,"")
}C[E].removeAttribute(I[G]);
C[E].setAttribute(I[G],B)
}}}}};
TinyMCE_Engine.prototype._eventPatch=function(E){var F,B,D,C;
if(typeof (tinyMCE)=="undefined"){return true
}try{if(tinyMCE.selectedInstance){D=tinyMCE.selectedInstance.getWin();
if(D&&D.event){C=D.event;
if(!C.target){C.target=C.srcElement
}TinyMCE_Engine.prototype.handleEvent(C);
return 
}}for(F in tinyMCE.instances){B=tinyMCE.instances[F];
if(!tinyMCE.isInstance(B)){continue
}tinyMCE.selectedInstance=B;
D=B.getWin();
if(D&&D.event){C=D.event;
if(!C.target){C.target=C.srcElement
}TinyMCE_Engine.prototype.handleEvent(C);
return 
}}}catch(A){}};
TinyMCE_Engine.prototype.unloadHandler=function(){tinyMCE.triggerSave(true,true)
};
TinyMCE_Engine.prototype.addEventHandlers=function(inst){var doc=inst.getDoc();
inst.switchSettings();
if(tinyMCE.isMSIE){tinyMCE.addEvent(doc,"keypress",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(doc,"keyup",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(doc,"keydown",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(doc,"mouseup",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(doc,"mousedown",TinyMCE_Engine.prototype._eventPatch);
tinyMCE.addEvent(doc,"click",TinyMCE_Engine.prototype._eventPatch)
}else{tinyMCE.addEvent(doc,"keypress",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"keydown",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"keyup",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"click",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"mouseup",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"mousedown",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"focus",tinyMCE.handleEvent);
tinyMCE.addEvent(doc,"blur",tinyMCE.handleEvent);
eval('try { doc.designMode = "On"; } catch(e) {}')
}};
TinyMCE_Engine.prototype.onMouseMove=function(){var A;
if(!tinyMCE.hasMouseMoved){A=tinyMCE.selectedInstance;
if(A.isFocused){A.undoBookmark=A.selection.getBookmark();
tinyMCE.hasMouseMoved=true
}}};
TinyMCE_Engine.prototype.cancelEvent=function(A){if(tinyMCE.isMSIE){A.returnValue=false;
A.cancelBubble=true
}else{A.preventDefault()
}};
TinyMCE_Engine.prototype.addEvent=function(B,C,A){if(B.attachEvent){B.attachEvent("on"+C,A)
}else{B.addEventListener(C,A,false)
}};
TinyMCE_Engine.prototype.addSelectAccessibility=function(C,B,A){if(!B._isAccessible){B.onkeydown=tinyMCE.accessibleEventHandler;
B.onblur=tinyMCE.accessibleEventHandler;
B._isAccessible=true;
B._win=A
}return false
};
TinyMCE_Engine.prototype.accessibleEventHandler=function(B){var A=this._win;
B=tinyMCE.isMSIE?A.event:B;
var C=tinyMCE.isMSIE?B.srcElement:B.target;
if(B.type=="blur"){if(C.oldonchange){C.onchange=C.oldonchange;
C.oldonchange=null
}return true
}if(C.nodeName=="SELECT"&&!C.oldonchange){C.oldonchange=C.onchange;
C.onchange=null
}if(B.keyCode==13||B.keyCode==32){C.onchange=C.oldonchange;
C.onchange();
C.oldonchange=null;
tinyMCE.cancelEvent(B);
return false
}return true
};
TinyMCE_Engine.prototype._resetIframeHeight=function(){var A;
if(tinyMCE.isMSIE&&!tinyMCE.isOpera){A=tinyMCE.selectedInstance.iframeElement;
if(A._oldHeight){A.style.height=A._oldHeight;
A.height=A._oldHeight
}}};
function TinyMCE_Selection(A){this.instance=A
}TinyMCE_Selection.prototype={getSelectedHTML:function(){var C=this.instance;
var D,B=this.getRng(),A;
if(tinyMCE.isSafari){return B.toString()
}D=document.createElement("body");
if(tinyMCE.isGecko){D.appendChild(B.cloneContents())
}else{D.innerHTML=B.item?B.item(0).outerHTML:B.htmlText
}A=tinyMCE._cleanupHTML(C,C.contentDocument,C.settings,D,D,false,true,false);
if(tinyMCE.getParam("convert_fonts_to_spans")){tinyMCE.convertSpansToFonts(C.getDoc())
}return A
},getSelectedText:function(){var D=this.instance;
var E,C,B,A;
if(tinyMCE.isMSIE){E=D.getDoc();
if(E.selection.type=="Text"){C=E.selection.createRange();
A=C.text
}else{A=""
}}else{B=this.getSel();
if(B&&B.toString){A=B.toString()
}else{A=""
}}return A
},getBookmark:function(E){var C=this.getRng();
var N=this.instance.getDoc();
var F,B,O,J,D,I,H,G;
var M,L,K,A=-999999999;
if(tinyMCE.isOpera){return null
}L=N.body.scrollLeft+N.documentElement.scrollLeft;
K=N.body.scrollTop+N.documentElement.scrollTop;
if(tinyMCE.isSafari||tinyMCE.isGecko){return{rng:C,scrollX:L,scrollY:K}
}if(tinyMCE.isMSIE){if(E){return{rng:C}
}if(C.item){J=C.item(0);
D=N.getElementsByTagName(J.nodeName);
for(I=0;
I<D.length;
I++){if(J==D[I]){F=I;
break
}}return{tag:J.nodeName,index:F,scrollX:L,scrollY:K}
}else{M=C.duplicate();
M.collapse(true);
F=Math.abs(M.move("character",A));
M=C.duplicate();
M.collapse(false);
B=Math.abs(M.move("character",A))-F;
return{start:F,length:B,scrollX:L,scrollY:K}
}}if(tinyMCE.isGecko){O=tinyMCE.getParentElement(C.startContainer);
for(H=0;
H<O.childNodes.length&&O.childNodes[H]!=C.startContainer;
H++){}D=N.getElementsByTagName(O.nodeName);
for(I=0;
I<D.length;
I++){if(O==D[I]){F=I;
break
}}J=tinyMCE.getParentElement(C.endContainer);
for(G=0;
G<J.childNodes.length&&J.childNodes[G]!=C.endContainer;
G++){}D=N.getElementsByTagName(J.nodeName);
for(I=0;
I<D.length;
I++){if(J==D[I]){B=I;
break
}}return{startTag:O.nodeName,start:F,startIndex:H,endTag:J.nodeName,end:B,endIndex:G,startOffset:C.startOffset,endOffset:C.endOffset,scrollX:L,scrollY:K}
}return null
},moveToBookmark:function(G){var A,B,D;
var E=this.instance;
var I=E.getDoc();
var F=E.getWin();
var C=this.getSel();
if(!G){return false
}if(tinyMCE.isSafari){C.setBaseAndExtent(G.startContainer,G.startOffset,G.endContainer,G.endOffset);
return true
}if(tinyMCE.isMSIE){if(G.rng){G.rng.select();
return true
}F.focus();
if(G.tag){A=E.getBody().createControlRange();
B=I.getElementsByTagName(G.tag);
if(B.length>G.index){try{A.addElement(B[G.index])
}catch(H){}}}else{A=E.getSel().createRange();
A.moveToElementText(E.getBody());
A.collapse(true);
A.moveStart("character",G.start);
A.moveEnd("character",G.length)
}A.select();
F.scrollTo(G.scrollX,G.scrollY);
return true
}if(tinyMCE.isGecko&&G.rng){C.removeAllRanges();
C.addRange(G.rng);
F.scrollTo(G.scrollX,G.scrollY);
return true
}if(tinyMCE.isGecko){A=I.createRange();
B=I.getElementsByTagName(G.startTag);
if(B.length>G.start){A.setStart(B[G.start].childNodes[G.startIndex],G.startOffset)
}B=I.getElementsByTagName(G.endTag);
if(B.length>G.end){A.setEnd(B[G.end].childNodes[G.endIndex],G.endOffset)
}C.removeAllRanges();
C.addRange(A);
F.scrollTo(G.scrollX,G.scrollY);
return true
}return false
},selectNode:function(D,I,G,H){var E=this.instance,C,A,B;
if(!D){return 
}if(typeof (I)=="undefined"){I=true
}if(typeof (G)=="undefined"){G=false
}if(typeof (H)=="undefined"){H=true
}if(tinyMCE.isMSIE){A=E.getBody().createTextRange();
try{A.moveToElementText(D);
if(I){A.collapse(H)
}A.select()
}catch(F){}}else{C=this.getSel();
if(!C){return 
}if(tinyMCE.isSafari){C.setBaseAndExtent(D,0,D,D.innerText.length);
if(I){if(H){C.collapseToStart()
}else{C.collapseToEnd()
}}this.scrollToNode(D);
return 
}A=E.getDoc().createRange();
if(G){B=tinyMCE.getNodeTree(D,new Array(),3);
if(B.length>0){A.selectNodeContents(B[0])
}else{A.selectNodeContents(D)
}}else{A.selectNode(D)
}if(I){if(!H&&D.nodeType==3){A.setStart(D,D.nodeValue.length);
A.setEnd(D,D.nodeValue.length)
}else{A.collapse(H)
}}C.removeAllRanges();
C.addRange(A)
}this.scrollToNode(D);
tinyMCE.selectedElement=null;
if(D.nodeType==1){tinyMCE.selectedElement=D
}},scrollToNode:function(B){var C=this.instance;
var G,E,F,D,A;
G=tinyMCE.getAbsPosition(B);
E=C.getDoc();
F=E.body.scrollLeft+E.documentElement.scrollLeft;
D=E.body.scrollTop+E.documentElement.scrollTop;
A=tinyMCE.isMSIE?document.getElementById(C.editorId).style.pixelHeight:C.targetElement.clientHeight;
if(!tinyMCE.settings.auto_resize&&!(G.absTop>D&&G.absTop<(D-25+A))){C.contentWindow.scrollTo(G.absLeft,G.absTop-A+25)
}},getSel:function(){var A=this.instance;
if(tinyMCE.isMSIE&&!tinyMCE.isOpera){return A.getDoc().selection
}return A.contentWindow.getSelection()
},getRng:function(){var B=this.instance;
var A=this.getSel();
if(A==null){return null
}if(tinyMCE.isMSIE&&!tinyMCE.isOpera){return A.createRange()
}if(tinyMCE.isSafari&&!A.getRangeAt){return""+window.getSelection()
}return A.getRangeAt(0)
},getFocusElement:function(){var C=this.instance;
if(tinyMCE.isMSIE&&!tinyMCE.isOpera){var D=C.getDoc();
var A=D.selection.createRange();
var E=A.item?A.item(0):A.parentElement()
}else{if(C.isHidden()){return C.getBody()
}var B=this.getSel();
var A=this.getRng();
if(!B||!A){return null
}var E=A.commonAncestorContainer;
if(!A.collapsed){if(A.startContainer==A.endContainer){if(A.startOffset-A.endOffset<2){if(A.startContainer.hasChildNodes()){E=A.startContainer.childNodes[A.startOffset]
}}}}E=tinyMCE.getParentElement(E)
}return E
}};
function TinyMCE_UndoRedo(A){this.instance=A;
this.undoLevels=new Array();
this.undoIndex=0;
this.typingUndoIndex=-1;
this.undoRedo=true
}TinyMCE_UndoRedo.prototype={add:function(C){var B;
if(C){this.undoLevels[this.undoLevels.length]=C;
return true
}var E=this.instance;
if(this.typingUndoIndex!=-1){this.undoIndex=this.typingUndoIndex
}var F=tinyMCE.trim(E.getBody().innerHTML);
if(this.undoLevels[this.undoIndex]&&F!=this.undoLevels[this.undoIndex].content){tinyMCE.dispatchCallback(E,"onchange_callback","onChange",E);
var A=tinyMCE.settings.custom_undo_redo_levels;
if(A!=-1&&this.undoLevels.length>A){for(var D=0;
D<this.undoLevels.length-1;
D++){this.undoLevels[D]=this.undoLevels[D+1]
}this.undoLevels.length--;
this.undoIndex--
}B=E.undoBookmark;
if(!B){B=E.selection.getBookmark()
}this.undoIndex++;
this.undoLevels[this.undoIndex]={content:F,bookmark:B};
this.undoLevels.length=this.undoIndex+1;
return true
}return false
},undo:function(){var A=this.instance;
if(this.undoIndex>0){this.undoIndex--;
tinyMCE.setInnerHTML(A.getBody(),this.undoLevels[this.undoIndex].content);
A.repaint();
if(A.settings.custom_undo_redo_restore_selection){A.selection.moveToBookmark(this.undoLevels[this.undoIndex].bookmark)
}}},redo:function(){var A=this.instance;
tinyMCE.execCommand("mceEndTyping");
if(this.undoIndex<(this.undoLevels.length-1)){this.undoIndex++;
tinyMCE.setInnerHTML(A.getBody(),this.undoLevels[this.undoIndex].content);
A.repaint();
if(A.settings.custom_undo_redo_restore_selection){A.selection.moveToBookmark(this.undoLevels[this.undoIndex].bookmark)
}}tinyMCE.triggerNodeChange()
}};
var TinyMCE_ForceParagraphs={_insertPara:function(E,U){function M(Y){function d(e){return e.replace(new RegExp("[ \t\r\n]+","g"),"").toLowerCase()==""
}if(Y.getElementsByTagName("img").length>0){return false
}if(Y.getElementsByTagName("table").length>0){return false
}if(Y.getElementsByTagName("hr").length>0){return false
}var Z=tinyMCE.getNodeTree(Y,new Array(),3);
for(var a=0;
a<Z.length;
a++){if(!d(Z[a].nodeValue)){return false
}}return true
}var X=E.getDoc();
var P=E.getSel();
var H=E.contentWindow;
var K=P.getRangeAt(0);
var L=X.body;
var D=X.documentElement;
var Q="P";
var B=X.createRange();
B.setStart(P.anchorNode,P.anchorOffset);
B.collapse(true);
var T=X.createRange();
T.setStart(P.focusNode,P.focusOffset);
T.collapse(true);
var A=B.compareBoundaryPoints(B.START_TO_END,T)<0;
var W=A?P.anchorNode:P.focusNode;
var R=A?P.anchorOffset:P.focusOffset;
var O=A?P.focusNode:P.anchorNode;
var I=A?P.focusOffset:P.anchorOffset;
W=W.nodeName=="BODY"?W.firstChild:W;
O=O.nodeName=="BODY"?O.firstChild:O;
var J=tinyMCE.getParentBlockElement(W);
var F=tinyMCE.getParentBlockElement(O);
if(J&&new RegExp("absolute|relative|static","gi").test(J.style.position)){J=null
}if(F&&new RegExp("absolute|relative|static","gi").test(F.style.position)){F=null
}if(J!=null){Q=J.nodeName;
if(Q=="TD"||Q=="TABLE"||(Q=="DIV"&&new RegExp("left|right","gi").test(J.style.cssFloat))){Q="P"
}}if(tinyMCE.getParentElement(J,"OL,UL")!=null){return false
}if((J!=null&&J.nodeName=="TABLE")||(F!=null&&F.nodeName=="TABLE")){J=F=null
}var C=(J!=null&&J.nodeName==Q)?J.cloneNode(false):X.createElement(Q);
var G=(F!=null&&F.nodeName==Q)?F.cloneNode(false):X.createElement(Q);
if(/^(H[1-6])$/.test(Q)){G=X.createElement("p")
}var V=W;
var N=O;
node=V;
do{if(node==L||node.nodeType==9||tinyMCE.isBlockElement(node)){break
}V=node
}while((node=node.previousSibling?node.previousSibling:node.parentNode));
node=N;
do{if(node==L||node.nodeType==9||tinyMCE.isBlockElement(node)){break
}N=node
}while((node=node.nextSibling?node.nextSibling:node.parentNode));
if(V.nodeName=="TD"){V=V.firstChild
}if(N.nodeName=="TD"){N=N.lastChild
}if(J==null){K.deleteContents();
P.removeAllRanges();
if(V!=D&&N!=D){B=K.cloneRange();
if(V==L){B.setStart(V,0)
}else{B.setStartBefore(V)
}C.appendChild(B.cloneContents());
if(N.parentNode.nodeName==Q){N=N.parentNode
}K.setEndAfter(N);
if(N.nodeName!="#text"&&N.nodeName!="BODY"){B.setEndAfter(N)
}var S=K.cloneContents();
if(S.firstChild&&(S.firstChild.nodeName==Q||S.firstChild.nodeName=="BODY")){G.innerHTML=S.firstChild.innerHTML
}else{G.appendChild(S)
}if(M(C)){C.innerHTML="&nbsp;"
}if(M(G)){G.innerHTML="&nbsp;"
}K.deleteContents();
T.deleteContents();
B.deleteContents();
G.normalize();
B.insertNode(G);
C.normalize();
B.insertNode(C)
}else{L.innerHTML="<"+Q+">&nbsp;</"+Q+"><"+Q+">&nbsp;</"+Q+">";
G=L.childNodes[1]
}E.selection.selectNode(G,true,true);
return true
}if(V.nodeName==Q){B.setStart(V,0)
}else{B.setStartBefore(V)
}B.setEnd(W,R);
C.appendChild(B.cloneContents());
T.setEndAfter(N);
T.setStart(O,I);
var S=T.cloneContents();
if(S.firstChild&&S.firstChild.nodeName==Q){G.innerHTML=S.firstChild.innerHTML
}else{G.appendChild(S)
}if(M(C)){C.innerHTML="&nbsp;"
}if(M(G)){G.innerHTML="&nbsp;"
}var K=X.createRange();
if(!V.previousSibling&&V.parentNode.nodeName.toUpperCase()==Q){K.setStartBefore(V.parentNode)
}else{if(B.startContainer.nodeName.toUpperCase()==Q&&B.startOffset==0){K.setStartBefore(B.startContainer)
}else{K.setStart(B.startContainer,B.startOffset)
}}if(!N.nextSibling&&N.parentNode.nodeName.toUpperCase()==Q){K.setEndAfter(N.parentNode)
}else{K.setEnd(T.endContainer,T.endOffset)
}K.deleteContents();
K.insertNode(G);
K.insertNode(C);
G.normalize();
C.normalize();
E.selection.selectNode(G,true,true);
return true
},_handleBackSpace:function(D){var C=D.getRng(),E=C.startContainer,A,B=false;
if(E&&E.nextSibling&&E.nextSibling.nodeName=="BR"){A=E.nodeValue;
if(A!=null&&A.length>=C.startOffset&&A.charAt(C.startOffset-1)==" "){B=true
}if(A!=null&&C.startOffset==A.length){E.nextSibling.parentNode.removeChild(E.nextSibling)
}}return B
}};
function TinyMCE_Layer(B,A){this.id=B;
this.blockerElement=null;
this.events=false;
this.element=null;
this.blockMode=typeof (A)!="undefined"?A:true;
this.doc=document
}TinyMCE_Layer.prototype={moveRelativeTo:function(J,A){var H=this.getAbsPosition(J);
var I=parseInt(J.offsetWidth);
var C=parseInt(J.offsetHeight);
var D=this.getElement();
var E=parseInt(D.offsetWidth);
var B=parseInt(D.offsetHeight);
var G,F;
switch(A){case"tl":G=H.absLeft;
F=H.absTop;
break;
case"tr":G=H.absLeft+I;
F=H.absTop;
break;
case"bl":G=H.absLeft;
F=H.absTop+C;
break;
case"br":G=H.absLeft+I;
F=H.absTop+C;
break;
case"cc":G=H.absLeft+(I/2)-(E/2);
F=H.absTop+(C/2)-(B/2);
break
}this.moveTo(G,F)
},moveBy:function(A,C){var B=this.getElement();
this.moveTo(parseInt(B.style.left)+A,parseInt(B.style.top)+C)
},moveTo:function(A,C){var B=this.getElement();
B.style.left=A+"px";
B.style.top=C+"px";
this.updateBlocker()
},resizeBy:function(A,B){var C=this.getElement();
this.resizeTo(parseInt(C.style.width)+A,parseInt(C.style.height)+B)
},resizeTo:function(A,B){var C=this.getElement();
if(A!=null){C.style.width=A+"px"
}if(B!=null){C.style.height=B+"px"
}this.updateBlocker()
},show:function(){this.getElement().style.display="block";
this.updateBlocker()
},hide:function(){this.getElement().style.display="none";
this.updateBlocker()
},isVisible:function(){return this.getElement().style.display=="block"
},getElement:function(){if(!this.element){this.element=this.doc.getElementById(this.id)
}return this.element
},setBlockMode:function(A){this.blockMode=A
},updateBlocker:function(){var E,B,A,F,C,D;
B=this.getBlocker();
if(B){if(this.blockMode){E=this.getElement();
A=this.parseInt(E.style.left);
F=this.parseInt(E.style.top);
C=this.parseInt(E.offsetWidth);
D=this.parseInt(E.offsetHeight);
B.style.left=A+"px";
B.style.top=F+"px";
B.style.width=C+"px";
B.style.height=D+"px";
B.style.display=E.style.display
}else{B.style.display="none"
}}},getBlocker:function(){var B,A;
if(!this.blockerElement&&this.blockMode){B=this.doc;
A=B.createElement("iframe");
A.style.cssText="display: none; position: absolute; left: 0; top: 0";
A.src="javascript:false;";
A.frameBorder="0";
A.scrolling="no";
B.body.appendChild(A);
this.blockerElement=A
}return this.blockerElement
},getAbsPosition:function(B){var A={absLeft:0,absTop:0};
while(B){A.absLeft+=B.offsetLeft;
A.absTop+=B.offsetTop;
B=B.offsetParent
}return A
},create:function(E,D,B){var C=this.doc,A=C.createElement(E);
A.setAttribute("id",this.id);
if(D){A.className=D
}if(!B){B=C.body
}B.appendChild(A);
return this.element=A
},parseInt:function(A){if(A==null||A==""){return 0
}return parseInt(A)
}};
function TinyMCE_Menu(){var A;
if(typeof (tinyMCE.menuCounter)=="undefined"){tinyMCE.menuCounter=0
}A="mc_menu_"+tinyMCE.menuCounter++;
TinyMCE_Layer.call(this,A,true);
this.id=A;
this.items=new Array();
this.needsUpdate=true
}TinyMCE_Menu.prototype=tinyMCE.extend(TinyMCE_Layer.prototype,{init:function(A){var B;
this.settings={separator_class:"mceMenuSeparator",title_class:"mceMenuTitle",disabled_class:"mceMenuDisabled",menu_class:"mceMenu",drop_menu:true};
for(B in A){this.settings[B]=A[B]
}this.create("div",this.settings.menu_class)
},clear:function(){this.items=new Array()
},addTitle:function(A){this.add({type:"title",text:A})
},addDisabled:function(A){this.add({type:"disabled",text:A})
},addSeparator:function(){this.add({type:"separator"})
},addItem:function(A,B){this.add({text:A,js:B})
},add:function(A){this.items[this.items.length]=A;
this.needsUpdate=true
},update:function(){var F=this.getElement(),E="",C,B,A=this.items,D=this.settings;
if(this.settings.drop_menu){E+='<span class="mceMenuLine"></span>'
}E+='<table border="0" cellpadding="0" cellspacing="0">';
for(C=0;
C<A.length;
C++){B=tinyMCE.xmlEncode(A[C].text);
c=A[C].class_name?' class="'+A[C].class_name+'"':"";
switch(A[C].type){case"separator":E+='<tr class="'+D.separator_class+'"><td>';
break;
case"title":E+='<tr class="'+D.title_class+'"><td><span'+c+">"+B+"</span>";
break;
case"disabled":E+='<tr class="'+D.disabled_class+'"><td><span'+c+">"+B+"</span>";
break;
default:E+='<tr><td><a href="javascript:void(0);" onmousedown="'+tinyMCE.xmlEncode(A[C].js)+';return false;"><span'+c+">"+B+"</span></a>"
}E+="</td></tr>"
}E+="</table>";
F.innerHTML=E;
this.needsUpdate=false;
this.updateBlocker()
},show:function(){var A,B;
if(tinyMCE.lastMenu==this){return 
}if(this.needsUpdate){this.update()
}if(tinyMCE.lastMenu&&tinyMCE.lastMenu!=this){tinyMCE.lastMenu.hide()
}TinyMCE_Layer.prototype.show.call(this);
if(!tinyMCE.isOpera){}tinyMCE.lastMenu=this
}});
TinyMCE_Engine.prototype.debug=function(){var A="",D,B,C;
D=document.getElementById("tinymce_debug");
if(!D){var E=document.createElement("div");
E.setAttribute("className","debugger");
E.className="debugger";
E.innerHTML='Debug output:<textarea id="tinymce_debug" style="width: 100%; height: 300px" wrap="nowrap" mce_editable="false"></textarea>';
document.body.appendChild(E);
D=document.getElementById("tinymce_debug")
}B=this.debug.arguments;
for(C=0;
C<B.length;
C++){A+=B[C];
A+=B[C];
if(C<B.length-1){A+=", "
}}D.value+=A+"\n"
};