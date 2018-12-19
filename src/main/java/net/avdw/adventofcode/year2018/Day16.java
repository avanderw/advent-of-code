package net.avdw.adventofcode.year2018;

import org.apache.commons.lang3.ArrayUtils;
import org.pmw.tinylog.Configuration;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {
    static Integer[] register = new Integer[]{0, 0, 0, 0};

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        Configurator.defaultConfig().level(Level.DEBUG).formatPattern("{level}:\t{message}").activate();

        Map<String, Instruction> instructions = new HashMap<>();
        instructions.put("addr", new RRInstruction(add));
        instructions.put("addi", new RIInstruction(add));
        instructions.put("mulr", new RRInstruction(mul));
        instructions.put("muli", new RIInstruction(mul));
        instructions.put("banr", new RRInstruction(ban));
        instructions.put("bani", new RIInstruction(ban));
        instructions.put("borr", new RRInstruction(bor));
        instructions.put("bori", new RIInstruction(bor));
        instructions.put("setr", new RRInstruction(set));
        instructions.put("seti", new IRInstruction(set));
        instructions.put("gtir", new IRInstruction(gt));
        instructions.put("gtri", new RIInstruction(gt));
        instructions.put("gtrr", new RRInstruction(gt));
        instructions.put("eqir", new IRInstruction(eq));
        instructions.put("eqri", new RIInstruction(eq));
        instructions.put("eqrr", new RRInstruction(eq));

        Logger.debug("testing configuration");
        Sample testSample = new Sample();
        testSample.before = new Integer[]{3, 2, 1, 1};
        testSample.after = new Integer[]{3, 2, 2, 1};
        testSample.opcode = 9;
        testSample.A = 2;
        testSample.B = 1;
        testSample.C = 2;
        Logger.trace("sample {}", testSample);
        applyInstructionsToSample(instructions, testSample);
        Set<Map.Entry<String, Instruction>> testIntructions = instructions.entrySet().stream().filter(e -> e.getValue().opcodes.contains(testSample.opcode)).collect(Collectors.toSet());
        Logger.debug("total of {} matched instructions {}", testIntructions.size(), testIntructions);
        if (testIntructions.size() != 3) {
            Logger.error("error in configuration");
            return;
        }
        Logger.debug("configuration passed");
        instructions.values().forEach(i -> i.opcodes.clear());

        List<Sample> program = new ArrayList<>();
        Set<Sample> samples = new HashSet<>();
        Pattern linePattern = Pattern.compile("[A-Za-z]+:\\s+\\[(\\d)+, (\\d)+, (\\d)+, (\\d)+\\]");
        URL inputUrl = Day16.class.getResource("day16-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Logger.trace("reading {}",line);
            if (line.startsWith("Before:")) {
                Sample sample = new Sample();
                Matcher lineMatcher = linePattern.matcher(line);
                if (lineMatcher.matches()) {
                    sample.before[0] = Integer.parseInt(lineMatcher.group(1));
                    sample.before[1] = Integer.parseInt(lineMatcher.group(2));
                    sample.before[2] = Integer.parseInt(lineMatcher.group(3));
                    sample.before[3] = Integer.parseInt(lineMatcher.group(4));
                }
                sample.opcode = (scanner.nextInt());
                sample.A = (scanner.nextInt());
                sample.B = (scanner.nextInt());
                sample.C = (scanner.nextInt());
                scanner.nextLine();

                line = scanner.nextLine();
                lineMatcher = linePattern.matcher(line);
                if (lineMatcher.matches()) {
                    sample.after[0] = Integer.parseInt(lineMatcher.group(1));
                    sample.after[1] = Integer.parseInt(lineMatcher.group(2));
                    sample.after[2] = Integer.parseInt(lineMatcher.group(3));
                    sample.after[3] = Integer.parseInt(lineMatcher.group(4));
                }
                samples.add(sample);
                Logger.trace("added sample {}", sample);
                scanner.nextLine();
            } else if (line.length() != 0){
                Logger.trace("program code {}", line);
                Scanner lineScanner = new Scanner(line);
                Sample sample = new Sample();
                sample.opcode = lineScanner.nextInt();
                sample.A = lineScanner.nextInt();
                sample.B = lineScanner.nextInt();
                sample.C = lineScanner.nextInt();
                program.add(sample);
            }
        }
        Logger.debug("{} samples collected", samples.size());

        for (Sample sample : samples) {
            Logger.trace("sample {}", sample);
            applyInstructionsToSample(instructions, sample);
        }
        Logger.debug("tested instructions against samples");

        List<Sample> sampleSelection = samples.stream().filter(s -> s.instructions.size() >= 3).collect(Collectors.toList());
        Logger.info("there are {} samples with more than 3 opcodes assigned", sampleSelection.size());

        while (instructions.values().stream().anyMatch(i -> i.opcodes.size() > 1)) {
            instructions.values().stream().filter(i -> i.opcodes.size() == 1).forEach(i -> {
                instructions.values().stream().filter(in -> in != i).forEach(in -> {
                    in.opcodes.remove(i.opcodes.iterator().next());
                });
            });
        }
        if (instructions.values().stream().anyMatch(i -> i.opcodes.size() < 1)) {
            Logger.error("removed too many opcodes from an instruction");
        }

        Map<Integer, String> opcodeMap = new HashMap<>();
        instructions.entrySet().stream().forEach(e->{
            opcodeMap.put(e.getValue().opcodes.iterator().next(), e.getKey());
        });
        register = new Integer[]{0,0,0,0};
        program.forEach(s-> {
            Logger.trace("register {} {} [{}] = {} {} ({})", Arrays.toString(register), opcodeMap.get(s.opcode), s.C, s.A, s.B, String.format("%s %s %s %s", s.opcode, s.A, s.B, s.C));
            instructions.get(opcodeMap.get(s.opcode)).apply(s.A, s.B, s.C);
        });
        Logger.info("register after exeucting the program {}", Arrays.toString(register));
    }

    private static void applyInstructionsToSample(Map<String, Instruction> instructions, Sample sample) {
        for (Map.Entry<String, Instruction> instruction : instructions.entrySet()) {
            register = ArrayUtils.clone(sample.before);
            instruction.getValue().apply(sample.A, sample.B, sample.C);

            boolean isEqual = true;
            for (int i = 0; i < register.length; i++) {
                if (isEqual) {
                    isEqual = register[i].equals(sample.after[i]);
                }
            }

            if (isEqual) {
                Logger.trace("after applying {} => {} #MATCH: adding opcode {} to instruction {}", instruction.getKey(), Arrays.toString(register), sample.opcode, instruction.getKey());
                instruction.getValue().opcodes.add(sample.opcode);
                sample.instructions.add(instruction.getKey());
            } else {
                Logger.trace("after applying {} => {}", instruction.getKey(), Arrays.toString(register));
            }
        }
    }

    static class Sample {
        Integer[] before = new Integer[]{0, 0, 0, 0};
        Integer[] after = new Integer[]{0, 0, 0, 0};
        int opcode, A, B, C;
        Set<String> instructions = new HashSet<>();

        @Override
        public String toString() {
            return String.format("{b%s => (%s %s %s %s) => a%s : m%s}", Arrays.toString(before), opcode, A, B, C, Arrays.toString(after), instructions);
        }
    }

    static BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
    static BiFunction<Integer, Integer, Integer> mul = (a, b) -> a * b;
    static BiFunction<Integer, Integer, Integer> ban = (a, b) -> a & b;
    static BiFunction<Integer, Integer, Integer> bor = (a, b) -> a | b;
    static BiFunction<Integer, Integer, Integer> set = (a, b) -> a;
    static BiFunction<Integer, Integer, Integer> gt = (a, b) -> a > b ? 1 : 0;
    static BiFunction<Integer, Integer, Integer> eq = (a, b) -> a.equals(b) ? 1 : 0;

    static class IRInstruction extends Instruction {
        IRInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            register[C] = function.apply(A, register[B]);
        }
    }

    static class RIInstruction extends Instruction {
        RIInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            register[C] = function.apply(register[A], B);
        }
    }

    static class RRInstruction extends Instruction {
        RRInstruction(BiFunction<Integer, Integer, Integer> function) {
            super(function);
        }

        @Override
        void apply(int A, int B, int C) {
            Logger.trace("before: r{}[{}] = r{}[{}] op r{}[{}]", C, register[C], A, register[A], B, register[B]);
            register[C] = function.apply(register[A], register[B]);
            Logger.trace("after:  r{}[{}] = r{}[{}] op r{}[{}]", C, register[C], A, register[A], B, register[B]);
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
}
