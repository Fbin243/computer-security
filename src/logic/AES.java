package logic;

import constants.Algorithm;
import utils.FileGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES {
    private final SecretKey aesKey;

    public AES() {
        this.aesKey = generateKsKey(128);
        System.out.println("AES Key ***: " + this.aesKey);
    }

    public String encrypt(String inputFile) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);

        try {
            String plainText = new String(Files.readAllBytes(Paths.get(inputFile)));
            System.out.println("Plain text: " + plainText);

            byte[] outputBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            System.out.println("Output bytes: " + Base64.getEncoder().encodeToString(outputBytes));

//            decrypt2(Base64.getEncoder().encodeToString(outputBytes), outputBytes );

            return Base64.getEncoder().encodeToString(outputBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

    public void decrypt2(String base64EncodedKey, String inputFile) throws Exception{
        SecretKey secretKey = convertStringToSecretKey(base64EncodedKey);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        System.out.println("secretKey: " + secretKey);
        //            String plainText = new String(Files.readAllBytes(Paths.get(inputFile)));
        String plainText = "kmr/l6ZRW7cmNx9wMuojLH8zfFFFvP6HYh5OHc3lmnVouq0V49d3KWvOA2McVwRun1IdPQNi92yG+6s/312yK2Fc8CXy81b8i9LzEusmHNVmFjtl11xREMSyd+LSXQtu";
        System.out.println("Plain text decrypt2: " + plainText);

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(plainText));

//        byte[] decryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        System.out.println("Decrypted bytes: " + new String(decryptedBytes));

    }

//    public String encrypt(String inputFile) throws Exception {
//        Cipher cipher = Cipher.getInstance(Algorithm.AES);
//        cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);
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

    public String decrypt(String base64EncodedKey, String inputFile) throws Exception {
        Path path = Paths.get(inputFile);

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found: " + inputFile);
        }

        SecretKey secretKey = new SecretKeySpec(base64EncodedKey.getBytes(), Algorithm.AES);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        try {
            String plainText = new String(Files.readAllBytes(path));
            System.out.println("Plain text decrypt: " + plainText);

//            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder()
//                    .decode(plainText.getBytes(StandardCharsets.UTF_8)));

//            return new String(decryptedBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
        return "";

//        // Decode the base64 encoded key
//        byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
//        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
//
//        // Initialize the cipher for decryption
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
//
//        // Read the Base64 encoded string from the file
//        String base64EncodedData = new String(Files.readAllBytes(Paths.get(inputFile)));
//
//        System.out.println("Base64 encoded data: " + base64EncodedData);
//
//        // Decode the Base64 string to get the encrypted bytes
//        byte[] inputBytes = Base64.getDecoder().decode(base64EncodedData);
//
//        System.out.println("Input bytes: " + new String(inputBytes));
//
//        // Decrypt the bytes
//        byte[] outputBytes = cipher.doFinal(inputBytes);
//
//        // Return the decrypted data as a string
//        return new String(outputBytes);
    }


//    public String decrypt(String key, String inputFile) throws Exception {
//        SecretKey secretKey = new SecretKeySpec(key.getBytes(), Algorithm.AES);
//        System.out.println("Secret key: " + secretKey.getEncoded().toString());
//        Cipher cipher = Cipher.getInstance(Algorithm.AES);
//        cipher.init(Cipher.DECRYPT_MODE, secretKey);
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

    public SecretKey generateKsKey(int keySize) {
        try {
            // Create a KeyGenerator for AES
            KeyGenerator keyGen = KeyGenerator.getInstance(Algorithm.AES);
            keyGen.init(keySize);
            SecretKey secretKey = keyGen.generateKey();

            return secretKey;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getAesKey() {
        return Base64.getEncoder().encodeToString(aesKey.getEncoded());
    }

    public static String convertSecretKeyToString(SecretKey secretKey) {
        byte[] encodedKey = secretKey.getEncoded();
        return Base64.getEncoder().encodeToString(encodedKey);
    }

    public static SecretKey convertStringToSecretKey(String encodedKeyString) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKeyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }
}
