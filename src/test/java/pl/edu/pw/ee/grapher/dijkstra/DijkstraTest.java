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
        var graph = new GraphCoherent();
        var entry = new TestEntry();
        var expected = new ExpectedPath();

        //when
        var result = Dijkstra.findPath(graph.getGraph(), entry.getEntry());

        //then
        Assertions.assertEquals(expected.getPathData(), result);
    }
}
