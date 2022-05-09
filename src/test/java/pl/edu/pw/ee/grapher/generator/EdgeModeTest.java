package pl.edu.pw.ee.grapher.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.EntryData;
import pl.edu.pw.ee.grapher.bfs.Bfs;
import pl.edu.pw.ee.grapher.graph.Graph;

class EdgeModeTest {

    @Test
    void generateGraph_EdgeMode_test_pass(){
        //given
        int rows = 3;
        int columns = 3;
        float rangeStart = 0F;
        float rangeEnd = 1F;
        boolean expected = true;

        //when
        EntryData userData = new EntryData(rows, columns);
        userData.setRangeStart(rangeStart);
        userData.setRangeEnd(rangeEnd);
        GraphGenerator generator = new EdgeMode();
        Graph graph = new Graph(userData.getRows(),userData.getColumns());
        generator.generate(graph,userData);

        //then
        boolean result = Bfs.checkIfCoherent(graph);

        Assertions.assertEquals(expected,result);
    }

}
