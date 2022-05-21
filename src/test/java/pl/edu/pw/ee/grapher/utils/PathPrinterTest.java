package pl.edu.pw.ee.grapher.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.dijkstra.Dijkstra;
import pl.edu.pw.ee.grapher.dijkstra.PathData;
import pl.edu.pw.ee.grapher.testData.GraphCoherent;
import pl.edu.pw.ee.grapher.testData.TestEntry;

import static pl.edu.pw.ee.grapher.utils.PathPrinter.printExtendedPathToString;
import static pl.edu.pw.ee.grapher.utils.PathPrinter.printStandardPathToString;

class PathPrinterTest {
    @Test
    void printStandardPathToString_test_pass(){
        //given
        var expected = "(1;2): (1;2): 1 ----> 3 ----> 2\n";
        var graph = new GraphCoherent();
        var entry = new TestEntry();
        var path = Dijkstra.findPath(graph.getGraph(), entry.getEntry());

        //when
        var result = printStandardPathToString(PathData.pathInOrder(path), path);

        //then
        Assertions.assertEquals(expected,result);
    }

    @Test
    void printExtendedPathToString_test_pass(){
        ///given
        var expected = "(1;2): 1 (5,84) ----> 3 (1,04) ----> 2\n";
        var graph = new GraphCoherent();
        var entry = new TestEntry();
        var path = Dijkstra.findPath(graph.getGraph(), entry.getEntry());

        //when
        var result = printExtendedPathToString(PathData.pathInOrder(path), path);

        //then
        Assertions.assertEquals(expected,result.replace('.', ','));
    }
}
