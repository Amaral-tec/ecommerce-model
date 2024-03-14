package br.com.amaral.util;

import java.util.InputMismatchException;

public class ValidateCPF {

public static boolean isCPF(String CPF) {
		
		CPF = CPF.replaceAll("\\.", "").replaceAll("\\-", "");
		
		// Consider CPF error formed by a sequence of equal numbers
		if (CPF.equals("00000000000") || CPF.equals("11111111111") || CPF.equals("22222222222")
				|| CPF.equals("33333333333") || CPF.equals("44444444444") || CPF.equals("55555555555")
				|| CPF.equals("66666666666") || CPF.equals("77777777777") || CPF.equals("88888888888")
				|| CPF.equals("99999999999") || (CPF.length() != 11))
			return (false);

		char dig10, dig11;
		int sm, i, r, num, peso;

		// Protects the code against possible conversation errors of type (int)
		try {
			// Calculation of the first Check Digit
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				/*
				 * Converts the ith character of the CNPJ into a number:
				 * for example, transforms the character '0' into the integer 0
				 * (48 is position '0' in the ASCII table)
				 */
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48);

			// Calculation of the second Check Digit
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (CPF.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);

			// Verify that the calculated digits match the entered digits.
			if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static String imprimeCPF(String CPF) {
		// CPF Mask: 999.999.999-99
		return (CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." + CPF.substring(6, 9) + "-"
				+ CPF.substring(9, 11));
	}

}
