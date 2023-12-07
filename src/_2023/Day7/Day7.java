package _2023.Day7;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import outils.number.MyNumber;
import outils.pair.Pair;
import outils.string.MyString;

public class Day7 {

    private static enum ENUMSYMBOL {
        hight,
        paire,
        twoPaire,
        three,
        house,
        foor,
        all
    }

    static Integer[] all = { 5 };
    static Integer[] foor = { 1, 4 };
    static Integer[] house = { 2, 3 };
    static Integer[] three = { 1, 1, 3 };
    static Integer[] twoPaire = { 1, 2, 2 };
    static Integer[] paire = { 1, 1, 1, 2 };
    static Integer[] hight = { 1, 1, 1, 1, 1 };

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day7\\input.txt"));

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
        HashMap<String, List<Pair<String, Integer>>> symbol = getHashSymbol(input, false);
        int posWin = 1;
        for (ENUMSYMBOL enSymbol : ENUMSYMBOL.values()) {
            List<Pair<String, Integer>> value = symbol.get(enSymbol.name());
            value.sort((a, b) -> compareMain(b.getFirst(), a.getFirst(), false));
            for (Pair<String, Integer> pair : value) {
                res += pair.getSecond() * posWin;
                posWin++;
            }
        }

        return res;
    }

    public static int part2(List<String> input) {
        int res = 0;
        HashMap<String, List<Pair<String, Integer>>> symbol = getHashSymbol(input, true);
        int posWin = 1;
        for (ENUMSYMBOL enSymbol : ENUMSYMBOL.values()) {
            List<Pair<String, Integer>> value = symbol.get(enSymbol.name());

            value.sort((a, b) -> compareMain(b.getFirst(), a.getFirst(), true));
            for (Pair<String, Integer> pair : value) {
                res += pair.getSecond() * posWin;
                posWin++;
            }
        }

        return res;
    }

    private static HashMap<String, List<Pair<String, Integer>>> getHashSymbol(List<String> input, boolean useJoker) {
        HashMap<String, List<Pair<String, Integer>>> symbol = new HashMap();
        for (ENUMSYMBOL enSymbol : ENUMSYMBOL.values()) {
            symbol.put(enSymbol.name(), new ArrayList<>());
        }
        for (String line : input) {
            String cartes = line.substring(0, 5);
            int score = MyNumber.extractIntegers(line.substring(6));
            Integer[] carteVal = getTypeCarte(cartes);

            int occurenceJ = MyString.compterOccurrences(cartes, 'J');

            if (comparer(carteVal, all)) {
                List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.all.name());
                newList.add(new Pair<String, Integer>(cartes, score));
                symbol.put(ENUMSYMBOL.all.name(), newList);
            } else if (comparer(carteVal, foor)) {
                if ((occurenceJ == 1 || occurenceJ == 4) && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.all.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.all.name(), newList);
                } else {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.foor.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.foor.name(), newList);
                }
            } else if (comparer(carteVal, house)) {
                if ((occurenceJ == 2 || occurenceJ == 3) && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.all.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.all.name(), newList);
                } else {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.house.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.house.name(), newList);
                }

            } else if (comparer(carteVal, three)) {
                if ((occurenceJ == 1 || occurenceJ == 3) && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.foor.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.foor.name(), newList);
                } else {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.three.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.three.name(), newList);
                }
            } else if (comparer(carteVal, twoPaire)) {
                if (occurenceJ == 1 && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.house.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.house.name(), newList);
                } else if (occurenceJ == 2 && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.foor.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.foor.name(), newList);
                } else {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.twoPaire.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.twoPaire.name(), newList);
                }
            } else if (comparer(carteVal, paire)) {
                if ((occurenceJ == 1 || occurenceJ == 2) && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.three.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.three.name(), newList);
                } else {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.paire.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.paire.name(), newList);
                }
            } else if (comparer(carteVal, hight)) {
                if ((occurenceJ == 1) && useJoker) {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.paire.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.paire.name(), newList);
                } else {
                    List<Pair<String, Integer>> newList = symbol.get(ENUMSYMBOL.hight.name());
                    newList.add(new Pair<String, Integer>(cartes, score));
                    symbol.put(ENUMSYMBOL.hight.name(), newList);
                }
            }
        }
        return symbol;
    }

    private static Integer[] getTypeCarte(String cartes) {
        List<Integer> newT = new ArrayList<>();
        List<Integer> posDejaVu = new ArrayList<>();
        for (int i = 0; i < cartes.length(); i++) {
            char a = cartes.charAt(i);
            int pos = 1;
            for (int j = i + 1; j < cartes.length(); j++) {
                if (a == cartes.charAt(j) && !posDejaVu.contains(j) && !posDejaVu.contains(i)) {
                    ;
                    pos++;
                    posDejaVu.add(j);

                }
            }
            if (!posDejaVu.contains(i))
                newT.add(pos);
        }
        if (newT.size() == 5) {

        }
        Collections.sort(newT);
        return newT.toArray(new Integer[0]);
    }

    public static int compareMain(String main1, String main2, boolean useJoker) {
        // Comparer les cartes une par une jusqu'à trouver la carte la plus forte
        for (int i = 0; i < main1.length(); i++) {
            char carte1 = main1.charAt(i);
            char carte2 = main2.charAt(i);

            // Convertir les caractères en valeurs numériques pour la comparaison
            int valeurCarte1 = getValeurCarte(carte1, useJoker);
            int valeurCarte2 = getValeurCarte(carte2, useJoker);

            // Comparer les valeurs des cartes
            int comparaison = Integer.compare(valeurCarte2, valeurCarte1);

            // Si les cartes sont différentes, retourner le résultat de la comparaison
            if (comparaison != 0) {
                return comparaison;
            }
        }

        // Si toutes les cartes sont égales, considérer les mains comme égales
        return 0;
    }

    static boolean comparer(Integer[] tableau1, Integer[] tableau2) {
        if (Arrays.equals(tableau1, tableau2)) {
            return true;
        }
        return false;
    }

    private static int getValeurCarte(char carte, boolean useJoker) {
        // Retourner la valeur numérique associée à la carte
        switch (carte) {
            case 'A':
                return 14;
            case 'K':
                return 13;
            case 'Q':
                return 12;
            case 'J':
                if (useJoker)
                    return 1;
                return 11;
            case 'T':
                return 10;
            default:
                return Character.getNumericValue(carte);
        }
    }

}
