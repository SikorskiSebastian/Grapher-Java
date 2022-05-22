package pl.edu.pw.ee.grapher.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;

class GraphGeneratorTest {

    @Test
    void generateGraph_EdgeWeights_test_pass(){
        //given
        var rows = 3;
        var columns = 3;
        var rangeStart = 0F;
        var rangeEnd = 1F;
        var expected = true;
        var result = true;


        //when
        var userData = new EntryData(rows, columns);
        userData.setRangeStart(rangeStart);
        userData.setRangeEnd(rangeEnd);
        var generator = new WageMode();
        var graph = new Graph(userData.getRows(),userData.getColumns());
        generator.generate(graph,userData);

        //then
        for(int i = 0; i < rows * columns; i++){
            for(int k = 0; k < 4; k++){
                if(graph.getVertex(i).getExistence(k)){
                    if(!(graph.getVertex(i).getWeight(k) >= rangeStart && graph.getVertex(i).getWeight(k) <= rangeEnd)){
                        result = false;
                    }
                }
            }
        }
        Assertions.assertEquals(expected,result);

    }

}
