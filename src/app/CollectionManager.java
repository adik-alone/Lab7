package app;

import person.Person;

import javax.sound.midi.Soundbank;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class CollectionManager {
    TreeSet<Person> set;

    LocalDateTime creationTime;

    App app;

    public CollectionManager(App app){
        this.app = app;
        set = new TreeSet<>();
        this.creationTime = LocalDateTime.now();

    }

    public void Add(){
        System.out.println("Добовляем элемент");
        Person person;
        try{
            person = app.createPerson(set.last().getId() + 1);
        }catch (NoSuchElementException e){
            person = app.createPerson(1);
        }
        set.add(person);
        System.out.println("+++SUCSESS+++");
    }

    public void Clear(){
        System.out.println("Чистим коллекцию");
        set.clear();
        System.out.println("+++SUCSESS+++");
    }

    public void Show(){
        System.out.println("Показываем коллекцию");

        if(set.isEmpty()){
            System.out.println("collection is empty");
        }else{
            System.out.println(set);
        }

        System.out.println("+++SUCSESS+++");
    }

    public void Info(){
        System.out.println("Информация о коллекции");
        System.out.println("Размер коллекции: " + set.size());
        System.out.println("Тип коллекции: " + set.getClass());
        System.out.println("Время создания: " + creationTime);
        System.out.println("+++SUCSESS+++");
    }
    public void ExecuteScript(){
        app.execute();
    }
}
