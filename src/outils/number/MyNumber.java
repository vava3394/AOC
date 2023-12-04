package outils.number;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author vportal
 */
public class MyNumber {
    public static boolean isNumber(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Integer> extractIntegersToList(String input) {
        List<Integer> integerList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            integerList.add(number);
        }

        return integerList;
    }

    public static Integer extractIntegers(String input) {

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        throw new IllegalArgumentException("Impossible il n'y a pas entier");
    }

    public static int getNumberGame(String input) {
        return extractIntegers(input);
    }
}
