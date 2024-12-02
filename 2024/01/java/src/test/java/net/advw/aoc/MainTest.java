package net.advw.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static Stream<Arguments> part1TestCases() {
        return Stream.of(
                Arguments.of("input.txt", "11")
        );
    }

    private static Stream<Arguments> part2TestCases() {
        return Stream.of(
                Arguments.of("input.txt", "31")
        );
    }

    @ParameterizedTest
    @MethodSource("part1TestCases")
    void testPart1(String input, String expectedOutput) {
        try {
            Main.Reader reader = new Main.Reader();
            assertEquals(expectedOutput, Part1.solve(reader.input));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    @ParameterizedTest
    @MethodSource("part2TestCases")
    void testPart2(String input, String expectedOutput) {
        try {
            Main.Reader reader = new Main.Reader();
            assertEquals(expectedOutput, Part2.solve(reader.input));
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}