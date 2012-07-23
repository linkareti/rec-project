tinyMCE.importPluginLanguagePack("iespell","en,tr,cs,el,fr_ca,it,ko,sv,zh_cn,fr,de,pl,pt_br,nl,da,he,nb,ru,ru_KOI8-R,ru_UTF-8,nn,fi,cy,es,is,zh_tw,zh_tw_utf8,sk");
var TinyMCE_IESpellPlugin={getInfo:function(){return{longname:"IESpell (MSIE Only)",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_iespell.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){if(A=="iespell"&&(tinyMCE.isMSIE&&!tinyMCE.isOpera)){return tinyMCE.getButtonHTML(A,"lang_iespell_desc","{$pluginurl}/images/iespell.gif","mceIESpell")
}return""
},execCommand:function(F,A,E,G,C){if(E=="mceIESpell"){try{var B=new ActiveXObject("ieSpell.ieSpellExtension");
B.CheckDocumentNode(tinyMCE.getInstanceById(F).contentDocument.documentElement)
}catch(D){if(D.number==-2146827859){if(confirm(tinyMCE.getLang("lang_iespell_download","",true))){window.open("http://www.iespell.com/download.php","ieSpellDownload","")
}}else{alert("Error Loading ieSpell: Exception "+D.number)
}}return true
}return false
}};
tinyMCE.addPlugin("iespell",TinyMCE_IESpellPlugin);