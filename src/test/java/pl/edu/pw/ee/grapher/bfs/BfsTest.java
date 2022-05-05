package pl.edu.pw.ee.grapher.bfs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.testGraph.GraphCoherent;

public class BfsTest {
    @Test
    public void checkIfCoherent_coherent_graph(){
        //given
        GraphCoherent graph = new GraphCoherent();
        boolean expected = true;

        //when
        boolean result = Bfs.checkIfCoherent(graph.getGraph());

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void checkIfCoherent_not_coherent_graph(){
        //given
        Graph graph = new Graph(2,2);
        boolean expected = false;

        //when
        boolean result = Bfs.checkIfCoherent(graph);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void calculateVertex_test_pass(){
        //given
        int index = 4;
        int numOfVertices = 12;
        int expected = 5;

        //when
        int result = Bfs.calculateVertex(index, numOfVertices);

        //then
        Assertions.assertEquals(expected, result);
    }
}
