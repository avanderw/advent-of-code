package net.avdw.adventofcode.year2021;

import lombok.SneakyThrows;
import net.avdw.adventofcode.Day;
import net.avdw.adventofcode.Runner;

import java.util.*;

public class Day10 extends Day {
    public static void main(String[] args) {
        Runner.run(new Day10(), 5000);
    }

    public int test01(String[] line) {
        String value = null;
        Stack<String> callStack = new Stack<>();
        for (String s : line) {
            switch (s) {
                case "(":
                case "[":
                case "{":
                case "<":
                    callStack.push(s);
                    break;
                case ")":
                    value = callStack.pop();
                    if (!value.equals("(")) {
                        return 3;
                    }
                    break;
                case "]":
                    value = callStack.pop();
                    if (!value.equals("[")) {
                        return 57;
                    }
                    break;
                case "}":
                    value = callStack.pop();
                    if (!value.equals("{")) {
                        return 1197;
                    }
                    break;
                case ">":
                    value = callStack.pop();
                    if (!value.equals("<")) {
                        return 25137;
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return 0;
    }

    @SneakyThrows
    public String part01() {
        Scanner scanner = new Scanner(getInput());

        long score = 0;
        while (scanner.hasNextLine()) {
            score += test01(scanner.nextLine().split(""));
        }

        return "" + score;
    }

    public String[] test02(String[] line) {
        String value = null;
        Stack<String> callStack = new Stack<>();
        for (String s : line) {
            switch (s) {
                case "(":
                case "[":
                case "{":
                case "<":
                    callStack.push(s);
                    break;
                case ")":
                    value = callStack.pop();
                    if (!value.equals("(")) {
                        throw new UnsupportedOperationException();
                    }
                    break;
                case "]":
                    value = callStack.pop();
                    if (!value.equals("[")) {
                        throw new UnsupportedOperationException();
                    }
                    break;
                case "}":
                    value = callStack.pop();
                    if (!value.equals("{")) {
                        throw new UnsupportedOperationException();
                    }
                    break;
                case ">":
                    value = callStack.pop();
                    if (!value.equals("<")) {
                        throw new UnsupportedOperationException();
                    }
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        String[] completion = new String[callStack.size()];
        for (int i = 0; i < completion.length; i++) {
            completion[i] = callStack.pop();
        }
        return completion;
    }

    @SneakyThrows
    public String part02() {
        Scanner scanner = new Scanner(getInput());

        List<Long> scores = new ArrayList<>();
        while (scanner.hasNextLine()) {
            try {
                scores.add(score(test02(scanner.nextLine().split(""))));
            } catch (UnsupportedOperationException ignore) {
            }
        }

        Collections.sort(scores);
        return "" + scores.get(scores.size() / 2);
    }

    private long score(String[] completion) {
        long score = 0;
        for (String s : completion) {
            score *= 5;
            switch (s) {
                case "(":
                    score += 1;
                    break;
                case "[":
                    score += 2;
                    break;
                case "{":
                    score += 3;
                    break;
                case "<":
                    score += 4;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }
        return score;
    }
}
