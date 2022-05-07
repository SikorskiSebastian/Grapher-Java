package pl.edu.pw.ee.grapher.graphio;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static pl.edu.pw.ee.grapher.Constants.*;

public class GraphReader{
    public static @NotNull Graph readFromFile(File graphFile) throws FileNotFoundException {
        Scanner in = new Scanner(graphFile);
        int rows = in.nextInt();
        int columns = in.nextInt();

        if (rows <= 0 || columns <= 0){
            in.close();
            throw new IllegalArgumentException("No columns or rows found!");
        }
        in.nextLine();
        Graph graph = new Graph(rows, columns);

        for (int i = 0; i < graph.getNumOfVertices(); i++){
            String line = in.nextLine();
            boolean insertion = insertGraph(graph, i, line);

            if (!insertion){
                in.close();
                throw new IllegalStateException("I was not able to insert graph!");
            }
        }

        in.close();
        return graph;
    }

    public static boolean insertGraph(@NotNull Graph graph, int index, String line){
        Scanner in = new Scanner(line);
        int rows = graph.getRows();
        int columns = graph.getColumns();

        while (in.hasNextInt()){
            boolean hasBeenAdded = false;
            int vertex = in.nextInt();
            in.skip("[\\s]*:");
            float weight = in.nextFloat(); //to wywala error

            if (index - columns >= 0 && index - columns < columns * rows){
                if (vertex == index - columns){
                    graph.getVertex(index).setExistence(UP, true);
                    graph.getVertex(index).setConnections(UP, index - columns);
                    graph.getVertex(index).setWeight(UP, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(UP, false);
            }
            if(index + 1 < columns * rows && (index+1) % columns != 0){
                if (vertex == index + 1){
                    graph.getVertex(index).setExistence(RIGHT, true);
                    graph.getVertex(index).setConnections(RIGHT, index + 1);
                    graph.getVertex(index).setWeight(RIGHT, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(RIGHT, false);
            }
            if (index + columns > 0 && index + columns < columns * rows){
                if(vertex == index + columns){
                    graph.getVertex(index).setExistence(DOWN, true);
                    graph.getVertex(index).setConnections(DOWN, index + columns);
                    graph.getVertex(index).setWeight(DOWN, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(DOWN, false);
            }
            if(index - 1 >= 0 && index % columns != 0){
                if(vertex == index - 1){
                    graph.getVertex(index).setExistence(LEFT, true);
                    graph.getVertex(index).setConnections(LEFT, index - 1);
                    graph.getVertex(index).setWeight(LEFT, weight);
                    hasBeenAdded = true;
                }
            } else {
                graph.getVertex(index).setExistence(LEFT, false);
            }

            if (!hasBeenAdded){
                in.close();
                return false;
            }
        }

        in.close();
        return true;
    }
}
