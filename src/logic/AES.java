package logic;

import constants.Algorithm;
import utils.FileGenerator;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class AES {
    private String fileExtension = "";
    private final SecretKey aesKey;

    public AES() {
        this.aesKey = generateKsKey(128);
    }

    public String encrypt(String inputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);

        try {
            String plainText = new String(Files.readAllBytes(Paths.get(inputFile)));

            byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(cipherText);
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

    public String decrypt(String KsKey, String inputFile) throws Exception {
        Path path = Paths.get(inputFile);

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found: " + inputFile);
        }

        SecretKey secretKey = convertStringToSecretKey(KsKey);
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        String cipherText;

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            fileExtension = br.readLine();
            cipherText = br.readLine();
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));

            return new String(decryptedBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Error during decryption", ex);
        }
        // try {
        //     String cipherText = new String(Files.readAllBytes(path));
        //     byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        //
        //     return new String(decryptedBytes);
        // } catch (IOException ex) {
        //     throw new RuntimeException("Error during AES crypto", ex);
        // }
    }

    public SecretKey generateKsKey(int keySize) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(Algorithm.AES);
            keyGen.init(keySize);
            SecretKey secretKey = keyGen.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Error during AES key generation", ex);
        }
    }

    public String getAesKey() {
        return Base64.getEncoder().encodeToString(aesKey.getEncoded());
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public static SecretKey convertStringToSecretKey(String encodedKeyString) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKeyString);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, Algorithm.AES);
    }
}
