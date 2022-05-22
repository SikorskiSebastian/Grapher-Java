package pl.edu.pw.ee.grapher.dijkstra;

import java.util.Arrays;
import java.util.Objects;

public class Heap {
    private final int numOfVertices;
    private int length;
    private final float[] priorities;
    private final int[] vertices;
    private final int[] vertexIndex;

    public Heap(int numOfVertices){
        this.numOfVertices = numOfVertices;
        this.length = 0;
        this.priorities = new float[numOfVertices];
        this.vertices = new int[numOfVertices];
        this.vertexIndex = new int[numOfVertices];

        Arrays.fill(priorities, Float.MAX_VALUE);
    }

    public int getLength() {
        return length;
    }

    public float getPriority(int index){
        return priorities[index];
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setPriorities(int index, float priority){
        this.priorities[index] = priority;
    }

    public void updatePriority(int id, float priority){
        int index = vertexIndex[id];
        float prevPriority = priorities[index];

        priorities[index] = priority;

        if (prevPriority < priority){
            int temp = 0;
            while ((temp = returnSmallerIndex(index)) >= 0){
                swapChildren(index, temp);
                index = temp;
            }
        }
        else {
            while (ifParentBigger(index)){
                swapPriorities(index);
                index = (index - 1) / 2;
            }
        }
    }

    public int returnSmallerIndex(int parent){
        int smallerIndex = 0;
        int leftChild = 2 * parent + 1;
        int rightChild = 2 * parent + 2;

        if (leftChild >= length){
            return -1;
        }
        if (rightChild >= length){
            smallerIndex = leftChild;
        }
        else if (priorities[leftChild] < priorities[rightChild]){
            smallerIndex = leftChild;
        }
        else {
            smallerIndex = rightChild;
        }
        if(priorities[smallerIndex] < priorities[parent]){
            return smallerIndex;
        }

        return -1;
    }

    public void swapChildren(int index, int parent){
        float temp = 0;
        int tmp = 0;

        temp = priorities[index];
        priorities[index] = priorities[parent];
        priorities[parent] = temp;

        tmp = vertices[index];
        vertices[index] = vertices[parent];
        vertices[parent] = tmp;

        vertexIndex[vertices[index]] = index;
        vertexIndex[vertices[parent]] = parent;
    }

    public void addToHeap(float priority){
        int index = length;

        priorities[index] = priority;
        vertices[index] = index;
        length++;
        vertexIndex[index] = index;

        while (ifParentBigger(index)){
            swapPriorities(index);
            index = (index - 1) /2;
        }
    }

    public void swapPriorities(int index){
        int parent = (index - 1) / 2;
        float temp = 0;
        int tmp = 0;

        temp = priorities[index];
        priorities[index] = priorities[parent];
        priorities[parent] = temp;

        tmp = vertices[index];
        vertices[index] = vertices[parent];
        vertices[parent] = tmp;

        vertexIndex[vertices[index]] = index;
        vertexIndex[vertices[parent]] = parent;
    }

    public boolean ifParentBigger(int index){
        int parent = (index - 1) / 2;

        if (index == 0){
            return false;
        }
        return priorities[index] < priorities[parent];
    }

    public int popFromHeap(){
        int parent = 0;
        int index = 0;
        int toReturn = vertices[0];

        priorities[0] = priorities[--length];
        vertices[0] = vertices[length];
        vertexIndex[vertices[0]] = 0;

        while ((index = returnSmallerIndex(parent)) >= 0){
            swapChildren(parent, index);
            parent = index;
        }

        return toReturn;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        var heap = (Heap) object;

        return numOfVertices == heap.numOfVertices && length == heap.length && Arrays.equals(priorities, heap.priorities) && Arrays.equals(vertices, heap.vertices) && Arrays.equals(vertexIndex, heap.vertexIndex);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(numOfVertices, length);

        result = 31 * result + Arrays.hashCode(priorities);
        result = 31 * result + Arrays.hashCode(vertices);
        result = 31 * result + Arrays.hashCode(vertexIndex);

        return result;
    }

    @Override
    public String toString() {
        return "Heap{" +
                "\nnumOfVertices=" + numOfVertices +
                "\n length=" + length +
                "\n priorities=" + Arrays.toString(priorities) +
                "\n vertices=" + Arrays.toString(vertices) +
                "\n vertexIndex=" + Arrays.toString(vertexIndex) +
                "}\n";
    }
}
