/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 *
 * @author joerg
 */
public class RSAKeyUtil {

    static boolean          initialized = false;
    static KeyPairGenerator generator;
    static KeyFactory       factory;

    public RSAKeyUtil() {
        if (!initialized) {
            try {
                generator = KeyPairGenerator.getInstance("RSA");
                generator.initialize(2048);
                factory   = KeyFactory.getInstance("RSA");
                initialized = true;
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {

            }
        }
    }

    public RSAKeyPair generateKeyPair() throws Exception {
        KeyPair pair = generator.generateKeyPair();
        return new RSAKeyPair(pair.getPrivate(), pair.getPublic());
    }

    public String encode(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public PrivateKey decodePrivate(String encoded) {
        try {
            EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(encoded));
            return factory.generatePrivate(privateKeySpec);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            return null;
        }
    }

    public PublicKey decodePublic(String encoded) {
        try {
            EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(encoded));
            return factory.generatePublic(publicKeySpec);
        } catch (InvalidKeySpecException invalidKeySpecException) {
            return null;
        }
    }

    public byte[] encrypt(Key key, byte[] content) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(content);
    }

    public byte[] decrypt(Key key, byte[] content) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(content);
    }

}
