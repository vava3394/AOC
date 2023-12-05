package _2023.Day5;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.list.MyList;
import outils.number.MyNumber;

public class Day5 {
    public static enum NAMETRANSFERT {
        seedtosoil,
        soiltofertilizer,
        fertilizertowater,
        watertolight,
        lighttotemperature,
        temperaturetohumidity,
        humiditytolocation
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day5\\input.txt"));

            Long solution1 = 0L;
            Long solution2 = 0L;
            String line = "";
            List<Long> seed = new ArrayList<>();

            if (in.hasNextLine())
                seed = MyNumber.extractLongToList(in.nextLine());
            while (in.hasNextLine()) {
                line += " " + in.nextLine();

            }
            List<List<Long>> seedPair = MyList.splitList(seed, 2);
            HashMap<String, List<List<Long>>> transfert = new HashMap<>();

            String regex = "(.*?):((\\s*\\d+)*)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(line);

            while (matcher.find()) {
                if (matcher.group(1).contains("map")) {
                    transfert.put(matcher.group(1).replace("-", "").replace("map", "").trim(),
                            MyList.splitList(MyNumber.extractLongToList(matcher.group(2)), 3));
                }
            }

            solution1 = part1(seed, transfert);
            long startTime = System.currentTimeMillis();
            solution2 = part2(seedPair, transfert);
            long endTime = System.currentTimeMillis();
            long elapsedTimeMilliseconds = endTime - startTime;
            long elapsedTimeMinutes = elapsedTimeMilliseconds / (60 * 1000);

            System.out.println("Temps d'exécution de la fonction : " + elapsedTimeMinutes + " minutes");

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<Long> input, HashMap<String, List<List<Long>>> transfert) {
        Long min = Long.MAX_VALUE;
        for (int i = 0; i < input.size(); i++) {
            min = Math.min(min, excuteCartographie(input.get(i), transfert));
        }
        return min;
    }

    /*
     * Giga Long
     */
    private static Long part2(List<List<Long>> input, HashMap<String, List<List<Long>>> transfert) {
        List<Long> mins = new ArrayList<>();
        for (List<Long> list : input) {
            Long out = Long.MAX_VALUE;
            Long start = list.get(0);
            Long end = list.get(0) + list.get(1);
            for (Long i = start; i < end; i++) {
                out = Math.min(out, excuteCartographie(i, transfert));
            }
            mins.add(out);
        }

        return Collections.min(mins);
    }

    private static Long excuteCartographie(Long in, HashMap<String, List<List<Long>>> transfert) {
        for (NAMETRANSFERT name : NAMETRANSFERT.values()) {
            in = getCartographie(in, transfert.get(name.toString()));
        }
        return in;
    }

    private static Long getCartographie(Long in, List<List<Long>> transfert) {
        Long out = in;
        for (List<Long> list : transfert) {
            if (in >= list.get(1) && in <= list.get(1) + list.get(2)) {
                return list.get(0) + in - list.get(1);
            }
        }
        return out;
    }

}
