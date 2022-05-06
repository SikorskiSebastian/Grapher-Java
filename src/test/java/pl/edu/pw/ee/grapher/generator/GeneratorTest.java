package pl.edu.pw.ee.grapher.generator;

import com.sun.javafx.geom.Edge;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.edu.pw.ee.grapher.EntryData;
import pl.edu.pw.ee.grapher.bfs.Bfs;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.graph.Vertex;

import static pl.edu.pw.ee.grapher.Constants.*;

public class GeneratorTest {

    @Test
    public void generateGraph_EdgeWeights_test_pass(){

        //sprawdzamy czy waga każdej krawędzi mieśći sie w granicach losowania

        //given
        int rows = 3;
        int columns = 3;
        float rangeStart = 0F;
        float rangeEnd = 1F;

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
                        Assertions.assertTrue(false);
                    }
                }
            }
        }
        Assertions.assertTrue(true);

    }

    @Test
    public void generateGraph_WageMode_test_pass(){

        //sprawdzamy czy w trybie WM istnieje każde możliwe połączenie

        //given
        int rows = 3;
        int columns = 3;
        float rangeStart = 0F;
        float rangeEnd = 1F;

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

            //check up
            if(i - columns >= 0 && i - columns < columns * rows){
                if(!currentVertex.getExistence(UP)){
                    Assertions.assertTrue(false);
                }
            }
            //check right
            if(i + 1 < columns * rows && (i + 1) % columns != 0){
                if(!currentVertex.getExistence(RIGHT)){
                    Assertions.assertTrue(false);
                }
            }
            //check down
            if(i + columns > 0 && i + columns < columns * rows){
                if(!currentVertex.getExistence(DOWN)){
                    Assertions.assertTrue(false);
                }
            }
            //check left
            if(i - 1 >= 0 && i % columns != 0){
                if(!currentVertex.getExistence(LEFT)){
                    Assertions.assertTrue(false);
                }
            }

        }
        Assertions.assertTrue(true);

    }

    @Test
    public void generateGraph_EdgeMode_test_pass(){

        //sprawdzamy czy waga każdej krawędzi mieśći sie w granicach losowania

        //given
        int rows = 3;
        int columns = 3;
        float rangeStart = 0F;
        float rangeEnd = 1F;

        //when
        EntryData userData = new EntryData(rows, columns);
        userData.setRangeStart(rangeStart);
        userData.setRangeEnd(rangeEnd);
        GraphGenerator generator = new EdgeMode();
        Graph graph = new Graph(userData.getRows(),userData.getColumns());
        generator.generate(graph,userData);

        //then
        if(!Bfs.checkIfCoherent(graph)){
            Assertions.assertTrue(false);
        }
        Assertions.assertTrue(true);

    }
}
