package pl.edu.pw.ee.grapher.graph;

import java.util.Arrays;

public class Vertex {
    private final boolean[] existence;
    private final int[] connections;
    private final float[] weights;

    public Vertex(){
        this.existence = new boolean[4];
        this.connections = new int[4];
        this.weights = new float[4];

        Arrays.fill(weights, -1);
        Arrays.fill(connections, -1);
        Arrays.fill(existence, false);
    }

    public int getConnection(int index){
        return connections[index];
    }

    public float getWeight(int index){
        return weights[index];
    }

    public boolean getExistence(int index){
        return existence[index];
    }

    public void setWeight(int index, float weight){
        this.weights[index] = weight;
    }

    public void setConnections(int index, int connection){
        this.connections[index] = connection;
    }

    public void setExistence(int index, boolean exist){
        this.existence[index] = exist;
    }
}
