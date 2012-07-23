(function(){function A(B){if(typeof console!="undefined"&&typeof console.warn=="function"){console.warn(B)
}}window.displayDocExtra=XWiki.displayDocExtra.wrap(function(){A("window.displayDocExtra is deprecated since XWiki 1.9M2. Use XWiki.displayDocExtra instead.");
var B=$A(arguments),C=B.shift();
return C.apply(window,B)
});
if(typeof XWiki.widgets=="object"&&typeof XWiki.widgets.LiveTable=="function"){window.ASSTable=Class.create(XWiki.widgets.LiveTable,{initialize:function($super,B,F,E,C,G,H,I,D){A("window.ASSTable is deprecated since XWiki 1.9M2. Use XWiki.widgets.LiveTable instead.");
if($("showLimits")){if($("showLimits").up("tr")){$("showLimits").up("tr").insert({after:new Element("tr").update(new Element("td").update(new Element("div",{id:E+"-pagination","class":"xwiki-grid-pagination-content"})))})
}$("showLimits").id=E+"-limits"
}if($("scrollbar1")&&$("scrollbar1").up("td")){if($("scrollbar1").up("td").next()){$("scrollbar1").up("td").next().remove()
}$("scrollbar1").up("td").remove()
}if($("table-filters")){$("table-filters").id=E+"-filters"
}$super(B,E,H,{action:D})
}})
}})();