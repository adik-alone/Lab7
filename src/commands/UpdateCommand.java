package commands;

import app.CommandList;

public class UpdateCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
        System.out.println("update.execute");
//        list.col_manager.Update();
        list.getLock().Update();
    }

    @Override
    public String info() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
