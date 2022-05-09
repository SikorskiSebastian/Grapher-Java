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
    public void start(@NotNull Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(GrapherClient.class.getResource("fxml/GrapherController.fxml"));
            int width = 1000;
            int height = 800;
            Scene scene = new Scene(fxmlLoader.load(), width, height);

            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/icon.png")).toString()));
            stage.setTitle("Grapher");
            stage.setResizable(false);

            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            System.err.println("I can't load fxml file!");
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}