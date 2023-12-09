package outils.number;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author vportal
 */
public class MyNumber {
    /**
     * Indique si la chaine est un entier ou pas
     * 
     * @param val String
     * @return boolean
     */
    public static boolean isNumber(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extract tous les entiers de la chaine de carractère
     * 
     * @param input - String
     * @return List Integer
     */
    public static List<Integer> extractIntegersToList(String input) {
        List<Integer> integerList = new ArrayList<>();

        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group());
            integerList.add(number);
        }

        return integerList;
    }

    /**
     * Extract tous les entiers de la chaine de carractère
     * 
     * @param input - String
     * @return List Integer
     */
    public static List<Long> extractLongToList(String input) {
        List<Long> integerList = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            Long number = Long.parseLong(matcher.group());
            integerList.add(number);
        }

        return integerList;
    }

    public static List<String> extractStringNumber(String input) {
        List<String> stringList = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            stringList.add(matcher.group());
        }

        return stringList;
    }

    /**
     * Extract le premier entier de la chaine de carractère
     * 
     * @param input - String
     * @return Integer
     */
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
