package pl.edu.pw.ee.grapher.bfs;

import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;

public interface CoherenceChecker {
    boolean checkIfCoherent(@NotNull Graph graph);
}
