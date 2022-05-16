package pl.edu.pw.ee.grapher.testData;

import pl.edu.pw.ee.grapher.EntryData;

public class TestEntry {
    private final EntryData entry;

    public TestEntry(){
        entry = new EntryData(2, 2);

        entry.setStartPoint(1);
        entry.setEndPoint(2);
    }

    public EntryData getEntry(){
        return entry;
    }
}
