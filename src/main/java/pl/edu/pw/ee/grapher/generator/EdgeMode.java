package pl.edu.pw.ee.grapher.generator;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.bfs.Bfs;
import pl.edu.pw.ee.grapher.graph.Graph;

public class EdgeMode extends GraphGenerator{
    @Override
    public void generate(@NotNull Graph graph, EntryData userData) {
        var numOfTries = 0;
        var maxNumOfTries = 500;
        var numOfVertices = graph.getNumOfVertices();

        while (!Bfs.checkIfCoherent(graph)){
            for (int i = 0; i < numOfVertices; i++) {
                makeConnectionFromVertex(i, graph, userData);
            }
            numOfTries++;

            if(numOfTries >= maxNumOfTries){
                var alert = new Alert(Alert.AlertType.CONFIRMATION, "If you select NO graph will not be coherent.", ButtonType.YES, ButtonType.NO);
                alert.setTitle("Max number of tries exceeded");
                alert.setHeaderText("Do you want to continue?");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.NO){
                    break;
                }
                maxNumOfTries *= 2;
            }
        }

    }
}
