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
    static final int ECART = 999999;

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day11\\input.txt"));
            ArrayList<String> lines = new ArrayList<String>();
            ArrayList<String> linesExpend = new ArrayList<String>();
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                boolean noG = true;
                for (char ch : line.toCharArray()) {
                    if (ch == '#') {
                        noG = false;
                        break;
                    }
                }
                if (noG) {
                    linesExpend.add(line);
                }
                linesExpend.add(line);
                lines.add(line);
            }
            int solution1 = 0;
            Long solution2 = 0l;

            solution1 = part1(getUnivers(linesExpend));
            solution2 = part2(lines);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(List<String> univers) {
        int res = 0;
        List<int[]> posGalaxies = getGalaxie(univers);
        for (int i = 0; i < posGalaxies.size(); i++) {
            int sr = posGalaxies.get(i)[0];
            int sc = posGalaxies.get(i)[1];
            for (int j = i + 1; j < posGalaxies.size(); j++) {
                int x = Math.abs(posGalaxies.get(j)[0] - sr);
                int y = Math.abs(posGalaxies.get(j)[1] - sc);
                res += (x + y);
            }
        }

        return res;
    }

    private static Long part2(List<String> univers) {
        Long res = 0l;
        List<Integer> posX = getPosX(univers);
        List<Integer> posY = getPosY(univers);
        List<int[]> posGalaxies = getGalaxie(univers);
        for (int i = 0; i < posGalaxies.size(); i++) {
            int sr = posGalaxies.get(i)[0];
            int sc = posGalaxies.get(i)[1];
            for (int j = i + 1; j < posGalaxies.size(); j++) {
                int x = Math.abs(posGalaxies.get(j)[0] - sr);
                int y = Math.abs(posGalaxies.get(j)[1] - sc);
                for (int xx : posX) {
                    if (xx < posGalaxies.get(j)[0] && xx > sr) {
                        x += ECART;
                    }
                }
                for (int yy : posY) {
                    if (posGalaxies.get(j)[1] - sc > 0) {
                        if (yy > sc && yy < posGalaxies.get(j)[1]) {
                            y += ECART;
                        }
                    } else if (posGalaxies.get(j)[1] - sc < 0) {
                        if (yy > posGalaxies.get(j)[1] && yy < sc) {
                            y += ECART;
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
                if (univers.get(r).charAt(c) == '#') {
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
                if (univers.get(r).charAt(c) == '#') {
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

    private static List<String> getUnivers(List<String> lines) {
        int rows = lines.size();
        int cols = lines.get(0).length();
        List<Integer> ajoutC = new ArrayList<>();
        int i = 0;
        for (int c = 0; c < cols; c++) {
            boolean noG = true;
            for (int r = 0; r < rows; r++) {
                if (lines.get(r).charAt(c) == '#') {
                    noG = false;
                    break;
                }
            }
            if (noG) {
                ajoutC.add(c + i);
                i++;
            }
        }
        for (Integer c : ajoutC) {
            for (int r = 0; r < rows; r++) {
                String test = lines.get(r);
                lines.set(r, test.substring(0, c) + "." + test.substring(c));
            }
        }
        return lines;
    }
}
