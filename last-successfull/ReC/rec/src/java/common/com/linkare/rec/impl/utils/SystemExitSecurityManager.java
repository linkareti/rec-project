/* 
 * SystemExitSecurityManager.java created on 30 Mar 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.beans.Beans;
import java.security.Permission;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SwingUtilities;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SystemExitSecurityManager extends SecurityManager {

	private static Set<String> ignoredPackagesExit = new HashSet<String>();
	static {
		ignoredPackagesExit.add("java.");
		ignoredPackagesExit.add("javax.");
		ignoredPackagesExit.add("com.sun.");
		ignoredPackagesExit.add("oracle.");
		ignoredPackagesExit.add("com.oracle.");
		ignoredPackagesExit.add("org.jdesktop.");
		ignoredPackagesExit.add("sun.");
	}

	private static Set<String> authorizedPackagesExit = new HashSet<String>();
	static {
		authorizedPackagesExit.add("com.linkare.rec.");
	}

	private Window mainApplicationWindow;

	@Override
	public void checkExit(int status) {

		super.checkExit(status);
		StackTraceElement[] stackTraceArray = Thread.currentThread().getStackTrace();

		boolean reallyExitingNow = false;
		if (stackTraceArray.length > 3 && stackTraceArray[2].getClassName() != null
				&& stackTraceArray[2].getMethodName() != null
				&& java.lang.Runtime.class.getName().equals(stackTraceArray[2].getClassName())
				&& "exit".equals(stackTraceArray[2].getMethodName())) {
			reallyExitingNow = true;
		}

		if (reallyExitingNow) {
			for (int i = 3; i < stackTraceArray.length; i++) {
				StackTraceElement stackTraceElement = stackTraceArray[i];
				if (!isInIgnoredPackages(stackTraceElement.getClassName())) {
					if (checkAuthorizedPackageExit(stackTraceElement.getClassName())) {
						return;
					}
				}
			}

			// So it doesn't come from an unauthorized class, but maybe it
			// is the default close operation
			// set on a Window that is not from the correct package
			if (disposeIfUnauthorizedWIndow()) {
				SystemExitSecurityManager.setThrowingSecurityExceptionButDisposing();
				throw new SecurityException("Your frame/window is not authorized to exit the JVM");
			}

		}

	}

	private boolean checkAuthorizedPackageExit(String className) throws SecurityException {
		if (className != null && isInAllowedPackageExit(className)) {
			return true;
		} else if (className != null) {

			disposeIfUnauthorizedWIndow();
			SystemExitSecurityManager.setThrowingSecurityExceptionButDisposing();
			throw new SecurityException("Your class " + className + " is not authorized to exit the JVM");
		}
		return false;
	}

	private static final ThreadLocal<Boolean> THROWING_SECURITY_EXCEPTION_BUT_DISPOSING = new ThreadLocal<Boolean>() {
		protected Boolean initialValue() {
			return Boolean.FALSE;
		}
	};

	/**
	 * 
	 */
	private static void setThrowingSecurityExceptionButDisposing() {
		THROWING_SECURITY_EXCEPTION_BUT_DISPOSING.set(Boolean.TRUE);
	}

	public static boolean isThrowingSecurityExceptionButDisposing() {
		return THROWING_SECURITY_EXCEPTION_BUT_DISPOSING.get() == Boolean.TRUE;
	}

	public static void clearThrowingSecurityExceptionButDisposing() {
		THROWING_SECURITY_EXCEPTION_BUT_DISPOSING.set(Boolean.FALSE);
	}

	/**
	 * 
	 */
	private boolean disposeIfUnauthorizedWIndow() {
		if (!GraphicsEnvironment.isHeadless() && Beans.isGuiAvailable() && SwingUtilities.isEventDispatchThread()
				&& EventQueue.getCurrentEvent() != null) {
			// Maybe is is a window trying to close, and choosing to
			// kill
			// the JVM... just try to dispose the window if it is not
			// our "main application window"
			AWTEvent event = EventQueue.getCurrentEvent();
			try {
				Component eventSourceComponent = (Component) event.getSource();
				Component rootComponent = SwingUtilities.getRoot(eventSourceComponent);
				if (rootComponent instanceof Window && rootComponent != this.mainApplicationWindow) {
					final Window wnd = (Window) rootComponent;
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							wnd.setVisible(false);
							wnd.dispose();
						}
					});
					return true;
				}

			} catch (Exception ignored) {

			}

		}

		return false;
	}

	private boolean isInAllowedPackageExit(String className) {
		for (String authorizedPackage : authorizedPackagesExit) {
			if (className.startsWith(authorizedPackage)) {
				return true;
			}
		}
		return false;
	}

	private boolean isInIgnoredPackages(String className) {
		if (className == null)
			return true;
		for (String authorizedPackageExit : ignoredPackagesExit) {
			if (className.startsWith(authorizedPackageExit)) {
				return true;
			}
		}
		return false;
	}

	public void setMainApplicationWindow(Window applicationWindow) {
		this.mainApplicationWindow = applicationWindow;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkPermission(Permission perm) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void checkPermission(Permission perm, Object context) {
	}

}