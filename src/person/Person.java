package person;

import exception.*;

import java.time.LocalDateTime;

public class Person implements Comparable<Person>{
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double height; //Значение поля должно быть больше 0
    private java.time.LocalDateTime birthday; //Поле может быть null
    private ColorEye eyeColor; //Поле не может быть null
    private ColorHair hairColor; //Поле может быть null
    private Location location; //Поле может быть null

    @Override
    public int compareTo(Person o) {
        return (int)(this.id - o.id);
    }

//    public Person(long id, String name, double height, ColorEye eye, ColorHair hair, LocalDateTime birthday, long x, double y, int XL, double YL, Double ZL) throws NotBeNullException {
//        this.id = id;
//        this.name = name;
//        this.height = height;
//        this.eyeColor = eye;
//        this.hairColor = hair;
//        this.birthday = birthday;
//        this.coordinates = new Coordinates(x, y);
//        this.location = new Location(XL, YL, ZL);
//        this.creationDate = LocalDateTime.now();
//    }

    public Person(long id){
        this.id = id;
        this.creationDate = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "id: " + id + " имя: " + name + " рост: " + height + " цвет глаз: " + eyeColor + " цвет волос: " + hairColor +
                " Дата рождения: " + birthday + " Координаты: " + coordinates + " Координаты локации: " + location + " Время создания: " + creationDate;
    }

    public void setName(String name) throws NotBeNullException {
        if (name.equals("")) throw new NotBeNullException();
        this.name = name;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setCoordinates(Coordinates coordinates){
        if (coordinates.equals(""));
        this.coordinates = coordinates;
    }

    public void setHeight(double height) throws RangeException {
        if (height <= 0) throw new RangeException();
        this.height = height;
    }

    public void setEyeColor(String eyeColor) throws NotBeNullException {
        if (eyeColor.equals("")) throw new NotBeNullException();
        this.eyeColor = ColorEye.valueOf(eyeColor);
    }

    public void setHairColor(String hairColor) {
        if (hairColor.equals("")){
            this.hairColor = null;
        }else {
            this.hairColor = ColorHair.valueOf(hairColor);
        }
    }
    public void setLocation(Location location) {
        this.location = location;
    }


    public LocalDateTime getBirthday() {
        return birthday;
    }
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getHeight() {
        return height;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public ColorEye getEyeColor() {
        return eyeColor;
    }
    public ColorHair getHairColor() {
        return hairColor;
    }

    public Location getLocation() {
        return location;
    }
    public Coordinates getCoordinates() {
        return coordinates;
    }
}

