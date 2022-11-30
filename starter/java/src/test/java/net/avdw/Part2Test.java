package net.avdw;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Part2Test {

    @State(Scope.Thread)
    public static class MyState {
        public  String in;

        @Setup(Level.Trial)
        public void doSetup() throws IOException {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("input.txt")).getFile());
            in = Files.readString(Paths.get(file.getAbsolutePath()));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "input_test.txt, 0",
    })
    void run(String input, String expected) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(input)).getFile());
        String in = Files.readString(Paths.get(file.getAbsolutePath()));
        String actual = Part2.run(in);
        assertEquals(expected, actual);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void benchmark(MyState state) {
        Part2.run(state.in);
    }

    @Disabled
    @Test
    void benchmarks() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".*")
                .mode(Mode.AverageTime)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(3)
                .threads(1)
                .measurementIterations(3)
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .build();

        new Runner(options).run();
    }
}