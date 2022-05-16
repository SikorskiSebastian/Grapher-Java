package pl.edu.pw.ee.grapher;

import java.io.File;

import static pl.edu.pw.ee.grapher.Constants.NO_MODE;

public class EntryData {
    private int mode;
    private int rows;
    private int columns;
    private float rangeStart;
    private float rangeEnd;
    private int startPoint;
    private int endPoint;
    private File graphFile;
    private int printMode;

    public EntryData(int rows, int columns){
        this.printMode = NO_MODE;
        this.mode = NO_MODE;
        this.rows = rows;
        this.columns = columns;
        this.rangeEnd = 0;
        this.rangeStart = 0;
        this.graphFile = null;
    }

    public EntryData(){
        this.printMode = NO_MODE;
        this.mode = NO_MODE;
        this.rows = 0;
        this.columns = 0;
        this.rangeEnd = 0;
        this.rangeStart = 0;
        this.startPoint = 0;
        this.endPoint = 0;
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

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
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

    public void setStartPoint(int startPoint) {
        this.startPoint = startPoint;
    }

    public void setEndPoint(int endPoint) {
        this.endPoint = endPoint;
    }

    public File getGraphFile() {
        return graphFile;
    }

    public void setColumns(int columns){
        this.columns = columns;
    }
    public void setRows(int rows){
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "EntryData{" +
                "\nMode=" + mode +
                "\n rows=" + rows +
                "\n columns=" + columns +
                "\n rangeStart=" + rangeStart +
                "\n rangeEnd=" + rangeEnd +
                "\n startPoint=" + startPoint +
                "\n endPoint=" + endPoint +
                "\n graphFile=" + graphFile +
                "\n printMode=" + printMode +
                "}\n";
    }
}
