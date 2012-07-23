if(typeof XWiki=="undefined"){XWiki=new Object()
}if(typeof (XWiki.widgets)=="undefined"){XWiki.widgets=new Object()
}var useXWKns;
if(useXWKns){if(typeof _xwk=="undefined"){_xwk=new Object()
}}else{_xwk=this
}_xwk.ajaxSuggest=XWiki.widgets.Suggest=Class.create({options:{minchars:1,method:"get",varname:"input",className:"ajaxsuggest",timeout:2500,delay:500,offsety:-5,shownoresults:true,noresults:"No results!",maxheight:250,cache:false,seps:"",resultsParameter:"results",resultId:"id",resultValue:"value",resultInfo:"info",parentContainer:"body"},sInput:"",nInputChars:0,aSuggestions:[],iHighlighted:0,initialize:function(A,B){this.fld=$(A);
if(!this.fld){return false
}Object.extend(this.options,B||{});
if(!$(this.options.parentContainer)){this.options.parentContainer=$(document.body)
}if(this.options.seps){this.seps=this.options.seps
}else{this.seps=""
}this.fld.observe("keyup",this.onKeyUp.bindAsEventListener(this));
if(Prototype.Browser.IE||Prototype.Browser.WebKit){this.fld.observe("keydown",this.onKeyPress.bindAsEventListener(this))
}else{this.fld.observe("keypress",this.onKeyPress.bindAsEventListener(this))
}this.fld.setAttribute("autocomplete","off")
},onKeyUp:function(D){var B=D.keyCode;
switch(B){case Event.KEY_RETURN:case Event.KEY_ESC:case Event.KEY_UP:case Event.KEY_DOWN:break;
default:if(this.seps){var C=-1;
for(var A=0;
A<this.seps.length;
A++){if(this.fld.value.lastIndexOf(this.seps.charAt(A))>C){C=this.fld.value.lastIndexOf(this.seps.charAt(A))
}}if(C==-1){this.getSuggestions(this.fld.value)
}else{this.getSuggestions(this.fld.value.substring(C+1))
}}else{this.getSuggestions(this.fld.value)
}}},onKeyPress:function(B){if(!$(this.idAs)){return 
}var A=B.keyCode;
switch(A){case Event.KEY_RETURN:if(this.aSuggestions.length==1){this.setHighlight(1)
}this.setHighlightedValue();
Event.stop(B);
break;
case Event.KEY_ESC:this.clearSuggestions();
Event.stop(B);
break;
case Event.KEY_UP:this.changeHighlight(A);
Event.stop(B);
break;
case Event.KEY_DOWN:this.changeHighlight(A);
Event.stop(B);
break;
default:break
}},getSuggestions:function(D){D=D.strip();
if(D==this.sInput){return false
}if(D.length<this.options.minchars){this.sInput="";
return false
}if(D.length>this.nInputChars&&this.aSuggestions.length&&this.options.cache){var A=[];
for(var B=0;
B<this.aSuggestions.length;
B++){if(this.aSuggestions[B].value.substr(0,D.length).toLowerCase()==D.toLowerCase()){A.push(this.aSuggestions[B])
}}this.sInput=D;
this.nInputChars=D.length;
this.aSuggestions=A;
this.createList(this.aSuggestions);
return false
}else{this.sInput=D;
this.nInputChars=D.length;
var C=this;
clearTimeout(this.ajID);
this.ajID=setTimeout(function(){C.doAjaxRequest()
},this.options.delay)
}return false
},doAjaxRequest:function(){var D=this;
var B=this.options.script+this.options.varname+"="+encodeURI(this.fld.value.strip());
var E=this.options.method;
var C={};
if(this.options.json){C.Accept="application/json"
}else{C.Accept="application/xml"
}var A=new Ajax.Request(B,{method:E,requestHeaders:C,onSuccess:this.setSuggestions.bindAsEventListener(this),onFailure:function(F){alert("AJAX error: "+F.statusText)
}})
},setSuggestions:function(D){this.aSuggestions=[];
if(this.options.json){var E=D.responseJSON;
if(!E){return false
}var C=E[this.options.resultsParameter];
for(var B=0;
B<C.length;
B++){this.aSuggestions.push({id:C[B][this.options.resultId],value:C[B][this.options.resultValue],info:C[B][this.options.resultInfo]})
}}else{var A=D.responseXML;
var C=A.getElementsByTagName(this.options.resultsParameter)[0].childNodes;
for(var B=0;
B<C.length;
B++){if(C[B].hasChildNodes()){this.aSuggestions.push({id:C[B].getAttribute("id"),value:C[B].childNodes[0].nodeValue,info:C[B].getAttribute("info")})
}}}this.idAs="as_"+this.fld.id;
this.createList(this.aSuggestions)
},createList:function(A){var M=this;
if($(this.idAs)){$(this.idAs).remove()
}this.killTimeout();
if(A.length==0&&!this.options.shownoresults){return false
}var J=new Element("div",{id:this.idAs,className:this.options.className});
var C=new Element("div",{className:"as_corner"});
var F=new Element("div",{className:"as_bar"});
var O=new Element("div",{className:"as_header"});
O.appendChild(C);
O.appendChild(F);
J.appendChild(O);
var I=new Element("ul",{id:"as_ul"});
for(var P=0;
P<A.length;
P++){var T=A[P].value;
var L=T.toLowerCase().indexOf(this.sInput.toLowerCase());
var G=T.substring(0,L)+"<em>"+T.substring(L,L+this.sInput.length)+"</em>"+T.substring(L+this.sInput.length);
var N=new Element("span").update(G);
var R=new Element("a",{href:"#"});
var D=new Element("span",{className:"tl"}).update(" ");
var B=new Element("span",{className:"tr"}).update(" ");
R.appendChild(D);
R.appendChild(B);
R.appendChild(N);
R.name=P+1;
R.onclick=function(){M.setHighlightedValue();
return false
};
R.onmouseover=function(){M.setHighlight(this.name)
};
var H=new Element("li").update(R);
I.appendChild(H)
}if(A.length==0){var H=new Element("li",{className:"as_warning"}).update(this.options.noresults);
I.appendChild(H)
}J.appendChild(I);
var Q=new Element("div",{className:"as_corner"});
var S=new Element("div",{className:"as_bar"});
var K=new Element("div",{className:"as_footer"});
K.appendChild(Q);
K.appendChild(S);
J.appendChild(K);
var E=this.fld.cumulativeOffset();
J.style.left=E.left+"px";
J.style.top=(E.top+this.fld.offsetHeight+this.options.offsety)+"px";
J.style.width=this.fld.offsetWidth+"px";
J.onmouseover=function(){M.killTimeout()
};
J.onmouseout=function(){M.resetTimeout()
};
$(this.options.parentContainer).appendChild(J);
this.iHighlighted=0;
var M=this;
this.toID=setTimeout(function(){M.clearSuggestions()
},this.options.timeout)
},changeHighlight:function(A){var B=$("as_ul");
if(!B){return false
}var C;
if(A==40){C=this.iHighlighted+1
}else{if(A==38){C=this.iHighlighted-1
}}if(C>B.childNodes.length){C=B.childNodes.length
}if(C<1){C=1
}this.setHighlight(C)
},setHighlight:function(B){var A=$("as_ul");
if(!A){return false
}if(this.iHighlighted>0){this.clearHighlight()
}this.iHighlighted=Number(B);
A.childNodes[this.iHighlighted-1].className="as_highlight";
this.killTimeout()
},clearHighlight:function(){var A=$("as_ul");
if(!A){return false
}if(this.iHighlighted>0){A.childNodes[this.iHighlighted-1].className="";
this.iHighlighted=0
}},setHighlightedValue:function(){if(this.iHighlighted){if(this.sInput==""&&this.fld.value==""){this.sInput=this.fld.value=this.aSuggestions[this.iHighlighted-1].value
}else{if(this.seps){var D=-1;
for(var C=0;
C<this.seps.length;
C++){if(this.fld.value.lastIndexOf(this.seps.charAt(C))>D){D=this.fld.value.lastIndexOf(this.seps.charAt(C))
}}if(D==-1){this.sInput=this.fld.value=this.aSuggestions[this.iHighlighted-1].value
}else{this.fld.value=this.fld.value.substring(0,D+1)+this.aSuggestions[this.iHighlighted-1].value;
this.sInput=this.fld.value.substring(D+1)
}}else{this.sInput=this.fld.value=this.aSuggestions[this.iHighlighted-1].value
}}Event.fire(this.fld,"xwiki:suggest:selected");
this.fld.focus();
this.clearSuggestions();
if(typeof (this.options.callback)=="function"){this.options.callback(this.aSuggestions[this.iHighlighted-1])
}if(this.fld.id.indexOf("_suggest")>0){var B=this.fld.id.substring(0,this.fld.id.indexOf("_suggest"));
var A=$(B);
if(A){A.value=this.aSuggestions[this.iHighlighted-1].info
}}}},killTimeout:function(){clearTimeout(this.toID)
},resetTimeout:function(){clearTimeout(this.toID);
var A=this;
this.toID=setTimeout(function(){A.clearSuggestions()
},1000)
},clearSuggestions:function(){this.killTimeout();
var A=$(this.idAs);
var C=this;
if(A){var B=new Effect.Fade(A,{duration:"0.25",afterFinish:function(){if($(C.idAs)){$(C.idAs).remove()
}}})
}}});