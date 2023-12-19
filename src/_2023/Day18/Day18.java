package _2023.Day18;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;

import outils.matrix.Matrix;
import outils.pair.Pair;
import outils.point.Point;

/**
 * author vportal
 * 
 */
public class Day18 {
    static List<Point> pointsToVisit = new LinkedList<>();
    static HashMap<String, Point> dirs = new HashMap<>();

    static {
        dirs.put("U", new Point(-1, 0));
        dirs.put("D", new Point(1, 0));
        dirs.put("R", new Point(0, 1));
        dirs.put("L", new Point(0, -1));
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("_2023\\Day18\\input.txt"))) {
            List<String> lines = new ArrayList<>();
            while (in.hasNextLine()) {
                lines.add(in.nextLine());
            }

            double startTime = System.currentTimeMillis();
            Long solution1 = part1(lines);
            double endTime = System.currentTimeMillis();
            System.out.println("solution 1: " + solution1 + " Temps: " + ((endTime - startTime) / 1000) + "s");

            startTime = System.currentTimeMillis();
            // solution2 = part2(lines);
            endTime = System.currentTimeMillis();
            // System.out.println("solution 2: " + solution2 + " Temps: " + ((endTime -
            // startTime) / 1000) + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<String> lines) {
        // 32725 trop haut
        // 28095 ? mauvais
        List<Pair<String, Integer>> infos = getInfo(lines);
        pointsToVisit.add(new Point(0, 0));
        int nbVisited = 0;
        Point cur = pointsToVisit.get(0);
        for (Pair<String, Integer> pair : infos) {
            Point dir = dirs.get(pair.getFirst());
            int n = pair.getSecond();
            nbVisited += n;
            cur = new Point(cur.x + dir.x * n, cur.y + dir.y * n);
            pointsToVisit.add(cur);
        }

        double area = lacet(pointsToVisit);
        return (long) ((area - (nbVisited / 2) + 1) + nbVisited);
    }

    public static double lacet(List<Point> path) {
        double res = 0;
        for (int i = 0; i < path.size(); i++) {
            Point pointA = path.get(i);
            Point pointB = path.get((i + 1) % path.size());
            res += pointA.x * pointB.y - pointB.x * pointA.y;
        }
        return Math.abs(res) / 2;
    }

    private static List<Pair<String, Integer>> getInfo(List<String> lines) {
        List<Pair<String, Integer>> res = new ArrayList<>();
        for (String string : lines) {
            String lettre = string.substring(0, 1);
            int coups = Integer.valueOf(string.substring(2, 3));
            res.add(new Pair<>(lettre, coups));
        }
        return res;
    }

}
