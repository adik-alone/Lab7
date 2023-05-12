package commands;

import app.CommandList;

public class SaveCommand implements Command{

    CommandList list;
    @Override
    public void execute() {
        System.out.println("save.execute");
        this.list.col_manager.Save();
    }

    @Override
    public void info() {
        System.out.println("save : сохранить коллекцию в файл");
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
