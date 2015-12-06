package com.linkare.rec.impl.newface.installer.mac;

import javax.jnlp.UnavailableServiceException;

import com.linkare.rec.impl.newface.installer.Installer;

/**
 * Classe de instalação dos codecs para Mac. Apenas pergunta à pessoa se tem os
 * codecs instalados, dando-lhe a oportunidade de instalá-los ou permite
 * cancelar a execução da aplicação.
 * 
 * @author bcatarino
 */
public class MacInstaller extends Installer {

	/**
	 * Indica a versão do instalador. Sempre que seja feita uma alteração à
	 * aplicação que implique nova instalar, deverá ser incrementado manualmente
	 * este valor.
	 */
	private static final int INSTALLER_VERSION = 3;

	public static void main(final String[] args) throws UnavailableServiceException {
		new MacInstaller().executeInstaller(args);
	}

	@Override
	public void installSpecificSO() {
		// Bruno falta a parte de mostrar a mensagem a dizer q n foi possível
		// instalar o JVLC.
	}

	@Override
	protected int getInstallerVersion() {
		return MacInstaller.INSTALLER_VERSION;
	}
}