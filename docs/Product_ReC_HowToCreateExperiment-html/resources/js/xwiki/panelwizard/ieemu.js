if(browser.isMozilla){extendEventObject();
emulateAttachEvent();
emulateEventHandlers(["click","dblclick","mouseover","mouseout","mousedown","mouseup","mousemove","keydown","keypress","keyup"]);
emulateCurrentStyle();
emulateHTMLModel();
Event.LEFT=0;
Event.MIDDLE=1;
Event.RIGHT=2
}else{Event.LEFT=1;
Event.MIDDLE=4;
Event.RIGHT=2
}function extendEventObject(){Event.prototype.__defineSetter__("returnValue",function(A){if(!A){this.preventDefault()
}return A
});
Event.prototype.__defineSetter__("cancelBubble",function(A){if(A){this.stopPropagation()
}return A
});
Event.prototype.__defineGetter__("srcElement",function(){var A=this.target;
while(A.nodeType!=1){A=A.parentNode
}return A
});
Event.prototype.__defineGetter__("fromElement",function(){var A;
if(this.type=="mouseover"){A=this.relatedTarget
}else{if(this.type=="mouseout"){A=this.target
}}if(!A){return 
}while(A.nodeType!=1){A=A.parentNode
}return A
});
Event.prototype.__defineGetter__("toElement",function(){var A;
if(this.type=="mouseout"){A=this.relatedTarget
}else{if(this.type=="mouseover"){A=this.target
}}if(!A){return 
}while(A.nodeType!=1){A=A.parentNode
}return A
});
Event.prototype.__defineGetter__("offsetX",function(){return this.layerX
});
Event.prototype.__defineGetter__("offsetY",function(){return this.layerY
})
}function emulateAttachEvent(){HTMLDocument.prototype.attachEvent=HTMLElement.prototype.attachEvent=function(C,B){var A=C.replace(/on/,"");
B._ieEmuEventHandler=function(D){window.event=D;
return B()
};
this.addEventListener(A,B._ieEmuEventHandler,false)
};
HTMLDocument.prototype.detachEvent=HTMLElement.prototype.detachEvent=function(C,B){var A=C.replace(/on/,"");
if(typeof B._ieEmuEventHandler=="function"){this.removeEventListener(A,B._ieEmuEventHandler,false)
}else{this.removeEventListener(A,B,true)
}}
}function emulateEventHandlers(B){for(var A=0;
A<B.length;
A++){document.addEventListener(B[A],function(C){window.event=C
},true)
}}function emulateAllModel(){var A=function(){var B=this.getElementsByTagName("*");
var C=this;
B.tags=function(D){return C.getElementsByTagName(D)
};
return B
};
HTMLDocument.prototype.__defineGetter__("all",A);
HTMLElement.prototype.__defineGetter__("all",A)
}function extendElementModel(){HTMLElement.prototype.__defineGetter__("parentElement",function(){if(this.parentNode==this.ownerDocument){return null
}return this.parentNode
});
HTMLElement.prototype.__defineGetter__("children",function(){var C=[];
var A=0;
var D;
for(var B=0;
B<this.childNodes.length;
B++){D=this.childNodes[B];
if(D.nodeType==1){C[A++]=D;
if(D.name){if(!C[D.name]){C[D.name]=[]
}C[D.name][C[D.name].length]=D
}if(D.id){C[D.id]=D
}}}return C
});
HTMLElement.prototype.contains=function(A){if(A==this){return true
}if(A==null){return false
}return this.contains(A.parentNode)
}
}function emulateCurrentStyle(){HTMLElement.prototype.__defineGetter__("currentStyle",function(){return this.ownerDocument.defaultView.getComputedStyle(this,null)
})
}function emulateHTMLModel(){function B(C){C=C.replace(/\&/g,"&amp;").replace(/</g,"&lt;").replace(/>/g,"&gt;").replace(/\n/g,"<BR>");
while(/\s\s/.test(C)){C=C.replace(/\s\s/,"&nbsp; ")
}return C.replace(/\s/g," ")
}HTMLElement.prototype.insertAdjacentHTML=function(E,D){var F;
var C=this.ownerDocument.createRange();
switch(String(E).toLowerCase()){case"beforebegin":C.setStartBefore(this);
F=C.createContextualFragment(D);
this.parentNode.insertBefore(F,this);
break;
case"afterbegin":C.selectNodeContents(this);
C.collapse(true);
F=C.createContextualFragment(D);
this.insertBefore(F,this.firstChild);
break;
case"beforeend":C.selectNodeContents(this);
C.collapse(false);
F=C.createContextualFragment(D);
this.appendChild(F);
break;
case"afterend":C.setStartAfter(this);
F=C.createContextualFragment(D);
this.parentNode.insertBefore(F,this.nextSibling);
break
}};
HTMLElement.prototype.__defineSetter__("outerHTML",function(D){var C=this.ownerDocument.createRange();
C.setStartBefore(this);
var E=C.createContextualFragment(D);
this.parentNode.replaceChild(E,this);
return D
});
HTMLElement.prototype.__defineGetter__("canHaveChildren",function(){switch(this.tagName){case"AREA":case"BASE":case"BASEFONT":case"COL":case"FRAME":case"HR":case"IMG":case"BR":case"INPUT":case"ISINDEX":case"LINK":case"META":case"PARAM":return false
}return true
});
HTMLElement.prototype.__defineGetter__("outerHTML",function(){var C,D=this.attributes;
var F="<"+this.tagName;
for(var E=0;
E<D.length;
E++){C=D[E];
if(C.specified){F+=" "+C.name+'="'+C.value+'"'
}}if(!this.canHaveChildren){return F+">"
}return F+">"+this.innerHTML+"</"+this.tagName+">"
});
HTMLElement.prototype.__defineSetter__("innerText",function(C){this.innerHTML=B(C);
return C
});
var A;
HTMLElement.prototype.__defineGetter__("innerText",A=function(){var C=this.ownerDocument.createRange();
C.selectNodeContents(this);
return C.toString()
});
HTMLElement.prototype.__defineSetter__("outerText",function(C){this.outerHTML=B(C);
return C
});
HTMLElement.prototype.__defineGetter__("outerText",A);
HTMLElement.prototype.insertAdjacentText=function(D,C){this.insertAdjacentHTML(D,B(C))
}
};