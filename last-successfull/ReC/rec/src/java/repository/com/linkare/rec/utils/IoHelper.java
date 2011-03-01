/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.utils;

import com.linkare.rec.ejb.RecMDBBean;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 *
 * @author artur
 */
public class IoHelper {
    
    
    private static final Log log = LogFactory.getLog(IoHelper.class);

    public IoHelper() {
    }

    public void writeToFS(String virtualPath, byte[] value) {
        BufferedOutputStream bof = null;
        try {
            bof = new BufferedOutputStream(new FileOutputStream(virtualPath));
            bof.write(value);
            bof.flush();
        } catch (FileNotFoundException fnfexc) {
            log.error("File not found for virtualpath: " + virtualPath ,fnfexc);
            //TODO: verificar esta situação. Fazer throw de uma nova Exception????!!!
        } catch (IOException ioexc) {
            log.error("IOException writing to File System ",ioexc);
            //TODO: verificar esta situação. Fazer throw de uma nova Exception????!!!
        } finally {
            try {
                bof.close();
            } catch (IOException ex) {
               log.error("IOException closing the stream ", ex);
            }
        }
    }

    public byte[] readFromFS(String virtualPath) {
        return null;
    }
}
