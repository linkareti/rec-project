function init(){tinyMCEPopup.resizeToInnerSize();
var A=document.forms[0];
A.numcols.value=tinyMCE.getWindowArg("numcols",1);
A.numrows.value=tinyMCE.getWindowArg("numrows",1)
}function mergeCells(){var B=new Array();
var A=document.forms[0];
B.numcols=A.numcols.value;
B.numrows=A.numrows.value;
tinyMCEPopup.execCommand("mceTableMergeCells",false,B);
tinyMCEPopup.close()
};