package com.linkare.rec.impl.newface.installer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.jnlp.ExtensionInstallerService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.newface.component.media.VideoViewerController;
import com.linkare.rec.impl.newface.utils.PreferencesUtils;
import com.linkare.rec.impl.newface.utils.ZipExtractor;

/**
 * Efectua a instalação comum a todas as plataformas.
 * 
 * @author bcatarino
 */
public abstract class Installer implements Observer {

	private static final String INSTALLER_VERSION = "INSTALLER_VERSION";

	protected Logger log = Logger.getLogger(Installer.class.getName());

	protected ExtensionInstallerService installerService = null;

	protected ResourceBundle bundle = ResourceBundle.getBundle("com.linkare.rec.impl.newface.installer.installer");

	protected ExtensionInstallerService getInstallerService() throws UnavailableServiceException {

		if (installerService == null) {
			installerService = (ExtensionInstallerService) ServiceManager
					.lookup("javax.jnlp.ExtensionInstallerService");
		}
		return installerService;
	}

	public void executeInstaller(final String[] args) {

		for (final String arg : args) {
			log.fine("Installer arg: " + arg);
		}

		try {

			executeInstaller(args[0]);

		} catch (final UnavailableServiceException e) {
			log.severe(e.getMessage());
			handleException();
		} catch (final IOException e) {
			log.severe(e.getMessage());
			handleException();
		} catch (final RuntimeException e) {
			log.severe(e.getMessage());
			handleException();
		}
	}

	private void executeInstaller(final String type) throws UnavailableServiceException, IOException {

		if ("install".equals(type)) {
			install();
		} else if ("uninstall".equals(type)) {
			new Uninstaller().uninstall();
		} else {
			throw new UnsupportedOperationException("Apenas as opções "
					+ "de install e uninstall são válidas para o instalador");
		}

		getInstallerService().updateProgress(100);
		getInstallerService().setStatus(bundle.getString("complete"));
		getInstallerService().installSucceeded(false);
	}

	private void install() throws IOException, UnavailableServiceException {

		final int version = readVersionNumber();
		if (version == 0 || version < getInstallerVersion()) {

			printOSInfo();
			installSpecificSO();
			installNativeLibs();

			PreferencesUtils.writeUserPreference(Installer.INSTALLER_VERSION, String.valueOf(getInstallerVersion()));
		}
	}

	private void showUnsupportedMessage() {
		JOptionPane.showMessageDialog(null, bundle.getString("unsupported"), "", JOptionPane.OK_OPTION);
	}

	/**
	 * Reads the installer version number from the preferences.
	 * 
	 * @return
	 */
	private int readVersionNumber() {

		final String versionStr = PreferencesUtils.readUserPreference(Installer.INSTALLER_VERSION);
		try {
			return Integer.parseInt(versionStr);
		} catch (final NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Ponto de extensão para efectuar uma instalação específica para um
	 * determinado SO, para além da instalação default já feita por esta classe.
	 */
	protected abstract void installSpecificSO() throws UnavailableServiceException;

	protected void printOSInfo() {

		log.fine("jna.library.path: " + ReCSystemProperty.JNA_LIBRAY_PATH.getValue());

		log.fine("############ OS NAME: " + ReCSystemProperty.OS_NAME.getValue());
		log.fine("############ OS ARCH: " + ReCSystemProperty.OS_ARCH.getValue());
		log.fine("############ OS VERSION: " + ReCSystemProperty.OS_VERSION.getValue());
	}

	private void installNativeLibs() throws IOException {

		final String userHome = ReCSystemProperty.USER_HOME.getValue();
		log.fine("User home is " + userHome);

		final String pluginsPath = userHome + File.separator + ReCSystemProperty.VLC_PLUGINS_DESTDIR.getValue();
		log.fine("Plugins copied to " + pluginsPath);

		final File pluginsDir = new File(pluginsPath);
		// Só extrai os plugins do zip se n existir a directoria de destino
		// onde irão ficar os plugins.
		if (!pluginsDir.exists()) {

			// TODO fazer de forma a substituir sempre os ficheiros que
			// alteraram (filesize, md5sum???)
			final String pluginsResourceName = ReCSystemProperty.VLC_PLUGINS_FILENAME.getValue();
			log.fine("Resource name is " + pluginsResourceName);

			final ClassLoader loader = Installer.class.getClassLoader();
			final InputStream pluginsFileStream = loader.getResourceAsStream(pluginsResourceName);
			if (pluginsFileStream == null) {
				showUnsupportedMessage();
				return;
			}
			final String path = saveToTempFile(pluginsFileStream, "libsfile", "tmp");
			pluginsFileStream.close();

			log.fine("File to be extracted: " + path);
			final ZipExtractor extractor = new ZipExtractor(path);
			extractor.addObserver(this);
			extractor.extractFiles(pluginsPath);

			log.fine("Media Plugins extracted!");
		} else {
			log.fine("Plugins already installed. Didn't install plugins!");
		}
	}

	protected String saveToTempFile(final InputStream is, final String prefix, final String suffix) {

		try {

			final File f = File.createTempFile(prefix, suffix);
			f.deleteOnExit();
			copyStream(is, f);
			return f.getAbsolutePath();

		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	private void copyStream(final InputStream is, final File f) throws IOException {

		final FileOutputStream fos = new FileOutputStream(f);
		final byte[] ba = new byte[1024];
		int bytesRead = 0;
		while (bytesRead >= 0) {
			bytesRead = is.read(ba);
			if (bytesRead > 0) {
				fos.write(ba, 0, bytesRead);
			}
		}
		fos.flush();
		fos.close();

	}

	protected abstract int getInstallerVersion();

	@Override
	public void update(final Observable o, final Object arg) {
		if (arg instanceof int[]) {
			final int[] params = (int[]) arg;
			if (params.length == 2) {
				try {
					final int progressValue = (int) ((double) params[0] / params[1] * 100);
					getInstallerService().updateProgress(progressValue);
					final String message = bundle.getString("install.plugins").replace("$1", String.valueOf(params[0]))
							.replace("$2", String.valueOf(params[1]));
					getInstallerService().setStatus(message);
				} catch (final UnavailableServiceException e) {
					log.severe(e.getMessage());
				}
			}
		}
	}

	class Uninstaller {

		private final File eLabDir;

		public Uninstaller() {

			final String userHome = ReCSystemProperty.USER_HOME.getValue();
			log.fine("User home is " + userHome);

			final String eLabPath = userHome + File.separator + ".eLab";
			log.fine("Plugins copied to " + eLabPath);

			eLabDir = new File(eLabPath);
		}

		public void uninstall() {

			final int result = JOptionPane.showConfirmDialog(null, bundle.getString("ask.remove.plugins"), "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			final boolean agree = (result == JOptionPane.YES_OPTION);

			try {

				getInstallerService().setStatus(bundle.getString("remove.preferences"));
				PreferencesUtils.removeUserPreference(Installer.INSTALLER_VERSION);
				PreferencesUtils.removeUserPreference(VideoViewerController.VLC_PATH_KEY);

				// Bruno remover também o xvid e a directoria do VLC das
				// preferences

				if (agree) {
					getInstallerService().setStatus(bundle.getString("remove.plugins"));
					deleteDir(eLabDir);
				}
			} catch (final UnavailableServiceException e) {
				log.severe(e.getMessage());
			}

			log.fine("eLab directory deleted");
		}

		private boolean deleteDir(final File dir) throws UnavailableServiceException {
			if (dir.isDirectory()) {
				final String[] children = dir.list();
				for (final String element : children) {
					final File file = new File(dir, element);
					final boolean success = deleteDir(file);
					if (!success) {
						return false;
					}
				}
			}

			final int progressValue = (int) ((double) dir.getTotalSpace() / eLabDir.getTotalSpace() * 100);
			getInstallerService().updateProgress(progressValue);

			return dir.delete();
		}
	}

	/**
	 * Efectua o tratamento de excepções ocorridas na aplicação, dando a
	 * possibilidade ao utilizador de prosseguir.
	 */
	protected void handleException() {

		try {

			final int installationResult = JOptionPane.showConfirmDialog(null, bundle.getString("failed"), "",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (installationResult != JOptionPane.OK_OPTION) {
				getInstallerService().installFailed();
			}
		} catch (final UnavailableServiceException e) {
			log.severe(e.getMessage());
		}
	}
}