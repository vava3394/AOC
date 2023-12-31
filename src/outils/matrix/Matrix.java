package outils.matrix;

import java.util.ArrayList;
import java.util.List;

/**
 * author vportal
 * 
 */
public class Matrix {

    /**
     * Via une list de string creer une matrix de chaque charracter des string
     * 
     * @param input - List String
     * @return List List String
     */
    public static List<List<String>> createListMatrix(List<String> input) {
        List<List<String>> mx = new ArrayList<>();
        for (String ln : input) {
            mx.add(getLineMatrix(ln));
        }
        return mx;
    }

    /**
     * Via une list de string creer une matrix de chaque charracter des string
     * 
     * @param input - List<String>
     * @return String[][]
     */
    public static String[][] createMatrix(List<String> input) {
        String[][] mx = new String[input.size()][];
        for (int i = 0; i < mx.length; i++) {
            mx[i] = getLineMatrix(input.get(i)).toArray(new String[0]);
        }
        return mx;
    }

    public static int[][] createIntMatrix(List<String> input) {
        int[][] mx = new int[input.size()][];
        for (int i = 0; i < mx.length; i++) {
            mx[i] = getLineIntMatrix(input.get(i));
        }
        return mx;
    }

    /**
     * 
     * @param input - List List String
     * @return String[][]
     */
    public static String[][] converToMatrix(List<List<String>> input) {
        String[][] mx = new String[input.size()][];
        for (int i = 0; i < input.size(); i++) {
            mx[i] = input.get(i).toArray(new String[0]);
        }
        return mx;
    }

    /**
     * 
     * @param input - String[][]
     * @return List List String
     */
    public static List<List<String>> convertToListMatrix(String[][] input) {
        List<List<String>> mx = new ArrayList<>();
        for (String[] listInput : input) {
            List<String> line = new ArrayList<>();
            for (String element : listInput) {
                line.add(element);
            }
            mx.add(line);
        }
        return mx;
    }

    /**
     * Affichage de la matrix
     * 
     * @param input - List List String
     */
    public static void printMatrix(List<List<String>> input) {
        printMatrix(converToMatrix(input));
    }

    /**
     * Affichage de la matrix
     * 
     * @param <T>
     * 
     */
    public static <T> void printMatrix(T[][] mx) {
        for (int i = 0; i < mx.length; i++) {
            for (int j = 0; j < mx[i].length; j++) {
                System.out.print(mx[i][j]);
                if (j < mx[i].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }

    public static void printMatrix(int[][] mx) {
        for (int i = 0; i < mx.length; i++) {
            for (int j = 0; j < mx[i].length; j++) {
                System.out.print(mx[i][j]);
                if (j < mx[i].length - 1) {
                    System.out.print(",");
                }
            }
            System.out.println();
        }
    }

    private static List<String> getLineMatrix(String line) {
        List<String> res = new ArrayList<>();

        while (line.length() > 0) {
            String element = line.substring(0, 1);
            res.add(element);
            line = line.substring(1);
        }
        return res;
    }

    private static int[] getLineIntMatrix(String line) {
        int[] res = new int[line.length()];
        for (int i = 0; i < res.length; i++) {
            String element = line.substring(i, i + 1);
            res[i] = (Integer.parseInt(element));
        }
        return res;
    }

}
