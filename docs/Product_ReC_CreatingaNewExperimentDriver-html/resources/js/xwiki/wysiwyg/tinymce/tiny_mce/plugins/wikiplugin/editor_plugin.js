var TinyMCE_wikipluginPlugin={getInfo:function(){return{longname:"Wiki Plugin"}
},initInstance:function(A){},execCommand:function(D,A,C,E,B){return false
},handleNodeChange:function(F,D,E,C,A,B){return true
},setupContent:function(C,A,B){},onChange:function(A){},handleEvent:function(A){top.status="wiki plugin event: "+A.type;
return true
},cleanup:function(B,C,D){switch(B){case"get_from_editor":var E=D.editorId+"_content";
var A=document.getElementById(E);
if(A&&A.style.display!="none"){return A.value
}else{C=wikiEditor.convertInternal(C)
}break;
case"insert_to_editor":C=wikiEditor.convertExternal(C);
break;
case"get_from_editor_dom":C=wikiEditor.encodeNode(C);
C=wikiEditor.tagListInternal(C);
break;
case"insert_to_editor_dom":break
}return C
}};
tinyMCE.addPlugin("wikiplugin",TinyMCE_wikipluginPlugin);