function chwPositionSelector(C,B,A){this.property=C;
this.type=B;
this.selected=A;
this.leftButtonDown=false;
this.onmouseover=function(E,D){if(this.selected==D){E.className="chwSelectedCellHighlighted"
}else{if(this.leftButtonDown==true){document.getElementById("chw"+this.property+this.selected).className="normal";
this.selected=D;
E.className="chwSelectedCellHighlighted";
switch(this.type){case"Position":document.getElementById("chw"+this.property+"Input").value=D.toLowerCase();
break;
case"Alignment":document.getElementById("chw"+this.property+"HorizontalInput").value=D.substring(D.indexOf("_")+1).toLowerCase();
document.getElementById("chw"+this.property+"VerticalInput").value=D.substring(0,D.indexOf("_")).toLowerCase();
break
}}else{E.className="chwNormalCellHighlighted"
}}};
this.onmouseout=function(E,D){if(this.selected==D){E.className="chwSelectedCell"
}else{E.className="chwNormalCell"
}};
this.onmousedown=function(E,D){if(E.button!=window.wizard.LMB){return 
}this.leftButtonDown=true;
if(this.selected==D){return 
}document.getElementById("chw"+this.property+this.selected).className="chwNormalCell";
this.selected=D;
switch(this.type){case"Position":document.getElementById("chw"+this.property+"Input").value=D.toLowerCase();
break;
case"Alignment":document.getElementById("chw"+this.property+"HorizontalInput").value=D.substring(D.indexOf("_")+1).toLowerCase();
document.getElementById("chw"+this.property+"VerticalInput").value=D.substring(0,D.indexOf("_")).toLowerCase();
break
}document.getElementById("chw"+this.property+D).className="chwSelectedCellHighlighted"
};
this.onmouseup=function(D){if(D.button!=window.wizard.LMB){return 
}this.leftButtonDown=false
}
}function chwColorChooser(A){this.property=A;
this.element=document.getElementById("chw"+this.property+"Input");
this.customGroup=document.getElementById("chw"+this.property+"CustomGroup");
this.custom=document.getElementById("chw"+this.property+"CustomInput");
this.customOption=document.getElementById("chw"+this.property+"CustomOption");
this.storedColorCode="#000000";
this.colorChoiceChanged=function(){if(this.element.value.indexOf("#")==0){this.customGroup.style.display="inline"
}else{this.customGroup.style.display="none"
}};
this.showCustomColor=function(){if(this.custom.value.match("^#(([0-9a-fA-F][9a-fA-F][0-9a-fA-F])|((([0-9a-fA-F]{2})[9a-fA-F]([0-9a-fA-F]){3})))$")){this.custom.style.backgroundColor=this.custom.value;
this.custom.style.color="#000"
}else{if(this.custom.value.match("^(([0-9a-fA-F][9a-fA-F][0-9a-fA-F])|((([0-9a-fA-F]{2})[9a-fA-F]([0-9a-fA-F]){3})))$")){this.custom.style.backgroundColor="#"+this.custom.value;
this.custom.style.color="#000"
}else{if(this.custom.value.match("(^#[0-9a-fA-F]{3}$)|(^#[0-9a-fA-F]{6}$)")){this.custom.style.backgroundColor=this.custom.value;
this.custom.style.color="#FFF"
}else{if(this.custom.value.match("(^[0-9a-fA-F]{3}$)|(^[0-9a-fA-F]{6}$)")){this.custom.style.backgroundColor="#"+this.custom.value;
this.custom.style.color="#FFF"
}}}}};
this.customColorValueFocus=function(){this.storedColorCode=this.custom.value
};
this.validateCustomColor=function(){var B=this.element.selectedIndex;
if(this.custom.value.match("^#[0-9a-fA-F]{3}$")){this.customOption.value="#"+this.custom.value.charAt(1)+this.custom.value.charAt(1)+this.custom.value.charAt(2)+this.custom.value.charAt(2)+this.custom.value.charAt(3)+this.custom.value.charAt(3)
}else{if(this.custom.value.match("^[0-9a-fA-F]{3}$")){this.customOption.value="#"+this.custom.value.charAt(0)+this.custom.value.charAt(0)+this.custom.value.charAt(1)+this.custom.value.charAt(1)+this.custom.value.charAt(2)+this.custom.value.charAt(2)
}else{if(this.custom.value.match("^[0-9a-fA-F]{6}$")){this.customOption.value="#"+this.custom.value
}else{if(this.custom.value.match("^#[0-9a-fA-F]{6}$")){this.customOption.value=this.custom.value
}else{this.custom.value=this.storedColorCode;
this.showCustomColor();
return false
}}}}this.element.selectedIndex=B
};
this.showCustomColor()
}function chwWizard(){var K;
var S=["Data","Type","Titles","Axes","Grid","Labels","Legend","Space","Colors","Insert"];
var P;
var J;
var A={Data:true,Type:false,Titles:false,Axes:false,Grid:false,Labels:false,Legend:false,Space:false,Colors:false,Insert:false};
var C={Bar:{Data:[],Type:["ChartType"],Titles:["ChartTitle","ChartSubtitle"]},Pie:{Data:[],Type:["ChartType"],Titles:["ChartTitle","ChartSubtitle"]}};
var R=new Object();
var Q=new Object();
var O;
var B;
var E;
var L=false;
var H=false;
var F=false;
var N=false;
var I=true;
var G;
var D,M;
adjustPage=function(V,W){var U=0,T=0;
while(U<V.length&&T<W.length){if(V[U]<W[T]){document.getElementById("chw"+V[U++]+"Div").className="chwVisible"
}else{if(V[U]>W[T]){document.getElementById("chw"+W[T++]+"Div").className="chwHidden"
}else{U++;
T++
}}}while(U<V.length){document.getElementById("chw"+V[U++]+"Div").className="chwVisible"
}while(T<W.length){document.getElementById("chw"+W[T++]+"Div").className="chwHidden"
}};
getPageIndex=function(T){for(var U=0;
U<S.length;
U++){if(S[U]==T){return U
}}};
getNextPageIndex=function(T){while(!C[J][S[++T]]){if(T==S.length-1){return -1
}}return T
};
getPrevPageIndex=function(T){while(!C[J][S[--T]]){if(T==0){return -1
}}return T
};
enableBack=function(){B.className="chwButton";
L=true
};
disableBack=function(){B.className="chwButtonDisabled";
L=false
};
enableNext=function(){O.className="chwButton";
H=true
};
disableNext=function(){O.className="chwButtonDisabled";
H=false
};
enableFinish=function(){E.className="chwButton";
F=true
};
disableFinish=function(){E.className="chwButtonDisabled";
F=false
};
this.initialize=function(T,W,U){K=T;
D=W;
M=U;
B=document.getElementById("chwBackButton");
O=document.getElementById("chwNextButton");
E=document.getElementById("chwFinishButton");
P=S[0];
document.getElementById("chw"+P+"Wizard").className="chwActivePage";
J=document.getElementById("chwChartTypeInput").value;
for(var V in C[J]){document.getElementById("chw"+V+"WizardButton").className="chwNavigationImageDisabled";
adjustPage(C[J][V],[])
}document.getElementById("chw"+P+"WizardButton").className="chwNavigationImage";
R.ChartTitlePosition=new chwPositionSelector("ChartTitlePosition","Position","Top");
R.ChartTitleAlignment=new chwPositionSelector("ChartTitleAlignment","Alignment","Center_Center");
R.ChartSubtitlePosition=new chwPositionSelector("ChartSubtitlePosition","Position","Top");
R.ChartSubtitleAlignment=new chwPositionSelector("ChartSubtitleAlignment","Alignment","Center_Center");
Q.ChartTitleColor=new chwColorChooser("ChartTitleColor");
Q.ChartTitleBackgroundColor=new chwColorChooser("ChartTitleBackgroundColor");
Q.ChartSubtitleColor=new chwColorChooser("ChartSubtitleColor");
Q.ChartSubtitleBackgroundColor=new chwColorChooser("ChartSubtitleBackgroundColor");
if(document.implementation&&document.implementation.hasFeature("HTMLEvents","2.0")){this.LMB=0
}else{this.LMB=1
}window.colorPicker=new ColorPicker(document.getElementById("chwColorpickerHSMap"),document.getElementById("chwColorpickerLMap"),document.getElementById("chwColorpickerLPointer"),document.getElementById("chwColorPickerShow"),document.getElementById("chwColorCodeDisplay"))
};
this.showWizardPage=function(W){if(P==W){return 
}if(!A[W]){return 
}var V=getPageIndex(P);
if(V==0){document.getElementById("chwBackButton").className="chwButton";
L=true
}var T=getNextPageIndex(V);
if(T==-1){document.getElementById("chwNextButton").className="chwButton";
H=true
}document.getElementById("chw"+P+"Wizard").className="chwInactivePage";
var U=document.getElementById("chw"+P+"WizardButton");
if(U.src.indexOf("Hover.png")>=0){U.src=K+"chwTaskCompletedHover.png"
}else{U.src=K+"chwTaskCompleted.png"
}P=W;
var V=getPageIndex(P);
if(V==0){document.getElementById("chwBackButton").className="chwButtonDisabled";
L=false
}var T=getNextPageIndex(V);
if(T==-1){document.getElementById("chwNextButton").className="chwButtonDisabled";
H=false
}document.getElementById("chw"+P+"Wizard").className="chwActivePage";
U=document.getElementById("chw"+P+"WizardButton");
if(U.src.indexOf("Hover.png")>=0){U.src=K+"chwTaskCompletingHover.png"
}else{U.src=K+"chwTaskCompleting.png"
}};
this.enterButton=function(T){if(!A[T]){return false
}var U=document.getElementById("chw"+T+"WizardButton");
var V=U.src;
V=V.substring(0,V.indexOf(".png"))+"Hover.png";
U.src=V
};
this.leaveButton=function(T){if(!A[T]){return 
}var U=document.getElementById("chw"+T+"WizardButton");
var V=U.src;
V=V.substring(0,V.indexOf("Hover.png"))+".png";
U.src=V
};
this.changeChartType=function(T){var V=document.getElementById("chwPreviewImg");
V.setAttribute("src",K+"/chwSample"+T+"Chart.png");
V.setAttribute("alt","Chart Type: "+T);
V.setAttribute("title","Chart Type: "+T);
for(var X in C[J]){if(!C[T][X]){A[X]=false;
adjustPage([],C[J][X]);
document.getElementById("chw"+X+"WizardButton").className="chwNavigationImageHidden"
}}for(var X in C[T]){if(!C[J][X]){adjustPage(C[T][X],[])
}else{adjustPage(C[T][X],C[J][X])
}A[X]=false;
document.getElementById("chw"+X+"WizardButton").className="chwNavigationImageDisabled";
document.getElementById("chw"+X+"WizardButton").src=K+"chwTaskWaiting.png"
}document.getElementById("chw"+J+"Subtypes").className="chwHidden";
document.getElementById("chw"+J+"SubtypeInput").disabled=true;
J=T;
document.getElementById("chw"+J+"Subtypes").className="chwVisible";
document.getElementById("chw"+J+"SubtypeInput").disabled=false;
var W=getPageIndex(P);
for(var U=0;
U<W;
U++){if(!C[J][S[U]]){continue
}document.getElementById("chw"+S[U]+"WizardButton").className="chwNavigationImage";
document.getElementById("chw"+S[U]+"WizardButton").src=K+"chwTaskCompleted.png";
A[S[U]]=true
}A[P]=true;
document.getElementById("chwFinishButton").className="chwButtonDisabled";
F=false;
document.getElementById("chw"+P+"WizardButton").className="chwNavigationImage";
document.getElementById("chw"+P+"WizardButton").src=K+"chwTaskCompleting.png"
};
this.changeChartSubtype=function(T){};
this.flipAdvanced=function(T){var U=document.getElementById("chw"+T+"Legend");
if(U.firstChild.nodeValue.indexOf(">>")>=0){U.firstChild.nodeValue=U.firstChild.nodeValue.replace(">>","<<");
U.title=U.title.replace(D,M);
document.getElementById("chw"+T+"Div").className="chwVisible"
}else{U.firstChild.nodeValue=U.firstChild.nodeValue.replace("<<",">>");
U.title=U.title.replace(M,D);
document.getElementById("chw"+T+"Div").className="chwHidden"
}};
this.changeSourceType=function(T){switch(T){case"Reuse":document.getElementById("chwDataDefineDiv").className="chwHidden";
document.getElementById("chwDataReuseDiv").className="chwVisible";
if(I){enableNext()
}else{disableNext()
}break;
case"Define":document.getElementById("chwDataDefineDiv").className="chwVisible";
document.getElementById("chwDataReuseDiv").className="chwHidden";
if(N){enableNext()
}else{disableNext()
}break
}};
this.flipEnabled=function(T){if(document.getElementById("chw"+T+"Enabled").checked){document.getElementById("chw"+T+"Input").disabled=false
}else{document.getElementById("chw"+T+"Input").disabled=true
}};
this.showNextPage=function(){if(!H){return false
}var U=getPageIndex(P);
var T=getNextPageIndex(U);
if(P=="Type"){for(var V in C[J]){A[V]=true;
document.getElementById("chw"+V+"WizardButton").className="chwNavigationImage";
document.getElementById("chw"+V+"WizardButton").src=K+"chwTaskCompleted.png"
}document.getElementById("chwFinishButton").className="chwButton";
F=true
}T=S[T];
A[T]=true;
document.getElementById("chw"+T+"WizardButton").className="chwNavigationImage";
this.showWizardPage(T);
return false
};
this.showPrevPage=function(){if(!L){return false
}var U=getPageIndex(P);
var T=getPrevPageIndex(U);
T=S[T];
this.showWizardPage(T);
return false
};
this.finish=function(){if(!F){return false
}document.getElementById("chwForm").submit();
return false
};
this.setValidDatasource=function(T){document.getElementById("chwDataSourceInput").value=T;
document.getElementById("chwDefineHasDatasource").className="chwNotice";
enableNext();
N=true
};
this.storeValue=function(T){G=T
};
this.validateNumber=function(W,V,T,U){if(!(Boolean(Number(W.value))||(Number(W.value)==0))){W.value=G;
return false
}else{if(U===undefined){U=0
}var X=Number(W.value).toFixed(U)-0;
if(V!==undefined&&X<V){X=V
}if(T!==undefined&&X>T){X=T
}W.value=X
}return true
};
this.changeInserts=function(T){var U=document.getElementById("chw"+T+"Input");
U.value="left:"+document.getElementById("chw"+T+"LeftInput").value+";";
U.value+="top:"+document.getElementById("chw"+T+"TopInput").value+";";
U.value+="right:"+document.getElementById("chw"+T+"RightInput").value+";";
U.value+="bottom:"+document.getElementById("chw"+T+"BottomInput").value
};
this.changeFont=function(T){var U=document.getElementById("chw"+T+"Input");
U.value="name:"+document.getElementById("chw"+T+"FamilyInput").value+";";
U.value+="style:"+document.getElementById("chw"+T+"StyleInput").value+";";
U.value+="size:"+document.getElementById("chw"+T+"SizeInput").value
};
this.checkTitle=function(T){if(document.getElementById("chw"+T+"Input").value==""){}};
this.selectorMouseOver=function(T,U,V){R[T].onmouseover(U,V)
};
this.selectorMouseOut=function(T,U,V){R[T].onmouseout(U,V)
};
this.selectorMouseDown=function(T,U,V){R[T].onmousedown(U,V)
};
this.selectorMouseUp=function(T,U){R[T].onmouseup(U)
};
this.colorChoiceChanged=function(T){Q[T].colorChoiceChanged()
};
this.showCustomColor=function(T){Q[T].showCustomColor()
};
this.validateCustomColor=function(T){Q[T].validateCustomColor()
};
this.customColorValueFocus=function(T){Q[T].customColorValueFocus()
}
}window.wizard=new chwWizard();
function ColorPicker(hsmap,lmap,lpointer,colorShower,codeDisplay){this.hsmap=hsmap;
this.lmap=lmap;
if(lpointer!=null){this.lpointer=lpointer;
this.lpointerContainer=lpointer.parentNode
}this.colorShower=colorShower;
this.codeDisplay=codeDisplay;
this.container=document.getElementById("chwColorPicker");
this.fieldset=document.getElementById("chwColorPickerFieldset");
this.hueComponent=document.getElementById("chwColorpickerHue");
this.satComponent=document.getElementById("chwColorpickerSaturation");
this.lumComponent=document.getElementById("chwColorpickerLightness");
this.redComponent=document.getElementById("chwColorpickerRed");
this.greenComponent=document.getElementById("chwColorpickerGreen");
this.blueComponent=document.getElementById("chwColorpickerBlue");
this.hue=159;
this.sat=85;
this.lum=120;
this.red=80;
this.green=100;
this.blue=160;
this.storedValue="";
this.LMB=(window.ActiveXObject?1:0);
this.reqester=null;
this.left=120;
this.top=120;
this.min3=function(a,b,c){if(a<=b&&a<=c){return a
}if(b<=a&&b<=c){return b
}return c
};
this.max3=function(a,b,c){if(a>=b&&a>=c){return a
}if(b>=a&&b>=c){return b
}return c
};
this.validCode=function(code){if(!code.match("^#[0-9a-fA-F]{6}$")){return false
}return true
};
this.hsl2rgb=function(hue,sat,lum){var _val,_max,_min,_part,_half,_hi,_lo,_mid;
var _r,_g,_b;
_val=(lum/240)*255;
if(lum>=120){_max=255;
_min=_val-(255-_val)
}else{if(lum<120){_min=0;
_max=_val*2
}}_part=sat/240;
_half=(_max-_min)/2;
_hi=_half*_part;
_lo=_half-_hi;
if(sat==0||_max==_min){_r=_val;
_g=_val;
_b=_val
}else{if(hue<40){_r=_max-_lo;
_b=_min+_lo;
_mid=hue/40;
_g=((_r-_b)*_mid)+_b
}else{if(hue>=200&&hue<=240){_r=_max-_lo;
_g=_min+_lo;
_mid=(240-hue)/40;
_b=((_r-_g)*_mid)+_g
}else{if(hue>=80&&hue<120){_g=_max-_lo;
_r=_min+_lo;
_mid=(hue-80)/40;
_b=((_g-_r)*_mid)+_r
}else{if(hue>=40&&hue<80){_g=_max-_lo;
_b=_min+_lo;
_mid=(80-hue)/40;
_r=((_g-_b)*_mid)+_b
}else{if(hue>=160&&hue<200){_b=_max-_lo;
_g=_min+_lo;
_mid=(hue-160)/40;
_r=((_b-_g)*_mid)+_g
}else{if(hue>=120&&hue<160){_b=_max-_lo;
_r=_min+_lo;
_mid=(160-hue)/40;
_g=((_b-_r)*_mid)+_r
}}}}}}}red=Math.round(_r);
green=Math.round(_g);
blue=Math.round(_b);
return{red:red,green:green,blue:blue}
};
this.hsl2code=function(H,S,L){var rgb=this.hsl2rgb(H,S,L);
return this.rgb2code(rgb.red,rgb.green,rgb.blue)
};
this.rgb2hsl=function(red,green,blue){var R=red/255;
var G=green/255;
var B=blue/255;
var min=this.min3(R,G,B);
var max=this.max3(R,G,B);
var delta=max-min;
var L=(max+min)/2;
var H,S;
if(delta==0){H=0;
S=0
}else{if(L<0.5){S=delta/(max+min)
}else{S=delta/(2-(max+min))
}var deltaR=(((max-R)/6)+(max/2))/delta;
var deltaG=(((max-G)/6)+(max/2))/delta;
var deltaB=(((max-B)/6)+(max/2))/delta;
if(R==max){H=deltaB-deltaG
}else{if(G==max){H=(1/3)+deltaR-deltaB
}else{if(B==max){H=(2/3)+deltaG-deltaR
}}}if(H<0){H+=1
}if(H>1){H-=1
}}return{hue:Math.round(H*240),sat:Math.round(S*240),lum:Math.round(L*240)}
};
this.setValues=function(R,G,B,H,S,L){this.red=R;
this.green=G;
this.blue=B;
this.hue=H;
this.sat=S;
this.lum=L
};
this.setComponents=function(R,G,B,H,S,L,code){this.redComponent.value=R;
this.greenComponent.value=G;
this.blueComponent.value=B;
this.hueComponent.value=H;
this.satComponent.value=S;
this.lumComponent.value=L;
this.colorShower.style.backgroundColor=code;
this.codeDisplay.value=code;
this.lpointerContainer.style.backgroundPosition="0 "+(240-L)+"px";
this.lmap.style.backgroundColor=this.hsl2code(H,S,120);
this.storedValue=code
};
this.rgb2code=function(red,green,blue){red=Math.round(red).toString(16);
if(red.length==1){red="0"+red
}green=Math.round(green).toString(16);
if(green.length==1){green="0"+green
}blue=Math.round(blue).toString(16);
if(blue.length==1){blue="0"+blue
}return"#"+red+green+blue
};
this.code2rgb=function(code){if(!this.validCode(code)){return false
}var red=Number("0x"+code.substring(1,3));
var green=Number("0x"+code.substring(3,5));
var blue=Number("0x"+code.substring(5));
return{red:red,green:green,blue:blue}
};
this.getTargetEltX=function(event,elt){var x;
if(event.offsetX!==undefined){x=event.offsetX
}else{x=event.screenX-document.getBoxObjectFor(elt).screenX
}if(x<0){x=0
}if(x>240){x=240
}return x
};
this.getTargetEltY=function(event,elt){var y;
if(event.offsetY!==undefined){y=event.offsetY
}else{y=event.screenY-document.getBoxObjectFor(elt).screenY
}if(y<0){y=0
}if(y>240){y=240
}return y
};
this.hsChanged=function(event,elt){var hue=this.getTargetEltX(event,elt)-0;
if(hue>240){hue=240
}if(hue<0){hue=0
}var sat=240-this.getTargetEltY(event,elt);
if(sat>240){sat=240
}if(sat<0){sat=0
}var rgb=this.hsl2rgb(hue,sat,this.lum);
var colorcode=this.rgb2code(rgb.red,rgb.green,rgb.blue);
if(!this.validCode(colorcode)){return 
}this.setValues(rgb.red,rgb.green,rgb.blue,hue,sat,this.lum);
this.setComponents(rgb.red,rgb.green,rgb.blue,hue,sat,this.lum,colorcode)
};
this.lChanged=function(event,elt){var lum=240-(this.getTargetEltY(event,elt));
if(lum>240){lum=240
}if(lum<0){lum=0
}var rgb=this.hsl2rgb(this.hue,this.sat,lum);
var colorcode=this.rgb2code(rgb.red,rgb.green,rgb.blue);
if(!this.validCode(colorcode)){return 
}this.setValues(rgb.red,rgb.green,rgb.blue,this.hue,this.sat,lum);
this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,colorcode)
};
this.htmlCodeChanged=function(){var colorcode=this.codeDisplay.value;
if(colorcode.charAt(0)!="#"){colorcode="#"+colorcode
}if(!this.validCode(colorcode)){return 
}var rgb=this.code2rgb(colorcode);
var hsl=this.rgb2hsl(rgb.red,rgb.green,rgb.blue);
this.setValues(rgb.red,rgb.green,rgb.blue,hsl.hue,hsl.sat,hsl.lum);
this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,colorcode)
};
this.rgbCodeChanged=function(){var red=this.redComponent.value-0;
if(red>255){red=255
}if(red<0){red=0
}var green=this.greenComponent.value-0;
if(green>255){green=255
}if(green<0){green=0
}var blue=this.blueComponent.value-0;
if(blue>255){blue=255
}if(blue<0){blue=0
}if(red!=red||green!=green||blue!=blue){this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,this.storedValue);
return 
}var colorcode=this.rgb2code(red,green,blue);
if(!this.validCode(colorcode)){this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,this.storedValue);
return 
}var hsl=this.rgb2hsl(red,green,blue);
this.setValues(red,green,blue,hsl.hue,hsl.sat,hsl.lum);
this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,colorcode)
};
this.hslCodeChanged=function(){var hue=this.hueComponent.value-0;
if(hue>240){hue=240
}if(hue<0){hue=0
}var sat=this.satComponent.value-0;
if(sat>240){sat=240
}if(sat<0){sat=0
}var lum=this.lumComponent.value-0;
if(lum>240){lum=240
}if(lum<0){lum=0
}if(hue!=hue||sat!=sat||lum!=lum){this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,this.storedValue);
return 
}var colorcode=this.hsl2code(hue,sat,lum);
if(!this.validCode(colorcode)){this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,this.storedValue);
return 
}var rgb=this.hsl2rgb(hue,sat,lum);
this.setValues(rgb.red,rgb.green,rgb.blue,hue,sat,lum);
this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,colorcode)
};
this.colorPicked=function(event,elt){if(elt==this.hsmap){this.hsChanged(event,elt)
}else{if(elt==this.lmap||elt==this.lpointer){this.lChanged(event,elt)
}}};
this.mouseDown=function(event,elt){if(event.button!=this.LMB){return true
}else{this.picking=true;
this.crtPickingTarget=elt;
this.colorPicked(event,elt);
try{event.preventDefault()
}catch(e){event.returnValue=false
}return false
}};
this.mouseMove=function(event,elt){if(this.picking==true&&this.crtPickingTarget==elt){if(window.ActiveXObject){if(event.srcElement==this.crtPickingTarget){this.colorPicked(event,elt)
}}else{this.colorPicked(event,elt)
}}event.returnValue=false
};
this.mouseUp=function(event,elt){this.picking=false;
this.crtPickingTarget=null
};
this.filterKeys=function(event){};
this.show=function(requester,color){if(color===undefined){color=requester.value
}if(!color.match("(#[0-9a-fA-F]{6})")){color="#dddddd"
}this.requester=requester;
this.storedValue=color;
var rgb=this.code2rgb(color);
var hsl=this.rgb2hsl(rgb.red,rgb.green,rgb.blue);
this.setValues(rgb.red,rgb.green,rgb.blue,hsl.hue,hsl.sat,hsl.lum);
this.setComponents(this.red,this.green,this.blue,this.hue,this.sat,this.lum,color);
this.container.style.display="block";
if(window.ActiveXObject){var selects=document.getElementsByTagName("select");
for(var i=0;
i<selects.length;
i++){selects.item(i).style.visibility="hidden"
}}if(document.width&&document.getBoxObjectFor){this.fieldset.style.left=this.max3(document.width/2-document.getBoxObjectFor(this.fieldset).width/2,0,0)+"px"
}else{if(document.body.clientWidth){this.fieldset.style.left=this.max3(document.body.clientWidth/2-this.fieldset.clientWidth/2,0,0)+"px"
}}};
this.OK=function(event){if(window.ActiveXObject){var selects=document.getElementsByTagName("select");
for(var i=0;
i<selects.length;
i++){selects.item(i).style.visibility="visible"
}}this.requester.value=this.storedValue;
this.container.style.display="none";
this.requester.focus();
eval(this.requester.getAttribute("onfocus"))
};
this.Cancel=function(event){if(window.ActiveXObject){var selects=document.getElementsByTagName("select");
for(var i=0;
i<selects.length;
i++){selects.item(i).style.visibility="visible"
}}this.container.style.display="none";
this.requester.focus()
}
}function createColorPicker(){window.colorPicker=new ColorPicker(document.getElementById("chwColorpickerHSMap"),document.getElementById("chwColorpickerLMap"),document.getElementById("chwColorpickerLPointer"),document.getElementById("chwColorPickerShow"),document.getElementById("chwColorCodeDisplay"))
};