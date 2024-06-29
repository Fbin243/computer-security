package utils;

import java.util.UUID;

public class FileGenerator {
    public static String generateUniqueString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }
}
