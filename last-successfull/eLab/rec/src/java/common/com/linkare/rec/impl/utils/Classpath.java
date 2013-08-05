/* 
 * Classpath.java created on 3 Mar 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public final class Classpath {

	public static interface FilenameFilter {
		/**
		 * All paths will be represented using forward slashes and no files will
		 * begin with a slash
		 * 
		 * @param filename
		 * @return
		 */
		public boolean accept(String filename);
	}

	/**
	 * Returns a list of the classes on the classpath. The names returned will
	 * be appropriate for using Class.forName(String) in that the slashes will
	 * be changed to dots and the .class file extension will be removed.
	 * 
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static String[] getClasspathClassNames() throws ZipException, IOException {
		final String[] classes = getClasspathFileNamesWithExtension(".class");
		for (int i = 0; i < classes.length; i++) {
			classes[i] = classes[i].substring(0, classes[i].length() - 6).replace("/", ".");
		}
		return classes;
	}

	public static String[] getClasspathFileNamesWithExtension(final String extension) throws ZipException, IOException {
		return getClasspathFileNames(new Classpath.FilenameFilter() {
			public boolean accept(String filename) {
				return filename.endsWith(extension);
			}
		});
	}

	public static String[] getClasspathFileNames(Classpath.FilenameFilter filter) throws ZipException, IOException {
		final List<String> filenames = new ArrayList<String>();
		for (String filename : getClasspathFileNames()) {
			if (filter.accept(filename)) {
				filenames.add(filename);
			}
		}
		return filenames.toArray(new String[filenames.size()]);
	}

	/**
	 * Returns the fully qualified class names of all the classes in the
	 * classpath. Checks directories and zip files. The FilenameFilter will be
	 * applied only to files that are in the zip files and the directories. In
	 * other words, the filter will not be used to sort directories.
	 * 
	 * @return
	 * @throws ZipException
	 * @throws IOException
	 */
	public static String[] getClasspathFileNames() throws ZipException, IOException {
		final StringTokenizer tokenizer = new StringTokenizer(System.getProperty("java.class.path"),
				File.pathSeparator, false);
		final Set<String> filenames = new LinkedHashSet<String>();

		while (tokenizer.hasMoreTokens()) {
			final String classpathElement = tokenizer.nextToken();
			final File classpathFile = new File(classpathElement);

			if (classpathFile.exists() && classpathFile.canRead()) {
				if (classpathElement.toLowerCase(Locale.US).endsWith(".jar")) {
					final ZipFile zip = new ZipFile(classpathFile);
					Enumeration<? extends ZipEntry> entries = zip.entries();

					while (entries.hasMoreElements()) {
						ZipEntry entry = entries.nextElement();
						if (!entry.isDirectory()) {
							filenames.add(entry.getName());
						}
					}

				} else if (classpathFile.isDirectory()) {
					// lets go through and find all of the subfolders
					final Set<File> directoriesToSearch = new HashSet<File>();
					final Set<File> newDirectories = new HashSet<File>();
					directoriesToSearch.add(classpathFile);
					final String basePath = classpathFile.getAbsolutePath();

					while (directoriesToSearch.size() > 0) {
						for (File searchDirectory : directoriesToSearch) {
							File[] directoryFiles = searchDirectory.listFiles();
							for (File directoryFile : directoryFiles) {
								if (directoryFile.isDirectory()) {
									newDirectories.add(directoryFile);
								} else {
									filenames.add(directoryFile.getAbsolutePath().substring(basePath.length() + 1));
								}
							}
						}
						directoriesToSearch.clear();
						directoriesToSearch.addAll(newDirectories);
						newDirectories.clear();
					}
				}
			}
		}

		final String[] uniqueNames = new String[filenames.size()];
		int index = 0;

		for (String name : filenames) {
			uniqueNames[index++] = name.replace("\\", "/");
		}

		return uniqueNames;
	}
}