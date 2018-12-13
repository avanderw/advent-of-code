package net.avdw.adventofcode.year2018;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Day13 {
    static Character[][] map = new Character[149][150];

    public static void main(String[] args) throws URISyntaxException, FileNotFoundException {
        HashMap<Vector2D, HashMap<Character, Vector2D>> stateChangeRules = new HashMap<>();

        Vector2D velocity = new Vector2D(-1, 0);
        HashMap<Character, Vector2D> stateChangeRule = new HashMap<>();
        stateChangeRule.put('/', new Vector2D(0, 1));
        stateChangeRule.put('\\', new Vector2D(0, -1));
        stateChangeRule.put('-', new Vector2D(-1, 0));
        stateChangeRules.put(velocity, stateChangeRule);

        velocity = new Vector2D(1, 0);
        stateChangeRule = new HashMap<>();
        stateChangeRule.put('/', new Vector2D(0, -1));
        stateChangeRule.put('\\', new Vector2D(0, 1));
        stateChangeRule.put('-', new Vector2D(1, 0));
        stateChangeRules.put(velocity, stateChangeRule);

        velocity = new Vector2D(0, -1);
        stateChangeRule = new HashMap<>();
        stateChangeRule.put('/', new Vector2D(1, 0));
        stateChangeRule.put('\\', new Vector2D(-1, 0));
        stateChangeRule.put('|', new Vector2D(0, -1));
        stateChangeRules.put(velocity, stateChangeRule);

        velocity = new Vector2D(0, 1);
        stateChangeRule = new HashMap<>();
        stateChangeRule.put('/', new Vector2D(-1, 0));
        stateChangeRule.put('\\', new Vector2D(1, 0));
        stateChangeRule.put('|', new Vector2D(0, 1));
        stateChangeRules.put(velocity, stateChangeRule);

        URL inputUrl = Day13.class.getResource("day13-input.txt");
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));

        List<Vehicle> vehicleList = new ArrayList();
        Integer y = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            for (int x = 0; x < line.length(); x++) {
                switch (line.charAt(x)) {
                    case '>':
                        map[y][x] = '-';
                        vehicleList.add(new Vehicle(vehicleList.size(), new Vector2D(1, 0), new Vector2D(x, y)));
                        break;
                    case '<':
                        map[y][x] = '-';
                        vehicleList.add(new Vehicle(vehicleList.size(), new Vector2D(-1, 0), new Vector2D(x, y)));
                        break;
                    case '^':
                        map[y][x] = '|';
                        vehicleList.add(new Vehicle(vehicleList.size(), new Vector2D(0, -1), new Vector2D(x, y)));
                        break;
                    case 'v':
                        map[y][x] = '|';
                        vehicleList.add(new Vehicle(vehicleList.size(), new Vector2D(0, 1), new Vector2D(x, y)));
                        break;
                    default:
                        map[y][x] = line.charAt(x);
                }
            }
            y++;
        }

        Vector2D firstCrashPosition = null;
        Boolean crashDetected = Boolean.FALSE;
        while (vehicleList.size() != 1) {
            vehicleList = vehicleList.stream().sorted(Comparator.<Vehicle>comparingDouble(v -> v.position.getY()).thenComparingDouble(v -> v.position.getX())).collect(Collectors.toList());

            List<Vehicle> crashedVehicles = new ArrayList<>();
            for (Vehicle vehicle : vehicleList) {
                vehicle.move();

                Character mapTile = map[(int) vehicle.position.getY()][(int) vehicle.position.getX()];
                switch (mapTile) {
                    case '+':
                        switch (vehicle.turnState) {
                            case 0:
                                vehicle.velocity = new Vector2D(vehicle.velocity.getY(), -vehicle.velocity.getX());
                                break;
                            case 1:
                                break;
                            case 2:
                                vehicle.velocity = new Vector2D(-vehicle.velocity.getY(), vehicle.velocity.getX());
                                break;
                            default:
                                throw new RuntimeException("bad turn state");
                        }
                        vehicle.turnState = ++vehicle.turnState % 3;
                        break;
                    default:
                        if (stateChangeRules.get(vehicle.velocity).containsKey(mapTile)) {
                            vehicle.velocity = stateChangeRules.get(vehicle.velocity).get(mapTile);
                        } else {
                            throw new RuntimeException(String.format("vehicle%s, tile=%s", vehicle, mapTile));
                        }
                }


                for (Vehicle other : vehicleList) {
                    if (!vehicle.equals(other) && vehicle.position.equals(other.position)) {
                        if (!crashedVehicles.contains(vehicle)) {crashedVehicles.add(vehicle);}
                        if (!crashedVehicles.contains(other)){crashedVehicles.add(other);}
                        crashDetected = Boolean.TRUE;
                        if (firstCrashPosition == null) {firstCrashPosition = vehicle.position;}
                        System.out.println(String.format("vehicle (%s) crashed with other (%s)", vehicle, other));
                    }
                }
            }

            if (crashDetected) {
                crashDetected = Boolean.FALSE;

                vehicleList.removeAll(crashedVehicles);
                crashedVehicles.clear();
//                System.out.println("removed offending vehicles, remaining: " + vehicleList);
            }
        }

        System.out.println();
        System.out.println(String.format("first crash position=%s", firstCrashPosition));
        System.out.println(String.format("remaining vehicle=%s", vehicleList));
//        print(map);
    }

    private static void print(Character[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
    }

    static class Vehicle {
        private Integer id;
        private Vector2D velocity;
        private Vector2D position;
        private Integer turnState = 0; // 0 = left, 1 = straight, 2 = right

        Vehicle(Integer id, Vector2D velocity, Vector2D position) {
            this.id = id;
            this.velocity = velocity;
            this.position = position;
        }

        public String toString() {
            return String.format("#%s=%s, %s:%s", id, position, velocity, map[(int) position.getY()][(int) position.getX()]);
        }

        public void move() {
            position = position.add(velocity);
        }
    }
}
