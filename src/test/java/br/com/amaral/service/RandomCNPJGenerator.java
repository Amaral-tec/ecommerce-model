package br.com.amaral.service;

import java.util.Random;

public class RandomCNPJGenerator {

	public static void main(String[] args) {
        String cnpj = generateCNPJ();
        System.out.println("Generated CNPJ: " + cnpj);
    }

    public static String generateCNPJ() {
        int[] cnpjArray = new int[14];
        Random random = new Random();

        for (int i = 0; i < 12; i++) {
            cnpjArray[i] = random.nextInt(10);
        }

        int firstVerificationDigit = calculateVerificationDigit(cnpjArray, 5);

        cnpjArray[12] = firstVerificationDigit;

        int secondVerificationDigit = calculateVerificationDigit(cnpjArray, 6);

        cnpjArray[13] = secondVerificationDigit;

        StringBuilder cnpjBuilder = new StringBuilder();
        for (int digit : cnpjArray) {
            cnpjBuilder.append(digit);
        }

        return cnpjBuilder.toString();
    }

    public static int calculateVerificationDigit(int[] cnpjArray, int weight) {
        int sum = 0;

        for (int i = 0; i < cnpjArray.length; i++) {
            sum += cnpjArray[i] * weight;
            weight--;

            if (weight < 2) {
                weight = 9;
            }
        }

        int remainder = sum % 11;

        return (remainder < 2) ? 0 : 11 - remainder;
    }

    public static String generateNewCNPJ() {
        return generateCNPJ();
    }
}
