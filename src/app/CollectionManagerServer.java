package app;

import person.Person;

import java.util.NoSuchElementException;
import java.util.Optional;

public class CollectionManagerServer extends CollectionManager{

    public CollectionManagerServer(App app) {
        super(app);
    }
    @Override
    public void Show(){
        app.Write("Показываем коллекцию");
        set.stream().sorted().forEach(app::Write);
    }

    @Override
    public void Remove(){
        app.Write("Удаляем объект");
        int id = 0;
        id = Integer.parseInt(app.getRequestData());
        final int ID = id;
//        set = (TreeSet<Person>) set.stream().filter(p -> p.getId() != ID).collect(Collectors.toSet());
        set.stream().filter(p -> p.getId() == ID).forEach(p -> set.remove(p));
    }
    @Override
    public void Add(){
//        OptionalInt currentID = ;
//        long id = currentID;
        Person person = app.getRequestPerson();
//        Optional<Person> per = set.stream().reduce((p1, p2) -> p1.getId() > p2.getId() ? p1 : p2);
        try {
            person.setId(set.last().getId() + 1);
        }catch (NoSuchElementException e ){
            person.setId(1);
        }
        set.add(person);
    }
}
