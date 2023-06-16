package commands;

import app.CommandList;

public class AddIFminCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
//        list.col_manager.add_if_min();
    }

    @Override
    public String info() {
        return "add_if_min {element} : добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции";
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
