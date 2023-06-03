package app;

public class CommandManager {
    String our_command;
    CommandList commandList;

    ScriptExecuter scriptExecuter;

    public CommandManager(CommandList list){
        this.commandList = list;
    }

    public void getCommand(String s){
        our_command = s;
        commandList.commandMap.get(s).execute();
    }

    public void setScriptExecuter(ScriptExecuter scriptExecuter) {
        this.scriptExecuter = scriptExecuter;
    }

    public ScriptExecuter getScriptExecuter() {
        return scriptExecuter;
    }
}
