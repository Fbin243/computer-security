package logic;

import constants.Algorithm;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class AES {
    private static final String ALGORITHM = Algorithm.AES;
    private static final String TRANSFORMATION = Algorithm.AES;

    public static void encrypt(String key, String inputFile, String outputFile) throws Exception {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, String inputFile, String outputFile) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(cipherMode, secretKey);

        try (FileInputStream inputStream = new FileInputStream(inputFile);
             FileOutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] inputBytes = new byte[(int) inputStream.available()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            outputStream.write(outputBytes);
        } catch (IOException ex) {
            throw new RuntimeException("Error during file encryption", ex);
        }
    }
}
