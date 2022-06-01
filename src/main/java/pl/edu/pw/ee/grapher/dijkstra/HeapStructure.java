package pl.edu.pw.ee.grapher.dijkstra;

public interface HeapStructure {
    void updatePriority(int id, float priority);

    int returnSmallerIndex(int parent);

    void swapChildren(int index, int parent);

    void addToHeap(float priority);

    void swapPriorities(int index);

    boolean ifParentBigger(int index);

    int popFromHeap();
}
