function computeBounds(){leftPanelsLeft=getX(leftPanels);
leftPanelsRight=leftPanelsLeft+leftPanels.offsetWidth;
rightPanelsLeft=getX(rightPanels);
rightPanelsRight=rightPanelsLeft+rightPanels.offsetWidth;
allpanelsLeft=getX(allPanels);
allpanelsTop=getY(allPanels)
}function debugwrite(A){document.getElementById("headerglobal").appendChild(document.createTextNode(A))
}function isPanel(A){if(A.className&&((A.className=="panel")||(A.className.indexOf("panel ")>=0)||(A.className.indexOf(" panel")>=0))){return true
}return false
}function getX(A){if(A.offsetParent){if(window.ActiveXObject){return A.offsetLeft+getX(A.offsetParent)+A.clientLeft
}else{return A.offsetLeft+getX(A.offsetParent)+(A.scrollWidth-A.clientWidth)
}}else{if(A.x){return A.x
}else{return 0
}}}function getY(A){if(A.offsetParent){if(window.ActiveXObject){return A.offsetTop+getY(A.offsetParent)+A.clientTop
}else{return A.offsetTop+getY(A.offsetParent)+(A.scrollHeight-A.clientHeight)
}}else{if(A.y){return A.y
}else{return 0
}}}function getBlocList(D){var E=[];
var A=D.childNodes.length;
for(var C=0;
C<A;
C++){var B=D.childNodes[C];
if(isPanel(B)){if(!B.isDragging){E.push(B)
}}}return E
}function getDragBoxPos(C,D){var A=C.length;
if(A==0){return 0
}for(var B=0;
B<A;
B++){if(C[B]==dragel){return B
}}return -1
}function getAllPanels(D){var E=[];
var C=D.getElementsByTagName("div");
var A=0;
for(var B=0;
B<C.length;
B++){if(isPanel(C[B])){E[A]=C[B];
A++
}}return E
}function getClosestDropTarget(A,D,B,C){if(window.showLeftColumn==1&&(A<=leftPanelsRight&&(A+B)>=leftPanelsLeft)){return leftPanels
}if(window.showRightColumn==1&&((A+B)>=rightPanelsLeft&&A<=rightPanelsRight)){return rightPanels
}return allPanels
}function onDragStart(B,A,F){if(B.isDragging){return 
}hideTip();
window.isDraggingPanel=true;
if(enabletip==true){hideTip()
}realParent=B.parentNode;
parentNode=B.parentNode;
var D=(realParent!=leftPanels&&realParent!=rightPanels);
var C=Position.cumulativeOffset(B);
var E=Position.realOffset(B);
var A=C[0];
var F=C[1]-E[1]+(document.documentElement.scrollTop-0+document.body.scrollTop-0);
if(window.ActiveXObject){dragel.style.height=(B.offsetHeight?(B.offsetHeight):B.displayHeight)+"px"
}else{dragel.style.height=(B.offsetHeight?(B.offsetHeight-2):B.displayHeight)+"px"
}dragel.style.display="block";
B.style.left=A+"px";
B.style.top=F+"px";
B.style.zIndex="10";
if(D){parentNode=allPanels;
B.placeholder=document.createElement("div");
B.placeholder.className="placeholder";
if(window.ActiveXObject){B.placeholder.style.height=(B.offsetHeight?(B.offsetHeight):B.displayHeight)+"px"
}else{B.placeholder.style.height=(B.offsetHeight?(B.offsetHeight-2):B.displayHeight)+"px"
}realParent.replaceChild(B.placeholder,B);
B.placeholder.style.display="block";
addClass(allPanels,"dropTarget")
}else{realParent.replaceChild(dragel,B)
}B.style.position="absolute";
document.body.appendChild(B);
B.isDragging=true;
prevcolumn=parentNode
}function onDrag(B,A,E){if(enabletip==true){hideTip()
}parentNode=getClosestDropTarget(A,E,B.offsetWidth,B.offsetHeight);
if(parentNode!=prevcolumn){if(prevcolumn!=allPanels){prevcolumn.removeChild(dragel)
}if(parentNode!=allPanels){parentNode.appendChild(dragel);
rmClass(allPanels,"dropTarget")
}else{addClass(allPanels,"dropTarget")
}}prevcolumn=parentNode;
var C=getBlocList(parentNode);
var D=getDragBoxPos(C,E);
if(D==-1){return 
}if(C.length==0){if(parentNode!=allPanels){parentNode.appendChild(dragel)
}}else{if(D!=0&&E<=getY(C[D-1])){parentNode.insertBefore(dragel,C[D-1])
}else{if(D!=(C.length-1)&&E>=getY(C[D+1])){if(C[D+2]){parentNode.insertBefore(dragel,C[D+2])
}else{if(parentNode!=allPanels){parentNode.appendChild(dragel)
}else{dragel.parentNode.removeChild(dragel)
}}}}}}function onDragEnd(B,A,C){B.isDragging=false;
window.isDraggingPanel=false;
B.style.position="static";
if(parentNode==allPanels){B.placeholder.parentNode.replaceChild(B,B.placeholder);
B.placeholder=undefined;
rmClass(allPanels,"dropTarget")
}else{parentNode.replaceChild(B,dragel)
}dragel.style.display="none"
}function executeCommand(C,E){function A(){if(D.readyState==4){if(D.status==200){if(B){B(D.responseText)
}else{alert("no callback defined")
}}else{alert("There was a problem retrieving the xml data:\n"+D.status+":\t"+D.statusText+"\n"+D.responseText)
}}}var D=null;
var B=E;
if(window.XMLHttpRequest){D=new XMLHttpRequest();
D.onreadystatechange=A;
D.open("GET",C,true);
D.send(null)
}else{if(window.ActiveXObject){D=new ActiveXObject("Microsoft.XMLHTTP");
if(D){D.onreadystatechange=A;
D.open("GET",C,true);
D.send()
}else{alert("your browser does not support xmlhttprequest")
}}else{alert("your browser does not support xmlhttprequest")
}}}function start1(){var B;
var A;
var G;
var D=document.getElementsByTagName("div");
for(B=0;
B<D.length;
B++){C=D[B];
var F=C.id;
if(isPanel(C)){attachDragHandler(C)
}}window.panelsInList=getAllPanels(allPanels);
window.panelsOnLeft=getBlocList(leftPanels);
window.panelsOnRight=getBlocList(rightPanels);
var C;
for(B=0;
B<panelsInList.length;
B++){G=window.allPanelsPlace[B]["left"];
if(G!=-1){C=panelsOnLeft[G];
if(C){C.fullname=window.allPanelsPlace[B].fullname;
C.placeholder=document.createElement("div");
C.placeholder.className="placeholder";
if(window.ActiveXObject){C.displayHeight=(C.offsetHeight?(C.offsetHeight):0)
}else{C.displayHeight=(C.offsetHeight?(C.offsetHeight-2):0)
}C.placeholder.style.height=(C.displayHeight)+"px";
C.placeholder.style.display="block";
panelsInList[B].parentNode.replaceChild(C.placeholder,panelsInList[B])
}}G=window.allPanelsPlace[B]["right"];
if(G!=-1){C=panelsOnRight[G];
if(C){C.fullname=window.allPanelsPlace[B].fullname;
C.placeholder=document.createElement("div");
C.placeholder.className="placeholder";
if(window.ActiveXObject){C.displayHeight=(C.offsetHeight?(C.offsetHeight):0)
}else{C.displayHeight=(C.offsetHeight?(C.offsetHeight-2):0)
}C.placeholder.style.height=(C.displayHeight)+"px";
C.placeholder.style.display="block";
if(panelsInList[B].parentNode){panelsInList[B].parentNode.replaceChild(C.placeholder,panelsInList[B])
}}}panelsInList[B].fullname=window.allPanelsPlace[B].fullname
}leftPanels.savedPanelList=getBlocList(leftPanels);
rightPanels.savedPanelList=getBlocList(rightPanels);
leftPanels.isVisible=window.showLeftColumn;
rightPanels.isVisible=window.showRightColumn;
if(!leftPanels.isVisible){leftPanels.panels=getBlocList(leftPanels)
}if(!rightPanels.isVisible){rightPanels.panels=getBlocList(rightPanels)
}var E=document.getElementById("PageLayoutSection").getElementsByTagName("td");
layoutMaquettes=new Object();
for(B=0;
B<E.length;
B++){for(A=0;
A<E[B].childNodes.length;
++A){if(E[B].childNodes[A].tagName=="DIV"){layoutMaquettes[B]=E[B].childNodes[A];
break
}}}window.activeWizardPage=document.getElementById("PanelListSection");
window.activeWizardTab=document.getElementById("firstwtab");
document.getElementById("PageLayoutSection").style.display="none"
}function attachDragHandler(A){A.ondblclick=function(C){};
Drag.init(A,A);
A.onDragStart=function(C,D){onDragStart(this,C,D)
};
A.onDrag=function(C,D){onDrag(this,C,D)
};
A.onDragEnd=function(C,D){onDragEnd(this,C,D)
};
var B=A.getElementsByTagName("h5").item(0);
if(B){B.onclick=function(C){};
B.onClick=function(C){}
}}function getBlocNameList(D){var E="";
var A=D.childNodes.length;
for(var C=0;
C<A;
C++){var B=D.childNodes[C];
if(isPanel(B)){if(!B.isDragging){if(E!=""){E+=","
}E+=B.fullname
}}}return E
}function save(){url=window.ajaxurl;
var A=getBlocNameList(leftPanels);
url+="&leftPanels="+A;
url+="&showLeftPanels="+window.showLeftColumn;
var B=getBlocNameList(rightPanels);
url+="&rightPanels="+B;
url+="&showRightPanels="+window.showRightColumn;
executeCommand(url,saveResult)
}function saveResult(A){if(A=="SUCCESS"){alert(window.panelsavesuccess);
leftPanels.savedPanelList=getBlocList(leftPanels);
rightPanels.savedPanelList=getBlocList(rightPanels);
leftPanels.isVisible=window.showLeftColumn;
rightPanels.isVisible=window.showRightColumn
}else{alert(window.panelsaveerror);
alert(A)
}}function releasePanels(B){B.panels=getBlocList(B);
for(var A=0;
A<B.panels.length;
++A){releasePanel(B.panels[A])
}}function releasePanel(A){A.parentNode.removeChild(A);
A.placeholder.parentNode.replaceChild(A,A.placeholder);
A.placeholder=undefined
}function restorePanels(B){for(var A=0;
A<B.panels.length;
++A){if(!B.panels[A].placeholder){restorePanel(B.panels[A],B)
}}B.panels=undefined
}function revertPanels(B){for(var A=0;
A<B.savedPanelList.length;
++A){restorePanel(B.savedPanelList[A],B)
}}function restorePanel(B,A){B.placeholder=document.createElement("div");
B.placeholder.className="placeholder";
if(window.ActiveXObject){B.placeholder.style.height=(B.offsetHeight?(B.offsetHeight):0)
}else{B.placeholder.style.height=(B.offsetHeight?(B.offsetHeight-2):0)
}B.placeholder.style.display="block";
B.parentNode.replaceChild(B.placeholder,B);
A.appendChild(B)
}function changePreviewLayout(A,B){document.getElementById("selectedoption").id="";
A.id="selectedoption";
switch(B){case 0:if(window.showLeftColumn==1){window.showLeftColumn=0;
leftPanels.style.display="none";
releasePanels(leftPanels)
}if(window.showRightColumn==1){window.showRightColumn=0;
rightPanels.style.display="none";
releasePanels(rightPanels)
}mainContainer.className="hidelefthideright";
break;
case 1:if(window.showLeftColumn==0){window.showLeftColumn=1;
leftPanels.style.display="block";
restorePanels(leftPanels)
}if(window.showRightColumn==1){window.showRightColumn=0;
rightPanels.style.display="none";
releasePanels(rightPanels)
}mainContainer.className="hideright";
break;
case 2:if(window.showLeftColumn==1){window.showLeftColumn=0;
leftPanels.style.display="none";
releasePanels(leftPanels)
}if(window.showRightColumn==0){window.showRightColumn=1;
rightPanels.style.display="block";
restorePanels(rightPanels)
}mainContainer.className="hideleft";
break;
case 3:if(window.showLeftColumn==0){window.showLeftColumn=1;
leftPanels.style.display="block";
restorePanels(leftPanels)
}if(window.showRightColumn==0){window.showRightColumn=1;
rightPanels.style.display="block";
restorePanels(rightPanels)
}mainContainer.className="content";
break;
default:break
}computeBounds()
}function revert(){releasePanels(leftPanels);
releasePanels(rightPanels);
revertPanels(leftPanels);
revertPanels(rightPanels);
var A=0;
if(leftPanels.isVisible){A+=1
}if(rightPanels.isVisible){A+=2
}changePreviewLayout(layoutMaquettes[A],A)
}function switchToWizardPage(B,A){window.activeWizardPage.style.display="none";
window.activeWizardTab.className="";
window.activeWizardTab=B;
window.activeWizardTab.className="active";
window.activeWizardPage=document.getElementById(A);
window.activeWizardPage.style.display="block";
B.blur()
}function panelEditorInit(){tipobj=document.all?document.all.dhtmltooltip:document.getElementById?document.getElementById("dhtmltooltip"):"";
parentNode=null;
realParent=null;
dragel=document.createElement("div");
dragel.id="dragbox";
dragel.className="panel";
dragWidth=0;
nb=0;
layoutMaquetes=null;
window.leftPanels=document.getElementById("leftPanels");
window.rightPanels=document.getElementById("rightPanels");
allPanels=document.getElementById("allviewpanels");
mainContent=document.getElementById("contentcolumn");
mainContainer=document.getElementById("body");
leftPanelsLeft=getX(leftPanels);
leftPanelsRight=leftPanelsLeft+leftPanels.offsetWidth;
rightPanelsLeft=getX(rightPanels);
rightPanelsRight=rightPanelsLeft+rightPanels.offsetWidth;
allpanelsLeft=getX(allPanels);
allpanelsTop=getY(allPanels);
prevcolumn=allPanels;
start1()
}if(window.addEventListener){window.addEventListener("load",panelEditorInit,false)
}else{if(window.attachEvent){window.attachEvent("onload",panelEditorInit)
}};