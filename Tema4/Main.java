/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author Seby
 */

import com.github.javafaker.Faker;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        ///random locations
        List<Location> locations = generateRandomLocations(10);
        System.out.println("All Locations:");
        locations.forEach(System.out::println);

        ///random edges
        List<Edge> edges = generateRandomEdges(locations, 20);
        System.out.println("\nEdges:");
        edges.forEach(System.out::println);

        Graph<Location, DefaultWeightedEdge> graph = buildGraph(locations, edges);

        ///start
        Location startLocation = locations.stream()
            .filter(Location::isFriendly)
            .findFirst()
            .orElse(locations.get(0));
        Robot robot = new Robot(startLocation);
        System.out.println("\nRobot starting at: " + robot.getCurrentLocation());

        Map<Location, Integer> fastestTimes = computeFastestTimes(graph, startLocation);

        Map<LocationType, List<Location>> locationsByType = locations.stream()
            .collect(Collectors.groupingBy(Location::getType));

        System.out.println("\nFastest Times from " + startLocation.getName() + ":");

        System.out.println("Friendly Locations:");
        displayTimesForType(locationsByType, fastestTimes, LocationType.FRIENDLY);
        
        System.out.println("\nNeutral Locations:");
        displayTimesForType(locationsByType, fastestTimes, LocationType.NEUTRAL);
        
        System.out.println("\nEnemy Locations:");
        displayTimesForType(locationsByType, fastestTimes, LocationType.ENEMY);
    }

    private static List<Location> generateRandomLocations(int count) {
        Faker faker = new Faker();
        Random random = new Random();
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = faker.address().city();
            LocationType type = LocationType.values()[random.nextInt(LocationType.values().length)];
            locations.add(new Location(name, type));
        }
        return locations;
    }

    private static List<Edge> generateRandomEdges(List<Location> locations, int edgeCount) {
        Random random = new Random();
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < edgeCount; i++) {
            Location from = locations.get(random.nextInt(locations.size()));
            Location to = locations.get(random.nextInt(locations.size()));
            if (from != to) { ///fara self-loops
                int time = 5 + random.nextInt(56); /// 5 - 60 
                double probability = 0.5 + random.nextDouble() * 0.5; /// 0.5 - 1.0
                edges.add(new Edge(from, to, time, probability));
            }
        }
        return edges;
    }

    private static Graph<Location, DefaultWeightedEdge> buildGraph(List<Location> locations, List<Edge> edges) {
        Graph<Location, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        
        locations.forEach(graph::addVertex);
        
        Random random = new Random();
        for (Edge edge : edges) {
           if (random.nextDouble() <= edge.getProbability()) {
                DefaultWeightedEdge weightedEdge = graph.addEdge(edge.getFrom(), edge.getTo());
           if (weightedEdge != null) {
                graph.setEdgeWeight(weightedEdge, edge.getTime());
        }
    }
}
        return graph;
    }

    private static Map<Location, Integer> computeFastestTimes(Graph<Location, DefaultWeightedEdge> graph, Location start) {
        DijkstraShortestPath<Location, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<>(graph);
        Map<Location, Integer> times = new HashMap<>();
        for (Location target : graph.vertexSet()) {
            if (target != start) {
                var path = dijkstra.getPath(start, target);
                if (path != null) {
                    times.put(target, (int) path.getWeight());
                } else {
                    times.put(target, Integer.MAX_VALUE); 
                }
            } else {
                times.put(target, 0); /// Timp insusi 0
            }
        }
        return times;
    }

    private static void displayTimesForType(Map<LocationType, List<Location>> locationsByType, 
                                            Map<Location, Integer> fastestTimes, 
                                            LocationType type) {
        List<Location> locations = locationsByType.getOrDefault(type, Collections.emptyList());
        locations.stream()
            .sorted(Comparator.comparing(Location::getName))
            .forEach(loc -> {
                int time = fastestTimes.get(loc);
                if (time == Integer.MAX_VALUE) {
                    System.out.println(loc.getName() + ": No path exists");
                } else {
                    System.out.println(loc.getName() + ": " + time + " minutes");
                }
            });
    }
}
