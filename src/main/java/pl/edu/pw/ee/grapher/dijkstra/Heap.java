package pl.edu.pw.ee.grapher.dijkstra;

import java.util.Arrays;

public class Heap {
    private int numOfVertices;
    private int length;
    private float[] priorities;
    private int[] nodes;
    private int[] nodesIndex;

    public Heap(int numOfVertices){
        this.numOfVertices = numOfVertices;
        this.length = 0;
        this.priorities = new float[numOfVertices];
        this.nodes = new int[numOfVertices];
        this.nodesIndex = new int[numOfVertices];

        Arrays.fill(priorities, Float.MAX_VALUE);
    }

    public int getLength() {
        return length;
    }

    public float[] getPriorities() {
        return priorities;
    }

    public int[] getNodes() {
        return nodes;
    }

    public int[] getNodesIndex() {
        return nodesIndex;
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setNodes(int[] nodes) {
        this.nodes = nodes;
    }

    public void updatePriority(int id, float priority){
        int index = nodesIndex[id];
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

        tmp = nodes[index];
        priorities[index] = nodes[parent];
        priorities[parent] = index;

        nodesIndex[nodes[index]] = index;
        nodesIndex[nodes[parent]] = parent;
    }

    public void addToHeap(float priority){
        int index = length;

        priorities[index] = priority;
        nodes[index] = index;
        length++;
        nodesIndex[index] = index;

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

        tmp = nodes[index];
        nodes[index] = nodes[parent];
        nodes[parent] = tmp;

        nodesIndex[nodes[index]] = index;
        nodesIndex[nodes[parent]] = parent;
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
        int toReturn = nodes[0];

        priorities[0] = priorities[--length];
        nodes[0] = nodes[length];
        nodesIndex[nodes[0]] = 0;

        while ((index = returnSmallerIndex(parent)) >= 0){
            swapChildren(parent, index);
            parent = index;
        }

        return toReturn;
    }
}
