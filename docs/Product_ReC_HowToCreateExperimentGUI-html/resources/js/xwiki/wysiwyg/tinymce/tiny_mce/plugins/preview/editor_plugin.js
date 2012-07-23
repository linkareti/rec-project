tinyMCE.importPluginLanguagePack("preview","en,tr,cs,de,el,fr_ca,it,ko,pt,sv,zh_cn,fa,fr,pl,pt_br,nl,da,he,nb,hu,ru,ru_KOI8-R,ru_UTF-8,nn,es,cy,is,zh_tw,zh_tw_utf8,sk");
var TinyMCE_PreviewPlugin={getInfo:function(){return{longname:"Preview",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_preview.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"preview":return tinyMCE.getButtonHTML(A,"lang_preview_desc","{$pluginurl}/images/preview.gif","mcePreview")
}return""
},execCommand:function(L,D,C,A,M){switch(C){case"mcePreview":var H=tinyMCE.getParam("plugin_preview_pageurl",null);
var B=tinyMCE.getParam("plugin_preview_width","550");
var G=tinyMCE.getParam("plugin_preview_height","600");
if(H){var N=new Array();
N.file=H;
N.width=B;
N.height=G;
tinyMCE.openWindow(N,{editor_id:L,resizable:"yes",scrollbars:"yes",inline:"yes",content:tinyMCE.getContent(),content_css:tinyMCE.getParam("content_css")})
}else{var F=window.open("","mcePreview","menubar=no,toolbar=no,scrollbars=yes,resizable=yes,left=20,top=20,width="+B+",height="+G);
var E="";
var I=tinyMCE.getContent();
var K=I.indexOf("<body"),J;
if(K!=-1){K=I.indexOf(">",K);
J=I.lastIndexOf("</body>");
I=I.substring(K+1,J)
}E+=tinyMCE.getParam("doctype");
E+='<html xmlns="http://www.w3.org/1999/xhtml">';
E+="<head>";
E+="<title>"+tinyMCE.getLang("lang_preview_desc")+"</title>";
E+='<base href="'+tinyMCE.settings.base_href+'" />';
E+='<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />';
E+='<link href="'+tinyMCE.getParam("content_css")+'" rel="stylesheet" type="text/css" />';
E+="</head>";
E+='<body dir="'+tinyMCE.getParam("directionality")+'">';
E+=I;
E+="</body>";
E+="</html>";
F.document.write(E);
F.document.close()
}return true
}return false
}};
tinyMCE.addPlugin("preview",TinyMCE_PreviewPlugin);