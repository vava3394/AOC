package outils.number;

/**
 * author vportal
 */
public class IsNumber {
    public static boolean isNumber(String val) {
        try {
            Integer.parseInt(val);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String args[]) {
        System.out.println(Long.parseLong("03"));
    }
}
