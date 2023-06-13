package cllient.commands;

import app.CommandList;
import cllient.ListWithCommandClient;

public class AllCommand implements CommandClient{
    ListWithCommandClient lwcc;
    @Override
    public void addToList(ListWithCommandClient l) {
        this.lwcc = l;
    }

    @Override
    public void execute() {
        lwcc.getApp().AllRequest();
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public void addToList(CommandList l) {
        return;
    }
}
