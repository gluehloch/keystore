package de.awtools.keystore;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Objects;

import org.junit.jupiter.api.Test;

class CryptoniumTest {

    @Test
    void eecryptEencrypt() throws Exception {
        var ks = KeyStore.getInstance(KeyStore.getDefaultType());
        InputStream resourceAsStream = CryptoniumTest.class.getResourceAsStream("/awtest.jks");
        ks.load(resourceAsStream, "awtest".toCharArray());

        KeyStoreService keyStoreService = new KeyStoreService(ks, "awtest", "awtest");
        var c = new Cryptonium(keyStoreService);

        byte[] encrypt1 = c.encrypt("Das ist ein Test".getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(encrypt1));

        byte[] encrypt2 = c.encrypt("Das ist ein Test".getBytes(StandardCharsets.UTF_8));
        System.out.println(Arrays.toString(encrypt2));
        
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

    String getPublicKeyString() throws IOException {
        byte[] fileBytes;
        try (InputStream stream = CryptoniumTest.class.getResourceAsStream("awtest.cer")) {
            fileBytes = Objects.requireNonNull(stream).readAllBytes();
        }
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

}
