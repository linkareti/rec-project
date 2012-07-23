function insertImage(){var D=document.forms[0].href.value;
var B=document.forms[0].width.value;
var A=document.forms[0].height.value;
var E=document.forms[0].align.options[document.forms[0].align.selectedIndex].value;
var C=document.forms[0].halign.options[document.forms[0].halign.selectedIndex].value;
tinyMCEPopup.restoreSelection();
tinyMCE.themes.wikieditor.insertImage(D,B,A,E,C);
tinyMCEPopup.close()
}function init(){var C=tinyMCE.getWindowArg("editor_id");
var A=tinyMCE.getWindowArg("src");
document.forms[0].href.value=A.replace(/%20/gi," ");
document.forms[0].width.value=tinyMCE.getWindowArg("width");
document.forms[0].height.value=tinyMCE.getWindowArg("height");
for(var B=0;
B<document.forms[0].align.options.length;
B++){if(document.forms[0].align.options[B].value==tinyMCE.getWindowArg("align")){document.forms[0].align.options.selectedIndex=B
}}for(var B=0;
B<document.forms[0].halign.options.length;
B++){if(document.forms[0].halign.options[B].value==tinyMCE.getWindowArg("halign")){document.forms[0].halign.options.selectedIndex=B
}}document.forms[0].insert.value=tinyMCE.getLang("lang_"+tinyMCE.getWindowArg("action"),"Insert",true)
}function cancelAction(){tinyMCEPopup.close()
}var preloadImg=new Image();
function resetImageData(){var A=document.forms[0];
A.width.value=A.height.value=""
}function updateImageData(){var A=document.forms[0];
if(A.width.value==""){A.width.value=preloadImg.width
}if(A.height.value==""){A.height.value=preloadImg.height
}}function getImageData(){preloadImg=new Image();
tinyMCE.addEvent(preloadImg,"load",updateImageData);
tinyMCE.addEvent(preloadImg,"error",function(){var A=document.forms[0];
A.width.value=A.height.value=""
});
preloadImg.src=tinyMCE.convertRelativeToAbsoluteURL(tinyMCE.settings.base_href,document.forms[0].src.value)
};