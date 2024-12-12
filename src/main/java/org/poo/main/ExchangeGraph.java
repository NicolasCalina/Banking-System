package org.poo.main;

import java.util.*;

public class ExchangeGraph {
    private Map<String, List<Edge>> adjList;

    public static class Edge {
        String toCurrency;
        double rate;

        public Edge(String toCurrency, double rate) {
            this.toCurrency = toCurrency;
            this.rate = rate;
        }
    }

    private static class Node {
        String currency;
        double rate;

        public Node(String currency, double rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }

    // Constructor
    public ExchangeGraph() {
        adjList = new HashMap<>();
    }

    public void addCurrency(String currency) {
        adjList.putIfAbsent(currency, new ArrayList<>());
    }

    public void addExchangeRate(String from, String to, double rate) {
        addCurrency(from);
        addCurrency(to);
        adjList.get(from).add(new Edge(to, rate));
        adjList.get(to).add(new Edge(from, 1 / rate));
    }

    public double findExchangeRate(String from, String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return -1;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble(node -> -node.rate)); // Max-heap pentru cel mai bun schimb
        Map<String, Double> bestRates = new HashMap<>();
        pq.add(new Node(from, 1.0));
        bestRates.put(from, 1.0);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.currency.equals(to)) {
                return current.rate;
            }

            for (Edge neighbor : adjList.get(current.currency)) {
                double newRate = current.rate * neighbor.rate;

                if (newRate > bestRates.getOrDefault(neighbor.toCurrency, 0.0)) {
                    bestRates.put(neighbor.toCurrency, newRate);
                    pq.add(new Node(neighbor.toCurrency, newRate));
                }
            }
        }

        return -1;
    }
}