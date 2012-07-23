tinyMCE.importPluginLanguagePack("searchreplace","en,tr,sv,zh_cn,fa,fr_ca,fr,de,pl,pt_br,cs,nl,da,he,nb,hu,ru,ru_KOI8-R,ru_UTF-8,nn,fi,cy,es,is,zh_tw,zh_tw_utf8,sk");
var TinyMCE_SearchReplacePlugin={getInfo:function(){return{longname:"Search/Replace",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_searchreplace.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){A.addShortcut("ctrl","f","lang_searchreplace_search_desc","mceSearch",true)
},getControlHTML:function(A){switch(A){case"search":return tinyMCE.getButtonHTML(A,"lang_searchreplace_search_desc","{$pluginurl}/images/search.gif","mceSearch",true);
case"replace":return tinyMCE.getButtonHTML(A,"lang_searchreplace_replace_desc","{$pluginurl}/images/replace.gif","mceSearchReplace",true)
}return""
},execCommand:function(J,G,F,D,M){var O=tinyMCE.getInstanceById(J);
function K(Q,P){M[Q]=typeof (M[Q])=="undefined"?P:M[Q]
}function C(P,R,Q){O.execCommand("mceInsertContent",false,R)
}if(!M){M=new Array()
}K("editor_id",J);
K("searchstring","");
K("replacestring",null);
K("replacemode","none");
K("casesensitive",false);
K("backwards",false);
K("wrap",false);
K("wholeword",false);
K("inline","yes");
switch(F){case"mceResetSearch":tinyMCE.lastSearchRng=null;
return true;
case"mceSearch":if(D){var N=new Array();
if(M.replacestring!=null){N.file="../../plugins/searchreplace/replace.htm";
N.width=320;
N.height=100+(tinyMCE.isNS7?20:0);
N.width+=tinyMCE.getLang("lang_searchreplace_replace_delta_width",0);
N.height+=tinyMCE.getLang("lang_searchreplace_replace_delta_height",0)
}else{N.file="../../plugins/searchreplace/search.htm";
N.width=310;
N.height=105+(tinyMCE.isNS7?25:0);
N.width+=tinyMCE.getLang("lang_searchreplace_search_delta_width",0);
N.height+=tinyMCE.getLang("lang_searchreplace_replace_delta_height",0)
}O.execCommand("SelectAll");
if(tinyMCE.isMSIE){var B=O.selection.getRng();
B.collapse(true);
B.select()
}else{O.selection.getSel().collapseToStart()
}tinyMCE.openWindow(N,M)
}else{var I=tinyMCE.getInstanceById(J).contentWindow;
var L=tinyMCE.getInstanceById(J).contentWindow.document;
var H=tinyMCE.getInstanceById(J).contentWindow.document.body;
if(H.innerHTML==""){alert(tinyMCE.getLang("lang_searchreplace_notfound"));
return true
}if(M.replacemode=="current"){C(M.string,M.replacestring,M.backwards);
M.replacemode="none";
tinyMCE.execInstanceCommand(J,"mceSearch",D,M,false);
return true
}if(tinyMCE.isMSIE){var A=tinyMCE.lastSearchRng?tinyMCE.lastSearchRng:L.selection.createRange();
var E=0;
if(M.wholeword){E=E|2
}if(M.casesensitive){E=E|4
}if(!A.findText){alert("This operation is currently not supported by this browser.");
return true
}if(M.replacemode=="all"){while(A.findText(M.string,M.backwards?-1:1,E)){A.scrollIntoView();
A.select();
A.collapse(false);
C(M.string,M.replacestring,M.backwards)
}alert(tinyMCE.getLang("lang_searchreplace_allreplaced"));
return true
}if(A.findText(M.string,M.backwards?-1:1,E)){A.scrollIntoView();
A.select();
A.collapse(M.backwards);
tinyMCE.lastSearchRng=A
}else{alert(tinyMCE.getLang("lang_searchreplace_notfound"))
}}else{if(M.replacemode=="all"){while(I.find(M.string,M.casesensitive,M.backwards,M.wrap,M.wholeword,false,false)){C(M.string,M.replacestring,M.backwards)
}alert(tinyMCE.getLang("lang_searchreplace_allreplaced"));
return true
}if(!I.find(M.string,M.casesensitive,M.backwards,M.wrap,M.wholeword,false,false)){alert(tinyMCE.getLang("lang_searchreplace_notfound"))
}}}return true;
case"mceSearchReplace":M.replacestring="";
tinyMCE.execInstanceCommand(J,"mceSearch",D,M,false);
return true
}return false
}};
tinyMCE.addPlugin("searchreplace",TinyMCE_SearchReplacePlugin);