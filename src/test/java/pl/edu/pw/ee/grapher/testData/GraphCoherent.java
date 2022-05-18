package pl.edu.pw.ee.grapher.testData;

import pl.edu.pw.ee.grapher.graph.Graph;
import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class GraphCoherent {
    private final Graph graph;

    public GraphCoherent(){
        this.graph = new Graph(2, 2);

        graph.getVertex(0).setExistence(RIGHT, true);
        graph.getVertex(0).setExistence(DOWN, true);

        graph.getVertex(1).setExistence(LEFT, true);
        graph.getVertex(1).setExistence(DOWN, true);

        graph.getVertex(2).setExistence(RIGHT, true);
        graph.getVertex(2).setExistence(UP, true);

        graph.getVertex(3).setExistence(LEFT, true);
        graph.getVertex(3).setExistence(UP, true);

        graph.getVertex(0).setWeight(RIGHT, (float) 2.262818);
        graph.getVertex(0).setWeight(DOWN, (float) 5.466553);

        graph.getVertex(1).setWeight(LEFT, (float) 9.961426);
        graph.getVertex(1).setWeight(DOWN, (float) 5.840989);

        graph.getVertex(2).setWeight(RIGHT, (float) 7.304227);
        graph.getVertex(2).setWeight(UP, (float) 8.870625);

        graph.getVertex(3).setWeight(LEFT, (float) 1.042517);
        graph.getVertex(3).setWeight(UP, (float) 3.169910);

        graph.getVertex(0).setConnections(RIGHT, 1);
        graph.getVertex(0).setConnections(DOWN, 2);

        graph.getVertex(1).setConnections(LEFT, 0);
        graph.getVertex(1).setConnections(DOWN, 3);

        graph.getVertex(2).setConnections(RIGHT, 3);
        graph.getVertex(2).setConnections(UP, 0);

        graph.getVertex(3).setConnections(LEFT, 2);
        graph.getVertex(3).setConnections(UP, 1);
    }

    public Graph getGraph(){
        return graph;
    }
}
