isc.Element.addClassMethods({getOffsetLeft:function(E){if(E==null){this.logWarn("getOffsetLeft: passed null element");
return 0
}var C=E.offsetLeft;
if(E._cachedReportedOffsetLeft==C){return E._cachedCalculatedOffsetLeft
}else{}var A=parseInt(isc.Element.getComputedStyleAttribute(E,"marginLeft"));
if(isc.isA.Number(A)&&A>0){C-=A
}var I=this.getDocumentBody(),N,M="px",D=E.style.position;
if(isc.Browser.isMoz){if(E.offsetParent==null){return C
}if(E.offsetParent!=I){N=this.ns.Element.getComputedStyle(E.offsetParent,["borderLeftWidth","overflow"]);
var O=isc.Browser.geckoVersion,L=(N.overflow!="visible")&&(O>=20051111||(D==isc.Canvas.ABSOLUTE&&N.overflow!="hidden")),J=(O>20020826&&(E.offsetParent.style.MozBoxSizing=="border-box"));
if(J!=L){if(J){C-=(isc.isA.Number(parseInt(N.borderLeftWidth))?parseInt(N.borderLeftWidth):0)
}if(L){C+=(isc.isA.Number(parseInt(N.borderLeftWidth))?parseInt(N.borderLeftWidth):0)
}}}}if(isc.Browser.isIE&&!isc.Browser.isIE8Strict){var B=E.offsetParent,N;
if(B&&N!=I){N=B.currentStyle
}var H=(E.currentStyle.height!=isc.Canvas.AUTO||E.currentStyle.width!=isc.Canvas.AUTO);
var F=true;
while(B&&B!=document.body){if(N.position==isc.Canvas.ABSOLUTE){F=false
}if(N.width==isc.Canvas.AUTO&&N.height==isc.Canvas.AUTO&&N.position==isc.Canvas.RELATIVE){if(F&&isc.isA.String(N.borderLeftWidth)&&N.borderLeftWidth.contains(M)){C-=parseInt(N.borderLeftWidth)
}if(H){if(isc.isA.String(N.marginLeft)&&N.marginLeft.contains(M)){var G=parseInt(N.marginLeft);
if(G>0){C-=G
}}if(B.offsetParent!=I){var K=B.offsetParent.currentStyle.padding;
if(isc.isA.String(K)&&K.contains(M)){C-=parseInt(K)
}}else{C-=(I.leftMargin?parseInt(I.leftMargin):0)
}}}D=B.style.position;
B=B.offsetParent;
if(B&&B!=document.body){N=B.currentStyle
}}}if(isc.Browser.isSafari&&isc.Browser.safariVersion<525.271){if(E.offsetParent!=null&&E.offsetParent!=I){var P=this.ns.Element.getComputedStyle(E.offsetParent,["borderLeftWidth"]).borderLeftWidth;
if(P!=null){P=parseInt(P)
}if(isc.isA.Number(P)){C-=P
}}}E._cachedReportedOffsetLeft=E.offsetLeft;
E._cachedCalculatedOffsetLeft=C;
return C
}});