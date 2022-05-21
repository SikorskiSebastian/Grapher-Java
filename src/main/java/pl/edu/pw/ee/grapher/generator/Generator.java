package pl.edu.pw.ee.grapher.generator;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.utils.EntryData;

public interface Generator {
    void makeConnectionFromVertex(int index, @NotNull Graph graph, EntryData userData);
    void generate(@NotNull Graph graph, EntryData userData);
}
