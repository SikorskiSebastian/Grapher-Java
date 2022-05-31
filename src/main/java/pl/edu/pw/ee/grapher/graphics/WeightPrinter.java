package pl.edu.pw.ee.grapher.graphics;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.util.Map;

import static pl.edu.pw.ee.grapher.utils.Constants.*;
import static pl.edu.pw.ee.grapher.utils.Constants.UP;

public class WeightPrinter {
    private WeightPrinter(){}

    public static void printWeights(@NotNull Graph graph, @NotNull GraphicsContext gc, float pointSize, Map<Integer, Point2D> canvasLocationOfNodes){
        gc.setFill(new Color(0, 0, 0, 1));
        gc.setFont(new Font(pointSize * 0.2));
        for (int index = 0; index < graph.getNumOfVertices(); index++) {
            var coordsOfCenter = canvasLocationOfNodes.get(index);
            if (graph.getVertex(index).getExistence(RIGHT)) {
                WeightPrinter.printWeightRight(graph.getVertex(index).getWeight(RIGHT), gc, coordsOfCenter, pointSize);
            }
            if (graph.getVertex(index).getExistence(DOWN)) {
                WeightPrinter.printWeightDown(graph.getVertex(index).getWeight(DOWN), gc, coordsOfCenter, pointSize);
            }
            if (graph.getVertex(index).getExistence(LEFT)) {
                WeightPrinter.printWeightLeft(graph.getVertex(index).getWeight(LEFT), gc, coordsOfCenter, pointSize);
            }
            if (graph.getVertex(index).getExistence(UP)) {
                WeightPrinter.printWeightUp(graph.getVertex(index).getWeight(UP), gc, coordsOfCenter, pointSize);
            }
        }
    }

    private static void printWeightRight(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX()+ 0.9 * pointSize, coordsOfCenter.getY() - pointSize/5 - pointSize/10);
    }
    private static void printWeightDown(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX() + pointSize/5 + pointSize/5 + pointSize/10, coordsOfCenter.getY() + pointSize);
    }
    private static void printWeightLeft(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX() - pointSize, coordsOfCenter.getY() + 2.2 * pointSize/5);
    }
    private static void printWeightUp(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX() - pointSize/5 - pointSize/5 - pointSize/10, coordsOfCenter.getY() - pointSize);
    }
}
