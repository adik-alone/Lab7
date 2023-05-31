package cllient;

import commands.Command;
import person.Person;

import java.io.Serializable;

public class Request implements Serializable {
    String command = null;
    Person createdPerson = null;
    String data = null;

    protected void setCommand(String s){
        this.command = s;
    }
    protected void setCreatedPerson(Person p){
        this.createdPerson = p;
    }

    protected void setData(String s){
        this.data = s;
    }

    public String getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }

    public Person getCreatedPerson() {
        return createdPerson;
    }

    @Override
    public String toString() {
        return "Имя команды: " + command + "\n" + "Дополнительные данные: " + data + "\n" + "Созданная персона: " + "\n" + createdPerson;
    }
}
