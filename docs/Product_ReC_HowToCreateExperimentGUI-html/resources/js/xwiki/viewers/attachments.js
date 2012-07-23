if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.viewers)=="undefined"){XWiki.viewers=new Object()
}XWiki.viewers.Attachments=Class.create({counter:1,initialize:function(){if($("attachform")){this.addDeleteListener();
this.prepareForm()
}else{this.addTabLoadListener()
}},addDeleteListener:function(){$$(".attachment a.deletelink").each(function(A){A.observe("click",function(B){A.blur();
B.stop();
if(A.disabled){return 
}else{new XWiki.widgets.ConfirmedAjaxRequest(A.readAttribute("href")+(Prototype.Browser.Opera?"":"&ajax=1"),{onCreate:function(){A.disabled=true
},onSuccess:function(){A.up(".attachment").remove();
this.updateCount()
}.bind(this),onComplete:function(){A.disabled=false
}},{confirmationText:"$msg.get('core.viewers.attachments.delete.confirm')",progressMessageText:"$msg.get('core.viewers.attachments.delete.inProgress')",successMessageText:"$msg.get('core.viewers.attachments.delete.done')",failureMessageText:"$msg.get('core.viewers.attachments.delete.failed')"})
}}.bindAsEventListener(this))
}.bind(this))
},updateCount:function(){if($("Attachmentstab")&&$("Attachmentstab").down(".itemCount")){$("Attachmentstab").down(".itemCount").update("$msg.get('docextra.extranb', ['__number__'])".replace("__number__",$("Attachmentspane").select(".attachment").size()))
}if($("attachmentsshortcut")&&$("attachmentsshortcut").down(".itemCount")){$("attachmentsshortcut").down(".itemCount").update("$msg.get('docextra.extranb', ['__number__'])".replace("__number__",$("Attachmentspane").select(".attachment").size()))
}},prepareForm:function(){this.form=$("attachform").up("form");
this.defaultFileDiv=this.form.down("input[type='file']").up("div");
this.inputSize=this.form.down("input[type='file']").size;
this.addInitialRemoveButton();
this.addAddButton();
this.blockEmptySubmit();
this.resetOnCancel()
},addInitialRemoveButton:function(){this.defaultFileDiv.appendChild(this.createRemoveButton())
},addAddButton:function(){var A=new Element("input",{type:"button",value:"$msg.get('core.viewers.attachments.upload.addFileInput')",className:"attachmentActionButton add-file-input"});
this.addDiv=new Element("div");
this.addDiv.appendChild(A);
Event.observe(A,"click",this.addField.bindAsEventListener(this));
this.defaultFileDiv.up().insertBefore(this.addDiv,this.defaultFileDiv.next())
},blockEmptySubmit:function(){Event.observe(this.form,"submit",this.onSubmit.bindAsEventListener(this))
},resetOnCancel:function(){Event.observe(this.form,"reset",this.onReset.bindAsEventListener(this));
Event.observe(this.form.down(".cancel"),"click",this.onReset.bindAsEventListener(this))
},addField:function(D){var C=new Element("input",{type:"file",name:"filepath_"+this.counter,size:this.inputSize,className:"uploadFileInput"});
var A=new Element("input",{type:"hidden",name:"filename_"+this.counter});
var B=this.createRemoveButton();
var E=new Element("div");
E.insert(A).insert(C).insert(B);
this.addDiv.parentNode.insertBefore(E,this.addDiv);
D.element().blur();
this.counter++
},removeField:function(A){A.element().up("div").remove()
},createRemoveButton:function(){var A=new Element("input",{type:"button",value:"$msg.get('core.viewers.attachments.upload.removeFileInput')",title:"$msg.get('core.viewers.attachments.upload.removeFileInput.title')",className:"attachmentActionButton remove-file-input"});
Event.observe(A,"click",this.removeField.bindAsEventListener(this));
return A
},onSubmit:function(B){var A=false;
this.form.getInputs("file").each(function(C){if(C.value!=""){A=true
}});
if(!A){B.stop()
}},onReset:function(A){if(A){A.stop()
}this.form.getInputs("file").each(function(B){B.up().remove()
});
this.counter=1;
this.addField(A)
},addTabLoadListener:function(A){var B=function(C){if(C.memo.id=="Attachments"){this.addDeleteListener();
this.prepareForm();
document.stopObserving("xwiki:docextra:loaded",B);
delete B
}}.bindAsEventListener(this);
document.observe("xwiki:docextra:loaded",B)
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.viewers.Attachments()
});