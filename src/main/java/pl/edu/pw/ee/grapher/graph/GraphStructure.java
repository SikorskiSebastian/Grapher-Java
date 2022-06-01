package pl.edu.pw.ee.grapher.graph;

public interface GraphStructure {
    Vertex getVertex(int index);

    int getNumOfVertices();

    int getColumns();

    int getRows();
}
