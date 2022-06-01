package pl.edu.pw.ee.grapher.utils;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static pl.edu.pw.ee.grapher.utils.Constants.*;

public class RadioButtonHandler {
    private final RadioButton wageModeRB;
    private final RadioButton standardRB;
    private final RadioButton extendedRB;
    private final RadioButton edgeModeRB;
    private final RadioButton randomModeRB;

    public RadioButtonHandler(RadioButton wageModeRB, RadioButton standardRB, RadioButton extendedRB, RadioButton edgeModeRB, RadioButton randomModeRB){
        this.edgeModeRB = edgeModeRB;
        this.extendedRB = extendedRB;
        this.randomModeRB = randomModeRB;
        this.standardRB = standardRB;
        this.wageModeRB = wageModeRB;
    }

    public void setSearchRadioButtons(@NotNull EntryData userData) {
        var pathModeGroup = new ToggleGroup();

        standardRB.setToggleGroup(pathModeGroup);
        extendedRB.setToggleGroup(pathModeGroup);
        standardRB.setSelected(true);
        userData.setPrintMode(STANDARD_MODE);
    }

    public void setGenerationRadioButtons(@NotNull EntryData userData) {
        var genModeGroup = new ToggleGroup();

        wageModeRB.setToggleGroup(genModeGroup);
        edgeModeRB.setToggleGroup(genModeGroup);
        randomModeRB.setToggleGroup(genModeGroup);
        wageModeRB.setSelected(true);
        userData.setMode(WEIGHT_MODE);
    }

    public void setActionButtons(EntryData userData){
        wageModeRB.setOnAction(event -> userData.setMode(WEIGHT_MODE));
        edgeModeRB.setOnAction(event -> userData.setMode(EDGE_MODE));
        randomModeRB.setOnAction(event -> userData.setMode(RANDOM_MODE));
        standardRB.setOnAction(event -> userData.setPrintMode(STANDARD_MODE));
        extendedRB.setOnAction(event -> userData.setPrintMode(EXTENDED_MODE));
    }

    public RadioButton getEdgeModeRB() {
        return edgeModeRB;
    }

    public RadioButton getExtendedRB() {
        return extendedRB;
    }

    public RadioButton getStandardRB() {
        return standardRB;
    }

    public RadioButton getRandomModeRB() {
        return randomModeRB;
    }

    public RadioButton getWageModeRB() {
        return wageModeRB;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }
        var handler = (RadioButtonHandler) object;

        return Objects.equals(wageModeRB, handler.wageModeRB) && Objects.equals(standardRB, handler.standardRB) && Objects.equals(extendedRB, handler.extendedRB) && Objects.equals(edgeModeRB, handler.edgeModeRB) && Objects.equals(randomModeRB, handler.randomModeRB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wageModeRB, standardRB, extendedRB, edgeModeRB, randomModeRB);
    }
}
