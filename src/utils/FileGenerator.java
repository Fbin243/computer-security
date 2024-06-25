package utils;

import java.security.SecureRandom;
import java.util.UUID;

public class FileGenerator {
    public static String generateUniqueString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
