package pl.edu.pw.ee.grapher.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.Nullable;
import pl.edu.pw.ee.grapher.bfs.Bfs;
import pl.edu.pw.ee.grapher.dijkstra.Dijkstra;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graphio.GraphReader;
import pl.edu.pw.ee.grapher.graphio.GraphSaver;
import pl.edu.pw.ee.grapher.validate.ControllerAlerts;
import pl.edu.pw.ee.grapher.validate.ControllerValidate;

import static pl.edu.pw.ee.grapher.utils.Constants.EXTENDED_MODE;
import static pl.edu.pw.ee.grapher.utils.Constants.STANDARD_MODE;

public class ButtonHandler {
    private ButtonHandler(){}

    public static @Nullable Graph genButtonAction(EntryData userData, TextField columnInput, TextField rowsInput, TextField endInput, TextField startInput, TextArea consoleOutput){
        if (!ControllerValidate.setUserGenData(userData, columnInput, rowsInput, endInput, startInput)){
            ControllerInitializer.updateConsole("Wrong input!\n", consoleOutput);
            return null;
        }
        ControllerInitializer.updateConsole(String.format("Graph (%d x%d) was generated successfully, with edge weights in range of (%.2f %.2f)%n",userData.getColumns(),userData.getRows(),userData.getRangeStart(),userData.getRangeEnd()), consoleOutput);
        return ControllerInitializer.makeGraph(userData);
    }

    public static @Nullable PathData searchButtonAction(Graph graph, EntryData userData, TextArea consoleOutput, TextField startPointInput, TextField endPointInput){
        if (graph == null){
            ControllerAlerts.popNullGraphAlert();
            return null;
        }
        userData.setColumns(graph.getColumns());
        userData.setRows(graph.getRows());
        if (!ControllerValidate.setUserReadData(userData, startPointInput, endPointInput)){
            ControllerInitializer.updateConsole("Wrong start or end.\n", consoleOutput);
            return null;
        }

        var finder = new Bfs(graph.getNumOfVertices());
        if(!finder.checkIfCoherent(graph)){
            ControllerInitializer.updateConsole("Selected graph is not coherent!\n", consoleOutput);
            ControllerAlerts.popNotCoherentGraph();
            return null;
        }
        if (userData.getStartPoint() == userData.getEndPoint()){
            ControllerAlerts.popSamePointsInfo();
            return null;
        }

        var shortPathFinder = new Dijkstra(graph.getNumOfVertices());
        var path = shortPathFinder.findPath(graph, userData);

        if(userData.getPrintMode() == STANDARD_MODE) {
            ControllerInitializer.updateConsole(PathPrinter.printStandardPathToString(PathData.pathInOrder(path),path), consoleOutput);
        } else if (userData.getPrintMode() == EXTENDED_MODE) {
            ControllerInitializer.updateConsole(PathPrinter.printExtendedPathToString(PathData.pathInOrder(path),path), consoleOutput);
        }
        return path;
    }

    public static void saveButtonAction(Graph graph, TextArea consoleOutput, TextField fileInput){
        if (ControllerValidate.isGraphNull(graph)){
            ControllerInitializer.updateConsole("No graph to save!\n", consoleOutput);
            return;
        }
        var fc = new FileChooser();
        var file = fc.showOpenDialog(null);

        if (ControllerValidate.isFileNullGen(file)){
            ControllerInitializer.updateConsole("No file!\n", consoleOutput);
            return;
        }

        GraphSaver.saveToFile(graph, file);
        fileInput.setText(file.getName());
        ControllerInitializer.updateConsole(String.format("Graph (%d x %d) was successfully saved to a file (%s)%n",graph.getColumns(), graph.getRows(),file.getName()), consoleOutput);
    }

    public static @Nullable Graph openButtonAction(TextArea consoleOutput, TextField fileInput){
        var fc = new FileChooser();
        var file = fc.showOpenDialog(null);
        if (ControllerValidate.isFileNullRead(file)){
            ControllerInitializer.updateConsole("No file!\n", consoleOutput);
            return null;
        }

        var graph = GraphReader.readFromFile(file);
        if (graph == null){
            ControllerAlerts.popNullReadAlert();
            ControllerInitializer.updateConsole("Graph is null.\n", consoleOutput);
            return null;
        }

        fileInput.setText(file.getName());
        ControllerInitializer.updateConsole(String.format("Graph (%d x %d) was successfully loaded from a file (%s)%n",graph.getColumns(), graph.getRows(), file.getName()), consoleOutput);
        return graph;
    }
}
