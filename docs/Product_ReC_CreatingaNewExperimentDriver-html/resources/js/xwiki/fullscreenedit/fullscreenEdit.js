(function(){function A(J,I){if(!document.getElementById){return 
}var K=J.style[H(I)];
if(!K){if(document.defaultView){K=document.defaultView.getComputedStyle(J,"").getPropertyValue(I)
}else{if(J.currentStyle){K=J.currentStyle[H(I)]
}}}return K
}function F(J,I,K){J.style[I]=K
}function H(N){var L=N.split("-");
if(L.length==1){return L[0]
}var J=N.indexOf("-")==0?L[0].charAt(0).toUpperCase()+L[0].substring(1):L[0];
for(var K=1,I=L.length;
K<I;
K++){var M=L[K];
J+=M.charAt(0).toUpperCase()+M.substring(1)
}return J
}function E(J,K){var I;
while(J.hasChildNodes()){I=J.firstChild;
K.appendChild(I)
}}Event.observe(window,"load",function(){if(!$("xwikieditcontent")){return 
}var L,T,S;
if($("mce_editor_0_parent")){L=D;
T=C;
S="wysiwyg"
}else{L=B;
T=G;
S="wiki"
}var I=document.getElementById("xwikitext");
if(I){var P=document.createElement("div");
P.setAttribute("id","show-dialog-btn");
Event.observe(P,"click",L,true);
P.setAttribute("title","$msg.get('fullScreenTooltip')");
var J=document.getElementById("xwikieditcontent");
J.insertBefore(P,J.firstChild);
var V=document.createElement("div");
V.setAttribute("id","fullscreen-dlg");
var N=document.createElement("div");
N.setAttribute("id","fullscreen-dlg-head");
var Q=document.createElement("div");
Q.setAttribute("id","closeBtn");
Event.observe(Q,"click",T,true);
N.appendChild(Q);
var R=document.createElement("div");
R.className="titleText";
R.appendChild(document.createTextNode("Fullscreen Editing"));
N.appendChild(R);
var O=document.createElement("div");
O.setAttribute("id","fullscreen-dlg-body");
O.appendChild(N);
var U=document.createElement("div");
U.setAttribute("id","fullscreen-dlg-body-tab-"+S);
O.appendChild(U);
V.appendChild(O);
var K=document.createElement("div");
K.className="backBtn";
Event.observe(K,"click",T,true);
var M=document.createElement("div");
M.className="btnText";
M.appendChild(document.createTextNode("Back"));
K.appendChild(M);
O.appendChild(K);
document.body.appendChild(V)
}});
function D(){var J=document.getElementById("fullscreen-dlg");
var M=document.getElementById("fullscreen-dlg-body-tab-wysiwyg");
F(J,"display","block");
var L=document.getElementById("parentswapDiv");
var N=document.getElementById("swapDiv");
if(L==null||L==undefined){L=document.createElement("div");
L.setAttribute("id","parentswapDiv");
if(N==null||N==undefined){N=document.createElement("div");
N.setAttribute("id","swapDiv")
}L.appendChild(N);
M.appendChild(L)
}var K=document.getElementById("xwikieditcontent");
tinyMCE.execCommand("mceRemoveControl",false,"content");
var I=document.createElement("div");
document.body.appendChild(I);
E(L,I);
E(K,L);
E(I,K);
document.body.removeChild(I);
document.body.style.overflow="hidden";
document.getElementById("show-dialog-btn").style.display="none";
tinyMCE.execCommand("mceAddControl",false,"content")
}function C(){var J=document.getElementById("fullscreen-dlg");
F(J,"display","none");
var L=document.getElementById("parentswapDiv");
var K=document.getElementById("xwikieditcontent");
var M=document.getElementById("swapDiv");
tinyMCE.execCommand("mceRemoveControl",false,"content");
var I=document.createElement("div");
document.body.appendChild(I);
E(L,I);
E(K,L);
E(I,K);
document.body.removeChild(I);
document.body.style.overflow="auto";
document.getElementById("show-dialog-btn").style.display="block";
window.setTimeout("tinyMCE.execCommand('mceAddControl', false, 'content')",10)
}function B(){var J=document.getElementById("fullscreen-dlg");
F(J,"display","block");
var M=document.getElementById("parentswapDiv");
var N=document.getElementById("swapDiv");
if(M==null||M==undefined){M=document.createElement("div");
M.setAttribute("id","parentswapDiv");
if(N==null||N==undefined){N=document.createElement("div");
N.setAttribute("id","swapDiv")
}M.appendChild(N);
var L=document.getElementById("fullscreen-dlg-body-tab-wiki");
L.appendChild(M)
}var K=document.getElementById("xwikieditcontentinner");
var I=document.createElement("div");
document.body.appendChild(I);
E(M,I);
E(K,M);
E(I,K);
document.body.removeChild(I);
document.body.style.overflow="hidden"
}function G(){var J=document.getElementById("fullscreen-dlg");
F(J,"display","none");
var L=document.getElementById("parentswapDiv");
var K=document.getElementById("xwikieditcontentinner");
var M=document.getElementById("swapDiv");
var I=document.createElement("div");
document.body.appendChild(I);
E(L,I);
E(K,L);
E(I,K);
document.body.removeChild(I);
document.body.style.overflow="auto"
}})();