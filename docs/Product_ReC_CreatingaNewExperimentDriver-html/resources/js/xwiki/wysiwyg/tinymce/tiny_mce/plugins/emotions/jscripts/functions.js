function init(){tinyMCEPopup.resizeToInnerSize()
}function insertEmotion(C,B){B=tinyMCE.getLang(B);
if(B==null){B=""
}B=B.replace(/&/g,"&amp;");
B=B.replace(/\"/g,"&quot;");
B=B.replace(/</g,"&lt;");
B=B.replace(/>/g,"&gt;");
var A='<img src="'+tinyMCE.baseURL+"/plugins/emotions/images/"+C+'" mce_src="'+tinyMCE.baseURL+"/plugins/emotions/images/"+C+'" border="0" alt="'+B+'" title="'+B+'" />';
tinyMCE.execCommand("mceInsertContent",false,A);
tinyMCEPopup.close()
};