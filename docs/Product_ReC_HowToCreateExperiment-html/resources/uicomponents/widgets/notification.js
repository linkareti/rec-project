if(typeof (XWiki)=="undefined"){XWiki=new Object()
}if(typeof (XWiki.widgets)=="undefined"){XWiki.widgets=new Object()
}XWiki.widgets.Notification=Class.create({text:"Hello world!",defaultOptions:{plain:{timeout:5},info:{timeout:5},warning:{timeout:5},error:{timeout:10},inprogress:{timeout:false},done:{timeout:2}},initialize:function(C,B,A){this.text=C||this.text;
this.type=(typeof this.defaultOptions[B]!="undefined")?B:"plain";
this.options=Object.extend(Object.clone(this.defaultOptions[this.type]),A||{});
this.createElement();
if(!this.options.inactive){this.show()
}},createElement:function(){if(!this.element){this.element=new Element("div",{"class":"xnotification xnotification-"+this.type}).update(this.text);
if(this.options.icon){this.element.setStyle({backgroundImage:this.options.icon,paddingLeft:"22px"})
}if(this.options.backgroundColor){this.element.setStyle({backgroundColor:this.options.backgroundColor})
}if(this.options.color){this.element.setStyle({color:this.options.color})
}this.element=this.element.wrap(new Element("div",{"class":"xnotification-wrapper"}));
Event.observe(this.element,"click",this.hide.bindAsEventListener(this))
}},show:function(){if(!this.element.descendantOf(XWiki.widgets.Notification.getContainer())){XWiki.widgets.Notification.getContainer().insert({top:this.element})
}this.element.show();
if(this.options.timeout){this.timer=window.setTimeout(this.hide.bind(this),this.options.timeout*1000)
}},hide:function(){this.element.hide();
if(this.element.parentNode){this.element.remove()
}if(this.timer){window.clearTimeout(this.timer);
this.timer=null
}},replace:function(A){if(this.element.parentNode){this.element.replace(A.element)
}if(this.timer){window.clearTimeout(this.timer);
this.timer=null
}A.show()
}});
XWiki.widgets.Notification.container=null;
XWiki.widgets.Notification.getContainer=function(){if(!XWiki.widgets.Notification.container){XWiki.widgets.Notification.container=new Element("div",{"class":"xnotification-container"});
$(document.body).insert(XWiki.widgets.Notification.container);
if(Prototype.Browser.IE){XWiki.widgets.Notification.container.setStyle({position:"absolute",bottom:"0px"});
Event.observe(window,"scroll",function(){var A=new Element("div");
XWiki.widgets.Notification.container.insert({top:A});
setTimeout(A.remove.bind(A),1)
})
}}return XWiki.widgets.Notification.container
};