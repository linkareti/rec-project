package com.linkare.irn.nascimento.model.message;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public interface IMessageTemplate {

    /**
     * @return the code
     */
    public String getCode();

    /**
     * @return the type
     */
    public MessageTemplateType getType();

    /**
     * @return the subject
     */
    public String getSubject();

    /**
     * @return the content
     */
    public String getContent();
}