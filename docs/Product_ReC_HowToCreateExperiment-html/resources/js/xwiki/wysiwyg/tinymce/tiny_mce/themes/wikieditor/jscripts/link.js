function init(){var G=tinyMCE.getWindowArg("text").toString();
var B=tinyMCE.getWindowArg("href").toString();
var E=tinyMCE.getWindowArg("target").toString();
document.forms[0].wiki_text.value=G;
document.forms[0].web_text.value=G;
document.forms[0].file_text.value=G;
document.forms[0].attach_text.value=G;
document.forms[0].email_text.value=G;
document.forms[0].wiki_page.value=G;
if(linkPopupHasTab("wiki_tab")){document.getElementById("wiki_tab").className="current";
document.getElementById("wiki_panel").className="current"
}else{if(linkPopupHasTab("web_tab")){document.getElementById("web_tab").className="current";
document.getElementById("web_panel").className="current"
}else{if(linkPopupHasTab("attachments_tab")){document.getElementById("attachments_tab").className="current";
document.getElementById("attachments_panel").className="current"
}else{if(linkPopupHasTab("file_tab")){document.getElementById("file_tab").className="current";
document.getElementById("file_panel").className="current"
}else{if(linkPopupHasTab("email_tab")){document.getElementById("email_tab").className="current";
document.getElementById("email_panel").className="current"
}}}}}if((B!=null)&&(B!="")){if(B.search(/(https?|ftp):\/\/[-a-zA-Z0-9+&@#\/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#\/%=~_|]/gi)>-1){if(linkPopupHasTab("web_tab")){mcTabs.displayTab("web_tab","web_panel");
document.forms[0].web_page.value=B;
document.forms[0].web_target.value=E
}}else{if(B.search(/wikiattachment:-:(.*?)/gi)>-1){if(linkPopupHasTab("attachments_tab")){mcTabs.displayTab("attachments_tab","attachments_panel");
document.forms[0].attach_file.value=B.replace(/wikiattachment:-:/gi,"").replace(/%20/gi," ")
}}else{if(B.search(/mailto:(.*?)/gi)>-1){if(linkPopupHasTab("email_tab")){mcTabs.displayTab("email_tab","email_panel");
document.forms[0].email.value=B.replace(/mailto:/gi,"")
}}else{if(B.search(/file:(\/\/\/\/\/)(.*?)/gi)>-1){if(linkPopupHasTab("file_tab")){mcTabs.displayTab("file_tab","file_panel")
}}else{if(B.search(/file:(\/\/)(.*?)/gi)>-1){if(linkPopupHasTab("file_tab")){mcTabs.displayTab("file_tab","file_panel")
}}else{if(linkPopupHasTab("wiki_tab")){mcTabs.displayTab("wiki_tab","wiki_panel");
var D="",A=B;
if(B.indexOf(".")>-1){D=B.substring(0,B.indexOf("."));
A=B.substring(B.indexOf(".")+1,B.length)
}document.forms[0].wiki_space.value=D;
document.forms[0].wiki_page.value=A;
document.forms[0].wiki_target.value=E
}}}}}}}document.forms[0].insert.value=tinyMCE.getLang("lang_"+tinyMCE.getWindowArg("action"),"Insert",true);
var C=tinyMCE.getWindowArg("className");
var F=tinyMCE.getWindowArg("editor_id")
}function insertLink(){var I=document.getElementById("wiki_tab");
var N=document.getElementById("web_tab");
var B=document.getElementById("file_tab");
var C=document.getElementById("attachments_tab");
var G=document.getElementById("email_tab");
var F;
tinyMCEPopup.restoreSelection();
if(I!=null&&I.className=="current"){var D=document.forms[0].wiki_page.value;
var A=document.forms[0].wiki_space.value;
var E=document.forms[0].wiki_text.value;
var H=document.forms[0].wiki_target.value;
tinyMCE.themes.wikieditor.insertLink(D,H,E,A,"",F,"")
}else{if(N!=null&&N.className=="current"){var M=document.forms[0].web_text.value;
var D=document.forms[0].web_page.value;
var H=document.forms[0].web_target.value;
tinyMCE.themes.wikieditor.insertLink(D,H,M,"","",F,"")
}else{if(C!=null&&C.className=="current"){var D=document.forms[0].attach_file.value;
var L=document.forms[0].attach_text.value;
tinyMCE.themes.wikieditor.insertLink("wikiattachment:-:"+D,"",L,"","",F,"")
}else{if(B!=null&&B.className=="current"){var L=document.forms[0].file_text.value;
var D=document.forms[0].filepaths.value;
var K="";
if(":"==D.charAt(D.indexOf("\\")-1)){K="file://"+D.replace(/\\/gi,"/")
}else{if(D.substring(0,2)=="\\\\"){K="file:///"+D.replace(/\\/gi,"/")
}}tinyMCE.themes.wikieditor.insertLink(K,"",L,"","",F,"")
}else{if(G!=null&&G.className=="current"){var L=document.forms[0].email_text.value;
var J=document.forms[0].email.value;
D="mailto:"+J;
tinyMCE.themes.wikieditor.insertLink(D,"",L,"","",F,"")
}}}}}tinyMCEPopup.close()
}function cancelAction(){tinyMCEPopup.close()
}function populateWikiForm(A){document.forms[0].href.value=A
}function updateAttachName(B){B.xredirect.value=location;
var C=B.filepath.value;
if(C==""){return false
}var A=C.lastIndexOf("\\");
if(A==-1){A=C.lastIndexOf("/")
}C=C.substring(A+1);
if(B.filename.value==C){return true
}if(B.filename.value==""){B.filename.value=C
}return true
}function linkPopupHasTab(H){var G=tinyMCE.getParam("use_linkeditor_tabs");
if(G==null||G==""){G="wiki_tab"
}var A=G.split(",");
var C=false;
for(var B=0;
B<A.length;
B++){var E=/(\S+(\s+\S+)*)+/i;
var F=E.exec(A[B]);
var D=(F&&F[1])?F[1]:"";
if(D==H){C=true
}}return C
};