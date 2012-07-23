if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.editors)=="undefined"){XWiki.editors=new Object()
}XWiki.editors.XDataEditors=Class.create({initialize:function(){this.classDocument=$$("meta[name=document]")[0].content;
this.classDocumentName=$$("meta[name=page]")[0].content;
this.classDocumentSpace=$$("meta[name=space]")[0].content;
this.urlEditTemplate="$xwiki.getURL('__space__.__page__', 'edit')".replace("__space__",this.classDocumentSpace).replace("__page__",this.classDocumentName);
this.urlAddPropertyTemplate="$xwiki.getURL('__space__.__page__', 'propadd')".replace("__space__",this.classDocumentSpace).replace("__page__",this.classDocumentName);
$$(".xclass").each(function(A){this.enhanceClassUX(A)
}.bind(this));
this.ajaxObjectAdd($("add_xobject"));
this.ajaxPropertyAdd();
this.makeSortable($("xwikiclassproperties"));
this.classSwitcherBehavior();
this.ajaxRemoveDeprecatedProperties($("body"),".syncAllProperties")
},enhanceClassUX:function(A){this.expandCollapseClass(A);
A.select(".xobject").each(function(B){this.enhanceObjectUX(B)
}.bind(this));
A.select(".xproperty").each(function(B){this.expandCollapseMetaProperty(B);
this.ajaxPropertyDeletion(B);
this.makeDisableVisible(B)
}.bind(this));
this.ajaxObjectAdd(A)
},enhanceObjectUX:function(A){this.ajaxObjectDeletion(A);
this.editButtonBehavior(A);
this.expandCollapseObject(A);
this.ajaxRemoveDeprecatedProperties(A,".syncProperties")
},ajaxObjectAdd:function(A){if(!A){return 
}A.select(".xobject-add-control").each(function(B){B.observe("click",function(G){B.blur();
G.stop();
var D,E,F;
if(B.href){D=B.href.replace(/[?&]xredirect=[^&]*/,"");
E=true
}else{var C=A.down("select");
if(C&&C.selectedIndex>=0){F=C.options[C.selectedIndex].value
}E=F&&F!="-";
D=this.urlEditTemplate+"?xpage=editobject&xaction=addObject&className="+F
}if(!B.disabled&&E){new Ajax.Request(D,{onCreate:function(){B.disabled=true;
B.notification=new XWiki.widgets.Notification("$msg.get('core.editors.object.add.inProgress')","inprogress")
},onSuccess:function(H){var I=B.up(".add_xobject");
if(I){var K;
if(I.up(".xclass")){I.insert({before:H.responseText});
K=I.previous()
}else{I.insert({after:H.responseText});
K=I.next()
}if(K){var M;
if(K.hasClassName("xclass")){this.enhanceClassUX(K);
M=K.down(".xobject")
}else{if(K.hasClassName("xobject")){this.enhanceObjectUX(K);
var J=K.id.replace(/^xobject_/,"xclass_").replace(/_\d+$/,"");
var L=$(J);
if(L){L.down(".add_xobject").insert({before:K});
this.updateXObjectCount(L)
}M=K
}}M.removeClassName("collapsed");
M.up(".xclass").removeClassName("collapsed")
}}B.notification.replace(new XWiki.widgets.Notification("$msg.get('core.editors.object.add.done')","done"))
}.bind(this),onFailure:function(H){var I=H.statusText;
if(H.statusText==""||H.status==12031){I="Server not responding"
}B.notification.replace(new XWiki.widgets.Notification("$msg.get('core.editors.object.add.failed')"+I,"error"))
},onComplete:function(){B.disabled=false
},on1223:function(H){H.request.options.onSuccess(H)
},on0:function(H){H.request.options.onFailure(H)
}})
}}.bindAsEventListener(this))
}.bind(this))
},ajaxObjectDeletion:function(A){var B=A.down("a.delete");
B.observe("click",function(C){B.blur();
C.stop();
if(!B.disabled){new XWiki.widgets.ConfirmedAjaxRequest(B.href,{onCreate:function(){B.disabled=true
},onSuccess:function(){var D=B.up(".xobject");
var E=D.up(".xclass");
D.remove();
this.updateXObjectCount(E)
}.bind(this),onComplete:function(){B.disabled=false
}},{confirmationText:"$msg.get('core.editors.object.delete.confirm')",progressMessageText:"$msg.get('core.editors.object.delete.inProgress')",successMessageText:"$msg.get('core.editors.object.delete.done')",failureMessageText:"$msg.get('core.editors.object.delete.failed')"})
}}.bindAsEventListener(this))
},ajaxRemoveDeprecatedProperties:function(A,B){A.select(B).each(function(C){C.observe("click",function(D){C.blur();
D.stop();
if(!C.disabled){new Ajax.Request(C.href,{onCreate:function(){C.disabled=true;
C.notification=new XWiki.widgets.Notification("$msg.get('core.editors.object.removeDeprecatedProperties.inProgress')","inprogress")
},onSuccess:function(E){A.select(".deprecatedProperties").invoke("remove");
C.notification.replace(new XWiki.widgets.Notification("$msg.get('core.editors.object.removeDeprecatedProperties.done')","done"))
},onFailure:function(E){var F=E.statusText;
if(E.statusText==""||E.status==12031){F="Server not responding"
}C.notification.replace(new XWiki.widgets.Notification("$msg.get('core.editors.object.removeDeprecatedProperties.failed')"+F,"error"))
},onComplete:function(){C.disabled=false
},on1223:function(E){E.request.options.onSuccess(E)
},on0:function(E){E.request.options.onFailure(E)
}})
}})
})
},ajaxPropertyAdd:function(){$$("input[name=action_propadd]").each(function(B){B._x_propnameElt=$("propname");
B._x_proptypeElt=$("proptype");
B._x_form_tokenElt=$("form_token");
var A=B._x_form_tokenElt?B._x_form_tokenElt.value:"";
B.observe("click",function(D){B.blur();
D.stop();
if(!B.disabled&&B._x_propnameElt.value!=""&&B._x_proptypeElt.selectedIndex>=0){var C=this.urlAddPropertyTemplate+"?propname="+B._x_propnameElt.value+"&proptype="+B._x_proptypeElt.options[B._x_proptypeElt.selectedIndex].value+"&xredirect="+encodeURIComponent(this.urlEditTemplate+"?xpage=editclass&xaction=displayProperty&propName="+B._x_propnameElt.value)+"&form_token="+B._x_form_tokenElt.value;
new Ajax.Request(C,{onCreate:function(){B.disabled=true;
B.notification=new XWiki.widgets.Notification("$msg.get('core.editors.class.addProperty.inProgress')","inprogress")
},onSuccess:function(E){$("xclassContent").insert({bottom:E.responseText});
var F=$("xclassContent").lastChild;
this.expandCollapseMetaProperty(F);
this.makeSortable(F);
this.ajaxPropertyDeletion(F);
this.makeDisableVisible(F);
B.notification.replace(new XWiki.widgets.Notification("$msg.get('core.editors.class.addProperty.done')","done"))
}.bind(this),onFailure:function(E){var F=E.statusText;
if(E.statusText==""||E.status==12031){F="Server not responding"
}B.notification.replace(new XWiki.widgets.Notification("$msg.get('core.editors.class.addProperty.failed')"+F,"error"))
},onComplete:function(){B.disabled=false
},on1223:function(E){E.request.options.onSuccess(E)
},on0:function(E){E.request.options.onFailure(E)
}})
}}.bindAsEventListener(this))
}.bind(this))
},ajaxPropertyDeletion:function(B){var A=B.down("a.delete");
A.observe("click",function(C){A.blur();
C.stop();
if(!A.disabled){new XWiki.widgets.ConfirmedAjaxRequest(A.href,{onCreate:function(){A.disabled=true
},onSuccess:function(){B.remove()
},onComplete:function(){A.disabled=false
}},{confirmationText:"$msg.get('core.editors.class.deleteProperty.confirm')",progressMessageText:"$msg.get('core.editors.class.deleteProperty.inProgress')",successMessageText:"$msg.get('core.editors.class.deleteProperty.done')",failureMessageText:"$msg.get('core.editors.class.deleteProperty.failed')"})
}})
},makeDisableVisible:function(A){A.down(".disabletool input").observe("click",function(B){A.toggleClassName("disabled")
})
},editButtonBehavior:function(A){var B=A.down("a.edit");
if(!B){return 
}B.observe("click",function(C){B.blur();
C.stop();
window.location=B.href
}.bindAsEventListener())
},updateXObjectCount:function(C){var B=C.select(".xobject").size();
if(B==0){C.remove()
}else{var A=C.down(".xclass_xobject_nb");
if(typeof (A)!="undefined"){A.update("("+B+")")
}}},expandCollapseObject:function(B){totalItems=$$("#xwikiobjects .xobject").size();
B.addClassName("collapsable");
if(totalItems>1){B.toggleClassName("collapsed")
}var A=B.down(".xobject-title");
A.observe("click",function(C){A.up().toggleClassName("collapsed")
}.bindAsEventListener());
B.select(".xobject-content dt").each(function(D){if(!D.down("input[type=checkbox]")){D.addClassName("collapsable");
var C=new Element("span",{"class":"collapser"});
C.observe("click",function(E){this.up("dt").next("dd").toggle();
this.toggleClassName("collapsed")
}.bindAsEventListener(C));
D.insert({top:C})
}else{D.addClassName("uncollapsable")
}});
B.select(".xobject-content dt label").each(function(C){C.observe("click",function(D){if(C.up("dt").down("span").hasClassName("collapsed")){C.up("dt").next("dd").toggle();
C.up("dt").down("span").toggleClassName("collapsed")
}}.bindAsEventListener())
})
},expandCollapseClass:function(B){B.addClassName("collapsable");
var A=B.down(".xclass-title");
A.observe("click",function(C){A.up().toggleClassName("collapsed")
}.bindAsEventListener())
},expandCollapseMetaProperty:function(B){B.addClassName("collapsable");
B.addClassName("collapsed");
var A=B.down(".xproperty-title");
A.observe("click",function(C){A.up().toggleClassName("collapsed")
}.bindAsEventListener());
B.select(".xproperty-content dt").each(function(D){if(!D.down("input[type=checkbox]")){D.addClassName("collapsable");
var C=new Element("span",{"class":"collapser"});
C.observe("click",function(E){this.up("dt").next("dd").toggle();
this.toggleClassName("collapsed")
}.bindAsEventListener(C));
D.insert({top:C})
}else{D.addClassName("uncollapsable")
}});
B.select(".xproperty-content dt label").each(function(C){C.observe("click",function(D){if(C.up("dt").down("span").hasClassName("collapsed")){C.up("dt").next("dd").toggle();
C.up("dt").down("span").toggleClassName("collapsed")
}}.bindAsEventListener())
})
},classSwitcherBehavior:function(){var A=$$("#switch-xclass #classname");
if(A.size()>0){A=A[0];
A.observe("change",function(){var B=this.options[this.selectedIndex].value;
if(B!="-"){new XWiki.widgets.ConfirmationBox({onYes:function(){document.fire("xwiki:actions:save",{"continue":true,form:$("propupdate")});
document.observe("xwiki:document:saved",function(){window.self.location=B
})
},onNo:function(){window.self.location=B
}},{confirmationText:"$msg.get('core.editors.class.switchClass.confirm')"})
}}.bindAsEventListener(A));
A.up("form").down("input[type='submit']").hide();
A.up("form").down(".warningmessage").hide()
}},makeSortable:function(A){if(!A){return 
}A.select(".xproperty-content").each(function(B){B.select("input").each(function(C){if(C.id.endsWith("_number")){B.numberProperty=C;
C.up().hide();
if(C.up().previous("dt")){C.up().previous("dt").hide()
}}})
});
A.select(".xproperty-title .tools").each(function(C){var B=new Element("span",{"class":"tool move",title:"Drag and drop to change the order"}).update("move");
C.makePositioned();
C.appendChild(B);
B.observe("click",function(D){D.stop()
}.bindAsEventListener())
});
Sortable.create("xclassContent",{tag:"div",only:"xproperty",handle:"move",starteffect:this.startDrag.bind(this),endeffect:this.endDrag.bind(this),onUpdate:this.updateOrder.bind(this)})
},updateOrder:function(A){var C=A.childElements();
for(var B=0;
B<C.size();
++B){var D=C[B].down(".xproperty-content");
D.numberProperty.value=B+1
}},startDrag:function(A){A.addClassName("dragged");
$("xclassContent").childElements().each(function(B){B._expandedBeforeDrag=!B.hasClassName("collapsed");
B.addClassName("collapsed")
})
},endDrag:function(A){A.removeClassName("dragged");
$("xclassContent").childElements().each(function(B){if(B._expandedBeforeDrag){B.removeClassName("collapsed")
}})
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.editors.XDataEditors()
});