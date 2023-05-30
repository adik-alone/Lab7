package cllient.commands;

import app.CommandList;
import cllient.ListWithCommandClient;

public class SingleCommand implements CommandClient {

    ListWithCommandClient lwcc;

    @Override
    public void execute() {

    }
    @Override
    public String info() {
        return null;
    }

    @Override
    public void addToList(ListWithCommandClient l) {
        this.lwcc = l;
    }
    @Override
    public void addToList(CommandList l) {
        return;
    }
}
