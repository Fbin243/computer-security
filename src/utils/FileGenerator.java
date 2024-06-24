package utils;

import java.security.SecureRandom;
import java.util.UUID;

public class FileGenerator {
    public static String generateDecryptedFileName(String inputFile, String fileMode) {
        String fileName = inputFile.substring(0, inputFile.lastIndexOf('.'));
        String randomCode = generateRandomCode(8); // Example: Generate an 8-character random code
        return fileName + "-" + fileMode + "-" + randomCode + ".txt";
    }

    public static String generateRandomCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static String generateUniqueString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
