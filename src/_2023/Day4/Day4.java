package _2023.Day4;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;

import outils.matrix.Matrix;
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
        for (String line : lines) {

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

            res += getNumberIdentic(tirageWin, tirage);
        }

        return res;
    }

    private static int getNumberIdentic(List<Integer> tirageWin, List<Integer> tirage) {
        int points = 0;
        for (Integer element : tirage) {
            if (tirageWin.contains(element)) {
                if (points == 0) {
                    points = 1;
                } else {
                    points *= 2;
                }
            }
        }
        return points;
    }

    private static int getNumberIdentic2(List<Integer> tirageWin, List<Integer> tirage) {
        int points = 0;
        for (Integer element : tirage) {
            if (tirageWin.contains(element)) {
                if (points == 0) {
                    points = 1;
                } else {
                    points++;
                }
            }
        }
        return points;
    }

    private static int part2(List<String> lines) {
        int res = 0;
        HashMap<Integer, Integer> cartes = new HashMap<>();

        for (String line : lines) {
            int colonIndex = line.indexOf(":");
            String tiragesString = "";
            int game;
            List<Integer> tirageWin = new ArrayList<>();
            List<Integer> tirage = new ArrayList<>();
            if (colonIndex != -1) {
                game = MyNumber.getNumberGame(line.substring(0, colonIndex));
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

            if (!cartes.containsKey(game)) {
                cartes.put(game, 1);
            } else {
                cartes.put(game, cartes.get(game) + 1);
            }

            int numberWin = getNumberIdentic2(tirageWin, tirage);
            for (int i = 0; i < cartes.get(game); i++) {
                for (int j = 1; j <= numberWin; j++) {
                    if (game - 1 + j < lines.size()) {
                        if (!cartes.containsKey(game + j))
                            cartes.put(game + j, 1);
                        else
                            cartes.put(game + j, cartes.get(game + j) + 1);
                    }
                }
            }

        }
        System.out.println(cartes);
        for (Integer value : cartes.values()) {
            res += value;
        }

        return res;
    }

}
