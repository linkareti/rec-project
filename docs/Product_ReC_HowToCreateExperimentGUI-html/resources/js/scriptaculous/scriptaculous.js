var Scriptaculous={Version:"1.8.3",require:function(B){var A=document.createElement("script");
A.type="text/javascript";
A.src=B;
document.getElementsByTagName("head")[0].appendChild(A)
},REQUIRED_PROTOTYPE:"1.6.0.3",load:function(){function A(C){var D=C.replace(/_.*|\./g,"");
D=parseInt(D+"0".times(4-D.length));
return C.indexOf("_")>-1?D-1:D
}if((typeof Prototype=="undefined")||(typeof Element=="undefined")||(typeof Element.Methods=="undefined")||(A(Prototype.Version)<A(Scriptaculous.REQUIRED_PROTOTYPE))){throw ("script.aculo.us requires the Prototype JavaScript framework >= "+Scriptaculous.REQUIRED_PROTOTYPE)
}var B=/scriptaculous\.js(\?.*)?$/;
$$("head script[src]").select(function(C){return C.src.match(B)
}).each(function(D){var E=D.src.replace(B,""),C=D.src.match(/\?.*load=([a-z,]*)/);
(C?C[1]:"builder,effects,dragdrop,controls,slider,sound").split(",").each(function(F){Scriptaculous.require(E+F+".js")
})
})
}};
Scriptaculous.load();