package cllient;

import cllient.commands.PersonCommand;
import cllient.commands.ScriptCommand;
import cllient.commands.SingleCommand;
import commands.Command;
import cllient.commands.CommandClient;

import java.util.HashMap;
import java.util.Map;

public class ListWithCommandClient {
    AppClient app;
    Map<String, Command> commandList = new HashMap<>();

    private void CreateCommand (String name, CommandClient command){
        commandList.put(name, command);
        command.addToList(this);
    }
    protected void Create(){
        CreateCommand("help", new SingleCommand());
        CreateCommand("info", new SingleCommand());
        CreateCommand("show", new SingleCommand());
//        CreateCommand("clear", new RequestFactorySingle("clear", this));
//        CreateCommand("average_of_height", new RequestFactorySingle("average_of_height"));
//        CreateCommand("max_by_height", new RequestFactorySingle("max_by_height"));
//        CreateCommand("print_descending", new RequestFactorySingle("print_descending"));
//        CreateCommand("execute_script", new RequestFactoryData("execute_script"));
//        CreateCommand("remove_by_id", new RequestFactoryData("remove_by_id"));
        CreateCommand("add", new PersonCommand());
//        CreateCommand("add_if_max", new RequestFactoryPerson("add_if_max"));
//        CreateCommand("add_if_min", new RequestFactoryPerson("add_if_min"));
//        CreateCommand("remove_lower", new RequestFactoryPerson("remove_lower"));
//        CreateCommand("update", new RequestFactoryAll("update"));
        CreateCommand("execute_script", new ScriptCommand());
    }

    public void CreateRequest(String s){
        commandList.get(s).execute();
    }

    public void setApp(AppClient app){
        this.app = app;
    }

    public AppClient getApp(){
        return this.app;
    }
}
