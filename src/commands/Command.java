package commands;

import app.CommandList;

public interface Command {

    void execute();

    void info();

    void addToList(CommandList l);
}
