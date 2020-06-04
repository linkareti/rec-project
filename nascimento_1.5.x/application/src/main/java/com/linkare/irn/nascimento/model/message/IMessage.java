package com.linkare.irn.nascimento.model.message;

import java.util.List;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 * @param <ORIGIN>
 *            the origin class of the message
 * @param <DESTINATION>
 *            the destination class of the message
 */
public interface IMessage<ORIGIN, DESTINATION> {

    public ORIGIN getOrigin();

    public List<DESTINATION> getDestinations();

    public void addDestination(final DESTINATION destination);

    public String getContent();

    public void setContent(String content);

    public MessageStatus getStatus();

    public void setStatus(MessageStatus status);
}