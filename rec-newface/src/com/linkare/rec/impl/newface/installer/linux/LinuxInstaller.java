package com.linkare.rec.impl.newface.installer.linux;

import com.linkare.rec.impl.newface.installer.Installer;
import javax.jnlp.ExtensionInstallerService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

/**
 * Classe de instalação dos codecs para Linux. Apenas pergunta à pessoa se tem
 * os codecs instalados, dando-lhe a oportunidade de instalá-los ou permite
 * cancelar a execução da aplicação.
 * @author bcatarino
 */
public class LinuxInstaller extends Installer {

    public static void main(String[] args) {

        new LinuxInstaller().install(args);
    }

    @Override
    public void install(String[] args) {

        super.install(args);

        ExtensionInstallerService installerService = null;
                
        try {

            // Bruno internacionalização das mensagens?
            installerService = (ExtensionInstallerService) ServiceManager.
                        		lookup("javax.jnlp.ExtensionInstallerService");

            int result = JOptionPane.showConfirmDialog(null,
				"A aplicação requer que os codecs de xvid estejam instalados." +
                System.getProperty("line.separator") + 
                "Por favor, verifique no seu gestor de pacotes se tem o ffmpeg " +
                System.getProperty("line.separator") + 
                "instalado. Deseja continuar?",
                "Instalação de codecs",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE );
			boolean agree = (result == JOptionPane.YES_OPTION);
			if (agree) {
				installerService.installSucceeded(false);
			} else {
				JOptionPane.showMessageDialog(null,
					"A execução da aplicação foi cancelada.");
                //Bruno forma de não dar erro no final... simplesmente cancelar!!!! Ligação com application-desc?
				installerService.installFailed();
			}

            System.out.println("############################### Terminou instalação ###############################");

        } catch (UnavailableServiceException ex) {
            log.severe("Ocorreu um erro na instalação");
            installerService.installFailed();
        }
    }
}
