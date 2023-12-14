# keystore
Utility to access the Java keystore and an example to encrypt/decrypt strings with a public/private key

## JUnit Test

Der Befehl für das Generieren eines Test KeyStore (Password: awtest)
```
keytool -genkey -alias awtest -keyalg RSA -keystore awtest.jks -keysize 2048 -validity 365000
```
