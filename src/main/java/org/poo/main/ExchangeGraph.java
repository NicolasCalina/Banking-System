package org.poo.main;

import java.util.*;

public class ExchangeGraph {
    // Graful de schimb va fi o hartă de monede către lista de vecini
    private Map<String, List<Edge>> adjList;

    // Clasa pentru o muchie (pentru a stoca monedele și rata de schimb)
    public static class Edge {
        String toCurrency;
        double rate;

        public Edge(String toCurrency, double rate) {
            this.toCurrency = toCurrency;
            this.rate = rate;
        }
    }

    // Constructor
    public ExchangeGraph() {
        adjList = new HashMap<>();
    }

    // Adăugăm o monedă
    public void addCurrency(String currency) {
        adjList.putIfAbsent(currency, new ArrayList<>());
    }

    // Adăugăm o relație de schimb între două monede
    public void addExchangeRate(String from, String to, double rate) {
        addCurrency(from);
        addCurrency(to);
        adjList.get(from).add(new Edge(to, rate));
        adjList.get(to).add(new Edge(from, 1 / rate)); // inversul ratei de schimb pentru graf neorientat
    }

    // Găsim distanța între două monede (adică, rata de schimb) folosind BFS
    public double findExchangeRate(String from, String to) {
        if (!adjList.containsKey(from) || !adjList.containsKey(to)) {
            return -1; // Nu există această monedă
        }

        // BFS pentru a găsi cea mai scurtă cale
        Queue<String> queue = new LinkedList<>();
        Map<String, Double> distances = new HashMap<>();
        queue.add(from);
        distances.put(from, 1.0);

        while (!queue.isEmpty()) {
            String currentCurrency = queue.poll();
            double currentRate = distances.get(currentCurrency);

            for (Edge neighbor : adjList.get(currentCurrency)) {
                if (!distances.containsKey(neighbor.toCurrency)) {
                    double newRate = currentRate * neighbor.rate;
                    distances.put(neighbor.toCurrency, newRate);
                    queue.add(neighbor.toCurrency);

                    if (neighbor.toCurrency.equals(to)) {
                        return newRate;
                    }
                }
            }
        }

        return -1; // Nu există un drum de la `from` la `to`
    }
}