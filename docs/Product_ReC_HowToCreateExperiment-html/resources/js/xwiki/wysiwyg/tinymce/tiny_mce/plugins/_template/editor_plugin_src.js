tinyMCE.importPluginLanguagePack("template","en,tr,he,nb,ru,ru_KOI8-R,ru_UTF-8,nn,fi,cy,es,is,pl");
var TinyMCE_TemplatePlugin={getInfo:function(){return{longname:"Template plugin",author:"Your name",authorurl:"http://www.yoursite.com",infourl:"http://www.yoursite.com/docs/template.html",version:"1.0"}
},initInstance:function(A){alert("Initialization parameter:"+tinyMCE.getParam("template_someparam",false));
A.addShortcut("ctrl","t","lang_template_desc","mceTemplate")
},getControlHTML:function(A){switch(A){case"template":return tinyMCE.getButtonHTML(A,"lang_template_desc","{$pluginurl}/images/template.gif","mceTemplate",true)
}return""
},execCommand:function(E,A,D,F,C){switch(D){case"mceTemplate":if(F){var B=new Array();
B.file="../../plugins/template/popup.htm";
B.width=300;
B.height=200;
tinyMCE.openWindow(B,{editor_id:E,some_custom_arg:"somecustomdata"});
tinyMCE.triggerNodeChange(false)
}else{alert("execCommand: mceTemplate gets called from popup.")
}return true
}return false
},handleNodeChange:function(F,D,E,C,A,B){if(D.parentNode.nodeName=="STRONG"||D.parentNode.nodeName=="B"){tinyMCE.switchClass(F+"_template","mceButtonSelected");
return true
}tinyMCE.switchClass(F+"_template","mceButtonNormal")
},setupContent:function(C,A,B){},onChange:function(A){},handleEvent:function(A){top.status="template plugin event: "+A.type;
return true
},cleanup:function(A,B,C){switch(A){case"get_from_editor":alert("[FROM] Value HTML string: "+B);
break;
case"insert_to_editor":alert("[TO] Value HTML string: "+B);
break;
case"get_from_editor_dom":alert("[FROM] Value DOM Element "+B.innerHTML);
break;
case"insert_to_editor_dom":alert("[TO] Value DOM Element: "+B.innerHTML);
break
}return B
},_someInternalFunction:function(B,A){return 1
}};
tinyMCE.addPlugin("template",TinyMCE_TemplatePlugin);