package com.linkare.rec.impl.newface.installer.windows;

import com.linkare.rec.impl.newface.installer.Installer;
import com.linkare.rec.impl.newface.utils.ZipExtractor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.jnlp.ExtensionInstallerService;
import javax.jnlp.UnavailableServiceException;
import javax.swing.JOptionPane;

/**
 * Classe responsável pela parte de instalação específica para Windows.
 * @author bcatarino
 */
public class WindowsInstaller extends Installer {

    @Override
    public void install(String[] args) throws UnavailableServiceException {

        log.fine("Beginning installation");

        log.fine("Installed native libraries and extracted media plugins");

        ExtensionInstallerService installerService = null;
        try {

            super.install(args);

            int result = JOptionPane.showConfirmDialog(null,
				"A aplicação requer que os codecs de xvid estejam instalados." +
                System.getProperty("line.separator") + 
                "Se não tem, ou não tem a certeza se estão instalados, carregue ok. " +
                System.getProperty("line.separator") +
                "Não poderá visualizar o vídeo se não tiver os codecs " +
                System.getProperty("line.separator") +
                "necessários instalados na sua máquina.",
                "Instalação de codecs xvid",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE );
			boolean agree = (result==JOptionPane.YES_OPTION);
            
            boolean installOk = false;
			if (agree) {
                installOk = installXvid();
			}

            if (installOk) {
                JOptionPane.showMessageDialog(null, "Codecs instalados com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Os codecs não foram instalados. O vídeo poderá não ser visível.");
            }

        } catch (Exception ex) {
            
            int installationResult = JOptionPane.showConfirmDialog(null,
                            "Erro na instalação. Deseja prosseguir, mesmo podendo não ter vídeo?",
                            "",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE );
            
            if (installationResult != JOptionPane.OK_OPTION) {
                getInstallerService().installFailed();
            }
        }

        getInstallerService().installSucceeded(false);
    }

    public boolean installXvid() throws IOException, InterruptedException {

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream pluginsFileStream = loader.getResourceAsStream("xvid.zip");

        log.fine("Saving xvid installer to temp file...");

        String path = saveToTempFile(pluginsFileStream, "xvid", "tmp");
        pluginsFileStream.close();

        log.fine("Path of temporary xvid.zip temporary file: " + path);

        ZipExtractor.extractFiles(path, System.getProperty("user.home"));

        String xvidPath = System.getProperty("user.home")
                + File.separator + "xvid.exe";

        log.fine("xvid installer extracted to " + xvidPath);
        log.fine("Running xvid installer...");

        Process xvid = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", xvidPath});

        return xvid.waitFor() == 0;
    }

    public static void main(String[] args) throws UnavailableServiceException {

        //Bruno deixa rebentar ou trata de alguma forma?
        new WindowsInstaller().install(args);
    }
}
