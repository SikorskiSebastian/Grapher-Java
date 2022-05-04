package pl.edu.pw.ee.grapher.dijkstra;

import java.util.Arrays;

public class PathData {
    private final int numOfVertices;
    private int start;
    private int end;
    private final int[] predecessors;
    private final float[] weights;

    public PathData(int numOfVertices){
        this.start = -1;
        this.end = -1;
        this.numOfVertices = numOfVertices;
        this.predecessors = new int[numOfVertices];
        this.weights = new float[numOfVertices];

        Arrays.fill(predecessors, -1);
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPredecessor(int index){
        return predecessors[index];
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public float getWeight(int index){
        return weights[index];
    }

    public void setPredecessor(int index, int predecessor){
        this.predecessors[index] = predecessor;
    }

    public void setWeight(int index, float weight){
        this.weights[index] = weight;
    }
}
