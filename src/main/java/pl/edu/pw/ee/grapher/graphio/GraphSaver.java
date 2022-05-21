package pl.edu.pw.ee.grapher.graphio;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class GraphSaver {
    private GraphSaver(){}

    public static void saveToFile(@NotNull Graph graph, File graphFile){
        var myGraph = new StringBuilder();

        myGraph.append(graph.getRows()).append(" ").append(graph.getColumns()).append("\n");

        for (int i = 0; i < graph.getNumOfVertices(); i++){
            myGraph.append("\t");
            for (int j = 0; j < 4; j++){
                if (graph.getVertex(i).getExistence(j)){
                    myGraph.append(" ").append(graph.getVertex(i).getConnection(j)).append(" :");
                    myGraph.append(graph.getVertex(i).getWeight(j)).append(" ");
                }
            }
            myGraph.append("\n");
        }

        try {
            Files.writeString(graphFile.toPath(), myGraph.toString());
        } catch (IOException exception) {
            System.err.println("I can't write to this file!");
            exception.printStackTrace();
        }
    }
}
