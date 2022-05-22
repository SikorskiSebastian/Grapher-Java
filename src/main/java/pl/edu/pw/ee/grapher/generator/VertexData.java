package pl.edu.pw.ee.grapher.generator;

import java.util.Objects;

public class VertexData {
    private final int direction;
    private final int connection;
    private final int index;

    public VertexData(int index, int connection, int direction){
        this.index = index;
        this.direction = direction;
        this.connection = connection;
    }

    public int getConnection() {
        return connection;
    }

    public int getDirection() {
        return direction;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object){
            return true;
        }

        if (object == null || getClass() != object.getClass()){
            return false;
        }

        var instance = (VertexData) object;
        return direction == instance.direction && connection == instance.connection && index == instance.index;
    }

    @Override
    public int hashCode() {
        return 28 * Objects.hash(direction,  connection, index);
    }

    @Override
    public String toString() {
        return "VertexData {\n" +
                "\n direction = " + direction +
                ",\n connection = " + connection +
                ",\n index = " + index +
                "\n}";
    }
}
