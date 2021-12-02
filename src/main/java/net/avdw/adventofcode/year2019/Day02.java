package net.avdw.adventofcode.year2019;

import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day02 {
    public static void main(String[] args) throws FileNotFoundException {
        Logger.debug(runProgram(Arrays.asList(1, 0, 0, 0, 99)));
        Logger.debug(runProgram(Arrays.asList(2, 3, 0, 3, 99)));
        Logger.debug(runProgram(Arrays.asList(2, 4, 4, 5, 99, 0)));
        Logger.debug(runProgram(Arrays.asList(1, 1, 1, 4, 99, 5, 6, 0, 99)));
        Logger.debug(runProgram(Arrays.asList(1, 9, 10, 3, 2, 3, 11, 0, 99, 30, 40, 50)));

        Logger.getConfiguration().level(Level.INFO).activate();

        URL inputUrl = Day01.class.getResource("day02.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));
        scanner.useDelimiter(",");
        List<Integer> program = new ArrayList<>();
        while (scanner.hasNext()) {
            program.add(scanner.nextInt());
        }
        scanner.close();

        Logger.info("---< Part 2 >---");
        for (int a = 0; a < 100; a++) {
            for (int b = 0; b < 100; b++) {
                program.set(1, a);
                program.set(2, b);
                List<Integer> runResults = runProgram(new ArrayList<>(program));
                if (runResults.get(0) == 19690720) {
                    Logger.info("Results: {}", runResults);
                    Logger.info("100 * noun + verb = 100 * {} + {} = {}", runResults.get(1), runResults.get(2), 100 * runResults.get(1) + runResults.get(2));
                } else {
                    Logger.debug("{} {} = {}", a, b, runResults.get(0));
                }
            }
        }

        Logger.info("---< Part 1 >---");
        program.set(1, 12);
        program.set(2, 2);
        Logger.info(runProgram(program));
    }

    private static List<Integer> runProgram(final List<Integer> program) {
        Logger.debug("Run: {}", program);
        int idx = 0;
        OpCodeType opCodeType = OpCodeType.get(program.get(idx));
        while (opCodeType != OpCodeType.EXIT) {
            int firstIdx = program.get(++idx);
            int secondIdx = program.get(++idx);
            int outputIdx = program.get(++idx);

            Logger.debug("{}: [{}] [{}] => [{}]", opCodeType, firstIdx, secondIdx, outputIdx);
            program.set(outputIdx, OpCodeExecutor.execute(opCodeType, program.get(firstIdx), program.get(secondIdx)));
            opCodeType = OpCodeType.get(program.get(++idx));
        }
        Logger.debug("End: {}", program);
        return program;
    }

    static class OpCodeExecutor {
        static int execute(OpCodeType type, int firstValue, int secondValue) {
            int output;
            switch (type) {
                case ADD:
                    output = firstValue + secondValue;
                    Logger.debug("= {} + {} = {}", firstValue, secondValue, output);
                    break;
                case MULTIPLY:
                    output = firstValue * secondValue;
                    Logger.debug("= {} * {} = {}", firstValue, secondValue, output);
                    break;
                case EXIT:
                default:
                    throw new UnsupportedOperationException();
            }
            return output;
        }
    }

    enum OpCodeType {
        ADD, MULTIPLY, EXIT;

        static OpCodeType get(int code) {
            switch (code) {
                case 1:
                    return ADD;
                case 2:
                    return MULTIPLY;
                case 99:
                    return EXIT;
                default:
                    Logger.error("Unknown code: {}", code);
                    throw new UnsupportedOperationException();
            }
        }
    }
}
