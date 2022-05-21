package pl.edu.pw.ee.grapher.bfs;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graph.Vertex;
import static pl.edu.pw.ee.grapher.utils.Constants.*;


import java.util.Arrays;
import java.util.LinkedList;

public class Bfs {
    private Bfs() {}

    public static boolean checkIfCoherent(@NotNull Graph graph) {
        var queue = new LinkedList<Integer>();
        var visited = new boolean[graph.getNumOfVertices()];
        Arrays.fill(visited, false);
        queue.add(0);

        while (!queue.isEmpty()) {
            int currentIndex = queue.removeFirst();
            Vertex currentVertex = graph.getVertex(currentIndex);

            for (int i = 0; i < 4; i++) {
                if (currentVertex.getExistence(i) && !visited[currentVertex.getConnection(i)]) {
                    queue.add(currentVertex.getConnection(i));
                    visited[currentVertex.getConnection(i)] = true;
                }
            }
        }
        visited[0] = true;
        for (boolean b : visited) {
            if (!b) {
                return false;
            }
        }

        Graph invertedGraph = invertGraph(graph);

        Arrays.fill(visited, false);
        queue.clear();
        queue.add(0);

        while (!queue.isEmpty()) {
            int currentIndex = queue.removeFirst();
            Vertex currentVertex = invertedGraph.getVertex(currentIndex);

            for (int i = 0; i < 4; i++) {
                if (currentVertex.getExistence(i) && !visited[currentVertex.getConnection(i)]) {
                    queue.add(currentVertex.getConnection(i));
                    visited[currentVertex.getConnection(i)] = true;
                }
            }
        }
        visited[0] = true;
        for (boolean b : visited) {
            if (!b)
                return false;
        }

        return true;
    }

    private static @NotNull Graph invertGraph(@NotNull Graph graph) {
        var invertedGraph = new Graph(graph.getRows(),graph.getColumns());
        int vertexUpIndex;
        int vertexRightIndex;
        int vertexDownIndex;
        int vertexLeftIndex;
        Vertex currentInvertedVertex;

        for(int i = 0; i < graph.getNumOfVertices(); i++) {
            Vertex currentVertex = graph.getVertex(i);

            vertexUpIndex = currentVertex.getConnection(UP);
            vertexRightIndex = currentVertex.getConnection(RIGHT);
            vertexDownIndex = currentVertex.getConnection(DOWN);
            vertexLeftIndex = currentVertex.getConnection(LEFT);

            if(vertexUpIndex != -1) {
                currentInvertedVertex = invertedGraph.getVertex(vertexUpIndex);
                currentInvertedVertex.setExistence(DOWN,true);
                currentInvertedVertex.setConnections(DOWN, i);
                currentInvertedVertex.setWeight(DOWN, currentVertex.getWeight(UP));
            }
            if(vertexRightIndex != -1) {
                currentInvertedVertex = invertedGraph.getVertex(vertexRightIndex);
                currentInvertedVertex.setExistence(LEFT,true);
                currentInvertedVertex.setConnections(LEFT, i);
                currentInvertedVertex.setWeight(LEFT, currentVertex.getWeight(RIGHT));
            }
            if(vertexDownIndex != -1) {
                currentInvertedVertex = invertedGraph.getVertex(vertexDownIndex);
                currentInvertedVertex.setExistence(UP,true);
                currentInvertedVertex.setConnections(UP, i);
                currentInvertedVertex.setWeight(UP, currentVertex.getWeight(DOWN));
            }
            if(vertexLeftIndex != -1) {
                currentInvertedVertex = invertedGraph.getVertex(vertexLeftIndex);
                currentInvertedVertex.setExistence(RIGHT,true);
                currentInvertedVertex.setConnections(RIGHT, i);
                currentInvertedVertex.setWeight(RIGHT, currentVertex.getWeight(LEFT));
            }
        }

        return invertedGraph;
    }

}
