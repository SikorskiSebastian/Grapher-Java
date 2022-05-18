package pl.edu.pw.ee.grapher.utils;

import pl.edu.pw.ee.grapher.dijkstra.PathData;

public class PathPrinter {
    public static String printStandardPathToString(int[] pathInOrder, PathData path) {
        var pathConsoleOutput = new StringBuilder();

        pathConsoleOutput.append(String.format("(%d;%d): ", path.getStart(), path.getEnd()));

        for (int vertex = 0; vertex <pathInOrder.length - 1; vertex++) {
            pathConsoleOutput.append(String.format("%d ----> ", vertex));
        }
        pathConsoleOutput.append(pathInOrder[pathInOrder.length - 1]).append("\n");

        return pathConsoleOutput.toString();
    }

    public static String printExtendedPathToString(int[] pathInOrder, PathData path) {
        var pathConsoleOutput = new StringBuilder();

        pathConsoleOutput.append(String.format("(%d;%d): ", path.getStart(), path.getEnd()));
        for (int i = 0; i < pathInOrder.length - 1; i++){
            pathConsoleOutput.append(String.format("%d (%.2f) ----> ",pathInOrder[i], path.getWeight(pathInOrder[i+1])));
        }
        pathConsoleOutput.append(pathInOrder[pathInOrder.length-1]).append("\n");

        return  pathConsoleOutput.toString();
    }
}
