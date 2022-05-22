package pl.edu.pw.ee.grapher.graphio;

import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static pl.edu.pw.ee.grapher.utils.Constants.GRAPHER_ERROR;

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
            Files.writeString(graphFile.toPath(), myGraph.toString(), StandardCharsets.UTF_8);
        } catch (IOException exception) {
            GraphSaver.popEncodingError();
        }
    }

    public static void popEncodingError(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("IOException");
        alert.setContentText("There is a problem with encoding.");
        alert.show();
    }
}
