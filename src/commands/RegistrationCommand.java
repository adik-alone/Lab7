package commands;

import app.CommandList;

public class RegistrationCommand implements Command{
    CommandList list;

    @Override
    public void execute() {
        list.app.Registration();
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
