package person;

import exception.NotBeNullException;

public class Location {
    private int x;
    private double y;
    private Double z; //Поле не может быть null


    public void setZ(Double z) throws NotBeNullException {
        if (z == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
        if (z == Double.POSITIVE_INFINITY) throw new IllegalArgumentException();
        if (z.equals("")) throw new NotBeNullException();
        this.z = z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Икс: " + x + " Игрек: " + y + " Зэд: " + z;
    }
}
