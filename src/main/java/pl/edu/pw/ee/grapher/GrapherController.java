package pl.edu.pw.ee.grapher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.edu.pw.ee.grapher.generator.EdgeMode;
import pl.edu.pw.ee.grapher.generator.WageMode;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.net.URL;
import java.util.ResourceBundle;

public class GrapherController implements Initializable {
    @FXML
    private Button genButton, openButton, saveButton, searchButton;
    @FXML
    private TextField columnInput, rowsInput, startInput, endInput, fileInput, pointsInput, consoleOutput;

    private EntryData userData;
    private Graph graph;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = new EntryData();

        genButton.setOnMouseClicked(event -> {
            userData.setColumns(Integer.parseInt(columnInput.getText()));
            userData.setRows(Integer.parseInt(rowsInput.getText()));
            userData.setRangeEnd(Float.parseFloat(endInput.getText()));
            userData.setRangeStart(Float.parseFloat(startInput.getText()));

            var gen = new WageMode();
            var graph = new Graph(userData.getRows(),userData.getColumns());
            gen.generate(graph,userData);


        });
    }
}