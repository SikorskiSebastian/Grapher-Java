package pl.edu.pw.ee.grapher.bfs;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.util.Arrays;

public class Bfs {
    private Bfs() {}

    public static boolean checkIfCoherent(@NotNull Graph graph){
        var queue = new int[graph.getNumOfVertices()];
        Arrays.fill(queue, -1);

        for (int i = 0; i < graph.getNumOfVertices(); i++){
            var visited = new boolean[graph.getNumOfVertices()];
            int reader = 0;
            int writer = 0;

            queue[writer] = i;
            visited[i] = true;
            writer = Bfs.calculateVertex(writer, graph.getNumOfVertices());

            while (reader != writer){
                int currentVertex = queue[reader];
                reader = Bfs.calculateVertex(reader, graph.getNumOfVertices());

                for (int j = 0; j < 4; j++){
                    int aim = graph.getVertex(currentVertex).getConnection(j);

                    if (graph.getVertex(currentVertex).getExistence(j) && !visited[aim]){
                        queue[writer] = aim;
                        visited[aim] = true;
                        writer = Bfs.calculateVertex(writer, graph.getNumOfVertices());
                    }
                }
            }

            if (!Bfs.checkIfVisited(visited, graph.getNumOfVertices())){
                return false;
            }
        }

        return true;
    }

    private static boolean checkIfVisited(boolean[] visited, int numOfVertices){
        for (int k = 0; k < numOfVertices; k++){
            if (!visited[k]){
                return false;
            }
        }
        return true;
    }

    public static int calculateVertex(int index, int numOfVertices){
        return (index + 1) % numOfVertices;
    }
}
