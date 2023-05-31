package cllient.commands;

import app.CommandList;
import cllient.ListWithCommandClient;

public class ScriptCommand implements CommandClient{

    ListWithCommandClient lwcc;
    @Override
    public void addToList(ListWithCommandClient l) {
        this.lwcc = l;
    }

    @Override
    public void execute() {
        lwcc.getApp().executeScript();
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
