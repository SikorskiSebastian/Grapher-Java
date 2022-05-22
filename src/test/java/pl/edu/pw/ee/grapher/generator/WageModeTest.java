package pl.edu.pw.ee.grapher.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.utils.EntryData;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graph.Vertex;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

class WageModeTest {

    @Test
    void generateGraph_WageMode_test_pass(){
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
            var currentVertex = graph.getVertex(i);

            if(i - columns >= 0){
                if(!currentVertex.getExistence(UP)){
                   result = false;
                    break;
                }
            }
            if(i + 1 < columns * rows && (i + 1) % columns != 0){
                if(!currentVertex.getExistence(RIGHT)){
                    result = false;
                    break;
                }
            }
            if(i + columns < columns * rows){
                if(!currentVertex.getExistence(DOWN)){
                    result = false;
                    break;
                }
            }
            if(i - 1 >= 0 && i % columns != 0){
                if(!currentVertex.getExistence(LEFT)){
                    result = false;
                    break;
                }
            }
        }
        Assertions.assertEquals(expected, result);
    }

}
