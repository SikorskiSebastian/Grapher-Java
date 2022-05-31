package pl.edu.pw.ee.grapher.graphics;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.graph.Graph;

import java.util.Map;

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
            if (!(graph.getRows() <= 60 && graph.getColumns() <= 60)) {
                gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
                return;
            }
            gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
            GraphPrinting.resizePrintWindow(graph, graphCanvas, scrollAnchor, pointSize);

            for (int i = 0; i < graph.getRows(); i++) {
                for (int j = 0; j < graph.getColumns(); j++) {
                    var index = i * graph.getColumns() + j;
                    gc.fillOval(pointSize * (j + 0.5) + j * pointSize, pointSize * (i + 0.5) + i * pointSize, pointSize, pointSize);
                    var coordsOfCenter = new Point2D(pointSize * (j + 0.5) + j * pointSize + pointSize / 2, pointSize * (i + 0.5) + i * pointSize + pointSize / 2);
                    canvasLocationOfNodes.put(index, coordsOfCenter);
                }
            }
            ArrowPrinter.printArrows(graph, gc, pointSize, canvasLocationOfNodes);

            gc.setFont(new Font(pointSize * 0.35));
            gc.setFill(new Color(1, 1, 1, 1));
            for (int index = 0; index < graph.getNumOfVertices(); index++) {
                var coordsOfCenter = canvasLocationOfNodes.get(index);
                gc.fillText(String.valueOf(index), coordsOfCenter.getX(), coordsOfCenter.getY() + gc.getFont().getSize() / 3);
            }

            WeightPrinter.printWeights(graph, gc, pointSize, canvasLocationOfNodes);
    }

    public static void printPathOnGraph(@NotNull Graph graph, PathData path, Map<Integer, Point2D> canvasLocationOfNodes, float pointSize, @NotNull GraphicsContext gc, @NotNull Canvas graphCanvas, AnchorPane scrollAnchor) {
        if(!(graph.getRows() <= 60 && graph.getColumns() <= 60)){
            gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
            return;
        }
        var pathColor = new Color(0,0.3529,0.6274,1);
        gc.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
        GraphPrinting.printGraph(graph, pointSize, gc, graphCanvas, scrollAnchor, canvasLocationOfNodes);
        var pathInOrder = PathData.pathInOrder(path);

        gc.setFill(pathColor);
        for (int index : pathInOrder) {
            var currentCenterOfNode = canvasLocationOfNodes.get(index);
            gc.fillOval(currentCenterOfNode.getX() - pointSize / 2, currentCenterOfNode.getY() - pointSize / 2, pointSize, pointSize);
        }
        gc.setFill(new Color(1,1,1,1));

        gc.setFont(new Font(pointSize * 0.35));
        for (int index : pathInOrder) {
            var coordsOfCenter = canvasLocationOfNodes.get(index);
            gc.fillText(String.valueOf(index), coordsOfCenter.getX(), coordsOfCenter.getY() + gc.getFont().getSize() / 3);
        }

        for(int index = 0; index < pathInOrder.length - 1; index++){
            var currentNode = pathInOrder[index];
            var nextNode = pathInOrder[index + 1];
            var currentCenterOfNode = canvasLocationOfNodes.get(currentNode);

            if(currentNode + 1 == nextNode){
                ArrowPrinter.makeArrowRight(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode + graph.getColumns() == nextNode ){
                ArrowPrinter.makeArrowDown(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode - 1 == nextNode) {
                ArrowPrinter.makeArrowLeft(gc, currentCenterOfNode, pointSize, pathColor);
            } else if (currentNode - graph.getColumns() == nextNode) {
                ArrowPrinter.makeArrowUp(gc, currentCenterOfNode, pointSize, pathColor);
            }
        }
    }
}
