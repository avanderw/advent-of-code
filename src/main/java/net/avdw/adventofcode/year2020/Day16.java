package net.avdw.adventofcode.year2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class Day16 {
    public static void main(String[] args) throws FileNotFoundException {
        URL inputUrl = Year2020.class.getResource("day16.txt");
        Scanner scanner = new Scanner(new File(inputUrl.getFile()));

        Map<String, List<Integer>> rules = new HashMap<>();
        List<List<Integer>> nearbyTicketList = new ArrayList<>();
        int parseState = 0;
        List<Integer> myTicket = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.equals("your ticket:")) {
                parseState = 1;
            } else if (line.equals("nearby tickets:")) {
                parseState = 2;
            } else if (line.trim().isEmpty()) {

            } else {
                switch (parseState) {
                    case 0:
                        rules.putAll(readRules(line));
                        break;
                    case 1:
                        myTicket = readTicket(line);
                        break;
                    case 2:
                        List<Integer> nearbyTicket = readTicket(line);
                        nearbyTicketList.add(nearbyTicket);
                        break;
                    default:
                        throw new UnsupportedOperationException();
                }
            }
        }

        Set<Integer> validValues = rules.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
        Set<Integer> invalidValues = nearbyTicketList.stream().flatMap(Collection::stream).collect(Collectors.toSet());
        invalidValues.removeAll(validValues);
        System.out.printf("Invalid (without duplicates) %s = %s%n", invalidValues, invalidValues.stream().mapToInt(r -> r).sum());

        Map<Integer, Set<String>> possibilitySpace = new HashMap<>();
        for (int i = 0; i < rules.keySet().size(); i++) {
            possibilitySpace.put(i, new HashSet<>(rules.keySet()));
        }
        System.out.println(possibilitySpace);

        nearbyTicketList.stream().filter(validValues::containsAll).forEach(ticket -> {
            for (int idx = 0; idx < ticket.size(); idx++) {
                int value = ticket.get(idx);
                Set<String> notPossible = new HashSet<>();
                if (possibilitySpace.get(idx).size() != 1) {
                    possibilitySpace.get(idx).forEach(possibility -> {
                        List<Integer> values = rules.get(possibility);
                        if (!values.contains(value)) {
                            notPossible.add(possibility);
                        }
                    });
//                System.out.printf("TICKET %s, idx %s, possibilitySpace %s, notPossible %s%n", ticket, idx,
//                        possibilitySpace.get(idx).stream().map(p->String.format("%s[%s]", p, rules.get(p).contains(value))).collect(Collectors.joining(",")),
//                notPossible);
                    possibilitySpace.get(idx).removeAll(notPossible);
                    if (possibilitySpace.get(idx).size() == 1) {
                        String key = possibilitySpace.get(idx).stream().findAny().get();
                        System.out.printf("Resolved idx %s to %s removing from other possibilities%n", idx, key);
                        int finalIdx = idx;
                        possibilitySpace.entrySet().stream()
                                .filter(e -> !e.getKey().equals(finalIdx))
                                .filter(e -> e.getValue().contains(key))
                                .forEach(e -> {
                                    System.out.printf("Removing %s from %s leaving ", key, e.getKey());
                                    e.getValue().remove(key);
                                    System.out.println(e);
                                });
                    }
                }
            }
        });

        while (possibilitySpace.values().stream().anyMatch(v -> v.size() > 1)) {
            possibilitySpace.values().stream().filter(v -> v.size() == 1).forEach(list -> {
                String key = list.stream().findAny().get();
                possibilitySpace.values().stream().filter(vlist -> vlist.size() > 1).forEach(vlist -> vlist.remove(key));
            });
        }

        List<Integer> finalMyTicket = myTicket;
        possibilitySpace.entrySet().forEach(e->{
            System.out.printf("%s = %48s = %s%n", e.getKey(), e.getValue(), finalMyTicket.get(e.getKey()));
        });
        System.out.println(possibilitySpace.entrySet().stream()
                .filter(e -> e.getValue().stream().findAny().get().startsWith("departure"))
                .mapToLong(e -> {
                    System.out.printf("%s = %s%n", e.getKey(), finalMyTicket.get(e.getKey()));
                    return finalMyTicket.get(e.getKey());
                })
                .reduce(1L, (left, right) -> left * right));
    }

    private static Map<String, List<Integer>> readRules(final String line) {
        System.out.printf("Parsing RULE '%s' ", line);
        Map<String, List<Integer>> rules = new HashMap<>();
        String[] split = line.split(":");
        String rule = split[0].trim();
        List<Integer> values = new ArrayList<>();
        String[] valueSplit = split[1].trim().split("or");
        for (String value : valueSplit) {
            String[] marker = value.trim().split("-");
            for (int i = Integer.parseInt(marker[0]); i <= Integer.parseInt(marker[1]); i++) {
                values.add(i);
            }
        }
        rules.put(rule, values);
        System.out.println(rules);
        return rules;
    }

    private static List<Integer> readTicket(final String line) {
        System.out.printf("Parsing TICKET '%s' ", line);
        List<Integer> values = new ArrayList<>();
        Scanner tokenScanner = new Scanner(line);
        tokenScanner.useDelimiter(",");
        while (tokenScanner.hasNextInt()) {
            values.add(tokenScanner.nextInt());
        }
        System.out.println(values);
        return values;
    }
}
