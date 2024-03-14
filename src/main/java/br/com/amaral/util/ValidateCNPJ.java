package br.com.amaral.util;

import java.util.InputMismatchException;

public class ValidateCNPJ {

public static boolean isCNPJ(String CNPJ) {
		
		
		CNPJ = CNPJ.replaceAll("\\.", "").replaceAll("\\/", "").replaceAll("\\-", ""); 
		
		// CNPJs formed by a sequence of equal numbers are considered errors
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// Protects the code against possible conversation errors of type (int)
		try {
			// Calculation of the first Check Digit
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				/*
				 * Converts the ith character of the CNPJ into a number:
				 * for example, transforms the character '0' into the integer 0
				 * (48 is position '0' in the ASCII table)
				 */
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculation of the second Check Digit
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verify that the calculated digits match the entered digits.
			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

	public static String imprimeCNPJ(String CNPJ) {
		// CNPJ Mask: 99.999.999.9999-99
		return (CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "."
				+ CNPJ.substring(8, 12) + "-" + CNPJ.substring(12, 14));
	}
}
