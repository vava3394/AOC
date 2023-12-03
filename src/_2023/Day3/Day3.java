package _2023.Day3;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import outils.matrix.Matrix;

/**
 * author vportal
 * 
 */
public class Day3 {

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day3\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            int solution2 = 0;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }
            List<List<String>> mx = Matrix.createListMatrix(lines);

            solution1 = part1(mx);
            solution2 = part2(mx);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<List<String>> mx) {
        int res = 0;
        for (int row = 0; row < mx.size(); row++) {
            for (int col = 0; col < mx.get(row).size(); col++) {
                if (mx.get(row).get(col).matches("\\d+")) {
                    int i = 0;
                    String number = "";
                    while (col + i < mx.get(row).size() && mx.get(row).get(col + i).matches("\\d+")) {
                        number += mx.get(row).get(col + i);
                        i++;
                    }
                    for (int j = col; j < col + i; j++) {
                        if (isSymAround(mx, row, j)) {
                            res += Integer.parseInt(number);
                            break;
                        }
                    }
                    col += i;
                }
            }
        }

        return res;
    }

    private static int part2(List<List<String>> mx) {
        int res = 0;
        for (int row = 0; row < mx.size(); row++) {
            for (int col = 0; col < mx.get(row).size(); col++) {
                if (mx.get(row).get(col).equals("*")) {
                    res += getEngrenage(mx, row, col);
                }
            }
        }
        return res;
    }

    private static int getEngrenage(List<List<String>> mx, int i, int j) {
        List<Integer> dejaVu = new ArrayList<>();
        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (row >= 0 && row < mx.size() && col >= 0
                        && col < mx.get(i).size() && (row != i || col != j)) {
                    String numberString = "";
                    if (mx.get(row).get(col).matches("\\d+")) {
                        numberString = mx.get(row).get(col);
                        if (col - 1 >= 0 && mx.get(row).get(col - 1).matches("\\d+")) {
                            numberString = mx.get(row).get(col - 1).concat(numberString);
                            if (col - 2 >= 0 && mx.get(row).get(col - 2).matches("\\d+"))
                                numberString = mx.get(row).get(col - 2).concat(numberString);
                        }

                        if (col + 1 < mx.get(i).size() && mx.get(row).get(col + 1).matches("\\d+")) {
                            numberString += mx.get(row).get(col + 1);
                            if (col + 2 < mx.get(i).size() && mx.get(row).get(col + 2).matches("\\d+"))
                                numberString += mx.get(row).get(col + 2);
                        }
                        if (numberString != "") {
                            int number = Integer.parseInt(numberString);
                            if (!dejaVu.contains(number)) {
                                dejaVu.add(number);
                            }
                        }
                    }
                }
            }
        }
        if (dejaVu.size() > 1) {
            return dejaVu.stream().reduce(1, (a, b) -> a * b);
        }
        return 0;
    }

    private static boolean isSymAround(List<List<String>> mx, int i, int j) {
        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (row >= 0 && row < mx.size() && col >= 0 && col < mx.get(i).size() && (row != i || col != j)) {
                    if (!mx.get(row).get(col).equals(".") && !mx.get(row).get(col).matches("\\d+")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
