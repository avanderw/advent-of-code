package net.avdw.adventofcode.year2018;

import java.util.*;
import java.util.function.BiFunction;

public class Day21 {

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
        cheat();


        registers = new Integer[]{0, 0, 0, 0, 0, 0};
        ip = 2;
        List<Execute> program = new ArrayList<>();
        program.add(new Execute("seti", 123, 0, 5));
        program.add(new Execute("bani", 5, 456, 5));
        program.add(new Execute("eqri", 5, 72, 5));
        program.add(new Execute("addr", 5, 2, 2));
        program.add(new Execute("seti", 0, 0, 2));
        program.add(new Execute("seti", 0, 4, 5));
        program.add(new Execute("bori", 5, 65536, 4));
        program.add(new Execute("seti", 15466939, 9, 5));
        program.add(new Execute("bani", 4, 255, 3));
        program.add(new Execute("addr", 5, 3, 5));
        program.add(new Execute("bani", 5, 16777215, 5));
        program.add(new Execute("muli", 5, 65899, 5));
        program.add(new Execute("bani", 5, 16777215, 5));
        program.add(new Execute("gtir", 256, 4, 3));
        program.add(new Execute("addr", 3, 2, 2));
        program.add(new Execute("addi", 2, 1, 2));
        program.add(new Execute("seti", 27, 8, 2));
        program.add(new Execute("seti", 0, 7, 3));
        program.add(new Execute("addi", 3, 1, 1));
        program.add(new Execute("muli", 1, 256, 1));
        program.add(new Execute("gtrr", 1, 4, 1));
        program.add(new Execute("addr", 1, 2, 2));
        program.add(new Execute("addi", 2, 1, 2));
        program.add(new Execute("seti", 25, 2, 2));
        program.add(new Execute("addi", 3, 1, 3));
        program.add(new Execute("seti", 17, 7, 2));
        program.add(new Execute("setr", 3, 7, 4));
        program.add(new Execute("seti", 7, 3, 2));
        program.add(new Execute("eqrr", 5, 0, 3));
        program.add(new Execute("addr", 3, 2, 2));
        program.add(new Execute("seti", 5, 9, 2));

        int count = 0;
        for (Execute execute : program) {
            System.out.print(String.format("ip=%-5s", count));
            printExecute(execute);
            count++;
        }

        System.out.println();
        System.out.println();

        boolean checked = false;
        boolean modified = false;
        boolean secondModified = false;
        count = 0;
        while (count < 200) {//(registers[ip] < program.size()) {
            if (registers[ip] == 28) {
                System.out.println("check");
                checked = true;
            }
            if (registers[ip] == 24 && !modified) {
                System.out.println("first modify");
            }
            if (registers[ip] == 24 && modified && !secondModified && checked) {
                System.out.println("second modify");
            }
            Execute execute = program.get(registers[ip]);
            System.out.print(String.format("ip=%-5s %-40s ", registers[ip], Arrays.toString(registers)));
            if (registers[ip] == 24 && !modified) {
                registers[3] = 256;
                modified = true;
            } else if (registers[ip] == 24 && modified && !secondModified && checked) {
                registers[3] = 61253;
                secondModified = true;
            } else {
                    instructions.get(execute.instruction).apply(execute.A, execute.B, execute.C);
            }
            System.out.print(String.format("%-20s %-40s ", execute, Arrays.toString(registers)));
            registers[ip]++;
            count++;

            printExecute(execute);
        }
    }

    private static int cheatF(int a) {
        a |= 0x10000;
        int b = 15466939;
        b += a&0xff;       b &= 0xffffff;
        b *= 65899;        b &= 0xffffff;
        b += (a>>8)&0xff;  b &= 0xffffff;
        b *= 65899;        b &= 0xffffff;
        b += (a>>16)&0xff; b &= 0xffffff;
        b *= 65899;        b &= 0xffffff;
        return b;
    }

    private static void cheat() {
        List<Integer> seen = new LinkedList<>();
        int n = cheatF(0);
        System.out.println(String.format("%s", n));
        while (true) {
            int n2 = cheatF(n);
            if (seen.contains(n2)) {
                break;
            }
            seen.add(n);
            n = n2;
        }
        System.out.println(String.format("%s", n));
    }

    private static void printExecute(Execute execute) {
        String a = "X", b = "X", c;
        c = String.format("R%s", execute.C);
        if (RRInstruction.class.isInstance(instructions.get(execute.instruction))) {
            a = String.format("R%s", execute.A);
            b = String.format("R%s", execute.B);
        } else if (RIInstruction.class.isInstance(instructions.get(execute.instruction))) {
            a = String.format("R%s", execute.A);
            b = String.format("%s", execute.B);
        } else if (IRInstruction.class.isInstance(instructions.get(execute.instruction))) {
            a = String.format("%s", execute.A);
            b = String.format("R%s", execute.B);
        } else if (IInstruction.class.isInstance(instructions.get(execute.instruction))) {
            a = String.format("%s", execute.A);
        } else if (RInstruction.class.isInstance(instructions.get(execute.instruction))) {
            a = String.format("R%s", execute.A);
        }

        if (instructions.get(execute.instruction).function == add) {
            System.out.println(String.format("%15s = %s + %s", c, a, b));
        } else if (instructions.get(execute.instruction).function == mul) {
            System.out.println(String.format("%15s = %s * %s", c, a, b));
        } else if (instructions.get(execute.instruction).function == ban) {
            System.out.println(String.format("%15s = %s & %s", c, a, b));
        } else if (instructions.get(execute.instruction).function == bor) {
            System.out.println(String.format("%15s = %s | %s", c, a, b));
        } else if (instructions.get(execute.instruction).function == set) {
            System.out.println(String.format("%15s = %s", c, a));
        } else if (instructions.get(execute.instruction).function == gt) {
            System.out.println(String.format("%15s = %s > %s ? 1 : 0", c, a, b));
        } else if (instructions.get(execute.instruction).function == eq) {
            System.out.println(String.format("%15s = %s == %s ? 1 : 0", c, a, b));
        }
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
