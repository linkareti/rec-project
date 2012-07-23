var tinyMCE=null,tinyMCELang=null;
function TinyMCE_Popup(){}TinyMCE_Popup.prototype.init=function(){var D=window.opener?window.opener:window.dialogArguments;
var B;
if(!D){D=parent.parent;
if(typeof (D.tinyMCE)=="undefined"){D=top
}}window.opener=D;
this.windowOpener=D;
this.onLoadEval="";
tinyMCE=D.tinyMCE;
tinyMCELang=D.tinyMCELang;
if(!tinyMCE){alert("tinyMCE object reference not found from popup.");
return 
}B=tinyMCE.selectedInstance;
this.isWindow=tinyMCE.getWindowArg("mce_inside_iframe",false)==false;
this.storeSelection=(tinyMCE.isMSIE&&!tinyMCE.isOpera)&&!this.isWindow&&tinyMCE.getWindowArg("mce_store_selection",true);
if(this.isWindow){window.focus()
}if(this.storeSelection){B.selectionBookmark=B.selection.getBookmark(true)
}if(tinyMCELang.lang_dir){document.dir=tinyMCELang.lang_dir
}var A=new RegExp("{|\\$|}","g");
var E=document.title.replace(A,"");
if(typeof tinyMCELang[E]!="undefined"){var C=document.createElement("div");
C.innerHTML=tinyMCELang[E];
document.title=C.innerHTML;
if(tinyMCE.setWindowTitle!=null){tinyMCE.setWindowTitle(window,C.innerHTML)
}}document.write('<link href="'+tinyMCE.getParam("popups_css")+'" rel="stylesheet" type="text/css">');
tinyMCE.addEvent(window,"load",this.onLoad)
};
TinyMCE_Popup.prototype.onLoad=function(){var dir,i,elms,body=document.body;
if(tinyMCE.getWindowArg("mce_replacevariables",true)){body.innerHTML=tinyMCE.applyTemplate(body.innerHTML,tinyMCE.windowArgs)
}dir=tinyMCE.selectedInstance.settings.directionality;
if(dir=="rtl"&&document.forms&&document.forms.length>0){elms=document.forms[0].elements;
for(i=0;
i<elms.length;
i++){if((elms[i].type=="text"||elms[i].type=="textarea")&&elms[i].getAttribute("dir")!="ltr"){elms[i].dir=dir
}}}if(body.style.display=="none"){body.style.display="block"
}if(tinyMCEPopup.onLoadEval!=""){eval(tinyMCEPopup.onLoadEval)
}};
TinyMCE_Popup.prototype.executeOnLoad=function(str){if(tinyMCE.isOpera){this.onLoadEval=str
}else{eval(str)
}};
TinyMCE_Popup.prototype.resizeToInnerSize=function(){if(this.isWindow&&tinyMCE.isNS71){window.resizeBy(0,10);
return 
}if(this.isWindow){var G=document;
var F=G.body;
var C,A,D,B,I,H;
if(F.style.display=="none"){F.style.display="block"
}C=F.style.margin;
F.style.margin="0";
A=G.createElement("div");
A.id="mcBodyWrapper";
A.style.display="none";
A.style.margin="0";
B=G.body.childNodes;
for(var E=B.length-1;
E>=0;
E--){if(A.hasChildNodes()){A.insertBefore(B[E].cloneNode(true),A.firstChild)
}else{A.appendChild(B[E].cloneNode(true))
}B[E].parentNode.removeChild(B[E])
}G.body.appendChild(A);
D=document.createElement("iframe");
D.id="mcWinIframe";
D.src=document.location.href.toLowerCase().indexOf("https")==-1?"about:blank":tinyMCE.settings.default_document;
D.width="100%";
D.height="100%";
D.style.margin="0";
G.body.appendChild(D);
D=document.getElementById("mcWinIframe");
I=tinyMCE.getWindowArg("mce_width")-D.clientWidth;
H=tinyMCE.getWindowArg("mce_height")-D.clientHeight;
window.resizeBy(I,H);
F.style.margin=C;
D.style.display="none";
A.style.display="block"
}};
TinyMCE_Popup.prototype.resizeToContent=function(){var G=(navigator.appName=="Microsoft Internet Explorer");
var B=(navigator.userAgent.indexOf("Opera")!=-1);
if(B){return 
}if(G){try{window.resizeTo(10,10)
}catch(C){}var D=document.body;
var A=D.offsetWidth;
var H=D.offsetHeight;
var J=(D.scrollWidth-A)+4;
var I=D.scrollHeight-H;
try{window.resizeBy(J,I)
}catch(C){}}else{window.scrollBy(1000,1000);
if(window.scrollX>0||window.scrollY>0){window.resizeBy(window.innerWidth*2,window.innerHeight*2);
window.sizeToContent();
window.scrollTo(0,0);
var F=parseInt(screen.width/2)-(window.outerWidth/2);
var E=parseInt(screen.height/2)-(window.outerHeight/2);
window.moveTo(F,E)
}}};
TinyMCE_Popup.prototype.getWindowArg=function(B,A){return tinyMCE.getWindowArg(B,A)
};
TinyMCE_Popup.prototype.restoreSelection=function(){if(this.storeSelection){var A=tinyMCE.selectedInstance;
A.getWin().focus();
if(A.selectionBookmark){A.selection.moveToBookmark(A.selectionBookmark)
}}};
TinyMCE_Popup.prototype.execCommand=function(C,D,B){var A=tinyMCE.selectedInstance;
this.restoreSelection();
A.execCommand(C,D,B);
if(this.storeSelection){A.selectionBookmark=A.selection.getBookmark(true)
}};
TinyMCE_Popup.prototype.close=function(){tinyMCE.closeWindow(window)
};
TinyMCE_Popup.prototype.pickColor=function(B,A){tinyMCE.selectedInstance.execCommand("mceColorPicker",true,{element_id:A,document:document,window:window,store_selection:false})
};
TinyMCE_Popup.prototype.openBrowser=function(element_id,type,option){var cb=tinyMCE.getParam(option,tinyMCE.getParam("file_browser_callback"));
var url=document.getElementById(element_id).value;
tinyMCE.setWindowArg("window",window);
tinyMCE.setWindowArg("document",document);
if(eval("typeof(tinyMCEPopup.windowOpener."+cb+")")=="undefined"){alert("Callback function: "+cb+" could not be found.")
}else{eval("tinyMCEPopup.windowOpener."+cb+"(element_id, url, type, window);")
}};
TinyMCE_Popup.prototype.importClass=function(B){window[B]=function(){};
for(var A in window.opener[B].prototype){window[B].prototype[A]=window.opener[B].prototype[A]
}window[B].constructor=window.opener[B].constructor
};
var tinyMCEPopup=new TinyMCE_Popup();
tinyMCEPopup.init();