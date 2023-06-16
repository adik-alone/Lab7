package app;

import app.MultiThread.ClientData;
import app.MultiThread.HandlerThread;
import app.MultiThread.SendAnswerThread;
import commands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class CommandList {

     public CollectionManager col_manager;

     public App app;

     HandlerThread lock;

    Map<String, Command> commandMap = new HashMap<>();

    public CommandList(App app){
//        this.col_manager = col_manager;
        this.app = app;
    }

    public HandlerThread getLock() {
        return lock;
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
        CreateCommand("update", new UpdateCommand()); //ожидает данные
        CreateCommand("remove_by_id", new RemoveBYidCommand()); //ожидает данные
        CreateCommand("clear", new ClearCommand());
//        CreateCommand("save", new SaveCommand());
//        CreateCommand("execute_script", new ExecuteScriptCommand()); //ожидает данные
//        CreateCommand("add_if_max", new AddIFmaxCommand()); //ожидает данные
//        CreateCommand("add_if_min", new AddIFminCommand()); //ожидает данные
//        CreateCommand("remove_lower", new RemoveLowerCommand()); //ожидает данные
//        CreateCommand("average_of_height", new AverageOFheightCommand());
//        CreateCommand("max_by_height", new MaxBYheightCommand());
//        CreateCommand("print_descending", new PrintDescendingCommand());

        CreateCommand("registration", new RegistrationCommand());
        CreateCommand("login", new LoginCommand());
    }

    public synchronized void ExecuteCommand(String s, HandlerThread now){
        lock = now;
        commandMap.get(s).execute();
        System.out.println("here");

        System.out.println(lock);
    }

    public void Help(ClientData data){
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();


        Thread send = new SendAnswerThread(data, queue);
        send.start();
        try {
            queue.put("Информация о командах");

            Set<String> keys = commandMap.keySet();

            for (String s : keys) {
                if(!(s.equals("login") || s.equals("registration"))) {
                    queue.put(commandMap.get(s).info());
                }
            }
            queue.put("+++SUCCESS+++");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
