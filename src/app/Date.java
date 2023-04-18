package app;

import exception.RangeException;

public class Date {
    int year;
    int mounth;
    int day;
    int hour;
    int minute;


    public void setYear(int year) throws RangeException {
        if (year < 1900) throw new RangeException();
        if (year > 2023) throw new RangeException();
        this.year = year;
    }

    public void setMounth(int mounth) throws RangeException{
        if((mounth <= 0) || (mounth > 12)) throw new RangeException();
        this.mounth = mounth;
    }

    public void setDay(int day) throws RangeException{
        if (day <= 0 || day > 31) throw new RangeException();
        this.day = day;
    }

    public void setHour(int hour) throws RangeException{
        if (hour < 0 || hour > 24) throw new RangeException();
        this.hour = hour;
    }

    public void setMinute(int minute) throws RangeException {
        if (minute < 0 || minute >= 60) throw new RangeException();
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public int getMounth() {
        return mounth;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
