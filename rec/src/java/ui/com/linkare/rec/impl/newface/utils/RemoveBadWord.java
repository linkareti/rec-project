/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.impl.newface.utils;

import com.linkare.rec.am.RecServiceRemote;
import com.linkare.rec.am.repository.BadWordDTO;
import java.rmi.RemoteException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public class RemoveBadWord {

    public String REPLACE = "***";
    String lookupAddress = lookupAddress = System.getProperty("rec.am.endpoint")+"/RecServiceWS";
    String namespace = ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig").getString("mail.namespace");
    String part = java.util.ResourceBundle.getBundle("com.linkare.rec.impl.newface.resources.wsconfig").getString("mail.part");
    RecServiceRemote remote = WSServiceLocator.lookup(lookupAddress, namespace, part, RecServiceRemote.class);
    private List<BadWordDTO> badWordDTO = null;

    public List<BadWordDTO> getBadWordRegexList(String locale) {
        try {
            if(badWordDTO == null){
                badWordDTO = remote.getBadWordRegexByLocale(locale);
            }
        } catch (RemoteException ex) {
            Logger.getLogger(RemoveBadWord.class.getName()).log(Level.SEVERE, null, ex);
        }
        return badWordDTO;
    }

    public RemoveBadWord() {
    }

    public String filterBadWord(String message) {
        Pattern pat;
        Matcher matcher = null;
        for (BadWordDTO badword : badWordDTO) {
            pat = Pattern.compile("\\b"+badword.getRegex()+"\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            matcher = pat.matcher(message);
            message = matcher.replaceAll(REPLACE);
        }
        return message;
    }

    public static void main(String[] args) {
        (new RemoveBadWord()).filterBadWord("Teste para ver se esse metodo fdp troca o caralho por algum simbolo peneleiro.");
    }
}
