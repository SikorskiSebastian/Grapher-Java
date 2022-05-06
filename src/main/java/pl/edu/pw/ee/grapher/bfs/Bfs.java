package pl.edu.pw.ee.grapher.bfs;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.util.Arrays;

public class Bfs {
    public static boolean checkIfCoherent(@NotNull Graph graph){
        int numOfVertices = graph.getNumOfVertices();
        int[] queue = new int[numOfVertices];
        Arrays.fill(queue, -1);

        for (int i = 0; i < numOfVertices; i++){
            boolean[] visited = new boolean[numOfVertices];
            int reader = 0;
            int writer = 0;

            queue[writer] = i;
            visited[i] = true;
            writer = Bfs.calculateVertex(writer, numOfVertices);

            while (reader != writer){
                int currentVertex = queue[reader];
                reader = Bfs.calculateVertex(reader, numOfVertices);

                for (int j = 0; j < 4; j++){
                    int aim = graph.getVertex(currentVertex).getConnection(j);

                    if (graph.getVertex(currentVertex).getExistence(j)){
                        if (!visited[aim]){
                            visited[aim] = true;
                            queue[writer] = aim;
                            writer = Bfs.calculateVertex(writer, numOfVertices);
                        }
                    }
                }
            }

            for (int k = 0; k < numOfVertices; k++){
                if (!visited[k]){
                    return false;
                }
            }
        }

        return true;
    }

    public static int calculateVertex(int index, int numOfVertices){
        return (index + 1) % numOfVertices;
    }
}
