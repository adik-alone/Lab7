package commands;

import app.CommandList;

import java.util.TreeSet;

public class ClearCommand implements Command {

    CommandList list;

    public void execute(){
        list.col_manager.Clear();
    }

    @Override
    public String info() {
        return "clear : очистить коллекцию";
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
