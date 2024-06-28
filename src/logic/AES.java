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
import java.util.List;

public class AES {
    private String fileExtension = "pdf";
    private final SecretKey aesKey;

    public AES() {
        this.aesKey = generateKsKey(128);
    }

    public byte[] encrypt(String inputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);

        try {
            // String plainText = new String(Files.readAllBytes(Paths.get(inputFile)));
            //
            // byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] fileBytes = Files.readAllBytes(Paths.get(inputFile));

            byte[] cipherBytes = cipher.doFinal(fileBytes);

            // return Base64.getEncoder().encodeToString(cipherText);
            return cipherBytes;
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

    public byte[] decrypt(String KsKey, String inputFile) throws Exception {
        Path path = Paths.get(inputFile);

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found: " + inputFile);
        }

        SecretKey secretKey = convertStringToSecretKey(KsKey);
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedBytes = Files.readAllBytes(path);

        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

        return decryptedBytes;
    }

    // List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

    // String cipherText;

    // byte[] decodedBytes = Base64.getDecoder().decode(cipherText);

    // if (lines.size() < 2) {
    //     throw new RuntimeException("File format is incorrect. Expected at least two lines.");
    // }

    // fileExtension = lines.get(0); // Read the file extension (first line)
    // System.out.println("File extension: " + fileExtension);
    // String cipherText = lines.get(1);    // Read the encrypted data (second line)


    // try {
    //     byte[] cipherBytes = Files.readAllBytes(path);
    //     byte[] decodedBytes = Base64.getDecoder().decode(cipherBytes);
    //     byte[] decryptedBytes = cipher.doFinal(decodedBytes);
    //
    //     return decryptedBytes;
    // } catch (IOException ex) {
    //     throw new RuntimeException("Error during AES crypto", ex);
    // } catch (Exception ex) {
    //     throw new RuntimeException("Error during decryption", ex);
    // }

    // try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
    //     fileExtension = br.readLine();
    //     cipherText = br.readLine();
    //     byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
    //
    //     return new String(decryptedBytes);
    // } catch (IOException ex) {
    //     throw new RuntimeException("Error during AES crypto", ex);
    // } catch (Exception ex) {
    //     throw new RuntimeException("Error during decryption", ex);
    // }

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
