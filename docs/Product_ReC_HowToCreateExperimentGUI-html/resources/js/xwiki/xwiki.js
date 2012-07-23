if(typeof XWiki=="undefined"){var XWiki={}
}Object.extend(XWiki,{constants:{wikiSpaceSeparator:":",spacePageSeparator:".",pageAttachmentSeparator:"@",anchorSeparator:"#",docextraCommentsAnchor:"Comments",docextraAttachmentsAnchor:"Attachments",docextraHistoryAnchor:"History",docextraInformationAnchor:"Information"},resource:{getWikiFromResourceName:function(A){if(A.include(XWiki.constants.wikiSpaceSeparator)){return A.substring(0,A.indexOf(XWiki.constants.wikiSpaceSeparator))
}return null
},getSpaceFromResourceName:function(B){var A=B;
if(B.include(XWiki.constants.wikiSpaceSeparator)){B=B.substring(B.indexOf(XWiki.constants.wikiSpaceSeparator)+1,B.length)
}if(B.include(XWiki.constants.spacePageSeparator)){if(B.include(XWiki.constants.pageAttachmentSeparator)&&B.indexOf(XWiki.constants.spacePageSeparator)>B.indexOf(XWiki.constants.pageAttachmentSeparator)){return null
}return B.substring(0,B.indexOf(XWiki.constants.spacePageSeparator))
}if(A.include(XWiki.constants.wikiSpaceSeparator)&&!A.include(XWiki.constants.pageAttachmentSeparator)&&!A.include(XWiki.constants.anchorSeparator)){return B
}return null
},getNameFromResourceName:function(B){var A=B;
if(B.include(XWiki.constants.wikiSpaceSeparator)){B=B.substring(B.indexOf(XWiki.constants.wikiSpaceSeparator)+1,B.length)
}if(B.include(XWiki.constants.pageAttachmentSeparator)){B=B.substring(0,B.indexOf(XWiki.constants.pageAttachmentSeparator))
}if(B.include(XWiki.constants.anchorSeparator)){B=B.substring(0,B.indexOf(XWiki.constants.anchorSeparator))
}if(B.include(XWiki.constants.spacePageSeparator)){return B.substring(B.indexOf(XWiki.constants.spacePageSeparator)+1,B.length)
}else{if(A.include(XWiki.constants.wikiSpaceSeparator)){return null
}else{return B
}}},getAttachmentFromResourceName:function(A){if(A.include(XWiki.constants.pageAttachmentSeparator)){return A.substring(A.indexOf(XWiki.constants.pageAttachmentSeparator)+1,A.length)
}return null
},getAnchorFromResourceName:function(A){if(A.include(XWiki.constants.anchorSeparator)){return A.substring(A.indexOf(XWiki.constants.anchorSeparator)+1,A.length)
}return null
},get:function(C){var I=this.getWikiFromResourceName(C);
var B=this.getSpaceFromResourceName(C);
var H=this.getNameFromResourceName(C);
var F=this.getAttachmentFromResourceName(C);
var E=this.getAnchorFromResourceName(C);
if(!I){I=XWiki.currentWiki
}if(!B){B=XWiki.currentSpace
}if(!H){H=XWiki.currentPage
}if(!F){F=""
}if(!E){E=""
}var D=B+XWiki.constants.spacePageSeparator+H;
var A=I+XWiki.constants.wikiSpaceSeparator+B;
var G=I+XWiki.constants.wikiSpaceSeparator+D;
return{wiki:I,space:B,prefixedSpace:A,fullName:D,prefixedFullName:G,name:H,attachment:F,anchor:E}
},serialize:function(B){var A="";
if(B.wiki){A=B.wiki
}if(B.space){if(A.length>0){A+=XWiki.constants.wikiSpaceSeparator
}A+=B.space
}if(B.name){if(A.length>0){A+=XWiki.constants.spacePageSeparator
}A+=B.name
}if(B.attachment){if(A.length>0){A+=XWiki.constants.pageAttachmentSeparator
}A+=B.attachment
}if(B.anchor){if(A.length>0){A+=XWiki.constants.anchorSeparator
}A+=B.anchor
}return A
}},getResource:function(A){return this.resource.get(A)
},displayDocExtra:function(A,B,D){var C=function(E){var F=document.getElementById(E+"tab");
var G=document.getElementById(E+"pane");
if(window.activeDocExtraTab!=null){window.activeDocExtraTab.className="";
window.activeDocExtraPane.className="hidden"
}window.activeDocExtraTab=F;
window.activeDocExtraPane=G;
window.activeDocExtraTab.className="active";
window.activeDocExtraPane.className="";
F.blur();
document.fire("xwiki:docextra:activated",{id:E})
};
if($(A+"pane").className.indexOf("empty")!=-1){if(window.activeDocExtraPane!=null){window.activeDocExtraPane.className="invisible"
}$("docextrapanes").className="loading";
new Ajax.Updater(A+"pane",window.docgeturl+"?xpage=xpart&vm="+B,{method:"post",evalScripts:true,onComplete:function(E){$("docextrapanes").className="";
document.fire("xwiki:docextra:loaded",{id:A,element:$(A+"pane")});
C(A);
if(D){$(A+"anchor").id=A;
location.href="#"+A;
$(A).id=A+"anchor"
}}})
}else{C(A);
if(D){$(A+"anchor").id=A;
location.href="#"+A;
$(A).id=A+"anchor"
}}},makeRenderingErrorsExpandable:function(A){if(typeof A=="undefined"){A=document.body
}$(A).select(".xwikirenderingerror").each(function(B){if(B.next().innerHTML!==""&&B.next().hasClassName("xwikirenderingerrordescription")){B.style.cursor="pointer";
B.title="$msg.get('platform.core.rendering.error.readTechnicalInformation')";
Event.observe(B,"click",function(C){C.element().next().toggleClassName("hidden")
})
}})
},fixLinksTargetAttribute:function(F){if(XWiki.contextaction=="view"||XWiki.contextaction=="preview"){if(typeof F=="undefined"){F=document.body
}var E=F.select("a[rel]");
for(var D=0;
D<E.length;
D++){var C=E[D];
if(C.getAttribute("href")&&C.getAttribute("rel")){var A=C.getAttribute("rel").split(" ");
for(var B=0;
B<A.length;
B++){if(A[B].charAt(0)=="_"){C.target=A[B].substring(1);
break
}else{if(A[B]=="external"){C.target="_blank";
break
}}}}}}},insertSectionEditLinks:function(){if(XWiki.docsyntax=="xwiki/2.0"&&XWiki.contextaction=="view"&&XWiki.hasEdit){var D=1;
var A=$("xwikicontent");
if(!A){return 
}A=A.childNodes;
var C=new RegExp("H[1-"+$xwiki.getSectionEditingDepth()+"]");
for(var B=0;
B<A.length;
B++){var F=$(A[B]);
if(C.test(F.nodeName)&&F.className.include("wikigeneratedheader")==false){var E=document.createElement("SPAN");
var G=document.createElement("A");
G.href=window.docediturl+"?section="+D;
G.style.textDecoration="none";
G.innerHTML="$msg.get('edit')";
E.className="edit_section";
E.appendChild(G);
F.insert({after:E});
D++
}}}},insertCreatePageFromTemplateModalBoxes:function(){if(XWiki.docsyntax=="xwiki/2.0"&&XWiki.contextaction=="view"&&XWiki.hasEdit){XWiki.widgets.CreatePagePopup=Class.create(XWiki.widgets.ModalPopup,{initialize:function($super,D){var C=new Element("div",{"class":"modal-popup"});
C.insert(D.content);
$super(C,{show:{method:this.showDialog,keys:[]},close:{method:this.closeDialog,keys:["Esc"]}},{displayCloseButton:true,verticalPosition:"center",backgroundColor:"#FFF"});
this.showDialog();
this.setClass("createpage-modal-popup")
}});
var B=document.body.select("span.wikicreatelink");
for(var A=0;
A<B.length;
A++){B[A].down("a").observe("click",function(C){new Ajax.Request(C.currentTarget.href+"&xpage=createinline&ajax=1",{method:"get",onSuccess:function(E){var D=E.getHeader("redirect");
if(D){window.location=D
}else{new XWiki.widgets.CreatePagePopup({content:E.responseText})
}},onFailure:function(){new XWiki.widgets.Notification("$msg.get('core.create.ajax.error')","error",{inactive:true}).show()
}});
C.stop()
})
}}},watchlist:{actionsMap:{tmWatchDocument:"adddocument",tmUnwatchDocument:"removedocument",tmWatchSpace:"addspace",tmUnwatchSpace:"removespace",tmWatchWiki:"addwiki",tmUnwatchWiki:"removewiki"},flowMap:{tmWatchDocument:"tmUnwatchDocument",tmUnwatchDocument:"tmWatchDocument",tmWatchSpace:"tmUnwatchSpace",tmUnwatchSpace:"tmWatchSpace",tmWatchWiki:"tmUnwatchWiki",tmUnwatchWiki:"tmWatchWiki"},executeAction:function(A){var C=window.docgeturl+"?xpage=watch&do="+this.actionsMap[A.id];
var B=new Ajax.Request(C,{method:"get",onComplete:function(){if(A.nodeName=="A"){A.parentNode.toggleClassName("hidden");
$(XWiki.watchlist.flowMap[A.id]).parentNode.toggleClassName("hidden")
}else{A.toggleClassName("hidden");
$(XWiki.watchlist.flowMap[A.id]).toggleClassName("hidden")
}}})
},initialize:function(){for(button in XWiki.watchlist.actionsMap){if($(button)!=null){var B=$(button);
var A=this;
if(B.nodeName!="A"){B=$(button).firstChild
}B.stopObserving("click");
B.observe("click",function(D){Event.stop(D);
var C=D.element();
while(C.id==""){C=C.parentNode
}XWiki.watchlist.executeAction(C)
})
}}}},initialize:function(){if(typeof this.isInitialized=="undefined"||this.isInitialized==false){this.isInitialized=true;
document.fire("xwiki:dom:loading");
this.makeRenderingErrorsExpandable();
this.fixLinksTargetAttribute();
this.insertSectionEditLinks();
this.insertCreatePageFromTemplateModalBoxes();
this.watchlist.initialize();
document.fire("xwiki:dom:loaded")
}}});
document.observe("dom:loaded",XWiki.initialize.bind(XWiki));
function hideForm(A){A.getElementsByTagName("fieldset").item(0).className="collapsed"
}function toggleForm(B){var A=B.getElementsByTagName("fieldset").item(0);
if(A.className=="collapsed"){A.className="expanded"
}else{A.className="collapsed"
}}function togglePanelVisibility(A,B){A=$(A);
A.toggleClassName("collapsed");
if(B){if(A.hasClassName("collapsed")){createCookie(B,"collapsed","")
}else{eraseCookie(B)
}}}function showsubmenu(A){if(A.lastChild.tagName.toLowerCase()=="span"){if(window.hidetimer){if(window.hideelement==A.lastChild){clearTimeout(window.hidetimer);
window.hidetimer=null;
window.hideelement=null
}else{doHide()
}}var B=Element.positionedOffset(A);
A.lastChild.style.left=(B[0]-10)+"px";
A.lastChild.style.top=(B[1]+A.offsetHeight)+"px";
A.lastChild.className=A.lastChild.className.replace("hidden","visible")
}}function hidesubmenu(A){if(A.lastChild.tagName.toLowerCase()=="span"){window.hideelement=A.lastChild;
window.hidetimer=setTimeout(doHide,100)
}}function doHide(){window.hideelement.className=window.hideelement.className.replace("visible","hidden");
clearTimeout(window.hidetimer);
window.hidetimer=null;
window.hideelement=null
}function toggleClass(B,A){if(!eltHasClass(B,A)){B.className+=" "+A
}else{rmClass(B,A)
}}function addClass(B,A){if(!eltHasClass(B,A)){B.className+=" "+A
}}function eltHasClass(B,A){if(!B.className){return false
}return new RegExp("\\b"+A+"\\b").test(B.className)
}function rmClass(B,A){B.className=B.className.replace(new RegExp("\\s*\\b"+A+"\\b"),"")
}function openURL(A){win=open(A,"win","titlebar=0,width=990,height=500,resizable,scrollbars");
if(win){win.focus()
}}function openHelp(){win=open("http://platform.xwiki.org/xwiki/bin/view/Main/XWikiSyntax?xpage=print","XWikiSyntax","titlebar=0,width=750,height=480,resizable,scrollbars");
if(win){win.focus()
}}function updateName(A,D,C){var B=A.value;
B=noaccent(B);
if(C!=false){B=B.replace(/class$/gi,"")
}if(D==null){A.value=B
}else{D.value=B
}if(B==""){return false
}return true
}function noaccent(A){temp=A.replace(/[\u00c0\u00c1\u00c2\u00c3\u00c4\u00c5\u0100\u0102\u0104\u01cd\u01de\u01e0\u01fa\u0200\u0202\u0226]/g,"A");
temp=temp.replace(/[\u00e0\u00e1\u00e2\u00e3\u00e4\u00e5\u0101\u0103\u0105\u01ce\u01df\u01e1\u01fb\u0201\u0203\u0227]/g,"a");
temp=temp.replace(/[\u00c6\u01e2\u01fc]/g,"AE");
temp=temp.replace(/[\u00e6\u01e3\u01fd]/g,"ae");
temp=temp.replace(/[\u008c\u0152]/g,"OE");
temp=temp.replace(/[\u009c\u0153]/g,"oe");
temp=temp.replace(/[\u00c7\u0106\u0108\u010a\u010c]/g,"C");
temp=temp.replace(/[\u00e7\u0107\u0109\u010b\u010d]/g,"c");
temp=temp.replace(/[\u00d0\u010e\u0110]/g,"D");
temp=temp.replace(/[\u00f0\u010f\u0111]/g,"d");
temp=temp.replace(/[\u00c8\u00c9\u00ca\u00cb\u0112\u0114\u0116\u0118\u011a\u0204\u0206\u0228]/g,"E");
temp=temp.replace(/[\u00e8\u00e9\u00ea\u00eb\u0113\u0115\u0117\u0119\u011b\u01dd\u0205\u0207\u0229]/g,"e");
temp=temp.replace(/[\u011c\u011e\u0120\u0122\u01e4\u01e6\u01f4]/g,"G");
temp=temp.replace(/[\u011d\u011f\u0121\u0123\u01e5\u01e7\u01f5]/g,"g");
temp=temp.replace(/[\u0124\u0126\u021e]/g,"H");
temp=temp.replace(/[\u0125\u0127\u021f]/g,"h");
temp=temp.replace(/[\u00cc\u00cd\u00ce\u00cf\u0128\u012a\u012c\u012e\u0130\u01cf\u0208\u020a]/g,"I");
temp=temp.replace(/[\u00ec\u00ed\u00ee\u00ef\u0129\u012b\u012d\u012f\u0131\u01d0\u0209\u020b]/g,"i");
temp=temp.replace(/[\u0132]/g,"IJ");
temp=temp.replace(/[\u0133]/g,"ij");
temp=temp.replace(/[\u0134]/g,"J");
temp=temp.replace(/[\u0135]/g,"j");
temp=temp.replace(/[\u0136\u01e8]/g,"K");
temp=temp.replace(/[\u0137\u0138\u01e9]/g,"k");
temp=temp.replace(/[\u0139\u013b\u013d\u013f\u0141]/g,"L");
temp=temp.replace(/[\u013a\u013c\u013e\u0140\u0142\u0234]/g,"l");
temp=temp.replace(/[\u00d1\u0143\u0145\u0147\u014a\u01f8]/g,"N");
temp=temp.replace(/[\u00f1\u0144\u0146\u0148\u0149\u014b\u01f9\u0235]/g,"n");
temp=temp.replace(/[\u00d2\u00d3\u00d4\u00d5\u00d6\u00d8\u014c\u014e\u0150\u01d1\u01ea\u01ec\u01fe\u020c\u020e\u022a\u022c\u022e\u0230]/g,"O");
temp=temp.replace(/[\u00f2\u00f3\u00f4\u00f5\u00f6\u00f8\u014d\u014f\u0151\u01d2\u01eb\u01ed\u01ff\u020d\u020f\u022b\u022d\u022f\u0231]/g,"o");
temp=temp.replace(/[\u0156\u0158\u0210\u0212]/g,"R");
temp=temp.replace(/[\u0157\u0159\u0211\u0213]/g,"r");
temp=temp.replace(/[\u015a\u015c\u015e\u0160\u0218]/g,"S");
temp=temp.replace(/[\u015b\u015d\u015f\u0161\u0219]/g,"s");
temp=temp.replace(/[\u00de\u0162\u0164\u0166\u021a]/g,"T");
temp=temp.replace(/[\u00fe\u0163\u0165\u0167\u021b\u0236]/g,"t");
temp=temp.replace(/[\u00d9\u00da\u00db\u00dc\u0168\u016a\u016c\u016e\u0170\u0172\u01d3\u01d5\u01d7\u01d9\u01db\u0214\u0216]/g,"U");
temp=temp.replace(/[\u00f9\u00fa\u00fb\u00fc\u0169\u016b\u016d\u016f\u0171\u0173\u01d4\u01d6\u01d8\u01da\u01dc\u0215\u0217]/g,"u");
temp=temp.replace(/[\u0174]/g,"W");
temp=temp.replace(/[\u0175]/g,"w");
temp=temp.replace(/[\u00dd\u0176\u0178\u0232]/g,"Y");
temp=temp.replace(/[\u00fd\u00ff\u0177\u0233]/g,"y");
temp=temp.replace(/[\u0179\u017b\u017d]/g,"Z");
temp=temp.replace(/[\u017a\u017c\u017e]/g,"z");
temp=temp.replace(/[\u00df]/g,"SS");
temp=temp.replace(/[^a-zA-Z0-9_]/g,"");
return temp
}function prepareName(B){var D=B.register_first_name.value;
var A=B.register_last_name.value;
var C=B.xwikiname;
if(D!=""){D=D.substring(0,1).toUpperCase()+D.substring(1);
D.replace(/ /g,"")
}if(A!=""){A=A.substring(0,1).toUpperCase()+A.substring(1);
A.replace(/ /g,"")
}if(C.value==""){C.value=noaccent(D+A)
}}function createCookie(C,D,E){if(E){var B=new Date();
B.setTime(B.getTime()+(E*24*60*60*1000));
var A="; expires="+B.toGMTString()
}else{var A=""
}document.cookie=C+"="+D+A+"; path=/"
}function readCookie(B){var D=B+"=";
var A=document.cookie.split(";");
for(var C=0;
C<A.length;
C++){var E=A[C];
while(E.charAt(0)==" "){E=E.substring(1,E.length)
}if(E.indexOf(D)==0){return E.substring(D.length,E.length)
}}return null
}function eraseCookie(A){createCookie(A,"",-1)
}function checkAdvancedContent(A){result=false;
if(!document.forms.edit){return true
}data=document.forms.edit.content.value;
myRE=new RegExp("</?(html|body|img|a|i|b|embed|script|form|input|textarea|object|font|li|ul|ol|table|center|hr|br|p) ?([^>]*)>","ig");
results=data.match(myRE);
if(results&&results.length>0){result=true
}myRE2=new RegExp("(#(set|include|if|end|for)|#(#) Advanced content|public class|/* Advanced content */)","ig");
results=data.match(myRE2);
if(results&&results.length>0){result=true
}if(result==true){return confirm(A)
}return true
}shortcut={all_shortcuts:{},add:function(B,H,D){var G={type:"keydown",propagate:false,disable_in_input:false,target:document,keycode:false};
if(!D){D=G
}else{for(var A in G){if(typeof D[A]=="undefined"){D[A]=G[A]
}}}var F=D.target;
if(typeof D.target=="string"){F=document.getElementById(D.target)
}var C=this;
B=B.toLowerCase();
var E=function(N){N=N||window.event;
if(D.disable_in_input){var K;
if(N.target){K=N.target
}else{if(N.srcElement){K=N.srcElement
}}if(K.nodeType==3){K=K.parentNode
}if(K.tagName=="INPUT"||K.tagName=="TEXTAREA"||K.tagName=="SELECT"){return 
}}var I=0;
if(N.keyCode){I=N.keyCode
}else{if(N.which){I=N.which
}}var M=String.fromCharCode(I).toLowerCase();
if(I==188){M=","
}if(I==190){M="."
}var R=B.split("+");
var Q=0;
var O={"`":"~","1":"!","2":"@","3":"#","4":"$","5":"%","6":"^","7":"&","8":"*","9":"(","0":")","-":"_","=":"+",";":":","'":'"',",":"<",".":">","/":"?","\\":"|"};
var L={esc:27,escape:27,tab:9,space:32,"return":13,enter:13,backspace:8,scrolllock:145,scroll_lock:145,scroll:145,capslock:20,caps_lock:20,caps:20,numlock:144,num_lock:144,num:144,pause:19,"break":19,insert:45,home:36,"delete":46,end:35,pageup:33,page_up:33,pu:33,pagedown:34,page_down:34,pd:34,left:37,up:38,right:39,down:40,f1:112,f2:113,f3:114,f4:115,f5:116,f6:117,f7:118,f8:119,f9:120,f10:121,f11:122,f12:123};
var P={shift:{wanted:false,pressed:false},ctrl:{wanted:false,pressed:false},alt:{wanted:false,pressed:false},meta:{wanted:false,pressed:false}};
if(N.ctrlKey){P.ctrl.pressed=true
}if(N.shiftKey){P.shift.pressed=true
}if(N.altKey){P.alt.pressed=true
}if(N.metaKey){P.meta.pressed=true
}for(var J=0;
k=R[J],J<R.length;
J++){if(k=="ctrl"||k=="control"){Q++;
P.ctrl.wanted=true
}else{if(k=="shift"){Q++;
P.shift.wanted=true
}else{if(k=="alt"){Q++;
P.alt.wanted=true
}else{if(k=="meta"){Q++;
P.meta.wanted=true
}else{if(k.length>1){if(L[k]==I){Q++
}}else{if(D.keycode){if(D.keycode==I){Q++
}}else{if(M==k){Q++
}else{if(O[M]&&N.shiftKey){M=O[M];
if(M==k){Q++
}}}}}}}}}}if(Q==R.length&&P.ctrl.pressed==P.ctrl.wanted&&P.shift.pressed==P.shift.wanted&&P.alt.pressed==P.alt.wanted&&P.meta.pressed==P.meta.wanted){H(N);
if(!D.propagate){N.cancelBubble=true;
N.returnValue=false;
if(document.all&&!window.opera&&window.XMLHttpRequest){N.keyCode=0
}if(N.stopPropagation){N.stopPropagation();
N.preventDefault()
}return false
}}};
this.all_shortcuts[B]={callback:E,target:F,event:D.type};
if(F.addEventListener){F.addEventListener(D.type,E,false)
}else{if(F.attachEvent){F.attachEvent("on"+D.type,E)
}else{F["on"+D.type]=E
}}},remove:function(A){A=A.toLowerCase();
var D=this.all_shortcuts[A];
delete (this.all_shortcuts[A]);
if(!D){return 
}var B=D.event;
var C=D.target;
var E=D.callback;
if(C.detachEvent){C.detachEvent("on"+B,E)
}else{if(C.removeEventListener){C.removeEventListener(B,E,false)
}else{C["on"+B]=false
}}}};
function BrowserDetect(){var A=navigator.userAgent.toLowerCase();
this.isGecko=(A.indexOf("gecko")!=-1&&A.indexOf("safari")==-1);
this.isAppleWebKit=(A.indexOf("applewebkit")!=-1);
this.isKonqueror=(A.indexOf("konqueror")!=-1);
this.isSafari=(A.indexOf("safari")!=-1);
this.isOmniweb=(A.indexOf("omniweb")!=-1);
this.isOpera=(A.indexOf("opera")!=-1);
this.isIcab=(A.indexOf("icab")!=-1);
this.isAol=(A.indexOf("aol")!=-1);
this.isIE=(A.indexOf("msie")!=-1&&!this.isOpera&&(A.indexOf("webtv")==-1));
this.isMozilla=(this.isGecko&&A.indexOf("gecko/")+14==A.length);
this.isFirefox=(A.indexOf("firefox/")!=-1||A.indexOf("firebird/")!=-1);
this.isNS=((this.isGecko)?(A.indexOf("netscape")!=-1):((A.indexOf("mozilla")!=-1)&&!this.isOpera&&!this.isSafari&&(A.indexOf("spoofer")==-1)&&(A.indexOf("compatible")==-1)&&(A.indexOf("webtv")==-1)&&(A.indexOf("hotjava")==-1)));
this.isIECompatible=((A.indexOf("msie")!=-1)&&!this.isIE);
this.isNSCompatible=((A.indexOf("mozilla")!=-1)&&!this.isNS&&!this.isMozilla);
this.geckoVersion=((this.isGecko)?A.substring((A.lastIndexOf("gecko/")+6),(A.lastIndexOf("gecko/")+14)):-1);
this.equivalentMozilla=((this.isGecko)?parseFloat(A.substring(A.indexOf("rv:")+3)):-1);
this.appleWebKitVersion=((this.isAppleWebKit)?parseFloat(A.substring(A.indexOf("applewebkit/")+12)):-1);
this.versionMinor=parseFloat(navigator.appVersion);
if(this.isGecko&&!this.isMozilla){this.versionMinor=parseFloat(A.substring(A.indexOf("/",A.indexOf("gecko/")+6)+1))
}else{if(this.isMozilla){this.versionMinor=parseFloat(A.substring(A.indexOf("rv:")+3))
}else{if(this.isIE&&this.versionMinor>=4){this.versionMinor=parseFloat(A.substring(A.indexOf("msie ")+5))
}else{if(this.isKonqueror){this.versionMinor=parseFloat(A.substring(A.indexOf("konqueror/")+10))
}else{if(this.isSafari){this.versionMinor=parseFloat(A.substring(A.lastIndexOf("safari/")+7))
}else{if(this.isOmniweb){this.versionMinor=parseFloat(A.substring(A.lastIndexOf("omniweb/")+8))
}else{if(this.isOpera){this.versionMinor=parseFloat(A.substring(A.indexOf("opera")+6))
}else{if(this.isIcab){this.versionMinor=parseFloat(A.substring(A.indexOf("icab")+5))
}}}}}}}}this.versionMajor=parseInt(this.versionMinor);
this.isDOM1=(document.getElementById);
this.isDOM2Event=(document.addEventListener&&document.removeEventListener);
this.mode=document.compatMode?document.compatMode:"BackCompat";
this.isWin=(A.indexOf("win")!=-1);
this.isWin32=(this.isWin&&(A.indexOf("95")!=-1||A.indexOf("98")!=-1||A.indexOf("nt")!=-1||A.indexOf("win32")!=-1||A.indexOf("32bit")!=-1||A.indexOf("xp")!=-1));
this.isMac=(A.indexOf("mac")!=-1);
this.isUnix=(A.indexOf("unix")!=-1||A.indexOf("sunos")!=-1||A.indexOf("bsd")!=-1||A.indexOf("x11")!=-1);
this.isLinux=(A.indexOf("linux")!=-1);
this.isNS4x=(this.isNS&&this.versionMajor==4);
this.isNS40x=(this.isNS4x&&this.versionMinor<4.5);
this.isNS47x=(this.isNS4x&&this.versionMinor>=4.7);
this.isNS4up=(this.isNS&&this.versionMinor>=4);
this.isNS6x=(this.isNS&&this.versionMajor==6);
this.isNS6up=(this.isNS&&this.versionMajor>=6);
this.isNS7x=(this.isNS&&this.versionMajor==7);
this.isNS7up=(this.isNS&&this.versionMajor>=7);
this.isIE4x=(this.isIE&&this.versionMajor==4);
this.isIE4up=(this.isIE&&this.versionMajor>=4);
this.isIE5x=(this.isIE&&this.versionMajor==5);
this.isIE55=(this.isIE&&this.versionMinor==5.5);
this.isIE5up=(this.isIE&&this.versionMajor>=5);
this.isIE6x=(this.isIE&&this.versionMajor==6);
this.isIE6up=(this.isIE&&this.versionMajor>=6);
this.isIE4xMac=(this.isIE4x&&this.isMac)
}var browser=new BrowserDetect();
XWiki.Document=Class.create({initialize:function(C,B,A){this.page=C||XWiki.Document.currentPage;
this.space=B||XWiki.Document.currentSpace;
this.wiki=A||XWiki.Document.currentWiki
},getURL:function(C,D,B){C=C||"view";
var A=XWiki.Document.URLTemplate;
A=A.replace("__space__",encodeURIComponent(this.space));
A=A.replace("__page__",(this.page=="WebHome")?"":encodeURIComponent(this.page));
A=A.replace("__action__/",(C=="view")?"":(encodeURIComponent(C)+"/"));
if(D){A+="?"+D
}if(B){A+="#"+B
}return A
},getRestURL:function(A,C){A=A||"";
var B=XWiki.Document.RestURLTemplate;
B=B.replace("__wiki__",this.wiki);
B=B.replace("__space__",this.space);
B=B.replace("__page__",this.page);
if(A){B+="/"+A
}if(C){B+="?"+C
}return B
}});
document.observe("xwiki:dom:loading",function(){XWiki.Document.currentWiki=($$("meta[name=wiki]").length>0)?$$("meta[name=wiki]")[0].content:"xwiki";
XWiki.Document.currentSpace=($$("meta[name=space]").length>0)?$$("meta[name=space]")[0].content:"Main";
XWiki.Document.currentPage=($$("meta[name=page]").length>0)?$$("meta[name=page]")[0].content:"WebHome";
XWiki.Document.URLTemplate="$xwiki.getURL('__space__.__page__', '__action__')";
XWiki.Document.RestURLTemplate="${request.contextPath}/rest/wikis/__wiki__/spaces/__space__/pages/__page__";
XWiki.Document.WikiSearchURLStub="${request.contextPath}/rest/wikis/__wiki__/search";
XWiki.Document.SpaceSearchURLStub="${request.contextPath}/rest/wikis/__wiki__/spaces/__space__/search";
XWiki.Document.getRestSearchURL=function(D,C,A){A=A||XWiki.Document.currentWiki;
var B;
if(C){B=XWiki.Document.SpaceSearchURLStub.replace("__wiki__",A).replace("__space__",C)
}else{B=XWiki.Document.WikiSearchURLStub.replace("__wiki__",A)
}if(D){B+="?"+D
}return B
};
XWiki.currentDocument=new XWiki.Document()
});
document.observe("xwiki:dom:loaded",function(){var A=function(){if(this.value==this.defaultValue){this.value=""
}else{this.select()
}};
var B=function(){if(this.value==""){this.value=this.defaultValue
}};
$$("input.withTip").each(function(C){C.observe("focus",A.bindAsEventListener(C));
C.observe("blur",B.bindAsEventListener(C))
})
});
document.observe("xwiki:dom:loaded",function(){if(typeof (XWiki.widgets.Suggest)!="undefined"){$$("input.suggestDocuments").each(function(A){new XWiki.widgets.Suggest(A,{script:XWiki.Document.getRestSearchURL("scope=name&number=10&media=json&"),varname:"q",noresults:"Document not found",json:true,resultsParameter:"searchResults",resultId:"id",resultValue:"pageFullName",resultInfo:"pageFullName",timeout:30000,parentContainer:A.up()})
})
}});
document.observe("xwiki:dom:loaded",function(){if(typeof (XWiki.widgets.Suggest)!="undefined"){$$(".suggested").each(function(A){A.setAttribute("autocomplete","off");
if(typeof A.onfocus==="function"){A.onfocus();
A.removeAttribute("onfocus")
}})
}});
document.observe("xwiki:dom:loaded",function(){var H=$("hierarchy");
var D=$("breadcrumbs");
var F=$("editParentTrigger");
var B=$("parentinput");
var A=$("xwikidocparentinput");
var G=$("xwikidoctitleinput");
function E(J){if(J){J.stop()
}B.removeClassName("active");
F.addClassName("edit-parent");
F.removeClassName("hide-edit-parent")
}function I(J){if(J){J.stop()
}B.addClassName("active");
A.focus();
F.removeClassName("edit-parent");
F.addClassName("hide-edit-parent")
}function C(J){J.stop();
J.element().blur();
if(F.hasClassName("edit-parent")){I()
}else{E()
}}if($("hideEditParentTrigger")){$("hideEditParentTrigger").style.display="none"
}if(F){F.observe("click",C)
}if(A){if(H||D){["blur","change","xwiki:suggest:selected"].each(function(J){A.observe(J,function(){new Ajax.Request(XWiki.currentDocument.getURL("edit"),{parameters:{xpage:"xpart",vm:(H?"hierarchy.vm":"space.vm"),parent:A.value,title:G.value},onSuccess:function(K){if(H){H.replace(K.responseText);
H=$("hierarchy")
}else{var L=new Element("div");
L.update(K.responseText);
D.replace(L.down("[id=breadcrumbs]"));
D=$("breadcrumbs")
}}})
})
})
}$("body").observe("click",function(J){if(!J.element().descendantOf(B)&&J.element()!=B&&J.element()!=F){E()
}})
}});
document.observe("xwiki:dom:loaded",function(){var F=$("contentmenu")||$("editmenu");
var D=$("mainContentArea")||$("mainEditArea");
if(F&&D){E(F);
Event.observe(window,"resize",function(){if(F.style.position=="fixed"){F.style.width=D.getWidth()+"px";
if(typeof (F.__fm_extra)!="undefined"){if(F.__fm_extra.getStyle("padding-left").replace(/[^a-z]/g,"")=="px"){var H=F.__fm_extra.getStyle("border-left-width").replace(/[^0-9.]/g,"")-0;
H+=F.__fm_extra.getStyle("padding-left").replace(/[^0-9.]/g,"")-0;
H+=F.__fm_extra.getStyle("padding-right").replace(/[^0-9.]/g,"")-0;
H+=F.__fm_extra.getStyle("border-right-width").replace(/[^0-9.]/g,"")-0
}else{H=50
}F.__fm_extra.style.width=(D.getWidth()-H)+"px"
}}});
if(!browser.isIE6x){Event.observe(window,"scroll",B);
document.observe("xwiki:annotations:settings:loaded",B)
}}function B(){var I=$$(".annotationsettings");
var H=0;
if(I&&I.size()>0){F.__fm_extra=I[0];
E(F.__fm_extra);
H=F.__fm_extra.getHeight()
}var N=F.getHeight();
var L=D.cumulativeOffset().top+D.getHeight()-N-H;
var M=D.cumulativeOffset().top-N-H;
if(document.viewport.getScrollOffsets().top>=M&&document.viewport.getScrollOffsets().top<L){var K=D.getWidth();
var J=D.cumulativeOffset().left;
C(F,0,J,K);
C(F.__fm_extra,N,J,(K-50))
}else{if(document.viewport.getScrollOffsets().top>=L){G(F,L);
G(F.__fm_extra,L+N)
}else{A(F);
A(F.__fm_extra)
}}}function E(H){if(typeof (H.__fm_ghost)=="undefined"){H.__fm_ghost=new Element("div");
H.__fm_ghost.hide();
H.insert({after:H.__fm_ghost})
}H.__fm_ghost.clonePosition(H,{setWidth:false})
}function C(H,K,J,I){if(H){H.style.position="fixed";
H.style.top=K+"px";
H.style.left=J+"px";
H.style.width=I+"px";
H.__fm_ghost.show()
}}function G(H,I){if(H){H.style.position="absolute";
H.style.top=I+"px";
H.__fm_ghost.show()
}}function A(H){if(H){H.style.position="";
H.style.top="";
H.style.left="";
H.style.width="";
H.__fm_ghost.hide()
}}});