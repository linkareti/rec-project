var TinyMCE_wikipluginPlugin={getInfo:function(){return{longname:"Wiki Plugin"}
},initInstance:function(A){},execCommand:function(D,A,C,E,B){return false
},handleNodeChange:function(F,D,E,C,A,B){return true
},setupContent:function(C,A,B){},onChange:function(A){},handleEvent:function(A){top.status="wiki plugin event: "+A.type;
return true
},cleanup:function(A,B,C){switch(A){case"get_from_editor":B=wikiEditor.convertInternal(B);
break;
case"insert_to_editor":B=wikiEditor.convertExternal(B);
break;
case"get_from_editor_dom":B=wikiEditor.tagListInternal(B);
break;
case"insert_to_editor_dom":break
}return B
}};
tinyMCE.addPlugin("wikiplugin",TinyMCE_wikipluginPlugin);