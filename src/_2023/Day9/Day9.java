package _2023.Day9;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import outils.number.MyNumber;

/**
 * author vportal
 * 
 */
public class Day9 {

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day9\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            int solution2 = 0;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }
            solution1 = part1(lines);
            solution2 = part2(lines);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int part1(List<String> input) {
        int res = 0;
        for (String valuesString : input) {
            int newValue = 0;
            List<Integer> values = MyNumber.extractIntegersToList(valuesString);
            while (!isFinish(values)) {
                newValue += values.get(values.size() - 1);
                values = calculeNewValues(values);
            }
            res += newValue;
        }
        return res;
    }

    public static int part2(List<String> input) {
        int res = 0;
        for (String valuesString : input) {
            List<Integer> values = MyNumber.extractIntegersToList(valuesString);
            int newValue = 0;
            int i = 0;
            while (!isFinish(values)) {
                if (i % 2 == 0) {
                    newValue += values.get(0);
                } else {
                    newValue -= values.get(0);
                }
                values = calculeNewValues(values);
                i++;
            }

            res += newValue;
        }
        return res;
    }

    private static List<Integer> calculeNewValues(List<Integer> values) {
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < values.size() - 1; i++) {
            res.add(values.get(i + 1) - values.get(i));
        }
        return res;
    }

    private static boolean isFinish(List<Integer> value) {
        for (Integer integer : value) {
            if (integer != 0) {
                return false;
            }
        }
        return true;
    }
}
