package pt.utl.ist.elab.client.Aleatorio.utils;

/*
 * ImageFilter.java
 *
 * Created on 10 de Abril de 2003, 11:14
 */


/**
 *
 * @author Pedro Carvalho - LEFT - IST
 */
public class ImageFilter extends javax.swing.filechooser.FileFilter{
    
    private java.util.Hashtable filtros = null;
    private String fullDescription = "JPEG, BitMap and Tif Image Files";
    private String description = null;
    
    private boolean useExtensionsInDescription = true;
    
    /** Creates a new instance of ImageFilter */
    public ImageFilter() {
        this.filtros = new java.util.Hashtable(5);
        filtros.put("jpg",this);
        filtros.put("bmp",this);
        filtros.put("tif",this);
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
