package cllient;

import app.App;

import java.util.HashMap;
import java.util.Map;

public class ListWithCommandClient {
    AppClient app;
    Map<String, RequestFactory> commandList = new HashMap<>();
    protected void Create(){
        commandList.put("help", new RequestFactorySingle( this));
        commandList.put("info", new RequestFactorySingle( this));
        commandList.put("show", new RequestFactorySingle(this));
//        commandList.put("clear", new RequestFactorySingle("clear", this));
//        commandList.put("average_of_height", new RequestFactorySingle("average_of_height"));
//        commandList.put("max_by_height", new RequestFactorySingle("max_by_height"));
//        commandList.put("print_descending", new RequestFactorySingle("print_descending"));
//        commandList.put("execute_script", new RequestFactoryData("execute_script"));
//        commandList.put("remove_by_id", new RequestFactoryData("remove_by_id"));
        commandList.put("add", new RequestFactoryPerson( this));
//        commandList.put("add_if_max", new RequestFactoryPerson("add_if_max"));
//        commandList.put("add_if_min", new RequestFactoryPerson("add_if_min"));
//        commandList.put("remove_lower", new RequestFactoryPerson("remove_lower"));
//        commandList.put("update", new RequestFactoryAll("update"));
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
