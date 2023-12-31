package _2023.Day01;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import outils.number.MyNumber;

/**
 * author vportal
 */
public class Day01 {

    private static enum NUMBER {
        one, two, three, four, five, six, seven, eight, nine
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day01\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            int solution2 = 0;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }

            for (String nb : lines) {
                solution1 += getNumberPart1(nb);
                solution2 += getNumberPart2(nb);
            }
            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Sur chaque ligne, la valeur d'étalonnage peut être trouvée en combinant
     * premier chiffre et le dernier chiffre (dans cet ordre) pour former un seul
     * numéro à deux chiffres.
     */
    private static int getNumberPart1(String input) {
        int p1 = 0;
        int p2 = 0;
        for (int i = 0; i < input.length(); i++) {
            String c = "" + input.charAt(i);
            if (MyNumber.isNumber(c)) {
                p1 = Integer.parseInt(c);
                break;
            }

        }
        for (int i = input.length() - 1; i >= 0; i--) {
            String c = "" + input.charAt(i);
            if (MyNumber.isNumber(c)) {
                p2 = Integer.parseInt(c);
                break;
            }

        }

        return p1 * 10 + p2;
    }

    /*
     * Votre calcul n'est pas tout à fait correct. Il semble que certains des
     * chiffres sont en fait épelé avec des lettres: one, two, three, four, five,
     * six, seven, eight, et nine aussi compter comme "chiffres" valides".
     * 
     * Equipé de ces nouvelles informations, vous devez maintenant trouver le
     * premier et le dernier chiffre réel sur chaque ligne.
     */
    private static int getNumberPart2(String input) {
        int p1 = 0;
        int p2 = 0;
        for (int i = 0; i < input.length(); i++) {
            String c = "" + input.charAt(i);
            if (MyNumber.isNumber(c)) {
                p1 = Integer.parseInt(c);
                break;
            } else {
                for (NUMBER number : NUMBER.values()) {
                    if (input.substring(i).startsWith(number.name())) {
                        p1 = number.ordinal() + 1;
                        break;
                    }
                }
                if (p1 != 0) {
                    break;
                }
            }

        }
        for (int i = input.length() - 1; i >= 0; i--) {
            String c = "" + input.charAt(i);
            if (MyNumber.isNumber(c)) {
                p2 = Integer.parseInt(c);
                break;
            } else {
                for (NUMBER number : NUMBER.values()) {
                    if (input.substring(i).startsWith(number.name())) {
                        p2 = number.ordinal() + 1;
                        break;
                    }
                }
                if (p2 != 0) {
                    break;
                }
            }
        }

        return p1 * 10 + p2;
    }
}
