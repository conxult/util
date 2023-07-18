/*
 * Copyright by https://conxult.de
 */
package de.conxult.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 *
 * @author joerg
 */
@Getter @Setter @Accessors(chain = true)
public class RSAKeyPair {

    PrivateKey privateKey;
    PublicKey  publicKey;

    public RSAKeyPair() {
    }

    public RSAKeyPair(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
    

}
