package pl.edu.pw.ee.grapher.graph;

import java.util.Objects;

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

    @Override
    public String toString(){
        return this.existence.toString() + "\n" + this.connections.toString() + "\n" + this.weights.toString() + "\n";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }

        Vertex vertex = (Vertex) object;

        return Objects.equals(existence, vertex.existence) && Objects.equals(connections, vertex.connections)
                && Objects.equals(weights, vertex.weights);
    }

    @Override
    public int hashCode() {
        return Objects.hash(existence, connections, weights);
    }
}
