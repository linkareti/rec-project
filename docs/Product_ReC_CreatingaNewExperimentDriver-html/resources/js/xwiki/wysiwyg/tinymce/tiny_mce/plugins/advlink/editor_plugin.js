tinyMCE.importPluginLanguagePack("advlink","en,tr,de,sv,zh_cn,cs,fa,fr_ca,fr,pl,pt_br,nl,he,nb,ru,ru_KOI8-R,ru_UTF-8,nn,cy,es,is,zh_tw,zh_tw_utf8,sk,da");
var TinyMCE_AdvancedLinkPlugin={getInfo:function(){return{longname:"Advanced link",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_advlink.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){A.addShortcut("ctrl","k","lang_advlink_desc","mceAdvLink")
},getControlHTML:function(A){switch(A){case"link":return tinyMCE.getButtonHTML(A,"lang_link_desc","{$themeurl}/images/link.gif","mceAdvLink")
}return""
},execCommand:function(G,D,C,A,H){switch(C){case"mceAdvLink":var F=false;
var E=tinyMCE.getInstanceById(G);
var J=E.getFocusElement();
var B=E.selection.getSelectedText();
if(tinyMCE.selectedElement){F=(tinyMCE.selectedElement.nodeName.toLowerCase()=="img")||(B&&B.length>0)
}if(F||(J!=null&&J.nodeName=="A")){var I=new Array();
I.file="../../plugins/advlink/link.htm";
I.width=480;
I.height=400;
I.width+=tinyMCE.getLang("lang_advlink_delta_width",0);
I.height+=tinyMCE.getLang("lang_advlink_delta_height",0);
tinyMCE.openWindow(I,{editor_id:G,inline:"yes"})
}return true
}return false
},handleNodeChange:function(F,D,E,C,A,B){if(D==null){return 
}do{if(D.nodeName=="A"&&tinyMCE.getAttrib(D,"href")!=""){tinyMCE.switchClass(F+"_advlink","mceButtonSelected");
return true
}}while((D=D.parentNode));
if(B){tinyMCE.switchClass(F+"_advlink","mceButtonNormal");
return true
}tinyMCE.switchClass(F+"_advlink","mceButtonDisabled");
return true
}};
tinyMCE.addPlugin("advlink",TinyMCE_AdvancedLinkPlugin);