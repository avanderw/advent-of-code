package net.avdw.adventofcode.year2019;

import org.apache.commons.collections4.iterators.PermutationIterator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Day07 {
    public static void main(String[] args) {
        Logger.getConfiguration().level(Level.INFO).activate();

        Logger.info("Final output = {}", maxAmplification(Arrays.asList(3, 26, 1001, 26, -4, 26, 3, 27, 1002, 27, 2, 27, 1, 27, 26, 27, 4, 27, 1001, 28, -1, 28, 1005, 28, 6, 99, 0, 0, 5)));
        Logger.info("Final output = {}", maxAmplification(Arrays.asList(3, 52, 1001, 52, -5, 52, 3, 53, 1, 52, 56, 54, 1007, 54, 5, 55, 1005, 55, 26, 1001, 54, -5, 54, 1105, 1, 12, 1, 53, 54, 53, 1008, 54, 0, 55, 1001, 55, 1, 55, 2, 53, 55, 53, 4, 53, 1001, 56, -1, 56, 1005, 56, 6, 99, 0, 0, 0, 0, 10)));
        Logger.info("Final output = {}", maxAmplification(Arrays.asList(3, 8, 1001, 8, 10, 8, 105, 1, 0, 0, 21, 38, 63, 76, 93, 118, 199, 280, 361, 442, 99999, 3, 9, 101, 3, 9, 9, 102, 3, 9, 9, 101, 4, 9, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9, 101, 5, 9, 9, 1002, 9, 5, 9, 101, 5, 9, 9, 1002, 9, 4, 9, 4, 9, 99, 3, 9, 101, 2, 9, 9, 102, 3, 9, 9, 4, 9, 99, 3, 9, 101, 2, 9, 9, 102, 5, 9, 9, 1001, 9, 5, 9, 4, 9, 99, 3, 9, 102, 4, 9, 9, 1001, 9, 3, 9, 1002, 9, 5, 9, 101, 2, 9, 9, 1002, 9, 2, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 99, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 99, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 99, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 99)));
    }

    private static Integer maxAmplification(List<Integer> program) {
        PriorityQueue<Integer> output = new PriorityQueue<>(Comparator.comparing(num -> -num));
        Iterator<List<Integer>> it = new PermutationIterator<>(Arrays.asList(5, 6, 7, 8, 9));
        while (it.hasNext()) {
            List<Integer> phases = it.next();
            List<Amplifier> amplifiers = new ArrayList<>();

            BlockingQueue<Integer> startingQueue = new LinkedBlockingQueue<>();
            BlockingQueue<Integer> inputQueue = startingQueue;
            BlockingQueue<Integer> outputQueue = null;
            for (int i = 0; i < phases.size(); i++) {
                if (i == phases.size() - 1) {
                    outputQueue = startingQueue;
                } else {
                    outputQueue = new LinkedBlockingQueue<>();
                }
                inputQueue.add(phases.get(i));
                if (i == 0) {
                    inputQueue.add(0);
                }
                amplifiers.add(new Amplifier(phases.get(i), program, inputQueue, outputQueue));
                inputQueue = outputQueue;
            }

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            amplifiers.forEach(executorService::submit);
            executorService.shutdown();
            try {
                executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Logger.error(e.getMessage());
                Logger.debug(e);
            }
            Logger.debug("PHASE: {} = {}", phases, amplifiers.get(4).outputQueue);
            output.add(amplifiers.get(4).outputQueue.poll());
        }
        return output.poll();
    }


    static class Amplifier implements Runnable {
        private final int phase;
        private final BlockingQueue<Integer> inputQueue;
        private final BlockingQueue<Integer> outputQueue;
        private final IntComputer intComputer;

        Amplifier(int phase, List<Integer> program, BlockingQueue<Integer> inputQueue, BlockingQueue<Integer> outputQueue) {
            intComputer = new IntComputer(program);
            this.phase = phase;
            this.inputQueue = inputQueue;
            this.outputQueue = outputQueue;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(String.format("amp%s", phase));
            Logger.debug("Running");
            Supplier<Integer> inputSupplier = () -> {
                try {
                    return inputQueue.take();
                } catch (InterruptedException e) {
                    Logger.error(e.getMessage());
                    Logger.debug(e);
                    throw new UnsupportedOperationException();
                }
            };
            Consumer<Integer> outputConsumer = outputQueue::add;
            intComputer.run(inputSupplier, outputConsumer);
            Logger.debug("Stopping");
        }
    }
}
