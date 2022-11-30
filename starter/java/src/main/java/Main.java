import net.avdw.Part1;
import net.avdw.Part2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("input.txt")).getFile());
        String in = Files.readString(Paths.get(file.getAbsolutePath()));
        String part1 = Part1.run(in);
        String part2 = Part2.run(in);
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2);
    }
}
