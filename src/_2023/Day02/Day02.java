package _2023.Day02;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.number.MyNumber;
import outils.split.Split;

/**
 * author vportal
 * 
 */
public class Day02 {

    private static enum ColorCube {
        red, blue, green
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day02\\input.txt"));

            ArrayList<String> games = new ArrayList<String>();
            int solution1 = 0;
            int solution2 = 0;
            String game = "";

            while (in.hasNextLine()) {
                game = in.nextLine();
                games.add(game);
            }

            for (String gm : games) {
                int colonIndex = gm.indexOf(":");
                String gameString = "";
                String tiragesString = "";
                if (colonIndex != -1) {
                    gameString = gm.substring(0, colonIndex).trim();
                    tiragesString = gm.substring(colonIndex).trim();
                } else {
                    throw new IllegalArgumentException("Le ':' n'est pas présent dans la chaîne.");
                }

                solution1 += part1(gameString, tiragesString);
                solution2 += part2(tiragesString);
            }

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Déterminez quels jeux auraient été possibles si le sac avait été chargé avec
     * seulement 12 cubes rouges, 13 cubes verts et 14 cubes bleus. Quelle est la
     * somme des ID de ces jeux?
     */
    private static int part1(String gameString, String tiragesString) {
        int nbGame = getNumberGame(gameString);
        String[] tirages = Split.splitString(tiragesString, ";");
        for (String tirage : tirages) {
            if (!validTirage(tirage)) {
                return 0;
            }
        }
        return nbGame;
    }

    /*
     * Pour chaque jeu, trouvez l'ensemble minimum de cubes qui doivent avoir été
     * présents. Quelle est la somme de la puissance de ces ensembles?
     */
    private static int part2(String tiragesString) {
        String[] tirages = Split.splitString(tiragesString, ";");
        return getMultMinCubes(tirages);
    }

    private static int getMultMinCubes(String[] tirages) {
        Map<String, Integer> colorNumbers = new HashMap<>();
        colorNumbers.put(ColorCube.blue.name(), 0);
        colorNumbers.put(ColorCube.red.name(), 0);
        colorNumbers.put(ColorCube.green.name(), 0);
        for (String tirage : tirages) {
            Pattern pattern = Pattern.compile("(\\d+)\\s+(\\w+)");
            Matcher matcher = pattern.matcher(tirage);

            // Trouver toutes les correspondances
            while (matcher.find()) {
                int number = Integer.parseInt(matcher.group(1));
                String color = matcher.group(2).toLowerCase();

                if (colorNumbers.containsKey(color) && colorNumbers.get(color) < number) {
                    colorNumbers.put(color, number);
                }

            }
        }
        return colorNumbers.get(ColorCube.red.name()) * colorNumbers.get(ColorCube.green.name())
                * colorNumbers.get(ColorCube.blue.name());
    }

    private static boolean validTirage(String tirage) {
        Map<String, Integer> colorNumbers = new HashMap<>();

        Pattern pattern = Pattern.compile("(\\d+)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(tirage);

        // Trouver toutes les correspondances
        while (matcher.find()) {
            int number = Integer.parseInt(matcher.group(1));
            String color = matcher.group(2).toLowerCase();

            colorNumbers.put(color, colorNumbers.getOrDefault(color, 0) + number);
        }

        if ((colorNumbers.containsKey(ColorCube.red.name()) && colorNumbers.get(ColorCube.red.name()) > 12)
                || (colorNumbers.containsKey(ColorCube.green.name()) && colorNumbers.get(ColorCube.green.name()) > 13)
                || (colorNumbers.containsKey(ColorCube.blue.name()) && colorNumbers.get(ColorCube.blue.name()) > 14)) {
            return false;
        }

        return true;
    }

    private static int getNumberGame(String input) {
        String nbString = input.replace("Game", "").trim();

        if (MyNumber.isNumber(nbString)) {
            return Integer.parseInt(nbString);
        } else {
            throw new IllegalArgumentException("Impossible de convertire le nbstring");
        }
    }
}
