package com.linkare.rec.impl.newface.installer.windows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.newface.installer.Installer;
import com.linkare.rec.impl.newface.utils.ZipExtractor;

/**
 * Classe responsável pela parte de instalação específica para Windows.
 * 
 * @author bcatarino
 */
public class WindowsInstaller extends Installer {

	/**
	 * Indica a versão do instalador. Sempre que seja feita uma alteração à
	 * aplicação que implique nova instalar, deverá ser incrementado manualmente
	 * este valor.
	 */
	private static final int INSTALLER_VERSION = 3;

	@Override
	public void installSpecificSO() throws UnavailableServiceException {

		log.fine("Beginning installation");

		try {

			final int result = JOptionPane.showConfirmDialog(null, bundle.getString("windows.ask.install.xvid"),
					bundle.getString("install.codecs"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			final boolean agree = (result == JOptionPane.YES_OPTION);

			boolean installOk = false;
			if (agree) {
				installOk = installXvid();
			}

			if (installOk) {
				JOptionPane.showMessageDialog(null, bundle.getString("codecs.installed"));
			} else {
				JOptionPane.showMessageDialog(null, bundle.getString("codecs.not.installed"));
			}

		} catch (final InterruptedException ex) {
			handleException();
		} catch (final IOException ex) {
			handleException();
		}
	}

	private boolean installXvid() throws IOException, InterruptedException {

		final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		final InputStream pluginsFileStream = loader.getResourceAsStream("xvid.zip");

		log.fine("Saving xvid installer to temp file...");

		final String path = saveToTempFile(pluginsFileStream, "xvid", "tmp");
		pluginsFileStream.close();

		log.fine("Path of temporary xvid.zip temporary file: " + path);

		final ZipExtractor extractor = new ZipExtractor(path);
		extractor.extractFiles(ReCSystemProperty.USER_HOME.getValue());

		final String xvidPath = ReCSystemProperty.USER_HOME.getValue() + File.separator + "xvid.exe";

		log.fine("xvid installer extracted to " + xvidPath);
		log.fine("Running xvid installer...");

		final Process xvid = Runtime.getRuntime().exec(new String[] { "cmd.exe", "/c", xvidPath });

		return xvid.waitFor() == 0;
	}

	@Override
	protected int getInstallerVersion() {
		return WindowsInstaller.INSTALLER_VERSION;
	}

	public static void main(final String[] args) {
		new WindowsInstaller().executeInstaller(args);
	}
}
