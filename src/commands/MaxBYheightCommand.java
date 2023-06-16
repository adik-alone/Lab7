package commands;

import app.CommandList;

public class MaxBYheightCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
//        list.col_manager.max_by_height();
    }

    @Override
    public String info() {
        return "max_by_height : вывести любой объект из коллекции, значение поля height которого является максимальным";
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
