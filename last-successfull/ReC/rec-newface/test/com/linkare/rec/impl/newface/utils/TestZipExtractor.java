package com.linkare.rec.impl.newface.utils;

import java.io.File;

/**
 * 
 * @author bcatarino
 */
public class TestZipExtractor {

	public static void main(String[] args) {

		System.out.println(System.getProperty("os.arch") + "; " + System.getProperty("os.name"));
		// String fileName = "libsvlc_linux.jar";
		String fileName = "vlcplugins_linux.jar";
		String userDir = System.getProperty("user.home");
		System.out.println("Userdir = " + userDir);
		System.out.println("Filename: " + userDir + File.separator + fileName);
		String destDir = userDir + File.separator + ".eLab" + File.separator + "plugins";
	}
}
