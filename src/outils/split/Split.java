package outils.split;

/**
 * author vportal
 */
public class Split {
    public static String[] splitString(String input, String delimiter) {
        return input.split(delimiter);
    }

    public static void main(String args[]) {
        System.out.println(splitString("03", "0"));
    }
}
