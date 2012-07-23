tinyMCE.importPluginLanguagePack("layer","en");
var TinyMCE_LayerPlugin={getInfo:function(){return{longname:"Layer",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_layer.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},initInstance:function(A){if(tinyMCE.isMSIE&&!tinyMCE.isOpera){A.getDoc().execCommand("2D-Position")
}},handleEvent:function(E){var D=tinyMCE.selectedInstance,B=TinyMCE_LayerPlugin;
var A=D.getWin(),C=D._lastStyleElm,E;
if(tinyMCE.isGecko){E=B._getParentLayer(D.getFocusElement());
if(E){if(!D._lastStyleElm){E.style.overflow="auto";
D._lastStyleElm=E
}}else{if(C){C=D._lastStyleElm;
C.style.width=C.scrollWidth+"px";
C.style.height=C.scrollHeight+"px";
C.style.overflow="";
D._lastStyleElm=null
}}}return true
},handleVisualAid:function(D,B,F,E){var A=E.getDoc().getElementsByTagName("div"),C;
for(C=0;
C<A.length;
C++){if(new RegExp("absolute|relative|static","gi").test(A[C].style.position)){if(F){tinyMCE.addCSSClass(A[C],"mceVisualAid")
}else{tinyMCE.removeCSSClass(A[C],"mceVisualAid")
}}}},getControlHTML:function(A){switch(A){case"moveforward":return tinyMCE.getButtonHTML(A,"lang_layer_forward_desc","{$pluginurl}/images/forward.gif","mceMoveForward",true);
case"movebackward":return tinyMCE.getButtonHTML(A,"lang_layer_backward_desc","{$pluginurl}/images/backward.gif","mceMoveBackward",true);
case"absolute":return tinyMCE.getButtonHTML(A,"lang_layer_absolute_desc","{$pluginurl}/images/absolute.gif","mceMakeAbsolute",true);
case"insertlayer":return tinyMCE.getButtonHTML(A,"lang_layer_insertlayer_desc","{$pluginurl}/images/insert_layer.gif","mceInsertLayer",true)
}return""
},execCommand:function(E,B,D,F,C){var A=TinyMCE_LayerPlugin;
switch(D){case"mceInsertLayer":A._insertLayer();
return true;
case"mceMoveForward":A._move(1);
return true;
case"mceMoveBackward":A._move(-1);
return true;
case"mceMakeAbsolute":A._toggleAbsolute();
return true
}return false
},handleNodeChange:function(I,C,H,D,F,E){var G=tinyMCE.getInstanceById(I),J=TinyMCE_LayerPlugin;
var A=J._getParentLayer(G.getFocusElement());
var B=tinyMCE.getParentElement(G.getFocusElement(),"div,p,img");
tinyMCE.switchClass(I+"_absolute","mceButtonDisabled");
tinyMCE.switchClass(I+"_moveforward","mceButtonDisabled");
tinyMCE.switchClass(I+"_movebackward","mceButtonDisabled");
if(B){tinyMCE.switchClass(I+"_absolute","mceButtonNormal")
}if(A&&A.style.position.toLowerCase()=="absolute"){tinyMCE.switchClass(I+"_absolute","mceButtonSelected");
tinyMCE.switchClass(I+"_moveforward","mceButtonNormal");
tinyMCE.switchClass(I+"_movebackward","mceButtonNormal")
}},_move:function(E){var D=tinyMCE.selectedInstance,H=TinyMCE_LayerPlugin,C,F=new Array();
var B=H._getParentLayer(D.getFocusElement()),I=-1,G=-1;
var A=tinyMCE.selectNodes(D.getBody(),function(J){return J.nodeType==1&&new RegExp("absolute|relative|static","gi").test(J.style.position)
});
for(C=0;
C<A.length;
C++){F[C]=A[C].style.zIndex?parseInt(A[C].style.zIndex):0;
if(I<0&&A[C]==B){I=C
}}if(E<0){for(C=0;
C<F.length;
C++){if(F[C]<F[I]){G=C;
break
}}if(G>-1){A[I].style.zIndex=F[G];
A[G].style.zIndex=F[I]
}else{if(F[I]>0){A[I].style.zIndex=F[I]-1
}}}else{for(C=0;
C<F.length;
C++){if(F[C]>F[I]){G=C;
break
}}if(G>-1){A[I].style.zIndex=F[G];
A[G].style.zIndex=F[I]
}else{A[I].style.zIndex=F[I]+1
}}D.repaint()
},_getParentLayer:function(A){return tinyMCE.getParentNode(A,function(B){return B.nodeType==1&&new RegExp("absolute|relative|static","gi").test(B.style.position)
})
},_insertLayer:function(){var B=tinyMCE.selectedInstance;
var E=tinyMCE.getParentElement(B.getFocusElement());
var D=tinyMCE.getAbsPosition(E);
var F=B.getDoc();
var C=F.createElement("div");
var A=B.selection.getSelectedHTML();
C.style.position="absolute";
C.style.left=D.absLeft+"px";
C.style.top=(D.absTop>20?D.absTop:20)+"px";
C.style.width="100px";
C.style.height="100px";
C.className="mceVisualAid";
if(!A){A=tinyMCE.getLang("lang_layer_content")
}C.innerHTML=A;
F.body.appendChild(C)
},_toggleAbsolute:function(){var C=tinyMCE.selectedInstance,A=TinyMCE_LayerPlugin;
var B=A._getParentLayer(C.getFocusElement());
if(B==null){B=tinyMCE.getParentElement(C.getFocusElement(),"div,p,img")
}if(B){if(B.style.position.toLowerCase()=="absolute"){B.style.position="";
B.style.left="";
B.style.top=""
}else{B.style.position="absolute";
if(B.style.left==""){B.style.left=20+"px"
}if(B.style.top==""){B.style.top=20+"px"
}if(B.style.width==""){B.style.width=B.width?(B.width+"px"):"100px"
}if(B.style.height==""){B.style.height=B.height?(B.height+"px"):"100px"
}tinyMCE.handleVisualAid(C.getBody(),true,C.visualAid,C)
}C.repaint();
tinyMCE.triggerNodeChange()
}}};
tinyMCE.addPlugin("layer",TinyMCE_LayerPlugin);