package commands;

import app.CommandList;

public class AddIFmaxCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
//        list.col_manager.add_if_max();
    }

    @Override
    public String info() {
        return "add_if_max {element} : добавить новый элемент в коллекцию, если его значение больше, чем у наибольшего элемента этой коллекции";
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
