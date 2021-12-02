package net.avdw.adventofcode;

public class Runner {
    public static void run(Day day) {
        System.out.printf("%n%s.1 = %s%n", day.iso(), day.part01());
        Profiler.run(day::part01);

        System.out.printf("%n%s.2 = %s%n", day.iso(), day.part02());
        Profiler.run(day::part02);
    }
}
