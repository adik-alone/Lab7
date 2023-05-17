package commands;

import app.CommandList;

public interface Command {

    void execute();

    String info();

    void addToList(CommandList l);
}
