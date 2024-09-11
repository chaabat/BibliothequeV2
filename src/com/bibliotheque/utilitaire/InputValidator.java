package com.bibliotheque.utilitaire;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;
import java.util.regex.Pattern;

public class InputValidator {

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    public static boolean isValidUUID(String uuidStr) {
        try {
            UUID.fromString(uuidStr);
            return false;
        } catch (IllegalArgumentException e) {
            return true;
        }
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public static boolean isValidInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr); // Try to parse the date
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isMinLength(String str, int minLength) {
        return str != null && str.length() >= minLength;
    }

    public static boolean isMaxLength(String str, int maxLength) {
        return str != null && str.length() <= maxLength;
    }
}
