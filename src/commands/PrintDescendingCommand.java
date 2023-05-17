package commands;

import app.CommandList;

public class PrintDescendingCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
        list.col_manager.PrintDescending();
    }

    @Override
    public String info() {
        return "print_descending : вывести элементы коллекции в порядке убывания";
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
