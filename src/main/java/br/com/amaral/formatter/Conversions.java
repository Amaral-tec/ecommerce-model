package br.com.amaral.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Conversions {

    private static final Map<String, String> MONTH_MAP = new HashMap<>();

    static {
        MONTH_MAP.put("january", "01");
        MONTH_MAP.put("february", "02");
        MONTH_MAP.put("march", "03");
        MONTH_MAP.put("april", "04");
        MONTH_MAP.put("may", "05");
        MONTH_MAP.put("june", "06");
        MONTH_MAP.put("july", "07");
        MONTH_MAP.put("august", "08");
        MONTH_MAP.put("september", "09");
        MONTH_MAP.put("october", "10");
        MONTH_MAP.put("november", "11");
        MONTH_MAP.put("december", "12");
        MONTH_MAP.put("jan", "01");
        MONTH_MAP.put("feb", "02");
        MONTH_MAP.put("mar", "03");
        MONTH_MAP.put("apr", "04");
        MONTH_MAP.put("jun", "06");
        MONTH_MAP.put("jul", "07");
        MONTH_MAP.put("aug", "08");
        MONTH_MAP.put("sep", "09");
        MONTH_MAP.put("oct", "10");
        MONTH_MAP.put("nov", "11");
        MONTH_MAP.put("dec", "12");
    }

    private static final String[] ACCENTS = {"ç", "Ç", "á", "é", "í", "ó", "ú", "ý", "Á", "É", "Í", "Ó", "Ú", "Ý", "à", "è", "ì", "ò", "ù", "À", "È", "Ì", "Ò", "Ù", "ã", "õ", "ñ", "ä", "ë", "ï", "ö", "ü", "ÿ", "Ä", "Ë", "Ï", "Ö", "Ü", "Ã", "Õ", "Ñ", "â", "ê", "î", "ô", "û", "Â", "Ê", "Î", "Ô", "Û"};
    private static final String[] WITHOUT_ACCENT = {"c", "C", "a", "e", "i", "o", "u", "y", "A", "E", "I", "O", "U", "Y", "a", "e", "i", "o", "u", "A", "E", "I", "O", "U", "a", "o", "n", "a", "e", "i", "o", "u", "y", "A", "E", "I", "O", "U", "A", "O", "N", "a", "e", "i", "o", "u", "A", "E", "I", "O", "U"};
    private static final String[] SPECIAL_CHARACTERS = {"\\.", ",", "-", ":", "\\(", "\\)", "ª", "\\|", "\\\\", "°"};

    public static boolean isEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email).matches();
    }

    public static String getMonthNumber(String month) {
        return MONTH_MAP.getOrDefault(month.toLowerCase(), "Invalid Month");
    }

    public static String removeSpecialCharacters(String text) {
        return text.replaceAll("[^0-9a-zA-Z]+", " ");
    }

    public static String removeAccents(String text) {
        for (int i = 0; i < ACCENTS.length; i++) {
            text = text.replace(ACCENTS[i], WITHOUT_ACCENT[i]);
        }

        for (String specialCharacter : SPECIAL_CHARACTERS) {
            text = text.replaceAll(specialCharacter, "");
        }

        text = text.replaceAll("^\\s+", "");
        text = text.replaceAll("\\s+$", "");
        text = text.replaceAll("\\s+", " ");

        return text;
    }

    public static Date formatDate(String data) {
        if (data.length() != 8) {
            return null;
        }

        int day = Integer.parseInt(data.substring(0, 2));
        int month = Integer.parseInt(data.substring(2, 4));
        int year = Integer.parseInt(data.substring(4, 8));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            return dateFormat.parse(String.format("%02d%02d%04d", day, month, year));
        } catch (ParseException e) {
            // Log the exception or return a default value
            return null;
        }
    }

    public static String convertDecimalToPercentage(double value) {
        return String.format("%.2f%%", value);
    }

    public static void main(String[] args) {
    }
}
