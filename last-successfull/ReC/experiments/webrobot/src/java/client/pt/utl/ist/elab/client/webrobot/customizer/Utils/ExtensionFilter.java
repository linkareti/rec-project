/*
 * ExeFilter.java
 *
 * Created on 13 de Dezembro de 2002, 15:54
 *
 *Utility to filter a file name, a file extension, or both!!!
 */

package pt.utl.ist.elab.client.webrobot.customizer.Utils;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ExtensionFilter extends javax.swing.filechooser.FileFilter {

	private String extName = null;
	private String fileName = null;
	private String customDesc = "";
	private boolean searchExt = false;
	private boolean searchName = false;
	private boolean searchExtAndName = false;

	/** Creates a new instance of ExtensionFilter */
	/*******************************************
	 * par1 = name to filter par2 = filter by extension, name or both Please
	 * remember that if you want to filter both give the filename.extension
	 **************************************************/

	public ExtensionFilter(final String par1, final String par2) {
		if (par2.equals("ext")) {
			extName = par1;
			searchExt = true;
		} else if (par2.equals("name")) {
			fileName = par1;
			searchName = true;
		} else if (par2.equals("nameAndExt")) {
			fileName = par1;
			searchExtAndName = true;
		}
	}

	// Accept all directories and all ext files.
	@Override
	public boolean accept(final java.io.File f) {
		if (f.isDirectory()) {
			return true;
		}

		final String extension = fileExt(f);
		final String fName = fileName(f);

		if (searchExt) {
			if (extension != null) {
				if (extension.equals(extName)) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} else if (searchName) {
			if (fName != null) {
				if (fName.equals(fileName)) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		} else if (searchExtAndName) {
			if (fileName.equals(f.getName())) {
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	private String fileExt(final java.io.File f) {
		String ext = null;
		final String s = f.getName();
		final int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	private String fileName(final java.io.File f) {
		String fName = null;
		final String s = f.getName();
		final int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			fName = s.substring(0, i);
		}
		return fName;
	}

	// The description of this filter
	@Override
	public String getDescription() {
		if (fileName == null) {
			return (customDesc + " (." + extName + ")");
		} else {
			return (customDesc + "  " + fileName);
		}
	}

	public void setDescription(final String customDesc) {
		this.customDesc = customDesc;
	}

	public String getExtension() {
		return extName;
	}
}