package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Adjacency list is probably the most common implementation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {
    private Map<Node, Collection<Edge>> adjacencyList;

    public AdjacencyList(File file) {
        try {
            Scanner input = new Scanner(file);
            adjacencyList = new HashMap<Node, Collection<Edge>>();

            int numNodes = Integer.parseInt(input.nextLine());
            Node[] nodes = new Node[numNodes];
            for (int i = 0; i < numNodes; i++) {
                nodes[i] = new Node(i);
            }

            int nodeNum = 0;
            Collection<Edge> tempEdges = new ArrayList<Edge>();
            while (input.hasNextLine()) {
                String[] temp = input.nextLine().split(":");
                if (Integer.parseInt(temp[0]) != nodeNum) {
                    adjacencyList.put(nodes[nodeNum], tempEdges);
                    nodeNum++;
                    tempEdges = new ArrayList<Edge>();
                }
                tempEdges.add(new Edge(nodes[Integer.parseInt(temp[0])], nodes[Integer.parseInt(temp[1])], Integer.parseInt(temp[2])));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AdjacencyList() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        Collection<Edge> neighbors = adjacencyList.get(x);
        if (neighbors == null || neighbors.isEmpty()) return false;
        for (Edge e : neighbors) {
            if (e.getTo().equals(y)) return true;
        }
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        Collection<Edge> edges = adjacencyList.get(x);
        ArrayList<Node> neighbors = new ArrayList<Node>();
        if (edges == null || edges.isEmpty()) return neighbors;
        for (Edge e : edges) {
            neighbors.add(e.getTo());
        }
        return neighbors;
    }

    @Override
    public boolean addNode(Node x) {
        if (adjacencyList.containsKey(x)) return false;
        adjacencyList.put(x, new ArrayList<Edge>());
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        if (!adjacencyList.containsKey(x)) return false;
        adjacencyList.remove(x);
        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        if (adjacencyList.get(x.getFrom()).contains(x)) return false;
        adjacencyList.get(x.getFrom()).add(x);
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        if (adjacencyList.get(x.getFrom()).contains(x)) {
            adjacencyList.get(x.getFrom()).remove(x);
            return true;
        }
        return false;
    }

    @Override
    public int distance(Node from, Node to) {
        if (from.equals(to)) return 0;
        ArrayList<Node> neighbors = (ArrayList<Node>) neighbors(from);
        if (neighbors.contains(to)) {
            ArrayList<Edge> edges = (ArrayList<Edge>) adjacencyList.get(from);
            for (Edge e : edges) {
                if (e.getTo() == to) return e.getValue();
            }
            for (Edge e : edges) {
                return e.getValue() + distance(e.getFrom(), e.getTo());
            }
        }
        return -1;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
