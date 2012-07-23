if(typeof (XWiki)=="undefined"||typeof (XWiki.widgets)=="undefined"||typeof (XWiki.widgets.ConfirmationBox)=="undefined"){if(typeof console!="undefined"&&typeof console.warn=="function"){console.warn("[MessageBox widget] Required class missing: XWiki.widgets.ModalPopup")
}}else{XWiki.widgets.ConfirmedAjaxRequest=Class.create(XWiki.widgets.ConfirmationBox,{defaultAjaxRequestParameters:{on1223:function(A){A.request.options.onSuccess(A)
},on0:function(A){A.request.options.onFailure(A)
}},initialize:function($super,B,A,C){this.interactionParameters=Object.extend({displayProgressMessage:true,progressMessageText:"$msg.get('core.widgets.confirmationBox.notification.inProgress')",displaySuccessMessage:true,successMessageText:"$msg.get('core.widgets.confirmationBox.notification.done')",displayFailureMessage:true,failureMessageText:"$msg.get('core.widgets.confirmationBox.notification.failed')"},C||{});
this.requestUrl=B;
this.ajaxRequestParameters=Object.extend(Object.clone(this.defaultAjaxRequestParameters),A||{});
Object.extend(this.ajaxRequestParameters,{onSuccess:function(){if(this.interactionParameters.displaySuccessMessage){if(this.progressNotification){this.progressNotification.replace(new XWiki.widgets.Notification(this.interactionParameters.successMessageText,"done"))
}else{new XWiki.widgets.Notification(this.interactionParameters.successMessageText,"done")
}}else{if(this.progressNotification){this.progressNotification.hide()
}}if(A.onSuccess){A.onSuccess.apply(this,arguments)
}}.bind(this),onFailure:function(D){if(this.interactionParameters.displayFailureMessage){var E=D.statusText;
if(D.statusText==""||D.status==12031){E="Server not responding"
}if(this.progressNotification){this.progressNotification.replace(new XWiki.widgets.Notification(this.interactionParameters.failureMessageText+E,"error"))
}else{new XWiki.widgets.Notification(this.interactionParameters.failureMessageText+E,"error")
}}else{if(this.progressNotification){this.progressNotification.hide()
}}if(A.onFailure){A.onFailure.apply(this,arguments)
}}.bind(this)});
$super({onYes:function(){if(this.interactionParameters.displayProgressMessage){this.progressNotification=new XWiki.widgets.Notification(this.interactionParameters.progressMessageText,"inprogress")
}new Ajax.Request(this.requestUrl,this.ajaxRequestParameters)
}.bind(this)},this.interactionParameters)
}})
};