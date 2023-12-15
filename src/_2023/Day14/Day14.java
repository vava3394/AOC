package _2023.Day14;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import outils.matrix.Matrix;

/**
 * author vportal
 * 
 */
public class Day14 {

    private static final String ROCK = "O";
    private static final String EMPTY = ".";
    private static final String ROCKCUBE = "#";

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day14\\input.txt"));

            List<String> lines = new ArrayList<>();
            Long solution1 = 0l;
            Long solution2 = 0l;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
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

    public static Long part1(List<String> lines) {
        String[][] mx = Matrix.createMatrix(lines);
        mx = slideN(mx);
        return calcule(mx);
    }

    public static Long part2(List<String> lines) {
        String[][] mx = Matrix.createMatrix(lines);
        for (int i = 0; i < 1000; i++) {
            mx = slideN(mx);
            mx = slideO(mx);
            mx = slideS(mx);
            mx = slideE(mx);
        }
        return calcule(mx);
    }

    private static String[][] slideN(String[][] mx) {

        for (int i = 0; i < mx[0].length; i++) {
            int posBlockage = -1;
            for (int j = 0; j < mx.length; j++) {
                if (mx[j][i].equals(ROCKCUBE)) {
                    posBlockage = j;
                } else if (mx[j][i].equals(ROCK)) {
                    mx[j][i] = EMPTY;
                    mx[posBlockage + 1][i] = ROCK;
                    posBlockage++;
                }
            }
        }
        return mx;
    }

    private static String[][] slideO(String[][] mx) {
        for (int i = 0; i < mx.length; i++) {
            int posBlockage = -1;
            for (int j = 0; j < mx[i].length; j++) {
                if (mx[i][j].equals(ROCKCUBE)) {
                    posBlockage = j;
                } else if (mx[i][j].equals(ROCK)) {
                    mx[i][j] = EMPTY;
                    mx[i][posBlockage + 1] = ROCK;
                    posBlockage++;
                }
            }
        }
        return mx;
    }

    private static String[][] slideS(String[][] mx) {
        for (int i = 0; i < mx[0].length; i++) {
            int posBlockage = mx.length;
            for (int j = mx.length - 1; j >= 0; j--) {
                if (mx[j][i].equals(ROCKCUBE)) {
                    posBlockage = j;
                } else if (mx[j][i].equals(ROCK)) {
                    mx[j][i] = EMPTY;
                    mx[posBlockage - 1][i] = ROCK;
                    posBlockage--;
                }
            }
        }
        return mx;
    }

    private static String[][] slideE(String[][] mx) {
        for (int i = 0; i < mx.length; i++) {
            int posBlockage = mx[i].length;
            for (int j = mx[i].length - 1; j >= 0; j--) {
                if (mx[i][j].equals(ROCKCUBE)) {
                    posBlockage = j;
                } else if (mx[i][j].equals(ROCK)) {
                    mx[i][j] = EMPTY;
                    mx[i][posBlockage - 1] = ROCK;
                    posBlockage--;
                }
            }
        }
        return mx;
    }

    private static Long calcule(String[][] mx) {
        Long res = 0l;
        for (int i = 0; i < mx.length; i++) {
            int nbRock = 0;
            for (int j = 0; j < mx[i].length; j++) {
                if (mx[i][j].equals(ROCK)) {
                    nbRock++;
                }
            }
            res += nbRock * ((mx.length - i));
        }

        return res;
    }
}
