package net.avdw.adventofcode.year2018;

import java.math.BigInteger;
import java.util.function.Function;

public class Day22 {
    public static void main(String[] args) {
        long depth = 9171;
        int targetX = 7;
        int targetY = 721;

//        depth = 510;
//        targetX = 10;
//        targetY = 10;

        BigInteger[][] geoIdx = new BigInteger[targetY+1][targetX+1];
        long[][] eroLvl = new long[geoIdx.length][geoIdx[0].length];
        geoIdx[0][0] = BigInteger.ZERO;
        eroLvl[0][0] = depth % 20183;
        for (int x = 1; x < geoIdx[0].length; x++) {
            geoIdx[0][x] = BigInteger.valueOf(x * 16807l);
            eroLvl[0][x] = (geoIdx[0][x].add(BigInteger.valueOf(depth)).mod(BigInteger.valueOf(20183))).longValueExact();
        }
        for (int y = 1; y < geoIdx.length; y++) {
            geoIdx[y][0] = BigInteger.valueOf(y * 48271l);
            eroLvl[y][0] = (geoIdx[y][0].add(BigInteger.valueOf(depth)).mod(BigInteger.valueOf(20183))).longValueExact();
        }

        for (int y = 1; y < geoIdx.length;y++) {
            for (int x = 1; x < geoIdx[y].length; x++) {
                if (x == targetX && y == targetY) {
                    geoIdx[y][x] = BigInteger.ZERO;
                } else {
                    geoIdx[y][x] = BigInteger.valueOf(eroLvl[y][x-1]).multiply(BigInteger.valueOf(eroLvl[y-1][x]));
                }
                eroLvl[y][x] = geoIdx[y][x].add(BigInteger.valueOf(depth)).mod(BigInteger.valueOf(20183)).longValueExact();
            }
        }

        long[][] rskLvl = new long[eroLvl.length][eroLvl[0].length];
        for (int y = 0; y < rskLvl.length;y++) {
            for (int x = 0; x < rskLvl[y].length; x++) {
                rskLvl[y][x] = eroLvl[y][x] % 3;
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

        System.out.println();
        print(geoIdx);
        System.out.println();
        print(eroLvl);
        System.out.println();
        print(rskLvl);
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
}
