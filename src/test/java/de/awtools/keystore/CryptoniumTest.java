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

        byte[] encrypt1 = c.encrypt("Das ist ein Test".getBytes("UTF-8"));
        System.out.println(encrypt1);

        byte[] encrypt2 = c.encrypt("Das ist ein Test".getBytes("UTF-8"));
        System.out.println(encrypt2);
        
        assertThat(encrypt1).isNotEqualTo(encrypt2);
 
        byte[] decrypt1 = c.decrypt(encrypt1);
        String x = new String(decrypt1);
        System.out.println(x);

        byte[] decrypt2 = c.decrypt(encrypt2);
        String y = new String(decrypt2);
        System.out.println(y);
        
        assertThat(x).isEqualTo(y);
        assertThat(new String(decrypt2)).isEqualTo("Das ist ein Test");
    }

}
