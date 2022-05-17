package pl.edu.pw.ee.grapher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import pl.edu.pw.ee.grapher.dijkstra.Dijkstra;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.generator.EdgeMode;
import pl.edu.pw.ee.grapher.generator.GraphGenerator;
import pl.edu.pw.ee.grapher.generator.WageMode;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graphio.GraphReader;
import pl.edu.pw.ee.grapher.graphio.GraphSaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static pl.edu.pw.ee.grapher.Constants.STANDARD_MODE;
import static pl.edu.pw.ee.grapher.Constants.WEIGHT_MODE;

public class GrapherController implements Initializable {
    @FXML
    private Button genButton, openButton, saveButton, searchButton;
    @FXML
    private RadioButton wageModeRB, edgeModeRB, randomModeRB, standardRB, extendedRB;
    @FXML
    private TextField columnInput, rowsInput, startInput, endInput, fileInput, startPointInput, endPointInput;
    @FXML
    private TextArea consoleOutput;

    private EntryData userData;
    private Graph graph;
    private PathData path;
    private String consoleText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = new EntryData();
        fileInput.setEditable(false);
        consoleOutput.setEditable(false);
        consoleText = "Grapher by SS & SP\n";
        consoleOutput.setText(consoleText);

        ToggleGroup genModeGroup = new ToggleGroup();
        wageModeRB.setToggleGroup(genModeGroup);
        edgeModeRB.setToggleGroup(genModeGroup);
        randomModeRB.setToggleGroup(genModeGroup);
        wageModeRB.setSelected(true);
        userData.setMode(WEIGHT_MODE);

        ToggleGroup pathModeGroup = new ToggleGroup();
        standardRB.setToggleGroup(pathModeGroup);
        extendedRB.setToggleGroup(pathModeGroup);
        standardRB.setSelected(true);
        userData.setPrintMode(STANDARD_MODE);

        genButton.setOnMouseClicked(event -> {
            userData.setColumns(Integer.parseInt(columnInput.getText()));
            userData.setRows(Integer.parseInt(rowsInput.getText()));
            userData.setRangeEnd(Float.parseFloat(endInput.getText()));
            userData.setRangeStart(Float.parseFloat(startInput.getText()));

            if(userData.getMode() == 1) {
                graph = null;
                var graphGenW = new WageMode();
                graph = new Graph(userData.getRows(), userData.getColumns());
                graphGenW.generate(graph, userData);
            } else if (userData.getMode() == 2) {
                graph = null;
                var graphGenE = new EdgeMode();
                graph = new Graph(userData.getRows(), userData.getColumns());
                graphGenE.generate(graph, userData);
            } else if (userData.getMode() == 3) {
                graph = null;
                var graphGenR = new GraphGenerator();
                graph = new Graph(userData.getRows(), userData.getColumns());
                graphGenR.generate(graph, userData);
            }
        });

        saveButton.setOnMouseClicked(event -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);

            if (file != null && graph != null) {
                GraphSaver.saveToFile(graph, file);
                fileInput.setText(file.getName());

                consoleText += String.format("Pomyślnie zapisano graf (%d x %d) do pliku (%s)%n",graph.getRows(), graph.getColumns(),file.getName());
                consoleOutput.setText(consoleText);
            } else if (graph == null) {
                consoleText += "Brak grafu do zapisania\n";
                consoleOutput.setText(consoleText);
            }
        });

        openButton.setOnMouseClicked(event -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);

            if(file != null ) {
                try {
                    graph = GraphReader.readFromFile(file);
                    fileInput.setText(file.getName());
                    consoleText += String.format("Pomyślnie wczytano graf (%d x %d) z pliku(%s)%n",graph.getColumns(), graph.getRows(), file.getName());
                    consoleOutput.setText(consoleText);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                consoleText += "Problem z plikiem podczas czytania \n";
                consoleOutput.setText(consoleText);
            }
        });

        wageModeRB.setOnMouseClicked(event -> userData.setMode(1));

        edgeModeRB.setOnMouseClicked(event -> userData.setMode(2));

        randomModeRB.setOnMouseClicked(event -> userData.setMode(3));

        searchButton.setOnMouseClicked(event -> {
            userData.setStartPoint(Integer.parseInt(startPointInput.getText().trim()));
            userData.setEndPoint(Integer.parseInt(endPointInput.getText().trim()));

            path = Dijkstra.findPath(graph, userData);
            System.out.println(path);

            int[] pathInOrder = PathData.pathInOrder(path);
            System.out.println(String.format("Shortest path between (%d, %d) with a length of %d",path.getStart(),path.getEnd(),pathInOrder.length));
            System.out.println(Arrays.toString(pathInOrder));


            if(userData.getPrintMode() == 1){

            } else if (userData.getPrintMode() == 2) {

            }
        });
    }
}