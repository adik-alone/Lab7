package commands;

import app.CommandList;

public class RemoveLowerCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
        list.col_manager.removeLower();
    }
    @Override
    public String info() {
        return "remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный";
    }
    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
