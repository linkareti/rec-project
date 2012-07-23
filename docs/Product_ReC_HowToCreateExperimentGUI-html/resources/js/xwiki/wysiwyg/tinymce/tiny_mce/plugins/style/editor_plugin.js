tinyMCE.importPluginLanguagePack("style","en");
var TinyMCE_StylePlugin={getInfo:function(){return{longname:"Style",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_style.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"styleprops":return tinyMCE.getButtonHTML(A,"lang_style_styleinfo_desc","{$pluginurl}/images/style_info.gif","mceStyleProps",true)
}return""
},execCommand:function(F,A,E,G,C){var D,B;
switch(E){case"mceStyleProps":TinyMCE_StylePlugin._styleProps();
return true;
case"mceSetElementStyle":B=tinyMCE.getInstanceById(F);
D=B.selection.getFocusElement();
if(D){D.style.cssText=C;
B.repaint()
}return true
}return false
},handleNodeChange:function(F,D,E,C,A,B){},_styleProps:function(){var A=tinyMCE.selectedInstance.selection.getFocusElement();
if(!A){return 
}tinyMCE.openWindow({file:"../../plugins/style/props.htm",width:480+tinyMCE.getLang("lang_style_props_delta_width",0),height:320+tinyMCE.getLang("lang_style_props_delta_height",0)},{editor_id:tinyMCE.selectedInstance.editorId,inline:"yes",style_text:A.style.cssText})
}};
tinyMCE.addPlugin("style",TinyMCE_StylePlugin);