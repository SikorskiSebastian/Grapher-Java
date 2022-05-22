package pl.edu.pw.ee.grapher.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.bfs.Bfs;
import pl.edu.pw.ee.grapher.graph.Graph;

class EdgeModeTest {

    @Test
    void generateGraph_EdgeMode_test_pass(){
        //given
        var rows = 3;
        var columns = 3;
        var rangeStart = 0F;
        var rangeEnd = 1F;
        var expected = true;

        //when
        var userData = new EntryData(rows, columns);
        userData.setRangeStart(rangeStart);
        userData.setRangeEnd(rangeEnd);
        var generator = new EdgeMode();
        var graph = new Graph(userData.getRows(),userData.getColumns());
        generator.generate(graph,userData);

        //then
        var result = Bfs.checkIfCoherent(graph);

        Assertions.assertEquals(expected,result);
    }

}
