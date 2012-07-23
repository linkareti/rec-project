if(typeof YAHOO=="undefined"){YAHOO={}
}YAHOO.namespace=function(C){if(!C||!C.length){return null
}var D=C.split(".");
var B=YAHOO;
for(var A=(D[0]=="YAHOO")?1:0;
A<D.length;
++A){B[D[A]]=B[D[A]]||{};
B=B[D[A]]
}return B
};
YAHOO.log=function(C,D,A){var B=YAHOO.widget.Logger;
if(B&&B.log){return B.log(C,D,A)
}else{return false
}};
YAHOO.extend=function(A,C){var B=function(){};
B.prototype=C.prototype;
A.prototype=new B();
A.prototype.constructor=A;
A.superclass=C.prototype;
if(C.prototype.constructor==Object.prototype.constructor){C.prototype.constructor=C
}};
YAHOO.namespace("util");
YAHOO.namespace("widget");
YAHOO.namespace("example");