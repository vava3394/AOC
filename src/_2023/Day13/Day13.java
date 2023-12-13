package _2023.Day13;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import outils.list.MyList;
import outils.matrix.Matrix;
import outils.pair.Pair;

/**
 * author vportal
 * 
 */
public class Day13 {
    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day13\\input.txt"));

            List<List<String>> lines = new ArrayList<>();
            Long solution1 = 0l;
            Long solution2 = 0l;
            String line = "";
            List<String> addLines = new ArrayList<>();

            while (in.hasNextLine()) {
                line = in.nextLine();

                if (line == "") {
                    lines.add(addLines);
                    addLines = new ArrayList<>();
                } else {
                    addLines.add(line);
                }
            }
            lines.add(addLines);
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

    private static Long part1(List<List<String>> input) {
        Long res = 0l;
        for (List<String> list : input) {
            List<String> transpose = MyList.getTranspose(list);
            res += findReflet(list, false);
            res += findReflet(transpose, true);
        }
        return res;
    }

    private static Long part2(List<List<String>> input) {
        Long res = 0l;
        for (List<String> list : input) {
            List<String> transpose = MyList.getTranspose(list);
            res += findWithOneDifference(list, false);
            res += findWithOneDifference(transpose, true);
        }
        return res;
    }

    public static int findWithOneDifference(List<String> list, boolean isTranspose) {
        List<String> above = new ArrayList<>();
        List<String> below = new ArrayList<>();
        int res = 0;
        for (int r = 0; r < list.size() - 1; r++) {
            if (r + 1 < list.size() - (r + 1)) {
                above = (list.subList(0, r + 1));
                below = list.subList(r + 1, r + (r + 1) + 1);
            } else {
                above = list.subList((r + 1) - (list.size() - (r + 1)), r + 1);
                below = list.subList(r + 1, list.size());
            }
            if (comparerDiff(MyList.reverseList(above), below) == 1) {
                res += isTranspose ? (r + 1) : (r + 1) * 100;
            }
        }
        return res;
    }

    private static int comparerDiff(List<String> liste1, List<String> liste2) {
        int diff = 0;
        for (int i = 0; i < liste1.size(); i++) {
            for (int j = 0; j < liste1.get(i).length(); j++) {
                if (liste1.get(i).charAt(j) != liste2.get(i).charAt(j)) {
                    diff++;
                }
            }
        }

        return diff;
    }

    public static int diff(String string1, String string2) {
        int differences = 0;

        for (int i = 0; i < string1.length(); i++) {
            if (string1.charAt(i) != string2.charAt(i)) {
                differences++;
            }
        }
        differences += Math.abs(string1.length() - string2.length());

        return differences;
    }

    private static Long findReflet(List<String> list, boolean isTranspose) {
        List<String> above = new ArrayList<>();
        List<String> below = new ArrayList<>();
        for (int r = 0; r < list.size() - 1; r++) {
            if (r + 1 < list.size() - (r + 1)) {
                above = (list.subList(0, r + 1));
                below = list.subList(r + 1, r + (r + 1) + 1);
            } else {
                above = list.subList((r + 1) - (list.size() - (r + 1)), r + 1);
                below = list.subList(r + 1, list.size());
            }
            boolean compare = comparerList(above, below);
            if (compare) {
                return (long) (isTranspose ? (r + 1) : (r + 1) * 100);
            }
        }
        return 0l;
    }

    private static boolean comparerList(List<String> liste1, List<String> liste2) {
        Set<String> ensemble1 = new HashSet<>(liste1);
        Set<String> ensemble2 = new HashSet<>(liste2);

        return ensemble1.equals(ensemble2);
    }
}
