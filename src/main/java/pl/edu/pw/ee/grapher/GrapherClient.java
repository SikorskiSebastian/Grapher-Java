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
        int width = 1280;
        int height = 720;
        Scene scene = new Scene(fxmlLoader.load(), width, height);

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