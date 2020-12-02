package net.avdw.adventofcode.year2018;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day22 {
    static Map<Type, Set<Gear>> restrictions = new HashMap<>();
    static Set<Node> toProcess = new HashSet<>();
    static Map<Transition, Gear> transitionGear = new HashMap<>();

    public static void main(String[] args) {
//        final long depth = 9171;
//        final int targetX = 7;
//        final int targetY = 721;

        final long depth = 11109;
        final int targetX = 10;
        final int targetY = 11; // 35

//        final int depth = 510;
//        final int targetX = 10;
//        final int targetY = 10;

        BigInteger[][] geoIdx = new BigInteger[targetY * 10][targetX * 10];
        long[][] eroLvl = new long[geoIdx.length][geoIdx[0].length];
        geoIdx[0][0] = BigInteger.ZERO;
        eroLvl[0][0] = depth % 20183;
        for (int x = 1; x < geoIdx[0].length; x++) {
            geoIdx[0][x] = BigInteger.valueOf(x * 16807);
            eroLvl[0][x] = (geoIdx[0][x].add(BigInteger.valueOf(depth)).mod(BigInteger.valueOf(20183))).longValueExact();
        }
        for (int y = 1; y < geoIdx.length; y++) {
            geoIdx[y][0] = BigInteger.valueOf(y * 48271);
            eroLvl[y][0] = (geoIdx[y][0].add(BigInteger.valueOf(depth)).mod(BigInteger.valueOf(20183))).longValueExact();
        }

        for (int y = 1; y < geoIdx.length; y++) {
            for (int x = 1; x < geoIdx[y].length; x++) {
                if (x == targetX && y == targetY) {
                    geoIdx[y][x] = BigInteger.ZERO;
                } else {
                    geoIdx[y][x] = BigInteger.valueOf(eroLvl[y][x - 1]).multiply(BigInteger.valueOf(eroLvl[y - 1][x]));
                }
                eroLvl[y][x] = geoIdx[y][x].add(BigInteger.valueOf(depth)).mod(BigInteger.valueOf(20183)).longValueExact();
            }
        }

        int[][] rskLvl = new int[eroLvl.length][eroLvl[0].length];
        for (int y = 0; y < rskLvl.length; y++) {
            for (int x = 0; x < rskLvl[y].length; x++) {
                rskLvl[y][x] = (int) (eroLvl[y][x] % 3);
                if (y == 0 && x == 0) {
                    System.out.print("M");
                } else if (y == targetY && x == targetX) {
                    System.out.print("T");
                } else {
                    System.out.print(String.format("%s", rskLvl[y][x] == 0 ? "." : rskLvl[y][x] == 1 ? "=" : "|"));
                }
            }
            System.out.println();
        }

        long totalRisk = 0;
        for (int y = 0; y <= targetY; y++) {
            for (int x = 0; x <= targetX; x++) {
                totalRisk += rskLvl[y][x];
            }
        }

        System.out.println(totalRisk);


        restrictions.put(Type.ROCKY, new HashSet<>(Arrays.asList(Gear.CLIMBING, Gear.TORCH)));
        restrictions.put(Type.WATER, new HashSet<>(Arrays.asList(Gear.CLIMBING, Gear.NOTHING)));
        restrictions.put(Type.NARROW, new HashSet<>(Arrays.asList(Gear.TORCH, Gear.NOTHING)));
        transitionGear.put(new Transition(Type.ROCKY, Type.WATER), Gear.CLIMBING);
        transitionGear.put(new Transition(Type.WATER, Type.ROCKY), Gear.CLIMBING);
        transitionGear.put(new Transition(Type.ROCKY, Type.NARROW), Gear.TORCH);
        transitionGear.put(new Transition(Type.NARROW, Type.ROCKY), Gear.TORCH);
        transitionGear.put(new Transition(Type.WATER, Type.NARROW), Gear.NOTHING);
        transitionGear.put(new Transition(Type.NARROW, Type.WATER), Gear.NOTHING);
        Node[][] dijkstra = new Node[rskLvl.length][rskLvl[0].length];
        Node firstNode = new Node(0, 0, Type.values()[rskLvl[0][0]]);
        firstNode.distance = 0;
        firstNode.gear = Gear.TORCH;
        toProcess.add(firstNode);
        List<Node> processed = new ArrayList<>();
        while (!toProcess.isEmpty()) {
            Node current = toProcess.stream().min(Comparator.comparingInt(n -> n.distance)).get();
            toProcess.remove(current);
            processed.add(current);

            final Optional<Node> north = (current.y - 1 > 0) ? Optional.of(new Node(current.x, current.y - 1, Type.values()[rskLvl[current.y - 1][current.x]])) : Optional.empty();
            final Optional<Node> south = (current.y + 1 < dijkstra.length) ? Optional.of(new Node(current.x, current.y + 1, Type.values()[rskLvl[current.y + 1][current.x]])) : Optional.empty();
            final Optional<Node> east = (current.x + 1 < dijkstra[0].length) ? Optional.of(new Node(current.x + 1, current.y, Type.values()[rskLvl[current.y][current.x + 1]])) : Optional.empty();
            final Optional<Node> west = (current.x - 1 > 0) ? Optional.of(new Node(current.x - 1, current.y, Type.values()[rskLvl[current.y][current.x - 1]])) : Optional.empty();

            if (north.isPresent()) {
                if (!processed.contains(north.get())) {
                    path(current, north);
                } else if (restrictions.get(north.get().type).contains(current.gear)) {
                    path(current, north);
                }

            }
            if (south.isPresent() && !processed.contains(south.get())) {
                path(current, south);
            }
            if (east.isPresent() && !processed.contains(east.get())) {
                path(current, east);
            }
            if (west.isPresent() && !processed.contains(west.get())) {
                path(current, west);
            }
        }

        System.out.println(processed.stream().filter(n -> n.x == targetX && n.y == targetY).findFirst());
        List<Node> nodes = processed.stream().sorted(Comparator.<Node>comparingInt(n -> n.y).thenComparingInt(n -> n.x)).collect(Collectors.toList());
        int currY = 0;
        for (Node node : nodes) {
            if (node.y != currY) {
                currY = node.y;
                System.out.println();
            }
            System.out.print(String.format("%5s", node.distance));
        }
        System.out.println();


        for (int i = 0; i < 100; i++) {
            System.out.println(processed.get(i));
        }


    }

    private static void path(Node from, Optional<Node> to) {
        to.get().distance = from.distance + 1;
        if (restrictions.get(to.get().type).contains(from.gear)) {
            to.get().gear = from.gear;
        } else {
            to.get().distance = to.get().distance + 7;
            to.get().gear = transitionGear.get(new Transition(from.type, to.get().type));
        }

        if (toProcess.contains(to.get())) {
            Node existingNode = toProcess.stream().filter(n -> n.equals(to.get())).findAny().get();
            existingNode.distance = Math.min(existingNode.distance, to.get().distance);
        } else {
            toProcess.add(to.get());
        }
    }

    private static void print(long[][] data) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[0].length; x++) {
                System.out.print(String.format("%10s", data[y][x]));
            }
            System.out.println();
        }
    }

    private static void print(BigInteger[][] data) {
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[0].length; x++) {
                System.out.print(String.format("%20s", data[y][x]));
            }
            System.out.println();
        }
    }

    enum Type {ROCKY, WATER, NARROW}

    enum Gear {CLIMBING, NOTHING, TORCH}

    static class Transition {
        Type from;
        Type to;

        public Transition(Type from, Type to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Transition that = (Transition) o;
            return from == that.from &&
                    to == that.to;
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

    }

    static class Node {
        final int x;
        final int y;
        int distance;
        Gear gear;
        Type type;

        public Node(int x, int y, Type type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return x == node.x &&
                    y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "x=" + x +
                    ", y=" + y +
                    ", distance=" + distance +
                    ", gear=" + gear +
                    ", type=" + type +
                    '}';
        }

    }
}
