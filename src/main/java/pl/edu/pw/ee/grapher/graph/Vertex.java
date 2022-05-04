package pl.edu.pw.ee.grapher.graph;

import org.jetbrains.annotations.NotNull;

public class Vertex {
    private Existence existence;
    private Connections connections;
    private Weights weights;

    public Vertex(){
        this.existence = new Existence();
        this.connections = new Connections();
        this.weights = new Weights();
    }

    public Connections getConnections() {
        return connections;
    }

    public Existence getExistence() {
        return existence;
    }

    public void setConnections(Connections connections) {
        this.connections = connections;
    }

    public void setExistence(Existence existence) {
        this.existence = existence;
    }

    public void setWeights(Weights weights) {
        this.weights = weights;
    }
}
