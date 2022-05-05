package pl.edu.pw.ee.grapher;

import pl.edu.pw.ee.grapher.graph.Graph;

public abstract class GraphGenerator {

    void generate(Graph graph, EntryData userData) {
        int numOfVertices = graph.getNumOfVertices();
        for (int i = 0; i < numOfVertices; i++) {
            makeConnectionFromVertex(i, graph, userData);
        }
    }

    float generateWeight(float start, float end) {
        return (int) ((Math.random() * (end - start)) + start);
    }

    boolean generateEdge(){
        return (Math.random() < 0.5);
    }

    abstract void makeConnectionFromVertex(int index, Graph graph, EntryData userData);

}
