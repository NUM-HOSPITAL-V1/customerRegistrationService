package mn.edu.userapi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

@Component
public class CryptoUtil {

    @Value("${hospital.security.publicKey}")
    private String publicKey;

    @Value("${hospital.security.privateKey}")
    private String privateKey;

    public String encrypt(String pattern) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(ENCRYPT_MODE, convertPublicKey());

        byte[] encryptedBytes = encryptCipher.doFinal(pattern.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String pattern) throws Exception {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(DECRYPT_MODE, convertPrivateKey());

        byte[] decryptBytes = Base64.getDecoder().decode(pattern);
        byte[] decrypted = decryptCipher.doFinal(decryptBytes);
        return new String(decrypted);
    }

    private PublicKey convertPublicKey() throws Exception {
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    private PrivateKey convertPrivateKey() throws Exception {
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(keySpec);
    }

    public KeyPair generateKeys() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public String encrypt_base64(String nat_id) throws Exception {
        return Base64.getUrlEncoder().encodeToString(nat_id.getBytes());
    }

    public String decrypt_base64(String encrypted_natID) throws Exception {
        return new String(Base64.getUrlDecoder().decode(encrypted_natID), StandardCharsets.UTF_8);
    }
}