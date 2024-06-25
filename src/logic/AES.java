package logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import constants.Algorithm;

public class AES {
    private static final String ALGORITHM = Algorithm.AES;
    private static final String TRANSFORMATION = Algorithm.AES;
    private final String aesKey;

    public AES() {
        this.aesKey = generateSymmetryKey(ALGORITHM, 128);
    }

    public String encrypt(String key, String inputFile) throws Exception {
        return handleCrypto(Cipher.ENCRYPT_MODE, key, inputFile);
    }

    public void decrypt(String key, String inputFile, String outputFile) throws Exception {
        String decrypted = handleCrypto(Cipher.DECRYPT_MODE, key, inputFile);
        try (FileOutputStream outputStream = new FileOutputStream(getDesktopDirectory() + outputFile)) {
            outputStream.write(Base64.getDecoder().decode(decrypted));
        } catch (IOException ex) {
            throw new RuntimeException("Error during file AES decryption", ex);
        }
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
            throw new RuntimeException("Error during AES crypto", ex);
        }
    }

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

    public String getAesKey() {
        return aesKey;
    }

    private String getDesktopDirectory() {
        String home = System.getProperty("user.home");
        return home + "/Desktop/";
    }
}
