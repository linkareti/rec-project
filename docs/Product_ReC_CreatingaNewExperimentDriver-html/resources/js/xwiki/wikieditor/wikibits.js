var noOverwrite=false;
var alertText;
var clientPC=navigator.userAgent.toLowerCase();
var is_gecko=((clientPC.indexOf("gecko")!=-1)&&(clientPC.indexOf("spoofer")==-1)&&(clientPC.indexOf("khtml")==-1)&&(clientPC.indexOf("netscape/7.0")==-1));
var is_safari=((clientPC.indexOf("AppleWebKit")!=-1)&&(clientPC.indexOf("spoofer")==-1));
var is_khtml=(navigator.vendor=="KDE"||(document.childNodes&&!document.all&&!navigator.taintEnabled));
if(clientPC.indexOf("opera")!=-1){var is_opera=true;
var is_opera_preseven=(window.opera&&!document.childNodes);
var is_opera_seven=(window.opera&&document.childNodes)
}function onloadhook(){if(!(document.getElementById&&document.getElementsByTagName)){return 
}histrowinit();
unhidetzbutton();
tabbedprefs();
akeytt()
}if(window.addEventListener){window.addEventListener("load",onloadhook,false)
}else{if(window.attachEvent){window.attachEvent("onload",onloadhook)
}}if(typeof stylepath!="undefined"&&typeof skin!="undefined"){if(is_opera_preseven){document.write('<link rel="stylesheet" type="text/css" href="'+stylepath+"/"+skin+'/Opera6Fixes.css">')
}else{if(is_opera_seven){document.write('<link rel="stylesheet" type="text/css" href="'+stylepath+"/"+skin+'/Opera7Fixes.css">')
}else{if(is_khtml){document.write('<link rel="stylesheet" type="text/css" href="'+stylepath+"/"+skin+'/KHTMLFixes.css">')
}}}}if(window.top!=window){window.top.location=window.location
}function toggleVisibility(B,E,A){var C=document.getElementById(B);
var D=document.getElementById(E);
var F=document.getElementById(A);
if(C.style.display=="none"){C.style.display="block";
D.style.display="none";
F.style.display="inline"
}else{C.style.display="none";
D.style.display="inline";
F.style.display="none"
}}function histrowinit(){hf=document.getElementById("pagehistory");
if(!hf){return 
}lis=hf.getElementsByTagName("LI");
for(i=0;
i<lis.length;
i++){inputs=lis[i].getElementsByTagName("INPUT");
if(inputs[0]&&inputs[1]){inputs[0].onclick=diffcheck;
inputs[1].onclick=diffcheck
}}diffcheck()
}function diffcheck(){var B=false;
var A=false;
hf=document.getElementById("pagehistory");
if(!hf){return 
}lis=hf.getElementsByTagName("LI");
for(i=0;
i<lis.length;
i++){inputs=lis[i].getElementsByTagName("INPUT");
if(inputs[1]&&inputs[0]){if(inputs[1].checked||inputs[0].checked){if(inputs[1].checked&&inputs[0].checked&&inputs[0].value==inputs[1].value){return false
}if(A){if(inputs[1].checked){A.className="selected";
return false
}}else{if(inputs[0].checked){return false
}}if(inputs[0].checked){B=lis[i]
}if(!A){inputs[0].style.visibility="hidden"
}if(B){inputs[1].style.visibility="hidden"
}lis[i].className="selected";
A=lis[i]
}else{if(!A){inputs[0].style.visibility="hidden"
}else{inputs[0].style.visibility="visible"
}if(B){inputs[1].style.visibility="hidden"
}else{inputs[1].style.visibility="visible"
}lis[i].className=""
}}}}function tabbedprefs(){prefform=document.getElementById("preferences");
if(!prefform||!document.createElement){return 
}if(prefform.nodeName=="A"){return 
}prefform.className=prefform.className+"jsprefs";
var F=new Array();
children=prefform.childNodes;
var D=0;
for(i=0;
i<children.length;
i++){if(children[i].nodeName.indexOf("FIELDSET")!=-1){children[i].id="prefsection-"+D;
children[i].className="prefsection";
if(is_opera||is_khtml){children[i].className="prefsection operaprefsection"
}legends=children[i].getElementsByTagName("LEGEND");
F[D]=new Object();
if(legends[0]&&legends[0].firstChild.nodeValue){F[D].text=legends[0].firstChild.nodeValue
}else{F[D].text="# "+D
}F[D].secid=children[i].id;
D++;
if(F.length!=1){children[i].style.display="none"
}else{var B=children[i].id
}}}var E=document.createElement("UL");
E.id="preftoc";
E.selectedid=B;
for(i=0;
i<F.length;
i++){var A=document.createElement("LI");
if(i==0){A.className="selected"
}var C=document.createElement("A");
C.href="#"+F[i].secid;
C.onclick=uncoversection;
C.innerHTML=F[i].text;
C.secid=F[i].secid;
A.appendChild(C);
E.appendChild(A)
}prefform.insertBefore(E,children[0]);
document.getElementById("prefsubmit").id="prefcontrol"
}function uncoversection(){oldsecid=this.parentNode.parentNode.selectedid;
newsec=document.getElementById(this.secid);
if(oldsecid!=this.secid){ul=document.getElementById("preftoc");
document.getElementById(oldsecid).style.display="none";
newsec.style.display="block";
ul.selectedid=this.secid;
lis=ul.getElementsByTagName("LI");
for(i=0;
i<lis.length;
i++){lis[i].className=""
}this.parentNode.className="selected"
}return false
}function checkTimezone(H,F){var C=new Date();
var G=C.getTimezoneOffset();
var B=Math.floor(Math.abs(G)/60);
var A=Math.abs(G)%60;
var D=((G>=0)?"-":"+")+((B<10)?"0":"")+B+((A<10)?"0":"")+A;
if(H!=D){var E=F.split("$1");
document.write(E[0]+"UTC"+D+E[1])
}}function unhidetzbutton(){tzb=document.getElementById("guesstimezonebutton");
if(tzb){tzb.style.display="inline"
}}function fetchTimezone(){var C=new Date();
var E=C.getTimezoneOffset();
var B=Math.floor(Math.abs(E)/60);
var A=Math.abs(E)%60;
var D=((E>=0)?"-":"")+((B<10)?"0":"")+B+":"+((A<10)?"0":"")+A;
return D
}function guessTimezone(A){document.preferences.wpHourDiff.value=fetchTimezone()
}function showTocToggle(A,B){if(document.getElementById){document.writeln('<span class=\'toctoggle\'>[<a href="javascript:toggleToc()" class="internal"><span id="showlink" style="display:none;">'+A+'</span><span id="hidelink">'+B+"</span></a>]</span>")
}}function toggleToc(){var C=document.getElementById("tocinside");
var B=document.getElementById("showlink");
var A=document.getElementById("hidelink");
if(C.style.display=="none"){C.style.display=tocWas;
A.style.display="";
B.style.display="none"
}else{tocWas=C.style.display;
C.style.display="none";
A.style.display="none";
B.style.display=""
}}function addButton(F,A,C,B,E){A=escapeQuotes(A);
C=escapeQuotes(C);
B=escapeQuotes(B);
E=escapeQuotes(E);
var G="";
if(!document.selection&&!is_gecko){var D=new RegExp("\\\\n","g");
C=C.replace(D,"");
B=B.replace(D,"");
G="onMouseover=\"if(!noOverwrite){document.infoform.infobox.value='"+C+E+B+"'};\""
}document.write('<a href="javascript:insertTags');
document.write("('"+C+"','"+B+"','"+E+"');\">");
document.write('<img src="'+F+'" border="0" ALT="'+A+'" TITLE="'+A+'"'+G+">");
document.write("</a>");
return 
}function addInfobox(D,C){alertText=C;
var A=navigator.userAgent.toLowerCase();
var B=new RegExp("\\\\n","g");
alertText=alertText.replace(B,"\n");
if(!document.selection&&!is_gecko){D=escapeQuotesHTML(D);
document.write("<form name='infoform' id='infoform'><input size=80 id='infobox' name='infobox' value=\""+D+'" READONLY></form>')
}}function escapeQuotes(B){var A=new RegExp("'","g");
B=B.replace(A,"\\'");
A=new RegExp('"',"g");
B=B.replace(A,"&quot;");
A=new RegExp("\\n","g");
B=B.replace(A,"\\n");
return B
}function escapeQuotesHTML(B){var A=new RegExp('"',"g");
B=B.replace(A,"&quot;");
return B
}function insertTags(I,A,B){var K=document.forms.edit.content;
if(!K){K=document.getElementById("content")
}if(document.selection&&!is_gecko){var L=document.selection.createRange().text;
if(!L){L=B
}K.focus();
if(L.charAt(L.length-1)==" "){L=L.substring(0,L.length-1);
document.selection.createRange().text=I+L+A+" "
}else{document.selection.createRange().text=I+L+A
}}else{if(K.selectionStart||K.selectionStart=="0"){var J=K.selectionStart;
var C=K.selectionEnd;
var E=K.scrollTop;
var D=(K.value).substring(J,C);
if(!D){D=B
}if(D.charAt(D.length-1)==" "){subst=I+D.substring(0,(D.length-1))+A+" "
}else{subst=I+D+A
}K.value=K.value.substring(0,J)+subst+K.value.substring(C,K.value.length);
K.focus();
var N=J+(I.length+D.length+A.length);
K.selectionStart=N;
K.selectionEnd=N;
K.scrollTop=E
}else{var H=alertText;
var G=new RegExp("\\$1","g");
var F=new RegExp("\\$2","g");
H=H.replace(G,B);
H=H.replace(F,I+B+A);
var M;
if(B){M=prompt(H)
}else{M=""
}if(!M){M=B
}M=I+M+A;
document.infoform.infobox.value=M;
if(!is_safari){K.focus()
}noOverwrite=true
}}if(K.createTextRange){K.caretPos=document.selection.createRange().duplicate()
}}function akeytt(){if(typeof ta=="undefined"||!ta){return 
}pref="alt-";
if(is_safari||navigator.userAgent.toLowerCase().indexOf("mac")+1){pref="control-"
}if(is_opera){pref="shift-esc-"
}for(id in ta){n=document.getElementById(id);
if(n){a=n.childNodes[0];
if(a){if(ta[id][0].length>0){a.accessKey=ta[id][0];
ak=" ["+pref+ta[id][0]+"]"
}else{ak=""
}a.title=ta[id][1]+ak
}else{if(ta[id][0].length>0){n.accessKey=ta[id][0];
ak=" ["+pref+ta[id][0]+"]"
}else{ak=""
}n.title=ta[id][1]+ak
}}}};