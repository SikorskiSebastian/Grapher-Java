package pl.edu.pw.ee.grapher.dijkstra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.testData.ExpectedPath;
import pl.edu.pw.ee.grapher.testData.GraphCoherent;
import pl.edu.pw.ee.grapher.testData.TestEntry;
class DijkstraTest {
    @Test
    void findPath_test_pass(){
        //given
        GraphCoherent graph = new GraphCoherent();
        TestEntry entry = new TestEntry();
        ExpectedPath expected = new ExpectedPath();

        //when
        PathData result = Dijkstra.findPath(graph.getGraph(), entry.getEntry());

        //then
        Assertions.assertEquals(expected.getPathData(), result);
    }
}
