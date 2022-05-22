package pl.edu.pw.ee.grapher.validate;

import javafx.scene.control.Alert;

import static pl.edu.pw.ee.grapher.utils.Constants.GRAPHER_ERROR;

public class ControllerAlerts {
    private ControllerAlerts(){}

    public static void popUserDataGenAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("NumberFormatException");
        alert.setContentText("You have given wrong number of generation items!");
        alert.show();
    }

    public static void popNullGraphGenAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("Nothing to save!");
        alert.setContentText("To save graph to file you have to first generate it!");
        alert.show();
    }

    public static void popFileGenAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("I can not save to file!");
        alert.setContentText("File does not exist.");
        alert.show();
    }

    public static void popFileReadAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("I can not read from file!");
        alert.setContentText("File either does not exist or has nothing to be read.");
        alert.show();
    }

    public static void popNullReadAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("Graph has not been inserted!");
        alert.setContentText("File either has bad format or is not readable.");
        alert.show();
    }

    public static void popUserReadAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("Given start or end is in wrong range!");
        alert.setContentText("End cannot be bigger than start and they can not be less than 0.");
        alert.show();
    }

    public static void popNullGraphAlert(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("Graph is null!");
        alert.setContentText("I can not work on null item.");
        alert.show();
    }

    public static void popNotCoherentGraph(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(GRAPHER_ERROR);
        alert.setHeaderText("Graph is not coherent!");
        alert.setContentText("You have to give me coherent graph because it is impossible to find path.");
        alert.show();
    }
}
