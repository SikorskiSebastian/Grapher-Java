package pl.edu.pw.ee.grapher.testData;

import pl.edu.pw.ee.grapher.EntryData;

public class TestEntry {
    private final EntryData entry;

    public TestEntry(){
        entry = new EntryData(2, 2);

        entry.setPoint(0, 1);
        entry.setPoint(1, 2);
    }

    public EntryData getEntry(){
        return entry;
    }
}
