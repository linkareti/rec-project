function WikiEditor(){this._instance=null;
this._wsFilters=new Array();
this._wsReplace=new Array();
this._htmlFilters=new Array();
this._htmlReplace=new Array();
this._toolbarGenerators=new Array();
this._toolbarHandlers=new Array();
this._fixCommands=new Array();
this._commands=new Array();
var B=document.getElementsByTagName("script");
for(var A=0;
A<B.length;
A++){if(B[A].src&&(B[A].src.indexOf("wiki_editor.js")!=-1)){var C=B[A].src;
this.srcMode=(C.indexOf("_src")!=-1)?"_src":"";
C=C.substring(0,C.lastIndexOf("/"));
this.baseURL=C;
break
}}this.scriptsBaseURL=this.baseURL.substring(0,this.baseURL.lastIndexOf("/"))
}WikiEditor.prototype.init=function(C){this._imagePath="";
this._commandIntercept=false;
this._interceptedCommand="";
this._interceptedNode=null;
this._interceptedEditor="";
this._htmlTagRemover="__removeHtmlTags";
this.core=tinyMCE;
this._theme="default";
this._loadedPlugins=new Array();
this._useStyleToolbar=false;
if(C.plugins==null){C.plugins=""
}if(C.plugins.indexOf("wikieditor")==-1){C.plugins+=",wikiplugin"
}C.theme="wikieditor";
C.extended_valid_elements="li[class|dir<ltr?rtl|id|lang|onclick|ondblclick|onkeydown|onkeypress|onkeyup|onmousedown|onmousemove|onmouseout|onmouseover|onmouseup|style|title|type|value|wikieditorlistdepth|wikieditorlisttype]";
C.relative_urls=true;
C.remove_linebreaks=false;
if(C.use_linkeditor_tabs==null){C.use_linkeditor_tabs="wiki_tab, web_tab, attachments_tab, email_tab, file_tab"
}if((C.wiki_editor_toolbar==null)||(C.wiki_editor_toolbar.toString()=="")){C.wiki_editor_toolbar="texttoolbar, justifytoolbar, listtoolbar, indenttoolbar, undotoolbar, titletoolbar, styletoolbar, horizontaltoolbar, symboltoolbar, attachmenttoolbar, macrostoolbar, tabletoolbar, tablerowtoolbar, tablecoltoolbar, linktoolbar, togglebutton"
}this.setImagePath((C.wiki_images_path==null)?"":C.wiki_images_path);
if(C.wiki_theme&&C.wiki_theme!=""){this._theme=C.wiki_theme
}if(C.wiki_use_style=="true"){this._useStyleToolbar=C.wiki_use_style
}this.core.init(C);
this.core.loadScript(this.baseURL+"/themes/"+this._theme+".js");
if(C.wiki_plugins&&C.wiki_plugins!=""){var A=C.wiki_plugins.split(/\s*,\s*/i);
for(var B=0;
B<A.length;
B++){this.core.loadScript(this.baseURL+"/plugins/"+A[B]+".js");
this._loadedPlugins.push(A[B])
}}};
WikiEditor.prototype.getContent=function(){return this.core.getContent()
};
WikiEditor.prototype.setContent=function(A){this.core.setContent(A)
};
WikiEditor.prototype.triggerSave=function(B,A){this.core.triggerSave(B,A)
};
WikiEditor.prototype.updateContent=function(A){this.core.updateContent(A)
};
WikiEditor.prototype.isPluginLoaded=function(A){for(var B=0;
B<this._loadedPlugins.length;
B++){if(this._loadedPlugins[B]==A){return true
}}return false
};
WikiEditor.prototype.setHtmlTagRemover=function(A){this._htmlTagRemover=A
};
WikiEditor.prototype.addExternalProcessor=function(A,B){this._wsFilters.push(A);
this._wsReplace.push(B)
};
WikiEditor.prototype.addExternalProcessorBefore=function(A,B,C){this._insertBefore(this._wsFilters,this._wsReplace,A,B,C)
};
WikiEditor.prototype.addInternalProcessor=function(A,B){this._htmlFilters.push(A);
this._htmlReplace.push(B)
};
WikiEditor.prototype.addInternalProcessorBefore=function(A,B,C){this._insertBefore(this._htmlFilters,this._htmlReplace,A,B,C)
};
WikiEditor.prototype._insertBefore=function(D,B,A,G,F){var C=0;
for(var E=0;
E<B.length;
E++){if(B[E]==A){C=E;
break
}}for(var E=B.length;
E>C;
E--){B[E]=B[E-1];
D[E]=D[E-1]
}D[C]=G;
B[C]=F
};
WikiEditor.prototype.addFixCommand=function(A,B){this._fixCommands[A]=B
};
WikiEditor.prototype.addCommand=function(A,B){this._commands[A]=B
};
WikiEditor.prototype.addToolbarGenerator=function(A){this._toolbarGenerators.push(A)
};
WikiEditor.prototype.addToolbarHandler=function(A){this._toolbarHandlers.push(A)
};
WikiEditor.prototype.setImagePath=function(A){this._imagePath=A
};
WikiEditor.prototype.getImagePath=function(){return tinyMCE.settings.wiki_images_path
};
WikiEditor.prototype.dummyCommand=function(D,A,C,E,B){this.core.triggerNodeChange();
return true
};
WikiEditor.prototype.execCommand=function(D,A,C,E,B){if(this._fixCommands[C]!=null){this._commandIntercept=true;
this._interceptedCommand=C;
this._interceptedEditor=D
}if(this._commands[C]&&this[this._commands[C]]){return this[this._commands[C]](D,A,C,E,B)
}return false
};
WikiEditor.prototype.executedCommand=function(A){if(A==this._interceptedCommand&&this._interceptedNode){this[this._fixCommands[A]](this._interceptedEditor,this._interceptedNode);
this._interceptedCommand="";
this._interceptedNode=null
}};
WikiEditor.prototype.handleNodeChange=function(G,E,F,D,A,C){this._cleanNode(G,E);
if(this._commandIntercept){this._commandIntercept=false;
this._interceptedNode=E
}for(var B=0;
B<this._toolbarHandlers.length;
B++){if(this[this._toolbarHandlers[B]]){this[this._toolbarHandlers[B]](G,E,F,D,A,C)
}}};
WikiEditor.prototype.getControlHTML=function(A){var C="";
for(var B=0;
B<this._toolbarGenerators.length;
B++){if(this[this._toolbarGenerators[B]]&&(C=this[this._toolbarGenerators[B]](A))!=""){break
}}return C
};
WikiEditor.prototype.convertExternal=function(D){var F,C;
var A;
var G;
D=D.replace(/\$(\d)/g,"&#036;$1");
for(var B=0;
B<this._wsFilters.length;
B++){RegExp.lastIndex=0;
G=-1;
F=this._wsFilters[B];
while((C=F.exec(D))){if(C.index<=G){break
}RegExp.lastIndex=G=C.index;
var E=this._wsReplace[B];
if(this[E]){D=this[E](F,C,D)
}else{D=D.replace(F,E)
}}}D=unescape(D);
D=D.replace(/\\<(.*?)\\>/g,"\\&lt;$1\\&gt;");
return D
};
WikiEditor.prototype.convertInternal=function(D){var F,C;
var A;
var G;
D=D.replace(/\$(\d)/g,"&#036;$1");
for(var B=0;
B<this._htmlFilters.length;
B++){RegExp.lastIndex=0;
G=-1;
F=this._htmlFilters[B];
while((C=F.exec(D))){if(C.index<=G){break
}RegExp.lastIndex=G=C.index;
var E=this._htmlReplace[B];
if(this[E]){D=this[E](F,C,D)
}else{D=D.replace(F,E)
}}}D=this.trimString(this._removeHtmlTags(D));
D=unescape(D);
D=D.replace(/\&#036;/g,"$");
D=D.replace(/\\<((\/)*blockquote)\\>/g,"<$1>");
D=D.replace(/<blockquote>((<(\/)?blockquote>)|(\s*))*<\/blockquote>/g,"");
D=D.replace(/[\r\n]{4,}/g,"\r\n\r\n");
if(D.substring(D.length-2)=="\\\\"){D=D.substring(0,D.lastIndexOf("\\\\"))
}return D
};
WikiEditor.prototype.tagListInternal=function(B){for(var A=0;
B.childNodes[A];
A++){if(B.childNodes[A].nodeType==1){switch(B.childNodes[A].nodeName.toLowerCase()){case"ul":case"ol":this._tagListInternal(B.childNodes[A],1);
break;
default:this.tagListInternal(B.childNodes[A])
}}}return B
};
WikiEditor.prototype._tagListInternal=function(B,D){var C="";
for(var A=0;
B.childNodes[A];
A++){if(B.childNodes[A].nodeType==1){switch(B.childNodes[A].nodeName.toLowerCase()){case"ul":case"ol":C+=this._tagListInternal(B.childNodes[A],D+1);
break;
case"li":B.childNodes[A].setAttribute("wikieditorlisttype",B.nodeName.toLowerCase());
B.childNodes[A].setAttribute("wikieditorlistdepth",D);
break
}}}return C
};
WikiEditor.prototype.replaceMatchingTag=function(E,K,G){var H=new RegExp("<"+K+"[^>]*>","gi");
var I=new RegExp("</s*"+K+"[^>]*>","gi");
var B=H.exec(E);
var C=I.exec(E);
var F=this._getResultIndex(B);
var D=this._getResultIndex(C);
var A=F;
var J=new Array();
if(F>-1&&D>-1&&F<D){do{RegExp.lastIndex=A+B[0].length;
B=H.exec(E);
A=this._getResultIndex(B);
if(A==-1){break
}else{if(A<D){RegExp.lastIndex=D+C[0].length;
C=I.exec(E);
D=this._getResultIndex(C)
}else{break
}}}while(true);
J.start=F;
J.end=D+C[0].length;
if(typeof (G)=="undefined"){J.string=E
}else{J.string=E.substring(0,F)+G+E.substring(D+C[0].length,E.length)
}}else{J.start=J.end=-1;
J.string=E
}return J
};
WikiEditor.prototype._getResultIndex=function(A){return(A==null)?-1:A.index
};
WikiEditor.prototype.readAttributes=function(F){var D=/\s*\w+\s*=\s*"[^"]*"\s*/gi;
var H=/\s*(\w+)\s*=\s*"([^"]*)"\s*/i;
var A=F.match(D);
var B=new Array();
var G=0;
if(A!=null){for(var C=0;
C<A.length;
C++){var E=H.exec(A[C]);
G++;
B[E[1].toLowerCase()]=E[2]
}}return(G>0)?B:null
};
WikiEditor.prototype.trimString=function(C){var A=/(\S+(\s+\S+)*)+/i;
var B=A.exec(C);
return(B&&B[1])?B[1]:""
};
WikiEditor.prototype.buildString=function(C,D){var A="";
for(var B=0;
B<D;
B++){A+=C
}return A
};
WikiEditor.prototype._removeHtmlTags=function(A){return this[this._htmlTagRemover](A)
};
WikiEditor.prototype.__removeHtmlTags=function(B){var A=/<[^>]*>/g;
return B.replace(A,"")
};
WikiEditor.prototype._escapeText=function(G){var F="";
var C;
var D="";
var H="";
var A="";
for(var B=0;
B<256;
B++){A=B.toString(16);
if(A.length<2){A="0"+A
}H+=A;
D+=unescape("%"+A)
}H=H.toUpperCase();
G.replace(String.fromCharCode(13)+"","%13");
for(var E=0;
E<G.length;
E++){C=G.substr(E,1);
for(var B=0;
B<D.length;
B++){if(C==D.substr(B,1)){C=C.replace(D.substr(B,1),"%"+H.substr(B*2,2));
B=D.length
}}F+=C
}return F
};
WikiEditor.prototype.trimRNString=function(A){A=A.replace(/(^(\r|\n))|((\r|\n)$)/gi,"");
if(A.lastIndexOf("\r")==(A.length-1)){A=A.substring(0,A.length-1)
}if(A.indexOf("\n")==0){A=A.substring(1,A.length)
}return A
};
WikiEditor.prototype.toolbars=["texttoolbar","justifytoolbar","listtoolbar","indenttoolbar","undotoolbar","titletoolbar","styletoolbar","horizontaltoolbar","symboltoolbar","attachmenttoolbar","macrostoolbar","tabletoolbar","tablerowtoolbar","tablecoltoolbar","linktoolbar","suptoolbar"];
WikiEditor.prototype.disableButtonsInWikiMode=function(C){for(var A=0;
A<this.toolbars.length;
A++){var B=document.getElementById(C+"_"+this.toolbars[A]);
if(B){B.style.display="none"
}}};
WikiEditor.prototype.showButtonsInWywisygMode=function(C){for(var A=0;
A<this.toolbars.length;
A++){var B=document.getElementById(C+"_"+this.toolbars[A]);
if(B){B.style.display="inline"
}}};
wikiEditor=new WikiEditor();