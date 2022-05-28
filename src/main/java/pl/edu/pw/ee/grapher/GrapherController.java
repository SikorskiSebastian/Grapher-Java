package pl.edu.pw.ee.grapher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Vertex;
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
import java.util.Arrays;
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
    private EntryData userData;
    private Graph graph;
    private PathData path;
    private String consoleText;

    HashMap<Integer, Point2D> canvasLocationOfNodes;
    GraphicsContext gc;
    private float pointSize;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGrapher();

        genButton.setOnMouseClicked(event -> {
            if (!ControllerValidate.setUserGenData(userData, columnInput, rowsInput, endInput, startInput)){
                updateConsole("Wrong input!\n");
                return;
            }
            makeGraph();
            updateConsole(String.format("Graph (%d x%d) was generated successfully, with edge weights in range of (%.2f %.2f)%n",userData.getColumns(),userData.getRows(),userData.getRangeStart(),userData.getRangeEnd()));
            printGraph(graph, pointSize);
        });

        saveButton.setOnMouseClicked(event -> {
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

        openButton.setOnMouseClicked(event -> {
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
            printGraph(graph, pointSize);
        });

        wageModeRB.setOnMouseClicked(event -> userData.setMode(WEIGHT_MODE));
        edgeModeRB.setOnMouseClicked(event -> userData.setMode(EDGE_MODE));
        randomModeRB.setOnMouseClicked(event -> userData.setMode(RANDOM_MODE));
        standardRB.setOnMouseClicked(event -> userData.setPrintMode(STANDARD_MODE));
        extendedRB.setOnMouseClicked(event -> userData.setPrintMode(EXTENDED_MODE));

        searchButton.setOnMouseClicked(event -> {
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
            printPathOnGraph(graph, path, canvasLocationOfNodes, pointSize);
        });

        graphCanvas.setOnMouseClicked(event -> {
            double posX = event.getX();
            double posY = event.getY();
        });
    }


    private void initializeGrapher() {
        userData = new EntryData();
        fileInput.setEditable(false);
        consoleOutput.setEditable(false);
        consoleText = "Grapher by SS & SP\n";
        consoleOutput.setText(consoleText);
        graph = null;
        setGenerationRadioButtons();
        setSearchRadioButtons();
        canvasLocationOfNodes = new HashMap<Integer, Point2D>();
        gc = graphCanvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        pointSize = 50;
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

    private void printGraph(@NotNull Graph graph, float pointSize){
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());

        resizePrintWindow(graphCanvas, scrollAnchor, pointSize);

        for(int i = 0; i < graph.getRows(); i++) {
            for(int j = 0; j < graph.getColumns(); j++) {
                int index = i*graph.getColumns() + j;
                gc.fillOval(pointSize *(j+0.5) + j*pointSize, pointSize *(i+0.5) + i*pointSize, pointSize, pointSize);
                Point2D coordsOfCenter = new Point2D(pointSize *(j+0.5) + j*pointSize + pointSize/2, pointSize *(i+0.5) + i*pointSize + pointSize/2);
                canvasLocationOfNodes.put(index, coordsOfCenter);
                //System.out.println("Dodano punkt: " + index + " " + coordsOfCenter.getX() + " " + coordsOfCenter.getY());
            }
        }

        for(int index = 0; index < graph.getNumOfVertices(); index++) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(index);
            Vertex currentVertex = graph.getVertex(index);

            if(currentVertex.getExistence(UP)) {
                makeArrowUp(gc, coordsOfCenter, pointSize, new Color(0,0,0,1));
            }
            if(currentVertex.getExistence(RIGHT)) {
                makeArrowRight(gc, coordsOfCenter, pointSize, new Color(0,0,0,1));
            }
            if(currentVertex.getExistence(DOWN)) {
                makeArrowDown(gc, coordsOfCenter, pointSize, new Color(0,0,0,1));
            }
            if(currentVertex.getExistence(LEFT)) {
                makeArrowLeft(gc, coordsOfCenter, pointSize, new Color(0,0,0,1));
            }
        }

        gc.setFont(new Font(pointSize * 0.35));
        gc.setFill(new Color(1,1,1,1));
        for(int index = 0; index < graph.getNumOfVertices(); index++) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(index);
            gc.fillText(String.valueOf(index),coordsOfCenter.getX(),coordsOfCenter.getY() + gc.getFont().getSize() / 3);
        }
        gc.setFill(new Color(0,0,0,1));
    }

    private void printPathOnGraph(Graph graph, PathData path, HashMap<Integer, Point2D> canvasLocationOfNodes, float pointSize) {
        var pathColor = new Color(1,0,0,1);
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        printGraph(graph,pointSize);
        int[] pathInOrder = PathData.pathInOrder(path);

        gc.setFill(pathColor);
        for(int index = 0; index < pathInOrder.length; index++){
            Point2D currentCenterOfNode = canvasLocationOfNodes.get(pathInOrder[index]);
            gc.fillOval(currentCenterOfNode.getX() - pointSize/2, currentCenterOfNode.getY() - pointSize/2, pointSize, pointSize);
        }
        gc.setFill(new Color(0,0,0,1));

        for(int index = 0; index < pathInOrder.length; index++) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(pathInOrder[index]);
            gc.fillText(String.valueOf(pathInOrder[index]),coordsOfCenter.getX(),coordsOfCenter.getY() + gc.getFont().getSize() / 3);
        }

        int currentNode;
        int nextNode;
        for(int index = 0; index < pathInOrder.length - 1; index++){
            currentNode = pathInOrder[index];
            nextNode = pathInOrder[index + 1];
            Point2D currentCenterOfNode = canvasLocationOfNodes.get(currentNode);

            if(currentNode + 1 == nextNode){
                makeArrowRight(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode + graph.getColumns() == nextNode ){
                makeArrowDown(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode - 1 == nextNode) {
                makeArrowLeft(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode - graph.getColumns() == nextNode) {
                makeArrowUp(gc, currentCenterOfNode, pointSize, pathColor);
            }
        }
    }

    private void resizePrintWindow(Canvas graphCanvas, AnchorPane scrollAnchor, float pointSize) {
        if(2*graph.getColumns()*pointSize >= 500) {
            graphCanvas.setWidth(2 * graph.getColumns() * pointSize);
            scrollAnchor.setPrefWidth(graphCanvas.getWidth());

        } else {
            graphCanvas.setWidth(500-2);
            scrollAnchor.setPrefWidth(500-2);
        }
        if(2*graph.getRows()*pointSize >= 460) {
            graphCanvas.setHeight(2 * graph.getRows() * pointSize);
            scrollAnchor.setPrefHeight(graphCanvas.getHeight());
        } else {
            graphCanvas.setHeight(460-2);
            scrollAnchor.setPrefHeight(460-2);
        }
    }

    private void makeArrowUp(GraphicsContext gc,Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() - pointSize/5,coordsOfCenter.getY() - 0.6 * pointSize - pointSize * 0.8,pointSize/20,pointSize * 0.8);

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() - pointSize/5;
        yPoints[0] = coordsOfCenter.getY() - 1.5 * pointSize;

        xPoints[1] = coordsOfCenter.getX();
        yPoints[1] = coordsOfCenter.getY() - 1.5 * pointSize + pointSize/5;

        xPoints[2] = coordsOfCenter.getX() - 2 * pointSize/5;
        yPoints[2] = coordsOfCenter.getY() - 1.5 * pointSize + pointSize/5;


        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }
    private void makeArrowRight(GraphicsContext gc,Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() + 0.6 * pointSize, coordsOfCenter.getY() - pointSize/5, pointSize * 0.8, pointSize/20);

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() + 1.5 * pointSize;
        yPoints[0] = coordsOfCenter.getY() - pointSize/5;

        xPoints[1] = coordsOfCenter.getX() + 1.5 * pointSize - pointSize/5;
        yPoints[1] = coordsOfCenter.getY() - 2 * pointSize/5;

        xPoints[2] = coordsOfCenter.getX() + 1.5 * pointSize - pointSize/5;
        yPoints[2] = coordsOfCenter.getY();


        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }
    private void makeArrowDown(GraphicsContext gc,Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() + pointSize/5, coordsOfCenter.getY() + 0.6 * pointSize, pointSize/20, pointSize * 0.8);

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() + pointSize/5;
        yPoints[0] = coordsOfCenter.getY() + 1.5 * pointSize;

        xPoints[1] = coordsOfCenter.getX() + 2 * pointSize/5;
        yPoints[1] = coordsOfCenter.getY() + 1.5 * pointSize - pointSize/5;

        xPoints[2] = coordsOfCenter.getX();
        yPoints[2] = coordsOfCenter.getY() + 1.5 * pointSize - pointSize/5;


        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }
    private void makeArrowLeft(GraphicsContext gc,Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() - 0.6 * pointSize - pointSize * 0.8, coordsOfCenter.getY() + pointSize/5, pointSize * 0.8, pointSize/20);

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() - 1.5 * pointSize;
        yPoints[0] = coordsOfCenter.getY() + pointSize/5;

        xPoints[1] = coordsOfCenter.getX() - 1.5 * pointSize + pointSize/5;
        yPoints[1] = coordsOfCenter.getY();

        xPoints[2] = coordsOfCenter.getX() - 1.5 * pointSize + pointSize/5;
        yPoints[2] = coordsOfCenter.getY() + 2 * pointSize/5;


        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }
}

