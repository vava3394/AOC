package _2023.Day4;

import java.io.File;
import java.util.ArrayList;
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
        for (String line : lines) {
            DataGame dataGame = new DataGame(line);
            res += getPointsOfWin(dataGame.tirageWin, dataGame.tirage);
        }

        return res;
    }

    private static int part2(List<String> lines) {
        HashMap<Integer, Integer> cartes = new HashMap<>();

        for (String line : lines) {

            DataGame dataGame = new DataGame(line);

            if (!cartes.containsKey(dataGame.game)) {
                cartes.put(dataGame.game, 1);
            } else {
                cartes.put(dataGame.game, cartes.get(dataGame.game) + 1);
            }

            int numberWin = getNumberWin(dataGame.tirageWin, dataGame.tirage);
            for (int nbCopies = 0; nbCopies < cartes.get(dataGame.game); nbCopies++) {
                for (int indexCopie = 1; indexCopie <= numberWin; indexCopie++) {
                    if (dataGame.game + indexCopie < lines.size()) {
                        if (!cartes.containsKey(dataGame.game + indexCopie))
                            cartes.put(dataGame.game + indexCopie, 1);
                        else
                            cartes.put(dataGame.game + indexCopie, cartes.get(dataGame.game + indexCopie) + 1);
                    }
                }
            }

        }
        return cartes.values().stream().reduce(0, (a, b) -> a + b);
    }

    private static int getPointsOfWin(List<Integer> tirageWin, List<Integer> tirage) {
        int nbWin = getNumberWin(tirageWin, tirage);
        if (nbWin > 0) {
            int points = 1;
            for (int i = 0; i < nbWin - 1; i++) {
                points *= 2;
            }
            return points;
        }

        return 0;
    }

    private static int getNumberWin(List<Integer> tirageWin, List<Integer> tirage) {
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

    private static class DataGame {
        String tiragesString = "";
        int game;
        List<Integer> tirageWin = new ArrayList<>();
        List<Integer> tirage = new ArrayList<>();

        public DataGame(String line) {
            int colonIndex = line.indexOf(":");
            if (colonIndex != -1) {
                game = MyNumber.getNumberGame(line.substring(0, colonIndex)) - 1;
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
        }

    }
}
