package _2023.Day17;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

import outils.matrix.Matrix;

class Node {
    int row, col, totalHeat, currentHeat, direction; // 0: right, 1: down, 2: left, 3: up
    Node parent;

    public Node(int row, int col, int totalHeat, int currentHeat, int direction, Node parent) {
        this.row = row;
        this.col = col;
        this.totalHeat = totalHeat;
        this.currentHeat = currentHeat;
        this.direction = direction;
        this.parent = parent;
    }
}

public class Day17 {
    public static int findMinHeat(int[][] heatMap) {
        int rows = heatMap.length;
        int cols = heatMap[0].length;
        Node start = new Node(0, 0, 0, 0, 0, null);
        Node goal = new Node(rows - 1, cols - 1, 0, 0, 0, null);

        PriorityQueue<Node> heap = new PriorityQueue<>(Comparator.comparingInt(node -> node.totalHeat));
        heap.offer(start);

        HashSet<String> visited = new HashSet<>();

        while (!heap.isEmpty()) {
            Node current = heap.poll();

            if (current.row == goal.row && current.col == goal.col) {
                printPath(current, heatMap);
                return current.totalHeat;
            }

            String positionKey = current.row + "," + current.col + "," + current.direction;
            if (visited.contains(positionKey)) {
                continue;
            }

            visited.add(positionKey);

            int[][] moves = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } }; // right, down, left, up
            for (int turn = -1; turn <= 1; turn += 2) {
                int nextDirection = (current.direction + turn + 4) % 4;
                int nextRow = current.row + moves[nextDirection][0];
                int nextCol = current.col + moves[nextDirection][1];

                if (nextRow >= 0 && nextRow < rows && nextCol >= 0 && nextCol < cols) {
                    int nextHeat = heatMap[nextRow][nextCol];
                    int nextTotalHeat = current.totalHeat + nextHeat;

                    Node nextNode = new Node(nextRow, nextCol, nextTotalHeat, nextHeat, nextDirection, current);
                    heap.offer(nextNode);
                }
            }
        }

        return -1; // No valid path
    }

    public static void printPath(Node node, int[][] heatMap) {
        while (node != null) {
            System.out.println("[" + node.row + "," + node.col + "] Heat: " + heatMap[node.row][node.col]);
            node = node.parent;
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
            // solution2 = part2(lines);
            endTime = System.currentTimeMillis();
            System.out.println("solution 2 : " + solution2 + " Temps : " + ((endTime - startTime) / 1000) + "s");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<String> lines) {
        int[][] heatMap = Matrix.createIntMatrix(lines);
        Matrix.printMatrix(heatMap);
        int minHeat = findMinHeat(heatMap);
        return (long) minHeat;
    }
}
