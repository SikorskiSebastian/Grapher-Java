package pl.edu.pw.ee.grapher.graphics;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.util.Map;

import static pl.edu.pw.ee.grapher.utils.Constants.*;
import static pl.edu.pw.ee.grapher.utils.Constants.LEFT;

public class ArrowPrinter {
    private ArrowPrinter(){}

    public static void printArrows(@NotNull Graph graph, GraphicsContext gc, float pointSize, Map<Integer, Point2D> canvasLocationOfNodes){
        for (int index = 0; index < graph.getNumOfVertices(); index++) {
            var coordsOfCenter = canvasLocationOfNodes.get(index);
            var currentVertex = graph.getVertex(index);

            if (currentVertex.getExistence(UP)) {
                ArrowPrinter.makeArrowUp(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
            if (currentVertex.getExistence(RIGHT)) {
                ArrowPrinter.makeArrowRight(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
            if (currentVertex.getExistence(DOWN)) {
                ArrowPrinter.makeArrowDown(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
            if (currentVertex.getExistence(LEFT)) {
                ArrowPrinter.makeArrowLeft(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
        }
    }

    public static void makeArrowUp(@NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() - pointSize/5,coordsOfCenter.getY() - 0.6 * pointSize - pointSize * 0.8,pointSize/20,pointSize * 0.8);

        var xPoints = new double[3];
        var yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() - pointSize/5;
        yPoints[0] = coordsOfCenter.getY() - 1.5 * pointSize;
        xPoints[1] = coordsOfCenter.getX();
        yPoints[1] = coordsOfCenter.getY() - 1.5 * pointSize + pointSize/5;
        xPoints[2] = coordsOfCenter.getX() - 2 * pointSize/5;
        yPoints[2] = coordsOfCenter.getY() - 1.5 * pointSize + pointSize/5;

        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }

    public static void makeArrowRight(@NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() + 0.6 * pointSize, coordsOfCenter.getY() - pointSize/5, pointSize * 0.8, pointSize/20);

        var xPoints = new double[3];
        var yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() + 1.5 * pointSize;
        yPoints[0] = coordsOfCenter.getY() - pointSize/5;
        xPoints[1] = coordsOfCenter.getX() + 1.5 * pointSize - pointSize/5;
        yPoints[1] = coordsOfCenter.getY() - 2 * pointSize/5;
        xPoints[2] = coordsOfCenter.getX() + 1.5 * pointSize - pointSize/5;
        yPoints[2] = coordsOfCenter.getY();

        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }

    public static void makeArrowDown(@NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() + pointSize/5, coordsOfCenter.getY() + 0.6 * pointSize, pointSize/20, pointSize * 0.8);

        var xPoints = new double[3];
        var yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() + pointSize/5;
        yPoints[0] = coordsOfCenter.getY() + 1.5 * pointSize;
        xPoints[1] = coordsOfCenter.getX() + 2 * pointSize/5;
        yPoints[1] = coordsOfCenter.getY() + 1.5 * pointSize - pointSize/5;
        xPoints[2] = coordsOfCenter.getX();
        yPoints[2] = coordsOfCenter.getY() + 1.5 * pointSize - pointSize/5;

        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }

    public static void makeArrowLeft(@NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() - 0.6 * pointSize - pointSize * 0.8, coordsOfCenter.getY() + pointSize/5, pointSize * 0.8, pointSize/20);

        var xPoints = new double[3];
        var yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() - 1.5 * pointSize;
        yPoints[0] = coordsOfCenter.getY() + pointSize/5;
        xPoints[1] = coordsOfCenter.getX() - 1.5 * pointSize + pointSize/5;
        yPoints[1] = coordsOfCenter.getY();
        xPoints[2] = coordsOfCenter.getX() - 1.5 * pointSize + pointSize/5;
        yPoints[2] = coordsOfCenter.getY() + 2 * pointSize/5;

        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }
}
