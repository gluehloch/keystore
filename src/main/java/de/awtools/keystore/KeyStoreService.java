package de.awtools.keystore;

import java.util.Optional;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

/**
 * Defines KeyStore access to get public/private keys to create and validate the
 * JWT.
 * 
 * @author Andre Winkler
 */
public class KeyStoreService {

    private String keyStorePassword;

    private String keyStoreCertificate;

    private String keyStoreAlias;

    // private Resource keyStoreResource;

    private KeyStore ks;
    private Key key;
    
    Key getKey() {
        return key;
    }
    
    KeyStore getKeyStore() {
        return ks;
    }

//    public void init() throws Exception {
//        ks = KeyStore.getInstance(KeyStore.getDefaultType());
//        ks.load(keyStoreResource.getInputStream(), keyStorePassword.toCharArray());
//
//        // TODO Die Klasse kann nur den 'awtest' Schlüssel verwenden.
//        key = ks.getKey("awtest", keyStorePassword.toCharArray());
//    }

    public KeyStoreService(KeyStore keyStore) {
        this.ks = keyStore;
    }

    /**
     * Returns the public key to validate a JWT.
     * 
     * @return public key
     */
    public Optional<PublicKey> publicKey() {
        try {
            if (key instanceof PrivateKey) {
                //  
                // TODO Unterschied zwischen ks.getCertificate(...) and ks.getKey(...) ???
                //
                Certificate cert = ks.getCertificate(keyStoreCertificate);
                PublicKey publicKey = cert.getPublicKey();
                return Optional.of(publicKey);
            }

            return Optional.empty();
        } catch (KeyStoreException ex) {
            return Optional.empty();
        }
    }

    /**
     * Returns the private key to create a JWT.
     * 
     * @return private key
     */
    public Optional<Key> privateKey() {
        Key key;
        try {
            key = ks.getKey(keyStoreAlias, keyStorePassword.toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException ex) {
            return Optional.empty();
        }

        return Optional.ofNullable(key);
    }

}
