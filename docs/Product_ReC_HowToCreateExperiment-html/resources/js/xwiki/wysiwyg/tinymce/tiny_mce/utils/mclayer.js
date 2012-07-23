function MCLayer(A){this.id=A;
this.settings=new Array();
this.blockerElement=null;
this.isMSIE=navigator.appName=="Microsoft Internet Explorer";
this.events=false;
this.autoHideCallback=null
}MCLayer.prototype={moveRelativeTo:function(E,G,C){var F=this.getAbsPosition(E);
var B=parseInt(E.offsetWidth);
var D=parseInt(E.offsetHeight);
var A,H;
switch(G){case"tl":break;
case"tr":A=F.absLeft+B;
H=F.absTop;
break;
case"bl":break;
case"br":break
}this.moveTo(A,H)
},moveBy:function(C,B){var D=this.getElement();
var A=parseInt(D.style.left);
var E=parseInt(D.style.top);
D.style.left=(A+C)+"px";
D.style.top=(E+B)+"px";
this.updateBlocker()
},moveTo:function(A,C){var B=this.getElement();
B.style.left=A+"px";
B.style.top=C+"px";
this.updateBlocker()
},show:function(){MCLayer.visibleLayer=this;
this.getElement().style.display="block";
this.updateBlocker()
},hide:function(){this.getElement().style.display="none";
this.updateBlocker()
},setAutoHide:function(B,A){this.autoHideCallback=A;
this.registerEventHandlers()
},getElement:function(){return document.getElementById(this.id)
},updateBlocker:function(){if(!this.isMSIE){return 
}var E=this.getElement();
var B=this.getBlocker();
var A=this.parseInt(E.style.left);
var F=this.parseInt(E.style.top);
var C=this.parseInt(E.offsetWidth);
var D=this.parseInt(E.offsetHeight);
B.style.left=A+"px";
B.style.top=F+"px";
B.style.width=C+"px";
B.style.height=D+"px";
B.style.display=E.style.display
},getBlocker:function(){if(!this.blockerElement){var B=document,A=B.createElement("iframe");
A.style.cssText="display: none; left: 0px; position: absolute; top: 0";
A.src="javascript:false;";
A.frameBorder="0";
A.scrolling="no";
B.body.appendChild(A);
this.blockerElement=A
}return this.blockerElement
},getAbsPosition:function(B){var A={absLeft:0,absTop:0};
while(B){A.absLeft+=B.offsetLeft;
A.absTop+=B.offsetTop;
B=B.offsetParent
}return A
},registerEventHandlers:function(){if(!this.events){var A=document;
this.addEvent(A,"mousedown",MCLayer.prototype.onMouseDown);
this.events=true
}},addEvent:function(B,C,A){if(B.attachEvent){B.attachEvent("on"+C,A)
}else{B.addEventListener(C,A,false)
}},onMouseDown:function(D){D=typeof (D)=="undefined"?window.event:D;
var F=document.body;
var B=MCLayer.visibleLayer;
if(B){var J=B.isMSIE?D.clientX+F.scrollLeft:D.pageX;
var I=B.isMSIE?D.clientY+F.scrollTop:D.pageY;
var A=B.getElement();
var G=parseInt(A.style.left);
var E=parseInt(A.style.top);
var H=parseInt(A.offsetWidth);
var C=parseInt(A.offsetHeight);
if(!(J>G&&J<G+H&&I>E&&I<E+C)){MCLayer.visibleLayer=null;
if(B.autoHideCallback&&B.autoHideCallback(B,D,J,I)){return true
}B.hide()
}}},addCSSClass:function(B,C){this.removeCSSClass(B,C);
var A=this.explode(" ",B.className);
A[A.length]=C;
B.className=A.join(" ")
},removeCSSClass:function(C,D){var A=this.explode(" ",C.className),B;
for(B=0;
B<A.length;
B++){if(A[B]==D){A[B]=""
}}C.className=A.join(" ")
},explode:function(E,C){var A=C.split(E);
var D=new Array();
for(var B=0;
B<A.length;
B++){if(A[B]!=""){D[D.length]=A[B]
}}return D
},parseInt:function(A){if(A==null||A==""){return 0
}return parseInt(A)
}};