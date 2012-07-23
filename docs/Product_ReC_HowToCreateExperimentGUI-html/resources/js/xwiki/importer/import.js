var XWiki=(function(E){var D=E.importer=E.importer||{};
var B={availableDocuments:"$msg.get('core.importer.availableDocuments')",importHistoryLabel:"$msg.get('core.importer.importHistory')",selectionEmpty:"$msg.get('core.importer.selectionEmptyWarning')","import":"$msg.get('core.importer.import')","package":"$msg.get('core.importer.package')",description:"$msg.get('core.importer.package.description')",version:"$msg.get('core.importer.package.version')",licence:"$msg.get('core.importer.package.licence')",author:"$msg.get('core.importer.package.author')",documentSelected:"$msg.get('core.importer.documentSelected')",whenDocumentAlreadyExists:"$msg.get('core.importer.whenDocumentAlreadyExists')",addNewVersion:"$msg.get('core.importer.addNewVersion')",replaceDocumentHistory:"$msg.get('core.importer.replaceDocumentHistory')",resetHistory:"$msg.get('core.importer.resetHistory')",importAsBackup:"$msg.get('core.importer.importAsBackup')",select:"$msg.get('core.importer.select')",all:"$msg.get('core.importer.selectAll')",none:"$msg.get('core.importer.selectNone')"};
var A="$xwiki.getSkinFile('js/smartclient/skins/Enterprise/images/TreeGrid/opener_closed.png')";
var C="$xwiki.getSkinFile('js/smartclient/skins/Enterprise/images/TreeGrid/opener_opened.png')";
if(!browser.isIE6x){document.observe("dom:loaded",function(){$$("#packagelistcontainer ul.xlist li.xitem a.package").invoke("observe","click",function(H){var F=H.element(),G=F.href.substring(F.href.indexOf("&file=")+6);
H.stop();
$$("div#packagelistcontainer div.active").invoke("removeClassName","active");
H.element().up("div.package").addClassName("active");
new D.PackageExplorer("packagecontainer",decodeURIComponent(G))
})
})
}Element.addMethods("input",{uncheck:function(F){F=$(F);
F.checked=false;
return F
},check:function(F){F=$(F);
F.checked=true;
return F
}});
D.PackageInformationRequest=Class.create({initialize:function(I,H){this.name=decodeURIComponent(I);
this.successCallback=H.onSuccess||function(){};
this.failureCallback=H.onFailure||function(){};
var G=window.docgeturl+"?xpage=packageinfo&package="+I;
var F=new Ajax.Request(G,{onSuccess:this.onSuccess.bindAsEventListener(this),on1223:this.on1223.bindAsEventListener(this),on0:this.on0.bindAsEventListener(this),onFailure:this.onFailure.bind(this)})
},on1223:function(F){F.request.options.onSuccess(F)
},on0:function(F){F.request.options.onFailure(F)
},onSuccess:function(F){this.successCallback(F)
},onFailure:function(F){this.failureCallback(F)
}});
D.PackageExplorer=Class.create({initialize:function(H,G){this.node=$(H);
this.name=G;
this.ignore={};
this.documentCount={};
this.node.addClassName("loading");
var F=new D.PackageInformationRequest(G,{onSuccess:this.onPackageInfosAvailable.bind(this),onFailure:this.onPackageInfosRequestFailed.bind(this)})
},onPackageInfosAvailable:function(I){this.node.removeClassName("loading");
this.node.update();
if(this.node.empty()){this.node.insert(new Element("h4",{"class":"legend"}).update(B.availableDocuments))
}var G=I.responseText.evalJSON();
this.infos=G.infos;
this.packageDocuments=G.files;
this.container=new Element("div",{id:"packageDescription"});
this.node.insert(this.container);
this.container.insert(this.createPackageHeader(G.infos));
var F=new Element("span").update(B.none);
F.observe("click",this.onIgnoreAllDocuments.bind(this));
var H=new Element("span").update(B.all);
H.observe("click",this.onRestoreAllDocuments.bind(this));
this.container.insert(new Element("div",{"class":"selectLinks"}).insert(B.select).insert(F).insert(", ").insert(H));
this.list=new Element("ul",{"class":"xlist package"});
this.container.insert(new Element("div",{id:"package"}).update(this.list));
Object.keys(this.packageDocuments).sort().each(this.addSpaceToPackage.bind(this));
this.container.insert(this.createPackageFormSubmit(G.infos));
this.container.down("div.packagesubmit input[type=radio]").checked=true
},onIgnoreAllDocuments:function(){this.container.select("input[type=checkbox][class=space]").invoke("uncheck");
this.container.select("input[type=checkbox][class=space]").invoke("fire","custom:click")
},onRestoreAllDocuments:function(){this.container.select("input[type=checkbox][class=space]").invoke("check");
this.container.select("input[type=checkbox][class=space]").invoke("fire","custom:click")
},onPackageInfosRequestFailed:function(G){this.node.update();
var F="Failed to retrieve package information. Reason: ";
if(G.statusText==""||response.status==12031){F+="Server not responding"
}else{F+=G.statusText
}this.node.removeClassName("loading");
this.node.update(new Element("div",{"class":"errormessage"}).update(F))
},createPackageFormSubmit:function(K){var J=new Element("div",{"class":"packagesubmit"});
J.insert(new Element("em").update(B.whenDocumentAlreadyExists));
var H=new Element("input",{type:"radio",name:"historyStrategy",checked:"checked",value:"add"});
J.insert(new Element("div",{"class":"historyStrategyOption"}).insert(H).insert(B.addNewVersion));
J.insert(new Element("div",{"class":"historyStrategyOption"}).insert(new Element("input",{type:"radio",name:"historyStrategy",value:"replace"})).insert(B.replaceDocumentHistory));
J.insert(new Element("div",{"class":"historyStrategyOption"}).insert(new Element("input",{type:"radio",name:"historyStrategy",value:"reset"})).insert(B.resetHistory));
if(E.hasProgramming){var F=new Element("input",{type:"checkbox",name:"importAsBackup",value:"true"});
if(K.backup){F.checked=true
}J.insert(new Element("div",{"class":"importOption"}).insert(F).insert(B.importAsBackup))
}var I=new Element("span",{"class":"buttonwrapper"});
var G=new Element("input",{type:"submit",value:B["import"],"class":"button"});
G.observe("click",this.onPackageSubmit.bind(this));
I.insert(G);
J.insert(I);
return J
},onPackageSubmit:function(){if(this.countSelectedDocuments()==0){var L=new Element("span",{"class":"warningmessage"}).update(B.selectionEmpty);
if(!$("packagecontainer").down("div.packagesubmit span.warningmessage")){$("packagecontainer").select("div.packagesubmit input").last().insert({after:L});
Element.remove.delay(5,L)
}return 
}var N={};
N.action="import";
N.name=this.name;
N.historyStrategy=$("packageDescription").down("input[type=radio][value='add']").checked?"add":($("packageDescription").down("input[type=radio][value='replace']").checked?"replace":"reset");
N.ajax="1";
var G=[];
var J=Object.keys(this.packageDocuments);
for(var I=0;
I<J.length;
I++){var F=this.packageDocuments[J[I]];
var K=Object.keys(F);
for(var H=0;
H<K.length;
H++){var M=F[K[H]];
M.each(function(P){if(!this.isIgnored(J[I],K[H],P.language)){var O=P.fullName+":"+P.language;
G.push(O);
N["language_"+O]=P.language
}}.bind(this))
}}N.pages=G;
this.node.update();
this.node.addClassName("loading");
this.node.setStyle("min-height:200px");
new Ajax.Request(window.location,{method:"post",parameters:N,onSuccess:function(O){$("packagecontainer").removeClassName("loading");
$("packagecontainer").update(O.responseText)
},onFailure:function(P){var O="Failed to import documents. Reason: ";
if(P.statusText==""||response.status==12031){O+="Server not responding"
}else{O+=P.statusText
}$("packagecontainer").removeClassName("loading");
$("packagecontainer").update(new Element("div",{"class":"errormessage"}).update(O))
}})
},createPackageHeader:function(G){var F=new Element("div",{"class":"packageinfos"});
F.insert(new Element("div").insert(new Element("span",{"class":"label"}).update(B["package"])).insert(new Element("span",{"class":"filename"}).update(this.name)));
if(G.name!==""){F.insert(new Element("div").insert(new Element("span",{"class":"label"}).update(B.description)).insert(new Element("span",{"class":"name"}).update(G.name)))
}if(G.version!==""){F.insert(new Element("div").insert(new Element("span",{"class":"label"}).update(B.version)).insert(new Element("span",{"class":"version"}).update(G.version)))
}if(G.author!==""){F.insert(new Element("div").insert(new Element("span",{"class":"label"}).update(B.author)).insert(new Element("span",{"class":"author"}).update(G.author)))
}if(G.licence!==""){F.insert(new Element("div").insert(new Element("span",{"class":"label"}).update(B.licence)).insert(new Element("span",{"class":"licence"}).update(G.licence)))
}return F
},addSpaceToPackage:function(F){var N=this.countDocumentsInSpace(F);
var P=N+" / "+N+" "+B.documentSelected;
var H=new Element("li",{"class":"xitem xunderline"});
var I=new Element("div",{"class":"xitemcontainer"});
var L=new Element("input",{type:"checkbox",checked:"checked","class":"space"});
L.observe("click",function(R){L.fire("custom:click",R.memo)
}.bind(this));
L.observe("custom:click",this.spaceCheckboxClicked.bind(this));
I.insert(L);
var J=new Element("img",{src:A});
I.insert(J);
var K=new Element("div",{"class":"spacename"}).update(F);
I.insert(K);
var M=function(R){R.element().up("li").down("div.pages").toggleClassName("hidden");
R.element().up("li").down("img").src=R.element().up("li").down("div.pages").hasClassName("hidden")?A:C
};
J.observe("click",M);
K.observe("click",M);
I.insert(new Element("div",{"class":"selection"}).update(P));
I.insert(new Element("div",{"class":"clearfloats"}));
var G=new Element("div",{"class":"pages hidden"});
var O=new Element("ul",{"class":"xlist pages"});
var Q=this;
Object.keys(this.packageDocuments[F]).sort().each(function(R){Q.addDocumentToSpace(O,F,R)
});
G.update(O);
I.insert(G);
H.insert(I);
this.list.insert(H);
L.checked=true
},addDocumentToSpace:function(J,I,H){var G=this.packageDocuments[I][H],F=this;
G.sortBy(function(K){return K.language
}).each(function(N){var L=new Element("li",{"class":"xitem xhighlight"});
var K=new Element("div",{"class":"xitemcontainer xpagecontainer"});
var M=new Element("input",{type:"checkbox",checked:"checked"});
M.observe("click",F.documentCheckboxClicked.bind(F));
K.insert(new Element("span",{"class":"checkbox"}).update(M));
K.insert(new Element("span",{"class":"documentName"}).update(H));
if(N.language!=""){K.insert(new Element("span",{"class":"documentLanguage"}).update(" - "+N.language))
}K.insert(new Element("div",{"class":"clearfloats"}));
L.insert(new Element("div",{"class":"fullName hidden"}).update(N.fullName));
L.insert(new Element("div",{"class":"language hidden"}).update(N.language));
L.insert(K);
J.insert(L);
M.checked=true
})
},countDocumentsInSpace:function(G){var F=this;
if(typeof this.documentCount[G]=="undefined"){this.documentCount[G]=Object.keys(this.packageDocuments[G]).inject(0,function(I,H){return I+F.packageDocuments[G][H].length
})
}delete F;
return this.documentCount[G]
},countSelectedDocumentsInSpace:function(G){var H;
if(typeof this.ignore[G]=="undefined"){return this.countDocumentsInSpace(G)
}else{var F=this;
return(this.countDocumentsInSpace(G)-Object.keys(this.ignore[G]).inject(0,function(J,I){return J+F.ignore[G][I].length
}))
}},countSelectedDocuments:function(){var F=this;
return Object.keys(this.packageDocuments).inject(0,function(H,G){return H+F.countSelectedDocumentsInSpace(G)
})
},updateSelection:function(F,G){var I=this.countDocumentsInSpace(G);
var H=this.countSelectedDocumentsInSpace(G);
F.down(".selection").update(H+" / "+I+" "+B.documentSelected);
if(H==0){F.down("input.space").uncheck()
}else{F.down("input.space").check()
}},spaceCheckboxClicked:function(I){var H=I.element().checked;
var G=I.element().up(".xitemcontainer").down(".spacename").innerHTML;
var F=I.element().up(".xitemcontainer").down("div.pages");
if(!H){this.ignoreSpace(G);
F.select("input[type='checkbox']").invoke("uncheck")
}else{this.restoreSpace(G);
F.select("input[type='checkbox']").invoke("check")
}this.updateSelection(I.element().up(".xitemcontainer"),G)
},documentCheckboxClicked:function(G){var I=G.element().up("div").down("span.documentName").innerHTML.stripTags().strip();
var H=G.element().up("li").up("div.xitemcontainer").down(".spacename").innerHTML;
var J=G.element().up("li").down(".language").innerHTML;
var F=G.element().checked;
if(!F){this.ignoreDocument(H,I,J)
}else{this.restoreDocument(H,I,J)
}this.updateSelection(G.element().up("li").up("div.xitemcontainer"),H)
},isIgnored:function(G,H,I){if(typeof this.ignore[G]=="undefined"){return false
}if(typeof this.ignore[G][H]=="undefined"){return false
}for(var F=0;
F<this.ignore[G][H].length;
F++){if(this.ignore[G][H][F].language==I){return true
}}return false
},ignoreSpace:function(F){this.ignore[F]=Object.toJSON(this.packageDocuments[F]).evalJSON()
},restoreSpace:function(F){if(typeof this.ignore[F]!="undefined"){delete this.ignore[F]
}},ignoreDocument:function(F,G,H){if(typeof this.ignore[F]=="undefined"){this.ignore[F]=new Object()
}if(typeof this.ignore[F][G]=="undefined"){this.ignore[F][G]=[]
}this.ignore[F][G][this.ignore[F][G].length]={language:H}
},restoreDocument:function(F,H,I){if(typeof this.ignore[F]!="undefined"&&typeof this.ignore[F][H]!="undefined"){for(var G=0;
G<this.ignore[F][H].length;
G++){if(this.ignore[F][H][G].language===I){delete this.ignore[F][H][G];
this.ignore[F][H]=this.ignore[F][H].compact()
}}}}});
return E
})(XWiki||{});