package com.linkare.rec.impl.newface.installer.linux;

import java.io.IOException;

import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

import com.linkare.rec.impl.newface.installer.Installer;

/**
 * Classe de instalação dos codecs para Linux. Apenas pergunta à pessoa se tem os codecs instalados, dando-lhe a
 * oportunidade de instalá-los ou permite cancelar a execução da aplicação.
 * 
 * @author bcatarino
 */
public class LinuxInstaller extends Installer {

	public static void main(String[] args) throws UnavailableServiceException {

		//Bruno deixa rebentar ou trata de alguma forma?
		new LinuxInstaller().install(args);

	}

	@Override
	public void install(String[] args) throws UnavailableServiceException {

		try {

			super.install(args);

			int result = JOptionPane.showConfirmDialog(null, "A aplicação requer que os codecs de xvid estejam instalados."
					+ System.getProperty("line.separator") + "Por favor, verifique no seu gestor de pacotes se tem o ffmpeg "
					+ System.getProperty("line.separator") + "instalado. Deseja continuar?", "Instalação de codecs",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			boolean agree = (result == JOptionPane.YES_OPTION);
			if (!agree) {
				JOptionPane.showMessageDialog(null, "A execução da aplicação foi cancelada.");
				getInstallerService().installFailed();
			}

		} catch (IOException e) {
			getInstallerService().installFailed();
		}

		getInstallerService().installSucceeded(false);
	}
}
