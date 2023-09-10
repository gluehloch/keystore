package de.awtools.keystore;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.InputStream;
import java.security.KeyStore;

import org.junit.jupiter.api.Test;

class CryptoniumTest {

    @Test
    void eecryptEencrypt() throws Exception {
        var ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream resourceAsStream = CryptoniumTest.class.getResourceAsStream("/awtest.jks");
        ks.load(resourceAsStream, "awtest".toCharArray());

        KeyStoreService keyStoreService = new KeyStoreService(ks, "awtest", "awtest");
        var c = new Cryptonium(keyStoreService);

        byte[] encrypt2 = c.encrypt("Das ist ein Test".getBytes("UTF-8"));
        System.out.println(encrypt2);
        
        byte[] decrypt2 = c.decrypt(encrypt2);
        System.out.println(decrypt2);
        System.out.println(new String(decrypt2));
        
        assertThat(new String(decrypt2)).isEqualTo("Das ist ein Test");
    }

}
