package pl.edu.pw.ee.grapher.dijkstra;

import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;

public class DijkstraTest {
    @Test
    public void findPath_test_pass(){ //TODO po napisaniu generacji grafu dokonczyc test
        //given
        Graph graph = new Graph(2, 2);
        EntryData userData = new EntryData(2, 2);

        //when
        PathData result = Dijkstra.findPath(graph, userData, 0);

        //then

        assert(true);
    }
}
