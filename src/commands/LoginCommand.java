package commands;

import app.CommandList;

public class LoginCommand implements Command{
    CommandList list;
    @Override
    public void execute() {
        System.out.println("I am in login command");
        list.getLock().Login();
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
