if(!tinyMCE.settings.contextmenu_skip_plugin_css){tinyMCE.loadCSS(tinyMCE.baseURL+"/plugins/contextmenu/css/contextmenu.css")
}var TinyMCE_ContextMenuPlugin={_contextMenu:null,getInfo:function(){return{longname:"Context menus",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_contextmenu.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){if(tinyMCE.isMSIE5_0&&tinyMCE.isOpera){return 
}TinyMCE_ContextMenuPlugin._contextMenu=new TinyMCE_ContextMenu({commandhandler:"TinyMCE_ContextMenuPlugin._commandHandler",spacer_image:tinyMCE.baseURL+"/plugins/contextmenu/images/spacer.gif"});
tinyMCE.addEvent(A.getDoc(),"click",TinyMCE_ContextMenuPlugin._hideContextMenu);
tinyMCE.addEvent(A.getDoc(),"keypress",TinyMCE_ContextMenuPlugin._hideContextMenu);
tinyMCE.addEvent(A.getDoc(),"keydown",TinyMCE_ContextMenuPlugin._hideContextMenu);
tinyMCE.addEvent(document,"click",TinyMCE_ContextMenuPlugin._hideContextMenu);
tinyMCE.addEvent(document,"keypress",TinyMCE_ContextMenuPlugin._hideContextMenu);
tinyMCE.addEvent(document,"keydown",TinyMCE_ContextMenuPlugin._hideContextMenu);
if(tinyMCE.isGecko){tinyMCE.addEvent(A.getDoc(),"contextmenu",function(B){TinyMCE_ContextMenuPlugin._showContextMenu(tinyMCE.isMSIE?A.contentWindow.event:B,A)
})
}else{tinyMCE.addEvent(A.getDoc(),"contextmenu",TinyMCE_ContextMenuPlugin._onContextMenu)
}},_onContextMenu:function(C){var F=tinyMCE.isMSIE?C.srcElement:C.target;
var D,A;
if((A=tinyMCE.getParentElement(F,"body"))!=null){for(var E in tinyMCE.instances){var B=tinyMCE.instances[E];
if(!tinyMCE.isInstance(B)){continue
}if(A==B.getBody()){D=B;
break
}}return TinyMCE_ContextMenuPlugin._showContextMenu(tinyMCE.isMSIE?D.contentWindow.event:C,D)
}},_showContextMenu:function(E,D){function C(J,I){return J.getAttribute(I)?J.getAttribute(I):""
}var A,H,G,B;
var F=tinyMCE.getAbsPosition(D.iframeElement);
A=tinyMCE.isMSIE?E.screenX:F.absLeft+(E.pageX-D.getBody().scrollLeft);
H=tinyMCE.isMSIE?E.screenY:F.absTop+(E.pageY-D.getBody().scrollTop);
G=tinyMCE.isMSIE?E.srcElement:E.target;
B=this._contextMenu;
B.inst=D;
window.setTimeout(function(){var K=tinyMCE.getParam("theme");
B.clearAll();
var I=D.selection.getSelectedText().length!=0||G.nodeName=="IMG";
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/cut.gif","$lang_cut_desc","Cut","",!I);
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/copy.gif","$lang_copy_desc","Copy","",!I);
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/paste.gif","$lang_paste_desc","Paste","",false);
if(I||(G?(G.nodeName=="A")||(G.nodeName=="IMG"):false)){B.addSeparator();
B.addItem(tinyMCE.baseURL+"/themes/advanced/images/link.gif","$lang_link_desc",D.hasPlugin("advlink")?"mceAdvLink":"mceLink");
B.addItem(tinyMCE.baseURL+"/themes/advanced/images/unlink.gif","$lang_unlink_desc","unlink","",(G?(G.nodeName!="A")&&(G.nodeName!="IMG"):true))
}G=tinyMCE.getParentElement(G,"img,table,td"+(D.hasPlugin("advhr")?",hr":""));
if(G){switch(G.nodeName){case"IMG":B.addSeparator();
if(tinyMCE.getAttrib(G,"class").indexOf("mceItemFlash")!=-1){B.addItem(tinyMCE.baseURL+"/plugins/flash/images/flash.gif","$lang_flash_props","mceFlash")
}else{B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/image.gif","$lang_image_props_desc",D.hasPlugin("advimage")?"mceAdvImage":"mceImage")
}break;
case"HR":B.addSeparator();
B.addItem(tinyMCE.baseURL+"/plugins/advhr/images/advhr.gif","$lang_insert_advhr_desc","mceAdvancedHr");
break;
case"TABLE":case"TD":if(D.hasPlugin("table")){var L=(G.nodeName=="TABLE")?"":C(G,"colspan");
var J=(G.nodeName=="TABLE")?"":C(G,"rowspan");
L=L==""?"1":L;
J=J==""?"1":J;
B.addSeparator();
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/cut.gif","$lang_table_cut_row_desc","mceTableCutRow");
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/copy.gif","$lang_table_copy_row_desc","mceTableCopyRow");
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/paste.gif","$lang_table_paste_row_before_desc","mceTablePasteRowBefore","",D.tableRowClipboard==null);
B.addItem(tinyMCE.baseURL+"/themes/"+K+"/images/paste.gif","$lang_table_paste_row_after_desc","mceTablePasteRowAfter","",D.tableRowClipboard==null);
B.addSeparator();
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table.gif","$lang_table_desc","mceInsertTable","insert");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table.gif","$lang_table_props_desc","mceInsertTable");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_cell_props.gif","$lang_table_cell_desc","mceTableCellProps");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_delete.gif","$lang_table_del","mceTableDelete");
B.addSeparator();
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_row_props.gif","$lang_table_row_desc","mceTableRowProps");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_insert_row_before.gif","$lang_table_row_before_desc","mceTableInsertRowBefore");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_insert_row_after.gif","$lang_table_row_after_desc","mceTableInsertRowAfter");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_delete_row.gif","$lang_table_delete_row_desc","mceTableDeleteRow");
B.addSeparator();
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_insert_col_before.gif","$lang_table_col_before_desc","mceTableInsertColBefore");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_insert_col_after.gif","$lang_table_col_after_desc","mceTableInsertColAfter");
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_delete_col.gif","$lang_table_delete_col_desc","mceTableDeleteCol");
B.addSeparator();
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_split_cells.gif","$lang_table_split_cells_desc","mceTableSplitCells","",(L=="1"&&J=="1"));
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table_merge_cells.gif","$lang_table_merge_cells_desc","mceTableMergeCells","",false)
}break
}}else{if(D.hasPlugin("table")){B.addSeparator();
B.addItem(tinyMCE.baseURL+"/plugins/table/images/table.gif","$lang_table_desc","mceInsertTable","insert")
}}B.show(A,H)
},10);
tinyMCE.cancelEvent(E);
return false
},_hideContextMenu:function(){if(TinyMCE_ContextMenuPlugin._contextMenu){TinyMCE_ContextMenuPlugin._contextMenu.hide()
}},_commandHandler:function(D,C){var A=TinyMCE_ContextMenuPlugin._contextMenu;
A.hide();
var B=false;
if(D=="mceInsertTable"||D=="mceTableCellProps"||D=="mceTableRowProps"||D=="mceTableMergeCells"){B=true
}if(D=="Paste"){C=null
}if(tinyMCE.getParam("dialog_type")=="modal"&&tinyMCE.isMSIE){window.setTimeout(function(){A.inst.execCommand(D,B,C)
},100)
}else{A.inst.execCommand(D,B,C)
}}};
tinyMCE.addPlugin("contextmenu",TinyMCE_ContextMenuPlugin);
function TinyMCE_ContextMenu(B){function C(E,D){B[E]=typeof (B[E])!="undefined"?B[E]:D
}var A=this;
this.isMSIE=(navigator.appName=="Microsoft Internet Explorer");
this.contextMenuDiv=document.createElement("div");
this.contextMenuDiv.className="contextMenu";
this.contextMenuDiv.setAttribute("class","contextMenu");
this.contextMenuDiv.style.display="none";
this.contextMenuDiv.style.position="absolute";
this.contextMenuDiv.style.zindex=1000;
this.contextMenuDiv.style.left="0";
this.contextMenuDiv.style.top="0";
this.contextMenuDiv.unselectable="on";
document.body.appendChild(this.contextMenuDiv);
C("commandhandler","");
C("spacer_image","images/spacer.gif");
this.items=new Array();
this.settings=B;
this.html="";
if(tinyMCE.isMSIE&&!tinyMCE.isMSIE5_0&&!tinyMCE.isOpera){this.pop=window.createPopup();
doc=this.pop.document;
doc.open();
doc.write('<html><head><link href="'+tinyMCE.baseURL+'/plugins/contextmenu/css/contextmenu.css" rel="stylesheet" type="text/css" /></head><body unselectable="yes" class="contextMenuIEPopup"></body></html>');
doc.close()
}}TinyMCE_ContextMenu.prototype={clearAll:function(){this.html="";
this.contextMenuDiv.innerHTML=""
},addSeparator:function(){this.html+='<tr class="contextMenuItem"><td class="contextMenuIcon"><img src="'+this.settings.spacer_image+'" width="20" height="1" class="contextMenuImage" /></td><td><img class="contextMenuSeparator" width="1" height="1" src="'+this.settings.spacer_image+'" /></td></tr>'
},addItem:function(C,G,F,D,B){if(G.charAt(0)=="$"){G=tinyMCE.getLang(G.substring(1))
}var E="";
var A="";
if(tinyMCE.isMSIE&&!tinyMCE.isMSIE5_0){E="contextMenu.execCommand('"+F+"', '"+D+"');return false;"
}else{E=this.settings.commandhandler+"('"+F+"', '"+D+"');return false;"
}if(C==""){C=this.settings.spacer_image
}if(!B){A+='<tr class="contextMenuItem">'
}else{A+='<tr class="contextMenuItemDisabled">'
}A+='<td class="contextMenuIcon"><img src="'+C+'" width="20" height="20" class="contextMenuImage" /></td>';
A+='<td><div class="contextMenuText">';
A+='<a href="javascript:void(0);" onclick="'+E+'" onmousedown="return false;">&#160;';
A+=G;
A+="&#160;</a>";
A+="</div></td>";
A+="</tr>";
this.html+=A
},show:function(B,F){var C,E,A;
if(this.html==""){return 
}var D="";
D+='<a href="#"></a><table border="0" cellpadding="0" cellspacing="0">';
D+=this.html;
D+="</table>";
this.contextMenuDiv.innerHTML=D;
this.contextMenuDiv.style.display="block";
E=this.contextMenuDiv.offsetWidth;
A=this.contextMenuDiv.offsetHeight;
this.contextMenuDiv.style.display="none";
if(tinyMCE.isMSIE&&!tinyMCE.isMSIE5_0&&!tinyMCE.isOpera){this.pop.document.body.innerHTML='<div class="contextMenu">'+D+"</div>";
this.pop.document.tinyMCE=tinyMCE;
this.pop.document.contextMenu=this;
this.pop.show(B,F,E,A)
}else{C=this.getViewPort();
this.contextMenuDiv.style.left=(B>C.width-E?C.width-E:B)+"px";
this.contextMenuDiv.style.top=(F>C.height-A?C.height-A:F)+"px";
this.contextMenuDiv.style.display="block"
}},getViewPort:function(){return{width:document.documentElement.offsetWidth||document.body.offsetWidth,height:self.innerHeight||document.documentElement.clientHeight||document.body.clientHeight}
},hide:function(){if(tinyMCE.isMSIE&&!tinyMCE.isMSIE5_0&&!tinyMCE.isOpera){this.pop.hide()
}else{this.contextMenuDiv.style.display="none"
}},execCommand:function(command,value){eval(this.settings.commandhandler+"(command, value);")
}};