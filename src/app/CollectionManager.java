package app;

import person.Person;

import javax.sound.midi.Soundbank;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.TreeSet;

public class CollectionManager {
//    TreeSet<Person> set;
//    LocalDateTime creationTime;
//    App app;
//
//    public CollectionManager(App app){
//        this.app = app;
//        set = new TreeSet<>();
//        this.creationTime = LocalDateTime.now();
//    }
//
//    public void createCollection(){
//        app.xmlworker.HowMany();
//        for (int i = app.xmlworker.amount; i > 0; i--){
//            Person person = app.creationStartPerson();
//            set.add(person);
//        }
//        System.out.println("Collection created success");
//    }
//
//    public void Add(){
//        app.Write("Добавляем элемент ");
//        Person person;
//        try{
//            person = app.createPerson(set.last().getId() + 1);
//        }catch (NoSuchElementException e){
//            person = app.createPerson(1);
//        }
//        set.add(person);
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void Clear(){
//        app.Write("Чистим коллекцию");
//        set.clear();
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void Show(){
//        app.Write("Показываем коллекцию");
//
//        if(set.isEmpty()){
//            app.Write("collection is empty");
//        }else{
////            System.out.println(set);
//            showCollection();
//        }
//        app.Write("+++SUCCESS+++");
//    }
//
//    private void showCollection(){
//        for (Person p: set){
//            app.Write(p.toString());
////            System.out.println();
//            app.Write("====================================================");
//            app.Write("");
//        }
//    }
//
//    public void Info(){
//        app.Write("Информация о коллекции");
//        app.Write("Размер коллекции: " + set.size());
//        app.Write("Тип коллекции: " + set.getClass());
//        app.Write("Время создания: " + creationTime);
//        app.Write("+++SUCCESS+++");
//    }
////    public void ExecuteScript(){
////        app.executeScript();
////    }
//
//    public void Save(){
//        System.out.println("save.execute");
//        try {
//            app.xmlworker.save(set);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("+++SUCCESS+++");
//    }
//
////    public void Exit(){
////        app.finish();
////        app.Write("+++SUCCESS+++");
////    }
//
//    public void Remove() {
//        app.Write("remove.execute");
//        int id = 0;
//        id = Integer.parseInt(app.reader.WaitData());
//        for(Person p: set){
//            if (p.getId() == id){
//                set.remove(p);
//                app.Write("+++SUCCESS+++");
//                return;
//            }
//        }
//        app.Write("Такого элемента не найдено");
//    }
//
//    public void Update() {
//        app.Write("update.execute");
//
//        int id = 0;
//        id = Integer.parseInt(app.reader.WaitData());
//        for (Person p: set){
//            if (p.getId() == id){
//                set.remove(p);
//                set.add(app.createPerson(id));
//                app.Write("+++SUCCESS+++");
//                return;
//            }
//        }
//        app.Write("Такого элемента не найдено");
//    }
//
//    public void removeLower() {
//        Person p = app.createPerson(-1);
//        set.removeIf(p1 -> p.getHeight() > p1.getHeight());
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void add_if_max() {
//        Person p = app.createPerson(set.last().getId() + 1);
//        for (Person p1: set){
//            if (p1.getHeight() > p.getHeight()){
//                app.Write("Существует больший элемент");
//                app.Write("+++SUCCESS+++");
//                return;
//            }
//        }
//        set.add(p);
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void add_if_min() {
//        Person p = app.createPerson(set.last().getId() + 1);
//        for (Person p1: set){
//            if (p1.getHeight() < p.getHeight()){
//                app.Write("Существует меньший элемент");
//                app.Write("+++SUCCESS+++");
//                return;
//            }
//        }
//        set.add(p);
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void average() {
//        double h = 0;
//        for (Person p: set){
//            h += p.getHeight();
//        }
//        h = h / set.size();
//        app.Write(h + " метров : среднее значение высоты в группе");
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void max_by_height() {
//        Person p = set.first();
//        for (Person p1: set){
//            if (p1.getHeight() > p.getHeight()){
//                p = p1;
//            }
//        }
//        app.Write(p.toString());
//        app.Write("+++SUCCESS+++");
//    }
//
//    public void PrintDescending() {
//        for(Person p: set.descendingSet()){
//            app.Write(p.toString());
//            app.Write("===================================");
//        }
//        app.Write("+++SUCCESS+++");
//    }
}
