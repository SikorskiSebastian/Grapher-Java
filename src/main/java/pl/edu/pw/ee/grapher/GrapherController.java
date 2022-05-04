package pl.edu.pw.ee.grapher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GrapherController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}