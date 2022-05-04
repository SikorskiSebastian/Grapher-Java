package pl.edu.pw.ee.grapher.graph;

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
                + connectionRight + " LeftConnection: " + connectionLeft;
    }
}
