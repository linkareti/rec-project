var offsetxpoint=-60;
var offsetypoint=20;
var ie=document.all;
var ns6=document.getElementById&&!document.all;
var enabletip=false;
var tipobj=$("dhtmltooltip");
var tippedNode=undefined;
function ietruebody(){return(document.compatMode&&document.compatMode!="BackCompat")?document.documentElement:document.body
}function showtip(C,A,B,D){Event.observe(document,"mousemove",positionTip);
Event.observe(C,"mouseout",hideTip);
if(D){tipobj.style.textAlign=D
}tippedNode=C;
tipobj.style.visibility="hidden";
tipobj.style.display="block";
tipobj.style.width="auto";
tipobj.innerHTML=A;
if(tipobj.offsetWidth>B){tipobj.style.width=B+"px"
}tipobj.style.display="none";
tipobj.style.visibility="visible";
enabletip=true;
return false
}function positionTip(E){if(enabletip){var B=Event.pointerX(E);
var A=Event.pointerY(E);
var D=ie&&!window.opera?ietruebody().clientWidth-event.clientX-offsetxpoint:window.innerWidth-E.clientX-offsetxpoint-20;
var C=ie&&!window.opera?ietruebody().clientHeight-event.clientY-offsetypoint:window.innerHeight-E.clientY-offsetypoint-20;
var F=(offsetxpoint<0)?offsetxpoint*(-1):-1000;
if(D<tipobj.offsetWidth){tipobj.style.left=ie?ietruebody().scrollLeft+event.clientX-tipobj.offsetWidth+"px":window.pageXOffset+E.clientX-tipobj.offsetWidth+"px"
}else{if(B<F){tipobj.style.left="5px"
}else{tipobj.style.left=B+offsetxpoint+"px"
}}if(C<tipobj.offsetHeight){tipobj.style.top=ie?ietruebody().scrollTop+event.clientY-tipobj.offsetHeight-offsetypoint+"px":window.pageYOffset+E.clientY-tipobj.offsetHeight-offsetypoint+"px"
}else{tipobj.style.top=A+offsetypoint+"px"
}tipobj.style.display="block"
}}function hideTip(A){if(!window.enabletip){return 
}Event.stopObserving(document,"mousemove",positionTip);
enabletip=false;
tipobj.style.display="none"
}Event.observe(window,"load",function(){$("body").appendChild(tipobj)
});