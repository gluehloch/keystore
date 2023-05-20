package de.awtools.keystore;

import java.security.PublicKey;
import java.security.Key;

import javax.crypto.Cipher;

public class Cryptonium {

    private static final String ALGORITHM = "RSA";

    private final KeyStoreService keyStoreService;

    public Cryptonium(KeyStoreService keyStoreService) {
        this.keyStoreService = keyStoreService;
    }

    /**
     * Entschüsseln
     */
    public byte[] encrypt(byte[] inputData) throws Exception {
        PublicKey key = keyStoreService.publicKey().orElseThrow();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return  cipher.doFinal(inputData);
    }

    /**
     * Verschlüsseln
     */
    public byte[] decrypt(byte[] inputData) throws Exception {
        Key key = keyStoreService.privateKey().orElseThrow();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(inputData);
    }

}