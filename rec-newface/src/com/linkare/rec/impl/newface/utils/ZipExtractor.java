package com.linkare.rec.impl.newface.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Funcionalidades para descompactar arquivos zip. Também extrai ficheiros jar ou qualquer outro formato que utilize
 * compressão zip.
 * 
 * @author bcatarino
 */
public class ZipExtractor {

	/**
	 * Extrai todos os ficheiros existentes num ficheiro comprimido para uma directoria de destino. A extracção é feita
	 * recursivamente, preservando os paths relativos à root do zip.
	 * 
	 * @param zipFile
	 *            Nome do ficheiro zip que se pretende extrair.
	 * @param destDir
	 *            Directoria onde se pretende extrair o zip e que servirá de root dir para os paths relativos dos
	 *            ficheiros existentes no zip.
	 */
	public static void extractFiles(String zipFile, String destDir) {

		try {

			// Uma vez que não há garantia que os folders sejam criados antes
			// dos ficheiros, criamos primeiro a directoria base, de seguida as
			// directorias filhas e apenas no final, os ficheiros.
			extractDirectory(destDir);
			createDirectories(zipFile, destDir);
			createFiles(zipFile, destDir);

		} catch (IOException e) {
			//FIXME tratamento correcto da excepção!!!!
			e.printStackTrace();
		}
	}

	private static void createDirectories(String zipFile, String destDir) throws IOException {

		ZipFile file = new ZipFile(zipFile);
		Enumeration<? extends ZipEntry> entries = file.entries();

		while (entries.hasMoreElements()) {

			ZipEntry entry = entries.nextElement();
			String name = entry.getName();

			if (entry.isDirectory()) {
				extractDirectory(destDir + File.separator + name);
			}
		}
	}

	private static void extractDirectory(String dirName) {
		File dir = new File(dirName);
		dir.mkdirs();
	}

	private static void createFiles(String zipFile, String destDir) throws FileNotFoundException, IOException {

		ZipFile file = new ZipFile(zipFile);
		Enumeration<? extends ZipEntry> entries = file.entries();

		while (entries.hasMoreElements()) {

			ZipEntry entry = entries.nextElement();
			String name = entry.getName();

			if (!entry.isDirectory()) {
				extractSingleFile(file, entry, destDir + File.separator + name);
			}
		}
	}

	private static void extractSingleFile(ZipFile file, ZipEntry entry, String fileName) throws FileNotFoundException, IOException {

		InputStream input = file.getInputStream(entry);
		FileOutputStream output = new FileOutputStream(fileName);
		byte[] buffer = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = input.read(buffer)) > 0) {
			output.write(buffer, 0, bytesRead);
		}
		output.close();
		input.close();
	}
}
