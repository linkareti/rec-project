function init(){tinyMCEPopup.resizeToInnerSize();
var A=document.forms[0];
A.searchstring.value=tinyMCE.getWindowArg("searchstring");
A.replacestring.value=tinyMCE.getWindowArg("replacestring");
A.casesensitivebox.checked=tinyMCE.getWindowArg("casesensitive");
tinyMCEPopup.execCommand("mceResetSearch",false,{dummy:""},false)
}function searchNext(B){var A=document.forms[0];
if(A.searchstring.value==""||A.searchstring.value==A.replacestring.value){return 
}tinyMCEPopup.execCommand("mceSearch",false,{string:A.searchstring.value,replacestring:A.replacestring.value,replacemode:B,casesensitive:A.casesensitivebox.checked,backwards:false},false);
window.focus()
}function cancelAction(){tinyMCEPopup.close()
};