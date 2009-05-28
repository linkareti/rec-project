package com.linkare.rec.impl.newface.component.media;

import com.linkare.rec.impl.newface.utils.ZipExtractor;
import com.sun.jna.Platform;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que faz todo o setup inicial do módulo de vídeo, extraindo
 * as libs nativas necessárias para o filesystem, de forma independente do
 * Sistema Operativo e carregando-as de acordo com as dependências.
 * @author bcatarino
 */
public class MediaSetup {

    /**
     * Efectua todo o setup inicial da aplicação relacionada com o módulo de
     * vídeo, que inclui: <ul>
     * <li> Load das bibliotecas nativas distribuídas juntamente com a
     * aplicação e que são necessárias ao funcionamento do módulo de vídeo,
     * pela ordem de dependências correcta </li>
     * <li> Extracção dos plugins necessários ao funcionamento do vídeo para
     * uma directoria do filesystem. </li> </ul>
     */
    public static void setup() {

        //TODO usado apenas para fazer o matching das caracteristicas do SO com especificacao de resources no JNLP.Delete no final ou logar?
        printOSInfo();

        installNativeLibs();
        loadNativeLibraries();
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

    //Delete???
    private static void printOSInfo() {

        System.out.println("jna.library.path: " + System.getProperty("jna.library.path"));

        System.out.println("############ OS NAME: " + System.getProperty("os.name"));
        System.out.println("############ OS ARCH: " + System.getProperty("os.arch"));
        System.out.println("############ OS VERSION: " + System.getProperty("os.version"));
    }

    private static void loadNativeLibraries() {

        //TODO alterar security manager para n ser null (O security all-permissions do jnlp n devia fazer isso????)
        System.setSecurityManager(null);

//        System.getSecurityManager().checkPermission(new RuntimePermission("loadLibrary.jnidispatch"));
        
//        System.setProperty("jna.library.path", System.getProperty("user.home")
//                + File.separator + System.getProperty("vlc.core.destdir"));

        // No Windows, é necessário fazer o System.loadLibrary antes do native.LoadLibrary. Ver se no Linux está alguma coisa num path esquisito
        loadLibrary(Platform.isWindows() ? "libvlccore" : "vlccore");
        loadLibrary(Platform.isWindows() ? "libvlc" : "vlc");
    }

    private static void loadLibrary(String name) {

        try {
            
            System.out.println("!!!!!!!!!!!!!!! Loading " + name + "!!!!!!!!!!!!!!!!!!");
            System.loadLibrary(name);
            System.out.println("!!!!!!!!!!!!!!! Done Loading " + name + "!!!!!!!!!!!!!!!!!!");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static String saveToTempFile(InputStream is) {
        try {
            File f = File.createTempFile("libsfile", "tmp");
            f.deleteOnExit();
            copyStream(is, f);
            return f.getAbsolutePath();
        } catch (IOException ex) {
            Logger.getLogger(MediaSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private static void installNativeLibs() {

        try {
            
            //TODO verificar se a directoria já existe e não extrair nesse caso.
            String userHome = System.getProperty("user.home");
            System.out.println("User home is " + userHome);

//            String vlcCorePath = userHome + File.separator + System.getProperty("vlc.core.destdir");
//            System.out.println("Plugins copied to " + vlcCorePath);

            String pluginsPath = userHome + File.separator + System.getProperty("vlc.plugins.destdir");
            System.out.println("Plugins copied to " + pluginsPath);

            File pluginsDir = new File(pluginsPath);
            // Só extrai os plugins do zip se n existir a directoria de destino
            // onde irão ficar os plugins.
            if (!pluginsDir.exists()) {
                //TODO fazer de forma a substituir sempre os ficheiros que alteraram (filesize, md5sum???)
                String pluginsResourceName = System.getProperty("vlc.plugins.filename");
                System.out.println("Resource name is " + pluginsResourceName);

                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                InputStream pluginsFileStream = loader.getResourceAsStream(pluginsResourceName);
                String path = saveToTempFile(pluginsFileStream);
                pluginsFileStream.close();

                System.out.println("File to be extracted: " + path);
                ZipExtractor.extractFiles(path, pluginsPath);

                System.out.println("Terminou extracção de plugins");
            } else {
                System.out.println("Não extraiu plugins!!!!");
            }
            
        } catch (IOException ex) {
            Logger.getLogger(MediaSetup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devolve um conjunto de parâmetros de configuração do VLC que permite
     * fazer uma inicialização por default. Deve ter a system property
     * <b>vlc.plugins.destdir</b> definida com um path relativo, cuja root será
     * a user.home.
     * @return
     */
    public static String[] getDefaultMediaParameters() {

        File pluginsDir = new File(System.getProperty("user.home"),
                System.getProperty("vlc.plugins.destdir"));

        System.out.println("Plugins Path = " + pluginsDir.getAbsolutePath());

        int i = 0;
        //TODO ver se todos são necessários ou se deve apagar alguns ou acrescentar outros.
        String[] arg = new String[4];
        arg[i++] = "--intf=dummy";
        arg[i++] = "--ignore-config";
        arg[i++] = "--no-plugins-cache";
        arg[i++] = "--plugin-path=" + pluginsDir.getAbsolutePath();
        return arg;
    }
}