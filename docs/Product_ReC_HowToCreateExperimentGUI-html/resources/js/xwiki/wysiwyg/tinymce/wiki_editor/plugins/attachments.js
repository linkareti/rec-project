WikiEditor.prototype.initAttachmentsPlugin=function(){if(!this.isPluginLoaded("core")){alert("Attachment Plugin: You must load the core syntax plugin before!");
return 
}this.addExternalProcessorBefore("convertTableExternal",(/{\s*image\s*:\s*(.*?)(\|(.*?))?(\|(.*?))?(\|(.*?))?(\|(.*?))?}/i),"convertImageExternal");
this.addInternalProcessorBefore("convertStyleInternal",(/(<div\s*class=\"img(.*?)\"\s*>\s*)?<img\s*([^>]*)(class=\"wikiimage\")\s*([^>]*)\/>(<\/div>)?/i),"convertImageInternal");
this.addExternalProcessorBefore("convertTableExternal",(/{\s*attach\s*:\s*(.*?)(\|(.*?))?}/i),"convertAttachmentExternal");
this.addInternalProcessorBefore("convertLinkInternal",(/<a\s*href=\"wikiattachment:-:(.*?)\"\s*([^>]*)>(.*?)<\/a>/i),"convertAttachmentInternal");
this.addToolbarHandler("handleAttachmentsButtons")
};
wikiEditor.initAttachmentsPlugin();
WikiEditor.prototype.ATTACHMENT_CLASS_NAME="";
WikiEditor.prototype.insertAttachment=function(D,C,A){var B=((C!=null)&&this.trimString(C)!="")?C:A;
this.core.execInstanceCommand(D,"mceInsertRawHTML",false,'<a href="wikiattachment:-:'+A+'" class="'+this.ATTACHMENT_CLASS_NAME+'">'+B+"</a>")
};
WikiEditor.prototype.convertImageInternal=function(I,O,H){var J="";
var B;
var F="";
var D=this.trimString(O[3]+" "+O[5]);
var K=this.readAttributes(D);
if(O[2]&&this.trimString(O[2])!=""){F=this.trimString(O[2])
}if(K&&(B=K.src)!=null){B=this.trimString(B);
var M=tinyMCE.getParam("wiki_images_path").toString();
B=M.substring(0,M.indexOf("/",2))+"/"+XWiki.servletpath+B.substring(B.indexOf("/",3)+1);
var L=new RegExp(this.getImagePath().replace(/\+/g,"\\+")+"(.*)","i");
var A=L.exec(B);
if(A){var E=unescape(A[1]);
J="{image:"+E;
var C=K.width?this.trimString(K.width):"";
var N=K.height?this.trimString(K.height):"";
var G=K.align?this.trimString(K.align):"";
if(C!=""||N!=""||G!=""||F!=""){J+="|"+(N?N:" ")+"|"+(C?C:" ");
if(F&&F!=""){J+="|"+(G?G:" ")+"|"+(F?F:"")
}else{if(G!=""){J+="|"+(G?G:"")
}}}J+="}"
}}return H.replace(I,J)
};
WikiEditor.prototype.IMAGE_CLASS_NAME="wikiimage";
WikiEditor.prototype.convertImageExternal=function(F,B,D){var C,A,H;
var E;
this.trimString(B[9]);
var G="";
if(B[9]){E=this.trimString(B[9])
}else{E=""
}if(E!=""){G+='<div class="img'+E+'">'
}G+='<img id="'+B[1]+'" class="'+this.IMAGE_CLASS_NAME+'" src="'+escape(this.getImagePath()+B[1])+'" ';
if(B[5]&&(C=this.trimString(B[5]))!=""){G+='width="'+C+'" '
}if(B[3]&&(A=this.trimString(B[3]))!=""){G+='height="'+A+'" '
}if(B[7]&&(H=this.trimString(B[7]))!=""){G+='align="'+H+'" '
}if(E!=""){G+='halign="'+E+'" '
}G+="/>";
if(E!=""){G+="</div>"
}return D.replace(F,G)
};
WikiEditor.prototype.handleAttachmentsButtons=function(F,D,E,C,A,B){tinyMCE.switchClass(F+"_image","mceButtonNormal");
do{switch(D.nodeName.toLowerCase()){case"img":tinyMCE.switchClass(F+"_image","mceButtonSelected");
break
}}while((D=D.parentNode))
};
WikiEditor.prototype.getAttachmentsToolbar=function(){return this.getAttachmentsControls("image")+this.getAttachmentsControls("attachment")
};
WikiEditor.prototype.getAttachmentsControls=function(A){var B="";
switch(A){case"image":B=this.createButtonHTML("image","image.gif","lang_image_desc","mceImage",true);
break;
case"attachment":B=this.createButtonHTML("attachment","attachment.gif","lang_attachment_desc","wikiAttachment",true);
break
}return B
};
WikiEditor.prototype.convertAttachmentExternal=function(D,A,C){var B=((typeof (A[3])=="undefined")||(this.trimString(A[3])==""))?A[1]:A[3];
var E='<a href="wikiattachment:-:'+B+'" class="'+this.ATTACHMENT_CLASS_NAME+'">'+A[1]+"</a>";
return C.replace(D,E)
};
WikiEditor.prototype.convertAttachmentInternal=function(C,A,B){A[1]=A[1].replace(/%20/gi," ");
A[3]=A[3].replace(/%20/gi," ");
var D;
if(A[1]==A[3]){D="{attach:"+A[1]+"}"
}else{if((A[1]=="undefined")||(this.trimString(A[1])=="")){D="{attach:"+A[3]+"}"
}else{D="{attach:"+A[3]+"|"+A[1]+"}"
}}return B.replace(C,D)
};