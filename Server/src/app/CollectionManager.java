package app;

import person.Person;

import javax.sound.midi.Soundbank;
import java.io.IOException;
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

    public void createCollection(){
        app.xmlworker.HowMany();
        for (int i = app.xmlworker.amount; i > 0; i--){
            Person person = app.creationStartPerson();
            set.add(person);
        }
    }

    public void Add(){
        System.out.println("Добовляем элемент ");
        Person person;
        try{
            person = app.createPerson(set.last().getId() + 1);
        }catch (NoSuchElementException e){
            person = app.createPerson(1);
        }
        set.add(person);
        System.out.println("+++SUCCESS+++");
    }

    public void Clear(){
        System.out.println("Чистим коллекцию");
        set.clear();
        System.out.println("+++SUCCESS+++");
    }

    public void Show(){
        System.out.println("Показываем коллекцию");

        if(set.isEmpty()){
            System.out.println("collection is empty");
        }else{
//            System.out.println(set);
            showCollection();
        }
        System.out.println("+++SUCCESS+++");
    }

    private void showCollection(){
        for (Person p: set){
            System.out.println(p);
//            System.out.println();
            System.out.println("====================================================");
            System.out.println();
        }
    }

    public void Info(){
        System.out.println("Информация о коллекции");
        System.out.println("Размер коллекции: " + set.size());
        System.out.println("Тип коллекции: " + set.getClass());
        System.out.println("Время создания: " + creationTime);
        System.out.println("+++SUCCESS+++");
    }
    public void ExecuteScript(){
        app.executeScript();
    }

    public void Save(){
        System.out.println("save.execute");
        try {
            app.xmlworker.save(set);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("+++SUCCESS+++");
    }

    public void Exit(){
        app.finish();
        System.out.println("+++SUCCESS+++");
    }

    public void Remove() {
        System.out.println("remove.execute");
        int id = Integer.parseInt(app.reader.WaitData());
        for(Person p: set){
            if (p.getId() == id){
                set.remove(p);
                System.out.println("+++SUCCESS+++");
                return;
            }
        }
        System.out.println("Такого элемента не найдено");
    }

    public void Update() {
        System.out.println("update.execute");

        int id = Integer.parseInt(app.reader.WaitData());
        for (Person p: set){
            if (p.getId() == id){
                set.remove(p);
                set.add(app.createPerson(id));
                System.out.println("+++SUCCESS+++");
                return;
            }
        }
        System.out.println("Такого элемента не найдено");
    }

    public void removeLower() {
        Person p = app.createPerson(-1);
        set.removeIf(p1 -> p.getHeight() > p1.getHeight());
        System.out.println("+++SUCCESS+++");
    }

    public void add_if_max() {
        Person p = app.createPerson(set.last().getId() + 1);
        for (Person p1: set){
            if (p1.getHeight() > p.getHeight()){
                System.out.println("Сущестует больший элемент");
                System.out.println("+++SUCCESS+++");
                return;
            }
        }
        set.add(p);
        System.out.println("+++SUCCESS+++");
    }

    public void add_if_min() {
        Person p = app.createPerson(set.last().getId() + 1);
        for (Person p1: set){
            if (p1.getHeight() < p.getHeight()){
                System.out.println("Сущестует меньший элемент");
                System.out.println("+++SUCCESS+++");
                return;
            }
        }
        set.add(p);
        System.out.println("+++SUCCESS+++");
    }

    public void average() {
        double h = 0;
        for (Person p: set){
            h += p.getHeight();
        }
        h = h / set.size();
        System.out.println(h + " метров : среднее значение высоты в группе");
        System.out.println("+++SUCCESS+++");
    }

    public void max_by_height() {
        Person p = set.first();
        for (Person p1: set){
            if (p1.getHeight() > p.getHeight()){
                p = p1;
            }
        }
        System.out.println(p);
        System.out.println("+++SUCCESS+++");
    }

    public void PrintDescending() {
        for(Person p: set.descendingSet()){
            System.out.println(p);
            System.out.println("===================================");
        }
        System.out.println("+++SUCCESS+++");
    }
}
