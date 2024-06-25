package logic;

import constants.Algorithm;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AES {
    private final SecretKey aesKey;

    public AES() {
        this.aesKey = generateSymmetryKey(Algorithm.AES, 128);
    }

    public static SecretKey generateAESKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n, new SecureRandom());
        return keyGenerator.generateKey();
    }

    public String encrypt(String inputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {

            byte[] inputBytes = new byte[(int) inputStream.available()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            return Base64.getEncoder().encodeToString(outputBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

    public String decrypt(String key, String inputFile) throws Exception {
//        byte[] decodedKey = Base64.getDecoder().decode(key);
////        String decrypted = handleCrypto2(Cipher.DECRYPT_MODE, key, inputFile);
//        String decrypted = handleCrypto(Cipher.DECRYPT_MODE, inputFile);
//        System.out.println("Decrypted AES: " + decrypted);
////        try (BufferedWriter bw = new BufferedWriter(
////                new FileWriter(getDesktopDirectory() + outputFile, false))) {
////            bw.write(decrypted);
////        } catch (IOException ex) {
////            throw new RuntimeException("Error during file AES decryption", ex);
////        }
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), Algorithm.AES);
        System.out.println("Secret key: " + secretKey.getEncoded().toString());
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {

            byte[] inputBytes = new byte[(int) inputStream.available()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            return Base64.getEncoder().encodeToString(outputBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

//    private String handleCrypto(int cipherMode, String inputFile, String key) throws Exception {
//        Cipher cipher = Cipher.getInstance(Algorithm.AES);
//        cipher.init(cipherMode, );
//
//        try (FileInputStream inputStream = new FileInputStream(inputFile)) {
//
//            byte[] inputBytes = new byte[(int) inputStream.available()];
//            inputStream.read(inputBytes);
//
//            byte[] outputBytes = cipher.doFinal(inputBytes);
//
//            return Base64.getEncoder().encodeToString(outputBytes);
//        } catch (IOException ex) {
//            throw new RuntimeException("Error during AES crypto", ex);
//        }
//    }

    public SecretKey generateSymmetryKey(String algorithm, int keySize) {
        try {
            // Create a KeyGenerator for AES
            KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
            keyGen.init(keySize); // for example
            SecretKey secretKey = keyGen.generateKey();

            // Encode the key to a string format
//            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
//            System.out.println("secretKey.getEncoded: " + encodedKey);
//            byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
//            System.out.println("Length of decoded key: " + decodedKey.length);
            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAesKey() {
        return Base64.getEncoder().encodeToString(aesKey.getEncoded());
    }
}
