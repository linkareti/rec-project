package com.linkare.irn.nascimento.service.mapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * Provides import utility methods for several data types.
 * 
 * @author Filipe Amaral - Linkare TI
 *
 */
public final class ImportUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ImportUtils.class);

    private static final String DATE_PATTERN = "dd-MM-yyyy";

    private static final String DATE_TIME_PATTERN = "dd-MM-yyyy HH:mm";

    private static final Pattern VALID_DATE_PATTERN = Pattern.compile("^(0?[1-9]|[12][0-9]|3[01])\\-(0?[1-9]|1[012])\\-\\d{4}$");

    private static final List<String> TRUES = Lists.newArrayList("true", "1");
    private static final List<String> FALSES = Lists.newArrayList("false", "0");

    private ImportUtils() {
	// avoid external instantiation
    }

    /**
     * Tries, for provided enumeration class, to resolve the provided string value as one of the enumeration's values.
     * 
     * @param value
     *            Target enum value as string
     * @param targetEnum
     *            Target enumeration
     * @return The resolved enumeration value, or {@code null} otherwise
     */
    public static <E extends Enum<?>> E enumOf(final String value, final Class<E> targetEnum) {
	if (StringUtils.isBlank(value)) {
	    LOG.debug("Enumeration {} is null or empty.", targetEnum.getSimpleName());

	    return null;
	}

	return Arrays.stream(targetEnum.getEnumConstants()).filter(e -> value.equals(e.name()))
		     .peek(e -> LOG.debug("Found value '{}' in enumeration {}", value, targetEnum.getSimpleName())).findFirst().orElseGet(() -> {
			 LOG.debug("Value '{}' without correspondence in enumeration {}", value, targetEnum.getSimpleName());
			 return null;
		     });
    }

    /**
     * Tries, with provided parse function, to resolve the provided string value as a number.
     * 
     * @param value
     *            Target number as string
     * @param parseFuntion
     *            The parse function to use
     * @return The resolved number value, or {@code null} otherwise
     */
    public static <N extends Number> N numberOf(final String value, Function<String, N> parseFuntion) {
	if (StringUtils.isBlank(value)) {
	    LOG.debug("Number value is null or empty.");

	    return null;
	}

	N parsedNumber = null;

	try {
	    parsedNumber = parseFuntion.apply(value);
	} catch (NumberFormatException e) {
	    LOG.warn("Unable to parse number from '{}'.", value);
	}

	return parsedNumber;
    }

    /**
     * Tries to resolve the provided string value as a float.
     * <p>
     * This is the same as calling the method {@link #numberOf(String, Function)} as <i>numberOf(value, Float::valueOf)</i>.
     * 
     * @param value
     *            Target float as string
     * @return The resolved float value, or {@code null} otherwise
     */
    public static Float floatOf(final String value) {
	return numberOf(value, Float::valueOf);
    }

    /**
     * Tries to resolve the provided string value as a integer.
     * <p>
     * This is the same as calling the method {@link #numberOf(String, Function)} as <i>numberOf(value, Integer::valueOf)</i>.
     * 
     * @param value
     *            Target integer as string
     * @return The resolved integer value, or {@code null} otherwise
     */
    public static Integer integerOf(final String value) {
	return numberOf(value, Integer::valueOf);
    }

    /**
     * Tries to resolve the provided string value as a boolean.
     * <p>
     * Supported values include:
     * <ul>
     * <li>true: "true", "1"
     * <li>false: "false", "0"
     * </ul>
     * All remaining values will return <code>null</code>.
     * 
     * @param value
     *            Target integer as string
     * @return The resolved integer value, or {@code null} otherwise
     */
    public static Boolean booleanOf(final String value) {
	if (StringUtils.isBlank(value)) {
	    LOG.debug("Boolean value is null or empty.");

	    return null;
	}

	Boolean result = null;

	String lowerCaseValue = value.toLowerCase();

	if (TRUES.contains(lowerCaseValue)) {
	    result = Boolean.TRUE;
	} else if (FALSES.contains(lowerCaseValue)) {
	    result = Boolean.FALSE;
	} else {
	    LOG.debug("Unable to parse boolean from {}.", value);
	}

	return result;
    }

    /**
     * Tries to resolve the provided string value as a simple date with format . All remaining values will return <code>null</code>.
     * 
     * @param value
     *            Target integer as string
     * @return The resolved integer value, or {@code null} otherwise
     */
    public static Date dateOf(final String value) {
	if (StringUtils.isBlank(value)) {
	    LOG.debug("Date value is null or empty.");

	    return null;
	}

	Date date = null;

	if (VALID_DATE_PATTERN.matcher(value).matches()) {
	    try {
		date = new SimpleDateFormat(DATE_PATTERN).parse(value);
	    } catch (ParseException e) {
		LOG.warn("Unable to parse date ({}) from '{}'.", DATE_PATTERN.toUpperCase(), value);
	    }
	} else {
	    LOG.warn("Unable to parse date ({}) from '{}'.", DATE_PATTERN.toUpperCase(), value);
	}

	return date;
    }

    /**
     * Tries to resolve the provided string value as a simple date with format . All remaining values will return <code>null</code>.
     * 
     * @param value
     *            Target integer as string
     * @return The resolved integer value, or {@code null} otherwise
     */
    public static Date dateTimeOf(final String value) {
	if (StringUtils.isBlank(value)) {
	    LOG.debug("Date value is null or empty.");

	    return null;
	}

	Date date = null;

	try {
	    date = new SimpleDateFormat(DATE_TIME_PATTERN).parse(value);
	} catch (ParseException e) {
	    LOG.warn("Unable to parse date ({}) from '{}'.", DATE_TIME_PATTERN.toUpperCase(), value);
	}

	return date;
    }
}
