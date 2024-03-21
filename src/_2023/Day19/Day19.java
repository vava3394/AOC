package _2023.Day19;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import outils.number.MyNumber;
import outils.pair.Pair;

/**
 * author vportal
 * 
 */
public class Day19 {

    public static class Instruction {
        String engrennage;
        boolean isSuperieur;
        int valeur;
        String res;

        public Instruction(String engrennage, boolean isSuperieur, int valeur, String res) {
            this.engrennage = engrennage;
            this.isSuperieur = isSuperieur;
            this.valeur = valeur;
            this.res = res;
        }

        @Override
        public String toString() {
            return "{" + engrennage + (isSuperieur ? ">" : "<") + valeur + ":" + res + "}";
        }

    }

    public static class Engrennage {
        String engrennage;
        Long valeur;

        public Engrennage(String engrennage, Long valeur) {
            this.engrennage = engrennage;
            this.valeur = valeur;
        }

        @Override
        public String toString() {
            return "{" + engrennage + ":" + valeur + "}";
        }

    }

    static HashMap<String, List<Instruction>> hashInst = new HashMap<>();
    static HashMap<String, List<List<Engrennage>>> hashEng = new HashMap<>();

    static {
        hashEng.put("A", new ArrayList<>());
        hashEng.put("R", new ArrayList<>());
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("_2023\\Day19\\input.txt"))) {
            List<String> instruction = new ArrayList<>();
            List<String> engrennages = new ArrayList<>();
            while (in.hasNextLine()) {
                String line = in.nextLine();
                if (line.length() == 0) {
                    break;
                }
                instruction.add(line);
            }
            while (in.hasNextLine()) {
                engrennages.add(in.nextLine());
            }

            double startTime = System.currentTimeMillis();
            Long solution1 = part1(instruction, engrennages);
            double endTime = System.currentTimeMillis();
            System.out.println("solution 1: " + solution1 + " Temps: " + ((endTime -
                    startTime) / 1000) + "s");

            startTime = System.currentTimeMillis();
            Long solution2 = part2(instruction, engrennages);
            endTime = System.currentTimeMillis();
            System.out.println("solution 2: " + solution2 + " Temps: " + ((endTime -
                    startTime) / 1000) + "s");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Long part1(List<String> instructions, List<String> engrennage) {
        createInstruction(instructions);
        creatEngrennage(engrennage);
        calcul();

        Long res = 0l;
        for (List<Engrennage> engs : hashEng.get("A")) {
            for (Engrennage eng : engs) {
                res += eng.valeur;
            }
        }
        return res;
    }

    private static Long part2(List<String> instructions, List<String> engrennage) {
        hashInst = new HashMap<>();
        createInstruction(instructions);
        return caculeByValue(4000);
    }

    static Long resPart2 = 0l;

    private static void valueRange(List<List<Integer>> eng, String instruction) {
        List<Instruction> instructions = hashInst.get(instruction);
        List<List<Integer>> engPass = new ArrayList<>(eng);
        for (Instruction inst : instructions) {
            if (inst.engrennage == null) {
                file.offer(new Pair<>(inst.res, engPass));
            } else {
                int engIdx = -1;

                switch (inst.engrennage) {
                    case "x":
                        engIdx = 0;
                        break;
                    case "m":
                        engIdx = 1;
                        break;
                    case "a":
                        engIdx = 2;
                        break;
                    case "s":
                        engIdx = 3;
                        break;
                }
                if (engIdx != -1) {
                    int lower = eng.get(engIdx).get(0);
                    int upper = eng.get(engIdx).get(1);
                    if (inst.isSuperieur) {
                        if (lower < inst.valeur) {
                            if (upper > inst.valeur) {
                                List<List<Integer>> copie = new ArrayList<>(engPass);
                                copie.set(engIdx, List.of(inst.valeur + 1, upper));
                                file.add(new Pair<String, List<List<Integer>>>(inst.res, copie));
                                engPass.set(engIdx, List.of(lower, inst.valeur));
                            }
                        } else {
                            file.add(new Pair<String, List<List<Integer>>>(inst.res, engPass));
                        }
                    } else {
                        if (upper > inst.valeur) {
                            if (lower < inst.valeur) {
                                List<List<Integer>> copie = new ArrayList<>(engPass);
                                copie.set(engIdx, List.of(lower, inst.valeur - 1));
                                file.add(new Pair<String, List<List<Integer>>>(inst.res, copie));
                                engPass.set(engIdx, List.of(inst.valeur, upper));
                            }
                        } else {
                            file.add(new Pair<String, List<List<Integer>>>(inst.res, engPass));
                        }
                    }
                }
            }
        }
    }

    static Queue<Pair<String, List<List<Integer>>>> file = new LinkedList<>();

    private static Long caculeByValue(int value) {
        // 167409079868000
        // 167409079868000

        // 291411878080719 trop haut
        // 129128984661201
        // 188801368643554

        // 162282893419518
        // 59672383982353
        Long res = 0l;
        Long totalPosibiliti = 0l;
        List<List<Integer>> newList = new ArrayList<>();
        newList.add(List.of(1, value));
        newList.add(List.of(1, value));
        newList.add(List.of(1, value));
        newList.add(List.of(1, value));
        file.offer(new Pair<>("in", newList));
        List<List<List<Integer>>> resA = new ArrayList<>();
        while (!file.isEmpty()) {
            Pair<String, List<List<Integer>>> pair = file.poll();
            if (pair.getFirst().equals("A")) {
                List<List<Integer>> is = pair.getSecond();
                resA.add(is);
                Long possibilite = 1l;
                for (List<Integer> list : is) {
                    possibilite *= (list.get(1) - list.get(0)) + 1;
                }
                totalPosibiliti += possibilite;
            } else if (!pair.getFirst().equals("R")) {
                valueRange(pair.getSecond(), pair.getFirst());
            }
        }

        if (resA.size() > 1) {
            Queue<List<List<Integer>>> file = new LinkedList<>(resA);


            List<Integer> dejaVu = new ArrayList<>();
            for (int i = 0; i < resA.size() - 1; i++) {
                List<List<Integer>> A = resA.get(i);
                if (!dejaVu.contains(i)) {
                    for (int j = i + 1; j < resA.size(); j++) {
                        List<List<Integer>> B = resA.get(j);
                        List<List<Integer>> ajout = new ArrayList<>();
                        if ((B.get(0).get(0) >= A.get(0).get(0) &&
                                B.get(0).get(1) <= A.get(0).get(1)) &&
                                (B.get(1).get(0) >= A.get(1).get(0) &&
                                        B.get(1).get(1) <= A.get(1).get(1))
                                &&
                                (B.get(2).get(0) >= A.get(2).get(0) &&
                                        B.get(2).get(1) <= A.get(2).get(1))
                                &&
                                (B.get(3).get(0) >= A.get(3).get(0) &&
                                        B.get(3).get(1) <= A.get(3).get(1))) {
                            dejaVu.add(j);
                        } else if ((A.get(0).get(0) >= B.get(0).get(0) &&
                                A.get(0).get(1) <= B.get(0).get(1)) &&
                                (A.get(1).get(0) >= B.get(1).get(0) &&
                                        A.get(1).get(1) <= B.get(1).get(1))
                                &&
                                (A.get(2).get(0) >= B.get(2).get(0) &&
                                        A.get(2).get(1) <= B.get(2).get(1))
                                &&
                                (A.get(3).get(0) >= B.get(3).get(0) &&
                                        A.get(3).get(1) <= B.get(3).get(1))) {
                            continue;
                        } else {
                            for (int k = 0; k < 4; k++) {
                                if (B.get(k).get(0) >= A.get(k).get(0) &&
                                        B.get(k).get(1) <= A.get(k).get(1)) {
                                    ajout.add(B.get(k));
                                } else if (B.get(k).get(0) < A.get(k).get(0)) {
                                    if (B.get(k).get(1) >= A.get(k).get(0)) {
                                        if (B.get(k).get(1) <= A.get(k).get(1))
                                            ajout.add(List.of(B.get(k).get(0), A.get(k).get(0) - 1));
                                        else {
                                            ajout.add(B.get(k));
                                        }
                                    } else {
                                        ajout.add(B.get(k));
                                    }
                                } else if (B.get(k).get(0) >= A.get(k).get(1)) {
                                    ajout.add(B.get(k));
                                } else if (B.get(k).get(1) >= A.get(k).get(1)) {
                                    ajout.add(List.of(A.get(k).get(1) + 1, B.get(k).get(1)));
                                } else {
                                    ajout.add(B.get(k));
                                }
                            }
                            resA.add(ajout);
                        }

                    }
                }

            }
            System.out.println(dejaVu);
        }
        return totalPosibiliti;
    }

    // List<Integer> dejaVu = new ArrayList<>();

    // for (int i = 0; i < resA.size(); i++) {
    // if (!dejaVu.contains(i)) {
    // List<List<Integer>> vaList = resA.get(i);
    // for (int j = i + 1; j < resA.size(); j++) {
    // List<List<Integer>> test = resA.get(j);
    // Long sous = 1l;
    // for (int k = 0; k < 4; k++) {
    // if (vaList.get(k).get(0) <= test.get(k).get(0)) {
    // if (vaList.get(k).get(1) < test.get(k).get(0)) {
    // sous *= 0;
    // } else if (vaList.get(k).get(1) >= test.get(k).get(1)) {
    // sous *= (test.get(k).get(1) - test.get(k).get(0)) + 1;
    // } else {
    // sous *= (vaList.get(k).get(1) - test.get(k).get(0)) + 1;
    // }
    // } else if (vaList.get(k).get(0) <= test.get(k).get(1)) {
    // sous *= (test.get(k).get(1) - vaList.get(k).get(0)) + 1;
    // } else {
    // sous *= 0;
    // }
    // }
    // if (sous != 0) {
    // dejaVu.add(j);
    // }
    // }
    // System.out.println(vaList);
    // Long possibilite = 1l;
    // for (List<Integer> list : vaList) {
    // possibilite *= ((list.get(1) - list.get(0)) + 1);
    // }
    // res += possibilite;

    // }
    // }
    // System.out.println(dejaVu);

    private static void calcul() {
        while (verif()) {
            for (Entry<String, List<List<Engrennage>>> engrennage : hashEng.entrySet()) {
                if (!engrennage.getKey().equals("A") && !engrennage.getKey().equals("R")) {
                    List<Instruction> insts = hashInst.get(engrennage.getKey());
                    while (!engrennage.getValue().isEmpty() && insts != null) {
                        List<Engrennage> engList = engrennage.getValue().get(0);
                        boolean isPasse = false;

                        for (Instruction instruction : insts) {
                            for (Engrennage eng : engList) {
                                if (instruction.engrennage != null) {
                                    if (!eng.engrennage.equals(instruction.engrennage)) {
                                        continue;
                                    }
                                    if (instruction.isSuperieur) {
                                        if (eng.valeur > instruction.valeur) {
                                            hashEng.get(instruction.res).add(engList);
                                            hashEng.get(engrennage.getKey()).remove(engList);
                                            isPasse = true;
                                            break;
                                        }
                                    } else {
                                        if (eng.valeur < instruction.valeur) {
                                            hashEng.get(instruction.res).add(engList);
                                            hashEng.get(engrennage.getKey()).remove(engList);
                                            isPasse = true;

                                            break;
                                        }
                                    }
                                }
                            }
                            if (isPasse) {
                                break;
                            }
                        }

                        if (!isPasse) {
                            hashEng.get(insts.get(insts.size() - 1).res).add(engList);
                            hashEng.get(engrennage.getKey()).remove(engList);
                        }
                    }

                }
            }
        }
    }

    private static boolean verif() {
        for (Entry<String, List<List<Engrennage>>> engrennage : hashEng.entrySet()) {
            if (!engrennage.getValue().isEmpty()
                    && (!engrennage.getKey().equals("A") && !engrennage.getKey().equals("R"))) {
                return true;
            }
        }
        return false;
    }

    private static void createInstruction(List<String> instructions) {
        for (String string : instructions) {
            Pattern pattern = Pattern.compile("(.*)[{](.*[<>].+:.+),(.*)[}]");
            Matcher matcher = pattern.matcher(string);

            while (matcher.find()) {
                hashInst.computeIfAbsent(matcher.group(1), k -> new ArrayList<>());
                String[] insts = matcher.group(2).split(",");

                for (String inst : insts) {
                    boolean isSuperieur = inst.contains(">");
                    int index = inst.indexOf(":");
                    hashInst.get(matcher.group(1))
                            .add(new Instruction(inst.substring(0, 1), isSuperieur,
                                    MyNumber.extractIntegers(inst),
                                    inst.substring(index + 1)));
                }
                hashInst.get(matcher.group(1))
                        .add(new Instruction(null, false, 0,
                                matcher.group(3)));
                hashInst.put(matcher.group(1), Collections.unmodifiableList(hashInst.get(matcher.group(1))));
            }

        }
    }

    private static void creatEngrennage(List<String> engrennages) {
        hashEng.computeIfAbsent("in", k -> new ArrayList<>());
        for (String string : hashInst.keySet()) {
            hashEng.computeIfAbsent(string, k -> new ArrayList<>());
        }
        for (String string : engrennages) {
            Pattern pattern = Pattern.compile("[{](.*),*[}]");
            Matcher matcher = pattern.matcher(string);
            while (matcher.find()) {
                String[] engs = matcher.group(1).split(",");
                List<Engrennage> engsList = new ArrayList<>();
                for (String eng : engs) {
                    engsList.add(new Engrennage(eng.substring(0, 1), (long) MyNumber.extractIntegers(eng)));
                }
                hashEng.get("in").add(engsList);
            }
        }
    }
}
