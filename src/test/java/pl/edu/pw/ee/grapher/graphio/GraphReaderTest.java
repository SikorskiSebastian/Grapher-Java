package pl.edu.pw.ee.grapher.graphio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.testData.GraphCoherent;

import java.io.File;
import java.io.FileNotFoundException;

class GraphReaderTest {
    @Test
    void readFromFile_test_pass(){
        //given
        var expected = new GraphCoherent();
        var graphFile = new File("src/test/resources/resultGraph.txt");

        //when
        var result = GraphReader.readFromFile(graphFile);

        //then
        Assertions.assertEquals(expected.getGraph(), result);
    }

    @Test
    void readFromFile_test_IllegalArgument_exception(){
        //given
        var graphFile = new File("src/test/resources/rowsEqualsZero.txt");

        //when

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> GraphReader.readFromFile(graphFile));
    }

    @Test
    void readFromFile_test_IllegalState_exception(){
        //given
        var graphFile = new File("src/test/resources/illegalState.txt");

        //when

        //then
        Assertions.assertThrows(IllegalStateException.class, () -> GraphReader.readFromFile(graphFile));
    }
}
