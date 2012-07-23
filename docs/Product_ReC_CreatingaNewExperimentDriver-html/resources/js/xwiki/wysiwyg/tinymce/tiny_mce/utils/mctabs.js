function MCTabs(){this.settings=new Array()
}MCTabs.prototype.init=function(A){this.settings=A
};
MCTabs.prototype.getParam=function(B,A){var C=null;
C=(typeof (this.settings[B])=="undefined")?A:this.settings[B];
if(C=="true"||C=="false"){return(C=="true")
}return C
};
MCTabs.prototype.displayTab=function(E,A){var I=document.getElementById(A);
var D=I?I.parentNode:null;
var C=document.getElementById(E);
var H=C?C.parentNode:null;
var G=this.getParam("selection_class","current");
if(C&&H){var B=H.childNodes;
for(var F=0;
F<B.length;
F++){if(B[F].nodeName=="LI"){B[F].className=""
}}C.className="current"
}if(I&&D){var B=D.childNodes;
for(var F=0;
F<B.length;
F++){if(B[F].nodeName=="DIV"){B[F].className="panel"
}}I.className="current"
}};
MCTabs.prototype.getAnchor=function(){var B,A=document.location.href;
if((B=A.lastIndexOf("#"))!=-1){return A.substring(B+1)
}return""
};
var mcTabs=new MCTabs();