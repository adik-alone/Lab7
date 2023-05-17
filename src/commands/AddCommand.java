package commands;

import app.CommandList;

import java.util.TreeSet;

public class AddCommand implements Command {

    CommandList list;
    @Override
    public void execute() {
        System.out.println("add.execute");
        list.col_manager.Add();
    }

    @Override
    public String info() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
