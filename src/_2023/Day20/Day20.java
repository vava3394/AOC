package _2023.Day20;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.pair.Pair;

/**
 * author vportal
 * 
 */
public class Day20 {

    static final String BROAD = "broadcaster";
    static final String FLIP = "%";
    static final String CONJ = "&";

    static class Interruptueur {
        String name;
        boolean memorieSignal;
        Boolean isFlipFlop;
        List<String> memoriesEntres;
        List<String> res;

        public Interruptueur(String name, Boolean isFlipFlop, List<String> res) {
            this.name = name;
            this.res = res;
            this.isFlipFlop = isFlipFlop;
            this.memoriesEntres = new ArrayList<>();
            this.memorieSignal = false;
        }

        @Override
        public String toString() {
            return "" + (isFlipFlop != null ? (isFlipFlop ? FLIP : CONJ) : BROAD) + name
                    + "->" + res + "memoriseSignal : " + memorieSignal + " , memorieEntre : " + memoriesEntres;
        }
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("_2023\\Day20\\input.txt"))) {
            List<String> lines = new ArrayList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                lines.add(line);
            }

            createInterrupteurs(lines);

            Long solution1 = 0l;
            Long solution2 = 0l;
            double startTime = System.currentTimeMillis();
            solution1 = part1();
            double endTime = System.currentTimeMillis();
            System.out.println("solution 1: " + solution1 + " Temps: " + ((endTime -
                    startTime) / 1000) + "s");

            startTime = System.currentTimeMillis();
            solution2 = part2(lines);
            endTime = System.currentTimeMillis();
            System.out.println("solution 2: " + solution2 + " Temps: " + ((endTime -
                    startTime) / 1000) + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static HashMap<String, Interruptueur> interruptueurs = new HashMap<>();
    static Long lower = 0l;
    static Long upper = 0l;
    static Queue<Pair<String, Boolean>> file = new LinkedList<>();

    private static Long part1() {
        for (int i = 0; i < 1000; i++) {
            file.add(new Pair<>(BROAD, false));

            while (!file.isEmpty()) {
                Pair<String, Boolean> pair = file.poll();
                transmission(pair.getFirst(), pair.getSecond());
            }
        }

        return lower * upper;
    }

    private static Long part2(List<String> lines) {
        interruptueurs = new HashMap<>();
        createInterrupteurs(lines);
        Long res = 0l;
        while (true) {
            file.add(new Pair<>(BROAD, false));

            while (!file.isEmpty()) {
                Pair<String, Boolean> pair = file.poll();
                if (pair.getFirst().equals("rx") && !pair.getSecond()) {
                    return res;
                }

                transmission(pair.getFirst(), pair.getSecond());
                res++;
            }
        }
    }

    private static void transmission(String name, Boolean isUpper) {

        if (isUpper) {
            upper++;
        } else {
            lower++;
        }
        if (interruptueurs.containsKey(name)) {
            Interruptueur interruptueur = interruptueurs.get(name);
            if (null != interruptueur.isFlipFlop) {
                if (interruptueur.isFlipFlop) {
                    if (!isUpper) {
                        interruptueur.memorieSignal = !interruptueur.memorieSignal;
                        for (String newInterrup : interruptueur.res) {
                            file.offer(new Pair<String, Boolean>(newInterrup, interruptueur.memorieSignal));
                        }

                    }
                } else {
                    boolean isIdentique = true;
                    for (String nameMemorie : interruptueur.memoriesEntres) {
                        Interruptueur entreeMemorie = interruptueurs.get(nameMemorie);
                        if (!entreeMemorie.memorieSignal) {
                            isIdentique = false;
                            interruptueur.memorieSignal = true;
                            for (String newInterrup : interruptueur.res) {
                                file.offer(new Pair<String, Boolean>(newInterrup, interruptueur.memorieSignal));
                            }
                            break;
                        }
                    }
                    if (isIdentique) {
                        interruptueur.memorieSignal = false;
                        for (String newInterrup : interruptueur.res) {
                            file.offer(new Pair<String, Boolean>(newInterrup, interruptueur.memorieSignal));
                        }
                    }
                }
            } else {
                for (String newInterrup : interruptueur.res) {
                    file.offer(new Pair<String, Boolean>(newInterrup, isUpper));
                }
            }
        }
    }

    private static void createInterrupteurs(List<String> lines) {
        for (String stringInter : lines) {
            Pattern pattern = Pattern.compile("(.*)\\s->\\s(.*)");
            Matcher matcher = pattern.matcher(stringInter);

            while (matcher.find()) {
                Boolean isFlipFlop = matcher.group(1).equals(BROAD) ? null
                        : matcher.group(1).contains(FLIP) ? true : false;
                List<String> resList = List.of(matcher.group(2).split(", "));
                interruptueurs.put(isFlipFlop != null ? matcher.group(1).substring(1) : matcher.group(1),
                        new Interruptueur(matcher.group(1).substring(1), isFlipFlop, resList));
            }
        }
        for (Entry<String, Interruptueur> hashInterrup : interruptueurs.entrySet()) {

            for (String nameRes : hashInterrup.getValue().res) {
                if (interruptueurs.containsKey(nameRes)) {
                    interruptueurs.get(nameRes).memoriesEntres.add(hashInterrup.getKey());
                }

            }
        }
    }
}
