package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day08 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day08.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        List<Instruction> originalProgram = new ArrayList<>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String op = line.substring(0, 3);
            Integer value = Integer.parseInt(line.substring(4));
            originalProgram.add(new Instruction(op, value));
        }

        int acc = -1;
        int lastIdxChange = 0;
        boolean repeat = true;
        while (repeat) {
            List<Instruction> program = new ArrayList<>(originalProgram);
            boolean changed = false;
            while (!changed) {
                Instruction instruction = program.get(lastIdxChange);
                switch (instruction.op) {
                    case "nop":
                        program.set(lastIdxChange, new Instruction("jmp", instruction.value));
                        changed = true;
                        break;
                    case "jmp":
                        program.set(lastIdxChange, new Instruction("nop", instruction.value));
                        changed = true;
                        break;
                    case "acc":
                        changed = false;
                        break;
                    default:
                        throw new UnsupportedOperationException(instruction.op);
                }
                lastIdxChange++;
            }

            repeat = false;
            acc = 0;
            int idx = 0;
            List<Instruction> visited = new ArrayList<>();
            while (idx < program.size()) {
                Instruction instruction = program.get(idx);
                if (visited.contains(instruction)) { // loop detected
                    repeat = true;
                    idx = program.size();
                    continue;
                }
                visited.add(instruction);
                switch (instruction.op) {
                    case "nop":
                        idx++;
                        break;
                    case "acc":
                        acc += instruction.value;
                        idx++;
                        break;
                    case "jmp":
                        idx += instruction.value;
                        break;
                    default:
                        throw new UnsupportedOperationException(instruction.op);
                }
            }
        }
        System.out.println(acc);
    }

    private static class Instruction {
        private final String op;
        private final Integer value;

        public Instruction(final String op, final Integer value) {
            this.op = op;
            this.value = value;
        }
    }
}
