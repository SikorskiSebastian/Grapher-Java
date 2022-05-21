package pl.edu.pw.ee.grapher.graphio;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class VertexSetter {
    private VertexSetter(){}

    public static boolean setUp(@NotNull Graph graph, int index, int vertex, float weight, boolean hasBeenAdded){
        if (vertex == index - graph.getColumns()) {
            graph.getVertex(index).setExistence(UP, true);
            graph.getVertex(index).setConnections(UP, index - graph.getColumns());
            graph.getVertex(index).setWeight(UP, weight);
            return true;
        }
        return hasBeenAdded;
    }

    public static boolean setDown(@NotNull Graph graph, int index, int vertex, float weight, boolean hasBeenAdded){
        if (vertex == index + graph.getColumns()) {
            graph.getVertex(index).setExistence(DOWN, true);
            graph.getVertex(index).setConnections(DOWN, index + graph.getColumns());
            graph.getVertex(index).setWeight(DOWN, weight);
            return true;
        }
        return hasBeenAdded;
    }

    public static boolean setRight(@NotNull Graph graph, int index, int vertex, float weight, boolean hasBeenAdded){
        if (vertex == index + 1) {
            graph.getVertex(index).setExistence(RIGHT, true);
            graph.getVertex(index).setConnections(RIGHT, index + 1);
            graph.getVertex(index).setWeight(RIGHT, weight);
            return true;
        }
        return hasBeenAdded;
    }

    public static boolean setLeft(@NotNull Graph graph, int index, int vertex, float weight, boolean hasBeenAdded){
        if (vertex == index - 1) {
            graph.getVertex(index).setExistence(LEFT, true);
            graph.getVertex(index).setConnections(LEFT, index - 1);
            graph.getVertex(index).setWeight(LEFT, weight);
            return true;
        }
        return hasBeenAdded;
    }
}
