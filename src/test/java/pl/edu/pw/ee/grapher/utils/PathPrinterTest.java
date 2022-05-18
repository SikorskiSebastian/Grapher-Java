package pl.edu.pw.ee.grapher.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.dijkstra.Dijkstra;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.testData.GraphCoherent;
import pl.edu.pw.ee.grapher.testData.TestEntry;

import static pl.edu.pw.ee.grapher.utils.PathPrinter.printExtendedPathToString;
import static pl.edu.pw.ee.grapher.utils.PathPrinter.printStandardPathToString;

public class PathPrinterTest {

    @Test
    void printStandardPathToString_Test(){
        //given
        var expected = "(1;2): 0 ----> 1 ----> 2\n";
        var graph = new GraphCoherent();
        var path = Dijkstra.findPath(graph.getGraph(), new TestEntry().getEntry());

        //when
        var result = printStandardPathToString(PathData.pathInOrder(path), path);

        //then
        Assertions.assertEquals(expected,result);
    }

    @Test
    void printExtendedPathToString_Test(){
        ///given
        var expected = "(1;2): 1 (5,84) ----> 3 (1,04) ----> 2\n";
        var graph = new GraphCoherent();
        var path = Dijkstra.findPath(graph.getGraph(), new TestEntry().getEntry());

        //when
        var result = printExtendedPathToString(PathData.pathInOrder(path), path);

        //then
        Assertions.assertEquals(expected,result);
    }

}
