package br.com.amaral.service;

import java.util.Random;

public class RandomCPFGenerator {

	public static void main(String[] args) {
        String cpf = generateCPF();
        System.out.println("Generated CPF: " + cpf);
    }

    public static String generateCPF() {
        int[] cpfArray = new int[9];
        Random random = new Random();

        for (int i = 0; i < 9; i++) {
            cpfArray[i] = random.nextInt(10);
        }

        int firstVerificationDigit = calculateVerificationDigit(cpfArray);

        cpfArray = addElementToArray(cpfArray, firstVerificationDigit);

        int secondVerificationDigit = calculateVerificationDigit(cpfArray);

        cpfArray = addElementToArray(cpfArray, secondVerificationDigit);

        StringBuilder cpfBuilder = new StringBuilder();
        for (int digit : cpfArray) {
            cpfBuilder.append(digit);
        }

        return cpfBuilder.toString();
    }

    public static int calculateVerificationDigit(int[] cpfArray) {
        int weight = cpfArray.length + 1;
        int sum = 0;

        for (int digit : cpfArray) {
            sum += digit * weight;
            weight--;
        }

        int remainder = sum % 11;

        return (remainder < 2) ? 0 : 11 - remainder;
    }

    public static int[] addElementToArray(int[] array, int element) {
        int[] newArray = new int[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = element;
        return newArray;
    }

    public static String generateNewCPF() {
        return generateCPF();
    }
}
