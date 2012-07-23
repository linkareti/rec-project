tinyMCE.importPluginLanguagePack("table","en,tr,ar,cs,da,de,el,es,fi,fr_ca,hu,it,ja,ko,nl,nb,pl,pt,pt_br,sv,tw,zh_cn,fr,de,he,nb,ru,ru_KOI8-R,ru_UTF-8,nn,cy,is,zh_tw,zh_tw_utf8,sk");
var TinyMCE_TablePlugin={getInfo:function(){return{longname:"Tables",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_table.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){if(tinyMCE.isGecko){var B=A.getDoc();
tinyMCE.addEvent(B,"mouseup",TinyMCE_TablePlugin._mouseDownHandler)
}A.tableRowClipboard=null
},getControlHTML:function(F){var B=new Array(["table","table.gif","lang_table_desc","mceInsertTable",true],["delete_col","table_delete_col.gif","lang_table_delete_col_desc","mceTableDeleteCol"],["delete_row","table_delete_row.gif","lang_table_delete_row_desc","mceTableDeleteRow"],["col_after","table_insert_col_after.gif","lang_table_col_after_desc","mceTableInsertColAfter"],["col_before","table_insert_col_before.gif","lang_table_col_before_desc","mceTableInsertColBefore"],["row_after","table_insert_row_after.gif","lang_table_row_after_desc","mceTableInsertRowAfter"],["row_before","table_insert_row_before.gif","lang_table_row_before_desc","mceTableInsertRowBefore"],["row_props","table_row_props.gif","lang_table_row_desc","mceTableRowProps",true],["cell_props","table_cell_props.gif","lang_table_cell_desc","mceTableCellProps",true],["split_cells","table_split_cells.gif","lang_table_split_cells_desc","mceTableSplitCells",true],["merge_cells","table_merge_cells.gif","lang_table_merge_cells_desc","mceTableMergeCells",true]);
for(var D=0;
D<B.length;
D++){var A=B[D];
var E="tinyMCE.execInstanceCommand('{$editor_id}','"+A[3]+"', "+(A.length>4?A[4]:false)+(A.length>5?", '"+A[5]+"'":"")+");return false;";
if(A[0]==F){return tinyMCE.getButtonHTML(F,A[2],"{$pluginurl}/images/"+A[1],A[3],(A.length>4?A[4]:false))
}}if(F=="tablecontrols"){var C="";
C+=tinyMCE.getControlHTML("table");
C+=tinyMCE.getControlHTML("separator");
C+=tinyMCE.getControlHTML("row_props");
C+=tinyMCE.getControlHTML("cell_props");
C+=tinyMCE.getControlHTML("separator");
C+=tinyMCE.getControlHTML("row_before");
C+=tinyMCE.getControlHTML("row_after");
C+=tinyMCE.getControlHTML("delete_row");
C+=tinyMCE.getControlHTML("separator");
C+=tinyMCE.getControlHTML("col_before");
C+=tinyMCE.getControlHTML("col_after");
C+=tinyMCE.getControlHTML("delete_col");
C+=tinyMCE.getControlHTML("separator");
C+=tinyMCE.getControlHTML("split_cells");
C+=tinyMCE.getControlHTML("merge_cells");
return C
}return""
},execCommand:function(E,A,D,F,C){switch(D){case"mceInsertTable":case"mceTableRowProps":case"mceTableCellProps":case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":case"mceTableDelete":var B=tinyMCE.getInstanceById(E);
B.execCommand("mceBeginUndoLevel");
TinyMCE_TablePlugin._doExecCommand(E,A,D,F,C);
B.execCommand("mceEndUndoLevel");
return true
}return false
},handleNodeChange:function(I,B,H,D,F,E){var A="1",C="1";
var G=tinyMCE.getInstanceById(I);
tinyMCE.switchClass(I+"_table","mceButtonNormal");
tinyMCE.switchClass(I+"_row_props","mceButtonDisabled");
tinyMCE.switchClass(I+"_cell_props","mceButtonDisabled");
tinyMCE.switchClass(I+"_row_before","mceButtonDisabled");
tinyMCE.switchClass(I+"_row_after","mceButtonDisabled");
tinyMCE.switchClass(I+"_delete_row","mceButtonDisabled");
tinyMCE.switchClass(I+"_col_before","mceButtonDisabled");
tinyMCE.switchClass(I+"_col_after","mceButtonDisabled");
tinyMCE.switchClass(I+"_delete_col","mceButtonDisabled");
tinyMCE.switchClass(I+"_split_cells","mceButtonDisabled");
tinyMCE.switchClass(I+"_merge_cells","mceButtonDisabled");
if(tdElm=tinyMCE.getParentElement(B,"td,th")){tinyMCE.switchClass(I+"_cell_props","mceButtonSelected");
tinyMCE.switchClass(I+"_row_before","mceButtonNormal");
tinyMCE.switchClass(I+"_row_after","mceButtonNormal");
tinyMCE.switchClass(I+"_delete_row","mceButtonNormal");
tinyMCE.switchClass(I+"_col_before","mceButtonNormal");
tinyMCE.switchClass(I+"_col_after","mceButtonNormal");
tinyMCE.switchClass(I+"_delete_col","mceButtonNormal");
A=tinyMCE.getAttrib(tdElm,"colspan");
C=tinyMCE.getAttrib(tdElm,"rowspan");
A=A==""?"1":A;
C=C==""?"1":C;
if(A!="1"||C!="1"){tinyMCE.switchClass(I+"_split_cells","mceButtonNormal")
}}if(tinyMCE.getParentElement(B,"tr")){tinyMCE.switchClass(I+"_row_props","mceButtonSelected")
}if(tinyMCE.getParentElement(B,"table")){tinyMCE.switchClass(I+"_table","mceButtonSelected");
tinyMCE.switchClass(I+"_merge_cells","mceButtonNormal")
}},_mouseDownHandler:function(B){var C=tinyMCE.isMSIE?event.srcElement:B.target;
var A=tinyMCE.selectedInstance.getFocusElement();
if(C.nodeName=="BODY"&&(A.nodeName=="TD"||A.nodeName=="TH"||(A.parentNode&&A.parentNode.nodeName=="TD")||(A.parentNode&&A.parentNode.nodeName=="TH"))){window.setTimeout(function(){var D=tinyMCE.getParentElement(A,"table");
tinyMCE.handleVisualAid(D,true,tinyMCE.settings.visual,tinyMCE.selectedInstance)
},10)
}},_doExecCommand:function(F,U,Q,w,AD){var s=tinyMCE.getInstanceById(F);
var M=s.getFocusElement();
var t=tinyMCE.getParentElement(M,"tr");
var AO=tinyMCE.getParentElement(M,"td,th");
var b=tinyMCE.getParentElement(M,"table");
var J=s.contentWindow.document;
var AR=b?b.getAttribute("border"):"";
if(t&&AO==null){AO=t.cells[0]
}function AN(y,x){for(var AT=0;
AT<y.length;
AT++){if(y[AT].length>0&&AN(y[AT],x)){return true
}if(y[AT]==x){return true
}}return false
}function AG(){var i=J.createElement("td");
i.innerHTML="&nbsp;"
}function I(y){var x=tinyMCE.getAttrib(y,"colspan");
var i=tinyMCE.getAttrib(y,"rowspan");
x=x==""?1:parseInt(x);
i=i==""?1:parseInt(i);
return{colspan:x,rowspan:i}
}function AJ(AT,AV){for(var AU=0;
AU<AT.length;
AU++){for(var i=0;
i<AT[AU].length;
i++){if(AT[AU][i]==AV){return{cellindex:i,rowindex:AU}
}}}return null
}function C(x,y,i){if(x[y]&&x[y][i]){return x[y][i]
}return null
}function D(AZ){var i=new Array();
var Aa=AZ.rows;
for(var AW=0;
AW<Aa.length;
AW++){for(var AY=0;
AY<Aa[AW].cells.length;
AY++){var AU=Aa[AW].cells[AY];
var AV=I(AU);
for(xstart=AY;
i[AW]&&i[AW][xstart];
xstart++){}for(var AX=AW;
AX<AW+AV.rowspan;
AX++){if(!i[AX]){i[AX]=new Array()
}for(var AT=xstart;
AT<xstart+AV.colspan;
AT++){i[AX][AT]=AU
}}}}return i
}function L(Ac,AZ,AU,AT){var y=D(Ac);
var Ab=AJ(y,AU);
if(AT.cells.length!=AZ.childNodes.length){var Ad=AZ.childNodes;
var AY=null;
for(var Aa=0;
AU=C(y,Ab.rowindex,Aa);
Aa++){var AW=true;
var AX=I(AU);
if(AN(Ad,AU)){AT.childNodes[Aa]._delete=true
}else{if((AY==null||AU!=AY)&&AX.colspan>1){for(var AV=Aa;
AV<Aa+AU.colSpan;
AV++){AT.childNodes[AV]._delete=true
}}}if((AY==null||AU!=AY)&&AX.rowspan>1){AU.rowSpan=AX.rowspan+1
}AY=AU
}Y(b)
}}function l(x,i){while((x=x.previousSibling)!=null){if(x.nodeName==i){return x
}}return null
}function AE(AT,AU){var x=AU.split(",");
while((AT=AT.nextSibling)!=null){for(var y=0;
y<x.length;
y++){if(AT.nodeName.toLowerCase()==x[y].toLowerCase()){return AT
}}}return null
}function Y(AT){if(AT.rows==0){return 
}var y=AT.rows[0];
do{var x=AE(y,"TR");
if(y._delete){y.parentNode.removeChild(y);
continue
}var AU=y.cells[0];
if(AU.cells>1){do{var i=AE(AU,"TD,TH");
if(AU._delete){AU.parentNode.removeChild(AU)
}}while((AU=i)!=null)
}}while((y=x)!=null)
}function O(AT,AW,AV){AT.rowSpan=1;
var x=AE(AW,"TR");
for(var AU=1;
AU<AV&&x;
AU++){var y=J.createElement("td");
y.innerHTML="&nbsp;";
if(tinyMCE.isMSIE){x.insertBefore(y,x.cells(AT.cellIndex))
}else{x.insertBefore(y,x.cells[AT.cellIndex])
}x=AE(x,"TR")
}}function p(Ab,Ad,AX){var y=D(Ad);
var AT=AX.cloneNode(false);
var Ac=AJ(y,AX.cells[0]);
var AY=null;
var AW=tinyMCE.getAttrib(Ad,"border");
var AV=null;
for(var Aa=0;
AV=C(y,Ac.rowindex,Aa);
Aa++){var AZ=null;
if(AY!=AV){for(var AU=0;
AU<AX.cells.length;
AU++){if(AV==AX.cells[AU]){AZ=AV.cloneNode(true);
break
}}}if(AZ==null){AZ=Ab.createElement("td");
AZ.innerHTML="&nbsp;"
}AZ.colSpan=1;
AZ.rowSpan=1;
AT.appendChild(AZ);
AY=AV
}return AT
}switch(Q){case"mceTableRowProps":if(t==null){return true
}if(w){var G=new Array();
G.file="../../plugins/table/row.htm";
G.width=380;
G.height=295;
G.width+=tinyMCE.getLang("lang_table_rowprops_delta_width",0);
G.height+=tinyMCE.getLang("lang_table_rowprops_delta_height",0);
tinyMCE.openWindow(G,{editor_id:s.editorId,inline:"yes"})
}return true;
case"mceTableCellProps":if(AO==null){return true
}if(w){var G=new Array();
G.file="../../plugins/table/cell.htm";
G.width=380;
G.height=295;
G.width+=tinyMCE.getLang("lang_table_cellprops_delta_width",0);
G.height+=tinyMCE.getLang("lang_table_cellprops_delta_height",0);
tinyMCE.openWindow(G,{editor_id:s.editorId,inline:"yes"})
}return true;
case"mceInsertTable":if(w){var G=new Array();
G.file="../../plugins/table/table.htm";
G.width=400;
G.height=250;
G.width+=tinyMCE.getLang("lang_table_table_delta_width",0);
G.height+=tinyMCE.getLang("lang_table_table_delta_height",0);
tinyMCE.openWindow(G,{editor_id:s.editorId,inline:"yes",action:AD})
}return true;
case"mceTableDelete":var c=tinyMCE.getParentElement(s.getFocusElement(),"table");
if(c){c.parentNode.removeChild(c);
s.repaint()
}return true;
case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":if(!b){return true
}if(b!=t.parentNode){b=t.parentNode
}if(b&&t){switch(Q){case"mceTableInsertRowBefore":if(!t||!AO){return true
}var AC=D(b);
var N=AJ(AC,AO);
var V=J.createElement("tr");
var T=null;
N.rowindex--;
if(N.rowindex<0){N.rowindex=0
}for(var AB=0;
AO=C(AC,N.rowindex,AB);
AB++){if(AO!=T){var d=I(AO);
if(d.rowspan==1){var g=J.createElement("td");
g.innerHTML="&nbsp;";
g.colSpan=AO.colSpan;
V.appendChild(g)
}else{AO.rowSpan=d.rowspan+1
}T=AO
}}t.parentNode.insertBefore(V,t);
break;
case"mceTableCutRow":if(!t||!AO){return true
}s.tableRowClipboard=p(J,b,t);
s.execCommand("mceTableDeleteRow");
break;
case"mceTableCopyRow":if(!t||!AO){return true
}s.tableRowClipboard=p(J,b,t);
break;
case"mceTablePasteRowBefore":if(!t||!AO){return true
}var V=s.tableRowClipboard.cloneNode(true);
var H=l(t,"TR");
if(H!=null){L(b,H,H.cells[0],V)
}t.parentNode.insertBefore(V,t);
break;
case"mceTablePasteRowAfter":if(!t||!AO){return true
}var u=AE(t,"TR");
var V=s.tableRowClipboard.cloneNode(true);
L(b,t,AO,V);
if(u==null){t.parentNode.appendChild(V)
}else{u.parentNode.insertBefore(V,u)
}break;
case"mceTableInsertRowAfter":if(!t||!AO){return true
}var AC=D(b);
var N=AJ(AC,AO);
var V=J.createElement("tr");
var T=null;
for(var AB=0;
AO=C(AC,N.rowindex,AB);
AB++){if(AO!=T){var d=I(AO);
if(d.rowspan==1){var g=J.createElement("td");
g.innerHTML="&nbsp;";
g.colSpan=AO.colSpan;
V.appendChild(g)
}else{AO.rowSpan=d.rowspan+1
}T=AO
}}if(V.hasChildNodes()){var u=AE(t,"TR");
if(u){u.parentNode.insertBefore(V,u)
}else{b.appendChild(V)
}}break;
case"mceTableDeleteRow":if(!t||!AO){return true
}var AC=D(b);
var N=AJ(AC,AO);
if(AC.length==1){b.parentNode.removeChild(b);
return true
}var a=t.cells;
var u=AE(t,"TR");
for(var AB=0;
AB<a.length;
AB++){if(a[AB].rowSpan>1){var g=a[AB].cloneNode(true);
var d=I(a[AB]);
g.rowSpan=d.rowspan-1;
var AI=u.cells[AB];
if(AI==null){u.appendChild(g)
}else{u.insertBefore(g,AI)
}}}var T=null;
for(var AB=0;
AO=C(AC,N.rowindex,AB);
AB++){if(AO!=T){var d=I(AO);
if(d.rowspan>1){AO.rowSpan=d.rowspan-1
}else{t=AO.parentNode;
if(t.parentNode){t._delete=true
}}T=AO
}}Y(b);
N.rowindex--;
if(N.rowindex<0){N.rowindex=0
}s.selection.selectNode(C(AC,N.rowindex,0),true,true);
break;
case"mceTableInsertColBefore":if(!t||!AO){return true
}var AC=D(b);
var N=AJ(AC,AO);
var T=null;
for(var z=0;
AO=C(AC,z,N.cellindex);
z++){if(AO!=T){var d=I(AO);
if(d.colspan==1){var g=J.createElement(AO.nodeName);
g.innerHTML="&nbsp;";
g.rowSpan=AO.rowSpan;
AO.parentNode.insertBefore(g,AO)
}else{AO.colSpan++
}T=AO
}}break;
case"mceTableInsertColAfter":if(!t||!AO){return true
}var AC=D(b);
var N=AJ(AC,AO);
var T=null;
for(var z=0;
AO=C(AC,z,N.cellindex);
z++){if(AO!=T){var d=I(AO);
if(d.colspan==1){var g=J.createElement(AO.nodeName);
g.innerHTML="&nbsp;";
g.rowSpan=AO.rowSpan;
var AI=AE(AO,"TD");
if(AI==null){AO.parentNode.appendChild(g)
}else{AI.parentNode.insertBefore(g,AI)
}}else{AO.colSpan++
}T=AO
}}break;
case"mceTableDeleteCol":if(!t||!AO){return true
}var AC=D(b);
var N=AJ(AC,AO);
var T=null;
if(AC.length>1&&AC[0].length<=1){b.parentNode.removeChild(b);
return true
}for(var z=0;
AO=C(AC,z,N.cellindex);
z++){if(AO!=T){var d=I(AO);
if(d.colspan>1){AO.colSpan=d.colspan-1
}else{if(AO.parentNode){AO.parentNode.removeChild(AO)
}}T=AO
}}N.cellindex--;
if(N.cellindex<0){N.cellindex=0
}s.selection.selectNode(C(AC,0,N.cellindex),true,true);
break;
case"mceTableSplitCells":if(!t||!AO){return true
}var K=I(AO);
var Z=K.colspan;
var e=K.rowspan;
if(Z>1||e>1){AO.colSpan=1;
for(var AK=1;
AK<Z;
AK++){var g=J.createElement("td");
g.innerHTML="&nbsp;";
t.insertBefore(g,AE(AO,"TD,TH"));
if(e>1){O(g,t,e)
}}O(AO,t,e)
}b=tinyMCE.getParentElement(s.getFocusElement(),"table");
break;
case"mceTableMergeCells":var AM=new Array();
var o=s.getSel();
var AC=D(b);
if(tinyMCE.isMSIE||o.rangeCount==1){if(w){var G=new Array();
var S=I(AO);
G.file="../../plugins/table/merge_cells.htm";
G.width=250;
G.height=105+(tinyMCE.isNS7?25:0);
G.width+=tinyMCE.getLang("lang_table_merge_cells_delta_width",0);
G.height+=tinyMCE.getLang("lang_table_merge_cells_delta_height",0);
tinyMCE.openWindow(G,{editor_id:s.editorId,inline:"yes",action:"update",numcols:S.colspan,numrows:S.rowspan});
return true
}else{var r=parseInt(AD.numrows);
var B=parseInt(AD.numcols);
var N=AJ(AC,AO);
if((""+r)=="NaN"){r=1
}if((""+B)=="NaN"){B=1
}var A=b.rows;
for(var z=N.rowindex;
z<AC.length;
z++){var AF=new Array();
for(var AB=N.cellindex;
AB<AC[z].length;
AB++){var E=C(AC,z,AB);
if(E&&!AN(AM,E)&&!AN(AF,E)){var k=AJ(AC,E);
if(k.cellindex<N.cellindex+B&&k.rowindex<N.rowindex+r){AF[AF.length]=E
}}}if(AF.length>0){AM[AM.length]=AF
}}}}else{var a=new Array();
var o=s.getSel();
var v=null;
var AL=null;
var X=-1,AS=-1,W,AQ;
if(o.rangeCount<2){return true
}for(var AK=0;
AK<o.rangeCount;
AK++){var AH=o.getRangeAt(AK);
var AO=AH.startContainer.childNodes[AH.startOffset];
if(!AO){break
}if(AO.nodeName=="TD"){a[a.length]=AO
}}var A=b.rows;
for(var z=0;
z<A.length;
z++){var AF=new Array();
for(var AB=0;
AB<A[z].cells.length;
AB++){var E=A[z].cells[AB];
for(var AK=0;
AK<a.length;
AK++){if(E==a[AK]){AF[AF.length]=E
}}}if(AF.length>0){AM[AM.length]=AF
}}var AL=new Array();
var v=null;
for(var z=0;
z<AC.length;
z++){for(var AB=0;
AB<AC[z].length;
AB++){AC[z][AB]._selected=false;
for(var AK=0;
AK<a.length;
AK++){if(AC[z][AB]==a[AK]){if(X==-1){X=AB;
AS=z
}W=AB;
AQ=z;
AC[z][AB]._selected=true
}}}}for(var z=AS;
z<=AQ;
z++){for(var AB=X;
AB<=W;
AB++){if(!AC[z][AB]._selected){alert("Invalid selection for merge.");
return true
}}}}var R=1,P=1;
var q=-1;
for(var z=0;
z<AM.length;
z++){var f=0;
for(var AB=0;
AB<AM[z].length;
AB++){var d=I(AM[z][AB]);
f+=d.colspan;
if(q!=-1&&d.rowspan!=q){alert("Invalid selection for merge.");
return true
}q=d.rowspan
}if(f>P){P=f
}q=-1
}var n=-1;
for(var AB=0;
AB<AM[0].length;
AB++){var j=0;
for(var z=0;
z<AM.length;
z++){var d=I(AM[z][AB]);
j+=d.rowspan;
if(n!=-1&&d.colspan!=n){alert("Invalid selection for merge.");
return true
}n=d.colspan
}if(j>R){R=j
}n=-1
}AO=AM[0][0];
AO.rowSpan=R;
AO.colSpan=P;
for(var z=0;
z<AM.length;
z++){for(var AB=0;
AB<AM[z].length;
AB++){var m=AM[z][AB].innerHTML;
var h=tinyMCE.regexpReplace(m,"[ \t\r\n]","");
if(h!="<br/>"&&h!="<br>"&&h!="&nbsp;"&&(AB+z>0)){AO.innerHTML+=m
}if(AM[z][AB]!=AO&&!AM[z][AB]._deleted){var N=AJ(AC,AM[z][AB]);
var AP=AM[z][AB].parentNode;
AP.removeChild(AM[z][AB]);
AM[z][AB]._deleted=true;
if(!AP.hasChildNodes()){AP.parentNode.removeChild(AP);
var AA=null;
for(var AB=0;
cellElm=C(AC,N.rowindex,AB);
AB++){if(cellElm!=AA&&cellElm.rowSpan>1){cellElm.rowSpan--
}AA=cellElm
}if(AO.rowSpan>1){AO.rowSpan--
}}}}}break
}b=tinyMCE.getParentElement(s.getFocusElement(),"table");
tinyMCE.handleVisualAid(b,true,tinyMCE.settings.visual,tinyMCE.selectedInstance);
tinyMCE.triggerNodeChange();
s.repaint()
}return true
}return false
}};
tinyMCE.addPlugin("table",TinyMCE_TablePlugin);