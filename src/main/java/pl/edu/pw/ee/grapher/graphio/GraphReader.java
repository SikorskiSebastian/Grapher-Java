package pl.edu.pw.ee.grapher.graphio;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class GraphReader{
    private GraphReader(){}

    public static @Nullable Graph readFromFile(File graphFile) {
        try (var in = new Scanner(graphFile, StandardCharsets.UTF_8)){
            int rows = in.nextInt();
            int columns = in.nextInt();

            if (rows <= 0 || columns <= 0){
                throw new IllegalArgumentException("No columns or rows found!");
            }
            in.nextLine();
            var graph = new Graph(rows, columns);

            for (int i = 0; i < graph.getNumOfVertices(); i++){
                String line = in.nextLine();
                var insertion = insertGraph(graph, i, line);

                if (!insertion){
                    throw new IllegalStateException("I was not able to insert graph!");
                }
            }

            return graph;
        } catch (InputMismatchException | IOException e) {
            System.err.println("Wrong arguments given!");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean insertGraph(@NotNull Graph graph, int index, String line) throws InputMismatchException{
        var in = new Scanner(line);
        int rows = graph.getRows();
        int columns = graph.getColumns();

        while (in.hasNextInt()) {
            boolean hasBeenAdded = false;
            int vertex = in.nextInt();
            String s = in.nextLine();
            in.close();
            in = new Scanner(s.substring(s.indexOf(":") + 1));
            float weight = Float.parseFloat(in.next());

            if (index - columns >= 0 && index - columns < columns * rows) {
                if (vertex == index - columns) {
                    graph.getVertex(index).setExistence(UP, true);
                    graph.getVertex(index).setConnections(UP, index - columns);
                    graph.getVertex(index).setWeight(UP, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(UP, false);
            }
            if (index + 1 < columns * rows && (index + 1) % columns != 0) {
                if (vertex == index + 1) {
                    graph.getVertex(index).setExistence(RIGHT, true);
                    graph.getVertex(index).setConnections(RIGHT, index + 1);
                    graph.getVertex(index).setWeight(RIGHT, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(RIGHT, false);
            }
            if (index + columns > 0 && index + columns < columns * rows) {
                if (vertex == index + columns) {
                    graph.getVertex(index).setExistence(DOWN, true);
                    graph.getVertex(index).setConnections(DOWN, index + columns);
                    graph.getVertex(index).setWeight(DOWN, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(DOWN, false);
            }
            if (index - 1 >= 0 && index % columns != 0) {
                if (vertex == index - 1) {
                    graph.getVertex(index).setExistence(LEFT, true);
                    graph.getVertex(index).setConnections(LEFT, index - 1);
                    graph.getVertex(index).setWeight(LEFT, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(LEFT, false);
            }

            if (!hasBeenAdded) {
                in.close();
                return false;
            }
        }

        in.close();
        return true;
    }
}
