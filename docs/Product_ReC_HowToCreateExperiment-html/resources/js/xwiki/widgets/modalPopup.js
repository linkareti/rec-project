if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.widgets)=="undefined"){XWiki.widgets=new Object()
}XWiki.widgets.ModalPopup=Class.create({options:{title:"",displayCloseButton:true,screenColor:"",borderColor:"",titleColor:"",backgroundColor:"",screenOpacity:"0.5",verticalPosition:"center",horizontalPosition:"center"},initialize:function(C,A,B){this.shortcuts={show:{method:this.showDialog,keys:["Ctrl+G","Meta+G"]},close:{method:this.closeDialog,keys:["Esc"]}},this.content=C||"Hello world!";
this.shortcuts=Object.extend(Object.clone(this.shortcuts),A||{});
this.options=Object.extend(Object.clone(this.options),B||{});
this.registerShortcuts("show")
},createDialog:function(C){this.dialog=new Element("div",{"class":"xdialog-modal-container"});
var B=new Element("div",{"class":"xdialog-screen"}).setStyle({opacity:this.options.screenOpacity,backgroundColor:this.options.screenColor});
this.dialog.update(B);
this.dialogBox=new Element("div",{"class":"xdialog-box"});
this.dialogBox._x_contentPlug=new Element("div");
this.dialogBox.update(this.dialogBox._x_contentPlug);
this.dialogBox._x_contentPlug.update(this.content);
if(this.options.title){var D=new Element("div",{"class":"xdialog-title"}).update(this.options.title);
D.setStyle({color:this.options.titleColor});
this.dialogBox.insertBefore(D,this.dialogBox.firstChild)
}if(this.options.displayCloseButton){var A=new Element("div",{"class":"xdialog-close",title:"Close"}).update("X");
A.setStyle({color:this.options.titleColor});
A.observe("click",this.closeDialog.bindAsEventListener(this));
this.dialogBox.insertBefore(A,this.dialogBox.firstChild)
}this.dialog.appendChild(this.dialogBox);
this.dialogBox.setStyle({textAlign:"left",borderColor:this.options.borderColor,backgroundColor:this.options.backgroundColor});
switch(this.options.verticalPosition){case"top":this.dialogBox.setStyle({top:"0"});
break;
case"bottom":this.dialogBox.setStyle({bottom:"0"});
break;
default:this.dialogBox.setStyle({top:"35%"});
break
}switch(this.options.horizontalPosition){case"left":this.dialog.setStyle({textAlign:"left"});
break;
case"right":this.dialog.setStyle({textAlign:"right"});
break;
default:this.dialog.setStyle({textAlign:"center"});
this.dialogBox.setStyle({margin:"auto"});
break
}document.body.appendChild(this.dialog);
this.dialog.hide()
},setClass:function(A){this.dialogBox.addClassName("xdialog-box-"+A)
},removeClass:function(A){this.dialogBox.removeClassName("xdialog-box-"+A)
},setContent:function(A){this.content=A;
this.dialogBox._x_contentPlug.update(this.content)
},showDialog:function(A){if(A){Event.stop(A)
}if(!XWiki.widgets.ModalPopup.active){XWiki.widgets.ModalPopup.active=true;
if(!this.dialog){this.createDialog()
}this.attachKeyListeners();
if(window.browser.isIE6x){this.dialog.setStyle({top:document.viewport.getScrollOffsets().top+"px"});
this.dialog._x_scrollListener=this.onScroll.bindAsEventListener(this);
Event.observe(window,"scroll",this.dialog._x_scrollListener);
$$("select").each(function(B){B._x_initiallyVisible=B.style.visibility;
B.style.visibility="hidden"
})
}this.dialog.show()
}},onScroll:function(A){this.dialog.setStyle({top:document.viewport.getScrollOffsets().top+"px"})
},closeDialog:function(A){if(A){Event.stop(A)
}if(window.browser.isIE6x){Event.stopObserving(window,"scroll",this.dialog._x_scrollListener);
$$("select").each(function(B){B.style.visibility=B._x_initiallyVisible
})
}this.dialog.hide();
this.detachKeyListeners();
XWiki.widgets.ModalPopup.active=false
},attachKeyListeners:function(){for(var A in this.shortcuts){if(A!="show"){this.registerShortcuts(A)
}}},detachKeyListeners:function(){for(var A in this.shortcuts){if(A!="show"){this.unregisterShortcuts(A)
}}},registerShortcuts:function(C){var A=this.shortcuts[C].keys;
var D=this.shortcuts[C].method;
for(var B=0;
B<A.size();
++B){if(Prototype.Browser.IE||Prototype.Browser.WebKit){shortcut.add(A[B],D.bindAsEventListener(this,C),{type:"keyup"})
}else{shortcut.add(A[B],D.bindAsEventListener(this,C),{type:"keypress"})
}}},unregisterShortcuts:function(B){for(var A=0;
A<this.shortcuts[B].keys.size();
++A){shortcut.remove(this.shortcuts[B].keys[A])
}},createButton:function(B,D,C,F){var E=new Element("span",{"class":"buttonwrapper"});
var A=new Element("input",{type:B,"class":"button",value:D,title:C,id:F});
E.update(A);
return E
}});
XWiki.widgets.ModalPopup.active=false;