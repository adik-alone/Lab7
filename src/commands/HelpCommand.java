package commands;

import app.CommandList;

public class HelpCommand implements Command {

    CommandList list;

    @Override
    public void execute() {
        System.out.println("help.execute");
//        list.Help();
        list.getLock().Help();
    }

    @Override
    public String info() {
        return "help : вывести справку по доступным командам";
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
