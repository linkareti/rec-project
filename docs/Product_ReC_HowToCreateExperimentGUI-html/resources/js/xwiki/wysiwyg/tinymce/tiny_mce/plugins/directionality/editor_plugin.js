tinyMCE.importPluginLanguagePack("directionality","en,tr,sv,fr_ca,zh_cn,cs,da,he,nb,de,hu,ru,ru_KOI8-R,ru_UTF-8,nn,es,cy,is,pl,nl,fr,pt_br");
var TinyMCE_DirectionalityPlugin={getInfo:function(){return{longname:"Directionality",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_directionality.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"ltr":return tinyMCE.getButtonHTML(A,"lang_directionality_ltr_desc","{$pluginurl}/images/ltr.gif","mceDirectionLTR");
case"rtl":return tinyMCE.getButtonHTML(A,"lang_directionality_rtl_desc","{$pluginurl}/images/rtl.gif","mceDirectionRTL")
}return""
},execCommand:function(E,A,D,G,C){switch(D){case"mceDirectionLTR":var B=tinyMCE.getInstanceById(E);
var F=tinyMCE.getParentElement(B.getFocusElement(),"p,div,td,h1,h2,h3,h4,h5,h6,pre,address");
if(F){F.setAttribute("dir","ltr")
}tinyMCE.triggerNodeChange(false);
return true;
case"mceDirectionRTL":var B=tinyMCE.getInstanceById(E);
var F=tinyMCE.getParentElement(B.getFocusElement(),"p,div,td,h1,h2,h3,h4,h5,h6,pre,address");
if(F){F.setAttribute("dir","rtl")
}tinyMCE.triggerNodeChange(false);
return true
}return false
},handleNodeChange:function(H,B,G,C,E,D){function I(K,J){return K.getAttribute(J)?K.getAttribute(J):""
}if(B==null){return 
}var F=tinyMCE.getParentElement(B,"p,div,td,h1,h2,h3,h4,h5,h6,pre,address");
if(!F){tinyMCE.switchClass(H+"_ltr","mceButtonDisabled");
tinyMCE.switchClass(H+"_rtl","mceButtonDisabled");
return true
}tinyMCE.switchClass(H+"_ltr","mceButtonNormal");
tinyMCE.switchClass(H+"_rtl","mceButtonNormal");
var A=I(F,"dir");
if(A=="ltr"||A==""){tinyMCE.switchClass(H+"_ltr","mceButtonSelected")
}else{tinyMCE.switchClass(H+"_rtl","mceButtonSelected")
}return true
}};
tinyMCE.addPlugin("directionality",TinyMCE_DirectionalityPlugin);