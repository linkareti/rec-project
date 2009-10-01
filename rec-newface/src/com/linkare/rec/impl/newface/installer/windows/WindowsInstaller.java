package com.linkare.rec.impl.newface.installer.windows;

import com.linkare.rec.impl.newface.installer.Installer;
import java.io.IOException;
import java.io.InputStream;
import javax.jnlp.ExtensionInstallerService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

/**
 * Classe responsável pela parte de instalação específica para Windows.
 * @author bcatarino
 */
public class WindowsInstaller extends Installer {

    @Override
    public void install(String[] args) {

        super.install(args);

        ExtensionInstallerService installerService = null;
        try {

            installerService = (ExtensionInstallerService) ServiceManager.
                        		lookup("javax.jnlp.ExtensionInstallerService");

            int result = JOptionPane.showConfirmDialog(null,
				"A aplicação requer que os codecs de xvid estejam instalados." +
                System.getProperty("line.separator") + 
                "Se não tem, ou não tem a certeza se estão instalados, carregue ok. ",
                "Instalação de codecs",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE );
			boolean agree = (result==JOptionPane.YES_OPTION);
            
            boolean installOk = false;
			if (agree) {
                installOk = installXvid();
			} else {
                installOk = true;
			}

            if (installOk) {
                System.out.println("!!!!!!!!!!!!!!!!!!Instalação com sucesso!!!!!!!!!!!!!!!!!!!!!!");
                installerService.installSucceeded(false);
            } else {
                System.out.println("===================Instalação falhou=============================");
                //Bruno não pode dar installation failed se já tiver codecs.
//                installerService.installFailed();
            }

            System.out.println("############################### Terminou instalação ###############################");

        } catch (InterruptedException ex) {
            log.severe("######################## Erro ao instalar ###############################");
            installerService.installFailed();
        } catch (UnavailableServiceException ex) {
            log.severe("######################## Erro ao instalar ###############################");
            installerService.installFailed();
        } catch (IOException ex) {
            log.severe("######################## Erro ao instalar ###############################");
            installerService.installFailed();
        } 
    }

    public boolean installXvid() throws IOException, InterruptedException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream pluginsFileStream = loader.getResourceAsStream("xvid.exe");
        String path = saveToTempFile(pluginsFileStream);
        pluginsFileStream.close();
        System.out.println("path = " + path);

        Process xvid = Runtime.getRuntime().exec(path);

        return xvid.waitFor() == 0;
    }

    public static void main(String[] args) {

        new WindowsInstaller().install(args);
    }
}
