package outils.string;

/**
 * author vportal
 * 
 */
public class MyString {

    public static String clearSpace(String input) {
        return input.replaceAll("\\s", "");
    }

    public static int compterOccurrences(String chaine, char lettre) {
        int compteur = 0;
        for (char caractere : chaine.toCharArray()) {
            if (caractere == lettre) {
                compteur++;
            }
        }
        return compteur;
    }
}
