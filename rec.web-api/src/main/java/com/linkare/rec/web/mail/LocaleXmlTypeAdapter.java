package com.linkare.rec.web.mail;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocaleXmlTypeAdapter extends XmlAdapter<String, Locale> {

	private static final Pattern PATTERN_SERIALIZED_LOCALE = Pattern
			.compile("([^_]+)(_([^_]+))?(_(.*))?");

	@Override
	@SuppressWarnings("PMD.SignatureDeclareThrowsException")
	public Locale unmarshal(String v) throws Exception {

		if (v == null) {
			return Locale.getDefault();
		}

		Matcher matcher = PATTERN_SERIALIZED_LOCALE.matcher(v);
		Locale x = Locale.getDefault();
		if (matcher.matches()) {
			String language = matcher.group(1);
			String country = matcher.group(3);
			String variant = matcher.group(5);
			if (variant != null) {
				x = new Locale(language, country, variant);
			} else if (country != null) {
				x = new Locale(language, country);
			} else {
				x = new Locale(language);
			}
		}
		return x;

	}

	@Override
	@SuppressWarnings("PMD.SignatureDeclareThrowsException")
	public String marshal(Locale v) throws Exception {

		if (v == null) {
			return null;
		}

		return v.toString();
	}
}