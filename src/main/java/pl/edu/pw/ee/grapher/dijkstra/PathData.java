package pl.edu.pw.ee.grapher.dijkstra;

import java.util.Arrays;
import java.util.Objects;

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

        Arrays.fill(weights, Float.MAX_VALUE);
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

    public float getWeight(int index){
        return weights[index];
    }

    public void setPredecessor(int index, int predecessor){
        this.predecessors[index] = predecessor;
    }

    public void setWeight(int index, float weight){
        this.weights[index] = weight;
    }

    @Override
    public boolean equals(Object object){
        if (object == null){
            return false;
        }
        if (!(object instanceof PathData)){
            return false;
        }
        var pathData = (PathData) object;

        for (int i = 0; i < numOfVertices; i++){
            if (this.getWeight(i) != pathData.getWeight(i)){
                return false;
            }
            if (this.getPredecessor(i) != pathData.getPredecessor(i)){
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(numOfVertices, start, end);

        result = 31 * result + Arrays.hashCode(predecessors);
        result = 31 * result + Arrays.hashCode(weights);

        return result;
    }

    @Override
    public String toString(){
        return "\nPredecessors:\n" + Arrays.toString(predecessors) +"\n" + "Weights:\n" +
                Arrays.toString(weights) + "\n";
    }

    public static int[] pathInOrder (PathData path) {
        int currentPoint = path.getEnd();

        int[] pathInOrder = new int[path.numOfVertices];
        int size = 0;

        while (currentPoint != -1) {
            pathInOrder[size++] = currentPoint;
            currentPoint = path.getPredecessor(currentPoint);
        }
        int[] trimedPathInOrder = new int[size];
        System.arraycopy(pathInOrder,0, trimedPathInOrder, 0, size);

        for(int i = 0; i < trimedPathInOrder.length / 2; i++) {
            int tmp = trimedPathInOrder[i];
            trimedPathInOrder[i] = trimedPathInOrder[trimedPathInOrder.length - i - 1];
            trimedPathInOrder[trimedPathInOrder.length - i - 1] = tmp;
        }


        return trimedPathInOrder;
    }
}
