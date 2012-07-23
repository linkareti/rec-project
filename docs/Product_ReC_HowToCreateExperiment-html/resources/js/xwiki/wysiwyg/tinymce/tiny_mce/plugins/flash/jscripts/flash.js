var url=tinyMCE.getParam("flash_external_list_url");
if(url!=null){if(url.charAt(0)!="/"&&url.indexOf("://")==-1){url=tinyMCE.documentBasePath+"/"+url
}document.write('<script language="javascript" type="text/javascript" src="'+url+'"><\/script>')
}function init(){tinyMCEPopup.resizeToInnerSize();
document.getElementById("filebrowsercontainer").innerHTML=getBrowserHTML("filebrowser","file","flash","flash");
var E=getFlashListHTML("filebrowser","file","flash","flash");
if(E==""){document.getElementById("linklistrow").style.display="none"
}else{document.getElementById("linklistcontainer").innerHTML=E
}var C=document.forms[0];
var B=tinyMCE.getWindowArg("swffile");
var F=""+tinyMCE.getWindowArg("swfwidth");
var A=""+tinyMCE.getWindowArg("swfheight");
if(F.indexOf("%")!=-1){C.width2.value="%";
C.width.value=F.substring(0,F.length-1)
}else{C.width2.value="px";
C.width.value=F
}if(A.indexOf("%")!=-1){C.height2.value="%";
C.height.value=A.substring(0,A.length-1)
}else{C.height2.value="px";
C.height.value=A
}C.file.value=B;
C.insert.value=tinyMCE.getLang("lang_"+tinyMCE.getWindowArg("action"),"Insert",true);
selectByValue(C,"linklist",B);
if(isVisible("filebrowser")){document.getElementById("file").style.width="230px"
}if(typeof (tinyMCEFlashList)!="undefined"&&tinyMCEFlashList.length>0){for(var D=0;
D<C.linklist.length;
D++){if(C.linklist.options[D].value==tinyMCE.getWindowArg("swffile")){C.linklist.options[D].selected=true
}}}}function getFlashListHTML(){if(typeof (tinyMCEFlashList)!="undefined"&&tinyMCEFlashList.length>0){var B="";
B+='<select id="linklist" name="linklist" style="width: 250px" onfocus="tinyMCE.addSelectAccessibility(event, this, window);" onchange="this.form.file.value=this.options[this.selectedIndex].value;">';
B+='<option value="">---</option>';
for(var A=0;
A<tinyMCEFlashList.length;
A++){B+='<option value="'+tinyMCEFlashList[A][1]+'">'+tinyMCEFlashList[A][0]+"</option>"
}B+="</select>";
return B
}return""
}function insertFlash(){var B=document.forms[0];
var D="";
var C=B.file.value;
var E=B.width.value;
var A=B.height.value;
if(B.width2.value=="%"){E=E+"%"
}if(B.height2.value=="%"){A=A+"%"
}if(E==""){E=100
}if(A==""){A=100
}D+='<img src="'+(tinyMCE.getParam("theme_href")+"/images/spacer.gif")+'" mce_src="'+(tinyMCE.getParam("theme_href")+"/images/spacer.gif")+'" width="'+E+'" height="'+A+'" border="0" alt="'+C+'" title="'+C+'" class="mceItemFlash" />';
tinyMCEPopup.execCommand("mceInsertContent",true,D);
tinyMCE.selectedInstance.repaint();
tinyMCEPopup.close()
};