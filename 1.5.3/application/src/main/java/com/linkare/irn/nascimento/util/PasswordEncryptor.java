package com.linkare.irn.nascimento.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Generic class to encrypt a password according to the MED5 algorithm. It has utility classes to check a given (unencrypted) password with another (encrypted)
 * password that may be used as a helper method in an authentication procedure.
 * 
 * @author Paulo Zenida - Linkare TI
 * 
 */
public final class PasswordEncryptor {

    private static final int PASSWORD_RADIX = 16;

    /**
     * Hiding constructor so that this utility class cannot be instantiated from outside.
     */
    private PasswordEncryptor() {
    }

    /**
     * 
     * @param password
     *            the password to be encrypted.
     * @return the encrypted password according to the MD5 algorithm.
     */
    public static String encryptPassword(String password) {
	if (password == null) {
	    throw new IllegalArgumentException("Password must be not null!");
	}

	byte buffer[] = new byte[password.length()];
	MessageDigest algorithm = getMessageDigest();
	buffer = password.getBytes();
	algorithm.update(buffer);

	byte[] digest = algorithm.digest();
	BigInteger bi = new BigInteger(digest);
	String pairLength = bi.toString(PASSWORD_RADIX);

	// append 0 if length is even
	if (pairLength.length() % 2 != 0) {
	    pairLength = "0" + pairLength;
	}
	return pairLength;
    }

    /**
     * 
     * @param encryptedPassword
     *            the encrypted password to be compared.
     * @param notEncryptedPassword
     *            the non encrypted password to be compared.
     * @return true if the non encrypted password, after being encrypted, is the equal to the encrypted password. Returns false otherwise.
     */
    public static boolean areEquals(String encryptedPassword, String notEncryptedPassword) {
	String digest = encryptPassword(notEncryptedPassword);
	return digest.equals(encryptedPassword);
    }

    /**
     * 
     * @return the MD5 digest algorithm.
     */
    private static MessageDigest getMessageDigest() {
	MessageDigest algorithm = null;
	try {
	    algorithm = MessageDigest.getInstance("MD5");
	} catch (NoSuchAlgorithmException e) {
	}
	algorithm.reset();
	return algorithm;
    }
}