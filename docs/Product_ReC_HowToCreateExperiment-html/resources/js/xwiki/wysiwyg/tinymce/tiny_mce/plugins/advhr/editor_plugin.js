tinyMCE.importPluginLanguagePack("advhr","en,tr,de,sv,zh_cn,cs,fa,fr_ca,fr,pl,pt_br,nl,da,he,nb,hu,ru,ru_KOI8-R,ru_UTF-8,nn,fi,es,cy,is,zh_tw,zh_tw_utf8,sk");
var TinyMCE_AdvancedHRPlugin={getInfo:function(){return{longname:"Advanced HR",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_advhr.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"advhr":return tinyMCE.getButtonHTML(A,"lang_insert_advhr_desc","{$pluginurl}/images/advhr.gif","mceAdvancedHr")
}return""
},execCommand:function(F,E,C,A,G){switch(C){case"mceAdvancedHr":var H=new Array();
H.file="../../plugins/advhr/rule.htm";
H.width=250;
H.height=160;
H.width+=tinyMCE.getLang("lang_advhr_delta_width",0);
H.height+=tinyMCE.getLang("lang_advhr_delta_height",0);
var I="",B="",D="";
if(tinyMCE.selectedElement!=null&&tinyMCE.selectedElement.nodeName.toLowerCase()=="hr"){tinyMCE.hrElement=tinyMCE.selectedElement;
if(tinyMCE.hrElement){I=tinyMCE.hrElement.getAttribute("size")?tinyMCE.hrElement.getAttribute("size"):"";
B=tinyMCE.hrElement.getAttribute("width")?tinyMCE.hrElement.getAttribute("width"):"";
D=tinyMCE.hrElement.getAttribute("noshade")?tinyMCE.hrElement.getAttribute("noshade"):""
}tinyMCE.openWindow(H,{editor_id:F,size:I,width:B,noshade:D,mceDo:"update"})
}else{if(tinyMCE.isMSIE){tinyMCE.execInstanceCommand(F,"mceInsertContent",false,"<hr />")
}else{tinyMCE.openWindow(H,{editor_id:F,inline:"yes",size:I,width:B,noshade:D,mceDo:"insert"})
}}return true
}return false
},handleNodeChange:function(F,D,E,C,A,B){if(D==null){return 
}do{if(D.nodeName=="HR"){tinyMCE.switchClass(F+"_advhr","mceButtonSelected");
return true
}}while((D=D.parentNode));
tinyMCE.switchClass(F+"_advhr","mceButtonNormal");
return true
}};
tinyMCE.addPlugin("advhr",TinyMCE_AdvancedHRPlugin);