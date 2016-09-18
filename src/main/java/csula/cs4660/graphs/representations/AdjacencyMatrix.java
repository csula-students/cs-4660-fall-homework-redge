package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Adjacency matrix in a sense store the nodes in two dimensional array
 *
 * TODO: please fill the method body of this class
 */
public class AdjacencyMatrix implements Representation {
    private Node[] nodes;
    private int[][] adjacencyMatrix;

    public AdjacencyMatrix(File file) {
        try {
            Scanner input = new Scanner(file);

            int numNodes = Integer.parseInt(input.nextLine());
            nodes = new Node[numNodes];
            for (int i = 0; i < numNodes; i++) {
                nodes[i] = new Node(i);
            }

            adjacencyMatrix = new int[numNodes][numNodes];
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    adjacencyMatrix[i][j] = -1;
                }
            }
            while (input.hasNextLine()) {
                String[] temp = input.nextLine().split(":");
                adjacencyMatrix[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = Integer.parseInt(temp[2]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public AdjacencyMatrix() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
        int fromIndex = -1, toIndex = -1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x)) fromIndex = i;
            if (nodes[i].equals(y)) toIndex = i;
        }

        if (adjacencyMatrix[fromIndex][toIndex] > 0) return true;
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
        ArrayList<Node> neighbors = new ArrayList<Node>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x)) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    if (adjacencyMatrix[i][j] > 0) neighbors.add(nodes[j]);
                }

            }
        }
        return neighbors;
    }

    @Override
    public boolean addNode(Node x) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x)) return false;
        }
        Node[] newNodes = new Node[nodes.length + 1];
        for (int i = 0; i < nodes.length; i++) {
            newNodes[i] = nodes[i];
        }
        newNodes[nodes.length] = x;
        nodes = newNodes;


        int[][] newAdjacencyMatrix = new int[adjacencyMatrix.length + 1][adjacencyMatrix[0].length + 1];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
            }
        }
        for (int i = 0; i < newAdjacencyMatrix[adjacencyMatrix.length].length; i++) {
            newAdjacencyMatrix[adjacencyMatrix.length][i] = 0;
        }
        adjacencyMatrix = newAdjacencyMatrix;
        return true;
    }

    @Override
    public boolean removeNode(Node x) {
        int index = -1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x)) {
                index = i;
            }
        }
        if (index == -1) return false;

        Node[] newNodes = new Node[nodes.length - 1];
        for (int i = 0; i < nodes.length; i++) {
            if (i == index) continue;
            if (i > index) newNodes[i - 1] = nodes[i];
            else newNodes[i] = nodes[i];
        }
        nodes = newNodes;


        int[][] newAdjacencyMatrix = new int[adjacencyMatrix.length - 1][adjacencyMatrix[0].length - 1];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (i == index) continue;
            if (i > index) {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    if (j == index) continue;
                    if (j > index) {
                        newAdjacencyMatrix[i - 1][j - 1] = adjacencyMatrix[i - 1][j - 1];
                    }
                    else {
                        newAdjacencyMatrix[i - 1][j] = adjacencyMatrix[i - 1][j];
                    }
                }
            }
            else {
                for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                    if (j == index) continue;
                    if (j > index) {
                        newAdjacencyMatrix[i][j - 1] = adjacencyMatrix[i][j - 1];
                    }
                    else {
                        newAdjacencyMatrix[i][j] = adjacencyMatrix[i][j];
                    }
                }
            }
        }
        for (int i = 0; i < newAdjacencyMatrix[0].length; i++) {
            newAdjacencyMatrix[adjacencyMatrix.length - 2][i] = 0;
        }
        adjacencyMatrix = newAdjacencyMatrix;

        return true;
    }

    @Override
    public boolean addEdge(Edge x) {
        int fromIndex = -1, toIndex = -1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x.getFrom())) fromIndex = i;
            if (nodes[i].equals(x.getTo())) toIndex = i;
        }
        if (fromIndex == -1 || toIndex == -1) return false;
        if (adjacencyMatrix[fromIndex][toIndex] != -1) return false;

        adjacencyMatrix[fromIndex][toIndex] = x.getValue();
        return true;
    }

    @Override
    public boolean removeEdge(Edge x) {
        int fromIndex = -1, toIndex = -1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(x.getFrom())) fromIndex = i;
            if (nodes[i].equals(x.getTo())) toIndex = i;
        }
        if (fromIndex == -1 || toIndex == -1) return false;
        if (adjacencyMatrix[fromIndex][toIndex] == -1) return false;

        adjacencyMatrix[fromIndex][toIndex] = -1;
        return true;
    }

    @Override
    public int distance(Node from, Node to) {
        int fromIndex = -1, toIndex = -1;
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].equals(from)) fromIndex = i;
            if (nodes[i].equals(to)) toIndex = i;
        }
        if (fromIndex == -1 || toIndex == -1) return -1;
        if (adjacencyMatrix[fromIndex][toIndex] == -1) return -1;

        return adjacencyMatrix[fromIndex][toIndex];
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
