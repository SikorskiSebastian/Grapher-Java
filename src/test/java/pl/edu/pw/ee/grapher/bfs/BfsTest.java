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
        var tester = new Bfs(graph.getGraph().getNumOfVertices());

        //when
        var result = tester.checkIfCoherent(graph.getGraph());

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void checkIfCoherent_not_coherent_graph(){
        //given
        var graph = new Graph(2,2);
        var expected = false;
        var tester = new Bfs(graph.getNumOfVertices());

        //when
        var result = tester.checkIfCoherent(graph);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    void calculateVertex_test_pass(){
        //given
        var index = 4;
        var numOfVertices = 12;
        var expected = 5;
        var tester = new Bfs(numOfVertices);

        //when
        var result = Bfs.calculateVertex(index, numOfVertices);

        //then
        Assertions.assertEquals(expected, result);
    }
}
