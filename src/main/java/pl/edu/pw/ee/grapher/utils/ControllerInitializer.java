package pl.edu.pw.ee.grapher.utils;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.generator.EdgeMode;
import pl.edu.pw.ee.grapher.generator.GraphGenerator;
import pl.edu.pw.ee.grapher.generator.WageMode;
import pl.edu.pw.ee.grapher.graph.Graph;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class ControllerInitializer {
    private ControllerInitializer(){}

    public static void addToPathList(PathData path, @NotNull ObservableList<PathData> itemsAsPathData, ListView<PathData> pathListView) {
        for (PathData p : itemsAsPathData){
            if(path.equals(p)){
                return;
            }
        }
        itemsAsPathData.add(path);
        pathListView.setItems(itemsAsPathData);

    }

    public static void updateConsole (String msg, @NotNull TextArea consoleOutput) {
        consoleOutput.setText(consoleOutput.getText() + msg);
        consoleOutput.setScrollTop(Double.MAX_VALUE);
    }

    public static Graph makeGraph(@NotNull EntryData userData){
        Graph graph = null;
        if(userData.getMode() == WEIGHT_MODE) {
            var graphGenW = new WageMode();
            graph = new Graph(userData.getRows(), userData.getColumns());
            graphGenW.generate(graph, userData);
        } else if (userData.getMode() == EDGE_MODE) {
            var graphGenE = new EdgeMode();
            graph = new Graph(userData.getRows(), userData.getColumns());
            graphGenE.generate(graph, userData);
        } else if (userData.getMode() == RANDOM_MODE) {
            var graphGenR = new GraphGenerator();
            graph = new Graph(userData.getRows(), userData.getColumns());
            graphGenR.generate(graph, userData);
        }
        return graph;
    }
}
