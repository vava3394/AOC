package _2023.Day11;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * author vportal
 * 
 */
public class Day11 {
    static final char EMPTY_SPACE = '.';
    static final char GALAXY = '#';
    static final int ECARTPT2 = 999999;
    static final int ECARTPT1 = 1;

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day11\\input.txt"));
            ArrayList<String> lines = new ArrayList<String>();
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }
            Long solution1 = 0l;
            Long solution2 = 0l;
            List<Integer> posX = getPosX(lines);
            List<Integer> posY = getPosY(lines);
            List<int[]> posGalaxies = getGalaxie(lines);

            solution1 = part1(posX, posY, posGalaxies);
            solution2 = part2(posX, posY, posGalaxies);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<Integer> posX, List<Integer> posY, List<int[]> posGalaxies) {
        return getDistance(posX, posY, posGalaxies, ECARTPT1);
    }

    private static Long part2(List<Integer> posX, List<Integer> posY, List<int[]> posGalaxies) {
        return getDistance(posX, posY, posGalaxies, ECARTPT2);
    }

    private static Long getDistance(List<Integer> posX, List<Integer> posY, List<int[]> posGalaxies,
            int ecart) {
        Long res = 0l;
        for (int i = 0; i < posGalaxies.size(); i++) {
            int sr = posGalaxies.get(i)[0];
            int sc = posGalaxies.get(i)[1];
            for (int j = i + 1; j < posGalaxies.size(); j++) {
                int x = Math.abs(posGalaxies.get(j)[0] - sr);
                int y = Math.abs(posGalaxies.get(j)[1] - sc);
                for (int xx : posX) {
                    if (xx < posGalaxies.get(j)[0] && xx > sr) {
                        x += ecart;
                    }
                }
                for (int yy : posY) {
                    if (posGalaxies.get(j)[1] - sc > 0) {
                        if (yy > sc && yy < posGalaxies.get(j)[1]) {
                            y += ecart;
                        }
                    } else if (posGalaxies.get(j)[1] - sc < 0) {
                        if (yy > posGalaxies.get(j)[1] && yy < sc) {
                            y += ecart;
                        }
                    }
                }
                res += (x + y);
            }
        }
        return res;
    }

    private static List<Integer> getPosY(List<String> univers) {
        int rows = univers.size();
        int cols = univers.get(0).length();
        List<Integer> ajoutC = new ArrayList<>();
        for (int c = 0; c < cols; c++) {
            boolean noG = true;
            for (int r = 0; r < rows; r++) {
                if (univers.get(r).charAt(c) == GALAXY) {
                    noG = false;
                    break;
                }
            }
            if (noG) {
                ajoutC.add(c);
            }
        }
        return ajoutC;
    }

    private static List<Integer> getPosX(List<String> univers) {
        int rows = univers.size();
        int cols = univers.get(0).length();
        List<Integer> ajoutR = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            boolean noG = true;
            for (int c = 0; c < cols; c++) {
                if (univers.get(r).charAt(c) == GALAXY) {
                    noG = false;
                    break;
                }
            }
            if (noG) {
                ajoutR.add(r);
            }
        }
        return ajoutR;
    }

    private static List<int[]> getGalaxie(List<String> univers) {
        List<int[]> res = new ArrayList<>();
        for (int r = 0; r < univers.size(); r++) {
            for (int c = 0; c < univers.get(r).length(); c++) {
                if (univers.get(r).charAt(c) == GALAXY) {
                    res.add(new int[] { r, c });
                }
            }
        }
        return res;
    }
}
