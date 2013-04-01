package com.linkare.rec.impl.newface.component.media;

import java.io.File;
import java.util.logging.Logger;

import com.linkare.rec.impl.config.ReCSystemProperty;
import com.sun.jna.Platform;

/**
 * Classe que faz todo o setup inicial do módulo de vídeo, extraindo as libs
 * nativas necessárias para o filesystem, de forma independente do Sistema
 * Operativo e carregando-as de acordo com as dependências.
 * 
 * @author bcatarino
 */
public class MediaSetup {

	private static final Logger log = Logger.getLogger(VideoViewerController.class.getName());

	/**
	 * Efectua todo o setup inicial da aplicação relacionada com o módulo de
	 * vídeo, que inclui:
	 * <ul>
	 * <li>Load das bibliotecas nativas distribuídas juntamente com a aplicação
	 * e que são necessárias ao funcionamento do módulo de vídeo, pela ordem de
	 * dependências correcta</li>
	 * <li>Extracção dos plugins necessários ao funcionamento do vídeo para uma
	 * directoria do filesystem.</li>
	 * </ul>
	 */
	public static void setup() {

		// TODO usado apenas para fazer o matching das caracteristicas do SO com
		// especificacao de resources no JNLP.Delete no final ou logar?
		// printOSInfo();

		// installNativeLibs();
		MediaSetup.loadNativeLibraries();
	}

	// Delete
	// private static void copyStream(InputStream is, File f) throws IOException
	// {
	//
	// FileOutputStream fos = new FileOutputStream(f);
	// byte[] ba = new byte[1024];
	// int bytesRead = 0;
	// while (bytesRead >= 0) {
	// bytesRead = is.read(ba);
	// if (bytesRead > 0)
	// fos.write(ba, 0, bytesRead);
	// }
	// fos.flush();
	// fos.close();
	//
	// }

	// Delete???
	// private static void printOSInfo() {
	//
	// log.fine("jna.library.path: " + System.getProperty("jna.library.path"));
	// log.fine("############ OS NAME: " + System.getProperty("os.name"));
	// log.fine("############ OS ARCH: " + System.getProperty("os.arch"));
	// log.fine("############ OS VERSION: " + System.getProperty("os.version"));
	// }

	private static void loadNativeLibraries() {

		// No Windows, é necessário fazer o System.loadLibrary antes do
		// native.LoadLibrary. Ver se no Linux está alguma coisa num path
		// esquisito
		MediaSetup.loadLibrary(Platform.isWindows() ? "libvlccore" : "vlccore");
		MediaSetup.loadLibrary(Platform.isWindows() ? "libvlc" : "vlc");
	}

	private static void loadLibrary(final String name) {
		System.loadLibrary(name);
	}

	// Delete
	// private static String saveToTempFile(InputStream is) {
	// try {
	// File f = File.createTempFile("libsfile", "tmp");
	// f.deleteOnExit();
	// copyStream(is, f);
	// return f.getAbsolutePath();
	// } catch (IOException ex) {
	// Logger.getLogger(MediaSetup.class.getName()).log(Level.SEVERE, null, ex);
	// }
	// return null;
	// }

	// Delete
	// private static void installNativeLibs() {
	//
	// try {
	//
	// //TODO verificar se a directoria já existe e não extrair nesse caso.
	// String userHome = System.getProperty("user.home");
	// log.fine("User home is " + userHome);
	//
	// String pluginsPath = userHome + File.separator +
	// System.getProperty("vlc.plugins.destdir");
	// log.fine("Plugins copied to " + pluginsPath);
	//
	// File pluginsDir = new File(pluginsPath);
	// // Só extrai os plugins do zip se n existir a directoria de destino
	// // onde irão ficar os plugins.
	// if (!pluginsDir.exists()) {
	// //TODO fazer de forma a substituir sempre os ficheiros que alteraram
	// (filesize, md5sum???)
	// String pluginsResourceName = System.getProperty("vlc.plugins.filename");
	// log.fine("Resource name is " + pluginsResourceName);
	//
	// ClassLoader loader = MediaSetup.class.getClassLoader();
	//
	// InputStream pluginsFileStream =
	// loader.getResourceAsStream(pluginsResourceName);
	// String path = saveToTempFile(pluginsFileStream);
	// pluginsFileStream.close();
	//
	// log.fine("File to be extracted: " + path);
	// ZipExtractor.extractFiles(path, pluginsPath);
	//
	// log.fine("Terminou extracção de plugins");
	// } else {
	// log.fine("Não extraiu plugins!!!!");
	// }
	//
	// } catch (IOException ex) {
	// Logger.getLogger(MediaSetup.class.getName()).log(Level.SEVERE, null, ex);
	// }
	// }

	/**
	 * Devolve um conjunto de parâmetros de configuração do VLC que permite
	 * fazer uma inicialização por default. Deve ter a system property
	 * <b>vlc.plugins.destdir</b> definida com um path relativo, cuja root será
	 * a user.home.
	 * 
	 * @return
	 */
	public static String[] getDefaultMediaParameters() {

		final File pluginsDir = new File(ReCSystemProperty.USER_HOME.getValue(), ReCSystemProperty.VLC_PLUGINS_DESTDIR.getValue());

		MediaSetup.log.fine("Plugins Path = " + pluginsDir.getAbsolutePath());

		int i = 0;
		// TODO ver se todos são necessários ou se deve apagar alguns ou
		// acrescentar outros.
		final String[] arg = new String[4];
		arg[i++] = "--intf=dummy";
		arg[i++] = "--ignore-config";
		arg[i++] = "--no-plugins-cache";
		arg[i++] = "--plugin-path=" + pluginsDir.getAbsolutePath();
		return arg;
	}
}