package _2023.Day15;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import outils.list.MyList;
import outils.number.MyNumber;

/**
 * author vportal
 * 
 */
public class Day15 {
    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day15\\input.txt"));

            List<String> lines = new ArrayList<>();
            Long solution1 = 0l;
            Long solution2 = 0l;

            while (in.hasNextLine()) {
                Pattern pattern = Pattern.compile("([^=,]+=[^=,]+|[^=,]+)");
                Matcher matcher = pattern.matcher(in.nextLine());

                // Afficher les valeurs extraites
                while (matcher.find()) {
                    lines.add(matcher.group());
                }
            }
            double startTime = System.currentTimeMillis();
            solution1 = part1(lines);
            double endTime = System.currentTimeMillis();
            System.out.println("solution 1 : " + solution1 + " Temps : " + ((endTime - startTime) / 1000) + "s");
            startTime = System.currentTimeMillis();
            solution2 = part2(lines);
            endTime = System.currentTimeMillis();
            System.out.println("solution 2 : " + solution2 + " Temps : " + ((endTime - startTime) / 1000) + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<String> lines) {
        return calculeList(lines);
    }

    private static Long part2(List<String> lines) {
        Long res = 0l;
        HashMap<Long, List<String>> hash = getHash(lines);
        for (Entry<Long, List<String>> value : hash.entrySet()) {
            for (int j = 0; j < value.getValue().size(); j++) {
                String[] split = value.getValue().get(j).split("=");
                res += (value.getKey() + 1) * (j + 1) * Integer.parseInt(split[1]);
            }
        }

        return res;
    }

    private static HashMap<Long, List<String>> getHash(List<String> lines) {
        HashMap<Long, List<String>> hash = new HashMap<>();
        for (String value : lines) {
            if (value.contains("=")) {
                String[] split = value.split("=");
                Long code = calcule(split[0]);
                if (hash.containsKey(code)) {
                    OptionalInt index = IntStream.range(0, hash.get(code).size())
                            .filter(i -> hash.get(code).get(i).contains(split[0])).findFirst();
                    if (index.isPresent()) {
                        hash.get(code).set(index.getAsInt(), value);
                    } else {
                        hash.get(code).add(value);
                    }
                } else {
                    hash.put(code, MyList.of(value));
                }
            } else {
                String[] split = value.split("-");
                Long code = calcule(split[0]);
                if (hash.containsKey(code)) {
                    OptionalInt index = IntStream.range(0, hash.get(code).size())
                            .filter(i -> hash.get(code).get(i).contains(split[0])).findFirst();
                    if (index.isPresent()) {
                        hash.get(code).remove(index.getAsInt());
                    }

                }
            }
        }
        return hash;
    }

    private static Long calculeList(List<String> input) {
        Long res = 0L;
        for (String value : input) {
            res += calcule(value);
        }

        return res;
    }

    private static Long calcule(String input) {
        Long code = 0l;
        for (char cs : input.toCharArray()) {
            int codeAscii = Integer.valueOf(cs);
            code += codeAscii;
            code *= 17;
            code = code % 256;
        }
        return code;
    }
}
