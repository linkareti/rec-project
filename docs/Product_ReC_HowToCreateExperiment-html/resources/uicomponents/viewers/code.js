if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.viewers)=="undefined"){XWiki.viewers=new Object()
}XWiki.viewers.Code=Class.create({initialize:function(A){this.showingLineNumbers=A;
this.toggleLink=$("toggleLineNumbers");
this.showText="$msg.get('core.viewers.code.showLineNumbers')";
this.hideText="$msg.get('core.viewers.code.hideLineNumbers')";
if(this.toggleLink){this.textarea=this.toggleLink.up().down("textarea");
if(this.textarea){this.attachToggleListener()
}}},attachToggleListener:function(){this.toggleLink.href="";
this.toggleLink.observe("click",this.toggleLineNumbers.bindAsEventListener(this))
},toggleLineNumbers:function(F){if(F){F.stop()
}var D="\n";
var B=this.textarea.value.split(D);
var G=B.size()-1;
var C=Math.ceil(Math.log(G+1)/Math.LN10);
for(var E=0;
E<G;
++E){if(this.showingLineNumbers){B[E]=B[E].replace(/^\s*[0-9]+:\s/,"")
}else{var A=E+1+"";
B[E]=" ".times(C-A.length)+A+": "+B[E]
}}this.textarea.value=B.join(D);
this.showingLineNumbers=!this.showingLineNumbers;
if(this.showingLineNumbers){this.toggleLink.update(this.hideText)
}else{this.toggleLink.update(this.showText)
}}});
document.observe("dom:loaded",function(){var A=true;
if(window.location.search.indexOf("showlinenumbers=0")>=0){A=false
}new XWiki.viewers.Code(A)
});