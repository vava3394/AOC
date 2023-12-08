package outils.graph;

/**
 * author vportal
 * 
 */
public class MyGraph {
    public static long calculatePPCM(int[] numbers) {
        long result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            result = calculatePPCM(result, numbers[i]);
        }
        return result;
    }

    public static long calculatePPCM(long a, long b) {
        return Math.abs(a * b) / calculatePGCD(a, b);
    }

    public static long calculatePGCD(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}
