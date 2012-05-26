package com.linkare.rec.am.mail;

import java.util.Locale;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocaleXmlTypeAdapter extends XmlAdapter<String, Locale> {

    @Override
    public Locale unmarshal(String v) throws Exception {
	String[] splitVariables = v.split(":");
	if (splitVariables == null) {
	    return null;
	}
	switch (splitVariables.length) {
	case 1:
	    return new Locale(splitVariables[0]);
	case 2:
	    return new Locale(splitVariables[0], splitVariables[1]);
	case 3:
	    return new Locale(splitVariables[0], splitVariables[1], splitVariables[2]);
	default:
	    return null;
	}
    }

    @Override
    public String marshal(Locale v) throws Exception {
	
	if (v == null || v.getLanguage() == null) {
	    return null;
	}

	String marshalled = v.getLanguage() + ":" + v.getCountry() != null ? v.getCountry() : "" + ":" + v.getVariant() != null ? v.getVariant() : "";
	if (marshalled.charAt(marshalled.length() - 1) == ':') {
	    marshalled = marshalled.substring(0, marshalled.lastIndexOf(":"));
	}
	
	return marshalled;
    }
}