tinyMCE.importPluginLanguagePack("fullpage","en,tr,sv");
var TinyMCE_FullPagePlugin={getInfo:function(){return{longname:"Fullpage",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_fullpage.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"fullpage":return tinyMCE.getButtonHTML(A,"lang_fullpage_desc","{$pluginurl}/images/fullpage.gif","mceFullPageProperties")
}return""
},execCommand:function(E,A,D,F,C){switch(D){case"mceFullPageProperties":var B=new Array();
B.file="../../plugins/fullpage/fullpage.htm";
B.width=430;
B.height=485+(tinyMCE.isOpera?5:0);
B.width+=tinyMCE.getLang("lang_fullpage_delta_width",0);
B.height+=tinyMCE.getLang("lang_fullpage_delta_height",0);
tinyMCE.openWindow(B,{editor_id:E,inline:"yes"});
return true;
case"mceFullPageUpdate":TinyMCE_FullPagePlugin._addToHead(tinyMCE.getInstanceById(E));
return true
}return false
},cleanup:function(L,J,I){switch(L){case"insert_to_editor":var G=J.toLowerCase();
var M=G.indexOf("<body"),N;
if(M!=-1){M=G.indexOf(">",M);
N=G.lastIndexOf("</body>");
I.fullpageTopContent=J.substring(0,M+1);
J=J.substring(M+1,N)
}else{if(!I.fullpageTopContent){var K=tinyMCE.getParam("fullpage_default_doctype",'<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">');
var F=tinyMCE.getParam("fullpage_default_encoding","utf-8");
var O=tinyMCE.getParam("fullpage_default_title","Untitled document");
var C=tinyMCE.getParam("fullpage_default_langcode","en");
var H=tinyMCE.getParam("fullpage_default_xml_pi",true);
var B=tinyMCE.getParam("fullpage_default_font_family","");
var E=tinyMCE.getParam("fullpage_default_font_size","");
var D=tinyMCE.getParam("fullpage_default_style","");
var A=tinyMCE.getParam("fullpage_default_text_color","");
O=O.replace(/&/g,"&amp;");
O=O.replace(/\"/g,"&quot;");
O=O.replace(/</g,"&lt;");
O=O.replace(/>/g,"&gt;");
G="";
if(H){G+='<?xml version="1.0" encoding="'+F+'"?>\n'
}G+=K+"\n";
G+='<html xmlns="http://www.w3.org/1999/xhtml" lang="'+C+'" xml:lang="'+C+'">\n';
G+="<head>\n";
G+="\t<title>"+O+"</title>\n";
G+='\t<meta http-equiv="Content-Type" content="text/html; charset='+F+'" />\n';
G+="</head>\n";
G+="<body";
if(B!=""||E!=""){G+=' style="';
if(D!=""){G+=D+";"
}if(B!=""){G+="font-family: "+B+";"
}if(E!=""){G+="font-size: "+E+";"
}G+='"'
}if(A!=""){G+=' text="'+A+'"'
}G+=">\n";
I.fullpageTopContent=G
}}this._addToHead(I);
break;
case"get_from_editor":if(I.fullpageTopContent){J=I.fullpageTopContent+J+"\n</body>\n</html>"
}break
}return J
},_addToHead:function(E){var J=E.getDoc();
var I=J.getElementsByTagName("head")[0];
var G=J.body;
var F=E.fullpageTopContent;
var H=J.createElement("body");
var B,D,A,C;
F=F.replace(/(\r|\n)/gi,"");
F=F.replace(/<\?[^\>]*\>/gi,"");
F=F.replace(/<\/?(!DOCTYPE|head|html)[^\>]*\>/gi,"");
F=F.replace(/<script(.*?)<\/script>/gi,"");
F=F.replace(/<title(.*?)<\/title>/gi,"");
F=F.replace(/<(meta|base)[^>]*>/gi,"");
F=F.replace(/<link([^>]*)\/>/gi,'<pre mce_type="link" $1></pre>');
F=F.replace(/<body/gi,'<div mce_type="body"');
F+="</div>";
H.innerHTML=F;
G.vLink=G.aLink=G.link=G.text="";
G.style.cssText="";
B=I.getElementsByTagName("link");
for(D=0;
D<B.length;
D++){if(tinyMCE.getAttrib(B[D],"mce_head")=="true"){B[D].parentNode.removeChild(B[D])
}}B=H.getElementsByTagName("pre");
for(D=0;
D<B.length;
D++){C=tinyMCE.getAttrib(B[D],"media");
if(tinyMCE.getAttrib(B[D],"mce_type")=="link"&&(C==""||C=="screen"||C=="all")&&tinyMCE.getAttrib(B[D],"rel")=="stylesheet"){A=J.createElement("link");
A.rel="stylesheet";
A.href=tinyMCE.getAttrib(B[D],"href");
A.setAttribute("mce_head","true");
I.appendChild(A)
}}B=H.getElementsByTagName("div");
if(B.length>0){G.style.cssText=tinyMCE.getAttrib(B[0],"style");
if((C=tinyMCE.getAttrib(B[0],"leftmargin"))!=""&&G.style.marginLeft==""){G.style.marginLeft=C+"px"
}if((C=tinyMCE.getAttrib(B[0],"rightmargin"))!=""&&G.style.marginRight==""){G.style.marginRight=C+"px"
}if((C=tinyMCE.getAttrib(B[0],"topmargin"))!=""&&G.style.marginTop==""){G.style.marginTop=C+"px"
}if((C=tinyMCE.getAttrib(B[0],"bottommargin"))!=""&&G.style.marginBottom==""){G.style.marginBottom=C+"px"
}G.dir=tinyMCE.getAttrib(B[0],"dir");
G.vLink=tinyMCE.getAttrib(B[0],"vlink");
G.aLink=tinyMCE.getAttrib(B[0],"alink");
G.link=tinyMCE.getAttrib(B[0],"link");
G.text=tinyMCE.getAttrib(B[0],"text");
if((C=tinyMCE.getAttrib(B[0],"background"))!=""){G.style.backgroundImage=C
}if((C=tinyMCE.getAttrib(B[0],"bgcolor"))!=""){G.style.backgroundColor=C
}}}};
tinyMCE.addPlugin("fullpage",TinyMCE_FullPagePlugin);