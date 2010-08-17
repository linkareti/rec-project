package com.linkare.rec.impl.newface.installer.windows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

import com.linkare.rec.impl.newface.installer.Installer;
import com.linkare.rec.impl.newface.utils.ZipExtractor;

/**
 * Classe responsável pela parte de instalação específica para Windows.
 * 
 * @author bcatarino
 */
public class WindowsInstaller extends Installer {
	
	/**
	 * Indica a versão do instalador. Sempre que seja feita uma alteração 
	 * à aplicação que implique nova instalar, deverá ser incrementado 
	 * manualmente este valor.
	 */
	private static final int INSTALLER_VERSION = 2;

	@Override
	public void installSpecificSO() throws UnavailableServiceException {

		log.fine("Beginning installation");

		try {
			
			int result = JOptionPane.showConfirmDialog(null, bundle.getString("windows.ask.install.xvid"), bundle
					.getString("install.codecs"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			boolean agree = (result == JOptionPane.YES_OPTION);

			boolean installOk = false;
			if (agree) {
				installOk = installXvid();
			}

			if (installOk) {
				JOptionPane.showMessageDialog(null, bundle.getString("codecs.installed"));
			} else {
				JOptionPane.showMessageDialog(null, bundle.getString("codecs.not.installed"));
			}

		} catch (InterruptedException ex) {
			handleException();
		} catch (IOException ex) {
			handleException();
		}
	}

	private boolean installXvid() throws IOException, InterruptedException {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream pluginsFileStream = loader.getResourceAsStream("xvid.zip");

		log.fine("Saving xvid installer to temp file...");

		String path = saveToTempFile(pluginsFileStream, "xvid", "tmp");
		pluginsFileStream.close();

		log.fine("Path of temporary xvid.zip temporary file: " + path);

		ZipExtractor extractor = new ZipExtractor(path);
		extractor.extractFiles(System.getProperty("user.home"));

		String xvidPath = System.getProperty("user.home") + File.separator + "xvid.exe";

		log.fine("xvid installer extracted to " + xvidPath);
		log.fine("Running xvid installer...");

		Process xvid = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", xvidPath });

		return xvid.waitFor() == 0;
	}
	
	protected int getInstallerVersion() {
		return INSTALLER_VERSION;
	}

	public static void main(String[] args) {
		new WindowsInstaller().executeInstaller(args);
	}
}
