package com.linkare.rec.impl.newface.installer.mac;

import java.io.IOException;

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

		try {
			//Bruno deixa rebentar ou trata de alguma forma?
			new MacInstaller().install(args);
		} catch (IOException e) {
			//Bruno fazer tratamento da excepção
		}
	}

	@Override
	public void installSpecificSO() {

		System.out.println(System.getProperty("java.library.path"));
		System.setProperty("java.library.path", "/Applications/VLC.app/Contents/MacOS/lib");
		System.out.println("Running mac installer");

		//Bruno falta a parte de mostrar a mensagem a dizer q n foi possível instalar o JVLC.
	}
}
