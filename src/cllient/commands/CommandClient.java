package cllient.commands;

import cllient.ListWithCommandClient;
import commands.Command;

public interface CommandClient extends Command {
    void addToList(ListWithCommandClient l);
}
