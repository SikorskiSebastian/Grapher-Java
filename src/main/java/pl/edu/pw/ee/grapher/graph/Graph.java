package pl.edu.pw.ee.grapher.graph;

import java.util.Arrays;

public class Graph {
    private Vertex[] vertices;
    private int rows;
    private int columns;

    public Graph(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
        this.vertices = new Vertex[rows * columns];

        for (int i = 0; i < vertices.length; i++){
            vertices[i] = new Vertex();
        }
    }

    public int getNumOfVertices(){
        return this.rows * this.columns;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public Vertex getVertex(int index){
        return vertices[index];
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setVertices(Vertex[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        Graph graph = (Graph) object;

        return rows == graph.rows && columns == graph.columns && Arrays.equals(vertices, graph.vertices);
    }

    @Override
    public String toString() {
        return "Graph{" +
                "Vertices=" + Arrays.toString(vertices) +
                "\n, Rows=" + rows +
                "\n, Columns=" + columns +
                "\n";
    }
}
