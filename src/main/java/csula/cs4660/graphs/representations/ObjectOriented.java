package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 *
 * TODO: Please fill the body of methods in this class
 */
public class ObjectOriented implements Representation {
    private Collection<Node> nodes;
    private Collection<Edge> edges;

    public ObjectOriented(File file) {
        nodes = new ArrayList<Node>();
        edges = new ArrayList<Edge>();

        try {
            Scanner input = new Scanner(file);

            int numNodes = Integer.parseInt(input.nextLine());
            for (int i = 0; i < numNodes; i++) {
                nodes.add(new Node(i));
            }

            while (input.hasNextLine()) {
                String[] temp = input.nextLine().split(":");
                for (Node n : nodes) {
                    if (n.getData().equals(Integer.parseInt(temp[0]))) {
                        for (Node k : nodes) {
                            if (k.getData().equals(Integer.parseInt(temp[1]))) {
                                edges.add(new Edge(n, k, Integer.parseInt(temp[2])));
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObjectOriented() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        for (Edge e : edges) {
            if (e.getFrom().equals(x) && e.getTo().equals(y)) return true;
        }
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (Edge e : edges) {
            if (e.getFrom().equals(x)) neighbors.add(e.getTo());
        }
        return neighbors;
    }

    @Override
    public boolean addNode(Node x) {
        if (nodes.contains(x)) return false;
        nodes.add(x);
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if (!nodes.contains(x)) return false;
        nodes.remove(x);

        ArrayList<Edge> edgeCopy = new ArrayList<Edge>();
        edgeCopy.addAll(edges);
        for (Edge e : edgeCopy) {
            if (e.getTo().equals(x) || e.getFrom().equals(x)) edges.remove(e);
        }

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        if (edges.contains(x)) return false;
        edges.add(x);
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if (!edges.contains(x)) return false;
        edges.remove(x);
        return true;
    }

    @Override
    public int distance(Node from, Node to) {
        for (Edge e : edges) {
            if (e.getFrom().equals(from) && e.getTo().equals(to)) return e.getValue();
        }

        for (Edge e : edges) {
            if (e.getFrom().equals(from)) {
                int partCost = distance(e.getTo(), to);
                if (partCost > 0) return partCost + e.getValue();
            }
        }

        return -1;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
