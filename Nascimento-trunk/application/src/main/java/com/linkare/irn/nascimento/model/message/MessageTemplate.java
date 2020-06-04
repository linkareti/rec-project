package com.linkare.irn.nascimento.model.message;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.linkare.irn.nascimento.model.DomainObject;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "message_template", uniqueConstraints = @UniqueConstraint(columnNames = "code"))
@NamedQueries({ @NamedQuery(name = MessageTemplate.QUERY_NAME_FIND_BY_CODE, query = "select mt from MessageTemplate mt where mt.code = :"
	+ MessageTemplate.QUERY_PARAM_CODE) })
public class MessageTemplate extends DomainObject implements IMessageTemplate {

    private static final long serialVersionUID = 7821417316342879555L;

    public static final String QUERY_NAME_FIND_BY_CODE = "NotificationTemplate.findByCode";

    public static final String QUERY_PARAM_CODE = "code";

    @Column(name = "code")
    @NotNull
    @Size(max = 75)
    private String code;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private MessageTemplateType type = MessageTemplateType.EMAIL;

    @Column(name = "subject")
    private String subject;

    @Column(name = "content", columnDefinition = "text")
    @NotNull
    private String content;

    @Override
    public String getCode() {
	return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(String code) {
	this.code = code;
    }

    @Override
    public MessageTemplateType getType() {
	return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(MessageTemplateType type) {
	this.type = type;
    }

    @Override
    public String getSubject() {
	return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(String subject) {
	this.subject = subject;
    }

    @Override
    public String getContent() {
	return content;
    }

    /**
     * @param content
     *            the content to set
     */
    public void setContent(String content) {
	this.content = content;
    }
}