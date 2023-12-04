package _2023.Day4;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import outils.number.MyNumber;;

/**
 * author vportal
 * 
 */
public class Day4 {

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day4\\input.txt"));

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

    private static int part1(ArrayList<String> lines) {
        int res = 0;
        List<Integer> points = getAllPointsOfWin(lines);
        for (Integer point : points) {
            if (point > 0) {
                if (point == 1)
                    res++;
                else
                    res += (int) Math.pow(2, point - 1);
            }
        }
        return res;
    }

    private static int part2(List<String> lines) {
        Integer[] cartes = new Integer[lines.size()];
        Arrays.fill(cartes, 1);
        List<Integer> points = getAllPointsOfWin(lines);

        for (int i = 0; i < points.size(); i++) {
            int point = points.get(i);
            for (int nbCopies = 0; nbCopies < cartes[i]; nbCopies++) {
                for (int indexCopie = 1; indexCopie <= point; indexCopie++) {
                    if (i + indexCopie < lines.size()) {
                        cartes[i + indexCopie]++;
                    }
                }
            }
        }
        return Arrays.stream(cartes).reduce(0, (a, b) -> a + b);
    }

    private static int getPointsOfWin(List<Integer> tirageWin, List<Integer> tirage) {
        return tirageWin.stream().filter(t -> tirage.contains(t)).toList().size();
    }

    private static List<Integer> getAllPointsOfWin(List<String> input) {
        List<Integer> res = new ArrayList<>();
        for (String line : input) {
            int colonIndex = line.indexOf(":");
            String tiragesString = "";
            List<Integer> tirageWin = new ArrayList<>();
            List<Integer> tirage = new ArrayList<>();

            if (colonIndex != -1) {
                tiragesString = line.substring(colonIndex);
            } else {
                throw new IllegalArgumentException("Le ':' n'est pas présent dans la chaîne.");
            }
            int colonIndexTirage = tiragesString.indexOf("|");
            if (colonIndexTirage != -1) {
                tirageWin = MyNumber.extractIntegersToList(tiragesString.substring(0, colonIndexTirage));
                tirage = MyNumber.extractIntegersToList(tiragesString.substring(colonIndexTirage));
            } else {
                throw new IllegalArgumentException("Le '|' n'est pas présent dans la chaîne.");
            }

            res.add(getPointsOfWin(tirageWin, tirage));
        }
        return res;
    }

}
