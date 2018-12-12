package net.avdw.adventofcode.year2018;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.function.Function;

public class Day11 {
    public static void main(String[] args) {
//        System.out.println(powerLevel(122, 79, 57));
//        System.out.println(powerLevel(217, 196, 39));
//        System.out.println(powerLevel(101, 153, 71));

        Integer[][] powerLevels = new Integer[300][300];
        for (int y = 0; y < powerLevels.length; y++) {
            for (int x = 0; x < powerLevels[y].length; x++) {
//                powerLevels[y][x] = powerLevel(x, y, 18);
//                powerLevels[y][x] = powerLevel(x, y, 42);
                powerLevels[y][x] = powerLevel(x, y, 6548);
            }
        }

//        for (int y =0; y < 300; y++) {
//            for (int x = 0; x < 300; x++) {
//                System.out.print(String.format("  %s ", powerLevels[y][x]));
//            }
//            System.out.println();
//        }

//        System.out.println();
//        System.out.println();

        for (int s = 1; s < 25; s++) {
            Vector2D point = new Vector2D(0, 0);
            Integer max = 0;
            Integer[][] total = new Integer[300-s+1][300-s+1];
            for (int y = 0; y < total.length; y++) {
                for (int x = 0; x < total[y].length; x++) {
                    total[y][x] = 0;
                    for (int offsetY = 0; offsetY < s; offsetY++) {
                        for (int offsetX = 0; offsetX < s; offsetX++) {
                            total[y][x] += powerLevels[y+offsetY][x+offsetX];
                        }
                    }
                    if (total[y][x] > max) {
                        max = total[y][x];
                        point = new Vector2D(x, y);
                    }
                }
            }
            System.out.println(String.format("%s,%s,%s = %s",(int)point.getX(), (int)point.getY(), s, max));
        }


//        for (int y =0; y < 298; y++) {
//            for (int x = 0; x < 298; x++) {
//                System.out.print(String.format("  %s ", total[y][x]));
//            }
//            System.out.println();
//        }


//        for (int y = (int)point.getY()-1; y < point.getY()+4; y++) {
//            for (int x = (int)point.getX()-1; x < point.getX()+4; x++) {
//                System.out.print(String.format("  %s ", powerLevels[y][x]));
//            }
//            System.out.println();
//        }
    }

    static Integer powerLevel(Integer x, Integer y, Integer serial) {
        Integer rackId = x + 10;
        Integer power = rackId * y;
        power += serial;
        power *= rackId;

        if (power < 100) {
            return 0;
        } else {
            return Integer.parseInt(String.valueOf(power.toString().charAt((""+power).length()-3))) - 5;
        }
    }
}
