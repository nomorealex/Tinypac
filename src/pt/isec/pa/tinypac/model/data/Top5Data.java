package pt.isec.pa.tinypac.model.data;

import java.io.Serial;
import java.io.Serializable;

public class Top5Data implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;
    private String name;
    private int points;
    public Top5Data(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }
    @Override
    public String toString() {
        return "Name -> "+name+" Score -> " +points;
    }
}
