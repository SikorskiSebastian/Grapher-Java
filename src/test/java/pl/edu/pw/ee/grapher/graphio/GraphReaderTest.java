package pl.edu.pw.ee.grapher.graphio;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.testData.GraphCoherent;

import java.io.File;
import java.io.FileNotFoundException;

public class GraphReaderTest {
    @Test
    public void readFromFile_test_pass() throws FileNotFoundException {
        //given
        GraphCoherent expected = new GraphCoherent();
        File graphFile = new File("testFiles/resultGraph.txt");

        //when
        Graph result = GraphReader.readFromFile(graphFile);

        //then
        Assertions.assertEquals(expected.getGraph(), result);
    }

    @Test
    public void readFromFile_test_IllegalArgument_exception(){
        //given

        //when

        //then

    }

    @Test
    public void readFromFile_test_NullPointer_exception(){
        //given

        //when

        //then

    }
}
