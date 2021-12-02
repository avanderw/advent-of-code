package net.avdw.adventofcode;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Profiler {
    public static void profile(Runnable runnable, long duration) {
        System.out.printf("Profiling (%,d ms)...%n", duration);
        DescriptiveStatistics statistics = new DescriptiveStatistics();

        long end = System.currentTimeMillis() + duration;
        while (System.currentTimeMillis() < end) {
            long start = System.nanoTime();
            runnable.run();
            statistics.addValue(System.nanoTime() - start);
        }

        System.out.printf("        N: %,d%n", statistics.getN());
        System.out.printf("        μ: %,.0f μs, σ: %,.0f μs%n", statistics.getMean() / 1000, statistics.getStandardDeviation() / 1000);
        System.out.printf("      min: %,.0f μs, max: %,.0f μs%n", statistics.getMin() / 1000, statistics.getMax() / 1000);
        System.out.printf("   median: %,.0f μs, skewness: %,.0f μs, kurtosis: %,.0f μs%n", statistics.getPercentile(.5) / 1000, statistics.getSkewness() / 1000, statistics.getKurtosis() / 1000);
    }

    public static void run(Runnable runnable) {
        Profiler.profile(runnable, 5000);
    }
}
