package commands;

import app.CommandList;

public class ExecuteScriptCommand implements Command{
    CommandList list;
    @Override
    public void execute() {
        list.col_manager.ExecuteScript();
    }

    @Override
    public void info() {
        System.out.println("execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.");
    }

    @Override
    public void addToList(CommandList l) {
        this.list = l;
    }
}
