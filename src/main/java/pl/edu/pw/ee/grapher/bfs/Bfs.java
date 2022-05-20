package pl.edu.pw.ee.grapher.bfs;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graph.Vertex;

import java.util.Arrays;
import java.util.LinkedList;

public class Bfs {
    private Bfs() {}

    public static boolean checkIfCoherent(@NotNull Graph graph) {
        boolean coherent = true;
        LinkedList<Integer> queue = new LinkedList<Integer>();
        boolean[] visited = new boolean[graph.getNumOfVertices()];
        Arrays.fill(visited, false);
        queue.add(0);

        while (!queue.isEmpty()) {
            int currentIndex = queue.getFirst();
            Vertex currentVertex = graph.getVertex(currentIndex);

            for (int i = 0; i < 4; i++) {
                if (currentVertex.getExistence(i)) {
                    if (!visited[currentVertex.getConnection(i)]) {
                        queue.add(currentVertex.getConnection(i));
                        visited[currentVertex.getConnection(i)] = true;
                    }
                }
            }
        }
        for (boolean b : visited) {
            if (!b)
                coherent = false;
        }

        Graph invertedGraph = invertGraph(graph);

        Arrays.fill(visited, false);
        queue.clear();
        queue.add(0);

        while (!queue.isEmpty()) {
            int currentIndex = queue.getFirst();
            Vertex currentVertex = invertedGraph.getVertex(currentIndex);

            for (int i = 0; i < 4; i++) {
                if (currentVertex.getExistence(i)) {
                    if (!visited[currentVertex.getConnection(i)]) {
                        queue.add(currentVertex.getConnection(i));
                        visited[currentVertex.getConnection(i)] = true;
                    }
                }
            }
        }
        for (boolean b : visited) {
            if (!b)
                coherent = false;
        }


    return coherent;
    }

    private static Graph invertGraph(Graph graph) {
        return new Graph(3,3);
    }

}
