function init(){tinyMCEPopup.resizeToInnerSize();
var A=document.forms[0];
A.searchstring.value=tinyMCE.getWindowArg("searchstring");
A.casesensitivebox.checked=tinyMCE.getWindowArg("casesensitive");
A.backwards[0].checked=tinyMCE.getWindowArg("backwards");
A.backwards[1].checked=!tinyMCE.getWindowArg("backwards");
tinyMCEPopup.execCommand("mceResetSearch",false,{dummy:""},false)
}function searchNext(){var A=document.forms[0];
if(A.searchstring.value==""){return 
}tinyMCEPopup.execCommand("mceSearch",false,{string:A.searchstring.value,casesensitive:A.casesensitivebox.checked,backwards:A.backwards[0].checked},false);
window.focus()
}function cancelAction(){tinyMCEPopup.close()
};