var Drag={obj:null,init:function(A,B){A.onmousedown=Drag.start;
A.root=B;
if(isNaN(parseInt(A.root.style.left))){A.root.style.left="0px"
}if(isNaN(parseInt(A.root.style.top))){A.root.style.top="0px"
}A.root.onDragStart=new Function();
A.root.onDragEnd=new Function();
A.root.onDrag=new Function()
},start:function(A){var B=Drag.obj=this;
A=Drag.fixE(A);
var D=parseInt(B.root.style.top);
var C=parseInt(B.root.style.left);
B.lastMouseX=A.clientX;
B.lastMouseY=A.clientY;
B.root.onDragStart(C,D,A.clientX,A.clientY);
document.onmousemove=Drag.drag;
document.onmouseup=Drag.end;
return false
},drag:function(B){B=Drag.fixE(B);
var G=Drag.obj;
var H=B.clientY;
var A=B.clientX;
var F=parseInt(G.root.style.top);
var E=parseInt(G.root.style.left);
var C,D;
C=E+A-G.lastMouseX;
D=F+H-G.lastMouseY;
G.root.style.left=C+"px";
G.root.style.top=D+"px";
G.lastMouseX=A;
G.lastMouseY=H;
G.root.onDrag(C,D,H,A);
return false
},end:function(){document.onmousemove=null;
document.onmouseup=null;
Drag.obj.root.onDragEnd(parseInt(Drag.obj.root.style.left),parseInt(Drag.obj.root.style.top));
Drag.obj=null
},fixE:function(A){if(typeof A=="undefined"){A=window.event
}if(typeof A.layerX=="undefined"){A.layerX=A.offsetX
}if(typeof A.layerY=="undefined"){A.layerY=A.offsetY
}return A
}};