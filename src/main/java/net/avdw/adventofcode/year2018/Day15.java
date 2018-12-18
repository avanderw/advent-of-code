package net.avdw.adventofcode.year2018;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Day15 {

    private static Integer mapSize = 32;
//            private static String test = "#######\n" +
//            "#.G...#\n" +
//            "#...EG#\n" +
//            "#.#.#G#\n" +
//            "#..G#E#\n" +
//            "#.....#\n" +
//            "#######\n";
//    private static String test = "#######\n" +
//            "#G..#E#\n" +
//            "#E#E.E#\n" +
//            "#G.##.#\n" +
//            "#...#E#\n" +
//            "#...E.#\n" +
//            "#######\n";
//    private static String test = "#######\n" +
//            "#E..EG#\n" +
//            "#.#G.E#\n" +
//            "#E.##E#\n" +
//            "#G..#.#\n" +
//            "#..E#.#\n" +
//            "#######\n";
//    private static String test = "#######\n" +
//            "#E.G#.#\n" +
//            "#.#G..#\n" +
//            "#G.#.G#\n" +
//            "#G..#.#\n" +
//            "#...E.#\n" +
//            "#######\n";
//    private static String test = "#######\n" +
//            "#.E...#\n" +
//            "#.#..G#\n" +
//            "#.###.#\n" +
//            "#E#G#G#\n" +
//            "#...#G#\n" +
//            "#######\n";
//    private static String test = "#########\n" +
//            "#G......#\n" +
//            "#.E.#...#\n" +
//            "#..##..G#\n" +
//            "#...##..#\n" +
//            "#...#...#\n" +
//            "#.G...G.#\n" +
//            "#.....G.#\n" +
//            "#########\n";
    private static Entity targetEntity = null;
    private static Integer level = 0;
    private static Integer targetRound = -1;
    private static Integer round = 0;
    private static Integer elfDamage = 3;

    public static void main(String[] args) throws FileNotFoundException, URISyntaxException {
        while (!elfWin()) {
            elfDamage++;
            System.out.println(String.format("=== ELF DAMAAGE %s ===", elfDamage));
        }
        System.out.println(String.format("Elf damage increased to %s", elfDamage));
    }

    public static boolean elfWin() throws URISyntaxException, FileNotFoundException {
        round = 0;
        URL inputUrl = Day15.class.getResource("day15-input.txt");
//        Scanner scanner = new Scanner(test);
        Scanner scanner = new Scanner(new File(inputUrl.toURI()));

        List<Entity> entityList = new ArrayList<>();
        Character[][] map = new Character[mapSize][mapSize];
        int y = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            for (int x = 0; x < line.length(); x++) {
                boolean isEntity = line.charAt(x) == 'G' || line.charAt(x) == 'E';
                map[y][x] = isEntity ? '.' : line.charAt(x);
                if (isEntity) {
                    entityList.add(new Entity(x, y, line.charAt(x) == 'E'));
                }
            }

            y++;
        }

        Long origElfCount = entityList.stream().filter(e -> e.isElf).count();
        targetEntity = entityList.get(entityList.size() - 2);


        if (level > 0) {
            System.out.println("=== INITIALLY ===");
        }
        StringBuilder orig = print(map, entityList);
        Integer maxRounds = 2000;
        while (entityList.stream().filter(e -> e.isAlive).map(e -> e.isElf).distinct().count() > 1 && round < maxRounds) {
            round++;

            if (level > 0) {
                System.out.println(String.format("%n=== ROUND %s ===", round));
            }
            doRound(map, entityList);
            removeDeadEntities(entityList, map);
            if (level > 0) {
                print(map, entityList, null, null);
            }
        }

        if (level > 0) {
            System.out.println();
            bake(map, entityList);
            scanner = new Scanner(orig.toString());
            for (y = 0; y < map.length; y++) {
                System.out.print(scanner.nextLine());
                if (y == map.length >> 1) {
                    System.out.print("  -->  ");
                } else {
                    System.out.print("       ");
                }
                for (int x = 0; x < map.length; x++) {
                    System.out.print(map[y][x]);
                }
                System.out.println();
            }
        }

        System.out.println();
        int health = entityList.stream().mapToInt(e -> e.health).sum();
        System.out.println(String.format("Combat ends after %s full rounds", round));
        System.out.println(String.format("%s win with %s total hit points left", entityList.get(0).isElf ? "Elves" : "Goblins", health));
        System.out.println(String.format("Outcome: %s * %s = %s%n", round, health, round * health));

        Long remainingElfs = entityList.stream().filter(e -> e.isAlive).filter(e -> e.isElf).count();
        System.out.println(String.format("%s/%s elves remain when doing %s damage", remainingElfs, origElfCount, elfDamage));
        return origElfCount.equals(remainingElfs);
    }

    private static void removeDeadEntities(List<Entity> entityList, Character[][] map) {
        List<Entity> deadEntities = entityList.stream().filter(e -> !e.isAlive).collect(Collectors.toList());
        deadEntities.stream().forEach(e -> {
            map[e.y][e.x] = '.';
            entityList.remove(e);
        });
    }

    private static void doRound(Character[][] map, List<Entity> entityList) {
        entityList.sort(Comparator.<Entity>comparingInt(e -> e.y).thenComparingInt(e -> e.x));

        for (Entity entity : entityList) {
            if (entityList.stream().filter(e -> e.isAlive).map(e -> e.isElf).distinct().count() < 2) {
                round--;
                break;
            }
            if (entity.isAlive) {
                bake(map, entityList);
                if (level > 1 && round.equals(targetRound) && entity == targetEntity) {
                    System.out.println(String.format("%s", entity));
                }

                List<Entity> aliveEnemies = aliveEnemies(map, entityList, entity);
                boolean nextToEnemy = !aliveEnemies.isEmpty();
                if (!nextToEnemy) { // move
                    Optional<Node> target = target(map, entityList, entity);
                    if (target.isPresent()) {
                        Node move = move(map, entity, target.get());
                        if (targetEntity == entity) {
                            List<Node> markers = new ArrayList<>();
                            markers.add(move);
                            if (level > 1 && round.equals(targetRound))
                                print(map, entityList, markers, '+');
                        }
                        map[entity.y][entity.x] = '.';
                        entity.y = move.y;
                        entity.x = move.x;
                        map[entity.y][entity.x] = entity.isElf ? 'E' : 'G';
                    }
                    aliveEnemies = aliveEnemies(map, entityList, entity);
                    nextToEnemy = !aliveEnemies.isEmpty();
                }

                if (nextToEnemy) {
                    aliveEnemies.sort(Comparator.<Entity>comparingInt(e -> e.health).thenComparingInt(e -> e.y).thenComparingInt(e -> e.x));
                    if (level > 0) {
                        System.out.println(String.format("%s can attack %s", entity, aliveEnemies));
                    }
                    aliveEnemies.get(0).damage();
                }
                unbake(map, entityList);
            }

        }
    }

    private static List<Entity> aliveEnemies(Character[][] map, List<Entity> entityList, Entity entity) {
        List<Entity> enemies = new ArrayList<>();
        if (map[entity.y - 1][entity.x] == (entity.isElf ? 'G' : 'E')) {
            enemies.add(entityList.stream().filter(e -> e.y == entity.y - 1).filter(e -> e.x == entity.x).findFirst().get());
        }
        if (map[entity.y][entity.x - 1] == (entity.isElf ? 'G' : 'E')) {
            enemies.add(entityList.stream().filter(e -> e.y == entity.y).filter(e -> e.x == entity.x - 1).findFirst().get());
        }
        if (map[entity.y][entity.x + 1] == (entity.isElf ? 'G' : 'E')) {
            enemies.add(entityList.stream().filter(e -> e.y == entity.y).filter(e -> e.x == entity.x + 1).findFirst().get());
        }
        if (map[entity.y + 1][entity.x] == (entity.isElf ? 'G' : 'E')) {
            enemies.add(entityList.stream().filter(e -> e.y == entity.y + 1).filter(e -> e.x == entity.x).findFirst().get());
        }
        enemies.removeIf(e -> !e.isAlive);
        return enemies;
    }

    private static StringBuilder print(Character[][] map, List<Entity> entityList) {
        return print(map, entityList, null, null);
    }

    private static Node move(Character[][] map, Entity entity, Node target) {
        Integer[][] dijkstra = dijkstra(map, new Entity(target.x, target.y, !entity.isElf));
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                if (dijkstra[y][x] == -2) {
                    dijkstra[y][x] = Integer.MAX_VALUE;
                }
            }
        }
        Node north = new Node(entity.x, entity.y - 1, dijkstra[entity.y - 1][entity.x].equals(-1) ? Integer.MAX_VALUE : dijkstra[entity.y - 1][entity.x]);
        Node south = new Node(entity.x, entity.y + 1, dijkstra[entity.y + 1][entity.x].equals(-1) ? Integer.MAX_VALUE : dijkstra[entity.y + 1][entity.x]);
        Node east = new Node(entity.x + 1, entity.y, dijkstra[entity.y][entity.x + 1].equals(-1) ? Integer.MAX_VALUE : dijkstra[entity.y][entity.x + 1]);
        Node west = new Node(entity.x - 1, entity.y, dijkstra[entity.y][entity.x - 1].equals(-1) ? Integer.MAX_VALUE : dijkstra[entity.y][entity.x - 1]);


        int min = Math.min(Math.min(Math.min(north.value, south.value), east.value), west.value);
        Node move = null;
        if (north.value == min && !dijkstra[north.y][north.x].equals(-1)) {
            move = north;
        } else if (west.value == min && !dijkstra[west.y][west.x].equals(-1)) {
            move = west;
        } else if (east.value == min && !dijkstra[east.y][east.x].equals(-1)) {
            move = east;
        } else if (!dijkstra[south.y][south.x].equals(-1)) {
            move = south;
        }
        if (entity == targetEntity && level > 2) {
            System.out.println(String.format("%s, %s, %s, %s, %s", min, north, south, east, west));
        }

        return move;
    }

    private static Optional<Node> target(Character[][] map, List<Entity> entityList, Entity entity) {
        List<Entity> enemies = entityList.stream().filter(e -> e.isElf != entity.isElf).filter(e -> e.isAlive).collect(Collectors.toList());

        List<Node> inRange = inRange(map, enemies);
        if (targetEntity == entity && level > 1 && round.equals(targetRound)) {
            print(map, entityList, inRange, '?');
        }

        Integer[][] dijkstra = dijkstra(map, entity);
        List<Node> reachable = inRange.stream().filter(n -> dijkstra[n.y][n.x] != -2).collect(Collectors.toList());

        if (targetEntity == entity && level > 1 && round.equals(targetRound)) {
            print(map, entityList, reachable, '@');
        }

        List<Node> nearest = new ArrayList<>();
        Integer min = Integer.MAX_VALUE;
        for (Node n : reachable) {
            if (dijkstra[n.y][n.x] < min) {
                nearest.clear();
                min = dijkstra[n.y][n.x];
                nearest.add(n);
            } else if (dijkstra[n.y][n.x].equals(min)) {
                nearest.add(n);
            }
        }

        if (targetEntity == entity && level > 1 && round.equals(targetRound)) {
            print(map, entityList, nearest, '!');
        }

        Optional<Node> chosen = nearest.stream().sorted(Comparator.<Node>comparingInt(e -> e.y).thenComparingInt(e -> e.x)).findFirst();
        if (targetEntity == entity && level > 1 && round.equals(targetRound)) {
            List<Node> theChosen = new ArrayList<>();
            chosen.ifPresent(theChosen::add);
            print(map, entityList, theChosen, '+');
        }

        return chosen;
    }

    private static List<Node> inRange(Character[][] map, List<Entity> entities) {
        List<Node> points = new ArrayList<>();
        for (Entity entity : entities) {
            if (map[entity.y - 1][entity.x] == '.') {
                points.add(new Node(entity.x, entity.y - 1));
            }
            if (map[entity.y][entity.x + 1] == '.') {
                points.add(new Node(entity.x + 1, entity.y));
            }
            if (map[entity.y + 1][entity.x] == '.') {
                points.add(new Node(entity.x, entity.y + 1));
            }
            if (map[entity.y][entity.x - 1] == '.') {
                points.add(new Node(entity.x - 1, entity.y));
            }
        }
        return points;
    }

    private static Integer[][] dijkstra(Character[][] map, Entity entity) {
        Integer[][] dijkstra = new Integer[map.length][map.length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                dijkstra[y][x] = map[y][x] == '.' ? -2 : -1;
            }
        }

        Queue<Node> toProcess = new LinkedList<>();
        toProcess.add(new Node(entity.x, entity.y, 0));

        while (!toProcess.isEmpty()) {
            Node node = toProcess.remove();
            int y = node.y;
            int x = node.x;
            if (dijkstra[y][x] == -2 || node.value == 0) {
                dijkstra[y][x] = node.value;
                if (dijkstra[y - 1][x] == -2) {
                    toProcess.add(new Node(x, y - 1, node.value + 1));
                }
                if (dijkstra[y][x + 1] == -2) {
                    toProcess.add(new Node(x + 1, y, node.value + 1));
                }
                if (dijkstra[y + 1][x] == -2) {
                    toProcess.add(new Node(x, y + 1, node.value + 1));
                }
                if (dijkstra[y][x - 1] == -2) {
                    toProcess.add(new Node(x - 1, y, node.value + 1));
                }
            }
        }

        return dijkstra;
    }

    private static void bake(Character[][] map, List<Entity> entityList) {
        for (Entity entity : entityList) {
            if (entity.isAlive) {
                map[entity.y][entity.x] = entity.isElf ? 'E' : 'G';
            } else {
                map[entity.y][entity.x] = '.';
            }
        }
    }

    private static void unbake(Character[][] map, List<Entity> entityList) {
        for (Entity entity : entityList) {
            map[entity.y][entity.x] = '.';
        }
    }

    private static StringBuilder print(Character[][] map, List<Entity> entityList, List<Node> markers, Character marker) {
        StringBuilder sb = new StringBuilder();
        if (markers == null) {
            markers = new ArrayList<>();
        }
        System.out.println();
        Character[][] draw = new Character[map.length][map.length];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map.length; x++) {
                draw[y][x] = map[y][x];
            }
        }
        entityList.forEach(e -> draw[e.y][e.x] = e.isElf ? 'E' : 'G');
        markers.forEach(m -> draw[m.y][m.x] = marker);

        for (int y = 0; y < draw.length; y++) {
            for (int x = 0; x < draw[y].length; x++) {
                sb.append(draw[y][x]);

                if (level > 0) {
                    System.out.print(draw[y][x]);
                }
            }
            int finalY = y;
            List<Entity> lineEntities = entityList.stream().filter(e -> e.y == finalY).collect(Collectors.toList());
            lineEntities.sort(Comparator.<Entity>comparingInt(e -> e.y).thenComparingInt(e -> e.x));

            if (level > 0) {
                System.out.print("   ");
            }
            for (Entity entity : lineEntities) {

                if (level > 0) {
                    System.out.print(String.format("%s(%s) ", entity.isElf ? "E" : "G", entity.health));
                }
            }
            sb.append("\n");

            if (level > 0) {
                System.out.println();
            }
        }
        return sb;
    }

    static class Node {
        int x;
        int y;
        int value;

        Node(int x, int y, int value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        Node(int x, int y) {
            this.x = x;
            this.y = y;
            value = -99;
        }

        @Override
        public String toString() {
            return String.format("[%s, %s, %s]", x, y, value);
        }
    }

    static class Entity {
        private int health = 200;
        private int x;
        private int y;
        private final Boolean isElf;
        private Boolean isAlive;

        Entity(int x, int y, Boolean isElf) {
            this.x = x;
            this.y = y;
            this.isElf = isElf;
            this.isAlive = Boolean.TRUE;
        }

        void damage() {
            health -= isElf ? 3 : elfDamage;
            if (health <= 0) {
                this.isAlive = Boolean.FALSE;
            }
        }

        public String toString() {
            return String.format("<%s:%s> %s(%s)", x, y, isElf ? 'E' : 'G', health);
        }
    }
}
