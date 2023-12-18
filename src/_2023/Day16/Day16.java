package _2023.Day16;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

import outils.matrix.Matrix;
import outils.pair.Pair;

public class Day16 {
    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null || getClass() != obj.getClass())
                return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    public static final Point bas = new Point(1, 0);
    public static final Point haut = new Point(-1, 0);
    public static final Point droite = new Point(0, 1);
    public static final Point gauche = new Point(0, -1);

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day16\\input.txt"));

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
        String[][] mx = Matrix.createMatrix(lines);
        String[][] mxCache = Matrix.createMatrix(lines);
        rayCast(new Point(0, 0), droite, mx, mxCache);
        return calcule(mxCache);
    }

    private static Long part2(List<String> lines) {
        String[][] mx = Matrix.createMatrix(lines);
        Long max = Long.MIN_VALUE;
        String[][] mxCache = null;
        for (int i = 0; i < mx.length; i++) {
            dejaVue = new HashMap<>();
            mxCache = Matrix.createMatrix(lines);
            rayCast(new Point(i, 0), droite, mx, mxCache);
            max = Math.max(max, calcule(mxCache));
            dejaVue = new HashMap<>();
            mxCache = Matrix.createMatrix(lines);
            rayCast(new Point(i, mx[0].length - 1), gauche, mx, mxCache);
            max = Math.max(max, calcule(mxCache));
        }
        for (int i = 0; i < mx[0].length; i++) {
            dejaVue = new HashMap<>();
            mxCache = Matrix.createMatrix(lines);
            rayCast(new Point(0, i), bas, mx, mxCache);
            max = Math.max(max, calcule(mxCache));
            dejaVue = new HashMap<>();
            mxCache = Matrix.createMatrix(lines);
            rayCast(new Point(mx.length - 1, i), haut, mx, mxCache);
            max = Math.max(max, calcule(mxCache));
        }

        return max;
    }

    private static Long calcule(String[][] mx) {
        Long res = 0l;
        for (int i = 0; i < mx.length; i++) {
            for (int j = 0; j < mx[i].length; j++) {
                if (mx[i][j].equals("#")) {
                    res++;
                }
            }
        }
        return res;
    }

    public static boolean verifMx(Point start, Point direction, String[][] mx) {
        if ((start.x + direction.x >= 0 && start.x + direction.x < mx.length) && (start.y + direction.y >= 0
                && start.y + direction.y < mx[start.x + direction.x].length)) {
            return true;
        }
        return false;
    }

    static HashMap<String, Set<Point>> dejaVue = new HashMap<>();
    static Queue<Pair<Point, Point>> pointsToVisit = new LinkedList<>();

    private static void rayCast(Point start, Point direction, String[][] mx, String[][] mxCache) {
        pointsToVisit.offer(new Pair<Point, Point>(start, direction));
        while (!pointsToVisit.isEmpty()) {
            Pair<Point, Point> current = pointsToVisit.poll();
            mxCache[current.getFirst().x][current.getFirst().y] = "#";
            String key = current.getFirst().toString();
            dejaVue.computeIfAbsent(key, k -> new HashSet<>());
            Set<Point> directionsSet = dejaVue.get(key);
            if (!directionsSet.contains(current.getSecond())) {
                directionsSet.add(current.getSecond());
                direction(current.getFirst(), current.getSecond(), mx, mxCache);
            }
        }
    }

    private static void direction(Point start, Point direction, String[][] mx, String[][] mxCache) {
        if (verifMx(start, direction, mxCache) && mx[start.x][start.y].equals(".")) {
            pointsToVisit
                    .offer(new Pair<Point, Point>(new Point(start.x + direction.x, start.y + direction.y), direction));
        } else {
            switch (mx[start.x][start.y]) {
                case "/":
                    handleSlashDirection(start, direction, mx, mxCache);
                    break;
                case "\\":
                    handleBackslashDirection(start, direction, mx, mxCache);
                    break;
                case "|":
                    handleVerticalDirection(start, direction, mx, mxCache);
                    break;
                case "-":
                    handleHorizontalDirection(start, direction, mx, mxCache);
                    break;
            }
        }
    }

    private static void handleSlashDirection(Point start, Point direction, String[][] mx, String[][] mxCache) {
        if (direction.equals(droite) && verifMx(start, haut, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + haut.x, start.y + haut.y), haut));
        } else if (direction.equals(haut) && verifMx(start, droite, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + droite.x, start.y + droite.y), droite));
        } else if (direction.equals(bas) && verifMx(start, gauche, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + gauche.x, start.y + gauche.y), gauche));
        } else if (direction.equals(gauche) && verifMx(start, bas, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + bas.x, start.y + bas.y), bas));
        }
    }

    private static void handleBackslashDirection(Point start, Point direction, String[][] mx, String[][] mxCache) {
        if (direction.equals(droite) && verifMx(start, bas, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + bas.x, start.y + bas.y), bas));
        } else if (direction.equals(haut) && verifMx(start, gauche, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + gauche.x, start.y + gauche.y), gauche));
        } else if (direction.equals(bas) && verifMx(start, droite, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + droite.x, start.y + droite.y), droite));
        } else if (direction.equals(gauche) && verifMx(start, haut, mxCache)) {
            pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + haut.x, start.y + haut.y), haut));
        }
    }

    private static void handleVerticalDirection(Point start, Point direction, String[][] mx, String[][] mxCache) {
        if (direction.equals(droite) || direction.equals(gauche)) {
            if (verifMx(start, haut, mxCache))
                pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + haut.x, start.y + haut.y), haut));
            if (verifMx(start, bas, mxCache))
                pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + bas.x, start.y + bas.y), bas));
        } else if (verifMx(start, direction, mxCache)) {
            pointsToVisit
                    .offer(new Pair<Point, Point>(new Point(start.x + direction.x, start.y + direction.y), direction));
        }
    }

    private static void handleHorizontalDirection(Point start, Point direction, String[][] mx, String[][] mxCache) {
        if (direction.equals(haut) || direction.equals(bas)) {
            if (verifMx(start, droite, mxCache))
                pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + droite.x, start.y + droite.y), droite));
            if (verifMx(start, gauche, mxCache))
                pointsToVisit.offer(new Pair<Point, Point>(new Point(start.x + gauche.x, start.y + gauche.y), gauche));
        } else if (verifMx(start, direction, mxCache)) {
            pointsToVisit
                    .offer(new Pair<Point, Point>(new Point(start.x + direction.x, start.y + direction.y), direction));
        }
    }

}
