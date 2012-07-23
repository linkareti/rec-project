if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.viewers)=="undefined"){XWiki.viewers=new Object()
}XWiki.viewers.Tags=Class.create({initialize:function(){$$(".doc-tags .tag-delete").each(this.ajaxTagDelete);
$$(".doc-tags .tag-add a").each(this.createTagAddForm.bind(this));
if($$(".doc-tags .tag-add-form").length>0){this.ajaxifyForm($$(".doc-tags .tag-add-form")[0])
}},ajaxTagDelete:function(A){A.observe("click",function(B){if(B){B.stop()
}if(!A.disabled){new Ajax.Request(A.readAttribute("href").replace(/&xredirect=.+$/,"&ajax=1"),{onCreate:function(){A.disabled=true;
A.notification=new XWiki.widgets.Notification("Deleting tag...","inprogress")
},onSuccess:function(){A.up(".tag-wrapper").remove()
},onFailure:function(C){new XWiki.widgets.Notification(C.responseText||"Server not responding","error")
},on0:function(C){C.request.options.onFailure(C)
},onComplete:function(){A.disabled=false;
A.notification.hide()
}})
}}.bindAsEventListener())
},createTagAddForm:function(A){A.observe("click",function(B){if(B){B.stop()
}if(!A._x_form){if(!A.disabled){new Ajax.Request(A.readAttribute("href").replace(/#.+$/,"&ajax=1&xpage=documentTags"),{onCreate:function(){A.disabled=true;
A.notification=new XWiki.widgets.Notification("Fetching form...","inprogress")
},onSuccess:function(C){var D=A.up();
A.remove();
D.update(C.responseText);
A._x_form=D.firstDescendant();
A._x_form._x_activator=A;
A._x_form.down("input[type=text]").focus();
this.ajaxifyForm(A._x_form)
}.bind(this),onFailure:function(C){new XWiki.widgets.Notification(C.responseText||"Server not responding","error")
},on0:function(C){C.request.options.onFailure(C)
},onComplete:function(){A.disabled=false;
A.notification.hide()
}})
}}else{Element.replace(A,A._x_form);
A._x_form.down("input[type=text]").focus()
}}.bindAsEventListener(this))
},ajaxifyForm:function(B){B.setAttribute("autocomplete","off");
B.down("input[type=text]").setAttribute("autocomplete","off");
B.down("input[type=text]").setAttribute("autocomplete","off");
B.observe("submit",function(C){C.stop();
B.down("input[type=text]").focus();
if(B.tag.value!=""){new Ajax.Request(B.action.replace(/&xredirect=.+$/,"&ajax=1&tag=")+encodeURIComponent(B.tag.value),{onCreate:function(){B.disable();
B.notification=new XWiki.widgets.Notification("Adding tag...","inprogress")
},onSuccess:function(D){var E=new Element("span");
E.insert(D.responseText+" ");
E.select(".tag-delete").each(this.ajaxTagDelete);
while(E.childNodes.length>0){B.up(".tag-add").insert({before:E.firstChild});
B.up(".tag-add").insert({before:" "});
E.removeChild(E.firstChild)
}B.reset()
}.bind(this),onFailure:function(D){new XWiki.widgets.Notification(D.responseText||"Server not responding","error")
},onComplete:function(){B.enable();
B.notification.hide()
},on0:function(D){D.request.options.onFailure(D)
}})
}}.bindAsEventListener(this));
B.observe("reset",function(C){Element.replace(B,B._x_activator)
}.bindAsEventListener(this));
var A=new Element("input",{type:"reset",value:B.down(".button-add-tag-cancel").innerHTML,"class":"button"});
B.down(".button-add-tag-cancel").replace(A);
new ajaxSuggest(B.down("input[type=text]"),{script:"${xwiki.getURL('Main.WebHome', 'view', 'xpage=suggest&classname=XWiki.TagClass&fieldname=tags&firCol=-&secCol=-')}&",varname:"input",seps:"${xwiki.getDocument('XWiki.TagClass').xWikiClass.tags.getProperty('separators').value}",shownoresults:false})
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.viewers.Tags()
});