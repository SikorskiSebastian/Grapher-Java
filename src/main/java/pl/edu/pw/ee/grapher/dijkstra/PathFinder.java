package pl.edu.pw.ee.grapher.dijkstra;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.utils.EntryData;

public interface PathFinder {
    @NotNull PathData findPath(@NotNull Graph graph, @NotNull EntryData userData);
}
