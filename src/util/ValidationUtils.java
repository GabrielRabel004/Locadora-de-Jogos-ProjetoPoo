package util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public final class ValidationUtils {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private ValidationUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return !isBlank(email) && EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPhone(String phone) {

        String digits = onlyDigits(phone);

        return digits.length() == 10 || digits.length() == 11;
    }

    public static boolean isAdult(LocalDate birthDate) {

        if (birthDate == null || birthDate.isAfter(LocalDate.now())) {
            return false;
        }

        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    public static boolean isValidCpf(String cpf) {

        String digits = onlyDigits(cpf);

        if (digits.length() != 11) {
            return false;
        }

        if (digits.chars().distinct().count() == 1) {
            return false;
        }

        return calculateDigit(digits, 10) == Character.getNumericValue(digits.charAt(9))
                && calculateDigit(digits, 11)
                == Character.getNumericValue(digits.charAt(10));
    }

    public static String onlyDigits(String value) {

        if (value == null) {
            return "";
        }

        return value.replaceAll("\\D", "");
    }

    private static int calculateDigit(String cpf, int weightStart) {

        int sum = 0;
        int indexLimit = weightStart - 1;
        int weight = weightStart;

        for (int index = 0; index < indexLimit; index++) {
            sum += Character.getNumericValue(cpf.charAt(index)) * weight--;
        }

        int result = 11 - (sum % 11);

        return result > 9 ? 0 : result;
    }
}
