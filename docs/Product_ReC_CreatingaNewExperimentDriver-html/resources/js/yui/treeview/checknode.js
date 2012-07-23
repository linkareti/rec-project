if(typeof ychecknode=="undefined"){ychecknode=1;
YAHOO.widget.CheckNode=function(D,E,C,A,B){if(E){this.init(D,C,A);
this.setUpLabel(E);
if(B&&B===true){this.check()
}}};
YAHOO.widget.CheckNode.prototype=new YAHOO.widget.TextNode();
YAHOO.widget.CheckNode.prototype.checked=false;
YAHOO.widget.CheckNode.prototype.checkState=0;
YAHOO.widget.CheckNode.prototype.getCheckElId=function(){return"ygtvcheck"+this.index
};
YAHOO.widget.CheckNode.prototype.getCheckEl=function(){return document.getElementById(this.getCheckElId())
};
YAHOO.widget.CheckNode.prototype.getCheckStyle=function(){return"ygtvcheck"+this.checkState
};
YAHOO.widget.CheckNode.prototype.getCheckLink=function(){return"YAHOO.widget.TreeView.getNode('"+this.tree.id+"',"+this.index+").checkClick()"
};
YAHOO.widget.CheckNode.prototype.checkClick=function(){if(this.checkState===0){this.check()
}else{this.uncheck()
}this.onCheckClick()
};
YAHOO.widget.CheckNode.prototype.onCheckClick=function(){};
YAHOO.widget.CheckNode.prototype.updateParent=function(){var D=this.parent;
if(!D||!D.updateParent){return 
}var A=false;
var C=false;
for(var B=0;
B<D.children.length;
++B){if(D.children[B].checked){A=true;
if(D.children[B].checkState==1){C=true
}}else{C=true
}}if(A){D.setCheckState(2)
}else{}D.updateCheckHtml();
D.updateParent()
};
YAHOO.widget.CheckNode.prototype.updateCheckHtml=function(){if(this.parent&&this.parent.childrenRendered){this.getCheckEl().className=this.getCheckStyle()
}};
YAHOO.widget.CheckNode.prototype.setCheckState=function(A){this.checkState=A;
this.checked=(A>0)
};
YAHOO.widget.CheckNode.prototype.check=function(){this.setCheckState(2);
for(var A=0;
A<this.children.length;
++A){}this.updateCheckHtml();
this.updateParent()
};
YAHOO.widget.CheckNode.prototype.uncheck=function(){this.setCheckState(0);
for(var A=0;
A<this.children.length;
++A){this.children[A].uncheck()
}this.updateCheckHtml();
this.updateParent()
};
YAHOO.widget.CheckNode.prototype.getNodeHtml=function(){var A=new Array();
A[A.length]='<table border="0" cellpadding="0" cellspacing="0">';
A[A.length]="<tr>";
for(i=0;
i<this.depth;
++i){A[A.length]='<td class="'+this.getDepthStyle(i)+'">&#160;</td>'
}A[A.length]="<td";
A[A.length]=' id="'+this.getToggleElId()+'"';
A[A.length]=' class="'+this.getStyle()+'"';
if(this.hasChildren(true)){A[A.length]=' onmouseover="this.className=';
A[A.length]="YAHOO.widget.TreeView.getNode('";
A[A.length]=this.tree.id+"',"+this.index+').getHoverStyle()"';
A[A.length]=' onmouseout="this.className=';
A[A.length]="YAHOO.widget.TreeView.getNode('";
A[A.length]=this.tree.id+"',"+this.index+').getStyle()"'
}A[A.length]=' onclick="javascript:'+this.getToggleLink()+'">&#160;';
A[A.length]="</td>";
A[A.length]="<td";
A[A.length]=' id="'+this.getCheckElId()+'"';
A[A.length]=' class="'+this.getCheckStyle()+'"';
A[A.length]=' onclick="javascript:'+this.getCheckLink()+'">';
A[A.length]="&#160;</td>";
A[A.length]="<td>";
A[A.length]="<a";
A[A.length]=' id="'+this.labelElId+'"';
A[A.length]=' class="'+this.labelStyle+'"';
A[A.length]=' href="'+this.href+'"';
A[A.length]=' target="'+this.target+'"';
if(this.hasChildren(true)){A[A.length]=" onmouseover=\"document.getElementById('";
A[A.length]=this.getToggleElId()+"').className=";
A[A.length]="YAHOO.widget.TreeView.getNode('";
A[A.length]=this.tree.id+"',"+this.index+').getHoverStyle()"';
A[A.length]=" onmouseout=\"document.getElementById('";
A[A.length]=this.getToggleElId()+"').className=";
A[A.length]="YAHOO.widget.TreeView.getNode('";
A[A.length]=this.tree.id+"',"+this.index+').getStyle()"'
}A[A.length]=" >";
A[A.length]=this.label;
A[A.length]="</a>";
A[A.length]="</td>";
A[A.length]="</tr>";
A[A.length]="</table>";
return A.join("")
};
YAHOO.widget.CheckNode.prototype.toString=function(){return"CheckNode ("+this.index+") "+this.label
}
};