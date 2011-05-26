package com.linkare.rec.impl.newface.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Observable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Funcionalidades para descompactar arquivos zip. Também extrai ficheiros jar
 * ou qualquer outro formato que utilize compressão zip.
 * 
 * @author bcatarino
 */
public class ZipExtractor extends Observable {

	private final ZipFile zipFile;
	private int extracted;

	/**
	 * Cria um Extractor para um ficheiro zip.
	 * 
	 * @param zipFile Ficheiro a extrair.
	 * @throws IOException
	 */
	public ZipExtractor(final String zipFile) throws IOException {
		this.zipFile = new ZipFile(zipFile);
	}

	/**
	 * Extrai todos os ficheiros existentes num ficheiro comprimido para uma
	 * directoria de destino. A extracção é feita recursivamente, preservando os
	 * paths relativos à root do zip.
	 * 
	 * @param destDir Directoria onde se pretende extrair o zip e que servirá de
	 *            root dir para os paths relativos dos ficheiros existentes no
	 *            zip.
	 */
	public void extractFiles(final String destDir) {

		extracted = 0;

		try {

			// Uma vez que não há garantia que os folders sejam criados antes
			// dos ficheiros, criamos primeiro a directoria base, de seguida as
			// directorias filhas e apenas no final, os ficheiros.
			extractDirectory(destDir);
			createDirectories(destDir);
			createFiles(destDir);

		} catch (final IOException e) {
			// FIXME tratamento correcto da excepção!!!!
			e.printStackTrace();
		}
	}

	private void createDirectories(final String destDir) throws IOException {

		final Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {

			final ZipEntry entry = entries.nextElement();
			final String name = entry.getName();

			if (entry.isDirectory()) {
				extractDirectory(destDir + File.separator + name);
				informObservers();
			}
		}
	}

	/**
	 * Envia informação a todos os Observers registados de que houve uma
	 * alteração.
	 */
	private void informObservers() {
		setChanged();
		notifyObservers(new int[] { ++extracted, zipFile.size() });
	}

	private void extractDirectory(final String dirName) {
		final File dir = new File(dirName);
		dir.mkdirs();
	}

	private void createFiles(final String destDir) throws FileNotFoundException, IOException {

		final Enumeration<? extends ZipEntry> entries = zipFile.entries();

		while (entries.hasMoreElements()) {

			final ZipEntry entry = entries.nextElement();
			final String name = entry.getName();

			if (!entry.isDirectory()) {
				extractSingleFile(zipFile, entry, destDir + File.separator + name);
				notifyObservers(new int[] { ++extracted, zipFile.size() });
			}
		}
	}

	private void extractSingleFile(final ZipFile file, final ZipEntry entry, final String fileName)
			throws FileNotFoundException, IOException {

		final InputStream input = file.getInputStream(entry);
		final FileOutputStream output = new FileOutputStream(fileName);
		final byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = input.read(buffer)) > 0) {
			output.write(buffer, 0, bytesRead);
		}
		output.close();
		input.close();
	}
}
