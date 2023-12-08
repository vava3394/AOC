package _2023.Day8;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.list.MyList;
import outils.number.MyNumber;

/**
 * author vportal
 * 
 */
public class Day8 {

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day8\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            Long solution2 = 0L;
            String line = "";
            String instruction = in.nextLine();
            in.nextLine();
            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }
            solution1 = part1(lines, MyList.getStringToListChar(instruction));
            solution2 = part2(lines, MyList.getStringToListChar(instruction));

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<String> input, List<Character> instruction) {
        int res = 0;
        HashMap<String, String[]> allLieux = getAllLieux(input);
        String lieu = "AAA";
        String[] coord = allLieux.get(lieu);

        while (!lieu.endsWith("ZZZ")) {
            if (instruction.get(0) == 'R') {
                lieu = coord[1];
            } else {
                lieu = coord[0];
            }
            coord = allLieux.get(lieu);
            res++;
            instruction.add(instruction.get(0));
            instruction.remove(0);
        }

        return res;
    }

    private static Long part2(List<String> input, List<Character> instruction) {
        List<Integer> cycles = new ArrayList<>();
        List<String> depart = new ArrayList<>();

        for (String value : input) {
            String regex = "(.*A)\\s*=";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            while (matcher.find()) {
                depart.add(matcher.group(1));
            }
        }
        for (String value : depart) {
            cycles.add(findCycle(value, input, instruction));
        }
        return calculatePPCM(cycles.stream().mapToInt(Integer::intValue).toArray());
    }

    private static int findCycle(String depart, List<String> input, List<Character> instruction) {
        int res = 0;
        HashMap<String, String[]> allLieux = getAllLieux(input);
        String lieu = depart;
        String[] coord = allLieux.get(lieu);

        while (!lieu.endsWith("Z")) {
            if (instruction.get(0) == 'R') {
                lieu = coord[1];
            } else {
                lieu = coord[0];
            }
            coord = allLieux.get(lieu);
            res++;
            instruction.add(instruction.get(0));
            instruction.remove(0);
        }

        return res;
    }

    // Fonction pour calculer le PPCM de plusieurs nombres
    private static long calculatePPCM(int[] numbers) {
        long result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = calculatePPCM(result, numbers[i]);
        }
        return result;
    }

    // Fonction pour calculer le PPCM de deux nombres
    private static long calculatePPCM(long a, long b) {
        return Math.abs(a * b) / calculatePGCD(a, b);
    }

    // Fonction pour calculer le PGCD de deux nombres en utilisant l'algorithme
    // d'Euclide
    private static long calculatePGCD(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static HashMap<String, String[]> getAllLieux(List<String> input) {
        HashMap<String, String[]> res = new HashMap<>();
        for (String value : input) {
            String lieu = value.substring(0, 3);
            String[] coord = new String[2];
            String regex = "[(](.*),\\s(.*)[)]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            while (matcher.find()) {
                coord[0] = matcher.group(1);
                coord[1] = matcher.group(2);
            }
            res.put(lieu, coord);
        }
        return res;
    }
}
