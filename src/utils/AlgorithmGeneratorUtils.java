package utils;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class AlgorithmGeneratorUtils {
    public static String generateSymmetryKey(String algorithm, int keySize) {
        try {
            // Create a KeyGenerator for AES
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(keySize);
            SecretKey secretKey = keyGen.generateKey();

            // Encode the key to a string format
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            return encodedKey;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating key!";
        }
    }
}
