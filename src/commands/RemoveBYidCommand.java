package commands;

import app.CommandList;

public class RemoveBYidCommand implements Command {

    CommandList list;
    @Override
    public void execute() {
        System.out.println("remove.execute");
//        list.col_manager.Remove();
        list.getLock().Remove();
    }

    @Override
    public String info() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
