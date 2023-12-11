package _2023.Day10;

import java.io.File;
import java.nio.channels.Pipe;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.text.Position;

/**
 * author vportal
 * 
 */
public class Day10 {
    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public enum Pipe {
        NS, EW, NE, NW, SE, SW, Start, Empty
    }

    public enum Position {
        Top, Right, Bottom, Left
    }

    public static class Map {
        private List<List<Pipe>> map;

        public Map(List<List<Pipe>> map) {
            this.map = map;
        }

        public List<Pipe> get(int index) {
            return map.get(index);
        }

        public int size() {
            return map.size();
        }

        @Override
        public String toString() {
            String text = "";
            for (List<Pipe> list : map) {
                for (Pipe pipe : list) {
                    text += pipe.name() + " ";
                }
                text += "\n";
            }
            return text;

        }
    }

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day10\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            double solution2 = 0;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }

            solution1 = part1(lines.toArray(new String[0]));
            solution2 = part2(lines);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map convertMx(List<String> lines) {
        List<List<Pipe>> pipe = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            List<Pipe> pipes = new ArrayList<>();
            for (int j = 0; j < lines.get(i).length(); j++) {
                if (lines.get(i).charAt(j) == '|') {
                    pipes.add(Pipe.NS);
                }
                if (lines.get(i).charAt(j) == '-') {
                    pipes.add(Pipe.EW);
                }
                if (lines.get(i).charAt(j) == 'L') {
                    pipes.add(Pipe.NE);
                }
                if (lines.get(i).charAt(j) == 'J') {
                    pipes.add(Pipe.NW);
                }
                if (lines.get(i).charAt(j) == '7') {
                    pipes.add(Pipe.SW);
                }
                if (lines.get(i).charAt(j) == 'F') {
                    pipes.add(Pipe.SE);
                }
                if (lines.get(i).charAt(j) == '.') {
                    pipes.add(Pipe.Empty);
                }
                if (lines.get(i).charAt(j) == 'S') {
                    pipes.add(Pipe.Start);
                }
            }
            pipe.add(pipes);
        }

        return new Map(pipe);
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
            int[] down = new int[] { r + 1, c }, up = new int[] { r - 1, c },
                    right = new int[] { r, c + 1 }, left = new int[] { r, c - 1 };

            if (r > 0 && "S|JL".contains(String.valueOf(ch))
                    && "|7F".contains(String.valueOf(lines[up[0]].charAt(c)))
                    && !isContain(loop, up)) {
                loop.add(up);
                queue.add(up);
            }

            if (r < lines.length - 1 && "S|7F".contains(String.valueOf(ch))
                    && "|JL".contains(String.valueOf(lines[down[0]].charAt(c)))
                    && !isContain(loop, down)) {
                loop.add(down);
                queue.add(down);
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

    public static double part2(List<String> lines) {
        Map map = convertMx(lines);
        List<Point> loop = findLoopStarts(map, findStart(map));
        System.out.println("loop res : " + loop.size());
        double area = lacet(loop);
        double pick = Math.ceil(area - loop.size() / 2 + 1);
        return pick;
    }

    public static boolean isContain(List<int[]> input, int[] test) {
        for (int[] i : input) {
            if (i[0] == test[0] && i[1] == test[1]) {
                return true;
            }
        }
        return false;
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

    public static Point findStart(Map map) {
        Point start = null;

        for (int y = 0; y < map.size(); y++) {
            List<Pipe> line = map.get(y);
            for (int x = 0; x < line.size(); x++) {
                Pipe pipe = line.get(x);
                if (pipe == Pipe.Start) {
                    start = new Point(x, y);
                    break;
                }
            }
        }

        if (start == null) {
            throw new RuntimeException("No start found");
        }

        return start;
    }

    public static List<Point> findLoopStarts(Map map, Point start) {
        if (map.get(start.y).get(start.x) != Pipe.Start) {
            throw new RuntimeException("Start is not a start");
        }

        List<Point> loops = new ArrayList<>();

        Pipe top = map.get(start.y - 1).get(start.x);
        Pipe right = map.get(start.y).get(start.x + 1);
        Pipe bottom = map.get(start.y + 1).get(start.x);
        Pipe left = map.get(start.y).get(start.x - 1);

        if (top == Pipe.NS || top == Pipe.SE || top == Pipe.SW)
            loops.add(new Point(start.x, start.y - 1));

        if (right == Pipe.EW || right == Pipe.NW || right == Pipe.SW)
            loops.add(new Point(start.x + 1, start.y));

        if (bottom == Pipe.NS || bottom == Pipe.NE || bottom == Pipe.NW)
            loops.add(new Point(start.x, start.y + 1));

        if (left == Pipe.EW || left == Pipe.NE || left == Pipe.SE)
            loops.add(new Point(start.x - 1, start.y));

        return loops;
    }

    public static Position getPreviousPosition(Point previous, Point current) {
        if (previous.x == current.x && previous.y == current.y - 1)
            return Position.Top;
        if (previous.x == current.x && previous.y == current.y + 1)
            return Position.Bottom;
        if (previous.x == current.x - 1 && previous.y == current.y)
            return Position.Left;
        if (previous.x == current.x + 1 && previous.y == current.y)
            return Position.Right;

        throw new RuntimeException("No position found");
    }

    public static Position getNextPosition(Pipe dir, Position pos) {
        if (dir == Pipe.NS) {
            if (pos == Position.Top)
                return Position.Bottom;
            if (pos == Position.Bottom)
                return Position.Top;
        }
        if (dir == Pipe.EW) {
            if (pos == Position.Left)
                return Position.Right;
            if (pos == Position.Right)
                return Position.Left;
        }
        if (dir == Pipe.NE) {
            if (pos == Position.Top)
                return Position.Right;
            if (pos == Position.Right)
                return Position.Top;
        }
        if (dir == Pipe.NW) {
            if (pos == Position.Top)
                return Position.Left;
            if (pos == Position.Left)
                return Position.Top;
        }
        if (dir == Pipe.SE) {
            if (pos == Position.Bottom)
                return Position.Right;
            if (pos == Position.Right)
                return Position.Bottom;
        }
        if (dir == Pipe.SW) {
            if (pos == Position.Bottom)
                return Position.Left;
            if (pos == Position.Left)
                return Position.Bottom;
        }

        throw new RuntimeException("No next position found");
    }

    public static Point getNextPoint(Map map, Point current, Position pos) {
        if (pos == Position.Top)
            return new Point(current.x, current.y - 1);
        if (pos == Position.Right)
            return new Point(current.x + 1, current.y);
        if (pos == Position.Bottom)
            return new Point(current.x, current.y + 1);
        if (pos == Position.Left)
            return new Point(current.x - 1, current.y);

        throw new RuntimeException("No next point found");
    }

    public static Position getNextDirection(Map map, Point previous, Point current) {
        Pipe currentPipe = map.get(current.y).get(current.x);

        Position previousPos = getPreviousPosition(previous, current);
        if (previousPos == null)
            throw new RuntimeException("No position found");

        if (currentPipe == Pipe.Empty)
            return null;

        Position nextPos = getNextPosition(currentPipe, previousPos);
        if (nextPos == null)
            throw new RuntimeException("No next position found");

        return nextPos;
    }

    public static Point getNext(Map map, Point previous, Point current) {
        Position nextPos = getNextDirection(map, previous, current);
        Point nextPoint = getNextPoint(map, current, nextPos);

        return nextPoint;
    }
}
