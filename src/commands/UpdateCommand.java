package commands;

import app.CommandList;

public class UpdateCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
        list.col_manager.Update();
    }

    @Override
    public void info() {
        System.out.println("update id {element} : обновить значение элемента коллекции, id которого равен заданному");
    }

    @Override
    public void addToList(CommandList l) {
        list = l;
    }
}
