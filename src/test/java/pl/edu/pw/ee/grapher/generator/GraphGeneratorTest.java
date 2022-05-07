package pl.edu.pw.ee.grapher.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;

public class GraphGeneratorTest {

    @Test
    public void generateGraph_EdgeWeights_test_pass(){
        //given
        int rows = 3;
        int columns = 3;
        float rangeStart = 0F;
        float rangeEnd = 1F;
        boolean expected = true;
        boolean result = true;


        //when
        EntryData userData = new EntryData(rows, columns);
        userData.setRangeStart(rangeStart);
        userData.setRangeEnd(rangeEnd);
        GraphGenerator generator = new WageMode();
        Graph graph = new Graph(userData.getRows(),userData.getColumns());
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
