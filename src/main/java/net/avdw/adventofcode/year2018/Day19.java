package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.function.BiFunction;

public class Day19 {
    static Integer ip;
    static Integer[] registers;

    static BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
    static BiFunction<Integer, Integer, Integer> mul = (a, b) -> a * b;
    static BiFunction<Integer, Integer, Integer> ban = (a, b) -> a & b;
    static BiFunction<Integer, Integer, Integer> bor = (a, b) -> a | b;
    static BiFunction<Integer, Integer, Integer> set = (a, b) -> a;
    static BiFunction<Integer, Integer, Integer> gt = (a, b) -> a > b ? 1 : 0;
    static BiFunction<Integer, Integer, Integer> eq = (a, b) -> a.equals(b) ? 1 : 0;

    static Map<String, Instruction> instructions = new HashMap<>();

    static void setup() {
        instructions.put("addr", new RRInstruction(add));
        instructions.put("addi", new RIInstruction(add));
        instructions.put("mulr", new RRInstruction(mul));
        instructions.put("muli", new RIInstruction(mul));
        instructions.put("banr", new RRInstruction(ban));
        instructions.put("bani", new RIInstruction(ban));
        instructions.put("borr", new RRInstruction(bor));
        instructions.put("bori", new RIInstruction(bor));
        instructions.put("setr", new RInstruction(set));
        instructions.put("seti", new IInstruction(set));
        instructions.put("gtir", new IRInstruction(gt));
        instructions.put("gtri", new RIInstruction(gt));
        instructions.put("gtrr", new RRInstruction(gt));
        instructions.put("eqir", new IRInstruction(eq));
        instructions.put("eqri", new RIInstruction(eq));
        instructions.put("eqrr", new RRInstruction(eq));
    }

    public static void main(String[] args) {
        setup();

        ip = 0;
        registers = new Integer[]{0, 0, 0, 0, 0, 0};
        List<Execute> program = new ArrayList<>();
        program.add(new Execute("seti", 5, 0, 1));
        program.add(new Execute("seti", 6, 0, 2));
        program.add(new Execute("addi", 0, 1, 0));
        program.add(new Execute("addr", 1, 2, 3));
        program.add(new Execute("setr", 1, 0, 0));
        program.add(new Execute("seti", 8, 0, 4));
        program.add(new Execute("seti", 9, 0, 5));
        while (registers[ip] < program.size()) {
            System.out.print(String.format("ip=%s %s ", registers[ip], Arrays.toString(registers)));
            Execute execute = program.get(registers[ip]);

            instructions.get(execute.instruction).apply(execute.A, execute.B, execute.C);
            System.out.println(String.format("%s %s", execute, Arrays.toString(registers)));
            registers[ip]++;
        }

        if (Arrays.equals(registers, new Integer[]{7, 5, 6, 0, 0, 9})) {
            System.out.println("setup cleared");
            System.out.println();
        } else {
            System.out.println(String.format("registers = %s", Arrays.toString(registers)));
            throw new RuntimeException();
        }

        ip = 3;
        registers = new Integer[]{0, 0, 0, 0, 0, 0};
        program = read();
        while (registers[ip] < program.size()) {
            Execute execute = program.get(registers[ip]);

//            System.out.print(String.format("ip=%s %s %s ", registers[ip], Arrays.toString(registers), execute));
            instructions.get(execute.instruction).apply(execute.A, execute.B, execute.C);
//            System.out.println(String.format("%s", Arrays.toString(registers)));
            registers[ip]++;
        }

        System.out.println(String.format("registers = %s", Arrays.toString(registers)));

        // reversed engineered block below computes sum of divisibles
        int s = 0;
        int a = 10551424;
        for (int b = 1; b <= a; b++) {
            if (a%b ==0) {
                s += b;
            }
        }
        System.out.println(String.format("sum of factors for %s = %s", a, s));


        int count = 0;
        ip = 3;
        registers = new Integer[]{1, 0, 0, 0, 0, 0};
        program = read();
        while (registers[ip] < program.size()) {
            Execute execute = program.get(registers[ip]);

            if (count % 100_000_000 == 0) {
                System.out.print(String.format("ip=%s %s %s ", registers[ip], Arrays.toString(registers), execute));
            }
            instructions.get(execute.instruction).apply(execute.A, execute.B, execute.C);

            if (count % 100_000_000 == 0) {
                System.out.println(String.format("%s", Arrays.toString(registers)));
            }
            registers[ip]++;
            count++;
        }

        System.out.println(Arrays.toString(registers));
    }

    private static List<Execute> read() {
        List<Execute> program = new ArrayList<>();
        URL inputUrl = Day19.class.getResource("day19-input.txt");
        try {
            Scanner scanner = new Scanner(new File(inputUrl.toURI()));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("#ip")) {
                    ip = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
                } else {
                    Scanner lineScanner = new Scanner(line);
                    program.add(new Execute(lineScanner.next(), lineScanner.nextInt(), lineScanner.nextInt(), lineScanner.nextInt()));
                }
            }
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
        return program;
    }

    static class IRInstruction extends Instruction {
        IRInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            registers[C] = function.apply(A, registers[B]);
        }
    }

    static class IInstruction extends Instruction {
        IInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            registers[C] = function.apply(A, 0);
        }
    }

    static class RIInstruction extends Instruction {
        RIInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            registers[C] = function.apply(registers[A], B);
        }
    }

    static class RRInstruction extends Instruction {
        RRInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            registers[C] = function.apply(registers[A], registers[B]);
        }
    }

    static class RInstruction extends Instruction {
        RInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            registers[C] = function.apply(registers[A], 0);
        }
    }

    static abstract class Instruction {
        Set<Integer> opcodes = new HashSet<>();
        BiFunction<Integer, Integer, Integer> function;

        Instruction(BiFunction<Integer, Integer, Integer> function) {
            this.function = function;
        }

        abstract void apply(int A, int B, int C);

        @Override
        public String toString() {
            return String.format("opcodes(%s)", opcodes);
        }
    }

    private static class Execute {
        private final String instruction;
        private final int A;
        private final int B;
        private final int C;

        public Execute(String instruction, int A, int B, int C) {
            this.instruction = instruction;
            this.A = A;
            this.B = B;
            this.C = C;
        }

        @Override
        public String toString() {
            return String.format("%s %s %s %s", instruction, A, B, C);
        }
    }
}
