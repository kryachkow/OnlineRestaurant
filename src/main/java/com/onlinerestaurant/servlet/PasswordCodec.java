package com.onlinerestaurant.servlet;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * Codec for encode and compare passwords.
 */
public class PasswordCodec {
    private static final int SALT_LENGTH = 128;
    private static final String ALGORITHM_NAME = "PBKDF2WithHmacSHA512";
    private static final int ITERATIONS = 200000;
    private static final int KEY_LENGTH = 512;


    /**
     * @param password password that need to be encoded.
     * @return encoded password with length of 160.
     */
    public static String encodePassword(final String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = getSalt();
        byte[] hashedPasswordBytes = hashPasswordToBytes(password.toCharArray(), salt);
        return Hex.encodeHexString(hashedPasswordBytes) + Hex.encodeHexString(salt);
    }

    /**
     * @param passwordCharArray charArray of password that need to be encoded.
     * @param salt salt.
     * @return byte array with 64 length of hashed password.
     */
    private static byte[] hashPasswordToBytes(final char[] passwordCharArray, final byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM_NAME);
        PBEKeySpec keySpec = new PBEKeySpec(passwordCharArray, salt, ITERATIONS, KEY_LENGTH);
        SecretKey key = skf.generateSecret(keySpec);
        return key.getEncoded();
    }


    /**
     * @return salt 16 length.
     */
    private static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_LENGTH/8];
        sr.nextBytes(salt);
        return salt;
    }

    /**
     * @param password not encoded password that need check.
     * @param encodedPassword encoded password.
     * @return true if passwords equals.
     */
    public static boolean checkPassword(String password, String encodedPassword) throws DecoderException, NoSuchAlgorithmException, InvalidKeySpecException {
        String passwordHexed = encodedPassword.substring(0, KEY_LENGTH / 8 * 2);
        byte[] salt = Hex.decodeHex(encodedPassword.substring(KEY_LENGTH / 8 * 2).toCharArray());
        byte[] hashedPasswordBytes = hashPasswordToBytes(password.toCharArray(), salt);
        return Hex.encodeHexString(hashedPasswordBytes).equals(passwordHexed);
    }
}
