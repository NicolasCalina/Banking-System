package org.poo.main;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Represents a graph for managing currency exchange rates.
 */
@Getter
@Setter
public class ExchangeGraph {
    private Map<String, List<Edge>> adjList;

    /**
     * Represents an edge in the graph, connecting two currencies with a specific exchange rate.
     */
    public static class Edge {
        private String toCurrency;
        private double rate;

        /**
         * Constructor for edge.
         *
         * @param toCurrency the currency to the edge
         * @param rate       the exchange rate for the edge
         */
        public Edge(final String toCurrency,
                    final double rate) {
            this.toCurrency = toCurrency;
            this.rate = rate;
        }
    }

    /**
     * Represents a node in the priority queue for calculations.
     */
    private static class Node {
        private String currency;
        private double rate;

        /**
         * Constructs a node with a currency and its associated rate.
         *
         * @param currency the currency name
         * @param rate     the exchange rate at this node
         */
        Node(final String currency,
             final double rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }

    /**
     * Constructor for the exchange graph.
     */
    public ExchangeGraph() {
        adjList = new HashMap<>();
    }

    /**
     * Adds a currency to the graph if it does not already exist.
     *
     * @param currency the name of the currency to add
     */
    public void addCurrency(final String currency) {
        adjList.putIfAbsent(currency, new ArrayList<>());
    }

    /**
     * Adds an exchange rate between two currencies.
     *
     * @param from the source currency
     * @param to   the target currency
     * @param rate the exchange rate from the source to the target currency
     */
    public void addExchangeRate(final String from,
                                final String to,
                                final double rate) {
        addCurrency(from);
        addCurrency(to);
        adjList.get(from).add(new Edge(to, rate));
        adjList.get(to).add(new Edge(from, 1 / rate));
    }

    /**
     * Finds the best exchange rate between two currencies.
     *
     * @param from the source currency
     * @param to   the target currency
     * @return the best exchange rate or -1 if no valid path exists
     */
    public double findExchangeRate(final String from,
                                   final String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return -1;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.
                comparingDouble(node -> -node.rate));
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
