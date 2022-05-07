package pl.edu.pw.ee.grapher;

import java.io.File;
import java.util.Arrays;

import static pl.edu.pw.ee.grapher.Constants.NO_MODE;

public class EntryData {
    private int mode;
    private final int rows; //i guess useless
    private final int columns; //i guess useless
    private float rangeStart;
    private float rangeEnd;
    private int[] points;
    private File graphFile;
    private int printMode;

    public EntryData(int rows, int columns){
        this.printMode = NO_MODE;
        this.mode = NO_MODE;
        this.rows = rows;
        this.columns = columns;
        this.rangeEnd = 0;
        this.rangeStart = 0;
        this.points = new int[rows * columns];
        this.graphFile = null;
    }

    public int getPrintMode() {
        return printMode;
    }

    public void setPrintMode(int printMode) {
        this.printMode = printMode;
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

    public void setPoint(int index, int point){
        this.points[index] = point;
    }

    public int getPoint(int index){
        return this.points[index];
    }

    @Override
    public String toString() {
        return "EntryData{" +
                "\nMode=" + mode +
                "\n rows=" + rows +
                "\n columns=" + columns +
                "\n rangeStart=" + rangeStart +
                "\n rangeEnd=" + rangeEnd +
                "\n points=" + Arrays.toString(points) +
                "\n graphFile=" + graphFile +
                "\n printMode=" + printMode +
                "}\n";
    }
}
