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
            String[][] mx = Matrix.createMatrix(lines);

            solution1 = part1(mx);
            solution2 = part2(mx);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(String[][] mx) {
        int res = 0;
        for (int row = 0; row < mx.length; row++) {
            for (int col = 0; col < mx[row].length; col++) {
                if (mx[row][col].matches("\\d+")) {
                    int i = 0;
                    String number = "";
                    while (col + i < mx[row].length && mx[row][col + i].matches("\\d+")) {
                        number += mx[row][col + i];
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

    private static int part2(String[][] mx) {
        int res = 0;
        for (int row = 0; row < mx.length; row++) {
            for (int col = 0; col < mx[row].length; col++) {
                if (mx[row][col].equals("*")) {
                    res += getEngrenage(mx, row, col);
                }
            }
        }
        return res;
    }

    private static int getEngrenage(String[][] mx, int i, int j) {
        List<Integer> dejaVu = new ArrayList<>();
        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (row >= 0 && row < mx.length && col >= 0 && col < mx[i].length && (row != i || col != j)) {
                    String numberString = "";
                    if (mx[row][col].matches("\\d+")) {
                        numberString = mx[row][col];
                        if (col - 1 >= 0 && mx[row][col - 1].matches("\\d+")) {
                            numberString = mx[row][col - 1].concat(numberString);
                            if (col - 2 >= 0 && mx[row][col - 2].matches("\\d+"))
                                numberString = mx[row][col - 2].concat(numberString);
                        }

                        if (col + 1 < mx[row].length && mx[row][col + 1].matches("\\d+")) {
                            numberString += mx[row][col + 1];
                            if (col + 2 < mx[row].length && mx[row][col + 2].matches("\\d+"))
                                numberString += mx[row][col + 2];
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

    private static boolean isSymAround(String[][] mx, int i, int j) {
        for (int row = i - 1; row <= i + 1; row++) {
            for (int col = j - 1; col <= j + 1; col++) {
                if (row >= 0 && row < mx.length && col >= 0 && col < mx[i].length && (row != i || col != j)) {
                    if (!mx[row][col].equals(".") && !mx[row][col].matches("\\d+")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
