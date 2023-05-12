package person;

import exception.NotBeNullException;
import exception.RangeException;

import java.io.NotActiveException;

public class Coordinates {
    private Long x; //Поле не может быть null
    private Double y; //Максимальное значение поля: 55, Поле не может быть null
    public void setY (Double y) throws NotBeNullException, RangeException {
        if (y.equals("")) throw new NotBeNullException();
        if (y == Double.NEGATIVE_INFINITY) throw new IllegalArgumentException();
        if (y >= 55) throw new RangeException();
        this.y = y;
    }

    public void setX(Long x) throws NotBeNullException {
        if (x.equals("")) throw new NotBeNullException();
        if (x <= Long.MIN_VALUE) throw new IllegalArgumentException();
        if (x >= Long.MAX_VALUE) throw new IllegalArgumentException();
        this.x = x;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Long getX() {
        return x;
    }
    public Double getY() {
        return y;
    }
}
