package app;

public class Main {
    public static void main(String[] args) {
        App app = new App();
        CollectionManager collection = new CollectionManager(app);

        CommandList list = new CommandList(collection);

        CommandManager manager = new CommandManager(list);

        list.CreateList();

        app.start(manager);
   }
}