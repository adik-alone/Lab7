package commands;

import app.CommandList;

import java.util.TreeSet;

public class ShowCommand implements Command {
    CommandList list;
    @Override
    public void execute() {
        System.out.println("show.execute");
//        list.col_manager.Show();
        list.getLock().Show();
    }


    @Override
    public String info() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }

    @Override
    public void addToList(CommandList list) {
        this.list = list;
    }
}
