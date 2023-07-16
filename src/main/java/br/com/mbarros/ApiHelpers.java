package br.com.mbarros;

import net.datafaker.Faker;

import java.security.SecureRandom;

/**
 * The ApiHelpers class provides helper methods for API-related operations.
 */
public class ApiHelpers {

    /**
     * Transforms the given value into an appropriate object based on predefined rules.
     *
     * @param value The value to be transformed.
     * @return The transformed object.
     */
    public static Object transformData(String value) {
        Faker faker = new Faker();
        int number = 0;

        if (value.contains(".")) {
            String[] parts = value.split("\\.");
            number = Integer.parseInt(parts[0]);
            value = parts[1];
        }

        return switch (value) {
            case "" -> " ";
            case "0" -> 0;
            case "null" -> null;
            case "negativeNumber" -> faker.number().negative();
            case "decimalNumber" -> faker.number().randomDouble(faker.number().numberBetween(1, 4), 1, 9999);
            case "numbers" -> Long.valueOf(faker.number().digits(number));
            case "stringNumbers" -> faker.number().digits(number);
            case "specialString" -> generateStringWithSpecialCharacters(number);
            case "false" -> false;
            case "true" -> true;
            default -> value;
        };
    }

    /**
     * Generates a string with special characters, numbers, letters and accented letters of the specified length.
     *
     * @param length The length of the string to be generated.
     * @return A string consisting of special characters, numbers, letters and accented letters with the specified length.
     */
    public static String generateStringWithSpecialCharacters(int length) {
        String specialCharacters = "!@#$%¨&*()-_=+[]{}^~´`<>,.;:/?|\"'";
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String accentedLetters = "áàãâéêíóôõúüçÁÀÃÂÉÊÍÓÔÕÚÜÇ";

        String caracteres = specialCharacters + letters + numbers + accentedLetters;
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }

        return sb.toString();
    }
}
