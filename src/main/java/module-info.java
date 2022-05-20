module pl.edu.pw.ee.grapher {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;

    exports pl.edu.pw.ee.grapher.graph;
    exports pl.edu.pw.ee.grapher.dijkstra;

    exports pl.edu.pw.ee.grapher.generator;
    opens pl.edu.pw.ee.grapher.generator to javafx.fxml;

    exports pl.edu.pw.ee.grapher.utils;
    opens pl.edu.pw.ee.grapher.utils to javafx.fxml;

    exports pl.edu.pw.ee.grapher;
    opens pl.edu.pw.ee.grapher to javafx.fxml;
}