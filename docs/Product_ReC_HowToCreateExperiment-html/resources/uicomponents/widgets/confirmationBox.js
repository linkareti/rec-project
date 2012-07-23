if(typeof (XWiki)=="undefined"||typeof (XWiki.widgets)=="undefined"||typeof (XWiki.widgets.ModalPopup)=="undefined"){if(typeof console!="undefined"&&typeof console.warn=="function"){console.warn("[MessageBox widget] Required class missing: XWiki.widgets.ModalPopup")
}}else{XWiki.widgets.ConfirmationBox=Class.create(XWiki.widgets.ModalPopup,{defaultInteractionParameters:{confirmationText:"$msg.get('core.widgets.confirmationBox.defaultQuestion')",yesButtonText:"$msg.get('core.widgets.confirmationBox.button.yes')",noButtonText:"$msg.get('core.widgets.confirmationBox.button.no')"},initialize:function($super,A,B){this.interactionParameters=Object.extend(Object.clone(this.defaultInteractionParameters),B||{});
$super(this.createContent(this.interactionParameters),{show:{method:this.showDialog,keys:[]},yes:{method:this.onYes,keys:["Enter","Space"]},no:{method:this.onNo,keys:["Esc"]},close:{method:this.closeDialog,keys:[]}},{displayCloseButton:false});
this.showDialog();
this.setClass("confirmation");
this.behavior=A||{}
},createContent:function(E){var B=new Element("div",{"class":"question"}).update(E.confirmationText);
var D=new Element("div",{"class":"buttons"});
var F=this.createButton("button",E.yesButtonText,"(Enter)","");
var A=this.createButton("button",E.noButtonText,"(Esc)","");
D.insert(F);
D.insert(A);
var C=new Element("div");
C.insert(B).insert(D);
Event.observe(F,"click",this.onYes.bindAsEventListener(this));
Event.observe(A,"click",this.onNo.bindAsEventListener(this));
return C
},onYes:function(){this.closeDialog();
if(typeof (this.behavior.onYes)=="function"){this.behavior.onYes()
}this.dialog.remove()
},onNo:function(){this.closeDialog();
if(typeof (this.behavior.onNo)=="function"){this.behavior.onNo()
}this.dialog.remove()
}})
};