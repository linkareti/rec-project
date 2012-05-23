/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.impl.newface.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
public class RemoveBadWord {
    
    public String REPLACE = "";
    public String REGEX   = "\\b(fdp|puta|viado|caralho|carago)\\b";
    
    public RemoveBadWord() {
    }
    Pattern pat = Pattern.compile(REGEX, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    public String filterBadWord(String message) {
        Matcher matcher = pat.matcher(message);
        StringBuffer stb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(stb, REPLACE);
        }
        matcher.appendTail(stb);
        System.out.println("Mensagem filtrada: "+stb.toString());
        
        return message;
    }
    
    public static void main(String[] args) {
        (new RemoveBadWord()).filterBadWord("Teste para ver se esse metodo fdp troca o caralho por algum simbolo viado.");
    }
}
