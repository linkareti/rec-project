if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.editors)=="undefined"){XWiki.editors=new Object()
}XWiki.editors.FullScreenEditing=Class.create({margin:0,buttonSize:16,initialize:function(){this.buttons=$(document.body).down(".bottombuttons");
if(!this.buttons){this.buttons=new Element("div",{"class":"bottombuttons"}).update(new Element("div",{"class":"buttons"}));
this.buttons._x_isCustom=true;
document.body.appendChild(this.buttons.hide())
}this.buttonsPlaceholder=new Element("span");
this.toolbarPlaceholder=new Element("span");
this.createCloseButtons();
$$("textarea").each(function(B){this.addBehavior(B)
}.bind(this));
$$(".xRichTextEditor").each(function(B){this.addBehavior(B)
}.bind(this));
this.addWysiwyg20Listener();
this.maximizedReference=$(document.body).down("input[name='x-maximized']");
if(this.maximizedReference&&this.maximizedReference.value!=""){var A=$$(this.maximizedReference.value);
if(A&&A.length>0){this.makeFullScreen(A[0])
}}},addBehavior:function(A){if(this.isWysiwyg20Content(A)){this.addWysiwyg20ContentButton(A)
}else{if(this.isWysiwyg10Content(A)){this.addWysiwyg10ContentButton(A)
}else{if(this.isWikiContent(A)){this.addWikiContentButton(A)
}else{if(this.isWysiwyg20Field(A)){this.addWysiwyg20FieldButton(A)
}else{if(this.isWikiField(A)){this.addWikiFieldButton(A)
}else{if(this.isWysiwyg10Field(A)){this.addWysiwyg10FieldButton(A)
}}}}}}},addWysiwyg20Listener:function(){document.observe("xwiki:wysiwyg:created",this.wysiwyg20Created.bindAsEventListener(this))
},wysiwyg20Created:function(B){var A=$(B.memo.instance.getRichTextArea()).up(".xRichTextEditor");
this.addBehavior(A);
while(true){A=A.up();
if(!A){return 
}else{if(A.previous(".fullScreenEditLinkContainer")){A.previous(".fullScreenEditLinkContainer").remove();
return 
}}}},isWikiContent:function(A){return A.name=="content"&&A.visible()
},isWysiwyg10Content:function(A){return A.name=="content"&&(Prototype.Browser.IE?A.previous(".mceEditorContainer"):A.next(".mceEditorContainer"))
},isWysiwyg20Content:function(A){return A.hasClassName("xRichTextEditor")&&A.up("div[id^=content_container]")
},isWikiField:function(A){return A.visible()
},isWysiwyg10Field:function(A){return !A.visible()&&A.name!="content"&&(Prototype.Browser.IE?A.previous(".mceEditorContainer"):A.next(".mceEditorContainer"))
},isWysiwyg20Field:function(A){return A.hasClassName("xRichTextEditor")&&!A.up("div[id^=content_container]")
},addWikiContentButton:function(A){A._toolbar=$(document.body).down(".leftmenu2");
if(A._toolbar){A._toolbar.insert({top:this.createOpenButton(A)})
}else{this.addWikiFieldButton(A)
}},addWysiwyg10ContentButton:function(E){var B=(Prototype.Browser.IE?E.previous(".mceEditorContainer"):E.next(".mceEditorContainer"));
if(!B){return false
}var D=B.down(".mceToolbar");
if(!D){return false
}var A=new Element("span",{"class":"mce_editor_fullscreentoolbar"});
var C=new Element("a",{"class":"mceButtonNormal"});
A.insert(new Element("img",{"class":"mceSeparatorLine",height:15,width:1,src:D.down("img.mceSeparatorLine").src}));
A.insert(C.insert(this.createOpenButton(B)));
D.insert(A);
B._toolbar=D;
return true
},addWysiwyg20ContentButton:function(B){var A=B.down(".gwt-MenuBar");
if(!A){if(!B._x_fullScreenLoader){B._x_fullScreenLoader_iterations=0;
B._x_fullScreenLoader=new PeriodicalExecuter(function(C){if(C._x_fullScreenLoader_iteration>100){C._x_fullScreenLoader.stop();
C._x_fullScreenLoader=false;
return 
}C._x_fullScreenLoader_iteration++;
this.addWysiwyg20ContentButton(C)
}.bind(this,B),0.2)
}return false
}A.insert({top:this.createOpenButton(B)});
B._toolbar=A;
if(B._x_fullScreenLoader){B._x_fullScreenLoader.stop();
B._x_fullScreenLoader=false
}return true
},addWikiFieldButton:function(A){Element.insert(A,{before:this.createOpenLink(A)})
},addWysiwyg10FieldButton:function(A){this.addWysiwyg10ContentButton(A)
},addWysiwyg20FieldButton:function(A){this.addWysiwyg20ContentButton(A)
},createOpenButton:function(B){var A=new Element("img",{"class":"fullScreenEditButton",title:"$msg.get('core.editors.fullscreen.editFullScreen')",alt:"$msg.get('core.editors.fullscreen.editFullScreen')",src:"$xwiki.getSkinFile('icons/silk/arrow_out.gif')"});
A.observe("click",this.makeFullScreen.bind(this,B));
A.observe("mousedown",this.preventDrag.bindAsEventListener(this));
B._x_fullScreenActivator=A;
A._x_maximizedElement=B;
return A
},createOpenLink:function(B){var C=new Element("div",{"class":"fullScreenEditLinkContainer"});
var A=new Element("a",{"class":"fullScreenEditLink",title:"$msg.get('core.editors.fullscreen.editFullScreen')"});
A.update("${msg.get('core.editors.fullscreen.editFullScreen')} &raquo;");
A.observe("click",this.makeFullScreen.bind(this,B));
C.update(A);
B._x_fullScreenActivator=A;
A._x_maximizedElement=B;
return C
},createCloseButtons:function(){this.closeButton=new Element("img",{"class":"fullScreenCloseButton",title:"$msg.get('core.editors.fullscreen.exitFullScreen')",alt:"$msg.get('core.editors.fullscreen.exitFullScreen')",src:"$xwiki.getSkinFile('icons/silk/arrow_in.gif')"});
this.closeButton.observe("click",this.closeFullScreen.bind(this));
this.closeButton.observe("mousedown",this.preventDrag.bindAsEventListener(this));
this.closeButton.hide();
this.actionCloseButton=new Element("input",{type:"button","class":"button",value:"$msg.get('core.editors.fullscreen.exitFullScreen')"});
this.actionCloseButtonWrapper=new Element("span",{"class":"buttonwrapper"});
this.actionCloseButtonWrapper.update(this.actionCloseButton);
this.actionCloseButton.observe("click",this.closeFullScreen.bind(this));
this.actionCloseButtonWrapper.hide();
this.buttons.down(".buttons").insert({top:this.actionCloseButtonWrapper})
},makeFullScreen:function(B){if(this.maximizedReference){if(B.id){this.maximizedReference.value=B.tagName+"[id='"+B.id+"']"
}else{if(B.name){this.maximizedReference.value=B.tagName+"[name='"+B.name+"']"
}else{if(B.className){this.maximizedReference.value=B.tagName+"."+B.className
}}}}this.maximized=B;
B._originalStyle={width:B.style.width,height:B.style.height};
if(B.hasClassName("xRichTextEditor")){var E=B.down(".gwt-RichTextArea");
E._originalStyle={width:E.style.width,height:E.style.height}
}else{if(B.hasClassName("mceEditorContainer")){var E=B.down(".mceEditorIframe");
E._originalStyle={width:E.style.width,height:E.style.height};
var D=B.down(".mceEditorSource");
D._originalStyle={width:D.style.width,height:D.style.height}
}}var F=B.up();
F.addClassName("fullScreenWrapper");
if(B._toolbar){if(B._toolbar.hasClassName("leftmenu2")){F.insert({top:B._toolbar.replace(this.toolbarPlaceholder)})
}B._x_fullScreenActivator.replace(this.closeButton)
}F.insert(this.buttons.replace(this.buttonsPlaceholder).show());
var C=B.up();
B._x_fullScreenActivator.hide();
while(C!=document.body){C._originalStyle={overflow:C.style.overflow,position:C.style.position,width:C.style.width,height:C.style.height,left:C.style.left,right:C.style.right,top:C.style.top,bottom:C.style.bottom,padding:C.style.padding,margin:C.style.margin};
C.setStyle({overflow:"visible",position:"absolute",width:"100%",height:"100%",left:0,top:0,right:0,bottom:0,padding:0,margin:0});
C.siblings().each(function(G){G._originalDisplay=G.style.display;
G.setStyle({display:"none"})
});
C=C.up()
}document.body._originalStyle={overflow:C.style.overflow,width:C.style.width,height:C.style.height};
var A=$(document.body).up();
A._originalStyle={overflow:A.style.overflow,width:A.style.width,height:A.style.height};
$(document.body).setStyle({overflow:"hidden",width:"100%",height:"100%"});
A.setStyle({overflow:"hidden",width:"100%",height:"100%"});
this.resizeListener=this.resizeTextArea.bind(this,B);
Event.observe(window,"resize",this.resizeListener);
this.closeButton.show();
this.actionCloseButtonWrapper.show();
this.resizeTextArea(B);
if(B._toolbar){B._toolbar.viewportOffset()
}document.fire("xwiki:fullscreen:entered",{target:B})
},closeFullScreen:function(){var B=this.maximized;
this.closeButton.hide();
this.actionCloseButtonWrapper.hide();
Event.stopObserving(window,"resize",this.resizeListener);
B.up().removeClassName("fullScreenWrapper");
if(B.hasClassName("xRichTextEditor")){var F=B.down(".gwt-RichTextArea");
F.setStyle(F._originalStyle)
}else{if(B.hasClassName("mceEditorContainer")){var F=B.down(".mceEditorIframe");
F.setStyle(F._originalStyle);
var E=B.down(".mceEditorSource");
E.setStyle(E._originalStyle)
}}var D=B.up();
var A=[];
while(D!=document.body){A.push(D);
D=D.up()
}var C=A.length;
while(C--){D=A[C];
D.setStyle(D._originalStyle);
D.siblings().each(function(G){G.style.display=G._originalDisplay
})
}document.body.setStyle(document.body._originalStyle);
$(document.body).up().setStyle($(document.body).up()._originalStyle);
this.buttonsPlaceholder.replace(this.buttons);
if(this.buttons._x_isCustom){this.buttons.hide()
}if(B._toolbar){if(B._toolbar.hasClassName("leftmenu2")){this.toolbarPlaceholder.replace(B._toolbar)
}this.closeButton.replace(B._x_fullScreenActivator)
}if(Prototype.Browser.IE){setTimeout(function(){B._x_fullScreenActivator.show();
this.setStyle(this._originalStyle)
}.bind(B),500)
}else{B._x_fullScreenActivator.show();
B.setStyle(B._originalStyle)
}delete this.maximized;
if(this.maximizedReference){this.maximizedReference.value=""
}document.fire("xwiki:fullscreen:exited",{target:B})
},resizeTextArea:function(B){if(!this.maximized){return 
}var A=document.viewport.getHeight();
var C=document.viewport.getWidth();
if(C<=0){C=document.body.clientWidth;
A=document.body.clientHeight
}C=C-this.margin;
A=A-B.positionedOffset().top-this.margin-this.buttons.getHeight();
B.setStyle({width:C+"px",height:A+"px"});
if(B.hasClassName("xRichTextEditor")){B.down(".gwt-RichTextArea").setStyle({width:C+"px",height:A-B.down(".xToolbar").getHeight()-B.down(".gwt-MenuBar").getHeight()+"px"})
}else{if(B.hasClassName("mceEditorContainer")){B.down(".mceEditorIframe").setStyle({width:C+"px",height:A-B._toolbar.getHeight()+"px"});
B.down(".mceEditorSource").setStyle({width:C+"px",height:A-B._toolbar.getHeight()+"px"})
}}document.fire("xwiki:fullscreen:resized",{target:B})
},preventDrag:function(A){A.stop()
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.editors.FullScreenEditing()
});