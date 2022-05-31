package pl.edu.pw.ee.grapher.graphio;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class GraphReader{
    private GraphReader(){}

    public static @Nullable Graph readFromFile(File graphFile) {
        try (var in = new Scanner(graphFile, StandardCharsets.UTF_8)){
            var rows = in.nextInt();
            var columns = in.nextInt();

            if (rows <= 0 || columns <= 0){
                throw new IllegalArgumentException("No columns or rows found!");
            }
            in.nextLine();
            var graph = new Graph(rows, columns);

            for (int i = 0; i < graph.getNumOfVertices(); i++){
                var line = in.nextLine();
                var insertion = insertGraph(graph, i, line);

                if (!insertion){
                    throw new NoSuchElementException("I was not able to insert graph!");
                }
            }
            return graph;
        } catch (IOException | NoSuchElementException | IllegalArgumentException e) {
            return null;
        }
    }

    public static boolean insertGraph(@NotNull Graph graph, int index, @NotNull String line){
        var rows = graph.getRows();
        var columns = graph.getColumns();

        try (var in = new Scanner(line.replace(':', ' '))) {
            while (in.hasNext()) {
                var hasBeenAdded = false;
                var vertex = Integer.parseInt(in.next());
                var weight = Float.parseFloat(in.next());

                if (index - columns >= 0 && index - columns < columns * rows) {
                    hasBeenAdded = VertexSetter.setUp(graph, index, vertex, weight, hasBeenAdded);
                } else {
                    graph.getVertex(index).setExistence(UP, false);
                }

                if (index + 1 < columns * rows && (index + 1) % columns != 0) {
                    hasBeenAdded = VertexSetter.setRight(graph, index, vertex, weight, hasBeenAdded);
                } else {
                    graph.getVertex(index).setExistence(RIGHT, false);
                }

                if (index + columns > 0 && index + columns < columns * rows) {
                    hasBeenAdded = VertexSetter.setDown(graph, index, vertex, weight, hasBeenAdded);
                } else {
                    graph.getVertex(index).setExistence(DOWN, false);
                }

                if (index - 1 >= 0 && index % columns != 0) {
                    hasBeenAdded = VertexSetter.setLeft(graph, index, vertex, weight, hasBeenAdded);
                } else {
                    graph.getVertex(index).setExistence(LEFT, false);
                }
                if (!hasBeenAdded) {
                    return false;
                }
            }
        } catch (NoSuchElementException exception) {
            return false;
        }

        return true;
    }
}
