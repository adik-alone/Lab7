package app;

import commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class CommandList {

     public CollectionManager col_manager;

    Map<String, Command> commandMap = new HashMap<>();

    public CommandList(CollectionManager col_manager){
        this.col_manager = col_manager;
    }

    private void CreateCommand (String name, Command command){
        commandMap.put(name, command);
        command.addToList(this);
    }
    public void CreateList(){
        CreateCommand("help", new HelpCommand());
        CreateCommand("info", new InfoCommand());
        CreateCommand("show", new ShowCommand());
        CreateCommand("add", new AddCommand()); // ожидает данные
//        CreateCommand("update", new UpdateCommand()); //ожидает данные
//        CreateCommand("remove_by_id", new RemoveBYidCommand()); //ожидает данные
//        CreateCommand("clear", new ClearCommand());
//        CreateCommand("save", new SaveCommand());
        CreateCommand("execute_script", new ExecuteScriptCommand()); //ожидает данные
//        CreateCommand("exit", new ExitCommand());
//        CreateCommand("add_if_max", new AddIFmaxCommand()); //ожидает данные
//        CreateCommand("add_if_min", new AddIFminCommand()); //ожидает данные
//        CreateCommand("remove_lower", new RemoveLowerCommand()); //ожидает данные
//        CreateCommand("average_of_height", new AverageOFheightCommand());
//        CreateCommand("max_by_height", new MaxBYheightCommand());
//        CreateCommand("print_descending", new PrintDescendingCommand());
    }

    public void ExecuteCommand(String s){
        commandMap.get(s).execute();
    }

    public void Help(){
        col_manager.app.Write("Иформация о командах");

        Set<String> keys = commandMap.keySet();

        for (String s: keys){
            col_manager.app.Write(commandMap.get(s).info());
        }
        col_manager.app.Write("+++SUCCESS+++");
    }
}
