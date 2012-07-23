if(typeof Effect=="undefined"){throw ("accordion.js requires including script.aculo.us' effects.js library!")
}var accordion=Class.create({showAccordion:null,currentAccordion:null,duration:null,effects:[],animating:false,initialize:function(B,C){if(!$(B)){throw (B+" doesn't exist!");
return false
}this.options=Object.extend({resizeSpeed:8,classNames:{toggle:"accordion_toggle",toggleActive:"accordion_toggle_active",content:"accordion_content"},defaultSize:{height:null,width:null},direction:"vertical",onEvent:"click"},C||{});
this.duration=((11-this.options.resizeSpeed)*0.15);
var A=$$("#"+B+" ."+this.options.classNames.toggle);
A.each(function(D){Event.observe(D,this.options.onEvent,this.activate.bind(this,D),false);
if(this.options.onEvent=="click"){D.onclick=function(){return false
}
}if(this.options.direction=="horizontal"){var E={width:"0px"}
}else{var E={height:"0px"}
}Object.extend(E,{display:"none"});
this.currentAccordion=$(D.next(0)).setStyle(E)
}.bind(this))
},activate:function(A){if(this.animating){return false
}this.effects=[];
this.currentAccordion=$(A.next(0));
this.currentAccordion.setStyle({display:"block"});
this.currentAccordion.previous(0).addClassName(this.options.classNames.toggleActive);
if(this.options.direction=="horizontal"){this.scaling={scaleX:true,scaleY:false}
}else{this.scaling={scaleX:false,scaleY:true}
}if(this.currentAccordion==this.showAccordion){this.deactivate()
}else{this._handleAccordion()
}},deactivate:function(){var A={duration:this.duration,scaleContent:false,transition:Effect.Transitions.sinoidal,queue:{position:"end",scope:"accordionAnimation"},scaleMode:{originalHeight:this.options.defaultSize.height?this.options.defaultSize.height:this.currentAccordion.scrollHeight,originalWidth:this.options.defaultSize.width?this.options.defaultSize.width:this.currentAccordion.scrollWidth},afterFinish:function(){this.showAccordion.setStyle({height:"0px",display:"none"});
this.showAccordion=null;
this.animating=false
}.bind(this)};
Object.extend(A,this.scaling);
this.showAccordion.previous(0).removeClassName(this.options.classNames.toggleActive);
new Effect.Scale(this.showAccordion,0,A)
},_handleAccordion:function(){var A={sync:true,scaleFrom:0,scaleContent:false,transition:Effect.Transitions.sinoidal,scaleMode:{originalHeight:this.options.defaultSize.height?this.options.defaultSize.height:this.currentAccordion.scrollHeight,originalWidth:this.options.defaultSize.width?this.options.defaultSize.width:this.currentAccordion.scrollWidth}};
Object.extend(A,this.scaling);
this.effects.push(new Effect.Scale(this.currentAccordion,100,A));
if(this.showAccordion){this.showAccordion.previous(0).removeClassName(this.options.classNames.toggleActive);
A={sync:true,scaleContent:false,transition:Effect.Transitions.sinoidal};
Object.extend(A,this.scaling);
this.effects.push(new Effect.Scale(this.showAccordion,0,A))
}new Effect.Parallel(this.effects,{duration:this.duration,queue:{position:"end",scope:"accordionAnimation"},beforeStart:function(){this.animating=true
}.bind(this),afterFinish:function(){if(this.showAccordion){this.showAccordion.setStyle({display:"none"})
}this.showAccordion=this.currentAccordion;
this.animating=false
}.bind(this)})
}});
function createAccordion(B){var A=new accordion(B.div,{resizeSpeed:10,classNames:{toggle:"accordionTabTitleBar",content:"accordionTabContentBox"},defaultSize:{width:("width" in B?B.width:null),height:("height" in B?B.height:null)}});
A.activate($$("#"+B.div+" .accordionTabTitleBar")[B.no])
};