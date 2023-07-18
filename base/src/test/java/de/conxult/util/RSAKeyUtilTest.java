/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author joerg
 */
public class RSAKeyUtilTest {

    String secret = RSAKeyUtilTest.class.getName();

    Base64.Encoder encoder = Base64.getEncoder();
    Base64.Decoder decoder = Base64.getDecoder();

    RSAKeyUtil testee = new RSAKeyUtil();

    @Test
    public void shouldEncodeDecodeKeyPair() throws Exception {
        RSAKeyPair keyPair = testee.generateKeyPair();

        String privateEncoded = testee.encode(keyPair.getPrivateKey());
        String publicEncoded  = testee.encode(keyPair.getPublicKey());

        RSAKeyPair keyPair2 = new RSAKeyPair(testee.decodePrivate(privateEncoded), testee.decodePublic(publicEncoded));
        String privateEncoded2 = testee.encode(keyPair2.getPrivateKey());
        String publicEncoded2  = testee.encode(keyPair2.getPublicKey());

        assertEquals(privateEncoded, privateEncoded2);
        assertEquals(publicEncoded, publicEncoded2);
    }

    @Test
    public void shouldEncryptPrivateDecryptPublic() throws Exception {
        RSAKeyPair keyPair = testee.generateKeyPair();
        String privateEncoded = testee.encode(keyPair.getPrivateKey());
        String publicEncoded  = testee.encode(keyPair.getPublicKey());

        RSAKeyPair keyPair2 = new RSAKeyPair(testee.decodePrivate(privateEncoded), testee.decodePublic(publicEncoded));

        RSAKeyPair[] keyPairs = new RSAKeyPair[] { keyPair, keyPair2 };

        for (int i = 0; (i < 4); i++) {
            byte[] encrypted = testee.encrypt(keyPairs[i/2].getPrivateKey(), secret.getBytes());
            byte[] decrypted = testee.decrypt(keyPairs[i%2].getPublicKey(), encrypted);

            assertEquals(secret, new String(decrypted));
        }
    }

    @Test
    public void shouldEncryptPublicDecryptPrivate() throws Exception {
        RSAKeyPair keyPair = testee.generateKeyPair();
        String privateEncoded = testee.encode(keyPair.getPrivateKey());
        String publicEncoded  = testee.encode(keyPair.getPublicKey());

        RSAKeyPair keyPair2 = new RSAKeyPair(testee.decodePrivate(privateEncoded), testee.decodePublic(publicEncoded));

        RSAKeyPair[] keyPairs = new RSAKeyPair[] { keyPair, keyPair2 };

        for (int i = 0; (i < 4); i++) {
            byte[] encrypted = testee.encrypt(keyPairs[i/2].getPublicKey(), secret.getBytes());
            byte[] decrypted = testee.decrypt(keyPairs[i%2].getPrivateKey(), encrypted);

            assertEquals(secret, new String(decrypted));
        }

    }

    @Test
    public void generate10KeyPairs() throws Exception {
        long start = System.nanoTime();
        for (int i = 0; (i < 10); i++) {
            RSAKeyPair keyPair = testee.generateKeyPair();
            String privateEncoded = testee.encode(keyPair.getPrivateKey());
            String publicEncoded  = testee.encode(keyPair.getPublicKey());
        }
        long finish = System.nanoTime();
        System.out.println("10 took ms each " + (finish - start) / 10_000_000);
    }

    String Xencrypt(Key key, byte[] secret) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        return encoder.encodeToString(encryptCipher.doFinal(secret));
    }

    String Xdecrypt(Key key, byte[] secret) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        return encoder.encodeToString(encryptCipher.doFinal(secret));
    }

}
