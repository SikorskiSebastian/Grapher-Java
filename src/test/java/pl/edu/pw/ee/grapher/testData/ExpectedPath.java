package pl.edu.pw.ee.grapher.testData;

import pl.edu.pw.ee.grapher.dijkstra.PathData;

public class ExpectedPath {
    private PathData pathData;

    public ExpectedPath(){
        pathData = new PathData(4);

        pathData.setPredecessor(0, 1);
        pathData.setPredecessor(1, -1);
        pathData.setPredecessor(2, 3);
        pathData.setPredecessor(3, 1);

        pathData.setWeight(0, (float) 9.961426);
        pathData.setWeight(1, (float) 0.0);
        pathData.setWeight(2, (float) 1.042517);
        pathData.setWeight(3, (float) 5.840989);
    }

    public PathData getPathData(){
        return pathData;
    }
}
