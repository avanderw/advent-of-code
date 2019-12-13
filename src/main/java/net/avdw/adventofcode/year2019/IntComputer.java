package net.avdw.adventofcode.year2019;

import org.apache.commons.lang3.StringUtils;
import org.pmw.tinylog.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Supplier;

class IntComputer {
    private List<Integer> program;

    IntComputer(final List<Integer> program) {
        this.program = program;
    }

    void run(Supplier<Integer> inputSupplier, Consumer<Integer> outputConsumer) {
        Logger.trace("Executing program: {}", program);

        ip = 0;
        mem = new Integer[program.size()];
        program.toArray(mem);

        OpCodeType opCodeType = OpCodeType.BEGIN;
        while (opCodeType != OpCodeType.EXIT) {
            Integer instruction = mem[ip];
            int mode = Integer.parseInt(StringUtils.leftPad("" + instruction, 5, "0").substring(0, 3));
            Logger.trace("INSTRUCTION: {}", instruction);
            opCodeType = OpCodeType.get(instruction % 100);

            Supplier<Integer> a;
            Supplier<Integer> b;
            int mode1Mask = 0b1;
            int mode2Mask = 0b10;
            int mode3Mask = 0b100;
            switch (opCodeType) {
                case ADD:
                    Logger.trace("ADD: {} + {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.trace("ADD: {} + {}", a.get(), b.get());
                    mem[mem[ip + 3]] = add.apply(a, b).get();
                    Logger.trace("ADD: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case MULTIPLY:
                    Logger.trace("MUL: {} * {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.trace("MUL: {} * {}", a.get(), b.get());
                    mem[mem[ip + 3]] = mul.apply(a, b).get();
                    Logger.trace("MUL: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case IN:
                    a = () -> mem[ip + 1];
                    in.accept(a, inputSupplier);
                    Logger.trace("IN: [{}] = {}", a.get(), mem[mem[ip + 1]]);
                    ip += 2;
                    break;
                case OUT:
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    out.accept(a, outputConsumer);
                    ip += 2;
                    break;
                case JNZ:
                    Logger.trace("JNZ: ip = {}", ip);
                    Logger.trace("JNZ: {} != 0 => [{}]", mem[ip + 1], mem[ip + 2]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.trace("JNZ: {} != 0 => [{}]", a.get(), b.get());
                    jnz.accept(a, b);
                    Logger.trace(Arrays.toString(mem));
                    Logger.trace("JNZ: ip = {}", ip);
                    break;
                case JZ:
                    Logger.trace("JZ: ip = {}", ip);
                    Logger.trace("JZ: {} == 0 => [{}]", mem[ip + 1], mem[ip + 2]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.trace("JZ: {} == 0 => [{}]", a.get(), b.get());
                    jz.accept(a, b);
                    Logger.trace(Arrays.toString(mem));
                    Logger.trace("JZ: ip = {}", ip);
                    break;
                case LT:
                    Logger.trace("LT: {} < {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.trace("LT: {} < {}", a.get(), b.get());
                    Logger.trace(Arrays.toString(mem));
                    mem[mem[ip + 3]] = lt.apply(a, b).get();
                    Logger.trace("LT: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case EQ:
                    Logger.trace("EQ: {} == {} => [{}]", mem[ip + 1], mem[ip + 2], mem[ip + 3]);
                    a = (mode & mode1Mask) != mode1Mask ? () -> mem[mem[ip + 1]] : () -> mem[ip + 1];
                    b = (mode & mode2Mask) != mode2Mask ? () -> mem[mem[ip + 2]] : () -> mem[ip + 2];
                    Logger.trace("EQ: {} == {}", a.get(), b.get());
                    Logger.trace(Arrays.toString(mem));
                    mem[mem[ip + 3]] = eq.apply(a, b).get();
                    Logger.trace("EQ: [{}] = {}", mem[ip + 3], mem[mem[ip + 3]]);
                    ip += 4;
                    break;
                case EXIT:
                    Logger.trace("EXIT:");
                case BEGIN:
                    break;
            }
        }
    }

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

    private int ip;
    private Integer[] mem;
    private BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> add = (a, b) -> () -> a.get() + b.get();
    private BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> mul = (a, b) -> () -> a.get() * b.get();
    private BiConsumer<Supplier<Integer>, Supplier<Integer>> jnz = (test, jump) -> {
        if (test.get() != 0) {
            ip = jump.get();
        } else {
            ip += 3;
        }
    };
    private BiConsumer<Supplier<Integer>, Supplier<Integer>> jz = (test, jump) -> {
        if (test.get() == 0) {
            ip = jump.get();
        } else {
            ip += 3;
        }
    };
    private BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> lt = (a, b) -> () -> a.get() < b.get() ? 1 : 0;
    private BiFunction<Supplier<Integer>, Supplier<Integer>, Supplier<Integer>> eq = (a, b) -> () -> a.get().equals(b.get()) ? 1 : 0;
    private BiConsumer<Supplier<Integer>, Supplier<Integer>> in = (address, input) -> {
        Logger.trace("IN: blocking");
        Integer in = input.get();
        Logger.debug("IN: {} => [{}]", in, address.get());
        mem[address.get()] = in;
    };
    private BiConsumer<Supplier<Integer>, Consumer<Integer>> out = (address, output) -> {
        Logger.debug("OUT: => {}", address.get());
        output.accept(address.get());
    };
}

