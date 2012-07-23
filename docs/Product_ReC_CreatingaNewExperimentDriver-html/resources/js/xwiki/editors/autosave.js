if(typeof (XWiki)=="undefined"||typeof (XWiki.actionButtons)=="undefined"||typeof (XWiki.actionButtons.AjaxSaveAndContinue)=="undefined"){if(typeof console!="undefined"&&typeof console.warn=="function"){console.warn("[Autosave feature] Required class missing: XWiki.actionButtons.AjaxSaveAndContinue")
}}else{if(typeof (XWiki.editors)=="undefined"){XWiki.editors=new Object()
}XWiki.editors.AutoSave=Class.create({enabled:false,frequency:5,disabledOpacity:0.2,initialize:function(){if(!(this.form=$("xwikieditcontent"))||!(this.form=this.form.up("form"))){return 
}this.initVersionMetadataElements();
this.createUIElements();
this.addListeners();
if(this.enabled){this.startTimer()
}},initVersionMetadataElements:function(){var A=new Element("div",{"class":"hidden"});
this.editComment=this.form.comment;
if(!this.editComment){this.editComment=new Element("input",{type:"hidden",name:"comment"});
this.customMetadataElementsContainer=A;
A.insert(this.editComment)
}this.minorEditCheckbox=this.form.minorEdit;
if(!this.minorEditCheckbox){this.minorEditCheckbox=new Element("input",{type:"checkbox",name:"minorEdit",checked:true});
this.customMetadataElementsContainer=A;
A.insert(this.minorEditCheckbox)
}},createUIElements:function(){this.autosaveCheckbox=new Element("input",{type:"checkbox",checked:this.enabled,name:"doAutosave",id:"doAutosave"});
this.autosaveInput=new Element("input",{type:"text",value:this.frequency,size:"2","class":"autosave-frequency"});
var B=new Element("label",{"class":"autosave","for":"doAutosave"});
B.appendChild(this.autosaveCheckbox);
B.appendChild(document.createTextNode(" Autosave"));
var C=new Element("label",{"class":"frequency"});
C.appendChild(document.createTextNode("every "));
C.appendChild(this.autosaveInput);
this.timeUnit=new Element("span");
this.setTimeUnit();
C.appendChild(document.createTextNode(" "));
C.appendChild(this.timeUnit);
if(!this.enabled){C.setOpacity(this.disabledOpacity)
}var A=new Element("div",{id:"autosaveControl"});
A.appendChild(B);
A.appendChild(document.createTextNode(" "));
A.appendChild(C);
A.appendChild(document.createTextNode(" "));
$(document.body).down(".bottombuttons .buttons").insert({bottom:A});
this.form.observe("submit",function(){A.remove()
});
document.observe("xwiki:actions:cancel",function(){A.remove()
})
},addListeners:function(){var A=function(B){if(B.keyCode==Event.KEY_RETURN){B.stop();
B.element().blur()
}};
["keydown","keyup","keypress"].each(function(B){this.autosaveInput.observe(B,A);
this.autosaveCheckbox.observe(B,A)
}.bind(this));
Event.observe(this.autosaveCheckbox,"click",function(){this.enabled=this.autosaveCheckbox.checked;
if(this.enabled){this.startTimer();
this.autosaveInput.up("label").setOpacity("1.0")
}else{this.stopTimer();
this.autosaveInput.up("label").setOpacity(this.disabledOpacity)
}}.bindAsEventListener(this));
Event.observe(this.autosaveInput,"blur",function(){var B=new Number(this.autosaveInput.value);
if(B>0){this.frequency=B;
this.setTimeUnit();
this.restartTimer()
}else{this.autosaveInput.value=this.frequency
}this.autosaveInput.removeClassName("focused")
}.bindAsEventListener(this));
Event.observe(this.autosaveInput,"focus",function(){this.autosaveInput.addClassName("focused")
}.bindAsEventListener(this))
},setTimeUnit:function(){if(this.frequency==1){this.timeUnit.update("minute")
}else{this.timeUnit.update("minutes")
}},startTimer:function(){this.timer=new PeriodicalExecuter(this.doAutosave.bind(this),this.frequency*60)
},stopTimer:function(){if(this.timer){this.timer.stop();
delete this.timer
}},restartTimer:function(){this.stopTimer();
this.startTimer()
},doAutosave:function(){this.updateVersionMetadata();
document.fire("xwiki:actions:save",{"continue":true,form:this.editComment.form});
this.resetVersionMetadata()
},updateVersionMetadata:function(){if(this.customMetadataElementsContainer){this.form.insert(this.customMetadataElementsContainer)
}this.userEditComment=this.editComment.value;
this.userMinorEdit=this.minorEditCheckbox.checked;
this.editComment.value+=" (Autosaved)";
this.minorEditCheckbox.checked=true
},resetVersionMetadata:function(){if(this.customMetadataElementsContainer){this.customMetadataElementsContainer.remove()
}this.editComment.value=this.userEditComment;
this.minorEditCheckbox.checked=this.userMinorEdit
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.editors.AutoSave()
})
};