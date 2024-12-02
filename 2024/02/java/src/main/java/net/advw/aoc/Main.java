package net.advw.aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import java.util.concurrent.TimeUnit;

public class Main {

    @State(Scope.Thread)
    public static class Reader {
        public  String input;

        public Reader() {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("input.txt")).getFile());
            try {
                input = Files.readString(Paths.get(file.getAbsolutePath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Benchmark
    public void benchmarkPart1(Reader reader) {
        Part1.solve(reader.input);
    }

    @Benchmark
    public void benchmarkPart2(Reader reader) {
        Part2.solve(reader.input);
    }

    public static void main(String[] args) throws RunnerException {
        Reader reader = new Reader();

        System.out.println("\nActual Part 1: " + Part1.solve(reader.input));
        System.out.println("Actual Part 2: " + Part2.solve(reader.input));


        Options options = new OptionsBuilder()
                .include(Main.class.getName() + ".*")
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(2)
                .measurementIterations(3)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(options).run();
    }
}