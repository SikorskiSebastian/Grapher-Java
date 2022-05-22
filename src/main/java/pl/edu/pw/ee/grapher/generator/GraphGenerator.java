package pl.edu.pw.ee.grapher.generator;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class GraphGenerator implements Generator {
    public void generate(@NotNull Graph graph, EntryData userData) {
        for (int i = 0; i < graph.getNumOfVertices(); i++) {
            makeConnectionFromVertex(i, graph, userData);
        }
    }

    public static float generateWeight(float start, float end) {
        return (float) ((Math.random() * (end - start)) + start);
    }

    public static boolean generateEdge(){
        return (Math.random() < 0.75);
    }

    public void makeConnectionFromVertex(int index, @NotNull Graph graph, EntryData userData) {
        genLeft(graph, userData, index);
        genUp(graph, userData, index);
        genDown(graph, userData, index);
        genRight(graph, userData, index);
    }

    private void genUp(Graph graph, EntryData userData, int index){
        if (generateEdge()) {
            if (index - graph.getColumns() >= 0 && index - graph.getColumns() < graph.getColumns() * graph.getRows()) {
                vertexSetter(graph, userData, new VertexData(index, index - graph.getColumns(), UP));
            }
        } else {
            graph.getVertex(index).setExistence(UP, false);
        }
    }

    private void genLeft(Graph graph, EntryData userData, int index){
        if(generateEdge()){
            if(index - 1 >= 0 && index % graph.getColumns() != 0){
                vertexSetter(graph, userData, new VertexData(index, index - 1, LEFT));
            }
        } else {
            graph.getVertex(index).setExistence(LEFT, false);
        }
    }

    private void genRight(Graph graph, EntryData userData, int index){
        if(generateEdge()) {
            if (index + 1 < graph.getColumns() * graph.getRows() && (index + 1) % graph.getColumns() != 0) {
                vertexSetter(graph, userData, new VertexData(index, index + 1, RIGHT));
            }
        } else {
            graph.getVertex(index).setExistence(RIGHT, false);
        }
    }

    private void genDown(Graph graph, EntryData userData, int index){
        if(generateEdge()) {
            if(index + graph.getColumns() > 0 && index + graph.getColumns() < graph.getColumns() * graph.getRows()){
                vertexSetter(graph, userData, new VertexData(index, index + graph.getColumns(), DOWN));
            }
        } else {
            graph.getVertex(index).setExistence(DOWN, false);
        }
    }

    public static void vertexSetter(@NotNull Graph graph, @NotNull EntryData entry, @NotNull VertexData data){
        graph.getVertex(data.getIndex()).setExistence(data.getDirection(), true);
        graph.getVertex(data.getIndex()).setWeight(data.getDirection(), generateWeight(entry.getRangeStart(), entry.getRangeEnd()));
        graph.getVertex(data.getIndex()).setConnections(data.getDirection(), data.getConnection());
    }
}
