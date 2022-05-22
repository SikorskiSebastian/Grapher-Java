package pl.edu.pw.ee.grapher.bfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.testData.GraphCoherent;

class BfsTest {
    @Test
    void checkIfCoherent_coherent_graph(){
        //given
        var graph = new GraphCoherent();
        var expected = true;

        //when
        var result = Bfs.checkIfCoherent(graph.getGraph());

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void checkIfCoherent_not_coherent_graph(){
        //given
        var graph = new Graph(2,2);
        var expected = false;

        //when
        var result = Bfs.checkIfCoherent(graph);

        //then
        Assertions.assertEquals(expected, result);
    }
}
