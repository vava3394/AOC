package _2023.Day12;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.number.MyNumber;
import outils.pair.Pair;

/**
 * author vportal
 * 
 */
public class Day12 {
    private static final int nbMulti = 4;
    private static HashMap<String, Long> cache = new HashMap<>();

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day12\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            Long solution1 = 0l;
            Long solution2 = 0l;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }
            List<Pair<String, List<Integer>>> input = getValues(lines);
            solution1 = part1(input);
            solution2 = part2(input);
            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<Pair<String, List<Integer>>> input) {
        Long res = 0l;
        for (Pair<String, List<Integer>> pair : input) {
            String text = pair.getFirst();
            List<Integer> numbers = pair.getSecond();
            res += getCountArrangements(text, numbers);
        }
        return res;
    }

    private static Long part2(List<Pair<String, List<Integer>>> input) {
        Long res = 0l;
        for (Pair<String, List<Integer>> pair : input) {
            String text = pair.getFirst();
            List<Integer> numbers = new ArrayList<>();
            numbers.addAll(pair.getSecond());
            for (int i = 0; i < nbMulti; i++) {
                numbers.addAll(pair.getSecond());
                text += "?" + pair.getFirst();
            }
            res += getCountArrangements(text, numbers);
        }
        return res;
    }

    private static Long getCountArrangements(String text, List<Integer> numbers) {
        Long res = 0l;
        if (text == "") {
            if (numbers.isEmpty())
                return 1l;
            return 0l;
        }

        if (numbers.isEmpty()) {
            if (text.contains("#"))
                return 0l;
            return 1l;
        }

        if (cache.containsKey(text + numbers)) {
            return cache.get(text + numbers);
        }

        if (text.charAt(0) == '.' || text.charAt(0) == '?') {
            res += getCountArrangements(text.substring(1), numbers);
        }
        if (text.charAt(0) == '?' || text.charAt(0) == '#') {
            if (numbers.get(0) <= text.length() && !text.substring(0, numbers.get(0)).contains(".") &&
                    (numbers.get(0) == text.length() || text.charAt(numbers.get(0)) != '#')) {
                res += getCountArrangements(
                        numbers.get(0) + 1 < text.length() ? text.substring(numbers.get(0) + 1) : "",
                        numbers.subList(1, numbers.size()));
            }
        }

        cache.put(text + numbers, res);
        return res;
    }

    private static List<Pair<String, List<Integer>>> getValues(List<String> lines) {
        List<Pair<String, List<Integer>>> res = new ArrayList<>();
        for (String line : lines) {
            String regex = "(.*)\\s(\\d.*)+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);
            matcher.find();
            res.add(new Pair<String, List<Integer>>(matcher.group(1),
                    MyNumber.extractIntegersToList(matcher.group(2))));

        }
        return res;
    }
}
