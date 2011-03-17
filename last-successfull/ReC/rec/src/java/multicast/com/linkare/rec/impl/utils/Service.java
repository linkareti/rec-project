/* 
 * Service.java created on 28 Jun 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author jpereira
 */
public class Service {
	private File controlFile = null;

	protected Service(String controlFileName) {
		if (controlFileName == null) {
			throw new NullPointerException("Service should have a control file...");
		}
		this.controlFile = new File(controlFileName);
	}

	protected final void removeStaleControlFile() {
		if (controlFile.exists()) {
			if (!controlFile.delete()) {
				System.out.println("Unable to delete control file " + controlFile.getAbsolutePath());
				System.exit(-1);
			}
			System.out.println("Removed stale control file @ " + controlFile.getAbsolutePath());
		}
	}

	protected final boolean checkControlFile() {
		return controlFile.exists();
	}

	protected boolean checkIsAlive() {
		if (!checkControlFile()) {
			return false;
		}
		int port = -1;
		try {
			port = readServicePort();
		} catch (NoControlFileException e) {
			return false;
		} catch (CannotReadServicePortException e) {
			return false;
		}

		return true;
	}

	/**
	 * @return
	 * @throws NoControlFileException
	 * @throws CannotReadServicePortException
	 */
	private int readServicePort() throws NoControlFileException, CannotReadServicePortException {
		if (!checkControlFile()) {
			throw new NoControlFileException("No control file @ " + controlFile.getAbsolutePath());
		}
		BufferedReader br = null;
		String portNumberStr = null;
		try {
			br = new BufferedReader(new FileReader(controlFile));
			portNumberStr = br.readLine();
			int portNumber = Integer.parseInt(portNumberStr);
			return portNumber;
		} catch (Exception e) {
			throw new CannotReadServicePortException("Cannot read service port '" + portNumberStr
					+ "' from control file @ " + controlFile.getAbsolutePath());
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
