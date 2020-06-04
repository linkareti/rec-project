package com.linkare.irn.nascimento.model.converter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@JsonIgnoreProperties(value = { "type", "group" })
public abstract class InternetAddressJsonMixin {
}