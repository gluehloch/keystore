package de.awtools.keystore;

import java.util.Objects;
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

    private final String keyStorePassword;

    private final String keyStoreAlias;

    private final KeyStore ks;
    private final Key key;
    
    public KeyStoreService(KeyStore keyStore, String keyStorePassword, String keyStoreAlias)
            throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException
    {
        Objects.requireNonNull(keyStore);
        Objects.requireNonNull(keyStorePassword);
        Objects.requireNonNull(keyStoreAlias);
        this.ks = keyStore;
        this.keyStorePassword = keyStorePassword;
        this.keyStoreAlias = keyStoreAlias;
        this.key = ks.getKey(keyStoreAlias, keyStorePassword.toCharArray());
        Objects.requireNonNull(this.key);
    }

    /**
     * Returns the public key to validate a JWT.
     * 
     * @return public key
     */
    public Optional<PublicKey> publicKey() {
        try {
            if (key instanceof PrivateKey) {
                // TODO Unterschied zwischen ks.getCertificate(...) and ks.getKey(...) ???
                Certificate cert = ks.getCertificate(keyStoreAlias);
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

        return Optional.of(key);
    }

    Key getKey() {
        return key;
    }
    
    KeyStore getKeyStore() {
        return ks;
    }

}
