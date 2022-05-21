package pl.edu.pw.ee.grapher.generator;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;

import static pl.edu.pw.ee.grapher.utils.Constants.*;
import static pl.edu.pw.ee.grapher.utils.Constants.LEFT;

public class WageMode extends GraphGenerator {
    @Override
    public void makeConnectionFromVertex(int index, @NotNull Graph graph, EntryData userData) {
        int columns = graph.getColumns();
        int rows = graph.getRows();

        if (index - columns >= 0 && index - columns < columns * rows) {
            vertexSetter(graph, userData, new VertexData(index, index - columns, UP));
        }
        if (index + 1 < columns * rows && (index + 1) % columns != 0) {
            vertexSetter(graph, userData, new VertexData(index, index + 1, RIGHT));
        }
        if (index + columns > 0 && index + columns < columns * rows) {
            vertexSetter(graph, userData, new VertexData(index, index + columns, DOWN));
        }
        if (index - 1 >= 0 && index % columns != 0) {
            vertexSetter(graph, userData, new VertexData(index, index - 1, LEFT));
        }
    }
}
