/*
 * JPEGImageFilter.java
 *
 * Created on 27 de Junho de 2003, 11:17
 */

package pt.utl.ist.elab.client.aleatorio.utils;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class JPEGImageFilter extends javax.swing.filechooser.FileFilter {

	private java.util.Hashtable<String, JPEGImageFilter> filtros = null;
	private String fullDescription = "JPEG Image Files";
	private final String description = null;

	private boolean useExtensionsInDescription = true;

	/** Creates a new instance of JPEGImageFilter */
	public JPEGImageFilter() {
		filtros = new java.util.Hashtable<String, JPEGImageFilter>(2);
		filtros.put("jpg", this);
	}

	@Override
	public boolean accept(final java.io.File file) {
		if (file != null) {
			if (file.isDirectory()) {
				return true;
			}
			final String extension = getExtension(file);
			if (extension != null && filtros.get(extension) != null) {
				return true;
			}
		}
		return false;
	}

	public String getExtension(final java.io.File f) {
		if (f != null) {
			final String filename = f.getName();
			final int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				return filename.substring(i + 1).toLowerCase();
			}
		}
		return null;
	}

	@Override
	public String getDescription() {
		if (fullDescription == null) {
			if (description == null || isExtensionListInDescription()) {
				fullDescription = description == null ? "(" : description + " (";
				// build the description from the extension list
				final java.util.Enumeration<String> extensions = filtros.keys();
				if (extensions != null) {
					fullDescription += "." + extensions.nextElement();
					while (extensions.hasMoreElements()) {
						fullDescription += ", ." + extensions.nextElement();
					}
				}
				fullDescription += ")";
			} else {
				fullDescription = description;
			}
		}
		return fullDescription;
	}

	public void setExtensionListInDescription(final boolean b) {
		useExtensionsInDescription = b;
		fullDescription = null;
	}

	public boolean isExtensionListInDescription() {
		return useExtensionsInDescription;
	}

}
