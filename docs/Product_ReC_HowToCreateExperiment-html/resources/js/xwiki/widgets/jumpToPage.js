if(typeof (XWiki)=="undefined"||typeof (XWiki.widgets)=="undefined"||typeof (XWiki.widgets.ModalPopup)=="undefined"){if(typeof console!="undefined"&&typeof console.warn=="function"){console.warn("[JumpToPage widget] Required class missing: XWiki.widgets.ModalPopup")
}}else{XWiki.widgets.JumpToPage=Class.create(XWiki.widgets.ModalPopup,{urlTemplate:"$xwiki.getURL('__space__.__document__', '__action__')",initialize:function($super){var B=new Element("div");
this.input=new Element("input",{type:"text",id:"jmp_target",title:"Space.Document"});
B.appendChild(this.input);
this.viewButton=this.createButton("button","View","View page (Enter, Meta+V)","jmp_view");
this.editButton=this.createButton("button","Edit","Edit page in the default editor (Meta+E)","jmp_edit");
var A=new Element("div",{"class":"buttons"});
A.appendChild(this.viewButton);
A.appendChild(this.editButton);
B.appendChild(A);
$super(B,{show:{method:this.showDialog,keys:["Meta+G","Ctrl+G","Ctrl+/","Meta+/"]},view:{method:this.openDocument,keys:["Enter","Meta+V","Ctrl+V"]},edit:{method:this.openDocument,keys:["Meta+E","Ctrl+E"]}},{title:"Go to:",verticalPosition:"top"});
this.addQuickLinksEntry()
},createDialog:function($super,A){Event.observe(this.viewButton,"click",this.openDocument.bindAsEventListener(this,"view"));
Event.observe(this.editButton,"click",this.openDocument.bindAsEventListener(this,"edit"));
$super(A);
if(typeof (XWiki.widgets.Suggest)!="undefined"){new XWiki.widgets.Suggest(this.input,{script:"${request.contextPath}/rest/wikis/${context.database}/search?scope=name&number=10&media=json&",varname:"q",noresults:"Document not found",json:true,resultsParameter:"searchResults",resultId:"id",resultValue:"pageFullName",resultInfo:"pageFullName",timeout:30000,parentContainer:this.dialog})
}},showDialog:function($super){$super();
this.input.value="";
this.input.focus()
},openDocument:function(A,B){if(!$("as_jmp_target")&&this.input.value!=""){Event.stop(A);
window.self.location=this.urlTemplate.replace("__space__/__document__",this.input.value.replace(".","/")).replace("__action__",B)
}},addQuickLinksEntry:function(){$$(".panel.QuickLinks .xwikipanelcontents").each(function(B){var A=new Element("span",{"class":"jmp-activator"});
A.update("Jump to any page in the wiki (Meta+G)");
Event.observe(A,"click",function(C){this.showDialog(C)
}.bindAsEventListener(this));
B.appendChild(A)
}.bind(this))
}});
document.observe("xwiki:dom:loaded",function(){new XWiki.widgets.JumpToPage()
})
};