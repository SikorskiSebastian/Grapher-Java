package pl.edu.pw.ee.grapher;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pl.edu.pw.ee.grapher.generator.WageMode;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graphio.GraphReader;
import pl.edu.pw.ee.grapher.graphio.GraphSaver;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GrapherController implements Initializable {
    @FXML
    private Button genButton, openButton, saveButton, searchButton;
    @FXML
    private TextField columnInput, rowsInput, startInput, endInput, fileInput, pointsInput, consoleOutput;

    private EntryData userData;
    private Graph graph = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userData = new EntryData();
        fileInput.setEditable(false);

        genButton.setOnMouseClicked(event -> {
            userData.setColumns(Integer.parseInt(columnInput.getText()));
            userData.setRows(Integer.parseInt(rowsInput.getText()));
            userData.setRangeEnd(Float.parseFloat(endInput.getText()));
            userData.setRangeStart(Float.parseFloat(startInput.getText()));
            userData.setMode(1);

            if(userData.getMode() == 1) {
                var ggen = new WageMode();
                graph = new Graph(userData.getRows(), userData.getColumns());
                ggen.generate(graph, userData);
            }

            //System.out.println(userData);
            //System.out.println(graph);

        });

        saveButton.setOnMouseClicked(event -> {
            File file = null;
            FileChooser fc = new FileChooser();
            file = fc.showOpenDialog(null);

            if (file != null) {
                GraphSaver.saveToFile(graph, file);
                fileInput.setText(file.getName());
            }
        });

        openButton.setOnMouseClicked(event -> {
            File file = null;
            FileChooser fc = new FileChooser();
            file = fc.showOpenDialog(null);

            try {
                graph = GraphReader.readFromFile(file);
                fileInput.setText(file.getName());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}