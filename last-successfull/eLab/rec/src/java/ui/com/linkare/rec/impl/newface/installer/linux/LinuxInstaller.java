package com.linkare.rec.impl.newface.installer.linux;

import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

import com.linkare.rec.impl.newface.installer.Installer;

/**
 * Classe de instalação dos codecs para Linux. Apenas pergunta à pessoa se tem
 * os codecs instalados, dando-lhe a oportunidade de instalá-los ou permite
 * cancelar a execução da aplicação.
 * 
 * @author bcatarino
 */
public class LinuxInstaller extends Installer {

	/**
	 * Indica a versão do instalador. Sempre que seja feita uma alteração à
	 * aplicação que implique nova instalar, deverá ser incrementado manualmente
	 * este valor.
	 */
	private static final int INSTALLER_VERSION = 3;

	public static void main(final String[] args) {
		new LinuxInstaller().executeInstaller(args);
	}

	@Override
	public void installSpecificSO() throws UnavailableServiceException {

		final int result = JOptionPane.showConfirmDialog(null, bundle.getString("linux.ask.install.xvid"),
				bundle.getString("install.codecs"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

		final boolean agree = (result == JOptionPane.YES_OPTION);

		if (!agree) {
			JOptionPane.showMessageDialog(null, bundle.getString("linux.canceled"));
			getInstallerService().installFailed();
		}
	}

	@Override
	protected int getInstallerVersion() {
		return LinuxInstaller.INSTALLER_VERSION;
	}
}