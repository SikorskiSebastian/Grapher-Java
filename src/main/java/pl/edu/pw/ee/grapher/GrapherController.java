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
import java.util.ResourceBundle;

import static pl.edu.pw.ee.grapher.Constants.STANDARD_MODE;
import static pl.edu.pw.ee.grapher.Constants.EXTENDED_MODE;
import static pl.edu.pw.ee.grapher.Constants.WEIGHT_MODE;
import static pl.edu.pw.ee.grapher.Constants.EDGE_MODE;
import static pl.edu.pw.ee.grapher.Constants.RANDOM_MODE;

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

            graph = null;


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
            updateConsole(String.format("Graph (%d x%d) was generated successfully, with edge weights in range of (%.2f %.2f)\n",userData.getColumns(),userData.getRows(),userData.getRangeStart(),userData.getRangeEnd()));
        });

        saveButton.setOnMouseClicked(event -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);

            if (file != null && graph != null) {
                GraphSaver.saveToFile(graph, file);
                fileInput.setText(file.getName());

                updateConsole(String.format("Graph (%d x %d) was successfully saved to a file (%s)\n",graph.getRows(), graph.getColumns(),file.getName()));
            } else if (graph == null) {
                updateConsole("No graph to save\n");
            }
        });

        openButton.setOnMouseClicked(event -> {
            FileChooser fc = new FileChooser();
            File file = fc.showOpenDialog(null);

            if(file != null ) {
                try {
                    graph = GraphReader.readFromFile(file);
                    fileInput.setText(file.getName());
                    updateConsole(String.format("Graph (%d x %d) was successfully loaded from a file (%s)\n",graph.getColumns(), graph.getRows(), file.getName()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                updateConsole("There is a problem with a file or user did not select the file \n");
            }
        });

        wageModeRB.setOnMouseClicked(event -> userData.setMode(WEIGHT_MODE));

        edgeModeRB.setOnMouseClicked(event -> userData.setMode(EDGE_MODE));

        randomModeRB.setOnMouseClicked(event -> userData.setMode(RANDOM_MODE));

        standardRB.setOnMouseClicked(event -> userData.setPrintMode(STANDARD_MODE));

        extendedRB.setOnMouseClicked(event -> userData.setPrintMode(EXTENDED_MODE));

        searchButton.setOnMouseClicked(event -> {
            userData.setStartPoint(Integer.parseInt(startPointInput.getText().trim()));
            userData.setEndPoint(Integer.parseInt(endPointInput.getText().trim()));

            path = Dijkstra.findPath(graph, userData);
            //System.out.println(path);

            int[] pathInOrder = PathData.pathInOrder(path);
            //System.out.println(String.format("Shortest path between (%d, %d) with a length of %d",path.getStart(),path.getEnd(),pathInOrder.length));
            //System.out.println(Arrays.toString(pathInOrder));


            if(userData.getPrintMode() == STANDARD_MODE){
                String pathConsoleOutput;

                pathConsoleOutput = String.format("(%d;%d): ", path.getStart(), path.getEnd());
                for(int i = 0; i <pathInOrder.length - 1; i++){
                    pathConsoleOutput += String.format("%d ----> ",pathInOrder[i]);
                }
                pathConsoleOutput += pathInOrder[pathInOrder.length-1];

                updateConsole(pathConsoleOutput + "\n");
            } else if (userData.getPrintMode() == EXTENDED_MODE) {
                updateConsole("RIGHT NOW ONLY STANDARD MODE WORKS!\n");
            }
        });

    }

    private void updateConsole (String msg){
        consoleText += msg;
        consoleOutput.setText(consoleText);
        consoleOutput.setScrollTop(Double.MAX_VALUE);
    }
}