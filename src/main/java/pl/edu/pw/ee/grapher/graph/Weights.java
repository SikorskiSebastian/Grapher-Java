package pl.edu.pw.ee.grapher.graph;

import java.util.Objects;

public class Weights {
    private float weightUp;
    private float weightDown;
    private float weightLeft;
    private float weightRight;

    public Weights(){
        this.weightDown = 0;
        this.weightRight = 0;
        this.weightLeft = 0;
        this.weightUp = 0;
    }

    public float getWeightDown() {
        return weightDown;
    }

    public float getWeightLeft() {
        return weightLeft;
    }

    public float getWeightRight() {
        return weightRight;
    }

    public float getWeightUp() {
        return weightUp;
    }

    public void setWeightDown(float weightDown) {
        this.weightDown = weightDown;
    }

    public void setWeightLeft(float weightLeft) {
        this.weightLeft = weightLeft;
    }

    public void setWeightRight(float weightRight) {
        this.weightRight = weightRight;
    }

    public void setWeightUp(float weightUp) {
        this.weightUp = weightUp;
    }

    @Override
    public String toString(){
        return "UpWeight: " + weightUp + " DownWeight: " + weightDown +" RightWeight: " + weightRight + " LeftWeight: "
                + weightLeft + "\n";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }

        Weights weights = (Weights) object;

        return Float.compare(weights.weightUp, weightUp) == 0 && Float.compare(weights.weightDown, weightDown) == 0
                && Float.compare(weights.weightLeft, weightLeft) == 0
                && Float.compare(weights.weightRight, weightRight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weightUp, weightDown, weightLeft, weightRight);
    }
}
