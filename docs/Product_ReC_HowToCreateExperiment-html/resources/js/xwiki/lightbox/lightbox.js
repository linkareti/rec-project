Lightbox=Class.create({initialize:function(C,A,B){this.formUrl=C;
this.saveUrl=A;
this.redirectUrl=B;
this.formData="";
this.loadedForms=new Object();
this.lbinit();
this.lbShow();
this.lbLoadForm(C)
},lbShow:function(){this.lbLoading();
toggleClass($("lb-bg"),"hidden");
toggleClass($("lb-align"),"hidden");
this.resizeBackground();
if(browser.isIE6x){$$("select").each(function(A){if(A.up("#lb")){return 
}A._x_originalVisibility=A.style.visibility;
A.setStyle({visibility:"hidden"})
})
}},lbHide:function(){toggleClass($("lb-bg"),"hidden");
toggleClass($("lb-align"),"hidden");
if(browser.isIE6x){$$("select").each(function(A){A.setStyle({visibility:A._x_originalVisibility})
})
}},lbLoading:function(){if(this.currentUrl){this.loadedForms[this.currentUrl]=$("lb-content").firstChild.cloneNode(true)
}$("lb-content").innerHTML=this.getWaiting()
},lbLoadForm:function(A){this.currentUrl=A;
if(this.loadedForms[A]){$("lb-content").innerHTML="";
this.lbPlaceContentInDocument(this.loadedForms[A],$("lb-content"));
this.form=c.getElementsByTagName("form")[0]
}else{new Ajax.Request(A,{onSuccess:this.lbFormDataLoaded.bind(this)})
}},lbFormDataLoaded:function(B){var A=document.createElement("div");
A.innerHTML=B.responseText;
$("lb-content").innerHTML="";
this.lbPlaceContentInDocument(A,$("lb-content"),function(){this.resizeBackground()
}.bind(this));
this.form=$("lb-content").getElementsByTagName("form")[0]
},lbPlaceContentInDocument:function(H,I,B){document.stopObserving("dom:loaded");
var C=Array.from(H.getElementsByTagName("script"));
var K=Array.from(H.getElementsByTagName("link"));
var M=Array.from(H.getElementsByTagName("style"));
var L=K.concat(C,M).flatten();
var J=[];
for(var F=0;
F<L.length;
F++){J[F]=document.createElement(L[F].tagName);
var E=L[F].attributes;
for(var D=0;
D<E.length;
D++){if(E[D].value!=""){J[F].setAttribute(E[D].name,E[D].value)
}}try{J[F].innerHTML=L[F].innerHTML;
L[F].parentNode.removeChild(L[F])
}catch(A){if(J[F].tagName.toLowerCase()=="script"){J[F].text=L[F].text;
L[F].parentNode.removeChild(L[F])
}}}I.appendChild(H);
var G=function(Q,P,R,N){var O=0;
if(N){O=N
}while(O<Q.length){P.appendChild(Q[O]);
if(Q[O].tagName.toLowerCase()=="script"&&Q[O].src!=""){if(browser.isIE==true&&typeof XDomainRequest=="undefined"){Event.observe(Q[O],"readystatechange",function(S){if(S.element().readyState=="complete"){G(Q,P,R,O+1)
}})
}else{Event.observe(Q[O],"load",function(){G(Q,P,R,O+1)
})
}return 
}O++
}R()
};
G(J,I,function(){if(Object.isFunction(B)){B()
}var N=document.createElement("script");
try{N.innerHTML='document.fire("dom:loaded");'
}catch(O){N.text='document.fire("dom:loaded");'
}I.appendChild(N)
}.bind(this))
},lbSaveForm:function(){this.lbSaveData();
Form.disable(this.form);
this.lbSaveSync(this.saveUrl);
this.lbHide();
window.location=this.redirectUrl
},lbNext:function(A){this.lbSaveData();
this.lbLoading();
this.lbLoadForm(A)
},lbSaveData:function(){this.formData+="&"+Form.serialize(this.form);
this.formData=this.formData.replace("_segmentChief=&","=&");
this.formData=this.formData.replace("_periodicity=&","=&")
},lbSave:function(A){this.lbSaveData();
new Ajax.Request(A+"?ajax=1",{parameters:this.formData,onSuccess:this.lbSaveDone.bind(this)})
},lbSaveSync:function(A){new Ajax.Request(A+"?ajax=1",{parameters:this.formData,asynchronous:false})
},lbSaveDone:function(A){this.lbHide()
},lbClearData:function(){this.formData=""
},lbClose:function(){this.lbHide();
if(this.redirectUrl!==undefined){window.location=this.redirectUrl
}},lbSetNext:function(A){this.nextURL=A
},getWaiting:function(){var A="$xwiki.getSkinFile('icons/ajax-loader.gif')";
return'<div style="padding: 30px;"><img src="'+A+'"/></div>'
},lbcustominit:function(B,A,E,C){if(!$("lb")){var D=this.insertlbcontent(B,A,E,C);
new Insertion.Top("body",D)
}},lbinit:function(){return this.lbcustominit("#FFF","#FFF","#000","rounded")
},insertlbcontent:function(B,A,E,C){var D='<div id="lb-bg" class="hidden"></div><div id="lb-align" class="hidden"><div id="lb"><div id="lb-top"><div id="close-wrap"><div id="lb-close" onclick="window.lb.lbClose();" title="Cancel and close">&nbsp;</div></div>';
if(C=="lightrounded"){D+=this.roundedlighttop(B,A)
}else{if(C=="rounded"){D+=this.roundedtop(B,A)
}else{D+='<div class="lb-squarred" style="background:'+B+"; border-color:"+A+'"></div></div>'
}}D+='</div><div class="lb-content" style="background:'+B+"; border-color:"+A+"; color:"+E+'" id="lb-content">Lightbox Content</div>';
if(C=="lightrounded"){D+=this.roundedlightbottom(B,A)
}else{if(C=="rounded"){D+=this.roundedbottom(B,A)
}else{D+='<div class="lb-squarred" style="background:'+B+"; border-color:"+A+'"></div></div></div></div>'
}}return D
},resizeBackground:function(){var A=document.body.parentNode.scrollHeight;
if(document.body.scrollHeight>A){A=document.body.scrollHeight
}if(document.body.parentNode.clientHeight>A){A=document.body.parentNode.clientHeight
}$("lb-bg").style.height=A+"px"
},roundedlightbottom:function(A,B){var C='<div class="roundedlight"><b class="top"><b class="b4b" style="background:'+B+';"></b><b class="b3b" style="background:'+A+"; border-color:"+B+';"></b><b class="b3b" style="background:'+A+"; border-color:"+B+';"></b><b class="b1b" style="background:'+A+"; border-color:"+B+';"></b></b> </div>';
return C
},roundedbottom:function(A,B){var C='<div class="rounded"><b class="bottom" style="padding:0px; margin:0px;"><b class="b12b" style="background:'+B+';"></b><b class="b11b" style="background:'+A+"; border-color:"+B+';"></b><b class="b10b" style="background:'+A+"; border-color:"+B+';"></b><b class="b9b" style="background:'+A+"; border-color:"+B+';"></b><b class="b8b" style="background:'+A+"; border-color:"+B+';"></b><b class="b7b" style="background:'+A+"; border-color:"+B+';"></b><b class="b6b" style="background:'+A+"; border-color:"+B+';"></b><b class="b5b" style="background:'+A+"; border-color:"+B+';"></b><b class="b4b" style="background:'+A+"; border-color:"+B+';"></b><b class="b3b" style="background:'+A+"; border-color:"+B+';"></b><b class="b2b" style="background:'+A+"; border-color:"+B+';"></b><b class="b1b" style="background:'+A+"; border-color:"+B+';"></b></b></div>';
return C
},roundedlighttop:function(A,B){var C='<div class="roundedlight"><b class="top"><b class="b1" style="background:'+B+';"></b><b class="b2" style="background:'+A+"; border-color:"+B+';"></b><b class="b3" style="background:'+A+"; border-color:"+B+';"></b><b class="b4" style="background:'+A+"; border-color:"+B+';"></b></b> </div>';
return C
},roundedtop:function(A,B){var C='<div class="rounded"><b class="top"><b class="b1" style="background:'+B+';"></b><b class="b2" style="background:'+A+"; border-color:"+B+';"></b><b class="b3" style="background:'+A+"; border-color:"+B+';"></b><b class="b4" style="background:'+A+"; border-color:"+B+';"></b><b class="b5" style="background:'+A+"; border-color:"+B+';"></b><b class="b6" style="background:'+A+"; border-color:"+B+';"></b><b class="b7" style="background:'+A+"; border-color:"+B+';"></b><b class="b8" style="background:'+A+"; border-color:"+B+';"></b><b class="b9" style="background:'+A+"; border-color:"+B+';"></b><b class="b10" style="background:'+A+"; border-color:"+B+';"></b><b class="b11" style="background:'+A+"; border-color:"+B+';"></b><b class="b12" style="background:'+A+"; border-color:"+B+';"></b></b></div>';
return C
},lightboxlink:function(B,A){var C='<a href="#" onclick="javascript:$(\'lb-content\').innerHTML ='+A+"; toggleClass($('lb-bg'), 'hidden'); toggleClass($('lb-align'), 'hidden');\">"+B+"</a>";
return C
}});