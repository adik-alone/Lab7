package app;

import person.Person;

import java.util.NoSuchElementException;
import java.util.Optional;

public class CollectionManagerServer extends CollectionManager{

//    public CollectionManagerServer(App app) {
//        super(app);
//    }
//    @Override
//    public void createCollection(){
//
//    }
//    @Override
//    synchronized public void Show(){
//        app.Write("Показываем коллекцию");
//        set.stream().sorted().forEach(app::Write);
//    }
//
//    @Override
//    synchronized public void Remove(){
//        app.Write("Удаляем объект");
//        int id = 0;
//        id = Integer.parseInt(app.getRequestData());
//        final int ID = id;
////        set = (TreeSet<Person>) set.stream().filter(p -> p.getId() != ID).collect(Collectors.toSet());
////        set.stream().filter(p -> p.getId() == ID).forEach(p -> set.remove(p));
//        for(Person p: set){
//            if (p.getId() == id){
//                set.remove(p);
//                app.Write("+++SUCCESS+++");
//                return;
//            }
//        }
//        app.Write("Такого элемента не найдено");
//    }
//    @Override
//    synchronized public void Add(){
////        OptionalInt currentID = ;
////        long id = currentID;
//        Person person = app.getRequestPerson();
////        Optional<Person> per = set.stream().reduce((p1, p2) -> p1.getId() > p2.getId() ? p1 : p2);
//        try {
//            person.setId(set.last().getId() + 1);
//        }catch (NoSuchElementException e ){
//            person.setId(1);
//        }
//        set.add(person);
//    }
//    @Override
//    synchronized public void Info(){
//        app.Write("Информация о коллекции");
//        app.Write("Размер коллекции: " + set.size());
//        app.Write("Тип коллекции: " + set.getClass());
//        app.Write("Время создания: " + creationTime);
//        app.Write("+++SUCCESS+++");
//    }
//    @Override
//    synchronized public void Update(){
//        app.Write("update.execute");
//        int id = 0;
//        id = Integer.parseInt(app.getRequestData());
//        for (Person p: set){
//            if (p.getId() == id){
//                set.remove(p);
//                Person person = app.getRequestPerson();
//                person.setId(id);
//                set.add(person);
//                app.Write("+++SUCCESS+++");
//                return;
//            }
//        }
//        app.Write("Такого элемента не найдено");
//    }
}
