package app;

import person.*;

import java.net.SocketException;
import java.nio.file.Watchable;
import java.time.LocalDateTime;
import java.util.Scanner;
import exception.*;

public class Creator {
    App app;
    Reader reader;
    private Person p;

    public Creator(App app){
        this.app = app;
    }

//    private String WaitData() {
//        try {
//            return reader.WaitData();
//        } catch (SocketException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public Person createPerson(long id, Reader reader){
        this.reader = reader;
        p = new Person(id); // app.comMan.commandList.col_manager.set.last().getId() + 1
        NameAsk();
        CoordinatesAsker();
        HeightAsker();
        BirthdayAsker();
        EyeColorAsker();
        HairColorAsker();
        LocationAsker();
        return p;
    }
    public Person createPerson(Reader reader){
        this.reader = reader;
        p = new Person(); // app.comMan.commandList.col_manager.set.last().getId() + 1
        IdAsker();
        NameAsk();
        CoordinatesAsker();
        CreationDateAsker();
        HeightAsker();
        BirthdayAsker2();
        EyeColorAsker();
        HairColorAsker();
        LocationAsker();
        return p;
    }

    private void IdAsker(){
        p.setId(Long.parseLong(reader.WaitData()));
    }
    public void NameAsk(){  
        while (true){
            try{
                System.out.println("Введите имя: ");
                p.setName(reader.WaitData());
                break;
            }catch (NotBeNullException e){
                System.out.println("Маловато будет");
            }
        }
    }
    
    public void CoordinatesAsker(){
        Coordinates cor = new Coordinates();
        XCoordAsker(cor);
        YCoordAsker(cor);
        p.setCoordinates(cor);
    }

    public void YCoordAsker(Coordinates coordinates) {
        while(true){
            try{
                System.out.println("Введите Y для координат:");
                coordinates.setY(Double.parseDouble(reader.WaitData()));
                break;
            }catch (NumberFormatException e){
                System.out.println("Вид данных не определён, попробуйте снова");
            }catch (NotBeNullException e){
                System.out.println("НЕ может быть пустым");
            }catch (RangeException e){
                System.out.println("Вы вышли за границу, возвращайтесь");
            }
        }
    }

    public void XCoordAsker(Coordinates coordinates){
        while(true){
            try{
                System.out.println("Введите X для координат:");
                coordinates.setX(Long.parseLong(reader.WaitData()));
                break;
            }catch (NumberFormatException e){
                System.out.println("Вид данных не определён, попробуйте снова");
            }catch (NotBeNullException e){
                System.out.println("Тут что-то должно быть");
            }
        }
    }
    
    private void HeightAsker(){
        while(true){
            try{
                System.out.println("Введите рост персонажа, но будте внимательны:");
                p.setHeight(Double.parseDouble(reader.WaitData()));
                break;
            } catch (RangeException e) {
                System.out.println("Вы не попали, у человек не может быть отрицательного роста, попробуйте позже, когда подрастёт");;
            }
            catch (NumberFormatException e){
                System.out.println("ну вы чего");
            }
        }
    }

    private void CreationDateAsker(){
        p.setCreationDate(LocalDateTime.parse(reader.WaitData()));
    }

    private void BirthdayAsker2(){
        p.setBirthday(LocalDateTime.parse(reader.WaitData()));
    }

    private void BirthdayAsker(){
        Date birthday = new Date();
        YearAsker(birthday);
        MounthAsker(birthday);
        DayAsker(birthday);
        HourAsker(birthday);
        MinuteAsker(birthday);
        p.setBirthday(LocalDateTime.of(birthday.getYear(), birthday.getMounth(), birthday.getDay(), birthday.getHour(), birthday.getMinute()));
    }



    private void YearAsker(Date birthday) {
        while(true){
            try {
                System.out.println("Введите год рождения:");
                birthday.setYear(Integer.parseInt(reader.WaitData()));
                break;
            } catch (RangeException e) {
                System.out.println("Вы уверены, что он ещё жив ?");
            }catch (NumberFormatException e ){
                System.out.println("Вы уверены, что это чило?");
            }
        }
    }
    public void MounthAsker(Date birthday) {
        while(true){
            try {
                System.out.println("Введите месяц рождения, помните, что их всего 12");
                birthday.setMounth(Integer.parseInt(reader.WaitData()));
                break;
            } catch (RangeException e) {
                System.out.println("Не попали");
            }
        }
    }
    public void DayAsker(Date birthday){
        while(true){
            try {
                System.out.println("Введите день рождения");
                birthday.setDay(Integer.parseInt(reader.WaitData()));
                break;
            } catch (RangeException e) {
                System.out.println("Увы их всего 31");
            }
        }
    }
    public void HourAsker(Date birthday){
        while(true){
            try {
                System.out.println("Введите час:");
                birthday.setHour(Integer.parseInt(reader.WaitData()));
                break;
            } catch (RangeException e) {
                System.out.println("Вы не поминте сколько часов в дне...");
            }
        }
    }
    public void MinuteAsker(Date birthday){
        while(true){
            try {
                System.out.println("Самое сложное, минуты...");
                birthday.setMinute(Integer.parseInt(reader.WaitData()));
                break;
            } catch (RangeException e) {
                System.out.println("Я расчитывал на большее");
            }
        }
    }

    public void EyeColorAsker(){
        while (true){
            try {
                System.out.println("Введите цвет глаз. Нужно выбрать один из цветов: green, yellow, orange");
                p.setEyeColor(reader.WaitData().toUpperCase());
                break;
            }catch (IllegalArgumentException e){
                System.out.println("НЕТ такого цвета! (по крайней мере у нас)");
            } catch (NotBeNullException e) {
                System.out.println("Увы, глаза всё же должны быть цветными");
            }
        }
    }

    public void HairColorAsker(){
        while(true){
            try {
                System.out.println("Введите цвет волос (если они есть, конечно). Нужно выбрать один из цветов: green, red, black, brown");
                p.setHairColor(reader.WaitData().toUpperCase());
                break;
            }catch (IllegalArgumentException e ){
                System.out.println("НЕТ такого цвета! (по крайней мере у нас)");
            }
        }
    }

    public void LocationAsker(){
        Location location = new Location();
        XLocalAsker(location);
        YLocalAsker(location);
        ZLocalAsker(location);
        p.setLocation(location);
    }
    public void XLocalAsker(Location location){
        while (true){
            try{
                System.out.println("Введите координату X локации:");
                location.setX(Integer.parseInt(reader.WaitData()));
                break;
            }catch (NumberFormatException e){
                System.out.println("Цифры выглядят не так");
            }
        }
    }
    public void YLocalAsker(Location location){
        while (true){
            try{
                System.out.println("Введите коориднату Y локации:");
                location.setY(Double.parseDouble(reader.WaitData().trim()));
                break;
            }catch (NumberFormatException e){
                System.out.println("Цифры выглядят не так");
            }
        }
    }
    public void ZLocalAsker(Location location){
        while (true){
            try{
                System.out.println("Введите координату Z локации:");
                location.setZ(Double.valueOf((reader.WaitData()).trim()));
                break;
            }catch (NumberFormatException e){
                System.out.println("Цифры выглядят не так");
            }catch (NotBeNullException e){
                System.out.println("Увы, Z есть всегда");
            }
        }
    }
}
