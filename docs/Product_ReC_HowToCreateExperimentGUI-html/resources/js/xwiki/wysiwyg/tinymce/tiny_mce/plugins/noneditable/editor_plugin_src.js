var TinyMCE_NonEditablePlugin={getInfo:function(){return{longname:"Non editable elements",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_noneditable.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){tinyMCE.importCSS(A.getDoc(),tinyMCE.baseURL+"/plugins/noneditable/css/noneditable.css");
if(tinyMCE.isMSIE5_0){tinyMCE.settings.plugins=tinyMCE.settings.plugins.replace(/noneditable/gi,"Noneditable")
}if(tinyMCE.isGecko){tinyMCE.addEvent(A.getDoc(),"keyup",TinyMCE_NonEditablePlugin._fixKeyUp)
}},cleanup:function(I,F,D){switch(I){case"insert_to_editor_dom":var A=tinyMCE.getNodeTree(F,new Array(),1);
var E=tinyMCE.getParam("noneditable_editable_class","mceEditable");
var J=tinyMCE.getParam("noneditable_noneditable_class","mceNonEditable");
for(var C=0;
C<A.length;
C++){var H=A[C];
var B=tinyMCE.getAttrib(H,"contenteditable");
if(new RegExp("true|false","gi").test(B)){TinyMCE_NonEditablePlugin._setEditable(H,B=="true")
}if(tinyMCE.isMSIE){var G=H.className?H.className:"";
if(G.indexOf(E)!=-1){H.contentEditable=true
}if(G.indexOf(J)!=-1){H.contentEditable=false
}}}break;
case"insert_to_editor":if(tinyMCE.isMSIE){var E=tinyMCE.getParam("noneditable_editable_class","mceEditable");
var J=tinyMCE.getParam("noneditable_noneditable_class","mceNonEditable");
F=F.replace(new RegExp('class="(.*)('+E+')([^"]*)"',"gi"),'class="$1$2$3" contenteditable="true"');
F=F.replace(new RegExp('class="(.*)('+J+')([^"]*)"',"gi"),'class="$1$2$3" contenteditable="false"')
}break;
case"get_from_editor_dom":if(tinyMCE.getParam("noneditable_leave_contenteditable",false)){var A=tinyMCE.getNodeTree(F,new Array(),1);
for(var C=0;
C<A.length;
C++){A[C].removeAttribute("contenteditable")
}}break
}return F
},_fixKeyUp:function(E){var D=tinyMCE.selectedInstance;
var C=D.getSel();
var A=D.getRng();
var B=C.anchorNode;
if((E.keyCode==38||E.keyCode==37||E.keyCode==40||E.keyCode==39)&&(elm=TinyMCE_NonEditablePlugin._isNonEditable(B))!=null){A=D.getDoc().createRange();
A.selectNode(elm);
A.collapse(true);
C.removeAllRanges();
C.addRange(A);
tinyMCE.cancelEvent(E)
}},_isNonEditable:function(D){var B=tinyMCE.getParam("noneditable_editable_class","mceEditable");
var A=tinyMCE.getParam("noneditable_noneditable_class","mceNonEditable");
if(!D){return 
}do{var C=D.className?D.className:"";
if(C.indexOf(B)!=-1){return null
}if(C.indexOf(A)!=-1){return D
}}while(D=D.parentNode);
return null
},_setEditable:function(E,D){var B=tinyMCE.getParam("noneditable_editable_class","mceEditable");
var A=tinyMCE.getParam("noneditable_noneditable_class","mceNonEditable");
var C=E.className?E.className:"";
if(C.indexOf(B)!=-1||C.indexOf(A)!=-1){return 
}if((C=tinyMCE.getAttrib(E,"class"))!=""){C+=" "
}C+=D?B:A;
E.setAttribute("class",C);
E.className=C
}};
tinyMCE.addPlugin("noneditable",TinyMCE_NonEditablePlugin);