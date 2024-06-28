package logic;

import constants.Algorithm;
import constants.Common;
import utils.FileGenerator;
import utils.Helpers;

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
    private String fileExtension = "";
    private final SecretKey aesKey;

    public AES() {
        this.aesKey = generateKsKey(128);
    }

    public byte[] encrypt(String inputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);

        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(inputFile));
            byte[] cipherBytes = cipher.doFinal(fileBytes);

            return cipherBytes;
        } catch (IOException ex) {
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

    public byte[] decrypt(String KsKey, String inputFile) throws Exception {
        Path encryptedPath = Paths.get(inputFile);
        Path infoPath = Paths.get(Helpers.getFileName(inputFile) + Common.INFO_FILE_EXTENSION);

        if (!Files.exists(encryptedPath)) {
            throw new RuntimeException("File not found: " + inputFile);
        }

        SecretKey secretKey = convertStringToSecretKey(KsKey);
        Cipher cipher = Cipher.getInstance(Algorithm.AES);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] readBytes = Files.readAllBytes(encryptedPath);
        byte[] decryptedBytes = cipher.doFinal(readBytes);

        List<String> lines = Files.readAllLines(infoPath, StandardCharsets.UTF_8);
        fileExtension = lines.get(0);

        return decryptedBytes;
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
