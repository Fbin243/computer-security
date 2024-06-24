package logic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
    private String algorithm;

    public SHA(String algorithm) {
        this.algorithm = algorithm;
    }

    public String hash(String input) {
        try {
            if (this.algorithm == null)
                throw new RuntimeException("Algorithm not set");
            MessageDigest digest = MessageDigest.getInstance(this.algorithm);
            byte[] hashBytes = digest.digest(input.getBytes());
            return convertBytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertBytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
