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
import pl.edu.pw.ee.grapher.graphics.GraphPrinting;
import pl.edu.pw.ee.grapher.utils.*;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

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
    private HashMap<Integer, Point2D> canvasLocationOfVertices;
    private GraphicsContext gc;
    private float pointSize;
    private int numberClicked = 0;
    private RadioButtonHandler radioButtonHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGrapher();

        genButton.setOnAction(event -> {
            if ((graph = ButtonHandler.genButtonAction(userData, columnInput, rowsInput, endInput, startInput, consoleOutput)) == null){
                return;
            }
            GraphPrinting.printGraph(graph, pointSize, gc, graphCanvas, scrollAnchor, canvasLocationOfVertices);
            pathListView.getItems().clear();
        });

        saveButton.setOnAction(event -> ButtonHandler.saveButtonAction(graph, consoleOutput, fileInput));
        openButton.setOnAction(event -> {
            if ((graph = ButtonHandler.openButtonAction(consoleOutput, fileInput)) == null){
                return;
            }
            GraphPrinting.printGraph(graph, pointSize, gc, graphCanvas, scrollAnchor, canvasLocationOfVertices);
        });
        radioButtonHandler.setActionButtons(userData);

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
                var coordsOfCenter = canvasLocationOfVertices.get(index);

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
            if ((path = ButtonHandler.searchButtonAction(graph, userData, consoleOutput, startPointInput, endPointInput)) == null){
                return;
            }
            GraphPrinting.printPathOnGraph(graph, path, canvasLocationOfVertices, pointSize, gc, graphCanvas, scrollAnchor);
            ControllerInitializer.addToPathList(path, itemsAsPathData, pathListView);
        });

        pathListView.setOnMouseClicked(event -> {
            if (graph == null || path == null || pathListView.getSelectionModel().getSelectedItem() == null){
                return;
            }

            GraphPrinting.printPathOnGraph(graph, pathListView.getSelectionModel().getSelectedItem(), canvasLocationOfVertices, pointSize, gc, graphCanvas, scrollAnchor);
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

    private void initializeGrapher() {
        itemsAsPathData = FXCollections.observableArrayList();
        pathListView.setItems(itemsAsPathData);
        searchButton.toFront();
        userData = new EntryData();
        fileInput.setEditable(false);
        consoleOutput.setEditable(false);
        consoleOutput.setText("Grapher by SS & SP\n");
        graph = null;

        radioButtonHandler = new RadioButtonHandler(wageModeRB, standardRB, extendedRB, edgeModeRB, randomModeRB);
        radioButtonHandler.setGenerationRadioButtons(userData);
        radioButtonHandler.setSearchRadioButtons(userData);

        canvasLocationOfVertices = new HashMap<>();
        gc = graphCanvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        pointSize = 40;
    }
}

