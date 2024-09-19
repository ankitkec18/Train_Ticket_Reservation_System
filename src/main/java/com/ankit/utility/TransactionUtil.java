package com.ankit.utility;

import java.util.Random;

public class TransactionUtil {

    public static String generateTransactionId() {
        String fixedPrefix = "BBC";  // Fixed part of the transaction ID
        String randomLetters = generateRandomLetters(4);  // Random 4 letters (e.g., NSDF)
        String randomNumbers = generateRandomNumbers(4);  // Random 4 digits (e.g., 4673)

        // Return formatted transaction ID: BBC374-NSDF-4673
        return fixedPrefix + randomNumbers + "-" + randomLetters + "-" + randomNumbers;
    }

    // Generate random letters
    private static String generateRandomLetters(int length) {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(letters.charAt(random.nextInt(letters.length())));
        }
        return result.toString();
    }

    // Generate random numbers
    private static String generateRandomNumbers(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(digits.charAt(random.nextInt(digits.length())));
        }
        return result.toString();
    }
}
