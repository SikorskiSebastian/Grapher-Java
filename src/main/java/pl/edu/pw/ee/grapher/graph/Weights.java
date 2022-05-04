package pl.edu.pw.ee.grapher.graph;

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
                + weightLeft;
    }
}
