package com.linkare.irn.nascimento.service.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;

public class WsMailSenderSecurityHeadesHandlerResolver implements HandlerResolver {

    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
	List<Handler> handlerChain = new ArrayList<>();
	handlerChain.add(new WsMailSenderSecurityHeadesHandler());

	return handlerChain;
    }
}