package outils.list;

import java.util.ArrayList;
import java.util.List;

/**
 * author vportal
 * 
 */
public class MyList {
    public static <T> List<List<T>> splitList(List<T> list, int groupSize) {
        List<List<T>> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i += groupSize) {
            int end = Math.min(i + groupSize, list.size());
            res.add(new ArrayList<>(list.subList(i, end)));
        }
        return res;
    }

    public static <T> List<T> arrayToList(T[] cs) {
        List<T> res = new ArrayList<>();
        for (T t : cs) {
            res.add(t);
        }
        return res;
    }

    public static List<Character> getStringToListChar(String input) {
        List<Character> res = new ArrayList<>();
        for (char t : input.toCharArray()) {
            res.add(t);
        }
        return res;
    }

    public static List<String> getTranspose(List<String> lignes) {
        List<String> res = new ArrayList<>();
        for (int c = 0; c < lignes.get(0).length(); c++) {
            StringBuilder colonne = new StringBuilder();
            for (String ligne : lignes) {
                colonne.append(ligne.charAt(c));
            }
            res.add(colonne.toString());
        }

        return res;
    }

    public static <T> List<T> reverseList(List<T> list) {
        List<T> res = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            res.add(list.get(i));
        }
        return res;
    }

    public static <T> List<T> of(T input) {
        List<T> res = new ArrayList<>();
        res.add(input);
        return res;
    }
}
