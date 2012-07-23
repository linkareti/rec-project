if(typeof ytreeview=="undefined"){ytreeview=1;
YAHOO.widget.TreeView=function(A){if(A){this.init(A)
}};
YAHOO.widget.TreeView.nodeCount=0;
YAHOO.widget.TreeView.prototype={id:null,_el:null,_nodes:null,locked:false,_expandAnim:null,_collapseAnim:null,_animCount:0,maxAnim:2,setExpandAnim:function(A){if(YAHOO.widget.TVAnim.isValid(A)){this._expandAnim=A
}},setCollapseAnim:function(A){if(YAHOO.widget.TVAnim.isValid(A)){this._collapseAnim=A
}},animateExpand:function(C){if(this._expandAnim&&this._animCount<this.maxAnim){var A=this;
var B=YAHOO.widget.TVAnim.getAnim(this._expandAnim,C,function(){A.expandComplete()
});
if(B){++this._animCount;
B.animate()
}return true
}return false
},animateCollapse:function(C){if(this._collapseAnim&&this._animCount<this.maxAnim){var A=this;
var B=YAHOO.widget.TVAnim.getAnim(this._collapseAnim,C,function(){A.collapseComplete()
});
if(B){++this._animCount;
B.animate()
}return true
}return false
},expandComplete:function(){--this._animCount
},collapseComplete:function(){--this._animCount
},init:function(A){this.id=A;
if("string"!==typeof A){this._el=A;
this.id=this.generateId(A)
}this._nodes=[];
YAHOO.widget.TreeView.trees[this.id]=this;
this.root=new YAHOO.widget.RootNode(this)
},draw:function(){var A=this.root.getHtml();
this.getEl().innerHTML=A;
this.firstDraw=false
},getEl:function(){if(!this._el){this._el=document.getElementById(this.id)
}return this._el
},regNode:function(A){this._nodes[A.index]=A
},getRoot:function(){return this.root
},setDynamicLoad:function(A,B){this.root.setDynamicLoad(A,B)
},expandAll:function(){if(!this.locked){this.root.expandAll()
}},collapseAll:function(){if(!this.locked){this.root.collapseAll()
}},getNodeByIndex:function(B){var A=this._nodes[B];
return(A)?A:null
},getNodeByProperty:function(C,B){for(var A in this._nodes){var D=this._nodes[A];
if(D.data&&B==D.data[C]){return D
}}return null
},getNodesByProperty:function(D,C){var A=[];
for(var B in this._nodes){var E=this._nodes[B];
if(E.data&&C==E.data[D]){A.push(E)
}}return(A.length)?A:null
},removeNode:function(B,A){if(B.isRoot()){return false
}var C=B.parent;
if(C.parent){C=C.parent
}this._deleteNode(B);
if(A&&C&&C.childrenRendered){C.refresh()
}return true
},removeChildren:function(A){while(A.children.length){this._deleteNode(A.children[0])
}A.childrenRendered=false;
A.dynamicLoadComplete=false;
A.expand();
A.collapse()
},_deleteNode:function(A){this.removeChildren(A);
this.popNode(A)
},popNode:function(D){var E=D.parent;
var B=[];
for(var C=0,A=E.children.length;
C<A;
++C){if(E.children[C]!=D){B[B.length]=E.children[C]
}}E.children=B;
E.childrenRendered=false;
if(D.previousSibling){D.previousSibling.nextSibling=D.nextSibling
}if(D.nextSibling){D.nextSibling.previousSibling=D.previousSibling
}D.parent=null;
D.previousSibling=null;
D.nextSibling=null;
D.tree=null;
delete this._nodes[D.index]
},toString:function(){return"TreeView "+this.id
},generateId:function(A){var B=A.id;
if(!B){B="yui-tv-auto-id-"+YAHOO.widget.TreeView.counter;
YAHOO.widget.TreeView.counter++
}return B
},onExpand:function(A){},onCollapse:function(A){}};
YAHOO.widget.TreeView.trees=[];
YAHOO.widget.TreeView.counter=0;
YAHOO.widget.TreeView.getTree=function(B){var A=YAHOO.widget.TreeView.trees[B];
return(A)?A:null
};
YAHOO.widget.TreeView.getNode=function(B,C){var A=YAHOO.widget.TreeView.getTree(B);
return(A)?A.getNodeByIndex(C):null
};
YAHOO.widget.TreeView.addHandler=function(C,D,B,A){A=(A)?true:false;
if(C.addEventListener){C.addEventListener(D,B,A)
}else{if(C.attachEvent){C.attachEvent("on"+D,B)
}else{C["on"+D]=B
}}};
YAHOO.widget.TreeView.preload=function(E){E=E||"ygtv";
var C=["tn","tm","tmh","tp","tph","ln","lm","lmh","lp","lph","loading"];
var F=[];
for(var A=0;
A<C.length;
++A){F[F.length]='<span class="'+E+C[A]+'">&#160;</span>'
}var D=document.createElement("DIV");
var B=D.style;
B.position="absolute";
B.top="-1000px";
B.left="-1000px";
D.innerHTML=F.join("");
document.body.appendChild(D)
};
YAHOO.widget.TreeView.addHandler(window,"load",YAHOO.widget.TreeView.preload);
YAHOO.widget.Node=function(C,B,A){if(C){this.init(C,B,A)
}};
YAHOO.widget.Node.prototype={index:0,children:null,tree:null,data:null,parent:null,depth:-1,href:null,target:"_self",expanded:false,multiExpand:true,renderHidden:false,childrenRendered:false,dynamicLoadComplete:false,previousSibling:null,nextSibling:null,_dynLoad:false,dataLoader:null,isLoading:false,hasIcon:true,iconMode:0,_type:"Node",init:function(C,B,A){this.data=C;
this.children=[];
this.index=YAHOO.widget.TreeView.nodeCount;
++YAHOO.widget.TreeView.nodeCount;
this.expanded=A;
if(B){B.appendChild(this)
}},applyParent:function(B){if(!B){return false
}this.tree=B.tree;
this.parent=B;
this.depth=B.depth+1;
if(!this.href){this.href="javascript:"+this.getToggleLink()
}if(!this.multiExpand){this.multiExpand=B.multiExpand
}this.tree.regNode(this);
B.childrenRendered=false;
for(var C=0,A=this.children.length;
C<A;
++C){this.children[C].applyParent(this)
}return true
},appendChild:function(B){if(this.hasChildren()){var A=this.children[this.children.length-1];
A.nextSibling=B;
B.previousSibling=A
}this.children[this.children.length]=B;
B.applyParent(this);
return B
},appendTo:function(A){return A.appendChild(this)
},insertBefore:function(A){var C=A.parent;
if(C){if(this.tree){this.tree.popNode(this)
}var B=A.isChildOf(C);
C.children.splice(B,0,this);
if(A.previousSibling){A.previousSibling.nextSibling=this
}this.previousSibling=A.previousSibling;
this.nextSibling=A;
A.previousSibling=this;
this.applyParent(C)
}return this
},insertAfter:function(A){var C=A.parent;
if(C){if(this.tree){this.tree.popNode(this)
}var B=A.isChildOf(C);
if(!A.nextSibling){return this.appendTo(C)
}C.children.splice(B+1,0,this);
A.nextSibling.previousSibling=this;
this.previousSibling=A;
this.nextSibling=A.nextSibling;
A.nextSibling=this;
this.applyParent(C)
}return this
},isChildOf:function(B){if(B&&B.children){for(var C=0,A=B.children.length;
C<A;
++C){if(B.children[C]===this){return C
}}}return -1
},getSiblings:function(){return this.parent.children
},showChildren:function(){if(!this.tree.animateExpand(this.getChildrenEl())){if(this.hasChildren()){this.getChildrenEl().style.display=""
}}},hideChildren:function(){if(!this.tree.animateCollapse(this.getChildrenEl())){this.getChildrenEl().style.display="none"
}},getElId:function(){return"ygtv"+this.index
},getChildrenElId:function(){return"ygtvc"+this.index
},getToggleElId:function(){return"ygtvt"+this.index
},getEl:function(){return document.getElementById(this.getElId())
},getChildrenEl:function(){return document.getElementById(this.getChildrenElId())
},getToggleEl:function(){return document.getElementById(this.getToggleElId())
},getToggleLink:function(){return"YAHOO.widget.TreeView.getNode('"+this.tree.id+"',"+this.index+").toggle()"
},collapse:function(){if(!this.expanded){return 
}var A=this.tree.onCollapse(this);
if("undefined"!=typeof A&&!A){return 
}if(!this.getEl()){this.expanded=false;
return 
}this.hideChildren();
this.expanded=false;
if(this.hasIcon){this.getToggleEl().className=this.getStyle()
}},expand:function(){if(this.expanded){return 
}var A=this.tree.onExpand(this);
if("undefined"!=typeof A&&!A){return 
}if(!this.getEl()){this.expanded=true;
return 
}if(!this.childrenRendered){this.getChildrenEl().innerHTML=this.renderChildren()
}else{}this.expanded=true;
if(this.hasIcon){this.getToggleEl().className=this.getStyle()
}if(this.isLoading){this.expanded=false;
return 
}if(!this.multiExpand){var C=this.getSiblings();
for(var B=0;
B<C.length;
++B){if(C[B]!=this&&C[B].expanded){C[B].collapse()
}}}this.showChildren()
},getStyle:function(){if(this.isLoading){return"ygtvloading"
}else{var B=(this.nextSibling)?"t":"l";
var A="n";
if(this.hasChildren(true)||(this.isDynamic()&&!this.getIconMode())){A=(this.expanded)?"m":"p"
}return"ygtv"+B+A
}},getHoverStyle:function(){var A=this.getStyle();
if(this.hasChildren(true)&&!this.isLoading){A+="h"
}return A
},expandAll:function(){for(var A=0;
A<this.children.length;
++A){var B=this.children[A];
if(B.isDynamic()){alert("Not supported (lazy load + expand all)");
break
}else{if(!B.multiExpand){alert("Not supported (no multi-expand + expand all)");
break
}else{B.expand();
B.expandAll()
}}}},collapseAll:function(){for(var A=0;
A<this.children.length;
++A){this.children[A].collapse();
this.children[A].collapseAll()
}},setDynamicLoad:function(A,B){if(A){this.dataLoader=A;
this._dynLoad=true
}else{this.dataLoader=null;
this._dynLoad=false
}if(B){this.iconMode=B
}},isRoot:function(){return(this==this.tree.root)
},isDynamic:function(){var A=(!this.isRoot()&&(this._dynLoad||this.tree.root._dynLoad));
return A
},getIconMode:function(){return(this.iconMode||this.tree.root.iconMode)
},hasChildren:function(A){return(this.children.length>0||(A&&this.isDynamic()&&!this.dynamicLoadComplete))
},toggle:function(){if(!this.tree.locked&&(this.hasChildren(true)||this.isDynamic())){if(this.expanded){this.collapse()
}else{this.expand()
}}},getHtml:function(){var A=[];
A[A.length]='<div class="ygtvitem" id="'+this.getElId()+'">';
A[A.length]=this.getNodeHtml();
A[A.length]=this.getChildrenHtml();
A[A.length]="</div>";
return A.join("")
},getChildrenHtml:function(){var A=[];
A[A.length]='<div class="ygtvchildren"';
A[A.length]=' id="'+this.getChildrenElId()+'"';
if(!this.expanded){A[A.length]=' style="display:none;"'
}A[A.length]=">";
if((this.hasChildren(true)&&this.expanded)||(this.renderHidden&&!this.isDynamic())){A[A.length]=this.renderChildren()
}A[A.length]="</div>";
return A.join("")
},renderChildren:function(){var A=this;
if(this.isDynamic()&&!this.dynamicLoadComplete){this.isLoading=true;
this.tree.locked=true;
if(this.dataLoader){setTimeout(function(){A.dataLoader(A,function(){A.loadComplete()
})
},10)
}else{if(this.tree.root.dataLoader){setTimeout(function(){A.tree.root.dataLoader(A,function(){A.loadComplete()
})
},10)
}else{return"Error: data loader not found or not specified."
}}return""
}else{return this.completeRender()
}},completeRender:function(){var B=[];
for(var A=0;
A<this.children.length;
++A){this.children[A].childrenRendered=false;
B[B.length]=this.children[A].getHtml()
}this.childrenRendered=true;
return B.join("")
},loadComplete:function(){this.getChildrenEl().innerHTML=this.completeRender();
this.dynamicLoadComplete=true;
this.isLoading=false;
this.expand();
this.tree.locked=false
},getAncestor:function(B){if(B>=this.depth||B<0){return null
}var A=this.parent;
while(A.depth>B){A=A.parent
}return A
},getDepthStyle:function(A){return(this.getAncestor(A).nextSibling)?"ygtvdepthcell":"ygtvblankdepthcell"
},getNodeHtml:function(){return""
},refresh:function(){this.getChildrenEl().innerHTML=this.completeRender();
if(this.hasIcon){var A=this.getToggleEl();
if(A){A.className=this.getStyle()
}}},toString:function(){return"Node ("+this.index+")"
}};
YAHOO.widget.RootNode=function(A){this.init(null,null,true);
this.tree=A
};
YAHOO.widget.RootNode.prototype=new YAHOO.widget.Node();
YAHOO.widget.RootNode.prototype.getNodeHtml=function(){return""
};
YAHOO.widget.RootNode.prototype.toString=function(){return"RootNode"
};
YAHOO.widget.RootNode.prototype.loadComplete=function(){this.tree.draw()
};
YAHOO.widget.TextNode=function(C,B,A){if(C){this.init(C,B,A);
this.setUpLabel(C)
}};
YAHOO.widget.TextNode.prototype=new YAHOO.widget.Node();
YAHOO.widget.TextNode.prototype.labelStyle="ygtvlabel";
YAHOO.widget.TextNode.prototype.labelElId=null;
YAHOO.widget.TextNode.prototype.label=null;
YAHOO.widget.TextNode.prototype.setUpLabel=function(A){if(typeof A=="string"){A={label:A}
}this.label=A.label;
if(A.href){this.href=A.href
}if(A.target){this.target=A.target
}if(A.style){this.labelStyle=A.style
}this.labelElId="ygtvlabelel"+this.index
};
YAHOO.widget.TextNode.prototype.getLabelEl=function(){return document.getElementById(this.labelElId)
};
YAHOO.widget.TextNode.prototype.getNodeHtml=function(){var B=[];
B[B.length]='<table border="0" cellpadding="0" cellspacing="0">';
B[B.length]="<tr>";
for(i=0;
i<this.depth;
++i){B[B.length]='<td class="'+this.getDepthStyle(i)+'">&#160;</td>'
}var A="YAHOO.widget.TreeView.getNode('"+this.tree.id+"',"+this.index+")";
B[B.length]="<td";
B[B.length]=' id="'+this.getToggleElId()+'"';
B[B.length]=' class="'+this.getStyle()+'"';
if(this.hasChildren(true)){B[B.length]=' onmouseover="this.className=';
B[B.length]=A+'.getHoverStyle()"';
B[B.length]=' onmouseout="this.className=';
B[B.length]=A+'.getStyle()"'
}B[B.length]=' onclick="javascript:'+this.getToggleLink()+'">';
B[B.length]="&#160;";
B[B.length]="</td>";
B[B.length]="<td>";
B[B.length]="<a";
B[B.length]=' id="'+this.labelElId+'"';
B[B.length]=' class="'+this.labelStyle+'"';
B[B.length]=' href="'+this.href+'"';
B[B.length]=' target="'+this.target+'"';
B[B.length]=' onclick="return '+A+".onLabelClick("+A+')"';
if(this.hasChildren(true)){B[B.length]=" onmouseover=\"document.getElementById('";
B[B.length]=this.getToggleElId()+"').className=";
B[B.length]=A+'.getHoverStyle()"';
B[B.length]=" onmouseout=\"document.getElementById('";
B[B.length]=this.getToggleElId()+"').className=";
B[B.length]=A+'.getStyle()"'
}B[B.length]=" >";
B[B.length]=this.label;
B[B.length]="</a>";
B[B.length]="</td>";
B[B.length]="</tr>";
B[B.length]="</table>";
return B.join("")
};
YAHOO.widget.TextNode.prototype.onLabelClick=function(A){};
YAHOO.widget.TextNode.prototype.toString=function(){return"TextNode ("+this.index+") "+this.label
};
YAHOO.widget.MenuNode=function(C,B,A){if(C){this.init(C,B,A);
this.setUpLabel(C)
}this.multiExpand=false
};
YAHOO.widget.MenuNode.prototype=new YAHOO.widget.TextNode();
YAHOO.widget.MenuNode.prototype.toString=function(){return"MenuNode ("+this.index+") "+this.label
};
YAHOO.widget.HTMLNode=function(D,C,B,A){if(D){this.init(D,C,B);
this.initContent(D,A)
}};
YAHOO.widget.HTMLNode.prototype=new YAHOO.widget.Node();
YAHOO.widget.HTMLNode.prototype.contentStyle="ygtvhtml";
YAHOO.widget.HTMLNode.prototype.contentElId=null;
YAHOO.widget.HTMLNode.prototype.content=null;
YAHOO.widget.HTMLNode.prototype.initContent=function(B,A){if(typeof B=="string"){B={html:B}
}this.html=B.html;
this.contentElId="ygtvcontentel"+this.index;
this.hasIcon=A
};
YAHOO.widget.HTMLNode.prototype.getContentEl=function(){return document.getElementById(this.contentElId)
};
YAHOO.widget.HTMLNode.prototype.getNodeHtml=function(){var A=[];
A[A.length]='<table border="0" cellpadding="0" cellspacing="0">';
A[A.length]="<tr>";
for(i=0;
i<this.depth;
++i){A[A.length]='<td class="'+this.getDepthStyle(i)+'">&#160;</td>'
}if(this.hasIcon){A[A.length]="<td";
A[A.length]=' id="'+this.getToggleElId()+'"';
A[A.length]=' class="'+this.getStyle()+'"';
A[A.length]=' onclick="javascript:'+this.getToggleLink()+'"';
if(this.hasChildren(true)){A[A.length]=' onmouseover="this.className=';
A[A.length]="YAHOO.widget.TreeView.getNode('";
A[A.length]=this.tree.id+"',"+this.index+').getHoverStyle()"';
A[A.length]=' onmouseout="this.className=';
A[A.length]="YAHOO.widget.TreeView.getNode('";
A[A.length]=this.tree.id+"',"+this.index+').getStyle()"'
}A[A.length]=">&#160;</td>"
}A[A.length]="<td";
A[A.length]=' id="'+this.contentElId+'"';
A[A.length]=' class="'+this.contentStyle+'"';
A[A.length]=" >";
A[A.length]=this.html;
A[A.length]="</td>";
A[A.length]="</tr>";
A[A.length]="</table>";
return A.join("")
};
YAHOO.widget.HTMLNode.prototype.toString=function(){return"HTMLNode ("+this.index+")"
};
YAHOO.widget.TVAnim=function(){return{FADE_IN:"TVFadeIn",FADE_OUT:"TVFadeOut",getAnim:function(B,A,C){if(YAHOO.widget[B]){return new YAHOO.widget[B](A,C)
}else{return null
}},isValid:function(A){return(YAHOO.widget[A])
}}
}();
YAHOO.widget.TVFadeIn=function(A,B){this.el=A;
this.callback=B
};
YAHOO.widget.TVFadeIn.prototype={animate:function(){var D=this;
var C=this.el.style;
C.opacity=0.1;
C.filter="alpha(opacity=10)";
C.display="";
var B=0.4;
var A=new YAHOO.util.Anim(this.el,{opacity:{from:0.1,to:1,unit:""}},B);
A.onComplete.subscribe(function(){D.onComplete()
});
A.animate()
},onComplete:function(){this.callback()
},toString:function(){return"TVFadeIn"
}};
YAHOO.widget.TVFadeOut=function(A,B){this.el=A;
this.callback=B
};
YAHOO.widget.TVFadeOut.prototype={animate:function(){var C=this;
var B=0.4;
var A=new YAHOO.util.Anim(this.el,{opacity:{from:1,to:0.1,unit:""}},B);
A.onComplete.subscribe(function(){C.onComplete()
});
A.animate()
},onComplete:function(){var A=this.el.style;
A.display="none";
A.filter="alpha(opacity=100)";
this.callback()
},toString:function(){return"TVFadeOut"
}}
};