package pl.edu.pw.ee.grapher;

import java.io.File;

import static pl.edu.pw.ee.grapher.Constants.NO_MODE;

public class EntryData {
    private int mode;
    private final int rows; //i guess useless
    private final int columns; //i guess useless
    private float rangeStart;
    private float rangeEnd;
    private int[] points;
    private File graphFile;

    public EntryData(int rows, int columns){
        this.mode = NO_MODE;
        this.rows = rows;
        this.columns = columns;
        this.rangeEnd = 0;
        this.rangeStart = 0;
        this.points = new int[rows * columns];
        this.graphFile = null;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public float getRangeEnd() {
        return rangeEnd;
    }

    public float getRangeStart() {
        return rangeStart;
    }

    public int[] getPoints() {
        return points;
    }

    public void setGraphFile(File graphFile) {
        this.graphFile = graphFile;
    }

    public int getMode() {
        return mode;
    }

    public void setRangeEnd(float rangeEnd) {
        this.rangeEnd = rangeEnd;
    }

    public void setRangeStart(float rangeStart) {
        this.rangeStart = rangeStart;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public File getGraphFile() {
        return graphFile;
    }

    public void setPoints(int[] points) {
        this.points = points;
    }

    public int getPoint(int index){
        return this.points[index];
    }
}
