package pl.edu.pw.ee.grapher;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import pl.edu.pw.ee.grapher.graphics.GraphPrinting;
import pl.edu.pw.ee.grapher.validate.ControllerAlerts;
import pl.edu.pw.ee.grapher.validate.ControllerValidate;
import pl.edu.pw.ee.grapher.bfs.Bfs;
import pl.edu.pw.ee.grapher.dijkstra.Dijkstra;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.generator.EdgeMode;
import pl.edu.pw.ee.grapher.generator.GraphGenerator;
import pl.edu.pw.ee.grapher.generator.WageMode;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graphio.GraphReader;
import pl.edu.pw.ee.grapher.graphio.GraphSaver;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.utils.PathPrinter;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class GrapherController implements Initializable {
    @FXML
    private Canvas graphCanvas;
    @FXML
    private AnchorPane scrollAnchor;
    @FXML
    private Button genButton;
    @FXML
    private Button openButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button searchButton;
    @FXML
    private RadioButton wageModeRB;
    @FXML
    private RadioButton edgeModeRB;
    @FXML
    private RadioButton randomModeRB;
    @FXML
    private RadioButton standardRB;
    @FXML
    private RadioButton extendedRB;
    @FXML
    private TextField columnInput;
    @FXML
    private TextField rowsInput;
    @FXML
    private TextField startInput;
    @FXML
    private TextField endInput;
    @FXML
    private TextField fileInput;
    @FXML
    private TextField startPointInput;
    @FXML
    private TextField endPointInput;
    @FXML
    private TextArea consoleOutput;
    @FXML
    private TitledPane pathListTitlePane;
    @FXML
    private ListView<PathData> pathListView;
    private ObservableList<PathData> itemsAsPathData;
    private EntryData userData;
    private Graph graph;
    private PathData path;
    private HashMap<Integer, Point2D> canvasLocationOfNodes;
    private GraphicsContext gc;
    private float pointSize;
    private int numberClicked = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGrapher();

        genButton.setOnAction(event -> {
            if (!ControllerValidate.setUserGenData(userData, columnInput, rowsInput, endInput, startInput)){
                updateConsole("Wrong input!\n");
                return;
            }
            makeGraph();
            updateConsole(String.format("Graph (%d x%d) was generated successfully, with edge weights in range of (%.2f %.2f)%n",userData.getColumns(),userData.getRows(),userData.getRangeStart(),userData.getRangeEnd()));
            GraphPrinting.printGraph(graph, pointSize, gc, graphCanvas, scrollAnchor, canvasLocationOfNodes);
            pathListView.getItems().clear();
        });

        saveButton.setOnAction(event -> {
            if (ControllerValidate.isGraphNull(graph)){
                updateConsole("No graph to save!\n");
                return;
            }
            var fc = new FileChooser();
            var file = fc.showOpenDialog(null);

            if (ControllerValidate.isFileNullGen(file)){
                updateConsole("No file!\n");
                return;
            }

            GraphSaver.saveToFile(graph, file);
            fileInput.setText(file.getName());
            updateConsole(String.format("Graph (%d x %d) was successfully saved to a file (%s)%n",graph.getColumns(), graph.getRows(),file.getName()));
        });

        openButton.setOnAction(event -> {
            var fc = new FileChooser();
            var file = fc.showOpenDialog(null);
            if (ControllerValidate.isFileNullRead(file)){
                updateConsole("No file!\n");
                return;
            }

            graph = GraphReader.readFromFile(file);
            if (!ControllerValidate.isGraphRead(graph)){
                updateConsole("Graph is null.\n");
                return;
            }

            fileInput.setText(file.getName());
            updateConsole(String.format("Graph (%d x %d) was successfully loaded from a file (%s)%n",graph.getColumns(), graph.getRows(), file.getName()));
            GraphPrinting.printGraph(graph, pointSize, gc, graphCanvas, scrollAnchor, canvasLocationOfNodes);
        });

        wageModeRB.setOnAction(event -> userData.setMode(WEIGHT_MODE));
        edgeModeRB.setOnAction(event -> userData.setMode(EDGE_MODE));
        randomModeRB.setOnAction(event -> userData.setMode(RANDOM_MODE));
        standardRB.setOnAction(event -> userData.setPrintMode(STANDARD_MODE));
        extendedRB.setOnAction(event -> userData.setPrintMode(EXTENDED_MODE));

        graphCanvas.setOnMouseClicked(event -> {
            if (numberClicked % 2 == 0) {
                startPointInput.clear();
                endPointInput.clear();
            }
            numberClicked++;
            var pointClicked = new Point2D(event.getX(), event.getY());

            if (graph == null) {
                return;
            }

            for (int index = 0; index < graph.getNumOfVertices(); index++) {
                var coordsOfCenter = canvasLocationOfNodes.get(index);

                if (coordsOfCenter == null){
                    return;
                }

                if (pointClicked.distance(coordsOfCenter) <= pointSize / 2) {
                    if (numberClicked % 2 == 1) {
                        startPointInput.setText(String.valueOf(index));
                    } else if (numberClicked % 2 == 0) {
                        endPointInput.setText(String.valueOf(index));
                        searchButton.fire();
                    }
                }
            }
        });

        searchButton.setOnAction(event -> {
            if (graph == null){
                ControllerAlerts.popNullGraphAlert();
                return;
            }
            userData.setColumns(graph.getColumns());
            userData.setRows(graph.getRows());
            if (!ControllerValidate.setUserReadData(userData, startPointInput, endPointInput)){
                updateConsole("Wrong start or end.\n");
                return;
            }
            if(!Bfs.checkIfCoherent(graph)){
                updateConsole("Selected graph is not coherent!\n");
                ControllerAlerts.popNotCoherentGraph();
                return;
            }

            if (userData.getStartPoint() == userData.getEndPoint()){
                ControllerAlerts.popSamePointsInfo();
                return;
            }

            path = Dijkstra.findPath(graph, userData);

            if(userData.getPrintMode() == STANDARD_MODE) {
                updateConsole(PathPrinter.printStandardPathToString(PathData.pathInOrder(path),path));
            } else if (userData.getPrintMode() == EXTENDED_MODE) {
                updateConsole(PathPrinter.printExtendedPathToString(PathData.pathInOrder(path),path));
            }
            GraphPrinting.printPathOnGraph(graph, path, canvasLocationOfNodes, pointSize, gc, graphCanvas, scrollAnchor);
            addToPathList(path);
        });

        pathListView.setOnMouseClicked(event -> {
            if (graph == null){
                return;
            }
            GraphPrinting.printPathOnGraph(graph, pathListView.getSelectionModel().getSelectedItem(), canvasLocationOfNodes, pointSize, gc, graphCanvas, scrollAnchor);
            startPointInput.setText(String.valueOf(pathListView.getSelectionModel().getSelectedItem().getStart()));
            endPointInput.setText(String.valueOf(pathListView.getSelectionModel().getSelectedItem().getEnd()));
        });

        pathListTitlePane.setOnMouseClicked(event -> {
            if(pathListTitlePane.isExpanded()){
                searchButton.toBack();
            }
            if(!pathListTitlePane.isExpanded()){
                searchButton.toFront();
            }
        });
    }

    private void addToPathList(PathData path) {
        for (PathData p : itemsAsPathData){
            if(path.equals(p)){
                return;
            }
        }
        itemsAsPathData.add(path);
        pathListView.setItems(itemsAsPathData);

    }

    private void initializeGrapher() {
        itemsAsPathData = FXCollections.observableArrayList();
        pathListView.setItems(itemsAsPathData);
        searchButton.toFront();
        userData = new EntryData();
        fileInput.setEditable(false);
        consoleOutput.setEditable(false);
        var consoleText = "Grapher by SS & SP\n";
        consoleOutput.setText(consoleText);
        graph = null;
        setGenerationRadioButtons();
        setSearchRadioButtons();
        canvasLocationOfNodes = new HashMap<>();
        gc = graphCanvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        pointSize = 40;
    }

    private void updateConsole (String msg) {
        consoleOutput.setText(consoleOutput.getText() + msg);
        consoleOutput.setScrollTop(Double.MAX_VALUE);
    }

    private void setSearchRadioButtons() {
        var pathModeGroup = new ToggleGroup();

        standardRB.setToggleGroup(pathModeGroup);
        extendedRB.setToggleGroup(pathModeGroup);
        standardRB.setSelected(true);
        userData.setPrintMode(STANDARD_MODE);
    }

    private void setGenerationRadioButtons() {
        var genModeGroup = new ToggleGroup();

        wageModeRB.setToggleGroup(genModeGroup);
        edgeModeRB.setToggleGroup(genModeGroup);
        randomModeRB.setToggleGroup(genModeGroup);
        wageModeRB.setSelected(true);
        userData.setMode(WEIGHT_MODE);
    }

    private void makeGraph(){
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
    }

}

