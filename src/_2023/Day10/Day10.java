package _2023.Day10;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * author vportal
 * 
 */
public class Day10 {

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day10\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            int solution2 = 0;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }

            solution1 = part1(lines.toArray(new String[0]));
            // solution2 = part2(lines);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int part1(String[] lines) {
        int sr = 0, sc = 0;

        for (int r = 0; r < lines.length; r++) {
            for (int c = 0; c < lines[r].length(); c++) {
                if (lines[r].charAt(c) == 'S') {
                    sr = r;
                    sc = c;
                    break;
                }
            }
        }

        List<int[]> loop = new ArrayList<>();
        Queue<int[]> queue = new ArrayDeque<>();

        loop.add(new int[] { sr, sc });
        queue.add(new int[] { sr, sc });

        while (!queue.isEmpty()) {
            int[] pair = queue.poll();
            int r = pair[0];
            int c = pair[1];
            char ch = lines[r].charAt(c);
            int[] up = new int[] { r + 1, c }, down = new int[] { r - 1, c },
                    right = new int[] { r, c + 1 }, left = new int[] { r, c - 1 };

            if (r > 0 && "S|JL".contains(String.valueOf(ch))
                    && "|7F".contains(String.valueOf(lines[down[0]].charAt(c)))
                    && !isContain(loop, down)) {
                loop.add(down);
                queue.add(down);
            }

            if (r < lines.length - 1 && "S|7F".contains(String.valueOf(ch))
                    && "|JL".contains(String.valueOf(lines[up[0]].charAt(c)))
                    && !isContain(loop, up)) {
                loop.add(up);
                queue.add(up);
            }

            if (c > 0 && "S-J7".contains(String.valueOf(ch))
                    && "-LF".contains(String.valueOf(lines[r].charAt(left[1])))
                    && !isContain(loop, left)) {
                loop.add(left);
                queue.add(left);
            }

            if (c < lines[r].length() - 1 && "S-LF".contains(String.valueOf(ch))
                    && "-J7".contains(String.valueOf(lines[r].charAt(right[1])))
                    && !isContain(loop, right)) {
                loop.add(right);
                queue.add(right);
            }
        }

        return loop.size() / 2;

    }

    public static boolean isContain(List<int[]> input, int[] test) {
        for (int[] i : input) {
            if (i[0] == test[0] && i[1] == test[1]) {
                return true;
            }
        }
        return false;
    }
}
