package net.avdw.adventofcode.year2019;

import net.avdw.adventofcode.LoggingActivator;
import org.apache.commons.lang3.StringUtils;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Day05 {
    public static void main(String[] args) throws FileNotFoundException {
        Supplier<Integer> inputStream = () -> 5;
        Consumer<Integer> outputStream = (output)->Logger.info("= {}", output);

        LoggingActivator.activate();
        Logger.getConfiguration().level(Level.INFO).activate();
        runProgram(Arrays.asList(3, 9, 8, 9, 10, 9, 4, 9, 99, -1, 8), inputStream, outputStream);
        runProgram(Arrays.asList(3, 9, 7, 9, 10, 9, 4, 9, 99, -1, 8), inputStream, outputStream);
        runProgram(Arrays.asList(3, 3, 1108, -1, 8, 3, 4, 3, 99), inputStream, outputStream);
        runProgram(Arrays.asList(3, 3, 1107, -1, 8, 3, 4, 3, 99), inputStream, outputStream);
        runProgram(Arrays.asList(3, 12, 6, 12, 15, 1, 13, 14, 13, 4, 13, 99, -1, 0, 1, 9), inputStream, outputStream);
        runProgram(Arrays.asList(3, 3, 1105, -1, 9, 1101, 0, 0, 12, 4, 12, 99, 1), inputStream, outputStream);
        runProgram(Arrays.asList(3, 21, 1008, 21, 8, 20, 1005, 20, 22, 107, 8, 21, 20, 1006, 20, 31, 1106, 0, 36, 98, 0, 0, 1002, 21, 125, 20, 4, 20, 1105, 1, 46, 104, 999, 1105, 1, 46, 1101, 1000, 1, 20, 4, 20, 1105, 1, 46, 98, 99), inputStream, outputStream);

        Logger.getConfiguration().level(Level.INFO).activate();
        URL inputUrl = Day01.class.getResource("day05.txt");
        List<Integer> memory = new ArrayList<>();
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        scanner.useDelimiter(",");
        while (scanner.hasNextInt()) {
            memory.add(scanner.nextInt());
        }
        scanner.close();

        runProgram(memory, inputStream, outputStream);
    }

    private static void runProgram(List<Integer> memory, Supplier<Integer> inputStream, Consumer<Integer> outputStream) {
        Logger.info("Executing program: {}", memory);

        ip = 0;
        mem = new Integer[memory.size()];
        memory.toArray(mem);

        OpCodeType opCodeType = OpCodeType.BEGIN;
        while (opCodeType != OpCodeType.EXIT) {
            Integer instruction = mem[ip];
            int mode = Integer.parseInt(StringUtils.leftPad("" + instruction, 5,"0").substring(0, 3));
            Logger.debug("INSTRUCTION: {}", instruction);
            opCodeType = OpCodeType.get(instruction % 100);

            Supplier<Integer> a;
            Supplier<Integer> b;
            int mode1Mask = 0b1;
            int mode2Mask = 0b10;
            int mode3Mask = 0b100;
            switch (opCodeType) {
                case ADD:
                    Logger.debug("ADD: {} + {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.debug("ADD: {} + {}", a.get(), b.get());
                    mem[mem[ip + 3]] = add.apply(a, b).get();
                    Logger.debug("ADD: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case MULTIPLY:
                    Logger.debug("MUL: {} * {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.debug("MUL: {} + {}", a.get(), b.get());
                    mem[mem[ip + 3]] = mul.apply(a, b).get();
                    ip += 4;
                    break;
                case IN:
                    a = () -> mem[ip + 1];
                    Logger.debug("IN: {} => [{}]", inputStream.get(), a.get());
                    in.accept(a, inputStream);
                    Logger.debug("IN: [{}] = {}", a.get(), mem[mem[ip + 1]]);
                    ip += 2;
                    break;
                case OUT:
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    Logger.debug("OUT: [{}] => print", a.get());
                    out.accept(a, outputStream);
                    ip += 2;
                    break;
                case JNZ:
                    Logger.trace("JNZ: ip = {}", ip);
                    Logger.debug("JNZ: {} != 0 => [{}]", mem[ip + 1], mem[ip + 2]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.debug("JNZ: {} != 0 => [{}]", a.get(), b.get());
                    jnz.accept(a, b);
                    Logger.trace(Arrays.toString(mem));
                    Logger.trace("JNZ: ip = {}", ip);
                    break;
                case JZ:
                    Logger.trace("JZ: ip = {}", ip);
                    Logger.debug("JZ: {} == 0 => [{}]", mem[ip + 1], mem[ip + 2]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.debug("JZ: {} == 0 => [{}]", a.get(), b.get());
                    jz.accept(a, b);
                    Logger.trace(Arrays.toString(mem));
                    Logger.trace("JZ: ip = {}", ip);
                    break;
                case LT:
                    Logger.debug("LT: {} < {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.debug("LT: {} < {}", a.get(), b.get());
                    Logger.trace(Arrays.toString(mem));
                    mem[mem[ip + 3]] = lt.apply(a, b).get();
                    Logger.debug("LT: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case EQ:
                    Logger.debug("EQ: {} == {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.debug("EQ: {} == {}", a.get(), b.get());
                    Logger.trace(Arrays.toString(mem));
                    mem[mem[ip + 3]] = eq.apply(a, b).get();
                    Logger.debug("EQ: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case EXIT:
                case BEGIN:
                    break;
            }
        }
    }

    private static int ip;
    private static Integer[] mem;
    private static BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> add = (a, b) -> () -> a.get() + b.get();
    private static BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> mul = (a, b) -> () -> a.get() * b.get();
    private static BiConsumer<Supplier<Integer>, Supplier<Integer>> jnz = (test, jump) -> {
        if (test.get() != 0) {
            ip = jump.get();
        } else {
            ip += 3;
        }
    };
    private static BiConsumer<Supplier<Integer>, Supplier<Integer>> jz = (test, jump) -> {
        if (test.get() == 0) {
            ip = jump.get();
        } else {
            ip += 3;
        }
    };
    private static BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> lt = (a, b) -> () -> a.get() < b.get() ? 1 : 0;
    private static BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> eq = (a, b) -> () -> a.get().equals(b.get()) ? 1 : 0;
    private static BiConsumer<Supplier<Integer>, Supplier<Integer>> in = (address, input) -> mem[address.get()] = input.get();
    private static BiConsumer<Supplier<Integer>, Consumer<Integer>> out = (address, output) -> output.accept(address.get());

    enum OpCodeType {
        ADD, MULTIPLY, IN, OUT, JNZ, JZ, LT, EQ, EXIT, BEGIN;

        static OpCodeType get(int code) {
            switch (code) {
                case 1:
                    return ADD;
                case 2:
                    return MULTIPLY;
                case 3:
                    return IN;
                case 4:
                    return OUT;
                case 5:
                    return JNZ;
                case 6:
                    return JZ;
                case 7:
                    return LT;
                case 8:
                    return EQ;
                case 99:
                    return EXIT;
                default:
                    Logger.error("Unknown code: {}", code);
                    throw new UnsupportedOperationException();
            }
        }
    }
}
