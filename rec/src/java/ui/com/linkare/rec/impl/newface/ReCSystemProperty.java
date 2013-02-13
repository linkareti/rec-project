package com.linkare.rec.impl.newface;

/**
 * Holds the ReC System properties. Maps the property name and the required
 * flag.
 */
public enum ReCSystemProperty {

	/**
	 * The ReCFaceConfig.xml url location
	 */
	RECFACECONFIG("rec.face.config", true),
	/**
	 * The MultiCastController bind name
	 */
	REC_MULTICASTCONTROLLER_BINDNAME("rec.multicastcontroller.bindname", true),
	/**
	 * The openorb configuration file
	 */
	OPENORB_CONFIG("openorb.config", true),
	/**
	 * The openorb profile
	 */
	OPENORB_PROFILE("openorb.profile", true),
	/**
	 * The ORB Class
	 */
	ORG_OMG_CORBA_ORBCLASS("org.omg.CORBA.ORBClass", true),
	/**
	 * The ORB SingletonClass
	 */
	ORG_OMG_CORBA_ORBSINGLETONCLASS("org.omg.CORBA.ORBSingletonClass", true),

	/**
	 * Wether video is enabled or not
	 */
	VIDEO_ENABLED("video.enabled", false),

	/**
	 * The VLC plugins file name
	 */
	VLC_PLUGINS_FILENAME("vlc.plugins.filename", ReCApplication.IS_VIDEO_ENABLED),
	/**
	 * The VLC plugins destination dir
	 */
	VLC_PLUGINS_DESTDIR("vlc.plugins.destdir", ReCApplication.IS_VIDEO_ENABLED);

	String name;
	boolean required;

	ReCSystemProperty(final String name, final boolean required) {
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
