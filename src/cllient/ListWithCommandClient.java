package cllient;

import cllient.commands.*;
import commands.Command;

import java.util.HashMap;
import java.util.Map;

public class ListWithCommandClient {
    AppClient app;
    RequestFactory requestFactory;
    String nameCommand;
    Map<String, Command> commandList = new HashMap<>();

    private void CreateCommand (String name, CommandClient command){
        commandList.put(name, command);
        command.addToList(this);
    }
    protected void Create(){
        CreateCommand("help", new SingleCommand());
        CreateCommand("info", new SingleCommand());
        CreateCommand("show", new SingleCommand());
        CreateCommand("clear", new SingleCommand());
        CreateCommand("average_of_height", new SingleCommand());
        CreateCommand("max_by_height", new SingleCommand());
        CreateCommand("print_descending",new SingleCommand());
        CreateCommand("remove_by_id", new OneCommand());
        CreateCommand("add", new PersonCommand());
        CreateCommand("add_if_max", new PersonCommand());
        CreateCommand("add_if_min",new PersonCommand());
        CreateCommand("remove_lower",new PersonCommand());
        CreateCommand("update", new AllCommand());
        CreateCommand("execute_script", new ScriptCommand());
    }

    public void ExecuteCommand(String s){
        nameCommand = s;
        commandList.get(s).execute();
    }

    public String getCurrentNameCommand(){
        return nameCommand;
    }

    public void setApp(AppClient app){
        this.app = app;
    }

    public AppClient getApp(){
        return this.app;
    }

    public void setRequestFactory(RequestFactory rf){
        this.requestFactory = rf;
    }
    public RequestFactory getRequestFactory(){
        return requestFactory;
    }
}
