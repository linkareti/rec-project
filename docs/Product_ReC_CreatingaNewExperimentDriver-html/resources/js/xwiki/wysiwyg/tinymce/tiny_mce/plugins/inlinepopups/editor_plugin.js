var TinyMCE_InlinePopupsPlugin={getInfo:function(){return{longname:"Inline Popups",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_inlinepopups.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
}};
tinyMCE.addPlugin("inlinepopups",TinyMCE_InlinePopupsPlugin);
TinyMCE_Engine.prototype.orgOpenWindow=TinyMCE_Engine.prototype.openWindow;
TinyMCE_Engine.prototype.openWindow=function(E,C){if(C.inline!="yes"||tinyMCE.isOpera||tinyMCE.getParam("plugins").indexOf("inlinepopups")==-1){mcWindows.selectedWindow=null;
C.mce_inside_iframe=false;
this.orgOpenWindow(E,C);
return 
}var B,D,F;
C.mce_inside_iframe=true;
tinyMCE.windowArgs=C;
if(E.file.charAt(0)!="/"&&E.file.indexOf("://")==-1){B=tinyMCE.baseURL+"/themes/"+tinyMCE.getParam("theme")+"/"+E.file
}else{B=E.file
}if(!(width=parseInt(E.width))){width=320
}if(!(height=parseInt(E.height))){height=200
}D=(C&&C.resizable)?C.resizable:"no";
F=(C&&C.scrollbars)?C.scrollbars:"no";
height+=18;
for(var A in C){if(typeof (C[A])=="function"){continue
}B=tinyMCE.replaceVar(B,A,escape(C[A]))
}var H=document.getElementById(this.selectedInstance.editorId+"_parent");
var G=tinyMCE.getAbsPosition(H);
G.absLeft+=Math.round((H.firstChild.clientWidth/2)-(width/2));
G.absTop+=Math.round((H.firstChild.clientHeight/2)-(height/2));
mcWindows.open(B,mcWindows.idCounter++,"modal=yes,width="+width+",height="+height+",resizable="+D+",scrollbars="+F+",statusbar="+D+",left="+G.absLeft+",top="+G.absTop)
};
TinyMCE_Engine.prototype.orgCloseWindow=TinyMCE_Engine.prototype.closeWindow;
TinyMCE_Engine.prototype.closeWindow=function(A){if(mcWindows.selectedWindow!=null){mcWindows.selectedWindow.close()
}else{this.orgCloseWindow(A)
}};
TinyMCE_Engine.prototype.setWindowTitle=function(C,B){for(var D in mcWindows.windows){var A=mcWindows.windows[D];
if(typeof (A)=="function"){continue
}if(C.name==A.id+"_iframe"){window.frames[A.id+"_iframe"].document.getElementById(A.id+"_title").innerHTML=B
}}};
function TinyMCE_Windows(){this.settings=new Array();
this.windows=new Array();
this.isMSIE=(navigator.appName=="Microsoft Internet Explorer");
this.isGecko=navigator.userAgent.indexOf("Gecko")!=-1;
this.isSafari=navigator.userAgent.indexOf("Safari")!=-1;
this.isMac=navigator.userAgent.indexOf("Mac")!=-1;
this.isMSIE5_0=this.isMSIE&&(navigator.userAgent.indexOf("MSIE 5.0")!=-1);
this.action="none";
this.selectedWindow=null;
this.lastSelectedWindow=null;
this.zindex=100;
this.mouseDownScreenX=0;
this.mouseDownScreenY=0;
this.mouseDownLayerX=0;
this.mouseDownLayerY=0;
this.mouseDownWidth=0;
this.mouseDownHeight=0;
this.idCounter=0
}TinyMCE_Windows.prototype.init=function(A){this.settings=A;
if(this.isMSIE){this.addEvent(document,"mousemove",mcWindows.eventDispatcher)
}else{this.addEvent(window,"mousemove",mcWindows.eventDispatcher)
}this.addEvent(document,"mouseup",mcWindows.eventDispatcher);
this.doc=document
};
TinyMCE_Windows.prototype.getParam=function(B,A){var C=null;
C=(typeof (this.settings[B])=="undefined")?A:this.settings[B];
if(C=="true"||C=="false"){return(C=="true")
}return C
};
TinyMCE_Windows.prototype.eventDispatcher=function(B){B=typeof (B)=="undefined"?window.event:B;
if(mcWindows.selectedWindow==null){return 
}if(mcWindows.isGecko&&B.type=="mousedown"){var D=B.currentTarget;
for(var C in mcWindows.windows){var A=mcWindows.windows[C];
if(A.headElement==D||A.resizeElement==D){A.focus();
break
}}}switch(B.type){case"mousemove":mcWindows.selectedWindow.onMouseMove(B);
break;
case"mouseup":mcWindows.selectedWindow.onMouseUp(B);
break;
case"mousedown":mcWindows.selectedWindow.onMouseDown(B);
break;
case"focus":mcWindows.selectedWindow.onFocus(B);
break
}};
TinyMCE_Windows.prototype.addEvent=function(C,A,B){if(this.isMSIE){C.attachEvent("on"+A,B)
}else{C.addEventListener(A,B,true)
}};
TinyMCE_Windows.prototype.cancelEvent=function(A){if(this.isMSIE){A.returnValue=false;
A.cancelBubble=true
}else{A.preventDefault()
}};
TinyMCE_Windows.prototype.parseFeatures=function(C){C=C.toLowerCase();
C=C.replace(/;/g,",");
C=C.replace(/[^0-9a-z=,]/g,"");
var E=C.split(",");
var A=new Array();
A.left="10";
A.top="10";
A.width="300";
A.height="300";
A.resizable="yes";
A.minimizable="yes";
A.maximizable="yes";
A.close="yes";
A.movable="yes";
A.statusbar="yes";
A.scrollbars="auto";
A.modal="no";
if(C==""){return A
}for(var B=0;
B<E.length;
B++){var D=E[B].split("=");
if(D.length==2){A[D[0]]=D[1]
}}A.left=parseInt(A.left);
A.top=parseInt(A.top);
A.width=parseInt(A.width);
A.height=parseInt(A.height);
return A
};
TinyMCE_Windows.prototype.open=function(C,B,E){this.lastSelectedWindow=this.selectedWindow;
var F=new TinyMCE_Window();
var A,D="",H;
var G=this.getParam("images_path");
E=this.parseFeatures(E);
H="mcWindow_"+B;
F.deltaHeight=18;
if(E.statusbar=="yes"){F.deltaHeight+=13;
if(this.isMSIE){F.deltaHeight+=1
}}width=parseInt(E.width);
height=parseInt(E.height)-F.deltaHeight;
if(this.isMSIE){width-=2
}F.id=H;
F.url=C;
F.name=B;
F.features=E;
this.windows[B]=F;
iframeWidth=width;
iframeHeight=height;
D+='<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">';
D+="<html>";
D+="<head>";
D+="<title>Wrapper iframe</title>";
D+='<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">';
D+='<link href="'+this.getParam("css_file")+'" rel="stylesheet" type="text/css" />';
D+="</head>";
D+="<body onload=\"parent.mcWindows.onLoad('"+B+"');\">";
D+='<div id="'+H+'_container" class="mceWindow">';
D+='<div id="'+H+'_head" class="mceWindowHead" onmousedown="parent.mcWindows.windows[\''+B+"'].focus();\">";
D+='  <div id="'+H+'_title" class="mceWindowTitle"';
D+='  onselectstart="return false;" unselectable="on" style="-moz-user-select: none !important;"></div>';
D+='    <div class="mceWindowHeadTools">';
D+="      <a href=\"javascript:parent.mcWindows.windows['"+B+'\'].close();" target="_self" onmousedown="return false;" class="mceWindowClose"><img border="0" src="'+G+'/window_close.gif" /></a>';
D+="    </div>";
D+='</div><div id="'+H+'_body" class="mceWindowBody" style="width: '+width+"px; height: "+height+'px;">';
D+='<iframe id="'+H+'_iframe" name="'+H+'_iframe" frameborder="0" width="'+iframeWidth+'" height="'+iframeHeight+'" src="'+C+'" class="mceWindowBodyIframe" scrolling="'+E.scrollbars+'"></iframe></div>';
if(E.statusbar=="yes"){D+='<div id="'+H+'_statusbar" class="mceWindowStatusbar" onmousedown="parent.mcWindows.windows[\''+B+"'].focus();\">";
if(E.resizable=="yes"){if(this.isGecko){D+='<div id="'+H+'_resize" class="mceWindowResize"><div style="background-image: url(\''+G+"/window_resize.gif'); width: 12px; height: 12px;\"></div></div>"
}else{D+='<div id="'+H+'_resize" class="mceWindowResize"><img onmousedown="parent.mcWindows.windows[\''+B+'\'].focus();" border="0" src="'+G+'/window_resize.gif" /></div>'
}}D+="</div>"
}D+="</div>";
D+="</body>";
D+="</html>";
this.createFloatingIFrame(H,E.left,E.top,E.width,E.height,D)
};
TinyMCE_Windows.prototype.setDocumentLock=function(C){if(C){var E=document.getElementById("mcWindowEventBlocker");
if(E==null){E=document.createElement("div");
E.id="mcWindowEventBlocker";
E.style.position="absolute";
E.style.left="0";
E.style.top="0";
document.body.appendChild(E)
}E.style.display="none";
var D=this.getParam("images_path");
var B=document.body.clientWidth;
var A=document.body.clientHeight;
E.style.width=B;
E.style.height=A;
E.innerHTML='<img src="'+D+'/spacer.gif" width="'+B+'" height="'+A+'" />';
E.style.zIndex=mcWindows.zindex-1;
E.style.display="block"
}else{var E=document.getElementById("mcWindowEventBlocker");
if(mcWindows.windows.length==0){E.parentNode.removeChild(E)
}else{E.style.zIndex=mcWindows.zindex-1
}}};
TinyMCE_Windows.prototype.onLoad=function(name){var win=mcWindows.windows[name];
var id="mcWindow_"+name;
var wrapperIframe=window.frames[id+"_iframe"].frames[0];
var wrapperDoc=window.frames[id+"_iframe"].document;
var doc=window.frames[id+"_iframe"].document;
var winDiv=document.getElementById("mcWindow_"+name+"_div");
var realIframe=window.frames[id+"_iframe"].frames[0];
win.id="mcWindow_"+name;
win.winElement=winDiv;
win.bodyElement=doc.getElementById(id+"_body");
win.iframeElement=doc.getElementById(id+"_iframe");
win.headElement=doc.getElementById(id+"_head");
win.titleElement=doc.getElementById(id+"_title");
win.resizeElement=doc.getElementById(id+"_resize");
win.containerElement=doc.getElementById(id+"_container");
win.left=win.features.left;
win.top=win.features.top;
win.frame=window.frames[id+"_iframe"].frames[0];
win.wrapperFrame=window.frames[id+"_iframe"];
win.wrapperIFrameElement=document.getElementById(id+"_iframe");
mcWindows.addEvent(win.headElement,"mousedown",mcWindows.eventDispatcher);
if(win.resizeElement!=null){mcWindows.addEvent(win.resizeElement,"mousedown",mcWindows.eventDispatcher)
}if(mcWindows.isMSIE){mcWindows.addEvent(realIframe.document,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(realIframe.document,"mouseup",mcWindows.eventDispatcher)
}else{mcWindows.addEvent(realIframe,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(realIframe,"mouseup",mcWindows.eventDispatcher);
mcWindows.addEvent(realIframe,"focus",mcWindows.eventDispatcher)
}for(var i=0;
i<window.frames.length;
i++){if(!window.frames[i]._hasMouseHandlers){if(mcWindows.isMSIE){mcWindows.addEvent(window.frames[i].document,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(window.frames[i].document,"mouseup",mcWindows.eventDispatcher)
}else{mcWindows.addEvent(window.frames[i],"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(window.frames[i],"mouseup",mcWindows.eventDispatcher)
}window.frames[i]._hasMouseHandlers=true
}}if(mcWindows.isMSIE){mcWindows.addEvent(win.frame.document,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(win.frame.document,"mouseup",mcWindows.eventDispatcher)
}else{mcWindows.addEvent(win.frame,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(win.frame,"mouseup",mcWindows.eventDispatcher);
mcWindows.addEvent(win.frame,"focus",mcWindows.eventDispatcher)
}var func=this.getParam("on_open_window","");
if(func!=""){eval(func+"(win);")
}win.focus();
if(win.features.modal=="yes"){mcWindows.setDocumentLock(true)
}};
TinyMCE_Windows.prototype.createFloatingIFrame=function(B,G,F,E,A,C){var D=document.createElement("iframe");
var H=document.createElement("div");
E=parseInt(E);
A=parseInt(A)+1;
H.setAttribute("id",B+"_div");
H.setAttribute("width",E);
H.setAttribute("height",(A));
H.style.position="absolute";
H.style.left=G+"px";
H.style.top=F+"px";
H.style.width=E+"px";
H.style.height=(A)+"px";
H.style.backgroundColor="white";
H.style.display="none";
if(this.isGecko){iframeWidth=E+2;
iframeHeight=A+2
}else{iframeWidth=E;
iframeHeight=A+1
}D.setAttribute("id",B+"_iframe");
D.setAttribute("name",B+"_iframe");
D.setAttribute("border","0");
D.setAttribute("frameBorder","0");
D.setAttribute("marginWidth","0");
D.setAttribute("marginHeight","0");
D.setAttribute("leftMargin","0");
D.setAttribute("topMargin","0");
D.setAttribute("width",iframeWidth);
D.setAttribute("height",iframeHeight);
D.setAttribute("scrolling","no");
D.style.width=iframeWidth+"px";
D.style.height=iframeHeight+"px";
D.style.backgroundColor="white";
H.appendChild(D);
document.body.appendChild(H);
H.innerHTML=H.innerHTML;
if(this.isSafari){window.setTimeout(function(){doc=window.frames[B+"_iframe"].document;
doc.open();
doc.write(C);
doc.close()
},10)
}else{doc=window.frames[B+"_iframe"].window.document;
doc.open();
doc.write(C);
doc.close()
}H.style.display="block";
return H
};
function TinyMCE_Window(){}TinyMCE_Window.prototype.focus=function(){if(this!=mcWindows.selectedWindow){this.winElement.style.zIndex=++mcWindows.zindex;
mcWindows.lastSelectedWindow=mcWindows.selectedWindow;
mcWindows.selectedWindow=this
}};
TinyMCE_Window.prototype.minimize=function(){};
TinyMCE_Window.prototype.maximize=function(){};
TinyMCE_Window.prototype.startResize=function(){mcWindows.action="resize"
};
TinyMCE_Window.prototype.startMove=function(A){mcWindows.action="move"
};
TinyMCE_Window.prototype.close=function(){if(this.frame&&this.frame.tinyMCEPopup){this.frame.tinyMCEPopup.restoreSelection()
}if(mcWindows.lastSelectedWindow!=null){mcWindows.lastSelectedWindow.focus()
}var A=new Array();
for(var D in mcWindows.windows){var C=mcWindows.windows[D];
if(typeof (C)=="function"){continue
}if(C.name!=this.name){A[D]=C
}}mcWindows.windows=A;
var B=mcWindows.doc.getElementById(this.id+"_iframe");
B.parentNode.removeChild(B);
var B=mcWindows.doc.getElementById(this.id+"_div");
B.parentNode.removeChild(B);
mcWindows.setDocumentLock(false)
};
TinyMCE_Window.prototype.onMouseMove=function(D){var E=0;
var C=0;
var B=D.screenX-mcWindows.mouseDownScreenX;
var A=D.screenY-mcWindows.mouseDownScreenY;
switch(mcWindows.action){case"resize":width=mcWindows.mouseDownWidth+(D.screenX-mcWindows.mouseDownScreenX);
height=mcWindows.mouseDownHeight+(D.screenY-mcWindows.mouseDownScreenY);
width=width<100?100:width;
height=height<100?100:height;
this.wrapperIFrameElement.style.width=width+2;
this.wrapperIFrameElement.style.height=height+2;
this.wrapperIFrameElement.width=width+2;
this.wrapperIFrameElement.height=height+2;
this.winElement.style.width=width;
this.winElement.style.height=height;
height=height-this.deltaHeight;
this.containerElement.style.width=width;
this.iframeElement.style.width=width;
this.iframeElement.style.height=height;
this.bodyElement.style.width=width;
this.bodyElement.style.height=height;
this.headElement.style.width=width;
mcWindows.cancelEvent(D);
break;
case"move":this.left=mcWindows.mouseDownLayerX+(D.screenX-mcWindows.mouseDownScreenX);
this.top=mcWindows.mouseDownLayerY+(D.screenY-mcWindows.mouseDownScreenY);
this.winElement.style.left=this.left+"px";
this.winElement.style.top=this.top+"px";
mcWindows.cancelEvent(D);
break
}};
function debug(A){document.getElementById("debug").value+=A+"\n"
}TinyMCE_Window.prototype.onMouseUp=function(A){mcWindows.action="none"
};
TinyMCE_Window.prototype.onFocus=function(B){var C=B.currentTarget;
for(var D in mcWindows.windows){var A=mcWindows.windows[D];
if(typeof (A)=="function"){continue
}if(C.name==A.id+"_iframe"){A.focus();
return 
}}};
TinyMCE_Window.prototype.onMouseDown=function(B){var D=mcWindows.isMSIE?this.wrapperFrame.event.srcElement:B.target;
var C=0;
var A=0;
mcWindows.mouseDownScreenX=B.screenX;
mcWindows.mouseDownScreenY=B.screenY;
mcWindows.mouseDownLayerX=this.left;
mcWindows.mouseDownLayerY=this.top;
mcWindows.mouseDownWidth=parseInt(this.winElement.style.width);
mcWindows.mouseDownHeight=parseInt(this.winElement.style.height);
if(this.resizeElement!=null&&D==this.resizeElement.firstChild){this.startResize(B)
}else{this.startMove(B)
}mcWindows.cancelEvent(B)
};
var mcWindows=new TinyMCE_Windows();
mcWindows.init({images_path:tinyMCE.baseURL+"/plugins/inlinepopups/images",css_file:tinyMCE.baseURL+"/plugins/inlinepopups/css/inlinepopup.css"});