package pl.edu.pw.ee.grapher;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class GrapherClient extends Application {
    @Override
    public void start(@NotNull Stage stage) throws IOException{
        var fxmlLoader = new FXMLLoader(getClass().getResource("fxml/GrapherController.fxml"));
        var width = 1280;
        var height = 720;
        var scene = new Scene(fxmlLoader.load(), width, height);
        var main = Objects.requireNonNull(this.getClass().getResource("css/grapher.css")).toExternalForm();

        scene.getStylesheets().add(main);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
        stage.setTitle("Grapher");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}