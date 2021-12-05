package net.avdw.adventofcode;

public class Runner {
    public static void run(Day day, long profileDuration) {
        System.out.printf("%n%s.1 = %s%n", day.iso(), day.part01());
        Profiler.profile(day::part01, profileDuration);

        System.out.printf("%n%s.2 = %s%n", day.iso(), day.part02());
        Profiler.profile(day::part02, profileDuration);
    }
}
