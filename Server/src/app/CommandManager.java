package app;

public class CommandManager {
    String our_command;
    CommandList commandList;

    ScriptExecuter skriptExecuter;

    public CommandManager(CommandList list){
        this.commandList = list;
    }

    public void getCommand(String s){
        our_command = s;
        commandList.commandMap.get(s).execute();
    }

    public void setSkriptExecuter(ScriptExecuter skriptExecuter) {
        this.skriptExecuter = skriptExecuter;
    }

    public ScriptExecuter getSkriptExecuter() {
        return skriptExecuter;
    }
}
