package com.linkare.rec.impl.newface.installer.mac;

import javax.jnlp.UnavailableServiceException;

import com.linkare.rec.impl.newface.installer.Installer;

/**
 * Classe de instalação dos codecs para Mac. Apenas pergunta à pessoa se tem os codecs instalados, dando-lhe a
 * oportunidade de instalá-los ou permite cancelar a execução da aplicação.
 * 
 * @author bcatarino
 */
public class MacInstaller extends Installer {

	public static void main(String[] args) throws UnavailableServiceException {

		//Bruno deixa rebentar ou trata de alguma forma?
		new MacInstaller().install(args);

	}

	@Override
	public void install(String[] args) throws UnavailableServiceException {

		//		try {

		System.out.println(System.getProperty("java.library.path"));
		System.setProperty("java.library.path", "/Applications/VLC.app/Contents/MacOS/lib");
		System.out.println("Running mac installer");
		for (String arg : args) {
			log.fine("Installer arg: " + arg);
		}

			printOSInfo();
		//			super.install(args);

		//		} catch (IOException e) {
		//			getInstallerService().installFailed();
		//		}

		getInstallerService().installSucceeded(false);
	}
}
