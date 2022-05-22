package pl.edu.pw.ee.grapher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
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
import java.util.ResourceBundle;

import static pl.edu.pw.ee.grapher.utils.Constants.STANDARD_MODE;
import static pl.edu.pw.ee.grapher.utils.Constants.EXTENDED_MODE;
import static pl.edu.pw.ee.grapher.utils.Constants.WEIGHT_MODE;
import static pl.edu.pw.ee.grapher.utils.Constants.EDGE_MODE;
import static pl.edu.pw.ee.grapher.utils.Constants.RANDOM_MODE;

public class GrapherController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeGrapher();

        genButton.setOnMouseClicked(event -> {
            if (!ControllerValidate.setUserGenData(userData, columnInput, rowsInput, endInput, startInput)){
                updateConsole("Wrong input!");
                return;
            }
            makeGraph();
            updateConsole(String.format("Graph (%d x%d) was generated successfully, with edge weights in range of (%.2f %.2f)%n",userData.getColumns(),userData.getRows(),userData.getRangeStart(),userData.getRangeEnd()));
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
        });

        wageModeRB.setOnMouseClicked(event -> userData.setMode(WEIGHT_MODE));
        edgeModeRB.setOnMouseClicked(event -> userData.setMode(EDGE_MODE));
        randomModeRB.setOnMouseClicked(event -> userData.setMode(RANDOM_MODE));
        standardRB.setOnMouseClicked(event -> userData.setPrintMode(STANDARD_MODE));
        extendedRB.setOnMouseClicked(event -> userData.setPrintMode(EXTENDED_MODE));

        searchButton.setOnMouseClicked(event -> {
            if (!ControllerValidate.setUserReadData(userData, startPointInput, endPointInput)){
                updateConsole("Wrong start or end.\n");
                return;
            }
            if (graph == null){
                ControllerAlerts.popNullGraphAlert();
                return;
            }
            if(!Bfs.checkIfCoherent(graph)){
                updateConsole("Selected graph is not coherent!\n");
                ControllerAlerts.popNotCoherentGraph();
                return;
            }
            path = Dijkstra.findPath(graph, userData);

            if(userData.getPrintMode() == STANDARD_MODE) {
                updateConsole(PathPrinter.printStandardPathToString(PathData.pathInOrder(path),path));
            } else if (userData.getPrintMode() == EXTENDED_MODE) {
                updateConsole(PathPrinter.printExtendedPathToString(PathData.pathInOrder(path),path));
            }
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
    }

    private void updateConsole (String msg) {
        consoleText += msg;
        consoleOutput.setText(consoleText);
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

