package com.linkare.rec.impl.newface.installer;

import com.linkare.rec.impl.newface.utils.ZipExtractor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

/**
 * Efectua a instalação comum a todas as plataformas.
 * @author bcatarino
 */
public class Installer {

    protected static final Logger log = Logger.getLogger(Installer.class.getName());

    public void install(String[] args) {
        
        for (String a : args) {
            System.out.println("ARG: " + a);
        }

        printOSInfo();

        installNativeLibs();

    }

    public static void main(String[] args) {

        new Installer().install(args);
    }

    private static void printOSInfo() {

        System.out.println("!!!!!!!!!!!!!!!!!BEFORE LOGGING");

        log.fine("jna.library.path: " + System.getProperty("jna.library.path"));

        log.fine("############ OS NAME: " + System.getProperty("os.name"));
        log.fine("############ OS ARCH: " + System.getProperty("os.arch"));
        log.fine("############ OS VERSION: " + System.getProperty("os.version"));
    }

    private static void installNativeLibs() {

        try {

            //TODO verificar se a directoria já existe e não extrair nesse caso.
            String userHome = System.getProperty("user.home");
            log.fine("User home is " + userHome);

//            String vlcCorePath = userHome + File.separator + System.getProperty("vlc.core.destdir");
//            System.out.println("Plugins copied to " + vlcCorePath);

            String pluginsPath = userHome + File.separator + System.getProperty("vlc.plugins.destdir");
            log.fine("Plugins copied to " + pluginsPath);

            File pluginsDir = new File(pluginsPath);
            // Só extrai os plugins do zip se n existir a directoria de destino
            // onde irão ficar os plugins.
            if (!pluginsDir.exists()) {
                //TODO fazer de forma a substituir sempre os ficheiros que alteraram (filesize, md5sum???)
                String pluginsResourceName = System.getProperty("vlc.plugins.filename");
                log.fine("Resource name is " + pluginsResourceName);

                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                InputStream pluginsFileStream = loader.getResourceAsStream(pluginsResourceName);
                String path = saveToTempFile(pluginsFileStream);
                pluginsFileStream.close();

                log.fine("File to be extracted: " + path);
                ZipExtractor.extractFiles(path, pluginsPath);

                log.fine("Terminou extracção de plugins");
            } else {
                log.fine("Não extraiu plugins!!!!");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected static String saveToTempFile(InputStream is) {
        try {
            File f = File.createTempFile("libsfile", "tmp");
            f.deleteOnExit();
            copyStream(is, f);
            return f.getAbsolutePath();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static void copyStream(InputStream is, File f) throws IOException {

        FileOutputStream fos = new FileOutputStream(f);
        byte[] ba = new byte[1024];
        int bytesRead = 0;
        while (bytesRead >= 0) {
            bytesRead = is.read(ba);
            if(bytesRead>0)
                fos.write(ba, 0, bytesRead);
        }
        fos.flush();
        fos.close();

    }
}
