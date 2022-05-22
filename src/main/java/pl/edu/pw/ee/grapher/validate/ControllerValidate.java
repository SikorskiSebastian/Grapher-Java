package pl.edu.pw.ee.grapher.validate;

import javafx.scene.control.TextField;
import org.jetbrains.annotations.NotNull;
import pl.edu.pw.ee.grapher.graph.Graph;
import pl.edu.pw.ee.grapher.utils.EntryData;

import java.io.File;

public class ControllerValidate {
    private ControllerValidate(){}

    public static boolean setUserGenData(@NotNull EntryData userData, @NotNull TextField columnInput, @NotNull TextField rowsInput, @NotNull TextField endInput, @NotNull TextField startInput){
        try {
            userData.setColumns(Integer.parseInt(columnInput.getText()));
            userData.setRows(Integer.parseInt(rowsInput.getText()));
            userData.setRangeEnd(Float.parseFloat(endInput.getText()));
            userData.setRangeStart(Float.parseFloat(startInput.getText()));

            if (userData.getRows() <= 0 || userData.getColumns() <= 0){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exception) {
            ControllerAlerts.popUserDataGenAlert();
            return false;
        }
        return true;
    }

    public static boolean setUserReadData(@NotNull EntryData userData, @NotNull TextField startPointInput, @NotNull TextField endPointInput){
        try {
            userData.setStartPoint(Integer.parseInt(startPointInput.getText()));
            userData.setEndPoint(Integer.parseInt(endPointInput.getText()));
            var start = userData.getStartPoint();
            var end = userData.getEndPoint();

            if (end < start || start < 0 || end <= 0){
                throw new NumberFormatException();
            }
        } catch (NumberFormatException exception) {
            ControllerAlerts.popUserReadAlert();
            return false;
        }
        return true;
    }

    public static boolean isGraphNull(Graph graph){
        if (graph == null){
            ControllerAlerts.popNullGraphGenAlert();
            return true;
        }
        return false;
    }

    public static boolean isFileNullGen(File file){
        if (file == null){
            ControllerAlerts.popFileGenAlert();
            return true;
        }
        return false;
    }

    public static boolean isFileNullRead(File file){
        if (file == null){
            ControllerAlerts.popFileReadAlert();
            return true;
        }
        return false;
    }

    public static boolean isGraphRead(Graph graph){
        if (graph == null){
            ControllerAlerts.popNullReadAlert();
            return false;
        }
        return true;
    }
}
