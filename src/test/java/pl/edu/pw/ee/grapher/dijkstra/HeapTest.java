package pl.edu.pw.ee.grapher.dijkstra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HeapTest {
    private Heap heap;

    @BeforeEach
    public void initHeap(){
        this.heap = new Heap(6);
    }

    @Test
    public void update_priority_prevPriority_higher_priority(){
        //given
        int index = 2;
        float priority = 4;
        float expected = 5;

        //when
        heap.setPriorities(index, 5);
        heap.updatePriority(index, priority);
        float result = heap.getPriority(index);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void update_priority_prevPriority_less_priority(){
        //given
        int index = 2;
        float priority = 5;
        float expected = 3;

        //when
        heap.setPriorities(index, 3);
        heap.updatePriority(index, priority);
        float result = heap.getPriority(index);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void returnSmallerIndex_leftChild_higher_length(){
        //given
        int parent = 2;
        int expected = -1;

        //when
        int result = heap.returnSmallerIndex(parent);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void returnSmallerIndex_rightChild_higher_length(){
        //given
        int parent = 1;
        int expected = 3;

        //when
        heap.setLength(4);
        heap.setPriorities(3, 1);
        int result = heap.returnSmallerIndex(parent);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void returnSmallerIndex_leftChildPriority_higher_rightChildPriority(){
        //given
        int parent = 0;
        int expected = -1;

        //when
        heap.setPriorities(1, 4);
        heap.setPriorities(2, 2);
        int result = heap.returnSmallerIndex(parent);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void returnSmallerIndex_smallerIndexPriority_higher_parentPriority(){
        //given
        int parent = 1;
        int expected = 3;

        //when
        heap.setLength(4);
        heap.setPriorities(3, 2);
        heap.setPriorities(parent, 5);
        int result = heap.returnSmallerIndex(parent);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void swapChildren_test_pass(){
        //given
        int index = 2;
        int parent = 4;
        float expectedIndex = 7;
        float expectedParent = 6;

        //when
        heap.setLength(8);
        heap.setPriorities(index, 6);
        heap.setPriorities(parent, 7);
        heap.swapChildren(index, parent);
        float resultIndex = heap.getPriority(index);
        float resultParent = heap.getPriority(parent);

        //then
        Assertions.assertEquals(expectedIndex, resultIndex);
        Assertions.assertEquals(expectedParent, resultParent);
    }

    @Test
    public void addToHeap_test_pass(){
        //given
        float priority = 4;

        //when
        heap.addToHeap(priority);
        float result = heap.getPriority(0);

        //then
        Assertions.assertEquals(priority, result);
    }

    @Test
    public void swapPriorities_test_pass(){
        //given
        int index = 3;
        float expected = Float.MAX_VALUE;

        //when
        heap.setLength(4);
        heap.setPriorities(index, 5);
        heap.swapPriorities(index);
        float result = heap.getPriority(index);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void ifParentBigger_test_pass(){
        //given
        int index = 3;
        int parent = (index - 1) / 2;
        boolean expected = true;

        //when
        heap.setPriorities(index, 3);
        heap.setPriorities(parent, 6);
        boolean result = heap.ifParentBigger(index);

        //then
        Assertions.assertEquals(expected, result);
    }

    @Test
    public void ifParentBigger_index_equal_0(){
        //given
        int index = 0;
        boolean expected = false;

        //when
        boolean result = heap.ifParentBigger(index);

        //then
        Assertions.assertEquals(expected, result);
    }

}
