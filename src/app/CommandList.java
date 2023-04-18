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

    public void CreateCommand (String name, Command command){
        commandMap.put(name, command);
        command.addToList(this);
    }
    public void CreateList(){
        CreateCommand("help", new HelpCommand());
        CreateCommand("info", new InfoCommand());
        CreateCommand("show", new ShowCommand());
        CreateCommand("add", new AddCommand());
//        CreateCommand("update", new UpdateCommand());
//        CreateCommand("remove_by_id", new RemoveBYidCommand());
        CreateCommand("clear", new ClearCommand());
//        CreateCommand("save", new SaveCommand());
        CreateCommand("execute_script", new ExecuteScriptCommand());
        CreateCommand("exit", new ExitCommand());
//        CreateCommand("add_if_max", new AddIFmaxCommand());
//        CreateCommand("add_if_min", new AddIFminCommand());
//        CreateCommand("remove_lower", new RemoveLowerCommand());
//        CreateCommand("average_of_height", new AverageOFheightCommand());
//        CreateCommand("max_by_height", new MaxBYheightCommand());
//        CreateCommand("print_descending", new PrintDescendingCommand());
    }

    public void Help(){
        System.out.println("Иформация о командах");

        Set<String> keys = commandMap.keySet();

        for (String s: keys){
            commandMap.get(s).info();
        }
        System.out.println("+++SUCSESS+++");
    }
}
