function MCWindows(){this.settings=new Array();
this.windows=new Array();
this.isMSIE=(navigator.appName=="Microsoft Internet Explorer");
this.isGecko=navigator.userAgent.indexOf("Gecko")!=-1;
this.isSafari=navigator.userAgent.indexOf("Safari")!=-1;
this.isMac=navigator.userAgent.indexOf("Mac")!=-1;
this.isMSIE5_0=this.isMSIE&&(navigator.userAgent.indexOf("MSIE 5.0")!=-1);
this.action="none";
this.selectedWindow=null;
this.zindex=100;
this.mouseDownScreenX=0;
this.mouseDownScreenY=0;
this.mouseDownLayerX=0;
this.mouseDownLayerY=0;
this.mouseDownWidth=0;
this.mouseDownHeight=0
}MCWindows.prototype.init=function(A){this.settings=A;
if(this.isMSIE){this.addEvent(document,"mousemove",mcWindows.eventDispatcher)
}else{this.addEvent(window,"mousemove",mcWindows.eventDispatcher)
}this.addEvent(document,"mouseup",mcWindows.eventDispatcher)
};
MCWindows.prototype.getParam=function(B,A){var C=null;
C=(typeof (this.settings[B])=="undefined")?A:this.settings[B];
if(C=="true"||C=="false"){return(C=="true")
}return C
};
MCWindows.prototype.eventDispatcher=function(B){B=typeof (B)=="undefined"?window.event:B;
if(mcWindows.selectedWindow==null){return 
}if(mcWindows.isGecko&&B.type=="mousedown"){var D=B.currentTarget;
for(var C in mcWindows.windows){var A=mcWindows.windows[C];
if(typeof (A)=="function"){continue
}if(A.headElement==D||A.resizeElement==D){A.focus();
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
MCWindows.prototype.addEvent=function(C,A,B){if(this.isMSIE){C.attachEvent("on"+A,B)
}else{C.addEventListener(A,B,true)
}};
MCWindows.prototype.cancelEvent=function(A){if(this.isMSIE){A.returnValue=false;
A.cancelBubble=true
}else{A.preventDefault()
}};
MCWindows.prototype.parseFeatures=function(C){C=C.toLowerCase();
C=C.replace(/;/g,",");
C=C.replace(/[^0-9a-z=,]/g,"");
var E=C.split(",");
var A=new Array();
A.left=10;
A.top=10;
A.width=300;
A.height=300;
A.resizable=true;
A.minimizable=true;
A.maximizable=true;
A.close=true;
A.movable=true;
if(C==""){return A
}for(var B=0;
B<E.length;
B++){var D=E[B].split("=");
if(D.length==2){A[D[0]]=D[1]
}}return A
};
MCWindows.prototype.open=function(C,B,E){var F=new MCWindow();
var A,D="",G;
E=this.parseFeatures(E);
G="mcWindow_"+B;
width=parseInt(E.width);
height=parseInt(E.height)-12-19;
if(this.isMSIE){width-=2
}F.id=G;
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
D+='<link href="../jscripts/tiny_mce/themes/advanced/css/editor_ui.css" rel="stylesheet" type="text/css" />';
D+="</head>";
D+="<body onload=\"parent.mcWindows.onLoad('"+B+"');\">";
D+='<div id="'+G+'_container" class="mceWindow">';
D+='<div id="'+G+'_head" class="mceWindowHead" onmousedown="parent.mcWindows.windows[\''+B+"'].focus();\">";
D+='  <div id="'+G+'_title" class="mceWindowTitle"';
D+='  onselectstart="return false;" unselectable="on" style="-moz-user-select: none !important;">No name window</div>';
D+='    <div class="mceWindowHeadTools">';
D+="      <a href=\"javascript:parent.mcWindows.windows['"+B+'\'].close();" onmousedown="return false;" class="mceWindowClose"><img border="0" src="../jscripts/tiny_mce/themes/advanced/images/window_close.gif" /></a>';
D+="    </div>";
D+='</div><div id="'+G+'_body" class="mceWindowBody" style="width: '+width+"px; height: "+height+'px;">';
D+='<iframe id="'+G+'_iframe" name="'+G+'_iframe" onfocus="parent.mcWindows.windows[\''+B+'\'].focus();" frameborder="0" width="'+iframeWidth+'" height="'+iframeHeight+'" src="'+C+'" class="mceWindowBodyIframe"></iframe></div>';
D+='<div id="'+G+'_statusbar" class="mceWindowStatusbar" onmousedown="parent.mcWindows.windows[\''+B+"'].focus();\">";
D+='<div id="'+G+'_resize" class="mceWindowResize"><img onmousedown="parent.mcWindows.windows[\''+B+'\'].focus();" border="0" src="../jscripts/tiny_mce/themes/advanced/images/window_resize.gif" /></div>';
D+="</div>";
D+="</div>";
D+="</body>";
D+="</html>";
this.createFloatingIFrame(G,E.left,E.top,E.width,E.height,D)
};
MCWindows.prototype.onLoad=function(B){var G=mcWindows.windows[B];
var C="mcWindow_"+B;
var A=window.frames[C+"_iframe"].frames[0];
var E=window.frames[C+"_iframe"].document;
var I=window.frames[C+"_iframe"].document;
var H=document.getElementById("mcWindow_"+B+"_div");
var D=window.frames[C+"_iframe"].frames[0];
G.id="mcWindow_"+B+"_iframe";
G.winElement=H;
G.bodyElement=I.getElementById(C+"_body");
G.iframeElement=I.getElementById(C+"_iframe");
G.headElement=I.getElementById(C+"_head");
G.titleElement=I.getElementById(C+"_title");
G.resizeElement=I.getElementById(C+"_resize");
G.containerElement=I.getElementById(C+"_container");
G.left=G.features.left;
G.top=G.features.top;
G.frame=window.frames[C+"_iframe"].frames[0];
G.wrapperFrame=window.frames[C+"_iframe"];
G.wrapperIFrameElement=document.getElementById(C+"_iframe");
mcWindows.addEvent(G.headElement,"mousedown",mcWindows.eventDispatcher);
mcWindows.addEvent(G.resizeElement,"mousedown",mcWindows.eventDispatcher);
if(mcWindows.isMSIE){mcWindows.addEvent(D.document,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(D.document,"mouseup",mcWindows.eventDispatcher)
}else{mcWindows.addEvent(D,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(D,"mouseup",mcWindows.eventDispatcher);
mcWindows.addEvent(D,"focus",mcWindows.eventDispatcher)
}for(var F=0;
F<window.frames.length;
F++){if(!window.frames[F]._hasMouseHandlers){if(mcWindows.isMSIE){mcWindows.addEvent(window.frames[F].document,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(window.frames[F].document,"mouseup",mcWindows.eventDispatcher)
}else{mcWindows.addEvent(window.frames[F],"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(window.frames[F],"mouseup",mcWindows.eventDispatcher)
}window.frames[F]._hasMouseHandlers=true
}}if(mcWindows.isMSIE){mcWindows.addEvent(G.frame.document,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(G.frame.document,"mouseup",mcWindows.eventDispatcher)
}else{mcWindows.addEvent(G.frame,"mousemove",mcWindows.eventDispatcher);
mcWindows.addEvent(G.frame,"mouseup",mcWindows.eventDispatcher);
mcWindows.addEvent(G.frame,"focus",mcWindows.eventDispatcher)
}this.selectedWindow=G
};
MCWindows.prototype.createFloatingIFrame=function(B,G,F,E,A,C){var D=document.createElement("iframe");
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
function MCWindow(){}MCWindow.prototype.focus=function(){this.winElement.style.zIndex=mcWindows.zindex++;
mcWindows.selectedWindow=this
};
MCWindow.prototype.minimize=function(){};
MCWindow.prototype.maximize=function(){};
MCWindow.prototype.startResize=function(){mcWindows.action="resize"
};
MCWindow.prototype.startMove=function(A){mcWindows.action="move"
};
MCWindow.prototype.close=function(){document.body.removeChild(this.winElement);
mcWindows.windows[this.name]=null
};
MCWindow.prototype.onMouseMove=function(D){var E=0;
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
height=height-12-19;
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
MCWindow.prototype.onMouseUp=function(A){mcWindows.action="none"
};
MCWindow.prototype.onFocus=function(B){var C=B.currentTarget;
for(var D in mcWindows.windows){var A=mcWindows.windows[D];
if(typeof (A)=="function"){continue
}if(C.name==A.id){A.focus();
return 
}}};
MCWindow.prototype.onMouseDown=function(B){var D=mcWindows.isMSIE?this.wrapperFrame.event.srcElement:B.target;
var C=0;
var A=0;
mcWindows.mouseDownScreenX=B.screenX;
mcWindows.mouseDownScreenY=B.screenY;
mcWindows.mouseDownLayerX=this.left;
mcWindows.mouseDownLayerY=this.top;
mcWindows.mouseDownWidth=parseInt(this.winElement.style.width);
mcWindows.mouseDownHeight=parseInt(this.winElement.style.height);
if(D==this.resizeElement.firstChild){this.startResize(B)
}else{this.startMove(B)
}mcWindows.cancelEvent(B)
};
var mcWindows=new MCWindows();