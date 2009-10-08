package com.linkare.rec.impl.newface;

/**
 * Holds the ReC System properties. Maps the property name and the required flag.
 */
public enum ReCSystemProperty {

    //RECBASEUICONFIG("ReCBaseUIConfig", true),
    RECFACECONFIG("ReCFaceConfig", true), REC_MULTICASTCONTROLLER_BINDNAME("ReC.MultiCastController.BindName", true), REC_MULTICASTCONTROLLER_INITREF(
	    "ReC.MultiCastController.InitRef", true), OPENORB_CONFIG("openorb.config", true), OPENORB_PROFILE(
	    "openorb.profile", true), ORG_OMG_CORBA_ORBCLASS("org.omg.CORBA.ORBClass", true), ORG_OMG_CORBA_ORBSINGLETONCLASS(
	    "org.omg.CORBA.ORBSingletonClass", true),

    VIDEO_DEVELOPMENT_ENABLED("video.development.enabled", false), // FIXME Remove VIDEO_DEVELOPMENT_ENABLED flag after video tests

    VLC_PLUGINS_FILENAME("vlc.plugins.filename", ReCApplication.IS_VIDEO_DEVELOPMENT_ENABLED), VLC_PLUGINS_DESTDIR(
	    "vlc.plugins.destdir", ReCApplication.IS_VIDEO_DEVELOPMENT_ENABLED);

    String name;
    boolean required;

    ReCSystemProperty(String name, boolean required) {
	this.name = name;
	this.required = required;
    }

    public String getName() {
	return name;
    }

    public boolean isRequired() {
	return required;
    }

}
