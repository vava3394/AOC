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
}
