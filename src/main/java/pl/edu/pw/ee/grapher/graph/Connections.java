package pl.edu.pw.ee.grapher.graph;

import java.util.Objects;

public class Connections {
    private float connectionUp;
    private float connectionDown;
    private float connectionLeft;
    private float connectionRight;

    public Connections(){
        this.connectionDown = 0;
        this.connectionLeft = 0;
        this.connectionRight = 0;
        this.connectionUp = 0;
    }

    public float getConnectionDown() {
        return connectionDown;
    }

    public float getConnectionLeft() {
        return connectionLeft;
    }

    public float getConnectionRight() {
        return connectionRight;
    }

    public float getConnectionUp() {
        return connectionUp;
    }

    public void setConnectionDown(float connectionDown) {
        this.connectionDown = connectionDown;
    }

    public void setConnectionLeft(float connectionLeft) {
        this.connectionLeft = connectionLeft;
    }

    public void setConnectionRight(float connectionRight) {
        this.connectionRight = connectionRight;
    }

    public void setConnectionUp(float connectionUp) {
        this.connectionUp = connectionUp;
    }

    @Override
    public String toString(){
        return "UpConnection: " + connectionUp + " DownConnection: " + connectionDown +" RightConnection: "
                + connectionRight + " LeftConnection: " + connectionLeft +"\n";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }
        if (object == null || getClass() != object.getClass()){
            return false;
        }

        Connections that = (Connections) object;

        return Float.compare(that.connectionUp, connectionUp) == 0
                && Float.compare(that.connectionDown, connectionDown) == 0
                && Float.compare(that.connectionLeft, connectionLeft) == 0
                && Float.compare(that.connectionRight, connectionRight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionUp, connectionDown, connectionLeft, connectionRight);
    }
}
