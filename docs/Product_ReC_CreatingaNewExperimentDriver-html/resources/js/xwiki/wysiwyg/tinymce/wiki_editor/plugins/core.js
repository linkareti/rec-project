WikiEditor.prototype.initCorePlugin=function(){this.addExternalProcessor((/\{code(.*?)\}([\s\S]+?)\{code\}/i),"convertCodeMacroExternal");
this.addInternalProcessor(/<div\s*([^>]*)(class=\"code\")\s*([^>]*)>\s*<pre>([\s\S]+?)<\/pre>\s*<\/div>/i,"convertCodeMacroInternal");
this.addExternalProcessor((/^\s*(1(\.1)*)\s+([^\r\n]*)$/im),"convertHeadingExternal");
this.addInternalProcessor((/<h([1-7])\s*[^>]*?>\s*<span>([\s\S]+?)<\/span><\/h[1-7]>/i),"convertHeadingInternal");
this.addInternalProcessor((/<p[^>]*>&nbsp;?<\/p>/gi),"\\\\\r\n");
this.addExternalProcessor((/\\\\(\r?)(\n?)/gi),"<br />$1$2");
this.addExternalProcessor((/\\\\/gi),"<br />");
this.addExternalProcessor((/----(-*)/i),"convertHRExternal");
this.addInternalProcessor((/<hr(.*?)>/i),"convertHRInternal");
this.addExternalProcessor((/^\s*(\*+)\s+([^\r\n]+)$/im),"convertListExternal");
this.addExternalProcessor((/^\s*(#)\s+([^\r\n]+)$/im),"convertListExternal");
this.addExternalProcessor((/^\s*(1+)\.\s+([^\r\n]+)$/im),"convertListExternal");
this.addExternalProcessor((/^\s*(\-+)\s+([^\r\n]+)$/im),"convertListExternal");
this.addInternalProcessor((/\s*<(ul|ol)\s*([^>]*)>/i),"convertListInternal");
this.addExternalProcessor((/<%([\s\S]+?)%>/ig),"&lt;%$1%&gt;");
this.addExternalProcessor((/((\s|\S)*)/i),"convertParagraphExternal");
this.addInternalProcessor((/<p(.*?)>([\s\S]+?)<\/p>/i),"convertParagraphInternal");
this.addInternalProcessor((/(<br\s*\/>|<br\s*>)(\s*\r*\n*)/gi),"\\\\$2");
this.addExternalProcessor((/\[(.*?)((>|\|)(.*?))?((>|\|)(.*?))?\]/i),"convertLinkExternal");
this.addInternalProcessor((/<a\s*([^>]*)>(.*?)<\/a>/i),"convertLinkInternal");
this.addExternalProcessor((/\{table\}([\s\S]+?)\{table\}/i),"convertTableExternal");
this.addInternalProcessor((/<table\s*([^>]*)class=\"wiki-table\"\s*([^>]*)>([\s\S]+?)<\/table>/i),"convertTableInternal");
this.addExternalProcessor((/\*(\s*)(.+?)(\s*)\*/gi),'$1<strong class="bold">$2</strong>$3');
this.addInternalProcessor((/<strong[^>]*>(\s*)(.*?)(\s*)<\/strong>/i),"convertBoldTextInternal");
this.addInternalProcessor((/<b[^>]*>(\s*)(.*?)(\s*)<\/b>/i),"convertBoldTextInternal");
this.addExternalProcessor((/~~(\s*)(.+?)(\s*)~~/gi),'$1<em class="italic">$2</em>$3');
this.addInternalProcessor((/<em[^>]*>(\s*)(.*?)(\s*)<\/em>/i),"convertItalicTextInternal");
this.addInternalProcessor((/<i[^>]*>(\s*)(.*?)(\s*)<\/i>/i),"convertItalicTextInternal");
this.addExternalProcessor((/__(\s*)(.+?)(\s*)__/gi),"$1<u>$2</u>$3");
this.addInternalProcessor((/<u[^>]*>(\s*)(.*?)(\s*)<\/u>/i),"convertUnderLineTextInternal");
this.addExternalProcessor((/--(\s*)(.+?(\s*))--/gi),'$1<strike class="strike">$2</strike>$3');
this.addInternalProcessor((/<strike[^>]*>(\s*)(.*?)(\s*)<\/strike>/i),"convertStrikeTextInternal");
this.addInternalProcessor((/[#$][a-zA-Z0-9-_.]+\(([^&)]*&quot;[^)]*)+?\)/i),"convertVelocityScriptsInternal");
this.addInternalProcessor((/&lt;%([\s\S]+?)%&gt;/i),"convertGroovyScriptsInternal");
this.addExternalProcessorBefore("convertParagraphExternal",(/##([^\r\n]*)$|(#\*([\s\S]+?)\*#)/im),"convertVelocityCommentExternal");
this.addInternalProcessorBefore("convertStyleInternal",(/<div\s*([^>]*)class=\"vcomment\"\s*([^>]*)>([\s\S]+?)(\r?\n?)<\/div>/i),"convertVelocityCommentInternal");
this.addExternalProcessorBefore("convertLinkExternal",(/\r?\n?\{style:\s*(.*?)\}\r?\n?([\s\S]+?)\r?\n?\{style\}/i),"convertStyleExternal");
this.addInternalProcessorBefore("convertTableInternal",(/<(font|span|div)\s*(.*?)>\r?\n?([\s\S]+?)\r?\n?\<\/(font|span|div)>/i),"convertStyleInternal");
this.setHtmlTagRemover("removeHtmlTags_Groovy");
this.setHtmlTagRemover("removeSpecialHtmlTags");
this.addToolbarHandler("handleTextButtons");
this.addToolbarHandler("handleListButtons");
this.addToolbarHandler("handleIndentButtons");
this.addToolbarHandler("handleUndoButtons");
this.addToolbarHandler("handleTitlesList");
this.addToolbarHandler("handleStylesList");
this.addToolbarHandler("handleLinkButtons");
this.addToolbarHandler("handleHorizontalRuleButtons");
this.addToolbarHandler("handleSupAndSubButons");
this.addToolbarHandler("handleTableButtons");
this.addToolbarHandler("handleAlignButtons");
this.addCommand("Title","titleCommand");
this.addFixCommand("Title","fixTitle");
this.addFixCommand("InsertUnorderedList","fixInsertUnorderedList");
this.addFixCommand("Indent","fixInsertUnorderedList")
};
wikiEditor.initCorePlugin();
WikiEditor.prototype.removeHtmlTags_Groovy=function(B){var A=/<[^%][^>]*>/i;
return B.replace(A,"")
};
WikiEditor.prototype.removeSpecialHtmlTags=function(A){A=A.replace(/<span class="(wikilink|wikiexternallink)">\s*([\s\S]+?)<\/span>/g,"$2");
A=A.replace(/<span class="(bold|italic|underline|strike)">([\s\S]+?)<\/span>/g,"$2");
return A
};
WikiEditor.prototype.convertVelocityScriptsInternal=function(D,A,C){var B=/&quot;/gi;
return C.replace(D,A[0].replace(B,'"'))
};
WikiEditor.prototype.convertGroovyScriptsInternal=function(D,A,C){var B=/&quot;/gi;
var E="<%"+A[1].replace(B,'"')+"%>";
return C.replace(D,E)
};
WikiEditor.prototype.convertBoldTextInternal=function(C,A,B){var D=A[1];
if(A[2]!=""){D+="*"+A[2]+"*"
}D+=A[3];
return B.replace(C,D)
};
WikiEditor.prototype.convertItalicTextInternal=function(C,A,B){var D=A[1];
if(A[2]!=""){D+="~~"+A[2]+"~~"
}D+=A[3];
return B.replace(C,D)
};
WikiEditor.prototype.convertUnderLineTextInternal=function(C,A,B){var D=A[1];
if(A[2]!=""){D+="__"+A[2]+"__"
}D+=A[3];
return B.replace(C,D)
};
WikiEditor.prototype.convertStrikeTextInternal=function(C,A,B){var D=A[1];
if(A[2]!=""){D+="--"+A[2]+"--"
}D+=A[3];
return B.replace(C,D)
};
WikiEditor.prototype.utf8decode=function(A){var B="";
var C=0;
var D=c1=c2=0;
while(C<A.length){D=A.charCodeAt(C);
if(D<128){B+=String.fromCharCode(D);
C++
}else{if((D>191)&&(D<224)){c2=A.charCodeAt(C+1);
B+=String.fromCharCode(((D&31)<<6)|(c2&63));
C+=2
}else{c2=A.charCodeAt(C+1);
c3=A.charCodeAt(C+2);
B+=String.fromCharCode(((D&15)<<12)|((c2&63)<<6)|(c3&63));
C+=3
}}}return B
};
WikiEditor.prototype.convertLinkInternal=function(F,I,E){var B;
var H="";
var A;
var D;
var C=">";
if((B=this.trimString(I[2]))!=""){var G=this.readAttributes(I[1]);
if(G&&G.id){C="|"
}if(G&&G.href){A=this.trimString(G.href);
if(G.title&&G.title!=""){A=this.trimString(G.title)
}A=unescape(A);
A=this.utf8decode(A);
if((A.toLowerCase()==B.toLowerCase())&&(!G.target||(G.target=="_self"))){H="["+B+"]"
}else{if(G.target&&G.target!="_self"){D=this.trimString(G.target);
H="["+B+C+A+C+D+"]"
}else{H="["+B+C+A+"]"
}}}}else{H=I[2]
}return E.replace(F,H)
};
WikiEditor.prototype.convertTableInternal=function(D,K,C){var I=this.trimRNString(K[3]);
var E="";
if(tinyMCE.isMSIE){E+="\r\n"
}E+="{table}";
var J=I.split("</tr>");
for(var B=0;
B<(J.length-1);
B++){var G="";
if(B==0){G=J[0].replace(/(.*?)<tr(.*?)>/g,"")
}else{G=J[B].replace(/<tr(.*?)>/g,"")
}var F=G.split("</td>");
for(var A=0;
A<(F.length-1);
A++){var H=this.trimRNString(F[A].replace(/<td(.*?)>/g,""));
H=H.replace(/\r/gi,"");
H=H.replace(/\n/gi,"");
if((H.lastIndexOf("\\\\")>=0)&&(H.lastIndexOf("\\\\")==(H.length-2))){H+=" "
}if(H==""){H="&nbsp;"
}if(A==0){E+="\r\n"+H
}else{E+="|"+H
}}}E+="\r\n{table}";
if(tinyMCE.isMSIE){E+="\r\n"
}return C.replace(D,E)
};
WikiEditor.prototype.convertHeadingInternal=function(D,B,C){var E="";
var A;
if((A=this.trimString(B[2]))!=""){var F=B[1];
E="\r\n1";
E+=this.buildString(".1",F-1);
E+=" "+A
}E+="\r\n";
return C.replace(D,E)
};
WikiEditor.prototype.fixTitle=function(G,E){var F="";
if(this._titleChangeValue==0){this.core.execInstanceCommand(G,"mceRemoveNode",false,E)
}else{if(this._titleChangeValue==6){this.core.execInstanceCommand(G,"FormatBlock",false,"<div>");
var D=tinyMCE.selectedInstance.selection.getFocusElement();
D.className="code";
var A=tinyMCE.selectedInstance.selection.getBookmark();
var H=tinyMCE.selectedInstance.getDoc().createElement("<pre>");
var B=D.childNodes;
for(var C=0;
C<B.length;
C++){H.appendChild(B[C].cloneNode(true));
D.removeChild(B[C],true)
}D.appendChild(H);
tinyMCE.selectedInstance.selection.moveToBookmark(A)
}else{if(this._titleChangeValue==7){this.core.execInstanceCommand(G,"FormatBlock",false,"<div>");
var D=tinyMCE.selectedInstance.selection.getFocusElement();
D.className="vcomment"
}else{F="<h"+this._titleChangeValue+">";
this.core.execInstanceCommand(G,"FormatBlock",false,F)
}}}tinyMCE.triggerNodeChange()
};
WikiEditor.prototype._substituteNode=function(D,B){var C=D.parentNode;
if(C){for(var A=0;
D.childNodes[A];
A++){B.appendChild(D.childNodes[A])
}if(D.nodeName.toLowerCase()=="body"){D.appendChild(B)
}else{C.insertBefore(B,D);
C.removeChild(D)
}}};
WikiEditor.prototype.titleCommand=function(D,A,C,E,B){this._titleChangeValue=B;
return this.dummyCommand()
};
WikiEditor.prototype.fixInsertUnorderedList=function(B,A){do{switch(A.nodeName.toLowerCase()){case"ul":A.className=this.LIST_NORMAL_CLASS_NAME;
break
}}while((A=A.parentNode))
};
WikiEditor.prototype._cleanNode=function(B,A){do{switch(A.nodeName.toLowerCase()){case"body":return ;
case"p":A.className="";
break;
case"h3":if(A.parentNode&&A.parentNode.nodeName.toLowerCase()=="body"){}break
}}while((A=A.parentNode))
};
WikiEditor.prototype._removeBlankParagraphs=function(A){do{if(A.nodeName.toLowerCase()=="body"){break
}}while((A=A.parentNode));
this.__removeBlankParagraphs(A)
};
WikiEditor.prototype.__removeBlankParagraphs=function(B){if(B.nodeName.toLowerCase()=="p"&&this.trimString(B.innerHTML)==""){B.parentNode.innerHTML+="<br />";
B.parentNode.removeChild(B);
return 
}for(var A=0;
B.childNodes[A];
A++){this.__removeBlankParagraphs(B.childNodes[A])
}};
WikiEditor.prototype._fixParagraph=function(A){if(A.className||A.className.toLowerCase()!=this.PARAGRAPH_CLASS_NAME.toLowerCase()){A.className=this.PARAGRAPH_CLASS_NAME
}};
WikiEditor.prototype._cleanBR=function(B){if(B.nodeName.toLowerCase()=="br"){B.parentNode.replaceChild(document.createTextNode("\\\\"),B);
return 
}for(var A=0;
B.childNodes[A];
A++){this._cleanBR(B.childNodes[A])
}};
WikiEditor.prototype.encodeNode=function(C){function B(D){D=""+D;
D=D.replace(/^(\s*)1/g,"$1&#49;");
D=D.replace(/\*/g,"&#42;");
D=D.replace(/\~/g,"&#126;");
D=D.replace(/\[/g,"&#91;");
D=D.replace(/\]/g,"&#93;");
D=D.replace(/\_/g,"&#95;");
D=D.replace(/\-/g,"&#45;");
return D
}function A(E){for(var D=0;
E.childNodes[D];
D++){A(E.childNodes[D])
}var F=B(E.nodeValue);
if(F!=E.nodeValue){}E.nodeValue=F
}A(C);
return C
};
WikiEditor.prototype.handleSupAndSubButons=function(F,D,E,C,A,B){tinyMCE.switchClass(F+"_sup","mceButtonNormal");
tinyMCE.switchClass(F+"_sub","mceButtonNormal");
switch(D.nodeName.toLowerCase()){case"sup":tinyMCE.switchClass(F+"_sup","mceButtonSelected");
break;
case"sub":tinyMCE.switchClass(F+"_sub","mceButtonSelected");
break
}};
WikiEditor.prototype.handleHorizontalRuleButtons=function(F,D,E,C,A,B){tinyMCE.switchClass(F+"_hr","mceButtonNormal");
if(D.nodeName=="HR"){tinyMCE.switchClass(F+"_hr","mceButtonSelected")
}};
WikiEditor.prototype.handleLinkButtons=function(G,E,F,D,B,C){tinyMCE.switchClass(G+"_link","mceButtonDisabled",true);
tinyMCE.switchClass(G+"_unlink","mceButtonDisabled",true);
var A=tinyMCE.getParentElement(E,"a","href");
if(A||C){tinyMCE.switchClass(G+"_link",A?"mceButtonSelected":"mceButtonNormal",false)
}if(A){tinyMCE.switchClass(G+"_unlink","mceButtonNormal",false)
}};
WikiEditor.prototype.handleTableButtons=function(F,D,E,C,A,B){tinyMCE.switchClass(F+"_table","mceButtonNormal",false)
};
WikiEditor.prototype.handleAlignButtons=function(I,C,H,D,G,E){tinyMCE.switchClass(I+"_justifyleft","mceButtonNormal");
tinyMCE.switchClass(I+"_justifyright","mceButtonNormal");
tinyMCE.switchClass(I+"_justifycenter","mceButtonNormal");
tinyMCE.switchClass(I+"_justifyfull","mceButtonNormal");
var B=C;
var F=false;
do{if(!B.getAttribute||!B.getAttribute("align")){continue
}switch(B.getAttribute("align").toLowerCase()){case"left":tinyMCE.switchClass(I+"_justifyleft","mceButtonSelected");
F=true;
break;
case"right":tinyMCE.switchClass(I+"_justifyright","mceButtonSelected");
F=true;
break;
case"middle":case"center":tinyMCE.switchClass(I+"_justifycenter","mceButtonSelected");
F=true;
break;
case"justify":tinyMCE.switchClass(I+"_justifyfull","mceButtonSelected");
F=true;
break
}}while(!F&&(B=B.parentNode)!=null);
var A=tinyMCE.getParentElement(C,"div");
if(A&&A.style.textAlign=="center"){tinyMCE.switchClass(I+"_justifycenter","mceButtonSelected")
}};
WikiEditor.prototype.handleTitlesList=function(K,B,J,D,F,E){var G=document.getElementById(K+"_titleSelect");
if(G){var H=this.core.getParentElement(B,"h1,h2,h3,h4,h5,h6,");
if(H){var C=H.className;
var A=(C.split("-").length)-1;
this._selectByValue(G,A)
}else{this._selectByValue(G,0)
}var I=this.core.getParentElement(B,"div");
if(I&&(I.className=="code")){this._selectByValue(G,6)
}if(I&&(I.className=="vcomment")){this._selectByValue(G,7)
}}};
WikiEditor.prototype.handleStylesList=function(J,B,I,C,G,D){var A=document.getElementById(J+"_fontSizeSelect");
if(A){var H=tinyMCE.getParentElement(B);
if(H){var L=tinyMCE.getAttrib(H,"size");
if(L==""){var K=new Array("","8px","10px","12px","14px","18px","24px","36px");
L=""+H.style.fontSize;
for(var F=0;
F<K.length;
F++){if((""+K[F])==L){L=F;
break
}}}if(!this._selectByValue(A,L)){this._selectByValue(A,"")
}}else{this._selectByValue(A,"0")
}}A=document.getElementById(J+"_fontNameSelect");
if(A){var H=tinyMCE.getParentElement(B);
if(H){var E=tinyMCE.getAttrib(H,"face");
if(E==""){E=""+H.style.fontFamily
}if(!this._selectByValue(A,E)){this._selectByValue(A,"")
}}else{this._selectByValue(A,"")
}}};
WikiEditor.prototype.handleIndentButtons=function(I,B,H,C,E,D){tinyMCE.switchClass(I+"_outdent","mceButtonDisabled",true);
tinyMCE.switchClass(I+"_indent","mceButtonNormal");
var A=this.core.getParentElement(B,"blockquote");
var F=this.core.getParentElement(B,"ul");
var G=this.core.getParentElement(B,"ol");
if(A||F||G){tinyMCE.switchClass(I+"_outdent","mceButtonNormal",false)
}};
WikiEditor.prototype.handleUndoButtons=function(F,D,E,C,A,B){if(C!=-1){tinyMCE.switchClass(F+"_undo","mceButtonDisabled",true);
tinyMCE.switchClass(F+"_redo","mceButtonDisabled",true)
}if(E!=-1&&(E<C-1&&C>0)){tinyMCE.switchClass(F+"_redo","mceButtonNormal",false)
}if(E!=-1&&(E>0&&C>0)){tinyMCE.switchClass(F+"_undo","mceButtonNormal",false)
}};
WikiEditor.prototype.handleListButtons=function(F,D,E,C,A,B){tinyMCE.switchClass(F+"_bullist","mceButtonNormal");
tinyMCE.switchClass(F+"_numlist","mceButtonNormal");
do{switch(D.nodeName.toLowerCase()){case"ul":tinyMCE.switchClass(F+"_bullist","mceButtonSelected");
tinyMCE.switchClass(F+"_outdent","mceButtonNormal",false);
tinyMCE.switchClass(F+"_indent","mceButtonNormal",false);
break;
case"ol":tinyMCE.switchClass(F+"_numlist","mceButtonSelected");
break
}}while((D=D.parentNode))
};
WikiEditor.prototype._selectByValue=function(A,C){if(A){for(var B=0;
B<A.options.length;
B++){if(A.options[B].value==C){A.selectedIndex=B;
return true
}}}return false
};
WikiEditor.prototype.handleTextButtons=function(F,D,E,C,A,B){this.core.switchClass(F+"_bold","mceButtonNormal");
this.core.switchClass(F+"_italic","mceButtonNormal");
this.core.switchClass(F+"_underline","mceButtonNormal");
this.core.switchClass(F+"_strikethrough","mceButtonNormal");
do{switch(D.nodeName.toLowerCase()){case"b":case"strong":this.core.switchClass(F+"_bold","mceButtonSelected");
break;
case"i":case"em":this.core.switchClass(F+"_italic","mceButtonSelected");
break;
case"u":this.core.switchClass(F+"_underline","mceButtonSelected");
break;
case"strike":this.core.switchClass(F+"_strikethrough","mceButtonSelected");
break
}}while((D=D.parentNode))
};
WikiEditor.prototype.getUndoToolbar=function(){return this.getUndoControls("undo")+this.getUndoControls("redo")
};
WikiEditor.prototype.getUndoControls=function(A){str="";
switch(A){case"undo":str=this.createButtonHTML("undo","undo.gif","lang_undo_desc","Undo");
break;
case"redo":str=this.createButtonHTML("redo","redo.gif","lang_redo_desc","Redo");
break
}return str
};
WikiEditor.prototype.getSymbolToolbar=function(){return this.getSymbolControls("charmap")
};
WikiEditor.prototype.getSymbolControls=function(A){var B="";
switch(A){case"charmap":B=this.createButtonHTML("charmap","charmap.gif","lang_theme_charmap_desc","mceCharMap");
break
}return B
};
WikiEditor.prototype.getJustifyToolbar=function(){return this.getJustifyControls("justifyleft")+this.getJustifyControls("justifycenter")+this.getJustifyControls("justifyright")+this.getJustifyControls("justifyfull")
};
WikiEditor.prototype.getJustifyControls=function(A){var B="";
switch(A){case"justifyleft":B=this.createButtonHTML("justifyleft","justifyleft.gif","lang_justifyleft_desc","JustifyLeft");
break;
case"justifycenter":B=this.createButtonHTML("justifycenter","justifycenter.gif","lang_justifycenter_desc","JustifyCenter");
break;
case"justifyright":B=this.createButtonHTML("justifyright","justifyright.gif","lang_justifyright_desc","JustifyRight");
break;
case"justifyfull":B=this.createButtonHTML("justifyfull","justifyfull.gif","lang_justifyfull_desc","JustifyFull");
break
}return B
};
WikiEditor.prototype.getToggleButton=function(){return this.createButtonHTML("code","switch.gif","lang_theme_switch_desc","mceToggleEditor")
};
WikiEditor.prototype.getSupAndSubToolbar=function(){return this.getSupAndSubControls("sup")+this.getSupAndSubControls("sub")
};
WikiEditor.prototype.getSupAndSubControls=function(A){var B="";
switch(A){case"sup":B=this.createButtonHTML("sup","sup.gif","lang_theme_sup_desc","superscript");
break;
case"sub":B=this.createButtonHTML("sub","sub.gif","lang_theme_sub_desc","subscript");
break
}return B
};
WikiEditor.prototype.getTabToolbar=function(){return this.getTabControls("outdent")+this.getTabControls("indent")
};
WikiEditor.prototype.getTabControls=function(A){var B="";
switch(A){case"outdent":B=this.createButtonHTML("outdent","outdent.gif","lang_outdent_desc","Outdent");
break;
case"indent":B=this.createButtonHTML("indent","indent.gif","lang_indent_desc","Indent");
break
}return B
};
WikiEditor.prototype.getLinkToolbar=function(){return this.getLinkControls("link")+this.getLinkControls("unlink")
};
WikiEditor.prototype.getLinkControls=function(A){var B="";
switch(A){case"link":B=this.createButtonHTML("link","link.gif","lang_link_desc","mceLink",true);
break;
case"unlink":B=this.createButtonHTML("unlink","unlink.gif","lang_unlink_desc","unlink");
break
}return B
};
WikiEditor.prototype.getHorizontalruleControls=function(){var A=this.createButtonHTML("hr","hr.gif","lang_theme_hr_desc","inserthorizontalrule");
return A
};
WikiEditor.prototype.getRemoveformatControls=function(){var A=this.createButtonHTML("removeformat","removeformat.gif","lang_theme_removeformat_desc","removeformat");
return A
};
WikiEditor.prototype.getTableToolbar=function(){return this.getTableControls("table")
};
WikiEditor.prototype.getTableControls=function(A){var B="";
switch(A){case"table":B=this.createButtonHTML("table","table.gif","lang_table_desc","mceInsertTable",true);
break
}return B
};
WikiEditor.prototype.getTableRowToolbar=function(){return this.getTableRowControls("row_before")+this.getTableRowControls("row_after")+this.getTableRowControls("delete_row")
};
WikiEditor.prototype.getTableRowControls=function(A){var B="";
switch(A){case"row_before":B=this.createButtonHTML("row_before","table_insert_row_before.gif","lang_table_row_before_desc","mceTableInsertRowBefore");
break;
case"row_after":B=this.createButtonHTML("row_after","table_insert_row_after.gif","lang_table_row_after_desc","mceTableInsertRowAfter");
break;
case"delete_row":B=this.createButtonHTML("delete_row","table_delete_row.gif","lang_table_delete_row_desc","mceTableDeleteRow");
break
}return B
};
WikiEditor.prototype.getTableColToolbar=function(){return this.getTableColControls("col_before")+this.getTableColControls("col_after")+this.getTableColControls("delete_col")
};
WikiEditor.prototype.getTableColControls=function(A){var B="";
switch(A){case"col_before":B=this.createButtonHTML("col_before","table_insert_col_before.gif","lang_table_col_before_desc","mceTableInsertColBefore");
break;
case"col_after":B=this.createButtonHTML("col_after","table_insert_col_after.gif","lang_table_col_after_desc","mceTableInsertColAfter");
break;
case"delete_col":B=this.createButtonHTML("delete_col","table_delete_col.gif","lang_table_delete_col_desc","mceTableDeleteCol");
break
}return B
};
WikiEditor.prototype.getTitleToolbar=function(){return this.getTitleControl()
};
WikiEditor.prototype.getTitleControl=function(A){return'<select id="{$editor_id}_titleSelect" name="{$editor_id}_titleSelect" class="mceSelectList" onchange="tinyMCE.execInstanceCommand(\'{$editor_id}\',\'Title\',false,this.options[this.selectedIndex].value);wikiEditor.executedCommand(\'Title\');"><option value="0">{$lang_wiki_title_menu}</option><option value="1">{$lang_wiki_title_1}</option><option value="2">{$lang_wiki_title_2}</option><option value="3">{$lang_wiki_title_3}</option><option value="4">{$lang_wiki_title_4}</option><option value="5">{$lang_wiki_title_5}</option></select>'
};
WikiEditor.prototype.getStyleToolbar=function(){return this.getStyleControl("fontselect")+" "+this.getStyleControl("fontSizeSelect")+this.getStyleControl("mceForeColor")+this.getStyleControl("mceBackColor")
};
WikiEditor.prototype.getStyleControl=function(A){switch(A){case"mceForeColor":return this.createButtonHTML("forecolor","forecolor.gif","lang_theme_forecolor_desc","mceForeColor",true);
case"fontSizeSelect":return'<select id="{$editor_id}_fontSizeSelect" name="{$editor_id}_fontSizeSelect" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="tinyMCE.execInstanceCommand(\'{$editor_id}\',\'FontSize\',false,this.options[this.selectedIndex].value);" class="mceSelectList"><option value="0">{$lang_theme_font_size}</option><option value="1">1 (8 pt)</option><option value="2">2 (10 pt)</option><option value="3">3 (12 pt)</option><option value="4">4 (14 pt)</option><option value="5">5 (18 pt)</option><option value="6">6 (24 pt)</option><option value="7">7 (36 pt)</option></select>';
case"fontselect":var C='<select id="{$editor_id}_fontNameSelect" name="{$editor_id}_fontNameSelect" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="tinyMCE.execInstanceCommand(\'{$editor_id}\',\'FontName\',false,this.options[this.selectedIndex].value);" class="mceSelectList"><option value="">{$lang_theme_fontdefault}</option>';
var B="Arial=arial,helvetica,sans-serif;Courier New=courier new,courier,monospace;Georgia=georgia,times new roman,times,serif;Tahoma=tahoma,arial,helvetica,sans-serif;Times New Roman=times new roman,times,serif;Verdana=verdana,arial,helvetica,sans-serif;Impact=impact;WingDings=wingdings";
var D="Andale Mono=andale mono,times;Arial=arial,helvetica,sans-serif;Arial Black=arial black,avant garde;Book Antiqua=book antiqua,palatino;Comic Sans MS=comic sans ms,sand;Courier New=courier new,courier;Georgia=georgia,palatino;Helvetica=helvetica;Impact=impact,chicago;Symbol=symbol;Tahoma=tahoma,arial,helvetica,sans-serif;Terminal=terminal,monaco;Times New Roman=times new roman,times;Trebuchet MS=trebuchet ms,geneva;Verdana=verdana,geneva;Webdings=webdings;Wingdings=wingdings,zapf dingbats";
var F=tinyMCE.getParam("theme_advanced_fonts",D).split(";");
for(i=0;
i<F.length;
i++){if(F[i]!=""){var E=F[i].split("=");
C+='<option value="'+E[1]+'">'+E[0]+"</option>"
}}C+="</select>";
return C;
case"mceBackColor":return this.createButtonHTML("backcolor","backcolor.gif","lang_theme_backcolor_desc","mceBackColor",true)
}};
WikiEditor.prototype.getListToolbar=function(){return this.getListControls("bullist")+this.getListControls("numlist")
};
WikiEditor.prototype.getListControls=function(A){var B="";
switch(A){case"bullist":B=this.createButtonHTML("bullist","bullist.gif","lang_bullist_desc","InsertUnorderedList");
break;
case"numlist":B=this.createButtonHTML("numlist","numlist.gif","lang_numlist_desc","InsertOrderedList");
break
}return B
};
WikiEditor.prototype.getTextToolbar=function(){return this.getTextControls("bold")+this.getTextControls("italic")+this.getTextControls("underline")+this.getTextControls("strikeout")
};
WikiEditor.prototype.getTextControls=function(A){var B="";
switch(A){case"bold":B=this.createButtonHTML("bold","{$lang_bold_img}","lang_bold_desc","Bold");
break;
case"italic":B=this.createButtonHTML("italic","{$lang_italic_img}","lang_italic_desc","Italic");
break;
case"underline":B=this.createButtonHTML("underline","{$lang_underline_img}","lang_underline_desc","Underline");
break;
case"strikeout":B=this.createButtonHTML("strikethrough","strikethrough.gif","lang_striketrough_desc","Strikethrough");
break
}return B
};
WikiEditor.prototype.convertParagraphInternal=function(C,A,B){var D=this.trimString(A[2]);
if(D.substring(D.length-6)=="<br />"){D=D.substring(0,D.lastIndexOf("<br />"))
}D="\r\n"+D+"\r\n";
return B.replace(C,D)
};
WikiEditor.prototype.PARAGRAPH_CLASS_NAME="paragraph";
WikiEditor.prototype.convertParagraphExternal=function(G,L,F){var J=this._getLines(F);
var H="";
var K="";
var B=false;
var I=false;
var C=false;
if(J==null||J.length==0){return""
}for(var D=0;
D<J.length;
D++){K=J[D];
K=K.replace(/(\r$)|(\n$)|(\r\n$)/gi,"");
var A=this._hasHTML(K);
var E=this._onlyHasBr(K);
if(K!=""&&(!A||E)){if(!B){B=true;
I=true;
H+='<p class="'+this.PARAGRAPH_CLASS_NAME+'" >\r\n'
}H+=K+"\r\n";
I=false;
continue
}else{if(B){B=false;
H+="<br />\r\n</p>\r\n"
}}if(A){H+=K+"\r\n"
}C=E
}if(B){if(C){H+="<br />\r\n"
}H+="</p>\r\n"
}H=H.replace(/<p\s*(.*?)>\s*<\/p>/g,"");
return H
};
WikiEditor.prototype._getLines=function(B){var A;
if(this.core.isMSIE){A="\n"
}else{A="\n"
}return B.split(A)
};
WikiEditor.prototype._hasHTML=function(B){var A=/<[^>]+>/i;
return(A.exec(B)!=null)
};
WikiEditor.prototype._onlyHasBr=function(B){B=B.replace(/<br \/>/g," ");
var A=/<[^>]+>/i;
return(A.exec(B)==null)
};
WikiEditor.prototype.LIST_NORMAL_CLASS_NAME="star";
WikiEditor.prototype.LIST_MINUS_CLASS_NAME="minus";
WikiEditor.prototype.LIST_NUMERIC_CLASS_NAME="";
WikiEditor.prototype.LIST_NUMERIC_CLASS_NAME_1="norder";
WikiEditor.prototype.convertListExternal=function(D,A,C){var B=C.substring(A.index,C.length);
var E="";
switch(A[1].charAt(0)){case"*":E=this._convertRecursiveListExternal(D,B,0,this.LIST_NORMAL_CLASS_NAME);
break;
case"-":E=this._convertRecursiveListExternal(D,B,0,this.LIST_MINUS_CLASS_NAME);
break;
case"#":E=this._convertRecursiveListExternal(D,B,0,this.LIST_NUMERIC_CLASS_NAME);
break;
case"1":E=this._convertRecursiveListExternal(D,B,0,this.LIST_NUMERIC_CLASS_NAME);
break
}return C.substring(0,A.index)+"\r\n"+E
};
WikiEditor.prototype._convertNewLine2BrInList=function(E){var A=this._getLines(E);
var D="";
for(var C=0;
C<A.length;
C++){if((A[C].charAt(0)=="*")||(A[C].charAt(0)=="#")||(A[C].charAt(0)=="1")){D+=A[C];
var B=1;
if(((C+B)<=A.length)){while(((C+B)<A.length)&&((A[C+B].charAt(0)!="*")&&(A[C+B].charAt(0)!="#")&&((A[C+B].charAt(0)!="1")))&&this.trimString(A[C+B])!=""){D+="\r\n"+A[C+B];
B++
}}D+="\r\n";
C=(C+B-1)
}else{D+=A[C]+"\r\n"
}}return D
};
WikiEditor.prototype._convertGenericListExternal=function(E,C,D,F){var G="<"+D+' class="'+F+'">\r\n';
var B;
var A=C;
RegExp.lastIndex=0;
while((B=E.exec(A))&&B.index==0){G+="<li>"+this.trimString(B[2])+"</li>\r\n";
A=A.substring(B[0].length,A.length);
RegExp.lastIndex=0
}G+="</"+D+">\r\n"+A;
return G
};
WikiEditor.prototype._convertRecursiveListExternal=function(I,H,G,D){var J="";
var A=(D==this.LIST_NUMERIC_CLASS_NAME)?"ol":"ul";
RegExp.lastIndex=0;
var B=I.exec(H);
var C=(B!=null&&((B[1].charAt(0)=="*")||(B[1].charAt(0)=="-")||(B[1].charAt(0)=="#")||(B[1].charAt(0)=="1"))&&B.index==0)?B[1].length:0;
var K=(C>0)?B[0].length:0;
var F=H.substring(K,H.length);
var L=C-G;
var M=(L>0)?"<"+A+' class="'+D+'">':"</"+A+">";
for(var E=0;
E<Math.abs(C-G);
E++){J+=M+"\r\n"
}if(C>0){J+="<li>"+this.trimString(B[2])+"</li>\r\n";
J+=this._convertRecursiveListExternal(I,F,C,D)
}else{J+=H
}return J
};
WikiEditor.prototype.LINK_EXTERNAL_CLASS_NAME="wikiexternallink";
WikiEditor.prototype.LINK_INTERNAL_CLASS_NAME="wikilink";
WikiEditor.prototype.convertLinkExternal=function(F,I,E){var H=I[1];
var C=this.trimString(I[3]);
var A=(I[4])?(I[4]):(H);
A=A.replace(/\&/g,"&amp;");
var D=this.trimString(I[7]);
var B;
var G='<a class="'+B+'" href="'+A+'"';
if(this.isExternalLink(A)){B=this.LINK_EXTERNAL_CLASS_NAME;
G+=' title="'+A+'"'
}else{B=this.LINK_INTERNAL_CLASS_NAME
}if(C=="|"){G+=' id="'+A+'"'
}if((D!="undefined")&&(D!="")&&(D!="_self")){G+=' target="'+I[7]+'"'
}G+=">"+H+"</a>";
return E.replace(F,G)
};
WikiEditor.prototype.convertTableExternal=function(G,P,F){var K=this.trimRNString(P[1]);
K=K.replace(/<br \/>\r\n/gi,"<br />");
var A=this._getLines(K);
var H='<table class="wiki-table" cellpadding="0" cellspacing="0" align="center">';
var O=new Array();
var B=0;
for(var E=0;
E<A.length;
E++){A[E]=this.trimRNString(A[E]);
A[E]=A[E].replace(/<\/?p[^>]*>/gi,"");
if(A[E]!=""){O[B]=A[E];
B++
}}var N=new Array();
var L=0;
for(var E=0;
E<O.length;
E++){var M="";
var C=0;
do{M+=O[E+C];
C++
}while((O[E+C]!=null)&&(O[E+C-1].lastIndexOf("\\\\")==(O[E+C-1].length-2))&&(O[E+C-1].lastIndexOf("\\\\")!=-1));
N[L]=M;
L++;
E+=(C-1)
}for(var E=0;
E<N.length;
E++){if(E==0){H+="<tr class='table-head'>"
}else{if((E%2)==1){H+="<tr class='table-odd'>"
}else{H+="<tr class='table-even'>"
}}var J=N[E].split("|");
for(var D=0;
D<J.length;
D++){H+="<td>";
var I=J[D].split("\\\\");
if(I.length==1){H+=I[0]
}else{if(I.length>1){for(var C=0;
C<I.length;
C++){if(I[C]==""){I[C]="&nbsp;"
}H+=(I[C]+"<br />\r\n")
}}}H+="</td>"
}H+="</tr>"
}H+="</table>";
return F.replace(G,H)
};
WikiEditor.prototype.isExternalLink=function(A){var B=/(https?|ftp):\/\/[-a-zA-Z0-9+&@#\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\/%=~_|]/gi;
return A.search(B)>-1
};
WikiEditor.prototype.convertHeadingExternal=function(C,A,B){var E=A[1].split(".").length;
var D="\r\n<h"+E+"><span>"+this.trimString(A[3])+"</span></h"+E+">";
return B.substring(0,A.index)+D+B.substring(A.index+A[0].length,B.length)
};
WikiEditor.prototype.convertListInternal=function(F,I,E){var A=this.replaceMatchingTag(E,I[1],null);
var D="";
var H="";
var C=this.readAttributes(I[2]);
if(C&&C["class"]){D=C["class"]
}var G="";
if(A&&A.start>-1){G=this._convertListInternal(E.substring(A.start,A.end),D);
H=E.substring(0,A.start)+"\r\n";
var B=E.substring(A.end,E.length);
if(!this.core.isMSIE&&B.substring(0,6)=="<br />"){B="\r\n"+B.substring(6,B.length)
}H+=G+"\r\n"+B;
return H
}return E
};
WikiEditor.prototype._convertListInternal=function(F,B){var E=/<\s*li\s*([^>]*)>\s*(.*?)\s*<\/\s*li\s*>/gi;
var A;
var G="";
while((A=E.exec(F))){var D=this.readAttributes(A[1]);
RegExp.lastIndex=A.index;
var C=A[2];
if(C=="<br />"){C="&nbsp;"
}else{if(this.trimString(C)==""){C="&nbsp;"
}}var H=C.length-6;
if(C.substring(H)=="<br />"){C=C.substring(0,H)
}if(D&&D.wikieditorlisttype&&D.wikieditorlistdepth){switch(D.wikieditorlisttype){case"ul":if((B!=null)&&(B=="minus")){G+=this.buildString("-",parseInt(D.wikieditorlistdepth,10))+" "+C+"\r\n"
}else{G+=this.buildString("*",parseInt(D.wikieditorlistdepth,10))+" "+C+"\r\n"
}G.replace(/<div>([\s\S]+?)<\/div>/g,"$1");
break;
case"ol":G+=this.buildString("1",parseInt(D.wikieditorlistdepth,10))+". "+C+"\r\n";
break
}}else{}}return G
};
WikiEditor.prototype.convertStyleExternal=function(J,O,I){var L="";
var G=O[1].split("|");
var P="font",B="",F="",C="",A="",N="",H="";
var D=false;
for(var E=0;
E<G.length;
E++){var K=this.trimString(G[E].substring(0,G[E].indexOf("=")));
var M=this.trimString(G[E].substring(G[E].indexOf("=")+1,G[E].length));
if(K=="class"){F=M
}else{if(K=="id"){C=M
}else{if(K=="name"){A=M
}else{if(K=="type"){P=M
}else{if(K=="align"){H=M
}else{if(K=="icon"){B+="background-image: url("+M+");";
D=true
}else{B+=K+":"+M+";"
}}}}}}}L+="<"+P;
if(C!=""){L+=' id="'+C+'"'
}if(F!=""){L+=' class="'+F+'"'
}else{if(D){L+=' class="stylemacro"'
}}if(A!=""){L+=' name="'+A+'"'
}if(H!=""){L+=' align="'+H+'"'
}if(B!=""){L+=' style="'+B+'"'
}L+=">";
L+=O[2];
L+="</"+P+">";
return I.replace(J,L)
};
WikiEditor.prototype.convertStyleInternal=function(F,M,E){if(this.trimString(M[3])==""){return E.replace(F,M[3])
}E=E.replace(/<div class="paragraph">([\s\S]+?)<\/div>/g,"$1");
E=E.replace(/<span class="(wikilink|wikiexternallink)">\s*([\s\S]+?)<\/span>/g,"$2");
E=E.replace(/<span class="(bold|italic|underline|strike)">([\s\S]+?)<\/span>/g,"$2");
var I=M[1].toLowerCase();
var H="";
if(I=="span"||I=="div"){var B=this.readAttributes(M[2]);
if(I=="div"){}H+="{style:type="+I;
if(B){if(B.id){H+="|id="+B.id
}if(B.align){H+="|align="+B.align
}if(B["class"]&&B["class"]!="stylemacro"){H+="|class="+B["class"]
}if(B.name){H+="|name="+B.name
}if(B.style){var D=B.style.split(";");
for(var C=0;
C<D.length;
C++){var G=this.trimString(D[C].substring(0,D[C].indexOf(":"))).toLowerCase();
var K=this.trimString(D[C].substring(D[C].indexOf(":")+1,D[C].length));
var J=["font-size","font-family","background-color","color","width","height","float","border"];
for(var A=0;
A<J.length;
A++){if(G==J[A]){H+="|"+G+"="+K;
break
}}if(G=="background-image"){var L;
if(K.indexOf("url")>=0){L=K.substring(K.indexOf("(")+2,K.indexOf(")")-1);
H+="|icon="+L
}}}}}H+="}";
H+=M[3];
H+="{style}";
if(I=="div"){}}return E.replace(F,H)
};
WikiEditor.prototype.VELOCITY_COMMENT_CLASS_NAME="vcomment";
WikiEditor.prototype.convertVelocityCommentExternal=function(C,A,B){var D="";
var E="";
if((A[1]!=null)&&(A[1]!="undefined")&&(A[1]!="")){E=A[1]
}else{if((A[3]!=null)&&(A[3]!="undefined")&&(A[3]!="")){E=A[3]
}}D="<div class='"+this.VELOCITY_COMMENT_CLASS_NAME+"'>"+E+"</div>";
return B.replace(C,D)
};
WikiEditor.prototype.convertVelocityCommentInternal=function(C,A,B){var D="";
var E=A[3];
if(((E.indexOf("<br")>-1)||E.indexOf("\n")>-1)||(E.indexOf("<p")>-1)){D="#*"+E+"*#"
}else{if(this.core.isMSIE){D="\r\n##"+E+"\r\n"
}else{D="##"+E
}}if(A[4]!=null){D+=A[4]
}return B.replace(C,D)
};
WikiEditor.prototype.convertCodeMacroExternal=function(D,A,C){var E="";
var B="";
if(A[1]!=null&&A[1]!=""){B=this.trimString(A[1].substring(A[1].indexOf(":")+1,A[1].length))
}E+="<div";
if(B!=""){E+=' id="'+B+'"'
}E+=' class="code"><pre>';
E+=A[2].toString().replace(/</g,"&#60").replace(/>/g,"&#62");
E+="</pre></div>";
E=this._escapeText(E);
return C.replace(D,E)
};
WikiEditor.prototype.convertCodeMacroInternal=function(E,A,D){var F="";
var C=A[4];
var B=this.readAttributes(A[1]+A[3]);
F+="{code";
if(B&&B.id){F+=":"+this.trimString(B.id.toString())
}F+="}\r\n";
F+=this._escapeText(this.trimString(A[4]).replace(/<br \/>/g,"\r\n"));
F+="\r\n";
F+="{code}";
F="\r\n"+F+"\r\n";
return D.replace(E,F)
};
WikiEditor.prototype.convertHRExternal=function(D,A,C){var E="";
var B=0;
if(A[1]&&A[1]!=""){B=A[1].toString().length
}if(B>0){E='<hr class="line" name="'+B+'"/>'
}else{E='<hr class="line"/>'
}return C.replace(D,E)
};
WikiEditor.prototype.convertHRInternal=function(C,A,B){var D="----";
var E=this.readAttributes(A[1]);
if(E&&E.name){D+=this.buildString("-",E.name)
}if(this.core.isMSIE){D+="\r\n"
}else{if(A[1]==null||A[1]==""||this.trimString(A[1].toString())=="/"){D+="\r\n"
}}return B.replace(C,D)
};