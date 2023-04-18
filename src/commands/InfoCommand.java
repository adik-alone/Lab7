package commands;

import app.CommandList;

public class InfoCommand implements Command{

    CommandList list;

    @Override
    public void execute() {
        list.col_manager.Info();
    }

    @Override
    public void info() {
        System.out.println("info : вывести в стандартный поток вывода информацию о коллекции");
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
