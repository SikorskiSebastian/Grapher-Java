package pl.edu.pw.ee.grapher.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graph.Vertex;

import static pl.edu.pw.ee.grapher.Constants.*;

public class WageModeTest {

    @Test
    public void generateGraph_WageMode_test_pass(){
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
            Vertex currentVertex = graph.getVertex(i);

            if(i - columns >= 0){
                if(!currentVertex.getExistence(UP)){
                   result = false;
                }
            }
            if(i + 1 < columns * rows && (i + 1) % columns != 0){
                if(!currentVertex.getExistence(RIGHT)){
                    result = false;
                }
            }
            if(i + columns < columns * rows){
                if(!currentVertex.getExistence(DOWN)){
                    result = false;
                }
            }
            if(i - 1 >= 0 && i % columns != 0){
                if(!currentVertex.getExistence(LEFT)){
                    result = false;
                }
            }

        }
        Assertions.assertEquals(expected, result);
    }

}
