package com.linkare.irn.nascimento.util;

import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import org.apache.commons.lang.LocaleUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;

import com.linkare.irn.nascimento.model.config.Configuration;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class VelocityUtil {

    private static final String UTF8_ENCODING = "UTF-8";

    private static VelocityEngine classPathEngine;

    private static VelocityEngine stringEngine;

    private static VelocityEngine getClassPathEngine() {
	if (classPathEngine == null) {
	    classPathEngine = new VelocityEngine();
	    classPathEngine.setProperty(Velocity.RESOURCE_LOADER, "class");
	    classPathEngine.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.getName());
	    classPathEngine.init();
	}
	return classPathEngine;
    }

    private static VelocityEngine getStringEngine() {
	if (stringEngine == null) {
	    stringEngine = new VelocityEngine();
	    stringEngine.setProperty(Velocity.RESOURCE_LOADER, "string");
	    stringEngine.addProperty("string.resource.loader.class", StringResourceLoader.class.getName());
	    stringEngine.addProperty("string.resource.loader.repository.static", "false");
	    stringEngine.init();
	}
	return stringEngine;
    }

    private static VelocityContext initContext(final Configuration configuration) {
	final VelocityContext context = new VelocityContext();

	context.put("configuration", configuration);

	final Locale locale = LocaleUtils.toLocale(configuration.getLocale());
	context.put("locale", locale);

	final TimeZone timeZone = TimeZone.getTimeZone(configuration.getTimeZone());
	context.put("timeZone", timeZone);

	context.put(EscapeTool.DEFAULT_KEY, new EscapeTool());
	context.put("date", new DateTool());

	return context;
    }

    public static String getFromTemplate(final String vmTemplate, final Configuration configuration, final Map<String, Object> templateParams) {
	final VelocityEngine ve = getClassPathEngine();
	final Template template = ve.getTemplate("template/" + vmTemplate, UTF8_ENCODING);
	return getTemplateContent(template, configuration, templateParams);
    }

    public static String getFromString(final String code, final String content, final Configuration configuration, final Map<String, Object> templateParams) {

	final VelocityEngine ve = getStringEngine();

	final StringResourceRepository repo = (StringResourceRepository) ve.getApplicationAttribute(StringResourceLoader.REPOSITORY_NAME_DEFAULT);
	repo.putStringResource(code, content, UTF8_ENCODING);

	final Template template = ve.getTemplate(code, UTF8_ENCODING);
	return getTemplateContent(template, configuration, templateParams);
    }

    private static String getTemplateContent(final Template template, final Configuration configuration, final Map<String, Object> templateParams) {
	final VelocityContext context = initContext(configuration);

	if (templateParams != null && !templateParams.isEmpty()) {
	    for (final Entry<String, Object> entry : templateParams.entrySet()) {
		context.put(entry.getKey(), entry.getValue());
	    }
	}

	final StringWriter writer = new StringWriter();
	template.merge(context, writer);

	return writer.toString();
    }
}