package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Breadth first search
 */
public class BFS implements SearchStrategy {
    @Override
    public List<Edge> search(Graph graph, Node source, Node dist) {
        ArrayList<Edge> edges = new ArrayList<Edge>();

        // enqueue neighbors of source
        // iterate: check if n == dist; if not, enqueue n's neighbors and dequeue n
        return null;
    }
}
