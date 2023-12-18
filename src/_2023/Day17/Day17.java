package _2023.Day17;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import outils.matrix.Matrix;

public class Day17 {

    static class State implements Comparable<State> {
        int hl, row, col, dirRow, dirCol, n;

        State(int hl, int row, int col, int dirRow, int dirCol, int n) {
            this.hl = hl;
            this.row = row;
            this.col = col;
            this.dirRow = dirRow;
            this.dirCol = dirCol;
            this.n = n;
        }

        @Override
        public int compareTo(State other) {
            return Integer.compare(this.hl, other.hl);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            State state = (State) obj;
            return row == state.row && col == state.col && dirRow == state.dirRow && dirCol == state.dirCol
                    && n == state.n;
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new int[] { row, col, dirRow, dirCol, n });
        }
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day17\\input.txt"));

            List<String> lines = new ArrayList<>();
            Long solution1 = 0l;
            Long solution2 = 0l;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }
            double startTime = System.currentTimeMillis();
            solution1 = part1(lines);
            double endTime = System.currentTimeMillis();
            System.out.println("solution 1 : " + solution1 + " Temps : " + ((endTime - startTime) / 1000) + "s");
            startTime = System.currentTimeMillis();
            solution2 = part2(lines);
            endTime = System.currentTimeMillis();
            System.out.println("solution 2 : " + solution2 + " Temps : " + ((endTime - startTime) / 1000) + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<String> lines) {
        int[][] heatMap = Matrix.createIntMatrix(lines);
        return (long) findMinHeat(heatMap);
    }

    private static Long part2(List<String> lines) {
        int[][] heatMap = Matrix.createIntMatrix(lines);
        return (long) findMinHeatP2(heatMap);
    }

    public static int findMinHeat(int[][] heatMap) {
        int rows = heatMap.length; // Replace with the actual number of rows in your grid
        int cols = heatMap[0].length;
        Set<State> seen = new HashSet<>();
        PriorityQueue<State> pq = new PriorityQueue<>();

        pq.add(new State(0, 0, 0, 0, 0, 0));

        while (!pq.isEmpty()) {
            State state = pq.poll();

            int hl = state.hl;
            int r = state.row;
            int c = state.col;
            int dr = state.dirRow;
            int dc = state.dirCol;
            int n = state.n;

            if (r == rows - 1 && c == cols - 1) {
                return hl;
            }

            if (seen.contains(state)) {
                continue;
            }

            seen.add(state);

            if (n < 3 && (dr != 0 || dc != 0)) {
                int nr = r + dr;
                int nc = c + dc;
                if (0 <= nr && nr < rows && 0 <= nc && nc < cols) {
                    pq.add(new State(hl + heatMap[nr][nc], nr, nc, dr, dc, n + 1));
                }
            }

            int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
            for (int[] direction : directions) {
                int ndr = direction[0];
                int ndc = direction[1];
                if ((ndr != dr || ndc != dc) && (ndr != -dr || ndc != -dc)) {
                    int nr = r + ndr;
                    int nc = c + ndc;
                    if (0 <= nr && nr < rows && 0 <= nc && nc < cols) {
                        pq.add(new State(hl + heatMap[nr][nc], nr, nc, ndr, ndc, 1));
                    }
                }
            }
        }
        return -1;
    }

    public static int findMinHeatP2(int[][] heatMap) {
        int rows = heatMap.length; // Replace with the actual number of rows in your grid
        int cols = heatMap[0].length;
        Set<State> seen = new HashSet<>();
        PriorityQueue<State> pq = new PriorityQueue<>();

        pq.add(new State(0, 0, 0, 0, 0, 0));

        while (!pq.isEmpty()) {
            State state = pq.poll();

            int hl = state.hl;
            int r = state.row;
            int c = state.col;
            int dr = state.dirRow;
            int dc = state.dirCol;
            int n = state.n;

            if (r == rows - 1 && c == cols - 1) {
                return hl;
            }

            if (seen.contains(state)) {
                continue;
            }

            seen.add(state);

            if (n < 10 && (dr != 0 || dc != 0)) {
                int nr = r + dr;
                int nc = c + dc;
                if (0 <= nr && nr < rows && 0 <= nc && nc < cols) {
                    pq.add(new State(hl + heatMap[nr][nc], nr, nc, dr, dc, n + 1));
                }
            }

            if (n >= 4 || (dr == 0 && dc == 0)) {
                int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
                for (int[] direction : directions) {
                    int ndr = direction[0];
                    int ndc = direction[1];
                    if ((ndr != dr || ndc != dc) && (ndr != -dr || ndc != -dc)) {
                        int nr = r + ndr;
                        int nc = c + ndc;
                        if (0 <= nr && nr < rows && 0 <= nc && nc < cols) {
                            pq.add(new State(hl + heatMap[nr][nc], nr, nc, ndr, ndc, 1));
                        }
                    }
                }
            }

        }
        return -1;
    }

}
