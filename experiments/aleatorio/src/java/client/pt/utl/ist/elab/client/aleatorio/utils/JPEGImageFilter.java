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
    
    private java.util.Hashtable filtros = null;
    private String fullDescription = "JPEG Image Files";
    private String description = null;
    
    private boolean useExtensionsInDescription = true;
    
    /** Creates a new instance of JPEGImageFilter */
    public JPEGImageFilter() {
        this.filtros = new java.util.Hashtable(2);
        filtros.put("jpg",this);
    }
    
    public boolean accept(java.io.File file) {
        if (file != null )
        {
            if (file.isDirectory())
            {
                return true;
            }
            String extension = getExtension(file);
            if (extension != null && filtros.get(extension) != null)
            {
                return true;
            }
        }
        return false;
    }
    public String getExtension(java.io.File f) {
	if(f != null) {
	    String filename = f.getName();
	    int i = filename.lastIndexOf('.');
	    if(i>0 && i<filename.length()-1) {
		return filename.substring(i+1).toLowerCase();
	    };
	}
	return null;
    }
    
    public String getDescription() {
	if(fullDescription == null) {
	    if(description == null || isExtensionListInDescription()) {
 		fullDescription = description==null ? "(" : description + " (";
		// build the description from the extension list
		java.util.Enumeration extensions = filtros.keys();
		if(extensions != null) {
		    fullDescription += "." + (String) extensions.nextElement();
		    while (extensions.hasMoreElements()) {
			fullDescription += ", ." + (String) extensions.nextElement();
		    }
		}
		fullDescription += ")";
	    } else {
		fullDescription = description;
	    }
	}
	return fullDescription;
    }
    public void setExtensionListInDescription(boolean b) {
	useExtensionsInDescription = b;
	fullDescription = null;
    }
    public boolean isExtensionListInDescription() {
	return useExtensionsInDescription;
    }
    
}
