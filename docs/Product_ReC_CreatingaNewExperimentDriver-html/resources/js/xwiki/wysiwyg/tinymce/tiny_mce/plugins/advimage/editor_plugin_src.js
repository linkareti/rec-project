tinyMCE.importPluginLanguagePack("advimage","en,tr,de,sv,zh_cn,cs,fa,fr_ca,fr,pl,pt_br,nl,he,nb,ru,ru_KOI8-R,ru_UTF-8,nn,cy,es,is,zh_tw,zh_tw_utf8,sk,da");
var TinyMCE_AdvancedImagePlugin={getInfo:function(){return{longname:"Advanced image",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_advimage.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"image":return tinyMCE.getButtonHTML(A,"lang_image_desc","{$themeurl}/images/image.gif","mceAdvImage")
}return""
},execCommand:function(F,A,E,H,D){switch(E){case"mceAdvImage":var B=new Array();
B.file="../../plugins/advimage/image.htm";
B.width=480;
B.height=380;
B.width+=tinyMCE.getLang("lang_advimage_delta_width",0);
B.height+=tinyMCE.getLang("lang_advimage_delta_height",0);
var C=tinyMCE.getInstanceById(F);
var G=C.getFocusElement();
if(G!=null&&tinyMCE.getAttrib(G,"class").indexOf("mceItem")!=-1){return true
}tinyMCE.openWindow(B,{editor_id:F,inline:"yes"});
return true
}return false
},cleanup:function(type,content){switch(type){case"insert_to_editor_dom":var imgs=content.getElementsByTagName("img");
for(var i=0;
i<imgs.length;
i++){var onmouseover=tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i],"onmouseover"));
var onmouseout=tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i],"onmouseout"));
if((src=this._getImageSrc(onmouseover))!=""){if(tinyMCE.getParam("convert_urls")){src=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,src)
}imgs[i].setAttribute("onmouseover","this.src='"+src+"';")
}if((src=this._getImageSrc(onmouseout))!=""){if(tinyMCE.getParam("convert_urls")){src=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,src)
}imgs[i].setAttribute("onmouseout","this.src='"+src+"';")
}}break;
case"get_from_editor_dom":var imgs=content.getElementsByTagName("img");
for(var i=0;
i<imgs.length;
i++){var onmouseover=tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i],"onmouseover"));
var onmouseout=tinyMCE.cleanupEventStr(tinyMCE.getAttrib(imgs[i],"onmouseout"));
if((src=this._getImageSrc(onmouseover))!=""){if(tinyMCE.getParam("convert_urls")){src=eval(tinyMCE.settings.urlconverter_callback+"(src, null, true);")
}imgs[i].setAttribute("onmouseover","this.src='"+src+"';")
}if((src=this._getImageSrc(onmouseout))!=""){if(tinyMCE.getParam("convert_urls")){src=eval(tinyMCE.settings.urlconverter_callback+"(src, null, true);")
}imgs[i].setAttribute("onmouseout","this.src='"+src+"';")
}}break
}return content
},handleNodeChange:function(F,D,E,C,A,B){if(D==null){return 
}do{if(D.nodeName=="IMG"&&tinyMCE.getAttrib(D,"class").indexOf("mceItem")==-1){tinyMCE.switchClass(F+"_advimage","mceButtonSelected");
return true
}}while((D=D.parentNode));
tinyMCE.switchClass(F+"_advimage","mceButtonNormal");
return true
},_getImageSrc:function(B){var A,C=-1;
if(!B){return""
}if((C=B.indexOf("this.src="))!=-1){A=B.substring(C+10);
A=A.substring(0,A.indexOf("'"));
return A
}return""
}};
tinyMCE.addPlugin("advimage",TinyMCE_AdvancedImagePlugin);