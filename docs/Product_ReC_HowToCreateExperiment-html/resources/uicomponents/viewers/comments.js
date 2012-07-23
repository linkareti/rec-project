if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.viewers)=="undefined"){XWiki.viewers=new Object()
}XWiki.viewers.Comments=Class.create({xcommentSelector:".xwikicomment",initialize:function(){if($("commentscontent")){this.startup()
}if($("Commentstab")){this.container=$("Commentspane");
this.generatorTemplate="commentsinline.vm"
}else{if($$(".main.layoutsubsection").size()>0&&$$(".main.layoutsubsection").first().down("#commentscontent")){this.container=$$(".main.layoutsubsection").first();
this.generatorTemplate="comments.vm"
}}this.addTabLoadListener()
},startup:function(){if($("commentform")){this.form=$("commentform").up("form")
}else{this.form=undefined
}this.loadIDs();
this.addDeleteListener();
this.addReplyListener();
this.addSubmitListener(this.form);
this.addCancelListener();
this.addEditListener();
this.addPreview(this.form)
},loadIDs:function(){$$(this.xcommentSelector).each(function(B){var A=B.id;
B._x_number=A.substring(A.lastIndexOf("_")+1)-0
})
},addDeleteListener:function(){$$(this.xcommentSelector).each(function(A){A=A.down("a.delete");
if(!A){return 
}A.observe("click",function(B){A.blur();
B.stop();
if(A.disabled){return 
}else{new XWiki.widgets.ConfirmedAjaxRequest(A.readAttribute("href")+(Prototype.Browser.Opera?"":"&ajax=1"),{onCreate:function(){A.disabled=true
},onSuccess:function(){var C=A.up(this.xcommentSelector);
if(this.form&&this.form.descendantOf(C.next(".commentthread"))){this.resetForm()
}C.replace(this.createNotification("$msg.get('core.viewers.comments.commentDeleted')"));
this.updateCount()
}.bind(this),onComplete:function(){A.disabled=false
}},{confirmationText:"$msg.get('core.viewers.comments.delete.confirm')",progressMessageText:"$msg.get('core.viewers.comments.delete.inProgress')",successMessageText:"$msg.get('core.viewers.comments.delete.done')",failureMessageText:"$msg.get('core.viewers.comments.delete.failed')"})
}}.bindAsEventListener(this))
}.bind(this))
},addEditListener:function(){$$(this.xcommentSelector).each(function(A){A=A.down("a.edit");
if(!A){return 
}A.observe("click",function(B){A.blur();
B.stop();
if(A.disabled){return 
}else{if(A._x_editForm){var C=A.up(this.xcommentSelector);
C.hide();
A._x_editForm.show()
}else{new Ajax.Request(A.readAttribute("href").replace("viewer=comments","xpage=xpart&vm=commentsinline.vm"),{onCreate:function(){A.disabled=true;
A._x_notification=new XWiki.widgets.Notification("$msg.get('core.viewers.comments.editForm.fetch.inProgress')","inprogress")
},onSuccess:function(D){if(this.editing){this.cancelEdit(false,this.editing)
}var E=A.up(this.xcommentSelector);
E.insert({before:D.responseText});
A._x_editForm=E.previous();
this.addSubmitListener(A._x_editForm);
this.addPreview(A._x_editForm);
A._x_editForm.down("a.cancel").observe("click",this.cancelEdit.bindAsEventListener(this,A));
E.hide();
A._x_notification.hide();
this.editing=A
}.bind(this),onFailure:function(D){var E=D.statusText;
if(D.statusText==""||D.status==12031){E="Server not responding"
}A._x_notification.replace(new XWiki.widgets.Notification("$msg.get('core.viewers.comments.editForm.fetch.failed')"+E,"error"))
}.bind(this),on0:function(D){D.request.options.onFailure(D)
},onComplete:function(){A.disabled=false
}})
}}}.bindAsEventListener(this))
}.bind(this))
},cancelEdit:function(B,A){if(B){B.stop()
}var C=A.up(this.xcommentSelector);
A._x_editForm.hide();
C.show();
this.cancelPreview(A._x_editForm);
this.editing=false
},addReplyListener:function(){if(this.form){$$(this.xcommentSelector).each(function(A){A=A.down("a.commentreply");
if(!A){return 
}A.observe("click",function(B){A.blur();
B.stop();
if(this.form.up(".commentthread")){this.form.up(".commentthread").previous(this.xcommentSelector).down("a.commentreply").show()
}A.up(this.xcommentSelector).next(".commentthread").insert({top:this.form});
this.form["XWiki.XWikiComments_replyto"].value=A.up(this.xcommentSelector)._x_number;
this.form["XWiki.XWikiComments_comment"].value="";
this.form["XWiki.XWikiComments_comment"].focus();
A.hide()
}.bindAsEventListener(this))
}.bind(this))
}else{$$(this.xcommentSelector+" a.commentreply").each(function(A){A.hide()
})
}},addSubmitListener:function(A){if(A){A.down("input[type='submit']").observe("click",function(C){C.stop();
if(A.down("textarea").value!=""){var D=new Hash(A.serialize(true));
D.set("xredirect",window.docgeturl+"?xpage=xpart&vm="+this.generatorTemplate);
D.set("xpage","xpart");
D.set("vm",this.generatorTemplate);
var B=A.action.replace(/\?.*/,"");
D.unset("action_cancel");
A._x_notification=new XWiki.widgets.Notification("$msg.get('core.viewers.comments.add.inProgress')","inprogress");
A.disable();
this.restartNeeded=false;
new Ajax.Request(B,{method:"post",parameters:D,onSuccess:function(){this.restartNeeded=true;
this.editing=false;
A._x_notification.replace(new XWiki.widgets.Notification("$msg.get('core.viewers.comments.add.done')","done"))
}.bind(this),onFailure:function(E){var F=E.statusText;
if(E.statusText==""||E.status==12031){F="Server not responding"
}A._x_notification.replace(new XWiki.widgets.Notification("$msg.get('core.viewers.comments.add.failed')"+F,"error"))
}.bind(this),on0:function(E){E.request.options.onFailure(E)
},onComplete:function(E){if(this.restartNeeded){this.container.update(E.responseText);
document.fire("xwiki:docextra:loaded",{id:"Comments",element:this.container});
this.updateCount()
}else{A.enable()
}}.bind(this)})
}}.bindAsEventListener(this))
}},addCancelListener:function(){if(this.form){this.initialLocation=new Element("span",{className:"hidden"});
$("_comments").insert(this.initialLocation);
this.form.down("a.cancel").observe("click",this.resetForm.bindAsEventListener(this))
}},addPreview:function(C){if(!C){return 
}var A="$xwiki.getURL('__space__.__page__', 'preview')".replace("__space__",encodeURIComponent($$("meta[name=space]")[0].content)).replace("__page__",encodeURIComponent($$("meta[name=page]")[0].content));
C.commentElt=C.down("textarea");
var B=C.down("input[type=submit]").up("div");
C.previewButton=new Element("span",{"class":"buttonwrapper"}).update(new Element("input",{type:"button","class":"button",value:"$msg.get('core.viewers.comments.preview.button.preview')"}));
C.previewButton._x_modePreview=false;
C.previewContent=new Element("div",{"class":"commentcontent commentPreview"});
C.commentElt.insert({before:C.previewContent});
C.previewContent.hide();
B.insert({top:C.previewButton});
C.previewButton.observe("click",function(){if(!C.previewButton._x_modePreview&&!C.previewButton.disabled){C.previewButton.disabled=true;
var D=new XWiki.widgets.Notification("$msg.get('core.viewers.comments.preview.inProgress')","inprogress");
new Ajax.Request(A,{method:"post",parameters:{xpage:"plain",content:C.commentElt.value},onSuccess:function(E){this.doPreview(E.responseText,C);
D.hide()
}.bind(this),on400:function(E){this.doPreview("&nbsp;",C);
D.hide()
}.bind(this),onFailure:function(E){var F=E.statusText;
if(E.statusText==""||E.status==12031){F="Server not responding"
}D.replace(new XWiki.widgets.Notification("$msg.get('core.viewers.comments.preview.failed')"+F,"error"))
},on0:function(E){E.request.options.onFailure(E)
},onComplete:function(E){C.previewButton.disabled=false
}.bind(this)})
}else{this.cancelPreview(C)
}}.bindAsEventListener(this))
},doPreview:function(B,A){A.previewButton._x_modePreview=true;
A.previewContent.update(B);
A.previewContent.show();
A.commentElt.hide();
A.previewButton.down("input").value="$msg.get('core.viewers.comments.preview.button.back')"
},cancelPreview:function(A){A.previewButton._x_modePreview=false;
A.previewContent.hide();
A.previewContent.update("");
A.commentElt.show();
A.previewButton.down("input").value="$msg.get('core.viewers.comments.preview.button.preview')"
},resetForm:function(A){if(A){A.stop()
}if(this.form.up(".commentthread")){this.form.up(".commentthread").previous(this.xcommentSelector).down("a.commentreply").show();
this.initialLocation.insert({after:this.form})
}this.form["XWiki.XWikiComments_replyto"].value="";
this.form["XWiki.XWikiComments_comment"].value="";
this.cancelPreview(this.form)
},updateCount:function(){if($("Commentstab")&&$("Commentstab").down(".itemCount")){$("Commentstab").down(".itemCount").update("$msg.get('docextra.extranb', ['__number__'])".replace("__number__",$$(this.xcommentSelector).size()))
}if($("commentsshortcut")&&$("commentsshortcut").down(".itemCount")){$("commentsshortcut").down(".itemCount").update("$msg.get('docextra.extranb', ['__number__'])".replace("__number__",$$(this.xcommentSelector).size()))
}},addTabLoadListener:function(A){var B=function(C){if(C.memo.id=="Comments"){this.startup()
}}.bindAsEventListener(this);
document.observe("xwiki:docextra:loaded",B)
},createNotification:function(A){var B=new Element("div",{"class":"notification"});
B.update(A);
return B
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.viewers.Comments()
});