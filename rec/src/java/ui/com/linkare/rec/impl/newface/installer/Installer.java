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
	
	protected ResourceBundle bundle = ResourceBundle.getBundle(
			"com.linkare.rec.impl.newface.installer.installer");

	protected ExtensionInstallerService getInstallerService() throws UnavailableServiceException {

		if (installerService == null) {
			installerService = (ExtensionInstallerService) ServiceManager.lookup("javax.jnlp.ExtensionInstallerService");
		}
		return installerService;
	}

	public void executeInstaller(String[] args) 
			throws UnavailableServiceException, IOException {

		for (String arg : args) {
			log.fine("Installer arg: " + arg);
		}

		if ("install".equals(args[0])) {
			install();
		} else if ("uninstall".equals(args[0])) {
			new Uninstaller().uninstall();
		} else {
			throw new UnsupportedOperationException("Apenas as opções " +
					"de install e uninstall são válidas para o instalador");
		}

		getInstallerService().updateProgress(100);
		getInstallerService().setStatus(bundle.getString("complete"));
		getInstallerService().installSucceeded(false);
	}
	
	private void install() throws IOException, UnavailableServiceException {

		int version = readVersionNumber();
		if (version == 0 || version < getInstallerVersion()) {
			
			printOSInfo();
			installSpecificSO();
			installNativeLibs();

			PreferencesUtils.writeUserPreference(INSTALLER_VERSION, 
					String.valueOf(getInstallerVersion()));
		}
	}
	
	private void showUnsupportedMessage() {
		JOptionPane.showMessageDialog(null, bundle.getString("unsupported"),
				"", JOptionPane.OK_OPTION);
	}
	
	/**
	 * Reads the installer version number from the preferences.
	 * @return
	 */
	private int readVersionNumber() {
		
		String versionStr = PreferencesUtils.readUserPreference(INSTALLER_VERSION);
		try {
			return Integer.parseInt(versionStr);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	
	/**
	 * Ponto de extensão para efectuar uma instalação específica para um determinado SO, para além da instalação default
	 * já feita por esta classe.
	 */
	protected abstract void installSpecificSO() throws UnavailableServiceException;

	protected void printOSInfo() {

		log.fine("jna.library.path: " + System.getProperty("jna.library.path"));

		log.fine("############ OS NAME: " + System.getProperty("os.name"));
		log.fine("############ OS ARCH: " + System.getProperty("os.arch"));
		log.fine("############ OS VERSION: " + System.getProperty("os.version"));
	}

	private void installNativeLibs() throws IOException {

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

			ClassLoader loader = Installer.class.getClassLoader();
			InputStream pluginsFileStream = loader.getResourceAsStream(pluginsResourceName);
			if (pluginsFileStream == null) {
				showUnsupportedMessage();
				return;
			}
			String path = saveToTempFile(pluginsFileStream, "libsfile", "tmp");
			pluginsFileStream.close();

			log.fine("File to be extracted: " + path);
			ZipExtractor extractor = new ZipExtractor(path);
			extractor.addObserver(this);
			extractor.extractFiles(pluginsPath);

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
	
	protected abstract int getInstallerVersion();
	
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof int[]) {
			int[] params = (int[])arg;
			if (params.length == 2) {
				try {
					int progressValue = (int)((double)params[0]/params[1]*100);
					getInstallerService().updateProgress(progressValue);
					String message = bundle.getString("install.plugins").replace("$1", String.valueOf(params[0]))
							.replace("$2", String.valueOf(params[1]));
					getInstallerService().setStatus(message);
				} catch (UnavailableServiceException e) {
					//Bruno tratar excepção
					e.printStackTrace();
				}
			}
		}
		//Bruno else dá erro ou deixa passar?
	}
	
	class Uninstaller {
		
		private File eLabDir;
		
		public Uninstaller() {
			
			String userHome = System.getProperty("user.home");
			log.fine("User home is " + userHome);

			String eLabPath = userHome + File.separator + ".eLab";
			log.fine("Plugins copied to " + eLabPath);
			
			eLabDir = new File(eLabPath);
		}
		
		public void uninstall() {
			
			int result = JOptionPane.showConfirmDialog(null, 
					bundle.getString("ask.remove.plugins"),
					"", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

			boolean agree = (result == JOptionPane.YES_OPTION);
			
			try {

				getInstallerService().setStatus(bundle.getString("remove.preferences"));
				PreferencesUtils.removeUserPreference(INSTALLER_VERSION);
			
				//Bruno deveria remover também o xvid e a directoria do VLC 

				if (agree) {
					getInstallerService().setStatus(bundle.getString("remove.plugins"));
					deleteDir(eLabDir);
				}
			} catch (UnavailableServiceException e) {
				//Bruno tratar excepcao
				e.printStackTrace();
			}

			log.fine("eLab directory deleted");			
		}
		
		private boolean deleteDir(File dir) throws UnavailableServiceException {
			if (dir.isDirectory()) {
				String[] children = dir.list();
				for (int i = 0; i < children.length; i++) {
					File file = new File(dir, children[i]);
					boolean success = deleteDir(file);
					if (!success) {
						return false;
					}
				}
			}
			
			int progressValue = (int) ((double) dir.getTotalSpace() 
					/ eLabDir.getTotalSpace() * 100);
			getInstallerService().updateProgress(progressValue);
			
			return dir.delete(); 
		}
	}
}