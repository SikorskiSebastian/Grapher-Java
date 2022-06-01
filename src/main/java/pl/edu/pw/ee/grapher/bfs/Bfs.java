package pl.edu.pw.ee.grapher.bfs;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import static pl.edu.pw.ee.grapher.utils.Constants.*;
import java.util.Arrays;

public class Bfs implements CoherenceChecker {
    private final boolean[] visited;
    private final int[] queue;
    private final int numOfVertices;

    public Bfs(int numOfVertices) {
        this.queue = new int[numOfVertices];
        this.visited = new boolean[numOfVertices];
        this.numOfVertices = numOfVertices;
    }

    public boolean checkIfCoherent(@NotNull Graph graph) {
        if (!bfsLaunch(graph)){
            return false;
        }
        return bfsLaunch(invertGraph(graph));
    }

    public boolean bfsLaunch(@NotNull Graph graph){
        Arrays.fill(queue, -1);
        Arrays.fill(visited, false);
        var reader = 0;
        var writer = 0;

        queue[writer] = 0;
        visited[0] = true;
        writer = Bfs.calculateVertex(writer, numOfVertices);

        while (reader != writer){
            var currentVertex = queue[reader];
            reader = Bfs.calculateVertex(reader, numOfVertices);

            for (int j = 0; j < 4; j++){
                var aim = graph.getVertex(currentVertex).getConnection(j);

                if (graph.getVertex(currentVertex).getExistence(j) && !visited[aim]){
                    visited[aim] = true;
                    queue[writer] = aim;
                    writer = Bfs.calculateVertex(writer, numOfVertices);
                }
            }
        }
        for (boolean visit : visited){
            if (!visit){
                return false;
            }
        }

        return true;
    }


    public static int calculateVertex(int index, int numOfVertices){
        return (index + 1) % numOfVertices;
    }

    private static @NotNull Graph invertGraph(@NotNull Graph graph) {
        var invertedGraph = new Graph(graph.getRows(),graph.getColumns());

        for(int i = 0; i < graph.getNumOfVertices(); i++) {
            var currentVertex = graph.getVertex(i);
            var vertexUpIndex = currentVertex.getConnection(UP);
            var vertexRightIndex = currentVertex.getConnection(RIGHT);
            var vertexDownIndex = currentVertex.getConnection(DOWN);
            var vertexLeftIndex = currentVertex.getConnection(LEFT);

            if(currentVertex.getExistence(UP)) {
                Bfs.setInvertedVertex(invertedGraph, DOWN, i, vertexUpIndex);
            }
            if(currentVertex.getExistence(RIGHT)) {
                Bfs.setInvertedVertex(invertedGraph, LEFT, i, vertexRightIndex);
            }
            if(currentVertex.getExistence(DOWN)) {
                Bfs.setInvertedVertex(invertedGraph, UP, i, vertexDownIndex);
            }
            if(currentVertex.getExistence(LEFT)) {
                Bfs.setInvertedVertex(invertedGraph, RIGHT, i, vertexLeftIndex);
            }
        }

        return invertedGraph;
    }

    private static void setInvertedVertex(@NotNull Graph invertedGraph, int direction, int index, int vertexIndex){
        var currentInvertedVertex = invertedGraph.getVertex(vertexIndex);
        currentInvertedVertex.setExistence(direction,true);
        currentInvertedVertex.setConnections(direction, index);
        currentInvertedVertex.setWeight(direction, invertedGraph.getVertex(index).getWeight(direction));
    }

}
