package logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import constants.Algorithm;

public class AES {
    private static final String ALGORITHM = Algorithm.AES;
    private static final String TRANSFORMATION = Algorithm.AES;

    public String encrypt(String key, String inputFile) throws Exception {
        return handleCrypto(Cipher.ENCRYPT_MODE, key, inputFile);
    }

    private String handleCrypto(int cipherMode, String key, String inputFile) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile)) {

            byte[] inputBytes = new byte[(int) inputStream.available()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            return Base64.getEncoder().encodeToString(outputBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during file AES encryption", ex);
        }
    }
}
