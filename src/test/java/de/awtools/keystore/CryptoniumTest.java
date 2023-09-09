package de.awtools.keystore;

import java.security.KeyStore;

import org.junit.jupiter.api.Test;

class CryptoniumTest {

    @Test
    void encryptDecrypt() throws Exception {
        var ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(CryptoniumTest.class.getResourceAsStream("awtest.jks"), "awtest".toCharArray());
        
        KeyStoreService keyStoreService = new KeyStoreService(ks, "awtest", "awtest");
        var c = new Cryptonium(keyStoreService);
        
        byte[] encrypt = c.encrypt("Das ist ein Test".getBytes("UTF-8"));
        System.out.println(encrypt);
        
        c.decrypt(encrypt);
        System.out.println(encrypt);
        
        byte[] encrypt2 = c.encrypt("Das ist ein Test".getBytes("UTF-8"));
        System.out.println(encrypt2);
        
        byte[] decrypt2 = c.decrypt(encrypt2);
        System.out.println(decrypt2);
    }
}
