package pl.edu.pw.ee.grapher.utils;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graph.Vertex;

import java.util.Map;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class GraphPrinting {
    private GraphPrinting(){}

    public static void resizePrintWindow(@NotNull Graph graph, Canvas graphCanvas, AnchorPane scrollAnchor, float pointSize) {
        if(2*graph.getColumns()*pointSize >= 500) {
            graphCanvas.setWidth(2 * graph.getColumns() * pointSize);
            scrollAnchor.setPrefWidth(graphCanvas.getWidth());

        } else {
            graphCanvas.setWidth(500.0-2.0);
            scrollAnchor.setPrefWidth(500.0-2.0);
        }
        if(2*graph.getRows()*pointSize >= 460) {
            graphCanvas.setHeight(2 * graph.getRows() * pointSize);
            scrollAnchor.setPrefHeight(graphCanvas.getHeight());
        } else {
            graphCanvas.setHeight(460.0-2.0);
            scrollAnchor.setPrefHeight(460.0-2.0);
        }
    }

    public static void printGraph(@NotNull Graph graph, float pointSize, @NotNull GraphicsContext gc, @NotNull Canvas graphCanvas, AnchorPane scrollAnchor, Map<Integer, Point2D> canvasLocationOfNodes){
        if(!checkSizeForPrinting(graph)){
            gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
            return;
        }
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());

        GraphPrinting.resizePrintWindow(graph,graphCanvas, scrollAnchor, pointSize);

        for(int i = 0; i < graph.getRows(); i++) {
            for(int j = 0; j < graph.getColumns(); j++) {
                int index = i*graph.getColumns() + j;
                gc.fillOval(pointSize *(j+0.5) + j*pointSize, pointSize *(i+0.5) + i*pointSize, pointSize, pointSize);
                Point2D coordsOfCenter = new Point2D(pointSize *(j+0.5) + j*pointSize + pointSize/2, pointSize *(i+0.5) + i*pointSize + pointSize/2);
                canvasLocationOfNodes.put(index, coordsOfCenter);
            }
        }

        for(int index = 0; index < graph.getNumOfVertices(); index++) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(index);
            Vertex currentVertex = graph.getVertex(index);

            if(currentVertex.getExistence(UP)) {
                GraphPrinting.makeArrowUp(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
            if(currentVertex.getExistence(RIGHT)) {
                GraphPrinting.makeArrowRight(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
            if(currentVertex.getExistence(DOWN)) {
                GraphPrinting.makeArrowDown(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
            if(currentVertex.getExistence(LEFT)) {
                GraphPrinting.makeArrowLeft(gc, coordsOfCenter, pointSize, (Color) gc.getFill());
            }
        }

        gc.setFont(new Font(pointSize * 0.35));
        gc.setFill(new Color(1,1,1,1));
        for(int index = 0; index < graph.getNumOfVertices(); index++) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(index);
            gc.fillText(String.valueOf(index),coordsOfCenter.getX(),coordsOfCenter.getY() + gc.getFont().getSize() / 3);
        }
        gc.setFill(new Color(0,0,0,1));

        gc.setFont(new Font(pointSize * 0.2));
        for(int index = 0; index < graph.getNumOfVertices(); index ++) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(index);
            if(graph.getVertex(index).getExistence(RIGHT)) {
                GraphPrinting.printWeightRight(graph.getVertex(index).getWeight(RIGHT), gc, coordsOfCenter, pointSize);
            }
            if(graph.getVertex(index).getExistence(DOWN)) {
                GraphPrinting.printWeightDown(graph.getVertex(index).getWeight(DOWN), gc, coordsOfCenter, pointSize);
            }
            if(graph.getVertex(index).getExistence(LEFT)) {
                GraphPrinting.printWeightLeft(graph.getVertex(index).getWeight(LEFT), gc, coordsOfCenter, pointSize);
            }
            if(graph.getVertex(index).getExistence(UP)) {
                GraphPrinting.printWeightUp(graph.getVertex(index).getWeight(UP), gc, coordsOfCenter, pointSize);
            }
        }
    }
    public static void printPathOnGraph(Graph graph, PathData path, Map<Integer, Point2D> canvasLocationOfNodes, float pointSize, @NotNull GraphicsContext gc, @NotNull Canvas graphCanvas, AnchorPane scrollAnchor) {
        if(!checkSizeForPrinting(graph)){
            gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
            return;
        }
        var pathColor = new Color(1,0,0,1);
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        GraphPrinting.printGraph(graph, pointSize, gc, graphCanvas, scrollAnchor, canvasLocationOfNodes);
        int[] pathInOrder = PathData.pathInOrder(path);

        gc.setFill(pathColor);
        for (int index : pathInOrder) {
            Point2D currentCenterOfNode = canvasLocationOfNodes.get(index);
            gc.fillOval(currentCenterOfNode.getX() - pointSize / 2, currentCenterOfNode.getY() - pointSize / 2, pointSize, pointSize);
        }
        gc.setFill(new Color(0,0,0,1));

        for (int index : pathInOrder) {
            Point2D coordsOfCenter = canvasLocationOfNodes.get(index);
            gc.fillText(String.valueOf(index), coordsOfCenter.getX(), coordsOfCenter.getY() + gc.getFont().getSize() / 3);
        }

        int currentNode;
        int nextNode;
        for(int index = 0; index < pathInOrder.length - 1; index++){
            currentNode = pathInOrder[index];
            nextNode = pathInOrder[index + 1];
            Point2D currentCenterOfNode = canvasLocationOfNodes.get(currentNode);

            if(currentNode + 1 == nextNode){
                GraphPrinting.makeArrowRight(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode + graph.getColumns() == nextNode ){
                GraphPrinting.makeArrowDown(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode - 1 == nextNode) {
                GraphPrinting.makeArrowLeft(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode - graph.getColumns() == nextNode) {
                GraphPrinting.makeArrowUp(gc, currentCenterOfNode, pointSize, pathColor);
            }
        }
    }

    public static void makeArrowUp(@NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize, Color color) {
        gc.setFill(color);
        gc.fillRect(coordsOfCenter.getX() - pointSize/5,coordsOfCenter.getY() - 0.6 * pointSize - pointSize * 0.8,pointSize/20,pointSize * 0.8);

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

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

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

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

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

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

        double[] xPoints = new double[3];
        double[] yPoints = new double[3];

        xPoints[0] = coordsOfCenter.getX() - 1.5 * pointSize;
        yPoints[0] = coordsOfCenter.getY() + pointSize/5;

        xPoints[1] = coordsOfCenter.getX() - 1.5 * pointSize + pointSize/5;
        yPoints[1] = coordsOfCenter.getY();

        xPoints[2] = coordsOfCenter.getX() - 1.5 * pointSize + pointSize/5;
        yPoints[2] = coordsOfCenter.getY() + 2 * pointSize/5;


        gc.fillPolygon(xPoints,yPoints,3);
        gc.setFill(new Color(0, 0, 0, 1));
    }

    public static void printWeightRight(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX()+ 0.9 * pointSize, coordsOfCenter.getY() - pointSize/5 - pointSize/10);
    }
    public static void printWeightDown(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX() + pointSize/5 + pointSize/5 + pointSize/10, coordsOfCenter.getY() + pointSize);
    }
    public static void printWeightLeft(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX() - pointSize, coordsOfCenter.getY() + 2.2 * pointSize/5);
    }
    public static void printWeightUp(float weight, @NotNull GraphicsContext gc, @NotNull Point2D coordsOfCenter, float pointSize) {
        gc.fillText(String.format("%.2f", weight),coordsOfCenter.getX() - pointSize/5 - pointSize/5 - pointSize/10, coordsOfCenter.getY() - pointSize);
    }

    public static boolean checkSizeForPrinting(Graph graph){
        if(graph.getRows() > 60 || graph.getColumns() > 60){
            return false;
        }
        return true;
    }
}
