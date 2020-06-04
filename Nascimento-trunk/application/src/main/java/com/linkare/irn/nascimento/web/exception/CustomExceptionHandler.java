package com.linkare.irn.nascimento.web.exception;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.persistence.EntityNotFoundException;

import com.linkare.irn.nascimento.web.auth.UnauthorizedException;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
	this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
	return wrapped;
    }

    private static Throwable getOriginalCause(final Throwable throwable) {
	if (throwable.getCause() == null) {
	    return throwable;
	} else {
	    return getOriginalCause(throwable.getCause());
	}
    }

    @Override
    public void handle() throws FacesException {
	final Iterator<ExceptionQueuedEvent> iterator = getUnhandledExceptionQueuedEvents().iterator();

	final FacesContext fc = FacesContext.getCurrentInstance();
	while (iterator.hasNext()) {
	    ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
	    ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

	    final Throwable throwable = getOriginalCause(context.getException());

	    if (throwable instanceof ViewExpiredException) {
		handleViewExpiredException(iterator, fc, throwable);
	    } else if (throwable instanceof UnauthorizedException) {
		handleSecurityException(iterator, fc, throwable);
	    } else if (throwable instanceof EntityNotFoundException) {
		handleEntityNotFoundException(iterator, fc, throwable);
	    }
	}

	// Let the parent handle the rest
	getWrapped().handle();
    }

    private void handleViewExpiredException(final Iterator<ExceptionQueuedEvent> iterator, final FacesContext fc, Throwable throwable) {
	iterator.remove();
	final NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
	navigationHandler.handleNavigation(fc, null, "/view_expired_error.xhtml");
	fc.renderResponse();
    }

    private void handleSecurityException(final Iterator<ExceptionQueuedEvent> iterator, final FacesContext fc, Throwable throwable) {
	iterator.remove();
	final NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
	navigationHandler.handleNavigation(fc, null, "/unauthorized.xhtml");
	fc.renderResponse();
    }

    private void handleEntityNotFoundException(final Iterator<ExceptionQueuedEvent> iterator, final FacesContext fc, Throwable throwable) {
	iterator.remove();
	final NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
	navigationHandler.handleNavigation(fc, null, "/entity_not_found.xhtml");
	fc.renderResponse();
    }
}