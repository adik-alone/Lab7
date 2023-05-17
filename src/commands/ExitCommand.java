package commands;

import app.CommandList;

public class ExitCommand implements Command{

    CommandList list;

    public void execute(){
        System.out.println("exit.execute");
        list.col_manager.Exit();
    }

    @Override
    public String info() {
        return "exit : завершить программу (без сохранения в файл)";
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
