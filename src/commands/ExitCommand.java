package commands;

import app.CommandList;

public class ExitCommand implements Command{

    CommandList list;

    public void execute(){

    }

    @Override
    public void info() {
        System.out.println("exit : завершить программу (без сохранения в файл)");
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
