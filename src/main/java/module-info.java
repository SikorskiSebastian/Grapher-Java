module pl.edu.pw.ee.grapher {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens pl.edu.pw.ee.grapher to javafx.fxml;
    exports pl.edu.pw.ee.grapher;
    exports pl.edu.pw.ee.grapher.generator;
    opens pl.edu.pw.ee.grapher.generator to javafx.fxml;
}