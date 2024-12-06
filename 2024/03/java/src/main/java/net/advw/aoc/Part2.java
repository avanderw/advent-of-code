package net.advw.aoc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part2 {
    private static class Instruction implements Comparable<Instruction> {
        int index;
        String type;

        Instruction(int index, String type) {
            this.index = index;
            this.type = type;
        }

        @Override
        public int compareTo(Instruction other) {
            return Integer.compare(this.index, other.index);
        }

        @Override
        public String toString() {
            return type + " at " + index;
        }
    }

    public static String solve(String input) {
        int sum = 0;
        ArrayList<Instruction> instructions = new ArrayList<>();

        // Find all do/don't instructions and their positions
        Pattern doPattern = Pattern.compile("do\\(\\)");
        Matcher doMatcher = doPattern.matcher(input);
        while (doMatcher.find()) {
            instructions.add(new Instruction(doMatcher.start(), "do"));
        }

        Pattern dontPattern = Pattern.compile("don't\\(\\)");
        Matcher dontMatcher = dontPattern.matcher(input);
        while (dontMatcher.find()) {
            instructions.add(new Instruction(dontMatcher.start(), "dont"));
        }

        // Sort instructions by position
        Collections.sort(instructions);

        // Process mul instructions
        Pattern mulPattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
        Matcher mulMatcher = mulPattern.matcher(input);

        while (mulMatcher.find()) {
            int mulPos = mulMatcher.start();
            boolean enabled = true;  // default state
            String lastInstructionType = "none";

            // Find last instruction before this mul
            for (int i = instructions.size() - 1; i >= 0; i--) {
                Instruction inst = instructions.get(i);
                if (inst.index < mulPos) {
                    enabled = inst.type.equals("do");
                    lastInstructionType = inst.type;
                    break;
                }
            }

            if (enabled) {
                int x = Integer.parseInt(mulMatcher.group(1));
                int y = Integer.parseInt(mulMatcher.group(2));
                int product = x * y;
                sum += product;
            }
        }

        return String.valueOf(sum);
    }
}
