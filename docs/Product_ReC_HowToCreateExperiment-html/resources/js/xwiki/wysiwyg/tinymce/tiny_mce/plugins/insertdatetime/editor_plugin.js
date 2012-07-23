tinyMCE.importPluginLanguagePack("insertdatetime","en,tr,cs,el,fr_ca,it,ko,sv,zh_cn,fa,fr,de,pl,pt_br,nl,da,he,nb,ru,ru_KOI8-R,ru_UTF-8,nn,fi,es,cy,is,pl");
var TinyMCE_InsertDateTimePlugin={getInfo:function(){return{longname:"Insert date/time",author:"Moxiecode Systems",authorurl:"http://tinymce.moxiecode.com",infourl:"http://tinymce.moxiecode.com/tinymce/docs/plugin_insertdatetime.html",version:tinyMCE.majorVersion+"."+tinyMCE.minorVersion}
},getControlHTML:function(A){switch(A){case"insertdate":return tinyMCE.getButtonHTML(A,"lang_insertdate_desc","{$pluginurl}/images/insertdate.gif","mceInsertDate");
case"inserttime":return tinyMCE.getButtonHTML(A,"lang_inserttime_desc","{$pluginurl}/images/inserttime.gif","mceInsertTime")
}return""
},execCommand:function(F,C,E,G,D){function B(J,H){J=""+J;
if(J.length<H){for(var I=0;
I<(H-J.length);
I++){J="0"+J
}}return J
}function A(H,I){I=tinyMCE.regexpReplace(I,"%D","%m/%d/%y");
I=tinyMCE.regexpReplace(I,"%r","%I:%M:%S %p");
I=tinyMCE.regexpReplace(I,"%Y",""+H.getFullYear());
I=tinyMCE.regexpReplace(I,"%y",""+H.getYear());
I=tinyMCE.regexpReplace(I,"%m",B(H.getMonth()+1,2));
I=tinyMCE.regexpReplace(I,"%d",B(H.getDate(),2));
I=tinyMCE.regexpReplace(I,"%H",""+B(H.getHours(),2));
I=tinyMCE.regexpReplace(I,"%M",""+B(H.getMinutes(),2));
I=tinyMCE.regexpReplace(I,"%S",""+B(H.getSeconds(),2));
I=tinyMCE.regexpReplace(I,"%I",""+((H.getHours()+11)%12+1));
I=tinyMCE.regexpReplace(I,"%p",""+(H.getHours()<12?"AM":"PM"));
I=tinyMCE.regexpReplace(I,"%B",""+tinyMCE.getLang("lang_inserttime_months_long")[H.getMonth()]);
I=tinyMCE.regexpReplace(I,"%b",""+tinyMCE.getLang("lang_inserttime_months_short")[H.getMonth()]);
I=tinyMCE.regexpReplace(I,"%A",""+tinyMCE.getLang("lang_inserttime_day_long")[H.getDay()]);
I=tinyMCE.regexpReplace(I,"%a",""+tinyMCE.getLang("lang_inserttime_day_short")[H.getDay()]);
I=tinyMCE.regexpReplace(I,"%%","%");
return I
}switch(E){case"mceInsertDate":tinyMCE.execInstanceCommand(F,"mceInsertContent",false,A(new Date(),tinyMCE.getParam("plugin_insertdate_dateFormat",tinyMCE.getLang("lang_insertdate_def_fmt"))));
return true;
case"mceInsertTime":tinyMCE.execInstanceCommand(F,"mceInsertContent",false,A(new Date(),tinyMCE.getParam("plugin_insertdate_timeFormat",tinyMCE.getLang("lang_inserttime_def_fmt"))));
return true
}return false
}};
tinyMCE.addPlugin("insertdatetime",TinyMCE_InsertDateTimePlugin);