function init(){var A=document.forms[0];
A.width.value=tinyMCE.getWindowArg("width");
A.size.value=tinyMCE.getWindowArg("size");
A.insert.value=tinyMCE.getLang("lang_"+tinyMCE.getWindowArg("mceDo"),"Insert",true);
if(tinyMCE.getWindowArg("noshade")){A.noshade.checked=true
}if(tinyMCE.getWindowArg("width").lastIndexOf("%")!=-1){A.width2.value="%";
A.width.value=A.width.value.substring(0,A.width.value.length-1)
}}function insertHR(){var A=document.forms[0];
var D=A.width.value;
var C=A.size.value;
var B="<hr";
if(C!=""&&C!=0){B+=' size="'+C+'"'
}if(D!=""&&D!=0){B+=' width="'+D;
if(A.width2.value=="%"){B+="%"
}B+='"'
}if(A.noshade.checked==true){B+=' noshade="noshade"'
}B+=" />";
tinyMCEPopup.execCommand("mceInsertContent",true,B);
tinyMCEPopup.close()
}function cancelAction(){tinyMCEPopup.close()
};