package pl.edu.pw.ee.grapher.generator;

import pl.edu.pw.ee.grapher.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;

import static pl.edu.pw.ee.grapher.Constants.*;

public class GraphGenerator {

    public void generate(Graph graph, EntryData userData) {
        int numOfVertices = graph.getNumOfVertices();
        for (int i = 0; i < numOfVertices; i++) {
            makeConnectionFromVertex(i, graph, userData);
        }
    }

    float generateWeight(float start, float end) {
        return (int) ((Math.random() * (end - start)) + start);
    }

    static boolean generateEdge(){
        return (Math.random() < 0.5);
    }

    void makeConnectionFromVertex(int index, Graph graph, EntryData userData) {
        int columns = graph.getColumns();
        int rows = graph.getRows();

        if(generateEdge()) {
            if (index - columns >= 0 && index - columns < columns * rows) {
                graph.getVertex(index).setExistence(UP, true);
                graph.getVertex(index).setWeight(UP, generateWeight(userData.getRangeStart(), userData.getRangeEnd()));
                graph.getVertex(index).setConnections(UP, index - columns);
            }
        } else {
            graph.getVertex(index).setExistence(UP, false);
        }

        if(generateEdge()) {
            if (index + 1 < columns * rows && (index + 1) % columns != 0) {
                graph.getVertex(index).setExistence(RIGHT, true);
                graph.getVertex(index).setWeight(RIGHT, generateWeight(userData.getRangeStart(), userData.getRangeEnd()));
                graph.getVertex(index).setConnections(RIGHT, index + 1);
            }
        } else {
            graph.getVertex(index).setExistence(RIGHT, false);
        }

        if(generateEdge()) {
            if(index + columns > 0 && index + columns < columns * rows){
                graph.getVertex(index).setExistence(DOWN,true);
                graph.getVertex(index).setWeight(DOWN, generateWeight(userData.getRangeStart(), userData.getRangeEnd()));
                graph.getVertex(index).setConnections(DOWN, index + columns);
            }
        } else {
            graph.getVertex(index).setExistence(DOWN, false);
        }

        if(generateEdge()){
            if( index - 1 >= 0 && index % columns != 0){
                graph.getVertex(index).setExistence(LEFT, true);
                graph.getVertex(index).setWeight(LEFT, generateWeight(userData.getRangeStart(), userData.getRangeEnd()));
                graph.getVertex(index).setConnections(LEFT, index - 1);
            }
        } else {
            graph.getVertex(index).setExistence(LEFT, false);
        }

    }

}
