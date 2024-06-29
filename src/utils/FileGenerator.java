package utils;

import java.util.UUID;

import constants.Common;

public class FileGenerator {
    public static String generateUniqueString() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, Common.UUID_LENGTH);
    }
}
