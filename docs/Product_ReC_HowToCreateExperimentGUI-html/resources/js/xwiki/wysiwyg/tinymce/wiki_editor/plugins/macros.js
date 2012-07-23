WikiEditor.prototype.initMacrosPlugin=function(){if(!this.isPluginLoaded("core")){alert("Macros Plugin: You must load the core syntax plugin before!");
return 
}this.addToolbarHandler("handleMacrosButtons")
};
wikiEditor.initMacrosPlugin();
WikiEditor.prototype.insertMacro=function(B,A){this.core.execInstanceCommand(B,"mceInsertRawHTML",false,A)
};
WikiEditor.prototype.handleMacrosButtons=function(F,D,E,C,A,B){tinyMCE.switchClass(F+"_macro","mceButtonNormal");
do{switch(D.nodeName.toLowerCase()){case"macro":tinyMCE.switchClass(F+"_macro","mceButtonSelected");
break
}}while((D=D.parentNode))
};
WikiEditor.prototype.getMacrosToolbar=function(){return this.getMacrosControls("macro")
};
WikiEditor.prototype.getMacrosControls=function(A){var B="";
switch(A){case"macro":B=this.createButtonHTML("macro","macro.gif","lang_macro_desc","wikiMacro",true);
break
}return B
};