package com.linkare.rec.impl.newface.installer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

import javax.jnlp.ExtensionInstallerService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

import com.linkare.rec.impl.newface.utils.ZipExtractor;

/**
 * Efectua a instalação comum a todas as plataformas.
 * 
 * @author bcatarino
 */
public class Installer {

	protected Logger log = Logger.getLogger(Installer.class.getName());

	protected ExtensionInstallerService installerService = null;

	protected ExtensionInstallerService getInstallerService() throws UnavailableServiceException {

		if (installerService == null) {
			installerService = (ExtensionInstallerService) ServiceManager.lookup("javax.jnlp.ExtensionInstallerService");
		}
		return installerService;
	}

	public void install(String[] args) throws UnavailableServiceException, IOException {

		for (String arg : args) {
			log.fine("Installer arg: " + arg);
		}

		printOSInfo();

		installNativeLibs();
	}

	public static void main(String[] args) throws UnavailableServiceException, IOException {

		new Installer().install(args);
	}

	private void printOSInfo() {

		log.fine("jna.library.path: " + System.getProperty("jna.library.path"));

		log.fine("############ OS NAME: " + System.getProperty("os.name"));
		log.fine("############ OS ARCH: " + System.getProperty("os.arch"));
		log.fine("############ OS VERSION: " + System.getProperty("os.version"));
	}

	private void installNativeLibs() throws IOException {

		//TODO verificar se a directoria já existe e não extrair nesse caso.
		String userHome = System.getProperty("user.home");
		log.fine("User home is " + userHome);

		String pluginsPath = userHome + File.separator + System.getProperty("vlc.plugins.destdir");
		log.fine("Plugins copied to " + pluginsPath);

		File pluginsDir = new File(pluginsPath);
		// Só extrai os plugins do zip se n existir a directoria de destino
		// onde irão ficar os plugins.
		if (!pluginsDir.exists()) {
			//TODO fazer de forma a substituir sempre os ficheiros que alteraram (filesize, md5sum???)
			String pluginsResourceName = System.getProperty("vlc.plugins.filename");
			log.fine("Resource name is " + pluginsResourceName);

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream pluginsFileStream = loader.getResourceAsStream(pluginsResourceName);
			String path = saveToTempFile(pluginsFileStream, "libsfile", "tmp");
			pluginsFileStream.close();

			log.fine("File to be extracted: " + path);
			ZipExtractor.extractFiles(path, pluginsPath);

			log.fine("Media Plugins extracted!");
		} else {
			log.fine("Plugins already installed. Didn't install plugins!");
		}
	}

	protected String saveToTempFile(InputStream is, String prefix, String suffix) {

		try {

			File f = File.createTempFile(prefix, suffix);
			f.deleteOnExit();
			copyStream(is, f);
			return f.getAbsolutePath();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void copyStream(InputStream is, File f) throws IOException {

		FileOutputStream fos = new FileOutputStream(f);
		byte[] ba = new byte[1024];
		int bytesRead = 0;
		while (bytesRead >= 0) {
			bytesRead = is.read(ba);
			if (bytesRead > 0)
				fos.write(ba, 0, bytesRead);
		}
		fos.flush();
		fos.close();

	}
}
