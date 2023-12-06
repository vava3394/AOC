package _2023.Day6;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import outils.number.MyNumber;
import outils.string.MyString;

/**
 * author vportal
 * 
 */
public class Day6 {

    public static void main(String[] args) {
        Scanner in;
        try {
            in = new Scanner(new File("_2023\\Day6\\input.txt"));

            ArrayList<String> lines = new ArrayList<String>();
            int solution1 = 0;
            int solution2 = 0;
            String line = "";

            while (in.hasNextLine()) {
                line = in.nextLine();
                lines.add(line);
            }

            solution1 = part1(lines);
            solution2 = part2(lines);

            System.out.println("solution 1 : " + solution1);
            System.out.println("solution 2 : " + solution2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int part1(List<String> input) {
        List<Integer> listTime = MyNumber.extractIntegersToList(input.get(0));
        List<Integer> listDistance = MyNumber.extractIntegersToList(input.get(1));
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < listTime.size(); i++) {
            int time = listTime.get(i);
            int distance = listDistance.get(i);
            int nbWin = 0;
            for (int j = 0; j < time; j++) {
                if (j * (time - j) > distance) {
                    nbWin++;
                }
            }
            res.add(nbWin);
        }

        return res.stream().reduce(1, (a, b) -> a * b);
    }

    public static int part2(List<String> input) {
        List<Long> listTime = MyNumber.extractLongToList(MyString.clearSpace(input.get(0)));
        List<Long> listDistance = MyNumber
                .extractLongToList(MyString.clearSpace(input.get(1)));
        int nbWin = 0;
        for (int i = 0; i < listTime.size(); i++) {
            Long time = listTime.get(i);
            Long distance = listDistance.get(i);

            for (int j = 0; j < time; j++) {
                if (j * (time - j) > distance) {
                    nbWin++;
                }
            }
        }

        return nbWin;
    }
}
